$import("jfcontractstate.views.JfContractStateMainViewController");
$import("jfcontractstate.views.JfContractStateMainView");

mx.weblets.WebletManager.register({
    id: "jfcontractstate",
    name: "经法合同状态查询",
    requires: ["ETradePublicUtils/validate","ETradePublicUtils/utils"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new jfcontractstate.views.JfContractStateMainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});