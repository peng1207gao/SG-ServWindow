$ns("CoContranslossinfo.views");

$import("mx.controls.ToolBar");
$import("mx.datacontainers.FormEntityContainer");
$import("mx.datacontrols.DataForm");


CoContranslossinfo.views.DetailView = function()
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

    /**
     * 根据表单设置的ID主键，从服务端加载表单信息
     */
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
            alias:"CoContranslossinfoDetailViewToolBar",
            width: "100%"
        });
        var btnSave = _toolBar.appendItem("save", mx.msg("SAVE"));
        btnSave.alias = "CoContranslossinfoDetailViewBtnSave";
        btnSave.setImageKey("save");
        btnSave.on("click", me.controller._btnSave_onclick);
        me.addControl(_toolBar);
    }
	
    function _initDataForm()
    {
    	var restUrl = "~/rest/cocontranslossinfo/";
        /* 初始化 EntityContainer */        
        var formEntityContainer = new mx.datacontainers.FormEntityContainer({
            baseUrl : CoContranslossinfo.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "guid"
        });
        
        _form = new mx.datacontrols.DataForm({
			alias:"CoContranslossinfoDetailViewDataForm",
			displayPrimaryKey: false,
			maxCols:2,
			fields: [
			    [
			        "线损详细信息：",
			        {	name: "guid", caption: "GUID", editorType: "TextEditor", visible: false, lineBreak:false},
			        {	name: "contractid", caption: "合同ID", editorType: "TextEditor", lineBreak:false},
			        {	name: "translineno", caption: "输电线路序号", editorType: "TextEditor", visible: false, lineBreak:false},
			        
			        {	name: "contractname", caption: "合同名称", editorType: "TextEditor", lineBreak:false},
			        {	name: "transmission", caption: "输电方", editorType: "DropDownEditor", lineBreak:false, dropDownMaxHeight: 300},
			        {	name: "linkid", caption: "联络线", editorType: "DropDownEditor", lineBreak:false, dropDownMaxHeight: 300,   
			        	onchanged: me.controller._dataform_linkidDropDownEditor_onchanged},
			        {	name: "linestartendgate", caption: "线路起止信息", editorType: "TextEditor", lineBreak:false},
			        {	name: "loss", caption: "输电网损(%)", editorType: "TextEditor", lineBreak:false},
			        {	name: "direction", caption: "方向", editorType: "DropDownEditor", lineBreak:false}
			    ]
		    ],
            entityContainer: formEntityContainer,
            onload: me.controller._dataform_onload,
            onvalidate: me.controller._dataform_onvalidate
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