$import("fydlgz.views.MainViewController");
$import("fydlgz.views.MainView");

mx.weblets.WebletManager.register({
    id: "fydlgz",
    name: "分月电量跟踪",
    requires: ["ETradePublicUtils/utils"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new fydlgz.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});