$ns("coContractbackupInfo.views");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGridSearchBox");
$import("mx.datacontrols.DataGrid");
$import("mx.rpc.RESTClient");

//-----------------------------------------------
coContractbackupInfo.views.MainView1 = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    //网格列表
    var _dataGrid = null;
    //表单窗口对象
    var _detailWin = null;

    var restClient = new mx.rpc.RESTClient();

    base.init = me.init;
    me.init = function()
    {
        base.init();
        _initControls();
    };
    
    function _initControls()
    {
    	var btn_search = new mx.controls.Button({text : "分解电量到机组",left : 20});
    	me.addControl(btn_search);
    	
    	//按钮点击事件
    	btn_search.on("click", function() {
    		if(confirm("如果分解合同，将会覆盖已存在的数据，确定继续分解？")){
    			var result = restClient.getSync(coContractbackupInfo.mappath("~/rest/cocontractbackupinfo/fj"));
    			if(result.resultValue.items[0] == "true" || result.resultValue.items[0] == true){
    				alert("分解成功");
    			}else{
    				debugger;
    				alert(result.resultValue.items[0]);
    				alert("分解失败");
    			}
    		}else{
    			return;
    		}
    	});
    	
    	var btn_end = new mx.controls.Button({text : "合同终止", left :20});
    	me.addControl(btn_end);
    	
    	//合同终止按钮点击事件
    	btn_end.on("click", function() {
    		var v_dataGrid = me.getDataGrid();
    		
    		if (v_dataGrid.getCheckedIDs().length == 0) {
    			mx.indicate("info", "请勾选一条待删除记录。");
                return;
            }else if(v_dataGrid.getCheckedIDs().length > 1) {
            	mx.indicate("info", "只能勾选一条待删除记录。");
                return;
            }else if(confirm("您确认终止该条数据吗？")) {
            	var result = restClient.getSync(coContractbackupInfo.mappath("~/rest/cocontractbackupinfo/contractEnd?checkedId=8a8192163ed0bcf5013ed453340000d4"));
            	//alert(result.resultValue.items[0]);
            	if(result.resultValue.items[0] == "true" || result.resultValue.items[0] == true){
            		alert("合同终止成功");
            	}else{
            		alert("合同终止失败");
            	}
    		}
    	});
    	
    	//输电方按钮点击事件
    	var btn_Contrans = new mx.controls.Button({text : "输电方", left :20});
    	me.addControl(btn_Contrans);
    	btn_Contrans.on("click", function() {
    		var _openView = new coContractbackupInfo.views.SdfMainView({contractId:"6D130C45-D822-F29A-E07B-5437FFE8209A",contractName:"蒙古国电",guid:"402880e83d4dde0e013d4de489fe0001"});	
    		me._openWin = utils.commonFun.WinMananger.create({
    			reusable: true,//是否复用
    			width:"50%",
    			height:"80%",
    			title:"",
    			onshown:function(){
//    				_openView._getChartData();
//					_openView._drawChart();
    			}
    		});
    		me._openWin.setView(_openView);
    		me._openWin.showDialog();
    	});
	    _initDataGrid();
    	_initDetailWindow();
    }
    
    function _initDataGrid()
    {
        var restUrl = "~/rest/cocontractbackupinfo/";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : coContractbackupInfo.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "guid"
        });
        
        /* 初始化 DataGrid */
        _dataGrid = new mx.datacontrols.DataGrid({   
			// 构造查询属性。
			alias: "coContractbackupInfoMainViewDataGrid",
			searchBox: new mx.datacontrols.DataGridSearchBox({
			
				fields: [
	            {	name: "backupdate", caption: "BACKUPDATE", editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	            {	name: "backupperson", caption: "BACKUPPERSON", editorType: "TextEditor"	},
	            {	name: "backuporg", caption: "BACKUPORG", editorType: "TextEditor"	},
	            {	name: "backuptype", caption: "1??????2??????3?? ", editorType: "TextEditor"	},
	            {	name: "contractnum", caption: "CONTRACTNUM", editorType: "TextEditor"	},
	            {	name: "backupstatus", caption: "1?????0???? ", editorType: "TextEditor"	},
		        {	name: "description", caption: "DESCRIPTION", editorType: "TextEditor"	}
				]
			}),
			
			columns:[
	        {	name: "contractid", caption: "CONTRACTID" , editorType: "TextEditor"	},
	        {	name: "backupdate", caption: "BACKUPDATE" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "backupperson", caption: "BACKUPPERSON" , editorType: "TextEditor"	},
	        {	name: "backuporg", caption: "BACKUPORG" , editorType: "TextEditor"	},
	        {	name: "backuptype", caption: "1??????2??????3?? " , editorType: "TextEditor"	},
	        {	name: "contractnum", caption: "CONTRACTNUM" , editorType: "TextEditor"	},
	        {	name: "backupstatus", caption: "1?????0???? " , editorType: "TextEditor"	},
	        {	name: "description", caption: "DESCRIPTION" , editorType: "TextEditor"	},
	        {	name: "guid", caption: "GUID" , editorType: "TextEditor"	}
            ],
            // 构造列排序条件，如果有多列，则以逗号分隔。例sorter: "school ASC, class DESC"
            displayCheckBox: true,
	        displayPrimaryKey:false,//列表是否显示主键
            allowEditing: false, //列表默认不可编辑
	        pageSize : 20,
            entityContainer: gridEntityContainer
        });
        
        me.addControl(_dataGrid);
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
    
    /**
     * 分解电量到机组按钮
     */
	me.endOfClass(arguments)
    return me;
};