$ns("htquery.views");

$import("mx.permissions.Permission");
$import("mx.permissions.PermissionAgentClass");
//$import("htquery.views.DetailView");
$import("mx.rpc.RESTClient");

htquery.views.DetailViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    var restClient = new mx.rpc.RESTClient();
  
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new htquery.views.DetailView({controller: me, alias:"htqueryDetailView"});
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
    
    me._btnDown_onclick = function()
    {
    	
    	var _dataGrid = me.getView()._dataGrid;
    	if (_dataGrid.getCheckedIDs().length == 0)
        {
			mx.indicate("warn", "请选择合同附件！");
			return;
        }
		//多选框勾选记录，判断是否选择多条
		if(_dataGrid.getCheckedIDs().length>1)
		{
			mx.indicate("warn", "只能选择一条合同附件！");
			return;
		}
		
		var pkVal =  _dataGrid.getCheckedIDs()[0];
		if(pkVal!=""&&pkVal!=null){
			window.open(htquery.mappath("~/rest/cocontractaffixinfo/down?guid="+pkVal));
//			restClient.get(
//					htquery.mappath("~/rest/cocontractaffixinfo/down"),
//					{params: JSON.stringify({ itemType: pkVal })},
//				    function(p_context) // 回调函数
//				    {
//				        if(p_context=="false"){
//				        	mx.indicate("warn", "下载失败！");
//				        }else{
//				        	mx.indicate("warn", "下载成功！");
//				        }
//				    } 
//				); 

		}
    };
    
    me._btnDelete_onclick = function()
    {
    	var v_dataGrid = me.getView()._dataGrid;
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
    
    me._btnClose_onclick = function(){
    	
    	 me.view.parent.close();
    }
    
    return me.endOfClass(arguments);
};