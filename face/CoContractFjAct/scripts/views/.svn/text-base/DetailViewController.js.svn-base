$ns("CoContractFjAct.views");

$import("mx.permissions.Permission");
$import("mx.permissions.PermissionAgentClass");
$import("mx.rpc.RESTClient");


CoContractFjAct.views.DetailViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    var restClient = new mx.rpc.RESTClient();
  
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new CoContractFjAct.views.DetailView({controller: me, alias:"CoContractFjActDetailView"});
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {
        // iscID 是界面的统一权限功能编码，默认值为 "-1" ，表示不应用权限设置。
    	var permission = new mx.permissions.Permission({iscID:"-1"});
        // 根据“统一权限”设置组件的可见和只读等属性
    	// me.view 是当前页面的view页面，可根据需要传入其他需要权限控制页面元素
        mx.permissions.PermissionAgent.setPermission(permission, me.view);
    };
    
    me._btnSave_onclick = function()
    {
    	var validateObj = me.getView().getForm().validate();
		if(!validateObj.successful){
			if(validateObj.hint!=null)
				mx.indicate("info", validateObj.hint);
			return ;
		}
		var validateController = new validate.views.ValidateMainViewController();
		var editorsList = me.getView().getForm().editors;
		//数据类型校验
    	for(var i=0;i<editorsList.length;i++){
    		var editors = editorsList[i];
    		if(editors.visible){
	    		var isOk = validateController.exValidate("maxLength", editors, [editors.maxLength]);//maxlength.validator("交易大类ID不能为空\/\#sd%&^*", [20]);
	        	if(!isOk){
	    			return ;
	        	}
    		}
    	}
		var validateController = new validate.views.ValidateMainViewController();
    	var papercontractfile = me.view.getForm().editors.papercontractfile.value;
    	if(papercontractfile!=""&&papercontractfile!=null){
    		var suffixIndex = papercontractfile.indexOf('.');
    		if (suffixIndex == -1) {//@判断文件是否有后缀名
    			mx.indicate("warn", "上传失败！文件没有后缀名，不允许上传！");
    			return;
    		}
    		var name = papercontractfile.substring(0, suffixIndex).trim();
			if (name == "") {//@判断上传文件是否有文件名
				mx.indicate("warn", "上传失败！该文件名没有文件名，不允许上传！");
				return;
			}
			
			var pkVal =  me.view.getForm().editors.papercontractfile.pkVal;
			if(pkVal!=""&&pkVal!=null){
				restClient.get(
						CoContractFjAct.mappath("~/rest/cocontractaffixinfo/vaildFilesize"),
						{params: JSON.stringify({ itemType: pkVal })},
					    function(p_context) // 回调函数
					    {
					        if(p_context=="false"){
					        	mx.indicate("warn", "上传失败！文件过大，不允许上传，请重新选择！");
					        	return;
					        }else{
					        	  me.view.getForm().save(null,function(p_context){
					              	
					              	if(p_context.successful){
					              		mx.indicate("info", "文件上传成功！");
					              		me.view.parent.close();
					              	}else{
					              		mx.indicate("info", "文件上传失败！");
					              	}
					              	
					              });
					        }
					    } 
					); 

			}
			
    	}else{
    		
    		mx.indicate("warn", "文件名不存在！");
    		return;
    	}
    	
      
        
    };
    
    me._btnClose_onclick = function(){
    	
    	 me.view.parent.close();
    }
    
    return me.endOfClass(arguments);
};