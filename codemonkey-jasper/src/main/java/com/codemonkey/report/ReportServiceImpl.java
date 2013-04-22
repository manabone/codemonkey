package com.codemonkey.report;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import com.codemonkey.service.AbsService;

@Component
public class ReportServiceImpl extends AbsService implements ReportService{

	@Autowired private DriverManagerDataSource datasource;
	
	/* (non-Javadoc)
	 * @see com.codemonkey.report.ReportService#pdf(java.lang.String, java.lang.String)
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public void pdf(String destinationFile, String templatePath) {
		JasperReport jasperReport;
		JasperPrint jasperPrint;
		Connection connection = null;
		try {
			jasperReport = getJasperReport(templatePath);
			connection = datasource.getConnection();
			jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap(), connection);
			JasperExportManager.exportReportToPdfFile(jasperPrint, destinationFile);
			
		} catch (JRException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private JasperReport getJasperReport(String templatePath){
		JasperReport jasperReport = null;
		try {
			URL url = ClassLoader.getSystemResource(templatePath);
			jasperReport = (JasperReport)JRLoader.loadObject(url.getFile());
			getLog().info(url);
		} catch (JRException e) {
			e.printStackTrace();
		}
		return jasperReport;
	}
	
	/* (non-Javadoc)
	 * @see com.codemonkey.report.ReportService#xml(java.lang.String, java.lang.String)
	 */
	@Override
	public void xml(String destinationFile, String templatePath){

		JasperReport jasperReport;
		JasperPrint jasperPrint;
		Connection connection = null;
		try {
			jasperReport = getJasperReport(templatePath);
			connection = datasource.getConnection();
			jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<Object, Object>(), connection);
			JasperExportManager.exportReportToXmlFile(jasperPrint, destinationFile, false);
			
		} catch (JRException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
