
Ext.define('test.modules.${EntityName}FormModule', {
    extend: 'AM.modules.FormModule',

    id:'${entityName}FormModule',
    
    hidden : true,
    
    winTitle : '${EntityName}',
    
    modelName : '${EntityName}',
    
    modelFields : function(){
    
    	return [<#list fields as f>'${f.name}'<#if f_has_next>,</#if></#list>].concat(ExtUtils.defaultModelFields);
    },
    
    formItems : function(){
    
		var me = this;
    	
    	var p1 = ExtUtils.creationInfoPanel();
    	
    	var p2 = ExtUtils.panel({
    		title : 'Detail',
    		items : ${fieldsJson}
    	});
    	
    	return ExtUtils.fitLayout([p1, p2]);
    }
});
