$ns("contractTypeInfo.views");

$import("mx.permissions.Permission");
$import("mx.permissions.PermissionAgentClass");
//$import("contractTypeInfo.views.ContractTypeInfoDetailView");

contractTypeInfo.views.ContractTypeInfoDetailViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
  
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new contractTypeInfo.views.ContractTypeInfoDetailView({controller: me, alias:"contractTypeInfoContractTypeInfoDetailView"});
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
    	//表单数据校验
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
    			if ("bigdecimal" == editors.dataType && editors.getValue() != "" && editors.getValue() != null) {
    				var isOk = validateController.exValidate("floatNumber", editors);//maxlength.validator("交易大类ID不能为空\/\#sd%&^*", [20]);
                	if(!isOk){
            			return ;
                	}
				}
    			var isOk = validateController.exValidate("maxLength", editors, [editors.maxLength]);//maxlength.validator("交易大类ID不能为空\/\#sd%&^*", [20]);
            	if(!isOk){
        			return ;
            	}
    		}
    	}
    	
    	me._validValue();
//        me.view.getForm().save();
    };
    
    me._validValue = function(){
    	var parentID = me.getView().getForm().editors.supertypeid.value;
    	var param = {"supertypeid":parentID};
    	var restClient = new mx.rpc.RESTClient();
    	restClient.get(contractTypeInfo.mappath("~/rest/cocontracttypeinfo/validValue"),
    		function(result){
//    			debugger;
    			if(result=="yes"){
    				me.view.getForm().save();
    			}else if(result=="no"){
    				if(parentID==null||parentID==""||parentID=="null"){
						me.getView().getForm().editors.supertypeid.validateMessage = "请选择父合同类型id!";
						me.getView().getForm().editors.supertypeid.markError(true, me.getView().getForm().editors.supertypeid.validateMessage);
						me.getView().getForm().editors.supertypeid.focus();
						mx.indicate("warn", me.getView().getForm().editors.supertypeid.validateMessage);
    					return;
    				}else{
    					me.view.getForm().save();
    				}
    			}
    		}
    	);
    }
    
    return me.endOfClass(arguments);
};