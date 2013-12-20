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
	    
	    <link rel="stylesheet" type="text/css" href="${ctx}/css/bootstrap-3.0.0.min.css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/css/bootstrap-theme-3.0.0.min.css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/css/base.css" />
		
		<script type="text/javascript" src="${ctx}/js/jquery-2.0.3.min.js"></script>
	    <script type="text/javascript" src="${ctx}/js/bootstrap-3.0.0.min.js"></script>
		
		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	    <!--[if lt IE 9]>
	      <script src="${ctx}/js/html5shiv.js"></script>
	      <script src="${ctx}/js/respond.min.js"></script>
	    <![endif]-->
    
	    <decorator:head/>
	</head>
	<body>
		<input type="hidden" id="ctx" value="${ctx}">
		<decorator:body/>
	</body>
</html>
