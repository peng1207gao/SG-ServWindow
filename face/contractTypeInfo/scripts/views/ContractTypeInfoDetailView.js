$ns("contractTypeInfo.views");

$import("mx.controls.ToolBar");
$import("mx.datacontainers.FormEntityContainer");
$import("mx.datacontrols.DataForm");


contractTypeInfo.views.ContractTypeInfoDetailView = function()
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
    var marketId = "";

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
            alias:"contractTypeInfoContractTypeInfoDetailViewToolBar",
            width: "100%"
        });
        var btnSave = _toolBar.appendItem("save", mx.msg("SAVE"));
        btnSave.alias = "contractTypeInfoContractTypeInfoDetailViewBtnSave";
        btnSave.setImageKey("save");
        btnSave.on("click", me.controller._btnSave_onclick);
        me.addControl(_toolBar);
    }
	
    function _initDataForm()
    {
    	        var restUrl = "~/rest/cocontracttypeinfo/";
        /* 初始化 EntityContainer */        
        var formEntityContainer = new mx.datacontainers.FormEntityContainer({
            baseUrl : contractTypeInfo.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "contracttypeid"
        });
        
        _form = new mx.datacontrols.DataForm({
			
			alias:"contractTypeInfoContractTypeInfoDetailViewDataForm",
			displayPrimaryKey: false,
			fields: [
	        {	name: "contracttypeid", caption: "合同类型ID", editorType: "TextEditor", visible:false},
	        {	name: "typename", caption: "合同类型名称", editorType: "TextEditor"},
	        {	
	        	name: "supertypeid", caption: "父合同类型", editorType: "DropDownTreeEditor", dropDownAnimation: "slideDown",
	        	url:contractTypeInfo.mappath("~/../ETradePublicUtils/rest/contractType/tree")
	        },
	        {	name: "starteffectivedate", caption: "生效时间", editorType: "DateTimeEditor", formatString: "yyyy-MM-dd"},
	        {	name: "description", caption: "说明", editorType: "TextEditor", textMode: 'multiline'}
//	        {	name: "isdefine", caption: "是否自定义", editorType: "TextEditor"},
//	        {	name: "marketid", caption: "维护部门", editorType: "TextEditor"},
//	        {	name: "endeffectivedate", caption: "失效时间", editorType: "DateTimeEditor", formatString: "yyyy-MM-dd"},
//	        {	name: "effectiveflag", caption: "是否生效", editorType: "TextEditor"},
//	        {	name: "updatetime", caption: "更新时间", editorType: "DateTimeEditor", formatString: "yyyy-MM-dd"},
//		    {	name: "updatepersonid", caption: "更新人编号", editorType: "TextEditor"}
		    ],
            entityContainer: formEntityContainer,
            onload: _initValue
        });
        _form.on("validate", function(e)
		{
			   // e.param，待校验的表单数据。
			   // e.dataForm，当前表单信息。
			var objID = me.objID;
        	var restClient = new mx.rpc.RESTClient();
		    var entityContainer = _form.entityContainer;
		    var typename = entityContainer.getValue("typename");
		    var supertypeid = entityContainer.getValue("supertypeid");
	    	var result = restClient.getSync(contractTypeInfo.mappath("~/rest/cocontracttypeinfo/getTypeName?typename="+typename+"&supertypeid="+supertypeid+"&objID="+objID)).resultValue;
			if(result!=null&&result.items.length>0){
				e.successful = false;
			    e.hint = "该合同类型名称已经存在！";
			}
//		    if(result!=null&&result.items.length>0){
//		    	for(var i=0;i<result.items.length;i++){
//		    		if(entityContainer.getValue("typename")==result.items[i]){
//		    			e.successful = false;
//				        e.hint = "该合同类型名称已经存在！";
//		    		}
//		    	}
//		    }
		});
        me.addControl(_form);
    }
    
    function _initValue(){
    	var type = me.controller.mainCo.type;
    	if(type === "add"){
	    	var restClient = new mx.rpc.RESTClient();
	    	var result = restClient.getSync(contractTypeInfo.mappath("~/rest/cocontracttypeinfo/getTime"));
			//给时间下拉框赋初值
			_form.entityContainer.setValue("starteffectivedate",result);
			_form.editors.starteffectivedate.setValue(result);//显示值
    	}
    	_form.editors.description.editorAreaHeight = 400;
//    	_form.editors.supertypeid = utils.dropDownEditor.ContractTypeTree.ContractTypeDropDownEditor(true,false);
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