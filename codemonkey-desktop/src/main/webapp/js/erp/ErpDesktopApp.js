
Ext.Loader.setConfig({
	enabled : true,
	paths : {
		'Ext.ux.desktop' : '../../js/desktop/ux',
		'MyDesktop' : '../../js/desktop',
		'AM.modules' : '../../js/desktop/modules',
		'AM' : '../../js/apps',
		'erp' : '../../js/erp'
	}
});

Ext.define('erp.ErpDesktopApp', {
    extend: 'AM.base.AbsDesktopApp',

    requires: [
		'erp.modules.CustomerListModule',
		'erp.modules.CustomerFormModule',      
               
        'AM.modules.FooListModule',
        'AM.modules.FooFormModule',
        'AM.modules.BarModule'
    ],
    
    shortcutItems : [
		{ name: 'fooList', iconCls: 'grid-shortcut', module: 'fooListModule'},
		{ name: 'bar', iconCls: 'grid-shortcut', module: 'barModule'},
		{ name: 'customerList', iconCls: 'grid-shortcut', module: 'customerListModule'}
    ],
    
    quickStartItems : [
       { name: 'Grid Window', iconCls: 'icon-grid', module: 'fooListModule' }
    ],
    
    getModules : function(){
        return [
			new erp.modules.CustomerListModule(),
			new erp.modules.CustomerFormModule(),  
			
			new AM.modules.FooListModule(),
			new AM.modules.FooFormModule(),
			new AM.modules.BarModule()
        ];
    }
});
