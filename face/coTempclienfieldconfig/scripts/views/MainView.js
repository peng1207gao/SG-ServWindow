$ns("coTempclienfieldconfig.views");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGridSearchBox");
$import("mx.datacontrols.ComplexGrid");


coTempclienfieldconfig.views.MainView = function()
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
	    _initDataGrid();
    	_initDetailWindow();
        me.on("activate", me.controller._onactivate);
    }
    
    function _initDataGrid()
    {
        var restUrl = "~/rest/cotempclienfieldconfig/";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : coTempclienfieldconfig.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "sheetid"
        });
        
        /* 初始化 DataGrid */
        _dataGrid = new mx.datacontrols.ComplexGrid({   
			// 构造查询属性。
			alias: "coTempclienfieldconfigMainViewDataGrid",
			searchBox: new mx.datacontrols.DataGridSearchBox({
			
				fields: [
	            {	name: "sheetid", caption: "??", editorType: "TextEditor"	},
	            {	name: "canSheetid", caption: "??????SHEETID", editorType: "TextEditor"	},
	            {	name: "configFieldName", caption: "???????", editorType: "TextEditor"	},
	            {	name: "personid", caption: "???ID", editorType: "TextEditor"	},
	            {	name: "marketid", caption: "?????", editorType: "TextEditor"	},
	            {	name: "updatedate", caption: "????", editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
		        {	name: "contracttemplatecode", caption: "??????", editorType: "TextEditor"	}
				]
			}),
			
			columns:[
	        {	name: "sheetid", caption: "??" , editorType: "TextEditor"	},
	        {	name: "canSheetid", caption: "??????SHEETID" , editorType: "TextEditor"	},
	        {	name: "configFieldName", caption: "???????" , editorType: "TextEditor"	},
	        {	name: "personid", caption: "???ID" , editorType: "TextEditor"	},
	        {	name: "marketid", caption: "?????" , editorType: "TextEditor"	},
	        {	name: "updatedate", caption: "????" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "contracttemplatecode", caption: "??????" , editorType: "TextEditor"	}
            ],
            // 构造列排序条件，如果有多列，则以逗号分隔。例sorter: "school ASC, class DESC"
            displayCheckBox: true,
	        displayPrimaryKey:false,//列表是否显示主键
            allowEditing: false, //列表默认不可编辑
	        pageSize : 20,
            entityContainer: gridEntityContainer,
	        create: me.controller._btnNew_onclick,
            remove: me.controller._btnDelete_onclick
        });
        
	    //重置toolBar按钮项
        _resetToolBarItems();
        me.addControl(_dataGrid);
    }
    
    /**
     * 重置按钮项
     */
    function _resetToolBarItems(){
    	//去除保存按钮
		_dataGrid.toolBar.removeByIndex(1);
		_dataGrid.toolBar.removeByName("save");
		//插入编辑按钮
		_dataGrid.toolBar.insertItem(2,"-",true);
		_dataGrid.toolBar.insertItem(3,{ name: "edit", text: mx.msg("EDIT"), toolTip: mx.msg("EDIT"), imageKey : "edit", onclick: me.controller._btnEdit_onclick},true);
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