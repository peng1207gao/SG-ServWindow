mx.weblets.WebletManager.register({
    id: "dmfcoContractbaseinfo",
    name: "Dynamic Bind Model",
    requires:['ETradePublicUtils/pubreportmoreparamforcognos'],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
    	var client = new mx.rpc.RESTClient();
    	client.get(dmfcoContractbaseinfo.mappath('~/../ETradePublicUtils/rest/cognosreportconfig/getCognosUrlBak'),function(ip){
    		if(ip){
    			var ip = ip + '/p2pd/servlet/dispatch?b_action=cognosViewer&ui.action=run&ui.object=%2fcontent%2ffolder%5b%40name%3d%27%e7%94%b5%e5%8a%9b%e4%ba%a4%e6%98%93%e6%8a%a5%e8%a1%a8%e7%b3%bb%e7%bb%9f%27%5d%2ffolder%5b%40name%3d%270.%e5%85%ac%e5%85%b1%e6%8a%a5%e8%a1%a8%e7%b3%bb%e7%bb%9f%27%5d%2ffolder%5b%40name%3d%27report%27%5d%2ffolder%5b%40name%3d%2703.%e5%90%88%e5%90%8c%e7%ae%a1%e7%90%86%27%5d%2ffolder%5b%40name%3d%27%e5%90%88%e5%90%8c%e5%88%86%e6%9e%90%27%5d%2freport%5b%40name%3d%27%e5%90%88%e5%90%8c%e5%88%86%e6%9e%90%e6%80%bb%e8%a7%88%27%5d&ui.name=%e5%90%88%e5%90%8c%e5%88%86%e6%9e%90%e6%80%bb%e8%a7%88&run.outputFormat=&run.prompt=true&cv.toolbar=false&cv.header=false';
    			//procedureParams 报表所需参数列表  使用下面格式传参 
    			var procedurePar = {'2':'ps_batch','3':'pd_startdate','4':'pd_enddate'};
    			var dieparams = {'1':'PRC_DM_F_CO_CONTRACTBASEINFO'};
    			//所属区域
//    			var cjlable = new mx.controls.Label({
//    	    		text: "开始时间:",
//    	    		width:　"100px",
//    	    		textAlign: "center",
//    	    		verticalAlign: "middle"
//    	    	});
//    			// 初始化年份查询条件下拉框
//    	    	var dataTimeEditor = new mx.editors.DateTimeEditor({
//    	    	    formatString: "yyyy-MM-dd",
//    	    	    name:"p_startdate"
//    	    	});
//    	    	var cjlabled = new mx.controls.Label({
//    	    		text: "结束时间:",
//    	    		width:　"100px",
//    	    		textAlign: "center",
//    	    		verticalAlign: "middle"
//    	    	});
//    			// 初始化年份查询条件下拉框
//    	    	var dataTimeEditord = new mx.editors.DateTimeEditor({
//    	    	    formatString: "yyyy-MM-dd",
//    	    		name:"p_enddate"
//    	    	});
//    			var cjdropDownEditor = new mx.editors.DropDownTreeEditor({
//    				allowEditing : false, // 设置是否允许编辑
//    				displayCheckBox : false,
//    				width:　"150px",
//    				valueSeparator : "#",
//    				displayMember : "name",
//    				valueMember : "value",
//    				name:"p_marketid",
//    				url : utils.mappath("~/rest/marketTreeForRegister/tree")
//    			});
//    			var labels = [cjlable,cjlabled];
//    			var editors = [dataTimeEditor,dataTimeEditord];
    			var mvc = new pubreportmoreparamforcognos.views.publicprcviewsMainViewController({
    				reporturl : ip, procedureName : 'sg_dm.PRC_COMM_ETL_EXEC', procedureParams : procedurePar,dieparams:dieparams//,
//    				labels : labels,editors : editors,ifYM:false
    	    	});
    	    	e.context.rootViewPort.setViewController(mvc);
    		}else{
    			mx.indicate('info','获取报表远程链接失败！请查看配置文件');
    		}
    	});
    }
});