
Ext.define('erp.modules.SalesOrderLineFormModule', {
    extend: 'AM.modules.FormModule',

    id:'salesOrderLineFormModule',
    
    hidden : true,
    
    winTitle : 'SalesOrderLine',
    
    modelName : 'SalesOrderLine',
    
    modelFields : ['salesOrder','price','item','qty','id','code','name','description','originVersion','creationDate','createdBy','modificationDate','modifiedBy'],
    
    formItems : function(){
		return
		[{"xtype":"textfield","name":"code","fieldLabel":"编码"},{"xtype":"textfield","name":"name","fieldLabel":"名称"},{"xtype":"textfield","name":"description","fieldLabel":"描述"},{"xtype":"textfield","name":"createdBy","fieldLabel":"创建人"},{"xtype":"textfield","name":"modifiedBy","fieldLabel":"修改人"},{"xtype":"numberfield","name":"price","fieldLabel":"price"},{"xtype":"numberfield","name":"qty","fieldLabel":"qty"},{"xtype":"numberfield","name":"id","fieldLabel":"自动编号"},{"xtype":"numberfield","name":"originVersion","fieldLabel":"origin version"},{"xtype":"datefield","name":"creationDate","format":"Y-m-d","fieldLabel":"创建时间"},{"xtype":"datefield","name":"modificationDate","format":"Y-m-d","fieldLabel":"修改时间"},{"xtype":"searchingselect","name":"salesOrder","config":{"model":"SalesOrderList"},"fieldLabel":"sales order"},{"xtype":"searchingselect","name":"item","config":{"model":"ItemList"},"fieldLabel":"item"}];
    }
});
