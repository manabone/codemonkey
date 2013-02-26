
Ext.define('erp.modules.TransactionListModule', {
    extend: 'erp.modules.ListModule',

    id:'transactionListModule',
    
    requires : ['erp.modules.TransactionFormModule'],
    
    hidden : true,
    
    winTitle : 'Transaction',
    
    modelName : 'TransactionList',
    
    formModuleId : 'transactionFormModule',
	
	iconText : 'Transaction',
	iconCls : 'icon-grid',
    
    modelFields : ['item','qty','price','cost','amount','id','code','name','description','originVersion','creationDate','createdBy','modificationDate','modifiedBy'],
	
	gridCols : [{"dataIndex":"item","flex":1,"header":"item"},{"dataIndex":"qty","flex":1,"header":"qty"},{"dataIndex":"price","flex":1,"header":"price"},{"dataIndex":"cost","flex":1,"header":"cost"},{"dataIndex":"amount","flex":1,"header":"amount"},{"dataIndex":"id","flex":1,"header":"自动编号"},{"dataIndex":"code","flex":1,"header":"编码"},{"dataIndex":"name","flex":1,"header":"名称"},{"dataIndex":"description","flex":1,"header":"描述"},{"dataIndex":"originVersion","flex":1,"header":"origin version"},{"dataIndex":"creationDate","flex":1,"header":"创建时间"},{"dataIndex":"createdBy","flex":1,"header":"创建人"},{"dataIndex":"modificationDate","flex":1,"header":"修改时间"},{"dataIndex":"modifiedBy","flex":1,"header":"修改人"}]
	
});
