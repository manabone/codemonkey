<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<head>
	<link rel="stylesheet" type="text/css" href="${ctx}/css/desktop/css/desktop.css" />
	<script type="text/javascript" src="${ctx}/js/desktop/MyDesktopApp.js"></script>
	
	<script type="text/javascript">
		Ext.define('OverrideConnection', {
		    override: 'Ext.data.Connection',
		    onStateChange : function(request) {
		        if (request && request.xhr && request.xhr.readyState == 4) {
		            this.clearTimeout(request);
		            this.onComplete(request);
		            this.cleanup(request);
		        }
		    }
		});
	
		Ext.Loader.setConfig({
			enabled : true,
			paths : {
				'Ext.ux.desktop' : '../../js/desktop/ux',
				'MyDesktop' : '../../js/desktop',
				'AM.modules' : '../../js/desktop/modules',
				'AM' : '../../js/apps'
			}
		});	
		
		Ext.form.field.Base.prototype.initComponent = function(){
			var me = this; 
			Ext.form.field.Base.superclass.initComponent.call(this);
			me.subTplData = me.subTplData || {};
			this.addEvents('focus','blur','specialkey');
			// Init mixins
			me.initLabelable();
			me.initField();
			// Default name to inputId
			if (!me.name) {
				me.name = me.getInputId();
			} 
			if(this.allowBlank === false && this.fieldLabel){
				this.fieldLabel += '<font color=red>*</font>';
			}
		};
		
		var PAGE_DATA = {labels:[]};
		
		var myDesktopApp;
		
		Ext.onReady(function() {
			Ext.state.Manager.setProvider(Ext.create('Ext.state.CookieProvider'));
			myDesktopApp = new MyDesktopApp();
		});
	</script>
</head>
<body>

</body>	