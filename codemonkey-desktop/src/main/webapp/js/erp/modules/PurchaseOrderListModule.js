
Ext.define('erp.modules.PurchaseOrderListModule', {
    extend: 'erp.modules.DocumentListModule',

    id:'purchaseOrderListModule',
    
    requires : ['erp.modules.PurchaseOrderFormModule'],
    
    hidden : true,
    
    winTitle : 'PurchaseOrder',
    
    modelName : 'PurchaseOrderList',
    
    formModuleId : 'purchaseOrderFormModule',
	
	iconText : 'PurchaseOrder',
	iconCls : 'icon-grid',
    
    modelFields : function(){
    	return ['vendor' , 'vendor_text' , 'totalAmount' , 'warehouse' , 'warehouse_text' , 'status'].concat(ExtUtils.defaultModelFields);
    },
    
    searchForm : function() {
    	return {
    		items : [
				{name  : "JOINS"   , xtype : 'hiddenfield' , fieldLabel : "JOINS"  , value : 'lines_LEFT'},
				{xtype :"searchingselect",name :"lines.item.id",config :{model :"ItemList"},fieldLabel :"item" }
    		]
    	};
    },
    
	gridCols : function() {
		 return ExtUtils.defaultGridCols1.concat([
          {dataIndex : "vendor", hidden : true},
          {dataIndex : "vendor_text",flex : 1 , header : "vendor"},
          {dataIndex : "warehouse", hidden : true},
          {dataIndex : "warehouse_text" , flex : 1 , header : "warehouse"},
          {dataIndex : "status",flex : 1 , header : "status"},
          {dataIndex : "totalAmount",flex : 1 , header : "total amount"},
         ]).concat(ExtUtils.defaultGridCols2);
	}
	
});
