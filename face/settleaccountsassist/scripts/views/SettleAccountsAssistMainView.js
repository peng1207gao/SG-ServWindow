$ns("settleaccountsassist.views");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGridSearchBox");
$import("mx.datacontrols.DataGrid");
$import("mx.containers.Panel");


settleaccountsassist.views.SettleAccountsAssistMainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    //网格列表
    var _dataGrid = null;
    //表单窗口对象
    var _detailWin = null;
    
    var labelWidth = "8%";
    var comboWidth = "12%";
    var marketId;
    base.init = me.init;
    me.init = function()
    {
        base.init();
        _initControls();
    };
    
    function _initControls()
    {
    	_initWindowSplit();//整个界面布局
    	_initTopControls();
    	_initDownControls();

//    	_initDownSplit();
	    _initDataGrid();
    	_initDetailWindow();
        me.on("activate", me.controller._onactivate);
    }
    
     //将页面划分为两部分
    function _initWindowSplit() {
		me.mainSplit = new mx.containers.HSplit({
			rows : "133,auto",width:"100%",
			borderThick : 1
		});
		me.addControl(me.mainSplit);		
	}
	
	/**
     * 进行查询条件页面布局
     */
    function _initTopControls() {
    	me.panel = new mx.containers.Panel({ name:"panel", title:"业务查询", width:"100%", height: 80}); 
		me.panel1 = new mx.containers.Panel({ name:"panel", title:"业务操作", width:"100%", height: 50 }); 
		me.mainSplit.addControl(me.panel,0);
		me.mainSplit.addControl(me.panel1,0);
		
    	me.condContainer = new mx.containers.Container({
			"height" : "50%",
			padding:"2px"
		});
		me.condContainer1 = new mx.containers.Container({
			"height" : "50%",
			padding:"2px"
		});
		me.panel.addControl(me.condContainer);
		me.panel.addControl(me.condContainer1);
		
		_initCondContainer1();
		_initCondContainer2();
    	
	}
	
	function _initDownControls() {
		
		var textContainer1 = new mx.containers.Container({
			"height" : "100%",
			padding:"2px"
		});
		me.panel1.addControl(textContainer1);
		
		me.queryBtn = new mx.controls.Button({text : "查询"});
		me.queryBtn.on("click", me.selectData);
		
		me.maintainBtn = new mx.controls.Button({ text: "分解电量到经济机组",left:30 });
		me.maintainBtn.on("click", me.controller._showResolveElectricView);
		
		me.explainBtn = new mx.controls.Button({ text: "功能说明",left:60 });//功能说明按钮
		me.explainBtn.on("click", me.functionExplain);

		textContainer1.addControl(me.queryBtn);
//		textContainer1.addControl(me.maintainBtn);
//		textContainer1.addControl(me.explainBtn);
	}
	
	me.functionExplain = function(){
		var message = "一、电量分解到经济机组分解规则"+ "\n" +
						"*  1、通过合同id找到合同的购电方和售电方"+ "\n" +
						"*  2、通过市场成员找到经济机组"+ "\n" +
						"*  3、判断该合同开始时间与结束时间是否正确"+ "\n" +
						"*  4、判断该合同是否完成按月分解电量"+ "\n" +
						"*  5、判断该合同是否完成分段电量维护"+ "\n" +
						"*  6、获取经济机组权益容量"+ "\n" +
						"*  7、按照如下公式分解电量到经济机组"+ "\n" +
						"	电量 = A1/A  * B1/B * 总电量（尖峰电量，峰电量，平电量，谷电量）"+ "\n" +
						"	购方权益容量总和 A"+ "\n" +
						"	售方权益容量总和 B"+ "\n" +
						"	购方其中一个经济机组权益容量 A1"+ "\n" +
						"	售方其中一个经济机组权益容量 B1"+ "\n" +
						"	总电量为分段电量电价维护界面每月总电量，尖峰电量，峰电量，平电量，谷电量"+ "\n" +
						"二、电量分解到经济机组操作方法"+ "\n" +
						"*  1、通过查询条件筛选出需要进行分解的合同"+ "\n" +
						"*  2、选择一条或者多条合同点击分解电量到经济机组按钮，进入电量分解界面"+ "\n" +
						"*  3、点击指定经济机组按钮，进入经济机组选择界面"+ "\n" +
						"*  4、指定经济机组，点击确定按钮，返回电量分解界面"+ "\n" +
						"*  5、点击分解按钮，将电量分解到指定的经济机组中"+ "\n";
		
        var $container = $(document.body);

        if ($container == null) return;

        if (__$indication == null)
        {
            __$indication = $("<div id='indication' class='mx'><div id='icon' onclick='__$indication.fadeOut(500)'/><pre id='text' STYLE='text-align:left'/></pre>");
            __$indication.css("minWidth", 140);
            __$indication.css("maxWidth", "90%");
            __$indication.hide();
            __$indication.borderRadius(12);
        }
        $container.append(__$indication);

        var symbol = mx.utils.SymbolUtil.getSymbol("info");
        if (symbol != null)
        {
            __$indication.children("#icon").show();
            __$indication.children("#icon").css("font-size", "16px");
            __$indication.children("#icon").text("关闭");
        }
        else
        {
            __$indication.children("#icon").hide();
        }
        
        __$indication.children("#text").text(message);
        __$indication.css("left", 0);
        __$indication.css("top", 0);
        __$indication.centralize();
        
        __$indication.stop(true, true);
//        __$indication.show().delay(2000).fadeOut(1200);
        __$indication.show();
	}
	
