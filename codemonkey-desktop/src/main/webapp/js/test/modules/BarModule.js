
Ext.define('test.modules.BarModule', {
    extend: 'AM.modules.MModule',

    id:'barModule',
    
    hidden : true,
    
    winTitle : 'Bar',
    
    modelName : 'Bar',
    
	iconText : 'Bar',
	iconCls : 'icon-grid',
	
	createWindowItem : function(){
		var url = NS.url('bar');
		var config = {
			html : '<iframe border="0" width="100%" height="500px" src="' +url + '"></iframe>'
		};
		return config;
	}
});
