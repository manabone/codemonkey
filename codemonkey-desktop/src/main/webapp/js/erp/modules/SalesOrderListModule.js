
Ext.define('erp.modules.SalesOrderListModule', {
    extend: 'erp.modules.DocumentListModule',

    id:'salesOrderListModule',
    
    requires : ['erp.modules.SalesOrderFormModule'],
    
    hidden : true,
    
    winTitle : 'SalesOrder',
    
    modelName : 'SalesOrderList',
    
    formModuleId : 'salesOrderFormModule',
	
	iconText : 'SalesOrder',
	iconCls : 'icon-grid',
    
	//actions
	createShipmentAction : {
		action : 'createShipment', text: 'createShipment', iconCls : 'icon-reset'
	},
	//end actions
	
	createShipment : function(){
		var record = ExtUtils.getSelected(this.gridId);
		if(record && record.get('id')){
			var shipmentFormModule = this.app.getModule('salesShipmentFormModule');
			shipmentFormModule.createWindow({salesOrder : record.get('id') , callback : function(module){
				module.doAction('createShipment' , function(module , result){
					module.model = module.Model.create(result.data);
					module.getLineGridStore().loadData(module.model.data.lines);
					
				} , {salesOrder : record.get('id')});
			}});
		}
	},
	
	createBbar : function(){
    	var actions = [
			this.createModuleAction(this.createAction),
			this.createModuleAction(this.editAction),
			this.createModuleAction(this.destroyAction),
			this.createModuleAction(this.createShipmentAction)
		];
    	return this.createToolbar(actions);
    },
	
    modelFields : function(){
    	return ['customer' , 'customer_text' , 'totalAmount' , 'warehouse' , 'warehouse_text' , 'status'].concat(ExtUtils.defaultModelFields);
    },
    
    searchForm : function() {
    	return {
    		items : [
				{name  : "JOINS"   , xtype : 'hiddenfield' , fieldLabel : "JOINS"  , value : 'lines_LEFT' },
				{xtype :"searchingselect",name :"lines.item.id",config :{model :"ItemList"},fieldLabel :"item" }
    		]
    	};
    },
    
	gridCols : function() {
		 return ExtUtils.defaultGridCols1.concat([
          {dataIndex : "customer", hidden : true},
          {dataIndex : "customer_text",flex : 1 , header : "customer"},
          {dataIndex : "warehouse", hidden : true},
          {dataIndex : "warehouse_text" , flex : 1 , header : "warehouse"},
          {dataIndex : "status",flex : 1 , header : "status"},
          {dataIndex : "totalAmount",flex : 1 , header : "total amount"},
         ]).concat(ExtUtils.defaultGridCols2);
	} 
	
});
