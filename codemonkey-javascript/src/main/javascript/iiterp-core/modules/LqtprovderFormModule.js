
Ext.define('iiterp-core.modules.LqtprovderFormModule', {
	/**继承**/
    extend: 'AM.modules.FormModule',
    requires : ['AM.base.RegionTreeCombo'],

	/**常量**/
    id:'lqtprovderFormModule',
    
    winTitle : '供应商信息',
    
    modelName : 'Lqtprovder',
    
    /**自定义常量**/
    //基本信息
    baseInfoPanel_col1 : [ 
	      {xtype:"textfield" , name:"name" , fieldLabel:"名称" , allowBlank:false},
	      {xtype:"regionTreeCombo",name:"bsRegionInfo", modelName : 'regionTree' , fieldLabel :"地区", allowBlank:false},
	      {xtype:"textfield" , name:"pdFax" , fieldLabel:"传真" },
	      {xtype:"bsunioncodeinfofield" , name:"pdnumber" , fieldLabel:"厂商分类" , unioncodeField : 'pdnumber', allowBlank:false},
	      {xtype:"textfield" , name:"pdAid" , fieldLabel:"邮编" }
	], 
                    	
	baseInfoPanel_col2 : [
		 {xtype:"textfield" , name:"pdShrName" , fieldLabel:"简称" , allowBlank:false},
	     {xtype:"textfield" , name:"pdAddr" , fieldLabel:"地址" },
	     {xtype:"textfield" , name:"pdPerson" , fieldLabel:"联系人" },
	     {xtype:"bsunioncodeinfofield" , name:"tname" , fieldLabel:"产品分类", unioncodeField : 'tname', allowBlank:false},
	     {xtype:"textfield" , name:"email" , fieldLabel:"邮箱"}
	], 
	baseInfoPanel_col3 : [
  		 {xtype:"bsunioncodeinfofield" , name:"caption" , fieldLabel:"性质" , unioncodeField : 'caption',allowBlank:false},
         {xtype:"textfield" , name:"pdPhone" , fieldLabel:"电话"},
         {xtype:"textfield" , name:"mphone" , fieldLabel:"手机"}
  	], 
  	baseInfoPanel_col4: [
         {xtype:"textarea" , name:"pdRemark" , fieldLabel:"备注", width:900, height:70 }
  	], 
  	//采购信息
  	buyInfoPanel_col1 : [
		 {xtype:"textfield" , name:"keyText" , fieldLabel:"主营"},
	     {xtype:"textfield" , name:"bnum" , fieldLabel:"帐号"}
	], 
  	buyInfoPanel_col2 : [
  		 {xtype:"bsunioncodeinfofield" , name:"pdclass" , fieldLabel:"等级",unioncodeField : 'pdclass'},
         {xtype:"textfield" , name:"bank" , fieldLabel:"开户行"}
  	],
  	buyInfoPanel_col3 : [
  		 {xtype:"textfield" , name:"ivcAdd" , fieldLabel:"开票地址"},
         {xtype:"textfield" , name:"taxNum" , fieldLabel:"税号"}
  	],
	              	
   //证书信息
    certificateColumns : [
          {header : 'id', dataIndex: 'id' , hidden : true},
          {dataIndex : 'type_code' , hidden : true},
          ExtUtils.unionCodeColumn({
        	  header : '类型' ,  
        	  unioncodeField : 'type',
        	  dataIndex : 'type',
        	  textDataIndex : 'type_code',
        	  gridId : 'lqtprovderForm_certificateGrid'
          }),
          
          {dataIndex : 'code' , header : '编号' , editor : {xtype : 'textfield'}},                            
          
          {header : '有效期起' , dataIndex : 'beginDate' ,
        	  editor: {xtype : 'datefield' , format : 'Y-m-d' , getValue : function(){
        		  var format = this.format;
        		  var value = this.value;
        		  return value ? Ext.Date.format(value, format) : '';	
        	  }} 
          },
          {header : '有效期止' , dataIndex : 'endDate' ,
        	  editor: {xtype : 'datefield' , format : 'Y-m-d' , getValue : function(){
        		  var format = this.format;
        		  var value = this.value;
        		  return value ? Ext.Date.format(value, format) : '';	
        	  }} 
          },
          {dataIndex : 'remark' , header : '备注' , editor: {xtype : 'textfield'}}
    ],

  	
    /**实现父类方法**/
    modelFields : function(){
    
    	return ['pdShrName','bsRegionInfo','pdAddr','pdFax','tname','pdPerson','pdPhone','pdAid','pdRemark','keyText','bank','bnum','taxNum',
    	        'pdnumber','pdclass','ivcAdd','mphone','email','caption','mkind','status',
    	        'lqtprovderCertificates' , 'lqtprovderCertificates_modified' , 'lqtprovderCertificates_deleted'].concat(ExtUtils.defaultModelFields);
    },
    
    formItems : function(){
    	var me = this;
    	var certificateGrid = ExtUtils.arrayGrid({
    		id : 'lqtprovderForm_certificateGrid',
    		modelName : 'LqtprovderCertificate',
    		ownerModule : me,
			columns : me.certificateColumns
		});
    	var p1 = ExtUtils.creationInfoPanel();
    	
    	var baseInfoPanel = ExtUtils.panel({
    		collapsed : false,
    		collapsible : true,
    		title : '基本信息',
    		items : ExtUtils.columnLayout([this.baseInfoPanel_col1, this.baseInfoPanel_col2, this.baseInfoPanel_col3, this.baseInfoPanel_col4])
    	});
    	var buyInfoPanel = ExtUtils.panel({
    		collapsed : false,
    		collapsible : true,
    		title : '采购信息',
    		items : ExtUtils.columnLayout([this.buyInfoPanel_col1, this.buyInfoPanel_col2, this.buyInfoPanel_col3])
    	});
    	var actionBar =  me.createToolbar([
           me.createModuleAction(this.addCertificateAction),
           me.createModuleAction(this.removeCertificateAction)
        ]);

    	var certificatePanel = ExtUtils.panel({
    		title : '供应商资格认证',
    		collapsible : true,
    		items : certificateGrid,
    		tbar: actionBar
    	});
    	
    	return ExtUtils.fitLayout([baseInfoPanel , buyInfoPanel , certificatePanel , p1]);
    	
    },
	
	/**覆盖父类方法**/
    beforeSave : function(values){
    	var lqtprovderCertificates_modified = ExtUtils.getModifedData(Ext.getCmp('lqtprovderForm_certificateGrid'));
    	var lqtprovderCertificates_deleted = ExtUtils.getDeletedData(Ext.getCmp('lqtprovderForm_certificateGrid'));
    	Ext.apply(values , {
    		lqtprovderCertificates_modified : lqtprovderCertificates_modified || [],
    		lqtprovderCertificates_deleted : lqtprovderCertificates_deleted || []
    	});
    },
    
    afterModelLoad : function(model){
  	   Ext.getCmp('lqtprovderForm_certificateGrid').getStore().loadData(model.data.lqtprovderCertificates);
    },
    /**自定义页面行为**/
    addCertificateAction : {
		iconCls : 'add' , text: '添加' , action : 'addCertificate'
	},
	
	removeCertificateAction : {
		iconCls:'remove' , text: '删除' , action : 'removeCertificate'
    },
	
    /**自定义页面行为响应**/
    addCertificate : function(){
		var grid = Ext.getCmp('lqtprovderForm_certificateGrid');
		ExtUtils.addLine(grid);
	},
	
	removeCertificate : function(){
		var grid = Ext.getCmp('lqtprovderForm_certificateGrid');
		ExtUtils.removeLine(grid);
	}
});
