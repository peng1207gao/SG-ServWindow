$ns("co_contractChangeRecordInfo.views");

$import("mx.controls.ToolBar");
$import("mx.datacontainers.FormEntityContainer");
$import("mx.datacontrols.DataForm");


co_contractChangeRecordInfo.views.DetailView = function()
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
            alias:"co_contractChangeRecordInfoDetailViewToolBar",
            width: "100%"
        });
        var btnSave = _toolBar.appendItem("save", mx.msg("SAVE"));
        btnSave.alias = "co_contractChangeRecordInfoDetailViewBtnSave";
        btnSave.setImageKey("save");
        btnSave.on("click", me.controller._btnSave_onclick);
        me.addControl(_toolBar);
    }
	
    function _initDataForm()
    {
    	        var restUrl = "~/rest/cocontractchangerecordinfo/";
        /* 初始化 EntityContainer */        
        var formEntityContainer = new mx.datacontainers.FormEntityContainer({
            baseUrl : co_contractChangeRecordInfo.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "versionid"
        });
        
        _form = new mx.datacontrols.DataForm({
			
			alias:"co_contractChangeRecordInfoDetailViewDataForm",
			displayPrimaryKey: false,
			fields: [
			{	name: "marketid", caption: "市场ID" , editorType: "TextEditor"	},
	        {	name: "contractid", caption: "合同ID" , editorType: "TextEditor"	},
	        {	name: "supercontractid", caption: "上级合同ID" , editorType: "TextEditor"	},
	        {	name: "contractname", caption: "合同名称" , editorType: "TextEditor"	},
	        {	name: "contracttype", caption: "合同类型" , editorType: "TextEditor"	},
	        {	name: "papercontractcode", caption: "纸质合同编号" , editorType: "TextEditor"	},
	        {	name: "papercontractname", caption: "纸质合同名称" , editorType: "TextEditor"	},
	        {	name: "contracttemplateid", caption: "合同范本ID" , editorType: "TextEditor"	},
	        {	name: "signstate", caption: "签订状态" , editorType: "DropDownEditor"	},
	        {	name: "signeddate", caption: "合同签订时间" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "notsignedreason", caption: "合同未签订原因" , editorType: "TextEditor"	},
	        {	name: "signedperson", caption: "合同签订人" , editorType: "TextEditor"	},
	        {	name: "signedsite", caption: "合同签订地点" , editorType: "TextEditor"	},
	        {	name: "backupstate", caption: "合同备案状态" , editorType: "DropDownEditor"	},
	        {	name: "backupdate", caption: "合同备案时间" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "backupperson", caption: "合同备案人" , editorType: "TextEditor"	},
	        {	name: "backuporg", caption: "合同备案机构" , editorType: "TextEditor"	},
	        {	name: "disbackupreason", caption: "合同未备案原因" , editorType: "TextEditor"	},
	        {	name: "isend", caption: "是否到期" , editorType: "TextEditor"	},
	        {	name: "prepcontractflag", caption: "预备合同标志" , editorType: "TextEditor"	},
	        {	name: "issecrecyflag", caption: "是否保密合同" , editorType: "TextEditor"	},
	        {	name: "contractcyc", caption: "合同类型" , editorType: "TextEditor"	},
	        {	name: "purchaser", caption: "购电主体" , editorType: "TextEditor"	},
	        {	name: "seller", caption: "售电主体" , editorType: "TextEditor"	},
	        {	name: "sellerunit", caption: "售电关口" , editorType: "TextEditor"	},
	        {	name: "purchaseunit", caption: "购电关口" , editorType: "TextEditor"	},
	        {	name: "contractqty", caption: "合同电量" , editorType: "TextEditor"	},
	        {	name: "qtytype", caption: "电量口径" , editorType: "TextEditor"	},
	        {	name: "contractstartdate", caption: "合同开始时间" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "contractenddate", caption: "合同结束时间" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "transactionid", caption: "交易ID" , editorType: "TextEditor"	},
	        {	name: "transactiontype", caption: "交易性质" , editorType: "TextEditor"	},
	        {	name: "rightsettlementside", caption: "发电权结算方" , editorType: "TextEditor"	},
	        {	name: "isrepurchaseflag", caption: "是否回购交易" , editorType: "TextEditor"	},
	        {	name: "replacetype", caption: "替代类型" , editorType: "TextEditor"	},
	        {	name: "areatype", caption: "区域类型" , editorType: "TextEditor"	},
	        {	name: "isehvflag", caption: "是特高压交易标志" , editorType: "TextEditor"	},
	        {	name: "isresaleflag", caption: "是代购转售交易标志" , editorType: "TextEditor"	},
	        {	name: "isopen", caption: "是否开口合同" , editorType: "TextEditor"	},
	        {	name: "purchasegate", caption: "购电关口" , editorType: "TextEditor"	},
	        {	name: "settlegate", caption: "售电关口" , editorType: "TextEditor"	},
	        {	name: "lossundertaker", caption: "线损承担方" , editorType: "TextEditor"	},
	        {	name: "purchaserperson", caption: "购电方联系人" , editorType: "TextEditor"	},
	        {	name: "purchaserphone", caption: "购电方联系电话" , editorType: "TextEditor"	},
	        {	name: "purchaserconftime", caption: "购电方确认时间" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "sellerperson", caption: "售电方联系人" , editorType: "TextEditor"	},
	        {	name: "sellerphone", caption: "售电方联系电话" , editorType: "TextEditor"	},
	        {	name: "sellerconftime", caption: "售电方确认时间" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "transperson", caption: "输电方联系人" , editorType: "TextEditor"	},
	        {	name: "transphone", caption: "输电方联系电话" , editorType: "TextEditor"	},
	        {	name: "transconftime", caption: "输电方确认时间" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "changetimes", caption: "变更次数" , editorType: "TextEditor"	},
	        {	name: "description", caption: "说明" , editorType: "TextEditor"	},
	        {	name: "isdelete", caption: "删除标记" , editorType: "TextEditor"	},
	        {	name: "updatetime", caption: "更新日期" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "updatepersonid", caption: "更新人id" , editorType: "TextEditor"	},
	        {	name: "versionid", caption: "版本id" , editorType: "TextEditor"	},
	        {	name: "version", caption: "版本号" , editorType: "TextEditor"	},
	        {	name: "versiondesc", caption: "版本说明" , editorType: "TextEditor"	},
	        {	name: "isrelation", caption: "关联权限" , editorType: "TextEditor"	},
	        {	name: "exectype", caption: "合同执行类型" , editorType: "TextEditor"	},
	        {	name: "flow", caption: "合同流程" , editorType: "TextEditor"	},
	        {	name: "settleside", caption: "合同结算方" , editorType: "TextEditor"	},
	        {	name: "cmd", caption: "cmd" , editorType: "TextEditor"	},
	        {	name: "operator", caption: "operator" , editorType: "TextEditor"	},
	        {	name: "operatingdescription", caption: "operatingdescription" , editorType: "TextEditor"	},
	        {	name: "operatedate", caption: "operatedate" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "orderno", caption: "orderno" , editorType: "TextEditor"	},
	        {	name: "superexecid", caption: "superexecid" , editorType: "TextEditor"	},
	        {	name: "contractenergy", caption: "contractenergy" , editorType: "TextEditor"	},
	        {	name: "contractprice", caption: "contractprice" , editorType: "TextEditor"	},
	        {	name: "energy", caption: "energy" , editorType: "TextEditor"	},
	        {	name: "sequenceid", caption: "sequenceid" , editorType: "TextEditor"	},
	        {	name: "businessid", caption: "businessid" , editorType: "TextEditor"	},
	        {	name: "flowflag", caption: "flowflag" , editorType: "TextEditor"	},
	        {	name: "coVersion", caption: "coVersion" , editorType: "TextEditor"	},
	        {	name: "isTax", caption: "是否含税" , editorType: "TextEditor"	},
	        {	name: "sellerenergy", caption: "售电方上网电量" , editorType: "TextEditor"	},
	        {	name: "ingodown", caption: "是否入库" , editorType: "TextEditor"	},
	        {	name: "expend1", caption: "扩展1" , editorType: "TextEditor"	},
	        {	name: "expend2", caption: "扩展2" , editorType: "TextEditor"	},
	        {	name: "expend3", caption: "扩展3" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "expend4", caption: "扩展4" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "expend5", caption: "扩展5" , editorType: "TextEditor"	},
	        {	name: "expend6", caption: "扩展6" , editorType: "TextEditor"	},
	        {	name: "purchasergen", caption: "购电方发电量" , editorType: "TextEditor"	},
	        {	name: "purchaserenergy", caption: "购电方上网电量" , editorType: "TextEditor"	},
	        {	name: "sellergen", caption: "售电方发电量" , editorType: "TextEditor"	}
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