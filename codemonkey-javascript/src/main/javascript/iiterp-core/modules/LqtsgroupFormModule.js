
Ext.define('iiterp-core.modules.LqtsgroupFormModule', {
	/**继承**/
    extend: 'iiterp-core.modules.ProductFormModule',

	/**常量**/
    id:'lqtsgroupFormModule',
    
    winTitle : '添加组合',
    
    modelName : 'Lqtsgroup',
    
    /**自定义常量**/
    baseInfoPanel_col1 : [
		{xtype:"textfield",name:"code",fieldLabel:"产品编码", allowBlank:false},
		{xtype:"bsunioncodeinfofield",name:"guse",unioncodeField:'guse',fieldLabel:"用途"},
		{xtype:"searchingselect",name:"shperson",config:{model:"HyAppUserList"},fieldLabel:"审核人"},
		{xtype:"datefield",name:"shDate",format:"Y-m-d",fieldLabel:"审核时间"},
		{xtype:"bsunioncodeinfofield",name:"gkind",unioncodeField:'gkind',fieldLabel:"分类"}
				
    ],
    baseInfoPanel_col2 : [
		{xtype:"textfield",name:"name",fieldLabel:"产品名称", allowBlank:false},
		{xtype:"textfield",name:"gstyle",fieldLabel:"型号"},
		{xtype:"searchingselect",name:"sjperson",config:{model:"HyAppUserList"},fieldLabel:"设计人"},
		{xtype:"datefield",name:"sjDate",format:"Y-m-d",fieldLabel:"设计时间"},
		{xtype:"textfield",name:"gbitnum",fieldLabel:"位号"}
    ],
    baseInfoPanel_col3 : [
		{xtype:"searchingselect",name:"ctCustomerInfo",config:{model:"CtCustomerInfoList"},fieldLabel:"客户名称"},
	    {xtype:"textfield",name:"xsize",fieldLabel:"规格"},
		{xtype:"bsunioncodeinfofield",name:"xunit",unioncodeField : 'xunit',fieldLabel:"单位"},
		{xtype:"bsunioncodeinfofield",name:"xcaption",unioncodeField : 'xcaption',fieldLabel:"类型"},
		{xtype:"bsunioncodeinfofield",name:"paper",unioncodeField : 'paper',fieldLabel:"纸张"}
    
    ],
    baseInfoPanel_col4 : [
		{xtype:"textfield",name:"gfinaluser",fieldLabel:"最终用户"},
		{xtype:"bsunioncodeinfofield",name:"xversion",unioncodeField:'xversion' ,fieldLabel:"版次"},
		{xtype:"textfield",name:"xremark",fieldLabel:"备注",  width:900, height:70}
    ],
    
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
    
    /**实现父类方法**/
    modelFields : function(){
    
    	return ['ctCustomerInfo','gkind','guse','xunit','xsize','gstyle','xmaterial','xcaption','sjperson','sjDate','shperson',
    	        'shDate','paper','dkind','xversion','nddegree','xprice','xremark','sequence','gbitnum','gfinaluser' , 
    	        'lqtsgitemDesigns' , 'lqtsgitemDesigns_modified' , 'lqtsgitemDesigns_deleted'].concat(ExtUtils.defaultModelFields);
    },
    
    formItems : function(){
    
		var me = this;
		
		me.bomGrid = ExtUtils.arrayGrid({
    		height : 200,
    		modelName : 'bom',
    		ownerModule : me,
			columns : me.bomColumns,
			sorters : [{
				property: 'sortIndex', 
				direction : 'ASC'
			}]
		});
		
    	var p1 = ExtUtils.creationInfoPanel();
    	
    	var p2 = ExtUtils.panel({
    		collapsed : false,
    		collapsible : true,
    		title : '基本信息',
    		items : ExtUtils.columnLayout([this.baseInfoPanel_col1, this.baseInfoPanel_col2, this.baseInfoPanel_col3, this.baseInfoPanel_col4])
    	});
    	
    	var actionBar =  me.createBomBar();

    	var p3 = ExtUtils.panel({
    		title : '设计BOM',
    		collapsible : true,
    		items : me.bomGrid,
    		tbar: actionBar
    	});
    	
    	return ExtUtils.fitLayout([p2 , p3 , p1]);
    },
    
	/**覆盖父类方法**/
    beforeSave : function(values){
    	var me = this;
    	var lqtsgitemDesigns_modified = ExtUtils.getModifedData(me.bomGrid);
    	var lqtsgitemDesigns_deleted = ExtUtils.getDeletedData(me.bomGrid);
    	Ext.apply(values , {
    		lqtsgitemDesigns_modified : lqtsgitemDesigns_modified || [],
    		lqtsgitemDesigns_deleted : lqtsgitemDesigns_deleted || []
    	});
    },
    
    afterModelLoad : function(model){
    	var me = this;
    	me.bomGrid.getStore().loadData(model.data.lqtsgitemDesigns);
    }
    /**自定义页面行为**/
  
    /**自定义页面行为响应**/
	
});
