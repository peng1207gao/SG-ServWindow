$import("CoContranslossinfo.views.MainViewController");
$import("CoContranslossinfo.views.DetailView");
$import("CoContranslossinfo.views.DetailViewController");
$import("CoContranslossinfo.views.MainView");

mx.weblets.WebletManager.register({
    id: "CoContranslossinfo",
    name: "",
    requires:["ETradePublicUtils/utils"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new CoContranslossinfo.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});