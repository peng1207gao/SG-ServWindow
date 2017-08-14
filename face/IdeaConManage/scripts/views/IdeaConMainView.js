$ns("IdeaConManage.views");

$import("mx.editors.DropDownEditor");
$import("mx.controls.ToolBar");
$import("mx.datacontrols.DataForm");
$import("mx.containers.Container");
$import("mx.containers.Panel");
$import("mx.datacontrols.DataGrid");
$import("mx.controls.Label");
$import("mx.editors.TextEditor");
$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.ComplexGrid");
//$import("utils.dropDownEditor.contractTypeTree");
//$import("utils.dropDownEditor.contractSeqInfo");
//$import("utils.dropDownEditor.commonDropEditor");

IdeaConManage.views.IdeaConMainView = function()
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
//    	_initWindowSplit();//整个界面布局
    	_initTopControls();
    	_initDownControls();

    	_initDownSplit();
    	_initDataGrid();
    	_initTreePanel();
        me.on("activate", me.controller._onactivate);
    }
    
     //将页面划分为两部分
//    function _initWindowSplit() {
//		me.mainSplit = new mx.containers.HSplit({
//			rows : "133,auto",width:"100%",
//			borderThick : 1
//		});
//		me.addControl(me.mainSplit);		
//	}
    
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
		me.downSplit.addControl(me.treeSplit, 1);
	}
	
	 /******** 树节点选中事件 **********/
    me._tree_selectionchanged = function(e)
    {
    	if(e.selection.hasChildren){
    		e.selection.expand();
    	}
    	form.load(e.selection.id);
    };
	
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
	    	var sceneType = "ideaContract";//获取树结构的参数，标识获取意向性合同树结构
	    	detailView.sceneType = sceneType;
	    	
			form.load(contractid);
			tree.load({"contractid":contractid, "sceneType":sceneType});
		}
	};

	
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
		
		var isFilter = true;
		var isdisplay = true;
		me.mmm = new utils.dropDownEditor.contractTypeTree();
		me.contracttypeDropEditor = me.mmm.ContractTypeDropDownEditor(isFilter,isdisplay);
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
//		    url: IdeaConManage.mappath("~/../ETradePublicUtils/rest/baGencodeTypeList/getList?type=93")
//		});
//		me.timetypeDropEditor.setValue("0","合同开始时间");//初始化默认选中值
//		me.timetypeDropEditor.setWidth("12%");

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
		var date  = new Date()
    	me.starttimeEditor.setValue(date.getFullYear()+"-01-01");
		
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
		me.endtimeEditor.setValue(date.getFullYear()+"-12-31");
		
		me.contractNameLabel = new mx.controls.Label({text:"合同名称：",width:"8%",textAlign:"center",verticalAlign : "middle"});	//合同名称标签
    	me.contractNameText = new mx.editors.TextEditor({width:"12%"});	//合同文本框
		
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
		me.condContainer.addControl(me.contractNameLabel);
		me.condContainer.addControl(me.contractNameText);
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
		    url: IdeaConManage.mappath("~/../ETradePublicUtils/rest/baGencodeTypeList/getList?type=23")
		});
		me.contractCycDropEditor.setWidth("12%");

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
		    url: IdeaConManage.mappath("~/../ETradePublicUtils/rest/baGencodeTypeList/getList?type=67")
		});
		me.contractstateDropEditor.setWidth("12%");
		
		me.label8= new mx.controls.Label({
			text : "购电方：",
			textAlign : "center",
			verticalAlign : "middle",
			"width" : "8%"
		});
		
		me.purchaseDropEditor = utils.dropDownEditor.CommonDropEditor.gsAutoComPleteEditor("","1");
 		me.purchaseDropEditor.setWidth("12%");
 		utils.dropDownEditor.CommonDropEditor.autoInit(me.purchaseDropEditor,'1');//下拉框初始化值
 		
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
 		utils.dropDownEditor.CommonDropEditor.autoInit(me.sellerDropEditor,'2');//下拉框初始化值

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
	
    function _initDataGrid()
    {
        var restUrl = "~/rest/cocontractbaseinfo/";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : IdeaConManage.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "contractid"
        });
       
        /* 初始化 DataGrid */
        _dataGrid = new mx.datacontrols.ComplexGrid({  
			// 构造查询属性。
			alias: "IdeaConManageIdeaConMainViewDataGrid",
			columns:[
			{   id:"radio",name:"mainRadio",renderCell: me._radioRender,width:"30"},
	        {	name: "contractid", caption: "contractid" , editorType: "TextEditor",align:"center",dataAlign:"center"},
//	        {	name: "rownum", caption: "序号" , editorType: "TextEditor"	},
	        {	name: "papercontractcode", caption: "主合同编号" , editorType: "TextEditor", width:"150",align:"center",dataAlign:"center"	},
	        {	name: "contractname", caption: "合同名称" , editorType: "TextEditor",align:"center",dataAlign:"center", renderCell :_dataGrid_operColumn_cellRender, width:"200"},
	        {	name: "marketname", caption: "业务场景" , editorType: "TextEditor", width:"100",align:"center",dataAlign:"center"	},
	        {	name: "exectype", caption: "合同执行类型" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
	        {	name: "contracttype", caption: "合同类型" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
	        {	name: "purchaser", caption: "购电方" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
	        {	name: "seller", caption: "售电方" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
			{	name: "contractstartdate", caption: "开始日期" , align:"center",dataAlign:"center", editorType: "DateTimeEditor",formatString: "yyyy-MM-dd", width:"100"},
	        {	name: "contractenddate", caption: "截止日期" , align:"center",dataAlign:"center", editorType: "DateTimeEditor",formatString: "yyyy-MM-dd", width:"100"},
	        {	name: "energy", caption: "总电量" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
//	        {	name: "backupstate", caption: "备案状态" , editorType: "TextEditor", width:"60",align:"center",dataAlign:"center"	},
//	        {	name: "signstate", caption: "签订状态" , editorType: "TextEditor", width:"60",align:"center",dataAlign:"center"	},
	        {	name: "flowstate", caption: "合同状态" , editorType: "TextEditor", width:"60",align:"center",dataAlign:"center"	},
	        {	name: "marketid", caption: "业务场景id" , editorType: "TextEditor",align:"center",dataAlign:"center" }
	       
            ],
            // 构造列排序条件，如果有多列，则以逗号分隔。例sorter: "school ASC, class DESC"
            displayCheckBox: false,
	        displayPrimaryKey:false,//列表是否显示主键
            allowEditing: false, //列表默认不可编辑
            displayColSplitLine:true,//表格分割线
	        pageSize : 20,
            entityContainer: gridEntityContainer,
            displayToolBar:false,
            autoWrap:true,
            onselectionchanged:me._radioSelected,
            onload:setVisible
        });
        
        me.downSplit.addControl(_dataGrid, 0);
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
     * 设置表单内容只读
     */
    function setVisible(){
    	_dataGrid.columns["marketid"].setVisible(false);
    	var contracttype = isNull(me.contracttypeDropEditor.getValue());
    	//合同类型为发电权合同时 改表头名称
    	var restClient = new mx.rpc.RESTClient();
    	var result = restClient.getSync(IdeaConManage.mappath("~/rest/coContractbaseinfoV2Act/isfdqht"),{"params":JSON.stringify({"contracttype":contracttype})});
    	if(result==true){
    		_dataGrid.columns.purchaser.setCaption("被替代方");
    		_dataGrid.columns.seller.setCaption("替代方");
    	}
    }
    
	function _initDownSplit() {
		me.downSplit = new mx.containers.HSplit({
			rows : "auto,auto",width:"100%"
		});
		me.addControl(me.downSplit);
	}
    
    function isNull(obj){
    	if(obj != null && obj.length != 0  && "null" != obj){
    		return obj;
    	} else {
    		return "";
    	}
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
		
		me.maintainBtn = new mx.controls.Button({ text: "合同关系维护",left:30 });//合同关联维护按钮
		me.maintainBtn.on("click", me.controller._showContractRelateView);
		textContainer1.addControl(me.maintainBtn);
		
		me.authorityBtn = new mx.controls.Button({ text: "合同关联权限",left:60 });//合同关联权限按钮
		me.authorityBtn.on("click", me.authorityBtnClick);
		textContainer1.addControl(me.authorityBtn);
	}
	
	me.authorityBtnClick = function(){
		var selectedItem = _dataGrid.selection;
		if(selectedItem == null){
			mx.indicate("info", "请选择一条记录");
			return;
		}
		
		var str='执行性合同';
    	var exetype = selectedItem.values["exectype"];//合同执行类型
    	if(exetype == null || exetype == ""){
//    		alert("请维护数据的合同执行类型！");
    		mx.indicate("info", "请维护数据的合同执行类型！");
    		return;
    	}
    	
    	var isExecute = confirm("确认修改合同执行类型吗？");
    	if(isExecute){
			var userInfo = me.controller.getUserInfo();
			if(userInfo.successful == false || typeof(userInfo.resultValue.items) == "undefined"){
	    		mx.indicate("info", "用户登录信息过期！");
	    		return;
			}
			
			var presentUserMarketID = userInfo.resultValue.items[0].marketid;//当前用户的marketid
			var selectItemMarketID = selectedItem.values["marketid"];
    		
    		if(exetype.indexOf(str)==0 && presentUserMarketID == selectItemMarketID){
    			var data ={"contractid":selectedItem.values["contractid"], "exetype":"2"};//2-----'意向性协议'
			  	me.restClient = new mx.rpc.RESTClient();
		     	var s = me.restClient.getSync(IdeaConManage.mappath("~/rest/cocontractbaseinfo/authorityRelation"), { "params": JSON.stringify(data)});
		     	if(typeof(s)!='undefined'&&s!=null){
		 			if(s.type!='error'){
		 				if(s.resultValue!=null){
		 					me.selectDataWithPageIndex();//刷新界面信息
		 				}
		 			} else {
//		 				alert("修改数据失败");
						mx.indicate("info", "修改数据失败！");

		 			}
		 		}
			}else if(exetype.indexOf(str)!=0 &&  presentUserMarketID == selectItemMarketID){
				var data ={"contractid":selectedItem.values["contractid"], "exetype":"1"};//2-----'执行性合同'
			  	me.restClient = new mx.rpc.RESTClient();
		     	var s = me.restClient.getSync(IdeaConManage.mappath("~/rest/cocontractbaseinfo/authorityRelation"), { "params": JSON.stringify(data)});
		     	if(typeof(s)!='undefined'&&s!=null){
		 			if(s.type!='error'){
		 				if(s.resultValue!=null){
 							me.selectDataWithPageIndex();//刷新界面信息
		 				}
		 			} else {
//		 				alert("修改数据失败");
		 				mx.indicate("info", "修改数据失败！");
		 			}
		 		} 
			}else{
//				alert("该数据不允许修改合同执行类型！");
				mx.indicate("info", "该数据不允许修改合同执行类型！");
				return;
			}

    	}
	}
    me.selectDataWithPageIndex = function(){

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

		var params = {"marketid":marketid,"contracttype":contracttype,
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
    
	me.selectData = function(){

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
		var contractname = isNull(me.contractNameText.getValue());
		
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
	    	"sequenceid":sequenceid,
	    	"contractname":contractname
		}
    	_dataGrid.setFilter(params);
	    _dataGrid.load();
    }
    
    
	function _dataGrid_operColumn_cellRender(p_item, $p_cell){
		$("<a href='#' style='text-decoration:none; color: blue;'>"+p_item.values["contractname"]+"</a>").appendTo($p_cell).on("click",
			function(){
				me.controller._showContractDetailView(p_item);
			}).css("cursor","hand");
	}
	
    me.getRelationContractWindow = function(){
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