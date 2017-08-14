$import("tempfieldconfig.views.TempfieldConfigMainViewController");
$import("tempfieldconfig.views.TempfieldConfigMainView");
$import("tempfieldconfig.views.TempfieldConfigDetailViewController");
$import("tempfieldconfig.views.TempfieldConfigDetailView");

mx.weblets.WebletManager.register({
    id: "tempfieldconfig",
    name: "用户可配置合同字段",
    requires: ["ETradePublicUtils/utils","ETradePublicUtils/validate"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new tempfieldconfig.views.TempfieldConfigMainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});