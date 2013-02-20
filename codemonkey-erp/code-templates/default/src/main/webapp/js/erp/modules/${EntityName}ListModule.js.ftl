
Ext.define('erp.modules.${EntityName}ListModule', {
    extend: 'erp.modules.ListModule',

    id:'${entityName}ListModule',
    
    requires : ['erp.modules.${EntityName}FormModule'],
    
    hidden : true,
    
    winTitle : '${EntityName}',
    
    modelName : '${EntityName}List',
    
    formModuleId : '${entityName}FormModule',
	
	iconText : '${EntityName}',
	iconCls : 'icon-grid',
    
    modelFields : [<#list fields as f>'${f.name}'<#if f_has_next>,</#if></#list>],
	
	gridCols : ${columnsJson}
	
});
