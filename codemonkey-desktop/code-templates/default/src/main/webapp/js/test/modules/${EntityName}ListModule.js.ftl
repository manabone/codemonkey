
Ext.define('test.modules.${EntityName}ListModule', {
    extend: 'AM.modules.ListModule',

    id:'${entityName}ListModule',
    
    requires : ['test.modules.${EntityName}FormModule'],
    
    hidden : true,
    
    winTitle : '${EntityName}',
    
    modelName : '${EntityName}List',
    
    formModuleId : '${entityName}FormModule',
	
	iconText : '${EntityName}',
	iconCls : 'icon-grid',
    
    modelFields : function(){
    	return [<#list fields as f>'${f.name}'<#if f_has_next>,</#if></#list>].concat(ExtUtils.defaultModelFields);
    },
	
	gridCols  : function() {
		return ExtUtils.defaultGridCols1.concat(${columnsJson}).concat(ExtUtils.defaultGridCols2);
	}
	
});
