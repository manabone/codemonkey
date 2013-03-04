
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
    
    modelFields : ['fstring','fnumber','fbool','fstatus','fdate','appRole','appUserGroup','id','name','description','version','creationDate','createdBy','modificationDate','modifiedBy'],
	
	gridCols : [
			{header: 'fstring',  dataIndex: 'fstring',  flex: 1},
			{header: 'fnumber',  dataIndex: 'fnumber',  flex: 1},
			{header: 'fbool',  dataIndex: 'fbool',  flex: 1},
			{header: 'fstatus',  dataIndex: 'fstatus',  flex: 1},
			{header: 'fdate',  dataIndex: 'fdate',  flex: 1},
			{header: 'appRole',  dataIndex: 'appRole',  flex: 1},
			{header: 'appUserGroup',  dataIndex: 'appUserGroup',  flex: 1},
			{header: 'id',  dataIndex: 'id',  flex: 1},
			{header: 'name',  dataIndex: 'name',  flex: 1},
			{header: 'description',  dataIndex: 'description',  flex: 1},
			{header: 'version',  dataIndex: 'version',  flex: 1},
			{header: 'creationDate',  dataIndex: 'creationDate',  flex: 1},
			{header: 'createdBy',  dataIndex: 'createdBy',  flex: 1},
			{header: 'modificationDate',  dataIndex: 'modificationDate',  flex: 1},
			{header: 'modifiedBy',  dataIndex: 'modifiedBy',  flex: 1}
	]
});
