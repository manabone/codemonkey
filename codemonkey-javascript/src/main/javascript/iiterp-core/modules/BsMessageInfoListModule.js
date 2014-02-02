
Ext.define('iiterp-core.modules.BsMessageInfoListModule', {
    /**继承**/
    extend: 'AM.modules.ListModule',

	/**常量**/
    id:'bsMessageInfoListModule',
    
    requires : ['iiterp-core.modules.BsMessageInfoFormModule','iiterp-core.modules.BsMessageChatModule'],
    
    winTitle : '消息列表',
    
    modelName : 'BsMessageInfoList',
    
    formModuleId : 'bsMessageInfoFormModule',
    
    chatModuleId : 'bsMessageChatModule',
	
	/**自定义常量**/
    
    /**实现父类方法**/
    modelFields : function(){
    	return ['title','content','file','hasRead','senderName','senderDelete','messageKind','receiverNames','copyToNames','smsState'].concat(ExtUtils.defaultModelFields);
    },
	
	gridCols  : function() {
		var me = this;
		return [
			{header: 'id', dataIndex: 'id' , hidden : true},
			{dataIndex:'',flex:1,header:"全选"},
			{dataIndex:"senderName",sortable: false,flex:1,header:"发送人"},
			{dataIndex:"receiverNames",sortable: false,flex:1,header:"接收人"},
            {dataIndex:"copyToNames",sortable: false,flex:1,header:"抄送人"},
            {dataIndex:"title",flex:1,header:"标题"},
            {dataIndex:"messageKind",flex:1,header:"类型"},
            {dataIndex:"hasRead",flex:1,header:"状态"},
            {dataIndex:"creationDate",flex:1,header:"发送日期"},
            {dataIndex:'smsState',flex:1,header:"短信状态"},
            {dataIndex:'',flex:1,header:"操作"},
            {flex:1,header:"弹弹弹", xtype: 'actioncolumn',items:[{
            				icon   : '../../css/desktop/icons/fam/delete.gif' ,  // Use a URL in the icon config
		                    handler: function() {
		                    	if(!me.chatModuleId) return;
		                		
		                		var chatModule = me.app.getModule(me.chatModuleId);
		                		
		                		chatModule.createWindow();
		                    }
                }]}];
	},
    /**覆盖父类方法**/
    
    searchForm : function() {
    	return {
    		height: 150,
    		items : ExtUtils.columnLayout([
               	[
	               	{xtype :"combo" , name :"hasRead" , fieldLabel :"状态" , valueField : 'id' , displayField:"text" , allowBlank : false, editable : false,
	               		  	value: '',
							store:new Ext.data.SimpleStore({  
					 			fields:['id', 'text'],  
					 			data:[['','全部'],["1", "已读"],["0", "未读 "]]  
							}) 
	               	  },
	               	{xtype :"textfield" , name :"senderName" , fieldLabel:"发送人" },
	               	{xtype:"datefield" , name:"creationDate_GE" , fieldLabel:"发送日期" , format:"Y-m-d"}
               	],[
	               	 {xtype :"selectfield" , name :"messageKind" , model : 'MessageKind' , fieldLabel :"类型" },
	             	 {xtype :"textfield",name :"receiverName" , fieldLabel :"接收人" },
	               	 {xtype:"datefield" , name:"creationDate_LE" , fieldLabel:"到" , format:"Y-m-d"}
               	],[
	               	 {xtype :"textfield" , name :"keyword" , fieldLabel :"关键字" },
		             {xtype :"textfield",name :"copyToname" , fieldLabel :"抄送人" }
               	]
            ])
    	};
    },
    
     // actions 
    createAction : {
		action : 'create', text: '发消息', iconCls : 'add'
	},
	
    forwardAction : {
		action : 'forward' , text: '转发', iconCls:'option'
    },
    
	getLineContextActions : function(){
		return  [
	         this.forwardAction,
	         this.destroyAction
        ];
	},
    /**自定义页面行为**/
    
    /**自定义页面行为响应**/
    
    forward : function(){
    	var record = ExtUtils.getSelected(this.gridId);
		if(record && record.get('id')){
			var formModule = this.app.getModule(this.formModuleId);
	    	formModule.createWindow({gridId : this.gridId ,action:'edit', readConfig : {readAction : 'forward' , readParams : {message : record.get('id')} }});
		}
    }
	
});
