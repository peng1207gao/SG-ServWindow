$ns("CoContranslossinfo.views");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGrid");
$import("mx.containers.HSplit");
$import("mx.containers.Panel");
$import("mx.controls.Button");
$import("mx.containers.Panel");
$import("mx.containers.Container");

CoContranslossinfo.views.MainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    //网格列表
    var _dataGrid = null;
    //表单窗口对象
    var _detailWin = null;
    
    //业务操作区域
	me.operationPanel = null;
    
    me.hSplit = null;

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
    	
    	var editButton = new mx.controls.Button(
    		{text:"编辑",left:60,onclick:me.controller._btnEdit_onclick}
    	);
    	operationButtonContainer.addControl(editButton);
    	
    	var delButton = new mx.controls.Button(
    		{text:"删除",left:100,onclick:me.controller._btnDelete_onclick}
    	);
    	operationButtonContainer.addControl(delButton);
    }
    
    function _initDataGrid()
    {
        var restUrl = "~/rest/cocontranslossinfo/";
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : CoContranslossinfo.mappath(restUrl),
            iscID : "-1",
            primaryKey: "guid"
        });
        
        _dataGrid = new mx.datacontrols.DataGrid({   
			alias: "CoContranslossinfoMainViewDataGrid",
			columns:[
			    {name: "guid", caption: "GUID" , editorType: "TextEditor"},
	            {name: "contractid", caption: "合同ID" , editorType: "TextEditor", visible:false},
	            {name: "transmission", caption: "输电方" , editorType: "DropDownEditor"},
	            {name: "linkid", caption: "线损路径" , editorType: "DropDownEditor"},
	            {name: "linestartendgate", caption: "线损分段" , editorType: "TextEditor"},
	            {name: "loss", caption: "分段线损率(%)" , editorType: "TextEditor"},
	            {name: "direction", caption: "方向" , editorType: "DropDownEditor"}
            ],
            displayCheckBox: true,
	        displayPrimaryKey:false,
            allowEditing: false,
            allowInterlacedRow: true,
            displayRowNumber: true,
	        pageSize : 10,
            entityContainer: gridEntityContainer,
            onload: me.controller._dataGrid_onload
        });
        
        me.hSplit.addControl(_dataGrid, 1);
    }

    /**
     * 初始化表单视图窗口对象
     */
    function _initDetailWindow(){
    	_detailWin = utils.commonFun.WinMananger.create({
			reusable: true,//是否复用
			width:640,
			height:280,
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