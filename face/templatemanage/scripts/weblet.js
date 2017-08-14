$import("templatemanage.views.TemplateManageMainViewController");
$import("templatemanage.views.TemplateManageMainView");
$import("templatemanage.views.TemplateManageDetailView");
$import("templatemanage.views.TemplateManageDetailViewController");

mx.weblets.WebletManager.register({
    id: "templatemanage",
    name: "合同范本管理",
    requires: ["ETradePublicUtils/utils","tempfieldconfig"],
    onload: function(e)
    {
    },
    onstart: function(e)
    {
        var mvc = new templatemanage.views.TemplateManageMainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});