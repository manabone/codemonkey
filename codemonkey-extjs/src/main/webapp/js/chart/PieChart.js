Ext.define('AM.chart.PieChart', {

	extend : 'Ext.chart.Chart',

	alias : 'widget.piechart',

	modelName : 'ExampleData',

	searchParams : {},

	xFieldArray : [ 'name' ],
	yFieldArray : [ 'data1' ],

	xField : 'name',
	yField : 'data1',

	modelFields : [ 'name', 'data1' ],

	xtype : 'chart',
	animate : false,
	insetPadding : 30,
	shadow : true,
	legend : {
		position : 'right'
	},
	insetPadding : 60,
	theme : 'Base:gradients',

	seriesType : null,

	initComponent : function() {

		var me = this;

		this.Model = ExtUtils.createModel({
			modelName : this.modelName,
			modelFields : this.modelFields
		});

		this.store = ExtUtils.ajaxStore({
			model : this.modelName,
			autoLoad : true,
			listeners : {
				'beforeload' : function(cmp, operation, eOpts) {
					var proxy = cmp.getProxy();
					Ext.apply(proxy.extraParams, me.searchParams);
				}
			}
		});

		this.series = [ {
			type : 'pie',
			field : me.yField,
			showInLegend : true,
			tips : {
				trackMouse : true,
				width : 140,
				height : 28,
				renderer : function(storeItem, item) {
					//calculate percentage.
					var total = 0;
					store1.each(function(rec) {
						total += rec.get(me.yField);
					});
					this.setTitle(storeItem.get(me.xField) + ': '
							+ Math.round(storeItem.get(me.yField) / total * 100)
							+ '%');
				}
			},
			highlight : {
				segment : {
					margin : 20
				}
			},
			label : {
				field : me.xField,
				display : 'rotate',
				contrast : true,
				font : '18px Arial'
			}
		} ];

		this.callParent();
	}
});