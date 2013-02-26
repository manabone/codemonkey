
Ext.define('erp.modules.PurchaseOrderLineListModule', {
    extend: 'erp.modules.ListModule',

    id:'purchaseOrderLineListModule',
    
    requires : ['erp.modules.PurchaseOrderLineFormModule'],
    
    hidden : true,
    
    winTitle : 'PurchaseOrderLine',
    
    modelName : 'PurchaseOrderLineList',
    
    formModuleId : 'purchaseOrderLineFormModule',
	
	iconText : 'PurchaseOrderLine',
	iconCls : 'icon-grid',
    
    modelFields : ['purchaseOrder','price','item','qty','id','code','name','description','originVersion','creationDate','createdBy','modificationDate','modifiedBy'],
	
	gridCols : [{"dataIndex":"purchaseOrder","flex":1,"header":"purchase order"},{"dataIndex":"price","flex":1,"header":"price"},{"dataIndex":"item","flex":1,"header":"item"},{"dataIndex":"qty","flex":1,"header":"qty"},{"dataIndex":"id","flex":1,"header":"自动编号"},{"dataIndex":"code","flex":1,"header":"编码"},{"dataIndex":"name","flex":1,"header":"名称"},{"dataIndex":"description","flex":1,"header":"描述"},{"dataIndex":"originVersion","flex":1,"header":"origin version"},{"dataIndex":"creationDate","flex":1,"header":"创建时间"},{"dataIndex":"createdBy","flex":1,"header":"创建人"},{"dataIndex":"modificationDate","flex":1,"header":"修改时间"},{"dataIndex":"modifiedBy","flex":1,"header":"修改人"}]
	
});
