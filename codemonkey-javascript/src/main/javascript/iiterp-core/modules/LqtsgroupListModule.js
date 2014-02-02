
Ext.define('iiterp-core.modules.LqtsgroupListModule', {
    /**继承**/
    extend: 'AM.modules.ListModule',

	/**常量**/
    id:'lqtsgroupListModule',
    
    requires : ['iiterp-core.modules.LqtsgroupFormModule'],
    
    winTitle : '组合管理',
    
    modelName : 'LqtsgroupList',
    
    formModuleId : 'lqtsgroupFormModule',
	
	/**自定义常量**/
    
    /**实现父类方法**/
    modelFields : function(){
    	return ['ctCustomerInfo','gkind','guse','xunit','xsize','gstyle','xmaterial','xcaption','sjperson','sjDate','shperson','shDate','paper','dkind','xversion','nddegree','xprice','xremark','sequence'].concat(ExtUtils.defaultModelFields);
    },
	
	gridCols  : function() {
		return ExtUtils.defaultGridCols1.concat([
			{dataIndex:"code",flex:1,header:"组合编码"},
			{dataIndex:"name",flex:1,header:"组合名称"},
			{dataIndex:"gstyle",flex:1,header:"型号"},
			{dataIndex:"gkind_code",flex:1,header:"分类"},
			{dataIndex:"xunit_code",flex:1,header:"单位"},
			{dataIndex:"ctCustomerInfo_name",flex:1,header:"客户名称"},
			{dataIndex:"sjpersonname",flex:1,header:"设计人"},
			{dataIndex:"shpersonname",flex:1,header:"审核人"},
			{dataIndex:"xversion_code",flex:1,header:"版次"},
			{dataIndex:"creationDate",flex:1,header:"建档日期"},
			{dataIndex:"xremark",flex:1,header:"备注"}
			
		]);
	},
    /**覆盖父类方法**/
    searchForm : function() {
    	return {
    		height: 200,
    		items : ExtUtils.columnLayout([
               	[
               	 {xtype:"textfield" , name:"code_Like" , fieldLabel:"组合编码" },
               	 {xtype:"textfield" , name:"sjperson.name_Like" ,  fieldLabel:"设计人"},  
               	 {xtype:"bsunioncodeinfofield" , name:"gkind.id" , fieldLabel:"分类", unioncodeField : 'gkind'}
               	],[
               	 {xtype:"textfield" , name:"name_Like" , fieldLabel:"组合名称" },
               	 {xtype:"textfield" , name:"shperson.name_Like" ,  fieldLabel:"审核人"},
               	 {xtype:"textfield" , name:"xremark_Like" ,  fieldLabel:"备注"}  
               	],[
                 {xtype:"textfield" , name:"ctCustomerInfo.name_Like" ,  fieldLabel:"客户名称"},   
               	 {xtype:"datefield" , name:"creationDate_GE" , fieldLabel:"建档日期" , format:"Y-m-d"}
               	],[
               	 {xtype:"textfield" , name :"gstyle_Like" , fieldLabel:"型号" },
               	 {xtype:"datefield" , name:"creationDate_LE" , fieldLabel:"截止到" , format:"Y-m-d"}
               	 ]
            ])
    	};
    }
    /**自定义页面行为**/
    
    /**自定义页面行为响应**/
	
});
