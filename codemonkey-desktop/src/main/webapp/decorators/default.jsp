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
	    <c:if test="${locale != 'en'}">
			<script type="text/javascript" src="${ctx}/js/extjs41/ext-lang-${locale}.js"></script>
		</c:if>
		
		<script type="text/javascript" src="${ctx}/js/extjs41/examples.js"></script>
		
	    <link rel="stylesheet" type="text/css" href="${ctx}/css/resources/css/ext-all-${theme}.css" />
	    <link rel="stylesheet" type="text/css" href="${ctx}/css/resources/css/icon.css" />
	    <link rel="stylesheet" type="text/css" href="${ctx}/css/example.css" />

		<script type="text/javascript" src="${ctx}/js/i18n/i18n-${locale}.js"></script>

		<script type="text/javascript" src="${ctx}/js/apps/base/SearchingSelect.js"></script>
		<script type="text/javascript" src="${ctx}/js/apps/base/TreeCombo.js"></script>
		<script type="text/javascript" src="${ctx}/js/apps/base/SelectField.js"></script>
		
		
		<script type="text/javascript" src="${ctx}/js/ExtUtils.js"></script>
		<script type="text/javascript" src="${ctx}/js/ExtFix.js"></script>
		<script type="text/javascript" src="${ctx}/js/NamingStrategy.js"></script>
		
	    <decorator:head/>
	</head>
	<body>
		<input type="hidden" id="ctx" value="${ctx}">
		<decorator:body/>
	</body>
</html>
