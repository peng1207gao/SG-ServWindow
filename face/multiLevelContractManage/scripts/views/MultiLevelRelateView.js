$ns("multiLevelContractManage.views");

$import("mx.controls.ToolBar");
$import("mx.datacontrols.DataForm");
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
//$import("utils.dropDownEditor.marketTree");

multiLevelContractManage.views.MultiLevelRelateView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    //网格列表
    var _dataGrid = null;
    
    /**
     * 待关联合同对象id
     */
    me.objID = null;
    
    me._relateDataGrid = null;
    
    me.selectionItem = null;
    
    var width = "12%";

    base.init = me.init;
    me.init = function()
    {
        base.init();
        _initControls();
    };
    
    function _initControls()
    {
    	_initWindowSplit();
    	_initTopControls();
    	_initDownControls();

    	_initDownSplit();
    	_initDataGrid();
    	_initDownPanel();
    	_initRelateDateGrid();
        me.on("activate", me.controller._onactivate);
    }
    
    me.load = function()
    {
    	me.selectData();//加载上端表格信息
    	
        _relateDataGrid.pageNaviBar.setDisplay("none");//不显示 底端表格的分页控件 
        _relateDataGrid.setFilter(JSON.stringify({"contractid":me.objID}));
    	_relateDataGrid.load();//加载下端表格信息
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
    	me.panel = new mx.containers.Panel({ name:"panel1", title:"业务查询", width:"100%", height: 80}); 
		me.panel1 = new mx.containers.Panel({ name:"panel2", title:"业务操作", width:"100%", height: 50 }); 
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
	
	function _initCondContainer1(){
		me.label1 = new mx.controls.Label({
			text : "业务场景：",
			textAlign : "center",
			verticalAlign : "middle",
			"width" : "8%"
		});
		
		me.marketDropEditor  = utils.dropDownEditor.MarketTree.MarketDropDownEditor(true,"",false);
		me.marketDropEditor.setWidth(width);
    	
		me.label2 = new mx.controls.Label({
			text : "合同类型：",
			textAlign : "center",
			verticalAlign : "middle",
			"width" : "8%"
		});
		
		var isFilter = false;
		var isdisplay = false;
		
		me.contracttypeDropEditor = utils.dropDownEditor.ContractTypeTree.ContractTypeDropDownEditor(isFilter,isdisplay);
		me.contracttypeDropEditor.setWidth(width);

		me.label3 = new mx.controls.Label({
			text : "时间类型：",
			textAlign : "center",
			verticalAlign : "middle",
			"width" : "8%"
		});
		
		me.timetypeDropEditor = new mx.editors.DropDownEditor(
		{
			displayMember: "name",
		    valueMember: "value",
			allowDropDown : true,
		    url: IdeaConManage.mappath("~/../ETradePublicUtils/rest/baGencodeTypeList/getList?type=93")
		});
		me.timetypeDropEditor.setValue("0","合同开始时间");//初始化默认选中值
		me.timetypeDropEditor.setWidth(width);

		me.label4 = new mx.controls.Label({
			text : "开始时间：",
			textAlign : "center",
			verticalAlign : "middle",
			"width" : "8%"
		});
		
		me.starttimeEditor = new mx.editors.DateTimeEditor({
    		formatString: "yyyy-MM-dd"
		});
		me.starttimeEditor.setWidth(width);
		
		me.label5 = new mx.controls.Label({
			text : "截止时间：",
			textAlign : "center",
			verticalAlign : "middle",
			"width" : "8%"
		});
		
		me.endtimeEditor = new mx.editors.DateTimeEditor({
    		formatString: "yyyy-MM-dd"
		});
		me.endtimeEditor.setWidth(width);
		
		me.condContainer.addControl(me.label1);
		me.condContainer.addControl(me.marketDropEditor);
		me.condContainer.addControl(me.label2);
		me.condContainer.addControl(me.contracttypeDropEditor);
		me.condContainer.addControl(me.label3);
		me.condContainer.addControl(me.timetypeDropEditor);
		me.condContainer.addControl(me.label4);
		me.condContainer.addControl(me.starttimeEditor);
		me.condContainer.addControl(me.label5);
		me.condContainer.addControl(me.endtimeEditor);
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
		me.contractCycDropEditor.setWidth(width);

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
		me.contractstateDropEditor.setWidth(width);
		
		me.label8= new mx.controls.Label({
			text : "购电方：",
			textAlign : "center",
			verticalAlign : "middle",
			"width" : "8%"
		});
		
		me.purchaseDropEditor = utils.dropDownEditor.CommonDropEditor.gsAutoComPleteEditor("","1");
 		me.purchaseDropEditor.setWidth(width);
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
 		me.sellerDropEditor.setWidth(width);
		utils.dropDownEditor.CommonDropEditor.autoInit(me.sellerDropEditor, '2');//下拉框初始化值
 		fun.resizeAutocomplete(me.sellerDropEditor);
		
		me.label10= new mx.controls.Label({
			text : "合同序列：",
			textAlign : "center",
			verticalAlign : "middle",
			"width" : "8%"
		});
		
		me.contractserialDropEditor = utils.dropDownEditor.ContractSeqInfo.ContractSeqInfoDropDownEditor(false,"");
		me.contractserialDropEditor.setWidth(width);
		
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
			{   id:"detailradio",name:"detailradio",renderCell: me._radioRender,width:"30"},
	        {	name: "contractid", caption: "contractid" , editorType: "TextEditor",align:"center",dataAlign:"center" },
//	        {	name: "rownum", caption: "序号" , editorType: "TextEditor"	},
	        {	name: "papercontractcode", caption: "主合同编号" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
	        {	name: "contractname", caption: "合同名称" , editorType: "TextEditor",align:"center",dataAlign:"center", renderCell :_dataGrid_operColumn_cellRender, width:"200"},
	        {	name: "marketname", caption: "业务场景" , editorType: "TextEditor", width:"100",align:"center",dataAlign:"center"	},
	        {	name: "exectype", caption: "合同执行类型" , editorType: "TextEditor"	, align:"center",dataAlign:"center"},
	        {	name: "contracttype", caption: "合同类型" , editorType: "TextEditor", align:"center",dataAlign:"center"	},
	        {	name: "purchaser", caption: "购电方" , editorType: "TextEditor", align:"center",dataAlign:"center"	},
	        {	name: "seller", caption: "售电方" , editorType: "TextEditor", align:"center",dataAlign:"center"	},
			{	name: "contractstartdate", caption: "开始日期" , editorType: "DateTimeEditor", align:"center",dataAlign:"center"	, formatString: "yyyy-MM-dd", width:"100"},
	        {	name: "contractenddate", caption: "截止日期" , editorType: "DateTimeEditor", align:"center",dataAlign:"center"	, formatString: "yyyy-MM-dd", width:"100"},
	        {	name: "energy", caption: "总电量" , editorType: "TextEditor", align:"center",dataAlign:"center"	},
	        {	name: "backupstate", caption: "备案状态" , editorType: "TextEditor", width:"60", align:"center",dataAlign:"center"	},
	        {	name: "signstate", caption: "签订状态" , editorType: "TextEditor", width:"60", align:"center",dataAlign:"center"	},
	        {	name: "flowstate", caption: "流转状态" , editorType: "TextEditor", width:"60", align:"center",dataAlign:"center"	},
	        {	name: "supercontractid", caption: "supercontractid" , editorType: "TextEditor", align:"center",dataAlign:"center"}
	       
            ],
            // 构造列排序条件，如果有多列，则以逗号分隔。例sorter: "school ASC, class DESC"
            displayCheckBox: false,
	        displayPrimaryKey:false,//列表是否显示主键
            allowEditing: false, //列表默认不可编辑
	        pageSize : 20,
	        displayColSplitLine:true,//表格分割线
            entityContainer: gridEntityContainer,
            onselectionchanged:me._radioSelected,
            displayToolBar:false,
            autoWrap:true,
            onload:setVisible
        });
        
        me.downSplit.addControl(_dataGrid,0);
    }
    
    /**
     * 设置表单内容只读
     */
    function setVisible(){
    	_dataGrid.columns["supercontractid"].setVisible(false);
    	_relateDataGrid.columns["supercontractid"].setVisible(false);
    }
    
    
     /**
     * 创建radio表单
     */
	me._radioRender = function(p_item, $p_cell)
	{	
		var contractid = p_item.values.contractid;
		$("<input type='radio' id='detailradio"+contractid+"' name='radio' value='1'>").appendTo($p_cell).on("click",
    			function(){   					
    					//me._radio_Check(p_item);
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
			$("input[type=radio ][id='detailradio"+contractid+"']").attr("checked","checked");
		}
		
	};
	
    /**
     * 下方 Split
     */
	function _initDownSplit() {
		me.downSplit = new mx.containers.HSplit({
			rows : "auto,150",width:"100%",
			borderThick : 1
		});
		me.mainSplit.addControl(me.downSplit,1);
	}
	
	function _initDownPanel(){
		me.downPanel = new mx.containers.Panel({ name:"panel3", title:"待关联合同信息", width:"100%", height: "100%" });
		me.downSplit.addControl(me.downPanel,1);
	}

	
	function _initRelateDateGrid(){
		 var restUrl = "~/rest/cocontractbaseinfo/relationContract";
        /* 初始化 EntityContainer */        
    	var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : IdeaConManage.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "contractid",
            loadMeta:false
        });
        
     	/* 初始化 DataGrid */
        _relateDataGrid = new mx.datacontrols.DataGrid({  
			// 构造查询属性。
			alias: "IdeaConManageIdeaConMainViewDataGrid",
			columns:[
	        {	name: "contractid", caption: "contractid" , editorType: "TextEditor", align:"center",dataAlign:"center"},
//	        {	name: "rownum", caption: "序号" , editorType: "TextEditor"	},
	        {	name: "papercontractcode", caption: "主合同编号" , editorType: "TextEditor", align:"center",dataAlign:"center"	},
	        {	name: "contractname", caption: "合同名称" , editorType: "TextEditor", align:"center",dataAlign:"center", renderCell :_dataGrid_operColumn_cellRender, width:"200"},
	        {	name: "marketname", caption: "业务场景" , editorType: "TextEditor", width:"100", align:"center",dataAlign:"center"	},
	        {	name: "exectype", caption: "合同执行类型" , editorType: "TextEditor", align:"center",dataAlign:"center"	},
	        {	name: "contracttype", caption: "合同类型" , editorType: "TextEditor", align:"center",dataAlign:"center"	},
	        {	name: "purchaser", caption: "购电方" , editorType: "TextEditor", align:"center",dataAlign:"center"	},
	        {	name: "seller", caption: "售电方" , editorType: "TextEditor", align:"center",dataAlign:"center"	},
			{	name: "contractstartdate", caption: "开始日期" , editorType: "DateTimeEditor", align:"center",dataAlign:"center"	, formatString: "yyyy-MM-dd", width:"100"},
	        {	name: "contractenddate", caption: "截止日期" , editorType: "DateTimeEditor", align:"center",dataAlign:"center" , formatString: "yyyy-MM-dd", width:"100"},
	        {	name: "energy", caption: "总电量" , editorType: "TextEditor", align:"center",dataAlign:"center"	},
	        {	name: "backupstate", caption: "备案状态" , editorType: "TextEditor", width:"60", align:"center",dataAlign:"center"	},
	        {	name: "signstate", caption: "签订状态" , editorType: "TextEditor", width:"60", align:"center",dataAlign:"center"	},
	        {	name: "flowstate", caption: "流转状态" , editorType: "TextEditor", width:"60", align:"center",dataAlign:"center"	},
	        {	name: "supercontractid", caption: "supercontractid" , editorType: "TextEditor", align:"center",dataAlign:"center"}//, visible: false}
            ],
            // 构造列排序条件，如果有多列，则以逗号分隔。例sorter: "school ASC, class DESC"
            displayCheckBox: false,
	        displayPrimaryKey:false,//列表是否显示主键
            allowEditing: false, //列表默认不可编辑
	        pageSize : 20,
            entityContainer: gridEntityContainer,
            displayToolBar:false,
            autoWrap:true,
            autoSelect:true,
            onload:setVisible
        });
        
        me.downPanel.addControl(_relateDataGrid);
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
		
		me.setUpBtn = new mx.controls.Button({ text: "设为上级合同",left:30 });//【设为上级合同】按钮
		me.setUpBtn.on("click", me.set_upLevel_contract);
		textContainer1.addControl(me.setUpBtn);
	}
	
    function getUserInfo()
    {
    	me.restClient = new mx.rpc.RESTClient();
    	var respData = me.restClient.getSync(IdeaConManage.mappath("~/rest/cocontractbaseinfo/getUserInfo"));
    	return respData;
    }
	
	me.set_upLevel_contract = function(){
		_relateDataGrid.selectItem(0);//选中待关联的第一条合同
		var selectedItem = _dataGrid.selection;
		if(selectedItem == null){
			mx.indicate("info","请选择一条记录");
			return;
		}
    	//父界面选中记录 contractid 与 子界面选中记录contractid 比较
    	var childid = _relateDataGrid.selection.values["contractid"];//子界面选中记录
    	var fatherid =  selectedItem.values["contractid"];//父界面选中记录
    	
    	var supercontractid =  selectedItem.values["supercontractid"];//上端界面选中记录的父合同id

    	var exetype = selectedItem.values["exectype"];//合同执行类型
    	var willbeFatherId = selectedItem.values["contractid"];//合同列表信息中选中记录的contractid

    	if(childid == fatherid){
    		alert("不能选择此合同作为上级合同，请选择其他合同！");
    		return;
    	} else if(supercontractid == childid){
    		alert("待关联合同已经是选中合同的上级合同");
    		return;
    	} else {
    		setupCon(childid,fatherid);//设置上级合同
    	}
    	
	}
	
 	/**
	 * 设置上级合同
	 */
	function setupCon(childid,fatherid){
		var data = {"contractid":childid, "fatherid":fatherid};
			var client = new mx.rpc.RESTClient();
			var startUrl = IdeaConManage.mappath("~/rest/multiLevelContractManage/setUp");
			
			var result = client.getSync(startUrl, { "params": JSON.stringify(data)});
			if(result.successful == true){
				if(result.resultValue == "success"){
				mx.indicate("info","设置成功！");
				me.parent.hide();
				} else if (result.resultValue == "inRelation"){
					mx.indicate("info","两个合同已经存在上下级关系");
				} else {
					mx.indicate("info","设置失败。");
				}
			} else {
				mx.indicate("info","设置失败。");
			}
	}
	
	me.selectData=function(){

		var marketid = isNull(me.marketDropEditor.getValue());
		var contracttype = isNull(me.contracttypeDropEditor.getValue());
		var timetype = isNull(me.timetypeDropEditor.getValue());
		var contractstartdate = isNull(me.starttimeEditor.getValue());
		var contractenddate = isNull(me.endtimeEditor.getValue());
		
		var contractcyc = isNull(me.contractCycDropEditor.getValue());
		var signstate = isNull(me.contractstateDropEditor.getValue());//合同状态
		var purchaser = isNull(me.purchaseDropEditor.getValue());
		var seller = isNull(me.sellerDropEditor.getValue());
		var sequenceid = isNull(me.contractserialDropEditor.getValue());
		
		var params = {"marketid":marketid,"contracttype":contracttype,"timetype":timetype,
		"contractstartdate":contractstartdate,"contractenddate":contractenddate,
		"contractcyc":contractcyc,
		"signstate":signstate,
		"purchaser":purchaser,
		"seller":seller,
	    "sequenceid":sequenceid
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

    /**
     * 获取DataGrid网格列表对象
     */
    me.getDataGrid = function(){
    	return _dataGrid;
    }
    
	me.endOfClass(arguments)
    return me;
};