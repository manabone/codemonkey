
Ext.define('erp.modules.SalesShipmentListModule', {
    extend: 'erp.modules.DocumentListModule',

    id:'salesShipmentListModule',
    
    requires : ['erp.modules.SalesShipmentFormModule'],
    
    hidden : true,
    
    winTitle : 'SalesShipment',
    
    modelName : 'SalesShipmentList',
    
    formModuleId : 'salesShipmentFormModule',
	
	iconText : 'SalesShipment',
	iconCls : 'icon-grid',
    
    modelFields : function(){
    	return ['customer' , 'customer_text' , 'totalAmount' , 'warehouse' , 'warehouse_text' , 'status'].concat(ExtUtils.defaultModelFields);
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
          {dataIndex : "customer", hidden : true},
          {dataIndex : "customer_text",flex : 1 , header : "customer"},
          {dataIndex : "warehouse", hidden : true},
          {dataIndex : "warehouse_text" , flex : 1 , header : "warehouse"},
          {dataIndex : "status",flex : 1 , header : "status"},
          {dataIndex : "totalAmount",flex : 1 , header : "total amount"},
         ]).concat(ExtUtils.defaultGridCols2);
	} 
	
});
