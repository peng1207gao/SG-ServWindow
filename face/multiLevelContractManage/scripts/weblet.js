$import("multiLevelContractManage.views.MultiLevelMainViewController");
$import("multiLevelContractManage.views.MultiLevelMainView");
$import("multiLevelContractManage.views.MultiLevelRelateViewController");
$import("multiLevelContractManage.views.MultiLevelRelateView");

mx.weblets.WebletManager.register({
    id: "multiLevelContractManage",
    name: "多级合同管理",
    requires:["IdeaConManage","ETradePublicUtils/utils"],
    onload: function(e)
    {
  
    },
    onstart: function(e)
    {
        var mvc = new multiLevelContractManage.views.MultiLevelMainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});