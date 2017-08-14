$ns("multiLevelContractManage.views");

$import("mx.containers.Container");
$import("mx.containers.Panel");
$import("mx.datacontrols.DataGrid");
$import("mx.controls.Label");
$import("mx.editors.DropDownEditor");
$import("mx.editors.TextEditor");
$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGridSearchBox");
$import("mx.datacontrols.ComplexGrid");

//$import("utils.dropDownEditor.contractTypeTree");
//$import("utils.dropDownEditor.contractSeqInfo");
//$import("utils.dropDownEditor.marketMemberType");
//$import("utils.dropDownEditor.MarketTree");
//
//$import("IdeaConManage.views.IdeaConRelateViewController");


multiLevelContractManage.views.MultiLevelMainView = function()
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
    	me.restClient = new mx.rpc.RESTClient();//初始化 restClient
        base.init();
        _initControls();
    };
    
    function _initControls()
    {
//    	_initWindowSplit();
    	_initTopControls();
    	_initDownControls();

    	_initDownSplit();
    	_initTreePanel();
    	_initDataGrid();
    	_initDownPanel();
        me.on("activate", me.controller._onactivate);
    }
    
      //将页面划分为两部分
//  function _initWindowSplit() {
//		me.mainSplit = new mx.containers.HSplit({
//			rows : "133,auto",width:"100%",
//			borderThick : 1
//		});
//		me.addControl(me.mainSplit);		
//	}
	
	/**
     * 进行查询条件页面布局
     */
    function _initTopControls() {
    	me.panel = new mx.containers.Panel({ name:"panel", title:"业务查询", width:"100%", height: 80}); 
		me.panel1 = new mx.containers.Panel({ name:"panel", title:"业务操作", width:"100%", height: 50 }); 
//		me.mainSplit.addControl(me.panel,0);
//		me.mainSplit.addControl(me.panel1,0);
		me.addControl(me.panel);
		me.addControl(me.panel1);
		
		me.condContainer = new mx.containers.Container({
			"height" : "30",
			padding:"2px"
		});
		me.condContainer1 = new mx.containers.Container({
			"height" : "30",
			padding:"2px"
		});
		me.panel.addControl(me.condContainer);
		me.panel.addControl(me.condContainer1);
		
		_initCondContainer1();
		_initCondContainer2();
    	
	}
	
	function _initCondContainer1(){
		me.label1 = new mx.controls.Label({
			text : "业务场景：",
			textAlign : "center",
			verticalAlign : "middle",
			"width" : "8%"
		});
		
		me.marketDropEditor  = utils.dropDownEditor.MarketTree.MarketDropDownEditor(true,"",false);
		me.marketDropEditor.setWidth("12%");
    	
		me.label2 = new mx.controls.Label({
			text : "合同类型：",
			textAlign : "center",
			verticalAlign : "middle",
			"width" : "8%"
		});
		
		var isFilter = false;
		var isdisplay = true;
		
		me.contracttypeDropEditor = utils.dropDownEditor.ContractTypeTree.ContractTypeDropDownEditor(isFilter,isdisplay);
		me.contracttypeDropEditor.setWidth("12%");

//		me.label3 = new mx.controls.Label({
//			text : "时间类型：",
//			textAlign : "center",
//			verticalAlign : "middle",
//			"width" : "8%"
//		});
//		
//		me.timetypeDropEditor = new mx.editors.DropDownEditor(
//		{
//			displayMember: "name",
//		    valueMember: "value",
//			allowDropDown : true,
//		    url: multiLevelContractManage.mappath("~/../ETradePublicUtils/rest/baGencodeTypeList/getList?type=93"),
//		    width:"12%"
//		});
//		me.timetypeDropEditor.setValue("0","合同开始时间");//初始化默认选中值

		me.label4 = new mx.controls.Label({
			text : "开始时间：",
			textAlign : "center",
			verticalAlign : "middle",
			"width" : "8%"
		});
		
		me.starttimeEditor = new mx.editors.DateTimeEditor({
    		formatString: "yyyy-MM-dd",
    		width:"12%"
		});
		
		me.label5 = new mx.controls.Label({
			text : "截止时间：",
			textAlign : "center",
			verticalAlign : "middle",
			"width" : "8%"
		});
		
		me.endtimeEditor = new mx.editors.DateTimeEditor({
    		formatString: "yyyy-MM-dd",
    		width:"12%"
		});
		
		me.label111 = new mx.controls.Label({text:"合同名称：",width:"8%",textAlign:"center",verticalAlign : "middle"});	//合同名称标签
    	me.component1 = new mx.editors.TextEditor({width:"12%"});	//合同文本框
		
		me.condContainer.addControl(me.label1);
		me.condContainer.addControl(me.marketDropEditor);
		me.condContainer.addControl(me.label2);
		me.condContainer.addControl(me.contracttypeDropEditor);
//		me.condContainer.addControl(me.label3);
//		me.condContainer.addControl(me.timetypeDropEditor);
		me.condContainer.addControl(me.label4);
		me.condContainer.addControl(me.starttimeEditor);
		me.condContainer.addControl(me.label5);
		me.condContainer.addControl(me.endtimeEditor);
		me.condContainer.addControl(me.label111);
		me.condContainer.addControl(me.component1);
	}
	
	function _initCondContainer2(){
		me.label6 = new mx.controls.Label({
			text : "合同周期：",
			textAlign : "center",
			verticalAlign : "middle",
			"width" : "8%"
		});
		
		me.contractCycDropEditor = new mx.editors.DropDownEditor(
		{
			displayMember: "name",
		    valueMember: "value",
		    url: multiLevelContractManage.mappath("~/../ETradePublicUtils/rest/baGencodeTypeList/getList?type=23"),
		    width:"12%"
		});
		

		me.label7 = new mx.controls.Label({
			text : "合同状态：",
			textAlign : "center",
			verticalAlign : "middle",
			"width" : "8%"
		});
		
		me.contractstateDropEditor = new mx.editors.DropDownEditor(
		{
			displayMember: "name",
		    valueMember: "value",
		    url: multiLevelContractManage.mappath("~/../ETradePublicUtils/rest/baGencodeTypeList/getList?type=67"),
		    width:"12%"
		});
		
		me.label8= new mx.controls.Label({
			text : "购电方：",
			textAlign : "center",
			verticalAlign : "middle",
			"width" : "8%"
		});
		
		me.purchaseDropEditor = utils.dropDownEditor.CommonDropEditor.gsAutoComPleteEditor("","1");
 		me.purchaseDropEditor.setWidth("12%");
 		utils.dropDownEditor.CommonDropEditor.autoInit(me.purchaseDropEditor, '1');//下拉框初始化值
 		var fun = new utils.commonFun.DropDownEditorFun();
		fun.resizeAutocomplete(me.purchaseDropEditor);
		
		me.label9 = new mx.controls.Label({
			text : "售电方：",
			textAlign : "center",
			verticalAlign : "middle",
			"width" : "8%"
		});
		
		me.sellerDropEditor = utils.dropDownEditor.CommonDropEditor.gsAutoComPleteEditor("","2");
 		me.sellerDropEditor.setWidth("12%");
 		utils.dropDownEditor.CommonDropEditor.autoInit(me.sellerDropEditor, '2');//下拉框初始化值
		fun.resizeAutocomplete(me.sellerDropEditor);
		
		me.label10= new mx.controls.Label({
			text : "合同序列：",
			textAlign : "center",
			verticalAlign : "middle",
			"width" : "8%"
		});
		me.contractserialDropEditor = utils.dropDownEditor.ContractSeqInfo.ContractSeqInfoDropDownEditor(false,"");
		me.contractserialDropEditor.setWidth("12%");
		
		fun.resizeListEditor(me.contractserialDropEditor);
		
		me.condContainer1.addControl(me.label6);
		me.condContainer1.addControl(me.contractCycDropEditor);
		me.condContainer1.addControl(me.label7);
		me.condContainer1.addControl(me.contractstateDropEditor);
		me.condContainer1.addControl(me.label8);
		me.condContainer1.addControl(me.purchaseDropEditor);
		me.condContainer1.addControl(me.label9);
		me.condContainer1.addControl(me.sellerDropEditor);
		me.condContainer1.addControl(me.label10);
		me.condContainer1.addControl(me.contractserialDropEditor);
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
		
		me.maintainBtn = new mx.controls.Button({ text: "多级合同关系维护",left:30 });//合同关联维护按钮
		me.maintainBtn.on("click", me.contractRealtionMaintain);
		textContainer1.addControl(me.maintainBtn);
		
		me.authorityBtn = new mx.controls.Button({ text: "多级合同关联权限",left:60 });//合同关联权限按钮
		me.authorityBtn.on("click", me.authorityBtnClick);
		textContainer1.addControl(me.authorityBtn);
	}
	
	function _initDownSplit() {
		me.downSplit = new mx.containers.HSplit({
			rows : "auto,auto",width:"100%"
		});
		me.addControl(me.downSplit);
	}
	
	function _initDownPanel(){
//		me.dataGridPanel = new mx.containers.Panel({ name:"panel", width:"100%", height: "100%" });
		
//		me.dataGridPanel.addControl(_dataGrid);
		me.downSplit.addControl(_dataGrid,0);
		me.downSplit.addControl(me.treeSplit,1);
	}
	
	var form = null;
    var tree = null;
    var detailView = null;
	function _initTreePanel(){
		tree = new mx.datacontrols.DataTree({
            //需要向导替换，根据向导中选择的根数据类型进行替换下面的 department。
            baseUrl: IdeaConManage.mappath("~/rest/cocontractbaseinfo/getContractTree2"),
            displayCheckBox: false,  // 是否需要在树中显示选中框
            onselectionchanged: me._tree_selectionchanged
        });
		
		me.treeSplit = new mx.containers.VSplit({
			rows : "50%,50%",width:"100%",
			borderThick:0
		});
		
		var detailviewController = new IdeaConManage.views.IdeaConDetailViewController();
		detailView = detailviewController.getView();
		
		form = detailView.getForm();
		
		me.treeSplit.addControl(tree, 0);
		me.treeSplit.addControl(form, 1);
	}
	
	 /******** 树节点选中事件 **********/
    me._tree_selectionchanged = function(e)
    {
    	if(e.selection.hasChildren){
    		e.selection.expand();
    	}
    	form.load(e.selection.id);
    };
	
	me.selectData=function(){

		var marketid = isNull(me.marketDropEditor.getValue());
		var contracttype = isNull(me.contracttypeDropEditor.getValue());
//		var timetype = isNull(me.timetypeDropEditor.getValue());
		var contractstartdate = isNull(me.starttimeEditor.getValue());
		var contractenddate = isNull(me.endtimeEditor.getValue());
		
		var contractcyc = isNull(me.contractCycDropEditor.getValue());
		var signstate = isNull(me.contractstateDropEditor.getValue());//合同状态
		var purchaser = isNull(me.purchaseDropEditor.getValue());
		var seller = isNull(me.sellerDropEditor.getValue());
		var sequenceid = isNull(me.contractserialDropEditor.getValue());
		var contractname = isNull(me.component1.getValue());

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
		
		var params = {"marketid":marketid,"contracttype":contracttype,//"timetype":timetype,
			"contractstartdate":contractstartdate,"contractenddate":contractenddate,
			"contractcyc":contractcyc,
			"signstate":signstate,
			"purchaser":purchaser,
			"seller":seller,
	    	"sequenceid":sequenceid,
	    	"contractname":contractname
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
     * 点击合同【多级合同关系维护】按钮,调用方法
     */
     me.contractRealtionMaintain = function(){
     	var selectedItem = _dataGrid.selection;
 		if(selectedItem == null){
    		mx.indicate("info","请选择一条记录");
    		return;
    	}

		var isrelation = new String(selectedItem.values["isrelation"]);//合同执行类型

    	if(isrelation == null || isrelation == ""){
    		alert("请维护数据的关联权限！");
    		return
		}   	

		var userInfo = getUserInfo();//从后台获取用户信息（marketid）
    	if(userInfo.successful == false || typeof(userInfo.resultValue.items) == "undefined"){
    		mx.indicate("info", "用户登录信息过期！");
    		return;
    	}
    	
    	var presentUserMarketID = userInfo.resultValue.items[0].marketid;//当前用户的marketid
    	var selectedMarketID = selectedItem.values["marketid"];//选中记录的marketid
    	
    	if(isrelation == "1" && presentUserMarketID == selectedMarketID){// 选中记录的marketid与当前用户的marketid比较
    		var _contractRelateView = me._getContractRelateView();
//	    	_contractRelateView.objID = _dataGrid.getCheckedIDs()[0];
    		_contractRelateView.objID = selectedItem.values["contractid"];
		    //显示详细信息页面
	    	_showRelationContractView(_contractRelateView,"多级合同关联");
    	} else {
    		alert("该数据不允许关联上下级合同！");// TODO　是否提示   当前登录用户不能关联当前选中合同 
			return;
    	}
    }
    
    _contractRelateView = null;
     
    me._getContractRelateView = function(){
    	if (_contractRelateView == null)
        {
            var mvc = new multiLevelContractManage.views.MultiLevelRelateViewController();
            _contractRelateView = mvc.getView();
        }
    	return _contractRelateView;
    }
    
      /**
     * 显示意向性合同关联视图
     * @param p_view : 需要显示的视图对象
     * @param p_title : 对话框的标题
     */
    function _showRelationContractView(p_view,p_title){
    	var win = me.getRelationContractWindow();
    	if(typeof p_view != "undefined"){
    		p_view.load();
    		//设置显示视图、标题信息
    		win.setView(p_view);
    		win.setTitle(p_title ? p_title : win.title);
    	}
    	win.showDialog();
    }
    
    
    /**
     * 点击【多级合同关联权限】按钮，调用该方法
     */
    me.authorityBtnClick = function(){
    	var selectedItem = _dataGrid.selection;
    	if(selectedItem == null){
    		mx.indicate("info","请选择一条记录");
    		return;
    	}
    	var isrelation = selectedItem.values["isrelation"];//合同执行类型

//    	if(isrelation == null || isrelation == ""){
//    		alert("请维护数据的关联权限！");
//    		return;
//    	}
    	
    	var userInfo = getUserInfo();
		if(userInfo.successful == false || typeof(userInfo.resultValue.items) == "undefined"){
    		mx.indicate("info", "用户登录信息过期！");
    		return;
		}
		
		var childNum = selectedItem.values["childnum"];
		var supercontractid = selectedItem.values["supercontractid"];
		if(supercontractid != "" || childNum != "0"){//该合同是父合同,或者是子合同，则无法修改关联权限
			mx.indicate("info", "该合同有关联关系，无法维护关联权限");
			return;
		}
		
		var presentUserMarketID = userInfo.resultValue.items[0].marketid;//当前用户的marketid
		var selectItemMarketID = selectedItem.values["marketid"];
		
		if((isrelation == null || isrelation == "" || isrelation=="0") && presentUserMarketID == selectItemMarketID){//登录用户 与 选中合同记录 是同一场景
		 	var isExecute = confirm("确认修改关联权限吗？");
	    	if(isExecute){
	    		var data ={"contractid":selectedItem.values["contractid"], "isrelation":"1"};//设置 【关联权限】为 【是】
		     	var s = me.restClient.getSync(multiLevelContractManage.mappath("~/rest/multiLevelContractManage/authorityRelation"), { "params": JSON.stringify(data)});
		     	if(typeof(s)!='undefined'&&s!=null){
		 			if(s.type!='error'){
		 				if(s.resultValue!=null){
		 					me.selectData();//修改界面信息
		 				}
		 			}
		 		}
	    	} else {
	    		return;
	    	}
		}else if(isrelation=="1" && presentUserMarketID == selectItemMarketID){
			
			var isExecute = confirm("确认修改关联权限吗？");
	    	if(isExecute){
		    	var data ={"contractid":selectedItem.values["contractid"], "isrelation":"0"};//设置 【关联权限】为 【否】
		     	var s = me.restClient.getSync(multiLevelContractManage.mappath("~/rest/multiLevelContractManage/authorityRelation"), { "params": JSON.stringify(data)});
		     	if(typeof(s)!='undefined'&&s!=null){
		 			if(s.type!='error'){
		 				if(s.resultValue!=null){
							me.selectData();//修改界面信息
		 				}
		 			}
		 		}
	    	} else {
	    		return;
	    	}
		}else{
			alert("该数据不允许修改关联权限！");
			return;
		}
	}
	
    function getUserInfo()
    {
    	var respData = me.restClient.getSync(IdeaConManage.mappath("~/rest/cocontractbaseinfo/getUserInfo"));
    	return respData;
    }
	
    function _initDataGrid()
    {
        var restUrl = "~/rest/cocontractbaseinfo/";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : multiLevelContractManage.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "contractid"
        });
        
        /* 初始化 DataGrid */
        _dataGrid = new mx.datacontrols.ComplexGrid({   
			// 构造查询属性。
			alias: "multiLevelContractManageMultiLevelMainViewDataGrid",
			
			columns:[
			{   id:"radio",name:"mainRadio",renderCell: me._radioRender,width:"30"},
	        {	name: "contractid", caption: "contractid" , editorType: "TextEditor"},
//	        {	name: "rownum", caption: "序号" , editorType: "TextEditor"	},
	        {	name: "papercontractcode", caption: "主合同编号" , editorType: "TextEditor", width:"150",align:"center",dataAlign:"center"	},
	        {	name: "contractname", caption: "合同名称" , editorType: "TextEditor",align:"center",dataAlign:"center", 
	        							renderCell :_dataGrid_operColumn_cellRender, width:"230"},
	        {	name: "marketname", caption: "业务场景" , editorType: "TextEditor", width:"100",align:"center",dataAlign:"center"},
	        {	name: "isrelation", caption: "关联权限" , editorType: "DropDownEditor", width:"60",align:"center",dataAlign:"center"},
	        {	name: "contracttype", caption: "合同类型" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
	        {	name: "purchaser", caption: "购电方" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
	        {	name: "seller", caption: "售电方" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
			{	name: "contractstartdate", caption: "开始日期" , editorType: "DateTimeEditor",align:"center",dataAlign:"center"	, formatString: "yyyy-MM-dd", width:"100"},
	        {	name: "contractenddate", caption: "截止日期" , editorType: "DateTimeEditor"  ,align:"center",dataAlign:"center", formatString: "yyyy-MM-dd", width:"100"},
	        {	name: "energy", caption: "总电量" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
//	        {	name: "backupstate", caption: "备案状态" , editorType: "TextEditor", width:"60",align:"center",dataAlign:"center"	},
//	        {	name: "signstate", caption: "签订状态" , editorType: "TextEditor", width:"60",align:"center",dataAlign:"center"	},
	        {	name: "flowstate", caption: "合同状态" , editorType: "TextEditor", width:"60",align:"center",dataAlign:"center"	},
	        {	name: "marketid", caption: "业务场景id" , editorType: "TextEditor",align:"center",dataAlign:"center"},
	        {	name: "supercontractid", caption: "supercontractid" , editorType: "TextEditor"},
	        {	name: "childnum", caption: "childnum" , editorType: "TextEditor"}
	        
            ],
            displayCheckBox: false,
	        displayPrimaryKey:false,//列表是否显示主键
            allowEditing: false, //列表默认不可编辑
            displayColSplitLine:true,//表格分割线
            displayToolBar:false,
            autoWrap:true,
	        pageSize : 20,
            entityContainer: gridEntityContainer,
            onselectionchanged:me._radioSelected,
            onload:setVisible
        });
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
			
			detailView.objID = contractid;
	    	var sceneType = "multiLevel";//获取树结构的参数，标识获取意向性合同树结构
	    	detailView.sceneType = sceneType;
	    	
			form.load(contractid);
			tree.load({"contractid":contractid, "sceneType":sceneType});
		}
	};
    
    /**
     * 设置表单内容只读
     */
    function setVisible(){
    	_dataGrid.columns["marketid"].setVisible(false);
    	_dataGrid.columns["supercontractid"].setVisible(false);
    	_dataGrid.columns["childnum"].setVisible(false);
    	var contracttype = isNull(me.contracttypeDropEditor.getValue());
    	//合同类型为发电权合同时 改表头名称
    	var restClient = new mx.rpc.RESTClient();
    	var result = restClient.getSync(multiLevelContractManage.mappath("~/rest/coContractbaseinfoV2Act/isfdqht"),{"params":JSON.stringify({"contracttype":contracttype})});
    	if(result==true){
    		_dataGrid.columns.purchaser.setCaption("被替代方");
    		_dataGrid.columns.seller.setCaption("替代方");
    	}
    }
    
    function _dataGrid_operColumn_cellRender(p_item, $p_cell){
		$("<a href='#' style='text-decoration:none; color: blue;'>"+p_item.values["contractname"]+"</a>").appendTo($p_cell).on("click",
			function(){
				me.controller._showContractDetailView(p_item);
			} ).css("cursor","hand");
	}

    var _relationContractWin = null;
    me.getRelationContractWindow = function(){
//    	var _relationContractWin = multiLevelContractManage.context.windowManager.create({
    	var _relationContractWin = utils.commonFun.WinMananger.create({
			reusable: true,//是否复用
			width:"100%",
			height:"100%",
			title:"意向性合同关联"
		});
		
		return _relationContractWin;
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