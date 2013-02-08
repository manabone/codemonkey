<%@ include file="/WEB-INF/common/taglibs.jsp" %>

<!DOCTYPE html>

<html>
	<head>
	    <title><decorator:title default="Welcome"/> | <fmt:message key="webapp.name"/></title>
	    <link rel="shortcut icon" href="${ctx}/css/resources/icon/icon_monkey.png"/>
	    <meta http-equiv="Cache-Control" content="no-store"/>
	    <meta http-equiv="Pragma" content="no-cache"/>
	    <meta http-equiv="Expires" content="0"/>
	    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
	    <script type="text/javascript" src="${ctx}/js/extjs41/ext-all-debug.js"></script>
	    <link rel="stylesheet" type="text/css" href="${ctx}/css/resources/css/ext-all-${theme}.css" />
	    <link rel="stylesheet" type="text/css" href="${ctx}/css/resources/css/icon.css" />

		<script type="text/javascript" src="${ctx}/js/apps/base/ModelExt.js"></script>
		<script type="text/javascript" src="${ctx}/js/apps/base/RestExt.js"></script>
		<script type="text/javascript" src="${ctx}/js/apps/base/SearchingSelect.js"></script>
		<script type="text/javascript" src="${ctx}/js/apps/base/TreeCombo.js"></script>
		<script type="text/javascript" src="${ctx}/js/apps/base/SearchingSelectButton.js"></script>
		<script type="text/javascript" src="${ctx}/js/apps/base/GridDeleteButton.js"></script>
		<script type="text/javascript" src="${ctx}/js/apps/base/SelectField.js"></script>
		<script type="text/javascript" src="${ctx}/js/apps/base/ActionBar.js"></script>
		<script type="text/javascript" src="${ctx}/js/ExtUtils.js"></script>
		<script type="text/javascript" src="${ctx}/js/NamingStrategy.js"></script>
		
		<script type="text/javascript" src="${ctx}/js/apps/base/Portlet.js"></script>
		<script type="text/javascript" src="${ctx}/js/apps/base/PortalColumn.js"></script>
		<script type="text/javascript" src="${ctx}/js/apps/base/PortalDropZone.js"></script>
		<script type="text/javascript" src="${ctx}/js/apps/base/PortalPanel.js"></script>
	    <decorator:head/>
	</head>
	<body>
		<input type="hidden" id="ctx" value="${ctx}">
		<decorator:body/>
	</body>
</html>