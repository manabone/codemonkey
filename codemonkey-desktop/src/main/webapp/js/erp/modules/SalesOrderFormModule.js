
Ext.define('erp.modules.SalesOrderFormModule', {
    extend: 'erp.modules.DocumentFormModule',

    id:'salesOrderFormModule',
    
    hidden : true,
    
    winTitle : 'SalesOrder',
    
    modelName : 'SalesOrder',
    
    modelFields : ['customer' ,'customer_text', 'paymentDate','totalAmount','warehouse' ,'warehouse_text' , 'lines',
                   'id','code','name','description','originVersion','creationDate','createdBy','modificationDate','modifiedBy'],
    
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
    	
    	var lineGrid = ExtUtils.arrayGrid({
			columns :  [
   	            {header: 'id',  dataIndex: 'id',  flex: 1},
	 	  		{header: 'item' ,  dataIndex: 'item',  flex: 1 , hidden : true},
	 	  		{header: 'item' ,  dataIndex: 'item_text',  flex: 1},
	 	  		{header: 'price' ,  dataIndex: 'description',  flex: 1},
	 	  		{header: 'taxRate' ,  dataIndex: 'taxRate',  flex: 1},
	 	  		{header: 'qty' ,  dataIndex: 'qty',  flex: 1},
	 	  		{header: 'amount' ,  dataIndex: 'amount',  flex: 1},
	 	  		{header: 'tax' ,  dataIndex: 'tax',  flex: 1}
	 	  	]
		});
    	
    	return ExtUtils.fitLayout([p1,p2 , lineGrid]);
    }
});
