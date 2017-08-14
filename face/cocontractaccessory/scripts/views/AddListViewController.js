$ns("cocontractaccessory.views");


cocontractaccessory.views.AddListViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new cocontractaccessory.views.AddListView({ controller: me });
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {
        // 窗体激活时的逻辑。
    };
    
    /**
     * 初始化机组数据表格，删除上次加载的数据
     */
    me._init_dataGrid = function() {
    	me.getView().dataGrid.clearItems();
    }
    
    /**
     * 查询按钮点击事件处理函数，
     * 
     * 获得用户选择的合同角色类型，如果没有选择，则提示用户选择，
     * 
     * 根据合同角色类型和合同Id，加载机组信息。
     */
    me._btnLoadDataGrid_onclick = function() {
    	var contractroleValue = _getContractrole_dropDownEditor();
    	var contractidValue = me.view.outerController.getView().contractId;
    	if (contractroleValue == null) {
    		mx.indicate("info", "请选择合同角色");
    		return;
    	}
    	
    	//在加载机组信息时，将合同角色类型和合同Id发送给服务端，进行数据过滤
    	me.view.dataGrid.setFilter(
    			{contractrole: contractroleValue, 
    				contractid: contractidValue});
    	me.view.dataGrid.load();
    }
    
    /**
     * 确认按钮点击事件处理函数
     * 
     * 获得用户在数据表格中选择的机组信息，如果没有选择则提示用户选择
     * 
     * 调用服务端方法保存数据，保存成功后关闭弹出框，刷新外部数据表格，保存失败，提示具体的错误信息
     * 
     */
    me._btnSaveSelectedGen_onclick = function() {
    	var selectedGen = _getSelectedGenerators();
    	if (!selectedGen.ids || selectedGen.ids == "") {
    		mx.indicate("info", "请选择机组");
    		return;
    	}
    	
    	//保存数据
    	_saveSelectedGenerators(selectedGen);
    }
    
    /**
     * 获得用户选择的机组，将选择的机组ID使用逗号隔开
     * 
     * 获得合同角色（购电方，售电方）
     * 
     * 获得合同Id
     */
    function _getSelectedGenerators() {
    	var arr = {};
    	
    	//用户选择的机组主键Id集合
    	var ids = me.view.dataGrid.getCheckedIDs();
    	arr.ids = ids.join(",");
    	
    	//合同角色类型（购电方，售电方）
    	var contractroleValue = _getContractrole_dropDownEditor();
    	arr.contractrole = contractroleValue; 	
    	
    	//合同Id
    	arr.contractid = me.view.outerController.getView().contractId;
    	return arr;
    }
    
    /**
     * 将用户选择的机组保存到服务端
     * 
     * 保存成功后关闭当前窗口，刷新外部数据表格
     * 
     * 保存失败，提示具体的错误信息
     * 
     * @param gens 需要发送给服务端的数据
     */
    function _saveSelectedGenerators(gens) {
    	if (me.view.submitflag == 1) {
    		return //数据已经提交
    	}
    	me.view.submitflag = 1;
    	var client = new mx.rpc.RESTClient();
    	var url = cocontractaccessory.mappath("~/rest/cocontractaccessory/save");
    	client.post(
    			url, JSON.stringify(gens),
    			function(context) {
    				me.view.submitflag = 0; //服务端返回
    				if (context.successful) {
    					//关闭弹出框，刷新数据表格
    					var outerController = me.view.outerController;
    			    	if (outerController) {
    			    		outerController._refreshDataGrid();
    			    	}
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
     * 获得用户选择的合同角色类型，如果用户没有选择，则返回 null
     * 
     */
    function _getContractrole_dropDownEditor() {
    	var value = me.view.dropDownEditor.getValue();
    	return value;
    }
    
    return me.endOfClass(arguments);
};