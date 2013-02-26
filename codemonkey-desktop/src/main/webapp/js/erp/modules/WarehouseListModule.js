
Ext.define('erp.modules.WarehouseListModule', {
    extend: 'erp.modules.ListModule',

    id:'warehouseListModule',
    
    requires : ['erp.modules.WarehouseFormModule'],
    
    hidden : true,
    
    winTitle : 'Warehouse',
    
    modelName : 'WarehouseList',
    
    formModuleId : 'warehouseFormModule',
	
	iconText : 'Warehouse',
	iconCls : 'icon-grid',
    
    modelFields : ['id','code','name','description','originVersion','creationDate','createdBy','modificationDate','modifiedBy'],
	
	gridCols : [{"dataIndex":"id","flex":1,"header":"自动编号"},{"dataIndex":"code","flex":1,"header":"编码"},{"dataIndex":"name","flex":1,"header":"名称"},{"dataIndex":"description","flex":1,"header":"描述"},{"dataIndex":"originVersion","flex":1,"header":"origin version"},{"dataIndex":"creationDate","flex":1,"header":"创建时间"},{"dataIndex":"createdBy","flex":1,"header":"创建人"},{"dataIndex":"modificationDate","flex":1,"header":"修改时间"},{"dataIndex":"modifiedBy","flex":1,"header":"修改人"}]
	
});
