
Ext.define('iiterp-core.modules.BsMessageInfoFormModule', {
	/**继承**/
    extend: 'AM.modules.FormModule',
    
    requires : ['iiterp-hr.cmp.SelectPerson','AM.base.FileUploadPanel'],

	/**常量**/
    id:'bsMessageInfoFormModule',
    
    winTitle : '消息',
    
    modelName : 'BsMessageInfo',
    
    /**自定义常量**/
    
    /**实现父类方法**/
    modelFields : function(){
    
    	return ['title','content','messageKind', 'smsNotice', 'uploadIds' , 'uploadIdsToForward' , 'copyToEntries' , 'receiverEntries' , 'uploadEntries'].concat(ExtUtils.defaultModelFields);
    },
    
    formItems : function(){
    
    	var p1 = ExtUtils.creationInfoPanel();
    	
    	var p2 = ExtUtils.panel({
    		title : 'Detail',
    		items : [
		         {xtype:"textfield", name:"title", fieldLabel:"标题", width:1000 },
		         {xtype :"selectfield", name :"messageKind", model : 'MessageKind', fieldLabel :"类型" },
		         {xtype:"checkbox", name:"smsNotice", fieldLabel:"短信通知"},
		         {xtype:"htmleditor", name:"content", fieldLabel:"内容", width:1000},
		         {xtype:"selectperson", id:"receiverEntries" , title:"接收人", width:1000 },
		         {xtype:"selectperson", id:"copyToEntries" , title:"抄送人", width:1000 },
		         {xtype:"uploadpanel", id:"messageUploadPanel", width:1000 , modelName : 'bsUploadInfoList'  }
    		]
    	});
    	return ExtUtils.fitLayout([p1, p2]);
    },
    
	/**覆盖父类方法**/
    // actions 
    saveAction : {
		action : 'save', text: '发送', iconCls : 'icon-update'
	},
	
    beforeSave : function(values){
    	var panel = Ext.getCmp("messageUploadPanel");
    	values.uploadIds = panel.getValue();
    	//values.uploadIdsToForward = panel.getValueToForward();
    	
    	var receiverEntries = Ext.getCmp("receiverEntries");
    	values.receiverEntries = receiverEntries.getValues();
    	
    	var copyToEntries = Ext.getCmp("copyToEntries");
    	values.copyToEntries = copyToEntries.getValues();
    	
    	return values;
    },
    
    afterModelLoad : function(model){
    	Ext.getCmp("messageUploadPanel").store.loadData(model.data.uploadEntries || []);
    	Ext.getCmp("receiverEntries").grid.getStore().loadData(model.data.receiverEntries || []);
    	Ext.getCmp("copyToEntries").grid.getStore().loadData(model.data.copyToEntries || []);
    }
    /**自定义页面行为**/
    
    /**自定义页面行为响应**/
});
