$ns("contractaffixinfo.views");

$import("mx.permissions.Permission");
$import("mx.permissions.PermissionAgentClass");
//$import("contractaffixinfo.views.ContractAffixInfoDetailView");

contractaffixinfo.views.ContractAffixInfoDetailViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    var restClient = new mx.rpc.RESTClient();
  
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new contractaffixinfo.views.ContractAffixInfoDetailView({controller: me, alias:"contractaffixinfoContractAffixInfoDetailView"});
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
    	var papercontractfile = me.view.getForm().editors.papercontractfile.value;
    	if(papercontractfile!=""&&papercontractfile!=null){
    		var suffixIndex = papercontractfile.indexOf('.');
    		if (suffixIndex == -1) {//@判断文件是否有后缀名
    			mx.indicate("warn", "上传失败！文件没有后缀名，不允许上传！");
    			return;
    		}
    		var name = papercontractfile.substring(0, suffixIndex).trim();
			if (name == "") {//@判断上传文件是否有文件名
				mx.indicate("上传失败！该文件名没有文件名，不允许上传！");
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
					        	mx.indicate("warn", "上传失败！文件过大，不允许上传！");
					        	return;
					        }
					    } 
					); 

			}
			
    	}else{   		
    		mx.indicate("warn", "文件不存在，请上传文件！");
    		return;
    	}
        me.view.getForm().save();
    };
    
    me._saveButton_onclick = function()
    {
    	me.view.getForm().save(null,function(e){
			//保存后的回调函数
			if (e.successful){             
				mx.indicate("info", "附件上传成功！");
			}else{
				mx.indicate("info", "附件上传失败！");       
			}
		});
    }
    
    me._closeButton_onclick = function()
    {
    	me.winView.hide();
    }
    
    return me.endOfClass(arguments);
};