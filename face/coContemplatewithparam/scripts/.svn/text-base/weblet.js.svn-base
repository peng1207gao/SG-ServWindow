$import("coContemplatewithparam.views.MainViewController");
$import("coContemplatewithparam.views.DetailView");
$import("coContemplatewithparam.views.DetailViewController");
$import("coContemplatewithparam.views.MainView");
mx.weblets.WebletManager.register({
    id: "coContemplatewithparam",
    name: "coContemplatewithparam",
    requires:["ETradePublicUtils/utils"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new coContemplatewithparam.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});