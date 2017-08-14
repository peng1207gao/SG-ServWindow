$import("cocontractparameter.views.MainViewController");
$import("cocontractparameter.views.MainView");
$import("cocontractparameter.views.DetailView");
$import("cocontractparameter.views.DetailViewController");
$import("cocontractparameter.views.ConfigModuleEditerView");
$import("cocontractparameter.views.ConfigModuleEditerViewController");


mx.weblets.WebletManager.register({
    id: "cocontractparameter",
    name: "",
    requires:["ETradePublicUtils/utils"],
    
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new cocontractparameter.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});