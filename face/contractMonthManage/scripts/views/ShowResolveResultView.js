$ns("contractMonthManage.views");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGridSearchBox");
$import("mx.datacontrols.DataGrid");
$import("mx.containers.Panel");

contractMonthManage.views.ShowResolveResultView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    me.restClient = new mx.rpc.RESTClient();
    
    //网格列表
    var _dataGrid = null;
    
    me.contractid = null;
    
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
	    _initDataGrid();
        me.on("activate", me.controller._onactivate);
    }
    
    function _initDataGrid()
    {	
        var restUrl = "~/rest/cocontractmonthqty/getResolveResult";
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
			{ name: "contractname",caption: "合同名称", editorType: "TextEditor", width:150,align:"center",dataAlign:"center"},
			{ name: "purchaserunitname",caption: "购方经济机组", editorType: "TextEditor", width:150,align:"center",dataAlign:"center"},
			{ name: "sellerunitname",caption: "售方经济机组", editorType: "TextEditor", width:150,align:"center",dataAlign:"center"},
			{ name: "janqty",caption: "一月", editorType: "TextEditor", width:80,align:"center",dataAlign:"center"},
			{ name: "febqty",caption: "二月", editorType: "TextEditor", width:80,align:"center",dataAlign:"center"},
			{ name: "marqty",caption: "三月", editorType: "TextEditor", width:80,align:"center",dataAlign:"center"},
			{ name: "aprilqty",caption: "四月", editorType: "TextEditor", width:80,align:"center",dataAlign:"center"},
			{ name: "mayqty",caption: "五月", editorType: "TextEditor", width:80,align:"center",dataAlign:"center"},
			{ name: "junqty",caption: "六月", editorType: "TextEditor", width:80,align:"center",dataAlign:"center"},
			{ name: "julyqty",caption: "七月", editorType: "TextEditor", width:80,align:"center",dataAlign:"center"},
			{ name: "augqty",caption: "八月", editorType: "TextEditor", width:80,align:"center",dataAlign:"center"},
			{ name: "sepqty",caption: "九月", editorType: "TextEditor", width:80,align:"center",dataAlign:"center"},
			{ name: "octqty",caption: "十月", editorType: "TextEditor", width:80,align:"center",dataAlign:"center"},
			{ name: "novqty",caption: "十一月", editorType: "TextEditor", width:80,align:"center",dataAlign:"center"},
			{ name: "decqty",caption: "十二月", editorType: "TextEditor", width:80,align:"center",dataAlign:"center"}
            ],
            displayCheckBox: false,
	        displayPrimaryKey:false,//列表是否显示主键
            allowEditing: false, //列表默认不可编辑
            displayRowNumber :true,
            onload:setVisible,
            entityContainer: gridEntityContainer
        });
        me.addControl(_dataGrid);
    }
    
    function setVisible(){
    	_dataGrid.columns["contractid"].setVisible(false);
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