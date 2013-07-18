Ext.define('AM.base.SearchingSelect', {
//    extend:'Ext.form.field.ComboBox',
    
	extend : 'Ext.form.TwinTriggerField',
	
    requires : ['Ext.window.Window'],
    
    trigger1Cls: Ext.baseCSSPrefix + 'form-clear-trigger',

    trigger2Cls: Ext.baseCSSPrefix + 'form-search-trigger',
    
    alias: 'widget.searchingselect',
    
    editable : false,
    
    valueField : 'id',
    
    displayField : 'description',
    
    popupWin : null,
    
	defaultCols : [ 
       {header : 'id', hidden : false, dataIndex : 'id', flex : 1 }, 
       {header : 'name', dataIndex : 'name', flex : 1 }, 
       {header : 'description', dataIndex : 'description', flex : 1}
	],
	
    hValue : null,
    
    Model : null,
    
    getValue : function(){
    	return this.hValue;
    },
    
    setValue: function(v) {
    	
    	if(!v){
    		this.clearValue();
    		return;
    	}
    	
    	var me = this;
    	
    	var r = this.store.getById(v);
    	if(!r){
    		var proxy = this.store.getProxy();
    		proxy.extraParams = {id : v};
    		this.store.load({
    			callback: function(records, operation, success){
    				if(records && records[0]){
    					me.hValue = v;
        	    		me.setRawValue(records[0].get(me.displayField));
        	    		if(me.triggerCell && me.triggerCell.item(0)){
        	    			me.triggerCell.item(0).setDisplayed(true);
        	    		}
    				}else{
    					me.hValue = '';
        	    		me.setRawValue('');
        	    		if(me.triggerCell && me.triggerCell.item(0)){
        	    			me.triggerCell.item(0).setDisplayed(false);
        	    		}
    				}
    				
    			}
    		});
    	}else{
    		me.hValue = v;
    		me.setRawValue(r.get(me.displayField));
    		if(me.triggerCell && me.triggerCell.item(0)){
    			me.triggerCell.item(0).setDisplayed(true);
    		}
    	}
    },
    
    initComponent: function() {
    	var me = this;
    	
    	var modelFields = [];
    	
    	var gridCols = Ext.apply([] , this.config.cols , this.defaultCols);
    	this.gridCols = gridCols;
    	
    	for(var i = 0 ; i < gridCols.length ; i++ ){
    		modelFields.push(gridCols[i].dataIndex);
    	}
    	this.modelFields = modelFields;
    	
    	Ext.define(this.config.model , {
    	    extend: 'Ext.data.Model',
    	    fields: modelFields
    	});
    	
    	var proxy = ExtUtils.proxy(NS.uncapitalize(this.config.model));
    	
    	var store = Ext.create('Ext.data.Store', {
            autoLoad: false,
            model: this.config.model,
            proxy: proxy
        });
    	
    	me.store = store;
    	
    	if(this.config.initData){
    		this.config.initData(cmp);
    	}
    	
    	me.on('afterrender' , function( /*Ext.Component*/ cmp , /*Object*/ eOpts ){
    		cmp.triggerCell.item(0).setDisplayed(false);
    	});
    	
    	if(this.allowBlank === false && this.fieldLabel){
			this.fieldLabel += '<font color=red>*</font>';
		}
    	
    },
    
    clearValue : function(){
    	 var me = this;

         if (me.getValue()) {
//             me.setValue('');
             me.setRawValue('');
             me.hValue = '';
             me.triggerCell.item(0).setDisplayed(false);
             me.updateLayout();
         }
    },
    
    onTrigger1Click : function(){
       this.clearValue();
    },
    
    onTrigger2Click : function(/* Ext.EventObject */ e){
		var me = this;
		var proxy = this.store.getProxy();
		proxy.extraParams = {};
		me.store.load();
		ExtUtils.popup({
			id : me.id,
			modelName : me.config.model,
			columns : me.gridCols,
			itemdblclick : function(view , record , item , e , eOpts){
				me.itemdblclick(view , record , item , e , eOpts);
			} ,
			okclick : function(record){
				me.okclick(record);
			}
		});
	},
	
	itemdblclick : function(/* Ext.view.View*/ view ,/* Ext.data.Model*/ record, /*HTMLElement*/ item,/* Number*/ index, /*Ext.EventObject*/ e, /*Object*/ eOpts ){
		var me = this;
		me.setValue(record.get(me.valueField));
		me.setRawValue(record.get(me.displayField));
		Ext.callback(me.config.success , this , [record ,  me]);
//		Ext.getCmp(me.id + 'popupWin').close();
		me.triggerCell.item(0).setDisplayed(true);
	},
	
	okclick : function(record){
		var me = this;
		me.setValue(record.get(me.valueField));
		me.setRawValue(record.get(me.displayField));
		me.triggerCell.item(0).setDisplayed(true);
		Ext.callback(me.config.success , this , [record ,  me]);
	}
	
});