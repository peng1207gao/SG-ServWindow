$ns("jfcontractstate.views");

$import("mx.controls.ToolBar");
$import("mx.datacontainers.FormEntityContainer");
$import("mx.datacontrols.DataForm");


jfcontractstate.views.JfContractStateDetailView = function()
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
            alias:"jfcontractstateJfContractStateDetailViewToolBar",
            width: "100%"
        });
        var btnSave = _toolBar.appendItem("save", mx.msg("SAVE"));
        btnSave.alias = "jfcontractstateJfContractStateDetailViewBtnSave";
        btnSave.setImageKey("save");
        btnSave.on("click", me.controller._btnSave_onclick);
        me.addControl(_toolBar);
    }
	
    function _initDataForm()
    {
    	        var restUrl = "~/rest/sjjcjfhtlzxx/";
        /* 初始化 EntityContainer */        
        var formEntityContainer = new mx.datacontainers.FormEntityContainer({
            baseUrl : jfcontractstate.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "objId"
        });
        
        _form = new mx.datacontrols.DataForm({
			
			alias:"jfcontractstateJfContractStateDetailViewDataForm",
			displayPrimaryKey: false,
			fields: [
	        {	name: "objId", caption: "记录ID", editorType: "TextEditor", visible:false},
	        {	name: "companyname", caption: "运行单位名称", editorType: "TextEditor"},
	        {	name: "companyid", caption: "运行单位编码", editorType: "TextEditor"},
	        {	name: "contractname", caption: "合同名称", editorType: "TextEditor"},
	        {	name: "contractid", caption: "合同ID", editorType: "TextEditor"},
	        {	name: "contractno", caption: "合同编号", editorType: "TextEditor"},
	        {	name: "intodate", caption: "流转时间", editorType: "DateTimeEditor", formatString: "yyyy-MM-dd"},
	        {	name: "contractstate", caption: "合同流转信息,1起草2审核流转3审批4编号5签署6备案", editorType: "TextEditor"},
		    {	name: "contractsuggestion", caption: "合同审批意见", editorType: "TextEditor"}
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