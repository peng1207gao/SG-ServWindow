$ns("tempfieldconfig.views");

$import("mx.controls.ToolBar");
$import("mx.datacontainers.FormEntityContainer");
$import("mx.datacontrols.DataForm");


tempfieldconfig.views.TempfieldConfigDetailView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    /**
     * 表单对象id
     */
    me.objID = null;
    /**
     * 合同范本id
     */
    me.tempId = null;
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
            alias:"tempfieldconfigTempfieldConfigDetailViewToolBar",
            width: "100%"
        });
        var btnSave = _toolBar.appendItem("save", mx.msg("SAVE"));
        btnSave.alias = "tempfieldconfigTempfieldConfigDetailViewBtnSave";
        btnSave.setImageKey("save");
        btnSave.on("click", me.controller._btnSave_onclick);
        me.addControl(_toolBar);
    }
	
    function _initDataForm()
    {
    	        var restUrl = "~/rest/cotempclienfieldconfig_mk/";
        /* 初始化 EntityContainer */        
        var formEntityContainer = new mx.datacontainers.FormEntityContainer({
            baseUrl : tempfieldconfig.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "sheetid"
        });
        
        _form = new mx.datacontrols.DataForm({
			
			alias:"tempfieldconfigTempfieldConfigDetailViewDataForm",
			displayPrimaryKey: false,
			fields: [
	        {	name: "sheetid", caption: "主键", editorType: "TextEditor", visible:false},
	        {	name: "canSheetid", caption: "可配置字段", editorType: "DropDownEditor",
	        	displayMember: "text", valueMember: "value"
	        },
	        {	name: "configFieldName", caption: "对应模板字段", editorType: "TextEditor"},
//	        {	name: "personid", caption: "维护人ID", editorType: "TextEditor"},
//	        {	name: "marketid", caption: "维护人市场", editorType: "TextEditor"},
//	        {	name: "updatedate", caption: "维护时间", editorType: "DateTimeEditor", formatString: "yyyy-MM-dd"},
		    {	name: "contracttemplatecode", caption: "合同范本编号", editorType: "TextEditor",visible:false}
		    ],
            entityContainer: formEntityContainer,
            onload: _initValue
        });
        
        me.addControl(_form);
    }
    
    function _initValue(){
    	_form.setVisible("contracttemplatecode",false);
    	if(me.tempId!=null){//新增范本时范本id不为空，修改范本时范本id为空
    		_form.entityContainer.setValue("contracttemplatecode",me.tempId);
    	}
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