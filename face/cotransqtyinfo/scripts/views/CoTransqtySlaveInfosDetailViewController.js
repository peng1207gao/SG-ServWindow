$ns("cotransqtyinfo.views");

$import("mx.permissions.Permission");
$import("mx.permissions.PermissionAgentClass");

cotransqtyinfo.views.CoTransqtySlaveInfosDetailViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    var objID = null;
  
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new cotransqtyinfo.views.CoTransqtySlaveInfosDetailView({controller: me, alias:"cotransqtyinfoCoTransqtySlaveInfoDetailView"});
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
    	var result = new mx.rpc.RESTClient();
    	var params={"transInfoId":me.transInfoId};
		var aa = result.getSync(cotransqtyinfo.mappath("~/rest/cotransqtyinfo/getQuName"),{"params": JSON.stringify(params)}).resultValue;//获取起点关口
		name=aa.items[0];
        if(me.flag=='add'){
        	me.view.nameTextEditor.setValue(name);
	   		 me.view.getDataGrid().setFilter({transinfoid:me.transInfoId,bight:me.view.nameTextEditor.value});
	   	     me.view.getDataGrid().load();
	   	}else{
	   		me.view.nameTextEditor.setValue(me.bight);
	   		me.view.startDataTimeEditor.setValue(me.startdate);
	   		me.view.endDataTimeEditor.setValue(me.enddate);
	   		if(me.transInfoId!=null){
	       		me.view.getDataGrid().setFilter({transinfoid:me.transInfoId,bight:me.bight});
	       	}
	       	me.view.getDataGrid().load();
	   	}
    };
    me._btnCreate_onclick = function(){
    	 me.view.getDataGrid().entityContainer.create();
    }
    /**
     * 删除
     */
    me._btnDelete_onclick = function()
    {
	    var v_dataGrid = me.view.getDataGrid();
    	if (v_dataGrid.getCheckedIDs().length == 0)
        {
	     mx.indicate("info", "请至少勾选一条待删除记录。");
             return;
        }
		if (confirm("您确认删除数据吗？"))
		{
		     v_dataGrid.removeItems(v_dataGrid.getCheckedIDs());
		}
    };
    me._btnSave_onclick = function(){
    	var transInfoId=me.transInfoId;
    	var arr = me.view.getDataGrid().entityContainer.data;
    	var name = me.view.getNameValue();
    	var starttime  = me.view.getStartTimeValue();
    	var endtime  = me.view.getEndTimeValue();
    	if(name==null||name==''){
    		mx.indicate("warn", "电力曲线名称不能为空！");
			return;
    	}
    	if(starttime==null||starttime=='undefined'||endtime==null||endtime=='undefined'){
    		mx.indicate("warn", "开始时间和结束时间不能为空！");
			return;
    	}
    	if(starttime >= endtime){
			mx.indicate("warn", "开始时间必须小于结束时间！");
			return;
		}
    	//校验时间段重复
    	var arr1 = new Array();
    	var arr2 = new Array();
    	for(var i=0;i<arr.length;i++){
    		if(arr[i].qtytype==null){
    			mx.indicate("error","峰谷类型不能为空！");
    			return;
    		}
    		if(arr[i].starttime==null){
    			mx.indicate("error","表格开始时间不能为空！");
    			return;
    		}
    		if(arr[i].endtime==null){
    			mx.indicate("error","表格结束时间不能为空！");
    			return;
    		}
    		if(Number(arr[i].endtime)<=Number(arr[i].starttime)){
    			mx.indicate("error","表格开始时间不能大于等于结束时间！");
    			return;
    		}
    		arr1[i]=arr[i].starttime;
    		arr2[i]=arr[i].endtime;
    	}
    	for(var i=0;i<arr.length;i++){
    		for(var j=i+1;j<arr.length;j++){
    			if(Number(arr1[j])>=Number(arr1[i])&&Number(arr1[j])<Number(arr2[i])){
    				mx.indicate("error","表格中存在重复开始时间段！");
        			return;
    			}
    			if(Number(arr2[j])>Number(arr1[i])&&Number(arr2[j])<=Number(arr2[i])){
    				mx.indicate("error","表格中存在重复结束时间段！");
        			return;
    			}
    		}
    	}
    	
    	if(arr!=null&&arr.length>0){
    		for(var i=0;i<arr.length;i++){
    			if(arr[i].transinfoid!=null&&arr[i].transinfoid!=undefined){
    				if(arr[i].transinfoid!=transInfoId){
    					me.view.getDataGrid().entityContainer.changed=true;
    					me.view.getDataGrid().entityContainer.setValue(arr[i].guid,"transinfoid",transInfoId);
    				}
//    				arr[i].transinfoid=transInfoId;
//    				me.view.getDataGrid().items[i].setValue("transinfoid",transInfoId);
    			}else{
    				arr[i].setValue("transinfoid",transInfoId);
    			}
    			if(arr[i].bight!=null&&arr[i].bight!=undefined){
    				if(arr[i].bight!=name){
    					me.view.getDataGrid().entityContainer.changed=true;
    					me.view.getDataGrid().entityContainer.setValue(arr[i].guid,"bight",name);
    				}
//    				arr[i].bight=name;
//    				me.view.getDataGrid().items[i].setValue("bight",name);
    			}else{
    				arr[i].setValue("bight",name);
    			}

    			if(arr[i].startdate!=null&&arr[i].startdate!=undefined){
    				if(arr[i].startdate!=starttime){
    					me.view.getDataGrid().entityContainer.changed=true;
    					me.view.getDataGrid().entityContainer.setValue(arr[i].guid,"startdate",starttime);
    				}
//    				arr[i].startdate=starttime;
//    				me.view.getDataGrid().items[i].setValue("startdate",starttime);
    			}else{
    				arr[i].setValue("startdate",starttime);
    			}

    			if(arr[i].enddate!=null&&arr[i].enddate!=undefined){
    				if(arr[i].enddate!=endtime){
    					me.view.getDataGrid().entityContainer.changed=true;
    					me.view.getDataGrid().entityContainer.setValue(arr[i].guid,"enddate",endtime);
    				}
//    				arr[i].enddate=endtime;
//    				me.view.getDataGrid().items[i].setValue("enddate",endtime);
    			}else{
    				arr[i].setValue("enddate",endtime);
    			}
    			
    		}
    	}
    	me.view.getDataGrid().entityContainer.save();
 		me.win.hide();
//     	me.view.getDataGrid().load();
     	me.co.getDataGrid().load(function(){
     		me.co._initLeftChart();
     	});
    }
    me._btnSave_onclick1 = function()
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
                	if("power" == editors.name){
                		var isOk = validateController.exValidate("floatNumbera", editors);//maxlength.validator("交易大类ID不能为空\/\#sd%&^*", [20]);
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
    	//开始时间不能大约结束时间
    	var starttime = editorsList.starttime.value;
    	var endtime = editorsList.endtime.value;
    	if(parseInt(starttime) >= parseInt(endtime)){
			mx.indicate("warn", "开始时间必须小于结束时间！");
			return;
		}
        me.view.getForm().save();
    };
    
    me._btnClose_onclick = function(){
    	me.win.hide();
    }
    
    return me.endOfClass(arguments);
};