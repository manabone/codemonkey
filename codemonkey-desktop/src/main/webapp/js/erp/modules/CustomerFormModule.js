
Ext.define('erp.modules.CustomerFormModule', {
    extend: 'AM.modules.FormModule',

    id:'customerFormModule',
    
    hidden : true,
    
    winTitle : 'Customer',
    
    modelName : 'Customer',
    
    modelFields : ['id','code','name','description','originVersion','creationDate','createdBy','modificationDate','modifiedBy'],
    
    formItems : function(){
    	var p1 = ExtUtils.panel({
    		title : 'basic info',
			items:[
    			{"xtype":"textfield","name":"code","fieldLabel":"编码"},
    			{"xtype":"textfield","name":"name","fieldLabel":"名称"},
    			{"xtype":"textfield","name":"description","fieldLabel":"描述"}
			]
    	});
    	
    	var p2 = ExtUtils.creationInfoPanel();
    	
    	return ExtUtils.fitLayout([p1,p2]);
    }
});
