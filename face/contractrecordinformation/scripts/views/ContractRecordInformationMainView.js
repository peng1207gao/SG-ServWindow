$ns("contractrecordinformation.views");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGrid");
$import("mx.containers.Panel");
$import("mx.datacontrols.DataForm");
$import("mx.containers.Container");
$import("mx.controls.Label");
$import("mx.editors.DropDownEditor");
$import("mx.editors.TextEditor");
$import("mx.containers.Panel");
$import("mx.containers.HSplit");
$import("mx.controls.Button");
$import("mx.editors.DateTimeEditor");


contractrecordinformation.views.ContractRecordInformationMainView = function()
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
//	    _initDataGrid();
    	_initDetailWindow();
        me.on("activate", me.controller._onactivate);
    	me.hSplit = new mx.containers.HSplit({
			rows : "160px,auto",
			borderThick : 1
		});
		me.addControl(me.hSplit);
		me.search_panel = new mx.containers.Panel({
			name : "panelss",
			title : "合同签订信息",
			width : "100%",
			height : 100
		});
		me.operate_panel = new mx.containers.Panel({
			name : "panel",
			title : "业务操作",
			width : "100%",
			height : 50
		});
		me.operate_panel1 = new mx.containers.Panel({
			name : "panel",
			title : "合同备案信息",
			width : "100%",
			height : 450
		});
		me.hSplit.addControl(me.search_panel, 0);
		me.hSplit.addControl(me.operate_panel, 0);
		me.hSplit.addControl(me.operate_panel1, 1);
		var _btnClose = new mx.controls.Button({ text: "关闭",left:90,top:2 });
		var _btnNew = new mx.controls.Button({ text: "新增",left:30,top:2 }); 
		var _btnEdit = new mx.controls.Button({ text: "修改",left:50,top:2 }); 
		var _btnDelete = new mx.controls.Button({ text: "删除",left:70,top:2 });
		if(me.params.readType != '1')
		{
			me.operate_panel.addControl(_btnNew);	
			me.operate_panel.addControl(_btnEdit);
			me.operate_panel.addControl(_btnDelete);
			me.operate_panel.addControl(_btnClose);
		}
		
		_btnDelete.on("click",function(){
			me.controller._btnDelete_onclick();
		});
		_btnEdit.on("click",function(){
			me.controller._btnEdit_onclick();
		});
		_btnNew.on("click",function(){
			me.controller._btnNew_onclick();
		});
		_btnClose.on("click",function(){
			me.parent.hide();
		});
	    _initDataGrid();
    }
    function _initDataGrid()
    {
        var restUrl = "~/rest/tjfxmaplineid/";
        var contractIdStr = me.contractid;
        var contractId = {"contractId":me.contractid};//传递合同id
        var s = new mx.rpc.RESTClient().getSync(contractrecordinformation.mappath("~/rest/tjfxmaplineid/teStrings"),contractId);
        var signConteact = s.resultValue.items[0];
        var dict1 = s.resultValue.items[1];
        var dict2 = s.resultValue.items[2];
        var dateStr = s.resultValue.items[3];
        var displayName = "";
        if(dict1[0]==signConteact[1])
        {
        	displayName = dict1[1];
        }
        if(dict2[0] == signConteact[1])
        {
        	displayName = dict2[1];
        }
        me.hSplit = new mx.containers.HSplit({
			rows : "50%,50%",
			borderThick : 0
		});
    	var firstRowL = new mx.controls.Label({
    		text: "合同名称： "+signConteact[0],
    		//fontSize:"14px",
    		autoWrap:true,
    		css : {"float":"left","margin-left":"10px","margin-top":"10px","min-width":"300px"},
    		width:"auto"
    	});
    	if(signConteact[2]==null)
    	{
    		var dataTimeEditor = new mx.editors.DateTimeEditor({
    			formatString : "yyyy-MM-dd",
    			value : dateStr,
    			nullable:false,
    			width:200
    		});
    	}
    	else
    	{
    		var dataTimeEditor = new mx.editors.DateTimeEditor({
    			formatString : "yyyy-MM-dd",
    			value : signConteact[2],
    			nullable:false,
    			width:200
    		});
    	}
    	
    	var signPerEdit = new mx.editors.TextEditor({
    		text:signConteact[3],
    		width:"200px",
    		height:"20px",
    		maxLength:8,
    		css : {"margin-top":"10px","width":"200px"}
    	});
    	signPerEdit.setText(signConteact[3]);
    	var firstRowR = new mx.controls.Label({
    		text: "签订人：",
    		left:10,
    		css : {"margin-top":"10px"},
    		width:"65px"
    	});
    	var secondRowR = new mx.controls.Label({
    		text: "合同签订时间：",
    		//fontSize:"14px",
    		css : {"float":"left","margin-left":"10px"},
    		width:"100px"
    	});
    	var secondRowL = new mx.controls.Label({
    		text: "签订状态：",
    		//fontSize:"14px",
    		css : {"margin-left":"10px"},
    		width:"70px"
    	});
    	var dictDropDownEditor = new mx.editors.DropDownEditor({
    		displayMember: "name",
    		valueMember: "value",
    		width : "200px",
    		//allowDropDown : false,
    		//value :signConteact[1],
    		url: contractrecordinformation.mappath("~/../ETradePublicUtils/rest/baGencodeTypeList/getList?type=24")
    	});
    	dictDropDownEditor.setValue(signConteact[1],displayName);
    	me.search_panel.addControl(me.hSplit);
    	me.hSplit.addControl(firstRowL,0);
        me.hSplit.addControl(firstRowR,0);
        me.hSplit.addControl(signPerEdit,0);
        me.hSplit.addControl(secondRowR,1);
        me.hSplit.addControl(dataTimeEditor,1);
        me.hSplit.addControl(secondRowL,1);
        me.hSplit.addControl(dictDropDownEditor,1);
    	var _homeBtnSave = new mx.controls.Button({ text: "保存",left:"10px"});
    	if(me.params.readType != '1')
    	{
    		me.hSplit.addControl(_homeBtnSave,1);
    	}
    	_homeBtnSave.on("click", function() {
    		var params = {"contractId":contractIdStr,"signPer":signPerEdit.getValue(),"dateTime":dataTimeEditor.getValue(),"status":dictDropDownEditor.getValue()};
    		var saveStatus = new mx.rpc.RESTClient().getSync(contractrecordinformation.mappath("~/rest/tjfxmaplineid/saveSignContract"),{"params":JSON.stringify(params)});
    		alert("保存成功");
    		me.controller._refreshDataGrid;

    	});
        var m = new mx.rpc.RESTClient().getSync(contractrecordinformation.mappath("~/rest/tjfxmaplineid/setContractId"),{"contractId":me.contractid});
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : contractrecordinformation.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "guid"
        });
        
        /* 初始化 DataGrid */
        _dataGrid = new mx.datacontrols.DataGrid({   
			// 构造查询属性。
			alias: "contractrecordinformation",
			columns:[
			{	name: "guid", caption: "guid" , editorType: "TextEditor"	},
	        {	name: "status", caption: "备案状态" , editorType: "TextEditor"	},
	        {	name: "backuporg", caption: "备案机构" , editorType: "TextEditor"	},
	        {	name: "ctype", caption: "合同备案类型" , editorType: "TextEditor"	},
	        {	name: "backupperson", caption: "备案人" , editorType: "TextEditor"	},
	        {	name: "backupdate", caption: "备案时间" , editorType: "TextEditor"	},
	        {	name: "contractnum", caption: "备案份数" , editorType: "TextEditor"	}
            ],
            // 构造列排序条件，如果有多列，则以逗号分隔。例sorter: "school ASC, class DESC"
            displayCheckBox: true,
	        displayPrimaryKey:false,//列表是否显示主键
            allowEditing: false, //列表默认不可编辑
            displayRowNumber:true,
	        pageSize : 10,
            entityContainer: gridEntityContainer
        });
       me.operate_panel1.addControl(_dataGrid);
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