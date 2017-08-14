$ns("cocontractsequence.views");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGrid");
$import("mx.containers.HSplit");
$import("mx.containers.Panel");
$import("mx.controls.Button");
$import("mx.containers.Panel");
$import("mx.containers.Container");
$import("mx.editors.DateTimeEditor");
$import("mx.editors.DropDownTreeEditor");
$import("mx.editors.TextEditor");
$import("mx.editors.DropDownEditor");
//$import("utils.dropDownEditor.MarketTree.MarketDropDownEditor");


cocontractsequence.views.MainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    me.dataGrid = null;
    var HSplit1Area0=null;
    //表单窗口对象
    var _detailWin = null;
    
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
    	_initLayout();
    	_initSearchPanel();
    	_initOperationPanel();
    	_initDataGrid();
    	_initDetailWindow();
        me.on("activate", me.controller._onactivate);
    }
    
    /**
     * 视图布局，上下两部分：上面是查询和操作，下面是数据表格
     */
    function _initLayout() {
    	me.hSplit = new mx.containers.HSplit({
    		borderThick:"0px", rows:"170, auto"
    	});
    	
    	//业务查询区域
    	me.searchPanel = new mx.containers.Panel(
    		{width:"100%", height:"90", title:"业务查询"}
    	);
    	me.hSplit.addControl(me.searchPanel, 0);
    	
    	//业务操作区域
    	me.operationPanel = new mx.containers.Panel(
    		{width:"100%", height:"70", title:"业务操作"}
    	);
    	me.hSplit.addControl(me.operationPanel, 0);
    	
    	me.addControl(me.hSplit);
    }
    
    function _initSearchPanel() {
    	
    	//第一行查询条件容器
    	var searchButtonContainerOne = new mx.containers.Container(
    		{width:"100%",height:"40",padding:"2"}
    	);
    	me.searchPanel.addControl(searchButtonContainerOne);
    	
    	//第二行查询条件容器
    	var searchButtonContainerTwo = new mx.containers.Container(
    		{width:"100%",height:"40",padding:"2"}
    	);
    	me.searchPanel.addControl(searchButtonContainerTwo);
    	
    	
    	//
    	var htxlzqLabel = new mx.controls.Label(
    		{text:"合同序列周期:",width:"10%",textAlign:"right",right:4}
    	);
    	searchButtonContainerOne.addControl(htxlzqLabel);
    	me.searchEditors.sequencecircle = new mx.editors.DropDownEditor(
    		{width:"17%", allowEditing: false, displayMember: "text", valueMember: "value", 
    			url: cocontractsequence.mappath("~/rest/cocontractsequence/gencode/66")}
    	);
    	searchButtonContainerOne.addControl(me.searchEditors.sequencecircle);
    	
    	var htxllxLabel = new mx.controls.Label(
    		{text:"合同序列类型:",width:"10%",textAlign:"right",right:4}
    	);
    	searchButtonContainerOne.addControl(htxllxLabel);
    	me.searchEditors.sequencetype = new mx.editors.DropDownEditor(
    		{width:"17%", allowEditing: false, displayMember: "text", valueMember: "value", 
    			url: cocontractsequence.mappath("~/rest/cocontractsequence/gencode/65")}
    	);
    	searchButtonContainerOne.addControl(me.searchEditors.sequencetype);
    	
    	var ssywcjLabel = new mx.controls.Label(
    		{text:"所属业务场景:",width:"10%",textAlign:"right",right:4}
    	);
    	searchButtonContainerOne.addControl(ssywcjLabel);
    	me.searchEditors.marketid = new 
    	utils.dropDownEditor.MarketTree.MarketDropDownEditor(true,null,false); //业务场景下拉框
    	me.searchEditors.marketid.setWidth("17%");
    	searchButtonContainerOne.addControl(me.searchEditors.marketid);
    	
    	
    	//
    	var cjkssjLabel = new mx.controls.Label(
    		{text:"创建开始时间:",width:"10%",textAlign:"right",right:4}
    	);
    	searchButtonContainerTwo.addControl(cjkssjLabel);
    	me.searchEditors.createdatestart = new mx.editors.DateTimeEditor(
    		{width:"17%", formatString:"yyyy-MM-dd"}
    	);
    	searchButtonContainerTwo.addControl(me.searchEditors.createdatestart);
    	
    	var cjjssjLabel = new mx.controls.Label(
    		{text:"创建结束时间:",width:"10%",textAlign:"right",right:4}
    	);
    	searchButtonContainerTwo.addControl(cjjssjLabel);
    	me.searchEditors.createdateend = new mx.editors.DateTimeEditor(
    		{width:"17%", formatString:"yyyy-MM-dd"}
    	);
    	searchButtonContainerTwo.addControl(me.searchEditors.createdateend);
    	
    	var htxlmcLabel = new mx.controls.Label(
    		{text:"合同序列名称:",width:"10%",textAlign:"right",right:4}
    	);
    	searchButtonContainerTwo.addControl(htxlmcLabel);
    	me.searchEditors.sequencename = new mx.editors.TextEditor(
    		{width:"17%"}
    	);
    	searchButtonContainerTwo.addControl(me.searchEditors.sequencename);
    }
    
    function _initOperationPanel() {
    	var operationButtonContainer = new mx.containers.Container(
    		{width:"100%",height:"40",padding:"2"}
    	);
    	me.operationPanel.addControl(operationButtonContainer);
    	
    	var searchButton = new mx.controls.Button(
        	{text:"查询",left:20,onclick:me.controller._btnSearch_onclick}
        );
        operationButtonContainer.addControl(searchButton);
        	
    	var newButton = new mx.controls.Button(
    		{text:"新增",left:60,onclick:me.controller._btnNew_onclick}
    	);
    	operationButtonContainer.addControl(newButton);
    	
    	var editButton = new mx.controls.Button(
        	{text:"编辑",left:100,onclick:me.controller._btnEdit_onclick}
        );
        operationButtonContainer.addControl(editButton);
    	
    	var delButton = new mx.controls.Button(
    		{text:"删除",left:140,onclick:me.controller._btnDelete_onclick}
    	);
    	operationButtonContainer.addControl(delButton);
    }
    
    function _initDataGrid() {
    	var restUrl = "~/rest/cocontractsequence/";
    	var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : cocontractsequence.mappath(restUrl),
            loadMeta: false,
            iscID : "-1",
            primaryKey: "sequenceid"
        });
    	 me.dataGrid = new mx.datacontrols.DataGrid({
    		 alias: "cocontractsequenceMainViewDataGrid",
             columns:[
                 {name: "sequenceid", caption: "合同序列id" , editorType: "TextEditor",align:"center",dataAlign:"center"},
                 {name: "sequencename", caption: "合同序列名称" , editorType: "TextEditor",align:"center",dataAlign:"center"},
                 {name: "sequencecircleStr", caption: "合同序列周期" , editorType: "TextEditor",align:"center",dataAlign:"center",width:"120"},
                 {name: "contractType", caption: "合同类型" , editorType: "TextEditor",align:"center",dataAlign:"center"},
                 {name: "sequencetypeStr", caption: "合同序列类型" , editorType: "TextEditor",align:"center",dataAlign:"center",width:"120"},
                 {name: "createdate", caption: "合同序列创建时间" , editorType: "DateTimeEditor",formatString: "yyyy-MM-dd HH:mm:ss",align:"center",dataAlign:"center",width:"160"},
                 {name: "updatepersonid", caption: "维护人" , editorType: "TextEditor",align:"center",dataAlign:"center",width:"90"},
                 {name: "updatetime", caption: "维护时间" , editorType: "DateTimeEditor",formatString: "yyyy-MM-dd HH:mm:ss",align:"center",dataAlign:"center",width:"160"}
                 
             ],
             displayCheckBox: true,
 	         displayPrimaryKey:false,
             allowEditing: false,
             allowInterlacedRow: true,
             displayRowNumber: true,
 	         pageSize : 10,
             entityContainer: gridEntityContainer
         });
    	 HSplit1Area0 =  new mx.containers.Container({ 
 			id:"HSplit1Area0",
 			width:"100%"
// 			height："100%"
 		});
    	 HSplit1Area0.addControl(me.dataGrid);
         me.hSplit.addControl(HSplit1Area0, 1);
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
    
    return me.endOfClass(arguments);
};