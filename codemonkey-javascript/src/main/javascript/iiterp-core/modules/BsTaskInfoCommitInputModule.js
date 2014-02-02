
Ext.define('iiterp-core.modules.BsTaskInfoCommitInputModule', {
	/**继承**/
    extend: 'AM.modules.FormModule',

	/**常量**/
    id:'BsTaskInfoCommitInputModule',
    
    winTitle : '任务管理',
    
    modelName : 'BsTaskInfo',
    
    /**自定义常量**/
    baseInfoPanel_col1 : [
                          {xtype:"textfield" , name:"taskKind" , fieldLabel:"任务类型" , labelWidth:90 , readOnly : true}
                       ],                        
    baseInfoPanel_col2 : [
                    	  {xtype:"textfield" , name:"fromStaffId" , fieldLabel:"发送人" , labelWidth:90 , readOnly : true}
                       ],     
    baseInfoPanel_col3 : [
                          {xtype:"textfield" , name:"taskState" , fieldLabel:"任务状态" , labelWidth : 90 , readOnly : true}
                       ],                        
    baseInfoPanel_col4 : [
                    	  {xtype:"datetimefield" , name:"createDate" , format:"Y-m-d" , fieldLabel:"创建时间 " , labelWidth : 90 , readOnly : true}
                       ],     
    baseInfoPanel_col5 : [
                          {xtype:"datefield" , name:"finishDate" , format:"Y-m-d" , fieldLabel:"要求结束时间" , labelWidth : 90 , readOnly : true}
                       ],                        
    baseInfoPanel_col6 : [
                    	  {xtype:"textfield" , name:"needHours" , fieldLabel:"需要的小时数" , labelWidth:90 , readOnly : true}
                       ],
                       
    /**实现父类方法**/
    modelFields : function(){
    
    	return ['taskTitle','receivers','copys','taskDetails','createDate','finishDate'].concat(ExtUtils.defaultModelFields);
    },
    
    formItems : function(){
        
    	var baseInfoPanel = ExtUtils.panel({
    		title :  '添加',
    		items : [
    		         {xtype:"textfield" , name:"id" , fieldLabel:"id" , width:1200 , hidden: true},
                     ExtUtils.columnLayout([this.baseInfoPanel_col1, this.baseInfoPanel_col2, this.baseInfoPanel_col3]),
                     ExtUtils.columnLayout([this.baseInfoPanel_col4, this.baseInfoPanel_col5, this.baseInfoPanel_col6]),
                     {xtype:"textfield" , name:"taskTitle" , fieldLabel:"任务标题" , labelWidth:100 , width:1200 },
                     {xtype:"htmleditor", name:"taskDetails", fieldLabel:"任务详细描述", labelWidth:100 ,  width:1200},
                     {xtype:"htmleditor", name:"taskReport", fieldLabel:"工作情况报告",  labelWidth:100 , width:1200},
                     {xtype:"uploadpanel", id:"uploadPanel", width:1200 , modelName : 'bsUploadInfoList'}
    		]    		
   		});
    	return ExtUtils.fitLayout([baseInfoPanel]);
 	},
    
	/**覆盖父类方法**/
    save_callback : function(){
    	this.cancel();
    },
    

    
    commitInputSaveAction : {
		action : 'commitInputSave' , text: '提交任务', iconCls:'option'
    },

    createBbar : function(){
    	
    	var actions = [
			this.createModuleAction(this.commitInputSaveAction),
			this.createModuleAction(this.cancelAction)
		];
    	
    	return this.createToolbar(actions);
    },
    
    manageFields : function(form , model){
    	this.callParent();
    	form.findField('taskKind').setReadOnly(true);
    	form.findField('fromStaffId').setReadOnly(true);
    	form.findField('taskState').setReadOnly(true);
    	form.findField('createDate').setReadOnly(true);
    	form.findField('finishDate').setReadOnly(true);
    	form.findField('needHours').setReadOnly(true);  
    	form.findField('taskTitle').setReadOnly(true);
    	form.findField('taskDetails').setReadOnly(true);
    },
    
    /**自定义页面行为**/
    commitInputSave : function(){
    	var me = this;
    	
    	if(!ExtUtils.validateForm(me.formId)){
			return;
		}
		
		var values = ExtUtils.formValues(me.formId);
    	
    	ExtUtils.doAction(me.modelName , 'commitInputSave' , values , function(){
    		me.cancel();
    	});
    }
    /**自定义页面行为响应**/
});
