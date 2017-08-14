$import("contractExeTra.views.MainViewController");
$import("contractExeTra.views.MainView");
mx.weblets.WebletManager.register({
    id: "contractExeTra",
    name: "合同执行追踪",
    requires:["ETradePublicUtils/utils","htadd","ETradePublicUtils/fusionChart"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new contractExeTra.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});