$import("coContractbaseinfoV2Act.views.MainViewController");
$import("coContractbaseinfoV2Act.views.DetailView");
$import("coContractbaseinfoV2Act.views.DetailViewController");
$import("coContractbaseinfoV2Act.views.MainView");
$import("coContractbaseinfoV2Act.views.copybullMainViewController");
$import("coContractbaseinfoV2Act.views.copybullMainView");
mx.weblets.WebletManager.register({
    id: "coContractbaseinfoV2Act",
    name: "合同管理",
    requires:["ETradePublicUtils/utils","htadd","htquery","coContractbackupInfo","contractrecordinformation","contractVersionManage","jfcontractaffix","jfcontractstate","CoContractFjAct"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new coContractbaseinfoV2Act.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});