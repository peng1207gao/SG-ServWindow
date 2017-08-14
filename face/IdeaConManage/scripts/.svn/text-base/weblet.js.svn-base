$import("IdeaConManage.views.IdeaConMainViewController");
$import("IdeaConManage.views.IdeaConMainView");
$import("IdeaConManage.views.IdeaConRelateViewController");
$import("IdeaConManage.views.IdeaConRelateView");
$import("IdeaConManage.views.IdeaConDetailViewController");
$import("IdeaConManage.views.IdeaConDetailView");

mx.weblets.WebletManager.register({
    id: "IdeaConManage",
    name: "意向性协议管理",
    requires:["ETradePublicUtils/utils"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new IdeaConManage.views.IdeaConMainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});