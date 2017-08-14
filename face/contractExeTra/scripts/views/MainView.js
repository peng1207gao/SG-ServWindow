$ns("contractExeTra.views");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGridSearchBox");
$import("mx.datacontrols.DataGrid");
$import("mx.containers.HSplit");
$import("mx.containers.Panel");
$import("mx.rpc.RESTClient");
$import("fusionChart.lib.FusionCharts");


contractExeTra.views.MainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    var marketId = "";
    
    //数据表格查询过滤参数编辑器集合
    me.searchEditors = {};
    
    //网格列表
    me._dataGrid = null;
    //表单窗口对象
    var _detailWin = null;
    
    var tableContainer=null;
    
    var chartContainer=null;
    	
    var restClient = new mx.rpc.RESTClient();
    
    me.searchPanel=null;
    me.operationPanel=null;
    
    base.init = me.init;
    me.init = function()
    {
        base.init();
        _initControls();
    };
    
    
    function initMarketId(){
    	var items = restClient.getSync(htadd.mappath("~/rest/coContractbaseinfoV2Act/init")).resultValue.items;
    	marketId = items[0];
    }
    
    function _initControls()
    {	
    	 initMarketId();
    	_initLayout();
	    _initDataGrid();
        me.on("activate", me.controller._onactivate);
    }
    
    function _initLayout(){
    	var hSplit1 = new mx.containers.HSplit({
    		borderThick:"0px", rows:"180, auto",height:"180px"
    	});
    	
    	var hSplit2 = new mx.containers.HSplit({
    		borderThick:"0px", rows:"200, auto"
    	});
    	me.addControl(hSplit1);
    	me.addControl(hSplit2);
    	
    	
    	//业务查询区域
    	me.searchPanel = new mx.containers.Panel(
    		{width:"100%", height:"120", title:"业务查询"}
    	);
    	
    	//业务操作区域
    	me.operationPanel = new mx.containers.Panel(
    		{width:"100%", height:"60", title:"业务操作"}
    	);
    	
     	hSplit1.addControl(me.searchPanel, 0);
    	hSplit1.addControl(me.operationPanel, 0);
    	
    	
    	me.ywczContainer = new mx.containers.Container({
			width:"100%",
			height:"100%",
			borderThick : 0
		});
    	
    	me.searchPanel.addControl(me.ywcxContainer);
    	
    	me.operationPanel.addControl(me.ywczContainer);
    	
    	var ywcxContainer0= new mx.containers.Container({
			width:"100%",
			height:"30",
			borderThick : 0
		});
    	var ywcxContainer1= new mx.containers.Container({
			width:"100%",
			height:"30",
			borderThick : 0
		});
    	var ywcxContainer2= new mx.containers.Container({
			width:"100%",
			height:"30",
			borderThick : 0
		});
    	
    	me.searchPanel.addControl(ywcxContainer0);
    	me.searchPanel.addControl(ywcxContainer1);
    	me.searchPanel.addControl(ywcxContainer2);
    	
    	//查询条件
    	var htTypeLabel = new mx.controls.Label({
			text : "合同类型：",
			width : "120px",
			textAlign: "right", 
			verticalAlign: "middle",
			bottom:"-5px"
		}); 
    	me.htTypeSearch = utils.dropDownEditor.ContractTypeTree.ContractTypeDropDownEditor(true,true);
    	me.htTypeSearch.setWidth("13%");
		me.htTypeSearch.$e.css("bottom","-5px");
		me.htTypeSearch.on("changed",changeDrops1);//给合同类型添加onchanged触发事件
		
		
		ywcxContainer0.addControl(htTypeLabel);
		
		ywcxContainer0.addControl(me.htTypeSearch);
		
		var htzqLabel = new mx.controls.Label({
			text : "合同周期：",
			width : "80px",
			textAlign: "right", 
			verticalAlign: "middle",
			bottom:"-5px"
		}); 
		ywcxContainer0.addControl(htzqLabel);
		
		me.htzqSearch= new mx.editors.DropDownEditor({displayMember:"name",valueMember:"value",width:"13%",bottom:"-5px",
    		url:contractExeTra.mappath("~/../ETradePublicUtils/rest/baGencodeTypeList/getList?type=23")	//获取合同周期下拉框数据
		}); //合同周期下拉框
		ywcxContainer0.addControl(me.htzqSearch,0);
		
		var kssjLabel = new mx.controls.Label({
			text : "开始时间：",
			width : "80px",
			textAlign: "right", 
			verticalAlign: "middle",
			bottom:"-5px"
		}); 
		ywcxContainer0.addControl(kssjLabel);
		
		me.kssjSearch = new mx.editors.DateTimeEditor({
			value : me.year,
			formatString : "yyyy-MM-dd",
			bottom:"-5px",
			width:"13%"
		}); // 时间原件
		
		ywcxContainer0.addControl(me.kssjSearch);
		var date  = new Date()
    	me.kssjSearch.setValue(date.getFullYear()+"-01-01");
		var jssjLabel = new mx.controls.Label({
			text : "结束时间：",
			width : "80px",
			textAlign: "right", 
			verticalAlign: "middle",
			bottom:"-5px"
		}); 
		ywcxContainer0.addControl(jssjLabel);
		
		me.jssjSearch = new mx.editors.DateTimeEditor({
			value : me.year,
			formatString : "yyyy-MM-dd",
			bottom:"-5px",
			width:"13%"
		}); // 时间原件
		ywcxContainer0.addControl(me.jssjSearch);
	  	me.jssjSearch.setValue(date.getFullYear()+"-12-31");
		//第二行搜索条件
		var htztLabel = new mx.controls.Label({
			text : "合同状态：",
			width : "120px",
			textAlign: "right", 
			verticalAlign: "middle"
//			bottom:"-5px"
		}); 
		ywcxContainer1.addControl(htztLabel);
		
		me.htztSearch= new mx.editors.DropDownEditor(
    			{
    				displayMember: "name",
    			    valueMember: "value",
    				width : "13%",
//    				bottom :"-5px",
    				//displayCheckBox: true,
    				allowDropDown : false,
    			    url: htadd.mappath("~/../ETradePublicUtils/rest/baGencodeTypeList/getList?type=64")
    			});
		ywcxContainer1.addControl(me.htztSearch);
		
		
		me.gdfLabel = new mx.controls.Label(
				{	text:"购电方：",
					width:"80",
					textAlign:"right",
					verticalAlign: "middle"}
				);	//购电方标签
		ywcxContainer1.addControl(me.gdfLabel);	//添加标签
	
		//解决因为内容过长下拉框显示不全的问题
    	var fun = new utils.commonFun.DropDownEditorFun();
		
		me.gdfSearch = utils.dropDownEditor.CommonDropEditor.gsAutoComPleteEditor("","1");	//购电方下拉框
    	utils.dropDownEditor.CommonDropEditor.autoInit(me.gdfSearch,'1');
    	me.gdfSearch.setWidth("13%");
    	fun.resizeAutocomplete(me.gdfSearch);
		ywcxContainer1.addControl(me.gdfSearch); //添加文本框
	
		
	
		me.sdfLabel = new mx.controls.Label(
				{	text:"售电方：",
					width:"80px",
					textAlign:"right",
					verticalAlign: "middle"}
				);	//售电方标签
		ywcxContainer1.addControl(me.sdfLabel);	//添加标签
	
		me.sdfSearch = utils.dropDownEditor.CommonDropEditor.gsAutoComPleteEditor("","2"); //售电方下拉框
    	utils.dropDownEditor.CommonDropEditor.autoInit(me.sdfSearch,'2');
    	me.sdfSearch.setWidth("13%");
    	fun.resizeAutocomplete(me.sdfSearch);
		ywcxContainer1.addControl(me.sdfSearch);	//添加下拉框
		
		me.htxlLabel = new mx.controls.Label({text:"合同序列：",width:"80",textAlign:"right",verticalAlign: "middle"});	//购电方标签
		ywcxContainer1.addControl(me.htxlLabel);	//添加标签
    	
    	me.htSquSearch = new mx.editors.DropDownEditor({displayMember:"name",valueMember:"value",autoMatchInput:true,
    		allowEditing:true,width:"13%"}); //合同序列下拉框
    	//解决因为内容过长下拉框显示不全的问题
		fun.resizeListEditor(me.htSquSearch);
    	setComponent13Items("");	//给合同序列赋值
//    	me.htSquSearch.setText(me.htxltext);
//    	me.htSquSearch.$e.click(
//    		function(){
//    			if(me.htxl!="" && me.htxl!=null){
//    				me.htSquSearch.setValue(me.htxl);
//    			}else{
//    				me.htxl="";
//    			}
//    		}
//    	);
    	ywcxContainer1.addControl(me.htSquSearch);
    	
    	
    	me.label14 = new mx.controls.Label({text:"售电方发电类型：",width:"120px",textAlign:"right",verticalAlign: "middle"});	//售电方标签
    	ywcxContainer2.addControl(me.label14);	//添加标签
    	me.component14 = new mx.editors.DropDownEditor({displayMember:"name",valueMember:"value",width:"200",
    		url : contractExeTra.mappath("~/../ETradePublicUtils/rest/baGencodeTypeList/getList?type=13")	//获取合同状态下拉框数据
    		}); //合同状态下拉框
    	me.component14.setWidth("13%");
    	ywcxContainer2.addControl(me.component14);	//添加下拉框
    	
    	me.label1 = new mx.controls.Label({text:"合同名称：",width:"80px",textAlign:"right"});	//合同名称标签
    	ywcxContainer2.addControl(me.label1);	//添加标签
    	me.component1 = new mx.editors.TextEditor({width:"13%"});	//合同文本框
    	ywcxContainer2.addControl(me.component1); //添加文本框
    	
    	
    	me.queryHtBotton = new mx.controls.Button({ text: "查询",width:80,height:30,left:60,bottom:-5});//查询合同
		me.queryHtBotton.on("click", me.controller._btnSearch_onclick);
		me.ywczContainer.addControl(me.queryHtBotton);
		
		me.chartHtBotton = new mx.controls.Button({ text: "显示图表",width:80,height:30,left:60,bottom:-5});//查询合同
		me.ywczContainer.addControl(me.chartHtBotton);
		
		tableContainer= new mx.containers.Container({
			width:"100%",
			height:"100%",
			borderThick : 0
		});
		hSplit2.addControl(tableContainer,0);
		
		chartContainer= new mx.containers.Container({
			width:"100%",
			height:"100%",
			borderThick : 0
		});
		hSplit2.addControl(chartContainer,1);
    }
    
    
     var renderChart=function (){
     	var contracttype = isNull(me.htTypeSearch.value);
     	//合同类型为发电权合同时 改表头名称
    	var restClient = new mx.rpc.RESTClient();
    	var result = restClient.getSync(contractExeTra.mappath("~/rest/coContractbaseinfoV2Act/isfdqht"),{"params":JSON.stringify({"contracttype":contracttype})});
    	if(result==true){
    		me._dataGrid.columns.purchaser.setCaption("被替代方");
    		me._dataGrid.columns.seller.setCaption("替代方");
    	}
    	 var responseContent="";
    	 var tenStr=me.controller.getSeacrchMess();
    	var jgParams={};
    	if(tenStr){
    		jgParams=tenStr;
    	}
    	responseContent=restClient.getSync(contractExeTra.mappath("~/rest/contractexetra/getStatckChart"), { "params": JSON.stringify(jgParams)});
    	tenStr = responseContent.resultValue.items[0];
    	var chart = new FusionCharts(fusionChart.mappath("$/swf/StackedColumn2D.swf"),"myChartId","100%","100%","0","1");
    	chart.setDataXML(tenStr); 
    	chartContainer.$e.get(0).align = "center";// 内部居中
    	chart.render(chartContainer.$e.get(0));
    };
    
    function isNull(obj){
    	if(obj != null && obj.length != 0  && "null" != obj){
    		return obj;
    	} else {
    		return "";
    	}
    }
    
    /**
     * 给合同类型添加onchanged触发事件
     */
    changeDrops1 = function(e){	
    	var changecontracttype = e.target.value==null?"":e.target.value;
    	setComponent13Items(changecontracttype);
	}
    
    /**
     * 给合同序列赋值
     */
    function setComponent13Items(changecontracttype){
    	var items = restClient.getSync(contractExeTra.
    			mappath("~/rest/coContractbaseinfoV2Act/getComponent13Items"),
    			{marketId:marketId,contracttype:changecontracttype}).resultValue;	//获取售电方数据
    	me.htSquSearch.setItems(items);
    }
    
    /**
     * 给购电方下拉框赋值
     */
    function setComponent7Items(){
    	var type = me.htTypeSearch.getValue();	//获取合同类型值
    	
    	if(type==""||type==null){
    		return;
    	}
    	var items = restClient.getSync(htadd.
    			mappath("~/rest/coContractbaseinfoV2Act/getComponentItems"),
    			{type:type,marketId:marketId,value:1}).resultValue;	//获取购电方数据
    	me.gdfSearch.setItems(items);
    }	
    
    
    /**
     * 给售电方下拉框赋值
     */
    function setComponent8Items(){
    	var type =  me.htTypeSearch.getValue();	//获取合同类型值
    	if(type==""||type==null){
    		return;
    	}
    	var items = restClient.getSync(htadd.
    			mappath("~/rest/coContractbaseinfoV2Act/getComponentItems"),
    			{type:type,marketId:marketId,value:2}).resultValue;	//获取售电方数据
    	me.sdfSearch.setItems(items);
    }	
    
    function _initDataGrid()
    {
        var restUrl = "~/rest/contractexetra/";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : contractExeTra.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "contractid"
        });
        
        /* 初始化 DataGrid */
        me._dataGrid = new mx.datacontrols.DataGrid({   
			// 构造查询属性。
			alias: "contractExeTraMainViewDataGrid",
			
			columns:[
	        {	name: "contractid", caption: "合同ID" , editorType: "TextEditor",align:"center",dataAlign:"center"},
	        {	name: "contractname", caption: "合同名称" , editorType: "TextEditor",align:"center",dataAlign:"center"},
	        {	name: "purchaser", caption: "购电方" , editorType: "TextEditor",align:"center",dataAlign:"center"},
	        {	name: "seller", caption: "售电方" , editorType: "TextEditor",align:"center",dataAlign:"center"},
	        {	name: "contractqty", caption: "合同电量" , editorType: "TextEditor",align:"center",dataAlign:"center"},
	        {	name: "contractprice", caption: "合同电价" , editorType: "TextEditor",align:"center",dataAlign:"center"},
	        {	name: "wcdl", caption: "完成电量" , editorType: "TextEditor",align:"center",dataAlign:"center"},
	        {	name: "wcl", caption: "完成率" , editorType: "TextEditor",align:"center",dataAlign:"center"}
            ],
            // 构造列排序条件，如果有多列，则以逗号分隔。例sorter: "school ASC, class DESC"
            ///displayCheckBox: true,
            displayRowNumber: true,
	        displayPrimaryKey:false,//列表是否显示主键
            allowEditing: false, //列表默认不可编辑
	        pageSize :20,
            entityContainer: gridEntityContainer,
            onload:renderChart,
            rowNumberColWidth :60
        });
        tableContainer.addControl(me._dataGrid);
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
    	return me._dataGrid;
    }
    
	me.endOfClass(arguments)
    return me;
};