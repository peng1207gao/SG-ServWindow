$ns("resolveelectricquantitytounit.views");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGridSearchBox");
$import("mx.datacontrols.DataGrid");
$import("mx.containers.Panel");


resolveelectricquantitytounit.views.ResolveElectricQuantityToUnitMainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    //网格列表
    var _dataGrid = null;
    //表单窗口对象
    var _detailWin = null;

    me.contractids = null;//保存contractid数组
    
    var _ResolveDataTable = null;
    base.init = me.init;
    me.init = function()
    {
        base.init();
        _initControls();
    };
    
  	me.load = function()
    {
    	//加载第一个表单信息
    	loadDataGrid();
    }
    
    function loadDataGrid(){
    	_dataGrid.setFilter(JSON.stringify({"contractids":me.contractids}));
    	_dataGrid.load();
    	 
//    	var params = {"contractids" : me.contractids.substring(0,me.contractids.length - 1)}
    	_ResolveDataTable.setFilter(JSON.stringify({"clear":true, "contractids":me.contractids}));
    	_ResolveDataTable.load();
    }
    
    function _initControls()
    {
    	_initHSplit();
    	_initPanel();
    	_initButton();
    	_initDataGridHSplit();
	    _initDataGrid();
    	_initDetailWindow();
        me.on("activate", me.controller._onactivate);
    }
    
    function _initHSplit(){
   		me.mainSplit = new mx.containers.HSplit({
			rows : "60,auto",width:"100%",
			borderThick : 1
		});
		me.addControl(me.mainSplit);
    }
    
    function _initPanel(){
    	me.panel = new mx.containers.Panel({ name:"panel", title:"业务操作", width:"100%", height: 50});
    	me.mainSplit.addControl(me.panel, 0);
    }
    
    function _initButton(){
		var _btnResolve = new mx.controls.Button({ text: "分解",left:50,top:1 });
		var _btnSave = new mx.controls.Button({ text: "保存",left:70,top:1 });
		var _btnChooseUnit = new mx.controls.Button({ text: "指定经济机组",left:90,top:1 });
		_btnResolve.on("click", me.resolveContractAccessory);
		_btnSave.on("click", me.saveBusiGeneratorQty);
		_btnChooseUnit.on("click", me.controller.getEconUnitView);
		me.panel.addControl(_btnResolve);
		me.panel.addControl(_btnSave);
		me.panel.addControl(_btnChooseUnit);
    }
    
    function _initDataGridHSplit(){
    	me.dataGridSplit = new mx.containers.HSplit({
			rows : "50%,50%",width:"100%",
			borderThick : 1
		});
		me.mainSplit.addControl(me.dataGridSplit, 1);
    }
    
    function _initDataGrid()
    {	
        var restUrl = "~/rest/resolveelectricquantitytounit/";
        var restUrl1 = "~/rest/resolveelectricquantitytounit/contractAccessory";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : resolveelectricquantitytounit.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "guid"
        });
        var gridEntityContainer2 = new mx.datacontainers.GridEntityContainer({
            baseUrl : resolveelectricquantitytounit.mappath(restUrl1),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "guid",
            loadMeta:false
        });
        /* 初始化 DataGrid */
        _dataGrid = new mx.datacontrols.DataGrid({   
			// 构造查询属性。
			alias: "resolveelectricquantitytounitResolveElectricQuantityToUnitMainViewDataGrid",
			columns:[
			{ name: "timeno",caption: "时间段序号",width:80,dataAlign:"center"}, 
	        { name: "contractid", caption: "合同ID" , editorType: "TextEditor"	},
	        { name: "contractname", caption: "合同名称",width:"250" , editorType: "TextEditor", align:"center", dataAlign:"center"},
	        { name: "startdate", caption: "开始时间" , editorType: "DateTimeEditor", formatString: "yyyy-MM-dd", align:"center", dataAlign:"center"},
	        { name: "enddate", caption: "结束时间" , editorType: "DateTimeEditor", formatString: "yyyy-MM-dd", align:"center", dataAlign:"center"},
	        { name: "peakpower", caption: "尖峰" ,width:"100", align:"center", dataAlign:"center"} ,
            { name: "toppower", caption: "峰" ,width:"100", align:"center", dataAlign:"center"} ,
            { name: "flatpower", caption: "平", width:"100", align:"center", dataAlign:"center" } ,
            { name: "valleypower", caption: "谷", width:"100", align:"center", dataAlign:"center" } ,
//          { name: "lowest", caption: "低谷", width:"100", align:"center", dataAlign:"center" } ,
            { name: "totalpower", caption: "总", width:"100", align:"center", dataAlign:"center" } 
            ],
            displayCheckBox: false,
	        displayPrimaryKey:false,//列表是否显示主键
            allowEditing: false, //列表默认不可编辑
	        pageSize : 300,
	        onload:setVisible,
            entityContainer: gridEntityContainer
        });
        
        _ResolveDataTable = new mx.datacontrols.DataGrid({ 
            columns:  [ 
            	{ name: "guid",caption: "guid"},
                { name: "timeno",caption: "时间段序号",width:80,dataAlign:"center", readOnly:true}, 
                { name: "purchaseunit",caption: "购方经济机组", align:"center", dataAlign:"center", readOnly:true }, 
                { name: "sellerunit", caption: "售方经济机组", align:"center", dataAlign:"center", readOnly:true } ,
                { name: "startdate", caption: "开始时间" , editorType: "DateTimeEditor", formatString: "yyyy-MM-dd", 
                	align:"center", dataAlign:"center", readOnly:true},
    			{ name: "enddate", caption: "结束时间" , editorType: "DateTimeEditor", formatString: "yyyy-MM-dd", 
    				align:"center", dataAlign:"center", readOnly:true},
                { name: "peakValue", caption: "尖峰", align:"center", dataAlign:"center", editorType: "NumberEditor", valueType:"double" } ,
                { name: "topValue", caption: "峰", align:"center", dataAlign:"center", editorType: "NumberEditor", valueType:"double"  } ,
                { name: "flatValue", caption: "平", align:"center", dataAlign:"center", editorType: "NumberEditor", valueType:"double"  } ,
                { name: "valleyValue", caption: "谷", align:"center", dataAlign:"center", editorType: "NumberEditor", valueType:"double"  } ,
                { name: "totalValue", caption: "总", align:"center", dataAlign:"center", editorType: "NumberEditor", valueType:"double" },
                { name: "purchaseunitid",caption: "购方经济机组id", align:"center", dataAlign:"center", readOnly:true },
                { name: "sellerunitid", caption: "售方经济机组id", align:"center", dataAlign:"center", readOnly:true } 
//                { name: "contractid", caption: "合同id", align:"center", dataAlign:"center"}
			],
            allowEditing: true, //列表可编辑
  	        pageSize : 10,
  	        onload: resolveDataTableVisible,
  	        oncellediting:recordChanges,//修改电量值之后，记录修改过的值
  	        oncelledited:recordChanged,
            entityContainer: gridEntityContainer2
        });
        me.dataGridSplit.addControl(_dataGrid,0);
        me.dataGridSplit.addControl(_ResolveDataTable,1);
    }
    function resolveDataTableVisible(){
    	_ResolveDataTable.columns["purchaseunitid"].setVisible(false);
    	_ResolveDataTable.columns["sellerunitid"].setVisible(false);
    }
    var changesList = null;
    var editValue = null;
    /**
     * 记录修改过的记录，点击保存后，将修改过的数据传到后台，更新后台数据进行保存
     */
    function recordChanges(p_item){
    	editValue = p_item.item.values[p_item.column.name];
    }
    
    function recordChanged(p_item){
    	if(editValue != p_item.item.values[p_item.column.name]){
    		editValue = p_item.item.values[p_item.column.name];
	    	var contractid = p_item.item.values["contractid"];
	    	var purchaseunitid = p_item.item.values["purchaseunitid"];
	    	var sellerunitid = p_item.item.values["sellerunitid"];
	    	var timeno = p_item.item.values["timeno"];
	    	var period = null;
	    	if(p_item.column.name == "peakValue"){
	    		period = '2';
	    	} else if(p_item.column.name == "topValue"){
	    		period = '3';
	    	} else if(p_item.column.name == "flatValue"){
	    		period = '4';
	    	} else if(p_item.column.name == "valleyValue"){
	    		period = '5';
	    	} else if(p_item.column.name == "totalValue"){
	    		period = '1';
	    	}
	    	var data ={"contractid":contractid, "purchaseunitid":purchaseunitid, 
	    		"sellerunitid":sellerunitid, "timeno":timeno, "period":period,"value":editValue};
	    	if(changesList == null){
	    		changesList = new Array();
	    	}
	    	changesList.push(data);
    	}
    }
    
 	function setVisible(){
    	_dataGrid.columns["contractid"].setVisible(false);
    	
    	//使 换页 下拉框失效
    	var tenObj=_dataGrid.$e.find(".pageNaviBar .comboEditor").clone();
    	_dataGrid.$e.find(".pageNaviBar").find(".comboEditor").remove();
    	tenObj.prependTo(_dataGrid.$e.find(".pageNaviBar"));
    }
    
    me.resolveContractAccessory = function(){
    	var isResolve = true;
    	if(_ResolveDataTable.itemCount != 0){
    		isResolve = confirm("选中合同已经有分解电量数据，确认重新分解？");
    	}
    	if(isResolve){
    		var params = {"contractids" : me.contractids}
    		_ResolveDataTable.setFilter(JSON.stringify(params));
    		_ResolveDataTable.load({dataParams:{pageIndex:_ResolveDataTable.pageIndex,pageSize:_ResolveDataTable.pageSize}});
    	}
    }
    
    me.saveBusiGeneratorQty = function(){
    	var restUrl = resolveelectricquantitytounit.mappath("~/rest/resolveelectricquantitytounit/saveBusiGeneratorQty");
    	me.restClient = new mx.rpc.RESTClient();
    	var s = me.restClient.sendSync(restUrl, "POST", JSON.stringify({"items":changesList}));
    	if(typeof(s)!='undefined'&&s!=null){
	    	if(s.successful == true && s.resultValue == "success"){
	    		mx.indicate("info","数据保存成功");
	    		changesList = null;
	    	} else if (s.successful == true && s.resultValue == "saved"){
	    		mx.indicate("info","数据已经保存");
	    		changesList = null;
	    	} else if (s.successful == true && s.resultValue == "empty"){
	    		mx.indicate("info","没有要保存的数据");
	    		changesList = null;
	    	} else {
	    		mx.indicate("info","数据保存失败");
	    	}
    	}
    }
    
    /**
     * 初始化表单视图窗口对象
     */
    function _initDetailWindow(){
    	_detailWin = new utils.commonFun.WinMananger.create({
			reusable: true,//是否复用
			width:"50%",
			height:"70%",
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
    
    
    me.getResolveDataTable = function(){
    	return _ResolveDataTable;
    }
	me.endOfClass(arguments)
    return me;
};