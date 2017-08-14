$ns("contracttemplate.views");

$import("mx.permissions.Permission");
$import("mx.permissions.PermissionAgentClass");
$import("contracttemplate.views.DetailViewController");
$import("contracttemplate.views.MainView");

contracttemplate.views.MainViewController = function()
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
            me.view = new contracttemplate.views.MainView({ controller: me });
        }
        return me.view;
    };

    /**
     * 获取表单视图对象
     */
    me._getDetailFromView = function(){
    	if (_detailView == null)
        {
            var mvc = new contracttemplate.views.DetailViewController();
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
     * 新增
     */
    me._btnNew_onclick = function()
    {
    	_detailView.zhen = "ping";
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
    	_detailView.objID = "pingzhen";
	    //显示详细信息页面
    	_showDetailFormView(_detailView,"表单编辑");
    };

    
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
    /**
     * 说明按钮功能
     * 
     *
     */
    me.explainBtnMethod = function()
    {
    	var _openView = new mx.views.View(); 
        var _openWin = utils.commonFun.WinMananger.create({
         reusable: true,//是否复用
         width:"600px",
         height:"400px",
         title:""
        });
        ($("div.container view",_openView.$e).prevObject)
            .append($("<p><br>&nbsp;说明：<br>"+ 
        			  "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.查询关联合同信息列表，列表下方显示为统计信息。点击合同信息下方将显示对应合同的其他关联信息。<br>"+ 
        			  "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.点击【下载合同模板】按钮将下载合同模板下拉下表框选择的合同模板。<br>" +
        			  "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.点击【输出文件】按钮将下载选择的合同信息对应合同模板下拉下表框选择的合同模板生成合同并输出。<br>"+  
        			  "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.点击【查询】按钮根据查询条件合同类型、购电方、售电方、时间类型、合同时间等查询合同数据。"+
        			  "</p>"));
        _openWin.setView(_openView);
        _openWin.showDialog();
    };
    
    /**
     * 范本字段对应维护
     */
    me._tempFieldConfig = function(){
    	var win = utils.commonFun.WinMananger.create({ title: "用户可配置合同字段", width: 750, height: 540,reusable: true});
    	var pzzdView = new tempfieldconfig.views.TempfieldConfigMainViewController().getView();
    	win.setView(pzzdView);
		win.setTitle(win.title);
    	win.showDialog();
    }
    
    /**
     * 下载范本
     */
    me._downloadTemplate = function(){
    	var tempId = me.view.contractTempDownEditor.value;
    	if(tempId==null || tempId=="" || tempId=="null"){
    		mx.indicate("warn","请选择合同范本！");
    		return;
    	}else {
    		var restClient = new mx.rpc.RESTClient();
	    	var result = restClient.getSync(contracttemplate.mappath("~/rest/cocontracttemplate/isokdown?guid="+tempId));
	    	if(result=="fail"){
	    		mx.indicate("warn","没有可下载的范本，请先上传范本！");
	    		return;
	    	}
    		window.open(contracttemplate.mappath("~/rest/cocontracttemplate/down?guid="+tempId));
    	}
    }
    
    me.endOfClass(arguments);
    return me;
};