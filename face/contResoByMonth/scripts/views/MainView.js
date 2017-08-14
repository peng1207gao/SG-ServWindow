$ns("contResoByMonth.views");
$import("mx.controls.Button");

contResoByMonth.views.MainView=function(){
	var me = $extend(mx.views.View);
	
	var base = {};
	var _contResoByMonthWin=null;
	base.init = me.init;
	me.init = function () {
		base.init();
		_layout();
	};
	
	//controller  会调用 view 内的 contractid 
	me.contractid="CQ2007110638";
	
	function _layout(){
		me.tenButton= new mx.controls.Button({
			text : "合同分解弹出",
			left:20
		});
    	me.addControl(me.tenButton);
    	me.tenButton.on("click", function()
    			{
    		 		me.controller.showHtfjWin(me.contractid);
    	});
	}
	
	function _initControls(){
	}
	 me.endOfClass(arguments);
	 return me;
};