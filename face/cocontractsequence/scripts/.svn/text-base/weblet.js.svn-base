$import("cocontractsequence.views.MainViewController");
$import("cocontractsequence.views.MainView");
$import("cocontractsequence.views.DetailView");
$import("cocontractsequence.views.DetailViewController");

mx.weblets.WebletManager.register({
    id: "cocontractsequence",
    name: "",
    requires: ['ETradePublicUtils/utils',"ETradePublicUtils/validate"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new cocontractsequence.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});