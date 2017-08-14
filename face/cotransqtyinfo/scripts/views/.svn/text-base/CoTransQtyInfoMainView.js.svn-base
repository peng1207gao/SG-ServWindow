$ns("cotransqtyinfo.views");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGridSearchBox");
$import("mx.datacontrols.ComplexGrid");
$import("mx.containers.Panel");


cotransqtyinfo.views.CoTransQtyInfoMainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    //me.objID = "8a8128824197a6450141c0146ac922eb";
    
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
    	_initHSplit();//划分上下部分
    	_initPanel();//创建业务操作面板
    	_initContainer();//创建按钮容器
    	_initButton();
//    	_initNew();//创建新增按钮
//    	_initEdit();//创建修改按钮
//    	_initDelete();//创建删除按钮
//    	_initElecInfo();//创建电力信息按钮
	    _initDataGrid();
    	_initDetailWindow();
//        me.on("activate", me.controller._onactivate);
    }
    
    function _initHSplit(){
    
    	me.hSplit = new mx.containers.HSplit({
    		rows : "70,auto"
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
    	if(me.params.readType != '1'){//1的时候只读
    		_initNew();//创建新增按钮
    		_initEdit();//创建修改按钮
    		_initDelete();//创建删除按钮
    		_initElecInfo();//创建电力信息按钮
		}
    }
    
    function _initNew(){
	    me.InsertButton = new mx.controls.Button({
			text: "新增",
			left: 20,
			padding: 6,
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
    
    function _initElecInfo(){
    	me.ElecInfo = new mx.controls.Button({
    		text: "电力信息",
    		left: 80,
    		onclick:_elecInfo
    	});
//    	me.container.addControl(me.ElecInfo);
    }
    
    function _elecInfo(){
    	if (me._dataGrid.getCheckedIDs().length == 0)
        {
             mx.indicate("info", "请勾选一条记录。");
             return;
        }
        //多选框勾选记录，判断是否选择多条
    	if(me._dataGrid.getCheckedIDs().length > 1)
    	{
    	       mx.indicate("info", "选定的记录条数不能超过一条。");
    	       return;
    	}
    	var elecView = new cotransqtyinfo.views.CoTransqtySlaveInfoMainViewController({objId:me._dataGrid.getCheckedIDs(),readType:me.params.readType}).getView();
    	me.openWin = utils.commonFun.WinMananger.create({
	    		reusable : true,//是否复用
	    		width : "60%",
	    		height : "50%",
	    		title : "合同输电综合信息从表列表"
    	});
    	me.openWin.setView(elecView);
    	me.openWin.showDialog();
    }
    
    function _initDataGrid()
    {
        var restUrl = "~/rest/cotransqtyinfo/";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : cotransqtyinfo.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "transinfoid"
        });
        
        /* 初始化 DataGrid */
        me._dataGrid = new mx.datacontrols.DataGrid({   
			// 构造查询属性。
			alias: "cotransqtyinfoCoTransQtyInfoMainViewDataGrid",
			
			columns:[
	        {	name: "transinfoid", caption: "输电信息ID" , editorType: "TextEditor"},
//	        {	name: "contractid", caption: "合同ID" , editorType: "TextEditor"	},
	        {	name: "transmission", caption: "输电方" , editorType: "TextEditor"	},
	        {	name: "passagewayname", caption: "输电通道名称" , editorType: "TextEditor"	},
	        {	name: "passagewayno", caption: "输电通道序号" , editorType: "TextEditor"	},
	        {	name: "linkid", caption: "联络线" , editorType: "TextEditor"	},
	        {	name: "linkno", caption: "联络线序号" , editorType: "TextEditor"	},
	        {	name: "startgateid", caption: "起点关口" , editorType: "TextEditor"},
	        {	name: "endgateid", caption: "终点关口" , editorType: "TextEditor"	},
	        {	name: "transqty", caption: "输电电量" , editorType: "TextEditor"	},
	        {	name: "lossrate", caption: "线损率" , editorType: "TextEditor"	},
	        {	name: "startprice", caption: "上网电价" , editorType: "TextEditor"	},
	        {	name: "endprice", caption: "落地电价" , editorType: "TextEditor"	},
	        {	name: "transprice", caption: "输电电价" , editorType: "TextEditor"	},
	        {	name: "spare1", caption: "是否含税" , editorType: "TextEditor"	},
	        {	name: "seprate", caption: "联络线分电比例%" , editorType: "TextEditor"	},
	        {	name: "power", caption: "高峰电力" , editorType: "TextEditor"	},
	        {	name: "peakregurate", caption: "调峰比例%" , editorType: "TextEditor"	},
	        {	name: "transcap", caption: "收费容量" , editorType: "TextEditor"	},
	        {	name: "capprice", caption: "容量电价" , editorType: "TextEditor"	},
	        {	name: "starttime", caption: "开始时间" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "endtime", caption: "结束时间" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "explanation", caption: "备注" , editorType: "TextEditor"	}
//	        {	name: "capfee", caption: "容量电费" , editorType: "TextEditor"	},
//	        {	name: "spare1", caption: "备用1" , editorType: "TextEditor"	},
//	        {	name: "spare2", caption: "备用2" , editorType: "TextEditor"	},
//	        {	name: "spare3", caption: "备用3" , editorType: "TextEditor"	},
            ],
            // 构造列排序条件，如果有多列，则以逗号分隔。例sorter: "school ASC, class DESC"
            displayRowNumber: true,
            displayCheckBox: true,
	        displayPrimaryKey:false,//列表是否显示主键
            allowEditing: false, //列表默认不可编辑
	        pageSize : 20,
            entityContainer: gridEntityContainer
        });
        
        me.hSplit.addControl(me._dataGrid,1);
        me._dataGrid.setFilter({contractid:me.contractid});
        me._dataGrid.load();
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
    	return me._dataGrid;
    }
    
	me.endOfClass(arguments)
    return me;
};