$import("CoContransEnergyInfo.views.MainViewController");
$import("CoContransEnergyInfo.views.DetailViewController");
$import("CoContransEnergyInfo.views.MainView");
$import("CoContransEnergyInfo.views.DetailView");

mx.weblets.WebletManager.register({
    id: "CoContransEnergyInfo",
    name: "",
    requires:["ETradePublicUtils/utils"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new CoContransEnergyInfo.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});