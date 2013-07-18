
Ext.define('AM.modules.AppRoleFormModule', {
    extend: 'AM.modules.FormModule',

    id:'appRoleFormModule',
    
    hidden : true,
    
    winTitle : 'App Role',
    
    modelName : 'AppRole',
    
    modelFields : function() {
    	return ['urlPermissions' , 'cmpPermissions'].concat(ExtUtils.defaultModelFields);
    },
    
    // actions 
    addUrlPerimssionAction : {
		iconCls : 'add' , text: '添加' , action : 'addUrlPerimssion'
	},
	
	removeUrlPerimssionAction : {
		iconCls:'remove' , text: '删除' , action : 'removeUrlPerimssion'
    },
    
    addCmpPerimssionAction : {
		iconCls : 'add' , text: '添加' , action : 'addCmpPerimssion'
	},
	
	removeCmpPerimssionAction : {
		iconCls:'remove' , text: '删除' , action : 'removeCmpPerimssion'
    },
    //end actions
    
    urlPermissionColumns : [
		{header: '权限编码' ,  dataIndex: 'permission' },
		{header: '描述' ,  dataIndex: 'description', flex: 1},
		{header: '请求路径' ,  dataIndex: 'url',  flex: 1},
		{header: '请求类型' ,  dataIndex: 'requestType', flex: 1}
    ],
    
    cmpPermissionColumns : [
        {dataIndex: 'id' , hidden : true},
        {dataIndex: 'component_code' , hidden : true},
        ExtUtils.searchingColumn({
        	header : '控件编码' ,  
        	dataIndex : 'component',
        	textDataIndex : 'component_code',
        	listModel : 'SecurityComponent',
        	lineGridId : 'appRoleForm_cmpPermissionGrid',
        	itemdblclick : function(record , cmp , r){
        		r.set('component' , record.get('id'));
        		r.set('component_code' , record.get('code'));
        		r.set('component_type' , record.get('cmpType'));
        		r.set('component_description' , record.get('description'));
        	},
        	cols : [
        	     {header: 'id', dataIndex: 'id' , hidden : true},
        	     {dataIndex:"code",flex:1,header : i18n.code},
                 {header: '控件类型' ,  dataIndex: 'cmpType', flex: 1},
                 {header: '描述' ,  dataIndex: 'description', flex: 1}
            ]
        }),
        {header: '控件类型' , dataIndex: 'component_type' },
        {header: '描述' , dataIndex: 'component_description'},
  		{header: '操作权限' ,  dataIndex: 'cmpPermissionType' , 
        	editor: {xtype :"selectfield" , model : 'CmpPermissionType'}
        }
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
    		modelName : 'UrlPermission',
    		ownerModule : me,
			columns : me.urlPermissionColumns
		});
    	
      	var p3ActionBar =  me.createToolbar([
             me.createModuleAction(this.addUrlPerimssionAction),
             me.createModuleAction(this.removeUrlPerimssionAction)
      	]);
    	
    	var p3 = ExtUtils.panel({
    		title : '操作权限',
    		collapsible : true,
    		items : urlPermissionGrid,
    		tbar: p3ActionBar
    	});
    	
    	var cmpPermissionGrid = ExtUtils.arrayGrid({
    		id : 'appRoleForm_cmpPermissionGrid',
    		modelName : 'CmpPermission',
    		ownerModule : me,
            columns : me.cmpPermissionColumns,
            plugins : [
               Ext.create('Ext.grid.plugin.CellEditing', {clicksToEdit: 1})
 	    	]
		});
    	
    	var p4ActionBar =  me.createToolbar([
             me.createModuleAction(this.addCmpPerimssionAction),
             me.createModuleAction(this.removeCmpPerimssionAction)
      	]);
    	
    	var p4 = ExtUtils.panel({
    		title : '控件可见性权限',
    		collapsible : true,
    		items : cmpPermissionGrid,
    		tbar: p4ActionBar
    	});
    	
    	return ExtUtils.fitLayout([p1, p3 , p4 ,p2 ]);
    },
    
    addUrlPerimssion : function(){
    	
    	var me = this;
    	
    	ExtUtils.popup({
			id : 'urlPermission',
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
    	ExtUtils.addLine(grid , record.data);
    },
    
    removeUrlPerimssion : function(){
    	var grid = Ext.getCmp('appRoleForm_urlPermissionGrid');
    	ExtUtils.removeLine(grid);
    },
    
    addCmpPerimssion : function(){
    	var grid = Ext.getCmp('appRoleForm_cmpPermissionGrid');
    	ExtUtils.addLine(grid);
    },
    
    removeCmpPerimssion : function(){
    	var grid = Ext.getCmp('appRoleForm_cmpPermissionGrid');
    	ExtUtils.removeLine(grid);
    },
    
    beforeSave : function(values){
    	var urlPermissions = ExtUtils.getAllData(Ext.getCmp('appRoleForm_urlPermissionGrid'));
    	var cmpPermissions = ExtUtils.getAllData(Ext.getCmp('appRoleForm_cmpPermissionGrid'));
    	Ext.apply(values , {
    		urlPermissions : urlPermissions || [],
    		cmpPermissions : cmpPermissions || []
    	});
    },
    
    afterModelLoad : function(model){
 	   Ext.getCmp('appRoleForm_urlPermissionGrid').getStore().loadData(model.data.urlPermissions);
 	   Ext.getCmp('appRoleForm_cmpPermissionGrid').getStore().loadData(model.data.cmpPermissions);
    }

});
