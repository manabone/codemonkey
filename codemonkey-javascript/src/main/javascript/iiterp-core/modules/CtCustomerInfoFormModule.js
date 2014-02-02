
Ext.define('iiterp-core.modules.CtCustomerInfoFormModule', {
	/**继承**/
    extend: 'AM.modules.FormModule',
    
    requires : ['AM.base.RegionTreeCombo'],

	/**常量**/
    id:'ctCustomerInfoFormModule',
    
    winTitle : '客户管理',
    
    modelName : 'CtCustomerInfo',
    
    /**自定义常量**/
    
    /**实现父类方法**/
    modelFields : function(){
    
    	return ['customerType','customerLevel','customerName','customerKinds','customerRegionId','customerTrade','customerSalerIds','customerPhone','customerFax','customerCompanyAddress','customerZipCode','customerLinkman','customerMobilephone','customerEmail','customerProducts','customerYearValue','customerRemark','customerAbbreviation','customerFirmSize','customerTariff','customerCode'].concat(ExtUtils.defaultModelFields);
    },
    
    g1Columns : [
    	[
	    	{header: '姓名' , dataIndex: 'component_customerSalerIds' },
	    	{header: '工号' , dataIndex: 'component_customerSalerIds' }
	    ],[
	    	{xtype:"textfield",name:"customerSalerIds"},
	    	{xtype:"textfield",name:"customerSalerIds"}
	    ]
    ],
    
     baseInfoPanel_col1 : [
     		{xtype:"textfield",name:"customerName",fieldLabel:"客户名称"},
     		{allowBlank:false,xtype:"regionTreeCombo",name:"customerRegionId", modelName : 'regionTree' , fieldLabel :"地区ID"},
			{xtype:"textfield",name:"customerZipCode",fieldLabel:"邮政编码"},
			{xtype:"textfield",name:"customerLinkman",fieldLabel:"联系人"},
			{xtype:"bsunioncodeinfofield",name:"customerLevel",fieldLabel:"用户级别",unioncodeField : 'customerLevel'},
			{xtype:"bsunioncodeinfofield",name:"customerType",fieldLabel:"用户类型",unioncodeField : 'customerType'}
	],
     baseInfoPanel_col2 : [
     		{xtype:"textfield",name:"customerAbbreviation",fieldLabel:"简称"},
			{xtype:"textfield",name:"customerCompanyAddress",fieldLabel:"公司地址"},
			{xtype:"textfield",name:"customerEmail",fieldLabel:"邮箱"},
			{xtype:"textfield",name:"customerPhone",fieldLabel:"联系电话"},
			{xtype:"textfield",name:"customerFirmSize",fieldLabel:"企业规模"},
			{xtype:"textfield",name:"customerCode",fieldLabel:"客户编码"}
     ],
     baseInfoPanel_col3 : [
     		{xtype:"textfield",name:"customerTrade",fieldLabel:"所属行业"},
			{xtype:"bsunioncodeinfofield",name:"customerKinds",fieldLabel:"用户分类",unioncodeField : 'customerKinds'},
			{xtype:"textfield",name:"customerFax",fieldLabel:"传真号码"},
			{xtype:"textfield",name:"customerProducts",fieldLabel:"主要产品"},
			{xtype:"textfield",name:"customerYearValue",fieldLabel:"年产值"}
     ],
     
     
     baseInfoPanel_col4 :[
     		{xtype:"textarea",name:"customerRemark",fieldLabel:"其他说明"}
     ],
    
    formItems : function(){
    
		var me = this;
    	
    	var p1 = ExtUtils.creationInfoPanel();
    	
    	var p2 = ExtUtils.panel({
    		title : '基本信息',
    		collapsed : false,
    		collapsible : true,
    		items : ExtUtils.columnLayout([this.baseInfoPanel_col1, this.baseInfoPanel_col2, this.baseInfoPanel_col3])
    	});
    	
    	
    	var g1 = ExtUtils.arrayGrid({
    		id : 'ctCustomerForm_saleGrid',
    		modelName : 'sale',
    		ownerModule : me,
            columns : me.g1Columns
    	});
    	
    	var p3 = ExtUtils.panel({
    		title : '详细信息',
    		collapsed : false,
    		collapsible : true,
    		items : ExtUtils.columnLayout([[g1],this.baseInfoPanel_col4])
    	});
    	
    	return ExtUtils.fitLayout([p1 , p2 , p3]);
    }
    				
	/**覆盖父类方法**/
    
    /**自定义页面行为**/
    
    /**自定义页面行为响应**/
});
