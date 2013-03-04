Ext.define('test.modules.UploadFileModule', {
	
	extend: 'Ext.ux.desktop.Module',
	
	hidden : true,
	
	id : 'uploadFileModule',
	winTitle : 'upload file',
    
	iconText : null,
	iconCls : null,
	
    createWindowItem : function(){
    	
    	var panel = Ext.create('Ext.form.Panel', {
            width: 500,
            frame: true,
            title: 'File Upload Form',
            bodyPadding: '10 10 0',

            defaults: {
                anchor: '100%',
                allowBlank: false,
                msgTarget: 'side',
                labelWidth: 50
            },

            items: [{
                xtype: 'textfield',
                fieldLabel: 'Name',
                name : 'filename'
            },{
                xtype: 'filefield',
                id: 'form-file',
                emptyText: 'Select an image',
                fieldLabel: 'Photo',
                name: 'fileData',
                buttonText: '',
                buttonConfig: {
                    iconCls: 'upload-icon'
                }
            }],

            buttons: [{
                text: 'Save',
                handler: function(){
                    var form = this.up('form').getForm();
                    if(form.isValid()){
                        form.submit({
                            url: NS.url('uploadFile' , 'create'),
                            method : 'POST',
                            waitMsg: 'Uploading your file...',
                            success: function(fp, o) {
                                Ext.Msg.alert('success','upload succeed');
                            }
                        });
                    }
                }
            },{
                text: 'Reset',
                handler: function() {
                    this.up('form').getForm().reset();
                }
            }]
        });
    	
    	return panel;
    }
});