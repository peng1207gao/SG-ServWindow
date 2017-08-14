$ns("resolveelectricquantitytounit.views");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGridSearchBox");
$import("mx.datacontrols.DataGrid");
$import("mx.containers.Panel");

$import("mx.utils.JsonUtilClass");


resolveelectricquantitytounit.views.ChooseEconUnitView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    me.restClient = new mx.rpc.RESTClient();
    
    //网格列表
    var _dataGrid = null;
    //表单窗口对象
    var _detailWin = null;
    
    me.contractids = null;
    
    me.returnstatus = null;//返回状态，标识点击哪个按钮关闭的窗口
    
    me.selectionItems = null;//返回选中记录
    
    base.init = me.init;
    me.init = function()
    {
        base.init();
        _initControls();
    };
    
  	me.load = function()
    {
    	//加载第一个表单信息
//    	loadDataGrid();
    }
    
    function loadDataGrid(){
    	
    }
    
    function _initControls()
    {
    	_initHSplit();
    	_initPanel();
    	_initQueryEditor();
    	_initButton();
	    _initDataGrid();
        me.on("activate", me.controller._onactivate);
    }
    
    function _initHSplit(){
   		me.mainSplit = new mx.containers.HSplit({
			rows : "110,auto",width:"100%",
			borderThick : 1
		});
		me.addControl(me.mainSplit);
    }
    
    function _initPanel(){
    	me.queryPanel = new mx.containers.Panel({ name:"queryPanel", title:"业务查询", width:"100%", height: 50}); 
    	me.panel = new mx.containers.Panel({ name:"panel", title:"业务操作", width:"100%", height: 50});
    	me.mainSplit.addControl(me.queryPanel, 0);
    	me.mainSplit.addControl(me.panel, 0);
    }
    
    function _initQueryEditor(){
    	me.unitLabel = new mx.controls.Label({
			text : "物理机组：",
			textAlign : "center",
			verticalAlign : "middle"
		});
		
		me.unitDropDownEditor = new mx.editors.DropDownEditor({displayMember:"name",valueMember:"value",autoMatchInput:true,
    		allowEditing:true,width:"15%"}); //物理机组
    		
 		me.econUnitLabel = new mx.controls.Label({
			text : "经济机组：",
			textAlign : "center",
			verticalAlign : "middle"
		});
		
		me.econUnitDropDownEditor = new mx.editors.DropDownEditor({displayMember:"name",valueMember:"value",autoMatchInput:true,
    		allowEditing:true,width:"15%"}); //经济机组
		
		me.queryPanel.addControl(me.unitLabel); 
		me.queryPanel.addControl(me.unitDropDownEditor);
		me.queryPanel.addControl(me.econUnitLabel); 
		me.queryPanel.addControl(me.econUnitDropDownEditor);
    }
    
    me._initUnitItem = function(contractids){
    	var items = me.restClient.getSync(
    		resolveelectricquantitytounit.mappath("~/rest/resolveelectricquantitytounit/getUnitList?isUnit=true"), 
    		{ "params": contractids});//物理机组
  		me.unitDropDownEditor.setItems(items.resultValue); 
  		
  		var ecoitems = me.restClient.getSync(
    		resolveelectricquantitytounit.mappath("~/rest/resolveelectricquantitytounit/getUnitList?isUnit=false"), 
    		{ "params": contractids});//经济机组
  		me.econUnitDropDownEditor.setItems(ecoitems.resultValue); 
  		
    }
    
    function _initButton(){
		var _btnQuery = new mx.controls.Button({ text: "查询",left:50,top:1 });
		var _btnConfirm = new mx.controls.Button({ text: "确定",left:70,top:1 });
		var _btnClose = new mx.controls.Button({ text: "关闭",left:90,top:1 });
		_btnQuery.on("click", me.selectUnit);
		_btnConfirm.on("click", me.resolveContractAccessory);
		_btnClose.on("click", me.closeDialog);
		me.panel.addControl(_btnQuery);
		me.panel.addControl(_btnConfirm);
		me.panel.addControl(_btnClose);
    }
    
    function _initDataGrid()
    {	
        var restUrl = "~/rest/resolveelectricquantitytounit/getUnitGrid";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : resolveelectricquantitytounit.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            loadMeta:false,
            primaryKey: "guid"
        });
        /* 初始化 DataGrid */
        _dataGrid = new mx.datacontrols.DataGrid({   
			// 构造查询属性。
			alias: "unit",
			columns:[
			{ name: "contractid",caption: "contractid", editorType: "TextEditor", width:80,align:"center",dataAlign:"center"},
			{ name: "contractrole",caption: "合同角色", editorType: "TextEditor", width:80,align:"center",dataAlign:"center",
			renderCell:showName}, 
	        { name: "unit", caption: "物理机组" , editorType: "TextEditor",width:200,align:"center",dataAlign:"center"},
	        { name: "ecounit", caption: "经济机组" , editorType: "TextEditor",width:200,align:"center",dataAlign:"center"}
            ],
            displayCheckBox: true,
	        displayPrimaryKey:false,//列表是否显示主键
            allowEditing: false, //列表默认不可编辑
            displayRowNumber :true,
            onload:setVisible,
            entityContainer: gridEntityContainer
        });
        me.mainSplit.addControl(_dataGrid,1);
    }
    
    function setVisible(){
    	_dataGrid.columns["contractid"].setVisible(false);
    }
    
    function showName(p_item,$p_cell){
    	if(p_item.values["contractrole"]=="1"){
    		$p_cell.html("购电方");
    	} else if(p_item.values["contractrole"]=="2"){
    		$p_cell.html("售电方");
    	}
    }
    
    me.selectUnit = function(){
		var data = {
			"unit":me.unitDropDownEditor.value, 
			"ecounit":me.econUnitDropDownEditor.value, 
			"contractids":me.contractids.substring(15,me.contractids.length - 1)};
			
		_dataGrid.setFilter(data);
		_dataGrid.load();
    }
    
    me.resolveContractAccessory = function(){
    	me.returnstatus = "confirm";//点击【确定】按钮返回状态标识
    	if (_dataGrid.getCheckedItems().length == 0)
        {
	     	mx.indicate("info", "请至少勾选一条记录。");
			return;
        }
    	me.parent.hide();
    }
    
    /**
     * 获取DataGrid网格列表对象
     */
    me.getDataGrid = function(){
    	return _dataGrid;
    }
    
    me.closeDialog = function(){
    	me.returnstatus = "close";//点击【关闭】按钮返回状态标识
    	me.parent.hide();
    }
    
	me.endOfClass(arguments)
    return me;
};