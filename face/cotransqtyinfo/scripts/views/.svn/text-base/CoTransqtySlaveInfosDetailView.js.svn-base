$ns("cotransqtyinfo.views");

$import("mx.controls.ToolBar");
$import("mx.datacontainers.FormEntityContainer");
$import("mx.datacontrols.DataGrid");


cotransqtyinfo.views.CoTransqtySlaveInfosDetailView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    /**
     * 表单对象id
     */
    me.objID = null;
    /**
      * 表单对象
     */
    var _form = null;
    /**
     * 工具条
     */
    var _toolBar = null;
    

    /* 初始化单表单控件 */
    base.init = me.init;
    me.init = function()
    {
        base.init();
        _initControls();
    };


    function _initControls()
    {
    	_initLayOut();
	    _initDataGrid();
//	    _initEditors();
	    me.on("activate", me.controller._onactivate);
    }
	function _initLayOut(){
		me.hSplit = new mx.containers.HSplit({
    		rows : "70,auto"
    	});
    	me.addControl(me.hSplit);
    	me.panel = new mx.containers.Panel({
    		id:"Panel",
			border:"1",
			title:"业务操作"
    	});
    	me.hSplit.addControl(me.panel,0);
    	me.container = new mx.containers.Container({
    		id: "contaniner",
    		padding: "2px"
    	});
    	me.panel.addControl(me.container);
    	me.createBtn = new mx.controls.Button({
   			text: "新增",
   			left: 20,
   			padding: 6,
   			onclick:me.controller._btnCreate_onclick//onclick: me.controller._btnDelete_onclick
   		});
   		me.container.addControl(me.createBtn);
   		me.deleteBtn = new mx.controls.Button({
   			text: "删除",
   			left: 30,
   			padding: 6,
   			onclick: me.controller._btnDelete_onclick
   		});
   		me.container.addControl(me.deleteBtn);
    	me.saveBtn = new mx.controls.Button({
   			text: "保存",
   			left: 40,
   			padding: 6,
   			onclick:me.controller._btnSave_onclick
   		});
   		me.container.addControl(me.saveBtn);
   		me.closeBtn = new mx.controls.Button({
   			text: "关闭",
   			left: 50,
   			padding: 6,
   			onclick:me.controller._btnClose_onclick
   		});
   		me.container.addControl(me.closeBtn);
   		
   		me.hSplit1 = new mx.containers.HSplit({
    		rows : "100,auto",
    		borderThick:0
    		
    	});
   		me.hSplit.addControl(me.hSplit1,1);
    	
    	me.container = new mx.containers.Container(
    			{padding:"2px",height:"30px",width:"100%"});
    	me.container1 = new mx.containers.Container(
    			{padding:"2px",height:"30px",width:"100%"});
    	me.container2 = new mx.containers.Container(
    			{padding:"2px",height:"30px",width:"100%"});
    	me.hSplit1.addControl(me.container,0);
    	me.hSplit1.addControl(me.container1,0);
    	me.hSplit1.addControl(me.container2,0);
    	var nameLab = new mx.controls.Label({
    	    text: "典型曲线名称：",
    	    textAlign: "right",
    	    verticalAlign: "middle"
    	});
    	me.container.addControl(nameLab);
    	me.nameTextEditor = new mx.editors.TextEditor(
    			{
    			    "width" : "200px",
    			    "enabled" : false,
    			    "hint" : "名称" //指定显示的提示文字。
    			});
    	me.container.addControl(me.nameTextEditor);
    	var startLab = new mx.controls.Label({
    	    text: "开始时间：",
    	    textAlign: "right",
    	    verticalAlign: "middle"
    	});
    	me.container1.addControl(startLab);
    	me.startDataTimeEditor = new mx.editors.DateTimeEditor({
    		 "width" : "200px",
    	    formatString: "yyyy-MM-dd"
    	});
    	me.container1.addControl(me.startDataTimeEditor);
    	var endLab = new mx.controls.Label({
    	    text: "结束时间：",
    	    textAlign: "right",
    	    verticalAlign: "middle"
    	});
    	me.container2.addControl(endLab);
    	me.endDataTimeEditor = new mx.editors.DateTimeEditor({
    		 "width" : "200px",
    	    formatString: "yyyy-MM-dd"
    	});
    	me.container2.addControl(me.endDataTimeEditor);
	}
	
    function _initDataGrid()
    {
    	var restUrl = "~/rest/cotransqtyinfo/queryElec";
        /* 初始化 EntityContainer */     
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : cotransqtyinfo.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "guid"
        });
        me._dataGrid = new mx.datacontrols.DataGrid({   
			// 构造查询属性。
			alias: "contractManageMainViewDataGrid",
			searchBox: new mx.datacontrols.DataGridSearchBox({
			
				fields: [
				]
			}),
			columns:[
	        {	name: "guid", caption: "GUID", editorType: "TextEditor", visible:false},
	        {	name: "transinfoid", caption: "输电信息ID", editorType: "TextEditor",visible:false},
	        {	name: "starttime", caption: "开始时间", editorType: "DropDownEditor",nullable:false},
	        {	name: "endtime", caption: "结束时间", editorType: "DropDownEditor",nullable:false},
	        {	name: "power", caption: "电力值", editorType: "TextEditor",dataAlign:"left",valueType:"number",validType: "REGEXP",validOptions:{expression: /^(-)?(0|[1-9]\d{0,11})(\.\d{1,2})?$/},validateMessage:"请输入最多12位整数,最多2位小数的数字.",formatString:"0.00"},
	        {	name: "qtytype", caption: "峰谷类型", editorType: "DropDownEditor"},
	        {	name: "bight", caption: "典型曲线名称" , editorType: "TextEditor",visible:false},
	        {	name: "startdate", caption: "开始时间", editorType: "TextEditor",visible:false},
	        {	name: "enddate", caption: "结束时间", editorType: "TextEditor",visible:false},
	        {	name: "explanation", caption: "备注", editorType: "TextEditor",visible:false}
            ],
            // 构造列排序条件，如果有多列，则以逗号分隔。例sorter: "school ASC, class DESC"
            displayRowNumber:true,
            displayCheckBox: true,
	        displayPrimaryKey:false,//列表是否显示主键
            allowEditing: true, //列表默认不可编辑
	        pageSize : 20,
	        onload:_initLoad,
            entityContainer: gridEntityContainer
        });
