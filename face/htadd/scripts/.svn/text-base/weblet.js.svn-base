$import("htadd.views.MainViewController");
$import("htadd.views.MainView");
$import("htadd.views.htglViewController");
$import("htadd.views.htglView");
$import("htadd.views.zjxzViewController");
$import("htadd.views.zjxzView");

mx.weblets.WebletManager.register({
    id: "htadd",
    name: "合同查询",
    requires:["ETradePublicUtils/utils","marketMember/displayitemconfig", "marketMember/configmodule", "ETradePublicUtils/validate","contResoByMonth"
              ,"coContractgateinfo","CoContransEnergyInfo","marketMember/validate"
              ,"CoContransEnergyInfoV2","cotransqtyinfo","COContractEnergyInfo","contractaffixinfo"
              ,"cocontractaccessory","CoContranslossinfo","contractrecordinformation"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new htadd.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});