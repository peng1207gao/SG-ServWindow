$import("radarscan.views.MainViewController");
$import("radarscan.views.MainView");

mx.weblets.WebletManager.register({
    id: "radarscan",
    name: "雷达扫描",
    requires:["ETradePublicUtils/utils","ETradePublicUtils/validate","ETradePublicUtils/fusionChart"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new radarscan.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});