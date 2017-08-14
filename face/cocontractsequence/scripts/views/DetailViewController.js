$ns("cocontractsequence.views");
//$import("cocontractsequence.views.DetailView");
//$import("validate.views.ValidateMainViewController");

cocontractsequence.views.DetailViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
  
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new cocontractsequence.views.DetailView({ controller: me });
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {
        // TODO: 窗体激活时的逻辑。
		if (me.view != null && typeof(me.view.form) != "undefined")
		{
			me.view.form.load(me.view.objID);
		}
    };
    
    me._btnSave_onclick = function()
    {
    	var validateObj = me.getView().form.validate();
		if(!validateObj.successful){
			if(validateObj.hint!=null)
				mx.indicate("info", validateObj.hint);
			return ;
		}
		var validateController = new validate.views.ValidateMainViewController();
		var editorsList = me.getView().form.editors;
		//数据类型校验
    	for(var i=0;i<editorsList.length;i++){
    		var editors = editorsList[i];
    		
    		if("contractType" != editors.name){
	    		if(editors.visible){
	    		var isOk = validateController.exValidate("maxLength", editors, [editors.maxLength]);//maxlength.validator("交易大类ID不能为空\/\#sd%&^*", [20]);
	            	if(!isOk){
	        			return ;
	            	}
	    		}
    		}
    		
    	}
    	    if(me.view.form.editors[1].value==null||me.view.form.editors[1].value==""){
//    	    	alert("请填写合同序列名称");
    	    	mx.indicate("info", "请填写合同序列名称！");
    	    	return;
    	    }
    	    
    	    var validateContent = validateController.getValidate("maxLength");
    		//校验字段ID 长度30
    		var type=validateContent.validator(me.view.form.editors[1].value,[64]);
    		if (!type) {
    			mx.indicate("info", "合同序列名称："+validateContent.message.format([64]));
    			return;
    		}
    	    
    	    if(me.view.form.editors[3].value==null||me.view.form.editors[3].value==""){
    	    	alert("请选择合同序列周期");
    	    	mx.indicate("info", "请选择合同序列周期！");
    	    	return ;
    	    }
    	    if(me.view.form.editors[4].value==null||me.view.form.editors[4].value==""){
//    	    	alert("请选择合同类型");
    	    	mx.indicate("info", "请选择合同类型！");
    	    	return;
    	    }
		    me.getView().form.save(function(e) {
				if (e.successful) {// 保存成功
//					alert("保存成功");
					mx.indicate("info", "保存成功！");
					$("body>div:last").css("display","none");
					me.mainController._openWin.hide();
					me.mainController._btnSearch_onclick();
				} else {
//					alert("保存失败");
					mx.indicate("info", "保存失败！");
					return;
				}
			});
    };
    return me.endOfClass(arguments);
};