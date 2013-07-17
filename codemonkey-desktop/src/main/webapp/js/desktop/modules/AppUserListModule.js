
Ext.define('AM.modules.AppUserListModule', {
    extend: 'AM.modules.ListModule',

    id : 'appUserListModule',
    
    requires : ['AM.modules.AppUserFormModule'],
    
    hidden : true,
    
    winTitle : 'App User',
    
    modelName : 'AppUserList',
    
    formModuleId : 'appUserFormModule',
	
	iconText : 'App Users',
	iconCls : 'icon-grid',
    
	
	modelFields : function(){
    	return ['username'].concat(ExtUtils.defaultModelFields);
    },
    
    searchForm : function() {
    	return {
    		items : [
				{xtype :"textfield",name :"username_Like",fieldLabel :"用户名" }
    		]
    	};
    },
    
	gridCols : function() {
		 return [
         {dataIndex : "id",flex : 1 , hidden : true},
         {dataIndex : "username",flex : 1 , header : "用户名"}
         ].concat(ExtUtils.defaultGridCols2);
	} 
});
