
Ext.define('AM.modules.FormModule', {
    extend: 'Ext.ux.desktop.Module',

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
	
	manageFields : function(form , data){
		var fieldManager = this.fieldManager();
		
		var form = Ext.getCmp(this.formId);
		var win = Ext.getCmp(this.winId);
		
		if(this.action == 'show'){
			ExtUtils.setFormFieldState(form , 'readOnly' , fieldManager.exceptFields);
			ExtUtils.setFormButtonState(win , 'disable' , fieldManager.exceptButtons);
		}else{
			ExtUtils.setFormFieldState(form , 'editable' , fieldManager.exceptFields);
			ExtUtils.setFormButtonState(win , 'enable' , fieldManager.exceptButtons);
		}
		
	},
	
	modelFields : function(){ 
    	return ExtUtils.defaultModelFields;
    },
    
    fieldManager : function(){
    	return {
    		exceptFields : ['id' , 'createdBy' , 'modifiedBy' , 'originVersion' , 'creationDate' , 'modificationDate'],
    		exceptButtons : ['cancel']
    	};
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
    	this.action = config.action || 'show';
    	this.Model = ExtUtils.createModel({
        	modelName : this.modelName , 
        	modelFields : this.modelFields()
        });
    	this.callParent(arguments);
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
		var win = this.getWindow();
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
