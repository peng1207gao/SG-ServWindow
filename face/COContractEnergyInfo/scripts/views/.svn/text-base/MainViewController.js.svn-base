$ns("COContractEnergyInfo.views");

$import("mx.permissions.Permission");
$import("mx.permissions.PermissionAgentClass");

COContractEnergyInfo.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
	
    /**
     * 表单视图对象
     */
    var _detailView = null;
//    var timeno;
    
//    me.getView = function()
//    {
//        if (me.view == null)
//        {
//            me.view = new COContractEnergyInfo.views.MainView({ controller: me });
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
            me.view = new COContractEnergyInfo.views.MainView(e);
        }
        return me.view;
    };

    /**
     * 获取表单视图对象
     */
    me._getDetailFromView = function(){
    	if (_detailView == null)
        {
            var mvc = new COContractEnergyInfo.views.DetailViewController();
            _detailView = mvc.getView();
            _detailView.pview = me.getView();

			_detailView.getForm().entityContainer.off("saved", me._refreshDataGrid);
            _detailView.getForm().entityContainer.on("saved", me._refreshDataGrid);
        }
    	return _detailView;
    }

	// 加载列表数据。
	me._loadDataGrid = function(e)
	{
    	me.view.getDataGrid().load();
    	me.view.getDataTree().load();
	}

	// 表单视图保存后刷新列表数据。
	me._refreshDataGrid = function(e)
	{
		me.view.getDetailWindow().hide();
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
    
    /**
     * 新增
     */
    me._btnNew_onclick = function()
    {
    	var startDate=me.getView().sdataTimeEditor.value;
    	var endDate=me.getView().edataTimeEditor.value;
    	var mLabel1=me.getView().mLabel1.text;
    	var mLabel2=me.getView().mLabel2.text;
//    	timeno = me.getView().timeno;
//    	clickstime = me.getView().clickstime;
    	var ms1 = mLabel1.split(":");
    	var ms2 = mLabel2.split(":");
    	if(startDate==null) {
    		mx.indicate("info", "开始日期不能为空！");
//    		alert("开始日期不能为空!");
    		return;
    	}
    	if(endDate==null) {
    		mx.indicate("info", "结束日期不能为空！");
//			alert("结束日期不能为空!");
    		return;
    	}
    	var _sd = new Date(Date.parse(startDate.replace(/-/g,"/")));
    	var _ed = new Date(Date.parse(endDate.replace(/-/g,"/")));
    	if(_sd.getFullYear()==_ed.getFullYear()&&_sd.getMonth()==_ed.getMonth()) {
    	}else{
    		mx.indicate("info", "开始日期和结束日期必须为同一月！");
//    		alert("开始日期和结束日期必须为同一月!");
    		return;
    	}
    	if(_validDate(ms1[1], startDate)) {
    		mx.indicate("info", "开始日期小于默认开始日期！");
//    		alert("开始日期小于默认开始日期!");
    		return;
    	}
    	if(_validDate(endDate, ms2[1])) {
    		mx.indicate("info", "结束日期大于默认结束日期！");
//    		alert("结束日期大于默认结束日期!");
    		return;
    	}
    	if(_validDate(startDate, endDate)) {
    		mx.indicate("info", "开始日期大于结束日期！");
//    		alert("开始日期大于结束日期!");
    		return;
    	}
    	
    	var monthEnergy = 0;//该月的总电量,从后台取该月的总电量，需要根据合同id me.getView().contractid和开始时间startDate查询出该月的总电量
    	var contractId = me.getView().contractid;
    	var restClient = mx.rpc.RESTClient();
    	var result = restClient.getSync(COContractEnergyInfo.mappath("~/rest/cocontractenergyinfo/getMonthTotalEnergy"),
    		{"params":JSON.stringify({contractId:contractId,startDate:startDate})});
    	if(result.successful == true){
    		if(result.resultValue.items.length>0){
    			monthEnergy = result.resultValue.items[0];
    		}
    	}
    	
	    var _detailView = me._getDetailFromView();
	    //设置对象id
        _detailView.objID = null;
        _detailView.param = me.getView().contractid;
        _detailView.stDate = startDate;
        _detailView.enDate = endDate;
        _detailView.monthEnergy = monthEnergy;
//        _detailView.timeno = timeno;
//        _detailView.clickstime = clickstime;
        _showDetailFormView(_detailView,"表单填写");
    };
    /**
     * 李源
     * 判断开始日期是否大于截止日期
     */
	function _validDate(startDate,endDate)
	{
		var sds = startDate.split('-');
		var eds = endDate.split('-');
		if(sds[0]>eds[0]) {
			return true;
		}else if(sds[0]==eds[0]&&sds[1]>eds[1]) {
			return true;
		}else if(sds[0]==eds[0]&&sds[1]==eds[1]&&sds[2]>eds[2]) {
			return true;
		}else return false;
//		var sd = new Date(Date.parse(startDate.replace(/-/g,"/")));
//		var ed = new Date(Date.parse(endDate.replace(/-/g,"/")));
//		if(sd.getFullYear()>ed.getFullYear())
//		{
//			return true;
//		}
//		else if(sd.getFullYear()==ed.getFullYear()&&sd.getMonth()>ed.getMonth())
//		{
//			return true;
//		}
//		else if(sd.getFullYear()==ed.getFullYear()&&sd.getMonth()==ed.getMonth()&&sd.getDay()>ed.getDay())
//		{
//			return true;
//		}
//		else 
//			return false;
	}
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
		     me.getView().getDataTree().load();
		}
    };
    
    /**
     * 保存
     */
    me._btnSave_onclick = function()
    {
        me.view.getDataGrid().entityContainer.save();
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
    	
    	var startDate=me.getView().sdataTimeEditor.value;
    	var endDate=me.getView().edataTimeEditor.value;
    	var monthEnergy = 0;//该月的总电量,从后台取该月的总电量，需要根据合同id me.getView().contractid和开始时间startDate查询出该月的总电量
    	var contractId = me.getView().contractid;
    	var restClient = mx.rpc.RESTClient();
    	var result = restClient.getSync(COContractEnergyInfo.mappath("~/rest/cocontractenergyinfo/getMonthTotalEnergy"),
    		{"params":JSON.stringify({contractId:contractId,startDate:startDate})});
    	if(result.successful == true){
    		if(result.resultValue.items.length>0){
    			monthEnergy = result.resultValue.items[0];
    		}
    	}
    	
    	var _detailView = me._getDetailFromView();
    	_detailView.objID = v_dataGrid.getCheckedIDs()[0];
    	_detailView.param = me.getView().contractid;
        _detailView.stDate = startDate;
        _detailView.enDate = endDate;
    	_detailView.monthEnergy = monthEnergy;
	    //显示详细信息页面
    	_showDetailFormView(_detailView,"表单编辑");
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