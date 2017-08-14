$import("CoContractFjAct.views.MainViewController");
$import("CoContractFjAct.views.DetailView");
$import("CoContractFjAct.views.DetailViewController");
$import("CoContractFjAct.views.MainView");


mx.weblets.WebletManager.register({
    id: "CoContractFjAct",
    name: "合同查询",
    requires:["ETradePublicUtils/utils","htadd","htquery","ETradePublicUtils/validate"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new CoContractFjAct.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});