var CONSTANTS = {
	PAGE_SIZE : 25,
	APP_NAME : 'AM',
	STORE : 'store',
	VIEW : 'view',
	MODEL: 'model',
	CONTROLLER : 'controller',
	APP_FOLDER : '/js/apps',
	PROXY_TYPE : 'ajax',
	READER_TYPE : 'json',
	READER_ROOT : 'data',
	READER_SUCCESS : 'success',
	READER_TOTAL : 'totalCount',
	
	READ : 'read',
	CREATE : 'create',
	UPDATE : 'update',
	DESTROY : 'destroy',
	THEME : 'theme'
};

Ext.form.field.Date.prototype.format = 'Y-m-d';

var ExtUtils = {
	//-------------------------------------------------------
	//           DEFAULT CONSTANTS
	//-------------------------------------------------------
	
	defaultModelFields : ['id','name','description'],
	
	defaultGridCols : [	
       	{header: 'id'          ,  dataIndex: 'id'         ,  flex: 1 , hidden : true},
		{header: 'name'        ,  dataIndex: 'name'       ,  flex: 1},
		{header: 'description' ,  dataIndex: 'description',  flex: 1}
	],
	
	defaultFormItems : [
		{xtype:"textfield"  , name:"name"        , fieldLabel:"name"},
		{xtype:"textfield"  , name:"description" , fieldLabel:"description"},
		{xtype:"numberfield", name:"id"          , fieldLabel:"id" , hidden : true}
	],
	
	defaultFormButtons : [{
		action : 'save',
		iconCls: 'icon-update',
		text : 'Save'
	},{
		action : 'backToList',
		iconCls: 'icon-back-to-list',
		text : 'Back to List'
	}],
		
	//-------------------------------------------------------
	//           EXT APP UTILs
	//-------------------------------------------------------
	
	app : function(cfg){
		var me = this;
		var defaults = {
		    name: CONSTANTS.APP_NAME,
		    indexView : null,
		    appFolder: CONSTANTS.APP_FOLDER,

		    // automatically create an instance of AM.view.Viewport
		    autoCreateViewport: false,

		    launch : function(){
		    	var items = [{
		    		region : 'north',
		    		xtype : 'toolbar',
		    		items : [{xtype : 'tbspacer',flex : 1} , me.themeSelect(PAGE_DATA)]
		    	}];

		    	if (Ext.isString(this.indexView)) {
		    		items.push({region:'center' , xtype : this.indexView});
				}else if(Ext.isObject(this.indexView)){
					Ext.apply(this.indexView , {region:'center'});
					items.push(this.indexView);
				}else if(Ext.isArray(this.indexView)){
					items = {region:'center' , items : this.indexView};
				}
		    	Ext.create('AM.view.Viewport', {
		    		layout : 'border',
		    		
					items : items
				});
		    }
		};

		var config = Ext.apply({} , cfg , defaults);

		Ext.application(config);
	},
	
	//-------------------------------------------------------
	//           UI UTILs
	//-------------------------------------------------------
		
	searchingForm : function(config){
		var defaultConfig = {
			xtype : 'form',
			layout : 'column',
			border : 0,
			id : 'searchForm',
			title : 'search',
			height : 100 ,
			defaultType: 'textfield',
			bodyPadding: 10,
			collapsible : true,
			collapsed : true,
			collapseFirst : true,
			items : [
				{id : 'query'  , name  : 'query'   , xtype : 'textfield' , fieldLabel : 'name' }
			],
			buttons : [
				{xtype : 'button' , iconCls: 'icon-search', text : 'search' , action : 'search'}, 
				{xtype : 'button' , iconCls: 'icon-reset', text : 'reset' , action : 'reset'}     
            ]
			
		};
		return Ext.applyIf(config , defaultConfig);
	},
	
	themeSelect : function(PAGE_DATA){
        return{
        	editable: false,
            fieldLabel: 'Theme',
            labelWidth: 50,
            value: PAGE_DATA[CONSTANTS.THEME] || 'classic',
            width: 180,
            xtype: 'combo',
            listeners: {
                change: function(combo, value) {
                    var params = {};
                	params.theme = value;
                    location.search = Ext.Object.toQueryString(params);
                }
            },
            store: [
                ['classic', 'Classic'],
                ['gray', 'Gray']
            ],
            style: {
                margin: '2px'
            }
        };
	},
	
	mask : function(flag , msg){
		var cmp = Ext.getCmp('viewport');
		msg = msg || 'processing';
        if(cmp){
        	if(flag){
        		cmp.getEl().mask(msg);
        	}else{
        		cmp.getEl().unmask();
        	}
        }
	},
	msg : function(key){
		
		if(PAGE_DATA.labels[key]){
			return PAGE_DATA.labels[key];
		}
		
		return key;
	},
	
	markInvalidFields : function(formId , errors){
		var formPanel = Ext.getCmp(formId);
		var form = formPanel.getForm();
		for(var p in errors){
			var field = form.findField(p);
			if(field){
				field.markInvalid(errors[p]);
			}
		}
	},
	
	/*
	 * config{
	 * 	id : id,
	 * 	store : store,
	 * 	cols
	 * 	itemdblclick : function
	 * 	okclick : function
	 * }
	 * */
	popup : function(config){
		
		var win = Ext.create('Ext.window.Window', {
			id : config.id + 'popupWin',  
	        height: 400,
	        width: 600,
	        modal : true,
	        title: 'Select',
	        layout : {
				type : 'vbox',
				align : 'stretch',
				pack  : 'start'
			},
	        items: [{
				xtype : 'form',
				layout : 'column',
				border : 0,
				id : 'popup-form',
				flex : 1 ,
		    	defaultType: 'textfield',
		    	bodyPadding: '10% 40%',
		    	items : [
					{id : 'popup-query'  , name  : 'query'   , fieldLabel : 'name' },
					{id : 'popup-search' , xtype : 'button' , text : 'search', handler : function(){
						var store = Ext.getCmp('grid').getStore();
			    		ExtUtils.gridSearch(store, 'popup-form');
			    		
					}}, 
					{id : 'popup-reset'  , xtype : 'button' , text : 'reset' , handler : function(){
						var store = Ext.getCmp('grid').getStore();
			    		ExtUtils.gridReset(store, 'popup-form');
					}}
				]
	        },{
	        	xtype:'grid',
	        	flex : 6 ,
	        	store: config.store,
	        	columns: config.columns ,
	        	selType : config.selType || 'rowmodel',
	        	selModel : config.selModel,
	        	id : 'grid',
				listeners : {
					itemdblclick : {
						fn : function (/* Ext.view.View*/ view ,/* Ext.data.Model*/ record, /*HTMLElement*/ item,/* Number*/ index, /*Ext.EventObject*/ e, /*Object*/ eOpts ){
							config.itemdblclick(view , record , item , e , eOpts);
						}
					}
				}
	        }],
	        buttons : [{
			    id : 'ok',
				text: 'OK',
				handler : function(){
					var selModel = this.up('window').down('grid').getSelectionModel();
					var records = selModel.getSelection();
					if(records){
						if(selModel.mode == 'MULTI'){
							config.okclick(records);
						}else if(selModel.mode == 'SINGLE' && records[0] ){
							config.okclick(records[0]);
						}
					}
			    	this.up('window').close();
			    }
			}, {
				id: 'cancel',
			    text: 'Cancel',
			    handler : function(){
			    	this.up('window').close();
			    }
			}]
	    });
	    win.show();
	},
	
	//-------------------------------------------------------
	//           COMMON UTILs
	//-------------------------------------------------------	
	
	loadToForm : function (store , id , cmpId){
		var proxy = store.getProxy();
		if(id){
			proxy.extraParams = {id : id};
			store.load(function(options){
				var formPanel = Ext.getCmp(cmpId);
	    		formPanel.getForm().loadRecord(store.getAt(0));
			});
		}
	},
	
	form2record : function (cmpId , record){
		var	values = this.formValues(cmpId);
		if(values){
			record.set(values);
		}
		return record;
	},
	
	record2form : function(record , formId){
		var formPanel = Ext.getCmp(formId);
		formPanel.down('form').loadRecord(record);
	},
	
	formValues : function(formId){
		
		if(!formId) return null;
		
		var formPanel = Ext.getCmp(formId);
		
		if(!formPanel) return null;
		
		var form = formPanel.getForm();
		if(form.isValid()){
			return form.getFieldValues();
		}
		return null;
	},
	
	openUrl : function(url){
		window.open(url, '_self');
	},
	
	getStore: function(name) {
        var store = Ext.StoreManager.get(name);

        if (!store) {
            store = Ext.create(this.getModuleClassName(name, 'store'), {
                storeId: name
            });
        }

        return store;
    },

    getModel: function(model) {
        model = this.getModuleClassName(model, 'model');

        return Ext.ModelManager.getModel(model);
    },
	//-------------------------------------------------------
	//           MODULE UTILs
	//-------------------------------------------------------	
    getModuleClassName: function(name, module) {
        // Deciding if a class name must be qualified:
		// 1 - if the name doesn't contains at least one dot, we must definitely qualify it
		// 2 - the name may be a qualified name of a known class, but:
		// 2.1 - in runtime, the loader may not know the class - specially in production - so we must check the class manager
		// 2.2 - in build time, the class manager may not know the class, but the loader does, so we check the second one
		//       (the loader check assures it's really a class, and not a namespace, so we can have 'Books.controller.Books',
		//       and request for a controller called Books will not be underqualified)
		if (name.indexOf('.') !== -1 && (Ext.ClassManager.isCreated(name) || Ext.Loader.isAClassNameWithAKnownPrefix(name))) {
		    return name;
		} else {
		    return CONSTANTS.APP_NAME + '.' + module + '.' + name;
	    }
	},
	
	moduleDoSave : function(module , callback){
    	var values = ExtUtils.formValues(module.formId);
    	if(values){
    		var model = module.Model.create(values);
        	model.save({
        		success: function(model) {
        			Ext.getCmp(module.formId).getForm().loadRecord(model);
        			if(callback){
        			     callback(module , model);
        			}
        		},
        		
        		failure: function(rec, op) {
        			Ext.Msg.alert("Failed",op.request.scope.reader.jsonData["errorMsg"]);
        			var errors = op.request.scope.reader.jsonData["errorFields"];
        			var errorKey = op.request.scope.reader.jsonData["errorKey"];
        			var data = op.request.scope.reader.jsonData["data"];
        			
        			if(errorKey == "ValidationError" && errors){
        				ExtUtils.markInvalidFields(module.formId , errors);
        			}else if(errorKey == "BadObjVersionError" && data){
        				Ext.getCmp(module.formId).getForm().loadRecord(data);
        			}
    			}
        	});
    	}
	},
	
	moduleDoCancel : function(module){
		var win = module.app.desktop.getWindow(module.winId);
		win.doClose();
	},
	
	moduleDoCreate : function(module){
	 	var formModule = module.app.getModule(module.formModuleId);
	 	formModule.createWindow({entityId : null , gridId : module.gridId});
	},
    
    moduleDoEdit : function(module , id){
    	var formModule = module.app.getModule(module.formModuleId);
    	formModule.createWindow({entityId : id , gridId : module.gridId});
    },
    
    moduleDoSearch : function(module){
    	var store = Ext.getCmp(module.gridId).getStore();
    	ExtUtils.gridSearch(store , module.searchFormId);
    },
    
    moduleDoReset : function(module){
    	var store = Ext.getCmp(module.gridId).getStore();
    	ExtUtils.gridReset(store , module.searchFormId);
    },
    
    moduleDoDestroy : function(module , record){
    	if(record){
    		var model = module.Model.create(record.data);
    		model.destroy({
    			success: function(model) {
	      	        Ext.getCmp(module.gridId).getStore().load();
	      	    }
    		});
    	}
    },
	//-------------------------------------------------------
	//           GRID UTILs
	//-------------------------------------------------------	
		
	fieldsFromCols : function (cols){
		var fields = [];
		for(var i = 0 ; i < cols.length ; i++){
			fields.push(cols[i].dataIndex);
		}
		return fields;
	},
	
	ajaxGrid : function(config){
		var columns = config.columns || this.defaultGridCols;
		var fields = this.fieldsFromCols(columns);
		
		var modelName = config.model || config.modelName;
		
		Ext.define(modelName , {
		    extend: 'Ext.data.Model',
		    fields: fields,
		});
		
	    // create the Data Store
	    var store = this.ajaxStore(config);
	    
	    // create the grid
	    var grid = Ext.create('Ext.grid.Panel', {
	        store: store,
	        columns: columns,
	        id : config.gridId
	    });
		
		return grid;
	},
	
	//array grid
	arrayGrid : function(config){
		
		var fields = this.fieldsFromCols(config.columns);
		
		Ext.define('Model', {
		    extend: 'Ext.data.Model',
		    fields: fields
		});
		
		var modelConfig = {
	        model: 'Model',
	        data : []
		};
		
		if(config.data){
			Ext.apply(modelConfig , {data:config.data});
		}
		
		var store = Ext.create('Ext.data.JsonStore', modelConfig);
		
		Ext.apply(config , {
			store : store
		});
		
		var grid = Ext.create('Ext.grid.Panel', config );
		
		return grid;
	},
	
	removeFromGrid : function(grid){
		var records = grid.getSelectionModel().getSelection();
		if(records){
			for(var i = 0 ; i < records.length ; i++){
				grid.getStore().remove(records[i]);
			}
		}
	},
	
	getSelectedRow : function(grid , multi){
		var records = grid.getSelectionModel().getSelection();
		
		if(records && multi){
			return records;
		}
		
		if(records && records[0]){
			return records[0];
		}
		
		return null;
	},
	
	gridSearch : function(store , searchFormId){
		var formId = searchFormId || 'searchForm';
		var proxy = store.getProxy();
		var params = this.formValues(formId);
		if(params){
			proxy.extraParams = {
				queryInfo :	Ext.encode(params)
			};
			store.load({
				callback : function(records, operation, success){
					if(!success){
						Ext.Msg.alert('error',operation.request.scope.reader.jsonData["errorMsg"]);
					}
				}
			});
		}
	},
	
	gridReset : function(store , searchFormId){
		var formId = searchFormId || 'searchForm';
		var form = Ext.getCmp(formId);
		
		if(form){
			form.getForm().reset();
		}
		
		this.storeReload(store);
	},
	//-------------------------------------------------------
	//           STORE UTILs
	//-------------------------------------------------------	
	proxy : function(modelName){
		var proxy = {
			type: CONSTANTS.PROXY_TYPE,
			api: {
				read : NS.url(modelName , CONSTANTS.READ),
				update : NS.url(modelName , CONSTANTS.UPDATE),
				create : NS.url(modelName , CONSTANTS.CREATE),
				destroy : NS.url(modelName , CONSTANTS.DESTROY)
			},
			reader: {
				type: CONSTANTS.READER_TYPE,
				root: CONSTANTS.READER_ROOT,
				successProperty: CONSTANTS.READER_SUCCESS,
				totalProperty: CONSTANTS.READER_TOTAL	
			}
		};
		return proxy;
	},
	
	ajaxStore : function(config){
		return this._createStore('Ext.data.JsonStore' , config);
	},
	
	treeStore : function(config){
		return this._createStore('Ext.data.TreeStore' , config);
	},
	
	_createStore : function(storeClass , config){
		var defaults = {
			autoLoad : false,
			proxy: this.ajaxProxy(config)
		};
		config = Ext.apply({} , config , defaults);
		
		var store = Ext.create(storeClass , config );
		return store;
	},
	
	ajaxProxy : function(config){
		var proxy = {
			type: CONSTANTS.PROXY_TYPE,
	        url : NS.url(config.model || config.modelName , CONSTANTS.READ),
	        reader: {
				type: CONSTANTS.READER_TYPE,
				root: CONSTANTS.READER_ROOT,
				successProperty: CONSTANTS.READER_SUCCESS,
				totalProperty: CONSTANTS.READER_TOTAL
			}
	    };
		return proxy;
	},
	
	storeReload : function(store , params){
		
		if(!store) return;
		
		var proxy = store.getProxy();
		proxy.extraParams = params || {};
		store.load({
			callback : function(records, operation, success){
				if(!success){
					Ext.Msg.alert('error',operation.request.scope.reader.jsonData["errorMsg"]);
				}
			}
		});
	},
	
	buildQueryInfo : function(obj){
		return {queryInfo : Ext.encode(obj)};
	},
	//-------------------------------------------------------
	//           LAYOUT UTILs
	//-------------------------------------------------------	
	columnLayout : function(arrays){
		 var layout = {
			layout:'column',
			xtype:'container',
			items: []
		 };
		 
		 for(var i = 0 ; arrays && i < arrays.length ; i++){
			layout.items.push({
			    xtype:'container',
			    items: arrays[i]
			});
		 }
		 
		 return layout;
	},
	
	accordionLayout : function(array){
		 var layout = {
			layout:'accordion',
			xtype:'container',
			items: []
		 };
		 return Ext.apply(layout , {items : array});
	},
	
	tableLayout : function(columns , arrays){
		var panel = {
			xtype:'container',
			layout: {
			    type: 'table',
			    columns: columns
			},
			items : []
		};
		
		 for(var i = 0 ; arrays && i < arrays.length ; i++){
			 panel.items.push({
			    xtype:'container',
			    items: arrays[i]
			});
		 }
		 
		 return panel;
		
	}
	
};
