
Ext.define('erp.modules.SalesOrderFormModule', {
    extend: 'AM.modules.FormModule',

    id:'salesOrderFormModule',
    
    hidden : true,
    
    winTitle : 'SalesOrder',
    
    modelName : 'SalesOrder',
    
    modelFields : ['customer' ,'customer_text', 'paymentDate','totalAmount','warehouse' ,'warehouse_text' ,'id','code','name','description','originVersion','creationDate','createdBy','modificationDate','modifiedBy'],
    
    formItems : function(){
    	var p1 = ExtUtils.panel({
    		title : 'basic info',
			items:[
    			{"xtype":"textfield","name":"code","fieldLabel":"编码"},
    			{xtype :"searchingselect",name :"customer",config :{model :"CustomerList"},fieldLabel :"Cuustomer" , allowBlank : false},
    			{xtype :"datefield",name :"paymentDate",format :"Y-m-d",fieldLabel :"paymentDate"},
    			{"xtype":"textfield","name":"description","fieldLabel":"描述"}
			]
    	});
    	
    	var p2 = ExtUtils.creationInfoPanel();
    	
    	return ExtUtils.fitLayout([p1,p2]);
    }
});
