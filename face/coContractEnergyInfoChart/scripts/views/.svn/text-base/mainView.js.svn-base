$ns("coContractEnergyInfoChart.views");

$import("mx.datacontainers.TreeEntityContainer");
$import("mx.datacontrols.DataTree");
$import("mx.rpc.RESTClient");

coContractEnergyInfoChart.views.mainView=function(){
	var me = $extend(mx.views.View);
	
	var base = {};
	base.init = me.init;
	var restClient = new mx.rpc.RESTClient();
	me.init = function () {
		base.init();
		me.setHeight("410");
		me.setWidth("1002");
		me.permissionID = "-1";
		_initControls();
	};
	
	var VSplit4 = null;
	var VSplit4Area0 = null;
	var HSplit4 = null;
	var HSplit4Area0 = null;
	var Label3 = null;
	var HSplit4Area1 = null;
	var DataTree2 = null;
	var VSplit4Area1 = null;
	var HSplit5 = null;
	var HSplit5Area0 = null;
	var HSplit5Area1 = null;
	var HSplit6 = null;
	var HSplit6Area0 = null;
	var HSplit6Area1 = null;
	var tenLabel=null;
	var rjdlLabel=null;
	var zdlLabel=null;
	me.jgDataXml=null;
	var contractid="8a8192163eca7b00013ecb007c6c0065";
	function _initControls(){
		_initVSplit4();
		_initVSplit4Area0();
		_initHSplit4();
		_initHSplit4Area0();
		_initLabel3();
		_initHSplit4Area1();
		_initDataTree2();
		_initVSplit4Area1();
		_initHSplit5();
		_initHSplit5Area0();
		_initHSplit5Area1();
		_initHSplit6();
		_initHSplit6Area0();
		_initHSplit6Area1();
		//DataTree2.selectFirstNode();
		me.on("activate", me.controller._onactivate);
	}
	
	//初始化页面 左右 两侧 A B
	function _initVSplit4(){
		VSplit4 = new mx.containers.VSplit({
			id:"VSplit4",
			cols:"30%,70%",
			borderThick : 1
		});
		me.addControl(VSplit4);
	}

	//A页面 增加容器
	function _initVSplit4Area0(){
		VSplit4Area0 =  new mx.containers.Container({ 
			id:"VSplit4Area0"
		});
		
		VSplit4.addControl(VSplit4Area0, 0);
	}
	
	//A 页面  初始化 上下 两侧  A1  A2
	function _initHSplit4(){
		HSplit4 = new mx.containers.HSplit({
			id:"HSplit4",
			rows:"5%,95%",
			borderThick : 0
		});
		
		VSplit4Area0.addControl(HSplit4);
	}
	
	//A1放容器
	function _initHSplit4Area0(){
		HSplit4Area0 =  new mx.containers.Container({ 
			id:"HSplit4Area0"
		});
		
		HSplit4.addControl(HSplit4Area0, 0);
	}
	
	
	function _initLabel3(){
		Label3 = new mx.controls.Label({
			id:"Label3",
			height:"100%",
			width:"120px",
			text:"时间段信息:",
			css:{"display": "block","font-size": "14px","font-style": "normal","font-variant": "normal","font-weight":"bold"}
		});
		HSplit4Area0.addControl(Label3);
	}
	
	//A2加入容器
	function _initHSplit4Area1(){
		HSplit4Area1 =  new mx.containers.Container({ 
			id:"HSplit4Area1"
		});
		
		HSplit4.addControl(HSplit4Area1, 1);
		//将data2 加入容器
		HSplit4Area1.addControl(DataTree2);
	}
	
	function _initDataTree2(){
		 var treeEntityContainer = new mx.datacontainers.TreeEntityContainer({
		    	baseUrl : "~/contract/rest/coCoEnInChart/getCOContractEnergyTree?contractid="+contractid // rest 服务地址规约详见接口设计文档
			});
		 DataTree2 = new mx.datacontrols.DataTree({
				entityContainer : treeEntityContainer,
				displayCheckBox: false, // 是否需要在树中显示选中框 
				border:0,
				autoSelect:false,
				onnodeclick: onselectionchanged 
			});
		 DataTree2.load();
		 HSplit4Area1.addControl(DataTree2);
	}
	
	
	function onselectionchanged(e)
	{
		var tenStr=e.node.id.split("&&");
		tenStr=tenStr[0]+"至"+tenStr[1];
		tenLabel.setText("电力曲线:"+tenStr);
//		if(e.node.hasChildren){//true 父节点
//			
//		}else{ //子节点
			inideRjzdlAndZdl(e);
			_getChartData(e);
			renderChart();
//		F}
	}
	
	//B加入容器
	function _initVSplit4Area1(){
		VSplit4Area1 =  new mx.containers.Container({ 
			id:"VSplit4Area1"
		});
		
		VSplit4.addControl(VSplit4Area1, 1);
	}
	
	//B加入容器
	function _initHSplit5(){
		HSplit5 = new mx.containers.HSplit({
			id:"HSplit5",
			rows:"8%,92%",
			borderThick : 0
		});
		
		VSplit4Area1.addControl(HSplit5);
	}
	
	//B1
	function _initHSplit5Area0(){
		HSplit5Area0 =  new mx.containers.Container({ 
			id:"HSplit5Area0"
		});
		
		HSplit5.addControl(HSplit5Area0, 0);
		 tenLabel = new mx.controls.Label({
			id:"Label3",
			height:"30",
			width:"270px",
			text:"电力曲线:",
			css:{"display": "block","font-size": "14px","font-style": "normal","font-variant": "normal","font-weight":"bold"}
		});
		HSplit5Area0.addControl(tenLabel);
	}
	
	//B2加入容器
	function _initHSplit5Area1(){
		HSplit5Area1 =  new mx.containers.Container({ 
			id:"HSplit5Area1"
		});
		
		HSplit5.addControl(HSplit5Area1, 1);
	}
	
	//B2 分成chart  和日均电量 和总电量
	function _initHSplit6(){
		HSplit6 = new mx.containers.HSplit({
			id:"HSplit6",
			rows:"80%,20%",
			borderThick : 0
		});
		
		HSplit5Area1.addControl(HSplit6);
	}
	
	//char 容器
	function _initHSplit6Area0(){
		HSplit6Area0 =  new mx.containers.Container({ 
			id:"HSplit6Area0"
		});
		
		HSplit6.addControl(HSplit6Area0, 0);
	}
	
	//日均电量 总电量
	function _initHSplit6Area1(){
		HSplit6Area1 =  new mx.containers.Container({ 
			height:"30",
			id:"HSplit6Area1",
			width : "100%",
			height : "100%",
			top : 0,
			border : "0px solid #109790"
		});
		
		HSplit6.addControl(HSplit6Area1, 1);
		
		rjdlLabel = new mx.controls.Label({
				id:"Label3",
				height:"30",
				width:"170px",
				text:"日均电量：",
				left:"80px",
				css:{"font-size": "14px","font-style": "normal","font-variant": "normal","font-weight":"bold"}
		});
		HSplit6Area1.addControl(rjdlLabel);
	
		zdlLabel = new mx.controls.Label({
		id:"Label3",
		height:"30",
		width:"170px",
		text:"总电量:",
		left:"300px",
		css:{"font-size": "14px","font-style": "normal","font-variant": "normal","font-weight":"bold"}
		});
		HSplit6Area1.addControl(zdlLabel);
	}
	
	function _getChartData(e) {
		var tenStr=e.node.id.split("&&");
		var startDate = tenStr[0];
		var endDate = tenStr[1];
		var jgParams = {
			"contractId":contractid,
			"startDate" : startDate,
			"endDate" : endDate
		};
		var responseContent = restClient.getSync(coContractEnergyInfoChart
						.mappath("~/rest/coCoEnInChart/getChartData"), {
					"params" : JSON.stringify(jgParams)
				});
		me.jgDataXml = responseContent.resultValue.items[0];
	}
	function renderChart(){
		var qsChart = new FusionCharts(fusionChart.mappath("$/swf/MSLine.swf?ChartNoDataText=无相关数据"),
				"qsChartId", "100%", "100%", "0", "1");// 图表的显示
		qsChart.setDataXML(me.jgDataXml);
		HSplit6Area0.$e.get(0).align = "center";// 内部居中
		qsChart.render(HSplit6Area0.$e.get(0));
	}
	//初始化日均总电量
	function inideRjzdlAndZdl(e){
		var tenarr=e.node.id.split("&&");
		var str="";
//		if(e.node.haschildren){
//			
//		}else{
			str='contractId='+contractid+'&startdate='+tenarr[0]+'&enddate='+tenarr[1];//传递参数
//		}
		var items = restClient.getSync(coContractEnergyInfoChart.mappath("~/rest/coCoEnInChart/queryRjAndZdl?"+str)).resultValue.items;	
		if(items[0]=="error"){
			mx.indicate("info", "查询日均电量,总电量 信息格式错误！");
//			alert("查询日均电量,总电量 信息格式错误");
			return;
		}else{
			rjdlLabel.setText("日均电量："+items[0]);
			zdlLabel.setText("总电量："+items[0]);
		}
	}
	
	function initDataTree2RootNodes() {
		var _DataTree2_RootNodes = {nodes:[
		]};
		
		DataTree2.load(_DataTree2_RootNodes);
	}
	
	function _view_DataTree2_onexpanding(args) {
		var node = args.node;
		var params = null;
		node.queryParams = params;
	};
	
	var _view_DataTree2_onmenushowing = function(p_args){
		var node = p_args.node;
		
	};
	
	/**
	 * 对于自嵌套实体节点，获取该类节点第一层过滤条件
	 * @param filter 对应于设计时属性selfLoopTopFilter的值
	 * @param itemtype 当前节点的类型标示
	 * @param pnode 当前节点的父节点
	 * @param hasPropGroup 该实体节点是否有属性分组
	 */
	function _resolveLoopEntityNodeTopFilter(filter, itemtype, pnode, hasPropGroup) {
		if(filter == "") return "";
		if(hasPropGroup) filter = filter + " and ";
		
		var _node = _getParentInstantTreeNode(pnode);
		if(_node == null) return filter;
		if(_node.itemType == itemtype) {
			return "";
		}
		return filter;
	}
	
	/**
	 * 获取当前节点所属的实体节点(entityNode)或枚举节点(enumNode)，
	 * 如果当前节点是实体节点或枚举节点，则直接返回。
	 * @param node 当前节点
	 */
	function _getParentInstantTreeNode(node) {
		if(node == null) return "";
		if(node.displayMode == "entityNode" || node.displayMode == "enumNode") return node;
		
		while(node && node.getParentNode()) {
			if(node.getParentNode().displayMode == "entityNode" 
					|| node.getParentNode().displayMode == "enumNode") {
				return node.getParentNode();
			}
			node = node.getParentNode();
		}
		return null;
	}
	
	/**
	 * 获取当前节点所属的实体节点(entityNode)或枚举节点(enumNode)的id值
	 * @param node 当前节点
	 */
	function _getParentInstantTreeNodeID(node) {
		var pnode = _getParentInstantTreeNode(node);
		if(pnode == null) return "";
		else return pnode.id;
	}
	
	/**
	 * 获取与上级节点相关联的实体属性
	 * @param foreignKeyProp 对应设计时属性foreignKeyProp
	 * @param selfLoopProp  对应设计时属性selfLoopProp
	 * @param itemtype 当前节点的类型标示
	 * @param pnode 当前节点的父节点
	 */
	function _resolveforeignKeyProp(foreignKeyProp, selfLoopProp, itemtype, pnode) {
		var _node = _getParentInstantTreeNode(pnode);
		if(_node == null) return foreignKeyProp;
		if(_node.itemType == itemtype) {
			return selfLoopProp;
		}
		return foreignKeyProp;
	}
	
	return me.endOfClass(arguments);
};