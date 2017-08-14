$ns("contractaffixinfo.views");

$import("mx.controls.ToolBar");
$import("mx.datacontainers.FormEntityContainer");
$import("mx.datacontrols.DataForm");


contractaffixinfo.views.ContractAffixInfoDetailView = function()
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
//        _initToolBar();
	    _initDataForm();
	    _initRemark();
	    _initSave();
	    _initClose();
        me.on("activate", me.controller._onactivate);
    }
	
    function _initToolBar()
    {
    	_toolBar = new mx.controls.ToolBar({
            alias:"contractaffixinfoContractAffixInfoDetailViewToolBar",
            width: "100%"
        });
        var btnSave = _toolBar.appendItem("save", mx.msg("SAVE"));
        btnSave.alias = "contractaffixinfoContractAffixInfoDetailViewBtnSave";
        btnSave.setImageKey("save");
        btnSave.on("click", me.controller._btnSave_onclick);
        me.addControl(_toolBar);
    }
	
    function _initDataForm()
    {
    	        var restUrl = "~/rest/cocontractaffixinfo_mk/";
        /* 初始化 EntityContainer */        
        var formEntityContainer = new mx.datacontainers.FormEntityContainer({
            baseUrl : contractaffixinfo.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "guid"
        });
        
        _form = new mx.datacontrols.DataForm({
			
			alias:"contractaffixinfoContractAffixInfoDetailViewDataForm",
			displayPrimaryKey: false,
			fields: [
	        {	name: "marketid", caption: "市场ID", editorType: "TextEditor", visible:false},
	        {	name: "contractid", caption: "合同ID", editorType: "TextEditor", visible:false},
	        {	name: "affixno", caption: "附件编号", editorType: "TextEditor", visible:false},
	        {	name: "affixname", caption: "附件名称", editorType: "TextEditor"},
		    {	name: "affixtype", caption: "附件类型", editorType: "DropDownEditor"},
	        {	name: "description", caption: "备注", editorType: "TextEditor"},
	        {	name: "papercontractfile", caption: "文件", editorOptions:
	        	{editorType:"FileEditor",type:"form",uploadMode:"blob",tableName:"CO_ContractAffixInfo",
	        	colName:"papercontractfile",primaryKey:"guid",filesLimit:"1",onchanged:me.autoFillName}
	        },
	        {	name: "uploadperson", caption: "上传人", editorType: "TextEditor", visible:false},
	        {	name: "uploadtime", caption: "上传时间", editorType: "DateTimeEditor", formatString: "yyyy-MM-dd", visible:false},
	        {	name: "guid", caption: "GUID",editorType: "TextEditor",visible:false}
		    ],
            entityContainer: formEntityContainer,
            onload:_initValue
        });
        
        me.addControl(_form);
    }
    
    function _initRemark(){
    	me.remark = new mx.controls.Label({
    		text: "（注：文件不能大于10M!）",
    		textAlign: "left",
    		verticalAlign: "middle",
    		width: "100%"
    	});
    	me.addControl(me.remark);
    }
    
    function _initValue(){
    	_form.editors.affixname.reset();
    	_form.editors.affixtype.reset();
    	_form.editors.description.reset();
    	_form.editors.papercontractfile.reset();
    	_form.entityContainer.setValue("marketid", _form.editors.marketid.getValue());
    	_form.entityContainer.setValue("contractid", _form.editors.contractid.getValue());
    	_form.entityContainer.setValue("affixno", _form.editors.affixno.getValue());
    	_form.entityContainer.setValue("uploadperson", _form.editors.uploadperson.getValue());
    	_form.entityContainer.setValue("uploadtime", _form.editors.uploadtime.getValue());
    }

    //如果选择附件时没有输入文件名，则自动将选择的文件名填充到文件名的那一列
	me.autoFillName = function(){
		var fileEditor = me.getForm().getEditor("papercontractfile");
//		alert(fileEditor.value);
		if(me.getForm().getEditor("affixname").value==null||me.getForm().getEditor("affixname").value==""){
			me.getForm().getEditor("affixname").setValue(fileEditor.value);
			me.getForm().entityContainer.setValue("affixname",fileEditor.value);
		}
	}
    
	function _initSave(){
		me.saveButton = new mx.controls.Button({
			text: "保存 ",
			left: 200,
			top: 40,
			onclick: me.controller._btnSave_onclick
		});
		me.addControl(me.saveButton);
	}
	
	function _initClose(){
		me.closeButton = new mx.controls.Button({
			text: "关闭",
			left: 250,
			top: 40,
			onclick: me.controller._closeButton_onclick
		});
		me.addControl(me.closeButton);
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