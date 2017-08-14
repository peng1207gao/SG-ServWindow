$ns("coContractbaseinfoV2Act.views");
$import("coContractbaseinfoV2Act.views.copybullMainView");
$import("mx.rpc.RESTClient");

coContractbaseinfoV2Act.views.copybullMainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    var restClient = new mx.rpc.RESTClient();
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new coContractbaseinfoV2Act.views.copybullMainView({ controller: me });
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {
        // TODO: 窗体激活时的逻辑。
	if (me.view != null && typeof(me.view.dataGrid) != "undefined")
	{
 	    me.view.dataGrid.load();
	}	
    };
    
    
    me._btnNew_onclick = function()
    {
        me.view.dataGrid.appendItem();
    };
    
    me._btnDelete_onclick = function()
    {
        me.view.dataGrid.removeItems(me.view.dataGrid.getCheckedIDs());
    };
    
    me._btnSave_onclick = function()
    {
    	var validateController = new validate.views.ValidateMainViewController();
    	var items = me.getView().dataGrid.items;
    	for(var i=0;i<items.length;i++){
    		var col1= items[i].values.col1;
    		if(col1.length>64){
    			mx.indicate("error","纸质合同编号长度不能超过64");
    			return;
    		}
    		var col2= items[i].values.col2;
    		if(col2.length>66){
    			mx.indicate("error","合同名称长度不能超过200个字符，一个汉字3个字符");
    			return;
    		}
    	}
//    	me.getView().dataGrid.entityContainer.set
//    	me.getView().dataGrid.save();
    	var data = me.getView().dataGrid.entityContainer.data;
    	restClient.post(coContractbaseinfoV2Act.mappath("~/rest/coContractbaseinfoV2Act/copybull"),JSON.stringify({items:data}),function(htxlList){
    		if(htxlList!=null&&htxlList.resultValue!=null&&htxlList.resultValue.items!=null&&htxlList.resultValue.items.length>0){
        		if(htxlList.resultValue.items[0]=="succ"){
        			mx.indicate("info","保存成功！");
        			me.win.hide();
        		}
        	}else{
        		mx.indicate("info","保存失败！");
        		me.win.hide();
        	}
			
    	});
    	
    };
    
    var _detailView = null;
    var _win = null;
    me._btnEdit_onclick = function()
    {        
        if (me.view.dataGrid.selection == null)
        {
            return;
        }
        
        // TODO: 弹出表单视图
        if (_detailView == null)
        {
                            }

        if ($notEmpty(me.view.dataGrid) && $notEmpty(me.view.dataGrid.selection))
        {
            _detailView.objID = me.view.dataGrid.selection.id;
	    _detailView.load();
        }
        
        if (_win == null)
        {
            _win = me.getContext().windowManager.createFromView(_detailView);
        }
        _win.showDialog();
    };
    
    return me.endOfClass(arguments);
};