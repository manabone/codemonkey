
Ext.define('erp.modules.ItemTransactionListModule', {
    extend: 'AM.modules.ListModule',

    id:'itemTransactionListModule',
    
    hidden : true,
    
    winTitle : 'Transaction',
    
    modelName : 'ItemTransactionList',
    
    formModuleId : '',
	
	iconText : 'Transaction',
	iconCls : 'icon-grid',
    
    modelFields : function(){
    	return ['item' , 'item_text' , 'warehouse' , 'warehouse_text' ,'qty' ].concat(ExtUtils.defaultModelFields);
    },
	gridCols : function(){
		return [
	          {"dataIndex":"item",hidden:true,"header":"item"},
	          {"dataIndex":"item_text","flex":1,"header":"item"},
	          {"dataIndex":"warehouse",hidden:true,"header":"warehouse"},
	          {"dataIndex":"warehouse_text","flex":1,"header":"warehouse"},
	          {"dataIndex":"qty","flex":1,"header":"qty"},
         ].concat(ExtUtils.defaultGridCols2);
	},
	
	searchForm : function() {
    	return {
    		items : [
				{name  : "item.id"   , xtype : 'hiddenfield' , fieldLabel : "item"}
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
