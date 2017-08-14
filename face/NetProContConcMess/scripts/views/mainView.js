$ns("NetProContConcMess.views");

//$import("utils.dropDownEditor.marketTree");
$import("fusionChart.lib.FusionCharts");
$import("mx.controls.Label");
$import("mx.rpc.RESTClient");
NetProContConcMess.views.mainView=function(){
	var me = $extend(mx.views.View);
	
	var base = {};
	base.init = me.init;
	var ywcjLabel=null;
	var ywcjSearch=null;
	var restClient = new mx.rpc.RESTClient();
	me.conEnerMess=null;
	me.init = function () {
		base.init();
		me.setHeight("531");
		me.setWidth("1085");
		me.permissionID = "-1";
		_initControls();
	};
	
	var HSplit1 = null;
	var HSplit1Area0 = null;
	var HSplit1Area1 = null;
	
	function _initControls(){
		_initHSplit1();
		_initHSplit1Area0();
		_initHSplit1Area1();
		me.on("activate", me.controller._onactivate);
	}
	
	function _initHSplit1(){
		HSplit1 = new mx.containers.HSplit({
			id:"HSplit1",
			rows:"8%,92%"
		});
		
		me.addControl(HSplit1);
	}
	
	function _initHSplit1Area0(){
		HSplit1Area0 =  new mx.containers.Container({ 
			id:"HSplit1Area0"
		});
		
		HSplit1.addControl(HSplit1Area0, 0);
		ywcjLabel = new mx.controls.Label({text:"业务场景：",width:"80",textAlign:"right"});	//购电方标签
    	HSplit1Area0.addControl(ywcjLabel);	//添加标签
    	
    	ywcjSearch = utils.dropDownEditor.MarketTree.MarketDropDownEditor(true,"",false); //业务场景
    	ywcjSearch.$e.css("width","165px");
    	HSplit1Area0.addControl(ywcjSearch); 
    	HSplit1Area1 =  new mx.containers.Container({ 
			id:"HSplit1Area1"
		});
		HSplit1.addControl(HSplit1Area1, 1);
		HSplit1Area1.$e.get(0).align = "center";//内部居中
    	var queryPieBotton = new mx.controls.Button({ text: "查询",width:80,height:30,left:30,bottom:-5});
    	queryPieBotton.on("click", _initHSplit1Area1);
    	HSplit1Area0.addControl(queryPieBotton); 
	}
	
	function _initHSplit1Area1(){
		var marketId= ywcjSearch.getValue()==null?"":ywcjSearch.getValue();
		var jgParams ={"marketId":marketId};
		var responseContent = restClient.getSync(NetProContConcMess.mappath("~/rest/netProContConcMessr/getChartBar"), { "params": JSON.stringify(jgParams)});
		me.conEnerMess = responseContent.resultValue.items[0];
		if(me.conEnerMess=="0"){
			alert("该业务场景不存在数据");
			me.conEnerMess="";
		}
		var chart = new FusionCharts(fusionChart.mappath("$/swf/MSBar3D.swf"),"myChartId","100%","100%","0","1");//图表的显示
    	chart.setDataXML(me.conEnerMess); 
    	chart.render(HSplit1Area1.$e.get(0));
	}
	
	return me.endOfClass(arguments);
};