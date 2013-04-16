
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
    	return ['item' , 'item_text' , 'warehouse' , 'warehouse_text' ,'qtyOnHand'].concat(ExtUtils.defaultModelFields);
    
    },
	gridCols :  function(){
		
		return [
	          {"dataIndex":"item",hidden:true,"header":"item"},
	          {"dataIndex":"item_text","flex":1,"header":"item"},
	          {"dataIndex":"warehouse",hidden:true,"header":"warehouse"},
	          {"dataIndex":"warehouse_text","flex":1,"header":"warehouse"},
	          {"dataIndex":"qtyOnHand","flex":1,"header":"qty on hand"}
         ].concat(ExtUtils.defaultGridCols2);
	},
	
	searchForm : function() {
    	return {
    		items : [
				{name  : "item.id"   , xtype : 'hiddenfield' , fieldLabel : "item"  , value : ''},
				{xtype :"searchingselect",name :"warehouse.id",config :{model :"WarehouseList"},fieldLabel :"Warehouse" }
    		]
    	};
    },
	    
	afterWindowCreate : function(){
		ExtUtils.record2form({itemId : this.itemId} , this.searchFormId);
		var store = Ext.getCmp(this.gridId).getStore();
		ExtUtils.gridSearch(store , this.searchFormId);
	},
	
	createBbar : function(){
    	return null;
    }
});
