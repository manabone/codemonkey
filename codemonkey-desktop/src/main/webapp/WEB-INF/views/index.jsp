<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<head>
    <script type="text/javascript">
		Ext.Loader.setConfig({enabled:true});
		var PAGE_DATA = ${pageData};
		ExtUtils.app({
			indexView : '${indexView}',
		    controllers: [
		        '${controllers}' 
		    ]
		});
    </script>
</head>
