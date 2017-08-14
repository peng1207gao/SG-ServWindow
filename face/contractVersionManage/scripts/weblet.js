$import("contractVersionManage.views.ContractVersionMainViewController");
$import("contractVersionManage.views.ContractVersionMainView");
$import("contractVersionManage.views.ContractVersionDetailViewController");
$import("contractVersionManage.views.ContractVersionDetailView");


mx.weblets.WebletManager.register({
    id: "contractVersionManage",
    name: "合同版本",
    requires:["ETradePublicUtils/utils","IdeaConManage","htadd","marketMember/displayitemconfig"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new contractVersionManage.views.ContractVersionMainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});