
Ext.define('iiterp-core.modules.BsTaskInfoFormModule', {
	/**继承**/
    extend: 'AM.modules.FormModule',

	/**常量**/
    id:'bsTaskInfoFormModule',
    
    winTitle : '任务管理',
    
    modelName : 'BsTaskInfo',
    
    /**自定义常量**/
    baseInfoPanel_col1 : [
                          {xtype:"datefield" , name:"createDate" , format:"Y-m-d" , fieldLabel:"创建时间" , labelWidth : 90}
                       ],                        
    baseInfoPanel_col2 : [
                    	  {xtype:"datetimefield" , name:"finishDate" , format:"Y-m-d" , fieldLabel:"要求结束时间 " , width : 300 , labelWidth : 90 , allowBlank:false}
                       ],  
    baseInfoPanel_col3 : [
                          {xtype:"checkbox", name:"smsNotice", fieldLabel:"短信通知"}
                       ],     
    /**实现父类方法**/
    modelFields : function(){
    
    	return ['taskTitle','receivers','copys','taskDetails','createDate','finishDate','uploadEntries','smsNotice','originalTaskId'].concat(ExtUtils.defaultModelFields);
    },
    
    formItems : function(){
        
    	var baseInfoPanel = ExtUtils.panel({
    		title :  '添加',
    		items : [
    		         {xtype:"textfield" , name:"originalTaskId" , fieldLabel:"originalTaskId" , hidden : true},
                     {xtype:"textfield" , name:"taskTitle" , fieldLabel:"任务标题" , width:1000 , allowBlank:false},
                     {xtype:"htmleditor", name:"taskDetails", fieldLabel:"任务内容", width:1000 , allowBlank:false},                 
    		         ExtUtils.columnLayout([this.baseInfoPanel_col1, this.baseInfoPanel_col2 , this.baseInfoPanel_col3]),
                     {xtype:"selectperson", id:"receivers" , title:"接收人" , allowBlank:false , width:1000 },
                     {xtype:"selectperson", id:"copys" , title:"抄送人",width:1000 },
                     {xtype:"uploadpanel", id:"uploadPanel", width:1000 , modelName : 'bsUploadInfoList' }        
    		]    		
   		});
    	return ExtUtils.fitLayout([baseInfoPanel]);
 	},
    
	/**覆盖父类方法**/
    beforeSave : function(values){
    	debugger;
       	values.receivers = Ext.getCmp("receivers").getValues(); 
       	values.copys = Ext.getCmp("copys").getValues(); 
       	var panel = Ext.getCmp("uploadPanel");
    	values.uploadEntries = panel.getValue();
    	return values;
    }, 
    
    save_callback : function(){
    	this.cancel();
    },
    
    afterModelLoad : function(model){
    	Ext.getCmp("uploadPanel").store.loadData(model.data.uploadEntries);
    	Ext.getCmp("receivers").grid.getStore().loadData(model.data.receivers || []);
    	Ext.getCmp("copys").grid.getStore().loadData(model.data.copys || []);
    },
    
    
    manageFields : function(form , model){
    	this.callParent();
    	form.findField('createDate').setReadOnly(true);
    }
    /**自定义页面行为**;
    
    /**自定义页面行为响应**/
});
