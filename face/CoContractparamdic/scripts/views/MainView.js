$ns("CoContractparamdic.views");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGridSearchBox");
$import("mx.datacontrols.DataGrid");
$import("mx.containers.HSplit");
$import("mx.containers.Panel");
$import("mx.containers.Container");
$import("mx.editors.TextEditor");

CoContractparamdic.views.MainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    //网格列表
    var _dataGrid = null;
    //表单窗口对象
    var _detailWin = null;
	var contracttemplateId = null;//合同范本ID

    base.init = me.init;
    me.init = function()
    {
        base.init();
        _initControls();
    };
    
    function _initControls()
    {
    	//页面上面分成上下两部分
    	me.mSplit = new mx.containers.HSplit({rows:"120px,auto",borderThick:1});
    	me.addControl(me.mSplit);
    	
    	me.panel1 = new mx.containers.Panel({title:"业务查询", width:"100%", height:55});
    	me.mSplit.addControl(me.panel1,0);//添加panel1
    	me.container1 = new mx.containers.Container({padding:"2px",width:"100%"});
    	me.panel1.addControl(me.container1);//添加container1
    	var contractTempLabel = new mx.controls.Label({
    	    text: "字典标签名称：",
    	    textAlign: "right",
    	    width:"8%",
    	    right:4,
    	    verticalAlign: "middle"
    	});
    	//字典标签名称
    	me.textEditor = new mx.editors.TextEditor({"width" : "300px"});
    	me.container1.addControl(contractTempLabel);
    	me.container1.addControl(me.textEditor);
    	
    	me.panel2 = new mx.containers.Panel({title:"业务操作", width:"100%", height:55});
    	me.mSplit.addControl(me.panel2,0);//添加panel2
    	me.container2 = new mx.containers.Container({padding:"2px",width:"100%"});
    	me.panel2.addControl(me.container2);//添加container2
    	
    	//查询按钮
		var btn_search = new mx.controls.Button({text : "查询",left : 20});
		me.addControl(btn_search);
		//查询按钮点击事件
		btn_search.on("click", function() {
			if(me.textEditor.getValue() != "" && me.textEditor.getValue() != null){
				contracttemplateId = me.textEditor.getValue();
			}else{
				contracttemplateId = "";
			}
			var filter = {};
			filter.contracttemplateId = contracttemplateId;
			me.getDataGrid().setFilter(filter);
			me.getDataGrid().load();
		});
		me.container2.addControl(btn_search);
		
		//新增按钮
		var btn_add = new mx.controls.Button({text : "新增",left : 40});
		me.addControl(btn_add);
		//新增按钮点击事件
		btn_add.on("click", function() {
			me.controller._btnNew_onclick();
		});
		me.container2.addControl(btn_add);
		
		//修改参数按钮
		var btn_updparam = new mx.controls.Button({text : "修改",left : 60});
		me.addControl(btn_updparam);
		//修改参数按钮点击事件
		btn_updparam.on("click", function() {
			var v_dataGrid = me.getDataGrid();
    		
    		if (v_dataGrid.getCheckedIDs().length == 0) {
    			mx.indicate("info", "请勾选一条待修改记录。");
                return;
            }else if(v_dataGrid.getCheckedIDs().length > 1) {
            	mx.indicate("info", "只能勾选一条待修改记录。");
                return;
            }else {
            	me.controller._btnEdit_onclick();
    		}
		});
		me.container2.addControl(btn_updparam);
		
		//删除按钮
		var btn_delete = new mx.controls.Button({text : "删除",left : 80});
		me.addControl(btn_delete);
		//删除参数按钮点击事件
		btn_delete.on("click", function() {
			var v_dataGrid = me.getDataGrid();
    		
    		if (v_dataGrid.getCheckedIDs().length == 0) {
    			mx.indicate("info", "请选择要删除的记录");
                return;
            }else {
            	me.controller._btnDelete_onclick();
            }
		});
		me.container2.addControl(btn_delete);
    	
	    _initDataGrid();
    	_initDetailWindow();
        me.on("activate", me.controller._onactivate);
    }
    
    function _initDataGrid()
    {
        var restUrl = "~/rest/cocontractparamdic/";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : CoContractparamdic.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "sheetid"
        });
        
        /* 初始化 DataGrid */
        _dataGrid = new mx.datacontrols.DataGrid({   
			// 构造查询属性。
			alias: "CoContractparamdicMainViewDataGrid",
		
			columns:[
	        {	name: "sheetid", caption: "主键" , editorType: "TextEditor"	},
	        {	name: "dicName", caption: "标签名称" , editorType: "TextEditor", align:"center", width:"30%", dataAlign:"center"	},
	        {	name: "dicValue", caption: "标签值" , editorType: "TextEditor", align:"center", width:"30%", dataAlign:"center"	},
	        {	name: "dicContent", caption: "说明" , editorType: "TextEditor", align:"center", width:"20%", dataAlign:"center"	},
	        {	name: "operateUser", caption: "维护人" , editorType: "TextEditor", align:"center", width:"10%", dataAlign:"center"	},
	        {	name: "operateDate", caption: "维护时间" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd", align:"center", width:"10%", dataAlign:"center"}
            ],
            // 构造列排序条件，如果有多列，则以逗号分隔。例sorter: "school ASC, class DESC"
            displayCheckBox: true,
            displayRowNumber: true, //显示序号
	        displayPrimaryKey:false,//列表是否显示主键
            allowEditing: false, //列表默认不可编辑
	        pageSize : 20,
            entityContainer: gridEntityContainer
        });
        
        _dataGrid.load();
        me.addControl(_dataGrid);
        me.mSplit.addControl(_dataGrid,1);//添加panel2
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