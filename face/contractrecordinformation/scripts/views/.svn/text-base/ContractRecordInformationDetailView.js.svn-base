$ns("contractrecordinformation.views");

$import("mx.controls.ToolBar");
$import("mx.datacontainers.FormEntityContainer");
$import("mx.datacontrols.DataForm");
//$import("mytest.views.MainViewController");

contractrecordinformation.views.ContractRecordInformationDetailView = function()
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
            alias:"contractrecordinformationContractRecordInformationDetailViewToolBar",
            width: "100%"
        });
        var btnSave = _toolBar.appendItem("save", mx.msg("SAVE"));
        btnSave.alias = "contractrecordinformationContractRecordInformationDetailViewBtnSave";
        btnSave.setImageKey("save");
        btnSave.on("click", function(){
        	
        	//备案机构和备案份数数据校验
        	if(_form.entityContainer.getValue("backuporg")==null||_form.entityContainer.getValue("backuporg")=="")
        	{
        		alert("备案机构不能为空。");
        		return;
        	}
        	if(_form.entityContainer.getValue("contractnum")==null||_form.entityContainer.getValue("contractnum")=="")
        	{
        		alert("备案数量不能为空。");
        		return;
        	}
        	var contractSum = _form.entityContainer.getValue("contractnum");
        	if(!/^\d{1,3}$/.test(contractSum))
        	{
        		alert("备案数量应为大于0小于1000的整数。");
        		return;
        	}
        	
        	if(_form.entityContainer.getValue("contractid")==null||_form.entityContainer.getValue("contractid")=="")
        	{
        		var contractIdStr = me.contractid;
        		_form.entityContainer.setValue("contractid",contractIdStr);
        	}
        	_form.entityContainer.setValue("backupstatus",_form.entityContainer.getValue("status"));
        	_form.entityContainer.setValue("backuptype",_form.entityContainer.getValue("ctype"));
        	me.controller._btnSave_onclick();
        });
        me.addControl(_toolBar);
    }
    function _initDataForm()
    {
    	var restUrl = "~/rest/tjfxmaplineid/";
        /* 初始化 EntityContainer */        
        var formEntityContainer = new mx.datacontainers.FormEntityContainer({
            baseUrl : contractrecordinformation.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "guid"
        });
        //contractrecordinformation.mappath("~/../ETradePublicUtils/rest/baGencodeTypeList/getList?type=24")
        var getStatusData = new mx.rpc.RESTClient().getSync(contractrecordinformation.mappath("~/../ETradePublicUtils/rest/baGencodeTypeList/getList?type=26"));
        var statusArr =getStatusData.resultValue ;
        var getTypeData = new mx.rpc.RESTClient().getSync(contractrecordinformation.mappath("~/../ETradePublicUtils/rest/baGencodeTypeList/getList?type=71"));
        var typeArr = getTypeData.resultValue
        _form = new mx.datacontrols.DataForm({
			
			alias:"contractrecordinformationContractRecordInformationDetailViewDataForm",
			displayPrimaryKey: false,
			fields: [//backuptype  backupstatus
	        {	name: "guid", caption: "GUID", editorType: "TextEditor", visible:false},
	        {	name: "contractid", caption: "contractid", editorType: "TextEditor", visible:false},
	        {	name: "status", caption: "备案状态" , editorType: "DropDownEditor",allowEditing: false, displayMember: "name", valueMember: "value",  items: statusArr	},
	        {	name: "backupstatus", caption: "backupstatus", editorType: "TextEditor" , visible:false},
	        {	name: "backuporg", caption: "备案机构" , editorType: "TextEditor"	},
	        {	name: "ctype", caption: "合同备案类型" , editorType: "DropDownEditor"	,allowEditing: false, displayMember: "name", valueMember: "value",  items: typeArr},
	        {	name: "backuptype", caption: "backuptype", editorType: "TextEditor", visible:false},
	        {	name: "backupperson", caption: "备案人" , editorType: "TextEditor"	},
	        {	name: "backupdate", caption: "备案时间" , editorType: "DateTimeEditor", formatString: "yyyy-MM-dd"	},
	        {	name: "contractnum", caption: "备案份数" , editorType: "TextEditor"	},
	        {	name: "description", caption: "说明", editorType: "TextEditor", textMode: "multiline" }
		    ],
            entityContainer: formEntityContainer,
            onload : _initload
        });
        
        me.addControl(_form);
    }
    function _initload()
    {
    	if( me.objID!=""&&me.objID!=null)
    	{
    		var satusValue =  _form.entityContainer.getValue("backupstatus");
        	var typeValue =  _form.entityContainer.getValue("backuptype");
        	var statusText = _form.entityContainer.getValue("status");
        	var ctypeText = _form.entityContainer.getValue("ctype");
        	_form.entityContainer.setValue("status",satusValue);
        	_form.editors.status.setValue(statusText);
        	_form.entityContainer.setValue("ctype",typeValue);
        	//_form.fields.ctype.setValue(ctypeText);
        	_form.editors.ctype.setValue(ctypeText);
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