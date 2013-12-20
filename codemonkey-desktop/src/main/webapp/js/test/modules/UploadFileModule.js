Ext.define('test.modules.UploadFileModule', {
	
	extend: 'Ext.ux.desktop.Module',
	
	hidden : true,
	
	id : 'uploadFileModule',
	winTitle : 'upload file',
    
	iconText : null,
	iconCls : null,
	
    createWindowItem : function(){
    	
    	var panel = Ext.create('AM.base.FileUploadPanel');
    	
    	return panel;
    }
});