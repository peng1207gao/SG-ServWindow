$ns("jfcontractaffix.views");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGrid");


jfcontractaffix.views.JfContractAffixDetailView = function()
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
    var _dataGrid = null;
    

    /* 初始化单表单控件 */
    base.init = me.init;
    me.init = function()
    {
        base.init();
        _initControls();
    };

//    me.load = function()
//    {
//    	//加载表单信息
//    	_form.load(me.objID);
//    }

    function _initControls()
    {
    	_initHSplit();//把页面分为上下两部分
    	_initButton();//创建业务操作按钮
	    _initDataGrid();
        me.on("activate", me.controller._onactivate);
    }
	
	function _initHSplit(){
		me.hSplit = new mx.containers.HSplit({
			rows: "100,auto"
		});
		me.addControl(me.hSplit);
	}
	
	function _initButton(){
		me.panel = new mx.containers.Panel({
			title: "业务操作",
			id:"panel",
			border:"1"
		});
		me.hSplit.addControl(me.panel);
		me.container = new mx.containers.Container({
			id: "contaniner",
    		padding: "2px"
		});
		me.panel.addControl(me.container);
		//下载按钮
		me.downLoadBtn = new mx.controls.Button({
			text: "下载",
    		left: 20,
    		onclick: me.controller._btn_downLoad
		});
		me.container.addControl(me.downLoadBtn);
		//删除按钮
		me.deleteBtn = new mx.controls.Button({
			text: "删除",
			left: 40,
			onclick: me.controller._btn_delete
		});
		me.container.addControl(me.deleteBtn);
		//关闭按钮
		me.closeBtn = new mx.controls.Button({
			text: "关闭",
			left: 60,
			onclick: me.controller._btn_close
		});
		me.container.addControl(me.closeBtn);
	}
    
    function _initDataGrid()
    {
    	var restUrl = "~/rest/sjjcjfdljyhtwb/downList";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : jfcontractaffix.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "objId"
        });
        
        _dataGrid = new mx.datacontrols.DataGrid({
			
			alias:"jfcontractaffixJfContractAffixDetailViewDataGrid",
			displayPrimaryKey: false,
			columns: [
	        {	name: "objId", caption: "记录ID", editorType: "TextEditor", visible:false},
	        {	name: "companyname", caption: "运行单位名称", editorType: "TextEditor"},
	        {	name: "companyid", caption: "运行单位编码", editorType: "TextEditor"},
	        {	name: "contractname", caption: "合同名称", editorType: "TextEditor"},
	        {	name: "contractid", caption: "合同ID", editorType: "TextEditor"},
	        {	name: "contractno", caption: "合同编号", editorType: "TextEditor"},
	        {	name: "appendixname", caption: "合同文本名称", editorType: "TextEditor"},
	        {	name: "appendixtype", caption: "非结构平台附件类型", editorType: "TextEditor"},
		    {	name: "contracttext", caption: "合同文本所在非结构化平台ID", editorType: "TextEditor"}
		    ],
		    displayCheckBox: true,
	        displayPrimaryKey:false,//列表是否显示主键
            allowEditing: false, //列表默认不可编辑
            displayRowNumber: true,
	        pageSize : 20,
            entityContainer: gridEntityContainer
        });
        
        me.hSplit.addControl(_dataGrid,1);
    }

    /**
     * 获取表单对象
     */
    me.getDataGrid = function(){
		return _dataGrid;
    }
	
	me.endOfClass(arguments)
    return me;
};