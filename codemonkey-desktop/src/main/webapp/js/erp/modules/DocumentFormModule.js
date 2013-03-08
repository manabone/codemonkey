
Ext.define('erp.modules.DocumentFormModule', {
    extend: 'AM.modules.FormModule',

    id:'documentFormModule',
    
    lineGridId : null,
    
    addLineAction : {
		action : 'addLine', text: 'add', iconCls : 'add'
	},
	
    removeLineAction : {
		action : 'removeLine', text: 'remove', iconCls : 'remove'
	},
	
	addLine : function(){
		ExtUtils.addLine(this.getLineGrid());
	},
    
	removeLine : function(){
		ExtUtils.removeLine(this.getLineGrid());
	},
    
    afterModelLoad : function(model){
 	   this.getLineGridStore().loadData(model.data.lines);
    },
    
    getLineGrid : function(){
    	return Ext.getCmp(this.lineGridId);
    },
    
    getLineGridStore : function(){
    	return this.getLineGrid().getStore();
    },
    
    createBbar : function(){
    	var actions = [
			this.createModuleAction(this.addLineAction),
			this.createModuleAction(this.removeLineAction),
			this.spacer,
			this.createModuleAction(this.saveAction),
			this.createModuleAction(this.cancelAction)
		];
    	
    	return this.createToolbar(actions);
    },
    
    beforeSave : function(values){
    	var toModifyLines = ExtUtils.getModifedData(this.getLineGrid());
    	var toDeleteLines = ExtUtils.getDeletedData(this.getLineGrid());
    	Ext.apply(values , {
    		TO_MODIFY_LINES : toModifyLines,
    		TO_DELETE_LINES : toDeleteLines	
    	});
    }
        
});
