$import("mx.containers.HSplit");
$import("mx.containers.Container");
$import("NetProContConcMess.views.mainView");
$import("NetProContConcMess.views.mainViewController");
mx.weblets.WebletManager.register(
{
    id: "NetProContConcMess",
    name: "NetProContConcMess",

    requires: ["ETradePublicUtils/utils","ETradePublicUtils/fusionChart"],

    onload: function (e) {

    }, 
    onstart: function (e) {
        var mvc = new NetProContConcMess.views.mainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});