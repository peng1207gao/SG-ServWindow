$ns("contractVersionManage.views");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGridSearchBox");
$import("mx.datacontrols.ComplexGrid");
$import("mx.containers.Panel");
$import("mx.datacontrols.DataGrid");
$import("mx.controls.Label");
$import("mx.editors.DropDownEditor");
$import("mx.editors.TextEditor");
$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.ComplexGrid");

//$import("utils.dropDownEditor.contractTypeTree");
//$import("utils.dropDownEditor.contractSeqInfo");
//$import("utils.dropDownEditor.marketMemberType");
//$import("utils.dropDownEditor.marketTree");

contractVersionManage.views.ContractVersionMainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    //网格列表
    var _dataGrid = null;
    //合同历史版本信息列表
    var hisVersionGrid = null;
    //表单窗口对象
    var _detailWin = null;
    
    var labelWidth = "8%";
    
    var comboWidth = "12%";

    base.init = me.init;
    me.init = function()
    {
        base.init();
        _initControls();
    };
    
    function _initControls()
    {
    	//_initWindowSplit();
    	_initTopControls();//查询条件面板
    	_initDownControls();//按钮面板

    	_initDownSplit();
    	_initDownPanel();
    	_initDataGrid();
		_initHistoryVersionGrid();
		hisVersionGrid.pageNaviBar.setDisplay("none");//不显示 底端表格的分页控件
		_initDetailWindow();
        me.on("activate", me.controller._onactivate);
    }
    
     //将页面划分为两部分
    function _initWindowSplit() {
		me.mainSplit = new mx.containers.HSplit({
			rows : "100,auto",width:"100%",
			borderThick : 1
		});
		me.addControl(me.mainSplit);		
	}
	
	/**
     * 进行查询条件页面布局
     */
    function _initTopControls() {
    	me.panel = new mx.containers.Panel({ name:"panel", title:"业务查询", width:"100%", height: 50}); 
		me.panel1 = new mx.containers.Panel({ name:"panel", title:"业务操作", width:"100%", height: 50 }); 
		me.addControl(me.panel);
//		me.mainSplit.addControl(me.panel,0);
//		me.mainSplit.addControl(me.panel,0);
		me.addControl(me.panel1);
		
    	me.condContainer = new mx.containers.Container({
			padding:"2px",height:"30"
		});
		me.condContainer1 = new mx.containers.Container({
			padding:"2px",height:"30"
		});
		me.condContainer2 = new mx.containers.Container({
			padding:"2px",height:"30"
		});
		me.condContainer3 = new mx.containers.Container({
			padding:"2px",height:"30"
		});
		me.condContainer4 = new mx.containers.Container({
			padding:"2px",height:"30"
		});
		me.condContainer1.setDisplay("none");
		me.condContainer2.setDisplay("none");
		me.condContainer3.setDisplay("none");
		me.panel.addControl(me.condContainer);
		me.panel.addControl(me.condContainer1);
		me.panel.addControl(me.condContainer2);
//		me.panel.addControl(me.condContainer3);
		
		_initCondContainer1();
		_initCondContainer2();
	}
	
	function _initCondContainer1(){
		me.label1 = new mx.controls.Label({
			text : "业务场景：",
			textAlign : "center",
			verticalAlign : "middle",
			"width" : labelWidth
		});
		
		me.marketDropEditor  = utils.dropDownEditor.MarketTree.MarketDropDownEditor(true,"",false);
		me.marketDropEditor.setWidth(comboWidth);
    	
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

		me.label3 = new mx.controls.Label({
			text : "时间类型：",
			textAlign : "center",
			verticalAlign : "middle",
			"width" : labelWidth
		});
		
		me.timetypeDropEditor = new mx.editors.DropDownEditor(
		{
			displayMember: "name",
		    valueMember: "value",
			allowDropDown : true,
		    url: IdeaConManage.mappath("~/../ETradePublicUtils/rest/baGencodeTypeList/getList?type=93")
		});
		me.timetypeDropEditor.setValue("0","合同开始时间");//初始化默认选中值
		me.timetypeDropEditor.setWidth(comboWidth);

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
		
		me.condContainer.addControl(me.label1);
		me.condContainer.addControl(me.marketDropEditor);
		me.condContainer.addControl(me.label2);
		me.condContainer.addControl(me.contracttypeDropEditor);
		me.condContainer1.addControl(me.label3);
		me.condContainer1.addControl(me.timetypeDropEditor);
		me.condContainer1.addControl(me.label4);
		me.condContainer1.addControl(me.starttimeEditor);
		me.condContainer1.addControl(me.label5);
		me.condContainer1.addControl(me.endtimeEditor);
		
	}
	
	
	function _initCondContainer2(){
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
		    url: IdeaConManage.mappath("~/../ETradePublicUtils/rest/baGencodeTypeList/getList?type=23")
		});
		me.contractCycDropEditor.setWidth(comboWidth);

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
		    url: IdeaConManage.mappath("~/../ETradePublicUtils/rest/baGencodeTypeList/getList?type=67")
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
		utils.dropDownEditor.CommonDropEditor.autoInit(me.purchaseDropEditor,'1');//下拉框初始化值
		
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
		utils.dropDownEditor.CommonDropEditor.autoInit(me.sellerDropEditor,'2');//下拉框初始化值
		
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

		me.condContainer.addControl(me.label6);
		me.condContainer.addControl(me.contractCycDropEditor);
		me.condContainer1.addControl(me.label7);
		me.condContainer1.addControl(me.contractstateDropEditor);
		me.condContainer2.addControl(me.label8);
		me.condContainer2.addControl(me.purchaseDropEditor);
		me.condContainer2.addControl(me.label9);
		me.condContainer2.addControl(me.sellerDropEditor);
		me.condContainer.addControl(me.label10);
		me.condContainer.addControl(me.contractserialDropEditor);
		
		me.buttontest = new mx.controls.Button({text:"高级查询",left:10});	//查询合同按钮
    	me.condContainer.addControl(me.buttontest);	//添加按钮
    	
		me.buttontest.on("click",function(){
    		//me.mSplit.rows = "230,auto";
    		if(me.buttontest.text == "高级查询"){
    			var height = parseInt(me.panel.height)+30*2;
        		me.panel.setHeight(height);
        		me.condContainer1.setDisplay("block");
        		me.condContainer2.setDisplay("block");
//        		me.condContainer3.setDisplay("block");
        		me.buttontest.setText("收起");
    		}else if(me.buttontest.text == "收起"){
    			var height = parseInt(me.panel.height)-30*2;
        		me.panel.setHeight(height);
        		me.condContainer1.setDisplay("none");
        		me.condContainer2.setDisplay("none");
//        		me.condContainer3.setDisplay("none");
        		me.buttontest.setText("高级查询");
    		}
    		
    	});
	}
	
	function _initDownControls() {
		
		var textContainer1 = new mx.containers.Container({
			"height" : "100%",
			padding:"2px"
		});
		me.panel1.addControl(textContainer1);
		
		me.queryBtn = new mx.controls.Button({text : "查询"});
		me.queryBtn.on("click", me.selectData);
		textContainer1.addControl(me.queryBtn);
		
		me.versionCompareBtn = new mx.controls.Button({ text: "合同历史版本对比",left:30 });//合同关联维护按钮
		me.versionCompareBtn.on("click", function(){
	    	var selectedItem = _dataGrid.selection;
	    	
	    	if(selectedItem == null){
				mx.indicate("info", "请选择一条记录");
				return;
			}
	    	me.contractid = selectedItem.values["contractid"];
	    	me.controller.compareContractaVersion();
	    	
		});
		textContainer1.addControl(me.versionCompareBtn);
		
	}
	
	function _contractName_cellRender(p_item, $p_cell){
		$("<a href='#' style='text-decoration:none; color: blue;'>"+p_item.values["contractname"]+"</a>").appendTo($p_cell).on("click",
			function(){
				me.controller._showContractAddView(p_item);
			}).css("cursor","hand");
	}
	
	function _initDownSplit() {
		me.downSplit = new mx.containers.HSplit({
			rows : "auto,130",width:"100%",
			borderThick : 1
		});
		me.addControl(me.downSplit);
//		me.mainSplit.addControl(me.downSplit,1);
	}
	
	function _initDownPanel(){
		me.contractListPanel = new mx.containers.Panel({ name:"panel", width:"100%", height: "100%" });
		me.historyVersionGridPanel = new mx.containers.Panel({ name:"panel",title:"合同历史版本信息", width:"100%", height: "100%" });
		me.downSplit.addControl(me.contractListPanel,0);
		me.downSplit.addControl(me.historyVersionGridPanel,1);
	}
	
    
    function _initDataGrid()
    {
        var restUrl = "~/rest/contractVerion/";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : contractVersionManage.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "contractid"
        });
        
        /* 初始化 DataGrid */
        _dataGrid = new mx.datacontrols.ComplexGrid({   
			// 构造查询属性。
			alias: "contractVersionManageContractVersionMainViewDataGrid",
			
			columns:[
			{   id:"radio",name:"mainRadio",renderCell: me._radioRender,width:"30"},
	        {	name: "contractid", caption: "contractid" , editorType: "TextEditor",align:"center",dataAlign:"center"},
//	        {	name: "rownum", caption: "序号" , editorType: "TextEditor"	},
	        {	name: "papercontractcode", caption: "主合同编号" , editorType: "TextEditor", width:"150",align:"center",dataAlign:"center"	},
	        {	name: "contractname", caption: "合同名称" , editorType: "TextEditor",align:"center",dataAlign:"center", renderCell :_contractName_cellRender, width:"200"},
	        {	name: "contracttype", caption: "合同类型" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
	        {	name: "purchaser", caption: "购电方" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
	        {	name: "seller", caption: "售电方" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
			{	name: "contractstartdate", caption: "开始日期" , editorType: "DateTimeEditor",align:"center",dataAlign:"center", formatString: "yyyy-MM-dd", width:"90"},
	        {	name: "contractenddate", caption: "截止日期" , editorType: "DateTimeEditor",align:"center",dataAlign:"center", formatString: "yyyy-MM-dd", width:"90"},
	        {	name: "energy", caption: "总电量" , editorType: "TextEditor",width:"100",align:"center",dataAlign:"center"	},
	        {	name: "contractprice", caption: "单价" , editorType: "TextEditor", width:"80",align:"center",dataAlign:"center"	},
//	        {	name: "backupstate", caption: "备案状态" , editorType: "TextEditor", width:"60",align:"center",dataAlign:"center"	},
//	        {	name: "signstate", caption: "签订状态" , editorType: "TextEditor", width:"60",align:"center",dataAlign:"center"	},
//	        {	name: "flowstate", caption: "流转状态" , editorType: "TextEditor", width:"60",align:"center",dataAlign:"center"	},
	        {	name: "contractstate", caption: "合同状态", editorType: "TextEditor", width:"60",align:"center",dataAlign:"center"},
		    {	name: "maintainperson", caption: "合同更新人", editorType: "TextEditor",align:"center",dataAlign:"center"},
		    {	name: "maintaintime", caption: "维护时间", editorType: "DateTimeEditor",align:"center",dataAlign:"center", formatString: "yyyy-MM-dd", width:"90"},
		    {	name: "maintainunit", caption: "维护单位", editorType: "TextEditor",align:"center",dataAlign:"center"}
//	        {	name: "marketid", caption: "业务场景id" , editorType: "TextEditor",align:"center",dataAlign:"center" }
            ],
            // 构造列排序条件，如果有多列，则以逗号分隔。例sorter: "school ASC, class DESC"
            displayCheckBox: false,
	        displayPrimaryKey:false,//列表是否显示主键
            allowEditing: false, //列表默认不可编辑
	        pageSize : 20,
            entityContainer: gridEntityContainer,
            displayToolBar:false,
            onselectionchanged:me._radioSelected,
            displayColSplitLine:true,//表格分割线
            autoWrap:true,
            onload:changeHead
        });
        me.contractListPanel.addControl(_dataGrid);
    }
    
    
    function _initHistoryVersionGrid()
    {
        var restUrl = "~/rest/contractVerion/getHistoryVersion";
        /* 初始化 EntityContainer */
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : contractVersionManage.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "contractid"
        });
        
        /* 初始化 hisVersionGrid */
        hisVersionGrid = new mx.datacontrols.ComplexGrid({   
			// 构造查询属性。
			alias: "hisVersionManageContractVersionMainViewDataGrid",
//			co.contractID,co.paperContractCode,co.contractName,co.version,co.updateTime,pu.user_name,co.description
			columns:[
	        {	name: "contractid", caption: "contractid" , editorType: "TextEditor",align:"center",dataAlign:"center"},
//	        {	name: "rownum", caption: "序号" , editorType: "TextEditor"	},
	        {	name: "papercontractcode", caption: "主合同编号" , editorType: "TextEditor", width:"150",align:"center",dataAlign:"center"	},
	        {	name: "contractname", caption: "合同名称" , editorType: "TextEditor",align:"center",dataAlign:"center", renderCell :_contractName_cellRender, width:"300"},
	        {	name: "version", caption: "历史版本信息" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
	        {	name: "updatetime", caption: "信息更新时间" , editorType: "TextEditor",align:"center",dataAlign:"center",
	        	editorType: "DateTimeEditor",formatString: "yyyy-MM-dd HH:mm:ss", width:"150"},
	        {	name: "updateperson", caption: "变更人" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
	        {	name: "description", caption: "变更说明" , editorType: "TextEditor",align:"center",dataAlign:"center"	}
            ],
            displayCheckBox: false,
	        displayPrimaryKey:false,//列表是否显示主键
            allowEditing: false, //列表默认不可编辑
	        pageSize : 20,
            entityContainer: gridEntityContainer,
            displayToolBar:false,
            displayColSplitLine:true,//表格分割线
            autoWrap:true
        });
        me.historyVersionGridPanel.addControl(hisVersionGrid);
    }
    
    function changeHead(){
    	var contracttype = isNull(me.contracttypeDropEditor.getValue());
    	//合同类型为发电权合同时 改表头名称
    	var restClient = new mx.rpc.RESTClient();
    	var result = restClient.getSync(contractVersionManage.mappath("~/rest/coContractbaseinfoV2Act/isfdqht"),{"params":JSON.stringify({"contracttype":contracttype})});
    	if(result==true){
    		_dataGrid.columns.purchaser.setCaption("被替代方");
    		_dataGrid.columns.seller.setCaption("替代方");
    	}
    }
    
    /**
     * 创建radio表单
     */
	me._radioRender = function(p_item, $p_cell)
	{	
		var contractid = p_item.values.contractid;
		$("<input type='radio' id='radio"+contractid+"' name='mainRadio' value='1'>").appendTo($p_cell).on("click",
    			function(){   					
    					me._radioSelected(me);
				}).css("cursor","hand"); 
	}
	
	/**
	 * 点击每行数据的时候，radio设置为选中效果
	 */
    me._radioSelected = function(e)
	{	
		if(typeof(e.target) =="undefined" || e.target.selection == null){
			return;
		} else {
			var contractid = e.target.selection.values.contractid;
			$("input[type=radio ][id='radio"+contractid+"']").attr("checked","checked");

			var params = {"contractid":contractid};
			hisVersionGrid.setFilter(JSON.stringify(params));
			hisVersionGrid.load();
		}
		
	};
	
	me.selectData=function(){

		var marketid = isNull(me.marketDropEditor.getValue());
		var contracttype = isNull(me.contracttypeDropEditor.getValue());
		var timetype = isNull(me.timetypeDropEditor.getValue());
		var contractstartdate = isNull(me.starttimeEditor.getValue());
		var contractenddate = isNull(me.endtimeEditor.getValue());
		
		var contractcyc = isNull(me.contractCycDropEditor.getValue());
		var prepcontractflag = isNull(me.contractstateDropEditor.getValue());//合同状态
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
		
		var params = {"marketid":marketid,"contracttype":contracttype,"timetype":timetype,
			"contractstartdate":contractstartdate,"contractenddate":contractenddate,
			"contractcyc":contractcyc,
			"prepcontractflag":prepcontractflag,
			"purchaser":purchaser,
			"seller":seller,
	    	"sequenceid":sequenceid
		}
    	_dataGrid.setFilter(params);
	    _dataGrid.load({dataParams:{pageIndex:_dataGrid.pageIndex,pageSize:_dataGrid.pageSize}});
    }
    
    function isNull(obj){
    	if(obj != null && obj.length != 0  && "null" != obj){
    		return obj;
    	} else {
    		return "";
    	}
    }
    
    /**
     * 初始化表单视图窗口对象
     */
    function _initDetailWindow(){
    	_detailWin = utils.commonFun.WinMananger.create({
			reusable: true,//是否复用
			width:"100%",
			height:"100%",
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