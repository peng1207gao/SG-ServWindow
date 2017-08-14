$ns("contractTypeInfo.views");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGrid");
$import("mx.containers.Panel");
$import("mx.containers.Container");
$import("mx.containers.HSplit");
$import("mx.controls.TreeListView");


contractTypeInfo.views.ContractTypeInfoMainView = function()
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
    	_initHSplit();//页面划分上下部分
    	_initQueryPanel();//创建业务查询面板
    	_initOperPanel();//创建业务操作面板
    	_initUpContainer();//创建业务查询面板的控件容器
    	_initDownContainer();//创建业务操作面板的控件容器
    	_initMarket();//创建场景下拉树
    	_initStartDate();//创建开始时间选择框
    	_initEndDate();//创建截止时间选择框
    	_initSearch();//创建查询按钮
    	_initAdd();//创建新增按钮
    	_initEdit();//创建编辑按钮
    	_initEffect();//创建生效按钮
    	_initInvalid();//创建失效按钮
    	_initMember();//创建合同准入成员管理按钮
    	_initParams();//获取查询条件
	    _initDataGrid();//创建树形列表
    	_initDetailWindow();//创建弹窗
//        me.on("activate", me.controller._onactivate);
    }
    
    /**
     * 页面划分上下部分
     */
    function _initHSplit(){
    
    	me.hSplit = new mx.containers.HSplit({
    		rows: "120,auto",
    		width: "100%"
    	});
    	me.addControl(me.hSplit);
    }
    
    /**
     * 创建上部分的控件容器
     */
    function _initUpContainer(){
    	
    	me.upContainer = new mx.containers.Container({
    		id: "upContainer",
    		padding:"2px"
    	});
    	me.queryPanel.addControl(me.upContainer);
    }
    
    /**
     * 创建下部分的控件容器
     */
    function _initDownContainer(){
    
    	me.downContainer = new mx.containers.Container({
    		id: "downContainer",
    		padding:"2px"
    	});
    	me.operPanel.addControl(me.downContainer);
    }
    
    /**
     * 创建业务查询面板
     */
    function _initQueryPanel(){
    	me.queryPanel = new mx.containers.Panel({
    		name: "queryPanel",
    		title: "业务查询",
    		width: "100%",
    		height: "50%"
    	});
    	me.hSplit.addControl(me.queryPanel,0);
    }
    
    /**
     * 创建业务操作面板
     */
    function _initOperPanel(){
    	me.operPanel = new mx.containers.Panel({
    		name: "operPanel",
    		title: "业务操作",
    		width: "100%",
    		height: "50%"
    	});
    	me.hSplit.addControl(me.operPanel,0);
    }
    
    /**
     * 创建场景下拉树
     */
    function _initMarket(){
    
    	me.market = new mx.controls.Label({
    		text: "业务场景：",
    		textAlign: "center",
    		verticalAlign: "middle",
    		width: "6%",
    		left: 10
    	});
    	me.upContainer.addControl(me.market);
    	me.marketDropEditor = utils.dropDownEditor.MarketTree.MarketDropDownEditor(true,"",false);
    	me.marketDropEditor.setLeft(20);
    	me.marketDropEditor.setWidth("10%");
    	me.marketDropEditor.setReadOnly(true);
    	me.upContainer.addControl(me.marketDropEditor);
    }
    
    /**
     * 创建开始时间选择框
     */
    function _initStartDate(){
    
    	me.start = new mx.controls.Label({
    		text: "生效时间：",
   	 		textAlign: "center",
    		verticalAlign: "middle",
    		width: "6%",
    		left: 30
    	});
    	me.upContainer.addControl(me.start);
    	me.startTimeEditor = new mx.editors.DateTimeEditor({
    		formatString: "yyyy-MM-dd",
    		width: "13%",
    		left: 40
		});
		me.upContainer.addControl(me.startTimeEditor);
    }
    
    /**
     * 创建截止时间选择框
     */
    function _initEndDate(){
    
    	me.end = new mx.controls.Label({
    		text: "失效时间：",
   	 		textAlign: "center",
    		verticalAlign: "middle",
    		width: "6%",
    		left: 50
    	});
    	me.upContainer.addControl(me.end);
    	me.endTimeEditor = new mx.editors.DateTimeEditor({
    		formatString: "yyyy-MM-dd",
    		width: "13%",
    		left: 60
		});
		me.upContainer.addControl(me.endTimeEditor);
		
//		var linkEditor = new mx.editors.LinkEditor(
//		{
//		    "type" : "imgtext",//指定链接的类型。
//		    "width" : "90px",//指定控件宽度。
//		    "imageKey" : "1",//指定图标名称。
//		    "text" : "说明",
//		    left:70
//		});
//		linkEditor.on("click", function()
//		{
//		   mx.indicate("info", "生效时间与失效时间为时间范围！");
//		});
//    	me.upContainer.addControl(linkEditor);
    }
    
    /**
     * 创建查询按钮
     */
    function _initSearch(){
    
    	me.searchButton = new mx.controls.Button({
    		text: "查询",
    		left: 20,
    		padding: "2px",
    		onclick: me.controller._btnSearch_onclick
    	});
    	me.downContainer.addControl(me.searchButton);
    }
    
    /**
     * 创建新增按钮
     */
    function _initAdd(){
    
    	me.addButton = new mx.controls.Button({
    		text: "新增",
    		left: 40,
    		padding: "2px",
    		onclick: me.controller._btnNew_onclick
    	});
    	me.downContainer.addControl(me.addButton);
    }
    
    /**
     * 创建编辑按钮
     */
    function _initEdit(){
    
    	me.editButton = new mx.controls.Button({
    		text: "编辑",
    		left: 60,
    		padding: "2px",
    		onclick: me.controller._btnEdit_onclick
    	});
    	me.downContainer.addControl(me.editButton);
    }
    
    /**
     * 创建生效按钮
     */
    function _initEffect(){
    
    	me.effectButton = new mx.controls.Button({
    		text: "生效",
    		left: 80,
    		padding: "2px",
    		onclick: me.controller._btnEffect_onclick
    	});
    	me.downContainer.addControl(me.effectButton);
    }
    
    /**
     * 创建失效按钮
     */
    function _initInvalid(){
    
    	me.invalidButton = new mx.controls.Button({
    		text: "失效",
    		left: 100,
    		padding: "2px",
    		onclick: me.controller._btnInvalid_onclick
    	});
    	me.downContainer.addControl(me.invalidButton);
    	
    	me.explainBtn = new mx.controls.Button({text:"说明",left:120,onclick:declare});
    	me.downContainer.addControl(me.explainBtn);
    }
    
    /**
     * 说明按钮
     */
    function declare(){
		var message = " 生效时间与失效时间为时间范围"+ "\n";
		
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
    
    /**
     * 创建合同准入成员管理按钮
     */
    function _initMember(){
    
    	me.memberButton = new mx.controls.Button({
			text: "合同准入成员管理",
    		left: 120,
    		padding: "2px",
    		onclick: me.controller._btnMember_onclick
    	});
    	me.downContainer.addControl(me.memberButton);
    	me.memberButton.setVisible(false);
    }
    
    function _initParams(){
    	
    	me.marketId = me.marketDropEditor.value;
    	me.startTime = me.startTimeEditor.value == null ? "" : me.startTimeEditor.value;
    	me.endTime = me.endTimeEditor.value == null ? "" : me.endTimeEditor.value;
    	var params = {marketid:me.marketId,starteffectivedate:me.startTime,endeffectivedate:me.endTime};
    	return params;
    }
    
    /**
     * 创建树形列表
     */
    function _initDataGrid()
    {
        var restUrl = "~/rest/cocontracttypeinfo/queryTree";
        
        /* 初始化 DataGrid */
        _dataGrid = new mx.controls.TreeListView({   
			type: "remote",
			loadMeta:false,
			displayRowNumber:true,
    		displayCheckBox:false,
    		displayImage:false,
    		displayPrimaryKey:false,//列表是否显示主键
    		primaryKey:"contracttypeid",
			baseUrl : contractTypeInfo.mappath(restUrl),
			filter: {marketid:me.marketId,starteffectivedate:me.startTime,endeffectivedate:me.endTime},
			columns:[
	        {	name: "contracttypeid", text: "合同类型" , align:"center",	editorType:"DropDownEditor",width:"12.5%"},
//	        {	name: "typename", text: "合同类型" , align:"center"	},
	        {	name: "supertypeid", text: "合同父类型" , align:"center",editorType:"DropDownEditor",width:"12.5%"},
//	        {	name: "isdefine", text: "是否自定义" , align:"center",visible:false	},
	        {	name: "starteffectivedate", text: "生效时间" , align:"center",	width:"12.5%"},
	        {	name: "endeffectivedate", text: "失效时间" , align:"center",width:"12.5%"},
//	        {	name: "updatetime", text: "信息更新时间" , align:"center",visible:false},
	        {	name: "updatepersonid", text: "维护人" , align:"center",width:"12.5%"	},
	        {	name: "marketid", text: "业务场景" , align:"center",editorType:"DropDownEditor",width:"12.5%"},
	        {	name: "effectiveflag", text: "状态" , align:"center",width:"12.5%",renderCell:function(p_item, $p_cell){
	        		var value = p_item.getValue("effectiveflag");
	        		if(value == 0){
	        			$p_cell.html("使用");
	        		}else if(value == 1){
	        			$p_cell.html("失效");
	        			$p_cell.css("color","red")
	        		}
	        	}	
	        },
	        {	name: "description", text: "说明" , align:"center",width:"12.5%"	}
            ]
        });
        
        me.hSplit.addControl(_dataGrid,1);
//        _dataGrid.filter={marketid:me.marketId,starteffectivedate:me.startTime,endeffectivedate:me.endTime};
        _dataGrid.load();
//        _dataGrid.setFilter(_initParams());
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