$ns("cocontractmembership.views");

$import("mx.containers.HSplit");
$import("mx.containers.Panel");
$import("mx.controls.Button");
$import("mx.containers.Container");
$import("mx.editors.DateTimeEditor");
$import("mx.editors.TextEditor");
$import("mx.editors.DropDownEditor");
$import("mx.controls.Label");

cocontractmembership.views.MainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    //表单窗口对象
    var _detailWin = null;
    
    /**
     * 视图中的编辑器集合，这些编辑器值会提交到服务端，如果只作为界面展示则可以不放入该集合中
     * 
     * 包括：合同角色 contractroleType，合同类型 contractType等
     */
    me.dataFormEditors = {};
    
    /**
     * 数据重复提交标识，0：数据没提交；1：数据已经提交
     * 
     * 在MainViewController._save_submit_data函数中判断与控制
     */
    me.submitflag = 0;
    
    base.init = me.init;
    
    me.init = function()
    {
        base.init();
        _initControls();
    };
    
    function _initControls()
    {
    	_initLayout();
    	_initOperationPanel();
    	_initDataForm();
    	_initDetailWindow();
        me.on("activate", me.controller._onactivate);
    }
    
    /**
     * 界面布局，分为上下两个部分
     */
    function _initLayout() {
    	me.hSplit = new mx.containers.HSplit({
    		borderThick:"0px", rows:"60, auto"
    	});
    	
    	//业务操作区域
    	me.operationPanel = new mx.containers.Panel(
    		{width:"100%", height:"50", title:"业务操作"}
    	);
    	me.hSplit.addControl(me.operationPanel, 0);
    	
    	me.addControl(me.hSplit);
    }
    
    /**
     * 布局上部分，添加一个保存按钮
     */
    function _initOperationPanel() {
    	var operationButtonContainer = new mx.containers.Container(
    		{width:"100%",height:"30",padding:"2"}
    	);
    	me.operationPanel.addControl(operationButtonContainer);
    	
    	var searchButton = new mx.controls.Button(
        	{text:"保存",left:20,onclick:me.controller._btnSave_onclick}
        );
        operationButtonContainer.addControl(searchButton);
    }
    
    /**
     * 布局下部分，添加一些编辑器，并设置他们的布局
     */
    function _initDataForm() {
    	var dataFormContainer = new mx.containers.Container(
    		{width:"100%",height:"100%",padding:"2"}
    	);
    	me.hSplit.addControl(dataFormContainer, 1);
    	
    	//合同角色
    	var htjsLContainer = new mx.containers.Container(
    		{width:"100%",height:"40",padding:"2"}
    	);
    	dataFormContainer.addControl(htjsLContainer);
    	var hxLabel = new mx.controls.Label(
    		{text:"*",width:"68",textAlign:"right",right:5,css:{"color":"red"}}
    	);
    	htjsLContainer.addControl(hxLabel);
    	var htjsLabel = new mx.controls.Label(
    		{text:"合同角色:",width:"52",textAlign:"left",right:4}
    	);
    	htjsLContainer.addControl(htjsLabel);
    	me.dataFormEditors.contractroleType = new mx.editors.DropDownEditor(
    		{width:"300", allowEditing: false, displayMember: "name", valueMember: "value", 
    			url: cocontractmembership.mappath("~/../ETradePublicUtils/rest/baGencodeTypeList/getList?type=78"),
    			onchanged: me.controller._contractroleType_dropDownEditor_onchanged}
    	);
    	htjsLContainer.addControl(me.dataFormEditors.contractroleType);
    	
    	//合同类型
    	var htlxContainer = new mx.containers.Container(
    		{width:"100%",height:"40",padding:"2"}
    	);
    	dataFormContainer.addControl(htlxContainer);
    	var htlxLabel = new mx.controls.Label(
    		{text:"合同类型:",width:"120",textAlign:"right",right:4}
    	);
    	htlxContainer.addControl(htlxLabel);
    	me.dataFormEditors.contractType = new mx.editors.TextEditor(
    		{width:"300", readOnly: true}
    	);
    	htlxContainer.addControl(me.dataFormEditors.contractType);
    	
    	//准入成员选择
    	var extractContainer = new mx.containers.Container(
    		{width:"100%",height:"40",padding:"2"}
    	);
    	dataFormContainer.addControl(extractContainer);
    	var btLabel = new mx.controls.Label(
    		{text:"*",width:"40",textAlign:"right",right:5,css:{"color":"red"}}
    	);
    	extractContainer.addControl(btLabel);
    	var extractLabel = new mx.controls.Label(
    		{text:"准入成员选择 :",width:"80",textAlign:"left",right:4}
    	);
    	extractContainer.addControl(extractLabel);
    	me.dataFormEditors.extract = new mx.editors.TextEditor(
    		{width:"300", readOnly: true, textMode: 'multiline'}
    	);
    	extractContainer.addControl(me.dataFormEditors.extract);
    	var searchButton = new mx.controls.Button(
        	{text:"选择",left:10,onclick:me.controller._btnSearch_onclick}
        );
    	extractContainer.addControl(searchButton);
    	
    }
    
    /**
     * 初始化编辑器中的值，该函数由外部执行，或者是在me.controller._onactivate中执行
     * 
     * 当然在load之前必须设置一些参数，例如：合同类型ID，合同类型名称，窗口对象等
     * 
     * 可以参考MainViewController类中定义的变量
     */
    me.load = function() {
    	//
    	me.dataFormEditors.contractroleType.setValue(null);
    	me.dataFormEditors.extract.setValue(null);
    	me.controller._reflesh_extract_ids(null);
    	
    	//
    	me.dataFormEditors.contractType.setValue(
    			me.controller.contracttypename != null ? 
    					me.controller.contracttypename : 
    						"亲，外部controller没有设置合同类型名称，请设置先");
    }
    
    /**
     * 初始化表单视图窗口对象
     */
    function _initDetailWindow(){
    	_detailWin = utils.commonFun.WinMananger.create({
			reusable: true,//是否复用
			width:840,
			height:580,
			title:"表单维护"
		});
    	
    	//绑定创建关闭事件
    	_detailWin.off("hiding",me.controller._detailWindow_onhiding);
    	_detailWin.on("hiding",me.controller._detailWindow_onhiding);
    }
    
    /**
     * 获取表单视图窗口对象
     */
    me.getDetailWindow = function(){
    	return _detailWin;
    }
    
    return me.endOfClass(arguments);
};