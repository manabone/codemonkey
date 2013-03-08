
Ext.define('erp.modules.SalesOrderFormModule', {
    extend: 'erp.modules.DocumentFormModule',

    id:'salesOrderFormModule',
    
    winTitle : 'SalesOrder',
    
    modelName : 'SalesOrder',
    
    lineGridId : 'salesOrderLineGrid',
    
    modelFields : ['customer' ,'customer_text', 'paymentDate','totalAmount','warehouse' ,'warehouse_text' ,
                   TO_MODIFY_LINES , TO_DELETE_LINES ,
                   'id','code','name','description','originVersion','creationDate','createdBy','modificationDate','modifiedBy'],
    
    formItems : function(){
    	var p1 = ExtUtils.panel({
    		title : 'basic info',
			items:[
    			{xtype:"textfield",name:"code",fieldLabel:"编码"},
    			{xtype :"searchingselect",name :"customer",config :{model :"CustomerList"},fieldLabel :"Customer" , allowBlank : false},
    			{xtype :"datefield",name :"paymentDate",format :"Y-m-d",fieldLabel :"paymentDate"},
    			{xtype:"textfield",name:"description",fieldLabel:"描述"}
			]
    	});
    	
    	var p2 = ExtUtils.creationInfoPanel();
    	
    	var lineGrid = ExtUtils.arrayGrid({
    		id : this.lineGridId,
    		plugins : [
		           Ext.create('Ext.grid.plugin.CellEditing', {clicksToEdit: 1})
            ],
			columns :  [
   	            {header: 'id',  dataIndex: 'id',  flex: 1},
	 	  		{header: 'item' ,  dataIndex: 'item',  flex: 1 , hidden : true},
	 	  		{header: 'item' ,  dataIndex: 'item_text',  flex: 1 , 
	 	  			editor: {xtype :"searchingselect" , config :{model :"ItemList"} }
	 	  		},
	 	  		{header: 'price' ,  dataIndex: 'price',  flex: 1,
	 	  			editor: {xtype: 'numberfield'}
	 	  		},
	 	  		{header: 'taxRate' ,  dataIndex: 'taxRate',  flex: 1},
	 	  		{header: 'qty' ,  dataIndex: 'qty', flex: 1,
	 	  			editor: {xtype: 'numberfield'} 
	 	  		},
	 	  		{header: 'amount' ,  dataIndex: 'amount',  flex: 1},
	 	  		{header: 'tax' ,  dataIndex: 'tax',  flex: 1}
	 	  	]
		});
    	
    	var p3 = ExtUtils.panel({
    		title : 'Lines',
    		items : lineGrid
    	});
    	
    	return ExtUtils.fitLayout([p1, p3 ,p2 ]);
    }
    
});
