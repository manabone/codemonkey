Ext.define('AM.modules.TreeModule', {
	
	extend: 'Ext.ux.desktop.Module',
	
	hidden : true,
	
	id : 'treeModule',
	
	winTitle : 'tree',
	
	modelName : 'tree',
    
	iconText : null,
	iconCls : null,
	
    createWindowItem : function(){
    	var me = this;
    	var store = ExtUtils.treeStore({modelName : me.modelName});
    	store.load();
    	
         // create the Tree
         var tree = Ext.create('Ext.tree.Panel', {
             store: store,
             hideHeaders: true,
             rootVisible: true,
             viewConfig: {
                 plugins: [{
                     ptype: 'treeviewdragdrop'
                 }]
             },
             height: 350,
             width: 400,
             title: 'Directory Listing',
             collapsible: true
         });
         
         return tree;
    }
    
});