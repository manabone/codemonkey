(function(){
	Ext.define('OverrideConnection', {
	    override: 'Ext.data.Connection',
	    onStateChange : function(request) {
	        if (request && request.xhr && request.xhr.readyState == 4) {
	            this.clearTimeout(request);
	            this.onComplete(request);
	            this.cleanup(request);
	        }
	    }
	});
	
	Ext.define('OverrideLoadMask', {
	    override: 'Ext.LoadMask',
	    
	    setZIndex: function(index) {
	        var me = this,
	            owner = me.activeOwner;
	            
	        if (owner) {
	        	var zIndex = owner.el.getStyle('zIndex');
	        	//fix IE zIndex problem
	        	if(zIndex == 'auto'){
	        		zIndex = 0;
	        	}else{
	        		zIndex = parseInt(zIndex);
	        	}
	        	
	            index = parseInt(zIndex , 10) + 1;
	        }

	        me.getMaskEl().setStyle('zIndex', index - 1);
	        return me.mixins.floating.setZIndex.apply(me, arguments);
	    }
	});

	Ext.form.field.Base.prototype.initComponent = function(){
		var me = this; 
		Ext.form.field.Base.superclass.initComponent.call(this);
		me.subTplData = me.subTplData || {};
		this.addEvents('focus','blur','specialkey');
		// Init mixins
		me.initLabelable();
		me.initField();
		// Default name to inputId
		if (!me.name) {
			me.name = me.getInputId();
		} 
		if(this.allowBlank === false && this.fieldLabel){
			this.fieldLabel += '<font color=red>*</font>';
		}
		if(this.readOnly === true && this.fieldLabel){
			this.fieldLabel += '<font color=blue>*</font>';
		}
	};
})();