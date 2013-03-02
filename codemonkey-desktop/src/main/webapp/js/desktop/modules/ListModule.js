
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
    
//    doCreate : function(){
//    	var me = this;
//    	ExtUtils.moduleDoCreate(me);
//    },
//    
//    doEdit : function(id){
//    	var me = this;
//    	ExtUtils.moduleDoEdit(me , id);
//    },
//    
//    doSearch : function(){
//    	var me = this;
//    	ExtUtils.moduleDoSearch(me);
//    },
//    
//    doReset : function(){
//    	var me = this;
//    	ExtUtils.moduleDoReset(me);
//    },
//    
//    doDestroy : function(record){
//    	var me = this;
//    	ExtUtils.moduleDoDestroy(me , record);
//    },
//    
//    getActionBar : function(config){
//    	return Ext.create('AM.base.ListActionBar',config);
//    },
    
    createWindowItem : function(){
    	var me = this;
//    	 var actionBar = me.getActionBar({module : me , gridId : me.gridId});
         var gridConfig = {
     		id : this.gridId,
     		listeners: {
 				'itemdblclick' : function( /*Ext.view.View*/ view, /* Ext.data.Model*/ record, /*HTMLElement*/ item,/* Number*/ index, /*Ext.EventObject*/ e, /*Object*/ eOpts){
 					me.edit();
 			    }
 			 }
//            ,tbar: actionBar.createActionBar()
         };
         var grid2 = Ext.apply(gridConfig , this.grid);
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
         
         return [ searchForm ,  grid2];
    },
    
    afterWindowCreate : function(){
    	this.store.load();
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
    
	//end actions
	
	//actions handler
	create : function(){
		var formModule = this.app.getModule(this.formModuleId);
	 	formModule.createWindow({entityId : null , gridId : this.gridId});	
	},
	
	edit : function(){
		var formModule = this.app.getModule(this.formModuleId);
		
		var record = ExtUtils.getSelected(this.gridId);
		
		if(record && record.get('id')){
			formModule.createWindow({entityId : record.get('id') , gridId : this.gridId});
		}
	},
	
	search : function(){
    	var store = Ext.getCmp(this.gridId).getStore();
    	ExtUtils.gridSearch(store , this.searchFormId);
    },
    
    reset : function(){
    	var store = Ext.getCmp(this.gridId).getStore();
    	ExtUtils.gridReset(store , this.searchFormId);
    },
    
    destroy : function(){
    	var me = this;
    	var record = ExtUtils.getSelected(this.gridId);
    	if(record){
    		var model = this.Model.create(record.data);
    		model.destroy({
    			success: function(model) {
	      	        Ext.getCmp(me.gridId).getStore().load();
	      	    }
    		});
    	}
    },
	
	//end action handler
	
    createBbar : function(){
    	
    	var actions = [
			this.createModuleAction(this.createAction),
			this.createModuleAction(this.editAction),
			this.createModuleAction(this.destroyAction)
		];
    	
    	return this.createToolbar(actions);
    }
});
