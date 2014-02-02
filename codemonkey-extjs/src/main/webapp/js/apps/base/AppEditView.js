Ext.define('AM.base.AppEditView', {
    extend: 'AM.base.AppView',
    
    Model : null,
    gridId : null,
    
    formItems : Ext.emptyFn,
    beforeSave : Ext.emptyFn,
    afterModelLoad : Ext.emptyFn,
    create : Ext.emptyFn,
    
    manageFields : function(form , data){
		
	},
	
	modelFields : function(){ 
    	return ExtUtils.defaultModelFields;
    },
    
    // actions 
    saveAction : {
		action : 'save', text: i18n.save, iconCls : 'icon-update'
	},
	
	cancelAction : {
		action : 'cancel' , text: i18n.cancel , iconCls:'icon-back-to-list'
    },
    
    //action handlers
    save : function(){
		ExtUtils.moduleDoSave(this);
	},
	
	cancel : function(){
		this.reloadListGrid();
		this.win.close();
	},
    
	//constructor
    constructor: function () {
    	
    	var me = this;
    	
    	me.Model = ExtUtils.createModel({
        	modelName : this.modelName , 
        	modelFields : this.modelFields()
        });
    	
    	me.formId = NS.formId(me.modelName) + Ext.id();
    	
    	this.callParent();
    },
    
    afterWindowCreate : function(config){
    	this.loadEntityToForm(config);
    },
    
    onWindowClose : function(){
    	this.reloadListGrid();
    },
    
    loadEntityToForm : function(config){
    	var me = this;
    	if(config.readConfig){
    		 me.reloadEntityToForm(me.modelName , config.readConfig.readAction , config.readConfig.readParams);
    	}else if(me.entityId){
    		me.Model.load(me.entityId , {
        	    success: function(model) {
        	        var form = Ext.getCmp(me.formId).getForm();
        	        form.loadRecord(model);
        	        me.afterModelLoad(model);
        	        me.manageFields(form , model.data);
        	        ExtUtils.clearInvalidFields(me.formId);
        	    }
        	});
    	}else{
    		Ext.getCmp(me.formId).getForm().reset();
    		Ext.each(Ext.ComponentQuery.query('searchingselect') , function(name , index , array){
    			this.onTrigger1Click();
    		});
    		this.create();
    		ExtUtils.clearInvalidFields(me.formId);
    	}
    	
    	
    },
    
    reloadEntityToForm : function(model , action , param , fn){
    	var me = this;
    	ExtUtils.doAction(model , action , param , function(result){
    		var form = Ext.getCmp(me.formId).getForm();
    		form.loadRecord(result);
    		me.afterModelLoad(result);
	        me.manageFields(form , result.data);
	        ExtUtils.clearInvalidFields(me.formId);
    		if(fn){
    			fn(result);
    		}
    	});
    },
    
    createBbar : function(){
    	
    	var actions = [
			this.createModuleAction(this.saveAction),
			this.createModuleAction(this.cancelAction)
		];
    	
    	return this.createToolbar(actions);
    },
	
	doAction : function(action , fn , params){
		ExtUtils.moduleDoAction(this , action , fn , params);
	},
	
	reloadListGrid : function(){
		if(this.gridId){
			var grid = Ext.getCmp(this.gridId);
			if(grid){
				grid.getStore().load();
			}
		}
	},
	
	createWindow : function(config){
		
		var me = this;
		
		Ext.apply(this , config);
		
		var bbar = this.createBbar();
    	
		this.win = Ext.create('Ext.window.Window', {
			id: this.modelName + '_win_' + Ext.id(),
            title: this.winTitle ,
            width : this.winWidth || 600,
            height : this.winHeight || 500,	
            layout : 'fit',
            items : this.createWindowItem() || [],
            bbar : bbar
	    });
		
		this.win.on('close' , function ( panel, eOpts ){
         	me.onWindowClose();
        });
		
		this.win.show();
	    this.afterWindowCreate(config);
	},
	
	createWindowItem : function(){
    	var me = this;
    	
    	me.formId = NS.formId(me.id);
    	
    	return {
			id : me.formId,
			xtype : 'form',
	    	layout: {
	    	    type: 'vbox',
	    	    align : 'stretch',
	    	    pack  : 'start'
	    	},
	    	fieldDefaults: CONSTANTS.FIELD_DEFAULTS,
	    	defaultType: 'textfield',
	    	flex : 2,
	    	items : this.formItems() || ExtUtils.defaultFormItems
    	};
    },
    
    getWindow : function(){
		return this.win;
	}

});
