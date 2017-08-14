$import("CoContractparamdic.views.MainViewController");

mx.weblets.WebletManager.register({
    id: "CoContractparamdic",
    name: "",
    requires:["ETradePublicUtils/utils"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new CoContractparamdic.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});