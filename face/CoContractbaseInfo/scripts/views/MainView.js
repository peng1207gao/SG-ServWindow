$ns("CoContractbaseInfo.views");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGridSearchBox");
$import("mx.datacontrols.ComplexGrid");


CoContractbaseInfo.views.MainView = function()
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
        var restUrl = "~/rest/contractbase/";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : CoContractbaseInfo.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "contractid"
        });
        
        /* 初始化 DataGrid */
        _dataGrid = new mx.datacontrols.ComplexGrid({   
			// 构造查询属性。
			alias: "CoContractbaseInfoMainViewDataGrid",
			searchBox: new mx.datacontrols.DataGridSearchBox({
			
				fields: [
	            {	name: "marketid", caption: "??????????", editorType: "TextEditor"	},
	            {	name: "contractid", caption: "??id", editorType: "TextEditor"	},
	            {	name: "supercontractid", caption: "????id", editorType: "TextEditor"	},
	            {	name: "contractname", caption: "????", editorType: "TextEditor"	},
	            {	name: "contracttype", caption: "????", editorType: "TextEditor"	},
	            {	name: "papercontractcode", caption: "?????????????", editorType: "TextEditor"	},
	            {	name: "papercontractname", caption: "??????", editorType: "TextEditor"	},
	            {	name: "contracttemplateid", caption: "?????????????", editorType: "TextEditor"	},
	            {	name: "signstate", caption: "1???2??? ", editorType: "TextEditor"	},
	            {	name: "signeddate", caption: "?????? ", editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	            {	name: "notsignedreason", caption: "??????? ", editorType: "TextEditor"	},
	            {	name: "signedperson", caption: "????? ", editorType: "TextEditor"	},
	            {	name: "signedsite", caption: "?????? ", editorType: "TextEditor"	},
	            {	name: "backupperson", caption: "?????", editorType: "TextEditor"	},
	            {	name: "backupdate", caption: "??????", editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	            {	name: "backupstate", caption: "????", editorType: "TextEditor"	},
	            {	name: "backuporg", caption: "??????", editorType: "TextEditor"	},
	            {	name: "disbackupreason", caption: "?????", editorType: "TextEditor"	},
	            {	name: "isend", caption: "???? ", editorType: "TextEditor"	},
	            {	name: "prepcontractflag", caption: "????? ", editorType: "TextEditor"	},
	            {	name: "issecrecyflag", caption: "?????? ", editorType: "TextEditor"	},
	            {	name: "contractcyc", caption: "1??2??3??4??5??6?? ", editorType: "TextEditor"	},
	            {	name: "purchaser", caption: "???????? ", editorType: "TextEditor"	},
	            {	name: "seller", caption: "???????? ", editorType: "TextEditor"	},
	            {	name: "sellerunit", caption: "???? ", editorType: "TextEditor"	},
	            {	name: "purchaseunit", caption: "???? ", editorType: "TextEditor"	},
	            {	name: "contractqty", caption: "????", editorType: "TextEditor"	},
	            {	name: "qtytype", caption: "???? ", editorType: "TextEditor"	},
	            {	name: "contractstartdate", caption: "?????? ", editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	            {	name: "contractenddate", caption: "?????? ", editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	            {	name: "transactionid", caption: "????? ", editorType: "TextEditor"	},
	            {	name: "transactiontype", caption: "?????? ", editorType: "TextEditor"	},
	            {	name: "rightsettlementside", caption: "1???2??? ", editorType: "TextEditor"	},
	            {	name: "isrepurchaseflag", caption: "?????? ", editorType: "TextEditor"	},
	            {	name: "replacetype", caption: "???? ", editorType: "TextEditor"	},
	            {	name: "areatype", caption: "???????? ", editorType: "TextEditor"	},
	            {	name: "isehvflag", caption: "???????? ", editorType: "TextEditor"	},
	            {	name: "isresaleflag", caption: "????????? ", editorType: "TextEditor"	},
	            {	name: "isopen", caption: "?????? ", editorType: "TextEditor"	},
	            {	name: "purchasegate", caption: "???? ", editorType: "TextEditor"	},
	            {	name: "settlegate", caption: "???? ", editorType: "TextEditor"	},
	            {	name: "lossundertaker", caption: "1???2??? ", editorType: "TextEditor"	},
	            {	name: "purchaserperson", caption: "?????? ", editorType: "TextEditor"	},
	            {	name: "purchaserphone", caption: "??????? ", editorType: "TextEditor"	},
	            {	name: "purchaserconftime", caption: "??????? ", editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	            {	name: "sellerperson", caption: "?????? ", editorType: "TextEditor"	},
	            {	name: "sellerphone", caption: "??????? ", editorType: "TextEditor"	},
	            {	name: "sellerconftime", caption: "???????", editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	            {	name: "transperson", caption: "??????", editorType: "TextEditor"	},
	            {	name: "transphone", caption: "???????", editorType: "TextEditor"	},
	            {	name: "transconftime", caption: "???????", editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	            {	name: "changetimes", caption: "???? ", editorType: "TextEditor"	},
	            {	name: "description", caption: "?? ", editorType: "TextEditor"	},
	            {	name: "isdelete", caption: "???? ", editorType: "TextEditor"	},
	            {	name: "updatetime", caption: "?????? ", editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	            {	name: "updatepersonid", caption: "??????? ", editorType: "TextEditor"	},
	            {	name: "versionid", caption: "??ID ", editorType: "TextEditor"	},
	            {	name: "version", caption: "??? ", editorType: "TextEditor"	},
	            {	name: "versiondesc", caption: "???? ", editorType: "TextEditor"	},
	            {	name: "isrelation", caption: "?????????????????????? ", editorType: "TextEditor"	},
	            {	name: "exectype", caption: "??1???????2?????? ", editorType: "TextEditor"	},
	            {	name: "flow", caption: "1??????2?????????3?????4?????5??????????? ", editorType: "TextEditor"	},
	            {	name: "settleside", caption: "1????2???3?? ", editorType: "TextEditor"	},
	            {	name: "businessid", caption: "????id", editorType: "TextEditor"	},
	            {	name: "flowflag", caption: "????", editorType: "TextEditor"	},
	            {	name: "sequenceid", caption: "????id", editorType: "TextEditor"	},
	            {	name: "energy", caption: "??", editorType: "TextEditor"	},
	            {	name: "contractprice", caption: "????", editorType: "TextEditor"	},
	            {	name: "contractenergy", caption: "????", editorType: "TextEditor"	},
	            {	name: "superexecid", caption: "?????ID", editorType: "TextEditor"	},
	            {	name: "ingodown", caption: "????", editorType: "TextEditor"	},
	            {	name: "expend1", caption: "??1", editorType: "TextEditor"	},
	            {	name: "expend2", caption: "??2", editorType: "TextEditor"	},
	            {	name: "expend3", caption: "??3", editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	            {	name: "expend4", caption: "??4", editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	            {	name: "expend5", caption: "??5", editorType: "TextEditor"	},
	            {	name: "expend6", caption: "??6", editorType: "TextEditor"	},
	            {	name: "purchasergen", caption: "??????", editorType: "TextEditor"	},
	            {	name: "purchaserenergy", caption: "??????? ", editorType: "TextEditor"	},
	            {	name: "sellergen", caption: "??????", editorType: "TextEditor"	},
	            {	name: "sellerenergy", caption: "??????? ", editorType: "TextEditor"	},
	            {	name: "coVersion", caption: "????", editorType: "TextEditor"	},
		        {	name: "isTax", caption: "????", editorType: "TextEditor"	}
				]
			}),
			
			columns:[
	        {	name: "marketid", caption: "??????????" , editorType: "TextEditor"	},
	        {	name: "contractid", caption: "??id" , editorType: "TextEditor"	},
	        {	name: "supercontractid", caption: "????id" , editorType: "TextEditor"	},
	        {	name: "contractname", caption: "????" , editorType: "TextEditor"	},
	        {	name: "contracttype", caption: "????" , editorType: "TextEditor"	},
	        {	name: "papercontractcode", caption: "?????????????" , editorType: "TextEditor"	},
	        {	name: "papercontractname", caption: "??????" , editorType: "TextEditor"	},
	        {	name: "contracttemplateid", caption: "?????????????" , editorType: "TextEditor"	},
	        {	name: "signstate", caption: "1???2??? " , editorType: "TextEditor"	},
	        {	name: "signeddate", caption: "?????? " , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "notsignedreason", caption: "??????? " , editorType: "TextEditor"	},
	        {	name: "signedperson", caption: "????? " , editorType: "TextEditor"	},
	        {	name: "signedsite", caption: "?????? " , editorType: "TextEditor"	},
	        {	name: "backupperson", caption: "?????" , editorType: "TextEditor"	},
	        {	name: "backupdate", caption: "??????" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "backupstate", caption: "????" , editorType: "TextEditor"	},
	        {	name: "backuporg", caption: "??????" , editorType: "TextEditor"	},
	        {	name: "disbackupreason", caption: "?????" , editorType: "TextEditor"	},
	        {	name: "isend", caption: "???? " , editorType: "TextEditor"	},
	        {	name: "prepcontractflag", caption: "????? " , editorType: "TextEditor"	},
	        {	name: "issecrecyflag", caption: "?????? " , editorType: "TextEditor"	},
	        {	name: "contractcyc", caption: "1??2??3??4??5??6?? " , editorType: "TextEditor"	},
	        {	name: "purchaser", caption: "???????? " , editorType: "TextEditor"	},
	        {	name: "seller", caption: "???????? " , editorType: "TextEditor"	},
	        {	name: "sellerunit", caption: "???? " , editorType: "TextEditor"	},
	        {	name: "purchaseunit", caption: "???? " , editorType: "TextEditor"	},
	        {	name: "contractqty", caption: "????" , editorType: "TextEditor"	},
	        {	name: "qtytype", caption: "???? " , editorType: "TextEditor"	},
	        {	name: "contractstartdate", caption: "?????? " , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "contractenddate", caption: "?????? " , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "transactionid", caption: "????? " , editorType: "TextEditor"	},
	        {	name: "transactiontype", caption: "?????? " , editorType: "TextEditor"	},
	        {	name: "rightsettlementside", caption: "1???2??? " , editorType: "TextEditor"	},
	        {	name: "isrepurchaseflag", caption: "?????? " , editorType: "TextEditor"	},
	        {	name: "replacetype", caption: "???? " , editorType: "TextEditor"	},
	        {	name: "areatype", caption: "???????? " , editorType: "TextEditor"	},
	        {	name: "isehvflag", caption: "???????? " , editorType: "TextEditor"	},
	        {	name: "isresaleflag", caption: "????????? " , editorType: "TextEditor"	},
	        {	name: "isopen", caption: "?????? " , editorType: "TextEditor"	},
	        {	name: "purchasegate", caption: "???? " , editorType: "TextEditor"	},
	        {	name: "settlegate", caption: "???? " , editorType: "TextEditor"	},
	        {	name: "lossundertaker", caption: "1???2??? " , editorType: "TextEditor"	},
	        {	name: "purchaserperson", caption: "?????? " , editorType: "TextEditor"	},
	        {	name: "purchaserphone", caption: "??????? " , editorType: "TextEditor"	},
	        {	name: "purchaserconftime", caption: "??????? " , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "sellerperson", caption: "?????? " , editorType: "TextEditor"	},
	        {	name: "sellerphone", caption: "??????? " , editorType: "TextEditor"	},
	        {	name: "sellerconftime", caption: "???????" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "transperson", caption: "??????" , editorType: "TextEditor"	},
	        {	name: "transphone", caption: "???????" , editorType: "TextEditor"	},
	        {	name: "transconftime", caption: "???????" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "changetimes", caption: "???? " , editorType: "TextEditor"	},
	        {	name: "description", caption: "?? " , editorType: "TextEditor"	},
	        {	name: "isdelete", caption: "???? " , editorType: "TextEditor"	},
	        {	name: "updatetime", caption: "?????? " , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "updatepersonid", caption: "??????? " , editorType: "TextEditor"	},
	        {	name: "versionid", caption: "??ID " , editorType: "TextEditor"	},
	        {	name: "version", caption: "??? " , editorType: "TextEditor"	},
	        {	name: "versiondesc", caption: "???? " , editorType: "TextEditor"	},
	        {	name: "isrelation", caption: "?????????????????????? " , editorType: "TextEditor"	},
	        {	name: "exectype", caption: "??1???????2?????? " , editorType: "TextEditor"	},
	        {	name: "flow", caption: "1??????2?????????3?????4?????5??????????? " , editorType: "TextEditor"	},
	        {	name: "settleside", caption: "1????2???3?? " , editorType: "TextEditor"	},
	        {	name: "businessid", caption: "????id" , editorType: "TextEditor"	},
	        {	name: "flowflag", caption: "????" , editorType: "TextEditor"	},
	        {	name: "sequenceid", caption: "????id" , editorType: "TextEditor"	},
	        {	name: "energy", caption: "??" , editorType: "TextEditor"	},
	        {	name: "contractprice", caption: "????" , editorType: "TextEditor"	},
	        {	name: "contractenergy", caption: "????" , editorType: "TextEditor"	},
	        {	name: "superexecid", caption: "?????ID" , editorType: "TextEditor"	},
	        {	name: "ingodown", caption: "????" , editorType: "TextEditor"	},
	        {	name: "expend1", caption: "??1" , editorType: "TextEditor"	},
	        {	name: "expend2", caption: "??2" , editorType: "TextEditor"	},
	        {	name: "expend3", caption: "??3" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "expend4", caption: "??4" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "expend5", caption: "??5" , editorType: "TextEditor"	},
	        {	name: "expend6", caption: "??6" , editorType: "TextEditor"	},
	        {	name: "purchasergen", caption: "??????" , editorType: "TextEditor"	},
	        {	name: "purchaserenergy", caption: "??????? " , editorType: "TextEditor"	},
	        {	name: "sellergen", caption: "??????" , editorType: "TextEditor"	},
	        {	name: "sellerenergy", caption: "??????? " , editorType: "TextEditor"	},
	        {	name: "coVersion", caption: "????" , editorType: "TextEditor"	},
	        {	name: "isTax", caption: "????" , editorType: "TextEditor"	}
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