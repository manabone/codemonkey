
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
    	var me = this;
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
    		modelName : 'SalesOrderLine',
    		ownerModule : me,
    		plugins : this.orderLineGridPlugins(me),
            features: this.orderLineGridFeatures,
			columns : this.orderLineColumns()
		});
    	
    	var p3 = ExtUtils.panel({
    		title : 'Lines',
    		items : lineGrid
    	});
    	
    	return ExtUtils.fitLayout([p1, p3 ,p2 ]);
    }
    
});
