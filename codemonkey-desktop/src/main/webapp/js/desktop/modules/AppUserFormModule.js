
Ext.define('AM.modules.AppUserFormModule', {
    extend: 'AM.modules.FormModule',

    id:'appUserFormModule',
    
    hidden : true,
    
    winTitle : 'App User',
    
    modelName : 'AppUser',
    
    appRoleGridId : 'appUserForm_appRolesGrid',
    
    modelFields : function() {
    	return ['username' , 'roles'].concat(ExtUtils.defaultModelFields);
    },
    
    // actions 
    addAppRoleAction : {
		iconCls : 'add' , text: '添加' , action : 'addAppRole'
	},
	
	removeAppRoleAction : {
		iconCls:'remove' , text: '删除' , action : 'removeAppRole'
    },
    
    changePasswordAction : {
		iconCls:'change' , text: '修改密码' , action : 'changePassword'
    },
  
    //end actions
    
    formItems : function(){
    	var me = this;
    	var p1 = ExtUtils.panel({
    		title : '基本信息',
			items:[
    			{xtype:"textfield",name:"username",fieldLabel:"用户名"},
    			{xtype:"textfield",fieldLabel:"姓名" , name : 'name'}
			]
    	});
    	
    	var p2 = ExtUtils.creationInfoPanel();
    	
    	var appRolesGrid = ExtUtils.arrayGrid({
    		id : me.appRoleGridId,
    		modelName : 'AppRole',
    		ownerModule : me,
			columns : ExtUtils.defaultGridCols1
		});
    	
      	var p3ActionBar =  me.createToolbar([
             me.createModuleAction(this.addAppRoleAction),
             me.createModuleAction(this.removeAppRoleAction)
      	]);
    	
    	var p3 = ExtUtils.panel({
    		title : '用户角色',
    		collapsible : true,
    		items : appRolesGrid,
    		tbar: p3ActionBar
    	});
    	
    	return ExtUtils.fitLayout([p1, p3 ,p2 ]);
    },
    
    createBbar : function(){
    	var actions = [
   			this.createModuleAction(this.saveAction),
   			this.createModuleAction(this.cancelAction),
   			this.createModuleAction(this.changePasswordAction)
   		];
    	       	
    	return this.createToolbar(actions);
    },
    
    //action handlers
    
    addAppRole : function(){
    	
    	var me = this;
    	
    	ExtUtils.popup({
			id : 'appRole',
			modelName : 'AppRoleList',
			columns : ExtUtils.defaultGridCols1,
			itemdblclick : function(view , record , item , e , eOpts){
				me.addAppRole_callback(record);
			} ,
			okclick : function(record){
				me.addAppRole_callback(record);
			}
		});
    },
    
    addAppRole_callback : function(record){
    	var me = this;
    	var grid = Ext.getCmp(me.appRoleGridId);
    	ExtUtils.addLine(grid , record.data);
    },
    
    removeAppRole : function(){
    	var me = this;
    	var grid = Ext.getCmp(me.appRoleGridId);
    	ExtUtils.removeLine(grid);
    },
    
    changePassword : function(){
    	var me = this;
    	var win = Ext.create('Ext.window.Window', {
    		id : "appUserFormModule_changePasswordWin",  
	        modal : true,
	        title: '修改密码',
	        
    		items : Ext.create('Ext.form.Panel', {
    			id : 'appUserFormModule_passwordForm',
    			bodyStyle: 'padding:20px 20px 10px 20px',
    			items: [
    			    {xtype:"hidden", name : "id" , value : this.entityId},
	       			{xtype:"textfield",fieldLabel:"新密码" , inputType : 'password', name : "password"},
	       			{xtype:"textfield",fieldLabel:"确认新密码" , inputType : 'password', name : "password_ack"}
	   			]
    		}),
	   		
	   		buttons : [{
			    id : 'ok',
				text: i18n.ok,
				handler : function(){
					var values = ExtUtils.formValues("appUserFormModule_passwordForm");
					if(values.password && values.password == values.password_ack){
						me.doAction('changePassword' , function(){
				    		Ext.getCmp('appUserFormModule_changePasswordWin').close();
						}, Ext.encode(values));
					}else{
						alert("密码不一致");
					}
			    }
			}, {
				id: 'cancel',
			    text: i18n.cancel,
			    handler : function(){
			    	Ext.getCmp('appUserFormModule_changePasswordWin').close();
			    }
			}]
    	});
	    win.show();
    },
    
    //end action handlers
    
    
    beforeSave : function(values){
    	var me = this;
    	var roles = ExtUtils.getAllData(Ext.getCmp(me.appRoleGridId));
    	Ext.apply(values , {
    		roles : roles || []
    	});
    },
    
    afterModelLoad : function(model){
    	var me = this;
    	Ext.getCmp(me.appRoleGridId).getStore().loadData(model.data.roles);
    }
});
