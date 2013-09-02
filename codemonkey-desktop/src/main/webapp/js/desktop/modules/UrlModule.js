
Ext.define('AM.modules.UrlModule', {
    extend: 'Ext.ux.desktop.Module',

    createWindowItem : function(){
		var url = NS.url(this.modelName);
		var config = {
			html : '<iframe width="100%" height="500px" style=\"border:0px;\"" src="' +url + '"></iframe>'
		};
		return config;
	},
	
	createWindow : function(config){
		var win = this.callParent(config);
//		win.on('resize' , function( cmp, width, height, oldWidth, oldHeight, eOpts ){
//			var iframe = Ext.query("#" + cmp.el.id + ' iframe');
//			if(iframe && iframe[0]){
//				iframe[0].width = width;
//				iframe[0].height = height;
//			}
//		});
		return win;
	}

});
