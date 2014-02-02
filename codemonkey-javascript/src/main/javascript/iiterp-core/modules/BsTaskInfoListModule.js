
Ext.define('iiterp-core.modules.BsTaskInfoListModule', {
    /**继承**/
    extend: 'AM.modules.ListModule',

	/**常量**/
    id:'bsTaskInfoListModule',
    
    requires : ['iiterp-core.modules.BsTaskInfoFormModule'],
    
    winTitle : '任务管理',
    
    modelName : 'BsTaskInfoList',
    
    formModuleId : 'bsTaskInfoFormModule',
    
    formModule : 'BsTaskInfo',
	
	/**自定义常量**/
    
    /**实现父类方法**/
    modelFields : function(){
    	return ['box','fromStaffId','toStaffId'].concat(ExtUtils.defaultModelFields);
    },
	
	gridCols  : function() {
		return [
                {header: 'id', dataIndex: 'id' , hidden : true},
                {header: 'appUserfromId', dataIndex: 'appUserfromId' , hidden : true},
                {header: 'appUsertoId', dataIndex: 'appUsertoId' , hidden : true},
                {header: 'resourceSequence', dataIndex: 'resourceSequence' , hidden : true},
		        {dataIndex:"taskTitle",flex:1,header:"任务标题"},		        
		        {dataIndex:"taskKind",flex:1,header:"任务类型"},
				{dataIndex:"taskState",flex:1,header:"任务状态"},
				{dataIndex:"appUserfromName",flex:1,header:"发送人"},
				{dataIndex:"appUsertoName",flex:1,header:"接收人"},				
				{dataIndex:"finishDate",flex:1,header:"要求结束时间"},				
				{dataIndex:"needHours",flex:1,header:"剩余时间"}
				];
	},
    /**覆盖父类方法**/
    searchForm : function() {
    	return {
    		height: 150,
    		items : ExtUtils.columnLayout([
               	[
               	{xtype :"combo" , name :"box" , fieldLabel :"任务箱" , valueField : 'id' , displayField:"text" , allowBlank : false, editable : false,
               		  	value: 'receive',
						store:new Ext.data.SimpleStore({  
				 			fields:['id', 'text'],  
				 			data:[["receive", "我收到的任务"],["send", "我发出的任务"],["cc", "抄送给我的任务 "]]
						}) 
               	  },
               	{xtype :"textfield",name :"taskTitle" , fieldLabel :"任务标题" },
               	{xtype:"datefield" , name:"createDateBegin" , fieldLabel:"创建时间" , format:"Y-m-d"}
               	],[
               	{xtype :"textfield",name :"fromStaffId" , fieldLabel :"发送人" },
               	{xtype :"combo" , name :"taskKind" , fieldLabel :"任务类型" , valueField : 'id' , displayField:"text" , allowBlank : false, editable : false,
           		  	value: '',
					store:new Ext.data.SimpleStore({  
			 			fields:['id', 'text'],  
			 			data:[['','全部'],["taskKindAssigned", "交办任务"],["taskKindFeedback", "信息反馈单任务"]]
					}) 
           	     },
           	    {xtype:"datefield" , name:"createDateEnd" , fieldLabel:"到" , format:"Y-m-d"}
               	],[
                {xtype :"textfield",name :"toStaffId" , fieldLabel :"接收人" },
                {xtype :"selectfield" , name :"taskState" , model : 'TaskStateKind' , allowEmptyOption : true , fieldLabel :"任务状态" }
                ]                
            ])
    	};
    },
    
    // actions 	
    checkInputAction : {
		action : 'checkInput' , text: '评审', iconCls:'option'
    },

    commitInputAction : {
		action : 'commitInput' , text: '提交', iconCls:'option'
    },
    
    transformInputAction : {
		action : 'transformInput' , text: '转发', iconCls:'option'
    },
    
    feedbackInputAction : {
		action : 'feedbackInput' , text: '反馈 ', iconCls:'option'
    },
    
    bsTaskRdAction : {
		action : 'bsTaskRd' , text: '任务跟踪 ', iconCls:'option'
    },
    
	getLineContextActions : function(record){
		var havAction = new Array();
		
		havAction.push(this.showAction);
		havAction.push(this.commitInputAction);
		havAction.push(this.transformInputAction);
		havAction.push(this.editAction);
		havAction.push(this.checkInputAction);
		havAction.push(this.feedbackInputAction);
		
		havAction.push(this.showAction);
		havAction.push(this.bsTaskRdAction);
		if(PAGE_DATA.currentUserId == record.get('toStaffId')){
			havAction.push(this.feedbackInputAction);
			if('进行' == record.get('taskState')) {
				havAction.push(this.commitInputAction);
				havAction.push(this.transformInputAction);
			}
		};
		
		if(PAGE_DATA.currentUserId == record.get('fromStaffId')){
			if('进行' == record.get('taskState')){
				havAction.push(this.editAction);
			} else if('提交' == record.get('taskState')){
				havAction.push(this.checkInputAction);
			}
		};
		
		return havAction;
	},
    /**自定义页面行为**/

    /**自定义页面行为响应**/
	afterWindowCreate : function(config){
	 	this.callParent(); 	
	 	var runner = new Ext.util.TaskRunner(),
	    task = runner.start({
	         run: function(){
				var grid = Ext.getCmp('bsTaskInfoListModule_grid');
				if(grid){					
					var store = grid.getStore();
					var records = store.getRange();
					if(records){
						for(var i = 0 ; i < records.length ; i++){
							var taskState = records[i].get("taskState");
							if(taskState=="进行"){
								var startTime = new Date();
								var calEndTime = new Date(records[i].get("finishDate"));
								var needHours = ExtUtils.countDown(startTime , calEndTime );
								records[i].set("needHours" , needHours);
							} else {
								records[i].set("needHours" , "已结束");
							}
						}
					}
				}
	         },
	         interval: 1000
	    });
    	
    },
    
    checkInput : function(){
		
		var checkInputModule = this.app.getModule('BsTaskInfoCheckInputModule');
		
		var record = ExtUtils.getSelected(this.gridId);

		if(record && record.get('id')){
			checkInputModule.createWindow({gridId : this.gridId ,action:'edit', readConfig : {readAction : 'checkInput' , readParams : {bsTaskInfo :  record.get('id')}}});
		}
	},
	
	commitInput : function(){
		
		var commitInputModule = this.app.getModule('BsTaskInfoCommitInputModule');
		
		var record = ExtUtils.getSelected(this.gridId);

		if(record && record.get('id')){
			
			commitInputModule.createWindow({gridId : this.gridId ,action:'edit', readConfig : {readAction : 'commitInput' , readParams : {bsTaskInfo :  record.get('id')}}});
		}
	},
	
	transformInput : function(){
		var formModule = this.app.getModule(this.formModuleId);
		
		var record = ExtUtils.getSelected(this.gridId);

		if(record && record.get('id')){		
			formModule.createWindow({gridId : this.gridId ,action:'edit', readConfig : {readAction : 'transformInput' , readParams : {bsTaskInfo :  record.get('id')}}});
		}
	},
	
	feedbackInput : function(e){
    	var me = this;
		var record = ExtUtils.getSelected(this.gridId);
		ExtUtils.formWin({
			title : "发送反馈意见",
			height: 300,
		    width: 600,
			modal : true,
			formItems : [
	             {xtype:"textfield", name:"id" , hidden : true , value : record.get('id')},
	             {xtype:"textfield" , name:"fromStaffName" , fieldLabel:"发送人" , value : record.get('toStaffName') , readOnly : true},
	             {xtype:"textfield" , name:"toStaffName" , fieldLabel:"接收人" , value : record.get('fromStaffName') , readOnly : true},
	             {xtype:"textfield" , name:"feedbackTitle" , fieldLabel:"标题" , value : "针对[" + record.get('resourceSequence') +"]号任务的反馈意见" , readOnly : true},
	             {xtype:"textarea", name:"description", fieldLabel:"反馈内容" , allowBlank : false}
			],
			
			formButtons : [{				
				text: '发送',
				handler : function(){
					var form = this.up('form').getForm();
					var win = this.up('window');
					if (form.isValid()) {
						var values = form.getValues();
				    	ExtUtils.doAction(me.formModule , 'feedbackInputSave' , values , function(){
				    		win.close();
				    	ExtUtils.tipMsg(me , '系统提示' , '发送成功！');
				    	});						
					}
			    }
			}
			]
		});	
    },
    
    bsTaskRd : function(){
		var bsTaskRdListModule = this.app.getModule('BsTaskRdListModule');
		
		var record = ExtUtils.getSelected(this.gridId);

		if(record && record.get('id')){		
			bsTaskRdListModule.createWindow({gridId : this.gridId ,action:'edit', readConfig : {readAction : 'listInput' , readParams : {id :  record.get('id')}}});
		}
	}
    
});
