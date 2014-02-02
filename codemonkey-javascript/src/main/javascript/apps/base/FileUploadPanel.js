
Ext.define('AM.base.FileUploadPanel',{
	extend : 'Ext.grid.Panel',
	alias : 'widget.uploadpanel',
	width : 700,
	height : 300,
	
	idProperty : 'id',
	
	columns : [
        {xtype: 'rownumberer'},
        {text: 'id', hidden:true , width: 100,dataIndex: 'id'},
        {text: 'fileId', hidden:true , width: 100,dataIndex: 'fileId'},
		{text: '文件名', width: 100,dataIndex: 'fileName'},
        {text: '类型', width: 70,dataIndex: 'fileType'},
        {text: '大小', width: 70,dataIndex: 'fileSize',
        	renderer:function(v){
        		return Ext.util.Format.fileSize(v);
        	}
        },
        {text: '进度', width: 130,dataIndex: 'percent',
        	renderer:function(v){
				var stml =
					'<div>'+
						'<div style="border:1px solid #008000;height:10px;width:115px;margin:2px 0px 1px 0px;float:left;">'+		
							'<div style="float:left;background:#FFCC66;width:'+v+'%;height:8px;"><div></div></div>'+
						'</div>'+
					//'<div style="text-align:center;float:right;width:40px;margin:3px 0px 1px 0px;height:10px;font-size:12px;">{3}%</div>'+			
				'</div>';
				return stml;
        	}
        },
        {text: '状态', width: 80,dataIndex: 'status',
    		renderer:function(v){
				var status = "";
				if(v==-1){
					status = "等待上传";
				}else if(v==-2){
					status =  "上传中...";
				}else if(v==-3){
					status =  "<div style='color:red;'>上传失败</div>";
				}else if(v==-4){
					status =  "上传成功";
				}else if(v==-5){
					status =  "停止上传";
				}		
				return status;
			}
    	},
        {
    		text: '操作',
            xtype:'actioncolumn',
            width:50,
            items: [{
                icon: '../../css/resources/icon/download.png',
                tooltip: '下载',
                handler: function(gridView , rowIndex, colIndex) {
                	var grid = gridView.up('grid');
                	var id = grid.store.getAt(rowIndex).get('id');
            		if(id){
            			ExtUtils.urlDoAction(grid.modelName ,'download' , "id="+id);
                	}
                }
            },{
                icon: '../../css/resources/icon/destroy_16.png',
                tooltip: '删除',
                handler: function(gridView, rowIndex, colIndex) {
                	var grid = gridView.up('grid');
                	var uploadIds = [];
                	var id = grid.store.getAt(rowIndex).get('id');
                	var status = grid.store.getAt(rowIndex).get('status');
                	if(status == -2){
                		//上传中
                		var fileId = grid.store.getAt(rowIndex).get('fileId');
                		grid.swfupload.cancelUpload(fileId,false);
                		grid.store.remove(grid.store.getAt(rowIndex));
    				}else if( status == -4){
    					//上传成功
    					uploadIds.push(id);
                		ExtUtils.doAction(grid.modelName ,'delete' , {uploadIds : uploadIds} , 
                        		function(result){
                					if(result.success){
                						grid.store.remove(grid.store.getAt(rowIndex));
                					}
                        		}
                        );
    				}else{
    					grid.store.remove(grid.store.getAt(rowIndex));
    				}		
                }
            }]
        }
    ],
    store : Ext.create('Ext.data.JsonStore',{
    	autoLoad : false,
    	fields : ['id', 'fileId' , 'fileType','fileSize','percent','status','fileName']
    }),
    title : '附件',  
    addFileBtnText : '选择文件...',  
    uploadBtnText : '上传',  
    removeBtnText : '移除所有',  
    cancelBtnText : '取消上传',  
	debug : false,
	file_size_limit : 100,//MB
	file_types : '*.*',
	file_types_description : 'All Files',
	file_upload_limit : 50,
	file_queue_limit : 0,
    post_params : {},
	flash_url : "../../js/swfupload/swfupload.swf",
	flash9_url : "../../js/swfupload/swfupload_fp9.swf",
	file_post_name : "fileData",
	
	initComponent : function(){		
		
		var me = this;
		me.upload_url = NS.url(this.modelName , 'upload');
		me.delete_url = NS.url(this.modelName , 'delete');
		this.gridId = "uploadGridId" + Ext.id();
		
		this.dockedItems = [{
		    xtype: 'toolbar',
		    dock: 'top',
		    items: [
		        { 
			        xtype:'button',
			        itemId: 'addFileBtn',
			        iconCls : 'add',
			        text : this.addFileBtnText,
			        
			        listeners: {
			        	afterrender: { 
			        		 fn: function(btn){
	        					var config = me.getSWFConfig(btn);		
	        					me.swfupload = new SWFUpload(config);
	        					me.swfupload.setDebugEnabled(true);
	        					
	        					Ext.get(me.swfupload.movieName).setStyle({
	        						position : 'absolute',
	        						top : 0,
	        						left : -2
	        					});	
			        		 }, 
			        		 buffer:100
			        	}
		            }
		        },{ xtype: 'tbseparator' },{
		        	xtype : 'button',
		        	itemId : 'uploadBtn',
		        	iconCls : 'up',
		        	text : this.uploadBtnText,
		        	scope : this,
		        	handler : this.onUpload
		        },{ xtype: 'tbseparator' },{
		        	xtype : 'button',
		        	itemId : 'removeBtn',
		        	iconCls : 'trash',
		        	text : this.removeBtnText,
		        	scope : this,
		        	handler : this.onRemove
		        },{ xtype: 'tbseparator' },{
		        	xtype : 'button',
		        	itemId : 'cancelBtn',
		        	iconCls : 'cancel',
		        	disabled : true,
		        	text : this.cancelBtnText,
		        	scope : this,
		        	handler : this.onCancelUpload
		        }
		    ]
		}];
		
		this.callParent();
	},
	getSWFConfig : function(btn){
		var me = this;
		var placeHolderId = Ext.id();
		var em = btn.getEl().child('em');
		if(em==null){
			em = Ext.get(btn.getId()+'-btnWrap');
		}		
		em.setStyle({
			position : 'relative',
			display : 'block'
		});
		em.createChild({
			tag : 'div',
			id : placeHolderId
		});
		return {
			debug: me.debug,
			flash_url : me.flash_url,
			flash9_url : me.flash9_url,	
			file_post_name : me.file_post_name,
			upload_url: me.upload_url,
			post_params: me.post_params||{savePath:'upload\\'},
			file_size_limit : (me.file_size_limit*1024),
			file_types : me.file_types,
			file_types_description : me.file_types_description,
			file_upload_limit : me.file_upload_limit,
			file_queue_limit : me.file_queue_limit,
			button_width: em.getWidth(),
			button_height: em.getHeight(),
			button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
			button_cursor: SWFUpload.CURSOR.HAND,
			button_placeholder_id: placeHolderId,
			custom_settings : {
				scope_handler : me
			},
			swfupload_preload_handler : me.swfupload_preload_handler,
			file_queue_error_handler : me.file_queue_error_handler,
			swfupload_load_failed_handler : me.swfupload_load_failed_handler,
			upload_start_handler : me.upload_start_handler,
			upload_progress_handler : me.upload_progress_handler,
			upload_error_handler : me.upload_error_handler,
			upload_success_handler : me.upload_success_handler,
			upload_complete_handler : me.upload_complete_handler,
			file_queued_handler : me.file_queued_handler,
			file_dialog_complete_handler : me.file_dialog_complete_handler
		};
	},
	file_dialog_complete_handler : function (numFilesSelected, numFilesQueued, numFilesInQueue) {
		if (this.getStats().files_queued > 0) {
			this.startUpload(); 
		}
	},
	swfupload_preload_handler : function(){
		if (!this.support.loading) {
			Ext.Msg.show({
				title : '提示',
				msg : '浏览器Flash Player版本太低,不能使用该上传功能！',
				width : 250,
				icon : Ext.Msg.ERROR,
				buttons :Ext.Msg.OK
			});
			return false;
		}
	},
	file_queue_error_handler : function(file, errorCode, message){
		switch(errorCode){
			case SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED : msg('上传文件列表数量超限,不能选择！');
			break;
			case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT : msg('文件大小超过限制, 不能选择！');
			break;
			case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE : msg('该文件大小为0,不能选择！');
			break;
			case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE : msg('该文件类型不允许上传！');
			break;
		}
		function msg(info){
			Ext.Msg.show({
				title : '提示',
				msg : info,
				width : 250,
				icon : Ext.Msg.WARNING,
				buttons :Ext.Msg.OK
			});
		}
	},
	swfupload_load_failed_handler : function(){
		Ext.Msg.show({
			title : '提示',
			msg : 'SWFUpload加载失败！',
			width : 180,
			icon : Ext.Msg.ERROR,
			buttons :Ext.Msg.OK
		});
	},
	upload_start_handler : function(file){
		var me = this.settings.custom_settings.scope_handler;
		me.down('#cancelBtn').setDisabled(false);	
//		var rec = me.store.getById(file.id);
//		this.setFilePostName(encodeURIComponent(rec.get('fileName')));
	},
	upload_progress_handler : function(file, bytesLoaded, bytesTotal){
		var me = this.settings.custom_settings.scope_handler;		
		var percent = Math.ceil((bytesLoaded / bytesTotal) * 100);
		percent = percent == 100? 99 : percent;
       	var rec = me.store.findRecord( "fileId" , file.id);
       	rec.set('percent', percent);
		rec.set('status', file.filestatus);
		rec.commit();
	},
	upload_error_handler : function(file, errorCode, message){
		var me = this.settings.custom_settings.scope_handler;		
		var rec = me.store.findRecord( "fileId" , file.id);
       	rec.set('percent', 0);
		rec.set('status', file.filestatus);
		rec.commit();
	},
	upload_success_handler : function(file, serverData, responseReceived){
		var me = this.settings.custom_settings.scope_handler;		
		var rec = me.store.findRecord( "fileId" , file.id);
		if(Ext.JSON.decode(serverData).success){			
	       	rec.set('percent', 100);
			rec.set('status', file.filestatus);
			rec.set('id', Ext.JSON.decode(serverData).id);
		}else{
			rec.set('percent', 0);
			rec.set('status', SWFUpload.FILE_STATUS.ERROR);
		}
		rec.commit();
		if (this.getStats().files_queued > 0 && this.uploadStopped == false) {
			this.startUpload();
		}else{
			me.showBtn(me,true);
		}
	},
	upload_complete_handler : function(file){
		
	},
	file_queued_handler : function(file){
		var me = this.settings.custom_settings.scope_handler;
		me.store.add({
			fileId : file.id,
			fileName : file.name,
			fileSize : file.size,
			fileType : file.type,
			status : file.filestatus,
			percent : 0
		});
	},
	onUpload : function(){
		if (this.swfupload&&this.store.getCount()>0) {
			if (this.swfupload.getStats().files_queued > 0) {
				this.showBtn(this,false);
				this.swfupload.uploadStopped = false;		
				this.swfupload.startUpload();
			}
		}
	},
	showBtn : function(me,bl){
		me.down('#addFileBtn').setDisabled(!bl);
		me.down('#uploadBtn').setDisabled(!bl);
		me.down('#removeBtn').setDisabled(!bl);
		me.down('#cancelBtn').setDisabled(bl);
		if(bl){
			me.down('actioncolumn').show();
		}else{
			me.down('actioncolumn').hide();
		}		
	},
	onRemove : function(){
		var me = this;
		var ds = this.store;
		var uploadIds = [];
		for(var i=0;i<ds.getCount();i++){
			var record =ds.getAt(i);
			var id = record.get('id');
			var fileId = record.get('fileId');
			if(id){
				uploadIds.push(id);
			}
			this.swfupload.cancelUpload(fileId,false);
		}
		
		if(uploadIds){
    		ExtUtils.doAction(this.modelName ,'delete' , {uploadIds : uploadIds} , 
            		function(result){
		    			if(result.success){
		    				ds.removeAll();
		    			}
		    			me.swfupload.uploadStopped = false;
            		}
            );
    	}
		
	},
	onCancelUpload : function(){
		if (this.swfupload) {
			this.swfupload.uploadStopped = true;
			this.swfupload.stopUpload();
			this.showBtn(this,true);
		}
	},
	
	getValue : function(){
		var data = ExtUtils.getAllData(this);
		var uploadIds = [];
		if(data){
			for(var i = 0 ; i < data.length ; i++){
				if(data[i].id){
					uploadIds.push({id : data[i].id});
				}
			}
		}
		return uploadIds;
	}
});