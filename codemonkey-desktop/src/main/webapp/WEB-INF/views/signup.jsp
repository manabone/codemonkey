<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<head>
	<script type="text/javascript">
		Ext.onReady(function(){
		    Ext.QuickTips.init();
		 
			// Create a variable to hold our EXT Form Panel. 
			// Assign various config options as seen.	 
		    var login = new Ext.FormPanel({ 
		        labelWidth:80,
		        url:'${ctx}/app/auth/login', 
		        frame:true, 
		        title:'Please Login', 
		        defaultType:'textfield',
				monitorValid:true,
				standardSubmit : true,
			// Specific attributes for the text fields for username / password. 
			// The "name" attribute defines the name of variables sent to the server.
		        items:[{ 
	                fieldLabel:'用户名', 
	                name:'username', 
	                allowBlank:false,
	                value : '${username}' ? '${username}' : 'admin'
	            },{ 
	                fieldLabel:'密码', 
	                name:'password', 
	                inputType:'password', 
	                allowBlank:false,
	                value : '${password}' ? '${password}' : 'admin'
	            },{
	            	editable: false,
	                fieldLabel: 'Module',
	                value: 'erp',
	                name : 'module',
	                xtype: 'combo',
	                store: [
	                    ['erp', 'erp'],
	                    ['test', 'test']
	                ]
	            }],
		 
		        buttons:[{ 
	                text:'登录',
	                formBind: true,
	                // Function that fires when user clicks the button 
	                handler:function(){ 
	                    login.getForm().submit(); 
	                } 
	            }] 
		    });
		 
		 
			// This just creates a window to wrap the login form. 
			// The login object is passed to the items collection.       
		    var win = new Ext.Window({
		        layout:'fit',
		        width:300,
		        closable: false,
		        resizable: false,
		        plain: true,
		        border: false,
		        items: [login]
			});
			win.show();
		});
	</script>
</head>
