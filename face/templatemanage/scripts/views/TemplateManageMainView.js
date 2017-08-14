$ns("templatemanage.views");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGridSearchBox");
$import("mx.datacontrols.DataGrid");
$import("mx.containers.Panel");


templatemanage.views.TemplateManageMainView = function()
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
    	_initHSplit();
    	_initCondition();
    	_initButton();
	    _initDataGrid();
    	_initDetailWindow();
        me.on("activate", me.controller._onactivate);
    }
    
    function _initHSplit(){
    	me.hSplit = mx.containers.HSplit({
    		rows: "120px,auto"
    	});
    	me.addControl(me.hSplit);
    }
    
    function _initCondition(){
    	me.queryPanel = mx.containers.Panel({
    		id:"queryPanel",
			border:"1",
			height: "50%",
			title:"业务查询"
    	});
    	me.hSplit.addControl(me.queryPanel);
    	me.queryContainer = mx.containers.Container({
    		id: "queryContainer",
    		padding: "2px"
    	});
    	me.queryPanel.addControl(me.queryContainer);
    	
    	me.typeLabel = mx.controls.Label({
    		text: "合同类型选择:",
	    	textAlign: "center",
	    	verticalAlign: "middle"
    	});
    	me.queryContainer.addControl(me.typeLabel);
    	me.contractType = utils.dropDownEditor.ContractTypeTree.ContractTypeDropDownEditor(false,false);
    	me.contractType.setWidth(200);
    	me.queryContainer.addControl(me.contractType);
    	
    	me.checkEditor = new mx.editors.CheckEditor(
		{
		    value: "F",
		    caption: "显示已失效范本"
		});
		
		me.queryContainer.addControl(me.checkEditor);
    }
    
    function _initButton(){
    	me.buttonPanel = mx.containers.Panel({
    		id:"buttonPanel",
			border:"1",
			height: "50%",
			title:"业务操作"
    	});
    	me.hSplit.addControl(me.buttonPanel);
    	me.buttonContainer = mx.containers.Container({
    		id: "buttonContainer",
    		padding: "2px"
    	});
    	me.buttonPanel.addControl(me.buttonContainer);
    	
    	me.searchBtn = mx.controls.Button({
    		text: "查询",
    		left: 10,
    		onclick: me.controller.search
    	});
    	me.buttonContainer.addControl(me.searchBtn);
    	
    	me.addBtn = mx.controls.Button({
    		text: "增加范本",
    		left: 20,
    		onclick: me.controller._btnNew_onclick
    	});
    	me.buttonContainer.addControl(me.addBtn);
    	
    	me.editBtn = mx.controls.Button({
    		text: "修改范本",
    		left: 30,
    		onclick: me.controller._btnEdit_onclick
    	});
    	me.buttonContainer.addControl(me.editBtn);
    	
    	me.deleteBtn = mx.controls.Button({
    		text: "失效范本",
    		left: 40,
    		onclick: me.controller._btnDelete_onclick
    	});
    	me.buttonContainer.addControl(me.deleteBtn);
    	
    	me.effectBtn = mx.controls.Button({
    		text: "生效范本",
    		left: 50,
    		onclick: me.controller._btnEffect_onclick
    	});
    	me.buttonContainer.addControl(me.effectBtn);
    	
    	me.downloadBtn = mx.controls.Button({
    		text: "下载范本",
    		left: 60,
    		onclick: me.controller._btnDownload_onclick
    	});
//    	me.buttonContainer.addControl(me.downloadBtn);
    	
    	me.zdwhBtn = mx.controls.Button({
    		text: "范本字段对应维护",
    		left: 70,
    		onclick: me.controller._btnZdwh_onclick
    	});
//    	me.buttonContainer.addControl(me.zdwhBtn);
    	
    	me.explainBtn = mx.controls.Button({
    		text: "说明",
    		left: 80,
    		onclick: me.controller._btnExplain_onclick
    	});
//    	me.buttonContainer.addControl(me.explainBtn);
    }
    
    me._initParams = function(){
    	var checkEditor = me.checkEditor.value;
    	var checkEditorValue = null;
    	if(checkEditor=="T"){//选择框被选中，此时可以查询出失效的范本
    		checkEditorValue = "1";
    	}
    	var params = {contracttype:me.contractType.value,effectiveflag:checkEditorValue};
    	return params;
    }
    
    function _initDataGrid()
    {
        var restUrl = "~/rest/cocontracttemplate/";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : templatemanage.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "contracttemplateid"
        });
        
        /* 初始化 DataGrid */
        _dataGrid = new mx.datacontrols.DataGrid({   
			// 构造查询属性。
			alias: "templatemanageTemplateManageMainViewDataGrid",
			
			columns:[
//	        {	name: "marketid", caption: "市场ID" , editorType: "TextEditor"	},
	        {	name: "contracttemplatename", caption: "合同范本名称" , editorType: "TextEditor",width:"20%"	},
	        {	name: "contracttype", caption: "合同类型" , editorType: "TextEditor",width:"20%"	},
	        {	name: "contracttemplateid", caption: "合同范本ID" , editorType: "TextEditor"	},
	        {	name: "contracttemplatecode", caption: "合同范本编号" , editorType: "TextEditor",width:"20%"	},
//	        {	name: "version", caption: "版本号" , editorType: "TextEditor"	},
//	        {	name: "issueddate", caption: "下发时间" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "starteffectivedate", caption: "生效时间" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd",width:"20%"},
	        {	name: "endeffectivedate", caption: "失效时间" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd",width:"20%"}
//	        {	name: "effectiveflag", caption: "失效标志" , editorType: "TextEditor"	},
//	        {	name: "isdelete", caption: "删除标记" , editorType: "TextEditor"	},
//	        {	name: "updatetime", caption: "信息更新时间" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
//	        {	name: "updatepersonid", caption: "信息更新人编号" , editorType: "TextEditor"	}
            ],
            // 构造列排序条件，如果有多列，则以逗号分隔。例sorter: "school ASC, class DESC"
            displayCheckBox: true,
	        displayPrimaryKey:false,//列表是否显示主键
	        displayRowNumber: true,
            allowEditing: false, //列表默认不可编辑
	        pageSize : 20,
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
			width:700,
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