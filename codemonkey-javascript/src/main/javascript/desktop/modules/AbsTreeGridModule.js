Ext.define('AM.modules.AbsTreeGridModule', {
	
	extend: 'Ext.ux.desktop.Module',
	
	winTitle : null,
	iconText : null,
	iconCls : null,
	
	modelName : null,
	entityId : null,
	
	getColumns : Ext.emptyFn,
	afterTreeLoad : Ext.emptyFn,
	
    createWindowItem : function(){
    	
    	var me = this;
    	var fields = ExtUtils.fieldsFromCols(me.getColumns());
    		
    	Ext.define(me.modelName , {
	        extend: 'Ext.data.Model',
	        fields: fields
	    });
    	
    	var store = ExtUtils.treeStore({
    		model : me.modelName , 
    		root: {  
	            expanded:true,
	            id: me.entityId
	        }
    	});
//    	store.load();
    	// create the Tree
    	var tree = Ext.create('Ext.tree.Panel', {
            width: 500,
            height: 300,
            collapsible: true,
            useArrows: true,
            rootVisible: false,
            store: store,
            multiSelect: true,
            singleExpand: false,
            columns: me.getColumns()
        });
         
        return tree;
    }
    
});