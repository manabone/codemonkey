
Ext.define('erp.modules.CustomerListModule', {
    extend: 'AM.modules.ListModule',

    id:'customerListModule',
    
    requires : ['erp.modules.CustomerFormModule'],
    
    hidden : true,
    
    winTitle : 'Customer',
    
    modelName : 'CustomerList',
    
    formModuleId : 'customerFormModule',
	
	iconText : 'Customer',
	iconCls : 'icon-grid',
    
    modelFields : ['id','code','name','description','originVersion','creationDate','createdBy','modificationDate','modifiedBy'],
	
	gridCols : [{"dataIndex":"id","flex":1,"header":"id"},{"dataIndex":"code","flex":1,"header":"编码"},{"dataIndex":"name","flex":1,"header":"名称"},{"dataIndex":"description","flex":1,"header":"描述"},{"dataIndex":"originVersion","flex":1,"header":"origin version"},{"dataIndex":"creationDate","flex":1,"header":"创建时间"},{"dataIndex":"createdBy","flex":1,"header":"创建人"},{"dataIndex":"modificationDate","flex":1,"header":"修改时间"},{"dataIndex":"modifiedBy","flex":1,"header":"修改人"}]
	
});
