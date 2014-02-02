Ext.define('iiterp-core.modules.BsMessageChatModule', {
	
	 extend: 'AM.modules.FormModule',
	 
	 id:'bsMessageChatModule',
	 
	 modelName : 'BsMessageChat',
	 
	 modelFields : function(){
    	return ['type' , 'title','content','file','hasRead','senderName','senderDelete','messageKind','receiverName','copyToNames'].concat(ExtUtils.defaultModelFields);
    },
    
    afterWindowCreate : function(config){

    	ExtUtils.reloadStore(this.store , {receiverId : 4});
    },
	 
	 formItems : function(){
	 
	    var me = this;	
	    me.Model = Ext.define(me.modelName , {
	        extend: 'Ext.data.Model',
	        fields: me.modelFields()
		});
	    	 
	    me.store = ExtUtils.ajaxStore({
	    	model : me.modelName,
	    	proxy: ExtUtils.proxy(me.modelName , 'chat')
	    });
	   
    	var p1 = ExtUtils.panel({
			id:'chat-view',
			frame:true,
			collapsible:true,
			width:600,
			height:400,
			title:'PP',
			overflowY : 'auto',
			items: [                                                    
			        Ext.create('Ext.view.View', {
					store: me.store,
					tpl: [
							'<tpl for=".">',
								'<tpl if="type &gt; 0">',
									'<div class="sender" >',
										'<h1>{title}</h1>',
										'<div class="context">{senderName}:{content}</div>',
										'<div class="time">{creationDate}</div>',		
									'</div>',
									'<div class="x-clear"></div>',
								'</tpl>',
								'<tpl if="type &lt; 0">',
									'<div class="recevier" style="float: right;">',
										'<h1>{title}</h1>',
										'<div class="context">{receiverName}:{content}</div>',
										'<div class="time"> {creationDate}</div>',	
									'</div>',
									'<div class="x-clear"></div>',
								'</tpl>',
								'<div class="x-clear"></div>',
							'</tpl>'
							
						]
					})]
		});
			
		var chatInfo_col1 = [
			 {xtype:"textfield" , name:"title" , fieldLabel:"标题" , allowBlank:false , width:450}
		];
		var chatInfo_col2 = [
			 {xtype:"textfield" , name:"receiverName" , fieldLabel:"接收人" , allowBlank:false},
			 {xtype:"textfield" , name:"messageKind" , fieldLabel:"类型" , allowBlank:false}
		];
		var chatInfo_col3 = [
			 {xtype:"textfield" , name:"chaosong" , fieldLabel:"抄送人" , allowBlank:false},
			 {xtype:"checkbox" , name:"message" , fieldLabel:"短信通知" , allowBlank:false}
		];
		var chatInfo_col4 = [
			 {xtype:"textarea" , name:"content" , fieldLabel:"内容" , allowBlank:false , width:450}
		];
		
		var chatInfoPanel1 = ExtUtils.panel({
			collapsed : false,
			collapsible:false,
			padding:0,
			bodyPadding : 0,
			border:0,
			items : ExtUtils.columnLayout([chatInfo_col1])
		});
		
		var chatInfoPanel2 = ExtUtils.panel({
			collapsed : false,
			collapsible:false,
			padding:0,
			bodyPadding : 0,
			border:0,
			items : ExtUtils.columnLayout([chatInfo_col2, chatInfo_col3])
		});
		
		var chatInfoPanel3 = ExtUtils.panel({
			collapsed : false,
			collapsible:false,
			padding:0,
			bodyPadding : 0,
			border:0,
			items : ExtUtils.columnLayout([chatInfo_col4])
		});
		
		
		var p2 = ExtUtils.panel({
			height:400,
			collapsed : false,
			collapsible:false,
			items :ExtUtils.fitLayout([chatInfoPanel1, chatInfoPanel2,chatInfoPanel3 ]),
			bbar: [
				{ xtype: 'button', text: '发送' },
				{ xtype: 'button', text: '取消' }
			]
		});
	    return ExtUtils.fitLayout([p1,p2]);
	}
});




