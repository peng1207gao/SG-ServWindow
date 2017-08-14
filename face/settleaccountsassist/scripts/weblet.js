$import("settleaccountsassist.views.SettleAccountsAssistMainViewController");
$import("settleaccountsassist.views.SettleAccountsAssistMainView");


mx.weblets.WebletManager.register({
    id: "settleaccountsassist",
    name: "分解电量到经济机组",
    requires:["ETradePublicUtils/utils","htadd",
    "coContractbackupInfo","contractrecordinformation","resolveelectricquantitytounit"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new settleaccountsassist.views.SettleAccountsAssistMainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});