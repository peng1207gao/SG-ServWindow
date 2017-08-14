$ns("COContractEnergyInfo.views");

$import("mx.permissions.Permission");
$import("mx.permissions.PermissionAgentClass");

COContractEnergyInfo.views.DetailViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
  
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new COContractEnergyInfo.views.DetailView({controller: me, alias:"COContractEnergyInfoDetailView"});
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
    			if ("bigdecimal" == editors.dataType && editors.getValue() != "" && editors.getValue() != null) {
    				var isOk = validateController.exValidate("floatNumber", editors);//maxlength.validator("交易大类ID不能为空\/\#sd%&^*", [20]);
                	if(!isOk){
            			return ;
                	}
                	if("tradePriceMargin"==editors.name || "vendeeBreathPrice"==editors.name || "saleBreathPrice"==editors.name){
                		var isOk = validateController.exValidate("floatNumbera3", editors);//maxlength.validator("交易大类ID不能为空\/\#sd%&^*", [20]);
	                	if(!isOk){
	            			return ;
	                	}
                	}else{
                		var isOk = validateController.exValidate("floatNumbera4", editors);//maxlength.validator("交易大类ID不能为空\/\#sd%&^*", [20]);
	                	if(!isOk){
	            			return ;
	                	}
                	}
				}
    			var isOk = validateController.exValidate("maxLength", editors, [editors.maxLength]);//maxlength.validator("交易大类ID不能为空\/\#sd%&^*", [20]);
            	if(!isOk){
        			return ;
            	}
    		}
    	}
    	//如果选总，则其值则与已存该年度该合同之和进行计算比较,如果选择尖峰平谷这四类电量类型时，也要与合同电量进行校验editorsList.period.getValue()=='1'
    	if(editorsList.period != null){
    		if(me.result !=null&&editorsList.period.getValue()=='1'){//me.result为该合同电量与分段已存在电量之和的差值
    			if(me.getView().objID == null){//新增时
    				if(Number(me.result) < 0){
	    				mx.indicate("info", "分段电量和大于合同电量，请检查!");
		    			return;
	    			}
	    			else if(Number(me.result) < Number(editorsList.energy.getValue())){
		    			mx.indicate("info", "电量过大,请重新输入!");
		    			return;
	    			}
    			}
    			else{//变更时
    				var old = me.getView().energyold;
    				var news = editorsList.energy.getValue();
    				var result1 = Number(me.result)+Number(old)-Number(news);
    				if(Number(result1) < 0){
    					mx.indicate("info", "电量过大,请重新输入!");
		    			return;
    				}
    			}
    		}else if(editorsList.period.getValue()!='1'){//电量类型选择尖峰平谷时
	    		var contractId = me.view.param;
	    		var stDate = me.view.stDate;
	    		var restClient = mx.rpc.RESTClient();
	    		var result = restClient.getSync(COContractEnergyInfo.mappath("~/rest/cocontractenergyinfo/getMonthEnergy"),
	    			{"params":JSON.stringify({contractId:contractId,stDate:stDate})});
	    		if(result.successful == true){
	    			if(result.resultValue.items.length>0){
	    				me.energy = result.resultValue.items[0];//me.energy为该月的尖峰平谷四种电量的总和
	    			}
	    		}
	    		var formenergy = me.view.getForm().entityContainer.data.energy;//用户输入的电量值
	    		if(me.getView().objID == null){//新增时
		    		if(Number(formenergy)+Number(me.energy)>Number(me.view.monthEnergy)){
		    			mx.indicate("warn","该月份的尖峰平谷电量之和不能大于该月的总电量！");
		    			return;
		    		}
	    		}else {//变更时
	    			var old = me.getView().energyold;
    				var news = editorsList.energy.getValue();
    				var result1 = Number(me.result)+Number(old)-Number(news);
    				if(Number(result1) < 0){
    					mx.indicate("info", "电量过大,请重新输入!");
		    			return;
    				}
	    		}
    		}
    	}
    	
        me.view.getForm().save(function(e) {
        	var view = me.getView();
        	view.pview.getDataTree().load();
        });
    };
    
    return me.endOfClass(arguments);
};