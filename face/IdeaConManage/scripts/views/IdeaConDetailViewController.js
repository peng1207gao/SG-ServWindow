$ns("IdeaConManage.views");

$import("mx.permissions.Permission");
$import("mx.permissions.PermissionAgentClass");
//$import("IdeaConManage.views.IdeaConDetailView");

IdeaConManage.views.IdeaConDetailViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
  
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new IdeaConManage.views.IdeaConDetailView({controller: me, alias:"IdeaConManageIdeaConDetailView"});
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
        me.view.getForm().save();
    };
    
    return me.endOfClass(arguments);
};