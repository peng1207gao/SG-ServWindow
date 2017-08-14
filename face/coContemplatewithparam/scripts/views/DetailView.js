$ns("coContemplatewithparam.views");

$import("mx.controls.ToolBar");
$import("mx.datacontainers.FormEntityContainer");
$import("mx.datacontrols.DataForm");


coContemplatewithparam.views.DetailView = function()
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
            alias:"coContemplatewithparamDetailViewToolBar",
            width: "100%"
        });
        var btnSave = _toolBar.appendItem("save", mx.msg("SAVE"));
        btnSave.alias = "coContemplatewithparamDetailViewBtnSave";
        btnSave.setImageKey("save");
        btnSave.on("click", me.controller._btnSave_onclick);
        me.addControl(_toolBar);
    }
	
    function _initDataForm()
    {
    	        var restUrl = "~/rest/cocontemplatewithparam/";
        /* 初始化 EntityContainer */        
        var formEntityContainer = new mx.datacontainers.FormEntityContainer({
            baseUrl : coContemplatewithparam.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "contracttemplatecode"
        });
        
        _form = new mx.datacontrols.DataForm({
			
			alias:"coContemplatewithparamDetailViewDataForm",
			displayPrimaryKey: false,
			fields: [
	        {	name: "marketid", caption: "市场ID", editorType: "TextEditor"},
	        {	name: "contracttype", caption: "合同类型", editorType: "TextEditor"},
	        {	name: "contracttemplatecode", caption: "合同范本文号", editorType: "TextEditor", visible:false},
	        {	name: "contracttemplatename", caption: "合同范本名称", editorType: "TextEditor"},
	        {	name: "contracttemplatefile", caption: "合同范本文件", editorType: "TextEditor"},
	        {	name: "version", caption: "版本号", editorType: "TextEditor"},
	        {	name: "issueddate", caption: "下发时间", editorType: "DateTimeEditor", formatString: "yyyy-MM-dd"},
	        {	name: "starteffectivedate", caption: "生效日期", editorType: "DateTimeEditor", formatString: "yyyy-MM-dd"},
	        {	name: "endeffectivedate", caption: "失效日期", editorType: "DateTimeEditor", formatString: "yyyy-MM-dd"},
	        {	name: "effectiveflag", caption: "失效标志", editorType: "TextEditor"},
	        {	name: "isshare", caption: "是否共享", editorType: "TextEditor"},
		    {	name: "descreption", caption: "描述", editorType: "TextEditor"}
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