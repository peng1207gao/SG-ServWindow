$ns("tempfieldconfig.views");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGridSearchBox");
$import("mx.datacontrols.DataGrid");
$import("mx.containers.Panel");


tempfieldconfig.views.TempfieldConfigMainView = function()
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
    	_initCondition();//创建查询条件
    	_initOperate();//创建操作按钮
	    _initDataGrid();
    	_initDetailWindow();
        me.on("activate", me.controller._onactivate);
    }
    
    function _initHSplit(){
    	me.hSplit = new mx.containers.HSplit({
    		rows: "120px,auto"
    	});
    	me.addControl(me.hSplit);
    }
    
    function _initCondition(){
    	me.queryPanel = new mx.containers.Panel({
    		id:"queryPanel",
			border:"1",
			height: "50%",
			title:"业务查询"
    	});
    	me.hSplit.addControl(me.queryPanel);
    	me.queryContainer = new mx.containers.Container({
    		id: "queryContainer",
    		padding: "2px"
    	});
    	me.queryPanel.addControl(me.queryContainer);
    	
    	me.tempLabel = new mx.controls.Label({
    		text: "对应合同范本:",
		    textAlign: "center",
		    verticalAlign: "middle"
    	});
    	me.queryContainer.addControl(me.tempLabel);
    	//合同范本下拉框
    	me.contractTemp = utils.dropDownEditor.ContractTemplate.ContractTemplateDropDownEditor(false,null);
    	me.contractTemp.setWidth(200);
    	me.queryContainer.addControl(me.contractTemp);
    	
    	me.nameLabel = new mx.controls.Label({
    		text: "对应范本字段:",
		    textAlign: "center",
		    verticalAlign: "middle"
    	});
    	me.queryContainer.addControl(me.nameLabel);
    	me.tempName = new mx.editors.TextEditor({
    		"width" : "200px",
    		"hint" : "对应范本字段" //指定显示的提示文字。
    	});
    	me.queryContainer.addControl(me.tempName);
    }
    
    function _initOperate(){
    	me.btnPanel = new mx.containers.Panel({
    		id:"btnPanel",
			border:"1",
			height: "50%",
			title:"业务操作"
    	});
    	me.hSplit.addControl(me.btnPanel);
    	me.btnContainer = new mx.containers.Container({
    		id: "btnContainer",
    		padding: "2px"
    	});
    	me.btnPanel.addControl(me.btnContainer);
    	
    	me.searchBtn = new mx.controls.Button({
    		text: "查询",
    		left: 10,
    		onclick: me.controller._btnSearch_onclick
    	});
    	me.btnContainer.addControl(me.searchBtn);
    	
    	me.addBtn = new mx.controls.Button({
    		text: "新增",
    		left: 20,
    		onclick: me.controller._btnNew_onclick
    	});
    	me.btnContainer.addControl(me.addBtn);
    	
    	me.editBtn = new mx.controls.Button({
    		text: "修改",
    		left: 30,
    		onclick: me.controller._btnEdit_onclick
    	});
    	me.btnContainer.addControl(me.editBtn);
    	
    	me.deleteBtn = new mx.controls.Button({
    		text: "删除",
    		left: 40,
    		onclick: me.controller._btnDelete_onclick
    	});
    	me.btnContainer.addControl(me.deleteBtn);
    }
    
    /**
     * 获取查询条件
     */
    me.getParams = function(){
    	var params = {contracttemplatecode:me.contractTemp.value,configFieldName:me.tempName.value};
    	return params;
    }
    
    function _initDataGrid()
    {
        var restUrl = "~/rest/cotempclienfieldconfig_mk/";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : tempfieldconfig.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "sheetid"
        });
        
        /* 初始化 DataGrid */
        _dataGrid = new mx.datacontrols.DataGrid({   
			// 构造查询属性。
			alias: "tempfieldconfigTempfieldConfigMainViewDataGrid",
			
			columns:[
	        {	name: "sheetid", caption: "主键" , editorType: "TextEditor"	},
	        {	name: "canSheetid", caption: "表字段名称" , editorType: "TextEditor",width: "33%"	},
	        {	name: "configFieldName", caption: "对应范本字段" , editorType: "TextEditor"	,width: "33%"},
//	        {	name: "personid", caption: "维护人ID" , editorType: "TextEditor"	},
//	        {	name: "marketid", caption: "维护人市场" , editorType: "TextEditor"	},
//	        {	name: "updatedate", caption: "维护时间" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "contracttemplatecode", caption: "对应合同范本" , editorType: "TextEditor"	,width: "33%"}
            ],
            // 构造列排序条件，如果有多列，则以逗号分隔。例sorter: "school ASC, class DESC"
            displayCheckBox: true,
	        displayPrimaryKey:false,//列表是否显示主键
	        displayRowNumber:true,
            allowEditing: false, //列表默认不可编辑
	        pageSize : 10,
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
			height:200,
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