//	function _initDownSplit() {
//		me.downPanel = new mx.containers.Panel({ name:"panel", width:"100%", height: "100%" });
//		me.mainSplit.addControl(me.downPanel,1);
//	}
	
	function _initCondContainer1(){
		me.label2 = new mx.controls.Label({
			text : "合同类型：",
			textAlign : "center",
			verticalAlign : "middle",
			"width" : labelWidth
		});
		
		var isFilter = false;
		var isdisplay = true;
		
		me.contracttypeDropEditor = utils.dropDownEditor.ContractTypeTree.ContractTypeDropDownEditor(isFilter,isdisplay);
		me.contracttypeDropEditor.setWidth(comboWidth);

		me.label4 = new mx.controls.Label({
			text : "开始时间：",
			textAlign : "center",
			verticalAlign : "middle",
			"width" : labelWidth
		});
		
		me.starttimeEditor = new mx.editors.DateTimeEditor({
    		formatString: "yyyy-MM-dd"
		});
		me.starttimeEditor.setWidth(comboWidth);
		
		me.label5 = new mx.controls.Label({
			text : "截止时间：",
			textAlign : "center",
			verticalAlign : "middle",
			"width" : labelWidth
		});
		
		me.endtimeEditor = new mx.editors.DateTimeEditor({
    		formatString: "yyyy-MM-dd"
		});
		me.endtimeEditor.setWidth(comboWidth);
		
		me.label6 = new mx.controls.Label({
			text : "合同周期：",
			textAlign : "center",
			verticalAlign : "middle",
			"width" : labelWidth
		});
		
		me.contractCycDropEditor = new mx.editors.DropDownEditor(
		{
			displayMember: "name",
		    valueMember: "value",
		    url: settleaccountsassist.mappath("~/../ETradePublicUtils/rest/baGencodeTypeList/getList?type=23")
		});
		me.contractCycDropEditor.setWidth(comboWidth);
		
		me.label11 = new mx.controls.Label({
			text : "发电类型：",
			textAlign : "center",
			verticalAlign : "middle",
			"width" : labelWidth
		});
		
		me.powerTypeDropEditor = new mx.editors.DropDownEditor(
		{
			displayMember: "name",
		    valueMember: "value",
		    url: settleaccountsassist.mappath("~/../ETradePublicUtils/rest/baGencodeTypeList/getList?type=13")
		});
		me.powerTypeDropEditor.setWidth(comboWidth);
		
		me.condContainer.addControl(me.label2);
		me.condContainer.addControl(me.contracttypeDropEditor);
		me.condContainer.addControl(me.label6);
		me.condContainer.addControl(me.contractCycDropEditor);
		me.condContainer.addControl(me.label11);
		me.condContainer.addControl(me.powerTypeDropEditor);
		me.condContainer.addControl(me.label4);
		me.condContainer.addControl(me.starttimeEditor);
		me.condContainer.addControl(me.label5);
		me.condContainer.addControl(me.endtimeEditor);
	}
	
	function _initCondContainer2(){
		me.label7 = new mx.controls.Label({
			text : "合同状态：",
			textAlign : "center",
			verticalAlign : "middle",
			"width" : labelWidth
		});
		
		me.contractstateDropEditor = new mx.editors.DropDownEditor(
		{
			displayMember: "name",
		    valueMember: "value",
		    url: settleaccountsassist.mappath("~/../ETradePublicUtils/rest/baGencodeTypeList/getList?type=67")
		});
		me.contractstateDropEditor.setWidth(comboWidth);
		
		me.label8= new mx.controls.Label({
			text : "购电方：",
			textAlign : "center",
			verticalAlign : "middle",
			"width" : labelWidth
		});
		
		me.purchaseDropEditor = utils.dropDownEditor.CommonDropEditor.gsAutoComPleteEditor("","1");
		me.purchaseDropEditor.setWidth(comboWidth);
		utils.dropDownEditor.CommonDropEditor.autoInit(me.purchaseDropEditor, '1');//下拉框初始化值
 		var fun = new utils.commonFun.DropDownEditorFun();
		fun.resizeAutocomplete(me.purchaseDropEditor);
		
		me.label9 = new mx.controls.Label({
			text : "售电方：",
			textAlign : "center",
			verticalAlign : "middle",
			"width" : labelWidth
		});
		
		me.sellerDropEditor = utils.dropDownEditor.CommonDropEditor.gsAutoComPleteEditor("","2");
		me.sellerDropEditor.setWidth(comboWidth);
		utils.dropDownEditor.CommonDropEditor.autoInit(me.sellerDropEditor, '2');//下拉框初始化值
		fun.resizeAutocomplete(me.sellerDropEditor);
		
		me.label10= new mx.controls.Label({
			text : "合同序列：",
			textAlign : "center",
			verticalAlign : "middle",
			"width" : labelWidth
		});
		me.contractserialDropEditor = utils.dropDownEditor.ContractSeqInfo.ContractSeqInfoDropDownEditor(false,"");
		me.contractserialDropEditor.setWidth(comboWidth);
		
		fun.resizeListEditor(me.contractserialDropEditor);

		me.condContainer1.addControl(me.label7);
		me.condContainer1.addControl(me.contractstateDropEditor);
		me.condContainer1.addControl(me.label8);
		me.condContainer1.addControl(me.purchaseDropEditor);
		me.condContainer1.addControl(me.label9);
		me.condContainer1.addControl(me.sellerDropEditor);
		me.condContainer1.addControl(me.label10);
		me.condContainer1.addControl(me.contractserialDropEditor);
	}
    
    me.getUserInfo = function()
    {
    	me.restClient = new mx.rpc.RESTClient();
    	var respData = me.restClient.getSync(settleaccountsassist.mappath("~/rest/cocontractbaseinfo/getUserInfo"));
    	return respData;
    }
    
    function isNull(obj){
    	if(obj != null && obj.length != 0  && "null" != obj){
    		return obj;
    	} else {
    		return "";
    	}
    }
    
   	me.selectData = function(){
	   	//获得登录用户场景id
   		var userInfo = me.getUserInfo();
   		if(typeof(userInfo.resultValue) == "undefined"){
   			mx.indicate("info","用户登录超时");
   			return;
   		}
	   	var marketid = isNull(userInfo.resultValue.items[0].marketid);//当前用户的marketid
		var contracttype = isNull(me.contracttypeDropEditor.getValue());
		var contractstartdate = isNull(me.starttimeEditor.getValue());
		var contractenddate = isNull(me.endtimeEditor.getValue());
		var powertype = isNull(me.powerTypeDropEditor.getValue());
		
		var contractcyc = isNull(me.contractCycDropEditor.getValue());
		var signstate = isNull(me.contractstateDropEditor.getValue());//合同状态
		var purchaser = isNull(me.purchaseDropEditor.getValue());
		var seller = isNull(me.sellerDropEditor.getValue());
		var sequenceid = isNull(me.contractserialDropEditor.getValue());
		
		if(contractstartdate.length != 0 && contractenddate.length!=0){//校验开始时间和截止时间
			var startdate = new Date(contractstartdate);
			var enddate = new Date(contractenddate);
			
			var start = startdate.getTime();
			var end = enddate.getTime();
			if(start > end){
				mx.indicate("info","开始时间晚于截止时间！");
				return;
			}
		}
		
		var params = {"marketid":marketid,"contracttype":contracttype,
			"contractstartdate":contractstartdate,"contractenddate":contractenddate,
			"contractcyc":contractcyc,
			"signstate":signstate,
			"purchaser":purchaser,
			"seller":seller,
			"powertype":powertype,
	    	"sequenceid":sequenceid
		}
    	_dataGrid.setFilter(params);
	    _dataGrid.load();
    }
    
    function _initDataGrid()
    {
        var restUrl = "~/rest/settleAccountsAssist/";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : settleaccountsassist.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "contractid"
        });
        
        /* 初始化 DataGrid */
        _dataGrid = new mx.datacontrols.DataGrid({   
			// 构造查询属性。
			alias: "settleMainViewDataGrid",
			columns:[
	        {	name: "contractid", caption: "合同ID" , editorType: "TextEditor" , align:"center",dataAlign:"center"	},
	        {	name: "papercontractcode", caption: "主合同编号" , editorType: "TextEditor" , align:"center",dataAlign:"center"	},
	        {	name: "contractname", caption: "合同名称" , editorType: "TextEditor" , align:"center",dataAlign:"center",width:"250"},
	        {	name: "contracttype", caption: "合同类型" , editorType: "TextEditor" , align:"center",dataAlign:"center",width:"200"	},
	        {	name: "purchaser", caption: "购电方" , editorType: "TextEditor" , align:"center",dataAlign:"center",width:"200"	},
	        {	name: "seller", caption: "售电方" , editorType: "TextEditor" , align:"center",dataAlign:"center",width:"200"	},
	        {	name: "contractstartdate", caption: "开始时间" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd" , align:"center",dataAlign:"center"},
	        {	name: "contractenddate", caption: "截止时间" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd" , align:"center",dataAlign:"center"},
	        {	name: "energy", caption: "总电量" , editorType: "TextEditor" , align:"center",dataAlign:"center"	}
            ],
            displayCheckBox: true,
	        displayPrimaryKey:false,//列表是否显示主键
            allowEditing: false, //列表默认不可编辑
            displayRowNumber:true,
	        pageSize : 20,
            entityContainer: gridEntityContainer,
            onload:changeHead
        });
//        me.downPanel.addControl(_dataGrid);
        me.mainSplit.addControl(_dataGrid,1);
    }
    
    function changeHead(){
    	var contracttype = isNull(me.contracttypeDropEditor.getValue());
    	//合同类型为发电权合同时 改表头名称
    	var restClient = new mx.rpc.RESTClient();
    	var result = restClient.getSync(settleaccountsassist.mappath("~/rest/coContractbaseinfoV2Act/isfdqht"),{"params":JSON.stringify({"contracttype":contracttype})});
    	if(result==true){
    		_dataGrid.columns.purchaser.setCaption("被替代方");
    		_dataGrid.columns.seller.setCaption("替代方");
    	}
    }
    
    /**
     * 初始化表单视图窗口对象
     */
    function _initDetailWindow(){
    	_detailWin = new utils.commonFun.WinMananger.create({
			reusable: true,//是否复用
			width:"100%",
			height:"100%"
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