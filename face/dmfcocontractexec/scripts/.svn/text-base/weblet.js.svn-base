mx.weblets.WebletManager.register({
    id: "dmfcocontractexec",
    name: "Dynamic Bind Model",
    requires:['ETradePublicUtils/pubUtils','ETradePublicUtils/pubreportmoreparamforcognos'],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
    	var client = new mx.rpc.RESTClient();
    	client.get(dmfcocontractexec.mappath('~/../ETradePublicUtils/rest/cognosreportconfig/getCognosUrlBak'),function(ip){
    		if(ip){
    			var ip = ip + '/p2pd/servlet/dispatch?b_action=cognosViewer&ui.action=run&ui.object=%2fcontent%2ffolder%5b%40name%3d%27%e7%94%b5%e5%8a%9b%e4%ba%a4%e6%98%93%e6%8a%a5%e8%a1%a8%e7%b3%bb%e7%bb%9f%27%5d%2ffolder%5b%40name%3d%270.%e5%85%ac%e5%85%b1%e6%8a%a5%e8%a1%a8%e7%b3%bb%e7%bb%9f%27%5d%2ffolder%5b%40name%3d%27report%27%5d%2ffolder%5b%40name%3d%2703.%e5%90%88%e5%90%8c%e7%ae%a1%e7%90%86%27%5d%2ffolder%5b%40name%3d%27%e6%89%a7%e8%a1%8c%e8%b7%9f%e8%b8%aa%27%5d%2freport%5b%40name%3d%27%e6%89%a7%e8%a1%8c%e8%b7%9f%e8%b8%aa-%e5%b9%b4%27%5d&ui.name=%e6%89%a7%e8%a1%8c%e8%b7%9f%e8%b8%aa-%e5%b9%b4&run.outputFormat=&run.prompt=true&cv.toolbar=false&cv.header=false';
    			//procedureParams 报表所需参数列表  使用下面格式传参 
    			var procedurePar = {'2':'ps_batch','3':'pd_startdate','4':'pd_enddate'};
    			var dieparams = {'1':'PRC_DM_F_CO_CONTRACT_EXEC'};
    			var datelabel = new mx.controls.Label({
    	    		text: "时间:",
    	    		width:　"100px",
    	    		textAlign: "center",
    	    		verticalAlign: "middle"
    	    	});
    			// 初始化年份查询条件下拉框
    	    	var dataTimeEditor = new mx.editors.DateTimeEditor({
    	    	    formatString: "yyyy-MM-dd",
    	    	    name:'ksseldate'
    	    	});
    	    	var nowdate = new Date();
    			var preMonth = nowdate.getFullYear();
    	    	dataTimeEditor.setValue(preMonth+"-01-01");
    	    	var labels = [datelabel];
    			var editors = [dataTimeEditor];
    			var mvc = new pubreportmoreparamforcognos.views.publicprcviewsMainViewController({
    				reporturl : ip, procedureName : 'sg_dm.PRC_COMM_ETL_EXEC', procedureParams : procedurePar,dieparams:dieparams,
    				labels : labels,editors : editors,ifYM:false
    	    	});
    	    	e.context.rootViewPort.setViewController(mvc);
    		}else{
    			mx.indicate('info','获取报表远程链接失败！请查看配置文件');
    		}
    	});
    }
});