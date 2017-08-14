$ns("kqksexetrace.views");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGrid");
$import("mx.containers.Panel");
$import("mx.containers.TabControl");

kqksexetrace.views.KqKsExeTraceMainView = function()
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
    	_initTabControl();
    	_initPurchaseTabPage();
    	_initSellTabPage();
    	_initTransTabPage();
    	_initHSplit();//页面分为上下两部分
    	_initUpPanel();//创建存放查询条件的面板
    	_initDownPanel();//创建存放操作按钮的面板
    	_initQueryCondition();//创建查询条件
    	_initQueryButton();//创建查询按钮
	    _initDataGrid();//创建数据列表
	    _initDisplayButton();//创建显示图表按钮
	    _initDisplayChart();//创建图表
	    _initSellAndTransView();
    	_initDetailWindow();
        me.on("activate", me.controller._onactivate);
    }
    
    function _initTabControl(){
		me.tabControl = new mx.containers.TabControl({
			id:"TabControl",
			width:"100%"
		});
		
		me.addControl(me.tabControl);
	}
	
	function _initPurchaseTabPage(){
		me.purchaseTabPage = new mx.containers.TabPage({
			id:"TabPage1",
			autoInit:true,
			width:"100%",
			text:"购电方",
			name:"TabPage1"
		});
		
		me.tabControl.appendPage(me.purchaseTabPage);
	}
	
	function _initSellTabPage(){
		me.sellTabPage = new mx.containers.TabPage({
			id:"TabPage2",
			autoInit:true,
			width:"100%",
			text:"售电方",
			name:"TabPage2"
		});
		
		me.tabControl.appendPage(me.sellTabPage);
	}
	
	function _initTransTabPage(){
		me.transTabPage = new mx.containers.TabPage({
			id:"TabPage3",
			autoInit:true,
			width:"100%",
			text:"输电方",
			name:"TabPage3"
		});
		
		me.tabControl.appendPage(me.transTabPage);
	}
    
    function _initHSplit(){
    
    	me.hSplit = new mx.containers.HSplit({
    		rows: "110,auto"
    	});
    	me.purchaseTabPage.addControl(me.hSplit);
    }
    
    function _initUpPanel(){
    	me.upPanel = new mx.containers.Panel({
    		id:"upPanel",
			border:"1",
			height: "50%",
			title:"业务查询"
    	});
    	me.hSplit.addControl(me.upPanel);
    }
    
    function _initDownPanel(){
    	me.downPanel = new mx.containers.Panel({
    		id:"downPanel",
			border:"1",
			height: "50%",
			title:"业务操作"
    	});
    	me.hSplit.addControl(me.downPanel);
    }
    
    function _initQueryCondition(){
    	me.container1 = new mx.containers.Container({
    		id: "contaniner",
    		padding: "2px"
    	});
    	me.upPanel.addControl(me.container1);
    	
    	//合同名称查询框
    	me.nameLabel = new mx.controls.Label({
    		text: "联络线名称:",
	    	textAlign: "center",
	    	verticalAlign: "middle",
	    	width:80
    	});
    	me.container1.addControl(me.nameLabel);
    	me.lineName = new mx.editors.AutoCompleteEditor({
    		width: 150,
    		hint: "联络线名称",
    		mustMatch:false,
			allowDropDown:false,
			valueMember: "value",
			dropDownAnimation: "slideDown",
			url: kqksexetrace.mappath("~/rest/cocontractbaseinfo_mk/getLine")
    	});
    	me.container1.addControl(me.lineName);
    	
    	me.startLabel = new mx.controls.Label({
    		text: "开始时间:",
	    	textAlign: "center",
	    	verticalAlign: "middle",
	    	width:80
    	});
    	me.container1.addControl(me.startLabel);
    	me.startTime = new mx.editors.DateTimeEditor({
    		formatString: "yyyy-MM-dd"
    	});
    	me.container1.addControl(me.startTime);
    	
    	me.endLabel = new mx.controls.Label({
    		text: "截止时间:",
	    	textAlign: "center",
	    	verticalAlign: "middle",
	    	width:80
    	});
    	me.container1.addControl(me.endLabel);
    	me.endTime = new mx.editors.DateTimeEditor({
    		formatString: "yyyy-MM-dd"
    	});
    	me.container1.addControl(me.endTime);
    }
    
    function _initQueryButton(){
    	me.container2 = new mx.containers.Container({
    		id: "contaniner",
    		padding: "2px"
    	});
    	me.downPanel.addControl(me.container2);
    	me.searchBtn = new mx.controls.Button({
    		text: "查询",
    		left: 20,
    		onclick: me.controller._btn_Search
    	});
    	me.container2.addControl(me.searchBtn);
    }
    
    me._initParams = function(){
    	var params = {papercontractname:me.lineName.value,signeddate:me.startTime.value,backupdate:me.endTime.value,expend5:"purchase"};
    	return params;
    }
    
    me._initParams1 = function(){
    	var params = {"lineName":me.lineName.value,"startTime":me.startTime.value,"endTime":me.endTime.value,"type":"purchase"};
    	return params;
    }
    
    function _initDataGrid()
    {
    	//盛放DataGrid
    	me.container3 = new mx.containers.Container({
    		id: "contaniner",
    		height: 275,
    		padding: "2px"
    	});
    	me.hSplit.addControl(me.container3,1);
    	
        var restUrl = "~/rest/cocontractbaseinfo_mk/";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : kqksexetrace.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "contractid"
        });
        
        /* 初始化 DataGrid */
        _dataGrid = new mx.datacontrols.DataGrid({   
			// 构造查询属性。
			alias: "kqksexetraceKqKsExeTraceMainViewDataGrid",
			
			columns:[
	        {	name: "contractid", caption: "合同ID" , editorType: "TextEditor"	},
	        {	name: "papercontractname", caption: "合同名称" , editorType: "TextEditor"	},
	        {	name: "contractqty", caption: "合同电量" , editorType: "TextEditor"	},
	        {	name: "energy", caption: "完成电量" , editorType: "TextEditor"	},
	        {	name: "papercontractcode", caption: "完成率" , editorType: "TextEditor"	}
            ],
            // 构造列排序条件，如果有多列，则以逗号分隔。例sorter: "school ASC, class DESC"
            displayCheckBox: true,
	        displayPrimaryKey:false,//列表是否显示主键
            allowEditing: false, //列表默认不可编辑
            displayRowNumber: true,
	        pageSize : 10,
            entityContainer: gridEntityContainer
        });
        _dataGrid.setFilter(me._initParams());
        me.container3.addControl(_dataGrid);
    }
    
    function _initDisplayButton(){
    	//盛放显示图标按钮
    	me.container4 = new mx.containers.Container({
    		id: "contaniner",
    		padding: "2px",
    		height: 30
    	});
    	me.hSplit.addControl(me.container4,1);
    	me.displayBtn = new mx.controls.Button({
    		text: "显示图表",
    		left: 20,
    		onclick: me.controller._btn_Display
    	});
    	me.container4.addControl(me.displayBtn);
    }
    
    /**
     * 柱形图
     */
    function _initDisplayChart(){
    	//盛放图表
    	me.container5 = new mx.containers.Container({
    		id: "contaniner",
    		padding: "2px",
    		height: 500
    	});
    	me.hSplit.addControl(me.container5,1);
    	
//    	var restClient = new mx.rpc.RESTClient();
//    	var responseContent = restClient.getSync(kqksexetrace.mappath(
//    		"~/rest/cocontractbaseinfo_mk/getDataChart"), { "params": JSON.stringify(me._initParams1())});
//    	if(responseContent.successful == true){
//			me.chartData = responseContent.resultValue.items[0];
//			var chart = new FusionCharts(fusionChart.mappath("$/swf/StackedBar2D.swf"),"myChartId","100%","100%","0","1");//图表的显示
//	    	chart.setDataXML(me.chartData); 
//	    	me.container5.addControl(chart);
//	    	chart.render(me.container5.$e.get(0));
//    	}
    }
    
    function _initSellAndTransView(){
    	var sellView = new kqksexetrace.views.KqKsExeTraceSellerViewController().getView();
    	var transView = new kqksexetrace.views.KqKsExeTraceTransViewController().getView();
    	me.sellTabPage.addControl(sellView);
    	me.transTabPage.addControl(transView);
    }
    
    /**
     * 初始化表单视图窗口对象
     */
    function _initDetailWindow(){
    	_detailWin = kqksexetrace.context.windowManager.create({
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