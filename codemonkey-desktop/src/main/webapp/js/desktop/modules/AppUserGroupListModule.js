
Ext.define('AM.modules.AppUserGroupListModule', {
    extend: 'AM.modules.ListModule',

    id:'appUserGroupListModule',
    
    requires : ['AM.modules.AppUserGroupFormModule'],
    
    hidden : true,
    
    winTitle : 'AppUserGroup',
    
    modelName : 'AppUserGroupList',
    
    formModuleId : 'appUserGroupFormModule',
	
	iconText : 'AppUserGroup',
	iconCls : 'icon-grid',
    
    modelFields : ['id','name','description','version','creationDate','createdBy','modificationDate','modifiedBy'],
	
	gridCols : [{"dataIndex":"id","flex":1,"header":"id"},{"dataIndex":"name","flex":1,"header":"name"},{"dataIndex":"description","flex":1,"header":"description"},{"dataIndex":"version","flex":1,"header":"version"},{"dataIndex":"creationDate","flex":1,"header":"creation date"},{"dataIndex":"createdBy","flex":1,"header":"created by"},{"dataIndex":"modificationDate","flex":1,"header":"modification date"},{"dataIndex":"modifiedBy","flex":1,"header":"modified by"}]
	
});
