
Ext.define('erp.modules.DocumentFormModule', {
    extend: 'AM.modules.FormModule',

    id:'documentFormModule',
    
    lineGridId : null,

	orderLineGridFeatures : [{
		ftype : 'summary'
	}],
	
	orderLineGridPlugins : function(me){
		return [
		       Ext.create('Ext.grid.plugin.CellEditing', {
	     	   clicksToEdit: 1 ,
	    	   listeners: {
	               'beforeedit': function(e) {
	            	   var ownerModule = e.grid.ownerModule;
	            	   var model = ExtUtils.formValues(ownerModule.formId);
	            	   if(model.status != 'Draft'){
	            		   return false;
	            	   }
	            	   return true;
	               }
	    	   }
	       })
       ];
	},
   
    orderLineColumns : function(){ 
    	return [
    	        {header: 'id',  dataIndex: 'id',  flex: 1 , hidden : true},
    	        {header: 'item_text' ,  dataIndex: 'item_text' , hidden : true},
    	        
    	        ExtUtils.searchingColumn({
    	        	header : 'item' ,  
    	        	dataIndex : 'item',
    	        	textDataIndex : 'item_text',
    	        	listModel : 'ItemList',
    	        	lineGridId : this.lineGridId
    	        }),
    	  		
    	  		{header: 'price' ,  dataIndex: 'price',  flex: 1,
    	  			editor: {xtype: 'numberfield'}
    	  		},
    	  		{header: 'taxRate' ,  dataIndex: 'taxRate',  flex: 1},
    	  		{header: 'qty' ,  dataIndex: 'qty', flex: 1, 
    	  			type : 'float',
    	  			summaryType : 'sum',
    	  			editor: {xtype: 'numberfield'} 
    	  		},
    	  		{header: 'amount' ,  dataIndex: 'amount',  flex: 1 , 
    	  			type : 'float',
    	  			summaryRenderer: Ext.util.Format.usMoney,
    	  			summaryType : 'sum'
    	  		},
    	  		{header: 'tax' ,  dataIndex: 'tax',  flex: 1}
    	  	];
    },
    
    postAction : {
		action : 'post', text: 'post', iconCls : 'add'
	},
    
    addLineAction : {
		action : 'addLine', text: 'add', iconCls : 'add'
	},
	
    removeLineAction : {
		action : 'removeLine', text: 'remove', iconCls : 'remove'
	},
	
	create : function(){
		this.getLineGridStore().removeAll();
	},
	
	addLine : function(){
		ExtUtils.addLine(this.getLineGrid());
	},
    
	removeLine : function(){
		ExtUtils.removeLine(this.getLineGrid());
	},
    
    afterModelLoad : function(model){
 	   this.getLineGridStore().loadData(model.data.lines);
    },
    
    getLineGrid : function(){
    	return Ext.getCmp(this.lineGridId);
    },
    
    getLineGridStore : function(){
    	return this.getLineGrid().getStore();
    },
    
    createBbar : function(){
    	var actions = [
    	    this.createModuleAction(this.postAction),          
			this.createModuleAction(this.addLineAction),
			this.createModuleAction(this.removeLineAction),
			this.spacer,
			this.createModuleAction(this.saveAction),
			this.createModuleAction(this.cancelAction)
		];
    	
    	return this.createToolbar(actions);
    },
    
    beforeSave : function(values){
    	var toModifyLines = ExtUtils.getModifedData(this.getLineGrid());
    	var toDeleteLines = ExtUtils.getDeletedData(this.getLineGrid());
    	Ext.apply(values , {
    		toModifyLines : toModifyLines || [],
    		toDeleteLines : toDeleteLines || []	
    	});
    },
    
    post : function(){
    	var me = this;
    	this.doAction('post' , function(result){
    		me.getLineGridStore().loadData(result.data.lines);
    	});
    },
    
    manageControl : function(model){
    	if(model.status != 'Draft'){
    		ExtUtils.manageFields(this.formId , true);
    		ExtUtils.manageButtons(this.winId , true);
    		
    	}else{
    		ExtUtils.manageFields(this.formId , false);
    		ExtUtils.manageButtons(this.winId , false);
    	}
    	
    }
        
});
