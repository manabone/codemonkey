package com.codemonkey.erp.controller;

import org.springframework.stereotype.Controller;

import com.codemonkey.erp.domain.Document;
import com.codemonkey.web.controller.AbsListExtController;

@Controller
public abstract class DocumentListController<T extends Document> extends AbsListExtController<T>{

}
