
Ext.define('iiterp-core.modules.CtCustomerInfoListModule', {
    /**继承**/
    extend: 'AM.modules.ListModule',

	/**常量**/
    id:'ctCustomerInfoListModule',
    
    requires : ['iiterp-core.modules.CtCustomerInfoFormModule'],
    
    winTitle : '客户管理',
    
    modelName : 'CtCustomerInfoList',
    
    formModuleId : 'ctCustomerInfoFormModule',
	
	/**自定义常量**/
    
    /**实现父类方法**/
    modelFields : function(){
    	return ['customerType','customerLevel','customerName','customerKinds','customerRegionId','customerTrade','customerSalerIds','customerPhone','customerFax','customerCompanyAddress','customerZipCode','customerLinkman','customerMobilephone','customerEmail','customerProducts','customerYearValue','customerRemark','customerAbbreviation','customerFirmSize','customerTariff','customerCode'].concat(ExtUtils.defaultModelFields);
    },
	
	gridCols  : function() {
		return [
				{dataIndex:"customerCode",flex:1,header:"客户编码"},
				{dataIndex:"customerName",flex:1,header:"客户名称"},
				{dataIndex:"customerType",flex:1,header:"用户类型"},
				{dataIndex:"customerRegionId",flex:1,header:"地区ID"},
				{dataIndex:"customerTrade",flex:1,header:"所属行业"},
				{dataIndex:"customerPhone",flex:1,header:"联系电话"}
			];
	},
    /**覆盖父类方法**/
    searchForm : function() {
    	return {
    		height: 150,
    		items : ExtUtils.columnLayout([
               	[
               	 {xtype :"textfield" , name :"customerName_Like" , fieldLabel :"客户名称" },
               	 {xtype :"textfield" , name :"customerRegion" , fieldLabel :"所在地区" }
               	],
               	[
            	 {xtype :"textfield" , name:"customerSalerIds" , fieldLabel:"销售人员"},
            	 {xtype :"textfield" , name :"customerTrade_Like" , fieldLabel :"所属行业" }
                ],[
                 {xtype :"textfield" , name :"customerType" , fieldLabel :"用户类型" },
                 {xtype :"datefield" , name :"creationDate_GE" , fieldLabel :"建档时间" }
                
                ],[
                 {xtype :"datefield" , name :"creationDate_LE" , fieldLabel :"到" }
                ]
            ])
    	};
    }
    /**自定义页面行为**/
    
    /**自定义页面行为响应**/
	
});
