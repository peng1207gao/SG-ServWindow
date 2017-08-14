mx.weblets.WebletManager.register({
    id: "contracthelfquerystatistics",
    name: "Dynamic Bind Model",
    requires: ['ETradePublicUtils/pubblankcognos'],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
    	var client = new mx.rpc.RESTClient();
    	client.get(contracthelfquerystatistics.mappath('~/../ETradePublicUtils/rest/cognosreportconfig/getCognosUrlBak'),function(ip){
    		if(ip){
    			var ip = ip + '/p2pd/servlet/dispatch?b_action=xts.run&m=portal/main.xts&m_redirect_windowmode=0&m_redirect=%2fp2pd%2fservlet%2fdispatch%3fb_action%3dxts.run%26m%3dportal%2flaunch.xts%26ui.gateway%3d%252fp2pd%252fservlet%252fdispatch%26ui.tool%3dAnalysisStudio%26ui.action%3dedit%26launch.openJSStudioInFrame%3dtrue%26ui.object%3d%252fcontent%252ffolder%255b%2540name%253d%2527%25e7%2594%25b5%25e5%258a%259b%25e4%25ba%25a4%25e6%2598%2593%25e6%258a%25a5%25e8%25a1%25a8%25e7%25b3%25bb%25e7%25bb%259f%2527%255d%252ffolder%255b%2540name%253d%25270.%25e5%2585%25ac%25e5%2585%25b1%25e6%258a%25a5%25e8%25a1%25a8%25e7%25b3%25bb%25e7%25bb%259f%2527%255d%252ffolder%255b%2540name%253d%2527report%2527%255d%252ffolder%255b%2540name%253d%252703.%25e5%2590%2588%25e5%2590%258c%25e7%25ae%25a1%25e7%2590%2586%2527%255d%252ffolder%255b%2540name%253d%2527%25e8%2587%25aa%25e5%258a%25a9%25e5%25bc%258f%25e6%258a%25a5%25e8%25a1%25a8%2527%255d%252fanalysis%255b%2540name%253d%2527%25e5%2590%2588%25e5%2590%258c%25e8%2587%25aa%25e5%25ae%259a%25e4%25b9%2589%25e6%259f%25a5%25e8%25af%25a2%25e7%25bb%259f%25e8%25ae%25a1%2527%255d%26ui.drillThroughTargetParameterValues%3d';
    			var mvc = new pubblankcognos.views.MainViewController({
    				reportUrl : ip
    	    	});
    	    	e.context.rootViewPort.setViewController(mvc);
    		}else{
    			mx.indicate('info','获取报表远程链接失败！请查看配置文件');
    		}
    	});
    }
});