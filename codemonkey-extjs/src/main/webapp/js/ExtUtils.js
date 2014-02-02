

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
		{dataIndex:"createdBy",flex:1,header:i18n.createdBy},
		{dataIndex:"creationDate",flex:1,header: i18n.creationDate},
		{dataIndex:"modifiedBy",flex:1,header:i18n.modifiedBy},
		{dataIndex:"modificationDate",flex:1,header:i18n.modificationDate}
	],
	
	defaultGridCols3 : [	
	   	{header: 'id', dataIndex: 'id' , hidden : true},
		{dataIndex:"name",flex:1,header : i18n.name}
	],
	
	defaultGridCols4 : [	
	    {header: 'id', dataIndex: 'id' , hidden : true},
	    {dataIndex:"code",flex:1,header : i18n.code}
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
	
	initApp : function(fn){
		var myMask = new Ext.LoadMask(Ext.getBody(), {
			msg : "正在加载，请稍后....."
		});  
	  	myMask.show();
	  
		Ext.state.Manager.setProvider(Ext.create('Ext.state.CookieProvider'));
	
		var items = fn();
		
		Ext.create('Ext.container.Viewport' , {
			id : 'viewport',
			layout: 'fit',
			items : items
		});
		
		myMask.hide();
	},
	
	formWin : function(config){
		
		var defaultConfig = {
	        height: 400,
	        width: 600,
	        layout : 'fit',
			
			items : ExtUtils.searchingForm({
				title : "Excel",
		        layout : {
					type : 'vbox',
					align : 'stretch',
					pack  : 'start'
				},
				url: config.formAction || '', 
				items : config.formItems,
				buttons : config.formButtons || [{
					text: i18n.ok,
					handler : function(){
						var form = this.up('form').getForm();
						if (form.isValid()) {
							form.submit({standardSubmit : true});
						}
				    }
				}, {
				    text: i18n.cancel,
				    handler : function(){
				    	this.up('window').close();
				    }
				}]
			})
			
		};
		
		var opt = Ext.apply({} , config , defaultConfig);
		
		var win = Ext.create('Ext.window.Window', opt);
	    win.show();
	    return win;
	},
	
	//-------------------------------------------------------
	//            UTILs
	//-------------------------------------------------------
	countDown : function(starTime , endTime ) {
		var result = (endTime - starTime)/(1000);
		var needHours;			
		if(result <= 0){	
			needHours = "已结束";
		}else{
			var m = Math.floor(result/60);
			var s = Math.floor(result%60);
			var h = Math.floor(m/60);
			m = Math.floor(m%60);
			needHours = h+"时"+m+"分"+s+"秒";
		}
		
		return needHours;
	},
	
	searchingForm : function(config){
		var defaultConfig = {
			xtype : 'form',
			border : 0,
//			id : 'searchForm',
			title : i18n.search,
			height : 100 ,
			defaultType: 'textfield',
			bodyPadding: 10,
			collapsible : true,
			collapsed : false,
			collapseFirst : true,
			fieldDefaults: CONSTANTS.FIELD_DEFAULTS,
			items : [
				{ name  : 'name_Like'   , xtype : 'textfield' , fieldLabel : i18n.name }
			]
//			,
//			buttons : [
//				{xtype : 'button' , iconCls: 'icon-search', text : i18n.search , action : i18n.search}, 
//				{xtype : 'button' , iconCls: 'icon-reset', text : i18n.reset , action : i18n.reset}     
//            ]
			
		};
		return Ext.apply({} , config , defaultConfig);
	},
	
	themeSelect : function(){
        return{
        	editable: false,
            fieldLabel: i18n.theme,
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
                ['classic', i18n.themeClassic],
                ['gray', i18n.themeGray]
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
	
	clearInvalidFields : function(formId){
		var formPanel = Ext.getCmp(formId);
		var form = formPanel.getForm();
		var fields = form.getFields();
		for(var i = 0 ; i < fields.length ; i++){
			fields.getAt(i).clearInvalid();
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
		
		var me = this;
		me.gridId = config.modelName + '_popupGrid_' + Ext.id();
		me.searchingFormId = config.modelName + '_popupSearchForm_' + Ext.id();
		
		var fields = ExtUtils.fieldsFromCols(config.columns);
    	
    	var store = ExtUtils.ajaxStore({
    		modelName : config.modelName,
    		fields : fields
    	});
    	
    	me.reloadStore(store , config.searchParams);
    	
    	config.searchingForm = Ext.apply({} , config.searchingForm , {
    		id : me.searchingFormId,
    		collapsed : false,
    		buttons : [
   				{xtype : 'button' , iconCls: 'icon-search', text : i18n.search , 
   					handler : function(){
   						var grid = Ext.getCmp(me.gridId);
   						ExtUtils.gridSearch(grid , me.searchingFormId , config.searchParams);
   					}
   				}, 
   				{xtype : 'button' , iconCls: 'icon-reset', text : i18n.reset , 
   					handler : function(){
   						var grid = Ext.getCmp(me.gridId);
   						ExtUtils.gridReset(grid , me.searchingFormId);
   					}
   				}
            ]
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
	        items: [
	            ExtUtils.searchingForm(config.searchingForm),

	            {
		        	xtype:'grid',
		        	flex : 6 ,
		        	store: config.store || store,
		        	columns: config.columns ,
		        	selType : config.selType || 'rowmodel',
		        	selModel : config.selModel,
		        	id : me.gridId,
					listeners : {
						itemdblclick : {
							fn : function (/* Ext.view.View*/ view ,/* Ext.data.Model*/ record, /*HTMLElement*/ item,/* Number*/ index, /*Ext.EventObject*/ e, /*Object*/ eOpts ){
								config.itemdblclick(view , record , item , e , eOpts);
								this.up('window').close();
							}
						}
					},
					dockedItems : [{
				        xtype : 'toolbar',
				        dock  : 'bottom',
				        items: [config.bbar || {
								xtype : 'pagingtoolbar',
								store : store,
								displayInfo: true
							}]
					}]
		        }
	        ],
	        buttons : [{
			    id : 'ok',
				text: i18n.ok,
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
			    text: i18n.cancel,
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
			var field = form.findField(p);
			if(field){
				field.setValue(data[p]);
			}
		}
		
	},
	
	validateForm : function(formId){
		var formPanel = Ext.getCmp(formId);
		if(formPanel){
			return formPanel.getForm().isValid();
		}
	},
	
	fieldValue : function(formId , fieldName){
		var data = this.formValues(formId);
		return data[fieldName];
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
	
	/**
	 * 设置form field属性，支持disable 和readOnly ， editable
	 * */
	setFormFieldState : function(formPanel , state , exceptFields){
		var form = formPanel.getForm();
		var fields = form.getFields();
		for(var i = 0 ; i < fields.items.length ; i++){
			if(this.indexOf(exceptFields , fields.items[i].name) < 0 ){
				if(state == 'readOnly'){
					fields.items[i].setReadOnly(true);
				}else if(state == 'editable'){
					fields.items[i].setReadOnly(false);
				}
			}
		}
	},
	
	indexOf : function (array , obj){
		
		var array1 = array || [];
		
		for(var i = 0 ; i < array1.length ; i++){
			if(array1[i] == obj){
				return i;
			}
		}
		
		return -1;
	},
	
	setFormButtonState : function(win , state , exceptButtons){
		var buttons = win.query('button');
		for(var i = 0 ; i < buttons.length ; i++){
			if(this.indexOf(exceptButtons , buttons[i].action) < 0 ){
				if(state == 'enable'){
					buttons[i].enable();
				}else if(state == 'disable'){
					buttons[i].disable();
				}
			}
		}
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
	
	createModel : function(config){
		return Ext.define(config.modelName , {
	        extend: 'Ext.data.Model',
	        fields: config.modelFields || ExtUtils.defaultModelFields,
	        proxy: ExtUtils.proxy(config.modelName)
		});
	}, 
	//-------------------------------------------------------
	//           GRID UTILs
	//-------------------------------------------------------	
	
	gridSumaryType : function(dataIndex){
		return function(records){
            var total = 0 ; 
    		for (var i = 0 ; i < records.length ; i++) {
                 var record = records[i];
                 total += parseFloat(record.get(dataIndex));
             }
             return total;
        };
	},
	
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
  				displayField : cfg.textDataIndex,
  				config :{
  					model : cfg.listModel , 
  					gridId : cfg.lineGridId ,
  					dataIndex: cfg.dataIndex,
  					hiddenDataIndex: cfg.textDataIndex,
  					cols : cfg.cols,
  					success : function(record , cmp){
  						var targetRecord = ExtUtils.getSelected(cmp.config.gridId);
  						if(targetRecord){
  							if(cfg.itemdblclick){
  	  							cfg.itemdblclick(record , cmp , targetRecord);
  	  						}else{
  	  							targetRecord.set(cfg.dataIndex , record.get('id'));
  	  							targetRecord.set(cfg.textDataIndex , record.get('name'));
  	  						}
  						}
  					}
  				}
  			}
  		};
		
		return col;
	},
	
	unionCodeColumn : function(cfg){
		var col = {
			header: cfg.header ,  
			dataIndex: cfg.dataIndex,
			flex: 1 ,
			
			renderer : function(value  , metaData , record , rowIndex , colIndex , store , view ){
				return record.get(cfg.textDataIndex); 
			},
			
  			editor: {
  				xtype :"bsunioncodeinfofield" , 
  				unioncodeField : cfg.unioncodeField,
				
				listeners : {
					select : {
						fn : function (combo, records, eOpts){
							var targetRecord = ExtUtils.getSelected(cfg.gridId);
							if(records && records[0]){
								targetRecord.set(cfg.dataIndex , records[0].get('id'));
	  							targetRecord.set(cfg.textDataIndex , records[0].get('code'));
							}
							Ext.getCmp(cfg.gridId).getView().refresh();
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
		    fields: fields
		});
		
	    // create the Data Store
	    var store = this.ajaxStore(config);
	    
	    // create the grid
	    var defaultGridConfig = {
    		id : config.gridId,
    		ownerModule : config.ownerModule,
    		xtype : 'grid',	
    		flex : 3,
	     	columns :   [Ext.create('Ext.grid.RowNumberer')].concat(config.columns),
	     	store : store,
	     	features: config.features,
			dockedItems : [{
		         xtype : 'toolbar',
		         dock  : 'bottom',
		         items: [config.bbar || {
						xtype : 'pagingtoolbar',
						store : store,
						displayInfo: true
					},{ 
					    type: 'minus',
					    iconCls : 'ss_accept ss_excel',
					    handler: function(event, toolEl, panel){
					    	var grid = this.up('grid');
					    	
					    	if(grid && grid.columns){
					    		var columns = [];
					    		for(var i = 0 ; i < grid.columns.length ; i++){
					    			var col = grid.columns[i];
					    			columns.push({dataIndex : col.dataIndex , header : encodeURI(col.text) , hidden :  col.hidden , xtype : col.xtype});
					    		}
					    		
					    		var params = grid.store.getProxy().extraParams || {};
					    		var store = grid.store;
					    		var limit = store.pageSize;
					    		var start = store.currentPage == 1 ? 0 : (store.currentPage - 1) * limit + 1;
					    		
					    		ExtUtils.formWin({
					    			height : 200,
					    			formItems :[
					    			    { name  : 'scope', model : 'ExcelScopeKind', xtype : 'selectfield' , fieldLabel : "范围"},
			    			            { name  : 'fileName'   , xtype : 'textfield' , fieldLabel : "文件名"},
			    			            { name  : 'start', hidden:true, xtype : 'textfield' , fieldLabel : "start" , value : start},
			    			            { name  : 'limit', hidden:true, xtype : 'textfield' , fieldLabel : "limit" , value : limit},
			    			            {xtype:"textfield",  hidden:true, name:"cols", fieldLabel:"列" , value : Ext.encode(columns)},
			    			            {xtype:"textfield", hidden:true, name:"sort", fieldLabel:"顺序", value : "[]"},
			    			            {xtype:"textfield", hidden:true, name:"queryInfo", fieldLabel:"条件", value : Ext.encode(params)}
		    			            ],
		    			            formAction : NS.url(modelName , 'export'),
		    			            modelName : modelName
					    		});
					    	}
					    }
					}
		         ]
			}]
	    };
	    
	    var gridConfig = Ext.apply({} , config , defaultGridConfig);
		
		return gridConfig;
	},
	
	exportExcelList : function(gridId , modelName) {
//		var parameters = {};
//		if(!Strings.isEmpty(path)){
//			var url = path + '?columnConfig=' + 
//			CUtils.myEncodeURI(CUtils.jsonEncode(this.getColumnConfigArray(grid))) + "&" + Ext.urlEncode(parameters);
//			document.forms["noDataForm"].action = url;
//			document.forms["noDataForm"].submit();
//		}
	},
	
	//array grid
	arrayGrid : function(config){
		
		var fields = this.fieldsFromCols(config.columns);
		
		Ext.define(config.modelName, {
		    extend: 'Ext.data.Model',
		    fields: fields
		});
		
		config.columns = [Ext.create('Ext.grid.RowNumberer')].concat(config.columns);
		
		//FIXME 独立成一个方法构建jsonStore
		var defaultConfig = {
	        model: config.modelName,
	        data : [],
	        sortOnLoad : true,
			sorters : [{
				property: 'id', 
				direction : 'ASC'
			}]
		};
		
		var storeConfig = Ext.apply({} , config , defaultConfig);
		
		var store = Ext.create('Ext.data.JsonStore', storeConfig);
       
		// create the grid
	    var defaultGridConfig = {
    		id : config.gridId,
    		ownerModule : config.ownerModule,
    		xtype : 'grid',	
    		flex : 3,
	     	columns :   [Ext.create('Ext.grid.RowNumberer')].concat(config.columns),
	     	store : store,
	     	features: config.features,
	    	plugins : [
	           Ext.create('Ext.grid.plugin.CellEditing', {clicksToEdit: 1})
	    	]
	    };
	    
	    var gridConfig = Ext.apply({} , config , defaultGridConfig);
		
		return gridConfig;
		
	},
	
	jsonStore : function(config){
		Ext.define(config.model, {
		    extend: 'Ext.data.Model',
		    fields: config.fields
		});
		
		var modelConfig = {
	        model: config.model,
	        data : []
		};
		
		if(config.data){
			Ext.apply(modelConfig , {data:config.data});
		}
		
		return Ext.create('Ext.data.JsonStore', modelConfig);
	},
	
	removeFromGrid : function(grid , selectFirst){
		var records = grid.getSelectionModel().getSelection();
		var store = grid.getStore();
		
		if(records){
			for(var i = 0 ; i < records.length ; i++){
				store.remove(records[i]);
			}
		}
		
		var r = null;
		var count = store.getCount();
		if(selectFirst){
			
			if(count > 0){
				r = store.getAt(0);
			}
		}else{
			if(count > 0){
				r = store.getAt(count - 1);
			}
		}
		
		if(r){
			grid.getSelectionModel().select(r);
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
	
	reloadStore : function(store , params , fn){
		var proxy = store.getProxy();
		if(params){
			proxy.extraParams = {
				queryInfo :	Ext.encode(params)
			};
			store.load({
				callback : function(records, operation, success){
					
					if(success && fn){
						fn(records, operation);
					}
					
					if(!success && operation && operation.request && operation.request.scope 
							&& operation.request.scope.reader && operation.request.scope.reader.jsonData){
						Ext.Msg.alert('error',operation.request.scope.reader.jsonData["errorMsg"]);
					}
				}
			});
		}
	},
	
	gridSearch : function(grid , searchFormId , defaultParams){
		var formId = searchFormId || 'searchForm';
		if(grid && grid.getStore()){
			var store = grid.getStore();
			
			var params = this.formValues(formId);
			
			params = Ext.apply({} , params , defaultParams);
			
			if(params){
				this.reloadStore(store , params);
			}
		}
	},
	
	gridReset : function(grid , searchFormId){
		var formId = searchFormId || 'searchForm';
		var form = Ext.getCmp(formId);
		
		if(form){
			form.getForm().reset();
		}
		
		if(grid && grid.getStore()){
			this.reloadStore(grid.getStore());
		}
		
	},
	
	addLine: function(grid, data, index){
		data = data || {};
		if(grid){
			var data2 = {};
			var cols = grid.columns;
			for(var i=0; i<cols.length;i++ ) {
				var fn = cols[i].dataIndex;
				if(fn){
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
			if(record.get('id') && record.get('id') == r.id){
				return true;
			}
		}
		return false;
	},
	//-------------------------------------------------------
	//           STORE UTILs
	//-------------------------------------------------------	
	proxy : function(modelName , action){
		var proxy = {
			type: CONSTANTS.PROXY_TYPE,
			reader: {
				type: CONSTANTS.READER_TYPE,
				root: CONSTANTS.READER_ROOT,
				successProperty: CONSTANTS.READER_SUCCESS,
				totalProperty: CONSTANTS.READER_TOTAL	
			}
		};
		
		if(action){
			Ext.apply(proxy , {
				api: {
					read : NS.url(modelName , action)
				}
			});
		}else{
			Ext.apply(proxy , {
				api: {
					read : NS.url(modelName , CONSTANTS.READ),
					update : NS.url(modelName , CONSTANTS.UPDATE),
					create : NS.url(modelName , CONSTANTS.CREATE),
					destroy : NS.url(modelName , CONSTANTS.DESTROY)
				}
			});
		}
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
	
//	storeReload : function(store , params){
//		
//		if(!store) return;
//		
//		var proxy = store.getProxy();
//		proxy.extraParams = params || {};
//		store.load({
//			callback : function(records, operation, success){
//				if(!success){
//					Ext.Msg.alert('error','error');
//				}
//			}
//		});
//	},
	
	buildQueryInfo : function(obj){
		return {queryInfo : Ext.encode(obj)};
	},
	//-------------------------------------------------------
	//           LAYOUT UTILs
	//-------------------------------------------------------	
	columnLayout : function(arrays){
		 var layout = {
			layout:'hbox',
			xtype:'container',
			items: []
		 };
		 
		 for(var i = 0 ; arrays && i < arrays.length ; i++){
			 layout.items.push(this.panel({border : 0 , items : arrays[i] }));
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
		var col1 = [
			{xtype:"textfield",name:"id","fieldLabel": i18n.id , readOnly : true},
			{xtype:"textfield",name:"createdBy","fieldLabel":  i18n.createdBy , readOnly : true},
			{xtype:"textfield",name:"modifiedBy","fieldLabel": i18n.modifiedBy , readOnly : true}
		];
    	
    	var col2 =  [
			{xtype:"textfield",name:"originVersion","fieldLabel": i18n.originVersion , readOnly : true},
			{xtype:"textfield",name:"creationDate","format":"Y-m-d","fieldLabel": i18n.creationDate , readOnly : true},
			{xtype:"textfield",name:"modificationDate","format":"Y-m-d","fieldLabel":i18n.modificationDate , readOnly : true}
		];
    	
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
    		bodyPadding : 5,
    		fieldDefaults: CONSTANTS.FIELD_DEFAULTS
    	});
		return p;
	},
	
	container : function(config){
		var p = Ext.apply({} , config , {
			xtype : 'container',
			padding : 0,
    		bodyPadding : 0,
    		fieldDefaults: CONSTANTS.FIELD_DEFAULTS
    	});
		return p;
	},
	
	//-------------------------------------------------------
	//           TREE UTILs
	//-------------------------------------------------------	
	
	checkedTreeJson : function(treePanelId){
		var values = [];
		var treeNodes = Ext.getCmp('appRoleForm_powerTreePanel').getView().getChecked();
		
		if(!treeNodes){
			return values;
		}
		
		for(var i = 0 ; i < treeNodes.length ; i++){
			values.push({id : treeNodes[i].data.id , parentId : treeNodes[i].data.parentId , root : treeNodes[i].data.root , text : treeNodes[i].data.text});
		}
		
		return values;
	},
	
	//-------------------------------------------------------
	//           MODULE UTILs
	//-------------------------------------------------------	
	
	createModuleAction : function(module , cfg){
    	Ext.apply(cfg , {
			handler: function(){
				if(cfg.action){
					module[cfg.action]();
				}
	        }
		});
		
		return  Ext.create('Ext.Action', cfg);
	},
	
	createMenu : function(actions){
		return {
            xtype: 'menu',
            plain: true,
            items : actions
		};
	},
	
	createToolbar : function(actions){
		return {
            xtype: 'toolbar',
            items: actions
            
        };
	},
	
	createButtons : function(actions){
		var buttons = [];
		
		for(var i = 0 ; i < actions.length ; i++){
			buttons.push(Ext.create('Ext.button.Button', actions[i]));
		}
		return buttons;
	},
	
	moduleDoSave : function(module){
		if(!ExtUtils.validateForm(module.formId)){
			return;
		}
		
		var values = ExtUtils.formValues(module.formId);
		if(values){
			
			module.beforeSave(values);
			
			var model = module.Model.create(values);
			ExtUtils.mask(Ext.getCmp(module.winId));
			
	    	model.save({
	    		success: function(model , res) {
	    			Ext.getCmp(module.formId).getForm().loadRecord(res.resultSet.records[0]);
	    			module.afterModelLoad(model);
	    			if(module.save_callback){
	    				module.save_callback(model);
	    			}
	    			ExtUtils.clearInvalidFields(module.formId);
	    			ExtUtils.unmask(Ext.getCmp(module.winId));
	    			//add tips
	    			ExtUtils.tipMsg(module.getWindow() , '系统提示' , '保存成功！');
	    		},
	    		
	    		failure: function(rec, op) {
	    			
	    			var errors = op.request.scope.reader.jsonData["errorFields"];
	    			var errorKey = op.request.scope.reader.jsonData["errorKey"];
	    			var data = op.request.scope.reader.jsonData["data"];
	    			var errorMsg = op.request.scope.reader.jsonData["errorMsg"];
	    			ExtUtils.clearInvalidFields(module.formId);
	    			ExtUtils.handleError(errorKey , errorMsg , errors , data , module);
	    			ExtUtils.record2form(data , module.formId);
	    			ExtUtils.unmask(Ext.getCmp(module.winId));
				}
	    	});
		}
	},
	
	handleError : function(errorKey , errorMsg , errors , data , module){
		Ext.Msg.alert("Failed" , errorMsg);
		
		if(errorKey == "ValidationError" && errors){
			ExtUtils.markInvalidFields(module.formId , errors);
		}else if(errorKey == "BadObjVersionError" && data){
			Ext.getCmp(module.formId).getForm().loadRecord(data);
		}
	},
	
	moduleDoAction : function(module , action , fn , params){
		var values = ExtUtils.formValues(module.formId);
		if(values){
			module.beforeSave(values);
			ExtUtils.mask(Ext.getCmp(module.winId));
			var url = NS.url(module.modelName , action);
			Ext.Ajax.request({
			    url: url,
			    method: 'post',
			    params: params || Ext.encode(values),
			    success: function(response){
			    	
			    	var result = Ext.decode(response.responseText);
			    	if(result.success === false){
			    		ExtUtils.handleError(result.errorKey , result.errorMsg , result.errorFields , result.data , module);
			    	}else {
			    		var model = module.Model.create(result.data);
			    		var form = Ext.getCmp(module.formId).getForm();
			    		form.loadRecord(model);
			    		module.manageFields(form , result.data);
			    		if(fn){
				    		fn(module , result);
				    	}
			    	}
			    	
			    	ExtUtils.unmask(Ext.getCmp(module.winId));
			    },
			    failure: function(response, opts) {
			    	Ext.Msg.alert("Failed","error occured");
			    	ExtUtils.unmask(Ext.getCmp(module.winId));
			    }
			});
		}
	},
	
	doAction : function(modelName , action , params , fn){
		var url = NS.url(modelName , action);
		Ext.Ajax.request({
		    url: url,
		    method: 'post',
		    params: params,
		    success: function(response){
		    	var result = Ext.decode(response.responseText);
	    		if(fn){
		    		fn(result);
		    	}
		    },
		    failure: function(response, opts) {
		    	Ext.Msg.alert("Failed","error occured");
		    }
		});
	},
	
	urlDoAction : function(modelName , action , params){
		var url = NS.url(modelName , action);
		window.open(url+"?"+params);
	},
	
	createBox : function(t, s){
        return '<div class="msg"><h3>' + t + '</h3><p>' + s + '</p></div>';
    },
    
    tipMsg : function(win , title, content){
         if(win){
            var msgCt = Ext.DomHelper.insertFirst(win.el , {id:'msg-div'}, true);
//            var s = Ext.String.format.apply(String, Array.prototype.slice.call(arguments, 1));
            var m = Ext.DomHelper.append(msgCt, ExtUtils.createBox(title || '系统提示' , content), true);
            m.hide();
            m.slideIn('t').ghost("t", { delay: 1000, remove: true});
         }
    },
    
    /*
	 * powerTrees : [{
	 * 					id : id,
	 * 					name : name,
	 * 					parent : parent
	 * }]
	 * 
	 * return [{
	 * 			id : id,
	 * 			name : name,
	 * 			menu : menu
	 * }]
	 * */
    treeMenu : function(powerTrees){
		var menu = [];
		for(var i = 0; i < powerTrees.length; i++){
			if(!powerTrees[i].parent){
				menu.push({
					id:powerTrees[i].id,
			        text:powerTrees[i].name, 
			        menu: ExtUtils.addScrollMenu(powerTrees, powerTrees[i].id),
			        handler: function(){}
				 });
			}
		}
		return menu;
    },
    
    addScrollMenu : function(powerTrees, menuid){
    	var menuItem = [];
        for (var i = 0; i < powerTrees.length; i++) {
            var entity = powerTrees[i];
            if (entity.parent == menuid) {
            	menuItem.push({
                    id: entity.id,
                    text: entity.name
                });
            }
        }
        return menuItem;
    },
    
    copyArrayProp : function(array , fromProp , toProp){
    	if(array){
    		for(var i = 0 ; i < array.length ; i++){
    			array[i][toProp] = array[i][fromProp];
    		}
    	}
    	return array;
    },
    /**
     * 判断是否包含中文字符
     * @param str
     * @returns true/false
     */
    isChinese : function(str){
    	var patrn=/[\u4E00-\u9FA5]|[\uFE30-\uFFA0]/gi; 
    	if(!patrn.exec(str)){ 
    		return false; 
    	}
    	return true;
    }
	
};

