Ext.define('AM.chart.XYChart', {
	
	extend : 'Ext.chart.Chart',
	
	modelName : 'ExampleData',
	
	searchParams : {},
	
	xFieldArray : ['name'],
	yFieldArray : ['data1'],
	
	xField : 'name',
	yField : 'data1',
	
	modelFields : ['name' , 'data1'],
	
	xtype : 'chart',
    animate : false,
    insetPadding : 30,
    legend : {
		position : 'right'
	},
    seriesType : null,
    
    initComponent : function(){
    	
    	var me = this;
    	
    	this.Model = ExtUtils.createModel({
        	modelName : this.modelName , 
        	modelFields : this.modelFields
        });

    	this.store = ExtUtils.ajaxStore({
    		model: this.modelName,
    		autoLoad : true,
    		listeners : {
    			'beforeload' : function( cmp , operation, eOpts ){
    				var proxy = cmp.getProxy();
    	    		Ext.apply(proxy.extraParams , me.searchParams);
    			}
    		}
    	});
    	
    	this.axes = [{
            type: 'Numeric',
            minimum: 0,
            position: 'left',
            fields: this.yFieldArray,
            title: false,
            grid: true
        },{
            type: 'Category',
            position: 'bottom',
            fields: this.xFieldArray,
            title: false
        }];
    	
    	this.series = [{
            type: this.seriesType,
            axis: 'left',
            highlight: true,
            tips: {
              trackMouse: true,
              width: 140,
              height: 28
            },
            label: {
              display: 'insideEnd',
              'text-anchor': 'middle',
                field: 'data1',
                renderer: Ext.util.Format.numberRenderer('0'),
                orientation: 'vertical',
                color: '#333'
            },
            xField: this.xField,
            yField: this.yField
        }];
    	
    	this.callParent();
    }
	
});