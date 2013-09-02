<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<head>
	<script type="text/javascript" src="${ctx}/js/desktop/modules/MModule.js"></script>
	
	<script type="text/javascript">
		
		Ext.Loader.setConfig({
			enabled : true,
			paths : {
				'Ext.ux.desktop' : '../../../js/desktop/ux',
				'MyDesktop' : '../../../js/desktop',
				'AM.modules' : '../../../js/desktop/modules',
				'AM' : '../../../js/apps',
				'test' : '../../../js/test'
			}
		});
		
		var PAGE_DATA = ${pageData};
		Ext.onReady(function(){
			Ext.create('Ext.container.Viewport', {
			    layout: 'fit',
			    items: [{
			        region: 'center',
			        xtype: 'mmodule', // TabPanel itself has no title
			        config : PAGE_DATA
			    }]
			});
		});
	</script>
</head>
<body>
</body>