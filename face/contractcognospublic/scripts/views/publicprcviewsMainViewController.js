$ns("contractcognospublic.views");
$import("contractcognospublic.views.publicprcviewsMainView");

contractcognospublic.views.publicprcviewsMainViewController = function()
{
	var me = $extend(mx.views.ViewController);
    var base = {};
    var client = new mx.rpc.RESTClient();
    var vilidate = new pubUtils.validateUtil.validateUtilController();
    me.userInfo = new utils.commonFun.utilFun().getLoginInfo();
    //存储过程需要写死的参数   例子：{'1':params1,'2':'params2'}  属性必须用数字标识且序号必须与存储过程所需参数顺序一致
    me.dieparams = null;
    //报表url
    me.reporturl = null;
    //是否隐藏生成数据按钮
    me.adddatabuttonhide = true;
    //存储过程名称
    me.procedureName = null;
    //存储过程所需参数对照表，｛'1'：'ps_batch'｝
    me.procedureParams = {};
    var procount = 0;
    //是否显示年月查询条件
    me.ifYM = true;
    //动态传所需要的查询条件，命名规则参照excel
    me.labels = [];
    me.editors = [];
    me.getView = function()
    {
    	if(me.adddatabuttonhide != true){
    		me.adddatabuttonhide = false;
    	}
    	
        if (me.view == null)
        {
            me.view = new contractcognospublic.views.publicprcviewsMainView({ controller: me });
            if(me.procedureName == null){
            	me.view.addData.hide();
            }
        }
        return me.view;
    };
    
    me._onactivate = function(e)
    {
    	//alert($("#cognosyear").val());
    	
        // TODO: 窗体激活时的逻辑。
	if (me.view != null && typeof(me.view.htmlContainer) != "undefined")
	{
		var params = me.getQueryParams();
		me.reporturl = me.reporturl + '&p_market=' + me.userInfo.marketCode;
    	if(params){
    		delete params['ksseldate'];
    		delete params['jsseldate'];
    		me.refresh(params);
    	}
    	for(var key in me.procedureParams){
    		procount += 1;
    	}
    	if(me.dieparams){
    		for(var key in me.dieparams){
    			procount += 1;
    		}
    	}
    	
	}	
    };
    
    //查询       （可以重写）
    me.clickquery = function(){
    	for(var i = 0;i < me.editors.length;i++){
    		me.editors[i].markError(false);
    		if(!vilidate.valiateEditor(me.editors[i]))
    			return;
    	}
    	var params = me.getQueryParams();
    	if(!params.p_year){
    		mx.indicate('info','时间不能为空');
    		return;
    	}
    	if(params){
    		delete params['ksseldate'];
    		delete params['jsseldate'];
    		me.refresh(params);
    	}
    };
    
    //生成数据       （可以重写）
    me.addData = function(){
    	var params = me.getQueryParams();
    	if(params){
    		if(confirm("如果有数据将删除原数据，您确认生成数据吗？")){
            	var proparams = populateParams(params);
            	client.get(utils.mappath('~/rest/cognosreportconfig/excutePromoreParam'),{"params": JSON.stringify(proparams),'paramscount':procount},function(e){
            		if(e.successful){
            			mx.indicate('info','操作成功');
            			params = me.getQueryParams();
                    	if(params){
                    		me.refresh(params);
                    	}
            		}else{
            			mx.indicate('info',e.resultHint);
            		}
            	});
        	}
    	}
    };
    
   //获取url参数 
    me.getUrl = function(params){
    	var url = me.reporturl;
    	for(var key in params){
    		url += "&" + key + "=" + params[key];
    	}
    	return url;
    }
    
    //获取查询参数
    me.getQueryParams = function(){
    	
//    	var day = ksseldate.getDate();
    	var params = {};
    	if(me.ifYM){
    		var ksseldate = me.view.dataTimeEditor.getValue();
        	var jsseldate = me.view.jsdataTimeEditor.getValue();
        	if(ksseldate==null){
        		mx.indicate("info", "开始时间不能为空");
        		return false;
        	}
        	if(jsseldate==null){
        		mx.indicate("info", "结束时间不能为空");
        		return false;
        	}
        	ksseldate = parseDate(ksseldate);
        	jsseldate = parseDate(jsseldate);
        	if(jsseldate - ksseldate < 0){
        		mx.indicate("info", "结束时间不能大于开始时间");
        		return false;
        	}
        	var years = ksseldate.getFullYear();
        	var months = ksseldate.getMonth() + 1;
        	var year = jsseldate.getFullYear();
        	var month = jsseldate.getMonth() + 1;
    		params = {'p_years':years,'p_months':months,'p_year':year,'p_month':month,'ksseldate':ksseldate,'jsseldate':jsseldate};
    	}
//    	params.p_marketid = me.userInfo.marketCode;
    	for(var i= 0 ;i<me.editors.length;i++){
    		var editor = me.editors[i];
    		if(!(editor.getValue() == null || editor.getValue().trim()=='')){
    			params[editor.name] = encodeURI(editor.getValue());
    		}
    	}
    	if(!me.ifYM && params.ksseldate){
    		params.ksseldate = parseDate(params.ksseldate);
    		var year = params.ksseldate.getFullYear();
        	var month = params.ksseldate.getMonth() + 1;
        	params.p_year = year;
        	params.p_month = month;
    	}
    	return params;
    }
    
    //转换参数格式，生成数据按钮用
    function populateParams(params){
    	var resultParam = {};
    	if(me.dieparams){//写死的参数
    		resultParam = me.dieparams;
    	}
    	params['ps_batch'] = 'MAN'+Date.format(new Date(),'yyyyMMddHHmmss');
    	params['pd_startdate'] = Date.format(params.ksseldate,'yyyy-MM-dd');
    	if(!me.ifYM){
    		params.jsseldate = params.ksseldate;
    	}
    	params['pd_enddate'] = Date.format(params.jsseldate,'yyyy-MM-dd');
    	for(var key in me.procedureParams){
    		resultParam[key] = params[me.procedureParams[key]];
    	}
    	resultParam['procedureName'] = me.procedureName;
    	return resultParam;
    }
    
    //刷新页面
    me.refresh = function(params){
    	var url = me.getUrl(params);
    	me.view.htmlContainer.url = url;
    	me.view.htmlContainer.init();
    }
    return me.endOfClass(arguments);
};