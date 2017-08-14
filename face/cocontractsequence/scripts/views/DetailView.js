$ns("cocontractsequence.views");

$import("mx.controls.ToolBar");
$import("mx.datacontrols.DataForm");
$import("mx.rpc.RESTClient");
cocontractsequence.views.DetailView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    me.objID = null;
    me.sequenceid=null;
    me.form = null;
    var restClient = new mx.rpc.RESTClient();
    base.init = me.init;
    me.init = function()
    {
        base.init();
        _initControls();
    };
    
    function _initControls()
    {
        var toolBar = new mx.controls.ToolBar({
            width: "100%",
	    items:[
	    	{name: "save", text: mx.msg("SAVE"), imageKey : "save", toolTip: mx.msg("SAVE"), onclick: me.controller._btnSave_onclick}
	    ]
        });
        me.addControl(toolBar);
        var restUrl = "~/rest/pubdemandelectricity/";
        
        var formEntityContainer = new mx.datacontainers.FormEntityContainer({
        	baseUrl : cocontractsequence.mappath("~/rest/cocontractsequence/"),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "sequenceid"
        });
	
	    me.form = new mx.datacontrols.DataForm({
			id:"tenform",
			entityContainer:formEntityContainer,
        	fields: [
        	    {	name: "sequenceid", caption: "合同序列id", editorType: "TextEditor"},
	            {	name: "sequencename", caption: "合同序列名称", editorType: "TextEditor"},
	            {	name: "sequencetypeStr", caption: "合同序列类型", editorType: "TextEditor",visible:false}, //默认给一个序列类型 为2
	            {	name: "sequencecircleStr", caption: "合同序列周期", editorType: "DropDownEditor"},
	            {   name:"contractType",caption:"合同类型",editorType:"DropDownTreeEditor",
			    	displayMember: "name",
    			    valueMember: "value",
			    	displayCheckBox: true,
					url: cocontractsequence.mappath("~/rest/cocontractsequence/getTree")
			    },
			    {	name: "contractTypeName", caption: "合同序列名称", editorType: "TextEditor",visible:false}
        	],
        	onload:me._onLoadDataForm
        });
	    me.addControl(me.form);
        me.form.load(me.controller.checkId);
        
    }
    me._onLoadDataForm=function(){
    	me.form.editors[2].setValue(2);
    	me.form.entityContainer.setValue("sequencetypeStr",2);
    	me.form.entityContainer.setValue("sequencename",me.form.editors[1].text)
    	me.form.entityContainer.setValue("sequencecircleStr",me.form.editors[3].value);
    	me.form.entityContainer.setValue("contractType",me.form.editors[4].value);
    	me.form.editors[4].setValue(me.form.editors[4].value,me.form.editors[5].text);
    }
    return me.endOfClass(arguments);
};