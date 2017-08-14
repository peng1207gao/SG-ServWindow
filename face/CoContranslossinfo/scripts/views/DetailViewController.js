$ns("CoContranslossinfo.views");

//$import("CoContranslossinfo.views.DetailView");
$import("mx.utils.CheckUtilClass");

CoContranslossinfo.views.DetailViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    //合同Id
    me.contractId = null;
  
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new CoContranslossinfo.views.DetailView(
            		{controller: me, alias:"CoContranslossinfoDetailView"});
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {
    };
    
    me._btnSave_onclick = function()
    {
        me.view.getForm().save();
    };
    
    /**
     * 数据表单加载后执行的回调函数
     * 1. 判断表单中是否设置了合同Id，如果没有设置，则设置合同Id
     * 2. 根据合同Id，从服务端加载合同名称，并设置到表单中
     */
    me._dataform_onload = function() {
    	var dataform = me.view.getForm();
    	var contractid = dataform.entityContainer.getValue(dataform.getEditor("contractid").name);
    	if (contractid == null || contractid == "") {
    		contractid = me.contractId;
    		dataform.entityContainer.setValue(dataform.getEditor("contractid").name, contractid, false);
    	}
//    	dataform.editors.contractname.setValue(me.name);
    	me._dataform_getContractName(dataform, contractid);
    }
    
    /**
     * 根据合同Id，从服务端加载合同名称，并设置到表单中
     */
    me._dataform_getContractName = function(dataform, contractid) {
    	var client = new mx.rpc.RESTClient();
    	var url = CoContranslossinfo.mappath("~/rest/cocontranslossinfo/contract/" + contractid);
    	client.get(url, function(context){
    		if (context.successful) {
    			var cname = context.resultValue.items[0].contractname;
    			if (cname != null) {
    	    		dataform.getEditor("contractname").setValue(cname, false);
    	    	}
    		} else {
    			mx.indicate("error", context.resultHint);
    		}
    	});
    }
    
    /**
     * 联络线下拉框数据变更时执行的函数
     * 根据linkid值从服务端加载“线路起止信息”和“输电网损(%)”，并设置到数据表单中
     */
    me._dataform_linkidDropDownEditor_onchanged = function(e) {
    	var linkid = e.target.value;
    	var client = new mx.rpc.RESTClient();
    	var url = CoContranslossinfo.mappath("~/rest/cocontranslossinfo/link/" + linkid);
    	client.get(url, function(context){
    		if (context.successful) {
    			var str = context.resultValue.items[0];
    			var tmp = str.split(",");
    			var dataform = me.view.getForm();
    			dataform.getEditor("linestartendgate").setValue(tmp[1], true);
    			dataform.getEditor("loss").setValue(tmp[2], true);
    		} else {
    			mx.indicate("error", context.resultHint);
    		}
    	});
    }
    
    /**
     * 执行一部分的表单提交校验
     * 1. 校验输电网损(%)，如果设置了值，则该值必须是数字，并且在0到100之间
     */
    me._dataform_onvalidate = function(e) {
    	
    	var lossValue = me.view.getForm().entityContainer.getValue("loss");
    	
    	if (lossValue != null && !isDigitAmong0And100(lossValue)) {
    		// 通过事件参数传递校验结果
            e.successful = false;
            e.hint = "请输入最大100的有效数字";
    	}
    	
    	function isDigitAmong0And100(num) {
    		if (!mx.utils.CheckUtil.isDigit(lossValue)) {
    			return false;
    		}
    		if (lossValue > 100 || lossValue < 0) {
    			return false;
    		}
    		return true;
    	}
    }
    
    return me.endOfClass(arguments);
};