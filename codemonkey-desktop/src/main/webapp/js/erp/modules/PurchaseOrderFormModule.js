
Ext.define('erp.modules.PurchaseOrderFormModule', {
    extend: 'erp.modules.DocumentFormModule',

    id:'purchaseOrderFormModule',
    
    winTitle : 'PurchaseOrder',
    
    modelName : 'PurchaseOrder',
    
    lineGridId : 'purchaseOrderLineGrid',
    
    modelFields : function() {
    	return ['vendor' ,'vendor_text', 'paymentDate', 'status' ,'totalAmount','warehouse' ,'warehouse_text' , 'lines' ,  'toModifyLines' , 'toDeleteLines' ].concat(ExtUtils.defaultModelFields);
    },
    
    formItems : function(){
    	var me = this;
    	
    	var p1 = ExtUtils.panel({
    		title : 'basic info',
			items:[
    			{xtype:"textfield",name:"code",fieldLabel:"code"},
    			{xtype:"textfield",name:"status",fieldLabel:"status" , readOnly : true},
    			{xtype :"searchingselect",name :"vendor",config :{model :"VendorList"},fieldLabel :"vendor" , allowBlank : false},
    			{xtype :"searchingselect",name :"warehouse",config :{model :"WarehouseList"},fieldLabel :"warehouse" , allowBlank : false},
    			{xtype :"datefield",name :"paymentDate",format :"Y-m-d",fieldLabel :"paymentDate"},
    			{xtype:"textfield",name:"description",fieldLabel:"description"}
			]
    	});
    	
    	var p2 = ExtUtils.creationInfoPanel();
    	
    	
    	var lineGrid = ExtUtils.arrayGrid({
    		id : this.lineGridId,
    		ownerModule : me,
    		plugins : this.orderLineGridPlugins(),
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
