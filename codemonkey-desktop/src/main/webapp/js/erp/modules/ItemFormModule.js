
Ext.define('erp.modules.ItemFormModule', {
    extend: 'AM.modules.FormModule',

    id:'itemFormModule',
    
    requires : ['erp.modules.ItemStockCardListModule'],
    
    hidden : true,
    
    winTitle : 'Item',
    
    modelName : 'Item',
    
    formItems : function(){
    	var p1 = ExtUtils.panel({
    		title : 'basic info',
			items:[
    			{"xtype":"textfield","name":"code","fieldLabel":"编码"},
    			{"xtype":"textfield","name":"name","fieldLabel":"名称"},
    			{"xtype":"textfield","name":"description","fieldLabel":"描述"}
			]
    	});
    	
    	var p2 = ExtUtils.creationInfoPanel();
    	
    	return ExtUtils.fitLayout([p1,p2]);
    },
    
    stockCardListAction : {
		action : 'stockCardList', text: 'StockCard', iconCls : 'icon-update'
	},
    
	createBbar : function(){
    	var actions = [
    	    this.createModuleAction(this.stockCardListAction),          
			this.spacer,
			this.createModuleAction(this.saveAction),
			this.createModuleAction(this.cancelAction)
		];
    	
    	return this.createToolbar(actions);
    },
    
    stockCardList : function(){
    	var stockCardListModule = this.app.getModule('itemStockCardListModule');
    	var itemId = 1;
    	stockCardListModule.createWindow({itemId : itemId});	
    }
});
