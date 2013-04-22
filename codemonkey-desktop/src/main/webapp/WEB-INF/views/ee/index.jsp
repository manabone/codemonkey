<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<head>
	<script type="text/javascript">
		var PAGE_DATA = ${pageData};

		Ext.Loader.setConfig({
			enabled : true,
			paths : {
				'Ext.ux.desktop' : '../../js/desktop/ux',
				'MyDesktop' : '../../js/desktop',
				'AM.modules' : '../../js/desktop/modules',
				'AM.base' : '../../../js/apps/base',
				'AM' : '../../js/apps',
				'erp' : '../../js/erp',
				'test' : '../../../js/test',
			}
		});
		
		Ext.onReady(function(){
			Ext.create('AM.base.PortalApp');
		});
	</script>
</head>
<body>
</body>