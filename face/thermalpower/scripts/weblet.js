$import("mx.containers.Panel");
$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGrid");
$import("mx.controls.ContextMenu");
$import("mx.datacontrols.PageNaviBar");
$import("thermalpower.views.mainView");
$import("thermalpower.views.mainViewController");
$import("thermalpower.views.DataGrid");
$import("thermalpower.views.DataGridController");

mx.weblets.WebletManager.register(
{
    id: "thermalpower",
    name: "thermalpower",

    requires: ["ETradePublicUtils/fusionChart"],

    onload: function (e) {

    }, 
    onstart: function (e) {
        var mvc = new thermalpower.views.mainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});