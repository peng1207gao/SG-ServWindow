$ns("coContractbackupInfo.views");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGridSearchBox");
$import("mx.datacontrols.ComplexGrid");
$import("mx.datacontrols.DataGrid");
$import("mx.containers.Panel");
$import("mx.editors.TextEditor");
$import("mx.editors.ComboEditor");
$import("mx.rpc.RESTClient");

coContractbackupInfo.views.SdfAddView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    //网格列表
    var _dataGrid = null;
    //表单窗口对象
    var _detailWin = null;
    
    var restClient = new mx.rpc.RESTClient();
    
    base.init = me.init;
    me.init = function()
    {
        base.init();
        _initControls();
    };
    
    function _initControls()
    {	
    	initLayout();
	    _initDataGrid();
    	_initDetailWindow();
    }
    
    function _initDataGrid()
    {
    	
    }
    
  //初始化页面布局
    function initLayout(){
    	//页面上面分成上下两部分
    	me.mSplit = new mx.containers.HSplit({rows:"55px,auto",borderThick:0});
    	me.addControl(me.mSplit);
    	me.panel = new mx.containers.Panel({title:"业务操作",width:"100%"});
    	me.mSplit.addControl(me.panel,0);//添加panel
    	me.container = new mx.containers.Container({padding:"2px",width:"100%"});
    	me.panel.addControl(me.container);//添加container
    	
    	if(me.params.readType != '1'){//1的时候只读

    		//保存按钮
    		var btn_save = new mx.controls.Button({text : "保存",left : 20});
    		me.container.addControl(btn_save);
    	
    		//关闭按钮
    		var btn_close = new mx.controls.Button({text : "关闭",left : 20});
    		me.container.addControl(btn_close);
    		//关闭按钮点击事件
    		btn_close.on("click", function() {
    			me.viewPort.close();//关闭当前页面
    		}); 
    	
    		me.panel1 = new mx.containers.Panel({title:"合同输电方信息",width:"100%"});
    		me.mSplit.addControl(me.panel1,1);//添加panel
    		me.container1 = new mx.containers.Container({padding:"2px",width:"100%"});
    		me.panel1.addControl(me.container1);//添加container
    		me.label1 = new mx.controls.Label({text:"合同名称：",fontSize:10,left:"20px",width:"85px"});
    		var textEditor = new mx.editors.TextEditor({
    			    "width" : "200px",
    			    "value" : me.contractName, 
    			    "readOnly":"true",
    			    "right":"2px"
    			});
    	
    		var comboEditor = new mx.editors.ComboEditor({
    			"width" : "200px",
    			"value" : "国网山东电力"
    		});
    	
    		me.label2 = new mx.controls.Label({text:"*",fontSize:10,left:"20px",css:{color:"red"}, width:"5px"});
    		me.label3 = new mx.controls.Label({text:"输电方：",fontSize:10,left:"24px"});

    		me.container1.addControl(me.label1);//“合同名称”放入container1
    		me.container1.addControl(textEditor);//合同名称输入框放入container1
    		me.container1.addControl(me.label2);//“*”放入container1
    		me.container1.addControl(me.label3);//输电方放入container1
    		me.container1.addControl(comboEditor);//输电方下拉框放入container1
    	
    		//保存按钮点击事件
    		btn_save.on("click", function() {
    			if(comboEditor.getValue() != "" && comboEditor.getValue() != null){
    				var contractid = me.contractId;
    				var guid = me.guid;
    				var sdf = comboEditor.getValue();
    				var result = restClient.getSync(coContractbackupInfo.mappath(
    					"~/rest/cocontransenergyinfo/saveSdf?contractid="+contractid+"&guid="+guid+"&sdf="+sdf));
    				if(result.resultValue.items[0] == "success"){
    					alert("保存成功");
    				}else if(result.resultValue.items[0] == "false"){
    					alert("已存在对应的输电方");
    				}else if(result.resultValue.items[0] == "fail"){
    					alert("保存失败");
    				}
    			}else{
    				alert("输电方不能为空");
    			}
    		});
    	}
    }
    
    /**
     * 初始化表单视图窗口对象
     */
    function _initDetailWindow(){
    	_detailWin = utils.commonFun.WinMananger.create({
			reusable: true,//是否复用
			width:640,
			height:400,
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