$import("cocontractbaseInfoV2.views.MainViewController");
$import("cocontractbaseInfoV2.views.DetailView");
$import("cocontractbaseInfoV2.views.DetailViewController");
$import("cocontractbaseInfoV2.views.MainView");

mx.weblets.WebletManager.register({
    id: "cocontractbaseInfoV2",
    name: "",
    requires:["ETradePublicUtils/fusionChart","ETradePublicUtils/utils"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new cocontractbaseInfoV2.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});