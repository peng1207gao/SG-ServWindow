$ns("cocontractaccessory.views");

$import("mx.controls.ToolBar");
$import("mx.datacontrols.DataGrid");
$import("mx.containers.HSplit");
$import("mx.editors.DropDownEditor");
$import("mx.controls.Label");

cocontractaccessory.views.AddListView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    me.dataGrid = null;
    
    /**
     * 打开该视图的外部视图的controller
     */
    me.outerController = null;
    
    /**
     * 数据重复提交标识，0：数据没提交；1：数据已经提交
     * 
     * 在AddListViewController._saveSelectedGenerators函数中判断与控制
     */
    me.submitflag = 0;
    
    me.contractid = null;

    base.init = me.init;
    me.init = function()
    {
        base.init();

        _initControls();
    };
    
    function _initControls()
    {
    	_initLayout();
    	
    	_initDropDownEditor();
    	
    	_initToolbar();
    	
    	_initDataGrid();
	
        me.on("activate", me.controller._onactivate);
    }
    
    me.load = function() {
    }
    
    function _initLayout() {
    	me.hSplit = new mx.containers.HSplit({
    		borderThick:"0px",
    		rows:"10%, 80%"
    	});
    	me.addControl(me.hSplit);
    }
    
    function _initDropDownEditor() {
    	
    	var label = new mx.controls.Label({
    		text: "合同角色：",
    	    textAlign: "center",
    	    verticalAlign: "middle"
    	});
    	me.hSplit.addControl(label, 0);

    	
    	me.dropDownEditor = new mx.editors.DropDownEditor({
			allowEditing: false,  // 设置是否允许编辑
		    displayCheckBox: false,
		    displayMember: "name",
		    valueMember: "value"
//		    items: [
//		        { name: "购电方", value: "1" },
//		        { name: "售电方", value: "2" }
//		    ]
    	});
    	me.hSplit.addControl(me.dropDownEditor, 0);
    }
    
    me._initDropDownEditor = function(){
    	me.restClient = new mx.rpc.RESTClient();
     	var s = me.restClient.getSync(cocontractaccessory.mappath("~/rest/cocontractaccessory/getDropDownList"),
     		{ "params": JSON.stringify({"contractid":me.contractid})});
    	me.dropDownEditor.setItems(s.resultValue); 
    }
    
    function _initToolbar() {
    	me.toolbar = new mx.controls.ToolBar({
    		width: "100%",
 	    	items:[
 				{name: "query", text: "查询", toolTip: "查询", imageKey : "add", 
 						onclick: me.controller._btnLoadDataGrid_onclick
 				},
 				"-",
 				{name: "ok", text: "确认", toolTip: "确认", imageKey : "save", 
 						onclick: me.controller._btnSaveSelectedGen_onclick
 				}
 	    	]
    	});
    	me.hSplit.addControl(me.toolbar, 0);
    }
    
    function _initDataGrid() {
        me.dataGrid = new mx.datacontrols.DataGrid({      
            displayCheckBox: true,
            allowEditing: false,
            baseUrl : cocontractaccessory.mappath("~/rest/cocontractaccessory/generator/"),
            primaryKey : "generatorid",
            columns:[
                {name: "generatorid", caption: "机组Id" , editorType: "TextEditor"},
                {name: "generatorname", caption: "机组名称" , editorType: "TextEditor", width:200}
            ],
            allowInterlacedRow: true,
            displayRowNumber: true,
            allowPaging: false
        });
  
        me.hSplit.addControl(me.dataGrid, 1);
    }
    
    return me.endOfClass(arguments);
};