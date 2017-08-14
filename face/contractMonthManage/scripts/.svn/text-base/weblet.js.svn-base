$import("contractMonthManage.views.MainView");
$import("contractMonthManage.views.MainViewController");
$import("contractMonthManage.views.ShowResolveResultView");
$import("contractMonthManage.views.ShowResolveResultViewController");


mx.weblets.WebletManager.register({
    id: "contractMonthManage",
    name: "合同分月管理",
    requires:["ETradePublicUtils/utils","htadd","htquery","coContractbackupInfo","contractrecordinformation","contractVersionManage","jfcontractaffix","jfcontractstate","CoContractFjAct","resolveelectricquantitytounit"],
    
    onload: function(e)
    {
    },
    onstart: function(e)
    {
    	 var mvc = new contractMonthManage.views.MainViewController();
         e.context.rootViewPort.setViewController(mvc);
    }
});