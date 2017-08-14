$ns("coContractbackupInfo.views");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGridSearchBox");
$import("mx.datacontrols.ComplexGrid");
$import("mx.datacontrols.DataGrid");

//输电方
coContractbackupInfo.views.SdfMainView = function()
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
    	if(me.params.readType != '1'){//1的时候只读
    		var btn_add = new mx.controls.Button({text : "新增",left : 20});
    		me.addControl(btn_add);
    		//新增按钮点击事件
    		btn_add.on("click", function() {
    			//me.objId = me.contractId;
    			me.objId = "4028804a3d4cc6df013d4d18dc220004";
    			me.controller._btnNew_onclick();
    			me.objName = "内蒙古国电";//me.contractName;
    		});
    	
    		var btn_delete = new mx.controls.Button({text : "删除",left : 20});
    		me.addControl(btn_delete);
    		//删除按钮点击事件
    		btn_delete.on("click", function() {
    			var v_dataGrid = me.getDataGrid();
    			if (v_dataGrid.getCheckedIDs().length == 0) {
    				mx.indicate("info", "请至少选择一条记录");
    				return;
    			}else if(v_dataGrid.getCheckedIDs().length > 1) {
    				mx.indicate("info", "只能勾选一条待删除记录。");
    				return;
    			}else if(confirm("您确认删除该条记录吗？")) {
    				var result = restClient.getSync(coContractbackupInfo.mappath("~/rest/cocontractbackupinfo/sdfDelete?checkedId="+ v_dataGrid.getCheckedIDs()));
    				if(result.resultValue.items[0] == "true" || result.resultValue.items[0] == true){
    					alert("删除成功");
    				}else{
    					alert("删除失败");
    				}
    			}
    		});
    	
    		var btn_close = new mx.controls.Button({text : "关闭",left : 20});
    		me.addControl(btn_close);
    		//关闭按钮点击事件
    		btn_close.on("click", function() {
    			me.viewPort.close();//关闭当前页面
    		});
    	}
    	
	    _initDataGrid();
    	_initDetailWindow();
    }
    
    function _initDataGrid()
    {
        var restUrl = "~/rest/cocontransenergyinfo/";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : coContractbackupInfo.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "contractId"
        });
        
        /* 初始化 DataGrid */
        _dataGrid = new mx.datacontrols.DataGrid({   
			// 构造查询属性。
			alias: "CoContransEnergyInfoMainViewDataGrid",

			columns:[
	        {	name: "contractId", caption: "序号" , editorType: "TextEditor", align:"center", dataAlign:"center"	},
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

        _dataGrid.setFilter({contractId:"4028804a3d4cc6df013d4d18dc220004", contractName:"天津"});//me.contractId});
       _dataGrid.load();
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
    
	me.endOfClass(arguments)
    return me;
};