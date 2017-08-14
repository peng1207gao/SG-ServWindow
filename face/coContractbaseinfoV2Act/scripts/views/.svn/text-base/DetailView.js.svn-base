$ns("coContractbaseinfoV2Act.views");

$import("mx.controls.ToolBar");
$import("mx.datacontainers.FormEntityContainer");
$import("mx.datacontrols.DataForm");


coContractbaseinfoV2Act.views.DetailView = function()
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
    	_form.load();
    }

    function _initControls()
    {
        _initToolBar();
	    _initDataForm();
	    alert(me.zhen+";"+me.objID);
        me.on("activate", me.controller._onactivate);
    }
	
    function _initToolBar()
    {
    	_toolBar = new mx.controls.ToolBar({
            alias:"coContractbaseinfoV2ActDetailViewToolBar",
            width: "100%"
        });
        var btnSave = _toolBar.appendItem("save", mx.msg("SAVE"));
        btnSave.alias = "coContractbaseinfoV2ActDetailViewBtnSave";
        btnSave.setImageKey("save");
        btnSave.on("click", me.controller._btnSave_onclick);
        me.addControl(_toolBar);
    }
	
    function _initDataForm()
    {
    	        var restUrl = "~/rest/cocontractbaseinfo/";
        /* 初始化 EntityContainer */        
        var formEntityContainer = new mx.datacontainers.FormEntityContainer({
            baseUrl : coContractbaseinfoV2Act.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "contractid"
        });
        
        _form = new mx.datacontrols.DataForm({
			
			alias:"coContractbaseinfoV2ActDetailViewDataForm",
			displayPrimaryKey: false,
			fields: [
	        {	name: "marketid", caption: "??????????", editorType: "TextEditor"},
	        {	name: "contractid", caption: "??id", editorType: "TextEditor", visible:false},
	        {	name: "supercontractid", caption: "????id", editorType: "TextEditor"},
	        {	name: "contractname", caption: "????", editorType: "TextEditor"},
	        {	name: "contracttype", caption: "????", editorType: "TextEditor"},
	        {	name: "papercontractcode", caption: "?????????????", editorType: "TextEditor"},
	        {	name: "papercontractname", caption: "??????", editorType: "TextEditor"},
	        {	name: "contracttemplateid", caption: "?????????????", editorType: "TextEditor"},
	        {	name: "signstate", caption: "1???2??? ", editorType: "TextEditor"},
	        {	name: "signeddate", caption: "?????? ", editorType: "DateTimeEditor", formatString: "yyyy-MM-dd"},
	        {	name: "notsignedreason", caption: "??????? ", editorType: "TextEditor"},
	        {	name: "signedperson", caption: "????? ", editorType: "TextEditor"},
	        {	name: "signedsite", caption: "?????? ", editorType: "TextEditor"},
	        {	name: "backupperson", caption: "?????", editorType: "TextEditor"},
	        {	name: "backupdate", caption: "??????", editorType: "DateTimeEditor", formatString: "yyyy-MM-dd"},
	        {	name: "backupstate", caption: "????", editorType: "TextEditor"},
	        {	name: "backuporg", caption: "??????", editorType: "TextEditor"},
	        {	name: "disbackupreason", caption: "?????", editorType: "TextEditor"},
	        {	name: "isend", caption: "???? ", editorType: "TextEditor"},
	        {	name: "prepcontractflag", caption: "????? ", editorType: "TextEditor"},
	        {	name: "issecrecyflag", caption: "?????? ", editorType: "TextEditor"},
	        {	name: "contractcyc", caption: "1??2??3??4??5??6?? ", editorType: "TextEditor"},
	        {	name: "purchaser", caption: "???????? ", editorType: "TextEditor"},
	        {	name: "seller", caption: "???????? ", editorType: "TextEditor"},
	        {	name: "sellerunit", caption: "???? ", editorType: "TextEditor"},
	        {	name: "purchaseunit", caption: "???? ", editorType: "TextEditor"},
	        {	name: "contractqty", caption: "????", editorType: "TextEditor"},
	        {	name: "qtytype", caption: "???? ", editorType: "TextEditor"},
	        {	name: "contractstartdate", caption: "?????? ", editorType: "DateTimeEditor", formatString: "yyyy-MM-dd"},
	        {	name: "contractenddate", caption: "?????? ", editorType: "DateTimeEditor", formatString: "yyyy-MM-dd"},
	        {	name: "transactionid", caption: "????? ", editorType: "TextEditor"},
	        {	name: "transactiontype", caption: "?????? ", editorType: "TextEditor"},
	        {	name: "rightsettlementside", caption: "1???2??? ", editorType: "TextEditor"},
	        {	name: "isrepurchaseflag", caption: "?????? ", editorType: "TextEditor"},
	        {	name: "replacetype", caption: "???? ", editorType: "TextEditor"},
	        {	name: "areatype", caption: "???????? ", editorType: "TextEditor"},
	        {	name: "isehvflag", caption: "???????? ", editorType: "TextEditor"},
	        {	name: "isresaleflag", caption: "????????? ", editorType: "TextEditor"},
	        {	name: "isopen", caption: "?????? ", editorType: "TextEditor"},
	        {	name: "purchasegate", caption: "???? ", editorType: "TextEditor"},
	        {	name: "settlegate", caption: "???? ", editorType: "TextEditor"},
	        {	name: "lossundertaker", caption: "1???2??? ", editorType: "TextEditor"},
	        {	name: "purchaserperson", caption: "?????? ", editorType: "TextEditor"},
	        {	name: "purchaserphone", caption: "??????? ", editorType: "TextEditor"},
	        {	name: "purchaserconftime", caption: "??????? ", editorType: "DateTimeEditor", formatString: "yyyy-MM-dd"},
	        {	name: "sellerperson", caption: "?????? ", editorType: "TextEditor"},
	        {	name: "sellerphone", caption: "??????? ", editorType: "TextEditor"},
	        {	name: "sellerconftime", caption: "???????", editorType: "DateTimeEditor", formatString: "yyyy-MM-dd"},
	        {	name: "transperson", caption: "??????", editorType: "TextEditor"},
	        {	name: "transphone", caption: "???????", editorType: "TextEditor"},
	        {	name: "transconftime", caption: "???????", editorType: "DateTimeEditor", formatString: "yyyy-MM-dd"},
	        {	name: "changetimes", caption: "???? ", editorType: "TextEditor"},
	        {	name: "description", caption: "?? ", editorType: "TextEditor"},
	        {	name: "isdelete", caption: "???? ", editorType: "TextEditor"},
	        {	name: "updatetime", caption: "?????? ", editorType: "DateTimeEditor", formatString: "yyyy-MM-dd"},
	        {	name: "updatepersonid", caption: "??????? ", editorType: "TextEditor"},
	        {	name: "versionid", caption: "??ID ", editorType: "TextEditor"},
	        {	name: "version", caption: "??? ", editorType: "TextEditor"},
	        {	name: "versiondesc", caption: "???? ", editorType: "TextEditor"},
	        {	name: "isrelation", caption: "?????????????????????? ", editorType: "TextEditor"},
	        {	name: "exectype", caption: "??1???????2?????? ", editorType: "TextEditor"},
	        {	name: "flow", caption: "1??????2?????????3?????4?????5??????????? ", editorType: "TextEditor"},
	        {	name: "settleside", caption: "1????2???3?? ", editorType: "TextEditor"},
	        {	name: "businessid", caption: "????id", editorType: "TextEditor"},
	        {	name: "flowflag", caption: "????", editorType: "TextEditor"},
	        {	name: "sequenceid", caption: "????id", editorType: "TextEditor"},
	        {	name: "energy", caption: "??", editorType: "TextEditor"},
	        {	name: "contractprice", caption: "????", editorType: "TextEditor"},
	        {	name: "contractenergy", caption: "????", editorType: "TextEditor"},
	        {	name: "superexecid", caption: "?????ID", editorType: "TextEditor"},
	        {	name: "ingodown", caption: "????", editorType: "TextEditor"},
	        {	name: "expend1", caption: "??1", editorType: "TextEditor"},
	        {	name: "expend2", caption: "??2", editorType: "TextEditor"},
	        {	name: "expend3", caption: "??3", editorType: "DateTimeEditor", formatString: "yyyy-MM-dd"},
	        {	name: "expend4", caption: "??4", editorType: "DateTimeEditor", formatString: "yyyy-MM-dd"},
	        {	name: "expend5", caption: "??5", editorType: "TextEditor"},
	        {	name: "expend6", caption: "??6", editorType: "TextEditor"},
	        {	name: "purchasergen", caption: "??????", editorType: "TextEditor"},
	        {	name: "purchaserenergy", caption: "??????? ", editorType: "TextEditor"},
	        {	name: "sellergen", caption: "??????", editorType: "TextEditor"},
	        {	name: "sellerenergy", caption: "??????? ", editorType: "TextEditor"},
	        {	name: "coVersion", caption: "????", editorType: "TextEditor"},
		    {	name: "isTax", caption: "????", editorType: "TextEditor"}
		    ],
            entityContainer: formEntityContainer
        });
        
        me.addControl(_form);
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