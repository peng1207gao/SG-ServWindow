$import("cotransqtyinfo.views.CoTransQtyInfoMainViewController");
$import("cotransqtyinfo.views.CoTransQtyInfoMainView");
$import("cotransqtyinfo.views.CoTransQtyInfoDetailViewController");
$import("cotransqtyinfo.views.CoTransQtyInfoDetailView");
$import("cotransqtyinfo.views.CoTransqtySlaveInfoDetailView");
$import("cotransqtyinfo.views.CoTransqtySlaveInfoDetailViewController");
$import("cotransqtyinfo.views.CoTransqtySlaveInfoMainView");
$import("cotransqtyinfo.views.CoTransqtySlaveInfoMainViewController");
$import("cotransqtyinfo.views.CoTransqtySlaveInfosDetailView");
$import("cotransqtyinfo.views.CoTransqtySlaveInfosDetailViewController");

mx.weblets.WebletManager.register({
    id: "cotransqtyinfo",
    name: "合同输电信息",
    requires:["ETradePublicUtils/validate","ETradePublicUtils/utils","ETradePublicUtils/fusionChart"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new cotransqtyinfo.views.CoTransQtyInfoMainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});