$ns("coContractgateinfo.views");

$import("mx.controls.ToolBar");
$import("mx.datacontainers.FormEntityContainer");
$import("mx.datacontrols.DataForm");
$import("mx.rpc.RESTClient");

coContractgateinfo.views.DetailView = function()
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
    	_toolBar = new mx.controls.ToolBar({
            alias:"coContractgateinfoDetailViewToolBar",
            width: "100%"
        });
        var btnSave = _toolBar.appendItem("save", mx.msg("SAVE"));
        btnSave.alias = "coContractgateinfoDetailViewBtnSave";
        btnSave.setImageKey("save");
        btnSave.on("click", me.controller._btnSave_onclick);
        me.addControl(_toolBar);
    }
	
    function _initDataForm()
    {
    	        var restUrl = "~/rest/cocontractgateinfo/";
        /* 初始化 EntityContainer */        
        var formEntityContainer = new mx.datacontainers.FormEntityContainer({
            baseUrl : coContractgateinfo.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "guid"
        });
        
        _form = new mx.datacontrols.DataForm({
			
			alias:"coContractgateinfoDetailViewDataForm",
			displayPrimaryKey: false,
			fields: [
	        {	name: "displaytype", caption: "合同角色", editorType: "DropDownEditor",onchanged:_changed},
	        {	name: "participantid", caption: "市场成员", editorType: "DropDownEditor",  onchanged: _dropDownEditor_changed},
	        {	name: "busiunitid", caption: "业务单元", editorType: "DropDownEditor"},
	        {	name: "gateid", caption: "计量点", editorType: "DropDownEditor"},
		    {	name: "guid", caption: "GUID", editorType: "TextEditor", visible:false},
	        {	name: "contractid", caption: "合同ID", editorType: "TextEditor", visible:false}
		    ],
            entityContainer: formEntityContainer,
            onload:_getContractId
        });
        
        me.addControl(_form);
    }
    function _changed(){
    	var displaytype = me.getForm().editors.displaytype.getValue();
    	var items = restClient.getSync(coContractgateinfo.
    			mappath("~/../ETradePublicUtils/rest/commonDropEditor/getSdfDownList?type="+displaytype+"&coType="+me.coType)
    			).resultValue;	//根据角色获取市场成员
    	me.getForm().editors.participantid.setDisplayMember("name");
    	me.getForm().editors.participantid.setValueMember("value");
    	if(items != null && items != "undefined"){
    		me.getForm().editors.participantid.reset();
			me.getForm().editors.participantid.setItems(items);
    	}
    	if(displaytype==1){
    		if(me.controller.gdf!=null){
    			me.getForm().entityContainer.setValue("participantid",me.controller.gdf);
    			me.getForm().editors.participantid.setValue(me.controller.gdf);
    			_dropDownEditor_changed();
//        		me.getForm().entityContainer.setValue("participantid",me.controller.gdf);
    		}
    	}else if(displaytype==2){
    		if(me.controller.sdf!=null){
    			me.getForm().entityContainer.setValue("participantid",me.controller.sdf);
    			me.getForm().editors.participantid.setValue(me.controller.sdf);
    			_dropDownEditor_changed();
    		}
    	}
    }
	function _dropDownEditor_changed() {
		var participantid = me.getForm().editors.participantid.getValue();
		var items = restClient.getSync(coContractgateinfo.
    			mappath("~/rest/cocontractgateinfo/getBusiunit"),
    			{participantid:participantid}).resultValue;	//获取购电方数据
		me.getForm().editors.busiunitid.setDisplayMember("text");
    	me.getForm().editors.busiunitid.setValueMember("value");
		if(items != null && items != "undefined"){
			me.getForm().editors.busiunitid.reset();
			me.getForm().editors.busiunitid.setItems(items);
    	}
		var itemsGate = restClient.getSync(coContractgateinfo.
    			mappath("~/rest/cocontractgateinfo/getGateid"),
    			{participantid:participantid}).resultValue;	//获取购关口
		me.getForm().editors.gateid.setDisplayMember("text");
    	me.getForm().editors.gateid.setValueMember("value");
		if(itemsGate != null && itemsGate != "undefined"){
			me.getForm().editors.gateid.reset();
			me.getForm().editors.gateid.setItems(itemsGate);
    	}
		if(itemsGate.length<=0){
			mx.indicate("warn","市场成员计量点不存在，请先维护计量点！");
		}
	}
	function _getContractId(){
		me.getForm().entityContainer.setValue("contractid",me.param);
		me.getForm().editors.contractid.setText(me.param);
//		me.getForm().editors.busiunitid.clearItems();
		var displaytype = me.getForm().editors.displaytype;
		var dropdown = new mx.editors.DropDownEditor(
			{displayMember: "text",
   			 valueMember: "value"});
		dropdown.setItems(displaytype.items);
		var items = dropdown.items;
		me.getForm().editors.displaytype.reset();
		me.getForm().editors.displaytype.clearItems();
		if(items.length > 0){
			for(i=0;i<items.length;i++){
				var value = items[i].value;
				if(value=='1' || value =='2'){
					me.getForm().editors.displaytype.appendItem(items[i].text,items[i].value);
				}
			}
		}
//		me.getForm().editors.displaytype.removeItem(me.getForm().editors.displaytype.items[1]);
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