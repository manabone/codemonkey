Ext.define('Erp.ui.SelectField', {
	extend : 'Ext.form.field.ComboBox',
	alias : 'widget.selectfield',

	valueField:'name',
	displayField:'text',
	queryMode:'local',
	
	initComponent : function() {
		var me = this;
		me.store = me.buildStore();
		Erp.ui.SelectField.superclass.initComponent.call(this);
		me.emptyText = 'Select ...';
		me.matchFieldWidth = false;
	},
	
	buildStore : function(){
		var me = this;
		var d = me.data;
		if(d && d.length > 0 && d[0][0] != '') {
			d.unshift(['','']);
		}
		return new Ext.data.ArrayStore({
		
		    id: 'ComboStore' + me.name, 
		    fields: [
		        me.valueField,
		        me.displayField
		    ],
		    data: d
		});
	}
});