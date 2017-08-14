$import("kqksexetrace.views.KqKsExeTraceMainViewController");
$import("kqksexetrace.views.KqKsExeTraceMainView");
$import("kqksexetrace.views.KqKsExeTraceDetailViewController");
$import("kqksexetrace.views.KqKsExeTraceDetailView");
$import("kqksexetrace.views.KqKsExeTraceSellerView");
$import("kqksexetrace.views.KqKsExeTraceSellerViewController");
$import("kqksexetrace.views.KqKsExeTraceTransView");
$import("kqksexetrace.views.KqKsExeTraceTransViewController");

mx.weblets.WebletManager.register({
    id: "kqksexetrace",
    name: "跨区跨省执行追踪",
    requires: ["ETradePublicUtils/utils","ETradePublicUtils/fusionChart"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new kqksexetrace.views.KqKsExeTraceMainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});