$ns("fydlgz.views");

$import("mx.containers.HtmlContainer");
$import("mx.datacontrols.DataGrid");
$import("mx.containers.HSplit");
$import("mx.containers.Panel");
$import("mx.containers.Container");
$import("mx.rpc.RESTClient");
$import("mx.controls.Label");

fydlgz.views.MainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    var restClient = new mx.rpc.RESTClient();
    
    var marketId="";//登陆场景
    //网格列表
    var _dataGrid = null;
    //表单窗口对象
    var _detailWin = null;

    base.init = me.init;
    me.init = function()
    {
        base.init();
        init();
        _initControls();
    };
    
    function _initControls()
    {
    	//页面上面分成上下两部分
    	me.mSplit = new mx.containers.HSplit({rows:"180px,auto",borderThick:1});
    	me.addControl(me.mSplit);
    	
    	me.panel1 = new mx.containers.Panel({title:"业务查询", width:"100%", height:117});
    	me.mSplit.addControl(me.panel1,0);//添加panel1
    	me.container1 = new mx.containers.Container({padding:"2px",height:"30",width:"100%"});
    	me.panel1.addControl(me.container1);//添加container1
    	me.container12 = new mx.containers.Container({padding:"2px",height:"30",width:"100%"});
    	me.panel1.addControl(me.container12);//添加container12
    	me.container3 = new mx.containers.Container({padding:"2px",height:"30",width:"100%"});
    	me.panel1.addControl(me.container3);//添加container12
    	
    	//合同类型
    	me.typeLabel =new mx.controls.Label({text: "合同类型：",width:"8%",textAlign:"right",right:4});
    	me.container1.addControl(me.typeLabel);
    	//解决因为内容过长下拉框显示不全的问题
    	var fun = new utils.commonFun.DropDownEditorFun();
    	//合同类型下拉框
    	me.contractType = utils.dropDownEditor.ContractTypeTree.ContractTypeDropDownEditor(false,false);
    	me.contractType.setWidth("15%");
    	me.container1.addControl(me.contractType);
    	me.contractType.on("changed",changeDrops);//给合同类型添加onchanged触发事件
    	me.contractSeq = new mx.controls.Label({text:"合同序列：",width:"8%",textAlign:"right",right:4});	//合同序列标签
    	me.container1.addControl(me.contractSeq);	//添加标签
    	me.component = new mx.editors.DropDownEditor({displayMember:"name",valueMember:"value",autoMatchInput:true,
    		allowEditing:true,width:"15%"}); //合同序列下拉框
		fun.resizeListEditor(me.component);
    	me.container1.addControl(me.component);	//添加下拉框
    	setComponent13Items("");	//给合同序列赋值
    	
    	//发电类型
    	me.fdtypeLabel = mx.controls.Label({text: "发电类型：",width:"8%",textAlign:"right",right:4});
    	me.container1.addControl(me.fdtypeLabel);
    	var restUrl="~/../marketMember/rest/bamarketparticipant/getPowertype";
    	//合同类型下拉框
		me.powertypeDropDownEditor = new mx.editors.DropDownEditor({
			width:"15%",
			allowEditing: false, // 设置是否允许编辑 
			displayCheckBox: false,
			valueSeparator: "#",
			displayMember: "name",
			valueMember: "value",
			url: fydlgz.mappath(restUrl)
		});
		me.container1.addControl(me.powertypeDropDownEditor);
		//调整下拉框长度
		fun.resizeListEditor(me.powertypeDropDownEditor);
		
		//时间类型
    	me.dateLabel = mx.controls.Label({text: "时间类型：",width:"8%",textAlign:"right",right:4});
//    	me.container12.addControl(me.dateLabel);
    	me.component10 = new mx.editors.DropDownEditor({displayMember:"name",valueMember:"value",width:"15%",items:
    					[{name:"合同开始时间",value:0},{name:"合同录入时间",value:1},{name:"合同签订时间",value:2}],
    					value:0});	//时间类型下拉框
//    	me.container12.addControl(me.component10); //添加下拉框
    	me.label11 = new mx.controls.Label({text:"开始时间：",width:"8%",textAlign:"right",right:4});	//开始时间标签
    	me.container12.addControl(me.label11);	//添加标签
    	me.component11 = new mx.editors.DateTimeEditor({width:"15%"}); //开始时间时间框
    	me.container12.addControl(me.component11);	//添加时间框
    	me.label12 = new mx.controls.Label({text:"截止时间：",width:"8%",textAlign:"right",right:4});	//结束时间标签
    	me.container12.addControl(me.label12);	//添加标签
    	me.component12 = new mx.editors.DateTimeEditor({width:"15%"}); //结束时间时间框
    	me.container12.addControl(me.component12);	//添加时间框
    	var date  = new Date()
    	me.component11.setValue(date.getFullYear()+"-01-01");
    	me.component12.setValue(date.getFullYear()+"-12-31");
    	var contractnameLabel = new mx.controls.Label({text:"合同名称：",width:"8%",textAlign:"right",right:4});	//合同名称标签
    	me.container12.addControl(contractnameLabel);	//添加标签
    	me.contractnameText = new mx.editors.TextEditor({width:"15%"});	//合同文本框
    	me.container12.addControl(me.contractnameText); //添加文本框
    	
     	me.label8 = new mx.controls.Label({text:"售电方：",width:"8%",textAlign:"right",right:4});	//售电方标签
    	me.container3.addControl(me.label8);	//添加标签
    	me.component8 = utils.dropDownEditor.CommonDropEditor.gsAutoComPleteEditor("","2"); //售电方下拉框
    	utils.dropDownEditor.CommonDropEditor.autoInit(me.component8,'2');
    	me.component8.setWidth("15%");
    	//解决因为内容过长下拉框显示不全的问题
    	fun.resizeAutocomplete(me.component8);
    	me.container3.addControl(me.component8);	//添加下拉框
    	//setComponent8Items();	//给售电方下拉框赋值
    	me.label7 = new mx.controls.Label({text:"购电方：",width:"8%",textAlign:"right",right:4});	//购电方标签
    	me.container3.addControl(me.label7);	//添加标签
//    	me.component7 = new mx.editors.DropDownEditor({displayMember:"name",valueMember:"value",width:"200"});	//购电方下拉框
//    	me.container3.addControl(me.component7); //添加文本框
//    	setComponent7Items();	//给购电方下拉框赋值
    	me.component7 = utils.dropDownEditor.CommonDropEditor.gsAutoComPleteEditor("","1");	//购电方下拉框
    	utils.dropDownEditor.CommonDropEditor.autoInit(me.component7,'1');
    	me.component7.setWidth("15%");
    	//解决因为内容过长下拉框显示不全的问题
    	fun.resizeAutocomplete(me.component7);
    	me.container3.addControl(me.component7); //添加文本框
    	
    	me.panel2 = new mx.containers.Panel({title:"业务操作", width:"100%", height:55});
    	me.mSplit.addControl(me.panel2,0);//添加panel2
    	me.container2 = new mx.containers.Container({padding:"2px",width:"100%"});
    	me.panel2.addControl(me.container2);//添加container2
    	
    	//查询按钮
		var btn_search = new mx.controls.Button({text : "查询",left : 20});
		me.addControl(btn_search);
		//查询按钮点击事件
		btn_search.on("click", function() {
			btn_searchMethed();
		});
		me.container2.addControl(btn_search);
		
		//导出按钮
		var btn_out = new mx.controls.Button({text : "导出",left : 40});
		me.addControl(btn_out);
		//导出按钮点击事件
		btn_out.on("click", function() {
			alert("导出");
		});
//		me.container2.addControl(btn_out);
    	
		me.panel3 = new mx.containers.Panel({title:"合同数据", width:"100%"});
    	me.mSplit.addControl(me.panel3,1);//添加panel3
//    	me.container3 = new mx.containers.Container({padding:"2px",width:"100%"});
//    	me.panel3.addControl(me.container3);//添加container3
		
	    btn_searchMethed();
        me.on("activate", me.controller._onactivate);
    }
    
    /**
     * 初始化方法(主要获取初始化参数)
     */
    function init(){
    	var items = restClient.getSync(fydlgz.mappath("~/rest/coContractbaseinfoV2Act/init")).resultValue.items;
    	marketId = items[0];
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
    	var items = restClient.getSync(fydlgz.
    			mappath("~/rest/coContractbaseinfoV2Act/getComponent13Items"),
    			{marketId:marketId,contracttype:changecontracttype}).resultValue;	//获取售电方数据
    	me.component.setItems(items);
    }
    function btn_searchMethed()
    {
    	var contractname = me.contractnameText.value;
    	var contractname1="1";
    	var contractname2="1";
    	if(contractname==''||contractname==null||contractname=="null"){
    		contractname="1";
    		contractname1="1";
        	contractname2="1";
//    		contractname="'%%' or co.contractname is null";
    	}else{
    		contractname=contractname;
    		contractname1="co.contractname";
        	contractname2="'"+contractname+"'";
    		
    	}
    	var gdf = me.component7.value;
    	if(gdf==null){
    		gdf="co.purchaser or co.purchaser is null";
    	}else{
    		gdf="'"+gdf+"'";
    	}
    	var sdf = me.component8.value;
    	if(sdf==null){
    		sdf="co.seller or co.seller is null";
    	}else{
    		sdf="'"+sdf+"'";
    	}
    	var contracttype =me.contractType.value;
    	if(contracttype==null){
    		contracttype="co.contracttype or co.contracttype is null";
    	}else{
    		contracttype="'"+contracttype+"'";
    	}
    	var contracttype1 =me.contractType.value;
    	if(contracttype1==null){
    		contracttype1="c.contracttypeid";
    	}else{
    		contracttype1="'"+contracttype1+"'";
    	}
    	var contractSeqence=me.component.value;
    	if(contractSeqence==null){
    		contractSeqence="co.SEQUENCEID or co.SEQUENCEID is null";
    	}else{
    		contractSeqence="'"+contractSeqence+"'";
    	}
    	var Seller=me.powertypeDropDownEditor.value;
    	if(Seller==null){
    		Seller="co.SELLER  or  co.SELLER is null";
    	}else{
    		Seller="'0'";
    	}
    	var fdlx=me.powertypeDropDownEditor.value;
    	if(fdlx==null){
    		fdlx="b.powertype";
    	}else{
    		fdlx="'"+fdlx+"'";
    	}
    	var datetype =0;// me.component10.value;
    	var startDate = me.component11.value;
    	var endDate = me.component12.value;
    	if(startDate!="" && endDate!=""){
    		if(startDate>endDate){
        		mx.indicate("info", "截止时间必须大于开始时间！");
        		return;
        	}
    	}
    	if(datetype==1){
    		if(startDate!=null&&endDate!=null){
    			StartLable ="TO_CHAR(co.UPDATETIME, 'YYYY-MM-DD')";
    			StartTime = "'"+startDate+"'";
    			endLable ="TO_CHAR(co.UPDATETIME, 'YYYY-MM-DD')";
    			endTime = "'"+endDate+"'";
    		}else if(startDate!=null&&endDate==null){
    			StartLable ="TO_CHAR(co.UPDATETIME, 'YYYY-MM-DD')";
    			StartTime = "'"+startDate+"'";
    			endLable ="co.UPDATETIME";
    			endTime = "co.UPDATETIME or co.UPDATETIME is null";
    		}else if(startDate==null&&endDate!=null){
    			StartLable ="co.UPDATETIME";
    			StartTime = "co.UPDATETIME or co.UPDATETIME is null";
    			endLable ="TO_CHAR(co.UPDATETIME, 'YYYY-MM-DD')";
    			endTime = "'"+endDate+"'";
    		}else{
    			StartLable ="co.UPDATETIME";
    			StartTime = "co.UPDATETIME or co.UPDATETIME is null";
    			endLable ="co.UPDATETIME";
    			endTime = "co.UPDATETIME or co.UPDATETIME is null";
    		}
    	}else if(datetype==2){
    		if(startDate!=null&&endDate!=null){
    			StartLable ="TO_CHAR(co.SIGNEDDATE, 'YYYY-MM-DD')";
    			StartTime = "'"+startDate+"'";
    			endLable ="TO_CHAR(co.SIGNEDDATE, 'YYYY-MM-DD')";
    			endTime = "'"+endDate+"'";
    		}else if(startDate!=null&&endDate==null){
    			StartLable ="TO_CHAR(co.SIGNEDDATE, 'YYYY-MM-DD')";
    			StartTime = "'"+startDate+"'";
    			endLable ="co.SIGNEDDATE";
    			endTime = "co.SIGNEDDATE or co.SIGNEDDATE is null";
    		}else if(startDate==null&&endDate!=null){
    			StartLable ="co.SIGNEDDATE";
    			StartTime = "co.SIGNEDDATE or co.SIGNEDDATE is null";
    			endLable ="TO_CHAR(co.SIGNEDDATE, 'YYYY-MM-DD')";
    			endTime = "'"+endDate+"'";
    		}else{
    			StartLable ="co.SIGNEDDATE";
    			StartTime = "co.SIGNEDDATE or co.SIGNEDDATE is null";
    			endLable ="co.SIGNEDDATE";
    			endTime = "co.SIGNEDDATE or co.SIGNEDDATE is null";
    		}
    	}else  if(datetype==3){
    		if(startDate!=null&&endDate!=null){
    			StartLable ="TO_CHAR(co.CONTRACTSTARTDATE, 'YYYY-MM-DD')";
    			StartTime = "'"+startDate+"'";
    			endLable ="TO_CHAR(co.CONTRACTSTARTDATE, 'YYYY-MM-DD')";
    			endTime = "'"+endDate+"'";
    		}else if(startDate!=null&&endDate==null){
    			StartLable ="TO_CHAR(co.CONTRACTSTARTDATE, 'YYYY-MM-DD')";
    			StartTime = "'"+startDate+"'";
    			endLable ="co.CONTRACTSTARTDATE";
    			endTime = "co.CONTRACTSTARTDATE or co.CONTRACTSTARTDATE is null";
    		}else if(startDate==null&&endDate!=null){
    			StartLable ="co.CONTRACTSTARTDATE";
    			StartTime = "co.CONTRACTSTARTDATE or co.CONTRACTSTARTDATE is null";
    			endLable ="TO_CHAR(co.CONTRACTSTARTDATE, 'YYYY-MM-DD')";
    			endTime = "'"+endDate+"'";
    		}else{
    			StartLable ="co.CONTRACTSTARTDATE";
    			StartTime = "co.CONTRACTSTARTDATE or co.CONTRACTSTARTDATE is null";
    			endLable ="co.CONTRACTSTARTDATE";
    			endTime = "co.CONTRACTSTARTDATE or co.CONTRACTSTARTDATE is null";
    		}
    	}else{
    		if(startDate!=null&&endDate!=null){
    			StartLable ="TO_CHAR(co.contractenddate, 'YYYY-MM-DD')";
    			StartTime = "'"+startDate+"'";
    			endLable ="TO_CHAR(co.CONTRACTSTARTDATE, 'YYYY-MM-DD')";
    			endTime = "'"+endDate+"'";
    		}else if(startDate!=null&&endDate==null){
    			StartLable ="TO_CHAR(co.contractenddate, 'YYYY-MM-DD')";
    			StartTime = "'"+startDate+"'";
    			endLable ="co.CONTRACTSTARTDATE";
    			endTime = "co.CONTRACTSTARTDATE or co.CONTRACTSTARTDATE is null";
    		}else if(startDate==null&&endDate!=null){
    			StartLable ="co.contractenddate";
    			StartTime = "co.contractenddate or co.contractenddate is null";
    			endLable ="TO_CHAR(co.CONTRACTSTARTDATE, 'YYYY-MM-DD')";
    			endTime = "'"+endDate+"'";
    		}else{
    			StartLable ="co.CONTRACTSTARTDATE";
    			StartTime = "co.CONTRACTSTARTDATE or co.CONTRACTSTARTDATE is null";
    			endLable ="co.contractenddate";
    			endTime = "co.contractenddate or co.contractenddate is null";
    		}
    	}
    	
    	if(me.templateid==null||me.templateid=='undefined'){
    		var restClient = new mx.rpc.RESTClient();
    		var reportName = {reportName:"COMONTHPOWERTRACK"}
        	me.templateid = restClient.getSync(fydlgz.mappath("~/rest/comonthpowertrack/getReportId"),{"params":JSON.stringify(reportName)});
    	}
    	if(me.htmlContainer!='undefined'&&me.htmlContainer!=null){
    		me.htmlContainer.$e.remove();
		}
    	//报表
		me.htmlContainer = new mx.containers.HtmlContainer({
	    		url : "~/../reportserver/reportviewer.html?templateId="+me.templateid+"&marketId="+marketId+"&marketId1"+marketId+"&contracttype="+contracttype+"&contracttype1="+contracttype1
	    		+"&contractSeqence="+contractSeqence+"&Seller="+Seller+"&fdlx="+fdlx+"&StartLable="+StartLable+"&StartTime="+StartTime+"&endLable="+endLable+"&endTime="+endTime+"&purchaSerh="+gdf
	    		+"&Sellerh="+sdf+"&contractname="+contractname+"&contractname1="+contractname1+"&contractname2="+contractname2,
	   			height : "100%",
	    		width : "100%",
	  			type : "Iframe"
   			});
		me.panel3.addControl(me.htmlContainer);
	}
   
    
    return me.endOfClass(arguments);
};