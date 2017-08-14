$import("contracttemplate.views.MainViewController");
$import("contracttemplate.views.MainView");

mx.weblets.WebletManager.register({
    id: "contracttemplate",
    name: "合同范本",
    requires:["ETradePublicUtils/utils","htadd","htquery","coContractbackupInfo","contractrecordinformation","contractVersionManage","jfcontractaffix","jfcontractstate","CoContractFjAct","tempfieldconfig"],
    
    onload: function(e)
    {
    },
    onstart: function(e)
    {
    	
    	var mvc = new contracttemplate.views.MainViewController();
        e.context.rootViewPort.setViewController(mvc);
    }
});