$ns("CoContractparamdic.views");

$import("mx.controls.ToolBar");
$import("mx.datacontainers.FormEntityContainer");
$import("mx.datacontrols.DataForm");
$import("mx.rpc.RESTClient");


CoContractparamdic.views.DetailView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    var restClient = new mx.rpc.RESTClient();

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
    	//页面上面分成上下两部分
    	me.mSplit = new mx.containers.HSplit({rows:"55px,auto",borderThick:1});
    	me.addControl(me.mSplit);
    	me.panel = new mx.containers.Panel({title:"业务操作",width:"100%"});
    	me.mSplit.addControl(me.panel,0);//添加panel
    	me.container = new mx.containers.Container({padding:"2px",width:"100%"});
    	me.panel.addControl(me.container);//添加container
    	
    	//保存按钮
    	var btn_save = new mx.controls.Button({text : "保存",left : 20});
    	me.container.addControl(btn_save);
    	btn_save.on("click",function() {
    		var dicName = me.getForm().entityContainer.data.dicName;
    		var dicValue = me.getForm().entityContainer.data.dicValue;
    		var orderNo = me.getForm().entityContainer.data.orderNo;
    		if(dicName == "" || dicName == null) {
    			mx.indicate("info", "参数字典名称不能为空");
    			return;
    		}else if(dicValue == "" || dicValue == null) {
    			mx.indicate("info", "参数字典值不能为空");
    			return;
    		}else if(orderNo == "" || orderNo == null) {
    			mx.indicate("info", "排序号不能为空");
    			return;
    		}else {
    			btn_save.on("click", me.controller._btnSave_onclick);
    		}
    	});
    	me.container.addControl(btn_save);
    	
    	//关闭按钮
    	var btn_close = new mx.controls.Button({text : "关闭",left : 40});
    	me.container.addControl(btn_close);
    	//关闭按钮点击事件
    	btn_close.on("click", function() {
    		me.controller.co.getDetailWindow().hide();//关闭当前页面
    	}); 
    	
    	me.container.addControl(btn_close);
        me.addControl(_toolBar);
    }
	
    function _initDataForm()
    {
    	        var restUrl = "~/rest/cocontractparamdic/";
        /* 初始化 EntityContainer */        
        var formEntityContainer = new mx.datacontainers.FormEntityContainer({
            baseUrl : CoContractparamdic.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "sheetid"
        });
        
        _form = new mx.datacontrols.DataForm({
			
			alias:"CoContractparamdicDetailViewDataForm",
			displayPrimaryKey: false,
			fields: [
	        {	name: "sheetid", caption: "主键", editorType: "TextEditor", visible:false},
	        {	name: "dicName", caption: "参数字典名称", editorType: "TextEditor"},
	        {	name: "orderNo", caption: "排序号", editorType: "TextEditor"},
	        {	name: "dicValue", caption: "参数字典值", editorType: "TextEditor", textMode:"multiline"},
	        {	name: "dicContent", caption: "说明", editorType: "TextEditor", textMode:"multiline"}
//	        {	name: "operateUser", caption: "维护人", editorType: "TextEditor"},
//		    {	name: "operateDate", caption: "维护时间", editorType: "DateTimeEditor", formatString: "yyyy-MM-dd"}
		    ],
            entityContainer: formEntityContainer,
            onload : a
        });
        
        me.addControl(_form);
        me.mSplit.addControl(_form,1);//添加_form到mSplit
    }

    function a () {
    	if(me.getForm().entityContainer.data.orderNo == null) {
    		var result = restClient.getSync(CoContractparamdic.mappath("~/rest/cocontractparamdic/getNextOrderNo"));
    		me.getForm().entityContainer.setValue("orderNo", result.resultValue.columns[0]);
    		me.getForm().editors.orderNo.setValue(result.resultValue.columns[0]);
    	}
    	me.getForm().editors.dicValue.setMaxLength(500);
    	me.getForm().editors.dicContent.setMaxLength(500);
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