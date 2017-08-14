$import("CoContransEnergyInfoV2.views.MainViewController");
$import("CoContransEnergyInfoV2.views.DetailViewController");
$import("CoContransEnergyInfoV2.views.MainView");
$import("CoContransEnergyInfoV2.views.DetailView");

mx.weblets.WebletManager.register({
    id: "CoContransEnergyInfoV2",
    name: "",
    requires:["ETradePublicUtils/validate","ETradePublicUtils/utils"],
    onload: function(e)
    {
    	$import("validate.views.ValidateMainViewController");
    },
    onstart: function(e)
    {
        var mvc = new CoContransEnergyInfoV2.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});