$import("mx.containers.VSplit");
$import("mx.containers.Container");
$import("mx.containers.HSplit");
$import("mx.controls.Label");
$import("mx.datacontrols.DataTree");
$import("mx.datacontainers.TreeEntityContainer");
$import("mx.controls.ContextMenu");
$import("coContractEnergyInfoChart.views.mainView");
$import("coContractEnergyInfoChart.views.mainViewController");
mx.weblets.WebletManager.register(
{
    id: "coContractEnergyInfoChart",
    name: "coContractEnergyInfoChart",

    requires: ["ETradePublicUtils/fusionChart"],

    onload: function (e) {

    }, 
    onstart: function (e) {
        var mvc = new coContractEnergyInfoChart.views.mainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});