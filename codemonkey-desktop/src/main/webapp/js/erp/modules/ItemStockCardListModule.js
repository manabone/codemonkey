
Ext.define('erp.modules.ItemStockCardListModule', {
    extend: 'AM.modules.ListModule',

    id:'itemStockCardListModule',
    
    requires : ['erp.modules.ItemStockCardFormModule'],
    
    hidden : true,
    
    winTitle : 'StockCard',
    
    modelName : 'ItemStockCardList',
    
    formModuleId : 'itemStockCardFormModule',
	
	iconText : 'StockCard',
	iconCls : 'icon-grid',
    
    modelFields : function(){
    	return ['item' , 'item_text' , 'warehouse' , 'warehouse_text' ,'qtyOnHand' , 'qtyOnSalesOrder' , 'qtyOnPurchaseOrder'].concat(ExtUtils.defaultModelFields);
    
    },
	gridCols :  function(){
		
		return [
	          {"dataIndex":"item",hidden:true,"header":"item"},
	          {"dataIndex":"item_text","flex":1,"header":"item"},
	          {"dataIndex":"warehouse",hidden:true,"header":"warehouse"},
	          {"dataIndex":"warehouse_text","flex":1,"header":"warehouse"},
	          {"dataIndex":"qtyOnHand","flex":1,"header":"qty on hand"},
	          {"dataIndex":"qtyOnSalesOrder","flex":1,"header":"qtyOnSalesOrder"},
	          {"dataIndex":"qtyOnPurchaseOrder","flex":1,"header":"qtyOnPurchaseOrder"}
         ].concat(ExtUtils.defaultGridCols2);
	},
	
	searchForm : function() {
    	return {
    		items : [
				{name  : "item.id"   , xtype : 'hiddenfield' , fieldLabel : "item"},
				{name :"warehouse.id" , xtype :"searchingselect", config :{model :"WarehouseList"},fieldLabel :"Warehouse" }
    		]
    	};
    },
	    
	afterWindowCreate : function(){
		ExtUtils.record2form({ 'item.id' : this.itemId}  , this.searchFormId);
		var store = Ext.getCmp(this.gridId).getStore();
		ExtUtils.gridSearch(store , this.searchFormId);
	},
	
	createBbar : function(){
    	return null;
    }
});
