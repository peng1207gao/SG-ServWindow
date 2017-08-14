$ns("cocontractmembership.views");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGrid");
$import("mx.containers.HSplit");
$import("mx.containers.VSplit");
$import("mx.containers.Panel");
$import("mx.controls.Button");
$import("mx.controls.Label");
$import("mx.containers.Container");
$import("mx.editors.DateTimeEditor");
$import("mx.editors.DropDownTreeEditor");
$import("mx.editors.TextEditor");
$import("mx.editors.DropDownEditor");
//$import("utils.dropDownEditor.MarketTree.MarketDropDownEditor");


cocontractmembership.views.DetailView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    //数据表格查询过滤参数编辑器集合
    me.searchEditors = {};
    
    base.init = me.init;
    me.init = function()
    {
        base.init();

        _initControls();
    };
    
    function _initControls()
    {
    	//画界面布局和控件
    	_initLayout();
    	_initDataGridLayout();
    	_initSearchPanel();
    	_initOperationPanel();
    	_initLeftDataGrid();
    	_initCenterButton();
    	_initRightDataGrid();
        me.on("activate", me.controller._onactivate);
    }
    
    /**
     * 视图布局，上下两部分：上面是查询和操作，下面是数据表格
     */
    function _initLayout() {
    	me.hSplit = new mx.containers.HSplit({
    		borderThick:"0px", rows:"160, auto"
    	});
    	
    	//业务查询区域
    	me.searchPanel = new mx.containers.Panel(
    		{width:"100%", height:"90", title:"业务查询"}
    	);
    	me.hSplit.addControl(me.searchPanel, 0);
    	
    	//业务操作区域
    	me.operationPanel = new mx.containers.Panel(
    		{width:"100%", height:"60", title:"业务操作"}
    	);
    	me.hSplit.addControl(me.operationPanel, 0);
    	
    	me.addControl(me.hSplit);
    }
    
    /**
     * 
     */
    function _initSearchPanel() {
    	var searchButtonContainerOne = new mx.containers.Container(
    		{width:"100%",height:"30",padding:"2"}
    	);
    	me.searchPanel.addControl(searchButtonContainerOne);
    	
    	//
    	var ssywcjLabel = new mx.controls.Label(
    		{text:"市场:",width:"60",textAlign:"right",right:4}
    	);
    	searchButtonContainerOne.addControl(ssywcjLabel);
    	me.searchEditors.shareMarket = new 
    	utils.dropDownEditor.MarketTree.MarketDropDownEditor(true, null, false); //业务场景下拉框
    	searchButtonContainerOne.addControl(me.searchEditors.shareMarket);
    	
    	//
    	var lxLabel = new mx.controls.Label(
    		{text:"类型:",width:"60",textAlign:"right",right:4}
    	);
    	searchButtonContainerOne.addControl(lxLabel);
    	me.searchEditors.participantType = new mx.editors.DropDownEditor(
    		{width:"150", allowEditing: false, displayMember: "name", valueMember: "value", 
    			url: cocontractmembership.mappath("~/../ETradePublicUtils/rest/baGencodeTypeList/getList?type=38")}
    	);
    	searchButtonContainerOne.addControl(me.searchEditors.participantType);
    	
    	//
    	var htxlmcLabel = new mx.controls.Label(
    		{text:"市场成员名称:",width:"85",textAlign:"right",right:4}
    	);
    	searchButtonContainerOne.addControl(htxlmcLabel);
    	me.searchEditors.participantName = new mx.editors.TextEditor(
    		{width:"200"}
    	);
    	searchButtonContainerOne.addControl(me.searchEditors.participantName);
    	
    	
    	var searchButtonContainerTwo = new mx.containers.Container(
    		{width:"100%",height:"30",padding:"2"}
    	);
    	me.searchPanel.addControl(searchButtonContainerTwo);
    	
    	//
    	var rlfwLabel = new mx.controls.Label(
    		{text:"容量范围:",width:"60",textAlign:"right",right:4}
    	);
    	searchButtonContainerTwo.addControl(rlfwLabel);
    	me.searchEditors.generatorClass = new mx.editors.DropDownEditor(
    		{width:"200", allowEditing: false, displayMember: "name", valueMember: "value", 
    			url: cocontractmembership.mappath("~/../ETradePublicUtils/rest/baGencodeTypeList/getList?type=106")}
    	);
    	searchButtonContainerTwo.addControl(me.searchEditors.generatorClass);
    	
    	//
    	var gyzyLabel = new mx.controls.Label(
    		{text:"公用/自备:",width:"60",textAlign:"right",right:4}
    	);
    	searchButtonContainerTwo.addControl(gyzyLabel);
    	me.searchEditors.commercialType = new mx.editors.DropDownEditor(
    		{width:"150", allowEditing: false, displayMember: "name", valueMember: "value", 
    			url: cocontractmembership.mappath("~/../ETradePublicUtils/rest/baGencodeTypeList/getList?type=20")}
    	);
    	searchButtonContainerTwo.addControl(me.searchEditors.commercialType);
    }
    
    /**
     * 
     */
    function _initOperationPanel() {
    	
    	var operationButtonContainer = new mx.containers.Container(
    		{width:"100%",height:"40",padding:"2"}
    	);
    	me.operationPanel.addControl(operationButtonContainer);
    	
    	var searchButton = new mx.controls.Button(
        	{text:"查询",left:20,onclick:me.controller._btnSearch_onclick}
        );
        operationButtonContainer.addControl(searchButton);
        	
    	var okButton = new mx.controls.Button(
    		{text:"确认",left:60,onclick:me.controller._btnOk_onclick}
    	);
    	operationButtonContainer.addControl(okButton);
    }
    
    /**
     * 设置
     */
    function _initDataGridLayout() {
    	me.vSplit = new mx.containers.VSplit({
    		cols:"46%, 54%", borderThick:"0px"
		});
    	me.hSplit.addControl(me.vSplit, 1);
    	
    	me.vSplitRight = new mx.containers.VSplit({
    		cols:"60, auto", borderThick:"0px"
		});
    	me.vSplit.addControl(me.vSplitRight, 1);
    }
    
    function _initLeftDataGrid() {
    	var restUrl = "~/rest/cocontractmembership/membership/";
    	var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
    		type : "local",
            loadMeta: false,
            iscID : "-1",
            primaryKey: "participantid"
        });
    	
    	me.leftDataGrid = new mx.datacontrols.DataGrid({
   		 alias: "cocontractmembershipRightDataGrid",
            columns:[
                {name: "participantid", caption: "市场成员id" , editorType: "TextEditor"},
                {name: "participantname", caption: "可选市场成员" , editorType: "TextEditor", width: "300"}
            ],
            displayCheckBox: true,
	        displayPrimaryKey:false,
            allowEditing: false,
            allowInterlacedRow: true,
            displayRowNumber: true,
	        allowPaging: false,
            entityContainer: gridEntityContainer
        });
    	me.vSplit.addControl(me.leftDataGrid, 0);
    }
    
    function _initCenterButton() {
    	var operationButtonContainer = new mx.containers.Container(
    		{width:"100%",height:"100%"}
    	);
    	operationButtonContainer.$e.css('padding-top', '150px');
    	me.vSplitRight.addControl(operationButtonContainer, 0);
    	
    	var rightButton = new mx.controls.Button(
        	{text:">>",onclick:me.controller._btnRight_onclick}
        );
        operationButtonContainer.addControl(rightButton);
        
        var leftButton = new mx.controls.Button(
        	{text:"<<", top: "100", onclick:me.controller._btnLeft_onclick}
        );
        
        leftButton.$e.css('margin-top', '10px');
        leftButton.$e.css('top', '0px');
        
        operationButtonContainer.addControl(leftButton);
    }
    
    function _initRightDataGrid() {
    	var restUrl = "~/rest/cocontractmembership/membership/";
    	var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
    		type: "local",
            loadMeta: false,
            iscID: "-1",
            primaryKey: "participantid"
        });
    	
    	me.rightDataGrid = new mx.datacontrols.DataGrid({
   		 alias: "cocontractmembershipRightDataGrid",
            columns:[
                {name: "participantid", caption: "市场成员id" , editorType: "TextEditor"},
                {name: "participantname", caption: "已选市场成员" , editorType: "TextEditor", width: "300"}
            ],
            displayCheckBox: true,
	        displayPrimaryKey:false,
            allowEditing: false,
            allowInterlacedRow: true,
            displayRowNumber: true,
	        allowPaging: false,
            entityContainer: gridEntityContainer
        });
    	me.vSplitRight.addControl(me.rightDataGrid, 1);
    }
    
    /**
     * 外部函数需要调用该方法，初始化界面数据
     * 
     */
    me.load = function() {
    	
//    	//界面初始化时，设置为空
//    	$.each(me.searchEditors, function(name, editor){
//    		if (editor.instanceOf(mx.editors.Editor)) {
//    			editor.setValue(null);
//    		}
//    	});
    	
    	//当用户选择的合同角色是输电方（3）时，将市场类型设置为发电企业，并不可编辑
    	var contractroletype = me.controller.contractroletype;
    	if (contractroletype == '3') {
    		me.searchEditors.participantType.setValue('1', "电网企业");
    		me.searchEditors.participantType.setReadOnly(true);
    	} else {
    		me.searchEditors.participantType.setValue(null);
    		me.searchEditors.participantType.setReadOnly(false);
    	}
    	
    	me.controller._load_left_datagrid();
    	me.controller._load_right_datagrid();
    }
    
    return me.endOfClass(arguments);
};