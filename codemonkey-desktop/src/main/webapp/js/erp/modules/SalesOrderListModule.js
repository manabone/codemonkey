
Ext.define('erp.modules.SalesOrderListModule', {
    extend: 'erp.modules.DocumentListModule',

    id:'salesOrderListModule',
    
    requires : ['erp.modules.SalesOrderFormModule'],
    
    hidden : true,
    
    winTitle : 'SalesOrder',
    
    modelName : 'SalesOrderList',
    
    formModuleId : 'salesOrderFormModule',
	
	iconText : 'SalesOrder',
	iconCls : 'icon-grid',
    
    modelFields : ['customer','totalAmount','warehouse','id','code','name','description','originVersion','creationDate','createdBy','modificationDate','modifiedBy'],
	
	gridCols : [{"dataIndex":"customer","flex":1,"header":"customer"},{"dataIndex":"totalAmount","flex":1,"header":"total amount"},{"dataIndex":"warehouse","flex":1,"header":"warehouse"},{"dataIndex":"id","flex":1,"header":"自动编号"},{"dataIndex":"code","flex":1,"header":"编码"},{"dataIndex":"name","flex":1,"header":"名称"},{"dataIndex":"description","flex":1,"header":"描述"},{"dataIndex":"originVersion","flex":1,"header":"origin version"},{"dataIndex":"creationDate","flex":1,"header":"创建时间"},{"dataIndex":"createdBy","flex":1,"header":"创建人"},{"dataIndex":"modificationDate","flex":1,"header":"修改时间"},{"dataIndex":"modifiedBy","flex":1,"header":"修改人"}]
	
});
