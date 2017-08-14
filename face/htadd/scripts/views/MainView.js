$ns("htadd.views");

$import("mx.containers.HSplit");
$import("mx.containers.Container");
$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGridSearchBox");
$import("mx.datacontrols.ComplexGrid");
$import("mx.containers.Panel");
$import("mx.datacontrols.DataGrid");
$import("mx.utils.CheckUtilClass");
//$import("utils.dropDownEditor.contractTypeTree");
//$import("utils.commonFun.utilFun");
$import("mx.windows.WindowManager");
//$import("utils.dropDownEditor.contractSeqInfo");
//$import("htadd.views.htglView");
//$import("htadd.views.zjxzView");
//$import("htadd.views.zjxzViewController");

htadd.views.MainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    //表单窗口对象
    var _htaddWin = null;
    var _htglAddwin=null;
    var tenHtlx=null;
    base.init = me.init;
    me.init = function()
    {
        base.init();
        _initDetailWindow();
        vb();
    };
    
    function _initDetailWindow(){
    	me.mainSplit = new mx.containers.HSplit({
			rows : "50%,50%",
			width:"100%",
			height:"100%",
			borderThick : 0
		});
    	me.addControl(me.mainSplit);
    	me.mainSplit1 = new mx.containers.HSplit({
			rows : "50%,auto",width:"100%",
			height:"100%",
			borderThick : 0
		});
    	me.mainSplit2 = new mx.containers.HSplit({
			rows : "50%,auto",width:"100%",
			height:"100%",
			borderThick : 0
		});
    	me.mainSplit.addControl(me.mainSplit1,0)
    	me.mainSplit.addControl(me.mainSplit2,1)
//    	me.condContainer.addControl(me.mainSplit);
    	//查询条件
    	var xing = new mx.controls.Label({
			text : "*",
			width:"60px",
			css:{"color":"red"},
			textAlign: "right", 
			bottom:"-10px"
		}); 
    	var htTypeLabel = new mx.controls.Label({
			text : "合同类型：",
			width:"60px",
			textAlign: "left", 
			verticalAlign: "middle",
			bottom:"-10px"
		}); 
    	
    	//合同类型
    	me.condContainer1 = new mx.containers.Container({
			"height" : "100%",
			"width" : "100%"
//			padding:"2px"
		});
		me.condContainer1.addControl(xing);
    	me.condContainer1.addControl(htTypeLabel);
		me.htTypeSearch = utils.dropDownEditor.ContractTypeTree.ContractTypeDropDownEditor(true,false);
		me.htTypeSearch.$e.css("bottom","-10px");
		me.condContainer1.addControl(me.htTypeSearch);
		me.mainSplit1.addControl(me.condContainer1,0);
		me.htTypeSearch.on("changed",vb);
		//合同序列
    	me.condContainer2 = new mx.containers.Container({
			"height" : "100%",
			"width" : "100%"
//			padding:"2px"
		});
    	//查询条件
    	var htSquLabel = new mx.controls.Label({
			text : "合同序列：",
			width : "120px",
			textAlign: "right", 
			verticalAlign: "middle"
		}); 
    	me.condContainer2.addControl(htSquLabel);
    	me.htSquSearch = null;
//    	me.htSquSearch = utils.dropDownEditor.ContractSeqInfo.ContractSeqInfoDropDownEditor(true,tenHtlx);
//		me.condContainer2.addControl(me.htSquSearch);
		me.mainSplit1.addControl(me.condContainer2,1);
		
		//合同类型
    	me.condContainer3 = new mx.containers.Container({
			"height" : "100%",
			"width" : "100%"
//			padding:"2px"
		});
    	//查询条件
    	var xzfsLabel = new mx.controls.Label({
			text : "新增方式：",
			width : "120px",
			textAlign: "right", 
			verticalAlign: "middle"
		}); 
    	me.condContainer3.addControl(xzfsLabel);
    	var strRadio="<label><input type='radio' name='htxzRadio' value='1' checked><span style='12px;'>直接新增</span> </label>&nbsp;&nbsp;&nbsp;&nbsp;" +
    			"<label><input type='radio' name='htxzRadio' value='2'><span style='12px;'>合同复制 </span></label>"
		me.condContainer3.$e.append(strRadio);
		me.mainSplit2.addControl(me.condContainer3,0);
		
		//合同类型
    	me.condContainer4 = new mx.containers.Container({
			"height" : "100%",
			"width" : "100%"
//			padding:"2px"
		});
    
    	me.qxButton= new mx.controls.Button({
			text : "取消",
			left:90
		});
		me.qxButton.$e.find("input").css("width","70");
    	me.qxButton.on("click", function()
			{
				me.mv._openWin.hide();
			});
    	me.condContainer4.addControl(me.qxButton);
    	
    	me.lastButton= new mx.controls.Button({
			text : "确认",
			left:120
		});
		me.lastButton.$e.find("input").css("width","70");
    	me.lastButton.on("click", function()
    			{
    				if(!checkPar()){
    					return false;
    				}
    				var xzfs=$("[name=htxzRadio]:checked").val();
    				var htlx=me.htTypeSearch.getValue()==null?"":me.htTypeSearch.getValue();
    				var coTName = me.htTypeSearch.text==null?"":me.htTypeSearch.text;
    				var htxl=me.htSquSearch.getValue()==null?"":me.htSquSearch.getValue();
    				var htxlName=me.htSquSearch.text==null?"":me.htSquSearch.text;
    				var _openView =null;
    				if(xzfs=='1'){
    					_openView=new htadd.views.zjxzViewController().getView({objID:null,readType:'0',coType:htlx,coTName:coTName,htxl:htxl,htxlName:htxlName,mv:me,btn:"add"});	
    				}else{
    					_openView=new htadd.views.htglView({htlx:htlx,htxl:htxl,xzfs:xzfs,htlxtext:me.htTypeSearch.text,htxltext:me.htSquSearch.text});	
    				}
    				me._openWin = new mx.windows.WindowManager().create({
		    			reusable: true,//是否复用
		    			width:"90%",
		    			height:"90%",
		    			title:"合同详细信息"
		    		});
    				me._openWin.setView(_openView);
    				me.mv._openWin.hide();
		    		me._openWin.showDialog(function(){
		    			me.mv._refresh();
		    		});
    			});
    	me.condContainer4.addControl(me.lastButton);
		me.mainSplit2.addControl(me.condContainer4,1);
    }
	
	function vb(){
		me.condContainer2.removeControl(me.htSquSearch);
		tenHtlx=me.htTypeSearch.getValue();
		me.htSquSearch = utils.dropDownEditor.ContractSeqInfo.ContractSeqInfoDropDownEditor(true,tenHtlx);
		me.condContainer2.addControl(me.htSquSearch);
	}
	//检查参数
	function checkPar(){
		var xzfs=$("[name=htxzRadio]:checked").val();
		if(xzfs=="1"){
			var tenStr=me.htTypeSearch.getValue();
			if(tenStr==null||tenStr==""){
//				alert("请选择合同类型");
				mx.indicate("info", "请选择合同类型！");
				return false;
			}
			tenStr=me.htSquSearch.getValue();
			if(tenStr==null||tenStr==""){
//				alert("请选择合同序列");
				mx.indicate("info", "请选择合同序列！");
				return false;
			}
		}
		else{
			var tenStr=me.htTypeSearch.getValue();
			if(tenStr==null||tenStr==""){
//				alert("请选择合同类型");
				mx.indicate("info", "请选择合同类型！");
				return false;
			}
		}
		return true;
		
	}
	me.endOfClass(arguments)
    return me;
};