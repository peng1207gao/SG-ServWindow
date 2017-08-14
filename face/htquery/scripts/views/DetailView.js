$ns("htquery.views");

$import("mx.controls.ToolBar");
$import("mx.datacontainers.FormEntityContainer");
$import("mx.datacontrols.DataForm");


htquery.views.DetailView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    /**
     * 表单对象id
     */
    me.objID = null;
  
    
    

    /* 初始化单表单控件 */
    base.init = me.init;
    me.init = function()
    {
    	
        base.init();
        
    };
 
    me._init = function(){
    	_initControls();
        load();
    }

    function _initControls()
    {
    	_initButton();
        _initDataGrid();
        me.on("activate", me.controller._onactivate);
    }
	

    
    function _initButton(){
    	
    	//业务操作区域
    	me.panel2 = new mx.containers.Panel({width:"100%",height:"50",title:"业务操作"}); //业务操作panel容器
    	me.addControl(me.panel2);
    	
    	me.container = new mx.containers.Container({width:"100%",height:"40"});	//放置保存、关闭等的容器
    	me.panel2.addControl(me.container);	//添加container1容器
    	me.button3 = new mx.controls.Button({text:"文本下载",left:10,onclick: me.controller._btnDown_onclick});	//保存按钮
    	me.container.addControl(me.button3);	//添加按钮
    	me.button = new mx.controls.Button({text:"删除",left:50,onclick: me.controller._btnDelete_onclick});	//保存按钮
    	me.container.addControl(me.button);	//添加按钮
    	me.button2 = new mx.controls.Button({text:"关闭",left:80,onclick: me.controller._btnClose_onclick});	//关闭按钮
    	me.container.addControl(me.button2);	//添加按钮
    }
	
    function _initDataGrid()
    {
    	var restUrl = "~/rest/cocontractaffixinfo/";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : htquery.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "guid"
        });
        
        /* 初始化 DataGrid */
        me._dataGrid = new mx.datacontrols.ComplexGrid({   
			// 构造查询属性。
			alias: "htqueryMainViewDataGrid",
			columns:[
			{	name: "guid", caption: "主键" , editorType: "TextEditor",align:"center",dataAlign:"center"	},         
			{	name: "affixname", caption: "附件名称" , editorType: "TextEditor",align:"center",dataAlign:"center",width:"20%"	},
			{	name: "uploadperson", caption: "维护人" , editorType: "TextEditor",align:"center",dataAlign:"center",width:"20%"},  
			{	name: "uploadtimestring", caption: "维护时间" , editorType: "TextEditor",align:"center",dataAlign:"center",width:"20%"	},
			{	name: "description", caption: "说明" , editorType: "TextEditor",align:"center",dataAlign:"center",width:"20%"	},
			{	name: "affixtypename", caption: "附件类型" , editorType: "TextEditor",align:"center",dataAlign:"center",width:"20%"	}
            ],
            // 构造列排序条件，如果有多列，则以逗号分隔。例sorter: "school ASC, class DESC"
            displayCheckBox: true,
	        displayPrimaryKey:false,//列表是否显示主键
            allowEditing: false, //列表默认不可编辑
            allowSorting:true,//列允许排序
	        pageSize : 10,
            entityContainer: gridEntityContainer,
            displayRowNumber : true,
            displayToolBar : false,
            height:"314"
        });
        me.addControl(me._dataGrid);//添加表格
    }
    
    function load(){
    	if(me.objID!=null){
    		me._dataGrid.setFilter({contractid:me.objID});
    		me._dataGrid.load();
    	}
    	
    	
    	//_dataGrid.columns["contracttype"].setVisible(false);
    }
    

	
    /**
     * 获取工具条
     */
    me.getToolBar = function(){
		return _toolBar;
    }
    
	me.endOfClass(arguments)
    return me;
};