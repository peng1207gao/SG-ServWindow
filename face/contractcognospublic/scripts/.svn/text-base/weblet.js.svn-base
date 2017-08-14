$import("contractcognospublic.views.publicprcviewsMainViewController");
$import("contractcognospublic.views.publicprcviewsMainView");
mx.weblets.WebletManager.register({
    id: "contractcognospublic",
    name: "Dynamic Bind Model",
    requires: ["ETradePublicUtils/utils","pubUtils"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
    	var mvc = new contractcognospublic.views.publicprcviewsMainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});