$ns("contracttemplate.views");

contracttemplate.views.DetailView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    base.init = me.init;
    me.init = function()
    {
        base.init();

        _initControls();
    };
    
    function _initControls()
    {	_initLayout();//选择窗口布局
        me.on("activate", me.controller._onactivate);
    }
    
    function _initLayout(){
    	me.container = new mx.containers.Container(
    			{padding:"2px",height:"50px",width:"100%"});
    	
    	me.container1 = new mx.containers.Container(
    			{padding:"2px",height:"30px",width:"100%"});
    	
    	me.container2 = new mx.containers.Container(
    			{padding:"2px",height:"30px",width:"100%"});
    	var vSplit = new mx.containers.VSplit({
    	    cols:"65px, auto"
    	});
    	me.addControl(me.container);
//    	me.addControl(me.container_1);
    	me.addControl(me.container1);
//    	me.addControl(me.container_2);
    	me.addControl(me.container2);
    	me.container.addControl(vSplit);
    	me.container_1 = new mx.containers.Container(
    			{padding:"2px",height:"30px",width:"100%"});
    	vSplit.addControl(me.container_1,0);
    	me.container_2 = new mx.containers.Container(
    			{padding:"2px",height:"30px",width:"100%"});
    	vSplit.addControl(me.container_2,1);
    	var formLabel = new mx.controls.Label({
    	    text: "格式：",
    	    textAlign: "right",
    	    width:"60px",
    	    verticalAlign: "middle"
    	});
    	var operateLabel = new mx.controls.Label({
    	    text: "操作：",
    	    textAlign: "right",
    	    width:"60px",
    	    verticalAlign: "middle"
    	});
    	me.container_1.addControl(formLabel);
    	me.container1.addControl(operateLabel);
    	me.sureBtn = new mx.controls.Button({text:"确定",left:40});	//查询合同按钮
    	me.cancelBtn = new mx.controls.Button({text:"取消",left:60});
    	me.container2.addControl(me.sureBtn);
    	me.container2.addControl(me.cancelBtn);
    	me.sureBtn.on("click",_sureMethod);
    	me.cancelBtn.on("click",_cancelMethod);
    	
    	var checkListEditor = new mx.editors.CheckListEditor(
    			{
    			    type:"radio",
    			    items:[
    			        {text: "变电站1", value: "xx-001"},
    			        {text: "变电站3", value: "xx-003"}]
//    			    onitemchanged:_changed
    			});
    	me.container_2.addControl(checkListEditor);
    }
    function _sureMethod(){
    	alert(me.controller.tempId+";"+me.controller.ids.length);
    	me.controller.mainView._openWin.hide();
    }
    
    function _cancelMethod(){
    	me.controller.mainView._openWin.hide();
    }
 

    return me.endOfClass(arguments);
};