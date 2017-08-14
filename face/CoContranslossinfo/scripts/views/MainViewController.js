$ns("CoContranslossinfo.views");

//$import("CoContranslossinfo.views.DetailViewController");
//$import("CoContranslossinfo.views.MainView");

CoContranslossinfo.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
	
    /**
     * 表单视图对象
     */
    var _detailView = null;
    
    //合同Id，显示指定合同下的线损信息
    me.contractId = null;
    
    me.getView = function(e)
    {
        if (me.view == null)
        {
        	if (e == null) {
				e = new Object();
			}
        	e.controller = me;
            me.view = new CoContranslossinfo.views.MainView(e);
        }
        return me.view;
    };

    /**
     * 获取表单视图对象
     */
    me._getDetailFromView = function(){
    	if (_detailView == null)
        {
            var mvc = new CoContranslossinfo.views.DetailViewController();
            
            //设置表单关联的合同Id
            mvc.contractId = me.getView().contractId;
            _detailView = mvc.getView();

			_detailView.getForm().entityContainer.off("saved", me._refreshDataGrid);
            _detailView.getForm().entityContainer.on("saved", me._refreshDataGrid);
        }
    	return _detailView;
    }

	// 加载列表数据。
	me._loadDataGrid = function(e)
	{
		me._dataGrid_setFilter();
    	me.view.getDataGrid().load();
	}

	// 表单视图保存后刷新列表数据。
	me._refreshDataGrid = function(e)
	{
		me.view.getDetailWindow().hide();
		
		me._dataGrid_setFilter();
    	me.view.getDataGrid().load();
	}

    me._onactivate = function(e)
    {
        //加载数据
        me._dataGrid_setFilter();
	    me.view.getDataGrid().load();
    };
    
    /**
     * 设置线损数据表格过滤条件，方法中添加了合同Id过滤参数，
     * 在渲染该view之前必须设置me.contractId的值，否则会抛出异常信息。
     * 
     */
    me._dataGrid_setFilter = function() 
    {
    	me.view.getDataGrid().setFilter({contractid:me.getView().contractId});
    }
    
    /**
     * 数据表格加载完成后执行的函数
     * 
     */
    me._dataGrid_onload = function(e) {
    	me.view.getDataGrid().$e.find("#head table tbody tr td:first span").text("序号");
    }
    
    /**
     * 新增
     */
    me._btnNew_onclick = function()
    {
	    var _detailView = me._getDetailFromView();
	    //设置对象id
        _detailView.objID = null;
        _showDetailFormView(_detailView,"线损信息增加页面");
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
    	_showDetailFormView(_detailView,"线损信息编辑页面");
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
    
    me.endOfClass(arguments);
    return me;
};