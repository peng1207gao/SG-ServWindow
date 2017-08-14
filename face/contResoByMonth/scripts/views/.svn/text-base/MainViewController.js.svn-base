$ns("contResoByMonth.views");

//$import("contResoByMonth.views.MainView");
//$import("contResoByMonth.views.contResoMonView");

contResoByMonth.views.MainViewController= function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new contResoByMonth.views.MainView({controller: me});
        }
        return me.view;
    };
    me.showHtfjWin=function(tenContractid){
		me._contResoByMonthWin = utils.commonFun.WinMananger.create({
			reusable: true,
			width:800,
			height:430,
			displayMaxButton:false,
			title:"合同按月分解"
		});
		var tenObj= new contResoByMonth.views.contResoMonView({contractid:tenContractid});
		me._contResoByMonthWin.setView(tenObj);
		me._contResoByMonthWin.showDialog();
	}
    me.endOfClass(arguments);
    return me;
};