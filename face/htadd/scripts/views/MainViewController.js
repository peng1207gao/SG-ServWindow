$ns("htadd.views");

$import("mx.permissions.Permission");
$import("mx.permissions.PermissionAgentClass");
$import("mx.windows.WindowManager");
//$import("htadd.views.MainView");

htadd.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    var _htaddView=null;
    
    me.getView = function(e)
    {
        if (me.view == null)
        {
        	if (e == null) {
				e = new Object();
			}
        	e.controller = me;
        	me.view = new htadd.views.MainView(e);
        }
        return me.view;
    };
//    me.getView = function()
//    {
//        if (me.view == null)
//        {
//            me.view = new htadd.views.MainView({controller: me});
//        }
////        me._btnNew_onclick();
//        return me.view;
//    };
//    
  
    //新增加
//    me._btnNew_onclick = function()
//    {
//    	me.getView();
//        // TODO： 此处新增的数据需要将服务端返回的 id 值设置到 GridItem 上。
//    	if (_htaddView == null)
//        {	
//    		_htaddView=me.view.getHtAddWindow(); 
//        }
//        _showAddHtView(_htaddView,"合同添加");
//    };
    function _showAddHtView(p_view,p_title){
    	p_view.showDialog();
    }
    me.endOfClass(arguments);
    return me;
};