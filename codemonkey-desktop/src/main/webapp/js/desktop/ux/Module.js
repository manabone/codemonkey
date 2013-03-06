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
    
    spacer : {xtype : 'tbspacer',flex : 1},

    afterWindowCreate : Ext.emptyFn,
    
    createBbar : Ext.emptyFn,
    
    createTbar : Ext.emptyFn,
    
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
        	
        	var bbar = this.createBbar();
        	var tbar = this.createTbar();
        	
            win = desktop.createWindow({
                id: me.winId,
                title: me.winTitle ,
                items: this.createWindowItem() || [],
            	tbar : tbar,
                bbar : bbar
            });
        }else{
        	desktop.taskbar.removeTaskButton(win.taskButton);        	
        	win.taskButton = desktop.taskbar.addTaskButton(win);
        	win.animateTarget = win.taskButton.el;
        }
        win.show();
        this.afterWindowCreate();
        return win;
    },
    
    createModuleAction : function(cfg){
		var me = this;
    	Ext.apply(cfg , {
			handler: function(){
				if(cfg.action){
					me[cfg.action]();
				}
	        }
		});
		
		return  Ext.create('Ext.Action', cfg);
	},
	
	createMenu : function(module , actions){
		return {
            xtype: 'menu',
            plain: true,
            items : actions
		};
	},
	
	createToolbar : function(actions){
		return {
            xtype: 'toolbar',
            items: actions
            
        };
	},
	
	createButtons : function(actions){
		var buttons = [];
		
		for(var i = 0 ; i < actions.length ; i++){
			buttons.push(Ext.create('Ext.button.Button', actions[i]));
		}
		return buttons;
	}
});
