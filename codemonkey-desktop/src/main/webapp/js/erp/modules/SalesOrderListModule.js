
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
    	return ['customer' , 'customer_text' , 'totalAmount' , 'warehouse' , 'warehouse_text'].concat(ExtUtils.defaultModelFields);
    },
	gridCols : function() {
		 return ExtUtils.defaultGridCols1.concat([
          {dataIndex : "customer", hidden : true},
          {dataIndex : "customer_text",flex : 1 , header : "customer"},
          {dataIndex : "warehouse", hidden : true},
          {dataIndex : "warehouse_text" , flex : 1 , header : "warehouse"},
          {dataIndex : "totalAmount",flex : 1 , header : "total amount"},
         ]).concat(ExtUtils.defaultGridCols2);
	} 
	
});
