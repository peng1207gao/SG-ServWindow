$import("CoContractbaseInfo.views.MainViewController");
$import("CoContractbaseInfo.views.DetailView");
$import("CoContractbaseInfo.views.DetailViewController");
$import("CoContractbaseInfo.views.MainView");

mx.weblets.WebletManager.register({
    id: "CoContractbaseInfo",
    name: "",
    requires:["ETradePublicUtils/utils"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new CoContractbaseInfo.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});