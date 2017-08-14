$import("COContractEnergyInfo.views.MainViewController");
$import("COContractEnergyInfo.views.DetailView");
$import("COContractEnergyInfo.views.DetailViewController");
$import("COContractEnergyInfo.views.MainView");


mx.weblets.WebletManager.register({
    id: "COContractEnergyInfo",
    name: "COContractEnergyInfo",
    requires:["ETradePublicUtils/validate","ETradePublicUtils/utils"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new COContractEnergyInfo.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});