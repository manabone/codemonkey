/*!
 * Ext JS Library 4.0
 * Copyright(c) 2006-2011 Sencha Inc.
 * licensing@sencha.com
 * http://www.sencha.com/license
 */

Ext.define('Ext.ux.desktop.Module', {
    mixins: {
        observable: 'Ext.util.Observable'
    },

    constructor: function (config) {
        this.mixins.observable.constructor.call(this, config);
        this.init();
    },

    afterWindowCreate : Ext.emptyFn,
    
    init : function(){
        this.launcher = {
            text : this.iconText ,
            iconCls : this.iconCls 
        };
        
    },
	
    createWindow : function(config){
    	var me = this;
    	Ext.apply(me , config);
        var desktop = this.app.getDesktop();
        me.winId = this.id + '_window';
        
        var win = desktop.getWindow(me.winId);
        if(!win){
            win = desktop.createWindow({
                id: me.winId,
                title: me.winTitle ,
                items: this.createWindowItem() || []
            });
        }else{
        	 win.taskButton = desktop.taskbar.addTaskButton(win);
        	 win.animateTarget = win.taskButton.el;
        }
        win.show();
        this.afterWindowCreate();
        return win;
    }
});
