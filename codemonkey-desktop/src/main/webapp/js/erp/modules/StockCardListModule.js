
Ext.define('erp.modules.StockCardListModule', {
    extend: 'AM.modules.ListModule',

    id:'stockCardListModule',
    
    requires : ['erp.modules.StockCardFormModule'],
    
    hidden : true,
    
    winTitle : 'StockCard',
    
    modelName : 'StockCardList',
    
    formModuleId : 'stockCardFormModule',
	
	iconText : 'StockCard',
	iconCls : 'icon-grid',
    
    modelFields : ['item','warehouse','qtyOnHand','id','code','name','description','originVersion','creationDate','createdBy','modificationDate','modifiedBy'],
	
	gridCols : [{"dataIndex":"item","flex":1,"header":"item"},{"dataIndex":"warehouse","flex":1,"header":"warehouse"},{"dataIndex":"qtyOnHand","flex":1,"header":"qty on hand"},{"dataIndex":"id","flex":1,"header":"自动编号"},{"dataIndex":"code","flex":1,"header":"编码"},{"dataIndex":"name","flex":1,"header":"名称"},{"dataIndex":"description","flex":1,"header":"描述"},{"dataIndex":"originVersion","flex":1,"header":"origin version"},{"dataIndex":"creationDate","flex":1,"header":"创建时间"},{"dataIndex":"createdBy","flex":1,"header":"创建人"},{"dataIndex":"modificationDate","flex":1,"header":"修改时间"},{"dataIndex":"modifiedBy","flex":1,"header":"修改人"}]
	
});
