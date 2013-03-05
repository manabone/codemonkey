
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

Ext.define('erp.MainApp', {
    extend: 'AM.base.AbsDesktopApp',

    requires: [
        'AM.modules.AppRoleListModule',
        'AM.modules.AppRoleFormModule'
        ,
        'AM.modules.AppUserListModule',
        'AM.modules.AppUserFormModule',
        'erp.modules.ItemListModule',
        'erp.modules.ItemFormModule',
        'erp.modules.CustomerListModule',
        'erp.modules.CustomerFormModule'
    ],
    
    shortcutItems : [
        { name: 'roles', iconCls: 'grid-shortcut', module: 'appRoleListModule'},
		{ name: 'users', iconCls: 'grid-shortcut', module: 'appUserListModule'},
		{ name: 'items', iconCls: 'grid-shortcut', module: 'itemListModule'},
		{ name: 'customers', iconCls: 'grid-shortcut', module: 'customerListModule'}
    ],
    
    quickStartItems : [
       { name: 'Accordion Window', iconCls: 'accordion', module: 'appRoleListModule' },
       { name: 'Grid Window', iconCls: 'icon-grid', module: 'fooListModule' }
    ],
    
    getModules : function(){
    	return [
			new AM.modules.AppRoleListModule(),
			new AM.modules.AppRoleFormModule(),
			new AM.modules.AppUserListModule(),
			new AM.modules.AppUserFormModule(),
			new erp.modules.ItemListModule(),
			new erp.modules.ItemFormModule(),
			new erp.modules.CustomerListModule(),
	        new erp.modules.CustomerFormModule()
		];
    }
});
