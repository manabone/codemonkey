
Ext.define('AM.modules.ListModule', {
    extend: 'Ext.ux.desktop.Module',

    hidden : true,
    
    Model : null,
	
	formModuleId : null,
	
	iconText : null,
	iconCls : null,
	
	gridId : null,
	searchFormId : null,
	
    modelFields : Ext.emptyFn,
	gridCols : Ext.emptyFn,
	searchForm : Ext.emptyFn,
	
    init : function(){
    	var me = this;
    	
    	this.callParent();
    	
    	me.gridId = me.id + "_grid";
    	me.searchFormId = me.id + "_searchForm";
        
        me.Model = Ext.define(me.modelName , {
	        extend: 'Ext.data.Model',
	        fields: me.modelFields() || ExtUtils.defaultModelFields,
	        proxy: ExtUtils.proxy(me.modelName)
		});
    	
       me.grid = ExtUtils.ajaxGrid({
        	gridId:me.gridId , 
        	model : me.modelName , 
        	modelFields : me.modelFields() , 
        	columns : me.gridCols() 
        });
        
    },
    
    createWindowItem : function(){
    	var me = this;
        var gridConfig = {
     		id : this.gridId,
     		listeners: {
 				'itemdblclick' : function( /*Ext.view.View*/ view, /* Ext.data.Model*/ record, /*HTMLElement*/ item,/* Number*/ index, /*Ext.EventObject*/ e, /*Object*/ eOpts){
 					me.show();
 			    }
 			},
 			viewConfig: {
 	             stripeRows: true,
 	             enableTextSelection:true,
 	             listeners: {
 	                 itemcontextmenu: function(view, rec, node, index, e) {
 	                	 e.stopEvent();
 	                	 var contextMenu = me.getLineContextMenu(rec);
 	                     contextMenu.showAt(e.getXY());
 	                     return false;
 	                 }
 	             }
 	         }
         };
         var grid2 = Ext.apply(gridConfig , this.grid);
         var searchFormDefaultConfig = {
         	id : this.searchFormId,
          	buttons : [
				this.createModuleAction(me.searchAction),
				this.createModuleAction(me.resetAction)
            ]
          };
         
         var searchFormConfig = Ext.apply({} , this.searchForm() , searchFormDefaultConfig);
         
         var searchForm = ExtUtils.searchingForm(searchFormConfig);
         
         return [ searchForm ,  grid2];
    },
    
    afterWindowCreate : function(config){
    	Ext.getCmp(this.gridId).getStore().load();
    },
    
    // actions 
    createAction : {
		action : 'create', text: i18n.add, iconCls : 'add'
	},
	
    editAction : {
		action : 'edit' , text:i18n.edit, iconCls:'option'
    },
    
    showAction : {
		action : 'show' , text:i18n.show, iconCls:'option'
    },
    
    destroyAction : {
		action : 'destroy', text: i18n.destroy, iconCls : 'remove'
	},
	
	searchAction : {
		action : 'search', text: i18n.search, iconCls : 'icon-search'
	},
	
	resetAction : {
		action : 'reset', text: i18n.reset, iconCls : 'icon-reset'
	},
    
	//end actions
	
	//actions handler
	create : function(){
		if(this.formModuleId){
			var formModule = this.app.getModule(this.formModuleId);
		 	formModule.createWindow({entityId : -1 , gridId : this.gridId, action : 'create'});
		 	return formModule;
		}
	},
	
	edit : function(){
		
		if(!this.formModuleId) return;
		
		var formModule = this.app.getModule(this.formModuleId);
		
		var record = ExtUtils.getSelected(this.gridId);
		
		if(record && record.get('id')){
			formModule.createWindow({entityId : record.get('id') , gridId : this.gridId , action : 'edit'});
		}
	},
	
	show : function(){
		
		if(!this.formModuleId) return;
		
		var formModule = this.app.getModule(this.formModuleId);
		
		var record = ExtUtils.getSelected(this.gridId);
		
		if(record && record.get('id')){
			formModule.createWindow({entityId : record.get('id') , gridId : this.gridId , action : 'show'});
		}
	},
	
	search : function(){
    	var grid = Ext.getCmp(this.gridId);
    	ExtUtils.gridSearch(grid , this.searchFormId);
    },
    
    reset : function(){
    	var grid = Ext.getCmp(this.gridId);
    	ExtUtils.gridReset(grid , this.searchFormId);
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
	
    getBbarActions : function(){
    	return [this.createAction];
    },
    
    createBbar : function(){
    	
    	var actions = this.buildModuleActions(this.getBbarActions());
    	
    	if(actions){
    		return this.createToolbar(actions);
    	}
    },
    
	getLineContextMenu : function(record){
		
		var actions = this.buildModuleActions(this.getLineContextActions(record));
		if(actions){
			return Ext.create('Ext.menu.Menu', {
		        items: actions
		    });
		}
	},
	
	getLineContextActions : function(record){
		return  [this.showAction , this.editAction];
	},
	
	buildModuleActions : function(acts){
		if(acts){
			var actions = [];
			for(var i = 0 ; i < acts.length ; i++){
				actions.push(this.createModuleAction(acts[i]));
			}
			return actions;
		}
		return null;
	}
	
});
