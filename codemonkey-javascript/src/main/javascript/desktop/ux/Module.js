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
    
    onWindowClose : Ext.emptyFn,
    
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
        me.winId = NS.windowId(this.id);
        
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
            
            win.on('close' , function ( panel, eOpts ){
            	me.onWindowClose();
            });
        }else{
        	desktop.taskbar.removeTaskButton(win.taskButton);        	
        	win.taskButton = desktop.taskbar.addTaskButton(win);
        	win.animateTarget = win.taskButton.el;
        }
        win.show();
        
        this.afterWindowCreate(config);
        this.fixWindowOverflowY();
        this.processSecurityComponents();
        if(config && config.callback){
        	config.callback(me);
        }
        return win;
    },
    
    //FIXME bad fix for window overflow
    fixWindowOverflowY : function(){
    	var els = Ext.query('#' + this.id + '_window_form-innerCt');
    	if(els && els[0]){
    		Ext.DomHelper.applyStyles(els[0], {'overflow-y':'auto'});
    	}
    },
    
    processSecurityComponents : function(){
    	if(PAGE_DATA && PAGE_DATA.cmpPermissions){
    		for(var i = 0 ; i < PAGE_DATA.cmpPermissions.length ; i++){
    			var cmps = Ext.ComponentQuery.query(PAGE_DATA.cmpPermissions[i].component_code);
    			if(cmps && cmps[0]){
    				if(PAGE_DATA.cmpPermissions[i].cmpPermissionType == 'Hidden'){
    					cmps[0].hide();
    				}else if(PAGE_DATA.cmpPermissions[i].cmpPermissionType == 'ReadOnly'){
    					cmps[0].readOnly();
    				}
    			}
    		}
    	}
    },
    
    createModuleAction : function(cfg){
		var me = this;
    	return ExtUtils.createModuleAction(me , cfg);
	},
	
	createMenu : function(actions){
		return ExtUtils.createMenu(actions);
	},
	
	createToolbar : function(actions){
		return ExtUtils.createToolbar(actions);
	},
	
	createButtons : function(actions){
		return ExtUtils.createButtons(actions);
	},
	
	getWindow : function(){
		return this.app.getDesktop().getWindow(this.winId);
	}
});
