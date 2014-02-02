var ACTION_BAR_INDEX = {
	create : 1,
	destroy : 10,
	spacer : 51,
	save  : 100,
	backToList : 110
};

Ext.define('AM.base.ActionBar' ,{
	
	actionBarItems : [],
	
	moduel : null,
	
	create : function(){
		var me = this;
		return {
			xtype : 'button',
			action : 'create',
		    text: 'add',
		    iconCls : 'add',
		    handler : function(){
		    	me.module.doCreate();
	        }
		};
	},
	
	edit : function(){
		var me = this;
		return {
	        text:'Edit',
	        tooltip:'Modify options',
	        iconCls:'option',
	        handler : function(){
		    	var record = ExtUtils.getSelectedRow(Ext.getCmp(me.module.gridId));
		    	if(record){
		    		me.module.doEdit(record.get('id'));
		    	}
	        }
		};
    },
	
	destroy : function(){
		var me = this;
		return {
			xtype : 'button',
			action : 'destroy',
		    text: 'destroy',
		    iconCls : 'remove',
		    handler : function(){
		    	var record = ExtUtils.getSelectedRow(Ext.getCmp(me.module.gridId));
		    	me.module.doDestroy(record);
		    }
		};
	},
	
	save : function(){
		var me = this;
		return {
			action : 'save',
			iconCls: 'icon-update',
			text : 'Save',
			handler : function(){
				me.module.doSave();
			}
		};
	},
	
	backToList : function(){
		var me = this;
		return {
			action : 'backToList',
			iconCls: 'icon-back-to-list',
			text : 'Back to List',
			handler : function(){
				me.module.doCancel();
			}
		};
	},
	
	spacer : {xtype : 'tbspacer',flex : 1},
	
	constructor : function(config) {
		Ext.apply(this , config);
	},
	
	createActionBar : function(){
		Ext.applyIf(this.actionBarItems , this.barItems);
		var barItems = [];
		for(var i = 0 ; i <= this.actionBarItems.length ; i++){
			if(this.actionBarItems[i]) {
				barItems.push(this.actionBarItems[i]);
			}
		}
		return barItems;
	}
	
});

Ext.define('AM.base.ListActionBar' , {
	
	extend : 'AM.base.ActionBar',
	
	constructor : function(config) {
		this.callParent([config]);
		
		this.actionBarItems = [];
		this.actionBarItems[ACTION_BAR_INDEX.create] = this.create();
		this.actionBarItems[ACTION_BAR_INDEX.destroy] = this.destroy();
		this.actionBarItems[ACTION_BAR_INDEX.spacer] = this.spacer;
		
		return this;
	}
	
});

Ext.define('AM.base.FormActionBar' ,{
	
	extend : 'AM.base.ActionBar',

	constructor : function(config) {
		this.callParent([config]);
		
		this.actionBarItems = [];
		this.actionBarItems[ACTION_BAR_INDEX.spacer] = this.spacer;
		this.actionBarItems[ACTION_BAR_INDEX.save] = this.save();
		this.actionBarItems[ACTION_BAR_INDEX.backToList] = this.backToList();
		
		return this;
	}
	
});
