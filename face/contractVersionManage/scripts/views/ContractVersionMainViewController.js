$ns("contractVersionManage.views");

$import("mx.permissions.Permission");
$import("mx.permissions.PermissionAgentClass");
$import("mx.utils.ExcelUtil");
//$import("contractVersionManage.views.ContractVersionDetailViewController");
//$import("contractVersionManage.views.ContractVersionMainView");

contractVersionManage.views.ContractVersionMainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
	
    /**
     * 表单视图对象
     */
    var _detailView = null;
    
//    me.getView = function()
//    {
//        if (me.view == null)
//        {
//            me.view = new contractVersionManage.views.ContractVersionMainView({ controller: me });
//        }
//        return me.view;
//    };
    
    me.getView = function(e)
    {
        if (me.view == null)
        {
        	if (e == null) {
				e = new Object();
			}
        	e.controller = me;
            me.view = new contractVersionManage.views.ContractVersionMainView(e);
        }
        return me.view;
    };

    /**
     * 获取表单视图对象
     */
    me._getDetailGridView = function(){
    	if (_detailView == null)
        {
            var mvc = new contractVersionManage.views.ContractVersionDetailViewController();
            _detailView = mvc.getView();

//			_detailView.getForm().entityContainer.off("saved", me._refreshDataGrid);
//            _detailView.getForm().entityContainer.on("saved", me._refreshDataGrid);
        }
    	return _detailView;
    }
    
    var contractAddView = null;
    function _getContractAddView(p_item){
    	var restClient = new mx.rpc.RESTClient();
    	var contractid = p_item.values["contractid"];//合同id
    	var contracttype = p_item.values["contracttype"];//合同类型
    	var coTName = p_item.values["contracttype"];//合同类型
    	var htxlList = restClient.getSync(contractVersionManage.mappath("~/rest/coContractbaseinfoV2Act/getHtxl"),{contractid:contractid});
    	var htxlName = null;
    	if(htxlList!=null && htxlList.length>0){
    		for(var i=0;i<htxlList.length;i++){
    			htxlName = htxlList[i][1];
    		}
    	}
    	var mvc = new htadd.views.zjxzViewController();
    	contractAddView = mvc.getView({objID:contractid,readType:'1',coTName:coTName,htxlName:htxlName,coType:contracttype});
    	
    	return contractAddView;
    }

    me.compareContractaVersion = function(){
		var _detailView = me._getDetailGridView();
	    //设置对象id
        _detailView.objID = me.getView().contractid;
        _showDetailGridView(_detailView,"合同历史版本信息"); 
		
    }
    
//	// 加载列表数据。
//	me._loadDataGrid = function(e)
//	{
//    	me.view.getDataGrid().load();
//	}
//
//	// 表单视图保存后刷新列表数据。
//	me._refreshDataGrid = function(e)
//	{
//		me.view.getDetailWindow().hide();
//    	me.view.getDataGrid().load();
//	}

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
     * 显示表单视图
     * @param p_view : 需要显示的视图对象
     * @param p_title : 对话框的标题
     */
    function _showDetailGridView(p_view,p_title){
    	var win = me.view.getDetailWindow();
    	if(typeof p_view != "undefined"){
    		p_view.load();
    		//设置显示视图、标题信息
    		win.setView(p_view);
    		win.setTitle(p_title ? p_title : win.title);
    	}
    	win.showDialog();
    }
    
     function _showContractManageView(p_view,p_title){
    	var win = me.view.getDetailWindow();
    	if(typeof p_view != "undefined"){
    		//设置显示视图、标题信息
    		win.setView(p_view);
    		win.setTitle(p_title ? p_title : win.title);
    	}
    	win.showDialog();
    }
    
    me._showContractAddView = function(p_item){
    	var view = _getContractAddView(p_item);
    	_showContractManageView(view,"合同管理");
    }
    
    me.endOfClass(arguments);
    return me;
};