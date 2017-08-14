$import("contResoByMonth.views.MainView");
$import("contResoByMonth.views.MainViewController");
$import("contResoByMonth.views.contResoMonViewController");
$import("contResoByMonth.views.contResoMonView");
$import("contResoByMonth.views.mulContResoMonViewController");
$import("contResoByMonth.views.mulContResoMonView");

mx.weblets.WebletManager.register(
{
    id: "contResoByMonth",
    name: "contResoByMonth",

    requires: ["ETradePublicUtils/utils"],

    onload: function (e) {

    }, 
    onstart: function (e) {
        var mvc = new contResoByMonth.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});

