<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<%@page import="com.codemonkey.utils.ExtConstant"%>

<head>
	<link rel="stylesheet" type="text/css" href="${ctx}/css/desktop/css/desktop.css" />
	<script type="text/javascript" src="${ctx}/js/${module}/MainApp.js"></script>
	<script type="text/javascript" src="${ctx}/js/desktop/ajax-pushlet-client.js"></script>
	
	<script type="text/javascript">
		var PAGE_DATA = ${pageData} ;
		var APP;
		Ext.onReady(function() { 
			
			PL._init(); 	 
			PL.joinListen("/event");
			
			var myMask = new Ext.LoadMask(Ext.getBody(), {
				msg : "正在加载，请稍后....."
			});  
		  	myMask.show();
		  
			Ext.state.Manager.setProvider(Ext.create('Ext.state.CookieProvider'));
		
			APP = Ext.create('${module}.MainApp' , {
				startConfig : {
					menu: ExtUtils.treeMenu(PAGE_DATA.powerTrees)
			    }
			});
			myMask.hide();
		});
	
	</script>
</head>
<body>

</body>
