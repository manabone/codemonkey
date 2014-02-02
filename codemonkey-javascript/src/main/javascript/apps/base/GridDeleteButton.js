Ext.define('AM.base.GridDeleteButton', {
    
	extend : 'Ext.button.Button',
	
    alias: 'widget.griddeletebutton',
    
    targetGridId : null,
    
    iconCls: 'icon-destroy',
    
    handler : function(){
    	ExtUtils.removeFromGrid(Ext.getCmp(this.targetGridId));
    }
	
});