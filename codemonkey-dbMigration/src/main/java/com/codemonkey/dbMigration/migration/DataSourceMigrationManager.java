package com.codemonkey.dbMigration.migration;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.sql.DataSource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;

import com.codemonkey.dbMigration.jdbc.DBType;
import com.codemonkey.dbMigration.jdbc.DatabaseUtils;
import com.codemonkey.dbMigration.resovler.MigrationResolver;
import com.codemonkey.dbMigration.resovler.MigrationResolverFactory;
import com.codemonkey.dbMigration.script.Migration;
import com.codemonkey.dbMigration.version.SimpleVersionStrategy;
import com.codemonkey.dbMigration.version.VersionStrategy;

public class DataSourceMigrationManager implements MigrationManager {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final JdbcTemplate jdbcTemplate;
	private DBType dbType;
	private VersionStrategy versionStrategy = new SimpleVersionStrategy();
	private MigrationResolverFactory migrationResolverFactory = new MigrationResolverFactory();

	public DataSourceMigrationManager(DataSource dataSource) {
		this(dataSource, DBType.UNKNOWN);
		this.dbType = determineDatabaseType();
	}

	public DataSourceMigrationManager(DataSource dataSource, DBType dbType) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.dbType = dbType;
	}

	public boolean validate() {
		return pendingMigrations().isEmpty();
	}

	public SortedSet<Migration> pendingMigrations() {
		Set<String> appliedMigrations = determineAppliedMigrationVersions();
		Set<Migration> availableMigrations = getAvailableMigrations();

		SortedSet<Migration> pendingMigrations = new TreeSet<Migration>();
		CollectionUtils.select(availableMigrations,
				new PendingMigrationPredicate(appliedMigrations),
				pendingMigrations);

		return pendingMigrations;
	}

	private Set<Migration> getAvailableMigrations() {
		Set<Migration> migrations = new HashSet<Migration>();
		List<MigrationResolver> resolvers = migrationResolverFactory.getAll();
		for(MigrationResolver r : resolvers){
			migrations.addAll(r.resolve());
		}
		return migrations;
	}

	public void migrate() {
		// Are migrations enabled?
		if (!isMigrationsEnabled()) {
			enableMigrations();
		}

		final Collection<Migration> pendingMigrations = pendingMigrations();

		if (pendingMigrations.isEmpty()) {
			logger.info("Database is up to date; no migration necessary.");
			return;
		}

		StopWatch watch = new StopWatch();
		watch.start();

		logger.info("Migrating database... applying "
				+ pendingMigrations.size() + " migration"
				+ (pendingMigrations.size() > 1 ? "s" : "") + ".");

		try {
			jdbcTemplate.execute(new ConnectionCallback<Object>() {
				public Object doInConnection(Connection connection)
						throws SQLException, DataAccessException {
					Migration currentMigration = null;

					final boolean autoCommit = connection.getAutoCommit();
					connection.setAutoCommit(false);

					try {
						for (Migration migration : pendingMigrations) {
							currentMigration = migration;
							logger.info("Running migration "
									+ currentMigration.getFilename() + ".");

							final Date startTime = new Date();
							StopWatch migrationWatch = new StopWatch();
							migrationWatch.start();

							currentMigration.migrate(dbType, connection);
							versionStrategy.recordMigration(dbType, connection,
									currentMigration.getVersion(), startTime,
									migrationWatch.getTime());

							connection.commit();
						}
					} catch (Exception e) {
						assert currentMigration != null;
						String message = "Migration for version "
								+ currentMigration.getVersion()
								+ " failed, rolling back and terminating migration.";
						logger.error(message, e);
						connection.rollback();
						throw new MigrationException(message, e);
					} finally {
						connection.setAutoCommit(autoCommit);
					}
					return null;
				}
			});
		} catch (DataAccessException e) {
			logger.error("Failed to migrate database.", e);
			throw new MigrationException(e);
		}

		watch.stop();

		logger.info("Migrated database in "
				+ DurationFormatUtils.formatDurationHMS(watch.getTime()) + ".");
	}

	public void setDatabaseType(DBType dbType) {
		this.dbType = dbType;
	}

	public void setVersionStrategy(VersionStrategy versionStrategy) {
		this.versionStrategy = versionStrategy;
	}

	protected DBType determineDatabaseType() {
		return jdbcTemplate.execute(new ConnectionCallback<DBType>() {
			public DBType doInConnection(Connection connection)
					throws SQLException, DataAccessException {
				return DatabaseUtils.databaseType(connection.getMetaData()
						.getURL());
			}
		});
	}

	protected boolean isMigrationsEnabled() {
		try {
			return jdbcTemplate.execute(new ConnectionCallback<Boolean>() {
				public Boolean doInConnection(Connection connection)
						throws SQLException, DataAccessException {
					return versionStrategy.isEnabled(dbType, connection);
				}
			});
		} catch (DataAccessException e) {
			logger.error("Could not enable migrations.", e);
			throw new MigrationException(e);
		}
	}

	protected void enableMigrations() {
		try {
			
			jdbcTemplate.execute(new ConnectionCallback<Object>() {
				public Object doInConnection(Connection connection)
						throws SQLException, DataAccessException {
					versionStrategy.enableVersioning(dbType, connection);
					return null;
				}
			});

			logger.info("Successfully enabled migrations.");
		} catch (DataAccessException e) {
			logger.error("Could not enable migrations.", e);
			throw new MigrationException(e);
		}
	}

	protected Set<String> determineAppliedMigrationVersions() {
		return jdbcTemplate.execute(new ConnectionCallback<Set<String>>() {
			public Set<String> doInConnection(Connection connection)
					throws SQLException, DataAccessException {
				return versionStrategy.appliedMigrations(dbType, connection);
			}
		});
	}

	private static class PendingMigrationPredicate implements Predicate {
		private final Set<String> appliedMigrations;

		public PendingMigrationPredicate(Set<String> appliedMigrations) {
			this.appliedMigrations = appliedMigrations == null ? new HashSet<String>()
					: appliedMigrations;
		}

		public boolean evaluate(Object input) {
			if (input instanceof Migration) {
				return !appliedMigrations.contains(((Migration) input)
						.getVersion());
			} else {
				return !appliedMigrations.contains(input.toString());
			}
		}
	}
}
