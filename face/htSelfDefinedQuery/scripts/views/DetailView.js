$ns("htSelfDefinedQuery.views");

$import("mx.controls.ToolBar");
$import("mx.datacontainers.FormEntityContainer");
$import("mx.datacontrols.DataForm");


htSelfDefinedQuery.views.DetailView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    /**
     * id
     */
    me.objID = null;
   
    me._mainView = null;
    
    

    /* 初始化单表单控件 */
    base.init = me.init;
    me.init = function()
    {
        base.init();
        _initControls();
    };

    me.load = function()
    {
    }

    function _initControls()
    {
    	
        _init();
        me.on("activate", me.controller._onactivate);
    }
	

    
    function _init(){
    	
    	//页面布局分为上下两部分
    	me.mSplit = new mx.containers.HSplit({rows:"80,auto",borderThick:0});	//该控件将布局分为上下两部分
    	me.addControl(me.mSplit);	//添加布局控件
    	
    	//业务操作
    	me.panel1 = new mx.containers.Panel({width:"100%",height:"80",title:"业务操作"});	//业务操作
    	me.mSplit.addControl(me.panel1,0);	//添加panel1容器
    	
    	me.container = new mx.containers.Container({width:"100%",height:"30",padding:"2",top:20});	//放置确定、关闭等的容器
    	me.addControl(me.container);	//添加container1容器
    	me.button = new mx.controls.Button({text:"确定",left:10,onclick: me.controller._btnSave_onclick});	//确定按钮
    	me.container.addControl(me.button);	//添加按钮
    	me.button2 = new mx.controls.Button({text:"关闭",left:40,onclick: me.controller._btnClose_onclick});	//关闭按钮
    	me.container.addControl(me.button2);	//添加按钮
    	me.panel1.addControl(me.container);
    	
    	
    	//添加显示列区域内包含的容器Contianer
    	me.container7 = new mx.containers.Container({width:"100%",height:"40",padding:"2"});	//显示列等的容器
    	me.mSplit.addControl(me.container7,1);	//添加container7容器
    	
    	me.check1 =new mx.editors.CheckEditor(
    			{
    				value: 'F',//设置默认为选中
    				caption:"合同类型",//合同类型
    				text:"col3",
    				padding:4,
    				onchanged: _checkEditor_changed
    			});
    	me.container7.addControl(me.check1);	//添加复选框
    	me.check2 =new mx.editors.CheckEditor(
    			{
    				value: 'F',//设置默认为选中
    				caption:"合同周期",//合同周期
    				text:"col3",
    				padding:4,
    				onchanged: _checkEditor_changed
    			});
    	me.container7.addControl(me.check2);	//添加复选框
    	me.check3 =new mx.editors.CheckEditor(
    			{
    				value: 'F',//设置默认为选中
    				caption:"时间类型",//时间类型
    				text:"col3",
    				padding:4,
    				onchanged: _checkEditor_changed
    			});
    	me.container7.addControl(me.check3);	//添加复选框
    	me.check4 =new mx.editors.CheckEditor(
    			{
    				value: 'F',//设置默认为选中
    				caption:"合同状态",//合同状态
    				text:"col3",
    				padding:4,
    				onchanged: _checkEditor_changed
    			});
    	me.container7.addControl(me.check4);	//添加复选框
    	me.check5 =new mx.editors.CheckEditor(
    			{
    				value: 'F',//设置默认为选中
    				caption:"购电方",//购电方
    				text:"col3",
    				padding:4,
    				onchanged: _checkEditor_changed
    			});
    	me.container7.addControl(me.check5);	//添加复选框
    	me.check6 =new mx.editors.CheckEditor(
    			{
    				value: 'F',//设置默认为选中
    				caption:"售电方",//售电方
    				text:"col3",
    				padding:4,
    				onchanged: _checkEditor_changed
    			});
    	me.container7.addControl(me.check6);	//添加复选框
    	me.check7 =new mx.editors.CheckEditor(
    			{
    				value: 'F',//设置默认为选中
    				caption:"业务场景",//合同类型
    				text:"col3",
    				padding:4,
    				onchanged: _checkEditor_changed
    			});
    	me.container7.addControl(me.check7);	//添加复选框
    	me.check8 =new mx.editors.CheckEditor(
    			{
    				value: 'F',//设置默认为选中
    				caption:"合同序列",//合同序列
    				text:"col3",
    				padding:4,
    				onchanged: _checkEditor_changed
    			});
    	me.container7.addControl(me.check8);	//添加复选框
    	
    }
	
    function _checkEditor_changed(check)
    {
		
    }
    
	me.endOfClass(arguments)
    return me;
};