$ns("cocontractparameter.views");

$import("mx.controls.ToolBar");
$import("mx.datacontrols.DataForm");

////////////////////////////////////////////////////////////////////////////////
//合同参数配置---》模版配置详细
cocontractparameter.views.ConfigModuleEditerView = function() {
	var me = $extend(mx.views.View);
	var base = {};
	me.objID = null;

	/* 初始化单表单控件 */
	base.init = me.init;
	me.init = function() {
		if(me.restClient==null)
			me.restClient = new mx.rpc.RESTClient();
		base.init();
		_initControls();
	};

	function _initControls() {
		//全局上下分割
		
		me.hSplit = new mx.containers.HSplit({
			rows : "60px,auto",
			borderThick : 1
		});
		me.addControl(me.hSplit);
		me.panel = new mx.containers.Panel({
			name : "panel",
			title : "业务操作",
			width : "100%",
			height : 55
		});
		me.hSplit.addControl(me.panel, 0);
		me.containers = new mx.containers.Container({
			height : "100%",
			padding : "0px"
		});
		me.panel.addControl(me.containers);
		me.btn_save = new mx.controls.Button({
			text : "保存",
			left : 30,
			onclick:function(){
				var cb_value = me.arrayValue;  //data[0]
				var txt_value = me.arrayText;  //显示名
				var ck_select = me.arrayCheck; //复选框
				var unitId = me.arrayUnitId;//项目ID
				
				var paramsList = "";//组装要传递到后台的保存参数
				for(var i = 0; i < cb_value.length; i++) {
					if(ck_select[i].isChecked()) {
						paramsList +=  cb_value[i].value +","+ txt_value[i].value +"@";
					}
				}
				
				if( paramsList.length > 0) {
					paramsList = paramsList.substring(0, paramsList.length-1);
				}else{
					mx.indicate("info", "你还没有选择任何选项！");
					return;
				}

				if(me.restClient==null)
					me.restClient = new mx.rpc.RESTClient();
				var moduleId = me.modulekey;
				var params = {"moduleId":moduleId, "paramsList":paramsList};
				//大数据传入后台
	    		me.restClient = new mx.rpc.RESTClient();
	    		var responseContent = me.restClient.getSync(cocontractparameter.mappath("~/rest/cocontractparameter/updateCoParam"),{ "params": JSON.stringify(params)});
				if(responseContent){
					mx.indicate("info", "保存成功！");
					me.viewPort.close();//关闭当前窗口并加载列表-----------------------重新load一下mainview的datagrid
				}
				else{
					mx.indicate("info", "保存失败！");
				}
			}
		});
		me.containers.addControl(me.btn_save);
		me.btn_selAll = new mx.controls.Button({
			text : "全选",
			left : 40,
			onclick:function(){
				for ( var i = 0; i < me.arrayCheck.length; i++) {
					me.arrayCheck[i].setValue("T");
				}
			}
		});
		me.containers.addControl(me.btn_selAll);
		me.btn_OffSel = new mx.controls.Button({
			text : "反选",
			left : 50,
			onclick:function(){
				for ( var i = 0; i < me.arrayCheck.length; i++) {
					if(me.arrayCheck[i].isChecked())//target
						me.arrayCheck[i].setValue("F");
					else
						me.arrayCheck[i].setValue("T");
				}
			}
		});
		me.containers.addControl(me.btn_OffSel);
		
		me.btn_close = new mx.controls.Button({
			text : "关闭",
			left : 60,
			onclick:function(){
				me.viewPort.close();//关闭当前窗口
			}
		});
		me.containers.addControl(me.btn_close);
		
		var lb_msg = new mx.controls.Label({ text : "(注：合同范本中需要的标签在后面的\"口\"打\"√\"。如果需要新增标签，请联系运维人员配置)", textAlign : "left", left : 70, width : 500, css : { color:"red" } });
		me.containers.addControl(lb_msg);
		
		
		_initDataForm();
		me.on("activate", me.controller._onactivate);
	}

	/**
	 * 初始化数据界面
	 */
	function _initDataForm() {
		//分组数组
		me.panelArr = new Array();
		me.arrayLabel = new Array(); //label标题
		me.arrayValue = new Array();  //中间值 用来存放data[0]
		me.arrayText = new Array();  //显示项目名称
		me.arrayCheck = new Array(); //复选框
		me.arrayCheckReadOnly = new Array();
		
		me.arrayLeftContainers = new Array();//容器
		me.arrayUnitId = new Array();//主键
		me.arrayLeftContainers = new Array();//容器
		me.arrayUnitId = new Array();//主键
		
		var pn = new mx.containers.Panel({
			name : "panel",
			width : "100%",
			border : "1px"
		});
		//me.hSplit.addControl(me.panel, 0);
		//存放合同标签的VSplit
		var _mainvSplit = new mx.containers.VSplit({
			cols : "50%, 50%",
			height : "auto",
			borderThick : 0
		});
		pn.addControl(_mainvSplit);
		//------------------containers begin
		//存放合同标签的左容器
		var _leftContainers = new mx.containers.Container({
			height : "100%",
			width : "45%",
			padding : "0px"
		});
		_mainvSplit.addControl(_leftContainers, 0);
		//存放合同标签的右容器
		var _rightContainers = new mx.containers.Container({
			height : "100%",
			width : "45%",
			padding : "0px"
		});
		_mainvSplit.addControl(_rightContainers, 1);
		//合同范本ID
		me.modulekey = me.main.contractTempDownEditor.getValue();
		//根据合同范本ID获取详细配置的信息
		var data = _initDetailData(me.modulekey);
		//计算并设置Panel高度
		var pnHeight = (((data.length - data.length % 3) / 3) + (data.length % 3 == 0 ? 0 : 1)) * 30;
		pnHeight = pnHeight <= 60 ? 100 : pnHeight <= 90 ? 120 : pnHeight;
		pn.setHeight(pnHeight);
		//存放panel
		me.panelArr = pn;
		excShowDetailView(data, _leftContainers, _rightContainers);
		//遍历显示panel
		me.hSplit.addControl(pn, 1);
	}
	
	/**
	 * 获取详细配置的数据
	 */
	function _initDetailData(modulekey){
		
		var responseContent = me.restClient.getSync(cocontractparameter.mappath("~/rest/cocontractparameter/findAllParamDic"), { modulekey: modulekey});
		if(responseContent.successful){
			return responseContent.resultValue;
		}
		return null;
	}
	
	/**
	 * 遍历数据，展示配置详细信息
	 */
	function excShowDetailView(data, _leftContainers, _rightContainers){
		//获取远数组长度
		var arrayLeftContainersLen = (me.arrayLeftContainers.length == 0 ? 0 : me.arrayLeftContainers.length);
		var arrayUnitIdLen = (me.arrayUnitId.length == 0 ? 0 : me.arrayUnitId.length);
		var arrayLabelLen = (me.arrayLabel.length == 0 ? 0 : me.arrayLabel.length);
		var arrayValueLen = (me.arrayValue.length == 0 ? 0 : me.arrayValue.length);
		var arrayTextLen = (me.arrayText.length == 0 ? 0 : me.arrayText.length);
		var arrayCheckLen = (me.arrayCheck.length == 0 ? 0 : me.arrayCheck.length);
		var arrayCheckReadonlyLen = (me.arrayCheckReadOnly.length == 0 ? 0 : me.arrayCheckReadOnly.length);
//		debugger;
		if(data && data.length > 0){
			for(var i=0; i< data.length; i++){
				//详细配置项目容器
				me.arrayLeftContainers[arrayLeftContainersLen + i] = new mx.containers.Container({ height : "25px",width : "500px", border : "0px solid grey", padding : "0px" });
//				debugger;
				me.arrayUnitId[arrayUnitIdLen + i]=data[i][3];//项目ID
				var y = i % 2;
				switch (y) {
					case 0:
						//左容器
						_leftContainers.addControl(me.arrayLeftContainers[arrayLeftContainersLen + i]);
						break;
					case 1:
						//右容器
						_rightContainers.addControl(me.arrayLeftContainers[arrayLeftContainersLen + i]);
						break;
					default:
						break;
				}
				
				me.arrayLabel[arrayLabelLen + i] = new mx.controls.Label({ text : data[i][1] ? data[i][1] : data[i][1] + "：", textAlign : "center", width : 250, autoWrap:true});
				me.arrayLabel[arrayLabelLen + i].$e.attr("title",data[i][1] ? data[i][1] : data[i][1]);
				me.arrayLeftContainers[arrayLeftContainersLen + i].addControl(me.arrayLabel[arrayLabelLen + i]);
				
				me.arrayValue[arrayValueLen + i] = new mx.editors.TextEditor({msgtxt:"隐藏名称", width : 0, hint : "隐藏名称", value : data[i][0] ? data[i][0] : data[i][0] ,maxLength:0,maxLength:0, display:"none"});
				me.arrayLeftContainers[arrayLeftContainersLen + i].addControl(me.arrayValue[arrayValueLen + i]);
				
				me.arrayText[arrayTextLen + i] = new mx.editors.TextEditor({msgtxt:"显示名称", width : 200, hint : "显示名称", value : data[i][3] ? data[i][3] : "["+data[i][1]+"]",maxLength:100});
				me.arrayLeftContainers[arrayLeftContainersLen + i].addControl(me.arrayText[arrayTextLen + i]);
				me.arrayCheck[arrayCheckLen + i] = new mx.editors.CheckEditor({ value : data[i][2] != null && data[i][2] != "" ? 'T' : 'F', onchanged:function(e){
					if (e.target.isReqDisplay) {
						mx.indicate("warn", "必填项必须显示。");
						e.target.value="T";
						return true;
					}
				},isReqDisplay:data[i][4]==1 ? true : false });
				me.arrayLeftContainers[arrayLeftContainersLen + i].addControl(me.arrayCheck[arrayCheckLen + i]);
				me.arrayLeftContainers[arrayLeftContainersLen + i].addControl(me.arrayCheckReadOnly[arrayCheckReadonlyLen + i]);
			}
		}
	}
	me.get_btn_save = function()
	{
		return me.btn_save;
	}
	return me.endOfClass(arguments);
};