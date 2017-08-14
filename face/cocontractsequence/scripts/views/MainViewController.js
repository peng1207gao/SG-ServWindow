$ns("cocontractsequence.views");
//$import("cocontractsequence.views.MainView");
//$import("cocontractsequence.views.DetailView");
//$import("cocontractsequence.views.DetailViewController");
$import("mx.rpc.RESTClient");
cocontractsequence.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    me._openWin=null;
    var detailController=null;
    var restClient = new mx.rpc.RESTClient();
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new cocontractsequence.views.MainView({ controller: me });
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {
    	me._btnSearch_onclick();
    };
    
    /**
     * 获得搜索条件值
     */
    function _searchEditor_value() {
    	var values = {};
    	var i=0;
    	$.each(me.view.searchEditors, function(name, editor){
    		var value = editor.getValue();
    		if(name=='marketid'){
    			if(value==null||value==""){
    				i++;
    				editor.$e.css("border-color","red");
    				return false;
    			}else{
    				editor.$e.css("border-color","");
    			}
    		}
    		if (value) {
    			values[name] = value;
    		}
    	});
    	if(i!=0){
    		return false;
    	}else{
    		return values;
    	}
    }
    
    /**
     * 设置数据表格查询过滤参数
     */
    me._dataGrid_setFilter = function() 
    {
    	var filterValues = _searchEditor_value();
    	if(!filterValues){
    		return false;
    	}else{
    		me.view.dataGrid.setFilter(filterValues);
    		return true;
    	}
    }

    /**
     * 查询按钮点击事件处理函数
     */
    me._btnSearch_onclick = function() {
    	var tenBool=me._dataGrid_setFilter();
    	if(!tenBool){
    		return false;
    	}
    	me.view.dataGrid.load();
    }
    
    /**
     * 新增按钮点击事件处理函数
     */
    me._btnNew_onclick = function()
    {
    	detailController=new cocontractsequence.views.DetailViewController({mainView:me.view})
        var _openView=detailController.getView();
        me._openWin = utils.commonFun.WinMananger.create({
			reusable: true,//是否复用
			width:"400px",
			height:"300px",
			title:"合同序列新增"
		});
		me._openWin.setView(_openView);
		me._openWin.showDialog();
		detailController.mainController=me;
    };
    
    /**
     * 删除按钮点击处理函数
     */
    me._btnDelete_onclick = function()
    {
    	var str=me.view.dataGrid.getCheckedIDs();
    	if(str.length==0){
//    		alert("请选择一条数据进行删除");
    		mx.indicate("info", "请选择一条数据进行删除！");
    		return false;
    	}
    	var items = restClient.getSync(cocontractsequence.mappath("~/rest/cocontractsequence/delete?ids="+str)).resultValue.items;
    	var str=items[0]!=null?items[0].split("_"):"";
    	if(str[0]!=null&&str[0]!=""){  //有关联的合同弹出 提示
    		var tenArr=str[0].split(",");
    		var tenStr="";
    		for(var i=0;i<tenArr.length;i++){
    			if(i!=tenArr.length-1){
    				tenStr+=(me.view.dataGrid.entityContainer.getEntityByKey(tenArr[i]).sequencename+",");
    			}else{
    				tenStr+=me.view.dataGrid.entityContainer.getEntityByKey(tenArr[i]).sequencename;
    			}
    		}
//    		alert("名称为 "+tenStr+" 的合同序列有关联合同,不能删除！");
    		mx.indicate("info", "名称为 "+tenStr+" 的合同序列有关联合同,不能删除！");
    	}
    	if(str[1]=="1"){
    		var tenArr=str[2].split(",");
    		var tenStr="";
    		for(var i=0;i<tenArr.length;i++){
    			if(i!=tenArr.length-1){
    				tenStr+=(me.view.dataGrid.entityContainer.getEntityByKey(tenArr[i]).sequencename+",");
    			}else{
    				tenStr+=me.view.dataGrid.entityContainer.getEntityByKey(tenArr[i]).sequencename;
    			}
    		}
//    		alert("名称为 "+tenStr+" 的合同序列 删除成功");
    		mx.indicate("info", "名称为 "+tenStr+" 的合同序列 删除成功！");
    	}else if(str[1]=="1"){
//    		alert("删除失败");
    		mx.indicate("info", "删除失败");
    	}else if(str[1]=="2"){
//    		alert("部分合同删除成功");
    		mx.indicate("info", "部分合同删除成功");
    	}
    	me._btnSearch_onclick();
//        me.view.dataGrid.removeItems(,function(e){},true);
    };
    
    /**
     * 编辑按钮点击事件处理函数
     */
    me._btnEdit_onclick = function()
    {
    	if(me.view.dataGrid!=null){
			 if(me.view.dataGrid.getCheckedIDs().length==0){
//	    		 alert("请选择一条数据");
	    		 mx.indicate("info", "请选择一条数据");
	    		 return false;
	    	 }else if(me.view.dataGrid.getCheckedIDs().length>1){
//	    		 alert("只能选择一条数据");
	    		 mx.indicate("info", "只能选择一条数据");
    			 return false;
	    	 }
    	}else{
    		return false;
    	}
    	var checkId=me.view.dataGrid.getCheckedIDs()[0];
    	detailController=new cocontractsequence.views.DetailViewController({checkId:checkId,mainView:me.view});
        var _openView=detailController.getView();
        me._openWin = utils.commonFun.WinMananger.create({
			reusable: true,//是否复用
			width:"400px",
			height:"300px",
			title:"合同序列修改"
		});
        me._openWin.setView(_openView);
		me._openWin.showDialog();
		detailController.mainController=me;
    };
    
    return me.endOfClass(arguments);
};