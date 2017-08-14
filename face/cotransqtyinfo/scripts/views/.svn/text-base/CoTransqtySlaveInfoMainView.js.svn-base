$ns("cotransqtyinfo.views");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGridSearchBox");
$import("mx.datacontrols.ComplexGrid");


cotransqtyinfo.views.CoTransqtySlaveInfoMainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    //网格列表
    var _dataGrid = null;
    //表单窗口对象
    var _detailWin = null;
    
    //输电信息id
    var objID = null;

    base.init = me.init;
    me.init = function()
    {
        base.init();
        _initControls();
    };
    
    function _initControls()
    {
    	_initHSplit();//划分上下部分
    	_initPanel();//创建业务操作面板
    	_initContainer();//创建按钮容器
    	_initButton();
//    	_initNew();//创建新增按钮
//    	_initEdit();//创建修改按钮
//    	_initDelete();//创建删除按钮
	    _initDataGrid();
    	_initDetailWindow();
        me.on("activate", me.controller._onactivate);
    }
    
    function _initHSplit(){
    
    	me.hSplit = new mx.containers.HSplit({
    		rows : "60,auto"
    	});
    	me.addControl(me.hSplit);
    }
    
    function _initContainer(){
    	me.container = new mx.containers.Container({
    	
    		id: "contaniner",
    		padding: "2px"
    	});
    	me.panel.addControl(me.container);
    }
    
    function _initPanel(){
    	
    	me.panel = new mx.containers.Panel({
    		id:"Panel",
			border:"1",
			title:"业务操作"
    	});
    	me.hSplit.addControl(me.panel);
    }
    
    function _initButton(){
    	if(me.controller.readType != '1'){//1的时候只读
	    	_initNew();//创建新增按钮
	    	_initEdit();//创建修改按钮
	    	_initDelete();//创建删除按钮
    	}
    }
    
    function _initNew(){
	    me.InsertButton = new mx.controls.Button({
			text: "新增",
			left: 20,
			padding: 2,
			onclick:me.controller._btnNew_onclick
		});
		me.container.addControl(me.InsertButton);
    }
    
    function _initEdit(){
    	me.UpdateButton = new mx.controls.Button({
			text: "修改",
			left: 40,
			onclick:me.controller._btnEdit_onclick
		});
		me.container.addControl(me.UpdateButton);
    }
    
    function _initDelete(){
    	me.DeleteButton = new mx.controls.Button({
			text: "删除",
			left: 60,
			onclick:me.controller._btnDelete_onclick
		});
		me.container.addControl(me.DeleteButton);
    }
    
    function _initDataGrid()
    {
        var restUrl = "~/rest/cotransqtyinfo/queryElec";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : cotransqtyinfo.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "guid"
        });
        
        /* 初始化 DataGrid */
        _dataGrid = new mx.datacontrols.DataGrid({   
			// 构造查询属性。
			alias: "cotransqtyinfoCoTransqtySlaveInfoMainViewDataGrid",
			columns:[
	        {	name: "guid", caption: "GUID" , editorType: "TextEditor"	},
//	        {	name: "transinfoid", caption: "输电信息ID" , editorType: "TextEditor",visible: false	},
	        {	name: "starttime", caption: "开始时间" , editorType: "TextEditor",width:"25%"	},
	        {	name: "endtime", caption: "结束时间" , editorType: "TextEditor",width:"25%"	},
	        {	name: "power", caption: "电力" , editorType: "TextEditor"	,width:"25%"},
	        {	name: "qtytype", caption: "峰谷属性" , editorType: "TextEditor",width:"25%"	}
//	        {	name: "explanation", caption: "??" , editorType: "TextEditor"	}
            ],
            // 构造列排序条件，如果有多列，则以逗号分隔。例sorter: "school ASC, class DESC"
	        displayRowNumber: true,
            displayCheckBox: true,
	        displayPrimaryKey:false,//列表是否显示主键
            allowEditing: false, //列表默认不可编辑
	        pageSize : 20,
            entityContainer: gridEntityContainer
        });
        
        me.hSplit.addControl(_dataGrid,1);
        _dataGrid.setFilter({transinfoid:me.controller.objId});
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