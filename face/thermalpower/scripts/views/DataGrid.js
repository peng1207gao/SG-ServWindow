$ns("thermalpower.views");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.ComplexGrid");


thermalpower.views.DataGrid = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    //网格列表
    var _topTenDataGrid = null;
    var _lastTenDataGrid = null;
    var _averageDataGrid = null;
    
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
	    _initTopTenDataGrid();
	    _initLastTenDataGrid();
	    _initAverageDataGrid();
        me.on("activate", me.controller._onactivate);
    }
    
    function _initAverageDataGrid(){
    	var averageRestUrl = "~/rest/setielineresultn/averageData";
    	var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : thermalpower.mappath(averageRestUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "guid",
            loadMeta:false
        });
        
        /* 初始化 DataGrid */
        _averageDataGrid = new mx.datacontrols.ComplexGrid({   
			// 构造查询属性。
			alias: "thermalpowerAverageDataGrid",
			columns:[
	        {	name: "sellername", caption: "售电方" , editorType: "TextEditor", width:"300"	},
	        {	name: "contractqty", caption: "合同电量" , editorType: "TextEditor"},
	        {	name: "energy_t", caption: "完成电量" , editorType: "TextEditor"	},
	        {	name: "percentage", caption: "完成率" , editorType: "TextEditor"	},
	        {	name: "deviation", caption: "偏差率" , editorType: "TextEditor"},
	        {	name: "deviationvalue", caption: "偏差值" , editorType: "TextEditor"}
            ],
            // 构造列排序条件，如果有多列，则以逗号分隔。例sorter: "school ASC, class DESC"
            displayCheckBox: false,
	        displayPrimaryKey:false,//列表是否显示主键
            allowEditing: false, //列表默认不可编辑
	        pageSize : 20,
	        displayToolBar:false,
	        displayRowNumber:true,
        	height:"40%",
			width:"100%",
			displayColSplitLine:true,//表格分割线
            entityContainer: gridEntityContainer
        });
    }
    
    function _initLastTenDataGrid(){
    	var lastTenRestUrl = "~/rest/setielineresultn/lastTen";
    	var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : thermalpower.mappath(lastTenRestUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "guid",
            loadMeta:false
        });
        
        /* 初始化 DataGrid */
        _lastTenDataGrid = new mx.datacontrols.ComplexGrid({   
			// 构造查询属性。
			alias: "thermalpowerLastTenDataGrid",
			columns:[
	        {	name: "sellername", caption: "售电方" , editorType: "TextEditor", width:"300"	},
	        {	name: "contractqty", caption: "合同电量" , editorType: "TextEditor"},
	        {	name: "energy_t", caption: "完成电量" , editorType: "TextEditor"	},
	        {	name: "percentage", caption: "完成率" , editorType: "TextEditor"	},
	        {	name: "deviation", caption: "偏差率" , editorType: "TextEditor"},
	        {	name: "deviationvalue", caption: "偏差值" , editorType: "TextEditor"}
            ],
            // 构造列排序条件，如果有多列，则以逗号分隔。例sorter: "school ASC, class DESC"
            displayCheckBox: false,
	        displayPrimaryKey:false,//列表是否显示主键
            allowEditing: false, //列表默认不可编辑
	        pageSize : 20,
	        displayToolBar:false,
	        displayRowNumber:true,
        	height:"40%",
			width:"100%",
			displayColSplitLine:true,//表格分割线
            entityContainer: gridEntityContainer
        });
    }
    
    function _initTopTenDataGrid()
    {
        var topTenRestUrl = "~/rest/setielineresultn/topTen";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : thermalpower.mappath(topTenRestUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "guid",
            loadMeta:false
        });
        
        /* 初始化 DataGrid */
        _topTenDataGrid = new mx.datacontrols.ComplexGrid({   
			// 构造查询属性。
			alias: "thermalpowerTopTenDataGrid",
			columns:[
	        {	name: "sellername", caption: "售电方" , editorType: "TextEditor", width:"300"	},
	        {	name: "contractqty", caption: "合同电量" , editorType: "TextEditor"},
	        {	name: "energy_t", caption: "完成电量" , editorType: "TextEditor"	},
	        {	name: "percentage", caption: "完成率" , editorType: "TextEditor"	},
	        {	name: "deviation", caption: "偏差率" , editorType: "TextEditor"},
	        {	name: "deviationvalue", caption: "偏差值" , editorType: "TextEditor"}
            ],
            // 构造列排序条件，如果有多列，则以逗号分隔。例sorter: "school ASC, class DESC"
            displayCheckBox: false,
	        displayPrimaryKey:false,//列表是否显示主键
            allowEditing: false, //列表默认不可编辑
	        pageSize : 20,
	        displayToolBar:false,
	        displayRowNumber:true,
        	height:"40%",
			width:"100%",
			displayColSplitLine:true,//表格分割线
            entityContainer: gridEntityContainer
        });
    }
    
    /**
     * 火电统调电厂完成率前10名 表格对象
     */
    me.getTopTenDataGrid = function(){
    	return _topTenDataGrid;
    }
    
    /**
     * 火电统调电厂完成率后10名 表格对象
     */
    me.getLastTenDataGrid = function(){
    	return _lastTenDataGrid;
    }
    
   /**
     * 火电统调电厂完成率平均进度 表格对象
     */
   me.getAverageDataGrid = function(){
    	return _averageDataGrid;
    }
    
	me.endOfClass(arguments)
    return me;
};