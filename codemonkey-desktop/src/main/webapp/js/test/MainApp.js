
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
        
//        'test.modules.Foo2ListModule',
//        'test.modules.Foo2FormModule',
        
        'test.modules.BarModule',
        'test.modules.TreeModule',
        'test.modules.TreeGridModule',
        'test.modules.UploadFileModule'
    ],
    
    shortcutItems : [
//        { name: 'Foo2 List', iconCls: 'grid-shortcut', module: 'foo2ListModule'},
        
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
    
    startConfig : {
    	menu: [{
    		 text: 'erp',
    		 handler: function(){
	            window.location = '?module=erp';
	        }
    	}]
    },
    
    getModules : function(){
        return [
//			new test.modules.Foo2ListModule(),
//			new test.modules.Foo2FormModule(),
			
			new test.modules.FooListModule(),
			new test.modules.FooFormModule(),
			new test.modules.BarModule(),
			new test.modules.TreeModule(),
			new test.modules.TreeGridModule(),
			new test.modules.UploadFileModule()
        ];
    }
});
