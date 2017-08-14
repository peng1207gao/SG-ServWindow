$import("jfcontractaffix.views.JfContractAffixMainViewController");
$import("jfcontractaffix.views.JfContractAffixMainView");
$import("jfcontractaffix.views.JfContractAffixDetailViewController");
$import("jfcontractaffix.views.JfContractAffixDetailView");

mx.weblets.WebletManager.register({
    id: "jfcontractaffix",
    name: "经法合同文本查询",
    requires: ["ETradePublicUtils/validate","ETradePublicUtils/utils"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new jfcontractaffix.views.JfContractAffixMainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});