$ns("cocontractparameter.views");

cocontractparameter.views.ConfigModuleEditerViewController = function() {
	var me = $extend(mx.views.ViewController);
	var base = {};

	me.getView = function(e) {
		if (me.view == null) {
			if(e == null)
				e = new Object();
			e.controller = me;
			me.view = new cocontractparameter.views.ConfigModuleEditerView(e);
		}
		return me.view;
	};

	me._onactivate = function(e) {
		// TODO: 窗体激活时的逻辑。
	};

	me._btnSave_onclick = function() {
	};

	return me.endOfClass(arguments);
};