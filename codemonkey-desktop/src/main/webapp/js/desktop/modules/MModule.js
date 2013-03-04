
Ext.define('AM.modules.MModule', {
    extend: 'Ext.ux.desktop.Module',

    hidden : true,
    
    Model : null,
	store : null,
	grid : null,
	
	formModuleId : null,
	
	iconText : null,
	iconCls : null,
	
	gridId : null,
	searchFormId : null,
	
	formId : null,
	
    init : function(){
        var me = this;
        this.callParent();
        
        me.gridId = me.id + "_grid";
        me.searchFormId = me.id + "_searchForm";
        
        me.Model = Ext.define(me.modelName , {
	        extend: 'Ext.data.Model',
	        fields: me.modelFields || ExtUtils.defaultModelFields,
	        proxy: ExtUtils.proxy(me.modelName)
		});
    	
    	me.store = ExtUtils.ajaxStore({ model : me.modelName});
    },
    
    
    // actions 
    createAction : {
		action : 'create', text: 'add', iconCls : 'add'
	},
	
	editAction : {
		action : 'edit' , text:'Edit', iconCls:'option'
    },
    
    destroyAction : {
		action : 'destroy', text: 'destroy', iconCls : 'remove'
	},
	
	searchAction : {
		action : 'search', text: 'search', iconCls : 'icon-search'
	},
	
	resetAction : {
		action : 'reset', text: 'reset', iconCls : 'icon-reset'
	},
	
	 saveAction : {
		action : 'save', text: 'save', iconCls : 'icon-update'
	},
	
	cancelAction : {
		action : 'cancel' , text:'cancel', iconCls:'icon-back-to-list'
    },
    
	//end actions
    
	//actions handler
    create : function(){
    	this.createDetailWindow({entityId : null});
    },
    
    edit : function(id){
    	var me = this;
    	var record = ExtUtils.getSelected(me.gridId);
    	me.createDetailWindow({entityId : record.get('id')});
    },
    
    search : function(){
    	var store = Ext.getCmp(this.gridId).getStore();
    	ExtUtils.gridSearch(store , this.searchFormId);
    },
    
    reset : function(){
    	var store = Ext.getCmp(this.gridId).getStore();
    	ExtUtils.gridReset(store , this.searchFormId);
    },
    
    destroy : function(record){
    	var me = this;
    	var record = ExtUtils.getSelected(me.gridId);
    	if(record){
    		var model = me.Model.create(record.data);
    		model.destroy({
    			success: function(model) {
	      	        Ext.getCmp(me.gridId).getStore().load();
	      	    }
    		});
    	}
    },
    
    save : function(){
    	var me = this;
		var values = ExtUtils.formValues(this.formId);
		if(values){
			var model = this.Model.create(values);
			ExtUtils.mask(Ext.getCmp(this.winId));
	    	model.save({
	    		success: function(model , res) {
    				me.search();
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
    	var me = this;
    	var detailForm = Ext.getCmp(me.formId);
        if(detailForm){
            detailForm.hide();
        }
    },
    
  //end action handler
    
    getActionBar : function(){
    	var actions = [
   			this.createModuleAction(this.createAction),
   			this.createModuleAction(this.editAction),
   			this.createModuleAction(this.destroyAction)
   		];
       	
       	return this.createToolbar(actions);
    },
    
    getDetailFormActionBar : function(){
    	var actions = [
   			this.createModuleAction(this.saveAction),
   			this.createModuleAction(this.cancelAction)
   		];
       	
       	return this.createToolbar(actions);
    },
    
    createDetailWindow : function (config){
    	var me = this;
        var detailForm = Ext.getCmp(me.formId);
        if(detailForm){
            detailForm.show();
            if(config.entityId){
        		this.Model.load(config.entityId , {
            	    success: function(model) {
            	    	detailForm.getForm().loadRecord(model);
            	    }
            	});
        	}else{
        		detailForm.getForm().reset();
        	}
        }
        
       
    },
    
    createWindowItem : function(){
    	var me = this;
    	 //grid
         var gridConfig = {
     		xtype : 'grid',	
     		flex : 3,	
     		id : this.gridId,
     		columns : me.gridCols || ExtUtils.defaultGridCols,
     		listeners: {
 				'itemdblclick' : function( /*Ext.view.View*/ view, /* Ext.data.Model*/ record, /*HTMLElement*/ item,/* Number*/ index, /*Ext.EventObject*/ e, /*Object*/ eOpts){
 					me.edit();
 			    }
 			 },
 			 
 			 tbar: me.getActionBar(),
 			 
 			 store : me.store
         };
         var grid2 = Ext.apply(gridConfig , this.grid);
         
          //seearch form
         var searchFormDefaultConfig = {
         	id : this.searchFormId,
         	items : [
       				{name  : "name_Ilike"   , xtype : 'textfield' , fieldLabel : "name" }
       			],
          	buttons : [
				this.createModuleAction(this.searchAction),
				this.createModuleAction(this.resetAction)
            ]
          };
         
         var searchFormConfig = Ext.applyIf(searchFormDefaultConfig , this.searchForm);
         
         var searchForm = ExtUtils.searchingForm(searchFormConfig);
         
         //detail form
         me.formId = me.id + '_detailForm';
         var formConfig = {
			id : me.formId,
			hidden : true,
			xtype : 'form',
	    	defaultType: 'textfield',
	    	flex : 2,
	    	bodyPadding: 20,
			items : me.formItems || ExtUtils.defaultFormItems,
			buttons : me.getDetailFormActionBar()
    	};
    	var detailFrom = Ext.apply(formConfig , this.form);
         
        return [searchForm ,  grid2 , detailFrom];
    },
    
    afterWindowCreate : function(){
    	this.store.load();
    }

});
