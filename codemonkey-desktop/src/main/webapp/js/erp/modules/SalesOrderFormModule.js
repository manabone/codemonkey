
Ext.define('erp.modules.SalesOrderFormModule', {
    extend: 'erp.modules.DocumentFormModule',

    id:'salesOrderFormModule',
    
    winTitle : 'SalesOrder',
    
    modelName : 'SalesOrder',
    
    lineGridId : 'salesOrderLineGrid',
    
    modelFields : function() {
    	return ['customer' ,'customer_text', 'paymentDate', 'status' ,'totalAmount','warehouse' ,'warehouse_text' , 'lines' ,  'toModifyLines' , 'toDeleteLines' ].concat(ExtUtils.defaultModelFields);
    },
    
    formItems : function(){
    	var p1 = ExtUtils.panel({
    		title : 'basic info',
			items:[
    			{xtype:"textfield",name:"code",fieldLabel:"code"},
    			{xtype:"textfield",name:"status",fieldLabel:"status" , readOnly : true},
    			{xtype :"searchingselect",name :"customer",config :{model :"CustomerList"},fieldLabel :"Customer" , allowBlank : false},
    			{xtype :"searchingselect",name :"warehouse",config :{model :"WarehouseList"},fieldLabel :"warehouse" , allowBlank : false},
    			{xtype :"datefield",name :"paymentDate",format :"Y-m-d",fieldLabel :"paymentDate"},
    			{xtype:"textfield",name:"description",fieldLabel:"description"}
			]
    	});
    	
    	var p2 = ExtUtils.creationInfoPanel();
    	
    	var lineGrid = ExtUtils.arrayGrid({
    		id : this.lineGridId,
    		plugins : [
		           Ext.create('Ext.grid.plugin.CellEditing', {
		        	   clicksToEdit: 1
		           })
            ],
			columns :  [
   	            {header: 'id',  dataIndex: 'id',  flex: 1 , hidden : true},
   	            {header: 'item_text' ,  dataIndex: 'item_text' , hidden : true},
   	            
   	            ExtUtils.searchingColumn({
   	            	header : 'item' ,  
   	            	dataIndex : 'item',
   	            	textDataIndex : 'item_text',
   	            	listModel : 'ItemList',
   	            	lineGridId : this.lineGridId
   	            }),
	 	  		
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
