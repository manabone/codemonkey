	Ext.define('AM.base.RestExt', {
	    extend: 'Ext.data.proxy.Rest',
	    alias : 'proxy.restext',
	    
	    search : function(){
	       return this.doRequest.apply(this, arguments);
	    }
	    
	}, function() {
	    Ext.apply(this.prototype, {
	        /**
	         * @property {Object} actionMethods
	         * Mapping of action name to HTTP request method. These default to RESTful conventions for the 'create', 'read',
	         * 'update' and 'destroy' actions (which map to 'POST', 'GET', 'PUT' and 'DELETE' respectively). This object
	         * should not be changed except globally via {@link Ext#override Ext.override} - the {@link #getMethod} function
	         * can be overridden instead.
	         */
	        actionMethods: {
	            create : 'POST',
	            read   : 'GET',
	            update : 'PUT',
	            destroy: 'DELETE',
	            search : 'POST',
	            list : 'POST'
	        }
	    });
	});