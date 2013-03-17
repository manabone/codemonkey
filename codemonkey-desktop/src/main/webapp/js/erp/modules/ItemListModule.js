
Ext.define('erp.modules.ItemListModule', {
    extend: 'AM.modules.ListModule',

    id:'itemListModule',
    
    requires : ['erp.modules.ItemFormModule'],
    
    hidden : true,
    
    winTitle : 'Item',
    
    modelName : 'ItemList',
    
    formModuleId : 'itemFormModule',
	
	iconText : 'Item',
	iconCls : 'icon-grid',
    
    modelFields : function(){
    	return ExtUtils.defaultModelFields;
    },
	
	gridCols : function(){
		return ExtUtils.defaultGridCols1.concat(ExtUtils.defaultGridCols2);
	} 
});
