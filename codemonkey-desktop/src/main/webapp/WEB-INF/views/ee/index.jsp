<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<head>
	<script type="text/javascript">
		var PAGE_DATA = ${pageData};
		Ext.onReady(function(){
	
			var modelFields = ${modelFields};
			var modelName =  "${modelName}";
			var proxy = ExtUtils.proxy(modelName);
			
			var formItems = ${formItems};
			
			var cols =  ${cols};
			
			//model
			Ext.define('AM.model.MM', {
				extend: 'Ext.data.Model',
				fields: modelFields
			});
	
			//store
			Ext.define('AM.store.MMStore', {
				extend: 'Ext.data.Store',
				model: 'AM.model.MM',
				autoLoad: true,
				proxy: proxy
			});
	
			//views
			Ext.define('AM.view.mm.Edit', {
				extend: 'Ext.window.Window',
				alias : 'widget.mmedit',
	
				requires: ['Ext.form.Panel'],
	
				title : 'Edit',
				layout: 'fit',
				modal : true,
				autoShow : true,
				width: 280,
	
				initComponent: function() {
					this.items = [
						{
							xtype: 'form',
							padding: '5 5 0 5',
							border: false,
							style: 'background-color: #fff;',
							items: formItems
						}
					];
	
					this.buttons = [
						{
							text: 'Save',
							action: 'save'
						},
						{
							text: 'Cancel',
							scope: this,
							handler: this.close
						}
					];
	
					this.callParent(arguments);
				}
			});
	
			Ext.define('AM.view.mm.List' ,{
				extend: 'Ext.panel.Panel',
				//extend: 'Ext.grid.Panel',
				alias : 'widget.mmlist',
	
				layout : {
					type : 'vbox',
					align : 'stretch',
					pack  : 'start'
				},
				
				items : [{
					xtype : 'toolbar',
					items : [{xtype : 'tbspacer',flex : 1} , ExtUtils.themeSelect(PAGE_DATA)]
				},
				ExtUtils.searchingForm() 
				, {
					xtype : 'gridpanel',
					store : 'MMStore',
					title : 'All',
					flex : 6 ,
					border : 0,
					buttons : [ {
						id : 'add',
						text : 'Add',
						iconCls: 'icon-create',
						action : 'add'
					}, {
						id : 'remove',
						text : 'Remove',
						iconCls: 'icon-destroy',
						action : 'remove'
					} ],

					columns : cols
				} ]

			});

			Ext.define('AM.view.Viewport', {
				extend : 'Ext.container.Viewport',

				layout : 'fit',
				items : [ {
					xtype : 'mmlist'
				} ]
			});

			//controller

			Ext.define('AM.controller.MMController', {
				extend : 'Ext.app.Controller',

				stores : [ 'MMStore' ],

				models : [ 'MM' ],

				views : [ 'mm.Edit', 'mm.List' ],

				refs : [ {
					ref : 'mmPanel',
					selector : 'panel'
				}, {
					ref : 'grid',
					selector : 'grid'
				} ],

				init : function() {
					this.control({
						'viewport > mmlist dataview' : {
							itemdblclick : this.editMM
						},
						'mmedit button[action=save]' : {
							click : this.updateMM
						},
						'#add' : {
							click : this.createMM
						},
						'#remove' : {
							click : this.removeMM
						},
						'#search' : {
							click : this.searchMM
						},
						'#reset' : {
							click : this.resetMM
						}
					});

				},
				
				searchMM : function(){
					var store = this.getStore('MMStore');
					var proxy = store.getProxy();
					var params = ExtUtils.formValues('#searchForm');
		    		proxy.extraParams = params;
		    		store.load();
				},
				
				resetMM : function(){
					var form = Ext.getCmp('searchForm');
					form.getForm().reset();
					var store = this.getStore('MMStore');
					var proxy = store.getProxy();
		    		proxy.extraParams = {};
		    		store.load();
				},

				removeMM : function() {
					var me = this;
					var grid = this.getGrid();
					var r = grid.getSelectionModel().getSelection();
					if (r && r[0]) {
						this.getStore('MMStore').remove(r[0]);
						ExtUtils.mask(true);
						this.getStore('MMStore').sync({
							callback : function() {
								me.syncCallback();
							}
						});
					}
				},

				createMM : function() {
					Ext.create('AM.view.mm.Edit').show();
				},

				editMM : function(grid, record) {
					
					if(!formItems || formItems.length == 0) {
						return;
					}
					
					var edit = Ext.create('AM.view.mm.Edit').show();

					edit.down('form').loadRecord(record);
				},

				updateMM : function(button) {
					var me = this;
					var win = button.up('window');
					var form = win.down('form');
					var record = form.getRecord();
					var values = form.getValues();
					var store = this.getStore('MMStore');
					if (values.id) {
						record.set(values);
					} else {
						var mm = Ext.create('AM.model.MM', values);
						store.add(mm);
					}
					win.close();
					ExtUtils.mask(true);
					store.sync({
						callback : function() {
							me.syncCallback();
						}
					});
				},

				syncCallback : function(options) {
					ExtUtils.mask(false);
					this.getStore('MMStore').load();
				}
			});

			Ext.application({
				name : 'AM',

				// automatically create an instance of AM.view.Viewport
				autoCreateViewport : true,

				controllers : [ 'MMController' ]
			});
		});
	</script>
</head>
<body>
</body>