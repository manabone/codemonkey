
Ext.define('iiterp-core.modules.LqtprovderListModule', {
    /**继承**/
    extend: 'AM.modules.ListModule',

	/**常量**/
    id:'lqtprovderListModule',
    
    requires : ['iiterp-core.modules.LqtprovderFormModule'],
    
    winTitle : '供应商列表',
    
    modelName : 'LqtprovderList',
    
    formModuleId : 'lqtprovderFormModule',
	
	/**自定义常量**/
    
    /**实现父类方法**/
    modelFields : function(){
    	return ['pdShrName','bsRegionInfo','pdAddr','pdFax','tname','pdPerson','pdPhone','pdAid','pdRemark','keyText','bank','bnum','taxNum','pdnumber','pdclass','ivcAdd','mphone','email','caption','mkind','status'].concat(ExtUtils.defaultModelFields);
    },
	
    gridCols  : function() {
		return ([	 {header: 'id', dataIndex: 'id' , hidden : true},
                     {dataIndex:"pdShrName" , flex:1 , header:"简称"},
                     {dataIndex:"name" , flex:1 , header:"全称"},                            
                     {dataIndex:"pdPerson" , flex:1 , header:"联系人"},
                     {dataIndex:"pdPhone" , flex:1 , header:"电话"},
                     {dataIndex:"mphone" , flex:1 , header:"手机"},
                     {dataIndex:"pdFax" , flex:1 , header:"传真"},
                     {dataIndex:"bsRegionInfo_name" , flex:1 , header:"地区"},
                     {dataIndex:"pdnumber_code" , flex:1 , header:"厂商分类"},
                     {dataIndex:"tname_code" , flex:1 , header:"产品分类"},
                     {dataIndex:"pdAddr" , flex:1 , header:"地址"},
                     {dataIndex:"pdAid" , flex:1 , header:"邮编"},
                     {dataIndex:"pdclass_code" , flex:1 , header:"等级"}
                     //{dataIndex:"" , flex:1 , header:"证书是否逾期"}
                    
               ]);
	},
    /**覆盖父类方法**/
	 searchForm : function() {
	    	return {
	    		height: 150,
	    		items : ExtUtils.columnLayout([
	               	[
	               	 {xtype :"textfield" , name :"name_Like" , fieldLabel :"名称" },
	               	 {xtype:"bsunioncodeinfofield" , name:"pdnumber.id" , unioncodeField : 'pdnumber' ,fieldLabel:"厂商分类"}
	               	],[
	               	 {xtype:"bsunioncodeinfofield" , name:"tname.id" , unioncodeField : 'tname' ,fieldLabel:"产品分类"},
	               	 {xtype:"bsunioncodeinfofield" , name:"pdclass.id" , unioncodeField : 'pdclass' , fieldLabel:"等级"}
	               	],[
	               	 {xtype :"regionTreeCombo" , name :"bsRegionInfo.id" , fieldLabel:"地区"},
	               	 {xtype :"textfield" , name :"keyText_Like" , fieldLabel:"关键字"}
	               	]
	            ])
	    	};
	    }
    /**自定义页面行为**/
    
    /**自定义页面行为响应**/
	
});
