
Ext.define('iiterp-core.modules.LqtprodbuyTreeModule', {
	/**继承**/
	extend: 'AM.modules.AbsTreeGridModule',

	/**常量**/
    id:'lqthardwareTreeModule',
    
    winTitle : '零件结构',
    
    modelName : 'LqtprodbuyTree',

//    modelName : 'treeGrid',
    
    /**自定义常量**/
   
    /**实现父类方法**/
    getColumns : function(){
    	return [{
					xtype : 'treecolumn', // this is so we know which column will show
					text : '零件编码',
					sortable : true,
					dataIndex : 'child_code'
			  },
	          {dataIndex : 'child_name' , header : '零件名称'},
	          {dataIndex : 'child_xmaterial_code' , header : '材质'},
	          {dataIndex : 'child_xsize' , header : '规格'},
	          {dataIndex : 'hcount' , header : '数量'},
	          {dataIndex : 'remark' , header : '备注'},
	          {header: 'id', dataIndex: 'id' , hidden : true},
	          {dataIndex : 'child' , hidden : true},
	          {dataIndex : 'sortIndex' , header : '排序' , hidden : true}
    	];
    	
//    	return [{
//            xtype: 'treecolumn', //this is so we know which column will show the tree
//            text: 'id',
//            flex: 2,
//            sortable: true,
//            dataIndex: 'id'
//        },{
//            //we must use the templateheader component so we can use a custom tpl
//            text: 'text',
//            flex: 1,
//            sortable: true,
//            dataIndex: 'text',
//            align: 'center'
//          
//        },{
//            text: 'name',
//            flex: 1,
//            dataIndex: 'name',
//            sortable: true
//        }];
    },
    
    afterTreeLoad : function(records, operation){
    	var me = this;
    	me.store.sort({
            property: 'sortIndex',
            direction: 'ASC'
        });
    }
	/**覆盖父类方法**/
   
    /**自定义页面行为**/
    
    /**自定义页面行为响应**/
});
