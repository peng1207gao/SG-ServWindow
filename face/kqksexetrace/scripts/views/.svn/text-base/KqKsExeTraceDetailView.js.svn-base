$ns("kqksexetrace.views");

$import("mx.controls.ToolBar");
$import("mx.datacontainers.FormEntityContainer");
$import("mx.datacontrols.DataForm");


kqksexetrace.views.KqKsExeTraceDetailView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    /**
     * 表单对象id
     */
    me.objID = null;
    /**
      * 表单对象
     */
    var _form = null;
    /**
     * 工具条
     */
    var _toolBar = null;
    

    /* 初始化单表单控件 */
    base.init = me.init;
    me.init = function()
    {
        base.init();
        _initControls();
    };

    me.load = function()
    {
    	//加载表单信息
    	_form.load(me.objID);
    }

    function _initControls()
    {
        _initToolBar();
	    _initDataForm();
        me.on("activate", me.controller._onactivate);
    }
	
    function _initToolBar()
    {
    	_toolBar = new mx.controls.ToolBar({
            alias:"kqksexetraceKqKsExeTraceDetailViewToolBar",
            width: "100%"
        });
        var btnSave = _toolBar.appendItem("save", mx.msg("SAVE"));
        btnSave.alias = "kqksexetraceKqKsExeTraceDetailViewBtnSave";
        btnSave.setImageKey("save");
        btnSave.on("click", me.controller._btnSave_onclick);
        me.addControl(_toolBar);
    }
	
    function _initDataForm()
    {
    	        var restUrl = "~/rest/cocontractbaseinfo/";
        /* 初始化 EntityContainer */        
        var formEntityContainer = new mx.datacontainers.FormEntityContainer({
            baseUrl : kqksexetrace.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "contractid"
        });
        
        _form = new mx.datacontrols.DataForm({
			
			alias:"kqksexetraceKqKsExeTraceDetailViewDataForm",
			displayPrimaryKey: false,
			fields: [
	        {	name: "contractid", caption: "合同ID", editorType: "TextEditor", visible:false},
	        {	name: "papercontractcode", caption: "纸质合同编号", editorType: "TextEditor"},
	        {	name: "papercontractname", caption: "纸质合同名称", editorType: "TextEditor"},
	        {	name: "contractqty", caption: "合同电力", editorType: "TextEditor"},
		    {	name: "energy", caption: "合同容量", editorType: "TextEditor"}
		    ],
            entityContainer: formEntityContainer
        });
        
        me.addControl(_form);
    }

    /**
     * 获取表单对象
     */
    me.getForm = function(){
		return _form;
    }
	
    /**
     * 获取工具条
     */
    me.getToolBar = function(){
		return _toolBar;
    }
    
	me.endOfClass(arguments)
    return me;
};