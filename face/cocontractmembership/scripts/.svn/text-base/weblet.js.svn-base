$import("cocontractmembership.views.MainViewController");
$import("cocontractmembership.views.MainView");
$import("cocontractmembership.views.DetailViewController");
$import('cocontractmembership.views.DetailView');

mx.weblets.WebletManager.register({
    id: "cocontractmembership",
    name: "",
    requires: ['ETradePublicUtils/utils'],
    onload: function(e)
    {
    	
    },
    onstart: function(e)
    {
        var mvc = new cocontractmembership.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});