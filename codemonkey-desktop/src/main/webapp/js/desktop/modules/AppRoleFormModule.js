
Ext.define('AM.modules.AppRoleFormModule', {
    extend: 'AM.modules.FormModule',

    id:'appRoleFormModule',
    
    hidden : true,
    
    winTitle : 'App Role',
    
    modelName : 'AppRole',
    
    // actions 
    addUrlPerimssionAction : {
		iconCls : 'add' , text: '添加' , action : 'addUrlPerimssion'
	},
	
	removeUrlPerimssionAction : {
		iconCls:'remove' , text: '删除' , action : 'removeUrlPerimssion'
    },
    //end actions
    
    urlPermissionColumns : [
		{header: '权限编码' ,  dataIndex: 'permission' },
		{header: '描述' ,  dataIndex: 'description', flex: 1},
		{header: '控件ID' ,  dataIndex: 'componentId',  flex: 1},
		{header: '请求路径' ,  dataIndex: 'url',  flex: 1},
		{header: '请求类型' ,  dataIndex: 'requestType', flex: 1}
    ],
    
    cmpPermissionColumns : [
        {header: '权限编码' ,  dataIndex: 'permission'},
        {header: '描述' ,  dataIndex: 'description', flex: 1},
  		{header: '控件ID' ,  dataIndex: 'componentId',  flex: 1},
  		{header: '控件类型' ,  dataIndex: 'cmpType',  flex: 1},
  		{header: '操作权限' ,  dataIndex: 'cmpPermissionType', flex: 1}
    ],
    
    formItems : function(){
    	var me = this;
    	var p1 = ExtUtils.panel({
    		title : '基本信息',
			items:[
    			{xtype:"textfield",name:"code",fieldLabel:"编码"},
    			{xtype:"textfield",name:"name",fieldLabel:"名称"},
    			{xtype:"textfield",name:"description",fieldLabel:"描述"}
			]
    	});
    	
    	var p2 = ExtUtils.creationInfoPanel();
    	
    	var urlPermissionGrid = ExtUtils.arrayGrid({
    		id : 'appRoleForm_urlPermissionGrid',
    		ownerModule : me,
            features: this.orderLineGridFeatures,
			columns : me.urlPermissionColumns
		});
    	
      	var p3ActionBar =  me.createToolbar([
             me.createModuleAction(this.addUrlPerimssionAction),
             me.createModuleAction(this.removeUrlPerimssionAction),
      	]);
    	
    	var p3 = ExtUtils.panel({
    		title : '操作权限',
    		items : urlPermissionGrid,
    		tbar: p3ActionBar
    	});
    	
    	var cmpPermissionGrid = ExtUtils.arrayGrid({
    		id : 'appRoleForm_cmpPermissionGrid',
    		ownerModule : me,
            columns : me.cmpPermissionColumns
		});
    	
    	var p4 = ExtUtils.panel({
    		title : '数据可见性权限',
    		items : cmpPermissionGrid,
    		tbar: [
		       { xtype: 'button', text: '添加' },
		       { xtype: 'button', text: '删除' }
		    ]
    	});
    	
    	return ExtUtils.fitLayout([p1, p3 , p4 ,p2 ]);
    },
    
    addUrlPerimssion : function(){
    	
    	var me = this;
    	
    	ExtUtils.popup({
			id : 'urlPermission_popup',
			modelName : 'UrlPermission',
			columns : me.urlPermissionColumns,
			itemdblclick : function(view , record , item , e , eOpts){
				me.addUrlPerimssion_callback(record);
			} ,
			okclick : function(record){
				me.addUrlPerimssion_callback(record);
			}
		});
    },
    
    addUrlPerimssion_callback : function(record){
    	var grid = Ext.getCmp('appRoleForm_urlPermissionGrid');
    	ExtUtils.addLine(grid , record);
    },
    
    removeUrlPerimssion : function(){
    	var grid = Ext.getCmp('appRoleForm_urlPermissionGrid');
    	ExtUtils.removeLine(grid);
    }

});
