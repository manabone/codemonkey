
Ext.define('AM.modules.AppRoleFormModule', {
    extend: 'AM.modules.FormModule',

    id:'appRoleFormModule',
    
    hidden : true,
    
    winTitle : 'App Role',
    
    modelName : 'AppRole',
    
    formItems : function(){
    	return [
				{"xtype":"textfield","name":"name","fieldLabel":"name" , allowBlank : false},
				{"xtype":"textfield","name":"description","fieldLabel":"description"},
				{"xtype":"numberfield","name":"id","fieldLabel":"id"}
	    	];
    }
});
