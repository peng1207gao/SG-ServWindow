$ns("IdeaConManage.views");

$import("mx.permissions.Permission");
$import("mx.permissions.PermissionAgentClass");
//$import("IdeaConManage.views.IdeaConDetailViewController");
//$import("IdeaConManage.views.IdeaConMainView");
//$import("IdeaConManage.views.IdeaConRelateViewController");

IdeaConManage.views.IdeaConMainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
	
    /**
     * 表单视图对象
     */
    var _detailView = null;
    
    /**
     * 意向性合同关联视图对象
     */
    var _contractRelateView = null;
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new IdeaConManage.views.IdeaConMainView({ controller: me });
        }
        return me.view;
    };

    /**
     * 获取表单视图对象
     */
    me._getDetailFromView = function(){
    	if (_detailView == null)
        {
            var mvc = new IdeaConManage.views.IdeaConDetailViewController();
            _detailView = mvc.getView();

			_detailView.getForm().entityContainer.off("saved", me._refreshDataGrid);
            _detailView.getForm().entityContainer.on("saved", me._refreshDataGrid);
        }
    	return _detailView;
    }
    
     /**
     * 获取意向性合同关联视图对象
     */
    me._getContractRelateView = function(){
    	if (_contractRelateView == null)
        {
            var mvc = new IdeaConManage.views.IdeaConRelateViewController();
            _contractRelateView = mvc.getView();
        }
    	return _contractRelateView;
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
        me.view.selectData();
        //加载数据
//	    me.view.getDataGrid().load();
    };
    
    /**
     * 新增
     */
    me._btnNew_onclick = function()
    {
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
    	var _detailView = me._getDetailFromView();
    	_detailView.objID = v_dataGrid.getCheckedIDs()[0];
	    //显示详细信息页面
    	_showDetailFormView(_detailView,"表单编辑");
    };

    me._showContractDetailView = function(p_item){
    	var _detailView = me._getDetailFromView();
    
    	_detailView.objID = p_item.values["contractid"];
    	var sceneType = "ideaContract";//获取树结构的参数，标识获取意向性合同树结构
    	_detailView.sceneType = sceneType;
    	_detailView._contractTree.load({"contractid":_detailView.objID, "sceneType":sceneType});//初始化树结构
	    //显示详细信息页面
    	_showDetailFormView(_detailView,"关联查询");
    }
    
    /**
     * 点击合同【关系维护】按钮,调用方法
     */
     me._showContractRelateView = function(){
     	var v_dataGrid = me.view.getDataGrid();
    	var selectedItem = v_dataGrid.selection;
    	if(selectedItem == null){
			mx.indicate("info", "请选择一条记录");
			return;
    	}	
     	
    	var userInfo = me.getUserInfo();//从后台获取用户信息（marketid）
    	if(userInfo.successful == false || typeof(userInfo.resultValue.items) == "undefined"){
    		mx.indicate("info", "用户登录信息过期！");
    		return;
    	}
    	
    	var presentUserMarketID = userInfo.resultValue.items[0].marketid;//当前用户的marketid
    	var selectedMarketID = selectedItem.values["marketid"];//选中记录的marketid
    	
    	if(presentUserMarketID == selectedMarketID){// 选中记录的marketid与当前用户的marketid比较
    		var _contractRelateView = me._getContractRelateView();
	    	_contractRelateView.objID =  selectedItem.values["contractid"];
		    //显示详细信息页面
	    	_showRelationContractView(_contractRelateView,"意向性合同关联");
    	} else {
//    		alert("该数据不允许关联意向性合同！");// TODO　是否提示   当前登录用户不能关联当前选中合同 
			mx.indicate("info", "该数据不允许关联意向性合同！");
			return;
    	}
    }
    
    me.getUserInfo = function()
    {
    	me.restClient = new mx.rpc.RESTClient();
    	var respData = me.restClient.getSync(IdeaConManage.mappath("~/rest/cocontractbaseinfo/getUserInfo"));
    	return respData;
    }
    
    
    /**
     * 显示表单视图
     * @param p_view : 需要显示的视图对象
     * @param p_title : 对话框的标题
     */
    function _showDetailFormView(p_view,p_title){
    	var win = p_view.getDetailWindow();
    	if(typeof p_view != "undefined"){
    		p_view.load();
    		//设置显示视图、标题信息
    		win.setView(p_view);
    		win.setTitle(p_title ? p_title : win.title);
    	}
    	win.showDialog();
    }
    
    
     /**
     * 显示意向性合同关联视图
     * @param p_view : 需要显示的视图对象
     * @param p_title : 对话框的标题
     */
    function _showRelationContractView(p_view,p_title){
    	var win = me.view.getRelationContractWindow();
    	if(typeof p_view != "undefined"){
    		p_view.load();
//    		p_view.selectData();
    		//设置显示视图、标题信息
    		win.setView(p_view);
    		win.setTitle(p_title ? p_title : win.title);
    	}
    	win.showDialog();
    }
    
    me.endOfClass(arguments);
    return me;
};