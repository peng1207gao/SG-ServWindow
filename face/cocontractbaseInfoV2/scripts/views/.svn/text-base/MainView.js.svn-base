$ns("cocontractbaseInfoV2.views");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGridSearchBox");
$import("mx.datacontrols.ComplexGrid");
$import("mx.rpc.RESTClient");
$import("mx.containers.HSplit");
$import("mx.containers.Panel");
$import("mx.containers.Container");
$import("mx.charts.ChartWrapper");
$import("mx.datas.DataTable");
$import("fusionChart.lib.FusionCharts");

//合同管理（图表显示）
cocontractbaseInfoV2.views.MainView = function()
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
    	//页面上面分成上下两部分
    	me.mSplit = new mx.containers.HSplit({rows:"145px,auto",borderThick:1});
    	me.addControl(me.mSplit);
    	me.panel = new mx.containers.Panel({title:"合同管理统计",width:"100%"});
    	me.mSplit.addControl(me.panel,0);//添加panel
    	me.container = new mx.containers.Container({padding:"2px",width:"100%"});
    	me.panel.addControl(me.container);//添加container
    	
    	me.result = restClient.getSync(cocontractbaseInfoV2.mappath("~/rest/cocontractbaseinfoV2/charts"));
    	//已签订合同数
    	var yqdcount = 0;
    	//起草中合同数
    	var qczcount = 0;
    	//流转审核合同数
    	var lzshcount = 0;
    	//已备案合同数
    	var ybacount = 0;
    	//if(me.result) {}
    	for(var i=0;i<4;i++) {
    		if(me.result.resultValue.items[0].statelist[i] != null && me.result.resultValue.items[0].statelist[i] != "") {
    			if(me.result.resultValue.items[0].statelist[i][1] == "已签订") {
        			yqdcount = me.result.resultValue.items[0].statelist[i][0];
    			}else if(me.result.resultValue.items[0].statelist[i][1] == "起草中") {
        			qczcount = me.result.resultValue.items[0].statelist[i][0];
        		}else if(me.result.resultValue.items[0].statelist[i][1] == "流转审核") {
        			lzshcount = me.result.resultValue.items[0].statelist[i][0];
        		}else if(me.result.resultValue.items[0].statelist[i][1] == "已备案") {
        			ybacount = me.result.resultValue.items[0].statelist[i][0];
        		}
    		}
    	}
    	var string1 = '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<font style="font-weight: bolder;font-size: 15px;">'+me.result.resultValue.items[0].codate+'</font>年,  <font style="font-weight: bolder;font-size: 15px;">'+me.result.resultValue.items[0].marketName+'</font>应签订合同  <font style="font-weight: bolder;font-size: 15px;  ">'+me.result.resultValue.items[0].count+'</font>份，截止  <font style="font-weight: bolder;font-size: 15px;  ">'+me.result.resultValue.items[0].gendate+'</font> 已签订合同 <font style="font-weight: bolder;font-size: 15px; ">'+yqdcount+'</font>份； 已备案合同  <font style="font-weight: bolder;font-size: 15px; ">'+ybacount+'</font>份； ' +
    			'起草中合同  <font style="font-weight: bolder;font-size: 15px; ">'+qczcount+'</font>份； 流转审核合同  <font style="font-weight: bolder;font-size: 15px; ">'+lzshcount+'</font>份； 合同签订完成率为  <font style="font-weight: bolder;font-size: 15px;  ">'+me.result.resultValue.items[0].percent+'%</font> 。 ';
    	var string2 = '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp向电厂购电合同<font style="font-weight: bolder;font-size: 15px;">'+me.result.resultValue.items[0].purcount+'</font>份，合同总电量<font style="font-weight: bolder;font-size: 15px;">'+me.result.resultValue.items[0].pursum+'</font>亿千瓦时; 其中，基数合同<font style="font-weight: bolder;font-size: 15px;">'+me.result.resultValue.items[0].jscount+'</font>份，合同电量<font style="font-weight: bolder;font-size: 15px;">'+me.result.resultValue.items[0].jssum+'</font>亿千瓦时; ' +
    			'发电权合同<font style="font-weight: bolder;font-size: 15px;">'+me.result.resultValue.items[0].fdqcount+'</font>份，合同电量<font style="font-weight: bolder;font-size: 15px;">'+me.result.resultValue.items[0].fdqsum+'</font>亿千瓦时; 外送电合同<font style="font-weight: bolder;font-size: 15px;">'+me.result.resultValue.items[0].wsdcount+'</font>份，合同电量<font style="font-weight: bolder;font-size: 15px;">'+me.result.resultValue.items[0].wsdsum+'</font>亿千瓦时; 电力用户与发电企业直购电合同<font style="font-weight: bolder;font-size: 15px;">'+me.result.resultValue.items[0].dyhcount+'</font>份，合同电量<font style="font-weight: bolder;font-size: 15px;">'+me.result.resultValue.items[0].dyhsum+'</font>亿千瓦时。 ';
    	var string3 = '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp跨区跨省交易合同<font style="font-weight: bolder;font-size: 15px;">'+me.result.resultValue.items[0].kqkscount+'</font>份，其中，跨区交易合同<font style="font-weight: bolder;font-size: 15px;">'+me.result.resultValue.items[0].areacount+'</font>份，跨省交易合同<font style="font-weight: bolder;font-size: 15px;">'+me.result.resultValue.items[0].procount+'</font>份。 ';
    	var hello = '<p style="font-size: 15px; font-family: Arial, Helvetica, sans-serif;">'+string1+'</p><p>&nbsp</p>'+
    	'<p style="font-size: 15px; font-family: Arial, Helvetica, sans-serif;">'+ string2 +'</p><p>&nbsp</p>'+
    	'<p style="font-size: 15px; font-family: Arial, Helvetica, sans-serif;">'+ string3 +'</p>';
    	me.container.$e.append(hello);
    	
    	//me.panel1 = new mx.containers.Panel({title:"合同管理统计饼图",width:"100%"});
    	//me.mSplit.addControl(me.panel1,1);//添加panel
    	me.container1 = new mx.containers.Container({padding:"2px",width:"100%"});
    	me.mSplit.addControl(me.container1,1);//添加container
    	
		//将container1页面上面分成上中下三部分
    	//me.aSplit = new mx.containers.HSplit({rows:"auto,auto",borderThick:0});
    	//me.container1.addControl(me.aSplit);
    	//把上部分分成左右两部分
    	me.vSplit1 = new mx.containers.VSplit({cols:"50%, 50%" , borderThick:0});//上部分
    	me.container1.addControl(me.vSplit1);//添加上部分
    	
    	var chart1 = new FusionCharts(fusionChart.mappath("$/swf/Pie3D.swf"),"myChartId","100%","100%","0","1");//图表的显示
    	chart1.setDataXML(me.result.resultValue.items[0].strCapXml1);//获取数据
    	me.vSplit1.$e.get(0).align = "center";//内部居中
		chart1.render(me.vSplit1.$panel1.get(0));//着色
		me.vSplit1.addControl(chart1,0);//把图表添加到vSplit

		var chart2 = new FusionCharts(fusionChart.mappath("$/swf/Pie3D.swf"),"myChartId","100%","100%","0","1");//图表的显示
    	chart2.setDataXML(me.result.resultValue.items[0].strCapXml2);//获取数据
    	me.vSplit1.$e.get(0).align = "center";//内部居中
		chart2.render(me.vSplit1.$panel2.get(0));//着色
		me.vSplit1.addControl(chart2,1);//把图表添加到vSplit
		
    	//把中部分分成左右两部分
    	me.vSplit2 = new mx.containers.VSplit({cols:"50%, 50%" ,borderThick:0});//中部分
    	me.container1.addControl(me.vSplit2);//添加中部分
    	
    	var chart3 = new FusionCharts(fusionChart.mappath("$/swf/Pie3D.swf"),"myChartId","100%","100%","0","1");//图表的显示
    	chart3.setDataXML(me.result.resultValue.items[0].strCapXml3);//获取数据
    	me.vSplit2.$e.get(0).align = "center";//内部居中
		chart3.render(me.vSplit2.$panel1.get(0));//着色
		me.vSplit2.addControl(chart3,0);//把图表添加到vSplit
    	
		var chart4 = new FusionCharts(fusionChart.mappath("$/swf/Pie3D.swf"),"myChartId","100%","100%","0","1");//图表的显示
    	chart4.setDataXML(me.result.resultValue.items[0].strCapXml4);//获取数据
    	me.vSplit2.$e.get(0).align = "center";//内部居中
		chart4.render(me.vSplit2.$panel2.get(0));//着色
		me.vSplit2.addControl(chart4,1);//把图表添加到vSplit
		
    	//把下部分分成左右两部分
    	me.vSplit3 = new mx.containers.VSplit({cols:"50%, 50%" ,borderThick:0});//下部分
    	me.container1.addControl(me.vSplit3);//添加下部分
    	
    	var chart5 = new FusionCharts(fusionChart.mappath("$/swf/Pie3D.swf"),"myChartId","100%","100%","0","1");//图表的显示
    	chart5.setDataXML(me.result.resultValue.items[0].strCapXml5);//获取数据
    	me.vSplit3.$e.get(0).align = "center";//内部居中
		chart5.render(me.vSplit3.$panel1.get(0));//着色
		me.vSplit3.addControl(chart5,0);//把图表添加到vSplit
		
    	var chart6 = new FusionCharts(fusionChart.mappath("$/swf/Pie3D.swf"),"myChartId","100%","100%","0","1");//图表的显示
    	chart6.setDataXML(me.result.resultValue.items[0].strCapXml6);//获取数据
    	me.vSplit3.$e.get(0).align = "center";//内部居中
		chart6.render(me.vSplit3.$panel2.get(0));//着色
		me.vSplit3.addControl(chart6,1);//把图表添加到vSplit
    	//_drawChart();
    	//_getChartData();
    	_initDetailWindow();
    }

    function _initDataGrid()
    {
        var restUrl = "~/rest/cocontractbaseinfoV2/";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : cocontractbaseInfoV2.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "contractid"
        });
        
        /* 初始化 DataGrid */
        _dataGrid = new mx.datacontrols.ComplexGrid({
			// 构造查询属性。
			alias: "cocontractbaseInfoV2MainViewDataGrid",

			columns:[
	        {	name: "marketid", caption: "??????????" , editorType: "TextEditor"	},
	        {	name: "contractid", caption: "??id" , editorType: "TextEditor"	},
	        {	name: "supercontractid", caption: "????id" , editorType: "TextEditor"	},
	        {	name: "contractname", caption: "????" , editorType: "TextEditor"	},
	        {	name: "contracttype", caption: "????" , editorType: "TextEditor"	},
	        {	name: "papercontractcode", caption: "?????????????" , editorType: "TextEditor"	},
	        {	name: "papercontractname", caption: "??????" , editorType: "TextEditor"	},
	        {	name: "contracttemplateid", caption: "?????????????" , editorType: "TextEditor"	},
	        {	name: "signstate", caption: "1???2??? " , editorType: "TextEditor"	},
	        {	name: "signeddate", caption: "?????? " , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "notsignedreason", caption: "??????? " , editorType: "TextEditor"	},
	        {	name: "signedperson", caption: "????? " , editorType: "TextEditor"	},
	        {	name: "signedsite", caption: "?????? " , editorType: "TextEditor"	},
	        {	name: "backupperson", caption: "?????" , editorType: "TextEditor"	},
	        {	name: "backupdate", caption: "??????" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "backupstate", caption: "????" , editorType: "TextEditor"	},
	        {	name: "backuporg", caption: "??????" , editorType: "TextEditor"	},
	        {	name: "disbackupreason", caption: "?????" , editorType: "TextEditor"	},
	        {	name: "isend", caption: "???? " , editorType: "TextEditor"	},
	        {	name: "prepcontractflag", caption: "????? " , editorType: "TextEditor"	},
	        {	name: "issecrecyflag", caption: "?????? " , editorType: "TextEditor"	},
	        {	name: "contractcyc", caption: "1??2??3??4??5??6?? " , editorType: "TextEditor"	},
	        {	name: "purchaser", caption: "???????? " , editorType: "TextEditor"	},
	        {	name: "seller", caption: "???????? " , editorType: "TextEditor"	},
	        {	name: "sellerunit", caption: "???? " , editorType: "TextEditor"	},
	        {	name: "purchaseunit", caption: "???? " , editorType: "TextEditor"	},
	        {	name: "contractqty", caption: "????" , editorType: "TextEditor"	},
	        {	name: "qtytype", caption: "???? " , editorType: "TextEditor"	},
	        {	name: "contractstartdate", caption: "?????? " , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "contractenddate", caption: "?????? " , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "transactionid", caption: "????? " , editorType: "TextEditor"	},
	        {	name: "transactiontype", caption: "?????? " , editorType: "TextEditor"	},
	        {	name: "rightsettlementside", caption: "1???2??? " , editorType: "TextEditor"	},
	        {	name: "isrepurchaseflag", caption: "?????? " , editorType: "TextEditor"	},
	        {	name: "replacetype", caption: "???? " , editorType: "TextEditor"	},
	        {	name: "areatype", caption: "???????? " , editorType: "TextEditor"	},
	        {	name: "isehvflag", caption: "???????? " , editorType: "TextEditor"	},
	        {	name: "isresaleflag", caption: "????????? " , editorType: "TextEditor"	},
	        {	name: "isopen", caption: "?????? " , editorType: "TextEditor"	},
	        {	name: "purchasegate", caption: "???? " , editorType: "TextEditor"	},
	        {	name: "settlegate", caption: "???? " , editorType: "TextEditor"	},
	        {	name: "lossundertaker", caption: "1???2??? " , editorType: "TextEditor"	},
	        {	name: "purchaserperson", caption: "?????? " , editorType: "TextEditor"	},
	        {	name: "purchaserphone", caption: "??????? " , editorType: "TextEditor"	},
	        {	name: "purchaserconftime", caption: "??????? " , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "sellerperson", caption: "?????? " , editorType: "TextEditor"	},
	        {	name: "sellerphone", caption: "??????? " , editorType: "TextEditor"	},
	        {	name: "sellerconftime", caption: "???????" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "transperson", caption: "??????" , editorType: "TextEditor"	},
	        {	name: "transphone", caption: "???????" , editorType: "TextEditor"	},
	        {	name: "transconftime", caption: "???????" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "changetimes", caption: "???? " , editorType: "TextEditor"	},
	        {	name: "description", caption: "?? " , editorType: "TextEditor"	},
	        {	name: "isdelete", caption: "???? " , editorType: "TextEditor"	},
	        {	name: "updatetime", caption: "?????? " , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "updatepersonid", caption: "??????? " , editorType: "TextEditor"	},
	        {	name: "versionid", caption: "??ID " , editorType: "TextEditor"	},
	        {	name: "version", caption: "??? " , editorType: "TextEditor"	},
	        {	name: "versiondesc", caption: "???? " , editorType: "TextEditor"	},
	        {	name: "isrelation", caption: "?????????????????????? " , editorType: "TextEditor"	},
	        {	name: "exectype", caption: "??1???????2?????? " , editorType: "TextEditor"	},
	        {	name: "flow", caption: "1??????2?????????3?????4?????5??????????? " , editorType: "TextEditor"	},
	        {	name: "settleside", caption: "1????2???3?? " , editorType: "TextEditor"	},
	        {	name: "businessid", caption: "????id" , editorType: "TextEditor"	},
	        {	name: "flowflag", caption: "????" , editorType: "TextEditor"	},
	        {	name: "sequenceid", caption: "????id" , editorType: "TextEditor"	},
	        {	name: "energy", caption: "??" , editorType: "TextEditor"	},
	        {	name: "contractprice", caption: "????" , editorType: "TextEditor"	},
	        {	name: "contractenergy", caption: "????" , editorType: "TextEditor"	},
	        {	name: "superexecid", caption: "?????ID" , editorType: "TextEditor"	},
	        {	name: "ingodown", caption: "????" , editorType: "TextEditor"	},
	        {	name: "expend1", caption: "??1" , editorType: "TextEditor"	},
	        {	name: "expend2", caption: "??2" , editorType: "TextEditor"	},
	        {	name: "expend3", caption: "??3" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "expend4", caption: "??4" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "expend5", caption: "??5" , editorType: "TextEditor"	},
	        {	name: "expend6", caption: "??6" , editorType: "TextEditor"	},
	        {	name: "purchasergen", caption: "??????" , editorType: "TextEditor"	},
	        {	name: "purchaserenergy", caption: "??????? " , editorType: "TextEditor"	},
	        {	name: "sellergen", caption: "??????" , editorType: "TextEditor"	},
	        {	name: "sellerenergy", caption: "??????? " , editorType: "TextEditor"	},
	        {	name: "coVersion", caption: "????" , editorType: "TextEditor"	},
	        {	name: "isTax", caption: "????" , editorType: "TextEditor"	}
            ],
            // 构造列排序条件，如果有多列，则以逗号分隔。例sorter: "school ASC, class DESC"
            displayCheckBox: true,
	        displayPrimaryKey:false,//列表是否显示主键
            allowEditing: false, //列表默认不可编辑
	        pageSize : 20,
            entityContainer: gridEntityContainer
	        //create: me.controller._btnNew_onclick,
           // remove: me.controller._btnDelete_onclick
        });
        
        me.addControl(_dataGrid);
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