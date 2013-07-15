
Ext.define('AM.modules.AppRoleFormModule', {
    extend: 'AM.modules.FormModule',

    id:'appRoleFormModule',
    
    hidden : true,
    
    winTitle : 'App Role',
    
    modelName : 'AppRole',
    
    formItems : function(){
    	var me = this;
    	var p1 = ExtUtils.panel({
    		title : 'basic info',
			items:[
    			{xtype:"textfield",name:"code",fieldLabel:"编码"},
    			{xtype:"textfield",name:"name",fieldLabel:"名称"},
    			{xtype:"textfield",name:"description",fieldLabel:"描述"}
			]
    	});
    	
    	var p2 = ExtUtils.creationInfoPanel();
    	
    	var urlPermissionGrid = ExtUtils.arrayGrid({
    		id : this.lineGridId,
    		ownerModule : me,
            features: this.orderLineGridFeatures,
			columns : [
    	        {header: '权限编码' ,  dataIndex: 'permission' },
    	  		{header: '控件ID' ,  dataIndex: 'price',  flex: 1},
    	  		{header: '请求路径' ,  dataIndex: 'url',  flex: 1},
    	  		{header: '请求类型' ,  dataIndex: 'requestType', flex: 1}
		   ]
		});
    	
    	var p3 = ExtUtils.panel({
    		title : '操作权限',
    		items : urlPermissionGrid,
    		tbar: [
		       { xtype: 'button', text: '添加' },
		       { xtype: 'button', text: '删除' }
		    ]
    	});
    	
    	var cmpPermissionGrid = ExtUtils.arrayGrid({
    		id : this.lineGridId,
    		ownerModule : me,
            features: this.orderLineGridFeatures,
            columns : [
    	        {header: '权限编码' ,  dataIndex: 'permission'},
    	  		{header: '控件ID' ,  dataIndex: 'price',  flex: 1},
    	  		{header: '控件类型' ,  dataIndex: 'cmpType',  flex: 1},
    	  		{header: '操作权限' ,  dataIndex: 'cmpPermissionType', flex: 1}
		    ]
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
    }
});
