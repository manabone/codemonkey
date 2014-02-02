
var PopWidth = 150; //信息框宽度
var PopHeight = 250; //信息框高度
var PopBorder = 0; //距屏幕边缘的距离
var PopShow = 100000000; //信息框的显示时间
var PopTop =0;
var showtime, hidetime; 
var oPopup; 
var taskNeedToDoCount = 0;
var taskHasCommitCount = 0;
var taskNeedToCheckCount = 0;
var messageReceiveBoxCount = 0;
var messageSendBoxCount = 0;
var mailReceiveBoxCount = 0;
var mailSendBoxCount = 0;
var taskNeedToDoCount0 = 0;
var taskHasCommitCount0 = 0;
var taskNeedToCheckCount0 = 0;
var messageReceiveBoxCount0 = 0;
var messageSendBoxCount0 = 0;
var mailReceiveBoxCount0 = 0;
var mailSendBoxCount0 = 0;

function popmsg(inparams){
	oPopup=null;
	PopHeight=inparams.length*68;
	var htmltext='';
	for(var i=0;i<inparams.length;i++) { 
			 if(inparams[i].substring(0,inparams[i].indexOf('='))=='taskNeedToDoCount'){
			 	var shul=inparams[i].substring(inparams[i].indexOf('=')+1);
			 	htmltext=htmltext+
			 	 "<table    width='" + PopWidth + "' height='"
			                             + PopHeight/inparams.length + "' border='0' cellpadding='0' cellspacing='0' background='/css/desktop/images/1.jpg' onClick='top.L(\"1\",\"任务管理\",\"任务管理\",\"../pages/BsTaskInfo/list.do?taskState=taskStateAction\",\"000\");'>"
			                             + "<tr><td align='center'><strong><font size=4 color=blue>" + shul + "</font></strong></td></tr></table>";
			}
			
			 if(inparams[i].substring(0,inparams[i].indexOf('='))=='taskNeedToCheckCount'){
			 	var shul=inparams[i].substring(inparams[i].indexOf('=')+1);
			 	htmltext=htmltext+
			 	 "<table style='border: 1 solid #000000' width='" + PopWidth + "' height='"
			                             + PopHeight/inparams.length + "' border='0' cellpadding='0' cellspacing='0' background='/css/desktop/images/2.jpg' onClick='top.L(\"1\",\"任务管理\",\"任务管理\",\"../pages/BsTaskInfo/list.do?taskState=taskStateCommit&box=send\",\"000\");'>"
			                             + "<tr><td align='center'><strong><font size=4 color=blue>" + shul + "</font></strong></td></tr></table>";
			}
			
			 if(inparams[i].substring(0,inparams[i].indexOf('='))=='messageReceiveBoxCount'){
			 	var shul=inparams[i].substring(inparams[i].indexOf('=')+1);
			 	htmltext=htmltext+
			 	 "<table style='border: 1 solid #000000' width='" + PopWidth + "' height='"
			                             + PopHeight/inparams.length + "' border='0' cellpadding='0' cellspacing='0' background='/css/desktop/images/3.jpg' onClick='top.L(\"1\",\"消息管理\",\"消息管理\",\"../pages/BsMessageInfo/list.do\",\"000\");'>"
			                             + "<tr><td align='center'><strong><font size=4 color=green>" + shul + "</font></strong></td></tr></table>";
			}
}                                                                                                                                                                                                                                                                   

		    //信息框的样式
		 if(htmltext!=''){  
		 	 		 oPopup = window.createPopup();                      
			 oPopup.document.body.style.backgroundColor = "transparent" ;
			     oPopup.document.body.innerHTML = htmltext;

		    popshow();
		  oPopup.document.body.onclick = pophide; //单击信息框时开始隐藏
		}
		}

		//信息框显示程序
		function popshow()
		{      
		    //当不足以显示信息框全部时，缩小信息框高度
		    var tmpHeight = PopTop < PopHeight ? PopTop : PopHeight;
		    //信息框定位，screen.width：屏幕宽度、screen.height：屏幕高度
		    oPopup.show(screen.width - (PopWidth + PopBorder), screen.height - PopTop, PopWidth, tmpHeight);
		    if (PopTop < (PopHeight + PopBorder))
		    {
		        PopTop = PopTop + 5; //信息框位置递增
		    } 
		    else
		    {
		       // pophide();
		       setTimeout("pophide();", PopShow); //准备隐藏信息框
		    }
		    showtime = setTimeout("popshow();",10);
		}
		//信息框隐藏程序
		function pophide()
		{
		    if (showtime) 
		    {
		        clearTimeout(showtime); //清除显示时间句柄
		    }
		    var tmpHeight = PopTop < PopHeight ? PopTop : PopHeight;
		    oPopup.show(screen.width - (PopWidth + PopBorder), screen.height - PopTop, PopWidth, tmpHeight);
		    if (PopTop > 0)
		    {
		        PopTop = PopTop - 10;
		        hidetime = setTimeout("pophide();",100);
		    } 
		    else
		    {
		        clearTimeout(hidetime);
		        oPopup.hide();          //完全隐藏信息框
		    }
		}


		function onData(event) 
		{		
		
			var json = event.get("staff"+staffId);
			var data = eval('('+json+')'); 
			var tmp1 = data.needToDoCount;
			var tmp2 =  data.hasCommitCount;
			var tmp3 = data.needToCheckCount;
			var tmp4 = data.messageReceiveBoxCount;
			var tmp5 = data.messageSendBoxCount;
			var tmp6 = data.mailReceiveBoxCount;
			var tmp7 = data.mailSendBoxCount;
			taskNeedToDoCount = tmp1;
			taskHasCommitCount = tmp2;
			taskNeedToCheckCount = tmp3;
			messageReceiveBoxCount = tmp4;
			messageSendBoxCount = tmp5;
			mailReceiveBoxCount = tmp6;
			mailSendBoxCount = tmp7;

			var inparams =new Array();  var i=0;
			if(taskNeedToDoCount>taskNeedToDoCount0){
				inparams[i]='taskNeedToDoCount='+taskNeedToDoCount;i++;
	 
			}
				if(taskNeedToCheckCount>taskNeedToCheckCount0){
					inparams[i]='taskNeedToCheckCount='+taskNeedToCheckCount;i++;
						 
			}	
		 if(messageReceiveBoxCount>messageReceiveBoxCount0){
		 	inparams[i]='messageReceiveBoxCount='+messageReceiveBoxCount;i++;
						
			}
				if(mailReceiveBoxCount>mailReceiveBoxCount0){
					inparams[i]='mailReceiveBoxCount='+mailReceiveBoxCount;i++;
					 
			}	

			popmsg(inparams);
						taskNeedToDoCount0 = tmp1;
			taskHasCommitCount0 = tmp2;
			taskNeedToCheckCount0 = tmp3;
			messageReceiveBoxCount0 = tmp4;
			messageSendBoxCount0 = tmp5;
			mailReceiveBoxCount0 = tmp6;
			mailSendBoxCount0 = tmp7;
			
     	}