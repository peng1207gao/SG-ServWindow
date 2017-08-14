$ns("coContractbackupInfo.views");

$import("mx.controls.ToolBar");
$import("mx.datacontainers.FormEntityContainer");
$import("mx.datacontrols.DataForm");


coContractbackupInfo.views.DetailView = function()
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
            alias:"coContractbackupInfoDetailViewToolBar",
            width: "100%"
        });
    	
    		//保存按钮
    		var btn_save = new mx.controls.Button({text : "保存",left : 20});
    		_toolBar.appendItem(btn_save);
    		btn_save.alias = "coContractbackupInfoDetailViewBtnSave";
    		btn_save.on("click", function() {
    			var transValue = me.getForm().entityContainer.data.transmission;
    			if(transValue != "" && transValue != null){
    				btn_save.on("click", me.controller._btnSave_onclick);
    			}else{
    				alert("输电方不能为空");
    			}
    		});
    		//关闭按钮
    		var btn_close = new mx.controls.Button({text : "关闭",left : 20});
    		btn_close.on("click", function() {
    			me.viewPort.close();//关闭当前页面
    		});
    		_toolBar.appendItem(btn_close);
    	

        me.addControl(_toolBar);
    }
	
    function _initDataForm()
    {
    	        var restUrl = "~/rest/cocontractbackupinfo/";
        /* 初始化 EntityContainer */        
        var formEntityContainer = new mx.datacontainers.FormEntityContainer({
            baseUrl : coContractbackupInfo.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "guid"
        });
        
        _form = new mx.datacontrols.DataForm({
			
			alias:"coContractbackupInfoDetailViewDataForm",
			displayPrimaryKey: false,
			fields: [
	        {	name: "contractid", caption: "合同名称", editorType: "TextEditor", readOnly: true },
	        {	name: "transmission", caption: "输电方", editorType: "DropDownEditor"},
		    {	name: "guid", caption: "GUID", editorType: "TextEditor", visible:false}
		    ],
            entityContainer: formEntityContainer,
            onload:initHt
        });
        
        me.addControl(_form);
    }
    
    function initHt(){
    	me.getForm().entityContainer.setValue("contractid",me.contractId);
    	me.getForm().editors.contractid.setValue(me.contractName);
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