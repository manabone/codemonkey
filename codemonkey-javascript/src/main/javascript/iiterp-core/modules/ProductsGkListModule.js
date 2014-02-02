
Ext.define('iiterp-core.modules.ProductsGkListModule', {
    /**继承**/
    extend: 'AM.modules.ListModule',

	/**常量**/
    id:'productsGkListModule',
    
    requires : ['iiterp-core.modules.ProductsGkFormModule'],
    
    winTitle : '产品信息列表',
    
    modelName : 'ProductsGkList',
    
    formModuleId : 'productsGkFormModule',
	
	/**自定义常量**/
    
    /**实现父类方法**/
    modelFields : function(){
    	return ['hid','hweihao','hname','hmedia','hwendu','pressureIn','pressureOut','hspeed','shaft','hsize','hxm','hmaterial','rinseF','rinseY','fengYe','hremark','hmodel'].concat(ExtUtils.defaultModelFields);
    },
	
	gridCols  : function() {
		return [
		        {dataIndex:"hid" , flex:1 , header:"产品编码"},
		        {dataIndex:"hweihao" , flex:1 , header:"设备位号"},
		        {dataIndex:"hname" , flex:1 , header:"设备名称"},
		        {dataIndex:"hmedia" , flex:1 , header:"工作介质"},
		        {dataIndex:"hwendu" , flex:1 , header:"工作温度"},
		        {dataIndex:"pressureIn" , flex:1 , header:"进口压力"},
		        {dataIndex:"pressureOut" , flex:1 , header:"出口压力"},
		        {dataIndex:"hspeed" , flex:1 , header:"轴转速"},
		        {dataIndex:"shaft" , flex:1 , header:"轴径"},
		        {dataIndex:"hsize" , flex:1 , header:"规格型号"},
		        {dataIndex:"hxm" , flex:1 , header:"密封芯"},
		        {dataIndex:"hmaterial" , flex:1 , header:"现用材料"},
		        {dataIndex:"rinseF" , flex:1 , header:"冲洗方案"},
		        {dataIndex:"rinseY" , flex:1 , header:"冲洗液"},
		        {dataIndex:"fengYe" , flex:1 , header:"封液"},
		        {dataIndex:"hmodel" , flex:1 , header:"设备型号"},
		        {dataIndex:"hremark" , flex:1 , header:"备注"},
		];
	},
    /**覆盖父类方法**/
    searchForm : function() {
    	return {
    		height: 180,
    		items : ExtUtils.columnLayout([
                    [
				     {xtype :"textfield" , name :"hid" , fieldLabel:"产品编码" },
				     {xtype :"textfield" , name :"rinseF" , fieldLabel:"冲洗方案" },
				     {xtype :"textfield" , name :"shaft" , fieldLabel:"轴径" }
                    ],
				    [
				     {xtype :"textfield" , name :"hweihao" , fieldLabel:"设备位号" },
				     {xtype :"textfield" , name :"hwendu" , fieldLabel:"工作温度" },
                     {xtype :"textfield" , name :"hxm" , fieldLabel:"密封芯" }
				   ],
				   [
                     {xtype :"textfield" , name :"hmedia" , fieldLabel:"工作介质" },
                     {xtype :"textfield" , name :"hmodel" , fieldLabel:"设备型号" },
				   ]
				  
			])
    	};
    }
    
    
    /**自定义页面行为**/
    
    /**自定义页面行为响应**/
	
});
