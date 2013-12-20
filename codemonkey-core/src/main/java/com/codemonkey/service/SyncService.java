package com.codemonkey.service;

import com.codemonkey.domain.IEntity;
import com.codemonkey.security.Operation;

public interface SyncService {

	void dosync(IEntity t, Operation op);

}
