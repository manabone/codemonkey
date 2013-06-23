
Ext.define('AM.modules.AppRoleListModule', {
    extend: 'AM.modules.ListModule',

    id:'appRoleListModule',
    
    requires : ['AM.modules.AppRoleFormModule'],
    
    hidden : true,
    
    winTitle : 'App Roles',
    
    modelName : 'AppRoleList',
    
    formModuleId : 'appRoleFormModule',
	
	iconText : 'App Roles',
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
				displayInfo: true
			}
	    };
    }
});
