$ns("contractExeTra.views");

$import("mx.permissions.Permission");
$import("mx.permissions.PermissionAgentClass");

contractExeTra.views.MainViewController = function()
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
            me.view = new contractExeTra.views.MainView({ controller: me });
        }
        return me.view;
    };

    /**
     * 获取表单视图对象
     */
    me._getDetailFromView = function(){
    	if (_detailView == null)
        {
            var mvc = new contractExeTra.views.DetailViewController();
            _detailView = mvc.getView();

			_detailView.getForm().entityContainer.off("saved", me._refreshDataGrid);
            _detailView.getForm().entityContainer.on("saved", me._refreshDataGrid);
        }
    	return _detailView;
    }

	// 加载列表数据。
	me._loadDataGrid = function(e)
	{
		me.searchFilter();
    	me.view.getDataGrid().load();
	}
	
	
	me.searchFilter = function(){
		var tenObj=me.getSeacrchMess();
		if(tenObj){
			me.view.getDataGrid().setFilter(tenObj);
		}else{
			me.view.getDataGrid().setFilter({});
		}
	}
	
	me.getSeacrchMess = function(){
		var filterValues={};
		var tenNum=0;
		var tenArr=["htTypeSearch","htzqSearch","kssjSearch","jssjSearch","htztSearch","gdfSearch","sdfSearch","htSquSearch","component14","component1"];
		for(var i=0;i<tenArr.length;i++){
			var tenStr=eval("me.view."+tenArr[i]).getValue();
			if(tenStr!=""&&tenStr!=null){
				tenNum++;
				filterValues[tenArr[i]]=tenStr;
			}
		}
		if(tenNum==0){
			return false;
		}else{
			return filterValues;
		}
	}
	
	// 表单视图保存后刷新列表数据。
	me._refreshDataGrid = function(e)
	{
		me.view.getDetailWindow().hide();
		me.searchFilter();
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
        me.searchFilter();
	    me.view.getDataGrid().load();
//	    me.view.renderChart();
    };
    
    me._btnSearch_onclick=function(){
        me.searchFilter();
	    me.view.getDataGrid().load();
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