$ns("contractcollect.views");

$import("mx.controls.ToolBar");
$import("mx.datacontrols.DataGrid");
$import("mx.containers.HtmlContainer");
$import("mx.containers.TabControl");

contractcollect.views.MainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    me.dataGrid = null;

    base.init = me.init;
    me.init = function()
    {
        base.init();

        _initControls();
    };
    function _initControls()
    {
    	me.tabControl = new mx.containers.TabControl({
		    pages:[
		        { text: "基数合同", name: "jsht" },
		        { text: "外送电合同", name: "wsdht" },
		        { text: "大用户合同", name: "dyhht" },
		        { text: "发电权合同", name: "fdqht" },
		        { text: "抽水招标合同", name: "cszbht" },
		        { text: "跨区跨省交易合同", name: "kqksjyht" }
		    ]
		});
		me.addControl(me.tabControl);
		var ip = "";//cognos服务器地址
		var restClient = new mx.rpc.RESTClient();
		ip = restClient.getSync(contractcollect.mappath('~/../ETradePublicUtils/rest/cognosreportconfig/getCognosUrl'));
//		var reportName = {reportName:"jsht"};
//		var jshtUrl = restClient.getSync(contractcollect.mappath("~/rest/comonthpowertrack/getReportId"),{"params":JSON.stringify(reportName)});
		var jshtUrl = ip + "/p2pd/servlet/dispatch?b_action=cognosViewer&ui.action=run&ui.object=%2fcontent%2ffolder%5b%40name%3d%27%e5%90%88%e5%90%8c%27%5d%2ffolder%5b%40name%3d%27report%27%5d%2freport%5b%40name%3d%27%e5%9f%ba%e6%95%b0%e5%90%88%e5%90%8c%27%5d&ui.name=%e5%9f%ba%e6%95%b0%e5%90%88%e5%90%8c&run.outputFormat=&run.prompt=true";
		me.htmlContainer0 = new mx.containers.HtmlContainer({
    		url:jshtUrl,
           	height:"100%",
           	width:"100%",
           	type:"Iframe"
        });
//        var reportName = {reportName:"wsdht"};
//		var wsdhtUrl = restClient.getSync(contractcollect.mappath("~/rest/comonthpowertrack/getReportId"),{"params":JSON.stringify(reportName)});
		var wsdhtUrl = ip + "/p2pd/servlet/dispatch?b_action=cognosViewer&ui.action=run&ui.object=%2fcontent%2ffolder%5b%40name%3d%27%e5%90%88%e5%90%8c%27%5d%2ffolder%5b%40name%3d%27report%27%5d%2freport%5b%40name%3d%27%e5%a4%96%e9%80%81%e7%94%b5%e5%90%88%e5%90%8c%27%5d&ui.name=%e5%a4%96%e9%80%81%e7%94%b5%e5%90%88%e5%90%8c&run.outputFormat=&run.prompt=true";
		me.htmlContainer1 = new mx.containers.HtmlContainer({
    		url:wsdhtUrl,
           	height:"100%",
           	width:"100%",
           	type:"Iframe"
        });
//        var reportName = {reportName:"dyhht"};
//		var dyhhtUrl = restClient.getSync(contractcollect.mappath("~/rest/comonthpowertrack/getReportId"),{"params":JSON.stringify(reportName)});
		var dyhhtUrl = ip + "/p2pd/servlet/dispatch?b_action=cognosViewer&ui.action=run&ui.object=%2fcontent%2ffolder%5b%40name%3d%27%e5%90%88%e5%90%8c%27%5d%2ffolder%5b%40name%3d%27report%27%5d%2freport%5b%40name%3d%27%e5%a4%a7%e7%94%a8%e6%88%b7%e5%90%88%e5%90%8c%27%5d&ui.name=%e5%a4%a7%e7%94%a8%e6%88%b7%e5%90%88%e5%90%8c&run.outputFormat=&run.prompt=true";
		me.htmlContainer2 = new mx.containers.HtmlContainer({
    		url:dyhhtUrl,
           	height:"100%",
           	width:"100%",
           	type:"Iframe"
        });
//        var reportName = {reportName:"fdqht"};
//		var fdqhtUrl = restClient.getSync(contractcollect.mappath("~/rest/comonthpowertrack/getReportId"),{"params":JSON.stringify(reportName)});
		var fdqhtUrl = ip + "/p2pd/servlet/dispatch?b_action=cognosViewer&ui.action=run&ui.object=%2fcontent%2ffolder%5b%40name%3d%27%e5%90%88%e5%90%8c%27%5d%2ffolder%5b%40name%3d%27report%27%5d%2freport%5b%40name%3d%27%e5%8f%91%e7%94%b5%e6%9d%83%e5%90%88%e5%90%8c%27%5d&ui.name=%e5%8f%91%e7%94%b5%e6%9d%83%e5%90%88%e5%90%8c&run.outputFormat=&run.prompt=true";
		me.htmlContainer3 = new mx.containers.HtmlContainer({
    		url:fdqhtUrl,
           	height:"100%",
           	width:"100%",
           	type:"Iframe"
        });
//        var reportName = {reportName:"cszbht"};
//		var cszbhtUrl = restClient.getSync(contractcollect.mappath("~/rest/comonthpowertrack/getReportId"),{"params":JSON.stringify(reportName)});
		var cszbhtUrl = ip + "/p2pd/servlet/dispatch?b_action=cognosViewer&ui.action=run&ui.object=%2fcontent%2ffolder%5b%40name%3d%27%e5%90%88%e5%90%8c%27%5d%2ffolder%5b%40name%3d%27report%27%5d%2freport%5b%40name%3d%27%e6%8a%bd%e6%b0%b4%e6%8b%9b%e6%a0%87%e5%90%88%e5%90%8c%27%5d&ui.name=%e6%8a%bd%e6%b0%b4%e6%8b%9b%e6%a0%87%e5%90%88%e5%90%8c&run.outputFormat=&run.prompt=true";
		me.htmlContainer4 = new mx.containers.HtmlContainer({
    		url:cszbhtUrl,
           	height:"100%",
           	width:"100%",
           	type:"Iframe"
        });
//        var reportName = {reportName:"kqksjyht"};
//		var kqksjyhtUrl = restClient.getSync(contractcollect.mappath("~/rest/comonthpowertrack/getReportId"),{"params":JSON.stringify(reportName)});
		var kqksjyhtUrl = ip + "/p2pd/servlet/dispatch?b_action=cognosViewer&ui.action=run&ui.object=%2fcontent%2ffolder%5b%40name%3d%27%e5%90%88%e5%90%8c%27%5d%2ffolder%5b%40name%3d%27report%27%5d%2freport%5b%40name%3d%27%e8%b7%a8%e5%8c%ba%e8%b7%a8%e7%9c%81%e4%ba%a4%e6%98%93%e5%90%88%e5%90%8c%27%5d&ui.name=%e8%b7%a8%e5%8c%ba%e8%b7%a8%e7%9c%81%e4%ba%a4%e6%98%93%e5%90%88%e5%90%8c&run.outputFormat=&run.prompt=true";
		me.htmlContainer5 = new mx.containers.HtmlContainer({
    		url:kqksjyhtUrl,
           	height:"100%",
           	width:"100%",
           	type:"Iframe"
        });
        me.tabControl.pages[0].addControl(me.htmlContainer0);
        me.tabControl.pages[1].init();
        me.tabControl.pages[1].addControl(me.htmlContainer1);
        me.tabControl.pages[2].init();
        me.tabControl.pages[2].addControl(me.htmlContainer2);
        me.tabControl.pages[3].init();
        me.tabControl.pages[3].addControl(me.htmlContainer3);
        me.tabControl.pages[4].init();
        me.tabControl.pages[4].addControl(me.htmlContainer4);
        me.tabControl.pages[5].init();
        me.tabControl.pages[5].addControl(me.htmlContainer5);
        
    }
    
    return me.endOfClass(arguments);
};