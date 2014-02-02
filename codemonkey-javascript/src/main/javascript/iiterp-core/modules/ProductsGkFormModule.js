
Ext.define('iiterp-core.modules.ProductsGkFormModule', {
	/**继承**/
    extend: 'AM.modules.FormModule',

	/**常量**/
    id:'productsGkFormModule',
    
    winTitle : '产品资料信息',
    
    modelName : 'ProductsGk',
    
    /**自定义常量**/
    baseInfoPanel_col1 : [
       {xtype:"textfield" , name:"hid" , fieldLabel:"产品编号" , allowBlank:false},
       {xtype:"textfield" , name:"hname" , fieldLabel:"设备名称"},
       {xtype:"numberfield" , name:"pressureIn" , fieldLabel:"进口压力" , allowDecimals: false},
       {xtype:"textfield" , name:"shaft" , fieldLabel:"轴径" , allowBlank:false},
       {xtype:"textfield" , name:"hmaterial" , fieldLabel:"现用材料"},
       {xtype:"textfield" , name:"fengYe" , fieldLabel:"封液"}
    ], 
    
    baseInfoPanel_col2 : [
       {xtype:"textfield" , name:"hmodel" , fieldLabel:"设备型号"},
       {xtype:"textfield" , name:"hmedia" , fieldLabel:"工作介质"},
       {xtype:"numberfield" , name:"pressureOut" , fieldLabel:"出口压力" , allowDecimals: false},
       {xtype:"textfield" , name:"hsize" , fieldLabel:"规格型号"},
       {xtype:"textfield" , name:"rinseF" , fieldLabel:"冲洗方案"}
     ], 
     
     baseInfoPanel_col3 : [
        {xtype:"textfield" , name:"hweihao" , fieldLabel:"设备位号"},
        {xtype:"numberfield" , name:"hwendu" , fieldLabel:"工作温度" , allowDecimals: false},
        {xtype:"textfield" , name:"hspeed" , fieldLabel:"轴转速"},
        {xtype:"textfield" , name:"hxm" , fieldLabel:"密封芯"},
        {xtype:"textfield" , name:"rinseY" , fieldLabel:"冲洗液"}
     ], 
     
     baseInfoPanel_col4 : [
        {xtype:"textarea" , name:"hremark" , fieldLabel:"备注" , width:900}
     ],
                    	
    /**实现父类方法**/
    modelFields : function(){
    
    	return ['hid','hweihao','hname','hmedia','hwendu','pressureIn','pressureOut','hspeed','shaft','hsize','hxm','hmaterial','rinseF','rinseY','fengYe','hremark','hmodel'].concat(ExtUtils.defaultModelFields);
    },
    
    formItems : function(){
    
		var me = this;
		
		var p1 = ExtUtils.creationInfoPanel();
    	
		var baseInfoPanel = ExtUtils.panel({
    		collapsed : false,
    		collapsible : true,
    		title :  '详情',
    		items : [
    		         ExtUtils.columnLayout([this.baseInfoPanel_col1, this.baseInfoPanel_col2, this.baseInfoPanel_col3]) , 
    		         ExtUtils.panel({items : this.baseInfoPanel_col4})
    		]
    		
    	});
    	
    	return ExtUtils.fitLayout([baseInfoPanel, p1]);
    }
    
	/**覆盖父类方法**/
    
    /**自定义页面行为**/
    
    /**自定义页面行为响应**/
});
