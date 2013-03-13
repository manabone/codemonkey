
Ext.Loader.setConfig({
	enabled : true,
	paths : {
		'Ext.ux.desktop' : '../../js/desktop/ux',
		'MyDesktop' : '../../js/desktop',
		'AM.modules' : '../../js/desktop/modules',
		'AM' : '../../js/apps',
		'test' : '../../js/test'
	}
});

Ext.define('test.MainApp', {
    extend: 'AM.base.AbsDesktopApp',

    requires: [
        'test.modules.FooListModule',
        'test.modules.FooFormModule',
        'test.modules.BarModule',
        'test.modules.TreeModule',
        'test.modules.TreeGridModule',
        'test.modules.UploadFileModule'
    ],
    
    shortcutItems : [
		{ name: 'Foo List', iconCls: 'grid-shortcut', module: 'fooListModule'},
		{ name: 'Bar', iconCls: 'grid-shortcut', module: 'barModule'},
		{ name: 'Tree grid' , iconCls: 'grid-shortcut', module: 'treeModule'},
		{ name: 'Tree grid', iconCls: 'grid-shortcut', module: 'treeGridModule'},
		{ name: 'File upload', iconCls: 'grid-shortcut', module: 'uploadFileModule'},
    ],
    
    quickStartItems : [
       { name: 'Accordion Window', iconCls: 'accordion', module: 'barModule' },
       { name: 'Grid Window', iconCls: 'icon-grid', module: 'fooListModule' }
    ],
    
    getModules : function(){
        return [
			new test.modules.FooListModule(),
			new test.modules.FooFormModule(),
			new test.modules.BarModule(),
			new test.modules.TreeModule(),
			new test.modules.TreeGridModule(),
			new test.modules.UploadFileModule()
        ];
    }
});