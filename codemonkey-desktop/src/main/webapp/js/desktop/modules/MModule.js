
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
    
    doCreate : function(){
    	this.createDetailWindow({entityId : null});
    },
    
    doEdit : function(id){
    	var me = this;
    	me.createDetailWindow({entityId : id});
    },
    
    doSearch : function(){
    	var store = Ext.getCmp(this.gridId).getStore();
    	ExtUtils.gridSearch(store , this.searchFormId);
    },
    
    doReset : function(){
    	var store = Ext.getCmp(this.gridId).getStore();
    	ExtUtils.gridReset(store , this.searchFormId);
    },
    
    doDestroy : function(record){
    	var me = this;
    	if(record){
    		var model = me.Model.create(record.data);
    		model.destroy({
    			success: function(model) {
	      	        Ext.getCmp(me.gridId).getStore().load();
	      	    }
    		});
    	}
    },
    
    doSave : function(){
    	var me = this;
    	ExtUtils.moduleDoSave(me , function(module , model){
    	   module.doSearch();
    	});
    },
    
    doCancel : function(){
    	var me = this;
    	var detailForm = Ext.getCmp(me.formId);
        if(detailForm){
            detailForm.hide();
        }
    },
    
    getActionBar : function(config){
    	return Ext.create('AM.base.ListActionBar',config);
    },
    
    getDetailFormActionBar : function(config){
    	return Ext.create('AM.base.FormActionBar',config);
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
    	 var actionBar = me.getActionBar({module : me , gridId : me.gridId});
         var gridConfig = {
     		xtype : 'grid',	
     		flex : 3,	
     		id : this.gridId,
     		columns : me.gridCols || ExtUtils.defaultGridCols,
     		listeners: {
 				'itemdblclick' : function( /*Ext.view.View*/ view, /* Ext.data.Model*/ record, /*HTMLElement*/ item,/* Number*/ index, /*Ext.EventObject*/ e, /*Object*/ eOpts){
 					me.doEdit(record.get('id'));
 			    }
 			 },
 			 
 			 tbar: actionBar.createActionBar(),
 			 
 			 store : me.store
         };
         var grid2 = Ext.apply(gridConfig , this.grid);
         
          //seearch form
         var searchFormDefaultConfig = {
         	id : this.searchFormId,
         	items : [
       				{name  : "name_Ilike"   , xtype : 'textfield' , fieldLabel : "name" }
       			],
          	buttons : [{
  	        	   xtype : 'button' , iconCls: 'icon-search', text : 'search' ,
  	        	   handler : function(){
  	        		   me.doSearch();
  	        	   }
  	           },{
  	        	   xtype : 'button' , iconCls: 'icon-reset', text : 'reset' , 
  	        	   handler : function(){
  	        		   me.doReset();
  	        	   }
  	           }
            ]
          };
         
         var searchFormConfig = Ext.applyIf(searchFormDefaultConfig , this.searchForm);
         
         var searchForm = ExtUtils.searchingForm(searchFormConfig);
         
         //detail form
         var detailFormActionBar = me.getDetailFormActionBar({module : me , gridId : me.gridId});
         me.formId = me.id + '_detailForm';
         var formConfig = {
			id : me.formId,
			hidden : true,
			xtype : 'form',
	    	defaultType: 'textfield',
	    	flex : 2,
	    	bodyPadding: 20,
			items : me.formItems || ExtUtils.defaultFormItems,
			buttons : detailFormActionBar.createActionBar()
    	};
    	var detailFrom = Ext.apply(formConfig , this.form);
         
        return [searchForm ,  grid2 , detailFrom];
    },
    
    afterWindowCreate : function(){
    	this.store.load();
    }

});
