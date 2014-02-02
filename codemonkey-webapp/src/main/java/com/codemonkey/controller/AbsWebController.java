package com.codemonkey.controller;

import com.codemonkey.domain.IEntity;
import com.codemonkey.service.GenericService;
import com.codemonkey.web.controller.AbsController;

public abstract class AbsWebController<T extends IEntity> extends AbsController{

	protected abstract GenericService<T> service();
	
}
