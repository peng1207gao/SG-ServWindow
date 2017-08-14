$ns("tempfieldconfig.views");

$import("mx.permissions.Permission");
$import("mx.permissions.PermissionAgentClass");

tempfieldconfig.views.TempfieldConfigMainViewController = function()
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
            me.view = new tempfieldconfig.views.TempfieldConfigMainView({ controller: me });
        }
        return me.view;
    };

    /**
     * 获取表单视图对象
     */
    me._getDetailFromView = function(){
    	if (_detailView == null)
        {
            var mvc = new tempfieldconfig.views.TempfieldConfigDetailViewController();
            _detailView = mvc.getView();

			_detailView.getForm().entityContainer.off("saved", me._refreshDataGrid);
            _detailView.getForm().entityContainer.on("saved", me._refreshDataGrid);
        }
    	return _detailView;
    }

	// 加载列表数据。
	me._loadDataGrid = function(e)
	{
    	me.view.getDataGrid().load();
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
     * 查询
     */
    me._btnSearch_onclick = function(){
    	//校验“对应范本字段”输入框，包括输入值限制长度和不能包含非法字符
    	var tvalue = me.view.tempName.value;
		if(tvalue == null || tvalue == "") {//@判断tvalue
			tvalue = "";
		} 
		var validateController = new validate.views.ValidateMainViewController();
		var validateContent = validateController.getValidate("maxLength");
		//校验查询条件 长度100
		var isValid=validateContent.validator(tvalue,[100]);
		if (!isValid) {
			mx.indicate("warn", "对应范本字段："+validateContent.message.format([100]));
			return;
		}
    	var v_dataGrid = me.view.getDataGrid();
    	v_dataGrid.setFilter(me.getView().getParams());
    	v_dataGrid.load();
    }
    
    /**
     * 新增
     */
    me._btnNew_onclick = function()
    {
    	me.chaseWin = utils.commonFun.WinMananger.create({
			reusable: true,//是否复用
			width:400,
			height:200,
			title:"选择窗口"
		});
    	var chaseView = mx.views.View();
    	var container1 = new mx.containers.Container({
    		id: "container1",
    		height: "50%",
    		padding: "2px"
    	});
    	var container2 = new mx.containers.Container({
    		id: "container2",
    		height: "50%",
    		padding: "2px"
    	});
    	chaseView.addControl(container1);
    	chaseView.addControl(container2);
    	me.tempLabel = new mx.controls.Label({
    		text: "对应合同范本:",
		    textAlign: "center",
		    verticalAlign: "middle"
    	});
    	container1.addControl(me.tempLabel);
    	me.contractTemp = utils.dropDownEditor.ContractTemplate.ContractTemplateDropDownEditor(false,null);
    	me.contractTemp.setWidth(200);
    	container1.addControl(me.contractTemp);
    	
    	var okBtn = new mx.controls.Button({
    		text: "确定",
    		left: 120,
    		onclick: _btnOk_onclick
    	});
    	container2.addControl(okBtn);
    	var cancelBtn = new mx.controls.Button({
    		text: "取消",
    		left: 130,
    		onclick: _btnCancel_onclick
    	});
    	container2.addControl(cancelBtn);
    	
    	me.chaseWin.setView(chaseView);
		me.chaseWin.setTitle(me.chaseWin.title);
    	me.chaseWin.showDialog();
        // TODO： 此处新增的数据需要将服务端返回的 id 值设置到 GridItem 上。
//	    var _detailView = me._getDetailFromView();
//	    //设置对象id
//        _detailView.objID = null;
//        _showDetailFormView(_detailView,"表单填写");
    };
    
    function _btnOk_onclick(){
    	var _detailView = me._getDetailFromView();
	    //设置对象id
        _detailView.objID = null;
        _detailView.tempId = me.contractTemp.value;
        _showDetailFormView(_detailView,"表单填写");
    	me.chaseWin.close();
    }
    
    function _btnCancel_onclick(){
    	me.chaseWin.close();
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
    	var _detailView = me._getDetailFromView();
    	_detailView.objID = v_dataGrid.getCheckedIDs()[0];
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