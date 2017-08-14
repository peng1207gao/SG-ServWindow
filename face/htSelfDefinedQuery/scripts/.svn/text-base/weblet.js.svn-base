$import("htSelfDefinedQuery.views.MainViewController");
$import("htSelfDefinedQuery.views.MainView");
$import("htSelfDefinedQuery.views.DetailViewController");
$import("htSelfDefinedQuery.views.DetailView");

mx.weblets.WebletManager.register({
    id: "htSelfDefinedQuery",
    name: "合同自定义查询",
    requires:["ETradePublicUtils/utils","htadd","htquery"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new htSelfDefinedQuery.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});