$ns("htadd.views");
//$import("htadd.views.zjxzView");
//$import("validate.views.ValidateMainViewController");

htadd.views.zjxzViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    me.saved = false;
//    
//    me.getView = function()
//    {
//        if (me.view == null)
//        {
//            me.view = new htadd.views.zjxzView({controller: me});
//        }
//        return me.view;
//    };
    me.getView = function(e)
    {
        if (me.view == null)
        {
        	if (e == null) {
				e = new Object();
			}
        	e.controller = me;
        	me.view = new htadd.views.zjxzView(e);
        }
        return me.view;
    };
    
    me._btnSave_onclick = function(){
    	var vali = me.view.displayPage.validate(me.view.dataForm);
		if (!vali) {
			return ;
		}
		//对页面某些特殊字段进行特殊校验
		//合同周期
		var contractcyc = me.view.dataForm.editors.contractcyc;
		//开始时间
		var contractstartdate = me.view.dataForm.editors.contractstartdate.getValue();
		//截止日期
		var contractenddate = me.view.dataForm.editors.contractenddate.getValue();
		if(contractcyc != null && contractcyc != "" && contractcyc != "undefined"){
			var _sd = new Date(Date.parse(contractstartdate.replace(/-/g,"/")));
		    var _ed = new Date(Date.parse(contractenddate.replace(/-/g,"/")));
//			var _sd = Date.parse(contractstartdate.replace(/-/g,"/"));
//		    var _ed = Date.parse(contractenddate.replace(/-/g,"/"));
		    //开始时间早于截止日期
		    if(_sd.getFullYear()>_ed.getFullYear()){
					mx.indicate("info", "合同开始时间必须早于截止日期！");
		    		return;
				}
				else if(_sd.getFullYear()==_ed.getFullYear()){
					if((_sd.getMonth()+1)>(_ed.getMonth()+1)){
						mx.indicate("info", "合同开始时间必须早于截止日期！");
			    		return;
					}
					else if((_sd.getMonth()+1)==(_ed.getMonth()+1)){
						if(_sd.getDate()>_ed.getDate()){
							mx.indicate("info", "合同开始时间必须早于截止日期！");
				    		return;
						}
					}
				}
			}
			//校验用户选择的合同周期是否符合合同日期决定的合同周期
			if(me.getView().btn == "edit"){
				me.getView().getContractCycle();//获取cycleName和cycleValue的值
			}
			if(contractcyc.getValue() != me.getView().getCycleValue()){
				mx.indicate("warn","根据所选合同开始日期和合同截止日期计算，合同周期应为“"+me.getView().getCycleName()+"”");
				return ;
			}
			
			//开始时间和结束时间的校验
			if(contractcyc.getValue() == '3' || contractcyc.getValue() == '2' ){//年度3校验,多月2校验
		    	if(_sd.getFullYear()!=_ed.getFullYear()){
		    		mx.indicate("info", "开始日期和截止日期必须为同一年！");
		    		return;
		    	}
			}
			else if(contractcyc.getValue() == '4' || contractcyc.getValue() == '5'){//月度4校验，多日5校验
				if(_sd.getMonth()!=_ed.getMonth()){
		    		mx.indicate("info", "开始日期和截止日期必须为同一月！");
		    		return;
		    	}
			}
			if(me.view.dataForm.editors.purchaser != null){
				me.view.dataForm.entityContainer.setValue("purchaser",me.view.newpurchaser.getValue());
			}
			if(me.view.dataForm.editors.seller != null){
				me.view.dataForm.entityContainer.setValue("seller",me.view.newseller.getValue());
			}
			//批复电价
			if(me.view.dataForm.editors.approvedTariff != null && me.view.newapprovedTariff != null && me.view.newapprovedTariff!=undefined){
				me.view.dataForm.entityContainer.setValue("approvedTariff",me.view.newapprovedTariff.getValue());
			}
			//购方发电量
			if(me.view.dataForm.editors.purchasergen != null){
				me.view.dataForm.entityContainer.setValue("purchasergen",me.view.newPurchasergen.getValue());
			}
			//购方上网电量
			if(me.view.dataForm.editors.purchaserenergy != null){
				me.view.dataForm.entityContainer.setValue("purchaserenergy",me.view.newPurchaserenergy.getValue());
			}
			//售方发电量
			if(me.view.dataForm.editors.sellergen != null){
				me.view.dataForm.entityContainer.setValue("sellergen",me.view.newSellergen.getValue());
			}
			//售方上网电量
			if(me.view.dataForm.editors.sellerenergy != null){
				me.view.dataForm.entityContainer.setValue("sellerenergy",me.view.newSellerenergy.getValue());
			}
    	var a = me.view.dataForm.save(function(e){
    		me.view.displayPage.objID = e.resultValue.items[0].contractid;//新增时保存完传合同id
    		me.view.displayPage.params.coName = e.resultValue.items[0].contractname;//供输电方使用的合同名称
    		me.saved = true;
    		mx.indicate("info", "保存成功");
    	});
	}
	
	/**
	 * 关闭
	 */
	me.close = function(){
		if(!me.saved && me.view.btn=="add"){
			if(confirm("信息未保存，关闭后未保存的信息将丢失！确定关闭？")){
				me.view.mv._openWin.hide();
			}else{
				return;
			}
		}else if(!me.saved && me.view.btn=="edit"){
			var entityContainer = me.view.dataForm.entityContainer;
			if(entityContainer!=null){
				if(entityContainer.changed==true){
					if(confirm("信息未保存，关闭后未保存的信息将丢失！确定关闭？")){
						me.view.mv._openWin.hide();
					}else{
						return;
					}
				}
			}
			me.view.mv._openWin.hide();
		}
		me.view.mv._openWin.hide();
	}

    me._onactivate = function(e)
    {
    };
    me.endOfClass(arguments);
    return me;
};