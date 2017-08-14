$import("htquery.views.MainViewController");
$import("htquery.views.MainView");
$import("htquery.views.DetailViewController");
$import("htquery.views.DetailView");

mx.weblets.WebletManager.register({
    id: "htquery",
    name: "合同查询",
    requires:["ETradePublicUtils/utils","htadd"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new htquery.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});