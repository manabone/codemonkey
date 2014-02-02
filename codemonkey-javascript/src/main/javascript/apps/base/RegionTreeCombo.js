Ext.define('AM.base.RegionTreeCombo', {
    extend: 'Ext.form.field.ComboBox',
    
    requires : ['Ext.window.Window'],

    url: '',
    tree: {},
    textProperty: 'text',
    valueProperty: 'id',
    
    alias: 'widget.regionTreeCombo',
    
    trigger2Cls : 'icon_book_edit',
    
    modelName : 'RegionTree',
    
    hValue : null,
    
    modelFields : ['id','parent' , 'name' , 'internalId'],

    initComponent: function () {
        Ext.apply(this, {
            editable: false,
            queryMode: 'local',
            select: Ext.emptyFn
        });

        this.displayField = this.displayField || 'text',
        this.treeid = Ext.String.format('tree-combobox-{0}', Ext.id());
        this.tpl = Ext.String.format('<div id="{0}"></div>', this.treeid);

        if (this.modelName) {
            var me = this;
            
            this.Model = ExtUtils.createModel({
            	modelName : this.modelName , 
            	modelFields : this.modelFields
            });
            
            var store = ExtUtils.treeStore({modelName : me.modelName});
            
            this.tree = Ext.create('Ext.tree.Panel', {
                rootVisible: false,
                autoScroll: true,
                height: 200,
                store: store
            });
            this.tree.on('itemclick', function (view, record) {
                me.setValue(record.data.id);
//                me.setValueField(record);
                me.collapse();
            });
            me.on('expand', function () {
                if (!this.tree.rendered) {
                    this.tree.render(this.treeid);
                }
            });
        }
        this.callParent(arguments);
    },
    
    onTrigger2Click : function(/* Ext.EventObject */ e){
    	var me = this;
		var win = ExtUtils.formWin({
			height: 300,
		    width: 600,
			modal : true,
			formItems : [
			     {xtype:"textfield",name:"id" },
	             {xtype:"regionTreeCombo",name:"parent" , modelName : 'regionTree' , fieldLabel : '父地区'},
	             {xtype:"textfield",name:"name" , fieldLabel : '新地区'}
			],
			
			formButtons : [{
				text: i18n.ok,
				handler : function(){
					var form = this.up('form').getForm();
					var win = this.up('window');
					if (form.isValid()) {
						var params = form.getValues();
						me.save(params , function(){
							me.setValue(params[me.valueProperty]);
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
						me.remove(params , function(){
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
			var r = this.tree.store.getNodeById(v);
	    	if(r){
	    		ExtUtils.record2form({id : r.data.id , parent : r.data.parentId , name : r.data.text} , formId);
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
    
    setValue : function(v) {
		
		var me = this;
    	
    	if(!v){
    		this.clearValue();
    		return;
    	}
    	
    	//set by id
    	var r = this.tree.store.getNodeById(v);
    	if(!r){
    		ExtUtils.reloadStore(this.tree.store , {id : v} , function(records, operation){
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
    			me.setValue(model.data[me.valueProperty]);
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
    
    remove : function(values , callback){
    	var me = this;
    	var model = this.Model.create(values);
    	model.destroy({
    		success: function(model , res) {
    			me.store.load();
    			me.setValue(null);
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
 

//前台代码
//
//Ext.create('TreeComboBox', {
//    renderTo: Ext.getBody(),
//    width: 400,
//    url: '/models/tree-data.json'
//});