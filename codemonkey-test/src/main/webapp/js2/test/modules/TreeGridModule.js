Ext.define('test.modules.TreeGridModule', {
	
	extend: 'Ext.ux.desktop.Module',
	
	hidden : true,
	
	id : 'treeGridModule',
	
	winTitle : 'treeGrid',
	
	modelName : 'treeGrid',
    
	iconText : null,
	iconCls : null,
	
    createWindowItem : function(){
    	var me = this;
    	
    	Ext.define(me.modelName , {
	        extend: 'Ext.data.Model',
	        fields: ['id' , 'text' , 'name']
	    });
    	 
    	var store = ExtUtils.treeStore({modelName : me.modelName , model : me.modelName});
    	store.load();
    	
         // create the Tree
    	var tree = Ext.create('Ext.tree.Panel', {
            title: 'grid tree',
            width: 500,
            height: 300,
            collapsible: true,
            useArrows: true,
            rootVisible: false,
            store: store,
            multiSelect: true,
            singleExpand: false,
            //the 'columns' property is now 'headers'
            columns: [{
                xtype: 'treecolumn', //this is so we know which column will show the tree
                text: 'id',
                flex: 2,
                sortable: true,
                dataIndex: 'id'
            },{
                //we must use the templateheader component so we can use a custom tpl
                //xtype: 'templatecolumn',
                text: 'text',
                flex: 1,
                sortable: true,
                dataIndex: 'text',
                align: 'center'
              
            },{
                text: 'name',
                flex: 1,
                dataIndex: 'name',
                sortable: true
            }]
        });
         
         return tree;
    }
    
});