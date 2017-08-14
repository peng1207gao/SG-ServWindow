$ns("htSelfDefinedQuery.views");

$import("mx.permissions.Permission");
$import("mx.permissions.PermissionAgentClass");
//$import("htSelfDefinedQuery.views.DetailView");
$import("mx.rpc.RESTClient");
//$import("htSelfDefinedQuery.views.MainViewController");


htSelfDefinedQuery.views.DetailViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    var restClient = new mx.rpc.RESTClient();
  
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new htSelfDefinedQuery.views.DetailView({controller: me, alias:"htSelfDefinedQueryDetailView"});
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
    	var queryNos = [];
		if(me.getView().check1.isChecked()){
    		
    		queryNos.add(1);
    	}
		if(me.getView().check2.isChecked()){
    		
    		queryNos.add(2);
    	}
		if(me.getView().check3.isChecked()){
    		
    		queryNos.add(3);
    	}
		if(me.getView().check4.isChecked()){
    		
    		queryNos.add(4);
    	}
		
		if(me.getView().check5.isChecked()){
    		
    		queryNos.add(5);
    	}
		
		if(me.getView().check6.isChecked()){
    		
    		queryNos.add(6);
    	}

		if(me.getView().check7.isChecked()){
			
			queryNos.add(7);
		}
		if(me.getView().check8.isChecked()){
			
			queryNos.add(8);
		}
		me.view.parent.close();
		//var mainView = new htSelfDefinedQuery.views.MainView().queryAddCodition(queryNos);
		me.getView()._mainView.queryAddCodition(queryNos);
		//mainView.container1.addControl(mainView.label6);
		//mainView.container1.addControl(me.label6);	//添加标签
    };
    
    me._btnClose_onclick = function(){
    	
    	 me.view.parent.close();
    }
    
    return me.endOfClass(arguments);
};