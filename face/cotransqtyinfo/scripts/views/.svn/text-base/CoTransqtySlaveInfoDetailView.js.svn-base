$ns("cotransqtyinfo.views");

$import("mx.controls.ToolBar");
$import("mx.datacontainers.FormEntityContainer");
$import("mx.datacontrols.DataForm");


cotransqtyinfo.views.CoTransqtySlaveInfoDetailView = function()
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
            alias:"cotransqtyinfoCoTransqtySlaveInfoDetailViewToolBar",
            width: "100%"
        });
        var btnSave = _toolBar.appendItem("save", mx.msg("SAVE"));
        btnSave.alias = "cotransqtyinfoCoTransqtySlaveInfoDetailViewBtnSave";
        btnSave.setImageKey("save");
        btnSave.on("click", me.controller._btnSave_onclick);
        me.addControl(_toolBar);
    }
	
    function _initDataForm()
    {
    	        var restUrl = "~/rest/cotransqtyinfo/queryElec";
        /* 初始化 EntityContainer */        
        var formEntityContainer = new mx.datacontainers.FormEntityContainer({
            baseUrl : cotransqtyinfo.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "guid"
        });
        
        _form = new mx.datacontrols.DataForm({
			
			alias:"cotransqtyinfoCoTransqtySlaveInfoDetailViewDataForm",
			displayPrimaryKey: false,
			fields: [
	        {	name: "guid", caption: "GUID", editorType: "TextEditor", visible:false},
	        {	name: "transinfoid", caption: "输电信息ID", editorType: "TextEditor",visible:false},
	        {	name: "qtytype", caption: "峰谷类型", editorType: "DropDownEditor"},
	        {	name: "starttime", caption: "开始时间", editorType: "DropDownEditor"},
	        {	name: "endtime", caption: "结束时间", editorType: "DropDownEditor"},
	        {	name: "power", caption: "电力值", editorType: "TextEditor"}
		    ],
            entityContainer: formEntityContainer,
            onload:_initValue
        });
        
        me.addControl(_form);
    }

    function _initValue()
    {
    	_form.entityContainer.data.setValue("transinfoid",me.controller.objID[0]);//实际值
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