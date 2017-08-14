$ns("contracttemplate.views");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGridSearchBox");
$import("mx.datacontrols.ComplexGrid");
$import("mx.containers.HSplit");
$import("mx.containers.Panel");
$import("mx.rpc.RESTClient");
$import("mx.utils.ExcelUtil");
$import("mx.containers.DockPage");
$import("mx.containers.DockPanel");
$import("mx.containers.HtmlContainer");

contracttemplate.views.MainView = function()
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
    	search();//查询数据
    }
    
    /**
     * 初始化方法(主要获取初始化参数)
     */
    function init(){
    	var items = restClient.getSync(contracttemplate.mappath("~/rest/coContractbaseinfoV2Act/init")).resultValue.items;
    	marketId = items[0];
    }
    
    /**
     * 页面初始化布局(主要控制页面控件的布局)
     */
    function initLayout(){
    	
    	
    	
    	//业务查询区域
    	me.panel1 = new mx.containers.Panel({width:"100%",height:"140",title:"业务查询"});	//业务查询panel容器
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
    	
    	//业务操作区域
    	me.panel2 = new mx.containers.Panel({width:"100%",height:"50",title:"业务操作"}); //业务操作panel容器,当需要显示经法功能的三个按钮时，heght:80
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
    	
    	
    	//me.container2添加业务查询第二行的控件
    	var fun = new utils.commonFun.DropDownEditorFun();
    	me.label6 = new mx.controls.Label({text:"合同类型：",width:"12%",textAlign:"right",right:4});	//合同类型标签
    	me.container2.addControl(me.label6);	//添加标签
    	me.component6 = utils.dropDownEditor.ContractTypeTree.ContractTypeDropDownEditor(false,true); //合同类型下拉框
    	me.component6.setWidth("17%");
    	me.component6.on("changed",changeDrops);//给合同类型添加onchanged触发事件
    	me.container2.addControl(me.component6);	//添加下拉框
    	
    	me.label13 = new mx.controls.Label({text:"合同序列：",width:"12%",textAlign:"right",right:4});	//合同序列标签
    	me.container2.addControl(me.label13);	//添加标签
    	me.component13 = new mx.editors.DropDownEditor({displayMember:"name",valueMember:"value",autoMatchInput:true,
    		allowEditing:true,width:"17%"}); //合同序列下拉框
    	//解决因为内容过长下拉框显示不全的问题
		fun.resizeListEditor(me.component13);
    	me.container2.addControl(me.component13);	//添加下拉框
    	setComponent13Items("");	//给合同序列赋值
    	me.label5 = new mx.controls.Label({text:"合同状态：",width:"12%",textAlign:"right",right:4});	//合同状态标签
    	me.container2.addControl(me.label5);	//添加标签
    	me.component5 = new mx.editors.DropDownEditor({displayMember:"name",valueMember:"value",width:"200",
    		url : contracttemplate.mappath("~/../ETradePublicUtils/rest/baGencodeTypeList/getList?type=67")	//获取合同状态下拉框数据
    		}); //合同状态下拉框
    	me.component5.setWidth("17%");
    	me.container2.addControl(me.component5);	//添加下拉框
    	
    	//me.container4添加业务查询第四行的控件
    	me.label1 = new mx.controls.Label({text:"合同名称：",width:"12%",textAlign:"right",right:4});	//合同名称标签
    	me.container4.addControl(me.label1);	//添加标签
    	me.component1 = new mx.editors.TextEditor({width:"17%"});	//合同文本框
    	me.container4.addControl(me.component1); //添加文本框
    	me.label2 = new mx.controls.Label({text:"纸质合同编号：",width:"12%",textAlign:"right",right:4});	//纸质合同编号标签
    	me.container4.addControl(me.label2);	//添加标签
    	me.component2 = new mx.editors.TextEditor({width:"17%"});	//纸质合同编号
    	me.container4.addControl(me.component2); //添加文本框
    	
    	//me.container3添加业务查询第三行的控件
    	me.label7 = new mx.controls.Label({text:"购电方：",width:"12%",textAlign:"right",right:4});	//购电方标签
    	me.container3.addControl(me.label7);	//添加标签
    	me.component7 = utils.dropDownEditor.CommonDropEditor.gsAutoComPleteEditor("","1");	//购电方下拉框
    	utils.dropDownEditor.CommonDropEditor.autoInit(me.component7,'1');
    	me.component7.setWidth("17%");
    	//解决因为内容过长下拉框显示不全的问题
    	fun.resizeAutocomplete(me.component7);
    	me.container3.addControl(me.component7); //添加文本框
    	me.label8 = new mx.controls.Label({text:"售电方：",width:"12%",textAlign:"right",right:4});	//售电方标签
    	me.container3.addControl(me.label8);	//添加标签
    	me.component8 = utils.dropDownEditor.CommonDropEditor.gsAutoComPleteEditor("","2"); //售电方下拉框
    	utils.dropDownEditor.CommonDropEditor.autoInit(me.component8,'2');
    	me.component8.setWidth("17%");
    	//解决因为内容过长下拉框显示不全的问题
    	fun.resizeAutocomplete(me.component8);
    	me.container3.addControl(me.component8);	//添加下拉框
    	
    
	
    	//me.container1添加业务查询第一行的控件
    	me.label10 = new mx.controls.Label({text:"时间类型：",width:"12%",textAlign:"right",right:4});	//时间类型标签
//    	me.container4.addControl(me.label10);	//添加标签
    	me.component10 = new mx.editors.DropDownEditor({displayMember:"name",valueMember:"value",width:"17%",items:
    					[{name:"合同开始时间",value:0},{name:"合同录入时间",value:1},{name:"合同签订时间",value:2}],
    					value:0});	//时间类型下拉框
//    	me.container4.addControl(me.component10); //添加下拉框
    	me.label11 = new mx.controls.Label({text:"开始时间：",width:"12%",textAlign:"right",right:4});	//开始时间标签
    	me.container1.addControl(me.label11);	//添加标签
    	me.component11 = new mx.editors.DateTimeEditor({width:"17%"}); //开始时间时间框
    	me.container1.addControl(me.component11);	//添加时间框
    	me.label12 = new mx.controls.Label({text:"截止时间：",width:"12%",textAlign:"right",right:4});	//结束时间标签
    	me.container1.addControl(me.label12);	//添加标签
    	me.component12 = new mx.editors.DateTimeEditor({width:"17%"}); //结束时间时间框
    	me.container1.addControl(me.component12);	//添加时间框
    	var date  = new Date()
    	me.component11.setValue(date.getFullYear()+"-01-01");
    	me.component12.setValue(date.getFullYear()+"-12-31");
    	me.label4 = new mx.controls.Label({text:"合同周期：",width:"12%",textAlign:"right",right:4});	//合同周期标签
    	me.container1.addControl(me.label4);	//添加标签
    	me.component4 = new mx.editors.DropDownEditor({displayMember:"name",valueMember:"value",width:"17%",
    		url:contracttemplate.mappath("~/../ETradePublicUtils/rest/baGencodeTypeList/getList?type=23")	//获取合同周期下拉框数据
    		}); //合同周期下拉框
    	me.container1.addControl(me.component4);	//添加下拉框
    	
    	
//    	me.label9 = new mx.controls.Label({text:"业务场景",width:"12%",textAlign:"right",right:4});	//业务场景标签
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
        		me.buttontest.setText("收起");
    		}else if(me.buttontest.text == "收起"){
    			var height = parseInt(me.panel1.height)-30*3;
        		me.panel1.setHeight(height);
        		me.container2.setDisplay("none");
        		me.container3.setDisplay("none");
        		me.container4.setDisplay("none");
        		me.buttontest.setText("高级查询");
    		}
    		
    	});
    	
    	
    	var contractTempLabel = new mx.controls.Label({
    	    text: "合同范本：",
    	    textAlign: "right",
    	    width:"8%",
    	    right:4,
    	    verticalAlign: "middle"
    	});
    	me.contractTempDownEditor = utils.dropDownEditor.ContractTemplate.ContractTemplateDropDownEditor(false,null); //合同范本下拉框
    	me.contractTempDownEditor.setWidth("300px");
    	me.container6.addControl(contractTempLabel);
    	me.container6.addControl(me.contractTempDownEditor);
    	//me.container6添加业务操作第一行的按钮
    	me.button1 = new mx.controls.Button({text:"查询合同",left:10,onclick:searchCon});	//查询合同按钮
    	me.container6.addControl(me.button1);	//添加按钮
    	me.outFileBtn = new mx.controls.Button({text:"生成文本",left:30,onclick:_outFileMethod});	//查询合同按钮
    	me.container6.addControl(me.outFileBtn);	//添加按钮
    	me.downloadTempBtn = new mx.controls.Button({text:"下载查看范本",left:50,onclick:me.controller._downloadTemplate});	//查询合同按钮
    	me.container6.addControl(me.downloadTempBtn);	//添加按钮
    	me.maintainBtn = new mx.controls.Button({text:"范本字段对应维护",left:70,onclick:me.controller._tempFieldConfig});	//查询合同按钮
