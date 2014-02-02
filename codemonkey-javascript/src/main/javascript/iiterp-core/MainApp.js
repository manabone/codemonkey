
Ext.Loader.setConfig({
	enabled : true,
	paths : {
		'Ext.ux' : 'js/extjs-ux',
		'Ext.ux.desktop' : 'js/desktop/ux',
		'MyDesktop' : 'js/desktop',
		'AM.modules' : 'js/desktop/modules',
		'AM' : 'js/apps',
		'test' : 'js/test',
		'iiterp-core' : 'js/iiterp-core'
	}
});

Ext.define('iiterp-core.MainApp', {
    extend: 'AM.base.AbsDesktopApp',

    /**添加依赖的js类**/
    requires: [
       'AM.modules.ChangePasswordFormModule',
       'iiterp-core.modules.BsMessageInfoListModule',    
       'iiterp-core.modules.BsMessageInfoFormModule',
       'iiterp-core.modules.BsTaskInfoListModule',
       'iiterp-core.modules.BsTaskInfoFormModule'
    ],
    
    /**添加桌面图标**/
    shortcutItems : [
		{ name: '消息', iconCls: 'grid-shortcut', module: 'appRoleListModule'},
		{ name: '任务', iconCls: 'grid-shortcut', module: 'appUserListModule'}
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
    
    /**添加弹出窗口实例**/
    getModules : function(){
        return [
            Ext.create('AM.modules.ChangePasswordFormModule'),
			Ext.create('iiterp-core.modules.BsMessageInfoListModule'),
			Ext.create('iiterp-core.modules.BsMessageInfoFormModule'),
			Ext.create('iiterp-core.modules.BsTaskInfoListModule'),
			Ext.create('iiterp-core.modules.BsTaskInfoFormModule')
        ];
    }
});
