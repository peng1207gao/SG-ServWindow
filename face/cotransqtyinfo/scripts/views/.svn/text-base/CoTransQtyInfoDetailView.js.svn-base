$ns("cotransqtyinfo.views");

$import("mx.controls.ToolBar");
$import("mx.datacontainers.FormEntityContainer");
$import("mx.datacontrols.DataForm");


cotransqtyinfo.views.CoTransQtyInfoDetailView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    /**
     * 表单对象id
     */
    me.objID = null;
    me.coType = null;
    /**
      * 表单对象
     */
    var _form = null;
    
    var _dataGrid = null;
    
    //表单窗口对象
    var _detailWin = null;
    /**
     * 工具条
     */
    var _toolBar = null;
    

    /* 初始化单表单控件 */
    base.init = me.init;
    me.init = function()
    {
        base.init();
        _initControls();
    };

    me.load = function()
    {
    	//加载表单信息
    	_form.load(me.objID);
    }

    function _initControls()
    {
        _initToolBar();
        _initLayout();
	    _initDataForm();
	    _initHtdlxx();
	    _initDetailWindow();
	    _initLeftChart();
        me.on("activate", me.controller._onactivate);
    }
    
    function _initLayout(){
    	me.panel1 = new mx.containers.Panel({
    		id:"panel1",
			border:"1",
			height:400,
			title:"合同输电信息:"
    	});
    	me.addControl(me.panel1);
    	
    	me.panel2 = new mx.containers.Panel({
    		id:"panel2",
			border:"1",
			height:700,
			title:"合同电力信息:"
    	});
    	me.addControl(me.panel2);
    }
	
    function _initToolBar()
    {
    	_toolBar = new mx.controls.ToolBar({
            alias:"cotransqtyinfoCoTransQtyInfoDetailViewToolBar",
            width: "100%"
        });
        var btnSave = _toolBar.appendItem("save", mx.msg("SAVE"));
        btnSave.alias = "cotransqtyinfoCoTransQtyInfoDetailViewBtnSave";
        btnSave.setImageKey("save");
        btnSave.on("click", me.controller._btnSave_onclick);
        me.addControl(_toolBar);
    }
	
    function _initDataForm()
    {
    	var restUrl = "~/rest/cotransqtyinfo/";
        /* 初始化 EntityContainer */        
        var formEntityContainer = new mx.datacontainers.FormEntityContainer({
            baseUrl : cotransqtyinfo.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "transinfoid"
        });
        
        _form = new mx.datacontrols.DataForm({
			
			alias:"cotransqtyinfoCoTransQtyInfoDetailViewDataForm",
			displayPrimaryKey: false,
			fields: [
	        {	name: "transinfoid", caption: "输电信息ID", editorType: "TextEditor", visible:false},
	        {	name: "contractid", caption: "合同ID", editorType: "TextEditor",visible:false},
	        {	name: "transmission", caption: "输电方", editorType: "DropDownEditor",displayMember:"name",valueMember:"value"},
	        {	name: "passagewayname", caption: "输电通道名称", editorType: "TextEditor"},
	        {	name: "passagewayno", caption: "输电通道序号", editorType: "TextEditor",lineBreak:false},
	        {	name: "linkid", caption: "联络线", editorType: "DropDownEditor",onchanged:_initGate},
	        {	name: "linkno", caption: "联络线序号", editorType: "TextEditor",lineBreak:false},
	        {	name: "startgateid", caption: "起点关口", editorType: "DropDownEditor",displayMember:"name",valueMember:"value"},
	        {	name: "endgateid", caption: "终点关口", editorType: "DropDownEditor",lineBreak:false,displayMember:"name",valueMember:"value"},
	        {	name: "transcap", caption: "收费容量", editorType: "TextEditor"},
	        {	name: "lossrate", caption: "线损率", editorType: "TextEditor",lineBreak:false},
	        {	name: "transprice", caption: "输电电价", editorType: "TextEditor"},
	        {	name: "isincludetax", caption: "是否含税", editorType: "DropDownEditor",lineBreak:false},
	        {	name: "startprice", caption: "上网电价", editorType: "TextEditor"},
	        {	name: "endprice", caption: "下网电价", editorType: "TextEditor",lineBreak:false},
	        {	name: "seprate", caption: "联络线分电比例%", editorType: "TextEditor"},
	        {	name: "capfee", caption: "容量电费", editorType: "TextEditor",lineBreak:false},
	        {	name: "transqty", caption: "输电电量", editorType: "TextEditor"},
		    {	name: "capprice", caption: "容量电价", editorType: "TextEditor",lineBreak:false},
	        {	name: "power", caption: "高峰电力", editorType: "TextEditor"},
	        {	name: "peakregurate", caption: "调峰比例%", editorType: "TextEditor",lineBreak:false},
//	        {	name: "spare1", caption: "备用1", editorType: "TextEditor"},
//	        {	name: "spare2", caption: "备用2", editorType: "TextEditor"},
//	        {	name: "spare3", caption: "备用3", editorType: "TextEditor"},
	        {	name: "starttime", caption: "开始时间", editorType: "DateTimeEditor", formatString: "yyyy-MM-dd"},
	        {	name: "endtime", caption: "结束时间", editorType: "DateTimeEditor", formatString: "yyyy-MM-dd",lineBreak:false},
	        {	name: "explanation", caption: "备注", editorType: "TextEditor", textMode: "multiline",width:400}
		    ],
            entityContainer: formEntityContainer,
            onload:_initChanged
        });
        
        _form.on("validate", function(e)
		{
			   // e.param，待校验的表单数据。
			   // e.dataForm，当前表单信息。
			var contractid = me.controller.mainCo.getView().contractid;
			var param = {"contractid":contractid};
        	var restClient = new mx.rpc.RESTClient();
	    	var result = restClient.getSync(cotransqtyinfo.mappath("~/rest/cotransqtyinfo/getContractDate"),
	    		{"params":JSON.stringify(param)}).resultValue;
		    var entityContainer = _form.entityContainer;
		    var starttime = entityContainer.getValue("starttime");//表单中填写的开始时间
		    var endtime = entityContainer.getValue("endtime");//表单中填写的结束时间
		    if(result!=null&&result.items[0].length>0){
		    	var contractStime = result.items[0][0];//该合同的开始时间
		    	var contractEtime = result.items[0][1];//该合同的结束时间
		    	if(starttime!=null && endtime!=null){
			    	if(starttime <= endtime){//合同开始时间不能大于开始时间
			    		if(starttime<contractStime || endtime>contractEtime){
			    			e.successful = false;
					        e.hint = "开始时间或结束时间不在合同时间（"+contractStime+"至"+contractEtime+"）范围内！";
			    		}
			    	}else {
			    		e.successful = false;
			    		e.hint = "开始时间不能大于结束时间！";
			    	}
		    	}
		    }
		});
        me.panel1.addControl(_form);
    }
    
    /**
     * 获取起点关口信息和终点关口信息
     */
    function _initGate(){
    	params = {"linkid":_form.editors.linkid.value};//获取选择的联络线id
    	me.gate = new mx.rpc.RESTClient();
    	me.startGate = me.gate.getSync(cotransqtyinfo.mappath("~/rest/cotransqtyinfo/getStartGate"),
    		{ "params": JSON.stringify(params)}).resultValue;//获取起点关口
    	me.endGate = me.gate.getSync(cotransqtyinfo.mappath("~/rest/cotransqtyinfo/getEndGate"),
    		{ "params": JSON.stringify(params)}).resultValue;//获取终点关口
    	_form.editors.startgateid.reset();//重置
    	_form.editors.startgateid.clearItems();//先清空起点关口下拉框
    	if(me.startGate.items!=null && me.startGate.items.length>0){
//    		for(var i=0;i<me.startGate.items.length;i++){
    			_form.editors.startgateid.setItems(me.startGate.items);//填充起点关口下拉框
    			_form.entityContainer.setValue("startgateid",me.startGate.items[0].value);//实际值
				_form.editors.startgateid.setValue(me.startGate.items[0].name);//显示值
//    		}
    	}
    	_form.editors.endgateid.reset();//重置
    	_form.editors.endgateid.clearItems();//先清空终点关口下拉框
    	if(me.endGate.items!=null && me.endGate.items.length>0){
//    		for(var i=0;i<me.endGate.items.length;i++){
    			_form.editors.endgateid.setItems(me.endGate.items);//填充终点关口下拉框
    			_form.entityContainer.setValue("endgateid",me.endGate.items[0].value);//实际值
				_form.editors.endgateid.setValue(me.endGate.items[0].name);//显示值
//    		}
    	}
    }

    /**
     * 由于起点关口和终点关口下拉框不是用数据字典做的，编辑页面中无法显示显示值，所以在datafrom onload时
     * 手动触发联络线下拉框的onchange事件
     */
    function _initChanged(){
    	var type = me.controller.mainCo.type;
    	_form.entityContainer.setValue("contractid",me.controller.mainCo.getView().contractid);//实际值
    	var restClient = new mx.rpc.RESTClient();
    	me.sdf = restClient.getSync(cotransqtyinfo.mappath("~/../ETradePublicUtils/rest/commonDropEditor/getSdfDownList?type=3&coType="+me.coType)).resultValue;
    	if(type == "add"){
	    	if(me.sdf!=null && me.sdf.length>0){
	    		_form.editors.transmission.setItems(me.sdf);//填充输电方下拉框
	    	}
    	}
    	if(type === "edit"){
    		var restClient1 = new mx.rpc.RESTClient();
    		var seleSdf = restClient1.getSync(cotransqtyinfo.mappath("~/rest/cotransqtyinfo/getSeleSdf?transid="+me.objID)).resultValue;
    		if(me.sdf!=null && me.sdf.length>0){
	    		_form.editors.transmission.setItems(me.sdf);//填充输电方下拉框
	    		if(seleSdf!=null && seleSdf.length>0){
	    			_form.entityContainer.setValue("transmission",seleSdf[0].value);//实际值
					_form.editors.transmission.setValue(seleSdf[0].name);//显示值
	    		}
	    	}
    		_initGate();
    	}
    	//解决因为字数过多下拉框显示不全的问题
		var allEditors = _form.editors;
		var fun = new utils.commonFun.DropDownEditorFun();
		for ( var i = 0; i < allEditors.length; i++) {
			var editor = allEditors[i];
			if (editor.editorType == "DropDownEditor") {
				fun.resizeListEditor(editor);
			}
		}
    }
    
    function _initHtdlxx(){
    	me.container1 = new mx.containers.Container({
    		padding: "2px",
    		height:"4%",
    		border:1
    	});
    	me.panel2.addControl(me.container1);
    	me.container2 = new mx.containers.Container({
    		padding: "2px",
    		height:"44%",
    		border:1
    	});
    	me.panel2.addControl(me.container2);
    	me.container3 = new mx.containers.Container({
    		padding: "2px",
    		height:"52%",
    		border:1
    	});
    	me.panel2.addControl(me.container3);
    	
    	me.addBtn = new mx.controls.Button({
    		text: "新增",
			left: 20,
			onclick:me.controller._btnNew_onclick
    	});
    	me.container1.addControl(me.addBtn);
    	
    	me.editBtn = new mx.controls.Button({
    		text: "修改",
			left: 40,
			onclick:me.controller._btnEdit_onclick
    	});
    	me.container1.addControl(me.editBtn);
    	
    	me.deleBtn = mx.controls.Button({
    		text: "删除",
			left: 60,
			onclick:me.controller._btnDelete_onclick
    	});
    	me.container1.addControl(me.deleBtn);
    	
    	var restUrl = "~/rest/cotransqtyinfo/queryDlqx";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : cotransqtyinfo.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "guid"
        });
        
        /* 初始化 DataGrid */
        _dataGrid = new mx.datacontrols.DataGrid({   
			columns:[
	        {	name: "guid", caption: "GUID" , editorType: "TextEditor"	},
	        {	name: "bight", caption: "电力曲线" , editorType: "TextEditor"},
	        {	name: "startdate", caption: "开始时间" , editorType: "DateTimeEditor",width:"25%"	},
	        {	name: "enddate", caption: "结束时间" , editorType: "DateTimeEditor",width:"25%"	}
//	        {	name: "power", caption: "电力" , editorType: "TextEditor"	,width:"25%"},
//	        {	name: "qtytype", caption: "峰谷属性" , editorType: "TextEditor",width:"25%"	}
//	        {	name: "explanation", caption: "??" , editorType: "TextEditor"	}
            ],
            // 构造列排序条件，如果有多列，则以逗号分隔。例sorter: "school ASC, class DESC"
	        displayRowNumber: true,
            displayCheckBox: true,
	        displayPrimaryKey:false,//列表是否显示主键
            allowEditing: false, //列表默认不可编辑
	        pageSize : 20,
            entityContainer: gridEntityContainer
        });
        me.container2.addControl(_dataGrid);
        var uuid = me.controller.mainCo.uuid;
        if(uuid!=null){
        	_dataGrid.setFilter({transinfoid:uuid});
        	_dataGrid.load();
        }
    }
    
    function _initGetData(){
    	var transInfoId = me.controller.mainCo.uuid;
    	var angularParams = {"transInfoId":transInfoId};	
    	me.restClient = new mx.rpc.RESTClient();
    	var responseContentPie3D = me.restClient.getSync(cotransqtyinfo.mappath("~/rest/cotransqtyinfo/queryLeftChartData"), { "params": JSON.stringify(angularParams)});
		me.pie3DDataXml = responseContentPie3D.resultValue.items[0];
    }
    function _initGetXml(){
    	if(me.container3 == undefined){
			me.container3 = new mx.containers.Container({
	    		padding: "2px",
	    		height:"52%",
	    		border:1
	    	});
	    	me.panel2.addControl(me.container3);
    	}
    	var pie3DChart = new FusionCharts(fusionChart.mappath("$/swf/MSLine.swf"),"MSLine4ID","100%","100%","0","1");//图表的显示
//    	pie3DChart.setXMLUrl(cotransqtyinfo.mappath("$/css/MSLine4.xml"));
    	pie3DChart.setDataXML(me.pie3DDataXml); 
    	
    	me.container3.$e.get(0).align = "center";//内部居中
    	pie3DChart.render(me.container3.$e.get(0));
    }
    
    function _initLeftChart(){
    	_initGetData();
    	_initGetXml();
		
    }
    function sleep(numberMillis){
    	var now = new Date();
    	var exitTime=now.getTime()+numberMillis;
    	while(true){
    		now =new Date();
    		if(now.getTime()>exitTime)
    			return;
    	}
    }
    
    /**
     * 初始化表单视图窗口对象
     */
    function _initDetailWindow(){//cotransqtyinfo.context.windowManager
    	_detailWin = utils.commonFun.WinMananger.create({
			reusable: true,//是否复用
			width:640,
			height:480,
			title:"表单维护"
		});
    }
    me._initLeftChart= function(){
    	_initLeftChart();
    }

    /**
     * 获取表单视图窗口对象
     */
    me.getDetailWindow = function(){
    	return _detailWin;
    }
    
    /**
     * 获取表单对象
     */
    me.getForm = function(){
		return _form;
    }
	
    me.getDataGrid = function(){
    	return _dataGrid;
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