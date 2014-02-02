Ext.define('AM.base.LinkedColumn', {
    
	extend: 'Ext.grid.column.Column',
    
	alias: ['widget.linkedcolumn'],
	
	sortKey : null,

	constructor: function() {
        this.getSortParam = function(){
        	return this.dataIndex + "_" + (this.sortKey || "name");
        };
		this.callParent(arguments);
    },
    
    clicklink : function(column , record , value){
    	var v = Ext.decode(value);
    	var module = APP.getModule(v.moduleId);
    	if(module && v.id){
    		module.createWindow({entityId : v.id});
    	}
    },
	
    processEvent: function(type, view, cell, recordIndex, cellIndex, e, record, row) {
    	
        var me = this;
        var key = type === 'keydown' && e.getKey();
        var mousedown = type == 'mousedown';

        if (mousedown || (key == e.ENTER || key == e.SPACE)) {

        	var dataIndex = me.dataIndex;
            var value = record.get(dataIndex);
        	
            // Mousedown on the now nonexistent cell causes the view to blur, so stop it continuing.
            if (mousedown && e.button == 0 ) {
            	 me.clicklink(me, record , value);
            }

            // Selection will not proceed after this because of the DOM update caused by the record modification
            // Invoke the SelectionModel unless configured not to do so
            if (!me.stopSelection) {
                view.selModel.selectByPosition({
                    row: recordIndex,
                    column: cellIndex
                });
            }

            // Prevent the view from propagating the event to the selection model - we have done that job.
            return false;
           
        } else {
            return me.callParent(arguments);
        }
    },

	defaultRenderer : function(v, meta) {
		var me = this;

		var value = Ext.decode(v);
		
		var linkedValue = value.linkText;
		
		if(!me.disabled){
			linkedValue = '<div style="text-decoration:underline;cursor:pointer;color:blue">' + value.linkText + '</div>';
		}
		return linkedValue;
	}
});