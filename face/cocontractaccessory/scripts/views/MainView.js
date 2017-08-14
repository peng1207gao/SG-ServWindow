$ns("cocontractaccessory.views");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGrid");
$import("mx.containers.HSplit");
$import("mx.containers.Panel");
$import("mx.controls.Button");
$import("mx.containers.Panel");
$import("mx.containers.Container");

cocontractaccessory.views.MainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    //网格列表
    var _dataGrid = null;
    //表单窗口对象
    var _detailWin = null;

    base.init = me.init;
    me.init = function()
    {
        base.init();
        _initControls();
    };
    
    function _initControls()
    {
    	_initLayout();
    	
    	// TODO readType为1时，弹出页面为只读，0时非只读，按自己需要
    	if(me.params.readType !='1'){
    		_initOperationButton();
    	}
    	
	    _initDataGrid();
    	_initDetailWindow();
    	
        me.on("activate", me.controller._onactivate);
    }
    
    /**
     * 界面布局，上下布局：上部操作按钮，下部数据表格
     */
    function _initLayout() {
    	me.hSplit = new mx.containers.HSplit({
    		borderThick:"0px",
    		rows:"55, auto"
    	});
    	
    	//业务操作区域
    	me.operationPanel = new mx.containers.Panel(
    		{width:"100%", title:"业务操作"}
    	);
    	me.hSplit.addControl(me.operationPanel, 0);
    	
    	me.addControl(me.hSplit);
    }
    
    function _initOperationButton() {
    	
    	var operationButtonContainer = new mx.containers.Container(
    		{width:"100%",padding:"2"}
    	);
    	me.operationPanel.addControl(operationButtonContainer);
    	
    	var newButton = new mx.controls.Button(
    		{text:"新增",left:20,onclick:me.controller._btnNew_onclick}
    	);
    	operationButtonContainer.addControl(newButton);
    	
    	var delButton = new mx.controls.Button(
    		{text:"删除",left:60,onclick:me.controller._btnDelete_onclick}
    	);
    	operationButtonContainer.addControl(delButton);
    }
    
    function _initDataGrid()
    {
        var restUrl = "~/rest/cocontractaccessory/";
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : cocontractaccessory.mappath(restUrl),
            iscID : "-1",
            primaryKey: "guid"
        });
        
        _dataGrid = new mx.datacontrols.DataGrid({   
			alias: "cocontractaccessoryMainViewDataGrid",
			columns:[
	        {	name: "guid", caption: "GUID" , editorType: "TextEditor"},
	        {	name: "contractid", caption: "CONTRACTID" , editorType: "TextEditor"},
	        {	name: "contractrole", caption: "合同角色" , editorType: "DropDownEditor"},
	        {	name: "participantid", caption: "机组名称" , editorType: "DropDownEditor",	width:200}
            ],
            displayCheckBox: me.params.readType !='1',   //如果界面是只读的，则隐藏选择框
	        displayPrimaryKey:false,
            allowEditing: false,
            allowInterlacedRow: true,
            displayRowNumber: true,
	        pageSize : 10,
            entityContainer: gridEntityContainer
        });
        
        me.hSplit.addControl(_dataGrid, 1);
    }
    
    /**
     * 初始化表单视图窗口对象
     */
    function _initDetailWindow(){
    	_detailWin = utils.commonFun.WinMananger.create({
			reusable: true,//是否复用
			width:440,
			height:480,
			title:"表单维护"
		});
    }

    /**
     * 获取表单视图窗口对象
     */
    me.getDetailWindow = function(){
    	return _detailWin;
    }
    
    /**
     * 获取DataGrid网格列表对象
     */
    me.getDataGrid = function(){
    	return _dataGrid;
    }
    
	me.endOfClass(arguments)
    return me;
};