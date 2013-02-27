
Ext.define('AM.modules.AppUserListModule', {
    extend: 'AM.modules.ListModule',

    id:'appUserListModule',
    
    requires : ['AM.modules.AppUserFormModule'],
    
    hidden : true,
    
    winTitle : 'App User',
    
    modelName : 'AppUserList',
    
    formModuleId : 'appUserFormModule',
	
	iconText : 'App Users',
	iconCls : 'icon-grid',
    
	
    init : function(){
    	this.callParent();
    	var me = this;
    	
    	me.Model = Ext.define(me.modelName , {
	        extend: 'Ext.data.Model',
	        fields: ['id', 'name', 'description'],
	        proxy: ExtUtils.proxy(me.modelName)
		});
    	    
    	me.store = ExtUtils.ajaxStore({model : me.modelName});
		
    	me.grid = {
    		xtype : 'grid',	
    		flex : 3,
	     	columns :  [
	            {header: 'id',  dataIndex: 'id',  flex: 1},
	 	  		{header: 'name' ,  dataIndex: 'name',  flex: 1},
	 	  		{header: 'description' ,  dataIndex: 'description',  flex: 1}
	 	  	],
	     	store : me.store,
	     	bbar:{
				xtype : 'pagingtoolbar',
				store : me.store,
				displayInfo: true,
				emptyMsg: "No records to display"
			}
	    };
    }
});
