$ns("templatemanage.views");

$import("mx.controls.ToolBar");
$import("mx.datacontainers.FormEntityContainer");
$import("mx.datacontrols.DataForm");


templatemanage.views.TemplateManageDetailView = function()
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
    	var fjmc = null;
    	if(me.objID!=null){
	    	var restClient = new mx.rpc.RESTClient();
	    	fjmc = restClient.getSync(templatemanage.mappath("~/rest/cocontracttemplate/getFjmc?id="+me.objID));
    	}
    	//加载表单信息
    	_form.load(me.objID,function(){//因为数据库中字段contracttemplatefile类型为blob，导致编辑一条记录时，form表单里该字段无法自动填充值，
    		if(me.objID!=null){        //故采取jQuery的方法实现自动填充值
	    		var html = "<div class='linkEditor'><a><span id='text'>"+fjmc+";"+"</span></a></div>";
				_form.editors.contracttemplatefile.$("#inputContainer").prepend(html);
				_form.editors.contracttemplatefile.$("#text").on("click",
					function() {
						me.controller.downFile(_form.editors.contracttemplatecode.value);//点击链接实现下载
					});
    		}
    	});
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
            alias:"templatemanageTemplateManageDetailViewToolBar",
            width: "100%"
        });
        var btnSave = _toolBar.appendItem("save", mx.msg("SAVE"));
        btnSave.alias = "templatemanageTemplateManageDetailViewBtnSave";
        btnSave.setImageKey("save");
        btnSave.on("click", me.controller._btnSave_onclick);
        me.addControl(_toolBar);
    }
	
    function _initDataForm()
    {
    	        var restUrl = "~/rest/cocontracttemplate/";
        /* 初始化 EntityContainer */        
        var formEntityContainer = new mx.datacontainers.FormEntityContainer({
            baseUrl : templatemanage.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "contracttemplatecode"
        });
        
        _form = new mx.datacontrols.DataForm({
			
			alias:"templatemanageTemplateManageDetailViewDataForm",
			displayPrimaryKey: false,
			fields: [
//	        {	name: "marketid", caption: "市场ID", editorType: "TextEditor"},
	        {	name: "contracttemplatecode", caption: "合同范本ID", editorType: "TextEditor", visible:false},
	        {	name: "contracttemplatename", caption: "合同范本名称", editorType: "TextEditor",nullable:false},
	        {	name: "contracttype", caption: "合同类型", editorType: "DropDownTreeEditor",
	        	url:templatemanage.mappath("~/../ETradePublicUtils/rest/contractType/tree")
	        },
	        {	name: "contracttemplatefile", caption: "导入文件", editorType: "FileEditor",type: "form",uploadMode: "file",
	        	tableName: "co_contemplatewithparam",primaryKey: "CONTRACTTEMPLATECODE",colName: "CONTRACTTEMPLATEFILE",nullable:false,
	        	filesLimit:"1",onchanged:me.autoFillName
	        },
	        {	name: "descreption", caption: "备注", editorType: "TextEditor",textMode:"multiline"}
//	        {	name: "issueddate", caption: "下发时间", editorType: "DateTimeEditor", formatString: "yyyy-MM-dd"},
//	        {	name: "starteffectivedate", caption: "生效时间", editorType: "DateTimeEditor", formatString: "yyyy-MM-dd"},
//	        {	name: "endeffectivedate", caption: "失效时间", editorType: "DateTimeEditor", formatString: "yyyy-MM-dd"},
//	        {	name: "effectiveflag", caption: "失效标志", editorType: "TextEditor"},
//	        {	name: "isdelete", caption: "删除标记", editorType: "TextEditor"},
//	        {	name: "updatetime", caption: "信息更新时间", editorType: "DateTimeEditor", formatString: "yyyy-MM-dd"},
//		    {	name: "updatepersonid", caption: "信息更新人编号", editorType: "TextEditor"}
		    ],
            entityContainer: formEntityContainer
        });
        
        me.addControl(_form);
    }
    
    //如果选择附件时没有输入文件名，则自动将选择的文件名填充到文件名的那一列
	me.autoFillName = function(){
		var fileEditor = me.getForm().getEditor("contracttemplatefile");
//		alert(fileEditor.value);
		if(me.getForm().getEditor("contracttemplatename").value==null||me.getForm().getEditor("contracttemplatename").value==""){
			me.getForm().getEditor("contracttemplatename").setValue(fileEditor.value);
			me.getForm().entityContainer.setValue("contracttemplatename",fileEditor.value);
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