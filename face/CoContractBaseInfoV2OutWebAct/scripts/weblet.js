$import("CoContractBaseInfoV2OutWebAct.views.MainViewController");
$import("CoContractBaseInfoV2OutWebAct.views.MainView");
mx.weblets.WebletManager.register({
    id: "CoContractBaseInfoV2OutWebAct",
    name: "合同管理",
    requires:["ETradePublicUtils/utils","htadd","coContractbackupInfo"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new CoContractBaseInfoV2OutWebAct.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});