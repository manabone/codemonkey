
Ext.define('erp.modules.CustomerListModule', {
    extend: 'AM.modules.ListModule',

    id:'customerListModule',
    
    requires : ['erp.modules.CustomerFormModule'],
    
    hidden : true,
    
    winTitle : 'Customer',
    
    modelName : 'CustomerList',
    
    formModuleId : 'customerFormModule',
	
	iconText : 'Customer',
	iconCls : 'icon-grid',
    
	modelFields : function(){
    	return ExtUtils.defaultModelFields;
    },
	
	gridCols : function(){
		return ExtUtils.defaultGridCols1.concat(ExtUtils.defaultGridCols2);
	} 
});
