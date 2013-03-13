
Ext.define('erp.modules.ItemFormModule', {
    extend: 'AM.modules.FormModule',

    id:'itemFormModule',
    
    hidden : true,
    
    winTitle : 'Item',
    
    modelName : 'Item',
    
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
