$ns("thermalpower.views");


//thermalpower、mainView在编译时将替换为实际值，设计过程中不要随意修改


thermalpower.views.mainViewController = function() {
	var me = $extend(mx.views.ViewController);
	var base = {};

	me.getView = function() {
		if (me.view == null) {
			me.view = new thermalpower.views.mainView({ controller: me });
		}
		return me.view;
	};
	me._onactivate = function(e) {
		// TODO: 窗体激活时的逻辑。
		me.getView().call();
	};


	//事件处理函数写在这里


	return me.endOfClass(arguments);
};