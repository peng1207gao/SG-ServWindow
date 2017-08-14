$ns("htadd.views");
//$import("displayitemconfig.views.DisplayItemSys");
//$import("contResoByMonth.views.contResoMonViewController");
//$import("coContractgateinfo.views.MainViewController");
//$import("CoContransEnergyInfo.views.DetailViewController");
//$import("cotransqtyinfo.views.CoTransQtyInfoMainViewController");
//$import("COContractEnergyInfo.views.MainViewController");
//$import("contractaffixinfo.views.ContractAffixInfoMainViewController");
//$import("cocontractaccessory.views.MainViewController");
//$import("CoContranslossinfo.views.MainViewController");
//直接新增
htadd.views.zjxzView = function(){
	
	var me = $extend(mx.views.View);
	var base = {};

	var month = null;//合同开始日期与合同截止日期之间间隔的月数
	var cycleName = null;//合同周期显示值
	var cycleValue = null;//合同周期实际值
	var count = 0;//用来标识第几次改变下拉框的值
	
	/* 初始化单表单控件 */
	base.init = me.init;
	me.init = function() {
		if(me.restClient==null)
			me.restClient = new mx.rpc.RESTClient();
		base.init();
		_initControls();
	};

	/**
	 * 初始化主视图
	 */
	function _initControls() {
		//全局上下分割
		me.hSplit = new mx.containers.HSplit({
			rows : "55px,atuo",
			borderThick : 1
		});
		me.addControl(me.hSplit);
		me.panel = new mx.containers.Panel({
			name : "panel",
			title : "业务操作",
			width : "100%",
			height : 50,
			padding : "0px"
		});
		me.hSplit.addControl(me.panel, 0);
		me.containers = new mx.containers.Container({
			height : "100%",
			padding : "0px"
		});
		me.panel.addControl(me.containers);
		if(me.readType != '1'){//1的时候只读
			//保存按钮
			me.btn_save = new mx.controls.Button({
				text : "保存",
				left : 30,
				onclick:me.controller._btnSave_onclick
			});
			me.containers.addControl(me.btn_save);
			
			me.btn_close = new mx.controls.Button({
				text : "关闭",
				left : 60,
				onclick:me.controller.close
			});
			me.containers.addControl(me.btn_close);
		}
		_initCenterPage();//项目配置及模版信息布局
		me.on("activate", me.controller._onactivate);
	}
	
	/**
	 * 项目配置及模版信息布局
	 */
	function _initCenterPage() {
		me.displayPage = new displayitemconfig.views.DisplayItemSys();
		me.displayPage._initDisplayPage();//初始化容器
		me.displayPage.objID = me.objID;
		me.con_down = me.displayPage.con_down;//得到容器
		me.hSplit.addControl(me.con_down, 1);
		var pageId = "69486820-5BAB-D177-18FD";//合同页面
		var cotype = me.coType;//合同类型
		var areaArr = ["合同基本信息","合同经济信息","合同附加信息"];
		me.displayPage.params = {"readType":me.readType,"coName":me.coName,"coType":me.coType};
		var fields = me.displayPage.getFormFields(pageId, cotype, areaArr);//获取字段数组
		areaArr = me.displayPage._initPageItems(pageId);
		for(var i=0;i<areaArr.length;i++){
			if(fields[i]!=null && fields[i].length>0){
				for(var j=0;j<fields[i].length;j++){
					if(fields[i][j].id=="contractstartdate"){
						fields[i][j].onchanged = _initContractCycle;
					}
					if(fields[i][j].id=="contractenddate"){
						fields[i][j].onchanged = _initContractCycle;
					}
					if(fields[i][j].id=="backupstate"){
						fields[i][j].onchanged = _initSignState;
					}
				}
			}
		}
		_initDataForm(fields);
	}
	
	function _initDataForm(fields){
		var formEntityContainer1 = new mx.datacontainers.FormEntityContainer({
			baseUrl:htadd.mappath("~/rest/contractAddRegister/"),
			iscID:"-1",
			loadMeta:false,
			primaryKey:"contractid"
		});
		
		me.dataForm = new mx.datacontrols.DataForm({
			fields: fields,
			id:"dataForm",
			width:"100%",
			padding:"3px",
			maxCols:3,
			entityContainer: formEntityContainer1
		});
		me.dataForm.load(me.objID, function(e){
			me.dataForm.entityContainer.setValue("contracttype",me.coType);
			
			if(me.objID == null || me.objID == ""){
				if(me.dataForm.editors.sequenceid != null){
					me.dataForm.editors.sequenceid.setValue(me.htxl);
					me.dataForm.entityContainer.setValue("sequenceid",me.htxl);
				}
				if(me.dataForm.editors.coVersion != null){
					me.dataForm.editors.coVersion.setValue("V1");
					me.dataForm.editors.coVersion.setReadOnly(true);
				}
			}
			else if(me.dataForm.editors.coVersion != null){
				me.dataForm.editors.coVersion.setReadOnly(true);
			}
//			me.dataForm.editors.contracttype.setValue(me.coTName,true);
			if(me.readType == '1'){//1的时候只读
				var editors = me.dataForm.editors;
				if(editors.length > 0){
					for(var c=0;c<editors.length;c++){
						editors[c].setReadOnly(true);
					}
				}
				
			}
			if(me.dataForm.editors.contracttype != null){
				$("div#contracttype",me.dataForm.$e)[0].innerHTML=me.coTName;
				me.dataForm.editors.contracttype.setReadOnly(true);
			}
			if(me.dataForm.editors.sequenceid != null){
				$("div#sequenceid",me.dataForm.$e)[0].innerHTML=me.htxlName;
				me.dataForm.editors.sequenceid.setReadOnly(true);
			}
			//购电方
			if(me.dataForm.editors.purchaser != null){
				var oldDropDown = me.dataForm.editors.purchaser;
				var value = me.dataForm.editors.purchaser.getValue()
				var items = oldDropDown.items;
				var newItems = new Array();
				if(items.length > 0){
					for (var i = 0; i < items.length; i++) {
						var item = items[i];
						newItems[i] = {text:item.text, value:item.value};
					}
				}
				var a = me.dataForm.editors.purchaser.$e;
    			me.newpurchaser = new mx.editors.DropDownEditor({
    				id:oldDropDown.id,
    				name:oldDropDown.name,
    				width:oldDropDown.width,
					displayMember: "text",
				    valueMember: "value",
					autoMatchInput:true,
	    			allowEditing:true,
	    			items:newItems
				});
				me.newpurchaser.$e.replaceAll(a);
				me.newpurchaser.setValue(value);
				if(me.readType == '1'){//1的时候只读
					me.newpurchaser.setReadOnly(true);
				}
			}
			//售电方
			if(me.dataForm.editors.seller != null){
				var oldDropDown = me.dataForm.editors.seller;
				var value = me.dataForm.editors.seller.getValue();
				var items = oldDropDown.items;
				var newItems = new Array();
				if(items.length > 0){
					for (var i = 0; i < items.length; i++) {
						var item = items[i];
						newItems[i] = {text:item.text, value:item.value};
					}
				}
				var a = me.dataForm.editors.seller.$e;
    			me.newseller = new mx.editors.DropDownEditor({
    				id:oldDropDown.id,
    				name:oldDropDown.name,
    				width:oldDropDown.width,
					displayMember: "text",
				    valueMember: "value",
					autoMatchInput:true,
	    			allowEditing:true,
	    			onchanged:_initPfdj,//自动获取批复电价实现,给售电方增加onchange事件
	    			items:newItems
				});
				me.newseller.$e.replaceAll(a);
				me.newseller.setValue(value);
				if(me.readType == '1'){//1的时候只读
					me.newseller.setReadOnly(true);
				}
			}
			if(me.dataForm.editors.approvedTariff != null){
				if(me.btn=="edit"){
					var pfdjValue = me.dataForm.editors.approvedTariff.getValue();
					_initPfdj();
					me.newapprovedTariff.setValue(pfdjValue);
				}
			}
			
			/**
			 * 开始——维护了发电量，上网电量可以自动带入，维护上网电量，也可以自动带入发电量，上网电量=发电量*（1-厂用电率）
			 */
			//购方发电量
			if(me.dataForm.editors.purchasergen != null){
				var oldPurchasergen = me.dataForm.editors.purchasergen;
				var value = me.dataForm.editors.purchasergen.getValue();
				var a = me.dataForm.editors.purchasergen.$e;
				me.newPurchasergen = new mx.editors.TextEditor({
    				id:oldPurchasergen.id,
    				name:oldPurchasergen.name,
    				width:oldPurchasergen.width,
	    			onchanged:_initGfswdl//自动获取购方上网电量
				});
				me.newPurchasergen.$e.replaceAll(a);
				me.newPurchasergen.setValue(value);
				if(me.readType == '1'){//1的时候只读
					me.newPurchasergen.setReadOnly(true);
				}
			}
			
			//购方上网电量
			if(me.dataForm.editors.purchaserenergy != null){
				var oldPurchaserenergy = me.dataForm.editors.purchaserenergy;
				var value = me.dataForm.editors.purchaserenergy.getValue();
				var a = me.dataForm.editors.purchaserenergy.$e;
				me.newPurchaserenergy = new mx.editors.TextEditor({
    				id:oldPurchaserenergy.id,
    				name:oldPurchaserenergy.name,
    				width:oldPurchaserenergy.width,
	    			onchanged:_initGffdl//自动获取购方发电量
				});
				me.newPurchaserenergy.$e.replaceAll(a);
				me.newPurchaserenergy.setValue(value);
				if(me.readType == '1'){//1的时候只读
					me.newPurchaserenergy.setReadOnly(true);
				}
			}
			
			//售方发电量
			if(me.dataForm.editors.sellergen != null){
				var oldSellergen = me.dataForm.editors.sellergen;
				var value = me.dataForm.editors.sellergen.getValue();
				var a = me.dataForm.editors.sellergen.$e;
				me.newSellergen = new mx.editors.TextEditor({
    				id:oldSellergen.id,
    				name:oldSellergen.name,
    				width:oldSellergen.width,
	    			onchanged:_initSfswdl//自动获取售方上网电量
				});
				me.newSellergen.$e.replaceAll(a);
				me.newSellergen.setValue(value);
				if(me.readType == '1'){//1的时候只读
					me.newSellergen.setReadOnly(true);
				}
			}
			
			//售方上网电量
			if(me.dataForm.editors.sellerenergy != null){
				var oldSellerenergy = me.dataForm.editors.sellerenergy;
				var value = me.dataForm.editors.sellerenergy.getValue();
				var a = me.dataForm.editors.sellerenergy.$e;
				me.newSellerenergy = new mx.editors.TextEditor({
    				id:oldSellerenergy.id,
    				name:oldSellerenergy.name,
    				width:oldSellerenergy.width,
	    			onchanged:_initSffdl//自动获取售方发电量
				});
				me.newSellerenergy.$e.replaceAll(a);
				me.newSellerenergy.setValue(value);
				if(me.readType == '1'){//1的时候只读
					me.newSellerenergy.setReadOnly(true);
				}
			}
			/**
			 * 结束——维护了发电量，上网电量可以自动带入，维护上网电量，也可以自动带入发电量，上网电量=发电量*（1-厂用电率）
			 */
			
			//解决因为字数过多下拉框显示不全的问题
			var allEditors = me.dataForm.editors;
			var fun = new utils.commonFun.DropDownEditorFun();
			for ( var i = 0; i < allEditors.length; i++) {
				var editor = allEditors[i];
				if (editor.editorType == "DropDownEditor") {
					fun.resizeListEditor(editor);
				}
			}
//			//把必填项的*改成红色
//			for(var i = 0;i<me.dataForm.fields.length;i++)
//			{
//			   for(var j =0;j<me.dataForm.fields[i].length;j++)
//			   {
//				   var field = me.dataForm.fields[i][j];
//				   var nullable = field.nullable;
//				   if(!nullable){
//					    var id = field.id;
//					    var caption = field.caption+":";
//					    var html = "<label style='width: 120px;'><a style='color:red'>* </a>"+caption+"</label>";
//					    me.dataForm.$("#"+id).find("label").replaceWith(html);
//				   }
//			   }
//			}
		});
		me.con_down.addControl(me.dataForm);
		
	}
	
	/**
	 * 选择已备案是，自动给签订状态设置已签订
	 */
	function _initSignState(){
		var backupstate = me.dataForm.editors.backupstate.value;
		if(backupstate!=null && backupstate!=""){
			if(backupstate=="1" && me.dataForm.editors.signstate != null){
				me.dataForm.editors.signstate.setValue("1");
				me.dataForm.entityContainer.setValue("signstate","1");
			}
		}
	}
	
	/**
	 * 根据合同开始日期和合同截止日期计算合同周期
	 */
	function _initContractCycle(){
//		debugger;
		var startDate = me.dataForm.editors.contractstartdate.value;//选择的合同开始日期
		var endDate = me.dataForm.editors.contractenddate.value;//选择的合同截止日期
		var restClient = new mx.rpc.RESTClient();
		if(startDate!=null && endDate!=null){
			var result = restClient.getSync(htadd.mappath("~/rest/contractAddRegister/getDays"),
				{"params":JSON.stringify({"startDate":startDate,"endDate":endDate})});
			if(result>=30){
				startDate = startDate.split("-");
				startDate = parseInt(startDate[0])*12+parseInt(startDate[1]);
				endDate = endDate.split("-");
				endDate = parseInt(endDate[0])*12+parseInt(endDate[1]);
				month = Math.abs(startDate - endDate) + 1;
				if(month>=24){
					cycleName = "多年";
					cycleValue = "1";
				}else if(month>=12 && month<24){
					cycleName = "年度";
					cycleValue = "3";
				}else if(month>=2 && month<12){
					cycleName = "多月";
					cycleValue = "2";
				}else if(month>=1 && month<2){
					cycleName = "月度";
					cycleValue = "4";
				}
			}else if(result<=1){
				cycleName = "日";
				cycleValue = "6";
			}else if(result>1 && result<30){
				cycleName = "多日";
				cycleValue = "5";
			}else {
				cycleName = "";
				cycleValue = "";
			}
		}else {
			return ;
		}
		me.dataForm.editors.contractcyc.setValue(cycleValue);
		me.dataForm.entityContainer.setValue("contractcyc",cycleValue);
	}
	
	/**
	 * 合同变更时调用，进而获取cycleName和cycleValue的值
	 */
	me.getContractCycle = function(){
		var startDate = me.dataForm.editors.contractstartdate.value;
		var endDate = me.dataForm.editors.contractenddate.value;
		var restClient = new mx.rpc.RESTClient();
		var result = restClient.getSync(htadd.mappath("~/rest/contractAddRegister/getDays"),
			{"params":JSON.stringify({"startDate":startDate,"endDate":endDate})});
		if(result>=30){
			startDate = startDate.split("-");
			startDate = parseInt(startDate[0])*12+parseInt(startDate[1]);
			endDate = endDate.split("-");
			endDate = parseInt(endDate[0])*12+parseInt(endDate[1]);
			month = Math.abs(startDate - endDate) + 1;
			if(month>=24){
				cycleName = "多年";
				cycleValue = "1";
			}else if(month>=12 && month<24){
				cycleName = "年度";
				cycleValue = "3";
			}else if(month>=2 && month<12){
				cycleName = "多月";
				cycleValue = "2";
			}else if(month>=1 && month<2){
				cycleName = "月度";
				cycleValue = "4";
			}
		}else if(result<=1){
			cycleName = "日";
			cycleValue = "6";
		}else if(result>1 && result<30){
			cycleName = "多日";
			cycleValue = "5";
		}else {
			cycleName = "";
			cycleValue = "";
		}
	}

	/**
	 * 合同周期显示值
	 */
	me.getCycleName = function(){
		return cycleName;
	}
	
	/**
	 * 合同周期实际值
	 */
	me.getCycleValue = function(){
		return cycleValue;
	}
	
	/**
	 * 自动获取批复电价
	 */
	function _initPfdj(){
		
		var sellerId = me.newseller.getValue();//获取售电方下拉框的值
		var restClient = new mx.rpc.RESTClient();
		var result = restClient.getSync(htadd.mappath("~/rest/contractAddRegister/getPfdj"),
			{"params":JSON.stringify({"sellerId":sellerId})});
		if(result.resultValue!=null&&result.resultValue.length>1){//如果查询到多个批复电价，则把批复电价修改成下拉框
			if(me.dataForm.editors.approvedTariff != null){
				var oldEditor = me.dataForm.editors.approvedTariff;
				var newItems = new Array();//新批复电价下拉框的值
				for (var i = 0; i < result.resultValue.length; i++) {
					var item = result.resultValue[i];
					newItems[i] = {text:item.name, value:item.value};
				}
				var a = null;
				if(count==0){//第一次将批复电价修改成下拉框，此时替换的对象是原form表单里的对象
					a = me.dataForm.editors.approvedTariff.$e;
				}else{//非首次修改批复电价为下拉框，此时替换的对象是批复电价修改后的控件对象
					a = me.newapprovedTariff.$e;
				}
				me.newapprovedTariff = new mx.editors.DropDownEditor({
    				id:oldEditor.id,
    				name:oldEditor.name,
    				width:oldEditor.width,
//					displayMember: "text",
					displayMember: "value",
				    valueMember: "value",
//					autoMatchInput:true,
					autoMatchInput:false,
	    			allowEditing:true,
	    			items:newItems
				});
				me.newapprovedTariff.$e.replaceAll(a);
				count++;
//				me.newapprovedTariff.setValue(value);
				if(me.readType == '1'){//1的时候只读
					me.newapprovedTariff.setReadOnly(true);
				}
			}
		
		}else if(result.resultValue!=null&&result.resultValue.length==1){//只查询到一个批复电价，此时批复电价仍然是编辑框
			if(me.dataForm.editors.approvedTariff != null){
				var a = null;
				if(count==0){
					a = me.dataForm.editors.approvedTariff.$e;
				}else{
					a = me.newapprovedTariff.$e;
				}
				me.newapprovedTariff = new mx.editors.TextEditor({
    				id:"approvedTariff",
    				name:"approvedTariff",
    				width:"100%"
				});
				me.newapprovedTariff.setValue(result.resultValue[0].value);
				me.newapprovedTariff.$e.replaceAll(a);
				count++;
//				me.newapprovedTariff.setValue(value);
				if(me.readType == '1'){//1的时候只读
					me.newapprovedTariff.setReadOnly(true);
				}
			}
		}else if(result.resultValue!=null&&result.resultValue.length==0){//没查询到批复电价，此时提示用户
			if(me.dataForm.editors.approvedTariff != null){
				var a = null;
				if(count==0){
					a = me.dataForm.editors.approvedTariff.$e;
				}else{
					a = me.newapprovedTariff.$e;
				}
				me.newapprovedTariff = new mx.editors.TextEditor({
    				id:"approvedTariff",
    				name:"approvedTariff",
    				width:"100%"
				});
//				mx.indicate("info","请先到电价管理模块维护批复电价");
				me.newapprovedTariff.$e.replaceAll(a);
				count++;
//				me.newapprovedTariff.setValue(value);
				if(me.readType == '1'){//1的时候只读
					me.newapprovedTariff.setReadOnly(true);
				}
			}
		}
	}
	
	/**
	 * 根据购方发电量获取购方上网电量
	 */
	function _initGfswdl(){
		var perchaserId = null;
		if(me.newpurchaser!=null){
			perchaserId = me.newpurchaser.getValue();
		}
		//取计划厂用电率并计算 购方上网电量=购方发电量*（1-厂用电率）
		var restClient = new mx.rpc.RESTClient();
		me.gfcydl = restClient.getSync(htadd.mappath("~/rest/contractAddRegister/getCydl"),
			{"params":JSON.stringify({"participantid":perchaserId})});
		var gffdl = me.newPurchasergen.value;//购方发电量
		var gfswdl = restClient.getSync(htadd.mappath("~/rest/contractAddRegister/getDl"),
			{"params":JSON.stringify({"gffdl":gffdl,"cydl":me.gfcydl})});
		if(me.newPurchaserenergy!=null){
			me.newPurchaserenergy.setValue(gfswdl);
		}
	}
	
	/**
	 * 根据购方上网电量获取购方发电量
	 */
	function _initGffdl(){
		var perchaserId = null;
		if(me.newpurchaser!=null){
			perchaserId = me.newpurchaser.getValue();
		}
		//取计划厂用电率并计算 购方发电量=购方上网电量/（1-厂用电率）
		var restClient = new mx.rpc.RESTClient();
		me.gfcydl = restClient.getSync(htadd.mappath("~/rest/contractAddRegister/getCydl"),
			{"params":JSON.stringify({"participantid":perchaserId})});
		var gfswdl = me.newPurchaserenergy.value;//购方上网电量
		var gffdl = restClient.getSync(htadd.mappath("~/rest/contractAddRegister/getDl"),
			{"params":JSON.stringify({"gfswdl":gfswdl,"cydl":me.gfcydl})});
		if(me.newPurchasergen!=null){
			me.newPurchasergen.setValue(gffdl);
		}
	}
	
	/**
	 * 根据售方发电量获取售方上网电量
	 */
	function _initSfswdl(){
		var sellerId = null;
		if(me.newseller!=null){
			sellerId = me.newseller.getValue();
		}
		//取计划厂用电率并计算 售方上网电量=售方发电量*（1-厂用电率）
		var restClient = new mx.rpc.RESTClient();
		me.sfcydl = restClient.getSync(htadd.mappath("~/rest/contractAddRegister/getCydl"),
			{"params":JSON.stringify({"participantid":sellerId})});
		var sffdl = me.newSellergen.value;//售方发电量
		var sfswdl = restClient.getSync(htadd.mappath("~/rest/contractAddRegister/getDl"),
			{"params":JSON.stringify({"sffdl":sffdl,"cydl":me.sfcydl})});
		if(me.newSellerenergy!=null){
			me.newSellerenergy.setValue(sfswdl);
		}
	}
	
	/**
	 * 根据售方上网电量获取售方发电量
	 */
	function _initSffdl(){
		var sellerId = null;
		if(me.newseller!=null){
			sellerId = me.newseller.getValue();
		}
		//取计划厂用电率并计算 售方发电量=售方上网电量/（1-厂用电率）
		var restClient = new mx.rpc.RESTClient();
		me.sfcydl = restClient.getSync(htadd.mappath("~/rest/contractAddRegister/getCydl"),
			{"params":JSON.stringify({"participantid":sellerId})});
		var sfswdl = me.newSellerenergy.value;//售方上网电量
		var sffdl = restClient.getSync(htadd.mappath("~/rest/contractAddRegister/getDl"),
			{"params":JSON.stringify({"sfswdl":sfswdl,"cydl":me.sfcydl})});
		if(me.newSellergen!=null){
			me.newSellergen.setValue(sffdl);
		}
	}
	
	return me.endOfClass(arguments);
}
