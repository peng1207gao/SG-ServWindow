$ns("contractMonthManage.views");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGridSearchBox");
$import("mx.datacontrols.GroupHeaderGrid");
$import("mx.containers.HSplit");
$import("mx.containers.Panel");
$import("mx.rpc.RESTClient");
$import("mx.utils.ExcelUtil");
$import("mx.containers.DockPage");
$import("mx.containers.DockPanel");

contractMonthManage.views.MainView = function()
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
	var flagChange = false;
	me.coType = ""; 
	
	
	var oldval=null;//记录表格没格修改前的值
	
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
    	_initDetailWindow();
    }
    
    /**
     * 初始化方法(主要获取初始化参数)
     */
    function init(){
    	var items = restClient.getSync(contractMonthManage.mappath("~/rest/coContractbaseinfoV2Act/init")).resultValue.items;
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
    	me.panel1 = new mx.containers.Panel({width:"100%",height:"110",title:"业务查询"});	//业务查询panel容器 当有高级查询按钮时height:"50"
//    	me.mSplit.addControl(me.panel1,0);	//添加panel1容器
    	me.addControl(me.panel1);
    	
    	//添加业务查询区域内包含的容器Contianer
    	me.container1 = new mx.containers.Container({width:"100%",height:"30",padding:"2"});	//放置标签、下拉框、文本框等的容器
    	me.panel1.addControl(me.container1);	//添加container1容器
    	me.container2 = new mx.containers.Container({width:"100%",height:"30",padding:"2"});	//放置标签、下拉框、文本框等的容器
    	me.panel1.addControl(me.container2);	//添加container2容器
    	me.container3 = new mx.containers.Container({width:"100%",height:"30",padding:"2"});	//放置标签、下拉框、文本框等的容器
    	me.panel1.addControl(me.container3);	//添加container3容器
    	
    	//业务操作区域
    	me.panel2 = new mx.containers.Panel({width:"100%",height:"50",title:"业务操作"}); //业务操作panel容器,当需要显示经法功能的三个按钮时，heght:80
//    	me.mSplit.addControl(me.panel2,0);	//添加panel2容器
    	me.addControl(me.panel2);
    	
    	
    	//添加业务操作区域内包含的容器Contianer
    	me.container6 = new mx.containers.Container({width:"100%",height:"30",padding:"2"});	//放置按钮等的容器
    	me.panel2.addControl(me.container6);	//添加container4容器
    	me.container7 = new mx.containers.Container({width:"100%",height:"30",padding:"2"});	//放置按钮等的容器
    	me.container7.setDisplay("none");
    	me.panel2.addControl(me.container7);	//添加container5容器
    }
    
    me._refresh = function(){
    	search();
    }
    /**
     * 初始化控件(添加页面的标签、下拉框、文本框、按钮、表格等控件)
     */
    function initComponent(){
    	
    	//me.container1添加业务查询第一行的控件
    	me.label10 = new mx.controls.Label({text:"时间类型：",width:"8%",textAlign:"right",right:4});	//时间类型标签
    	me.container1.addControl(me.label10);	//添加标签
    	me.component10 = new mx.editors.DropDownEditor({displayMember:"name",valueMember:"value",width:"17%",items:
    					[{name:"合同开始时间",value:0},{name:"合同录入时间",value:1},{name:"合同签订时间",value:2}],
    					value:0});	//时间类型下拉框
    	me.container1.addControl(me.component10); //添加下拉框
    	me.label11 = new mx.controls.Label({text:"开始时间：",width:"8%",textAlign:"right",right:4});	//开始时间标签
    	me.container1.addControl(me.label11);	//添加标签
    	me.component11 = new mx.editors.DateTimeEditor({width:"17%"}); //开始时间时间框
    	me.container1.addControl(me.component11);	//添加时间框
    	me.label12 = new mx.controls.Label({text:"截止时间：",width:"8%",textAlign:"right",right:4});	//结束时间标签
    	me.container1.addControl(me.label12);	//添加标签
    	me.component12 = new mx.editors.DateTimeEditor({width:"17%"}); //结束时间时间框
    	me.container1.addControl(me.component12);	//添加时间框
    	me.label5 = new mx.controls.Label({text:"合同状态：",width:"8%",textAlign:"right",right:4});	//合同状态标签
    	me.container1.addControl(me.label5);	//添加标签
    	me.component5 = new mx.editors.DropDownEditor({displayMember:"name",valueMember:"value",width:"200",
    		url : contractMonthManage.mappath("~/../ETradePublicUtils/rest/baGencodeTypeList/getList?type=67")	//获取合同状态下拉框数据
    		}); //合同状态下拉框
    	me.component5.setWidth("17%");
    	me.container1.addControl(me.component5);	//添加下拉框
    	
    	//me.container2添加业务查询第二行的控件
    	//解决因为内容过长下拉框显示不全的问题
    	var fun = new utils.commonFun.DropDownEditorFun();
    	me.label6 = new mx.controls.Label({text:"合同类型：",width:"8%",textAlign:"right",right:4});	//合同类型标签
    	me.container2.addControl(me.label6);	//添加标签
    	me.component6 = utils.dropDownEditor.ContractTypeTree.ContractTypeDropDownEditor(false,false); //合同类型下拉框
    	me.component6.setWidth("17%");
    	me.container2.addControl(me.component6);	//添加下拉框
    	me.component6.on("changed",changeDrops);//给合同类型添加onchanged触发事件
    	me.label13 = new mx.controls.Label({text:"合同序列：",width:"8%",textAlign:"right",right:4});	//合同序列标签
    	me.container2.addControl(me.label13);	//添加标签
    	me.component13 = new mx.editors.DropDownEditor({displayMember:"name",valueMember:"value",autoMatchInput:true,
    		allowEditing:true,width:"17%"}); //合同序列下拉框
		fun.resizeListEditor(me.component13);
    	me.container2.addControl(me.component13);	//添加下拉框
    	setComponent13Items("");	//给合同序列赋值
    	me.label4 = new mx.controls.Label({text:"合同周期：",width:"8%",textAlign:"right",right:4});	//合同周期标签
    	me.container2.addControl(me.label4);	//添加标签
    	me.component4 = new mx.editors.DropDownEditor({displayMember:"name",valueMember:"value",width:"17%",
    		url:contractMonthManage.mappath("~/../ETradePublicUtils/rest/baGencodeTypeList/getList?type=23")	//获取合同周期下拉框数据
    		}); //合同周期下拉框
    	me.container2.addControl(me.component4);	//添加下拉框
    	//me.container3添加业务查询第三行的控件
    	me.label14 = new mx.controls.Label({text:"售电方发电类型：",width:"8%",textAlign:"right",right:4});	//售电方标签
    	me.container2.addControl(me.label14);	//添加标签
    	me.component14 = new mx.editors.DropDownEditor({displayMember:"name",valueMember:"value",width:"200",
    		url : contractMonthManage.mappath("~/../ETradePublicUtils/rest/baGencodeTypeList/getList?type=13")	//获取合同状态下拉框数据
    		}); //合同状态下拉框
    	me.component14.setWidth("17%");
    	me.container2.addControl(me.component14);	//添加下拉框
    	
    	me.label8 = new mx.controls.Label({text:"售电方：",width:"8%",textAlign:"right",right:4});	//售电方标签
    	me.container3.addControl(me.label8);	//添加标签
    	me.component8 = utils.dropDownEditor.CommonDropEditor.gsAutoComPleteEditor("","2"); //售电方下拉框
    	utils.dropDownEditor.CommonDropEditor.autoInit(me.component8,'2');
    	me.component8.setWidth("17%");
    	//解决因为内容过长下拉框显示不全的问题
    	fun.resizeAutocomplete(me.component8);
    	me.container3.addControl(me.component8);	//添加下拉框
    	me.label7 = new mx.controls.Label({text:"购电方：",width:"8%",textAlign:"right",right:4});	//购电方标签
    	me.container3.addControl(me.label7);	//添加标签
    	me.component7 = utils.dropDownEditor.CommonDropEditor.gsAutoComPleteEditor("","1");	//购电方下拉框
    	utils.dropDownEditor.CommonDropEditor.autoInit(me.component7,'1');
    	me.component7.setWidth("17%");
    	//解决因为内容过长下拉框显示不全的问题
    	fun.resizeAutocomplete(me.component7);
    	me.container3.addControl(me.component7); //添加文本框
    	//me.container4添加业务查询第四行的控件
    	me.label1 = new mx.controls.Label({text:"合同名称：",width:"8%",textAlign:"right",right:4});	//合同名称标签
    	me.container3.addControl(me.label1);	//添加标签
    	me.component1 = new mx.editors.TextEditor({width:"17%"});	//合同文本框
    	me.container3.addControl(me.component1); //添加文本框
    	me.label2 = new mx.controls.Label({text:"纸质合同编号：",width:"8%",textAlign:"right",right:4});	//纸质合同编号标签
    	me.container3.addControl(me.label2);	//添加标签
    	me.component2 = new mx.editors.TextEditor({width:"17%"});	//纸质合同编号
    	me.container3.addControl(me.component2); //添加文本框
    	
    	
    	
    	//me.container6添加业务操作第一行的按钮
    	me.button1 = new mx.controls.Button({text:"查询",left:20,onclick:searchCon});	//查询合同按钮
    	me.container6.addControl(me.button1);	//添加按钮
    	me.button3 = new mx.controls.Button({text:"保存",left:40});	
    	me.container6.addControl(me.button3);	//添加按钮
    	me.button4 = new mx.controls.Button({text:"分解",left:60});	
    	me.container6.addControl(me.button4);	//添加按钮
    	me.button5 = new mx.controls.Button({text:"指定经济机组",left:80});	
    	me.container6.addControl(me.button5);	//添加按钮
    	me.button6 = new mx.controls.Button({text:"算法说明",left:100});	
    	me.container6.addControl(me.button6);	//添加按钮【算法说明】
    	
    	me.button3.on("click", saveContract);
    	me.button4.on("click", energyFJMonth);
    	me.button5.on("click", me.chooseUnit);
    	me.button5.on("click", me.controller.getEconUnitView);
    	me.button6.on("click", me.functionExplain);
    	
    	
    }
    
    
    function energyFJMonth(){
    	
    	if(flagChange){
    		mx.indicate("info", "你更改了电量数据，请先保存在分解。");
            return;
    	}
    	
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
    	var flag = restClient.getSync(contractMonthManage.mappath("~/rest/cocontractmonthqty/getMonthDataFlag?contractids="+objId));	//获取分解方式
    	if(!flag){
    		var month = "";
        	var v_dataGrid = _dataGrid;
        	var objId = v_dataGrid.getCheckedIDs();
        	var flag = restClient.getSync(contractMonthManage.mappath("~/rest/cocontractmonthqty/energyManageFJ?contractid="+objId+"&month="+month));	//获取分解方式
        	if(flag.successful && flag.resultValue){
        		var pageIndex = _dataGrid.pageIndex;
    			_dataGrid.moveToPage(pageIndex);
        		mx.indicate("info", "分解成功！");
        	}else{
        		var pageIndex = _dataGrid.pageIndex;
    			_dataGrid.moveToPage(pageIndex);
        		mx.indicate("info", "分解失败！"+flag.resultHint);
        	}
    	}else{
    		var containers = new mx.containers.Container({width:"100%",height:"100%"});	//放置按钮等的容器
        	var container1 = new mx.containers.Container({width:"100%",height:"15"});	//放置按钮等的容器
        	var containerMonth = new mx.containers.Container({width:"100%",height:"30"});	//放置按钮等的容器
        	var container2 = new mx.containers.Container({width:"100%",height:"10"});	//放置按钮等的容器
        	var containerButton = new mx.containers.Container({width:"100%",height:"30"});	//放置按钮等的容器
        	var monthbutton1 = new mx.controls.Button({text:"确定",left:140});	
        	var monthbutton2 = new mx.controls.Button({text:"取消",left:160});
        	containerButton.addControl(monthbutton1);	//添加标签
        	containerButton.addControl(monthbutton2);	//添加标签
        	containers.addControl(container1);	//添加标签
        	containers.addControl(containerMonth);	//添加标签
        	containers.addControl(container2);	//添加标签
        	containers.addControl(containerButton);	//添加标签
        	monthbutton1.on("click", energyFJ);
    		monthbutton2.on("click", energyFJClose);
        	var labelMonth = new mx.controls.Label({text:"分解月份：",width:"25%",textAlign:"right"});	//分解月份
        	me.dropDownEditorMonth = new mx.editors.DropDownEditor(
        			{
        			    allowEditing: false,  // 设置是否允许编辑
        			    displayMember: "name",
        			    valueMember: "value",
        			    width:"73%",
        			    hint: "若不选择分解月份，默认全部分解。",
        			    items: [
        			        { name: "1", value: "1" },
        			        { name: "2", value: "2" },
        			        { name: "3", value: "3" },
        			        { name: "4", value: "4" },
        			        { name: "5", value: "5" },
        			        { name: "6", value: "6" },
        			        { name: "7", value: "7" },
        			        { name: "8", value: "8" },
        			        { name: "9", value: "9" },
        			        { name: "10", value: "10" },
        			        { name: "11", value: "11" },
        			        { name: "12", value: "12" }
        			    ]
        			});
        	containerMonth.addControl(labelMonth);	//添加标签
        	containerMonth.addControl(me.dropDownEditorMonth);	//添加标签
    		me._openWin = utils.commonFun.WinMananger.create({
    			reusable: true,//是否复用
    			width:"300",
    			height:"80",
    			title:"指定分解月份"
    		});
    		me._openWin.setView(containers);
    		me._openWin.showDialog();
    	}
    	
    	
    	
    }
    
    function energyFJClose(){
    	me._openWin.close();
    }
    
    function saveContract(){
    	
    	for(var q=0;q<_dataGrid.entityContainer.data.length;q++){
			var contractid = _dataGrid.entityContainer.data[q].col0;
			var tenObj=_dataGrid.entityContainer.getEntityByKey(contractid);
			var startmonth = _dataGrid.entityContainer.data[q].col17;
			var endmonth = _dataGrid.entityContainer.data[q].col18;
			var energy = _dataGrid.entityContainer.data[q].energy;
			var energyType = _dataGrid.entityContainer.data[q].col15;
			if(energyType == "总电量"){
				energyType = "1";
			}
			var values  = "";
			if(energy != null && energy != ""){
				for(var i=1;i<13;i++){
					if(i!=12){
						values+=(eval("tenObj.col"+i)==null?"":eval("tenObj.col"+i))+",";
					}else{
						values+=(eval("tenObj.col"+i)==null?"":eval("tenObj.col"+i));
					}
				}
				var str=("contractid="+contractid+'&energyType='+energyType+'&energys='+values);
				var flag = restClient.getSync(contractMonthManage.mappath("~/rest/cocontractmonthqty/saveManage?"+str));	//获取分解方式
		    	if(flag){
		    		mx.indicate("info", "保存成功！");
		    	}else{
		    		mx.indicate("info", "保存失败！");
		    	}
			}
		}
    	var pageIndex = _dataGrid.pageIndex;
		_dataGrid.moveToPage(pageIndex);
    	flagChange=false;
    }
    
    function energyFJ(){
    	
    	var month = me.dropDownEditorMonth.getValue(0) == null ? "" : me.dropDownEditorMonth.getValue();
    	var v_dataGrid = _dataGrid;
    	var objId = v_dataGrid.getCheckedIDs();
    	var flag = restClient.getSync(contractMonthManage.mappath("~/rest/cocontractmonthqty/energyManageFJ?contractid="+objId+"&month="+month));	//获取分解方式
    	if(flag.successful && flag.resultValue){
    		me._openWin.close();
    		var pageIndex = _dataGrid.pageIndex;
			_dataGrid.moveToPage(pageIndex);
    		mx.indicate("info", "分解成功！");
    	}else{
    		me._openWin.close();
    		var pageIndex = _dataGrid.pageIndex;
			_dataGrid.moveToPage(pageIndex);
    		mx.indicate("info", "分解失败！"+flag.resultHint);
    	}
    }
    
    /**
     * 查询合同方法
     */
    function search(){
    	contractName = me.component1.getValue() == null ? "" : me.component1.getValue();	//合同名称
    	papercontractcode = me.component2.getValue() == null ? "" : me.component2.getValue();	//纸质合同编号
    	contractcyc = me.component4.getValue() == null ? "" : me.component4.getValue();		//合同周期
    	flowflag= me.component5.getValue() == null ? "" : me.component5.getValue();	//合同状态
    	contracttype = me.component6.getValue() == null ? "" : me.component6.getValue();	//合同类型
    	purchaser = me.component7.getValue() == null ? "" : me.component7.getValue();	//购电方
    	seller = me.component8.getValue() == null ? "" : me.component8.getValue();		//售电方
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
    	me.loadGrid1();	//加载第一个表格数据

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
     * 查询合同按钮
     */
    function searchCon(){
    	flowflag = "";	//状态恢复为空
    	search();	//调用查询方法
    }	
    /**
     * 加载第一个表格数据
     */
    me.loadGrid1 = function(){
    	//给dataGrid添加过滤条件
    	_dataGrid.setFilter({contractName:contractName,papercontractcode:papercontractcode,syscontractcode:syscontractcode,contractcyc:contractcyc,
    		prepcontractflag:prepcontractflag,contracttype:contracttype,purchaser:purchaser,seller:seller,marketid:marketid,searchDateType:searchDateType,
    		startDate:startDate,endDate:endDate,sequenceid:sequenceid,marketId:marketId,flowflag:flowflag,powertype:powertype});
    	//加载表格数据
    	_dataGrid.load();
    }
    
    function dataGridEdit(){
		for(var q=0;q<_dataGrid.entityContainer.data.length;q++){
			var contractid = _dataGrid.entityContainer.data[q].col0;
			var startmonth = _dataGrid.entityContainer.data[q].col17;
			var endmonth = _dataGrid.entityContainer.data[q].col18;
			var energy = _dataGrid.entityContainer.data[q].energy;
			if(energy != null && energy != ""){
				for(var n=1;n<13;n++){
					if(n>=Number(startmonth) && n<=Number(endmonth)){
						$("#"+contractid,_dataGrid.$e).find("td:eq("+(n+11)+")").prop("disabled",false);
						$("#"+contractid,_dataGrid.$e).find("td:eq("+(n+11)+")").css("background-color","");
					}else{
						$("#"+contractid,_dataGrid.$e).find("td:eq("+(n+11)+")").prop("disabled",true);
						$("#"+contractid,_dataGrid.$e).find("td:eq("+(n+11)+")").css("background-color","#BEBEBE");
					}
				}
			}else{
				for(var n=1;n<13;n++){
						$("#"+contractid,_dataGrid.$e).find("td:eq("+(n+11)+")").css("background-color","#BEBEBE");
						$("#"+contractid,_dataGrid.$e).find("td:eq("+(n+11)+")").prop("disabled",true);
				}
			}
		}
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
    	util.setBaseUrl(contractMonthManage.mappath("~/rest/coContractbaseinfoV2Act/excel")) ;
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
    
    
	function _resovleResult_cellRender(p_item, $p_cell){
		$("<a href='#' style='text-decoration:none; color: blue;'>"+p_item.values["resolveresult"]+"</a>").appendTo($p_cell).on("click",
			function(){
				me.controller.showEconResolve(p_item);
			}).css("cursor","hand");
	}
	
    
    /**
     * 初始化下方表格
     */
    function _initDataGrid()
    {
        var restUrl = "~/rest/coContractbaseinfoV2Act/queryMonthManage";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : contractMonthManage.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "col0",
            loadMeta : false
        });
        
        /* 初始化 DataGrid */
        _dataGrid = new mx.datacontrols.GroupHeaderGrid({   
			// 构造查询属性。
			alias: "contractMonthManageMainViewDataGrid",
			columns:[
			{	name: "col0", caption: "主键" , editorType: "TextEditor",align:"center",dataAlign:"center",readOnly:true	},         
			{	name: "papercontractcode", caption: "纸质合同编号" , editorType: "TextEditor",align:"center",dataAlign:"center",readOnly:true	},
			{	name: "col16", caption: "售电方别名" , editorType: "TextEditor",align:"center",dataAlign:"center",readOnly:true	},
			{	name: "contractname", caption: "合同名称" , editorType: "TextEditor",align:"center",dataAlign:"center",renderCell:detailContact,readOnly:true	},
			{	name: "resolveresult", caption: "经济机组分解结果" , editorType: "TextEditor",align:"center",dataAlign:"center",readOnly:true, renderCell: _resovleResult_cellRender},
			{	name: "col14", caption: "合同类型" , editorType: "TextEditor",align:"center",dataAlign:"center",readOnly:true	},
			{	name: "purchaser", caption: "购电方" , editorType: "TextEditor",align:"center",dataAlign:"center",readOnly:true	},
			{	name: "seller", caption: "售电方" , editorType: "TextEditor",align:"center",dataAlign:"center",readOnly:true	},
			{	name: "energy", caption: "电量" , editorType: "TextEditor",align:"center",dataAlign:"center",readOnly:true	},
			{   name : "a", caption: "分月电量（万千瓦时）" ,columns:
				[
					{
						name:"col15",caption:"电量类型",width:90,editorType: "TextEditor",align:"center",dataAlign:"center",readOnly:true
					},
					{
						name:"col1",caption:"一月",width:90,editorType: "TextEditor",align:"center",dataAlign:"center",validType:"DIGIT", validOptions:{minValue: 0, validateMessage:"请输入正数"}
					},
					{
						name:"col2",caption:"二月",width:90,editorType: "TextEditor",align:"center",dataAlign:"center",validType:"DIGIT", validOptions:{minValue: 0, validateMessage:"请输入正数"}
					},
					{
						name:"col3",caption:"三月",width:90,editorType: "TextEditor",align:"center",dataAlign:"center",validType:"DIGIT", validOptions:{minValue: 0, validateMessage:"请输入正数"}
					},
					{
						name:"col4",caption:"四月",width:90,editorType: "TextEditor",align:"center",dataAlign:"center",validType:"DIGIT", validOptions:{minValue: 0, validateMessage:"请输入正数"}
					},
					{
						name:"col5",caption:"五月",width:90,editorType: "TextEditor",align:"center",dataAlign:"center",validType:"DIGIT", validOptions:{minValue: 0, validateMessage:"请输入正数"}
					},
					{
						name:"col6",caption:"六月",width:90,editorType: "TextEditor",align:"center",dataAlign:"center",validType:"DIGIT", validOptions:{minValue: 0, validateMessage:"请输入正数"}
					},
					{
						name:"col7",caption:"七月",width:90,editorType: "TextEditor",align:"center",dataAlign:"center",validType:"DIGIT", validOptions:{minValue: 0, validateMessage:"请输入正数"}
					},
					{
						name:"col8",caption:"八月",width:90,editorType: "TextEditor",align:"center",dataAlign:"center",validType:"DIGIT", validOptions:{minValue: 0, validateMessage:"请输入正数"}
					},
					{
						name:"col9",caption:"九月",width:90,editorType: "TextEditor",align:"center",dataAlign:"center",validType:"DIGIT", validOptions:{minValue: 0, validateMessage:"请输入正数"}
					},
					{
						name:"col10",caption:"十月",width:90,editorType: "TextEditor",align:"center",dataAlign:"center",validType:"DIGIT", validOptions:{minValue: 0, validateMessage:"请输入正数"}
					},
					{
						name:"col11",caption:"十一月",width:90,editorType: "TextEditor",align:"center",dataAlign:"center",validType:"DIGIT", validOptions:{minValue: 0, validateMessage:"请输入正数"}
					},
					{
						name:"col12",caption:"十二月",width:90,editorType: "TextEditor",align:"center",dataAlign:"center",validType:"DIGIT", validOptions:{minValue: 0, validateMessage:"请输入正数"}
					},
					{
						name:"col13",caption:"lasttxt",width:90,editorType: "TextEditor",align:"center",dataAlign:"center",visible:false
					},
					{
						name:"col17",caption:"合同开始月份",width:90,editorType: "TextEditor",align:"center",dataAlign:"center",visible:false
					},
					{
						name:"col18",caption:"合同结束月份",width:90,editorType: "TextEditor",align:"center",dataAlign:"center",visible:false
					},
					{	name: "contracttype", caption: "contracttype" , editorType: "TextEditor",visible:false},
					{	name: "monthqtytype", caption: "monthqtytype" , editorType: "TextEditor",visible:false}
				]
			}
            ],
            // 构造列排序条件，如果有多列，则以逗号分隔。例sorter: "school ASC, class DESC"
            displayCheckBox: true,
	        displayPrimaryKey:false,//列表是否显示主键
            allowEditing: true, //列表默认不可编辑
            allowSorting:true,//列允许排序
	        pageSize : 20,
            entityContainer: gridEntityContainer,
            displayRowNumber : true,
            height:document.body.offsetHeight - 160 ,
            rowNumberColWidth : 40,
            oncelledited:function(e){
            	editCell(e);
            },
            oncellediting:function(e){
            	initoldVal(e);
            },
            onload:dataGridEdit//根据合同开始和结束时间来设置表格1-12月的格子是否可编辑
        });
        me.addControl(_dataGrid);//添加表格
    }
    
    function initoldVal(obj){
		var tenObj=_dataGrid.entityContainer.getEntityByKey(obj.item.id);
		oldval = eval("tenObj."+obj.column.name);
	}
    
    
    function editCell(obj){
		var tenObj=_dataGrid.entityContainer.getEntityByKey(obj.item.id);
		//获取最后一行隐藏列的值
    	var value = tenObj.col13;//@获取当前value值
    	if(value == ""){
    		value = tenObj.energy;
    	}
    	//获取点击表格 的月份
    	var num1=obj.column.name.substring(3,obj.column.name.length);
//    	var nowContractId= obj.item.id;
//    	var items = restClient.getSync(contResoByMonth.mappath("~/rest/contResoByMonth/initHtdl?contractid="+nowContractId)).resultValue;	//获取分解方式
//    	emonth = items[5].emonth;//@获取emonth
//    	emonth=Number(emonth);
    	
    	var emonth = obj.item.values.col18;
    	
    	//如果点击格 是 合同最后的月份    就降隐藏列  的值 赋给 这个月份
    	if (Number(num1)==Number(emonth)) {
    		_dataGrid.entityContainer.setValue(tenObj, "col"+emonth,value);
    		obj.item.setValue(Number(emonth)+9,value);
    	}else{	
    		//如果 点击的不是最后一个月份 
    		var newvalue =obj.cell.html();//@获取newvalue值
    		var oldvalue = (oldval==null?"":oldval);//@获取oldvalue值
    		var str=accSub(accAdd(Number(value),Number(oldvalue)),Number(newvalue));
    		if(newvalue != oldvalue){
    			flagChange = true;
    		}
    		_dataGrid.entityContainer.setValue(tenObj, "col"+emonth,str);
    		obj.item.setValue(Number(emonth)+9,str);
    	}
    	_dataGrid.entityContainer.setValue(tenObj, "col13",eval("obj.item.values.col"+emonth));
    	obj.item.setValue(21,eval("obj.item.values.col"+emonth));
	}
  //处理过的乘法运算arg1*10^n
	function accMul(arg1,n){
		var arg1_str = arg1.toString();//@转换为字符串
		var index = arg1_str.indexOf(".");
		try{
			var arr1 = arg1_str.split(".");
			var r1 = arr1[1].length;
			var r2 = arr1[0].length;
		} catch(e){
			r1 = 0;
		}
		arg1_str = arg1_str.replace(".","");
		
		if(-n>=r2){
			for(var i = 0;i < -n-r2+1;i++){
				arg1_str = "0"+arg1_str;
			}
		}
		var sub = n - r1;
		if(n>r1){
			for(var i = 0;i < sub;i++){
				arg1_str += "0";
				r1++;
			}
		}
		var len = arg1_str.length;
		var mul_str = arg1_str.substring(0,len-r1+n)+"."+arg1_str.substring(len-r1+n,len);
		
		var mul = Number(mul_str);
		return mul;
	}
  //处理过的加法运算
	function accAdd(arg1,arg2){
		var arg1_str = arg1.toString();//@转换为字符串
		var arg2_str = arg2.toString();//@转换为字符串
		try{
			var arr1 = arg1_str.split(".");
			var r1 = arr1[1].length;
		} catch(e){
			r1 = 0
		}
		try{
			var arr2 = arg2_str.split(".");
			var r2 = arr2[1].length;
		} catch(e){
			r2 = 0
		}
		var c = Math.abs(r1 - r2);
		var m = Math.max(r1,r2);
		arg1_str = arg1_str.replace(".","");
		arg2_str = arg2_str.replace(".","");
		if(r1<r2){
			for ( var i = 0; i < c; i++) {
				arg1_str += "0";
			}
		}else{
			for ( var i = 0; i < c; i++) {
				arg2_str += "0";
			}
		}
		var sum = Number(arg1_str)+Number(arg2_str);
		var len = sum.toString().length;
		var sum_str = accMul(sum,-m);
		//var sum_str = sum.toString().substring(0,len-m)+"."+sum.toString().substring(len-m,len);
		return Number(sum_str);
	}
	function accSub(arg1,arg2){
		return accAdd(arg1,-arg2);
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
    	var items = restClient.getSync(contractMonthManage.
    			mappath("~/rest/coContractbaseinfoV2Act/getComponentItems"),
    			{type:type,marketId:marketId,value:1}).resultValue;	//获取购电方数据
    	me.component7.setItems(items);
    }	
    /**
     * 给售电方下拉框赋值
     */
    function setComponent8Items(){
    	var type =  me.component6.getValue() == null ? "" : me.component6.getValue();	//获取合同类型值
    	var items = restClient.getSync(contractMonthManage.
    			mappath("~/rest/coContractbaseinfoV2Act/getComponentItems"),
    			{type:type,marketId:marketId,value:2}).resultValue;	//获取售电方数据
    	me.component8.setItems(items);
    }	
    /**
     * 给合同序列赋值
     */
    function setComponent13Items(changecontracttype){
    	var items = restClient.getSync(contractMonthManage.
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
    	var coTName = p_item.getValue("col14"); 
    	var htxlList = restClient.getSync(contractMonthManage.mappath("~/rest/coContractbaseinfoV2Act/getHtxl"),{contractid:objId});
    	var htxlName = null;
    	if(htxlList!=null && htxlList.length>0){
    		for(var i=0;i<htxlList.length;i++){
    			htxlName = htxlList[i][1];
    		}
    	}
    	$p_cell[0].onclick = function(){detail(objId,contracttype,coTName,htxlName);}	//添加查询事件
    	$p_cell.html("<a href=# >"+p_item.getValue("contractname")+"</a>");	//添加超链接
    	
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
     * 算法说明
     */
    me.functionExplain = function(){
		var message = "一、电量分解到经济机组分解规则"+ "\n" +
						"*  1、通过合同id找到合同的购电方和售电方"+ "\n" +
						"*  2、通过市场成员找到经济机组"+ "\n" +
						"*  3、判断该合同开始时间与结束时间是否正确"+ "\n" +
						"*  4、判断该合同是否完成按月分解电量"+ "\n" +
						"*  5、判断该合同是否完成分段电量维护"+ "\n" +
						"*  6、获取经济机组权益容量"+ "\n" +
						"*  7、按照如下公式分解电量到经济机组"+ "\n" +
						"	电量 = A1/A  * B1/B * 总电量（尖峰电量，峰电量，平电量，谷电量）"+ "\n" +
						"	购方权益容量总和 A"+ "\n" +
						"	售方权益容量总和 B"+ "\n" +
						"	购方其中一个经济机组权益容量 A1"+ "\n" +
						"	售方其中一个经济机组权益容量 B1"+ "\n" +
						"	总电量为分段电量电价维护界面每月总电量，尖峰电量，峰电量，平电量，谷电量"+ "\n" +
						"二、电量分解到经济机组操作方法"+ "\n" +
						"*  1、通过查询条件筛选出需要进行分解的合同"+ "\n" +
						"*  2、选择一条或者多条合同点击分解电量到经济机组按钮，进入电量分解界面"+ "\n" +
						"*  3、点击指定经济机组按钮，进入经济机组选择界面"+ "\n" +
						"*  4、指定经济机组，点击确定按钮，返回电量分解界面"+ "\n" +
						"*  5、点击分解按钮，将电量分解到指定的经济机组中"+ "\n";
		
        var $container = $(document.body);

        if ($container == null) return;

        if (__$indication == null)
        {
            __$indication = $("<div id='indication' class='mx'><div id='icon' onclick='__$indication.fadeOut(500)'/><pre id='text' STYLE='text-align:left'/></pre>");
            __$indication.css("minWidth", 140);
            __$indication.css("maxWidth", "90%");
            __$indication.hide();
            __$indication.borderRadius(12);
        }
        $container.append(__$indication);

        var symbol = mx.utils.SymbolUtil.getSymbol("info");
        if (symbol != null)
        {
            __$indication.children("#icon").show();
            __$indication.children("#icon").css("font-size", "16px");
            __$indication.children("#icon").text("关闭");
        }
        else
        {
            __$indication.children("#icon").hide();
        }
        
        __$indication.children("#text").text(message);
        __$indication.css("left", 0);
        __$indication.css("top", 0);
        __$indication.centralize();
        
        __$indication.stop(true, true);
        __$indication.show();
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
    
     /**
     * 初始化表单视图窗口对象
     */
    function _initDetailWindow(){
    	_chooseUnitWin = utils.commonFun.WinMananger.create({
			reusable: true,//是否复用
			width:"50%",
			height:"70%",
			title:"表单维护"
		});
    }
    
    /**
     * 获取表单视图窗口对象
     */
    me.getChooseUnitWindow = function(){
    	return _chooseUnitWin;
    }
    
	me.endOfClass(arguments)
    return me;
};