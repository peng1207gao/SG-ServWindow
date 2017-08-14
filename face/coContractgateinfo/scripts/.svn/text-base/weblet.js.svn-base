$import("coContractgateinfo.views.MainViewController");
$import("coContractgateinfo.views.DetailViewController");
$import("coContractgateinfo.views.DetailViewController1");
$import("coContractgateinfo.views.MainView");
$import("coContractgateinfo.views.DetailView");
$import("coContractgateinfo.views.DetailView1");

mx.weblets.WebletManager.register({
    id: "coContractgateinfo",
    name: "coContractgateinfo",
    requires:["ETradePublicUtils/utils"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new coContractgateinfo.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});