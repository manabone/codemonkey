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
	                fieldLabel:'Username', 
	                name:'username', 
	                allowBlank:false,
	                value : 'admin'
	            },{ 
	                fieldLabel:'Password', 
	                name:'password', 
	                inputType:'password', 
	                allowBlank:false,
	                value : 'admin'
	            },{
	            	editable: false,
	                fieldLabel: 'Module',
	                value: 'iiterp-hr',
	                name : 'module',
	                xtype: 'combo',
	                store: [
	                    ['test', '测试'],
	                    ['iiterp-hr', '人力资源']
	                ]
	            }],
		 
		        buttons:[{ 
	                text:'Login',
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
