Ext.define('AM.base.SearchingSelectButton', {
//    extend:'Ext.form.field.ComboBox',
    
	extend : 'Ext.button.Button',
	
    requires : ['Ext.window.Window'],
    
    alias: 'widget.searchingselectbutton',
    
    targetGridId : null,
    
    popupWin : null,
    
	defaultCols : [ 
       {header : 'id', hidden : false, dataIndex : 'id', flex : 1 }, 
       {header : 'name', dataIndex : 'name', flex : 1 }, 
       {header : 'description', dataIndex : 'description', flex : 1}
	],
	
    Model : null,
    
    store : null,
    
    getValue : function(){
    	var store = Ext.getCmp(this.targetGridId).getStore();
    	var data = [];
    	var range = store.getRange();
    	for(var i = 0 ; i < range.length ; i++){
    		data.push(range[i].data);
    	}
    	return data;
    },
    
    setValue: function(records) {
    	if(!records){
    		return;
    	}
    	store = Ext.getCmp(this.targetGridId).getStore();
    	for(var i = 0 ; i < records.length ; i++ ){
    		var r = store.getById(records[i].id);
    		if(!r){
    			store.add(records[i]);
    		}else{
    			r.set(records[i]);
    		}
    	}
    },
    
    removeSelected : function(){
    	ExtUtils.removeFromGrid(Ext.getCmp(this.targetGridId));
    },
    
    initComponent: function() {
    	var me = this;
    	
    	me.targetGridId = me.config.targetGridId;
    	
    	var modelFields = [];
    	
    	var gridCols = Ext.apply([] , this.config.cols , this.defaultCols);
    	this.gridCols = gridCols;
    	
    	for(var i = 0 ; i < gridCols.length ; i++ ){
    		modelFields.push(gridCols[i].dataIndex);
    	}
    	this.modelFields = modelFields;
    	
    	Ext.define('Model', {
    	    extend: 'Ext.data.Model',
    	    fields: modelFields
    	});
    	
    	var proxy = ExtUtils.proxy(NS.uncapitalize(this.config.model));
    	
    	var store = Ext.create('Ext.data.Store', {
            autoLoad: false,
            model: 'Model',
            proxy: proxy
        });
    	
    	me.store = store;
    	
    },
    
    handler : function(){
        var me = this;
        me.store.load();
		ExtUtils.popup({
			id : me.id,
			store : me.store,
			columns : me.gridCols,
			selType : 'checkboxmodel',
			selModel: {
				mode : 'MULTI'
			},
			okclick : function(records){
				me.okclick(records);
			}
		});
       
    },
	okclick : function(records){
		if(records){
			var me = this;
			var store = Ext.getCmp(this.targetGridId).getStore();
			
			store.add(records);
			Ext.callback(me.config.success , this , [records ,  me]);
		} 
	}
	
});