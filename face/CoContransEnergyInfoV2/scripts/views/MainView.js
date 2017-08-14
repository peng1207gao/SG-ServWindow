$ns("CoContransEnergyInfoV2.views");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGridSearchBox");
$import("mx.datacontrols.DataGrid");
$import("mx.containers.HSplit");
$import("mx.containers.Panel");
$import("mx.containers.Container");

//输电费及网损信息维护
CoContransEnergyInfoV2.views.MainView = function()
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
    	_initBtn();
	    
    	_initDetailWindow();
    	_dataGrid.load();
    }
    
    function _initBtn() {
    	
    	//页面上面分成上下两部分
    	me.mSplit = new mx.containers.HSplit({rows:"55px,auto",borderThick:1});
    	me.addControl(me.mSplit);
    	me.panel = new mx.containers.Panel({title:"业务操作",width:"100%"});
    	me.mSplit.addControl(me.panel,0);//添加panel
    	me.container = new mx.containers.Container({padding:"2px",width:"100%"});
    	me.panel.addControl(me.container);//添加container
    	
    	if(me.params.readType != '1'){//1的时候只读
    		//新增按钮
    		var btn_add = new mx.controls.Button({text : "新增",left : 20});
    		me.addControl(btn_add);
    		//新增按钮点击事件
    		btn_add.on("click", function() {
    			me.controller.contractid = me.contractid;
    			me.controller._btnNew_onclick();
    		});
    		me.container.addControl(btn_add);
    	
    		//编辑按钮
    		var btn_update = new mx.controls.Button({text : "编辑",left : 30});
    		me.addControl(btn_update);
    		//编辑按钮点击事件
    		btn_update.on("click", function() {
    			me.controller._btnEdit_onclick();
    		});
    		me.container.addControl(btn_update);

    		//删除按钮
    		var btn_delete = new mx.controls.Button({text : "删除",left : 40});
    		me.addControl(btn_delete);
    		//删除按钮点击事件
    		btn_delete.on("click", function() {
    			me.controller._btnDelete_onclick();
    		});
    		me.container.addControl(btn_delete);

    		var btn_close = new mx.controls.Button({text : "关闭",left : 50});
    		me.addControl(btn_close);
    		//关闭按钮点击事件
    		btn_close.on("click", function() {
    			me.viewPort.close();//关闭当前页面
    		});
    		me.container.addControl(btn_close);
    	}
    	_initDataGrid();
    }
    
    function _initDataGrid()
    {
        var restUrl = "~/rest/cocontransenergyinfov2/";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : CoContransEnergyInfoV2.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "guid"
        });
        
        /* 初始化 DataGrid */
        _dataGrid = new mx.datacontrols.DataGrid({   
			// 构造查询属性。
			alias: "CoContransEnergyInfoV2MainViewDataGrid",
			columns:[
	        {	name: "guid", caption: "GUID" , editorType: "TextEditor", align:"center", dataAlign:"center"},
	        {	name: "transmission", caption: "合同输电方ID" , editorType: "TextEditor", align:"center", dataAlign:"center"	},
	        {	name: "participantname", caption: "合同输电方" , editorType: "TextEditor", align:"center", dataAlign:"center"	},
	        {	name: "name1", caption: "输电费用收取方式" , editorType: "TextEditor", align:"center", dataAlign:"center"	},
	        {	name: "cost", caption: "输电费" , editorType: "TextEditor", align:"center", dataAlign:"center"},
	        {	name: "price", caption: "输电及网损价" , editorType: "TextEditor", align:"center", dataAlign:"center"	},
	        {	name: "transphone", caption: "说明" , editorType: "TextEditor", align:"center", dataAlign:"center"	},
	        {	name: "name", caption: "经济属性" , editorType: "TextEditor", align:"center", dataAlign:"center"	},
	        {	name: "taxrate", caption: "税率" , editorType: "TextEditor", align:"center", dataAlign:"center"	},
	        {	name: "energypercent", caption: "电量百分比" , editorType: "TextEditor", align:"center", dataAlign:"center"	},
	        {	name: "loss", caption: "输电损耗" , editorType: "TextEditor", align:"center", dataAlign:"center"	},
	        {	name: "startdate", caption: "开始时间" , editorType: "TextEditor", align:"center", dataAlign:"center",dataType:"date",formatString:"yyyy-MM-dd"	},
	        {	name: "enddate", caption: "截止时间" , editorType: "TextEditor", align:"center", dataAlign:"center",dataType:"date",formatString:"yyyy-MM-dd" }
            ],
            // 构造列排序条件，如果有多列，则以逗号分隔。例sorter: "school ASC, class DESC"
            displayCheckBox: true,
	        displayPrimaryKey:false,//列表是否显示主键
            allowEditing: false, //列表默认不可编辑
	        pageSize : 20,
            entityContainer: gridEntityContainer,
            onload : initG
        });
        _dataGrid.setFilter({contractId:me.contractid});
        me.addControl(_dataGrid);
        me.mSplit.addControl(_dataGrid,1);
    }

    function initG(){
    	_dataGrid.columns.transmission.setVisible(false);
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