
Ext.define('erp.modules.ItemStockCardFormModule', {
    extend: 'AM.modules.FormModule',

    id:'itemStockCardFormModule',
    
    hidden : true,
    
    winTitle : 'StockCard',
    
    modelName : 'ItemStockCard',
    
    modelFields : function(){
    	return  ['item' , 'item_text' , 'warehouse' , 'warehouse_text' ,'qtyOnHand' , 'qtyOnSalesOrder' , 'qtyOnPurchaseOrder'].concat(ExtUtils.defaultModelFields);
    },
    
    formItems : function(){
    
    	var p1 = ExtUtils.panel({
    		title : 'basic info',
			items:[
    			{xtype:"searchingselect",name:"item","config":{"model":"ItemList"},fieldLabel:"item", readOnly : true},
    			{xtype :"searchingselect",name :"warehouse",config :{model :"WarehouseList"},fieldLabel :"warehouse" , readOnly : true},
    			{xtype:"numberfield",name:"qtyOnHand",fieldLabel:"qty on hand" , readOnly : true},
    			{xtype:"numberfield",name:"qtyOnSalesOrder",fieldLabel:"qtyOnSalesOrder" , readOnly : true},
    			{xtype:"numberfield",name:"qtyOnPurchaseOrder",fieldLabel:"qtyOnPurchaseOrder" , readOnly : true}
			]
    	});
    	
    	var p2 = ExtUtils.creationInfoPanel();
    	
    	return ExtUtils.fitLayout([p1 , p2]);
    
    },
	
	createBbar : function(){
    	return null;
    }
    
});
