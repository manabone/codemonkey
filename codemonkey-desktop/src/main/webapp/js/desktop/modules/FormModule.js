
Ext.define('AM.modules.FormModule', {
    extend: 'Ext.ux.desktop.Module',

    hidden : true,
    
    model : null,
	store : null,
	form : null,
	entityId : null,
	winId : null,
	formId : null,
	
	gridId : null,
	
	iconText : null,
	iconCls : null,
	
	afterModelLoad : function(model){},
	
	create : Ext.emptyFn,
	
	manageControl : Ext.emptyFn,
	
	modelFields : function(){ 
    	return ExtUtils.defaultModelFields;
    },
    
    reloadListGrid : function(){
    	if(this.gridId){
			var grid = Ext.getCmp(this.gridId);
			if(grid){
				grid.getStore().load();
			}
		}
    },
	
	init : function(){
		this.callParent();
        this.Model = Ext.define(this.modelName , {
	        extend: 'Ext.data.Model',
	        fields: this.modelFields() || ExtUtils.defaultModelFields,
	        proxy: ExtUtils.proxy(this.modelName)
		});
        
    	this.form = {
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
	
    createWindowItem : function(){
    	var me = this;
    	
    	me.formId = NS.formId(me.id);
    	var formConfig = {
			id : me.formId
    	};
    	var form2 = Ext.apply(formConfig , this.form);
    	
    	return form2;
    },
    
    afterWindowCreate : function(){
    	this.loadEntityToForm();
    },
    
    onWindowClose : function(){
    	this.reloadListGrid();
    },
    
    loadEntityToForm : function(){
    	var me = this;
    	if(me.entityId){
    		me.Model.load(me.entityId , {
        	    success: function(model) {
        	        Ext.getCmp(me.formId).getForm().loadRecord(model);
        	        me.afterModelLoad(model);
        	        me.manageControl(model.data);
        	    }
        	});
    	}else{
    		Ext.getCmp(me.formId).getForm().reset();
    		Ext.each(Ext.ComponentQuery.query('searchingselect') , function(name , index , array){
    			this.onTrigger1Click();
    		});
    		this.create();
    	}
    },
    
    // actions 
    saveAction : {
		action : 'save', text: i18n.save, iconCls : 'icon-update'
	},
	
	cancelAction : {
		action : 'cancel' , text: i18n.cancel , iconCls:'icon-back-to-list'
    },
    
    //end actions
    
    //actions handler
    
    //handle the submit values before submit
    beforeSave : function(values){
    	
    },
    
	save : function(){
		ExtUtils.moduleDoSave(this);
	},
	
	cancel : function(){
		this.reloadListGrid();
		var win = this.app.desktop.getWindow(this.winId);
		win.doClose();
	},
	

	//end action handler
    
    createBbar : function(){
    	
    	var actions = [
			this.createModuleAction(this.saveAction),
			this.createModuleAction(this.cancelAction)
		];
    	
    	return this.createToolbar(actions);
    },
	
	doAction : function(action , fn , params){
		ExtUtils.moduleDoAction(this , action , fn , params);
	}
    
});
