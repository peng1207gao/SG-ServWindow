$ns("CoContransEnergyInfo.views");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGridSearchBox");
$import("mx.datacontrols.ComplexGrid");
$import("mx.datacontrols.DataGrid");
$import("mx.rpc.RESTClient");
$import("mx.containers.HSplit");
$import("mx.containers.Panel");
$import("mx.containers.Container");

//输电方
CoContransEnergyInfo.views.MainView = function()
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
    			me.controller.objId = me.contractid;
    			me.controller.objName = me.params.coName;
    			//me.objId = "4028804a3d4cc6df013d4d18dc220004";
    			me.controller._btnNew_onclick();
    			//me.contractName;
    		});
    		me.container.addControl(btn_add);
    	
    		//删除按钮
    		var btn_delete = new mx.controls.Button({text : "删除",left : 30});
    		me.addControl(btn_delete);
    		//删除按钮点击事件
    		btn_delete.on("click", function() {
    			me.controller._btnDelete_onclick();
    		});
    		me.container.addControl(btn_delete);
    	
    		//关闭按钮
    		var btn_close = new mx.controls.Button({text : "关闭",left : 40});
    		me.addControl(btn_close);
    		//关闭按钮点击事件
    		btn_close.on("click", function() {
    			me.viewPort.close();//关闭当前页面
    		});
    		me.container.addControl(btn_close);
    	}

	    _initDataGrid();
    	_initDetailWindow();
    }
    
    function _initDataGrid()
    {
        var restUrl = "~/rest/cocontransenergyinfo/";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : CoContransEnergyInfo.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "guid"
        });
        
        /* 初始化 DataGrid */
        _dataGrid = new mx.datacontrols.DataGrid({   
			// 构造查询属性。
			alias: "CoContransEnergyInfoMainViewDataGrid",

			columns:[
	        {	name: "guid", caption: "序号" , editorType: "TextEditor", align:"center", dataAlign:"center"	},
	        {	name: "participantName", caption: "输电方交易单位" , editorType: "TextEditor", align:"center", dataAlign:"center"	},
	        {	name: "licenceCode", caption: "许可证号 " , editorType: "TextEditor", align:"center", dataAlign:"center"	},
	        {	name: "taxCode", caption: "税号" , editorType: "TextEditor", align:"center", dataAlign:"center"	},
	        {	name: "linkMan", caption: "联系人" , editorType: "TextEditor", align:"center", dataAlign:"center"  },
	        {	name: "telephone", caption: "联系电话" , editorType: "TextEditor", align:"center", dataAlign:"center"  }
            ],
            // 构造列排序条件，如果有多列，则以逗号分隔。例sorter: "school ASC, class DESC"
            displayCheckBox: true,
	        displayPrimaryKey:false,//列表是否显示主键
            allowEditing: false, //列表默认不可编辑
	        pageSize : 15,
            entityContainer: gridEntityContainer
        });

       _dataGrid.setFilter({contractId:me.contractid, contractName:"天津"});//me.contractId});
       _dataGrid.load();
       me.addControl(_dataGrid);
       me.mSplit.addControl(_dataGrid,1);//添加panel
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