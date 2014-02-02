
Ext.define('iiterp-core.modules.LqthardwareListModule', {
    /**继承**/
    extend: 'AM.modules.ListModule',

	/**常量**/
    id:'lqthardwareListModule',
    
    requires : ['iiterp-core.modules.LqthardwareFormModule'],
    
    winTitle : '零件管理',
    
    modelName : 'LqthardwareList',
    
    formModuleId : 'lqthardwareFormModule',
	
	/**自定义常量**/
    
    /**实现父类方法**/
    modelFields : function(){
    	return ['hdcname','hkind','hcount','hsafeCount','hlocation','hinTimes','houtTimes','adPrice','hadvBprice','hgui','gz','mtech','jtech','mprice','oprice','jprice','xingShi','warehouseName','xunit','xsize','xstyle','xmaterial','xcaption','sjperson','sjDate','shperson','shDate','paper','dkind','xversion','nddegree','xprice','xremark','sequence'].concat(ExtUtils.defaultModelFields);
    },
	
	gridCols  : function() {
		return [
		    {dataIndex:"id",hidden:false},
			{dataIndex:"code",flex:1,header:"零件编码"},
			{dataIndex:"name",flex:1,header:"零件名称"},
			{dataIndex:"hdcname",flex:1,header:"分类"},
			{dataIndex:"xunit_code",flex:1,header:"单位"},
			{dataIndex:"hkind",flex:1,header:"属性"},
			{dataIndex:"xsize",flex:1,header:"规格"},
			{dataIndex:"xstyle_code",flex:1,header:"型号"},
			{dataIndex:"xmaterial_code",flex:1,header:"材质"},
			{dataIndex:"hcount",flex:1,header:"库存数量"},
			{dataIndex:"sjDate",flex:1,header:"设计时间"},
			{dataIndex:"creationDate",flex:1,header:"建档日期"},
			{dataIndex:"sjpersonname",flex:1,sortable: false,header:"设计人"},
			{dataIndex:"shpersonname",flex:1,sortable: false,header:"审核人"},
			{dataIndex:"xversion_code",flex:1,header:"版次"},
			{dataIndex:"xremark",flex:1,header:"备注"}
		];
	},
    /**覆盖父类方法**/
    searchForm : function() {
    	return {
    		height: 150,
    		items : ExtUtils.columnLayout([
               	[
               	 {xtype:"textfield" , name:"code_Like" , fieldLabel:"零件编码" },
               	 {xtype:"textfield" , name:"name_Like" , fieldLabel:"零件名称" },
               	 {xtype:"selectfield" , name:"hkind" , model : 'HKind', fieldLabel:"属性" }
               	],[
               	 {xtype:"textfield" , name:"xsize_Like" , fieldLabel:"规格" },
               	 {xtype:"bsunioncodeinfofield" , name:"hdcname.id" , fieldLabel:"分类", unioncodeField : 'hdcname'},
                 {xtype:"bsunioncodeinfofield",name:"xstyle.id",unioncodeField : 'xstyle',fieldLabel:"型号"}
               	],[
                 {xtype:"bsunioncodeinfofield",name:"xmaterial.id",unioncodeField : 'xmaterial',fieldLabel:"材质"},   
               	 {xtype:"textfield" , name:"xremark_Like" ,  fieldLabel:"备注"},  
               	 {xtype:"datefield" , name:"creationDate_GE" , fieldLabel:"建档日期" , format:"Y-m-d"}   
               	],[
               	 {xtype:"textfield" , name:"sjperson.name_Like" ,  fieldLabel:"设计人"},  
               	 {xtype:"textfield" , name:"shperson.name_Like" ,  fieldLabel:"审核人"},
               	 {xtype:"datefield" , name:"creationDate_LE" , fieldLabel:"截止到" , format:"Y-m-d"}
               	]
            ])
    	};
    }
    /**自定义页面行为**/
    
    /**自定义页面行为响应**/
	
});