//        me._dataGrid.setFilter({"transinfoid":me.controller.transInfoId});
        me.hSplit1.addControl(me._dataGrid,1);
    }
    function _initLoad(){
    	me._dataGrid.columns["transinfoid"].setVisible(false);
    	me._dataGrid.columns["bight"].setVisible(false);
    	me._dataGrid.columns["startdate"].setVisible(false);
    	me._dataGrid.columns["enddate"].setVisible(false);
    	me._dataGrid.columns["explanation"].setVisible(false);
    	
    }
    
//    function _initEditors(){
//    	if(me.controller.flag=='add'){
//    		 me._dataGrid.setFilter({"transinfoid":me.controller.transInfoId});
//    	     me._dataGrid.load();
//    	}else{
//    		me.nameTextEditor.setValue(me.controller.bight);
//    		me.startDataTimeEditor.setValue(me.controller.startdate);
//    		me.endDataTimeEditor.setValue(me.controller.enddate);
//    		if(me.controller.transInfoId!=null){
//        		me._dataGrid.setFilter({"transinfoid":me.controller.transInfoId});
//        	}
//    		if(me.bight!=null){
//    			me._dataGrid.setFilter({"bight":me.controller.bight});
//    		}
//        	me._dataGrid.load();
//    	}
//    }
    me.getDataGrid = function(){
    	return me._dataGrid;
    }
    me.getNameValue = function(){
    	return me.nameTextEditor.value;
    }
    me.getStartTimeValue = function(){
    	return me.startDataTimeEditor.value;
    }
    me.getEndTimeValue = function(){
    	return me.endDataTimeEditor.value;
    }
	me.endOfClass(arguments)
    return me;
};