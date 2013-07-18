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
	
	defaultModelFields : ['id','code','name','description','originVersion','creationDate','createdBy','modificationDate','modifiedBy'],
	
	defaultGridCols1 : [	
       	{header: 'id', dataIndex: 'id' , hidden : true},
       	{dataIndex:"code",flex:1,header : i18n.code},
		{dataIndex:"name",flex:1,header : i18n.name}
	],
	
	defaultGridCols2 : [	
		{dataIndex:"creationDate",flex:1,header: i18n.creationDate},
		{dataIndex:"createdBy",flex:1,header:i18n.createdBy},
		{dataIndex:"modificationDate",flex:1,header:i18n.modificationDate},
		{dataIndex:"modifiedBy",flex:1,header:i18n.modifiedBy}
	],
	
	defaultFormItems : [
	    {xtype:"textfield"  , name:"code"        , fieldLabel:i18n.code},
		{xtype:"textfield"  , name:"name"        , fieldLabel:i18n.name},
		{xtype:"textfield"  , name:"description" , fieldLabel:i18n.description},
		{xtype:"numberfield", name:"id"          , fieldLabel:i18n.id , hidden : true}
	],
	
	defaultFormButtons : [{
		action : 'save',
		iconCls: 'icon-update',
		text : i18n.save
	},{
		action : 'backToList',
		iconCls: 'icon-back-to-list',
		text : i18n.backToList
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
			title : i18n.search,
			height : 100 ,
			defaultType: 'textfield',
			bodyPadding: 10,
			collapsible : true,
			collapsed : true,
			collapseFirst : true,
			items : [
				{id : 'query'  , name  : 'query'   , xtype : 'textfield' , fieldLabel : i18n.name }
			],
			buttons : [
				{xtype : 'button' , iconCls: 'icon-search', text : i18n.search , action : i18n.search}, 
				{xtype : 'button' , iconCls: 'icon-reset', text : i18n.reset , action : i18n.reset}     
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
	
	mask : function(cmp , msg){
		cmp = cmp || Ext.getCmp('viewport');
		msg = msg || 'processing';
		
		if(cmp){
			cmp.getEl().mask(msg);
		}
		
	},
	
	unmask : function(cmp){
		cmp = cmp || Ext.getCmp('viewport');
		cmp.getEl().unmask();
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
		for(var i = 0 ; i < errors.length ; i++){
			if(!errors[i].rowId){
				var field = form.findField(errors[i].fieldName);
				if(field){
					field.markInvalid(errors[i].message);
				}
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
		
		var fields = ExtUtils.fieldsFromCols(config.columns);
    	
    	var store = ExtUtils.ajaxStore({
    		modelName : config.modelName,
    		fields : fields,
    		autoLoad : true
    	});
		
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
	        	store: config.store || store,
	        	columns: config.columns ,
	        	selType : config.selType || 'rowmodel',
	        	selModel : config.selModel,
	        	id : 'grid',
				listeners : {
					itemdblclick : {
						fn : function (/* Ext.view.View*/ view ,/* Ext.data.Model*/ record, /*HTMLElement*/ item,/* Number*/ index, /*Ext.EventObject*/ e, /*Object*/ eOpts ){
							config.itemdblclick(view , record , item , e , eOpts);
							this.up('window').close();
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
	
	record2form : function(data , formId){
		
		if(!data) return;
		var form = Ext.getCmp(formId).getForm();
		for(var p in data){
			form.findField(p).setValue(data[p]);
		}
		
	},
	
	formValues : function(formId){
		
		if(!formId) return null;
		
		var formPanel = Ext.getCmp(formId);
		
		if(!formPanel) return null;
		
		var data = {};
		var form = formPanel.getForm();
			
		var fields = form.getFields(); 
		for(var i = 0 ; i < fields.items.length ; i++){
			if(fields.items[i].disabled){
				fields.items[i].disabled = false;
				data[fields.items[i].name] = fields.items[i].getValue();
				fields.items[i].disabled = true;
			}else{
				data[fields.items[i].name] = fields.items[i].getValue();
			}
		}
		
		return data;
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
    
    manageFields : function(formId , disabled){
    	var formPanel = Ext.getCmp(formId);
		var form = formPanel.getForm();
		var fields = form.getFields(); 
		for(var i = 0 ; i < fields.items.length ; i++){
			if(disabled){
				fields.items[i].disable();
			}else{
				fields.items[i].enable();
			}
		}
    },
    
    manageButtons : function(winId , disabled){
    	var win = Ext.getCmp(winId);
    	var dockedItems = win.getDockedItems('toolbar[dock=bottom]');
    	if(dockedItems && dockedItems[0].items){
    		var buttons = dockedItems[0].items.items;
    		for(var i = 0 ; i < buttons.length ; i++){
    			if(buttons[i].action != 'cancel'){
    				if(disabled){
    					buttons[i].disable();
    				}else{
    					buttons[i].enable();
    				}
    			}
    		}
    	}
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
	
	//-------------------------------------------------------
	//           GRID UTILs
	//-------------------------------------------------------	
	searchingColumn : function(cfg){
		var col = {
			header: cfg.header ,  
			dataIndex: cfg.dataIndex,
			flex: 1 ,
			
			renderer : function(value  , metaData , record , rowIndex , colIndex , store , view ){
				return record.get(cfg.textDataIndex); 
			},
			
  			editor: {
  				xtype :"searchingselect" , 
  				config :{
  					model : cfg.listModel , 
  					gridId : cfg.lineGridId ,
  					dataIndex: cfg.dataIndex,
  					hiddenDataIndex: cfg.textDataIndex,
  					cols : cfg.cols,
  					success : function(record , cmp){
  						var targetRecord = ExtUtils.getSelected(cmp.config.gridId);
  						if(targetRecord){
  							targetRecord.set(cfg.dataIndex , record.get('id'));
  							if(cfg.itemdblclick){
  	  							cfg.itemdblclick(record , cmp , targetRecord);
  	  						}else{
  	  							targetRecord.set(cfg.textDataIndex , record.get('name'));
  	  						}
  						}
  					}
  				}
  			}
  		};
		
		return col;
	},
		
	getSelected : function(gridId){
		var grid = Ext.getCmp(gridId);
		var record = grid.getSelectionModel().getSelection()[0];
		return record;
	},
	fieldsFromCols : function (cols){
		var fields = [];
		for(var i = 0 ; i < cols.length ; i++){
			fields.push({name : cols[i].dataIndex , type : cols[i].type || 'string'});
		}
		return fields;
	},
	
	getDeletedData : function(grid){
		var data = [];
		
		var records = grid.getStore().getRemovedRecords();
		
		for(var i = 0 ; i < records.length ; i++){
			data.push(records[i].data);
		}
		
		return data;
	},
	
	getModifedData : function(grid){
		var data = [];
		
		var records = grid.getStore().getModifiedRecords();
		
		for(var i = 0 ; i < records.length ; i++){
			var row = {rowId : records[i].id};
			Ext.apply(row , records[i].data);
			data.push(row);
		}
		
		return data;
	},
	
	getAllData : function(grid){
		var data = [];
		
		var records = grid.getStore().getRange();
		
		for(var i = 0 ; i < records.length ; i++){
			var row = {rowId : records[i].id};
			Ext.apply(row , records[i].data);
			data.push(row);
		}
		
		return data;
	},
	
	ajaxGrid : function(config){
		var columns = config.columns || this.defaultGridCols1.concat(this.defaultGridCols2);
		var fields = this.fieldsFromCols(columns);
		
		var modelName = config.model || config.modelName;
		
		Ext.define(modelName , {
		    extend: 'Ext.data.Model',
		    fields: fields,
		});
		
	    // create the Data Store
	    var store = this.ajaxStore(config);
	    
	    // create the grid
	    var grid = {
    		id : config.gridId,
    		ownerModule : config.ownerModule,
    		xtype : 'grid',	
    		flex : 3,
	     	columns :   [Ext.create('Ext.grid.RowNumberer')].concat(config.columns),
	     	store : store,
	     	features: config.features,
	     	bbar:{
				xtype : 'pagingtoolbar',
				store : store,
				displayInfo: true
			}
	    };
		
		return grid;
	},
	
	//array grid
	arrayGrid : function(config){
		
		var fields = this.fieldsFromCols(config.columns);
		
		Ext.define(config.modelName, {
		    extend: 'Ext.data.Model',
		    fields: fields
		});
		
		config.columns = [Ext.create('Ext.grid.RowNumberer')].concat(config.columns);
		
		var modelConfig = {
	        model: config.modelName,
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
	
	addLine: function(grid, data, index){
		data = data || {};
		if(grid){
			var data2 = {};
			var cols = grid.columns;
			for(var i=0; i<cols.length;i++ ) {
				var fn = cols[i].dataIndex;
				switch(cols[i].fieldType){
					case "text" :
						data2[fn] = '';
						break;
					case "integer" :
//						data2[fn] = 0;
						break;
//					case "number" : 
//						data2[fn] = 0;
//						break;
					case "date" : 
						data2[fn] = '';
						break;
					case "checkbox" : 
						data2[fn] = false;
						break;
					default :
						data2[fn] = '';
						break;
				}
			}
			Ext.apply(data2,data);
			var store = grid.getStore();
			
			if(this.isExistedRecord(grid , data2)){
				return;
			}
			
			var r = Ext.ModelManager.create(data2, store.model.modelName);
			if(grid.plugins && grid.plugins[0]) grid.plugins[0].cancelEdit();
			var count = store.getCount();
			if(typeof(index) == 'number' && index <=count) {
				count = index;
			}
			store.insert(count, r);
            if(grid.plugins && grid.plugins[0]){
            	grid.plugins[0].startEditByPosition({row: count, column: 0});
            }
            grid.getView().getSelectionModel().select(count,false,false);
            var record = grid.getView().getSelectionModel().getSelection()[0];
            return record;
		}
	},
	
	removeLine: function(grid , recordToDel) {
		var selection;
		if(recordToDel) {
			selection = recordToDel;
		} else {
			selection = this.getSelected(grid.id);
		}
	   if(selection) {
	   		if(grid.plugins && grid.plugins[0]) {
				grid.plugins[0].cancelEdit();
			}
			var selectionIndex = grid.getStore().indexOf(selection);
        	grid.getStore().remove(selection);
        	if(grid.getStore().getCount() > 0) {
        		if(selectionIndex <= 0) {
        			selectionIndex = 1;
        		}
        		grid.getView().getSelectionModel().select(selectionIndex-1, false, false);
        	}
        }
	},
	
	isExistedRecord : function(grid , r){
		var store = grid.getStore();
		for(var i = 0 ; i < store.getCount() ; i++){
			var record = store.getAt(i);
			if(record.get('id') == r.id){
				return true;
			}
		}
		return false;
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
		config = Ext.apply({remoteSort : true} , config);
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
		var action = config.action || CONSTANTS.READ;
		var proxy = {
			type: CONSTANTS.PROXY_TYPE,
	        url : NS.url(config.model || config.modelName , action),
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
//		store.load({
//			callback : function(records, operation, success){
//				if(!success){
//					Ext.Msg.alert('error',operation.request.scope.reader.jsonData["errorMsg"]);
//				}
//			}
//		});
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
			 Ext.apply(arrays[i],{
				 border : 0,
				 padding : 5,
		    	 bodyPadding : 5
		     });
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
	
	fitLayout : function(array){
		 var layout = {
			layout:'fit',
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
		
	},
	
	creationInfoPanel : function(){
		var col1 = this.panel({
			items:[
				{xtype:"textfield",name:"id","fieldLabel": i18n.id , readOnly : true},
				{xtype:"textfield",name:"createdBy","fieldLabel":  i18n.createdBy , readOnly : true},
				{xtype:"textfield",name:"modifiedBy","fieldLabel": i18n.modifiedBy , readOnly : true}
			]
		});
    	
    	var col2 =  this.panel({
    		items:[
				{xtype:"textfield",name:"originVersion","fieldLabel": i18n.originVersion , readOnly : true},
				{xtype:"textfield",name:"creationDate","format":"Y-m-d","fieldLabel": i18n.creationDate , readOnly : true},
				{xtype:"textfield",name:"modificationDate","format":"Y-m-d","fieldLabel":i18n.modificationDate , readOnly : true}
			]
    	});
    	
    	var p2 = this.panel({
    		collapsed : true,
    		collapsible : true,
    		title : i18n.creationInfo,
    		items : ExtUtils.columnLayout([col1 , col2])
    	});
    	
    	return p2;
	},
	
	panel : function (config){
		var p = Ext.apply({} , config , {
			xtype : 'panel',
			padding : 5,
    		bodyPadding : 5
    	});
		return p;
	} 
	
};

