
Ext.define('AM.modules.ListModule', {
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
    	    
    	me.store = ExtUtils.ajaxStore({model : me.modelName});
		
    	me.grid = {
    		xtype : 'grid',	
    		flex : 3,
	     	columns :  me.gridCols || ExtUtils.defaultGridCols,
	     	store : me.store,
	     	bbar:{
				xtype : 'pagingtoolbar',
				store : me.store,
				displayInfo: true,
				emptyMsg: "No records to display"
			}
	    };
    },
    
    doCreate : function(){
    	var me = this;
    	ExtUtils.moduleDoCreate(me);
    },
    
    doEdit : function(id){
    	var me = this;
    	ExtUtils.moduleDoEdit(me , id);
    },
    
    doSearch : function(){
    	var me = this;
    	ExtUtils.moduleDoSearch(me);
    },
    
    doReset : function(){
    	var me = this;
    	ExtUtils.moduleDoReset(me);
    },
    
    doDestroy : function(record){
    	var me = this;
    	ExtUtils.moduleDoDestroy(me , record);
    },
    
    getActionBar : function(config){
    	return Ext.create('AM.base.ListActionBar',config);
    },
    
    createWindowItem : function(){
    	var me = this;
    	 var actionBar = me.getActionBar({module : me , gridId : me.gridId});
         var gridConfig = {
     		id : this.gridId,
     		listeners: {
 				'itemdblclick' : function( /*Ext.view.View*/ view, /* Ext.data.Model*/ record, /*HTMLElement*/ item,/* Number*/ index, /*Ext.EventObject*/ e, /*Object*/ eOpts){
 					me.doEdit(record.get('id'));
 			    }
 			 },
 			 
 			 tbar: actionBar.createActionBar()
         };
         var grid2 = Ext.apply(gridConfig , this.grid);
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
         
         return [ searchForm ,  grid2];
    },
    
    afterWindowCreate : function(){
    	this.store.load();
    }
   
});
