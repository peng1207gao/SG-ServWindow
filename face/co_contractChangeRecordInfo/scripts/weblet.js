$import("co_contractChangeRecordInfo.views.MainViewController");
$import("co_contractChangeRecordInfo.views.DetailView");
$import("co_contractChangeRecordInfo.views.DetailViewController");
$import("co_contractChangeRecordInfo.views.MainView");
mx.weblets.WebletManager.register({
    id: "co_contractChangeRecordInfo",
    name: "co_contractChangeRecordInfo",
    requires:["ETradePublicUtils/utils"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new co_contractChangeRecordInfo.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});