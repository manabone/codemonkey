
Ext.define('iiterp-core.modules.BsTaskRdListModule', {
    /**继承**/
    extend: 'AM.modules.ListModule',

	/**常量**/
    id:'BsTaskRdListModule',
    
    winTitle : '任务跟踪',
    
    modelName : 'BsTaskRdList',
    
	
	/**自定义常量**/
    
    /**实现父类方法**/
	
	gridCols  : function() {
		return [
		        {dataIndex:"taskTitle",flex:1,header:"操作人"},		        
		        {dataIndex:"taskKind",flex:1,header:"操作类型"},
				{dataIndex:"finishDate",flex:1,header:"操作时间"},			
				{dataIndex:"taskState",flex:1,header:"当前任务ID"},
				{dataIndex:"fromStaffName",flex:1,header:"原任务ID"},
				{dataIndex:"toStaffName",flex:1,header:"反馈消息ID"}
				];
	}

    /**自定义页面行为**/

    /**自定义页面行为响应**/
    
});
