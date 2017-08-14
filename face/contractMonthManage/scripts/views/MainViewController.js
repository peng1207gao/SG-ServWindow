$ns('contractMonthManage.views');

$import('contractMonthManage.views.MainView');

contractMonthManage.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var restClient = new mx.rpc.RESTClient();
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new contractMonthManage.views.MainView( { controller: me } );
        }
        
        return me.view;
    };
    
    me._onactivate = function()
    {
       
    };
    
    me.getEconUnitView = function(){
    	var dataGrid = me.getView().getDataGrid();
    	if (dataGrid.getCheckedIDs().length == 0)
        {
             mx.indicate("info", "请至少勾选一条待编辑记录。");
             return;
        }
		var _econUnitViewController = new resolveelectricquantitytounit.views.ChooseEconUnitViewController();
		var _econUnitView = _econUnitViewController.getView();
		
		var data = {"contractids":dataGrid.getCheckedIDs()};
		_econUnitView._initUnitItem(JSON.stringify(data));
		var chooseUnitDataGrid = _econUnitView.getDataGrid();
		
		var filterData = {"contractids":JSON.stringify(dataGrid.getCheckedIDs())};
		
		var reg=new RegExp("\\\\","g"); //创建正则RegExp对象  
		var s = JSON.stringify(filterData);
		s = s.replace(reg,"");
		_econUnitView.contractids = s;
	
		chooseUnitDataGrid.setFilter(filterData);
		chooseUnitDataGrid.load();
		var win = _showChooseUnitView(_econUnitView,"选择经济机组");

		win.showDialog(function(chooseView){
			var view = chooseView.view;
			if(view.returnstatus == "confirm"){//点击【确定】按钮，回调处理
				var items = view.getDataGrid().getCheckedItems();
				var itemsArray = new Array();
				for(var i = 0; i < items.length; i++){
					var item = items[i];
					var contractunit = item.values.contractid + "&" + 
							item.values.contractrole + "&" + 
							item.values.ecounitid;
					itemsArray.push(contractunit);
				}
				var restUrl = contractMonthManage.mappath("~/rest/cocontractmonthqty/chooseUnit");
		    	var s = restClient.sendSync(restUrl, "POST", JSON.stringify({"items":itemsArray}));
		    	if(s.successful){
		    		me.getView().loadGrid1();
		    	} else {
					mx.indicate("info", s.resultHint);		    	
		    	}
			}
		});
	}
	
	function _showChooseUnitView(p_view,p_title){
    	var win = me.view.getChooseUnitWindow();
    	if(typeof p_view != "undefined"){
    		p_view.load();
    		//设置显示视图、标题信息
    		win.setView(p_view);
    		win.setTitle(p_title ? p_title : win.title);
    	}
    	return win;
    }
    
    me.showEconResolve = function(p_item){
    	var contractid = p_item.values["col0"];
    	var monthqtytype = p_item.values["monthqtytype"];
    	var resultViewController = new contractMonthManage.views.ShowResolveResultViewController();
		var resultView = resultViewController.getView();
		
		var dataGrid = resultView.getDataGrid();
		var data = {"contractid": contractid,"monthqtytype":monthqtytype};
		dataGrid.setFilter(JSON.stringify(data));
		dataGrid.load();
    	var win = _showChooseUnitView(resultView,"电量分解结果");
    	win.showDialog();
    }
    
    return me.endOfClass(arguments);
};