Ext.define('AM.base.AppBatchedView' , {
    
	extend: 'AM.base.AppView',

	//override by a function
	searchForm : null,
	
	readParams : {},
	
	submitParams : {},
	
	constructor : function() {
		var me = this;
				 
		me.formId = me.modelName + '_form_' + Ext.id();
		me.searchingFormId = me.modelName + '_searchingForm_' + Ext.id();
		me.lineGridId = me.modelName + '_grid_' + Ext.id();
		
		me.ModelToSubmit = Ext.define(this.modelName + '_toSubmit' , {
	        extend: 'Ext.data.Model',
	        fields: ['recordsToModify' , 'recordsToDelete'],
	        proxy: ExtUtils.proxy(this.modelName)
		});
		
		me.Model = Ext.define(this.modelName , {
	        extend: 'Ext.data.Model',
	        fields: me.modelFields(),
	        proxy: ExtUtils.proxy(this.modelName)
		});

		this.items = this.createWindowItem();
       
		this.bbar =  this.createBbar();
		
		this.callParent();
		
		ExtUtils.gridSearch(Ext.getCmp(me.lineGridId) , me.searchingFormId);
	},
	
	createWindowItem : function(){
		var me = this;
		var items = [];
		
		var grid = ExtUtils.ajaxGrid({
			gridId: this.lineGridId, 
        	model : this.modelName, 
        	columns : this.gridCols(),
        	remoteSort : false,
        	plugins : [
		        Ext.create('Ext.grid.plugin.CellEditing', {clicksToEdit: 1})
	    	]
		});
		
		if(this.searchForm){
			var searchFormConfig = Ext.apply({} , this.searchForm() , {
			 	id : this.searchingFormId,
			 	collapsed : true,
			 	autoLoad : false,
	    		collapsible : true,
			  	buttons : [
					this.createModuleAction(me.searchAction),
					this.createModuleAction(me.resetAction)
			    ]
			});

			var searchingForm = ExtUtils.searchingForm(searchFormConfig);
		
			items = [searchingForm , grid];
			
		}else{
			items = [grid];
		}
		
		return items;
	},
	
	gridCols  : function() {
		return [
			{header: 'id', dataIndex: 'id' , hidden : true},
			{dataIndex:"code",flex:1,header : '标识' , editor: {xtype :"textfield"}},
			{dataIndex:"name",flex:1,header : '名称' , editor: {xtype :"textfield"}}
		];
	},
	
	modelFields : function(){
		return ExtUtils.defaultModelFields;
	},
	 // actions 
    createAction : {
		action : 'create', text: i18n.add, iconCls : 'add'
	},
	
    saveAction : {
		action : 'save' , text:i18n.save, iconCls:'save'
    },
    
    destroyAction : {
		action : 'destroy', text: i18n.destroy, iconCls : 'remove'
	},
	
	searchAction : {
		action : 'search', text: i18n.search, iconCls : 'icon-search'
	},
	
	resetAction : {
		action : 'reset', text: i18n.reset, iconCls : 'icon-reset'
	},
	
	beforeSave : function(values){
		if(!values.recordsToModify){
			return;
		}
		
		for(var i = 0 ; i < values.recordsToModify.length ; i++){
			Ext.apply(values.recordsToModify[i] , this.submitParams);
		}
		
	},

	//actions handler
	create : function(){
		var grid = Ext.getCmp(this.lineGridId);
		ExtUtils.addLine(grid);
	},
	
	search : function(){
		var me = this;
		var grid = Ext.getCmp(me.lineGridId);
    	ExtUtils.gridSearch(grid , me.searchingFormId , me.readParams);
    },
    
    reset : function(){
    	var me = this;
    	var grid = Ext.getCmp(me.lineGridId);
    	ExtUtils.gridReset(grid , me.searchingFormId , me.readParams);
    },
    
    destroy : function(){
    	var me = this;
    	var grid = Ext.getCmp(me.lineGridId);
    	ExtUtils.removeFromGrid(grid);
    },
    
    save : function(){
    	var me = this;
    	
    	var values = {};
    	
    	var recordsToModify = ExtUtils.getModifedData(Ext.getCmp(me.lineGridId));
    	var recordsToDelete = ExtUtils.getDeletedData(Ext.getCmp(me.lineGridId));
    	
    	Ext.apply(values , {
    		recordsToModify : recordsToModify || [],
    		recordsToDelete : recordsToDelete || []
    	});
    	
    	me.beforeSave(values);
    	
		var model = me.ModelToSubmit.create(values);
		ExtUtils.mask(me);
		
    	model.save({
    		success: function(model , res) {
    			Ext.getCmp(me.lineGridId).getStore().load();
    			if(me.save_callback){
    				me.save_callback(model);
    			}
    			ExtUtils.unmask(me);
    			//add tips
    			ExtUtils.tipMsg(me , '系统提示' , '保存成功！');
    		},
    		
    		failure: function(rec, op) {
    			ExtUtils.unmask(Ext.getCmp(me));
			}
    	});
    },
    
    save_callback : function(){
    	var me = this;
    	me.search();
    },
	//end action handler
    
    getBbarActions : function(){
    	return [this.createAction , this.destroyAction , this.saveAction];
    },
    
    createWindow : function(config){

    	var me = this;
		
		Ext.apply(this , config);
		
		this.win = Ext.create('Ext.window.Window', {
			id: this.modelName + '_win_' + Ext.id(),
            title: this.winTitle ,
            width : this.winWidth || 400,
            height : this.winHeight || 300,	
            layout :'fit',
            items : this
	    });
		
		this.win.on('close' , function ( panel, eOpts ){
         	me.onWindowClose();
        });
		
		this.win.show();
	    this.afterWindowCreate(config);
    },
    
    afterWindowCreate : function(config){
    	var me = this;
    	var store = Ext.getCmp(me.lineGridId).getStore();
    	
    	me.submitParams = config.submitParams;
    	
    	if(config.readConfig){
    		
    		var params = config.readConfig.readParams || {};
    		
    		this.readParams = params;
    		
    		ExtUtils.reloadStore(store , params);
    		
    	}else{
    		store.load();
    	}
    },
    
    onWindowClose : function(){
    	
    }
	
});
