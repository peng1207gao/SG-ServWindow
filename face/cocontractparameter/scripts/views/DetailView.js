$ns("cocontractparameter.views");

$import("mx.controls.ToolBar");
$import("mx.datacontainers.FormEntityContainer");
$import("mx.datacontrols.DataForm");


cocontractparameter.views.DetailView = function()
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
    	//页面上面分成上下两部分
    	me.mSplit = new mx.containers.HSplit({rows:"55px,auto",borderThick:1});
    	me.addControl(me.mSplit);
    	me.panel = new mx.containers.Panel({title:"业务操作",width:"100%"});
    	me.mSplit.addControl(me.panel,0);//添加panel
    	me.container = new mx.containers.Container({padding:"2px",width:"100%"});
    	me.panel.addControl(me.container);//添加container
    	
    	//保存按钮
    	var btn_save = new mx.controls.Button({text : "保存参数",left : 20});
    	me.container.addControl(btn_save);
    	btn_save.on("click",function() {
    		var transValue = me.getForm().entityContainer.data.tempparamname;
    		if(transValue != "" && transValue != null){
    			btn_save.on("click", me.controller._btnSave_onclick);
    		}else{
    			mx.indicate("info", "参数名称不能为空");
    			return;
    		}
    	});
    	me.container.addControl(btn_save);
    	
    	//关闭按钮
    	var btn_close = new mx.controls.Button({text : "关闭",left : 40});
    	me.container.addControl(btn_close);
    	//关闭按钮点击事件
    	btn_close.on("click", function() {
    		me.controller.co.getDetailWindow().hide()//关闭当前页面
    	}); 
    	me.container.addControl(btn_close);
        me.addControl(_toolBar);
    }
	
    function _initDataForm()
    {
    	var restUrl = "~/rest/cocontractparameter/";
        /* 初始化 EntityContainer */        
        var formEntityContainer = new mx.datacontainers.FormEntityContainer({
            baseUrl : cocontractparameter.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "tempparamid"
        });
        
        _form = new mx.datacontrols.DataForm({
			
			alias:"cocontractparameterDetailViewDataForm",
			displayPrimaryKey: false,
			fields: [
	        {	name: "tempparamid", caption: "参数ID", editorType: "TextEditor", visible:false},
	        {	name: "tempparamname", caption: "参数名称", editorType: "TextEditor", align:"center"},
	        {	name: "tempparamdesc", caption: "备注" , editorType: "TextEditor", textMode:"multiline", align:"center"}
		    ],
            entityContainer: formEntityContainer
        });
        
        me.addControl(_form);
        me.mSplit.addControl(_form,1);//添加_form到mSplit
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