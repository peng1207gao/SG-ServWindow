$ns("CoContransEnergyInfoV2.views");

$import("mx.controls.ToolBar");
$import("mx.datacontainers.FormEntityContainer");
$import("mx.datacontrols.DataForm");


CoContransEnergyInfoV2.views.DetailView = function()
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
    			var transValue = me.getForm().entityContainer.data.transmission;
    			if(transValue != "" && transValue != null){
    				btn_save.on("click", me.controller._btnSave_onclick);
    			}else{
    				alert("合同输电方不能为空");
    				return;
    			}
    		});
    		me.container.addControl(btn_save);
    	
    		//关闭按钮
    		var btn_close = new mx.controls.Button({text : "关闭",left : 20});
    		me.container.addControl(btn_close);
    		//关闭按钮点击事件
    		btn_close.on("click", function() {
    			me.viewPort.close();//关闭当前页面
    		}); 
    		me.container.addControl(btn_close);
	    _initDataForm();
    }
	
    function _initDataForm()
    {
    	var restUrl = "~/rest/cocontransenergyinfov2/";
        /* 初始化 EntityContainer */        
        var formEntityContainer = new mx.datacontainers.FormEntityContainer({
            baseUrl : CoContransEnergyInfoV2.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "guid"
        });
        _form = new mx.datacontrols.DataForm({
			alias:"CoContransEnergyInfoV2DetailViewDataForm",
			fields: [
	        {	name: "guid", caption: "GUID" , editorType: "TextEditor", visible:false },
	        {	name: "transmission", caption: "* 合同输电方" , editorType: "DropDownEditor" },
	        //{	name: "participantname", caption: "合同输电方" , editorType: "DropDownEditor"	},
	        {	name: "settletype", caption: "输电费用收取方式" , editorType: "DropDownEditor"	},
	        {	name: "cost", caption: "输电费" , editorType: "TextEditor"},
	        {	name: "price", caption: "输电及网损价" , editorType: "TextEditor"},
	        {	name: "transphone", caption: "说明" , editorType: "TextEditor"},
	        {	name: "isincludetax", caption: "经济属性" , editorType: "DropDownEditor"	},
	        {	name: "taxrate", caption: "税率" , editorType: "TextEditor"	},
	        {	name: "energypercent", caption: "电量百分比" , editorType: "TextEditor"	},
	        {	name: "loss", caption: "输电损耗" , editorType: "TextEditor"},
	        {	name: "startdate", caption: "开始时间" , editorType: "DateTimeEditor", formatString: "yyyy-MM-dd"	},
	        {	name: "enddate", caption: "截止时间" , editorType: "DateTimeEditor", formatString: "yyyy-MM-dd" }
		    ],
			displayPrimaryKey: false,
            entityContainer: formEntityContainer,
            onload:initHt
        });
        
        me.addControl(_form);
        me.mSplit.addControl(_form,1);//添加_form到mSplit
    }

    function initHt(){
    	me.getForm().entityContainer.setValue("contractid",me.contractid);
    	//me.getForm().editors.contractid.setValue(me.contractName);
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