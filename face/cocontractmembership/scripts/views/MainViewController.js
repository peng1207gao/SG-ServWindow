$ns("cocontractmembership.views");
//$import("cocontractmembership.views.MainView");
//$import("cocontractmembership.views.DetailViewController");

cocontractmembership.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    /**
     * 合同类型Id，需要外部controller设置
     */
    me.contracttypeid = null;
    
    /**
     * 合同类型名称，需要外部controller设置
     */
    me.contracttypename = null;
    
    /**
     * 缓存市场成员ID集合，千万别手动设置，由程序内部维护该变量的值
     * 
     * 参考函数 me._reflesh_extract_ids(ids)
     */
    me.participantIDsCache = null;
    
    /**
     * 弹出窗口关闭方式
     * 
     * 0 是直接点击窗口的关闭按钮
     * 
     * 1 是程序内部通过调用window.hide()关闭
     * 
     */
    me.detailViewCloseWay = null;
    
    /**
     * 打开该视图的窗口对象，在保存成功后会关闭该窗口，需要外部controller设置
     */
    me.win = null;
  
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new cocontractmembership.views.MainView({ controller: me });
        }
        return me.view;
    };
    
    /**
     * 获得市场成员准入选择视图
     * 
     */
    me._getDetailView = function() {
    	if (me.detailView == null) {
    		var mvc = new cocontractmembership.views.DetailViewController();
    		me.detailView = mvc.getView();
    		me.detailView.outerController = me;
    	}
    	return me.detailView;
    }
    
    me._onactivate = function(e)
    {
    	//界面数据初始化
    	me.view.load();
    };
    
    /**
     * 保存按钮点击事件处理函数
     * 
     * 获得需要提交到服务端的数据，请求服务端保存数据
     */
    me._btnSave_onclick = function()
    {
    	var data = me._getSubmitData();
    	
    	//用户没有选择市场成员
    	if (data.participantIDs == null) {
    		mx.indicate('info', "请先选择合同角色，再点击'选择'按钮，选择市场准入成员");
    		return;
    	}
    	
    	//请求服务端，保存数据
    	_save_submit_data(data);
    };
    
    /**
     * 合同角色选项变更时调用的函数
     * 
     * 根据选择的值，获得对应合同类型Id的市场准入成员信息
     * 
     * 并更新视图中的编辑器extract显示的信息
     * 
     */
    me._contractroleType_dropDownEditor_onchanged = function(e)
    {
    	var data = {};
    	data.contractroleType = e.target.value;
    	data.contractType = me.contracttypeid;
    	
    	var client = new mx.rpc.RESTClient();
    	var url = "~/rest/cocontractmembership/selectedMemberships";
    	client.post(
    		cocontractmembership.mappath(url), JSON.stringify(data),
    		function(context){
    			if (context.successful) {
    				
    				//从服务端获得已经关联的市场成员Id集合和名称集合
    				var names = context.resultValue.items[0].names;
    				var ids = context.resultValue.items[0].ids;
    				
    				//更新编辑器和ID集合缓存
    				me._update_extract_editor(names);
    				me._reflesh_extract_ids(ids);
    				
				} else {
					if (context.resultHint) {
						mx.indicate("error", context.resultHint);
					} else {
						mx.indicate("error", "未知错误，可能是网络连接异常");
					}
				}
    		}
    	);
    };
    
    /**
     * 获得需要提交到服务端的数据
     * 
     * 包括：合同角色 contractroleType，合同类型 contractType，准入成员的ID集合 participantIDs
     */
    me._getSubmitData = function() {
    	var data = {};
    	data.contractType = me.contracttypeid;
    	data.contractroleType = me.view.dataFormEditors['contractroleType'].getValue();
    	
    	//获得市场成员ID主键集合
    	data.participantIDs = me._get_extract_ids();
    	return data;
    }
    
    /**
     * 请求服务端，保存数据
     * 
     * 保存成功后关闭弹出框，保存失败提示错误信息
     */
    function _save_submit_data(data) {
    	if (me.view.submitflag == 1) {
    		mx.indicate('info', "已经提交请稍后...");
    		return //数据已经提交
    	}
    	me.view.submitflag = 1;
    	var client = new mx.rpc.RESTClient();
    	var url = cocontractmembership.mappath("~/rest/cocontractmembership/save");
    	client.post(
			url, JSON.stringify(data),
			function(context) {
				me.view.submitflag = 0; //服务端返回
				if (context.successful) {
					
					//关闭弹出框，刷新缓存数据
//			    	if (me.win) {
//			    		me.win.hide();
//			    	} else {
//			    		mx.indicate("info", "亲，请将窗口对象给我，否则我无法关闭当前窗口！");
//			    	}
			    	me._reflesh_extract_ids(null);
			    	mx.indicate("info", "保存成功");
			    	
				} else {
					if (context.resultHint) {
						mx.indicate("error", context.resultHint);
					} else {
						mx.indicate("error", "未知错误，可能是网络连接异常");
					}
				}
			}
    	);
    }
    
    /**
     * 使用服务端返回的数据更新”准入成员选择 “编辑器的值
     * 
     */
    me._update_extract_editor = function(data) {
    	me.view.dataFormEditors['extract'].setValue(data);
    }
    
    /**
     * 更新合同准入市场成员Id集合，id之间使用逗号隔开
     * 
     * 该函数在以下情况时执行：
     * 
     * 1. 用户选择了不同的合同角色
     * 
     * 2. 用户点击”选择“按钮，最后点击了”确认“按钮
     * 
     * 3. 请求服务端保存数据成功时，需要清除数据为下次操作做准备，通过传递 null 对象实现数据清除
     * 
     * 4. 初始化界面时，确保该值为空，否则会造成程序逻辑判断异常，通过调用view.load()方法初始化界面
     */
    me._reflesh_extract_ids = function(ids) {
    	me.participantIDsCache = ids;
    }
    
    /**
     * 获得用户指定的合同准入市场成员ID集合
     */
    me._get_extract_ids = function() {
    	return me.participantIDsCache;
    }
    
    /**
     * 选择按钮点击事件处理函数，弹出市场成员选择窗口
     */
    me._btnSearch_onclick = function() {
    	var contractroletype = me.view.dataFormEditors['contractroleType'].getValue();
    	if (!contractroletype) {
    		mx.indicate("info", "请选择合同角色");
    		return;
    	}
    	var _detailView = me._getDetailView();
    	
    	//在view视图load之前设置必要参数
    	_detailView.controller.contracttypeid = me.contracttypeid;
    	_detailView.controller.contractroletype = contractroletype;
    	
    	_showDetailFormView(_detailView,"市场成员选择");
    }
    
    /**
     * 关闭市场成员准入选择窗口，在点击“确认”按钮是调用该方法
     * 
     * 并设置当前弹出窗口关闭方式为0
     */
    me.detailView_win_hide = function() {
    	me.detailViewCloseWay = 0;
    	var win = me.view.getDetailWindow();
    	win.hide();
    }
    
    /**
     *当直接关闭窗口时，提示用户
     *
     *可以通过设置 e.cancel=true 来取消窗口关闭
     */
    me._detailWindow_onhiding = function(e) {
    	if (!(me.detailViewCloseWay == 0)) {
    		e.cancel = !confirm("直接关闭窗口，选择的数据将丢失");
    	}
    	me.detailViewCloseWay = null;
    }
    
    /**
     * 显示表单视图
     * @param p_view : 需要显示的视图对象
     * @param p_title : 对话框的标题
     */
    function _showDetailFormView(p_view,p_title){
    	var win = me.view.getDetailWindow();
    	if(typeof p_view != "undefined"){
    		
    		//
    		p_view.load();
    		//设置显示视图、标题信息
    		win.setView(p_view);
    		win.setTitle(p_title ? p_title : win.title);
    	}
    	win.showDialog();
    }
    
    return me.endOfClass(arguments);
};