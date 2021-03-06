
Ext.define('erp.modules.${EntityName}FormModule', {
    extend: 'AM.modules.FormModule',

    id:'${entityName}FormModule',
    
    hidden : true,
    
    winTitle : '${EntityName}',
    
    modelName : '${EntityName}',
    
    modelFields : [<#list fields as f>'${f.name}'<#if f_has_next>,</#if></#list>],
    
    formItems : function(){
		return ${fieldsJson};
    }
});
