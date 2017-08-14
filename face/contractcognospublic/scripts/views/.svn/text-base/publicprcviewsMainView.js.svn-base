$ns("contractcognospublic.views");
$import("mx.containers.HtmlContainer");
$import("mx.containers.Panel");
$import("mx.controls.Label");
$import("mx.editors.DropDownEditor");
$import("mx.controls.Button");

contractcognospublic.views.publicprcviewsMainView = function()
{
	var me = $extend(mx.views.View);
    var base = {};
    base.init = me.init;
    me.init = function()
    {
        base.init();

        _initControls();
    };
    
    function _initControls()
    {
    	// 初始化业务查询Panel
    	me._queryPanel = new mx.containers.Panel({
    		name: "queryPanelRight",
    		title: "业务查询",
    		width: "100%",
    		height: "52px"
    	}); 
    	
    	// 在查询容器中添加"年份"标签
    	me._yearLabel = new mx.controls.Label({
    		text: "开始时间:",
    		width:　"80px",
    		textAlign: "center",
    		verticalAlign: "middle"
    	});
    	
    	// 在查询容器中添加"年份"标签
    	me._jsyearLabel = new mx.controls.Label({
    		text: "结束时间:",
    		width:　"80px",
    		textAlign: "center",
    		verticalAlign: "middle"
    	});
    	// 初始化年份查询条件下拉框
    	me.dataTimeEditor = new mx.editors.DateTimeEditor({
    	    formatString: "yyyy-MM-dd"
    	});
    	
    	// 初始化年份查询条件下拉框
    	
    	me.jsdataTimeEditor = new mx.editors.DateTimeEditor({
    	    formatString: "yyyy-MM-dd"
    	});
    	
    	var nowdate = new Date();
    	var oneMonth = nowdate.getFullYear()+"-01-01";
    	if(nowdate.getMonth() == 0){
    		oneMonth = (nowdate.getFullYear()-1)+"-01-01";
    		nowdate = parseDate((nowdate.getFullYear()-1)+"-12-01");
    	}else{
    		var mon = nowdate.getMonth()<10?"-0"+nowdate.getMonth():nowdate.getMonth();
    		nowdate = parseDate(nowdate.getFullYear()+mon+"-01");
    	}
    	me.dataTimeEditor.setValue(parseDate(oneMonth));//默认1月
    	me.jsdataTimeEditor.setValue(nowdate);//默认上月
		// 在业务操作Panel里设置查询按钮
		me.btnQuery = new mx.controls.Button({
			text: "查询",
			left: "20px",
			onclick : me.controller.clickquery
		});
		
		// 在业务操作Panel里设置生成数据按钮
		me.addData = new mx.controls.Button({
			text: "生成数据",
			left: "40px",
			onclick : me.controller.addData,
			visible : me.controller.adddatabuttonhide
		});
		if(me.controller.ifYM){
			me._queryPanel.addControl(me._yearLabel);
			me._queryPanel.addControl(me.dataTimeEditor);
			me._queryPanel.addControl(me._jsyearLabel);
			me._queryPanel.addControl(me.jsdataTimeEditor);
		}else{
			me.dataTimeEditor.setValue(null);
			me.jsdataTimeEditor.setValue(null);
		}
		// 循环加入自定义查询条件 每行最多四个查询条件
		for(var i = 0;i<me.controller.labels.length;i++){
			// 调整条件位置 上下间隔10px
			me.controller.labels[i].setTop(parseInt(me._queryPanel.controls.length/8)*10);
			me.controller.editors[i].setTop(parseInt(me._queryPanel.controls.length/8)*10);
			if(me._queryPanel.controls.length % 8 == 0 && me._queryPanel.controls.length>0){
				me._queryPanel.addControl(me.controller.labels[i],true);
				me._queryPanel.addControl(me.controller.editors[i]);
			}else{
				me._queryPanel.addControl(me.controller.labels[i]);
				me._queryPanel.addControl(me.controller.editors[i]);
			}
		}
		
		// 加入按钮 调整按钮位置
		if(me._queryPanel.controls.length % 8 != 0){
			me.btnQuery.setTop(parseInt(me._queryPanel.controls.length/8)*10);
			me.addData.setTop(parseInt(me._queryPanel.controls.length/8)*10);
		}else{
			me.btnQuery.setTop(parseInt(me._queryPanel.controls.length/8-1)*10);
			me.addData.setTop(parseInt(me._queryPanel.controls.length/8-1)*10);
		}
		
		// 计算查询区域高度
		var queryPHeight = Math.ceil((me._queryPanel.controls.length -1)/8)*40 + 10;
		// 设置查询区域高度
		me._queryPanel.setHeight(queryPHeight<0?50:queryPHeight);
		me._queryPanel.addControl(me.btnQuery);
		me._queryPanel.addControl(me.addData);
		
		//放置cognos报表
    	me.htmlContainer = new mx.containers.HtmlContainer({   
     		  url:me.controller.reporturl,
     		  height:"100%",
     		  width:"100%",
     		  type:"Iframe"
     		 });
    	me.addControl(me._queryPanel);
      	me.addControl(me.htmlContainer);
        me.on("activate", me.controller._onactivate);
    }
    
    return me.endOfClass(arguments);
};