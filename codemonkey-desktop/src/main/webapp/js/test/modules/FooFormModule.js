
Ext.define('test.modules.FooFormModule', {
    extend: 'AM.modules.FormModule',

    id:'fooFormModule',
    
    hidden : true,
    
    winTitle : 'Foo',
    
    modelName : 'Foo',
    
    modelFields : function(){
    	return ['fstring','fnumber','fbool','fstatus','fdate','appRole','appUserGroup','id','name','description','originVersion','creationDate','createdBy','modificationDate','modifiedBy' , 'appRoles'];
    },
    
    afterModelLoad : function(model){
    	this.appRolesGrid.getStore().loadData(model.data.appRoles);
    },
    
	formItems : function(){
		
		var col1 =  [
			{allowBlank:false,xtype:"treecombo",name:"tree", modelName : 'tree' , fieldLabel :"tree"},
			{allowBlank :false,xtype :"textfield",name :"fstring",fieldLabel :"fstring"},
			{xtype :"textfield", name :"name",fieldLabel :"name" , allowBlank : false},
			{xtype :"textfield", name :"description",fieldLabel :"description"},
			{xtype :"textfield",name :"createdBy",fieldLabel :"created by"},
			{xtype :"textfield",name :"modifiedBy",fieldLabel :"modified by"},
			{xtype :"numberfield",minValue :0,name :"fnumber",fieldLabel :"fnumber",maxValue :10 , allowBlank : false},
			{xtype :"numberfield",name :"id",fieldLabel :"id"}    
	    ];
		
		var col2 = [
			{xtype :"numberfield",name :"originVersion",fieldLabel :"originVersion"},
			{xtype :"datefield",name :"fdate",format :"Y-m-d",fieldLabel :"fdate"},
			{xtype :"datefield",name :"creationDate",format :"Y-m-d",fieldLabel :"creation date" , allowBlank : false},
			{xtype :"datefield",name :"modificationDate",format :"Y-m-d",fieldLabel :"modification date"},
			{xtype :"checkbox",name :"fbool",fieldLabel :"fbool"},
			{xtype :"selectfield",name :"fstatus", model : 'Status' ,fieldLabel :"fstatus" , allowBlank : false},
			{xtype :"searchingselect",name :"appRole",config :{model :"AppRoleList"},fieldLabel :"app role" , allowBlank : false},
			{xtype :"searchingselect",name :"appUserGroup",config :{model :"AppUserGroup"},fieldLabel :"app user group"}       
	    ];
		
		var grid = ExtUtils.arrayGrid({
			columns :  [
   	            {header: 'id',  dataIndex: 'id',  flex: 1},
	 	  		{header: 'name' ,  dataIndex: 'name',  flex: 1},
	 	  		{header: 'description' ,  dataIndex: 'description',  flex: 1}
	 	  	]
		});
		this.appRolesGrid = grid;
		
		return [ExtUtils.columnLayout([col1 , col2]), grid];

	}
   
});
