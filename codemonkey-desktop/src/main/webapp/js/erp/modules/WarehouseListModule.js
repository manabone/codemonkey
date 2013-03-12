
Ext.define('erp.modules.WarehouseListModule', {
    extend: 'AM.modules.ListModule',

    id:'warehouseListModule',
    
    requires : ['erp.modules.WarehouseFormModule'],
    
    hidden : true,
    
    winTitle : 'Warehouse',
    
    modelName : 'WarehouseList',
    
    formModuleId : 'warehouseFormModule',
	
	iconText : 'Warehouse',
	iconCls : 'icon-grid',
    
	modelFields : function(){
    	return ExtUtils.defaultModelFields;
    },
	
	gridCols : function(){
		return ExtUtils.defaultGridCols;
	} 
});
