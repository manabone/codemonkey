
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
    
    modelFields : function(){
    	return ['customer','totalAmount','warehouse'].concat(ExtUtils.defaultModelFields);
    },
	gridCols : function() {
		 return [
          {"dataIndex":"customer","flex":1,"header":"customer"},
          {"dataIndex":"totalAmount","flex":1,"header":"total amount"},
          {"dataIndex":"warehouse","flex":1,"header":"warehouse"},
         ].concat(ExtUtils.defaultFormItems);
	} 
	
});
