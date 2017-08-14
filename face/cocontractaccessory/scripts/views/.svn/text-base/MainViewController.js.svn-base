$ns("cocontractaccessory.views");

cocontractaccessory.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
	
    var _detailView = null;
    
    //合同Id，显示指定合同下的机组信息
    //me.contractId = null;
    
    me.getView = function(e)
    {
        if (me.view == null)
        {
        	if (e == null) {
				e = new Object();
			}
        	e.controller = me;
            me.view = new cocontractaccessory.views.MainView(e);
        }
        return me.view;
    };

    /**
     * 获取表单视图对象
     */
    me._getDetailFromView = function(){
    	if (_detailView == null)
        {
            var mvc = new cocontractaccessory.views.AddListViewController();
            _detailView = mvc.getView();
            _detailView.outerController = me;
        }
    	
    	//重新打开该视图时清除数据表格中的数据
    	_detailView.controller._init_dataGrid();
    	
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
     * 获得合同Id，该合同Id是外部程序在打开该视图之前传递进来的
     */
    me.getContractId = function() {
    	return me.contractId;
    }
    
    /**
     * 新增
     */
    me._btnNew_onclick = function()
    {
	    var _detailView = me._getDetailFromView();
	    //设置对象id
        _detailView.objID = null;
        _detailView.contractid = me.getView().contractId;
        _detailView._initDropDownEditor();
        _showDetailFormView(_detailView,"添加合同机组");
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