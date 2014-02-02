Ext.define('AM.modules.EmptyModule', {
	
	extend: 'Ext.ux.desktop.Module',
	
	hidden : true,
	
	id : 'your module id',
	
	winTitle : 'your module title',
    
	iconText : null,
	iconCls : null,
	
	createWindowItem : function(){}
	
});