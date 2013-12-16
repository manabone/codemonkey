package com.codemonkey.controller;

import com.codemonkey.domain.EE;
import com.codemonkey.service.GenericService;
import com.codemonkey.web.controller.AbsController;

public abstract class AbsWebController<T extends EE> extends AbsController{

	protected abstract GenericService<T> service();
	
}
