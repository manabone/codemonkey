
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
    	return ['item','warehouse','qtyOnHand','id','code','name','description','originVersion','creationDate','createdBy','modificationDate','modifiedBy'];
    },
	gridCols :  function(){
		return [{"dataIndex":"item","flex":1,"header":"item"},{"dataIndex":"warehouse","flex":1,"header":"warehouse"},{"dataIndex":"qtyOnHand","flex":1,"header":"qty on hand"},{"dataIndex":"id","flex":1,"header":"自动编号"},{"dataIndex":"code","flex":1,"header":"编码"},{"dataIndex":"name","flex":1,"header":"名称"},{"dataIndex":"description","flex":1,"header":"描述"},{"dataIndex":"originVersion","flex":1,"header":"origin version"},{"dataIndex":"creationDate","flex":1,"header":"创建时间"},{"dataIndex":"createdBy","flex":1,"header":"创建人"},{"dataIndex":"modificationDate","flex":1,"header":"修改时间"},{"dataIndex":"modifiedBy","flex":1,"header":"修改人"}];
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
