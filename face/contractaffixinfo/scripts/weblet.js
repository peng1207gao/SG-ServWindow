$import("contractaffixinfo.views.ContractAffixInfoMainViewController");
$import("contractaffixinfo.views.ContractAffixInfoDetailViewController");
$import("contractaffixinfo.views.ContractAffixInfoMainView");
$import("contractaffixinfo.views.ContractAffixInfoDetailView");

mx.weblets.WebletManager.register({
    id: "contractaffixinfo",
    name: "合同文本信息",
    requires:["ETradePublicUtils/validate","CoContractFjAct","ETradePublicUtils/utils"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new contractaffixinfo.views.ContractAffixInfoMainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});