//    	me.container6.addControl(me.maintainBtn);	//添加按钮
    	me.explainBtn = new mx.controls.Button({text:"说明",left:90,onclick:me.controller.explainBtnMethod});	//查询合同按钮
//    	me.container6.addControl(me.explainBtn);	//添加按钮
    }
    /**
     * 输出文件方法xzh
     */
    function _outFileMethod(){
    	if(me.contractTempDownEditor.value==null){
    		mx.indicate("info","请选择合同范本！");
    		return;
    	}
    	var v_dataGrid = me.getDataGrid();
     	if (v_dataGrid.getCheckedIDs().length == 0)
         {
              mx.indicate("info", "请选择数据！");
              return;
         }
//    	var controller = new contracttemplate.views.DetailViewController({mainView:me,tempId:me.contractTempDownEditor.value,ids:v_dataGrid.getCheckedIDs()});
//    	var _openView = controller.getView();
    	
    	var params={tempIds:me.contractTempDownEditor.value,contractids:v_dataGrid.getCheckedIDs()};
    	var items = restClient.getSync(contracttemplate.
    			mappath("~/rest/contracrtTemplate/setFields"),
    			{"params" : JSON.stringify(params)});	//获取售电方数据
        me.openview = new mx.views.View();
    	me._openWin = utils.commonFun.WinMananger.create({
	    			reusable: true,//是否复用
	    			width:"600px",
	    			height:"400px",
	    			title:"选择窗口"
	    		});
	    me._openWin.setView(me.openview);
	    me._openWin.showDialog();
     	var contractIds = v_dataGrid.getCheckedIDs();
     	var idLength = contractIds.length;
     	var contractIdStr = "";
     	var tempId = me.contractTempDownEditor.value;
     	for(var i = 0 ; i <idLength ; i++ )
     	{
     		if(contractIdStr=="")
     		{
     			contractIdStr = contractIds[i];
     		}
     		else
     		{
     			contractIdStr =contractIdStr+"@_@"+contractIds[i];
     		}
     	}
     	$(document.body).find("#contractIds").val(contractIdStr);
     	$(document.body).find("#tempId").val(tempId);
     	
        me.htmlContainer = new mx.containers.HtmlContainer({
          url:contracttemplate.mappath("Default1.jsp?contractIds="+idLength+"&tempId="+tempId),
          height:"100%",
          width:"100%",
          type:"Iframe"
          });
        me.openview.addControl(me.htmlContainer);
       // me.$e.append("<input type='text' name='cid' value='"+idstrs+"'></input>");
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
    	//查询条件不能输入非法字符
    	if(!validate(contractName,"合同名称")){return};
    	if(!validate(papercontractcode,"纸质合同编号")){return;}
    	if(!validate(syscontractcode,"系统合同编号")){return;}
    	loadGrid1();	//加载第一个表格数据

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
    	
    	loadGrid1();	//加载第一个表格数据

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
     * 初始化下方表格
     */
    function _initDataGrid()
    {
        var restUrl = "~/rest/coContractbaseinfoV2Act/";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : contracttemplate.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "col0"
        });
        
        /* 初始化 DataGrid */
        _dataGrid = new mx.datacontrols.ComplexGrid({   
			// 构造查询属性。
			alias: "contracttemplateMainViewDataGrid",
			columns:[
			{	name: "col0", caption: "主键" , editorType: "TextEditor",align:"center",dataAlign:"center"	},         
			{	name: "col1", caption: "纸质合同编号" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
			{	name: "col2", caption: "合同名称" , editorType: "TextEditor",align:"center",dataAlign:"center",renderCell:detailContact	},  
			{	name: "col3", caption: "合同类型" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
			{	name: "col4", caption: "购电方" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
			{	name: "col5", caption: "售电方" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
//			{	name: "col6", caption: "总电量" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
//			{	name: "col7", caption: "电价" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
			{	name: "energy", caption: "总电量" , editorType: "TextEditor",align:"center",dataAlign:"center",formatString:"0.0000"	},
			{	name: "contractprice", caption: "电价" , editorType: "TextEditor",align:"center",dataAlign:"center",formatString:"0.0000"		},
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
            height:document.body.offsetHeight - 100 ,
            rowNumberColWidth : 40,
            onload:_init
        });
        me.addControl(_dataGrid);//添加表格
    }
    
    
    
    function _init(){
    	_dataGrid.columns["contracttype"].setVisible(false);
    	//合同类型为发电权合同时 改表头名称
    	var restClient = new mx.rpc.RESTClient();
    	var result = restClient.getSync(contracttemplate.mappath("~/rest/coContractbaseinfoV2Act/isfdqht"),{"params":JSON.stringify({"contracttype":contracttype})});
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
     * 给合同序列赋值
     */
    function setComponent13Items(changecontracttype){
    	var items = restClient.getSync(contracttemplate.
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
    	var htxlList = restClient.getSync(contracttemplate.mappath("~/rest/coContractbaseinfoV2Act/getHtxl"),{contractid:objId});
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