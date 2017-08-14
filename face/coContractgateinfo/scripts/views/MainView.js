$ns("coContractgateinfo.views");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGridSearchBox");
$import("mx.datacontrols.DataGrid");
$import("mx.containers.Panel");

coContractgateinfo.views.MainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
   // me.contractid="8a8192163eca7b00013ecb372c7b0095";
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
    	initLayout();//初始化布局
	    _initDataGrid();
    	_initDetailWindow();
        me.on("activate", me.controller._onactivate);
    }
    
    /**
     * 页面初始化布局(主要控制页面控件的布局)
     */
    function initLayout(){
    	me.hSplit = new mx.containers.HSplit({
    	    rows : "65px,auto",
    	    borderThick:1
    	});
		me.addControl(me.hSplit);
//		me.panel = new mx.containers.Panel({ name:"panel", title:"业务查询", width:"100%", height: 50}); 
		me.panel1 = new mx.containers.Panel({ name:"panel", title:"业务操作", width:"100%" }); 
    	
//		me.hSplit.addControl(me.panel,0);
		me.hSplit.addControl(me.panel1,0);
		
		me.containers = new mx.containers.Container({
			heigth:"100%",
			padding:"2px"
		});
		me.panel1.addControl(me.containers);
//		me.containers1 = new mx.containers.Container({
//			heigth:"100%",
//			border:"1px",
//			padding:"2px"
//		});
//		me.panel1.addControl(me.containers1);
		if (me.params.readType != '1') {// 1的时候只读
		var button1 = new mx.controls.Button({ 
			text: "新增购售电方",left:50,
			onclick:function(){
			me.controller._btnNew_onclick();
    	}}); 
		me.containers.addControl(button1);
		var button2 = new mx.controls.Button({ 
			text: "新增输电方",left:60,
			onclick:function(){
			me.controller._btnNew_onclick1();
    	}}); 
		me.containers.addControl(button2);
		var button3 = new mx.controls.Button({ 
			text: "删除",left:70,
			onclick:function(){
			me.controller._btnDelete_onclick();
    	}}); 
		me.containers.addControl(button3);	
		}
    }
    
    function _initDataGrid()
    {
        var restUrl = "~/rest/cocontractgateinfo/";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : coContractgateinfo.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "guid"
        });
        
        /* 初始化 DataGrid */
        _dataGrid = new mx.datacontrols.DataGrid({   
			// 构造查询属性。
			alias: "coContractgateinfoMainViewDataGrid",
			columns:[
	         {	name: "displaytype", caption: "合同角色" , editorType: "DropDownEditor", width:"25%"	},
	         {	name: "busiunitid", caption: "交易单元名称" , editorType: "DropDownEditor"	, width:"25%"},
	         {	name: "participantid", caption: "所属市场成员" , editorType: "DropDownEditor", width:"25%"	},
	         {	name: "gateid", caption: "计量关口" , editorType: "DropDownEditor", width:"25%"},
	         {	name: "guid", caption: "GUID" , editorType: "TextEditor"	}
            ],
            // 构造列排序条件，如果有多列，则以逗号分隔。例sorter: "school ASC, class DESC"
            displayCheckBox: true,
	        displayPrimaryKey:false,//列表是否显示主键
            allowEditing: false, //列表默认不可编辑
            allowPaging:true,
	        pageSize : 20,
            entityContainer: gridEntityContainer
        });
        _dataGrid.setFilter({contractID:me.contractid});
        me.hSplit.addControl(_dataGrid,1);
    	//加载表格数据
    	_dataGrid.load();
    }
    
    /**
     * 重置按钮项
     */
    function _resetToolBarItems(){
    	//去除保存按钮
		_dataGrid.toolBar.removeByIndex(1);
		_dataGrid.toolBar.removeByName("save");
		_dataGrid.toolBar.removeByName("moveup");
		_dataGrid.toolBar.removeByName("movedown");
		//插入编辑按钮
		//_dataGrid.toolBar.insertItem(2,"-",true);
		//_dataGrid.toolBar.insertItem(3,{ name: "edit", text: mx.msg("EDIT"), toolTip: mx.msg("EDIT"), imageKey : "edit", onclick: me.controller._btnEdit_onclick},true);
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