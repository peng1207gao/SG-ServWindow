$ns("CoContractchangerecordinfo.views");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGridSearchBox");
$import("mx.datacontrols.ComplexGrid");


CoContractchangerecordinfo.views.MainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    //网格列表
    var _dataGrid = null;
    //表单窗口对象
    var _detailWin = null;

    base.init = me.init;
    me.init = function()
    {
        base.init();
        _initControls();
    };
    
    function _initControls()
    {
	    _initDataGrid();
    	_initDetailWindow();
        me.on("activate", me.controller._onactivate);
    }
    
    function _initDataGrid()
    {
        var restUrl = "~/rest/cocontractchangerecordinfo/";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : CoContractchangerecordinfo.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "versionid"
        });
        
        /* 初始化 DataGrid */
        _dataGrid = new mx.datacontrols.ComplexGrid({   
			// 构造查询属性。
			alias: "CoContractchangerecordinfoMainViewDataGrid",
			searchBox: new mx.datacontrols.DataGridSearchBox({
			
				fields: [
	            {	name: "marketid", caption: "??????????", editorType: "TextEditor"	},
	            {	name: "contractid", caption: "CONTRACTID", editorType: "TextEditor"	},
	            {	name: "supercontractid", caption: "SUPERCONTRACTID", editorType: "TextEditor"	},
	            {	name: "contractname", caption: "CONTRACTNAME", editorType: "TextEditor"	},
	            {	name: "contracttype", caption: "??????", editorType: "TextEditor"	},
	            {	name: "papercontractcode", caption: "PAPERCONTRACTCODE", editorType: "TextEditor"	},
	            {	name: "papercontractname", caption: "PAPERCONTRACTNAME", editorType: "TextEditor"	},
	            {	name: "contracttemplateid", caption: "?????????????", editorType: "TextEditor"	},
	            {	name: "signstate", caption: "1???2??? ", editorType: "TextEditor"	},
	            {	name: "signeddate", caption: "SIGNEDDATE", editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	            {	name: "notsignedreason", caption: "NOTSIGNEDREASON", editorType: "TextEditor"	},
	            {	name: "signedperson", caption: "SIGNEDPERSON", editorType: "TextEditor"	},
	            {	name: "signedsite", caption: "SIGNEDSITE", editorType: "TextEditor"	},
	            {	name: "backupstate", caption: "????", editorType: "TextEditor"	},
	            {	name: "backupdate", caption: "??????", editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	            {	name: "backupperson", caption: "?????", editorType: "TextEditor"	},
	            {	name: "backuporg", caption: "??????", editorType: "TextEditor"	},
	            {	name: "disbackupreason", caption: "DISBACKUPREASON", editorType: "TextEditor"	},
	            {	name: "isend", caption: "ISEND", editorType: "TextEditor"	},
	            {	name: "prepcontractflag", caption: "PREPCONTRACTFLAG", editorType: "TextEditor"	},
	            {	name: "issecrecyflag", caption: "ISSECRECYFLAG", editorType: "TextEditor"	},
	            {	name: "contractcyc", caption: "1??2??3??4??5??6?? ", editorType: "TextEditor"	},
	            {	name: "purchaser", caption: "???? ", editorType: "TextEditor"	},
	            {	name: "seller", caption: "???? ", editorType: "TextEditor"	},
	            {	name: "sellerunit", caption: "???? ", editorType: "TextEditor"	},
	            {	name: "purchaseunit", caption: "???? ", editorType: "TextEditor"	},
	            {	name: "contractqty", caption: "????", editorType: "TextEditor"	},
	            {	name: "qtytype", caption: "???? ", editorType: "TextEditor"	},
	            {	name: "contractstartdate", caption: "CONTRACTSTARTDATE", editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	            {	name: "contractenddate", caption: "CONTRACTENDDATE", editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	            {	name: "transactionid", caption: "????? ", editorType: "TextEditor"	},
	            {	name: "transactiontype", caption: "?????? ", editorType: "TextEditor"	},
	            {	name: "rightsettlementside", caption: "1???2??? ", editorType: "TextEditor"	},
	            {	name: "isrepurchaseflag", caption: "ISREPURCHASEFLAG", editorType: "TextEditor"	},
	            {	name: "replacetype", caption: "REPLACETYPE", editorType: "TextEditor"	},
	            {	name: "areatype", caption: "???????? ", editorType: "TextEditor"	},
	            {	name: "isehvflag", caption: "ISEHVFLAG", editorType: "TextEditor"	},
	            {	name: "isresaleflag", caption: "ISRESALEFLAG", editorType: "TextEditor"	},
	            {	name: "isopen", caption: "ISOPEN", editorType: "TextEditor"	},
	            {	name: "purchasegate", caption: "PURCHASEGATE", editorType: "TextEditor"	},
	            {	name: "settlegate", caption: "SETTLEGATE", editorType: "TextEditor"	},
	            {	name: "lossundertaker", caption: "1???2??? ", editorType: "TextEditor"	},
	            {	name: "purchaserperson", caption: "PURCHASERPERSON", editorType: "TextEditor"	},
	            {	name: "purchaserphone", caption: "PURCHASERPHONE", editorType: "TextEditor"	},
	            {	name: "purchaserconftime", caption: "PURCHASERCONFTIME", editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	            {	name: "sellerperson", caption: "SELLERPERSON", editorType: "TextEditor"	},
	            {	name: "sellerphone", caption: "SELLERPHONE", editorType: "TextEditor"	},
	            {	name: "sellerconftime", caption: "SELLERCONFTIME", editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	            {	name: "transperson", caption: "??????", editorType: "TextEditor"	},
	            {	name: "transphone", caption: "???????", editorType: "TextEditor"	},
	            {	name: "transconftime", caption: "???????", editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	            {	name: "changetimes", caption: "CHANGETIMES", editorType: "TextEditor"	},
	            {	name: "description", caption: "DESCRIPTION", editorType: "TextEditor"	},
	            {	name: "isdelete", caption: "ISDELETE", editorType: "TextEditor"	},
	            {	name: "updatetime", caption: "UPDATETIME", editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	            {	name: "updatepersonid", caption: "UPDATEPERSONID", editorType: "TextEditor"	},
	            {	name: "versionid", caption: "VERSIONID", editorType: "TextEditor"	},
	            {	name: "version", caption: "VERSION", editorType: "TextEditor"	},
	            {	name: "versiondesc", caption: "VERSIONDESC", editorType: "TextEditor"	},
	            {	name: "isrelation", caption: "?????????????????????? ", editorType: "TextEditor"	},
	            {	name: "exectype", caption: "??1???????2?????? ", editorType: "TextEditor"	},
	            {	name: "flow", caption: "1??????2?????????3?????4?????5??????????? ", editorType: "TextEditor"	},
	            {	name: "settleside", caption: "1????2???3?? ", editorType: "TextEditor"	},
	            {	name: "cmd", caption: "1??????2?????3???? ", editorType: "TextEditor"	},
	            {	name: "operator", caption: "OPERATOR", editorType: "TextEditor"	},
	            {	name: "operatingdescription", caption: "OPERATINGDESCRIPTION", editorType: "TextEditor"	},
	            {	name: "operatedate", caption: "??????? ", editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	            {	name: "orderno", caption: "???????????????????????????1 ", editorType: "TextEditor"	},
	            {	name: "superexecid", caption: "SUPEREXECID", editorType: "TextEditor"	},
	            {	name: "contractenergy", caption: "CONTRACTENERGY", editorType: "TextEditor"	},
	            {	name: "contractprice", caption: "CONTRACTPRICE", editorType: "TextEditor"	},
	            {	name: "energy", caption: "ENERGY", editorType: "TextEditor"	},
	            {	name: "sequenceid", caption: "SEQUENCEID", editorType: "TextEditor"	},
	            {	name: "businessid", caption: "BUSINESSID", editorType: "TextEditor"	},
	            {	name: "flowflag", caption: "FLOWFLAG", editorType: "TextEditor"	},
	            {	name: "coVersion", caption: "????", editorType: "TextEditor"	},
	            {	name: "isTax", caption: "????", editorType: "TextEditor"	},
	            {	name: "sellerenergy", caption: "???????", editorType: "TextEditor"	},
	            {	name: "ingodown", caption: "INGODOWN", editorType: "TextEditor"	},
	            {	name: "expend1", caption: "EXPEND1", editorType: "TextEditor"	},
	            {	name: "expend2", caption: "EXPEND2", editorType: "TextEditor"	},
	            {	name: "expend3", caption: "EXPEND3", editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	            {	name: "expend4", caption: "EXPEND4", editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	            {	name: "expend5", caption: "EXPEND5", editorType: "TextEditor"	},
	            {	name: "expend6", caption: "EXPEND6", editorType: "TextEditor"	},
	            {	name: "purchasergen", caption: "PURCHASERGEN", editorType: "TextEditor"	},
	            {	name: "purchaserenergy", caption: "PURCHASERENERGY", editorType: "TextEditor"	},
		        {	name: "sellergen", caption: "SELLERGEN", editorType: "TextEditor"	}
				]
			}),
			
			columns:[
	        {	name: "marketid", caption: "??????????" , editorType: "TextEditor"	},
	        {	name: "contractid", caption: "CONTRACTID" , editorType: "TextEditor"	},
	        {	name: "supercontractid", caption: "SUPERCONTRACTID" , editorType: "TextEditor"	},
	        {	name: "contractname", caption: "CONTRACTNAME" , editorType: "TextEditor"	},
	        {	name: "contracttype", caption: "??????" , editorType: "TextEditor"	},
	        {	name: "papercontractcode", caption: "PAPERCONTRACTCODE" , editorType: "TextEditor"	},
	        {	name: "papercontractname", caption: "PAPERCONTRACTNAME" , editorType: "TextEditor"	},
	        {	name: "contracttemplateid", caption: "?????????????" , editorType: "TextEditor"	},
	        {	name: "signstate", caption: "1???2??? " , editorType: "TextEditor"	},
	        {	name: "signeddate", caption: "SIGNEDDATE" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "notsignedreason", caption: "NOTSIGNEDREASON" , editorType: "TextEditor"	},
	        {	name: "signedperson", caption: "SIGNEDPERSON" , editorType: "TextEditor"	},
	        {	name: "signedsite", caption: "SIGNEDSITE" , editorType: "TextEditor"	},
	        {	name: "backupstate", caption: "????" , editorType: "TextEditor"	},
	        {	name: "backupdate", caption: "??????" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "backupperson", caption: "?????" , editorType: "TextEditor"	},
	        {	name: "backuporg", caption: "??????" , editorType: "TextEditor"	},
	        {	name: "disbackupreason", caption: "DISBACKUPREASON" , editorType: "TextEditor"	},
	        {	name: "isend", caption: "ISEND" , editorType: "TextEditor"	},
	        {	name: "prepcontractflag", caption: "PREPCONTRACTFLAG" , editorType: "TextEditor"	},
	        {	name: "issecrecyflag", caption: "ISSECRECYFLAG" , editorType: "TextEditor"	},
	        {	name: "contractcyc", caption: "1??2??3??4??5??6?? " , editorType: "TextEditor"	},
	        {	name: "purchaser", caption: "???? " , editorType: "TextEditor"	},
	        {	name: "seller", caption: "???? " , editorType: "TextEditor"	},
	        {	name: "sellerunit", caption: "???? " , editorType: "TextEditor"	},
	        {	name: "purchaseunit", caption: "???? " , editorType: "TextEditor"	},
	        {	name: "contractqty", caption: "????" , editorType: "TextEditor"	},
	        {	name: "qtytype", caption: "???? " , editorType: "TextEditor"	},
	        {	name: "contractstartdate", caption: "CONTRACTSTARTDATE" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "contractenddate", caption: "CONTRACTENDDATE" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "transactionid", caption: "????? " , editorType: "TextEditor"	},
	        {	name: "transactiontype", caption: "?????? " , editorType: "TextEditor"	},
	        {	name: "rightsettlementside", caption: "1???2??? " , editorType: "TextEditor"	},
	        {	name: "isrepurchaseflag", caption: "ISREPURCHASEFLAG" , editorType: "TextEditor"	},
	        {	name: "replacetype", caption: "REPLACETYPE" , editorType: "TextEditor"	},
	        {	name: "areatype", caption: "???????? " , editorType: "TextEditor"	},
	        {	name: "isehvflag", caption: "ISEHVFLAG" , editorType: "TextEditor"	},
	        {	name: "isresaleflag", caption: "ISRESALEFLAG" , editorType: "TextEditor"	},
	        {	name: "isopen", caption: "ISOPEN" , editorType: "TextEditor"	},
	        {	name: "purchasegate", caption: "PURCHASEGATE" , editorType: "TextEditor"	},
	        {	name: "settlegate", caption: "SETTLEGATE" , editorType: "TextEditor"	},
	        {	name: "lossundertaker", caption: "1???2??? " , editorType: "TextEditor"	},
	        {	name: "purchaserperson", caption: "PURCHASERPERSON" , editorType: "TextEditor"	},
	        {	name: "purchaserphone", caption: "PURCHASERPHONE" , editorType: "TextEditor"	},
	        {	name: "purchaserconftime", caption: "PURCHASERCONFTIME" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "sellerperson", caption: "SELLERPERSON" , editorType: "TextEditor"	},
	        {	name: "sellerphone", caption: "SELLERPHONE" , editorType: "TextEditor"	},
	        {	name: "sellerconftime", caption: "SELLERCONFTIME" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "transperson", caption: "??????" , editorType: "TextEditor"	},
	        {	name: "transphone", caption: "???????" , editorType: "TextEditor"	},
	        {	name: "transconftime", caption: "???????" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "changetimes", caption: "CHANGETIMES" , editorType: "TextEditor"	},
	        {	name: "description", caption: "DESCRIPTION" , editorType: "TextEditor"	},
	        {	name: "isdelete", caption: "ISDELETE" , editorType: "TextEditor"	},
	        {	name: "updatetime", caption: "UPDATETIME" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "updatepersonid", caption: "UPDATEPERSONID" , editorType: "TextEditor"	},
	        {	name: "versionid", caption: "VERSIONID" , editorType: "TextEditor"	},
	        {	name: "version", caption: "VERSION" , editorType: "TextEditor"	},
	        {	name: "versiondesc", caption: "VERSIONDESC" , editorType: "TextEditor"	},
	        {	name: "isrelation", caption: "?????????????????????? " , editorType: "TextEditor"	},
	        {	name: "exectype", caption: "??1???????2?????? " , editorType: "TextEditor"	},
	        {	name: "flow", caption: "1??????2?????????3?????4?????5??????????? " , editorType: "TextEditor"	},
	        {	name: "settleside", caption: "1????2???3?? " , editorType: "TextEditor"	},
	        {	name: "cmd", caption: "1??????2?????3???? " , editorType: "TextEditor"	},
	        {	name: "operator", caption: "OPERATOR" , editorType: "TextEditor"	},
	        {	name: "operatingdescription", caption: "OPERATINGDESCRIPTION" , editorType: "TextEditor"	},
	        {	name: "operatedate", caption: "??????? " , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "orderno", caption: "???????????????????????????1 " , editorType: "TextEditor"	},
	        {	name: "superexecid", caption: "SUPEREXECID" , editorType: "TextEditor"	},
	        {	name: "contractenergy", caption: "CONTRACTENERGY" , editorType: "TextEditor"	},
	        {	name: "contractprice", caption: "CONTRACTPRICE" , editorType: "TextEditor"	},
	        {	name: "energy", caption: "ENERGY" , editorType: "TextEditor"	},
	        {	name: "sequenceid", caption: "SEQUENCEID" , editorType: "TextEditor"	},
	        {	name: "businessid", caption: "BUSINESSID" , editorType: "TextEditor"	},
	        {	name: "flowflag", caption: "FLOWFLAG" , editorType: "TextEditor"	},
	        {	name: "coVersion", caption: "????" , editorType: "TextEditor"	},
	        {	name: "isTax", caption: "????" , editorType: "TextEditor"	},
	        {	name: "sellerenergy", caption: "???????" , editorType: "TextEditor"	},
	        {	name: "ingodown", caption: "INGODOWN" , editorType: "TextEditor"	},
	        {	name: "expend1", caption: "EXPEND1" , editorType: "TextEditor"	},
	        {	name: "expend2", caption: "EXPEND2" , editorType: "TextEditor"	},
	        {	name: "expend3", caption: "EXPEND3" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "expend4", caption: "EXPEND4" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "expend5", caption: "EXPEND5" , editorType: "TextEditor"	},
	        {	name: "expend6", caption: "EXPEND6" , editorType: "TextEditor"	},
	        {	name: "purchasergen", caption: "PURCHASERGEN" , editorType: "TextEditor"	},
	        {	name: "purchaserenergy", caption: "PURCHASERENERGY" , editorType: "TextEditor"	},
	        {	name: "sellergen", caption: "SELLERGEN" , editorType: "TextEditor"	}
            ],
            // 构造列排序条件，如果有多列，则以逗号分隔。例sorter: "school ASC, class DESC"
            displayCheckBox: true,
	        displayPrimaryKey:false,//列表是否显示主键
            allowEditing: false, //列表默认不可编辑
	        pageSize : 20,
            entityContainer: gridEntityContainer,
	        create: me.controller._btnNew_onclick,
            remove: me.controller._btnDelete_onclick
        });
        
	    //重置toolBar按钮项
        _resetToolBarItems();
        me.addControl(_dataGrid);
    }
    
    /**
     * 重置按钮项
     */
    function _resetToolBarItems(){
    	//去除保存按钮
		_dataGrid.toolBar.removeByIndex(1);
		_dataGrid.toolBar.removeByName("save");
		//插入编辑按钮
		_dataGrid.toolBar.insertItem(2,"-",true);
		_dataGrid.toolBar.insertItem(3,{ name: "edit", text: mx.msg("EDIT"), toolTip: mx.msg("EDIT"), imageKey : "edit", onclick: me.controller._btnEdit_onclick},true);
    }

    /**
     * 初始化表单视图窗口对象
     */
    function _initDetailWindow(){
    	_detailWin = utils.commonFun.WinMananger.create({
			reusable: true,//是否复用
			width:640,
			height:480,
			title:"表单维护"
		});
    }

    /**
     * 获取表单视图窗口对象
     */
    me.getDetailWindow = function(){
    	return _detailWin;
    }
    
    /**
     * 获取DataGrid网格列表对象
     */
    me.getDataGrid = function(){
    	return _dataGrid;
    }
    
	me.endOfClass(arguments)
    return me;
};