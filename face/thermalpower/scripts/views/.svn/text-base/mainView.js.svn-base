$ns("thermalpower.views");

$import("fusionChart.lib.FusionCharts");

thermalpower.views.mainView=function(){
	var me = $extend(mx.views.View);
	
	var base = {};
	base.init = me.init;
	var tenNum="123123";
	var restClient = new mx.rpc.RESTClient();
	
	me.init = function () {
		base.init();
		me.setHeight("100%");
		me.setWidth("100%");
		me.permissionID = "-1";
		_initControls();
	};
	
	var Panel1 = null;
	var Panel2 = null;
	var DataGrid1 = null;
	var Panel10 = null;
	var Panel9 = null;
	var DataGrid5 = null;
	var Panel14 = null;
	var Panel13 = null;
	var DataGrid7 = null;
	function _changed(){
		_initAverageChart(me.checkListEditor.value);
	}
	function _initControls(){
		//第一部分图表
		_initPanel1();
		_initPanel2();
		_initDataGrid1();
		
		//第二部分图表
		_initPanel10();
		_initPanel9();
		_initDataGrid5();
		
		//第三部分图表
		_initPanel13();
		_initPanel14();
//		_initAverageChart();
		_initDataGrid7();
		me.on("activate", me.controller._onactivate);
	}
	
	me.call = function(){
		_initTopTenChart();
		_initLastTenChart();
		_initAverageChart("topTen");
	}
	
	function _initPanel1(){
		Panel1 = new mx.containers.Panel({
			id:"Panel1",
			border:"1",
			height:"500",
			title:"火电统调电厂完成率前十名"
		});
		
		me.addControl(Panel1);
	}
	
	function _initPanel2(){
		Panel2 = new mx.containers.Panel({
			id:"Panel2",
			border:"1",
			height:"60%",
			displayHead:false
			});
		Panel1.addControl(Panel2);
	}
	
	function _initAverageChart(toporlast){
		var responseContent = restClient.getSync(thermalpower.mappath("~/rest/setielineresultn/getAverageDataChart"), { "params": JSON.stringify({"toporlast":toporlast})});
		if(responseContent.successful == true){
			me.averageData = responseContent.resultValue.items[0];
			if(me.averageData=="0"){
				mx.indicate("info","该业务场景不存在火电统调电厂完成率与平均进度偏差数据");
				me.averageData="";
			}
			var chart = new FusionCharts(fusionChart.mappath("$/swf/StackedBar2D.swf"),"myChartId","100%","100%","0","1");//图表的显示
	    	chart.setDataXML(me.averageData); 
	    	Panel13.addControl(chart);
	    	chart.render(Panel13.$e.get(0));
		}
	}
	
	function _initTopTenChart(){
		var responseContent = restClient.getSync(thermalpower.mappath("~/rest/setielineresultn/getTopTenChart"));
		if(responseContent.successful == true){
			me.topTenData = responseContent.resultValue.items[0];
			if(me.topTenData=="0"){
				mx.indicate("info","该业务场景不存在火电统调电厂完成率前十名数据");
				me.topTenData="";
			}
			var chart = new FusionCharts(fusionChart.mappath("$/swf/StackedBar2D.swf"),"myChartId","100%","100%","0","1");//图表的显示
	    	chart.setDataXML(me.topTenData); 
	    	Panel2.addControl(chart);
	    	chart.render(Panel2.$e.get(0));
		}
	}
	
	function _initLastTenChart(){
		var responseContent = restClient.getSync(thermalpower.mappath("~/rest/setielineresultn/getLastTenChart"));
		if(responseContent.successful == true){
			me.lastTenData = responseContent.resultValue.items[0];
			if(me.lastTenData=="0"){
				mx.indicate("info","该业务场景不存在火电统调电厂完成率后十名数据");
				me.lastTenData="";
			}
			var chart = new FusionCharts(fusionChart.mappath("$/swf/StackedBar2D.swf"),"myChartId","100%","100%","0","1");//图表的显示
	    	chart.setDataXML(me.lastTenData);
	    	Panel9.addControl(chart);
	    	chart.render(Panel9.$e.get(0));
		}
	}
	
	function _initDataGrid1(){
		var DataGridController = new thermalpower.views.DataGridController();
		DataGrid1 = DataGridController.getView().getTopTenDataGrid();
		DataGrid1.load();
		Panel1.addControl(DataGrid1);
	}
	
	function _initPanel10(){
		Panel10 = new mx.containers.Panel({
			id:"Panel10",
			border:"1",
			height:"500",
			title:"火电统调电厂完成率后十名"
		});
		me.addControl(Panel10);
	}
	
	function _initPanel9(){
		Panel9 = new mx.containers.Panel({
			id:"Panel9",
			border:"1",
			height:"60%",
			displayHead:false
		});
		
		Panel10.addControl(Panel9);
	}
	
	function _initDataGrid5(){
		var DataGridController = new thermalpower.views.DataGridController();
		var lastTenDataGrid = DataGridController.getView().getLastTenDataGrid();
		lastTenDataGrid.load();
		Panel10.addControl(lastTenDataGrid);
	}
	
	
	function _initPanel14(){
		Panel14 = new mx.containers.Panel({
			id:"Panel14",
			border:"1",
			height:"500",
			title:"火电统调电厂完成率与平均进度偏差"
		});
		
		me.checkListEditor = new mx.editors.CheckListEditor(
		{
		    type:"radio",
		    items:[
		        {text: "前十名", value: "topTen", checked:true},
		        {text: "后十名", value: "lastTen"}],
		    onitemchanged:_changed
		});

		Panel14.addControl(me.checkListEditor);
		Panel14.addControl(Panel13);
		me.addControl(Panel14);
	}
	
	function _initPanel13(){
		Panel13 = new mx.containers.Panel({
			id:"Panel13",
			border:"1",
			height:"60%",
			displayHead:false
		});
	}
	
	function _initDataGrid7(){
		var DataGridController = new thermalpower.views.DataGridController();
		var averageDataGrid = DataGridController.getView().getAverageDataGrid();
		averageDataGrid.load();
		Panel14.addControl(averageDataGrid);
	}
	
	return me.endOfClass(arguments);
};