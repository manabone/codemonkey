
Ext.define('AM.modules.ChangePasswordFormModule', {
    extend: 'AM.modules.FormModule',

    id:'changePasswordFormModule',
    
    winTitle : '修改密码',
    
    modelName : 'AppUser',
    
    // actions 
    
    //end actions
    formItems : function(){
    	return [
   			{xtype:"textfield",fieldLabel:"新密码" , inputType : 'password', name : "password"},
   			{xtype:"textfield",fieldLabel:"确认新密码" , inputType : 'password', name : "password_ack"}
		];
    },
    
    //action handlers
    save : function(){
    	var me = this;
		
		if(!ExtUtils.validateForm(me.formId)){
			return;
		}
		
    	var values = ExtUtils.formValues(me.formId);
		if(values.password && values.password == values.password_ack){
			me.doAction('changePassword' , function(){
	    		me.cancel();
			}, Ext.encode(values));
		}else{
			alert("密码不一致");
		}
    }
    //end action handlers
});
