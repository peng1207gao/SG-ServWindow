$ns("radarscan.views");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGridSearchBox");
$import("mx.datacontrols.ComplexGrid");
$import("mx.containers.HSplit");
$import("mx.containers.Panel");
$import("mx.rpc.RESTClient");
$import("mx.utils.ExcelUtil");
$import("mx.containers.DockPage");
$import("mx.containers.DockPanel");
$import("fusionChart.lib.FusionCharts");


radarscan.views.MainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    //网格列表
    var _dataGrid = null;
    var _dataGrid2 = null;
    var threshold = "";
    var period = "";
    
    var restClient = new mx.rpc.RESTClient();


    base.init = me.init;
    me.init = function()
    {
        base.init();
        _initControls();
    };
    
    function _initControls()
    {
    	
    	initLayout();//初始化布局
    	initComponent();//初始化控件
    	_initChart();//初始化图形
    	_initDataGrid2();//初始化统计数据表格
    	_initDataGrid();//初始化表格
    	search();//查询数据
    }
    
    
    /**
     * 页面初始化布局(主要控制页面控件的布局)
     */
    function initLayout(){
    	//业务查询区域
    	me.panel1 = new mx.containers.Panel({width:"100%",height:"50",title:"业务查询"});	//业务查询panel容器
    	me.addControl(me.panel1);	//添加panel1容器
    	
    	//添加业务查询区域内包含的容器Contianer
    	me.container1 = new mx.containers.Container({width:"100%",height:"30",padding:"2"});	//放置标签、下拉框、文本框等的容器
    	me.panel1.addControl(me.container1);	//添加container1容器
    	
    	//业务操作区域
    	me.panel2 = new mx.containers.Panel({width:"100%",height:"50",title:"业务操作"}); //业务操作panel容器
    	me.addControl(me.panel2);	//添加panel2容器
    	
    	//添加业务操作区域内包含的容器Contianer
    	me.container6 = new mx.containers.Container({width:"100%",height:"40",padding:"2"});	//放置按钮等的容器
    	me.panel2.addControl(me.container6);	//添加container4容器
    	
    	//统计数据区域
    	me.panel3 = new mx.containers.Panel({width:"100%",height:"80",title:"统计数据"}); //业务操作panel容器
    	me.addControl(me.panel3);	//添加panel2容器
    	
    	
    }
    
    function _initChart(){
    	//添加业务查询区域内包含的容器Contianer
    	me.container2 = new mx.containers.Container({width:"100%",height:"300",padding:"2"});	//放置标签、下拉框、文本框等的容器
    	me.addControl(me.container2);	//添加container2容器
    }
  
    
    /**
     * 初始化控件(添加页面的标签、下拉框、文本框、按钮、表格等控件)
     */
    function initComponent(){
    	
    	me.label3 = new mx.controls.Label({text:"时间周期：",width:"8%",textAlign:"right",right:4});	//购电方标签
    	me.container1.addControl(me.label3);
    	me.editor2 =  new mx.editors.DropDownEditor({
    				displayMember: "name",
    			    valueMember: "value",
    			    nullable:false,
    			    items: [
    			        { name: "3日", value: "1" },
    			        { name: "5日", value: "2" },
    			        { name: "一周", value: "3" },
    			        { name: "二周", value: "4" },
    			        { name: "一月", value: "5" }
    			    ]

    			});
    	me.editor2.setValue("3");
        me.container1.addControl(me.editor2);
    	
    	me.label1 = new mx.controls.Label({text:"统调火电完成率偏差值：",width:"15%",textAlign:"right",right:4});	//购电方标签
    	me.container1.addControl(me.label1);
    	me.editor1 = new mx.editors.TextEditor({width:"10%"});
    	me.editor1.setValue("3");
        me.container1.addControl(me.editor1);
        me.label2 = new mx.controls.Label({text:"%",width:"20",textAlign:"left"});	//购电方标签
        me.container1.addControl(me.label2);
        
        
    	
    	//me.container6添加业务操作第一行的按钮
    	me.button1 = new mx.controls.Button({text:"查询",left:20,onclick:search});	//查询合同按钮
    	me.container6.addControl(me.button1);	//添加按钮
   
    	me.button2 = new mx.controls.Button({text:"说明",left:40,onclick:declare});	//查询合同按钮
    	me.container6.addControl(me.button2);	//添加按钮
    }
    
    
    /**
     * 查询合同方法
     */
    function search(){
    	
    	threshold = me.editor1.getValue() == null ? "" : me.editor1.getValue();
    	period = me.editor2.getValue() == null ? "" : me.editor2.getValue();
    	
    	
    	if(threshold==""){
    		mx.indicate("info", "统调火电完成率偏差值不能为空！");
			return;
    	}
    	
//    	if(period==""){
//    		mx.indicate("info", "时间周期不能为空！");
//			return;
//    	}
    	
    	var validateController = new validate.views.ValidateMainViewController();
		var validateContent = validateController.getValidate("floatNumberMaxSize");
		//校验查询条件 长度100
		var isValid=validateContent.validator(threshold,[100]);
		if(threshold<0){
			mx.indicate("info", "统调火电完成率偏差值：必须是0-100的有效数字！");
			return;
		}
		if (!isValid) {
			mx.indicate("info", "统调火电完成率偏差值：必须是0-100的有效数字！");
			return;
		}
    	loadGrid1();//加载第一个表格数据
    	loadGrid2();//加载第二个表格数据
    	_initRader();//加载第二个表格数据

    }
    
    /**
     * 说明按钮
     */
    function declare(){
		var message = " 雷达扫描中统调火电计算规则"+ "\n" +
						"*  1、统计范围为本年度的基数合同，即合同类型为基数合同及其子类型，合同起止时间为本年度的1月1日到12月31日的统调火电基数合同；"+ "\n" +
						"*  2、按售电方单机最大装机容量归类，再分别汇总计算各类合同的总电量，总完成电量，计算平均完成进度；"+ "\n" +
						"*  3、单个计算每个合同的合同电量，完成电量，完成进度；"+ "\n" +
						"*  4、比较单个合同完成进度与其所属类的平均完成进度大小，得到偏差值。公式:偏差值=单个进度-平均进度；"+ "\n" +
						"*  5、将偏差值与阈值进行比较，确定是统调火电完成率偏高、偏低还是正常。"+ "\n" +
						"   例如阈值为3%，则统调火电完成率偏高的合同为：偏差值>3%"+ "\n" +
						"	                统调火电完成率偏低的合同为：偏差值<-3%,"+ "\n" +
						"	                正常的合同为：-3%<=偏差值<=3%"+ "\n";
		
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
//        __$indication.show().delay(2000).fadeOut(1200);
        __$indication.show();
	}
    
    /**
     * 加载第一个表格数据
     */
    function loadGrid1(){
    	//给dataGrid添加过滤条件
    	_dataGrid.setFilter({col1:threshold/100,col2:period});
    	//加载表格数据
    	_dataGrid.load();
    }
    
    /**
     * 加载第二个表格数据
     */
    function loadGrid2(){
    	//给dataGrid添加过滤条件
    	_dataGrid2.setFilter({col1:threshold/100,col2:period});
    	//加载表格数据
    	_dataGrid2.load();
    }
    
    /**
     * 加载图形
     */
    function chart(){
    	if(me.picEditor!='undefined'&&me.picEditor!=null){
    		me.picEditor.$e.remove();
		}
    	
    	var params ={"threshold":threshold/100,"period":period};
		var responseContent = restClient.getSync(radarscan.mappath("~/rest/radarscan/getChartData"), { "params": JSON.stringify(params)});
		me.dataXml = responseContent.resultValue.items[0];
		if(me.conEnerMess==""){
			mx.indicate("info", "无数据！");
			me.conEnerMess="";
		}
		var chart = new FusionCharts(fusionChart.mappath("$/swf/Bar2D.swf"),"myChartId","100%","100%","0","1");//图表的显示
    	chart.setDataXML(me.dataXml); 
    	chart.render(me.container2.$e.get(0));
    }

    function _initRader()
    {
    	if(me.container2.$e.get(0).childElementCount==1){
    		me.container2.$e.get(0).innerHTML=null;
    	}
    	me.picEditor = new mx.editors.PictureEditor({
    		width:"100%",
    		height:"300px"
    	});
    	me.picEditor.setValue(radarscan.mappath("$/images/radarscan.gif"));
    	me.container2.addControl(me.picEditor);
    	window.setTimeout(chart, 5000);
    }
    
  
    function _initDataGrid2()
    {
        var restUrl = "~/rest/radarscan/getStatics";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : radarscan.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "col0"
        });
        
        /* 初始化 DataGrid */
        _dataGrid2 = new mx.datacontrols.ComplexGrid({   
			// 构造查询属性。
			alias: "radarscanMainViewDataGrid2",
			columns:[
			{	name: "col0", caption: "即将执行的合同" , editorType: "TextEditor",align:"center",dataAlign:"center",width:"15%"	},         
			{	name: "col1", caption: "即将到期的合同" , editorType: "TextEditor",align:"center",dataAlign:"center",width:"15%"	},
			{	name: "col2", caption: "新增的合同" , editorType: "TextEditor",align:"center",dataAlign:"center",width:"10%"	},  
			{	name: "col3", caption: "变更的合同" , editorType: "TextEditor",align:"center",dataAlign:"center",width:"10%"	},
			{	name: "col4", caption: "终止的合同" , editorType: "TextEditor",align:"center",dataAlign:"center",width:"10%"	},
			{	name: "col5", caption: "统调火电完成率偏高的合同" , editorType: "TextEditor",align:"center",dataAlign:"center",width:"20%"	},
			{	name: "col6", caption: "统调火电完成率偏低的合同" , editorType: "TextEditor",align:"center",dataAlign:"center",width:"20%"	}
			
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
        me.panel3.addControl(_dataGrid2);//添加表格
    }
  

    /**
     * 初始化下方表格
     */
    function _initDataGrid()
    {
        var restUrl = "~/rest/radarscan/";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : radarscan.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "contractid"
        });
        
        /* 初始化 DataGrid */
        _dataGrid = new mx.datacontrols.ComplexGrid({   
			// 构造查询属性。
			alias: "radarscanMainViewDataGrid",
			columns:[
			{	name: "contractid", caption: "主键" , editorType: "TextEditor",align:"center",dataAlign:"center"	},         
			{	name: "contractname", caption: "合同名称" , editorType: "TextEditor",align:"center",dataAlign:"center",width:"20%"	},
			{	name: "contracttype", caption: "合同类型" , editorType: "TextEditor",align:"center",dataAlign:"center",width:"20%"    },  
			{	name: "purchaser", caption: "售电方" , editorType: "TextEditor",align:"center",dataAlign:"center",width:"20%"	},
			{	name: "seller", caption: "购电方" , editorType: "TextEditor",align:"center",dataAlign:"center",width:"20%"	},
			{	name: "contractqty", caption: "合同电量" , editorType: "TextEditor",align:"center",dataAlign:"center",width:"20%"	}
			
            ],
            // 构造列排序条件，如果有多列，则以逗号分隔。例sorter: "school ASC, class DESC"
            displayCheckBox: true,
	        displayPrimaryKey:false,//列表是否显示主键
            allowEditing: false, //列表默认不可编辑
            allowSorting:true,//列允许排序
	        pageSize : 10,
            entityContainer: gridEntityContainer,
            displayRowNumber : true,
            displayToolBar : false,
            height: 270,
            rowNumberColWidth : 40
        });
        //me.mDSplit.addControl(_dataGrid,1);//添加表格
        me.addControl(_dataGrid)//添加表格
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