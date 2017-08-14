$ns("cotransqtyinfo.views");

$import("mx.permissions.Permission");
$import("mx.permissions.PermissionAgentClass");

cotransqtyinfo.views.CoTransQtyInfoDetailViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    me.ok = null;
    me.flag = null;
    var _detailView = null;
  
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new cotransqtyinfo.views.CoTransQtyInfoDetailView({controller: me, alias:"cotransqtyinfoCoTransQtyInfoDetailView"});
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
//    		debugger;
    		if(editors.visible){
    			if ("bigdecimal" == editors.dataType && editors.getValue() != "" && editors.getValue() != null) {
    				var isOk = validateController.exValidate("floatNumber", editors);//maxlength.validator("交易大类ID不能为空\/\#sd%&^*", [20]);
                	if(!isOk){
            			return ;
                	}
                	if("transqty"==editors.name || "capfee"==editors.name || "power"==editors.name || "transcap"==editors.name || "capprice"==editors.name){
	                	var isOk = validateController.exValidate("floatNumbera", editors);//maxlength.validator("交易大类ID不能为空\/\#sd%&^*", [20]);
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
        me.view.getForm().save();
        me.ok = "ok";
    };
    
    me._btnNew_onclick = function()
    {
    	me.flag = "add";
        // TODO： 此处新增的数据需要将服务端返回的 id 值设置到 GridItem 上。
    	if(me.mainCo.type=="add"&&me.ok==null){
    		mx.indicate("warn","请先保存合同输电信息！");
    		return;
    	}
//	    var _detailView = me._getDetailFromView();
    	var transInfoId = me.view.getForm().entityContainer.data.transinfoid;
    	var flag = me.flag;
	    var _detailView = new cotransqtyinfo.views.CoTransqtySlaveInfosDetailViewController({win:me.view.getDetailWindow(),transInfoId:transInfoId,flag:flag,co:me.view}).getView();
	    //设置对象id transInfoId
//        _detailView.objID = null;
//	    _detailView.transInfoId = me.view.getForm().entityContainer.data.transinfoid;
//	    _detailView.flag = me.flag;
        _showDetailFormView(_detailView,"表单填写");
    };
    
    /**
     * 编辑
     */
    me._btnEdit_onclick = function()
    {
    	me.flag = "edit";
        var v_dataGrid = me.view.getDataGrid();
    	if (v_dataGrid.getCheckedIDs().length == 0)
        {
             mx.indicate("info", "请勾选一条待编辑记录。");
             return;
        }
        //多选框勾选记录，判断是否选择多条
    	if(v_dataGrid.getCheckedIDs().length > 1)
    	{
    	       mx.indicate("info", "选定的记录条数不能超过一条。");
    	       return;
    	}
    	var bight =null;
    	var startdate =null;
    	var enddate =null;
    	var arr = v_dataGrid.items;
    	for(var i=0;i<arr.length;i++){
    		if(arr[i].checked){
    			bight=arr[i].values.bight;
    			startdate =arr[i].values.startdate;
    			enddate =arr[i].values.enddate;
    		}
    	}
//    	var _detailView = me._getDetailFromView();
//    	var bight = v_dataGrid.selections[0].values.bight;
//    	var startdate = v_dataGrid.selections[0].values.startdate;
//    	var enddate = v_dataGrid.selections[0].values.enddate;
    	var flag = me.flag;
    	var transInfoId = me.view.getForm().entityContainer.data.transinfoid;
    	var _detailView = new cotransqtyinfo.views.CoTransqtySlaveInfosDetailViewController({win:me.view.getDetailWindow(),bight:bight,transInfoId:transInfoId,startdate:startdate,enddate:enddate,flag:flag,co:me.view}).getView();
//    	_detailView.objID = v_dataGrid.getCheckedIDs()[0];
//    	_detailView.bight = v_dataGrid.selections[0].values.bight;
//    	_detailView.startdate = v_dataGrid.selections[0].values.startdate;
//    	_detailView.enddate = v_dataGrid.selections[0].values.enddate;
//    	_detailView.flag = me.flag;
	    //显示详细信息页面
    	_showDetailFormView(_detailView,"表单编辑");
    };
    
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
    	var transInfoId = me.view.getForm().entityContainer.data.transinfoid;
    	var bight = new Array();
    	for(var i=0;i<v_dataGrid.items.length;i++){
    		if(v_dataGrid.items[i].checked){
    			bight.push(v_dataGrid.items[i].values.bight);
    		}
    	}
    	var params = {"transInfoId":transInfoId,bights:bight};//获取选择的联络线id
    	var result = new mx.rpc.RESTClient();
		if (confirm("您确认删除数据吗？"))
		{
			var aa = result.getSync(cotransqtyinfo.mappath("~/rest/cotransqtyinfo/removeDlqx"),
		    		{ "params": JSON.stringify(params)}).resultValue;//获取起点关口
			if(aa.items!=null&&aa.items.length>0&&aa.items[0]=='succ'){
				mx.indicate("info","删除成功！");
			}else{
				mx.indicate("error","删除失败！");
			}
//		     v_dataGrid.removeItems(v_dataGrid.getCheckedIDs());
			v_dataGrid.load(function(){
				me.view._initLeftChart();
			});
			
		}
    };
    
    /**
     * 获取表单视图对象
     */
    me._getDetailFromView = function(){
    	if (_detailView == null)
        {
            var mvc = new cotransqtyinfo.views.CoTransqtySlaveInfosDetailViewController({win:me.view.getDetailWindow()});
            _detailView = mvc.getView();

//			_detailView.getForm().entityContainer.off("saved", me._refreshDataGrid);
//            _detailView.getForm().entityContainer.on("saved", me._refreshDataGrid);
        }
    	return _detailView;
    }
    
    /**
     * 显示表单视图
     * @param p_view : 需要显示的视图对象
     * @param p_title : 对话框的标题
     */
    function _showDetailFormView(p_view,p_title){
    	var win = me.view.getDetailWindow();
    	if(typeof p_view != "undefined"){
//    		p_view.load();
    		//设置显示视图、标题信息
    		win.setView(p_view);
    		win.setTitle(p_title ? p_title : win.title);
    	}
    	win.showDialog();
    }
    
    return me.endOfClass(arguments);
};