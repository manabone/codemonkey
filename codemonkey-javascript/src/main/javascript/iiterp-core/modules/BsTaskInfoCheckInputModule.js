
Ext.define('iiterp-core.modules.BsTaskInfoCheckInputModule', {
	/**继承**/
    extend: 'AM.modules.FormModule',

	/**常量**/
    id:'BsTaskInfoCheckInputModule',
    
    winTitle : '任务管理',
    
    modelName : 'BsTaskInfo',
    
    /**自定义常量**/
    baseInfoPanel_col1 : [
                          {xtype:"textfield" , name:"taskKind" , fieldLabel:"任务类型" , labelWidth:90}
                       ],                        
    baseInfoPanel_col2 : [
                    	  {xtype:"textfield" , name:"fromStaffId" , fieldLabel:"发送人" , labelWidth:90}
                       ],     
    baseInfoPanel_col3 : [
                          {xtype:"textfield" , name:"taskState" , fieldLabel:"任务状态" , labelWidth : 90}
                       ],                        
    baseInfoPanel_col4 : [
                    	  {xtype:"datetimefield" , name:"createDate" , format:"Y-m-d" , fieldLabel:"创建时间 " , labelWidth : 90}
                       ],     
    baseInfoPanel_col5 : [
                          {xtype:"datefield" , name:"finishDate" , format:"Y-m-d" , fieldLabel:"要求结束时间" , labelWidth : 90}
                       ],                        
    baseInfoPanel_col6 : [
                    	  {xtype:"textfield" , name:"needHours" , fieldLabel:"需要的小时数" , labelWidth:90}
                       ],    
    baseInfoPanel_col7 : [
                          {xtype :"selectfield" , name :"taskScoreState" , model : 'TaskScoreStateKind' , allowEmptyOption : false , fieldLabel :"最终结果" ,labelWidth : 90 , value : "完成"}
                       ],     
    baseInfoPanel_col8 : [
                          {xtype:"bsunioncodeinfofield" , name:"duo" , fieldLabel:"多", unioncodeField : 'duo' , allowBlank:false} 
                       ],                        
    baseInfoPanel_col9 : [
                          {xtype:"bsunioncodeinfofield" , name:"kuai" , fieldLabel:"快", unioncodeField : 'kuai' , allowBlank:false} 
                       ],  
    baseInfoPanel_col10 : [
                           {xtype:"bsunioncodeinfofield" , name:"hao" , fieldLabel:"好", unioncodeField : 'hao' , allowBlank:false}
                       ],
    baseInfoPanel_col11 : [
                           {xtype:"bsunioncodeinfofield" , name:"sheng" , fieldLabel:"省", unioncodeField : 'sheng' , allowBlank:false}
                       ], 
                       
    /**实现父类方法**/
    modelFields : function(){
    
    	return ['taskTitle','receivers','copys','taskDetails','createDate','finishDate'].concat(ExtUtils.defaultModelFields);
    },
    
    formItems : function(){
        
    	var baseInfoPanel = ExtUtils.panel({
    		title :  '任务评审',
    		items : [
    		         {xtype:"textfield" , name:"id" , fieldLabel:"id" , width:1200 , hidden: true},
                     ExtUtils.columnLayout([this.baseInfoPanel_col1, this.baseInfoPanel_col2, this.baseInfoPanel_col3]),
                     ExtUtils.columnLayout([this.baseInfoPanel_col4, this.baseInfoPanel_col5, this.baseInfoPanel_col6]),
                     {xtype:"textfield" , name:"taskTitle" , fieldLabel:"任务标题" ,  labelWidth : 100 , width:1200 },
                     {xtype:"htmleditor", name:"taskDetails", fieldLabel:"任务详细描述", labelWidth : 100 , width:1200},
                     {xtype:"htmleditor", name:"taskReport", fieldLabel:"工作情况报告",  labelWidth : 100 , width:1200},
                     ExtUtils.columnLayout([this.baseInfoPanel_col7, this.baseInfoPanel_col8, this.baseInfoPanel_col9, this.baseInfoPanel_col10, this.baseInfoPanel_col11]),
                     {xtype:"htmleditor", name:"taskEvaluate", fieldLabel:"任务评价", labelWidth : 100 , width:1200 , allowBlank:false},	 
                     {xtype:"uploadpanel", id:"uploadPanel", width:1200 , modelName : 'bsUploadInfoList' }
    		]    		
   		});
    	return ExtUtils.fitLayout([baseInfoPanel]);
 	},
    
	/**覆盖父类方法**/
    save_callback : function(){
    	this.cancel();
    },
    
    checkInputSaveAction : {
		action : 'checkInputSave' , text: '任务完成', iconCls:'option'
    },

    createBbar : function(){
    	
    	var actions = [
			this.createModuleAction(this.checkInputSaveAction),
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
    	form.findField('taskReport').setReadOnly(true);
    },
    
    /**自定义页面行为**/
    checkInputSave : function(){
    	var me = this;
    	
    	if(!ExtUtils.validateForm(me.formId)){
			return;
		}
		
		var values = ExtUtils.formValues(me.formId);
    	
    	ExtUtils.doAction(me.modelName , 'checkInputSave' , values , function(){
    		me.cancel();
    	});
    }
    /**自定义页面行为响应**/
});
