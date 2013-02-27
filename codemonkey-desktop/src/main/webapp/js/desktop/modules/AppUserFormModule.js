
Ext.define('AM.modules.AppUserFormModule', {
    extend: 'AM.modules.FormModule',

    id:'appUserFormModule',
    
    hidden : true,
    
    winTitle : 'App User',
    
    modelName : 'AppUser',
    
    formItems : function(){
    	return [
				{"xtype":"textfield","name":"name","fieldLabel":"name" , allowBlank : false},
				{"xtype":"textfield","name":"description","fieldLabel":"description"},
				{"xtype":"numberfield","name":"id","fieldLabel":"id"}
	    	];
    }
});
