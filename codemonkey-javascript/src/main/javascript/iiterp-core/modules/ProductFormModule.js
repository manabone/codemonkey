
Ext.define('iiterp-core.modules.ProductFormModule', {
	
	/**继承**/
    extend: 'AM.modules.FormModule',
    
    /**常量**/
    id : 'productFormModule',

    //设计bom
    bomColumns : [
          {header: 'id', dataIndex: 'id' , hidden : true},
          {dataIndex : 'child' , hidden : true},
          {dataIndex : 'sortIndex' , header : '排序' , hidden : false},
          {dataIndex : 'child_code' , header : '零件编码'},
          {dataIndex : 'child_name' , header : '零件名称'},
          {dataIndex : 'child_xmaterial_code' , header : '材质'},
          {dataIndex : 'child_xsize' , header : '规格'},
          {dataIndex : 'hcount' , header : '数量' , editor : {xtype : 'numberfield'}},
          {dataIndex : 'remark' , header : '备注' , editor : {xtype : 'textfield'}}
    ],
    
	/**覆盖父类方法**/
    
    /**自定义页面行为**/
    addBomLineAction : {
		iconCls : 'add' , text: '添加' , action : 'addBomLine'
	},
	
	removeBomLineAction : {
		iconCls:'remove' , text: '删除' , action : 'removeBomLine'
    },
    
    moveUpBomLineAction : {
		iconCls : 'add' , text: '上移' , action : 'moveUpBomLine'
	},
	
	moveDownBomLineAction : {
		iconCls:'remove' , text: '下移' , action : 'moveDownBomLine'
    },
    
    showStructureAction : {
    	iconCls:'add' , text: '产品结构' , action : 'showStructure'
    },
    /**自定义页面行为响应**/
	
	addBomLine : function(){
	    
    	var me = this;
    	
    	ExtUtils.popup({
			id : 'lqtsgroupFormModule_lqthardware_popup_' + Ext.id(),
			modelName : 'lqthardwareList',
			columns : [
	           {header: 'id', dataIndex: 'id' , hidden : true},
	           {dataIndex : 'code' , header : '零件编码'},
	           {dataIndex : 'name' , header : '零件名称'},
	           {dataIndex : 'xmaterial_code' , header : '材质'},
	           {dataIndex : 'xsize' , header : '规格'},
	           {dataIndex : 'remark' , header : '备注'}
			],
			itemdblclick : function(view , record , item , e , eOpts){
				me.addBomLine_callback(record);
			},
			okclick : function(record){
				me.addBomLine_callback(record);
			}
		});
    },
    
    addBomLine_callback : function(record){
    	var me = this;
    	var grid = me.bomGrid;
    	var store = grid.getStore();
    	
        var data = {
    		 child : record.data.id,
    		 child_code	: record.data.code,
    		 child_name : record.data.name,
    		 child_xmaterial_code : record.data.xmaterial_code,
    		 child_xsize : record.data.xsize,
    		 sortIndex : store.getCount()
        };
    	ExtUtils.addLine(grid , data);
    },
	
	removeBomLine : function(){
		var me = this;
    	var grid = me.bomGrid;
		ExtUtils.removeLine(grid);
		
		var store = grid.getStore();
		for(var i = 0 ; i < store.getCount() ; i++){
			var record = store.getAt(i);
			record.set('sortIndex' , i);
		}
		
	},
	
	moveUpBomLine : function(){
		var me = this;
		var record = ExtUtils.getSelected(me.bomGrid.getId());
		var store = me.bomGrid.getStore();
		if(record){
			var sortIndex = record.get('sortIndex');
			
			if(sortIndex > 0){
				var upperRecord = store.findRecord('sortIndex' , sortIndex - 1);
				if(upperRecord){
					upperRecord.set('sortIndex' , sortIndex);
					record.set('sortIndex' , sortIndex - 1);
				}
			}
			
		}
		store.sort({
            property: 'sortIndex',
            direction: 'ASC'
        });
	},
	
	moveDownBomLine : function(){
		var me = this;
		var record = ExtUtils.getSelected(me.bomGrid.getId());
		var store = me.bomGrid.getStore();
		if(record){
			var sortIndex = parseInt(record.get('sortIndex'));
			
			if(sortIndex < store.getCount() - 1){
				var lowerRecord = store.findRecord('sortIndex' , sortIndex + 1);
				if(lowerRecord){
					lowerRecord.set('sortIndex' , sortIndex);
					record.set('sortIndex' , sortIndex + 1);
				}
			}
		}
		store.sort({
            property: 'sortIndex',
            direction: 'ASC'
        });
	},
	
	createBomBar : function(){
    	var actions = [
			this.createModuleAction(this.addBomLineAction),
			this.createModuleAction(this.removeBomLineAction),
			this.createModuleAction(this.moveUpBomLineAction),
			this.createModuleAction(this.moveDownBomLineAction)
		];
    	return this.createToolbar(actions);
	}
});
