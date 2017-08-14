$ns("coContractbaseinfoV2Act.views");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGridSearchBox");
$import("mx.datacontrols.ComplexGrid");
$import("mx.containers.HSplit");
$import("mx.containers.Panel");
$import("mx.rpc.RESTClient");
$import("mx.utils.ExcelUtil");
$import("mx.containers.DockPage");
$import("mx.containers.DockPanel");

coContractbaseinfoV2Act.views.MainView = function()
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
	me.coType = ""; 
	
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
//    	_initDataGrid2();//初始化第一个表格(统计信息)
    	search();//查询数据
    }
    
    /**
     * 初始化方法(主要获取初始化参数)
     */
    function init(){
    	var items = restClient.getSync(coContractbaseinfoV2Act.mappath("~/rest/coContractbaseinfoV2Act/init")).resultValue.items;
    	marketId = items[0];
    }
    
    /**
     * 页面初始化布局(主要控制页面控件的布局)
     */
    function initLayout(){
    	
    	
    	//页面布局分为上下两部分
//    	me.mSplit = new mx.containers.HSplit({rows:"203,auto",borderThick:0});	//该控件将布局分为上下两部分
//    	me.addControl(me.mSplit);	//添加布局控件
    	
    	//业务查询区域
    	me.panel1 = new mx.containers.Panel({width:"100%",height:"140",title:"业务查询"});	//业务查询panel容器 当有高级查询按钮时height:"50"
//    	me.mSplit.addControl(me.panel1,0);	//添加panel1容器
    	me.addControl(me.panel1);
    	
    	//添加业务查询区域内包含的容器Contianer
    	me.container1 = new mx.containers.Container({width:"100%",height:"30",padding:"2"});	//放置标签、下拉框、文本框等的容器
    	me.panel1.addControl(me.container1);	//添加container1容器
    	me.container2 = new mx.containers.Container({width:"100%",height:"30",padding:"2"});	//放置标签、下拉框、文本框等的容器
    	me.container2.setDisplay("block");
    	me.panel1.addControl(me.container2);	//添加container2容器
    	me.container3 = new mx.containers.Container({width:"100%",height:"30",padding:"2"});	//放置标签、下拉框、文本框等的容器
    	me.container3.setDisplay("block");
    	me.panel1.addControl(me.container3);	//添加container3容器
    	me.container4 = new mx.containers.Container({width:"100%",height:"30",padding:"2"});	//放置标签、下拉框、文本框等的容器
    	me.container4.setDisplay("block");
    	me.panel1.addControl(me.container4);	//添加container4容器
//    	me.container5 = new mx.containers.Container({width:"100%",height:"30",padding:"2"});	//放置标签、下拉框、文本框等的容器
//    	me.container5.setDisplay("none");
//    	me.panel1.addControl(me.container5);	//添加container5容器
    	
    	//业务操作区域
    	me.panel2 = new mx.containers.Panel({width:"100%",height:"80",title:"业务操作"}); //业务操作panel容器,当需要显示经法功能的三个按钮时，heght:80
//    	me.mSplit.addControl(me.panel2,0);	//添加panel2容器
    	me.addControl(me.panel2);
    	
    	//统计信息区域
    	me.panel3 = new mx.containers.Panel({width:"100%",height:"80",title:"统计信息"}); //业务操作panel容器
//    	me.mSplit.addControl(me.panel3,0);	//添加panel2容器
    	me.addControl(me.panel3);
    	
    	//添加业务操作区域内包含的容器Contianer
    	me.container6 = new mx.containers.Container({width:"100%",height:"30",padding:"2"});	//放置按钮等的容器
    	me.panel2.addControl(me.container6);	//添加container4容器
    	me.container7 = new mx.containers.Container({width:"100%",height:"30",padding:"2"});	//放置按钮等的容器
//    	me.container7.setDisplay("none");//勿删
    	me.panel2.addControl(me.container7);	//添加container5容器
    	
    	//添加业务操作区域内包含的容器Contianer
    	me.container8 = new mx.containers.Container({width:"100%",height:"30",padding:"2",top:4});	//放置按钮等的容器
    	me.panel3.addControl(me.container8);	//添加container4容器
    	me.container9 = new mx.containers.Container({width:"100%",height:"30",padding:"2"});	//放置按钮等的容器
    	me.panel3.addControl(me.container9);	//添加container5容器
    	
    	//页面下方区域分成上下两部分
//    	me.mDSplit = new mx.containers.HSplit({rows:"80,auto"});	//分成上下两部分的控件
//    	me.mSplit.addControl(me.mDSplit,1);		//添加控件
    	
    	
    	
    }
    
    me._refresh = function(){
    	search();
    }
    /**
     * 初始化控件(添加页面的标签、下拉框、文本框、按钮、表格等控件)
     */
    function initComponent(){
    	//me.container8添加统计信息第一行区域
    	me.tlabel1 = new mx.controls.Label({text:"合同总数：",width:"80",textAlign:"right",right:4});	//合同总数标签
    	me.container8.addControl(me.tlabel1);	//添加标签
    	me.tlabel12 = new mx.controls.Label({text:"",width:"80",textAlign:"left",right:4});	//合同总数值标签
    	me.container8.addControl(me.tlabel12);	//添加标签
    	me.tlabel2 = new mx.controls.Label({text:"已签订/已备案数：",width:"110",textAlign:"right",right:4});	//合同总数标签
    	me.container8.addControl(me.tlabel2);	//添加标签
//    	me.tlabel22 = new mx.controls.Label({text:"",width:"80",textAlign:"left",right:4});	//合同总数值标签
    	me.tlabel22 = new mx.editors.LinkEditor(
    			{
    			    width : "80",//指定控件宽度。
    			    textAlign:"left",
    			    right:4
    			});
    	me.tlabel22.on("click", function()
		{
    		flowflag = "'5','4'";
    		searchflow();	//调用查询方法
		});
    	me.container8.addControl(me.tlabel22);	//添加标签
    	me.tlabel3 = new mx.controls.Label({text:"应签合同总电量：",width:"100",textAlign:"right",right:4});	//合同总数标签
    	me.container8.addControl(me.tlabel3);	//添加标签
    	me.tlabel32 = new mx.controls.Label({text:"",width:"100",textAlign:"left",right:4});	//合同总数值标签
    	me.container8.addControl(me.tlabel32);	//添加标签
    	me.tlabel4 = new mx.controls.Label({text:"已签合同总电量：",width:"100",textAlign:"right",right:4});	//合同总数标签
    	me.container8.addControl(me.tlabel4);	//添加标签
    	me.tlabel42 = new mx.controls.Label({text:"",width:"100",textAlign:"left",right:4});	//合同总数值标签
    	me.container8.addControl(me.tlabel42);	//添加标签
    	
    	me.tlabel5 = new mx.controls.Label({text:"平均电价：",width:"80",textAlign:"right",right:4});	//合同总数标签
    	me.container9.addControl(me.tlabel5);	//添加标签
    	me.tlabel52 = new mx.controls.Label({text:"",width:"80",textAlign:"left",right:4});	//合同总数值标签
    	me.container9.addControl(me.tlabel52);	//添加标签
    	me.tlabel6 = new mx.controls.Label({text:"最高电价：",width:"110",textAlign:"right",right:4});	//合同总数标签
    	me.container9.addControl(me.tlabel6);	//添加标签
    	me.tlabel62 = new mx.controls.Label({text:"",width:"80",textAlign:"left",right:4});	//合同总数值标签
    	me.container9.addControl(me.tlabel62);	//添加标签
    	me.tlabel7 = new mx.controls.Label({text:"最低电价：",width:"100",textAlign:"right",right:4});	//合同总数标签
    	me.container9.addControl(me.tlabel7);	//添加标签
    	me.tlabel72 = new mx.controls.Label({text:"",width:"80",textAlign:"left",right:4});	//合同总数值标签
    	me.container9.addControl(me.tlabel72);	//添加标签
    	
    	
    	//me.container1添加业务查询第一行的控件
    	me.label10 = new mx.controls.Label({text:"时间类型：",width:"13%",textAlign:"right",right:4});	//时间类型标签
//    	me.container1.addControl(me.label10);	//添加标签
    	me.component10 = new mx.editors.DropDownEditor({displayMember:"name",valueMember:"value",width:"18%",items:
    					[{name:"合同开始时间",value:0},{name:"合同录入时间",value:1},{name:"合同签订时间",value:2}],
    					value:0});	//时间类型下拉框
//    	me.container1.addControl(me.component10); //添加下拉框
    	me.label11 = new mx.controls.Label({text:"开始时间：",width:"13%",textAlign:"right",right:4});	//开始时间标签
    	me.container1.addControl(me.label11);	//添加标签
    	me.component11 = new mx.editors.DateTimeEditor({width:"18%"}); //开始时间时间框
    	me.container1.addControl(me.component11);	//添加时间框
    	var date  = new Date()
    	me.component11.setValue(date.getFullYear()+"-01-01");
    	me.label12 = new mx.controls.Label({text:"截止时间：",width:"13%",textAlign:"right",right:4});	//结束时间标签
    	me.container1.addControl(me.label12);	//添加标签
    	me.component12 = new mx.editors.DateTimeEditor({width:"18%"}); //结束时间时间框
    	me.container1.addControl(me.component12);	//添加时间框
    	me.component12.setValue(date.getFullYear()+"-12-31");
    	me.label4 = new mx.controls.Label({text:"合同周期：",width:"13%",textAlign:"right",right:4});	//合同周期标签
    	me.container1.addControl(me.label4);	//添加标签
    	me.component4 = new mx.editors.DropDownEditor({displayMember:"name",valueMember:"value",width:"18%",
    		url:coContractbaseinfoV2Act.mappath("~/../ETradePublicUtils/rest/baGencodeTypeList/getList?type=23")	//获取合同周期下拉框数据
    		}); //合同周期下拉框
    	me.container1.addControl(me.component4);	//添加下拉框
    	
    	//me.container2添加业务查询第二行的控件
    	//解决因为内容过长下拉框显示不全的问题
    	var fun = new utils.commonFun.DropDownEditorFun();
    	me.label6 = new mx.controls.Label({text:"合同类型：",width:"13%",textAlign:"right",right:4});	//合同类型标签
    	me.container2.addControl(me.label6);	//添加标签
    	me.component6 = utils.dropDownEditor.ContractTypeTree.ContractTypeDropDownEditor(false,true); //合同类型下拉框
    	me.component6.setWidth("18%");
    	me.container2.addControl(me.component6);	//添加下拉框
    	me.component6.on("changed",changeDrops);//给合同类型添加onchanged触发事件
    	me.label13 = new mx.controls.Label({text:"合同序列：",width:"13%",textAlign:"right",right:4});	//合同序列标签
    	me.container2.addControl(me.label13);	//添加标签
    	me.component13 = new mx.editors.DropDownEditor({displayMember:"name",valueMember:"value",autoMatchInput:true,
    		allowEditing:true,width:"18%"}); //合同序列下拉框
		fun.resizeListEditor(me.component13);
    	me.container2.addControl(me.component13);	//添加下拉框
    	setComponent13Items("");	//给合同序列赋值
    	
    	me.label5 = new mx.controls.Label({text:"合同状态：",width:"13%",textAlign:"right",right:4});	//合同状态标签
    	me.container2.addControl(me.label5);	//添加标签
    	me.component5 = new mx.editors.DropDownEditor({displayMember:"name",valueMember:"value",width:"200",
    		url : coContractbaseinfoV2Act.mappath("~/../ETradePublicUtils/rest/baGencodeTypeList/getList?type=67")	//获取合同状态下拉框数据
    		}); //合同状态下拉框
    	me.component5.setWidth("18%");
    	me.container2.addControl(me.component5);	//添加下拉框
    	
    	//me.container3添加业务查询第三行的控件
    	me.label14 = new mx.controls.Label({text:"售电方发电类型：",width:"13%",textAlign:"right",right:4});	//售电方标签
    	me.container3.addControl(me.label14);	//添加标签
    	me.component14 = new mx.editors.DropDownEditor({displayMember:"name",valueMember:"value",width:"200",
    		url : coContractbaseinfoV2Act.mappath("~/../ETradePublicUtils/rest/baGencodeTypeList/getList?type=13")	//获取合同状态下拉框数据
    		}); //合同状态下拉框
    	me.component14.setWidth("18%");
    	me.container3.addControl(me.component14);	//添加下拉框
    	me.label8 = new mx.controls.Label({text:"售电方：",width:"13%",textAlign:"right",right:4});	//售电方标签
    	me.container3.addControl(me.label8);	//添加标签
    	me.component8 = utils.dropDownEditor.CommonDropEditor.gsAutoComPleteEditor("","2"); //售电方下拉框
    	utils.dropDownEditor.CommonDropEditor.autoInit(me.component8,'2');
    	me.component8.setWidth("18%");
    	//解决因为内容过长下拉框显示不全的问题
    	fun.resizeAutocomplete(me.component8);
    	me.container3.addControl(me.component8);	//添加下拉框
    	//setComponent8Items();	//给售电方下拉框赋值
    	me.label7 = new mx.controls.Label({text:"购电方：",width:"13%",textAlign:"right",right:4});	//购电方标签
    	me.container3.addControl(me.label7);	//添加标签
//    	me.component7 = new mx.editors.DropDownEditor({displayMember:"name",valueMember:"value",width:"200"});	//购电方下拉框
//    	me.container3.addControl(me.component7); //添加文本框
//    	setComponent7Items();	//给购电方下拉框赋值
    	me.component7 = utils.dropDownEditor.CommonDropEditor.gsAutoComPleteEditor("","1");	//购电方下拉框
    	utils.dropDownEditor.CommonDropEditor.autoInit(me.component7,'1');
    	me.component7.setWidth("18%");
    	//解决因为内容过长下拉框显示不全的问题
    	fun.resizeAutocomplete(me.component7);
    	me.container3.addControl(me.component7); //添加文本框
    	
    	//me.container4添加业务查询第四行的控件
    	me.label1 = new mx.controls.Label({text:"合同名称：",width:"13%",textAlign:"right",right:4});	//合同名称标签
    	me.container4.addControl(me.label1);	//添加标签
    	me.component1 = new mx.editors.TextEditor({width:"18%"});	//合同文本框
    	me.container4.addControl(me.component1); //添加文本框
    	me.label2 = new mx.controls.Label({text:"纸质合同编号：",width:"13%",textAlign:"right",right:4});	//纸质合同编号标签
    	me.container4.addControl(me.label2);	//添加标签
    	me.component2 = new mx.editors.TextEditor({width:"18%"});	//纸质合同编号
    	me.container4.addControl(me.component2); //添加文本框
//    	me.label3 = new mx.controls.Label({text:"系统合同编号",width:"8%",textAlign:"right",right:4});	//系统合同编号标签
//    	me.container2.addControl(me.label3);	//添加标签
//    	me.component3 = new mx.editors.TextEditor({width:"17%"});	//系统合同编号
//    	me.container2.addControl(me.component3); //添加文本框
    	
//    	me.label9 = new mx.controls.Label({text:"业务场景",width:"8%",textAlign:"right",right:4});	//业务场景标签
//    	me.container5.addControl(me.label9);	//添加标签
//    	me.component9 = new utils.dropDownEditor.MarketTree.MarketDropDownEditor(true,null,false); //业务场景下拉框
//    	me.component9.setWidth("17%");
//    	me.container5.addControl(me.component9);	//添加下拉框
    	
    	
    	me.buttontest = new mx.controls.Button({text:"高级查询",left:10});	//查询合同按钮
//    	me.container1.addControl(me.buttontest);	//添加按钮
    	me.buttontest.on("click",function(){
    		//me.mSplit.rows = "230,auto";
    		if(me.buttontest.text == "高级查询"){
    			var height = parseInt(me.panel1.height)+30*3;
        		me.panel1.setHeight(height);
        		me.container2.setDisplay("block");
        		me.container3.setDisplay("block");
        		me.container4.setDisplay("block");
//        		me.container5.setDisplay("block");
        		me.buttontest.setText("收起");
    		}else if(me.buttontest.text == "收起"){
    			var height = parseInt(me.panel1.height)-30*3;
        		me.panel1.setHeight(height);
        		me.container2.setDisplay("none");
        		me.container3.setDisplay("none");
        		me.container4.setDisplay("none");
//        		me.container5.setDisplay("none");
        		me.buttontest.setText("高级查询");
    		}
    		
    	});
    	//me.container6添加业务操作第一行的按钮
    	me.button1 = new mx.controls.Button({text:"查询合同",left:10,onclick:searchCon});	//查询合同按钮
    	me.container6.addControl(me.button1);	//添加按钮
    	me.button2 = new mx.controls.Button({text:"新增合同",left:40});	//新增合同按钮
    	me.button2.on("click", function()
    			{
		    		var _openView = new htadd.views.MainViewController().getView({mv:me});
		    		me._openWin = utils.commonFun.WinMananger.create({
		    			reusable: true,//是否复用
		    			width:"400",
		    			height:"250",
		    			title:"合同新增第一步"
		    		});
					me._openWin.setView(_openView);
		    		me._openWin.showDialog(function(){
		    			search();
		    		});
    				
    			});
    	me.container6.addControl(me.button2);	//添加按钮
    	me.button3 = new mx.controls.Button({text:"合同变更",left:70});	//合同变更按钮
    	me.button3.on("click", function()
			{
	    		if (_dataGrid.getCheckedIDs().length == 0) {
	    			mx.indicate("info", "请勾选一条合同。");
	                return;
	            }else if(_dataGrid.getCheckedIDs().length > 1) {
	            	mx.indicate("info", "请勾选一条合同。");
	                return;
	            }
	            else{
	            	var contractid = _dataGrid.getCheckedIDs()[0];
	            	var coType = _dataGrid.getCheckedItems()[0].values.contracttype;
	            	var coName = _dataGrid.getCheckedItems()[0].values.col2;//合同名称
	            	var coTName = _dataGrid.getCheckedItems()[0].values.col3;//合同类型名称
	            	var coFlag = _dataGrid.getCheckedItems()[0].values.col10//合同状态
	            	if(coFlag=="已终止"){
	            		mx.indicate("info","合同已终止，不能变更！");
	            		return;
	            	}
	            	var restClient = new mx.rpc.RESTClient();
	            	var htxlList = restClient.getSync(coContractbaseinfoV2Act.mappath("~/rest/coContractbaseinfoV2Act/getHtxl"),{contractid:contractid});
//	            	var htxl = null;
	            	var htxlName = null;
	            	if(htxlList!=null && htxlList.length>0){
	            		for(var i=0;i<htxlList.length;i++){
//	            			htxl = htxlList[i][0];
	            			htxlName = htxlList[i][1];
	            		}
	            	}
	            	//readType为1时，弹出页面为只读，0时非只读，按自己需要
	    			var _openView = new htadd.views.zjxzViewController().getView({objID:contractid,coName:coName,coTName:coTName,readType:"0",coType:coType,htxlName:htxlName,mv:me,btn:"edit"});
	    			me._openWin = utils.commonFun.WinMananger.create({
	    				reusable: true,//是否复用
	    				width:"90%",
	    				height:"90%",
	    				title:"合同管理"
	    			});
    				me._openWin.setView(_openView);
		    		me._openWin.showDialog(function(){
		    			var pageIndex = _dataGrid.pageIndex;
		    			_dataGrid.moveToPage(pageIndex);
		    		});
	            }
				
			});
    	me.container6.addControl(me.button3);	//添加按钮
//    	me.button4 = new mx.controls.Button({text:"分解电量到机组",left:140});	//分解电量到机组按钮
//    	me.button4.on("click", function()
//    			{
//		    		if(confirm("如果分解合同，将会覆盖已存在的数据，确定继续分解？")){
//		    			var result = restClient.getSync(coContractbaseinfoV2Act.mappath("~/rest/cocontractbackupinfo/fj"));
//		    			if(result.resultValue.items[0] == "true" || result.resultValue.items[0] == true){
//		    				alert("分解成功");
//		    			}else{
//		    				alert(result.resultValue.items[0]);
//		    				alert("分解失败");
//		    			}
//		    		}else{
//		    			return;
//		    		}
//    			});
//    	me.container6.addControl(me.button4);	//添加按钮
    	me.button5 = new mx.controls.Button({text:"合同终止",left:100});	//合同终止按钮
    	me.button5.on("click", function()
    			{
    				var v_dataGrid = _dataGrid;
		    		
//		    		if (v_dataGrid.getCheckedIDs().length == 0) {
//		    			mx.indicate("info", "请勾选一条记录。");
//		                return;
//		            }else if(v_dataGrid.getCheckedIDs().length > 1) {
//		            	mx.indicate("info", "只能勾选一条记录。");
//		                return;
//		            }else if(confirm("您确认终止该条数据吗？")) {
//		            	var objId = v_dataGrid.getCheckedIDs()[0];
//		            	var result = restClient.getSync(coContractbaseinfoV2Act.mappath("~/rest/cocontractbackupinfo/contractEnd?checkedId="+objId));
//		            	//alert(result.resultValue.items[0]);
//		            	if(result.resultValue.items.length >  0){
//		            		if(result.resultValue.items[0] == "true" || result.resultValue.items[0] == true){
//			            		alert("合同终止成功");
//			            	}else{
//			            		alert("合同终止失败");
//			            	}
//		            	}
//		    		}
    				if (v_dataGrid.getCheckedIDs().length == 0)
			        {
				     	mx.indicate("info", "请至少勾选一个待终止合同。");
			            return;
			        }
    				if(confirm("您确认终止所选合同吗？")) {
		            	var objId = v_dataGrid.getCheckedIDs();
		            	
		            	
		            	var result = restClient.getSync(coContractbaseinfoV2Act.mappath("~/rest/cocontractbackupinfo/contractEnd?checkedId="+objId));
		            	//alert(result.resultValue.items[0]);
		            	if(result.resultValue.items.length >  0){
		            		if(result.resultValue.items[0] == "true" || result.resultValue.items[0] == true){
			            		mx.indicate("info", "合同终止成功！");
//			            		alert("合同终止成功");
			            	}else{
			            		mx.indicate("info", "合同终止失败！");
//			            		alert("合同终止失败");
			            	}
		            	}
		            	search();
		    		}
		    		
    			});
    	me.container6.addControl(me.button5);	//添加按钮
    	
    	//me.container7添加业务操作第二行的按钮
    	me.button6 = new mx.controls.Button({text:"版本管理",left:130});	//版本管理按钮
    	
    	me.button6.on("click", function()
    			{
		    		var v_dataGrid = _dataGrid;
		    		
		    		if (v_dataGrid.getCheckedIDs().length == 0) {
		    			mx.indicate("info", "请勾选一条记录。");
		                return;
		            }else if(v_dataGrid.getCheckedIDs().length > 1) {
		            	mx.indicate("info", "只能勾选一条记录。");
		                return;
		            }else{
			            var objId = v_dataGrid.getCheckedIDs()[0];
		    			var _openView = new contractVersionManage.views.ContractVersionMainViewController().getView({contractid:objId}).controller.compareContractaVersion();
	//		    		me._openWin = new mx.windows.WindowManager().create({
	//		    			reusable: true,//是否复用
	//		    			width:"100%",
	//		    			height:"100%",
	//		    			title:"合同版本管理"
	//		    		});
	//					me._openWin.setView(_openView);
	//		    		me._openWin.showDialog(function(){
			    			search();
	//		    		});
		            }
				
    			});
    	me.container6.addControl(me.button6);	//添加按钮
    	me.button8 = new mx.controls.Button({text:"模板管理",left:160});	//模板管理按钮
    	//marketMember/configmodule/index.jsp?tradeseqId=69486820-5BAB-D177-18FD
    	me.button8.on("click", function()
			{
	    		if (_dataGrid.getCheckedIDs().length == 0) {
	    			mx.indicate("info", "请勾选一条合同。");
	                return;
	            }else if(_dataGrid.getCheckedIDs().length > 1) {
	            	mx.indicate("info", "请勾选一条合同。");
	                return;
	            }
	            else{
	            	var coType = _dataGrid.getCheckedItems()[0].values.contracttype;
	    			var url = "../../marketMember/configmodule/index.jsp?tradeseqId=69486820-5BAB-D177-18FD&coType="+coType;
		    		if(parent != undefined && parent.topDiv != undefined){
						parent.addTab("合同模板管理",url);
					}
					else{
						window.location.href = url;
					}
	            }
//    			window.location.href = url;
			});
    	me.container6.addControl(me.button8);	//添加按钮
    	me.button7 = new mx.controls.Button({text:"合同导出",left:190,onclick:exportTable});	//合同导出按钮
    	me.container6.addControl(me.button7);	//添加按钮
    	me.button8 = new mx.controls.Button({text:"合同文件上传",left:10,onclick:fileUpload});	
    	me.container7.addControl(me.button8);	//添加按钮
    	me.button88 = new mx.controls.Button({text:"合同电量按月批量分解",left:40});	
    	me.button88.on("click", function()
    			{
    			var v_dataGrid = _dataGrid;
	    		if (v_dataGrid.getCheckedIDs().length == 0)
		        {
			     	mx.indicate("info", "请选择需电量分解合同。");
		            return;
		        }
	    		if (v_dataGrid.getCheckedIDs().length > 100)
		        {
			     	mx.indicate("info", "最多批量分解100条合同。");
		            return;
		        }
            	var objId = v_dataGrid.getCheckedIDs();
            	var params = {pageId: objId};//参数 undefined
            	
		
	    		var _openView = contResoByMonth.views.mulContResoMonViewController().getView({contractids:objId,params:params});
	    		me._openWin = utils.commonFun.WinMananger.create({
	    			reusable: true,//是否复用
	    			width:"800",
	    			height:"430",
	    			title:"合同电量按月批量分解"
	    		});
				me._openWin.setView(_openView);
	    		me._openWin.showDialog(function(){
	    			search();
	    		});
    			});
    	me.container7.addControl(me.button88);	//添加按钮
    	me.button10 = new mx.controls.Button({text:"批量复制",left:70});
    	me.container7.addControl(me.button10);//备用按钮勿删
    	
    	me.button10.on("click", function()
    			{
    			var v_dataGrid = _dataGrid;
	    		if (v_dataGrid.getCheckedIDs().length == 0)
		        {
			     	mx.indicate("info", "请选择需要复制的合同。");
		            return;
		        }
//	    		if (v_dataGrid.getCheckedIDs().length > 100)
//		        {
//			     	mx.indicate("info", "最多批量分解100条合同。");
//		            return;
//		        }
            	var objId = v_dataGrid.getCheckedIDs();
            	var v_items =v_dataGrid.getCheckedItems();
            	var params = {pageId: objId};//参数 undefined
            	
		
            	me._openWin = utils.commonFun.WinMananger.create({
            		reusable: true,//是否复用
            		width:"800",
            		height:"430",
            		title:"合同批量复制"
            	});
	    		var _openView = coContractbaseinfoV2Act.views.copybullMainViewController({win:me._openWin,contractids:objId,params:params,v_items:v_items}).getView();
				me._openWin.setView(_openView);
	    		me._openWin.showDialog(function(){
//	    			search();
	    		});
    			});
    	
    	me.button9 = new mx.controls.Button({text:"发送经法",left:10});
//    	me.container7.addControl(me.button9);//备用按钮勿删
    	me.button9 = new mx.controls.Button({text:"经法合同文本查询",left:40,onclick:searchAffix});
//    	me.container7.addControl(me.button9);//备用按钮勿删
    	me.button9 = new mx.controls.Button({text:"经法合同状态查询",left:100,onclick:searchState});
    	me.container7.addControl(me.button9);//备用按钮勿删
//    	me.button9 = new mx.controls.Button({text:"查看变更记录",left:140});	//查看变更记录按钮
//    	me.container7.addControl(me.button9);	//添加按钮
//    	me.button10 = new mx.controls.Button({text:"共享",left:180});	//共享按钮
//    	me.container7.addControl(me.button10);	//添加按钮
    	
    	//添加统计信息
    	
    	
    	//me.mDSplit.addControl(me.dockPanel_left,0);
    	
    	//测试
//    	me.button10.on("click",me.controller._btnNew_onclick);
    	
    }
    
    
    
    /**
     * 合同文件上传
     */
    function fileUpload(){
    	
    	if (_dataGrid.getCheckedIDs().length == 0)
        {
			mx.indicate("warn", "请选择合同！");
			return;
        }
		//多选框勾选记录，判断是否选择多条
		if(_dataGrid.getCheckedIDs().length>1)
		{
			mx.indicate("warn", "只能选择一条合同！");
			return;
		}
		
		_detailView = new CoContractFjAct.views.DetailViewController().getView();
    	if (!_detailView.initialized)
		{
			_detailView.init();
		}
    	
    	_detailView.objID = _dataGrid.getCheckedIDs()[0];
        
        var _detailWin = utils.commonFun.WinMananger.create({
			reusable: true,//是否复用
			width: 400,
			height: 300,
			title:"上传页面"
		});
        _detailView.load();
        _detailWin.setView(_detailView);
        _detailWin.showDialog(function(){
			var pageIndex = _dataGrid.pageIndex;
			_dataGrid.moveToPage(pageIndex);
		});
    	
    }
    
    
    /**
     * 查询合同方法
     */
    function search(){
    	contractName = me.component1.getValue() == null ? "" : me.component1.getValue();	//合同名称
    	papercontractcode = me.component2.getValue() == null ? "" : me.component2.getValue();	//纸质合同编号
//    	syscontractcode = me.component3.getValue() == null ? "" : me.component3.getValue();	//系统合同编号
    	contractcyc = me.component4.getValue() == null ? "" : me.component4.getValue();		//合同周期
    	//prepcontractflag = me.component5.getValue() == null ? "" : me.component5.getValue();	//合同状态
    	flowflag= me.component5.getValue() == null ? "" : me.component5.getValue();	//合同状态
    	contracttype = me.component6.getValue() == null ? "" : me.component6.getValue();	//合同类型
    	purchaser = me.component7.getValue() == null ? "" : me.component7.getValue();	//购电方
    	seller = me.component8.getValue() == null ? "" : me.component8.getValue();		//售电方
//    	marketid = me.component9.getValue() == null ? "" : me.component9.getValue();	//业务场景
    	searchDateType = me.component10.getValue() == null ? "" : me.component10.getValue();	//时间类型
    	startDate = me.component11.getValue() == null ? "" : me.component11.getValue();	//开始时间
    	endDate = me.component12.getValue() == null ? "" : me.component12.getValue();	//结束时间
    	sequenceid = me.component13.getValue() == null ? "" : me.component13.getValue();	//合同序列
    	powertype = me.component14.getValue() == null ? "" : me.component14.getValue();	//售电方发电类型
    	if(startDate!="" && endDate!=""){
    		if(startDate>endDate){
        		mx.indicate("info", "截止时间必须大于开始时间！");
        		return;
        	}
    	}
    	//查询条件不能输入非法字符
    	if(!validate(contractName,"合同名称")){return};
    	if(!validate(papercontractcode,"纸质合同编号")){return;}
    	if(!validate(syscontractcode,"系统合同编号")){return;}
    	loadGrid1();	//加载第一个表格数据
    	loadGrid2();	//加载第二个表格数据

    }
    //校验
    function validate(value,name){
  	  if(/\"+|\'+|\\+|\/+|\?/.test(value)){
		var message = name+"不能输入包含非法字符 { \" \' \\ / ? }的数据.";
		mx.indicate("warn", message);
		return false;
	  }
	  return true;
    }
    
    /**
     * 查询合同方法
     */
    function searchflow(){
    	contractName = me.component1.getValue() == null ? "" : me.component1.getValue();	//合同名称
    	papercontractcode = me.component2.getValue() == null ? "" : me.component2.getValue();	//纸质合同编号
//    	syscontractcode = me.component3.getValue() == null ? "" : me.component3.getValue();	//系统合同编号
    	contractcyc = me.component4.getValue() == null ? "" : me.component4.getValue();		//合同周期
    	//prepcontractflag = me.component5.getValue() == null ? "" : me.component5.getValue();	//合同状态
    	contracttype = me.component6.getValue() == null ? "" : me.component6.getValue();	//合同类型
    	purchaser = me.component7.getValue() == null ? "" : me.component7.getValue();	//购电方
    	seller = me.component8.getValue() == null ? "" : me.component8.getValue();		//售电方
//    	marketid = me.component9.getValue() == null ? "" : me.component9.getValue();	//业务场景
    	searchDateType = me.component10.getValue() == null ? "" : me.component10.getValue();	//时间类型
    	startDate = me.component11.getValue() == null ? "" : me.component11.getValue();	//开始时间
    	endDate = me.component12.getValue() == null ? "" : me.component12.getValue();	//结束时间
    	sequenceid = me.component13.getValue() == null ? "" : me.component13.getValue();	//合同序列
    	powertype = me.component14.getValue() == null ? "" : me.component14.getValue();	//售电方发电类型
    	if(startDate!="" && endDate!=""){
    		if(startDate>endDate){
    			mx.indicate("info", "截止时间必须大于开始时间！");
        		return;
        	}
    	}
    	//查询条件不能输入非法字符
    	if(!validate(contractName,"合同名称")){return};
    	if(!validate(papercontractcode,"纸质合同编号")){return;}
    	if(!validate(syscontractcode,"系统合同编号")){return;}
    	loadGrid1();	//加载第一个表格数据
    	loadGrid2();	//加载第二个表格数据

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
    	_dataGrid.load();
    }
    /**
     * 加载第二个表格数据
     */
    function loadGrid2(){
    	//给dataGrid添加过滤条件
//    	_dataGrid2.setFilter({contractName:contractName,papercontractcode:papercontractcode,syscontractcode:syscontractcode,contractcyc:contractcyc,
//    		prepcontractflag:prepcontractflag,contracttype:contracttype,purchaser:purchaser,seller:seller,marketid:marketid,searchDateType:searchDateType,
//    		startDate:startDate,endDate:endDate,sequenceid:sequenceid,marketId:marketId,flowflag:flowflag});
//    	//加载表格数据
//    	_dataGrid2.load();
    	
    	//var params = {"columns":"contractName,papercontractcode,syscontractcode,contractcyc,prepcontractflag,contracttype,purchaser,seller,marketid,searchDateType,startDate,endDate,sequenceid,marketId,flowflag","filter:contractName=contractName"};
//    			"filter":"contractName="+contractName+"&papercontractcode="+papercontractcode+"&syscontractcode="+syscontractcode+"&contractcyc="+contractcyc+"&prepcontractflag"=prepcontractflag+"&contracttype"=contracttype+"&purchaser="+purchaser+"&seller="+seller+"&marketid="+marketid+"&searchDateType="+searchDateType+"&startDate="+startDate+"&endDate="+endDate+"&sequenceid="+sequenceid+"&marketId="+marketId+"&flowflag="+flowflag};
    	
    	var item = restClient.getSync(coContractbaseinfoV2Act.
    			mappath("~/rest/coContractbaseinfoV2Act/getStatics2"),
    			{contractName:contractName,papercontractcode:papercontractcode,syscontractcode:syscontractcode,contractcyc:contractcyc,
    		prepcontractflag:prepcontractflag,contracttype:contracttype,purchaser:purchaser,seller:seller,marketid:marketid,searchDateType:searchDateType,
    		startDate:startDate,endDate:endDate,sequenceid:sequenceid,marketId:marketId,flowflag:flowflag,powertype:powertype});	
    	me.tlabel12.setText(item.col0);
    	me.tlabel22.setText(item.col1);
    	me.tlabel32.setText(item.col5);
    	me.tlabel42.setText(item.col6);
    	me.tlabel52.setText(item.col7);
    	me.tlabel62.setText(item.col8);
    	me.tlabel72.setText(item.col9);
    	
    	//var items = restClient.getSync(coContractbaseinfoV2Act.mappath("~/rest/coContractbaseinfoV2Act/getStatics"),{"params" : JSON.stringify(params)});
    	//marketId = items[0];
    }
  
    /**
     * 合同导出功能
     */
    function exportTable(){
    	var filter = {contractName:contractName,papercontractcode:papercontractcode,syscontractcode:syscontractcode,contractcyc:contractcyc,
        		prepcontractflag:prepcontractflag,contracttype:contracttype,purchaser:purchaser,seller:seller,marketid:marketid,searchDateType:searchDateType,
        		startDate:startDate,endDate:endDate,sequenceid:sequenceid,marketId:marketId,flowflag:flowflag,powertype:powertype};
    	var util = new mx.utils.ExcelUtil();
    	var title = "合同id,纸质合同编号,合同名称,合同类型,购电方,售电方,开始时间,截止时间,总电量";
    	title += ",电价,合同流转状态,合同状态,维护人,维护时间,维护单位";
    	util.setParams({tableName:"",columnCaptions:title,fileName:"合同查询导出文件.xls",filter:JSON.stringify(filter)});
    	util.setBaseUrl(coContractbaseinfoV2Act.mappath("~/rest/coContractbaseinfoV2Act/excel")) ;
    	util.exportExcel();
    }
    
    /**
     * 经法合同文本查询
     */
    function searchAffix(){
    	var _openView = new jfcontractaffix.views.JfContractAffixMainViewController({mv:me}).getView();
    	me._openWin = utils.commonFun.WinMananger.create({
			reusable: true,//是否复用
			width:"90%",
			height:"90%",
			title:"经法合同文本查询"
		});
		me._openWin.setView(_openView);
		me._openWin.showDialog();
    }
    
    /**
     * 经法合同状态查询
     */
    function searchState(){
    	var _openView = new jfcontractstate.views.JfContractStateMainViewController({mv:me}).getView();
    	me._openWin = utils.commonFun.WinMananger.create({
			reusable: true,//是否复用
			width:"90%",
			height:"90%",
			title:"经法合同状态查询"
		});
		me._openWin.setView(_openView);
		me._openWin.showDialog();
    }
    
    /**
     * 初始化第一个表格
     */
    function _initDataGrid2()
    {
    	
    	
    	
        var restUrl = "~/rest/coContractbaseinfoV2Act/getStatics";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : coContractbaseinfoV2Act.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "col0"
        });
        
        /* 初始化 DataGrid */
        _dataGrid2 = new mx.datacontrols.ComplexGrid({   
			// 构造查询属性。
			alias: "coContractbaseinfoV2ActMainViewDataGrid2",
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
        me.addControl(_dataGrid2);//添加表格
    }
    /**
     * 初始化下方表格
     */
    function _initDataGrid()
    {
        var restUrl = "~/rest/coContractbaseinfoV2Act/";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : coContractbaseinfoV2Act.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "col0"
        });
        
        /* 初始化 DataGrid */
        _dataGrid = new mx.datacontrols.ComplexGrid({   
			// 构造查询属性。
			alias: "coContractbaseinfoV2ActMainViewDataGrid",
			columns:[
			{	name: "col0", caption: "主键" , editorType: "TextEditor",align:"center",dataAlign:"center"	},         
			{	name: "col1", caption: "纸质合同编号" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
			{	name: "col16", caption: "售电方别名" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
			{	name: "col2", caption: "合同名称" , editorType: "TextEditor",align:"center",dataAlign:"center",renderCell:detailContact	},  
			{	name: "col3", caption: "合同类型" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
			{	name: "col4", caption: "购电方" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
			{	name: "col5", caption: "售电方" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
//			{	name: "col6", caption: "总电量" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
//			{	name: "col7", caption: "电价" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
			{	name: "energy", caption: "总电量" , editorType: "TextEditor",align:"center",dataAlign:"center",formatString:"0.0000"	},
			{	name: "contractprice", caption: "电价" , editorType: "TextEditor",align:"center",dataAlign:"center",formatString:"0.0000"	},
			{	name: "col8", caption: "开始时间" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
			{	name: "col9", caption: "截止时间" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
			{	name: "col10", caption: "合同状态" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
			//{	name: "col11", caption: "合同状态" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
//			{	name: "col12", caption: "维护人" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
//			{	name: "col13", caption: "维护时间" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
//			{	name: "col14", caption: "维护单位" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
			{	name: "contracttype", caption: "contracttype" , editorType: "TextEditor"},
			{	name: "col15", caption: "附件下载" , editorType: "TextEditor",align:"center",dataAlign:"center",renderCell:downlist	}
			
            ],
            // 构造列排序条件，如果有多列，则以逗号分隔。例sorter: "school ASC, class DESC"
            displayCheckBox: true,
	        displayPrimaryKey:false,//列表是否显示主键
            allowEditing: false, //列表默认不可编辑
            allowSorting:true,//列允许排序
	        pageSize : 20,
            entityContainer: gridEntityContainer,
            displayRowNumber : true,
            displayToolBar : false,
            height:document.body.offsetHeight - 180 ,
            rowNumberColWidth : 40,
            onload:_init
        });
        me.addControl(_dataGrid);//添加表格
    }
    
    
    
    function _init(){
    	_dataGrid.columns["contracttype"].setVisible(false);
    	//合同类型为发电权合同时 改表头名称
    	var restClient = new mx.rpc.RESTClient();
    	var result = restClient.getSync(coContractbaseinfoV2Act.mappath("~/rest/coContractbaseinfoV2Act/isfdqht"),{"params":JSON.stringify({"contracttype":contracttype})});
    	if(result==true){
    		_dataGrid.columns.col4.setCaption("被替代方");
    		_dataGrid.columns.col5.setCaption("替代方");
    	}
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
    	var items = restClient.getSync(coContractbaseinfoV2Act.
    			mappath("~/rest/coContractbaseinfoV2Act/getComponentItems"),
    			{type:type,marketId:marketId,value:1}).resultValue;	//获取购电方数据
    	me.component7.setItems(items);
    }	
    /**
     * 给售电方下拉框赋值
     */
    function setComponent8Items(){
    	var type =  me.component6.getValue() == null ? "" : me.component6.getValue();	//获取合同类型值
    	var items = restClient.getSync(coContractbaseinfoV2Act.
    			mappath("~/rest/coContractbaseinfoV2Act/getComponentItems"),
    			{type:type,marketId:marketId,value:2}).resultValue;	//获取售电方数据
    	me.component8.setItems(items);
    }	
    /**
     * 给合同序列赋值
     */
    function setComponent13Items(changecontracttype){
    	var items = restClient.getSync(coContractbaseinfoV2Act.
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
    	$p_cell.html("<a href:'#' >"+p_item.values[id]+"</a>");	//添加超链接
    	
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
    	var htxlList = restClient.getSync(coContractbaseinfoV2Act.mappath("~/rest/coContractbaseinfoV2Act/getHtxl"),{contractid:objId});
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
      	var _detailView = new htadd.views.zjxzViewController().getView({objID:objId,readType:"1",coType:contracttype,htxlName:htxlName,coTName:coTName});
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