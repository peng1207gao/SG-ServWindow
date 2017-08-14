$import("mx.containers.HSplit");
$import("mx.containers.Container");
$import("conEnerMessByType.views.mainView");
$import("conEnerMessByType.views.mainViewController");
mx.weblets.WebletManager.register(
{
    id: "conEnerMessByType",
    name: "conEnerMessByType",
    requires: ["ETradePublicUtils/utils","ETradePublicUtils/fusionChart"],

    onload: function (e) {

    }, 
    onstart: function (e) {
        var mvc = new conEnerMessByType.views.mainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});