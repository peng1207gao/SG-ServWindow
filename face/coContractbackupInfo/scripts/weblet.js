$import("coContractbackupInfo.views.MainViewController");
$import("coContractbackupInfo.views.DetailView");
$import("coContractbackupInfo.views.DetailViewController");
$import("coContractbackupInfo.views.MainView1");
$import("coContractbackupInfo.views.SdfAddView");
$import("coContractbackupInfo.views.SdfMainView");


mx.weblets.WebletManager.register({
    id: "coContractbackupInfo",
    name: "",
    requires:["ETradePublicUtils/utils"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new coContractbackupInfo.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});