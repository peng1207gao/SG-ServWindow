$ns("jfcontractstate.views");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGrid");


jfcontractstate.views.JfContractStateMainView = function()
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
    	_initHSplit();//创建上下两部分
    	_initUpPanel();//创建存放查询条件的面板
    	_initDownPanel();//创建存放操作按钮的面板
    	_initQueryCondition();//创建查询条件
    	_initButton();//创建查询按钮和关闭按钮
	    _initDataGrid();
    	_initDetailWindow();
        me.on("activate", me.controller._onactivate);
    }
    
    function _initHSplit(){
    
    	me.hSplit = new mx.containers.HSplit({
    		rows: "110,auto"
    	});
    	me.addControl(me.hSplit);
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
    		text: "合同名称:",
	    	textAlign: "center",
	    	verticalAlign: "middle",
	    	width:80
    	});
    	me.container1.addControl(me.nameLabel);
    	me.contractName = new mx.editors.TextEditor({
    		width: 120,
    		hint: "合同名称"
    	});
    	me.container1.addControl(me.contractName);
    	
    	//纸质合同编号查询框
    	me.noLabel = new mx.controls.Label({
    		text: "纸质合同编号:",
	    	textAlign: "center",
	    	verticalAlign: "middle",
	    	width: 90
    	});
    	me.container1.addControl(me.noLabel);
    	me.contractNo = new mx.editors.TextEditor({
    		width: 120,
    		hint: "纸质合同编号"
    	});
    	me.container1.addControl(me.contractNo);
    	
    	//业务场景下拉框
    	me.marketLabel = new mx.controls.Label({
    		text: "业务场景:",
	    	textAlign: "center",
	    	verticalAlign: "middle",
	    	width: 80
    	});
    	me.container1.addControl(me.marketLabel);
    	me.market = new mx.editors.DropDownEditor({
    		allowEditing: false,  // 设置是否允许编辑
			displayCheckBox: false,
			valueSeparator: "#",
			displayMember: "name",
			valueMember: "value",
			dropDownAnimation: "slideDown",
			url: jfcontractstate.mappath("~/rest/sjjcjfdljyhtwb/getWorkingUnit")
    	});
    	//默认为当前登录场景
    	var restClient = mx.rpc.RESTClient();
    	var result = restClient.getSync(jfcontractstate.mappath("~/rest/sjjcjfdljyhtwb/initMarket"));
    	if (result.successful) {
			var value = result.resultValue.value;
			var name = result.resultValue.name;
			if (value != undefined && name != undefined) {
				me.market.setValue(value,name,true); 
			}
		}
    	me.container1.addControl(me.market);
    }
    
    function _initButton(){
    	me.container2 = new mx.containers.Container({
    		id: "contaniner",
    		padding: "2px"
    	});
    	me.downPanel.addControl(me.container2);
    	//查询按钮
    	me.searchBtn = new mx.controls.Button({
    		text: "查询",
    		left: 20,
    		onclick: me.controller._btn_Search
    	});
    	me.container2.addControl(me.searchBtn);
    	//关闭按钮
    	me.closeBtn = new mx.controls.Button({
    		text: "关闭",
    		left: 40,
    		onclick: me.controller._btn_Close
    	});
    	me.container2.addControl(me.closeBtn);
    }
    
    /**
     * 获取查询条件
     */
    me._initParams = function(){
    	var params = {contractname:me.contractName.value,contractno:me.contractNo.value,companyid:me.market.value};
    	return params;
    }
    
    function _initDataGrid()
    {
        var restUrl = "~/rest/sjjcjfhtlzxx/";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : jfcontractstate.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "objId"
        });
        
        /* 初始化 DataGrid */
        _dataGrid = new mx.datacontrols.DataGrid({   
			// 构造查询属性。
			alias: "jfcontractstateJfContractStateMainViewDataGrid",
			
			columns:[
	        {	name: "objId", caption: "记录ID" , editorType: "TextEditor"	},
//	        {	name: "companyid", caption: "运行单位编码" , editorType: "TextEditor"	},
	        {	name: "contractno", caption: "纸质合同编号" , editorType: "TextEditor",width: "35%"	},
	        {	name: "contractname", caption: "合同名称" , editorType: "TextEditor",width: "25%"	},
	        {	name: "contractstate", caption: "合同状态" , editorType: "TextEditor"	,width: "15%"},
	        {	name: "companyname", caption: "业务场景" , editorType: "TextEditor",width: "25%"	}
//	        {	name: "contractid", caption: "合同ID" , editorType: "TextEditor"	},
//	        {	name: "intodate", caption: "流转时间" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
//	        {	name: "contractsuggestion", caption: "合同审批意见" , editorType: "TextEditor"	}
            ],
            // 构造列排序条件，如果有多列，则以逗号分隔。例sorter: "school ASC, class DESC"
            displayCheckBox: false,
	        displayPrimaryKey:false,//列表是否显示主键
	        displayRowNumber: true,
            allowEditing: false, //列表默认不可编辑
	        pageSize : 20,
            entityContainer: gridEntityContainer
        });
        
        me.hSplit.addControl(_dataGrid,1);
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