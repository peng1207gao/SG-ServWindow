$ns("thermalpower.views");

$import("mx.permissions.Permission");
$import("mx.permissions.PermissionAgentClass");
$import("thermalpower.views.DetailViewController");
$import("thermalpower.views.DataGrid");

thermalpower.views.DataGridController = function()
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
            me.view = new thermalpower.views.DataGrid({ controller: me });
        }
        return me.view;
    };

	// 加载列表数据。
	me._loadDataGrid = function(e)
	{
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
  
    me.endOfClass(arguments);
    return me;
};