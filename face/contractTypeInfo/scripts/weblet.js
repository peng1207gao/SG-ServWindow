$import("contractTypeInfo.views.ContractTypeInfoMainViewController");
$import("contractTypeInfo.views.ContractTypeInfoMainView");
$import("contractTypeInfo.views.ContractTypeInfoDetailViewController");
$import("contractTypeInfo.views.ContractTypeInfoDetailView");


mx.weblets.WebletManager.register({
    id: "contractTypeInfo",
    name: "合同类型管理",
    requires:["ETradePublicUtils/utils","cocontractmembership","ETradePublicUtils/validate"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new contractTypeInfo.views.ContractTypeInfoMainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});