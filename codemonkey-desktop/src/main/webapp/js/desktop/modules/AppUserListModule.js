
Ext.define('AM.modules.AppUserListModule', {
    extend: 'AM.modules.ListModule',

    id : 'appUserListModule',
    
    requires : ['AM.modules.AppUserFormModule'],
    
    hidden : true,
    
    winTitle : 'App User',
    
    modelName : 'AppUserList',
    
    formModuleId : 'appUserFormModule',
	
	iconText : 'App Users',
	iconCls : 'icon-grid',
    
	
	modelFields : function(){
    	return ['username'].concat(ExtUtils.defaultModelFields);
    },
    
    searchForm : function() {
    	return {
    		items : [
				{xtype :"textfield",name :"username_Like",fieldLabel :"用户名" }
    		]
    	};
    },
    
	gridCols : function() {
		 return [
         {dataIndex : "id",flex : 1 , hidden : true},
         {dataIndex : "username",flex : 1 , header : "用户名"},
         {dataIndex : "name",flex : 1 , header : "姓名"}
         ].concat(ExtUtils.defaultGridCols2);
	},
	
	createBbar : function(){
    	
    	var actions = [
			this.createModuleAction(this.editAction),
		];
    	
    	return this.createToolbar(actions);
    },
    
    createTbar : function(){
    	
    	var menu =  {
            text: i18n.actions,
            menu : [
				this.createModuleAction(this.editAction),
    		]
    	};
    	
    	return this.createToolbar(menu);
    }
});
