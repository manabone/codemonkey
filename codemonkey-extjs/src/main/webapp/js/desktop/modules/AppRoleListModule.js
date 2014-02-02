
Ext.define('AM.modules.AppRoleListModule', {
    extend: 'AM.modules.ListModule',

    id:'appRoleListModule',
    
    requires : ['AM.modules.AppRoleFormModule'],
    
    winTitle : 'App Roles',
    
    modelName : 'AppRoleList',
    
    formModuleId : 'appRoleFormModule',
	
	iconCls : 'icon-grid',
    
	modelFields : function(){
	   	return ExtUtils.defaultModelFields;
	},
    
	gridCols : function() {
		return ExtUtils.defaultGridCols1.concat(ExtUtils.defaultGridCols2);
	}
   
});
