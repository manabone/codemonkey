Ext.define('iiterp-core.cmp.BsUnioncodeInfoField', {
    
	extend : 'Ext.form.field.ComboBox',
	
    requires : ['Ext.window.Window'],
    
    alias: 'widget.bsunioncodeinfofield',
    
    trigger1Cls: Ext.baseCSSPrefix + 'form-clear-trigger',

    trigger2Cls : 'icon_book_edit',
    
    editable : true,
    
    minChars : 1,
    
    valueField : 'id',
    
    displayField : 'code',
    
    modelName : 'BsUnioncodeInfo',
    
    hValue : null,
    
    forChinese : true,
    
    modelFields : ['id' , 'code' , 'name' , 'description' , 'unioncodeField'],
    
    unioncodeField : null,
    
    listConfig: {
        getInnerTpl: function() {
            return '{code}';
        }
    },
    
    initComponent: function() {
    	var me = this;
    	
    	this.Model = ExtUtils.createModel({
        	modelName : this.modelName , 
        	modelFields : this.modelFields
        });

    	this.store = ExtUtils.ajaxStore({
    		model: this.modelName,
    		autoLoad : false,
    		listeners : {
    			'beforeload' : function( cmp , operation, eOpts ){
    				if(operation.params && me.forChinese ){
    					if(!ExtUtils.isChinese(operation.params.query)){
    						return false;
    					}else{
    						operation.params.query = encodeURI(operation.params.query);
    					}
    				}
    				var proxy = cmp.getProxy();
    	    		Ext.apply(proxy.extraParams , {unioncodeField : me.unioncodeField});
    			}
    		}
    	});
		
		me.emptyText = i18n.select;
		me.listConfig = {width : 150};
		me.matchFieldWidth = false;
		
		this.on('focus' , function(cmp , The, eOpts ){
			if(!cmp.readOnly && !cmp.isDisabled()){
				ExtUtils.reloadStore(cmp.store , {unioncodeField : me.unioncodeField} , 
						function(records, operation){
							cmp.expand();
						}
				);
			}
		});
		
		me.callParent(arguments);
    },
    
    onTrigger1Click : function(){
    	this.setValue('');
    	this.setRawValue('');
    },
     
    onTrigger2Click : function(/* Ext.EventObject */ e){
    	var me = this;
		var win = ExtUtils.formWin({
			height: 300,
		    width: 600,
			modal : true,
			formItems : [
	             {xtype:"textfield",name:"id" , hidden : true },
	             {xtype:"textfield",name:"unioncodeField" , hidden : true , value : me.unioncodeField},
	             {xtype:"textfield",name:"code", fieldLabel : i18n.code , allowBlank : false},
	             {xtype:"textfield",name:"name", fieldLabel : i18n.name},
	             {xtype:"textarea",name:"description", fieldLabel : i18n.description}
			],
			
			formButtons : [{
				text: i18n.ok,
				handler : function(){
					var form = this.up('form').getForm();
					var win = this.up('window');
					if (form.isValid()) {
						var params = form.getValues();
						me.save(params , function(){
							win.close();
						});
						
					}
			    }
			},
			{
				text: i18n.destroy,
				handler : function(){
					var form = this.up('form').getForm();
					var win = this.up('window');
					if (form.isValid()) {
						var params = form.getValues();
						me.destroy(params , function(){
							win.close();
						});
						
					}
			    }
			}
			]
		});
		
		var formId = win.down('form').id;
		
		if(me.getValue()){
			var v = me.getValue();
			var r = this.store.getById(v);
	    	if(r){
	    		ExtUtils.record2form(r.data , formId);
	    	}
		}
		
    },
    
    clearValue : function(){
    	var me = this;

        if (me.getValue()) {
            me.setRawValue('');
            me.hValue = '';
        }
    },
    
    getValue : function(){
    	return this.hValue;
    },
    
    setValue: function(v) {
    	var me = this;
    	
    	if(!v){
    		this.clearValue();
    		return;
    	}
    	
    	//set value by the option selected
    	if(Ext.isArray(v) && v[0]){
    		var r = v[0];
    		me.hValue = r.get(me.valueField);
    		me.setRawValue(r.get(me.displayField));
    		return;
    	}
    	
    	//set by id
    	var r = this.store.getById(v);
    	if(!r){
    		ExtUtils.reloadStore(this.store , {id : v} , function(records, operation){
    			if(records && records[0]){
					me.hValue = v;
    	    		me.setRawValue(records[0].get(me.displayField));
				}else{
					me.hValue = '';
    	    		me.setRawValue('');
				}
    		});
    	}else{
    		me.hValue = v;
    		me.setRawValue(r.get(me.displayField));
    	}
    },
    
    save : function(values , callback){
    	var me = this;
    	var model = this.Model.create(values);
    	model.save({
    		success: function(model , res) {
    			me.store.load();
    			me.setValue(model.data[me.valueField]);
    			me.setRawValue(model.data[me.displayField]);
    			if(callback){
    				callback();
    			}
    		},
    		failure: function(rec, op) {
    			var errors = op.request.scope.reader.jsonData["errorFields"];
    			var errorKey = op.request.scope.reader.jsonData["errorKey"];
    			var data = op.request.scope.reader.jsonData["data"];
    			var errorMsg = op.request.scope.reader.jsonData["errorMsg"];
    			ExtUtils.handleError(errorKey , errorMsg , errors , data , me);
			}
    	});
    },
    
    destroy : function(values , callback){
    	var me = this;
    	var model = this.Model.create(values);
    	model.destroy({
    		success: function(model , res) {
    			me.store.load();
    			me.setValue('');
    			me.setRawValue('');
    			if(callback){
    				callback();
    			}
    		},
    		failure: function(rec, op) {
    			var errors = op.request.scope.reader.jsonData["errorFields"];
    			var errorKey = op.request.scope.reader.jsonData["errorKey"];
    			var data = op.request.scope.reader.jsonData["data"];
    			var errorMsg = op.request.scope.reader.jsonData["errorMsg"];
    			ExtUtils.handleError(errorKey , errorMsg , errors , data , me);
			}
    	});
    }
   
});