$ns("htSelfDefinedQuery.views");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGridSearchBox");
$import("mx.datacontrols.ComplexGrid");
$import("mx.containers.HSplit");
$import("mx.containers.Panel");
$import("mx.rpc.RESTClient");
//$import("utils.dropDownEditor.MarketTreeClass");
//$import("utils.dropDownEditor.contractTypeTree");
$import("mx.utils.ExcelUtil");
$import("mx.containers.DockPage");
$import("mx.containers.DockPanel");
//$import("htadd.views.MainViewController");
//$import("htSelfDefinedQuery.views.DetailViewController");




htSelfDefinedQuery.views.MainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    //网格列表
    var _dataGrid = null;
    var _dataGrid2 = null;//第一个表格(统计信息)
    //表单窗口对象
    var _detailWin = null;
    var marketId;//登陆人场景
    var restClient = new mx.rpc.RESTClient();
    var flowflag = "";	//流转状态的值
    var contractName = "";	//合同名称
	var papercontractcode = "";	//纸质合同编号
	var syscontractcode = "";	//系统合同编号
	var contractcyc = "";		//合同周期
	var prepcontractflag = "";	//合同状态
	var contracttype = "";	//合同类型
	var purchaser = "";	//购电方
	var seller = "";		//售电方
	var marketid = "";	//业务场景
	var searchDateType = "";	//时间类型
	var startDate = "";	//开始时间
	var endDate = "";	//结束时间
	var sequenceid = "";	//合同序列
	var powertype = "";    //售电方发电类型
	
	var _queryNos = [];//查询条件编码集合
	var queryNum = 0;//查询条件总个数

    base.init = me.init;
    me.init = function()
    {
        base.init();
        _initControls();
    };
    
    function _initControls()
    {
    	init();//初始化方法
    	initLayout();//初始化布局
    	initComponent();//初始化控件
    	_initDataGrid();//初始化表格
    	//_initDataGrid2();//初始化第一个表格(统计信息)
    	search();//查询数据
    }
    
    /**
     * 初始化方法(主要获取初始化参数)
     */
    function init(){
    	var items = restClient.getSync(htSelfDefinedQuery.mappath("~/rest/coContractbaseinfoV2Act/init")).resultValue.items;
    	marketId = items[0];
    }
    
    /**
     * 页面初始化布局(主要控制页面控件的布局)
     */
    function initLayout(){
    	//页面布局分为上下两部分
//    	me.mSplit = new mx.containers.HSplit({rows:"233,auto",borderThick:0});	//该控件将布局分为上下两部分
//    	me.addControl(me.mSplit);	//添加布局控件
    	
    	//布局上部分分为"业务查询"和"业务操作"两部分
    	
    	//业务查询区域
    	me.panel1 = new mx.containers.Panel({width:"100%",height:"115",title:"业务查询"});	//业务查询panel容器
    	me.addControl(me.panel1);	//添加panel1容器
    	
    	//添加业务查询区域内包含的容器Contianer
    	me.container1 = new mx.containers.Container({width:"100%",height:"30",top:4});	//放置标签、下拉框、文本框等的容器
    	me.panel1.addControl(me.container1);	//添加container1容器
    	me.container2 = new mx.containers.Container({width:"100%",height:"30",top:4});	//放置标签、下拉框、文本框等的容器
    	me.panel1.addControl(me.container2);	//添加container2容器
    	me.container3 = new mx.containers.Container({width:"100%",height:"30",top:4});	//放置标签、下拉框、文本框等的容器
    	me.panel1.addControl(me.container3);	//添加container2容器
    
    	
    	//业务操作区域
    	me.panel2 = new mx.containers.Panel({width:"100%",height:"50",title:"业务操作"}); //业务操作panel容器
    	me.addControl(me.panel2);	//添加panel2容器
    	
    	//添加业务操作区域内包含的容器Contianer
    	me.container6 = new mx.containers.Container({width:"100%",height:"40"});	//放置按钮等的容器
    	me.panel2.addControl(me.container6);	//添加container4容器
    	
    	//页面下方区域分成上下两部分
//    	me.mDSplit = new mx.containers.HSplit({rows:"50,auto"});	//分成上下两部分的控件
//    	me.addControl(me.mDSplit);		//添加控件
    	
    	//显示列区域
    	me.panel3 = new mx.containers.Panel({width:"100%",height:"55",title:"显示列"}); //显示列panel容器
    	me.addControl(me.panel3);	//添加panel2容器
    	
    	//添加显示列区域内包含的容器Contianer
    	me.container7 = new mx.containers.Container({width:"100%",height:"40",padding:"2"});	//显示列等的容器
    	me.panel3.addControl(me.container7);	//添加container7容器
    	
    	
    }
    
    
    /**
     * 初始化控件(添加页面的标签、下拉框、文本框、按钮、表格等控件)
     */
    function initComponent(){
    	//me.container1添加业务查询第一行的控件
    	me.label6 = new mx.controls.Label({text:"合同类型：",width:"7%",textAlign:"right",right:4});	//合同类型标签
    	//me.container1.addControl(me.label6);	//添加标签
    	me.component6 = utils.dropDownEditor.ContractTypeTree.ContractTypeDropDownEditor(false,true); //合同类型下拉框
    	me.component6.setWidth("15%");
    	me.component6.on("changed",changeDrops);//给合同类型添加onchanged触发事件
    	//me.container1.addControl(me.component6);//添加下拉框
    	me.linkEditor1 = new mx.editors.LinkEditor(
    			{
    			     imageKey : "delete",//指定图标名称。
    			     display:1,
    			     width:"3%",
    			     onclick : deleteQuery
    			});
    	me.label4 = new mx.controls.Label({text:"合同周期：",width:"7%",textAlign:"right",right:4});	//合同周期标签
    	//me.container1.addControl(me.label4);	//添加标签
    	me.component4 = new mx.editors.DropDownEditor({displayMember:"name",valueMember:"value",width:"15%",
    		url:htSelfDefinedQuery.mappath("~/../ETradePublicUtils/rest/baGencodeTypeList/getList?type=23")	//获取合同周期下拉框数据
    		}); //合同周期下拉框
    	me.linkEditor2 = new mx.editors.LinkEditor(
    			{
    			    "imageKey" : "delete",//指定图标名称。
    			    display:2,
    			    width:"3%",
    			    onclick : deleteQuery
    			});
    	//me.container1.addControl(me.component4);	//添加下拉框
    	me.label10 = new mx.controls.Label({text:"时间类型：",width:"7%",textAlign:"right",right:4});	//时间类型标签
    	//me.container1.addControl(me.label10);	//添加标签
    	me.component10 = new mx.editors.DropDownEditor({displayMember:"name",valueMember:"value",width:"15%",items:
			[{name:"合同开始时间",value:0},{name:"合同录入时间",value:1},{name:"合同签订时间",value:2}],
			value:0});	//时间类型下拉框
    	//me.container31.addControl(me.component10); //添加下拉框
    	me.linkEditor3 = new mx.editors.LinkEditor(
    			{
    			    "imageKey" : "delete",//指定图标名称。
    			    display:3,
    			    width:"3%",
    			    onclick : deleteQuery
    			});
    	me.label11 = new mx.controls.Label({text:"开始时间：",width:"7%",textAlign:"right",right:4});	//开始时间标签
    	//me.container1.addControl(me.label11);	//添加标签
    	me.component11 = new mx.editors.DateTimeEditor({width:"15%"}); //开始时间时间框
    	//me.container1.addControl(me.component11);	//添加时间框
    	me.linkEditor11 =  new mx.editors.LinkEditor(
    			{
    				width:"3%"
    			});
    	me.label12 = new mx.controls.Label({text:"结束时间：",width:"7%",textAlign:"right",right:4});	//结束时间标签
    	//me.container1.addControl(me.label12);	//添加标签
    	me.component12 = new mx.editors.DateTimeEditor({width:"15%"}); //结束时间时间框
    	var date  = new Date()
    	me.component11.setValue(date.getFullYear()+"-01-01");
    	me.component12.setValue(date.getFullYear()+"-12-31");
    	//me.container1.addControl(me.component12);	//添加时间框
    	me.linkEditor22 =  new mx.editors.LinkEditor(
    			{
    				width:"3%"
    			});
    	
    	//me.container1添加业务查询第二行的控件
    	me.label5 = new mx.controls.Label({text:"合同状态：",width:"7%",textAlign:"right",right:4});	//合同状态标签
    	//me.container2.addControl(me.label5);	//添加标签
    	me.component5 = new mx.editors.DropDownEditor({displayMember:"name",valueMember:"value",width:"15%",
    		url : htSelfDefinedQuery.mappath("~/../ETradePublicUtils/rest/baGencodeTypeList/getList?type=67")	//获取合同状态下拉框数据
    		}); //合同状态下拉框
    	//me.container2.addControl(me.component5);	//添加下拉框
    	me.linkEditor4 = new mx.editors.LinkEditor(
    			{
    			    "imageKey" : "delete",//指定图标名称。
    			    display:4,
    			    width:"3%",
    			    onclick : deleteQuery
    			});
    	me.label7 = new mx.controls.Label({text:"购电方：",width:"7%",textAlign:"right",right:4});	//购电方标签
    	//me.container2.addControl(me.label7);	//添加标签
    	me.component7 = utils.dropDownEditor.CommonDropEditor.gsAutoComPleteEditor("","1");	//购电方下拉框
    	utils.dropDownEditor.CommonDropEditor.autoInit(me.component7,'1');
    	me.component7.setWidth("15%");
    	//解决因为内容过长下拉框显示不全的问题
    	var fun = new utils.commonFun.DropDownEditorFun();
		fun.resizeAutocomplete(me.component7);
    	//me.container2.addControl(me.component7); //添加文本框
    	me.linkEditor5 = new mx.editors.LinkEditor(
    			{
    			    "imageKey" : "delete",//指定图标名称。
    			    display:5,
    			    width:"3%",
    			    onclick : deleteQuery
    			});
    	//setComponent7Items();	//给购电方下拉框赋值
    	me.label8 = new mx.controls.Label({text:"售电方：",width:"7%",textAlign:"right",right:4});	//售电方标签
    	//me.container2.addControl(me.label8);	//添加标签
    	me.component8 = utils.dropDownEditor.CommonDropEditor.gsAutoComPleteEditor("","2"); //售电方下拉框
    	utils.dropDownEditor.CommonDropEditor.autoInit(me.component8,'2');
    	me.component8.setWidth("15%");
    	fun.resizeAutocomplete(me.component8);
    	//me.container2.addControl(me.component8);	//添加下拉框
    	me.linkEditor6 = new mx.editors.LinkEditor(
    			{
    			    "imageKey" : "delete",//指定图标名称。
    			    display:6,
    			    width:"3%",
    			    onclick : deleteQuery
    			});
    	//setComponent8Items();	//给售电方下拉框赋值
    	
    	me.label9 = new mx.controls.Label({text:"业务场景：",width:"7%",textAlign:"right",right:4});	//业务场景标签
    	//me.container2.addControl(me.label9);	//添加标签
    	me.component9 = new utils.dropDownEditor.MarketTree.MarketDropDownEditor(true,null,false); //业务场景下拉框
    	me.component9.setWidth("15%");
    	//me.container2.addControl(me.component9);	//添加下拉框
    	me.linkEditor7 = new mx.editors.LinkEditor(
    			{
    			    "imageKey" : "delete",//指定图标名称。
    			    display:7,
    			    width:"3%",
    			    onclick : deleteQuery
    			});
    	me.label13 = new mx.controls.Label({text:"合同序列：",width:"7%",textAlign:"right",right:4});	//合同序列标签
    	//me.container2.addControl(me.label13);	//添加标签
    	me.component13 = new mx.editors.DropDownEditor({displayMember:"name",valueMember:"value",autoMatchInput:true,
    		allowEditing:true,width:"15%"}); //合同序列下拉框
    	//解决因为内容过长下拉框显示不全的问题
		fun.resizeListEditor(me.component13);
    	//me.container2.addControl(me.component13);	//添加下拉框
    	me.linkEditor8 = new mx.editors.LinkEditor(
    			{
    			    "imageKey" : "delete",//指定图标名称。
    			    display:8,
    			    width:"3%",
    			     onclick : deleteQuery
    			});
    	setComponent13Items("");	//给合同序列赋值
    	
    	
    	//me.container6添加业务操作第一行的按钮
    	me.button1 = new mx.controls.Button({text:"查询合同",left:20,onclick:searchCon});	//查询合同按钮
    	me.container6.addControl(me.button1);	//添加按钮
    	
    	me.button2 = new mx.controls.Button({text:"增加查询条件",left:60,onclick:queryAdd});	//合同导出按钮
    	me.container6.addControl(me.button2);	//添加按钮
    	
    	me.check1 =new mx.editors.CheckEditor(
    			{
    				value: 'T',//设置默认为选中
    				caption:"主合同编号",//主合同编号
    				text:"col1",
    				padding:4,
    				onchanged: _checkEditor_changed
    			});
    	me.container7.addControl(me.check1);	//添加复选框
    	me.check2 =new mx.editors.CheckEditor(
    			{
    				value: 'T',//设置默认为选中
    				caption:"合同名称",//合同名称
    				text:"col2",
    				padding:4,
    				onchanged: _checkEditor_changed
    			});
    	me.container7.addControl(me.check2);	//添加复选框
    	me.check3 =new mx.editors.CheckEditor(
    			{
    				value: 'T',//设置默认为选中
    				caption:"合同类型",//合同类型
    				text:"col3",
    				padding:4,
    				onchanged: _checkEditor_changed
    			});
    	me.container7.addControl(me.check3);	//添加复选框
    	me.check4 =new mx.editors.CheckEditor(
    			{
    				value: 'T',//设置默认为选中
    				caption:"购电方",//购电方
    				text:"col4",
    				padding:4,
    				onchanged: _checkEditor_changed
    			});
    	me.container7.addControl(me.check4);	//添加复选框
    	me.check5 =new mx.editors.CheckEditor(
    			{
    				value: 'T',//设置默认为选中
    				caption:"售电方",//售电方
    				text:"col5",
    				padding:4,
    				onchanged: _checkEditor_changed
    			});
    	me.container7.addControl(me.check5);	//添加复选框
    	me.check6 =new mx.editors.CheckEditor(
    			{
    				value: 'T',//设置默认为选中
    				caption:"开始时间",//开始时间
    				text:"col8",
    				padding:4,
    				onchanged: _checkEditor_changed
    			});
    	me.container7.addControl(me.check6);	//添加复选框
    	me.check7 =new mx.editors.CheckEditor(
    			{
    				value: 'T',//设置默认为选中
    				caption:"截止时间",//截止时间
    				text:"col9",
    				padding:4,
    				onchanged: _checkEditor_changed
    			});
    	me.container7.addControl(me.check7);	//添加复选框
    	me.check8 =new mx.editors.CheckEditor(
    			{
    				value: 'T',//设置默认为选中
    				caption:"总电量",//总电量
    				text:"col6",
    				padding:4,
    				onchanged: _checkEditor_changed
    			});
    	me.container7.addControl(me.check8);	//添加复选框
    	me.check9 =new mx.editors.CheckEditor(
    			{
    				value: 'T',//设置默认为选中
    				caption:"电价",//电价
    				text:"col7",
    				padding:4,
    				onchanged: _checkEditor_changed
    			});
    	me.container7.addControl(me.check9);	//添加复选框
//    	me.check10 =new mx.editors.CheckEditor(
//    			{
//    				value: 'T',//设置默认为选中
//    				caption:"合同流转状态 ",//合同流转状态 
//    				text:"col10",
//    				padding:4,
//    				onchanged: _checkEditor_changed
//    			});
//    	me.container7.addControl(me.check10);	//添加复选框
    	me.check11 =new mx.editors.CheckEditor(
    			{
    				value: 'T',//设置默认为选中
    				caption:"合同状态",//合同状态
    				text:"col10",
    				padding:4,
    				onchanged: _checkEditor_changed
    			});
    	me.container7.addControl(me.check11);	//添加复选框
//    	me.check12 =new mx.editors.CheckEditor(
//    			{
//    				value: 'T',//设置默认为选中
//    				caption:"维护人 ",//维护人 
//    				text:"col12",
//    				padding:4,
//    				onchanged: _checkEditor_changed
//    			});
//    	me.container7.addControl(me.check12);	//添加复选框
//    	me.check13 =new mx.editors.CheckEditor(
//    			{
//    				value: 'T',//设置默认为选中
//    				caption:"维护时间 ",//维护时间 
//    				text:"col13",
//    				padding:4,
//    				onchanged: _checkEditor_changed
//    			});
//    	me.container7.addControl(me.check13);	//添加复选框
//    	me.check14 =new mx.editors.CheckEditor(
//    			{
//    				value: 'T',//设置默认为选中
//    				caption:"维护单位 ",//维护单位
//    				text:"col14",
//    				padding:4,
//    				onchanged: _checkEditor_changed
//    			});
//    	me.container7.addControl(me.check14);	//添加复选框
    	me.check15 =new mx.editors.CheckEditor(
    			{
    				value: 'T',//设置默认为选中
    				caption:"附件下载  ",//附件下载 
    				text:"col15",
    				padding:4,
    				onchanged: _checkEditor_changed
    			});
    	me.container7.addControl(me.check15);	//添加复选框
    	
    	
    	//添加统计信息
    	
    	
    	//me.mDSplit.addControl(me.dockPanel_left,0);
    	
    	//测试
//    	me.button10.on("click",me.controller._btnNew_onclick);
    	
    }
    
    
    
    function _checkEditor_changed(check)
    {
		var cloumns = _dataGrid.columns;
		for(var i=0;i<cloumns.length;i++){
			
			if(_dataGrid.columns[i].name == check.target.text ){
				_dataGrid.columns[i].setVisible(check.target.isChecked());
			}
			
		}
    }


    
    /**
     * 查询合同方法
     */
    function search(){
    	//contractName = me.component1.getValue() == null ? "" : me.component1.getValue();	//合同名称
    	//papercontractcode = me.component2.getValue() == null ? "" : me.component2.getValue();	//纸质合同编号
    	//syscontractcode = me.component3.getValue() == null ? "" : me.component3.getValue();	//系统合同编号
    	contractcyc = me.component4.getValue() == null ? "" : me.component4.getValue();		//合同周期
    	//prepcontractflag = me.component5.getValue() == null ? "" : me.component5.getValue();	//合同状态
    	flowflag = me.component5.getValue() == null ? "" : me.component5.getValue();	//合同状态
    	contracttype = me.component6.getValue() == null ? "" : me.component6.getValue();	//合同类型
    	purchaser = me.component7.getValue() == null ? "" : me.component7.getValue();	//购电方
    	seller = me.component8.getValue() == null ? "" : me.component8.getValue();		//售电方
    	marketid = me.component9.getValue() == null ? "" : me.component9.getValue();	//业务场景
    	searchDateType = me.component10.getValue() == null ? "" : me.component10.getValue();	//时间类型
    	startDate = me.component11.getValue() == null ? "" : me.component11.getValue();	//开始时间
    	endDate = me.component12.getValue() == null ? "" : me.component12.getValue();	//结束时间
    	sequenceid = me.component13.getValue() == null ? "" : me.component13.getValue();	//合同序列
    	
    	loadGrid1();	//加载第一个表格数据
//    	loadGrid2();	//加载第二个表格数据
    	

    }
    /**
     * 查询合同按钮
     */
    function searchCon(){
    	flowflag = "";	//状态恢复为空
    	search();	//调用查询方法
    }	
    /**
     * 加载第一个表格数据
     */
    function loadGrid1(){
    	//给dataGrid添加过滤条件
    	_dataGrid.setFilter({contractName:contractName,papercontractcode:papercontractcode,syscontractcode:syscontractcode,contractcyc:contractcyc,
    		prepcontractflag:prepcontractflag,contracttype:contracttype,purchaser:purchaser,seller:seller,marketid:marketid,searchDateType:searchDateType,
    		startDate:startDate,endDate:endDate,sequenceid:sequenceid,marketId:marketId,flowflag:flowflag,powertype:powertype});
    	//加载表格数据
    	//_dataGrid.load();
    	_dataGrid.load(null, function (){
        	if(!me.check1.isChecked()){
        		var cloumns = _dataGrid.columns;
        		for(var i=0;i<cloumns.length;i++){
        			if(_dataGrid.columns[i].name == me.check1.text ){
        				_dataGrid.columns[i].setVisible(false);
        			}
        		}
        		
        	}
        	if(!me.check2.isChecked()){
        		var cloumns = _dataGrid.columns;
        		for(var i=0;i<cloumns.length;i++){
        			if(_dataGrid.columns[i].name == me.check2.text ){
        				_dataGrid.columns[i].setVisible(false);
        			}
        		}
        		
        	}
        	if(!me.check3.isChecked()){
        		var cloumns = _dataGrid.columns;
        		for(var i=0;i<cloumns.length;i++){
        			if(_dataGrid.columns[i].name == me.check3.text ){
        				_dataGrid.columns[i].setVisible(false);
        			}
        		}
        		
        	} 
        	if(!me.check4.isChecked()){
        		var cloumns = _dataGrid.columns;
        		for(var i=0;i<cloumns.length;i++){
        			if(_dataGrid.columns[i].name == me.check4.text ){
        				_dataGrid.columns[i].setVisible(false);
        			}
        		}
        		
        	}
        	if(!me.check5.isChecked()){
        		var cloumns = _dataGrid.columns;
        		for(var i=0;i<cloumns.length;i++){
        			if(_dataGrid.columns[i].name == me.check5.text ){
        				_dataGrid.columns[i].setVisible(false);
        			}
        		}
        		
        	}
        	if(!me.check6.isChecked()){
        		var cloumns = _dataGrid.columns;
        		for(var i=0;i<cloumns.length;i++){
        			if(_dataGrid.columns[i].name == me.check6.text ){
        				_dataGrid.columns[i].setVisible(false);
        			}
        		}
        		
        	}
        	if(!me.check7.isChecked()){
        		var cloumns = _dataGrid.columns;
        		for(var i=0;i<cloumns.length;i++){
        			if(_dataGrid.columns[i].name == me.check7.text ){
        				_dataGrid.columns[i].setVisible(false);
        			}
        		}
        		
        	}
        	if(!me.check8.isChecked()){
        		var cloumns = _dataGrid.columns;
        		for(var i=0;i<cloumns.length;i++){
        			if(_dataGrid.columns[i].name == me.check8.text ){
        				_dataGrid.columns[i].setVisible(false);
        			}
        		}
        		
        	}
        	if(!me.check9.isChecked()){
        		var cloumns = _dataGrid.columns;
        		for(var i=0;i<cloumns.length;i++){
        			if(_dataGrid.columns[i].name == me.check9.text ){
        				_dataGrid.columns[i].setVisible(false);
        			}
        		}
        		
        	}
//        	if(!me.check10.isChecked()){
//        		var cloumns = _dataGrid.columns;
//        		for(var i=0;i<cloumns.length;i++){
//        			if(_dataGrid.columns[i].name == me.check10.text ){
//        				_dataGrid.columns[i].setVisible(false);
//        			}
//        		}
//        		
//        	}
        	if(!me.check11.isChecked()){
        		var cloumns = _dataGrid.columns;
        		for(var i=0;i<cloumns.length;i++){
        			if(_dataGrid.columns[i].name == me.check11.text ){
        				_dataGrid.columns[i].setVisible(false);
        			}
        		}
        		
        	}
//        	if(!me.check12.isChecked()){
//        		var cloumns = _dataGrid.columns;
//        		for(var i=0;i<cloumns.length;i++){
//        			if(_dataGrid.columns[i].name == me.check12.text ){
//        				_dataGrid.columns[i].setVisible(false);
//        			}
//        		}
//        		
//        	}
//        	if(!me.check13.isChecked()){
//        		var cloumns = _dataGrid.columns;
//        		for(var i=0;i<cloumns.length;i++){
//        			if(_dataGrid.columns[i].name == me.check13.text ){
//        				_dataGrid.columns[i].setVisible(false);
//        			}
//        		}
//        		
//        	}
//        	if(!me.check14.isChecked()){
//        		var cloumns = _dataGrid.columns;
//        		for(var i=0;i<cloumns.length;i++){
//        			if(_dataGrid.columns[i].name == me.check14.text ){
//        				_dataGrid.columns[i].setVisible(false);
//        			}
//        		}
//        		
//        	}
        	if(!me.check15.isChecked()){
        		var cloumns = _dataGrid.columns;
        		for(var i=0;i<cloumns.length;i++){
        			if(_dataGrid.columns[i].name == me.check15.text ){
        				_dataGrid.columns[i].setVisible(false);
        			}
        		}
        		
        	}
        	//合同类型为发电权合同时 改表头名称
        	var restClient = new mx.rpc.RESTClient();
	    	var result = restClient.getSync(htSelfDefinedQuery.mappath("~/rest/coContractbaseinfoV2Act/isfdqht"),{"params":JSON.stringify({"contracttype":contracttype})});
	    	if(result==true){
	    		_dataGrid.columns.col4.setCaption("被替代方");
	    		_dataGrid.columns.col5.setCaption("替代方");
	    	}
    	}); 
    	
    	
    }
    /**
     * 加载第二个表格数据
     */
    function loadGrid2(){
    	//给dataGrid添加过滤条件
    	_dataGrid2.setFilter({contractName:contractName,papercontractcode:papercontractcode,syscontractcode:syscontractcode,contractcyc:contractcyc,
    		prepcontractflag:prepcontractflag,contracttype:contracttype,purchaser:purchaser,seller:seller,marketid:marketid,searchDateType:searchDateType,
    		startDate:startDate,endDate:endDate,sequenceid:sequenceid,marketId:marketId,flowflag:flowflag,powertype:powertype});
    	//加载表格数据
    	_dataGrid2.load();
    }
  
    /**
     * 增加查询条件功能页面
     */
    function queryAdd(){
    	
    	_detailView = new htSelfDefinedQuery.views.DetailViewController().getView();
    	_detailView._mainView = me;
    	if (!_detailView.initialized)
		{
			_detailView.init();
		}
    	
    	//_detailView.objID = _dataGrid.getCheckedIDs()[0];
        
        var _detailWin = utils.commonFun.WinMananger.create({
			reusable: true,//是否复用
			width: 400,
			height: 300,
			title:"增加查询条件"
		});
        _detailView.load();
        _detailWin.setView(_detailView);
        _detailWin.showDialog();

    }
    /**
     * 删除查询条件
     */
    function deleteQuery(linkEditor){
    	
    	for(var i=0;i<_queryNos.length;i++){
    		if(_queryNos[i] == linkEditor.target.display){
    			if(linkEditor.target.display == '1'){
    				me.label6.parent.removeControl(me.label6);
    				me.component6.parent.removeControl(me.component6);
    				me.linkEditor1.parent.removeControl(me.linkEditor1);
    				me.component6.setValue(""); 
    	    		
    	    	}else if(linkEditor.target.display == '2'){
    	    		
    	    		me.label4.parent.removeControl(me.label4);
    				me.component4.parent.removeControl(me.component4);
    				me.linkEditor2.parent.removeControl(me.linkEditor2);
    				me.component4.setValue(""); 
    				
    	    	}else if(linkEditor.target.display == '3'){
    	    		
    	    		me.label10.parent.removeControl(me.label10);
    				me.component10.parent.removeControl(me.component10);
    				me.linkEditor3.parent.removeControl(me.linkEditor3);
    				me.label11.parent.removeControl(me.label11);
    				me.component11.parent.removeControl(me.component11);
    				me.linkEditor11.parent.removeControl(me.linkEditor11);
    				me.component11.setValue(""); 
    				me.label12.parent.removeControl(me.label12);
    				me.component12.parent.removeControl(me.component12);
    				me.linkEditor22.parent.removeControl(me.linkEditor22);
    				me.component12.setValue(""); 
    	    		
    	    	}else if(linkEditor.target.display == '4'){
    	    		
    	    		me.label5.parent.removeControl(me.label5);
    				me.component5.parent.removeControl(me.component5);
    				me.linkEditor4.parent.removeControl(me.linkEditor4);
    				me.component5.setValue(""); 
    				
    	    	}else if(linkEditor.target.display == '5'){
    	    		
    	    		me.label7.parent.removeControl(me.label7);
    				me.component7.parent.removeControl(me.component7);
    				me.linkEditor5.parent.removeControl(me.linkEditor5);
    				me.component7.setValue(""); 
    				
    	    	}else if(linkEditor.target.display == '6'){
    	    		
    	    		me.label8.parent.removeControl(me.label8);
    				me.component8.parent.removeControl(me.component8);
    				me.linkEditor6.parent.removeControl(me.linkEditor6);
    				me.component8.setValue(""); 
    	    		
    	    	}else if(linkEditor.target.display == '7'){
    	    		
    	    		me.label9.parent.removeControl(me.label9);
    				me.component9.parent.removeControl(me.component9);
    				me.linkEditor7.parent.removeControl(me.linkEditor7);
    				me.component9.setValue(""); 
    	    		
    	    	}else if(linkEditor.target.display == '8'){
    	    		
    	    		me.label13.parent.removeControl(me.label13);
    				me.component13.parent.removeControl(me.component13);
    				me.linkEditor8.parent.removeControl(me.linkEditor8);
    				me.component13.setValue(""); 
    				
    	    	}
    			_queryNos.removeAt(i);
    			if(linkEditor.target.display == '3'){
    				queryNum = queryNum - 3;
    			}else {
    				queryNum = queryNum - 1;
    			}
    			
    		}
    	}
    	
    }
    
    /**
     * 增加查询条件方法
     */
   me.queryAddCodition = function(queryNos){
	   
    	if(queryNos.length>0){
    		for(var i=0;i<queryNos.length;i++){
    			
    			queryAddNo(queryNos[i]);
    			queryNum = queryNum +1;
    			
    		}
    	}
    }
   
   /**
    * 增加某个查询条件
    */
   function getContainer(no){
	   if(no=='3'){
		   if(me.container1.controls.length<4){
			   return me.container1;
		   }else if(me.container2.controls.length<4){
			   return me.container2;
		   }else {
			   return me.container3;
		   }
	   }else {
		   if(me.container1.controls.length<11){
			   return me.container1;
		   }else if(me.container2.controls.length<11){
			   return me.container2;
		   }else {
			   return me.container3;
		   }
	   }
	  
   }
    
    
    /**
     * 增加某个查询条件
     */
    function queryAddNo(no){
    	
    	if(queryAddFlag(no)){
    		var container = getContainer(no);
	    	if(no == '1'){
	    		container.addControl(me.label6);//添加标签
	    		container.addControl(me.component6);//添加下拉框
	    		container.addControl(me.linkEditor1);//添加标签
	    	}else if(no == '2'){
	    		container.addControl(me.label4);	//添加标签
	    		container.addControl(me.component4);	//添加下拉框
	    		container.addControl(me.linkEditor2);//添加标签
	    	}else if(no == '3'){
	    		container.addControl(me.label10);	//添加标签
	    		container.addControl(me.component10); //添加下拉框
	    		container.addControl(me.linkEditor3);//添加标签
	    		queryNum = queryNum +1;
//	    		container = getContainer();
	    		container.addControl(me.label11);	//添加标签
	    		container.addControl(me.component11);	//添加时间框
	    		container.addControl(me.linkEditor11);//添加标签
	    		queryNum = queryNum +1;
//	    		container = getContainer();
	    		container.addControl(me.label12);	//添加标签
	    		container.addControl(me.component12);	//添加时间框
	    		container.addControl(me.linkEditor22);//添加标签
	    	}else if(no == '4'){
	    		container.addControl(me.label5);	//添加标签
	    		container.addControl(me.component5);	//添加下拉框
	    		container.addControl(me.linkEditor4);//添加标签
	    	}else if(no == '5'){
	    		container.addControl(me.label7);	//添加标签
	    		container.addControl(me.component7); //添加文本框
	    		container.addControl(me.linkEditor5);//添加标签
	    	}else if(no == '6'){
	    		
	    		container.addControl(me.label8);	//添加标签
	    		container.addControl(me.component8);	//添加下拉框
	    		container.addControl(me.linkEditor6);//添加标签
	    	}else if(no == '7'){
	    		
	    		container.addControl(me.label9);	//添加标签
	    		container.addControl(me.component9);	//添加下拉框
	    		container.addControl(me.linkEditor7);//添加标签
	    	}else if(no == '8'){
	    		container.addControl(me.label13);	//添加标签
	    		container.addControl(me.component13);	//添加下拉框
	    		container.addControl(me.linkEditor8);//添加标签
	    	}
	    	
	    	_queryNos.add(no);
     	}
    }
    
    /**
     * 判断查询条件是否已存在
     */
    function queryAddFlag(no){
    	
    	var flag = true;
    	
    	for(var i=0;i<_queryNos.length;i++){
    		if(_queryNos[i] == no){
    			flag = false;
    		}
    	}
    	
    	return flag;
    }
 
    
    /**
     * 初始化第一个表格
     */
    function _initDataGrid2()
    {
        var restUrl = "~/rest/coContractbaseinfoV2Act/getStatics";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : htSelfDefinedQuery.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "col0"
        });
        
        /* 初始化 DataGrid */
        _dataGrid2 = new mx.datacontrols.ComplexGrid({   
			// 构造查询属性。
			alias: "htSelfDefinedQueryMainViewDataGrid2",
			columns:[
			{	name: "col0", caption: "合同总数" , editorType: "TextEditor",align:"center",dataAlign:"center"	},         
			{	name: "col1", caption: "已备案合同个数" , editorType: "TextEditor",align:"center",dataAlign:"center",renderCell:search2	},
			{	name: "col2", caption: "已签订未备案" , editorType: "TextEditor",align:"center",dataAlign:"center",renderCell:search2	},  
			{	name: "col3", caption: "流转中" , editorType: "TextEditor",align:"center",dataAlign:"center",renderCell:search2	},
			{	name: "col4", caption: "起草中" , editorType: "TextEditor",align:"center",dataAlign:"center",renderCell:search2	},
			{	name: "col5", caption: "应签合同总电量" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
			{	name: "col6", caption: "已签合同总电量" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
			{	name: "col7", caption: "平均电价" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
			{	name: "col8", caption: "最高电价" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
			{	name: "col9", caption: "最低电价" , editorType: "TextEditor",align:"center",dataAlign:"center"	}
			
            ],
            // 构造列排序条件，如果有多列，则以逗号分隔。例sorter: "school ASC, class DESC"
            displayCheckBox: false,
	        displayPrimaryKey:true,//列表是否显示主键
            allowEditing: false, //列表默认不可编辑
            allowPaging : false,
	        pageSize : 10,
            entityContainer: gridEntityContainer,
            displayToolBar : false
        });
        me.mDSplit.addControl(_dataGrid2,0);//添加表格
    }
    /**
     * 初始化下方表格
     */
    function _initDataGrid()
    {
        var restUrl = "~/rest/coContractbaseinfoV2Act/";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : htSelfDefinedQuery.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "col0"
        });
        
        /* 初始化 DataGrid */
        _dataGrid = new mx.datacontrols.DataGrid({   
			// 构造查询属性。
			alias: "htSelfDefinedQueryMainViewDataGrid",
			columns:[
			{	name: "col0", caption: "主键" , editorType: "TextEditor",align:"center",dataAlign:"center"	},         
			{	name: "col1", caption: "纸质合同编号" , editorType: "TextEditor",align:"center",dataAlign:"center" },
			{	name: "col2", caption: "合同名称" , editorType: "TextEditor",align:"center",dataAlign:"center",renderCell:detailContact},  
			{	name: "col3", caption: "合同类型" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
			{	name: "col4", caption: "购电方" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
			{	name: "col5", caption: "售电方" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
//			{	name: "col6", caption: "总电量" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
//			{	name: "col7", caption: "电价" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
			{	name: "energy", caption: "总电量" , editorType: "TextEditor",align:"center",dataAlign:"center",formatString:"0.0000"	},
			{	name: "contractprice", caption: "电价" , editorType: "TextEditor",align:"center",dataAlign:"center",formatString:"0.0000"	},
			{	name: "col8", caption: "开始时间" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
			{	name: "col9", caption: "截止时间" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
			//{	name: "col10", caption: "合同流转状态" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
			{	name: "col10", caption: "合同状态" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
//			{	name: "col12", caption: "维护人" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
//			{	name: "col13", caption: "维护时间" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
//			{	name: "col14", caption: "维护单位" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
			{	name: "contracttype", caption: "contracttype" , editorType: "TextEditor",value:'1'	},
			{	name: "col15", caption: "附件下载" , editorType: "TextEditor",align:"center",dataAlign:"center",renderCell:downlist	}
			
            ],
            // 构造列排序条件，如果有多列，则以逗号分隔。例sorter: "school ASC, class DESC"
            displayCheckBox: false,
	        displayPrimaryKey:false,//列表是否显示主键
            allowEditing: false, //列表默认不可编辑
            allowSorting:true,//列允许排序
	        pageSize : 20,
            entityContainer: gridEntityContainer,
            displayRowNumber : true,
            displayToolBar : false,
            height:document.body.offsetHeight - 220 ,
            displayColSplitLine: false,
            rowNumberColWidth : 40,
            onload:_init
        });
        me.addControl(_dataGrid);//添加表格
    }
    
    function _init(){
    	_dataGrid.columns["contracttype"].setVisible(false);
    }
    
    /**
     * 给合同类型添加onchanged触发事件
     */
    changeDrops = function(e){	
    	var changecontracttype = e.target.value==null?"":e.target.value;
    	setComponent13Items(changecontracttype);
	}
    
    /**
     * 给购电方下拉框赋值
     */
    function setComponent7Items(){
    	var type =  me.component6.getValue() == null ? "" : me.component6.getValue();	//获取合同类型值
    	var items = restClient.getSync(htSelfDefinedQuery.
    			mappath("~/rest/coContractbaseinfoV2Act/getComponentItems"),
    			{type:type,marketId:marketId,value:1}).resultValue;	//获取购电方数据
    	me.component7.setItems(items);
    }	
    /**
     * 给售电方下拉框赋值
     */
    function setComponent8Items(){
    	var type =  me.component6.getValue() == null ? "" : me.component6.getValue();	//获取合同类型值
    	var items = restClient.getSync(htSelfDefinedQuery.
    			mappath("~/rest/coContractbaseinfoV2Act/getComponentItems"),
    			{type:type,marketId:marketId,value:2}).resultValue;	//获取售电方数据
    	me.component8.setItems(items);
    }	
    /**
     * 给合同序列赋值
     */
    function setComponent13Items(changecontracttype){
    	var items = restClient.getSync(htquery.
    			mappath("~/rest/coContractbaseinfoV2Act/getComponent13Items"),
    			{marketId:marketId,contracttype:changecontracttype}).resultValue;	//获取售电方数据
    	me.component13.setItems(items);
    }
    /**
     * 给第一个表格添加超链接查询
     */
    function search2(p_item,$p_cell){
    	var id = $p_cell[0].id;	//获取该单元格在哪一行
    	$p_cell[0].onclick = function(){search3(id);}	//添加查询事件
    	$p_cell.html("<a href=# >"+p_item.values[id]+"</a>");	//添加超链接
    	
    }
    
    /**
     * 附件下载加超链接到下载列表
     */
    function downlist(p_item,$p_cell){
    	
    	var objId = p_item.getValue("col0"); 
    	$p_cell[0].onclick = function(){downdetail(objId);}	//添加查询事件
    	$p_cell.html("<a href=# >"+p_item.getValue("col15")+"</a>");	//添加超链接
    }
    
    function downdetail(objId){
     	var _detailView = new htquery.views.DetailViewController().getView();
        _detailView.objID=objId;
        
        var _detailWin = utils.commonFun.WinMananger.create({
			reusable: true,//是否复用
			width: "600",
			height: "400",
			title:"下载页面"
		});
        _detailView._init();
        _detailWin.setView(_detailView);
        _detailWin.showDialog();
    }
    
    /**
     * 合同名称加超链接查看详细信息
     */
    function detailContact(p_item,$p_cell){
    	
    	var objId = p_item.getValue("col0"); 
    	var contracttype = p_item.getValue("contracttype"); 
    	var coTName = p_item.getValue("col3"); 
    	var htxlList = restClient.getSync(htSelfDefinedQuery.mappath("~/rest/coContractbaseinfoV2Act/getHtxl"),{contractid:objId});
    	var htxlName = null;
    	if(htxlList!=null && htxlList.length>0){
    		for(var i=0;i<htxlList.length;i++){
    			htxlName = htxlList[i][1];
    		}
    	}
    	$p_cell[0].onclick = function(){detail(objId,contracttype,coTName,htxlName);}	//添加查询事件
    	$p_cell.html("<a href=# >"+p_item.getValue("col2")+"</a>");	//添加超链接
    	
    }
    
    function detail(objId,contracttype,coTName,htxlName){
      	var _detailView = new htadd.views.zjxzViewController().getView({objID:objId,readType:"1",coType:contracttype,coTName:coTName,htxlName:htxlName});
    	if (!_detailView.initialized)
		{
			_detailView.init();
		}
        
        var _detailWin = utils.commonFun.WinMananger.create({
			reusable: true,//是否复用
			width: "90%",
			height: "90%",
			title:"合同详情页面"
		});
        _detailWin.setView(_detailView);
        _detailWin.showDialog();
    }
    
    /**
     * 数据统计里边的查询方法
     */
    function search3(id){
    	if(id === "col1") flowflag = "5";
    	else if(id === "col2") flowflag = "4";
    	else if(id === "col3") flowflag = "2";
    	else if(id === "col4") flowflag = "1";
		search();	//调用查询方法
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