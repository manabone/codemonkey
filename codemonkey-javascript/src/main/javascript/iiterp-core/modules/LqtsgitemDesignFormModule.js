
Ext.define('iiterp-core.modules.LqtsgitemDesignFormModule', {
	/**继承**/
    extend: 'AM.modules.FormModule',

	/**常量**/
    id:'lqtsgitemDesignFormModule',
    
    winTitle : 'LqtsgitemDesign',
    
    modelName : 'LqtsgitemDesign',
    
    /**自定义常量**/
    
    /**实现父类方法**/
    modelFields : function(){
    
    	return ['parent','child','hcount'].concat(ExtUtils.defaultModelFields);
    },
    
    formItems : function(){
    
		var me = this;
    	
    	var p1 = ExtUtils.creationInfoPanel();
    	
    	var p2 = ExtUtils.panel({
    		title : 'Detail',
    		items : [{xtype:"numberfield",name:"hcount",fieldLabel:"数量"}]
    	});
    	
    	return ExtUtils.fitLayout([p1, p2]);
    }
    
	/**覆盖父类方法**/
    
    /**自定义页面行为**/
    
    /**自定义页面行为响应**/
});
