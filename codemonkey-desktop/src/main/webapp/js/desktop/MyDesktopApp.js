
Ext.Loader.setConfig({
	enabled : true,
	paths : {
		'Ext.ux.desktop' : '../../js/desktop/ux',
		'MyDesktop' : '../../js/desktop',
		'AM.modules' : '../../js/desktop/modules',
		'AM' : '../../js/apps'
	}
});

Ext.define('MyDesktopApp', {
    extend: 'AM.base.AbsDesktopApp',

    requires: [
        'AM.modules.AppRoleListModule',
        'AM.modules.AppRoleFormModule',
        'AM.modules.FooListModule',
        'AM.modules.FooFormModule',
        'AM.modules.BarModule',
        'AM.modules.TreeModule',
        'AM.modules.TreeGridModule',
        'AM.modules.UploadFileModule'
    ],
    
    //桌面图标
    shortcutItems : [
        { name: '角色列表', iconCls: 'grid-shortcut', module: 'appRoleListModule'},
		{ name: 'fooList', iconCls: 'grid-shortcut', module: 'fooListModule'},
		{ name: 'bar', iconCls: 'grid-shortcut', module: 'barModule'},
		{ name: '树', iconCls: 'grid-shortcut', module: 'treeModule'},
		{ name: 'tree grid', iconCls: 'grid-shortcut', module: 'treeGridModule'},
		{ name: '上传文件', iconCls: 'grid-shortcut', module: 'uploadFileModule'},
    ],
    
    quickStartItems : [
       { name: 'Accordion Window', iconCls: 'accordion', module: 'appRoleListModule' },
       { name: 'Grid Window', iconCls: 'icon-grid', module: 'fooListModule' }
    ],
    
    getModules : function(){
        return [
			new AM.modules.AppRoleListModule(),
			new AM.modules.AppRoleFormModule(),
			new AM.modules.FooListModule(),
			new AM.modules.FooFormModule(),
			new AM.modules.BarModule(),
			new AM.modules.TreeModule(),
			new AM.modules.TreeGridModule(),
			new AM.modules.UploadFileModule()
        ];
    }
});
