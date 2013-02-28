
Ext.define('erp.modules.PurchaseOrderListModule', {
    extend: 'AM.modules.ListModule',

    id:'purchaseOrderListModule',
    
    requires : ['erp.modules.PurchaseOrderFormModule'],
    
    hidden : true,
    
    winTitle : 'PurchaseOrder',
    
    modelName : 'PurchaseOrderList',
    
    formModuleId : 'purchaseOrderFormModule',
	
	iconText : 'PurchaseOrder',
	iconCls : 'icon-grid',
    
    modelFields : ['vendor','totalAmount','warehouse','id','code','name','description','originVersion','creationDate','createdBy','modificationDate','modifiedBy'],
	
	gridCols : [{"dataIndex":"vendor","flex":1,"header":"vendor"},{"dataIndex":"totalAmount","flex":1,"header":"total amount"},{"dataIndex":"warehouse","flex":1,"header":"warehouse"},{"dataIndex":"id","flex":1,"header":"自动编号"},{"dataIndex":"code","flex":1,"header":"编码"},{"dataIndex":"name","flex":1,"header":"名称"},{"dataIndex":"description","flex":1,"header":"描述"},{"dataIndex":"originVersion","flex":1,"header":"origin version"},{"dataIndex":"creationDate","flex":1,"header":"创建时间"},{"dataIndex":"createdBy","flex":1,"header":"创建人"},{"dataIndex":"modificationDate","flex":1,"header":"修改时间"},{"dataIndex":"modifiedBy","flex":1,"header":"修改人"}]
	
});
