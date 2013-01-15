Ext.define('AM.base.ModelExt', {
	    extend: 'Ext.data.Model'
//	    	,
	    
//	    inheritableStatics : {
//	    	
//	    	doSearch : function(params , success){
//	    		debugger;
//	    		this.stores[0].proxy.url = this.proxy.api['search'];
//	    		this.stores[0].proxy.extraParams = params;
//	    		this.stores[0].load(success);
//    	    },
//    	    
//    	    doSave : function(obj , success){
//    	    	var postData = obj.getData();
//    	    	var options = {
//   	    			params : {
//   	    				id : postData.id || "",
//   	    				params : Ext.encode(postData)
//   	    			},
//   	    			success : success
//    	    	};
//    	    	this._exec(options, 'update');
//    	    },
//    	    
//    	    doLoad : function(id, success){
//    	    	if(id){
//    	    		var options = {
//   	   	    			params : {
//   	   	    				id : id,
//   	   	    			},
//   	   	    			success : success
//   	    	    	};
//    	    		this._exec(options, 'read');
//    	    	}
//    	    },
//    	    
//    	    doDelete : function(id){
//    	    	if(id){
//    	    		var options = {
//   	   	    			params : {
//   	   	    				id : id,
//   	   	    			}	
//   	    	    	};
//    	    		this._exec(options, 'destroy');
//    	    	}
//    	    },
//    	    
//	 	    _exec : function(options , action){
//	 	        options = Ext.apply({}, options);
//
//	 	        var me     = this,
//	 	            scope  = options.scope || me,
//	 	            stores = me.stores,
//	 	            i = 0,
//	 	            storeCount = 1,
//	 	            store = this.sotre,
//	 	            args,
//	 	            operation,
//	 	            callback;
//
//	 	        Ext.apply(options, {
//	 	            action : action
//	 	        });
//
//	 	        operation = new Ext.data.Operation(options);
//	 	        callback = function(operation) {
//	 	        	args = [me, operation];
//	 	        	ExtUtils.mask(false);
//	 	            if (operation.wasSuccessful()) {
//	 	                for(storeCount = stores ? stores.length : 0 ; i < storeCount; i++) {
//	 	                    store = stores[i];
//	 	                    args.push(store);
//	 	                    if(operation.resultSet && operation.resultSet.count > 0){
//	 	                    	Ext.each(operation.resultSet.records , function(r, index, records){
//	 	                    		
//	 	                    		var record = store.getById(r.data.id);
//	 	                    		
//	 	                    		if(operation.action == 'destroy'){
//	 	                    			if(record){
//	 	                    				store.remove(record);
//	 	                    			}
//	 	                    		}else{
//	 	                    			if(!record){
//	 	                    				store.add(r);
//	 	                    			}
//	 	                    		}
//	 	                    	});	
//	 	                    }
//	 	                    
//	 	                    store.fireEvent('write', store, operation);
//	 	                    store.fireEvent('datachanged', store);
//	 	                    // Not firing refresh here, since it's a single record
//	 	                }
//	 	                //me.clearListeners();
//	 	                Ext.callback(options.success, scope, args);
//	 	            } else {
//	 	                Ext.callback(options.failure, scope, args);
//	 	            }
//	 	            Ext.callback(options.callback, scope, args);
//	 	        };
//	 	       
//	 	        ExtUtils.mask(true);
//	 	        me.getProxy()[action](operation, callback, me);
//	 	        return me;
//	 	    }
//	    }
	   
	});