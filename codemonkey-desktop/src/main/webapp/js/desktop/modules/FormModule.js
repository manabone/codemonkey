
Ext.define('AM.modules.FormModule', {
    extend: 'Ext.ux.desktop.Module',

    hidden : true,
    
    model : null,
	store : null,
	form : null,
	entityId : null,
	winId : null,
	
	model : null,
	gridId : null,
	
	iconText : null,
	iconCls : null,
	
	afterModelLoad : function(model){},
	
	init : function(){
		this.callParent();
        this.Model = Ext.define(this.modelName , {
	        extend: 'Ext.data.Model',
	        fields: this.modelFields || ExtUtils.defaultModelFields,
	        proxy: ExtUtils.proxy(this.modelName)
		});
    	    
    	this.form = {
	    	xtype : 'form',
	    	layout: {
	    	    type: 'vbox',
	    	    align : 'stretch',
	    	    pack  : 'start',
	    	},
	    	defaultType: 'textfield',
	    	flex : 2,
	    	bodyPadding: 20,
	    	items : this.formItems() || ExtUtils.defaultFormItems
    	};
    },
    
    getActionBar : function(config){
    	return Ext.create('AM.base.FormActionBar', config);
    },
    
    doSave : function(){
    	var me = this;
    	ExtUtils.moduleDoSave(me);
    },
    
    doCancel : function(){
    	var me = this;
    	ExtUtils.moduleDoCancel(me);
    	var store = Ext.getCmp(this.gridId).getStore();
    	ExtUtils.storeReload(store);
    },
	
    createWindowItem : function(){
    	var me = this;
    	
    	me.loadEntityToForm();
    	
    	me.formId = me.winId + '_form';
    	var formConfig = {
			id : me.formId,
			buttons : me.getActionBar({module : me}).createActionBar()
    	};
    	var form2 = Ext.apply(formConfig , this.form);
    	
    	return form2;
    },
    
    afterWindowCreate : function(){
    	this.loadEntityToForm();
    },
    
    loadEntityToForm : function(){
    	var me = this;
    	if(me.entityId){
    		me.Model.load(me.entityId , {
        	    success: function(model) {
        	        Ext.getCmp(me.formId).getForm().loadRecord(model);
        	        me.afterModelLoad(model);
        	    }
        	});
    	}
    }
    
});
