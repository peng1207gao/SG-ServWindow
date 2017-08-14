$ns("contractTypeInfo.views");

$import("mx.permissions.Permission");
$import("mx.permissions.PermissionAgentClass");
//$import("contractTypeInfo.views.ContractTypeInfoDetailViewController");
//$import("contractTypeInfo.views.ContractTypeInfoMainView");
//$import("cocontractmembership.views.MainViewController");

contractTypeInfo.views.ContractTypeInfoMainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
	
    /**
     * 表单视图对象
     */
    var _detailView = null;
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new contractTypeInfo.views.ContractTypeInfoMainView({ controller: me });
        }
        return me.view;
    };

    /**
     * 获取表单视图对象
     */
    me._getDetailFromView = function(){
    	if (_detailView == null)
        {
            var mvc = new contractTypeInfo.views.ContractTypeInfoDetailViewController({mainCo:me});
            _detailView = mvc.getView();

			_detailView.getForm().entityContainer.off("saved", me._refreshDataGrid);
            _detailView.getForm().entityContainer.on("saved", me._refreshDataGrid);
        }
    	return _detailView;
    }

	// 加载列表数据。
	me._loadDataGrid = function(e)
	{
    	me.view.getDataGrid().load();
	}

	// 表单视图保存后刷新列表数据。
	me._refreshDataGrid = function(e)
	{
		me.view.getDetailWindow().hide();
    	me.view.getDataGrid().load();
	}

    me._onactivate = function(e)
    {
        // iscID 是界面的统一权限功能编码，默认值为 "-1" ，表示不应用权限设置。
    	var permission = new mx.permissions.Permission({iscID:"-1"});
        // 根据“统一权限”设置组件的可见和只读等属性
    	// me.view 是当前页面的view页面，可根据需要传入其他需要权限控制页面元素
        mx.permissions.PermissionAgent.setPermission(permission, me.view);
        //加载数据
	    me.view.getDataGrid().load();
    };
    
    /**
     * 查询
     */
    me._btnSearch_onclick = function()
    {
    	var marketid = me.getView().marketDropEditor.value;
    	var starttime = me.getView().startTimeEditor.value == null ? "" : me.getView().startTimeEditor.value;
    	var endtime = me.getView().endTimeEditor.value == null ? "" : me.getView().endTimeEditor.value;
    	if(starttime != "" && endtime != ""){
    		if(starttime > endtime){
    			mx.indicate("warn","开始时间不能大于截止时间！");
    			return;
    		}
    	}
    	me.getView().getDataGrid().filter = {"marketid":marketid,
    		"starteffectivedate":starttime,"endeffectivedate":endtime};
    	me.getView().getDataGrid().load();
    }
    
    /**
     * 新增
     */
    me._btnNew_onclick = function()
    {
    	me.type = "add";
        // TODO： 此处新增的数据需要将服务端返回的 id 值设置到 GridItem 上。
	    var _detailView = me._getDetailFromView();
	    //设置对象id
        _detailView.objID = null;
        _showDetailFormView(_detailView,"表单填写");
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
		if (confirm("您确认删除数据吗？"))
		{
		     v_dataGrid.removeItems(v_dataGrid.getCheckedIDs());
		}
    };
    
    /**
     * 保存
     */
    me._btnSave_onclick = function()
    {
        me.view.getDataGrid().entityContainer.save();
    };
    
    /**
     * 编辑
     */
    me._btnEdit_onclick = function()
    {   
    	me.type = "edit";
        var v_dataGrid = me.view.getDataGrid();
    	if (v_dataGrid.selection == null)
        {
             mx.indicate("info", "请勾选一条待编辑记录。");
             return;
        }
        
        //获取当前选中的记录的维护单位的场景id
        var param = {"selectMarketId":v_dataGrid.selection.marketid};
        var restClient = new mx.rpc.RESTClient();
        restClient.get(contractTypeInfo.mappath("~/rest/cocontracttypeinfo/limits"),
        	{"params":JSON.stringify(param)},function(result){
        		if(result=="limit"){
        			mx.indicate("warn","上级合同类型不能编辑！");
        			return;
        		}else if(result=="nolimit"){
        			var _detailView = me._getDetailFromView();
			    	_detailView.objID = v_dataGrid.selection.id;
				    //显示详细信息页面
			    	_showDetailFormView(_detailView,"表单编辑");
        		}
        	}
        );
        //多选框勾选记录，判断是否选择多条
//    	if(v_dataGrid.getCheckedIds().length > 1)
//    	{
//    	       mx.indicate("info", "选定的记录条数不能超过一条。");
//    	       return;
//    	}
//    	var _detailView = me._getDetailFromView();
//    	_detailView.objID = v_dataGrid.selection.id;
//	    //显示详细信息页面
//    	_showDetailFormView(_detailView,"表单编辑");
    };

    /**
     * 生效合同类型
     */
    me._btnEffect_onclick = function()
    {
    	var v_dataGrid = me.view.getDataGrid();
    	if (v_dataGrid.selection == null)
        {
             mx.indicate("info", "请勾选一条待生效记录。");
             return;
        }
        var param = {"selectMarketId":v_dataGrid.selection.marketid};
        var restClient = new mx.rpc.RESTClient();
        restClient.get(contractTypeInfo.mappath("~/rest/cocontracttypeinfo/limits"),
        	{"params":JSON.stringify(param)},function(result){
        		if(result=="limit"){
        			mx.indicate("warn","上级合同类型不能进行生效操作！");
        			return;
        		}else if(result=="nolimit"){
        			if (confirm("是否确认该合同类型生效？"))
					{
					     var restClient = new mx.rpc.RESTClient();
					     var param = {"contractTypeId":v_dataGrid.selection.id};
					     var result =  restClient.getSync(contractTypeInfo.mappath("~/rest/cocontracttypeinfo/effect"),
					     	{"params":JSON.stringify(param)}).resultValue;
					     mx.indicate("info",result.items[0])
			        	 me._btnSearch_onclick();//刷新页面
					}
        		}
        	}
        );
    	
    }
    
    /**
     * 失效合同类型
     */
    me._btnInvalid_onclick = function()
    {
    	var v_dataGrid = me.view.getDataGrid();
    	if (v_dataGrid.selection == null)
        {
             mx.indicate("info", "请勾选一条待失效记录。");
             return;
        }
        var param = {"selectMarketId":v_dataGrid.selection.marketid};
        var restClient = new mx.rpc.RESTClient();
        restClient.get(contractTypeInfo.mappath("~/rest/cocontracttypeinfo/limits"),
        	{"params":JSON.stringify(param)},function(result){
        		if(result=="limit"){
        			mx.indicate("warn","上级合同类型不能进行失效操作！");
        			return;
        		}else if(result=="nolimit"){
        			if (confirm("是否确认该合同类型及其所属所有子合同类型失效？"))
					{
					     var restClient = new mx.rpc.RESTClient();
					     var param = {"contractTypeId":v_dataGrid.selection.id};
					     var result =  restClient.getSync(contractTypeInfo.mappath("~/rest/cocontracttypeinfo/invalid"),
					     	{"params":JSON.stringify(param)}).resultValue;
					     mx.indicate("info",result.items[0])
						 me._btnSearch_onclick();//刷新页面
					}
        		}
        	}
        );
    	
    }
    
    me._btnMember_onclick = function()
    {
    	var v_dataGrid = me.view.getDataGrid();
    	if (v_dataGrid.selection == null)
        {
             mx.indicate("info", "请选择一条合同类型！");
             return;
        }
		me.openWin = utils.commonFun.WinMananger.create({
    		reusable : true,//是否复用
    		width : "60%",
    		height : "50%",
    		title : "合同准入成员管理"
		});
    	var openView = new cocontractmembership.views.MainViewController({
    		contracttypeid:v_dataGrid.selection.id,contracttypename:v_dataGrid.selection.typename,
    		win:me.openWin}).getView();
    	me.openWin.setView(openView);
    	me.openWin.showDialog();
    }
    
    /**
     * 显示表单视图
     * @param p_view : 需要显示的视图对象
     * @param p_title : 对话框的标题
     */
    function _showDetailFormView(p_view,p_title){
    	var win = me.view.getDetailWindow();
    	if(typeof p_view != "undefined"){
    		p_view.load();
    		//设置显示视图、标题信息
    		win.setView(p_view);
    		win.setTitle(p_title ? p_title : win.title);
    	}
    	win.showDialog();
    }
    
    me.endOfClass(arguments);
    return me;
};