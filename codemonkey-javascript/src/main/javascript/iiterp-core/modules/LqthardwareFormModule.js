
Ext.define('iiterp-core.modules.LqthardwareFormModule', {
	/**继承**/
	extend: 'iiterp-core.modules.ProductFormModule',

	/**常量**/
    id:'lqthardwareFormModule',
    
    requires : ['iiterp-core.modules.LqtprodbuyTreeModule'],
    
    winTitle : '新增零件',
    
    modelName : 'Lqthardware',
    
    /**自定义常量**/
    baseInfoPanel_col1 : [
		{xtype:"textfield",name:"code",fieldLabel:"零件编码", allowBlank:false},
		{xtype:"textfield",name:"name",fieldLabel:"零件名称", allowBlank:false},
		{xtype:"searchingselect",name:"sjperson",config:{model:"HyAppUserList"},fieldLabel:"设计人" , allowBlank:false},
		{xtype:"datefield",name:"sjDate",format:"Y-m-d",fieldLabel:"设计时间"},
		{xtype:"bsunioncodeinfofield",name:"xversion",unioncodeField:'xversion' ,fieldLabel:"版次"}
    			
	],
	baseInfoPanel_col2 : [
		{xtype:"bsunioncodeinfofield",name:"xmaterial",unioncodeField : 'xmaterial',fieldLabel:"材质" , allowBlank:false},
		{xtype:"textfield",name:"xsize",fieldLabel:"规格"},
		{xtype:"searchingselect",name:"shperson",config:{model:"HyAppUserList"},fieldLabel:"审核人"},
		{xtype:"datefield",name:"shDate",format:"Y-m-d",fieldLabel:"审核时间"},
		{xtype:"selectfield",name:"hkind",model:'HKind',fieldLabel:"属性"}
	],
	baseInfoPanel_col3 : [
		{xtype:"bsunioncodeinfofield",name:"xunit",unioncodeField : 'xunit',fieldLabel:"单位"},
		{xtype:"bsunioncodeinfofield",name:"xstyle",unioncodeField : 'xstyle',fieldLabel:"型号"},
		{xtype:"bsunioncodeinfofield",name:"xcaption",unioncodeField : 'xcaption',fieldLabel:"类型"},
		{xtype:"bsunioncodeinfofield",name:"paper",unioncodeField : 'paper',fieldLabel:"纸张"},
		{xtype:"bsunioncodeinfofield",name:"warehouseName",unioncodeField : 'warehouseName',fieldLabel:"仓库类型"}
	],
	baseInfoPanel_col4 : [
		{xtype:"selectfield",name:"xingShi",model: 'XingShi', fieldLabel:"形式"},
		{xtype:"textfield",name:"xremark",fieldLabel:"备注",  width:900, height:70 }
	],
    /**实现父类方法**/
    modelFields : function(){
    
    	return ['hdcname','hkind','hcount','hsafeCount','hlocation','hinTimes','houtTimes','adPrice','hadvBprice',
    	        'hgui','gz','mtech','jtech','mprice','oprice','jprice','xingShi','warehouseName','xunit','xsize',
    	        'xstyle','xmaterial','xcaption','sjperson','sjDate','shperson','shDate','paper','dkind','xversion',
    	        'nddegree','xprice','xremark','sequence','lqtprodbuys','lqtprodbuys_modified','lqtprodbuys_deleted'].concat(ExtUtils.defaultModelFields);
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
    	
    	var baseInfoPanel = ExtUtils.panel({
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
    	
    	return ExtUtils.fitLayout([baseInfoPanel , p3 , p1 ]);
    },
    
	/**覆盖父类方法**/
    beforeSave : function(values){
    	var me = this;
    	var lqtprodbuys_modified = ExtUtils.getModifedData(me.bomGrid);
    	var lqtprodbuys_deleted = ExtUtils.getDeletedData(me.bomGrid);
    	Ext.apply(values , {
    		lqtprodbuys_modified : lqtprodbuys_modified || [],
    		lqtprodbuys_deleted : lqtprodbuys_deleted || []
    	});
    },
    
    afterModelLoad : function(model){
    	var me = this;
    	me.bomGrid.getStore().loadData(model.data.lqtprodbuys);
    },
    
    createBbar : function(){
    	
    	var actions = [
			this.createModuleAction(this.saveAction),
			this.createModuleAction(this.cancelAction),
			this.createModuleAction(this.showStructureAction)
		];
    	
    	return this.createToolbar(actions);
    },
    /**自定义页面行为**/
    
    /**自定义页面行为响应**/
    showStructure : function(){
		var treeModule = this.app.getModule('lqthardwareTreeModule');
		treeModule.createWindow({entityId : this.entityId});
	 	return treeModule;
    }
});
