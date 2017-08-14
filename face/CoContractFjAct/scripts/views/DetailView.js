$ns("CoContractFjAct.views");

$import("mx.controls.ToolBar");
$import("mx.datacontainers.FormEntityContainer");
$import("mx.datacontrols.DataForm");

CoContractFjAct.views.DetailView = function()
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
    	
        _initDataForm();
        //_initToolBar();
        _initButton();
        me.on("activate", me.controller._onactivate);
    }
	
//    function _initToolBar()
//    {
//    	_toolBar = new mx.controls.ToolBar({
//            alias:"CoContractFjActDetailViewToolBar",
//            width: "100%"
//        });
//        var btnSave = _toolBar.appendItem("save", mx.msg("SAVE"));
//        btnSave.alias = "CoContractFjActDetailViewBtnSave";
//        btnSave.setImageKey("save");
//        btnSave.on("click", me.controller._btnSave_onclick);
//        me.addControl(_toolBar);
//    }
    
    function _initButton(){
    	
    	me.container2 = new mx.containers.Container({width:"100%",height:"30",padding:"2"});	//文件不能大于10M
    	me.addControl(me.container2);	//添加container2容器
    	me.label13 = new mx.controls.Label({text:"（注：文件不能大于10M!）",width:"200",textAlign:"right",right:4,left:80});	//合同序列标签
    	me.container2.addControl(me.label13);	//添加标签
    	me.addControl(me.container2);
    	
    	me.container = new mx.containers.Container({width:"100%",height:"30",padding:"2",top:20});	//放置保存、关闭等的容器
    	me.addControl(me.container);	//添加container1容器
    	me.button = new mx.controls.Button({text:"保存",left:140,onclick: me.controller._btnSave_onclick});	//保存按钮
    	me.container.addControl(me.button);	//添加按钮
    	me.button2 = new mx.controls.Button({text:"关闭",left:170,onclick: me.controller._btnClose_onclick});	//关闭按钮
    	me.container.addControl(me.button2);	//添加按钮
    	me.addControl(me.container);
    }
	
    function _initDataForm()
    {
    	        var restUrl = "~/rest/cocontractaffixinfo/";
        /* 初始化 EntityContainer */        
        var formEntityContainer = new mx.datacontainers.FormEntityContainer({
            baseUrl : CoContractFjAct.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "guid"
        });
        
        _form = new mx.datacontrols.DataForm({
			
			alias:"CoContractFjActDetailViewDataForm",
			displayPrimaryKey: false,
			fields: [
	       // {	name: "marketid", caption: "市场ID", editorType: "TextEditor"},
	       //{	name: "contractid", caption: "合同ID", editorType: "TextEditor"},
	        //{	name: "affixno", caption: "附件编号", editorType: "TextEditor"},
	        {	name: "affixname", caption: "附件名称", editorType: "TextEditor"},
	        {	name: "affixtype", caption: "附件类型", editorType: "DropDownEditor"},
	        {	name: "description", caption: "说明", editorType: "TextEditor"},
	        {	name: "papercontractfile", caption: "文件", editorType: "FileEditor",type: "form",uploadMode: "blob", tableName: "CO_ContractAffixInfo",primaryKey: "GUID",filesLimit:"1",limitTypes:"exe,dll",colName: "PAPERCONTRACTFILE"},
	       // {	name: "uploadperson", caption: "上传人", editorType: "TextEditor"},
	       // {	name: "uploadtime", caption: "上传时间", editorType: "DateTimeEditor", formatString: "yyyy-MM-dd"},
	       
	        {	name: "guid", caption: "GUID", editorType: "TextEditor", visible:false}
		   
		    ],
            entityContainer: formEntityContainer,
            onload:_getContractId
        });
        
        me.addControl(_form);
    }
    
    function _getContractId(){
		me.getForm().entityContainer.setValue("contractid",me.objID);
		//me.getForm().editors.contractid.setText(me.objID);
		//me.getForm().editors.linkid.on("changed",_init1);
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