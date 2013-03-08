
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
	
	create : Ext.emptyFn,
	
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
	    	items : this.formItems() || ExtUtils.defaultFormItems
    	};
    },
	
    createWindowItem : function(){
    	var me = this;
    	
    	me.formId = me.winId + '_form';
    	var formConfig = {
			id : me.formId
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
		action : 'save', text: 'save', iconCls : 'icon-update'
	},
	
	cancelAction : {
		action : 'cancel' , text:'cancel', iconCls:'icon-back-to-list'
    },
    
    //end actions
    
    //actions handler
    
    //handle the submit values before submit
    beforeSave : function(values){
    	
    },
    
	save : function(){
		var me = this;
		var values = ExtUtils.formValues(this.formId);
		if(values){
			this.beforeSave(values);
			var model = this.Model.create(values);
			ExtUtils.mask(Ext.getCmp(this.winId));
	    	model.save({
	    		success: function(model , res) {
	    			Ext.getCmp(me.formId).getForm().loadRecord(res.resultSet.records[0]);
	    			if(me.save_callback){
	    				me.save_callback(model);
	    			}
	    			
	    			ExtUtils.unmask(Ext.getCmp(me.winId));
	    		},
	    		
	    		failure: function(rec, op) {
	    			Ext.Msg.alert("Failed",op.request.scope.reader.jsonData["errorMsg"]);
	    			var errors = op.request.scope.reader.jsonData["errorFields"];
	    			var errorKey = op.request.scope.reader.jsonData["errorKey"];
	    			var data = op.request.scope.reader.jsonData["data"];
	    			
	    			if(errorKey == "ValidationError" && errors){
	    				ExtUtils.markInvalidFields(me.formId , errors);
	    			}else if(errorKey == "BadObjVersionError" && data){
	    				Ext.getCmp(me.formId).getForm().loadRecord(data);
	    			}
	    			
	    			ExtUtils.unmask(Ext.getCmp(me.winId));
				}
	    	});
		}
	},
	
	cancel : function(){
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
    }
    
});
