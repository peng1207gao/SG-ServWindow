$ns('cocontractmembership.views');

//$import('cocontractmembership.views.DetailView');

cocontractmembership.views.DetailViewController = function()
{
    var me = $extend(mx.views.ViewController);
    
    /**
     * 合同类型Id，需要外部controller设置
     */
    me.contracttypeid = null;
    
    /**
     * 合同角色类型
     */
    me.contractroletype = null;
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new cocontractmembership.views.DetailView( { controller: me } );
        }
        
        return me.view;
    };
    
    me._onactivate = function()
    {
    };
    
    /**
     * 查询按钮点击事件执行函数
     * 
     * 获得数据查询过滤参数值，加载数据表格数据
     */
    me._btnSearch_onclick = function() {
    	me._load_left_datagrid();
    }
    
    /**
     * 确认按钮点击事件执行函数
     * 
     * 更新用户选择的市场成员Id集合，为请求服务端保存数据做准备
     * 
     * 如果用户没有选择任何一个市场成员，则提示用户选择，如果用户已经选择，则保存市场成员Id集合
     * 
     */
    me._btnOk_onclick = function() {
    	me.view.rightDataGrid.checkAll();
    	var ids = me.view.rightDataGrid.getCheckedIDs();
    	
    	if (ids.length == 0) {
    		me.view.rightDataGrid.uncheckAll();
    		mx.indicate("info", "请至少添加一条市场成员。");
    		return;
    	}
    	//
    	me.view.outerController._reflesh_extract_ids(ids.join(','));
    	//
    	var items = me.view.rightDataGrid.getCheckedItems();
    	var names = _calculate_selected_participantnames(items);
    	me.view.outerController._update_extract_editor(names);
    	//
    	me.view.outerController.detailView_win_hide();
    }
    
    /**
     * 将已经选中的市场成员的名称使用逗号隔开，串联起来，并返回
     * 
     */
    function _calculate_selected_participantnames(items) {
    	var names = new Array();
    	for (var i = 0 ; i < items.length ; i++) {
    		names.add(items[i].values.participantname);
    	}
    	return names.join(',');
    }
    
    /**
     * 右方向箭头按钮点击事件执行函数
     * 
     * 将用户在左边表格中选择的数据添加到右边的表格中，同时删除左边表格中的数据
     * 
     */
    me._btnRight_onclick = function() {
    	var leftCheckedItems = me.view.leftDataGrid.getCheckedItems();
    	if (leftCheckedItems.length > 0) {
    		for (var i = 0 ; i < leftCheckedItems.length ; i++) {
    			var values = leftCheckedItems[i].values;
    			
    			me.view.leftDataGrid.removeItem(values.participantid, function() {
    				
    				//当数据表格中没有该主键的数据时，才执行添加操作
    				if (!me._exist_in_datagrid(me.view.rightDataGrid.items, values.participantid)) {
    					me.view.rightDataGrid.appendItem(values);
    				}
    			});
    		}
    		
    		me._refresh_datagrid_rownumber(me.view.rightDataGrid);
    	}else if(leftCheckedItems.length == 0){
    		mx.indicate("info","请在左侧列表中选择市场成员！");
    	}
    }
    
    /**
     * 左方向箭头按钮点击事件执行函数
     * 
     * 将用户在右边表格中选择的数据添加到左边的表格中，同时删除右边表格中的数据
     * 
     */
    me._btnLeft_onclick = function() {
    	var rightCheckedItems = me.view.rightDataGrid.getCheckedItems();
    	if (rightCheckedItems.length > 0) {
    		for (var i = 0 ; i < rightCheckedItems.length ; i++) {
    			var values = rightCheckedItems[i].values;
    			
    			me.view.rightDataGrid.removeItem(values.participantid, function() {
    				
    				//当数据表格中没有该主键的数据时，才执行添加操作
    				if (!me._exist_in_datagrid(me.view.leftDataGrid.items, values.participantid)) {
    					me.view.leftDataGrid.appendItem(values);
    				}
    			});
    		}
    		
    		//
    		me._refresh_datagrid_rownumber(me.view.rightDataGrid);
    	}else if(rightCheckedItems.length == 0){
    		mx.indicate("info","请在右侧列表中选择市场成员！");
    	}
    }
    
    /**
     * 判断指定的主键ID在指定的表格中是否存在
     * 
     * 返回 true 表示在数据表格中已经存在
     * 
     * 返回 false 表示在数据表格中不存在
     * 
     */
    me._exist_in_datagrid = function(items, id) {
    	if (items.length > 0) {
    		for (var i = 0 ; i < items.length ; i++) {
    			var participantid = items[i].values.participantid;
    			if (participantid == id) {
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
    /**
     * 加载界面左边的数据表格，该表格中的数据是未被选中的市场成员
     */
    me._load_left_datagrid = function() {
    	var data = {};
    	
    	//传递一个参数type，表示查询未选择的市场成员集合
    	data.type = '0';
    	data.contractroleType = me.contractroletype;
    	data.contractType = me.contracttypeid;
    	
    	//用户指定的过滤参数
		$.each(me.view.searchEditors, function(name, editor){
			if (editor.instanceOf(mx.editors.Editor)) {
				var value = editor.getValue();
	    		if (value) {
	    			data[name] = value;
	    		}
			}
    	});
		
		//在请求之前先把表格中已经存在的数据清除
		me.view.leftDataGrid.clearItems();
    	//
		_rest_datagrid_data(me.view.leftDataGrid, data);
    }
    
    /**
     * 加载界面右边的数据表格，该表格中的数据是已被选中的市场成员
     */
    me._load_right_datagrid = function() {
    	var data = {};
    	
    	//传递一个参数type，表示查询已经选择的市场成员集合
    	data.type = '1';
    	data.contractroleType = me.contractroletype;
    	data.contractType = me.contracttypeid;
    	
    	//在请求之前先把表格中已经存在的数据清除
		me.view.rightDataGrid.clearItems();
		//
		_rest_datagrid_data(me.view.rightDataGrid, data);
    }
    
    /**
     * 根据参数，请求服务端加载数据，并将数据添加到数据表格中
     */
    function _rest_datagrid_data(datagrid, data) {
    	var client = new mx.rpc.RESTClient();
    	var restUrl = cocontractmembership.mappath("~/rest/cocontractmembership/membership/");
    	client.post(
    		restUrl, JSON.stringify(data),
			function(context) {
				if (context.successful) {
					var items = context.resultValue.items;
					for (var i = 0 ; i < items.length ; i++) {
						datagrid.appendItem(items[i]);
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
     * 当数据表格中的行数变更后，重新计算界面行数
     * 
     * 比如在调用数据表格的方法 DataGrid.appendItem() 和 DataGrid.removeItem()
     * 
     * 后数据表格中的行数变更了，但是界面上显示的序列是乱的，通过该方法，重新计算。
     */
    me._refresh_datagrid_rownumber = function(datagrid) {
    	var $rownumbers = datagrid.$e.find('table td:#rownumber');
    	$rownumbers.each(function(index, rownumber){
    		$(rownumber).text(++index);
    	});
    }
    
    return me.endOfClass(arguments);
};