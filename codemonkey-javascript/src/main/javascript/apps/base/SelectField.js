Ext.define('Erp.ui.SelectField', {
	extend : 'Ext.form.field.ComboBox',
	alias : 'widget.selectfield',

	valueField:'name',
	displayField:'text',
	forceSelection : true,
	allowEmptyOption : null,
	
	editable : false,
	
	initComponent : function() {
		var me = this;
		
		if(me.model){
			me.store = new Ext.data.JsonStore ({
		        fields: ['name' , 'text'],
		        id: "selectStore-" + me.model,
		        autoLoad : true,
		        proxy: ExtUtils.ajaxProxy({model:'Enum'})
		    });
			
			ExtUtils.reloadStore(me.store , {className : me.model , allowEmptyOption : me.allowEmptyOption === false ? false : true});
		}else{
			me.queryMode = 'local';
			me.store = me.buildLocalStore();
		}
		
		Erp.ui.SelectField.superclass.initComponent.call(this);
		me.emptyText = '';
		me.listConfig = {width : 150};
		me.matchFieldWidth = false;
	},
	
	buildLocalStore : function(){
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