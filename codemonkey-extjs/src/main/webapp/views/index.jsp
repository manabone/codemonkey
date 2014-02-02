<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<html>
	<head>
	
		<c:set var="ctx" value="${pageContext.request.contextPath}"/>
		<c:set var="locale" value="zh_CN"/>
		<c:set var="theme" value="classic"/>
		<c:set var="module" value="iit-me"/>
	    
	    <title>项目名称</title>
	    <meta http-equiv="Cache-Control" content="no-store"/>
	    <meta http-equiv="Pragma" content="no-cache"/>
	    <meta http-equiv="Expires" content="0"/>
	    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
	    <script type="text/javascript" src="${ctx}/js/extjs41/ext-all-debug.js"></script>
	    
	    <script type="text/javascript" src="${ctx}/js/swfupload/swfupload.js"></script>
	    
	    <c:if test="${locale != 'en'}">
			<script type="text/javascript" src="${ctx}/js/extjs41/ext-lang-${locale}.js"></script>
		</c:if>
		
	    <link rel="stylesheet" type="text/css" href="${ctx}/css/resources/css/ext-all-${theme}.css" />
	    <link rel="stylesheet" type="text/css" href="${ctx}/css/resources/css/icon.css" />
	    <link rel="stylesheet" type="text/css" href="${ctx}/css/example.css" />
	    <link rel="stylesheet" type="text/css" href="${ctx}/css/portal.css" />
	    
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
		
		<script type="text/javascript" src="${ctx}/js/apps/base/AppView.js"></script>
		<script type="text/javascript" src="${ctx}/js/apps/base/AppListView.js"></script>
		<script type="text/javascript" src="${ctx}/js/apps/base/AppEditView.js"></script>
		
		<script type="text/javascript" src="${ctx}/js/portal/PortalColumn.js"></script>
		<script type="text/javascript" src="${ctx}/js/portal/PortalDropZone.js"></script>
		<script type="text/javascript" src="${ctx}/js/portal/PortalPanel.js"></script>
		<script type="text/javascript" src="${ctx}/js/portal/Portlet.js"></script>
		<script type="text/javascript" src="${ctx}/js/portal/PortalApp.js"></script>
		
		<script type="text/javascript">
		
			Ext.onReady(function() { 
				 Ext.create('Ext.app.Portal');
			});
		</script>
	</head>
	<body>
		<input type="hidden" id="ctx" value="${ctx}">
	</body>
</html>
