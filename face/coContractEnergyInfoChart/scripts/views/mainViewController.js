$ns("coContractEnergyInfoChart.views");


//coContractEnergyInfoChart、mainView在编译时将替换为实际值，设计过程中不要随意修改


coContractEnergyInfoChart.views.mainViewController = function() {
	var me = $extend(mx.views.ViewController);
	var base = {};

	me.getView = function() {
		if (me.view == null) {
			me.view = new coContractEnergyInfoChart.views.mainView({ controller: me });
		}
		return me.view;
	};
	me._onactivate = function(e) {
		// TODO: 窗体激活时的逻辑。
	};


	//事件处理函数写在这里


	return me.endOfClass(arguments);
};