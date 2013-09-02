Ext.define('AM.modules.MModule', {

	extend : 'Ext.panel.Panel',

	alias : 'widget.mmodule',

	layout : {
		type : 'vbox',
		align : 'stretch',
		pack : 'start'
	},
	
	 // actions 
    createAction : {
		action : 'create', text: i18n.add, iconCls : 'add'
	},
	
	cancelAction : {
		action : 'cancel', text: i18n.cancel, iconCls : 'backToList'
	},
	
	editAction : {
		action : 'edit' , text:i18n.edit, iconCls:'option'
    },
    
    saveAction : {
		action : 'save' , text:i18n.save, iconCls:'save'
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
	
	beforeSave : function(values){
		
	},

	initComponent : function() {
		var me = this;
				 
		me.detailWindowId = me.id + '_detailWin';
		me.formId = me.id + '_form';
		me.searchingFormId = me.id + '_searchingForm';
		me.gridId = me.id + '_grid';
		me.modelName = me.config.modelName;
		
		me.Model = Ext.define(this.modelName , {
	        extend: 'Ext.data.Model',
	        fields: me.config.modelFields,
	        proxy: ExtUtils.proxy(this.modelName)
		});

		var grid = ExtUtils.ajaxGrid({
			gridId: me.gridId, 
        	model : this.config.modelName , 
        	columns : this.config.cols,
        	bbar : {}
		});
		if(this.config.searchingFormItems && this.config.searchingFormItems.length > 0){
			var searchingForm = ExtUtils.searchingForm({
				id : me.searchingFormId , 
				items : me.config.searchingFormItems,
				buttons : [
					ExtUtils.createModuleAction(me , me.searchAction),
					ExtUtils.createModuleAction(me , me.resetAction)
	            ]
			});
			this.items = [searchingForm , grid];
		}else{
			this.items = [grid];
		}
		this.bbar =  ExtUtils.createToolbar( [
			ExtUtils.createModuleAction(me , this.createAction),
			ExtUtils.createModuleAction(me , this.editAction),
			ExtUtils.createModuleAction(me , this.destroyAction)
		]);
		
		this.callParent();
		
		ExtUtils.gridSearch(Ext.getCmp(me.gridId) , me.searchingFormId);
	},
	
	//actions handler
	create : function(){
		this.createWin(this);
	},
	
	createWin : function(module){
		return Ext.create('Ext.window.Window' , {
			id : module.detailWindowId,
			title : 'Edit',
			layout: 'fit',
			modal : true,
			autoShow : true,
		    items : [
				{
					id : module.formId,
					xtype: 'form',
					padding: '5 5 0 5',
					border: false,
					style: 'background-color: #fff;',
					items: module.config.formItems
				}
			],

			bbar :  ExtUtils.createToolbar([
                  ExtUtils.createModuleAction(module , module.saveAction),
                  ExtUtils.createModuleAction(module , module.cancelAction)
           ])
		});
	},
	
	edit : function(){
		var me = this;
		var record = ExtUtils.getSelected(me.gridId);
		
		if(record){
			var win = Ext.getCmp(me.detailWindowId);
			if(!win){
				win = me.createWin(this);
			}
			win.show();
			ExtUtils.record2form(record.data , me.formId);
		}
		
	},
	
	search : function(){
		var me = this;
		var grid = Ext.getCmp(me.gridId);
    	ExtUtils.gridSearch(grid , me.searchingFormId);
    },
    
    reset : function(){
    	var me = this;
    	var grid = Ext.getCmp(me.gridId);
    	ExtUtils.gridReset(grid , me.searchingFormId);
    },
    
    destroy : function(){
    	var me = this;
    	var record = ExtUtils.getSelected(me.gridId);
    	if(record){
    		var model = this.Model.create(record.data);
    		model.destroy({
    			success: function(model) {
	      	        ExtUtils.gridSearch(Ext.getCmp(me.gridId) , me.searchingFormId);
	      	    }
    		});
    	}
    },
    
    cancel : function(){
    	var me = this;
    	Ext.getCmp(me.detailWindowId).close();
    },
    
    save : function(){
    	var me = this;
		ExtUtils.mask();
		ExtUtils.moduleDoSave(me);

    },
    
    save_callback : function(){
    	var me = this;
    	Ext.getCmp(me.detailWindowId).close();
    	me.search();
    }
    
	//end action handler

});
