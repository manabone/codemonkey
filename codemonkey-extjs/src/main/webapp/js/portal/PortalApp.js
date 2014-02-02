Ext.define('Ext.app.Portal', {
	
    extend: 'Ext.panel.Panel',
    
    alias: 'widget.portalapp',
    
    //requires: [ 'Ext.diag.layout.ContextItem', 'Ext.diag.layout.Context' ],
    uses: ['Ext.app.PortalPanel', 'Ext.app.PortalColumn'],

    getTools: function(){
        return [{
            xtype: 'tool',
            type: 'gear',
            handler: function(e, target, panelHeader, tool){
                var portlet = panelHeader.ownerCt;
                portlet.setLoading('Loading...');
                Ext.defer(function() {
                    portlet.setLoading(false);
                }, 2000);
            }
        }];
    },
	
    initComponent: function(){
		
		var items = this.initPortalItems(this.portalItems);
		
        Ext.apply(this, {
            layout: {
                type: 'border',
                padding: '0 5 5 5' // pad the layout from the window edges
            },
            items: [{
                xtype: 'container',
                region: 'center',
                layout: 'border',
                items: [{
                    xtype: 'portalpanel',
                    region: 'center',
                    items: items
                }]
            }]
        });
        this.callParent(arguments);
    },
    
    /**
     * @param array[{items:[]},{items:[]}]
     */
    initPortalItems : function(array){
    	
    	if(!array){
    		return;
    	}
    	
    	var defaultPortletConfig = {
			listeners: {
				'close': Ext.bind(this.onPortletClose, this)
			}
    	};
    	
    	for(var i = 0 ; i < array.length ; i++){
    		var col = array[i];
    		if(col.items){
    			for(var j = 0 ; j < col.items.length ; j++ ){
    				var colItem = col.items[j];
    				colItem = Ext.apply({} , colItem , defaultPortletConfig);
    			}
    		}
    	}
    	
    	return array;
    },

    onPortletClose: function(portlet) {
        this.showMsg('"' + portlet.title + '" was removed');
    },

    showMsg: function(msg) {
        var el = Ext.get('app-msg'),
            msgId = Ext.id();

        this.msgId = msgId;
        el.update(msg).show();

        Ext.defer(this.clearMsg, 3000, this, [msgId]);
    },

    clearMsg: function(msgId) {
        if (msgId === this.msgId) {
            Ext.get('app-msg').hide();
        }
    }
});