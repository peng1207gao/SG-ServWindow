$ns("COContractEnergyInfo.views");

$import("mx.controls.ToolBar");
$import("mx.datacontainers.FormEntityContainer");
$import("mx.datacontrols.DataForm");


COContractEnergyInfo.views.DetailView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    var restClient = new mx.rpc.RESTClient();
    /**
     * 表单对象id
     */
    me.objID = null;
    /**
      * 表单对象
     */
    var _form = null;
    /**
     * 工具条
     */
    var _toolBar = null;
//    me.controller.result = null;
    me.energyold = null;

    /* 初始化单表单控件 */
    base.init = me.init;
    me.init = function()
    {
        base.init();
        _initControls();
    };

    me.load = function()
    {
    	//请求时先对已赋值的参数清空
    	restClient.getSync(COContractEnergyInfo.mappath("~/rest/cocontractenergyinfo/getParam"),{"startdate":null,"enddate":null,"contractid":null,"perd":null});
    	//加载表单信息
    	_form.load(me.objID);
    }

    function _initControls()
    {
        _initToolBar();
	    _initDataForm();
        me.on("activate", me.controller._onactivate);
    }
	
    function _initToolBar()
    {
    	_toolBar = new mx.controls.ToolBar({
            alias:"COContractEnergyInfoDetailViewToolBar",
            width: "100%"
        });
        var btnSave = _toolBar.appendItem("save", mx.msg("SAVE"));
        btnSave.alias = "COContractEnergyInfoDetailViewBtnSave";
        btnSave.setImageKey("save");
        btnSave.on("click", me.controller._btnSave_onclick);
        me.addControl(_toolBar);
    }
	
    function _initDataForm()
    {
    	var restUrl = "~/rest/cocontractenergyinfo/";
        /* 初始化 EntityContainer */        
        var formEntityContainer = new mx.datacontainers.FormEntityContainer({
            baseUrl : COContractEnergyInfo.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "guid"
        });
        
        _form = new mx.datacontrols.DataForm({
			
			alias:"COContractEnergyInfoDetailViewDataForm",
			displayPrimaryKey: false,
			fields: [
	        {	name: "contractid", caption: "合同ID", editorType: "TextEditor", visible:false},
	        {	name: "startdate", caption: "开始时间", editorType: "DateTimeEditor", formatString: "yyyy-MM-dd", visible:false},
	        {	name: "enddate", caption: "结束时间", editorType: "DateTimeEditor", formatString: "yyyy-MM-dd", visible:false},
	        {	name: "timeno", caption: "时段序号", editorType: "TextEditor", visible:false},
//	        {	name: "clickstime", caption: "时段序号", editorType: "TextEditor", visible:false},
	        {	name: "period", caption: "电量类型", editorType: "DropDownEditor",onchanged:_getEnergyByMon},
	        {	name: "energy", caption: "电量", editorType: "TextEditor"},
	        {	name: "purchaserprice", caption: "购电方电价", editorType: "TextEditor"},
	        {	name: "sellerprice", caption: "售电方电价", editorType: "TextEditor"},
	        {	name: "price", caption: "成交电价", editorType: "TextEditor"},
//	        {	name: "tradePriceMargin", caption: "价差成交电价", editorType: "TextEditor"},
//	        {	name: "vendeeBreathPrice", caption: "购方违约赔偿电价", editorType: "TextEditor"},
//	        {	name: "saleBreathPrice", caption: "售方违约赔偿电价", editorType: "TextEditor"},
	        {	name: "guid", caption: "GUID", editorType: "TextEditor", visible:false}
		    ],
            entityContainer: formEntityContainer,
            onload:_getContractId
        });
        
        me.addControl(_form);
    }
    function _getContractId(){
    	if(me.getForm().editors.contractid.getValue()==null || me.getForm().editors.contractid.getValue()==""){
			me.getForm().entityContainer.setValue("contractid",me.param);
    	}
		if(me.getForm().editors.startdate.getValue()==null || me.getForm().editors.startdate.getValue() ==""){
			me.getForm().entityContainer.setValue("startdate",me.stDate);
		}
		if(me.getForm().editors.enddate.getValue()==null || me.getForm().editors.enddate.getValue() == ""){
			me.getForm().entityContainer.setValue("enddate",me.enDate);
		}

		me.getForm().editors.contractid.setText(me.param);
		//初始化时，先读取该energy的值
		if(me.objID != null){// && me.getForm().editors.period.getValue() == '1'
			me.energyold = me.getForm().editors.energy.getValue();
		}
		//获取参数，已请求后台获得已存该合同该年度的电量
		if(me.getForm().editors.period !=null){// && me.getForm().editors.period.getValue() == '1'
			var period = "1";//me.getForm().editors.period.getValue();
			var startdate = me.getForm().editors.startdate.getValue();
			var enddate = me.getForm().editors.enddate.getValue();
			var contractid = me.getForm().entityContainer.getValue("contractid");
			var p = restClient.getSync(COContractEnergyInfo.mappath("~/rest/cocontractenergyinfo/getParam"),{"startdate":startdate,"enddate":enddate,"contractid":contractid,"perd":period});
			//电量和
			me.controller.result = p;
		}
		else{
			me.controller.result = null;
		}
	}
	
	//选择‘总’时，去获取电量按月分解数据，若没有手动填
	function _getEnergyByMon(){
		var period = me.getForm().editors.period.getValue();
		var p = null;
		if(period != "" && period != null){
			if(true){//时段为总  period == '1'
				p = restClient.getSync(COContractEnergyInfo.mappath("~/rest/cocontractenergyinfo/getParam"),{"startdate":me.stDate,"enddate":me.enDate,"contractid":me.param,"perd":period});
//				me.controller.result = p;
			}
			//不为总
			else{
				restClient.getSync(COContractEnergyInfo.mappath("~/rest/cocontractenergyinfo/getParam"),{"startdate":me.stDate,"enddate":me.enDate,"contractid":me.param,"perd":period});
//				p = null;
			}
//			//时段为总尖峰平谷时都加以校验
//			p = restClient.getSync(COContractEnergyInfo.mappath("~/rest/cocontractenergyinfo/getParam"),{"startdate":me.stDate,"enddate":me.enDate,"contractid":me.param,"perd":"1"});
			//加载数据
			_form.load(me.objID,function(){
				var editors = me.getForm().editors;
				if(editors != null && editors.length > 0){
					for(i=0;i<editors.length-1;i++){//对非空数据赋值给entityContainer
						var type = typeof(editors[i].value);
						if(editors[i].value != null && editors[i].value !="null"){
							if(editors[i].fieldName == "energy" && editors[i].getValue() == 0){
								me.getForm().entityContainer.setValue("energy","0");
							}else {
								me.getForm().entityContainer.setValue(editors[i].fieldName,editors[i].getValue());
							}
						}
					}
					me.getForm().entityContainer.setValue("period",period);
					me.getForm().editors.period.setValue(period);
				}
				me.controller.result = p;
			});
		}
		else{
			restClient.getSync(COContractEnergyInfo.mappath("~/rest/cocontractenergyinfo/getParam"),{"startdate":null,"enddate":null,"contractid":null,"period":period});
			me.controller.result = null;
		}
	}
    /**
     * 获取表单对象
     */
    me.getForm = function(){
		return _form;
    }
	
    /**
     * 获取工具条
     */
    me.getToolBar = function(){
		return _toolBar;
    }
    
	me.endOfClass(arguments)
    return me;
};