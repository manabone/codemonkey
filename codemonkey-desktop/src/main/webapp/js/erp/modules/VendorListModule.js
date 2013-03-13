
Ext.define('erp.modules.VendorListModule', {
    extend: 'AM.modules.ListModule',

    id:'vendorListModule',
    
    requires : ['erp.modules.VendorFormModule'],
    
    hidden : true,
    
    winTitle : 'Vendor',
    
    modelName : 'VendorList',
    
    formModuleId : 'vendorFormModule',
	
	iconText : 'Vendor',
	iconCls : 'icon-grid',
    
	modelFields : function(){
    	return ExtUtils.defaultModelFields;
    },
	
	gridCols : function(){
		return ExtUtils.defaultGridCols1.concat(ExtUtils.defaultGridCols2);
	} 
});
