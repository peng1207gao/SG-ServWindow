$ns("coTempclienfieldconfig.views");

$import("mx.controls.ToolBar");
$import("mx.datacontainers.FormEntityContainer");
$import("mx.datacontrols.DataForm");


coTempclienfieldconfig.views.DetailView = function()
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
            alias:"coTempclienfieldconfigDetailViewToolBar",
            width: "100%"
        });
        var btnSave = _toolBar.appendItem("save", mx.msg("SAVE"));
        btnSave.alias = "coTempclienfieldconfigDetailViewBtnSave";
        btnSave.setImageKey("save");
        btnSave.on("click", me.controller._btnSave_onclick);
        me.addControl(_toolBar);
    }
	
    function _initDataForm()
    {
    	        var restUrl = "~/rest/cotempclienfieldconfig/";
        /* 初始化 EntityContainer */        
        var formEntityContainer = new mx.datacontainers.FormEntityContainer({
            baseUrl : coTempclienfieldconfig.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "sheetid"
        });
        
        _form = new mx.datacontrols.DataForm({
			
			alias:"coTempclienfieldconfigDetailViewDataForm",
			displayPrimaryKey: false,
			fields: [
	        {	name: "sheetid", caption: "??", editorType: "TextEditor", visible:false},
	        {	name: "canSheetid", caption: "??????SHEETID", editorType: "TextEditor"},
	        {	name: "configFieldName", caption: "???????", editorType: "TextEditor"},
	        {	name: "personid", caption: "???ID", editorType: "TextEditor"},
	        {	name: "marketid", caption: "?????", editorType: "TextEditor"},
	        {	name: "updatedate", caption: "????", editorType: "DateTimeEditor", formatString: "yyyy-MM-dd"},
		    {	name: "contracttemplatecode", caption: "??????", editorType: "TextEditor"}
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