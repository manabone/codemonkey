<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>

<!DOCTYPE html>

<html>
	<head>
	    <title>大连华阳企业管理平台</title>
	    <meta http-equiv="Cache-Control" content="no-store"/>
	    <meta http-equiv="Pragma" content="no-cache"/>
	    <meta http-equiv="Expires" content="0"/>
	    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
	    <script type="text/javascript" src="${ctx}/js/extjs41/ext-all-debug.js"></script>
	    <script type="text/javascript" src="${ctx}/js/ajax-pushlet-client.js"></script>
	    
	    <script type="text/javascript" src="${ctx}/js/swfupload/swfupload.js"></script>
	    
	    <c:if test="${locale != 'en'}">
			<script type="text/javascript" src="${ctx}/js/extjs41/ext-lang-${locale}.js"></script>
		</c:if>
		
	    <link rel="stylesheet" type="text/css" href="${ctx}/css/resources/css/ext-all-${theme}.css" />
	    <link rel="stylesheet" type="text/css" href="${ctx}/css/resources/css/icon.css" />
	    <link rel="stylesheet" type="text/css" href="${ctx}/css/example.css" />
	    
	    <link rel="stylesheet" type="text/css" href="${ctx}/css/resources/css/CheckHeader.css" />
	    <link rel="stylesheet" type="text/css" href="${ctx}/css/resources/css/GroupTabPanel.css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/css/resources/css/ItemSelector.css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/css/resources/css/LiveSearchGridPanel.css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/css/resources/css/TabScrollerMenu.css" />

		<script type="text/javascript" src="${ctx}/js/i18n/i18n-${locale}.js"></script>

		<script type="text/javascript" src="${ctx}/js/apps/base/SearchingSelect.js"></script>
		<script type="text/javascript" src="${ctx}/js/apps/base/TreeCombo.js"></script>
		<script type="text/javascript" src="${ctx}/js/apps/base/SelectField.js"></script>
		<script type="text/javascript" src="${ctx}/js/apps/base/LinkedColumn.js"></script>
		<script type="text/javascript" src="${ctx}/js/apps/base/BsUnioncodeInfoField.js"></script>
		<script type="text/javascript" src="${ctx}/js/apps/base/DateTimeField.js"></script>
		
		<script type="text/javascript" src="${ctx}/js/ExtUtils.js"></script>
		<script type="text/javascript" src="${ctx}/js/ExtFix.js"></script>
		<script type="text/javascript" src="${ctx}/js/NamingStrategy.js"></script>
		<script type="text/javascript" src="${ctx}/js/ExtConstants.js"></script>
	    <decorator:head/>
	</head>
	<body>
		<input type="hidden" id="ctx" value="${ctx}">
		<decorator:body/>
	</body>
</html>
