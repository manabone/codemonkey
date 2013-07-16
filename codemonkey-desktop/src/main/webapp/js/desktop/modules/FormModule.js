
Ext.define('AM.modules.FormModule', {
    extend: 'Ext.ux.desktop.Module',

    hidden : true,
    
    model : null,
	store : null,
	form : null,
	entityId : null,
	winId : null,
	
	gridId : null,
	
	iconText : null,
	iconCls : null,
	
	afterModelLoad : function(model){},
	
	create : Ext.emptyFn,
	
	manageControl : Ext.emptyFn,
	
	modelFields : function(){ 
    	return ExtUtils.defaultModelFields;
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
	    			
	    			var errors = op.request.scope.reader.jsonData["errorFields"];
	    			var errorKey = op.request.scope.reader.jsonData["errorKey"];
	    			var data = op.request.scope.reader.jsonData["data"];
	    			var errorMsg = op.request.scope.reader.jsonData["errorMsg"];
	    			
	    			me.handleError(errorKey , errorMsg , errors , data);
	    			
	    			ExtUtils.unmask(Ext.getCmp(me.winId));
				}
	    	});
		}
	},
	
	handleError : function(errorKey , errorMsg , errors , data){
		
		var me = this;
		
		if(errorKey == "ValidationError" && errors){
			
			var formErrors = errors.filter(function(element, index, array){
				return (element.type == 'FormField');
			});
			
			ExtUtils.markInvalidFields(me.formId , formErrors);
			
			if(errorMsg){
				Ext.Msg.alert("Failed", errorMsg);
			}
			
		}else if(errorKey == "BadObjVersionError" && data){
			Ext.Msg.alert("Failed", errorMsg);
			var model = me.Model.create(data);
    		Ext.getCmp(me.formId).getForm().loadRecord(model);
		}else{
			Ext.Msg.alert("Failed", errorMsg);
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
    },
	
	doAction : function(action , fn , params){
		var me = this;
		var values = ExtUtils.formValues(me.formId);
		if(values){
			this.beforeSave(values);
			ExtUtils.mask(Ext.getCmp(me.winId));
			var url = NS.url(me.modelName , action);
			Ext.Ajax.request({
			    url: url,
			    method: 'post',
			    params: params || Ext.encode(values),
			    success: function(response){
			    	
			    	var result = Ext.decode(response.responseText);
			    	if(result.success === false){
			    		me.handleError(result.errorKey , result.errorMsg , result.errorFields , result.data);
			    	}else {
			    		var model = me.Model.create(result.data);
			    		Ext.getCmp(me.formId).getForm().loadRecord(model);
			    		me.manageControl(result.data);
			    		if(fn){
				    		fn(me , result);
				    	}
			    	}
			    	
			    	ExtUtils.unmask(Ext.getCmp(me.winId));
			    },
			    failure: function(response, opts) {
			    	Ext.Msg.alert("Failed","error occured");
			    	ExtUtils.unmask(Ext.getCmp(me.winId));
			    }
			});
		}
	}
    
});
