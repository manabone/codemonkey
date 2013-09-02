
Ext.define('test.modules.FooListModule', {
    extend: 'AM.modules.ListModule',

    id:'fooListModule',
    
    requires : ['test.modules.FooFormModule'],
    
    hidden : true,
    
    winTitle : 'Foo',
    
    modelName : 'FooList',
    
    formModuleId : 'fooFormModule',
	
	iconText : 'Foo',
	iconCls : 'icon-grid',
    
    modelFields : function(){
    	return ['fstring','fnumber','fbool','fstatus','fdate','appRole','appUserGroup','id','name','description','version','creationDate','createdBy','modificationDate','modifiedBy'];
    },
    
    searchForm : function() {
    	return {
    		items : [
				{xtype :"searchingselect",name :"appRole.id",config :{model :"AppRoleList"},fieldLabel :"app role" },
				{id : 'fstring'  , name  : 'fstring'   , xtype : 'textfield' , fieldLabel : 'fstring' },
				{id : 'name'  , name  : 'name_Like'   , xtype : 'textfield' , fieldLabel : 'name_Like' },
				{id : 'fnumber'  , name  : 'fnumber_LE'   , xtype : 'textfield' , fieldLabel : 'fnumber_LE' },
				{xtype :"selectfield",name :"fstatus",data :[["ACTIVE","ACTIVE"],["INACTIVE","INACTIVE"]],fieldLabel :"fstatus"}
    		]
    	};
    },
	
	gridCols : function() {
		return [
			{header: 'fstring',  dataIndex: 'fstring',  flex: 1},
			{header: 'fnumber',  dataIndex: 'fnumber',  flex: 1},
			{header: 'fbool',  dataIndex: 'fbool',  flex: 1},
			{header: 'fstatus',  dataIndex: 'fstatus',  flex: 1},
			{header: 'fdate',  dataIndex: 'fdate',  flex: 1},
			{header: 'appRole',  dataIndex: 'appRole',  flex: 1 , hidden : true},
			{header: 'appRole_text',  dataIndex: 'appRole_text',  flex: 1},
			{header: 'appUserGroup',  dataIndex: 'appUserGroup',  flex: 1},
			{header: 'id',  dataIndex: 'id',  flex: 1},
			{header: 'name',  dataIndex: 'name',  flex: 1},
			{header: 'description',  dataIndex: 'description',  flex: 1},
			{header: 'creationDate',  dataIndex: 'creationDate',  flex: 1},
			{header: 'createdBy',  dataIndex: 'createdBy',  flex: 1},
			{header: 'modificationDate',  dataIndex: 'modificationDate',  flex: 1},
			{header: 'modifiedBy',  dataIndex: 'modifiedBy',  flex: 1}
		];
	}
});
