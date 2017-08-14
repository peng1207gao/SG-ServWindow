$ns("templatemanage.views");

$import("mx.permissions.Permission");
$import("mx.permissions.PermissionAgentClass");
$import("mx.windows.WindowManager");


templatemanage.views.TemplateManageMainViewController = function()
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
            me.view = new templatemanage.views.TemplateManageMainView({ controller: me });
        }
        return me.view;
    };

    /**
     * 获取表单视图对象
     */
    me._getDetailFromView = function(){
    	if (_detailView == null)
        {
            var mvc = new templatemanage.views.TemplateManageDetailViewController();
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
    
    /**
     * 生效范本
     */
    me._btnEffect_onclick = function(){
    	var v_dataGrid = me.view.getDataGrid();
    	if (v_dataGrid.getCheckedIDs().length == 0)
        {
             mx.indicate("info", "请勾选一条待生效范本。");
             return;
        }
        //多选框勾选记录，判断是否选择多条
    	if(v_dataGrid.getCheckedIDs().length > 1)
    	{
    	       mx.indicate("info", "选定的记录条数不能超过一条。");
    	       return;
    	}
    	if (confirm("您确认生效范本吗？")){
	    	var restClient = mx.rpc.RESTClient();
	    	var result = restClient.getSync(templatemanage.mappath("~/rest/cocontracttemplate/effect?id="+v_dataGrid.getCheckedIDs()[0]));
	    	if(result == "success"){
	    		mx.indicate("info","范本生效成功！");
	    		v_dataGrid.load();
	    	}else if(result == "exist"){
	    		mx.indicate("info","范本已经生效！");
	    	}
    	}
    }

    /**
     * 查询
     */
    me.search = function(){
    	me.view.getDataGrid().setFilter(me.view._initParams());
    	me.view.getDataGrid().load();
    }
    
    /**
     * 下载范本
     */
    me._btnDownload_onclick = function(){
    	v_dataGrid = me.view.getDataGrid();
    	if (v_dataGrid.getCheckedIDs().length == 0)
        {
             mx.indicate("info", "请勾选一条下载记录。");
             return;
        }
        //多选框勾选记录，判断是否选择多条
    	if(v_dataGrid.getCheckedIDs().length > 1)
    	{
    	       mx.indicate("info", "选定的记录条数不能超过一条。");
    	       return;
    	}
    	var guid = v_dataGrid.getCheckedIDs()[0];
    	var restClient = new mx.rpc.RESTClient();
    	var result = restClient.getSync(templatemanage.mappath("~/rest/cocontracttemplate/isokdown?guid="+guid));
    	if(result=="fail"){
    		mx.indicate("warn","没有可下载的范本，请先上传范本！");
    		return;
    	}
    	window.open(templatemanage.mappath("~/rest/cocontracttemplate/down?guid="+guid));
    }
    
    /**
     * 说明
     */
    me._btnExplain_onclick = function(){
    	var win = utils.commonFun.WinMananger.create({ title: "说明", width: 650, height: 480,reusable: true});
    	var explainView = mx.views.View();
        ($("div.container view",explainView.$e).prevObject)
            .append($("<p><br>&nbsp;说明：<br>"+ 
        			  "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1. 对合同范本进行维护管理，包括查询范本、新增范本、修改范本、删除范本和生效范本等，可过滤是否显示失效范本。<br>"+ 
        			  "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2. 根据合同文本范围和合同类型【查询】过滤合同范本信息列表，如果选择“显示已失效参数”复选框查询列表将同时显示失效的合同范本信息。"+
        			  "</p>"));
		win.setView(explainView);
		win.setTitle(win.title);
    	win.showDialog();
    }
    
    /**
     * 用户可配置字段按钮
     */
    me._btnZdwh_onclick = function(){
    	var win = mx.windows.WindowManager().create({ title: "用户可配置合同字段", width: 750, height: 540,reusable: true});
    	var pzzdView = new tempfieldconfig.views.TempfieldConfigMainViewController().getView();
    	win.setView(pzzdView);
		win.setTitle(win.title);
    	win.showDialog();
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