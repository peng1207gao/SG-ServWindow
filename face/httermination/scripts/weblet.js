$import("httermination.views.MainViewController");
$import("httermination.views.MainView");

mx.weblets.WebletManager.register({
    id: "httermination",
    name: "合同终止",
    requires:["ETradePublicUtils/utils","htadd","htquery"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new httermination.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});