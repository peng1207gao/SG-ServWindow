$import("contractrecordinformation.views.ContractRecordInformationMainViewController");
$import("contractrecordinformation.views.ContractRecordInformationMainView");
$import("contractrecordinformation.views.ContractRecordInformationDetailViewController");
$import("contractrecordinformation.views.ContractRecordInformationMainView");

mx.weblets.WebletManager.register({
    id: "contractrecordinformation",
    name: "合同签订备案信息",
    requires:["ETradePublicUtils/utils"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new contractrecordinformation.views.ContractRecordInformationMainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});