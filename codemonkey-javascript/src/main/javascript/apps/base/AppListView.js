Ext.define('AM.base.AppListView' ,{
    extend: 'Ext.panel.Panel',

    layout : {
		type : 'vbox',
		align : 'stretch',
		pack  : 'start'
	},

    tbar: [{
        action : 'add',
        iconCls: 'icon-create',
    	text: 'Add'
    }, {
    	action : 'remove',
    	iconCls: 'icon-destroy',
        text: 'Remove'
    }]
	
});
