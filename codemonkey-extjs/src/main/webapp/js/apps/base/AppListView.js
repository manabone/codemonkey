Ext.define('AM.base.AppListView' , {
    
	extend: 'AM.base.AppView',

    Model : null,
	
	iconText : null,
	iconCls : null,
	
	gridId : null,
	searchFormId : null,
	
	formClass : null,
	
    modelFields : Ext.emptyFn,
	gridCols : Ext.emptyFn,
	searchForm : Ext.emptyFn,
	initGrid : Ext.emptyFn,
	
	constructor: function (config) {
		
		var me = this;
    	
    	me.gridId = me.modelName + "_grid_" + Ext.id();
    	me.searchFormId = me.modelName + "_searchForm_" + Ext.id();
        
        me.Model = Ext.define(me.modelName , {
	        extend: 'Ext.data.Model',
	        fields: me.modelFields() || ExtUtils.defaultModelFields,
	        proxy: ExtUtils.proxy(me.modelName)
		});
             
		var searchFormConfig = Ext.apply({} , this.searchForm() , {
		 	id : this.searchFormId,
		  	buttons : [
				this.createModuleAction(me.searchAction),
				this.createModuleAction(me.resetAction)
		    ]
		});
             
       me.searchForm = ExtUtils.searchingForm(searchFormConfig);
       
       var gridConfig = Ext.apply({} , this.initGrid() , {
		    	gridId : me.gridId , 
		       	model : me.modelName , 
		       	modelFields : me.modelFields() , 
		       	columns : me.gridCols(),
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
		      });
    	
       me.grid = ExtUtils.ajaxGrid(gridConfig);
       
       me.items =  me.createWindowItem();
       
       me.bbar = me.createBbar();
       
       this.callParent();
       
       this.afterWindowCreate();
    },
    
    createWindowItem : function(){
    	var me = this;
        return [me.searchForm ,  me.grid];
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
		if(this.formClass){
			var editView = Ext.create(this.formClass);
			editView.createWindow({entityId : -1 , gridId : this.gridId, action : 'create'});
		}
	},
	
	edit : function(){
		
		if(!this.formClass) return;
		
		var editView = Ext.create(this.formClass);
		
		var record = ExtUtils.getSelected(this.gridId);
		
		if(record && record.get('id')){
			editView.createWindow({entityId : record.get('id') , gridId : this.gridId , action : 'edit'});
		}
	},
	
	show : function(){
		
		if(!this.formClass) return;
		
		var editView = Ext.create(this.formClass);
		
		var record = ExtUtils.getSelected(this.gridId);
		
		if(record && record.get('id')){
			editView.createWindow({entityId : record.get('id') , gridId : this.gridId , action : 'show'});
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
	}
	
});
