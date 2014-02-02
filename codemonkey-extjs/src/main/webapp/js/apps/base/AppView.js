Ext.define('AM.base.AppView', {
    extend: 'Ext.panel.Panel',

    modelName : null,
    
    layout : {
		type : 'vbox',
		align : 'stretch',
		pack  : 'start'
	},
    
    createModuleAction : function(cfg){
		var me = this;
    	return ExtUtils.createModuleAction(me , cfg);
	},
	
	createMenu : function(actions){
		return ExtUtils.createMenu(actions);
	},
	
	createToolbar : function(actions){
		return ExtUtils.createToolbar(actions);
	},
	
	createButtons : function(actions){
		return ExtUtils.createButtons(actions);
	},
	
	createBbar : function(){
    	
    	var actions = this.buildModuleActions(this.getBbarActions());
    	
    	if(actions){
    		return this.createToolbar(actions);
    	}
    },
    
    buildModuleActions : function(acts){
		if(acts){
			var actions = [];
			for(var i = 0 ; i < acts.length ; i++){
				actions.push(this.createModuleAction(acts[i]));
			}
			return actions;
		}
		return null;
	}
});
