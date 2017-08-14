$import("cocontractaccessory.views.MainViewController");
$import("cocontractaccessory.views.AddListView");
$import("cocontractaccessory.views.AddListViewController");
$import("cocontractaccessory.views.MainView");
mx.weblets.WebletManager.register({
    id: "cocontractaccessory",
    name: "",
    requires:["ETradePublicUtils/utils"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new cocontractaccessory.views.MainViewController();
        
        //模拟测试
        mvc.contractId = "8a8192163eca7b00013ecb148dc9007e";
        e.context.rootViewPort.setViewController(mvc);
    }
});