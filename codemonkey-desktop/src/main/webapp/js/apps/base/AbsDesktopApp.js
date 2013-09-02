/*!
 * Ext JS Library 4.0
 * Copyright(c) 2006-2011 Sencha Inc.
 * licensing@sencha.com
 * http://www.sencha.com/license
 */

Ext.require(
	['Ext.window.MessageBox',
    'Ext.ux.desktop.ShortcutModel',
    'MyDesktop.BogusMenuModule',
    'MyDesktop.BogusModule',
    'MyDesktop.Settings']
);

Ext.define('AM.base.AbsDesktopApp', {
    extend: 'Ext.ux.desktop.App',
    
    requires : ['AM.modules.ChangePasswordFormModule'],

    getDesktopConfig: function () {
        var me = this, ret = me.callParent();

        return Ext.apply(ret, {
            //cls: 'ux-desktop-black',

        	//TODO , settings to be a module , click menu item launch module
            contextMenuItems: [ 
                { text: 'Change Settings', handler: this.onSettings, scope: this },
        	    { text: 'app role list', module:'appRoleListModule'}
            ],

            shortcuts: Ext.create('Ext.data.Store', {
                model: 'Ext.ux.desktop.ShortcutModel',
                data: me.shortcutItems
            }),

            wallpaperStretch: true
        });
    },

    // config for the start menu
    getStartConfig : function() {
        var me = this, ret = me.callParent();

        return Ext.apply(ret, {
            title: i18n.donGriffin,
            iconCls: 'user',
            height: 300,
            toolConfig: {
                width: 100,
                items: [
                    {
                        text:i18n.settings,
                        iconCls:'settings',
                        handler: me.onSettings,
                        scope: me
                    },
                    '-',
                    {
                        text:'ChangePassword',
                        iconCls:'change',
                        handler: me.changePassword,
                        scope: me
                    },
                    '-',
                    {
                        text:i18n.logout,
                        iconCls:'logout',
                        handler: me.onLogout,
                        scope: me
                    }
                ]
            }
        });
    },

    getTaskbarConfig: function () {
    	var me = this;
        var ret = this.callParent();

        return Ext.apply(ret, {
            quickStart: me.quickStartItems,
            trayItems: [
                { xtype: 'trayclock', flex: 1 }
            ]
        });
    },
         
    changePassword : function(){
    	var formModule = this.getModule('changePasswordFormModule');
	 	formModule.createWindow();
    },

    onLogout: function () {
        Ext.Msg.confirm(i18n.logout, i18n.tips , function(btn){
        	if(btn == 'yes'){
        		ExtUtils.mask();
        		window.location = '/app/auth/logout';
        	}
        });
    },

    onSettings: function () {
        var dlg = new MyDesktop.Settings({
            desktop: this.desktop
        });
        dlg.show();
    }
});
