$import("coTempclienfieldconfig.views.MainViewController");
$import("coTempclienfieldconfig.views.MainView");
$import("coTempclienfieldconfig.views.DetailViewController");
$import("coTempclienfieldconfig.views.DetailView");

mx.weblets.WebletManager.register({
    id: "coTempclienfieldconfig",
    name: "coTempclienfieldconfig",
    requires:["ETradePublicUtils/utils"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new coTempclienfieldconfig.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});