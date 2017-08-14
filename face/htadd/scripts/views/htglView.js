$ns("htadd.views");

//$import("utils.dropDownEditor.contractTypeTree");
$import("mx.rpc.RESTClient");
//$import("utils.dropDownEditor.marketTree");
$import("mx.utils.GUIDUtilClass");


htadd.views.htglView = function(){
	var me = $extend(mx.views.View);
    var base = {};
    var marketId = "";
    
    var restClient = new mx.rpc.RESTClient();
    base.init = me.init;
    var _dataGrid = null;
    
    me.init = function()
    {
        base.init();
        initMarketId();
        _initControls1();
        _initDataGrid();
        search();
    };
    
    
    /**
     * 初始化方法(主要获取初始化参数)
     */
    function initMarketId(){
    	var items = restClient.getSync(htadd.mappath("~/rest/coContractbaseinfoV2Act/init")).resultValue.items;
    	marketId = items[0];
    }
    
    function _initControls1(){
    	me.mainSplit = new mx.containers.HSplit({
			rows : "180,auto",
			width:"100%",
			height:"100%",
			borderThick : 1
		});
		me.addControl(me.mainSplit);
//    	me.mainSplit1 = new mx.containers.HSplit({
//			rows : "60%,auto",width:"100%",
//			height:"100%",
//			borderThick : 0
//		});
    	me.ywcxPanel = new mx.containers.Panel({ name:"panel", title:"业务查询", width:"100%", height:"110",border:0}); 
    	me.ywczPanel = new mx.containers.Panel({ name:"panel1", title:"业务操作", width:"100%", height:"30",border:0}); 
    	me.mainSplit.addControl(me.ywcxPanel,0);
		me.mainSplit.addControl(me.ywczPanel,0);
		
		
		me.ywcxContainer = new mx.containers.Container({
			width:"100%",
			height:"33",
			borderThick : 0
		});
		me.ywcxContainer1 = new mx.containers.Container({
			width:"100%",
			height:"33",
			borderThick : 0
		});
		me.ywcxContainer2 = new mx.containers.Container({
			width:"100%",
			height:"33",
			borderThick : 0
		});
		
		me.ywczContainer = new mx.containers.Container({
			width:"100%",
			height:"100%",
			borderThick : 0
		});
		me.ywcxPanel.addControl(me.ywcxContainer);
		me.ywcxPanel.addControl(me.ywcxContainer1);
		me.ywcxPanel.addControl(me.ywcxContainer2);
		me.ywczPanel.addControl(me.ywczContainer);
		var htTypeLabel = new mx.controls.Label({
			text : "合同类型：",
			width : "80px",
			textAlign: "right", 
			verticalAlign: "middle",
			bottom:"-5px"
		}); 
		me.ywcxContainer.addControl(htTypeLabel);
		
//		me.htTypeSearch = utils.dropDownEditor.ContractTypeTree.ContractTypeDropDownEditor(true,false);
		me.htTypeSearch = new mx.editors.TextEditor(
				{
				    "width" : "12%",
				    "bottom":"-5px",
				    "position":"relative"
				});
		me.htTypeSearch.setText(me.htlxtext);
		me.htTypeSearch.$e.attr("htlx",me.htlx);
		me.htTypeSearch.$e.prop("disabled","disabled");
		me.htTypeSearch.$e.find("input").css("background-color","rgba(31, 20, 20, 0.13)");
		me.htTypeSearch.setReadOnly(true);
		me.ywcxContainer.addControl(me.htTypeSearch);
		
		var htzqLabel = new mx.controls.Label({
			text : "合同周期：",
			width : "80px",
			textAlign: "right", 
			verticalAlign: "middle",
			bottom:"-5px"
		}); 
		me.ywcxContainer.addControl(htzqLabel);
		me.htzqSearch= new mx.editors.DropDownEditor(
    			{
    				displayMember: "name",
    			    valueMember: "value",
    				width : "12%",
    				bottom :"-5px",
    				//displayCheckBox: true,
    				allowDropDown : false,
    			    url: htadd.mappath("~/../ETradePublicUtils/rest/baGencodeTypeList/getList?type=23")
    			});
		me.ywcxContainer.addControl(me.htzqSearch);
		
		var sjlxLabel = new mx.controls.Label({
			text : "时间类型：",
			width : "80px",
			textAlign: "right", 
			verticalAlign: "middle",
			bottom:"-5px"
		}); 
		me.ywcxContainer.addControl(sjlxLabel);
		me.sjlxSearch= new mx.editors.DropDownEditor(
    			{
    				displayMember: "name",
    			    valueMember: "value",
    				width : "12%",
    				bottom :"-5px",
    				//displayCheckBox: true,
    				allowDropDown : false,
    			    url: htadd.mappath("~/../ETradePublicUtils/rest/baGencodeTypeList/getList?type=93")
    			});
		me.ywcxContainer.addControl(me.sjlxSearch);
		
		var kssjLabel = new mx.controls.Label({
			text : "开始时间：",
			width : "80px",
			textAlign: "right", 
			verticalAlign: "middle",
			bottom:"-5px"
		}); 
		me.ywcxContainer.addControl(kssjLabel);
		
		me.kssjSearch = new mx.editors.DateTimeEditor({
			value : me.year,
			formatString : "yyyy-MM-dd",
			bottom:"-5px",
			width:"13%"
		}); // 时间原件
		
		me.ywcxContainer.addControl(me.kssjSearch);
		
		var htztLabel = new mx.controls.Label({
			text : "合同状态：",
			width : "80px",
			textAlign: "right", 
			verticalAlign: "middle"
//			bottom:"-5px"
		}); 
		me.ywcxContainer1.addControl(htztLabel);
		
		me.htztSearch= new mx.editors.DropDownEditor(
    			{
    				displayMember: "name",
    			    valueMember: "value",
    				width : "12%",
//    				bottom :"-5px",
    				//displayCheckBox: true,
    				allowDropDown : false,
    			    url: htadd.mappath("~/../ETradePublicUtils/rest/baGencodeTypeList/getList?type=67")
    			});
		me.ywcxContainer1.addControl(me.htztSearch);
		
    	
    	me.ywcjLabel = new mx.controls.Label({text:"业务场景：",width:"80",textAlign:"right"});	//购电方标签
    	me.ywcxContainer1.addControl(me.ywcjLabel);	//添加标签
    	
    	me.ywcjSearch = utils.dropDownEditor.MarketTree.MarketDropDownEditor(true,"",false); //业务场景
//    	me.ywcjSearch.setReadOnly(true);
    	me.ywcjSearch.$e.css("width","12%");
    	me.ywcxContainer1.addControl(me.ywcjSearch); 
    	
    	me.htxlLabel = new mx.controls.Label({text:"合同序列：",width:"80",textAlign:"right",verticalAlign: "middle"});	//购电方标签
    	me.ywcxContainer1.addControl(me.htxlLabel);	//添加标签
    	me.htSquSearch = new mx.editors.DropDownEditor(
			{
				displayMember: "name",
			    valueMember: "value"
			});
    	var items = restClient.getSync(htadd.
    			mappath("~/../ETradePublicUtils/rest/contracseqinfo/getList?type="+me.htlx)).resultValue;
    	if(items!= null){
    		me.htSquSearch.setItems(items);
    	}
//    	me.htSquSearch = utils.dropDownEditor.ContractSeqInfo.ContractSeqInfoDropDownEditor(true,me.htlx);
    	me.htSquSearch.$e.css("width","12%");
    	me.htSquSearch.setValue(me.htxl);
//    	me.htSquSearch.setText(me.htxltext);
//    	me.htSquSearch.$e.click(
//    		function(){
//    			if(me.htxl!="" && me.htxl!=null){
//    				me.htSquSearch.setValue(me.htxl);
//    			}else{
//    				me.htxl="";
//    			}
//    		}
//    	);
    	me.ywcxContainer1.addControl(me.htSquSearch); 
    	

		var jssjLabel = new mx.controls.Label({
			text : "截止时间：",
			width : "80px",
			textAlign: "right", 
			verticalAlign: "middle"
//			bottom:"-5px"
		}); 
		me.ywcxContainer1.addControl(jssjLabel);
		
		me.jssjSearch = new mx.editors.DateTimeEditor({
			value : me.year,
			formatString : "yyyy-MM-dd",
//			bottom:"-5px",
			width:"13%"
		}); // 时间原件
		
		me.ywcxContainer1.addControl(me.jssjSearch);
    	
	    me.gdfLabel = new mx.controls.Label(
					{	text:"购电方：",
						width:"80",
						textAlign:"right",
						verticalAlign: "middle"}
					);	//购电方标签
		me.ywcxContainer2.addControl(me.gdfLabel);	//添加标签
		
		me.gdfSearch = new mx.editors.DropDownEditor(
					{	displayMember:"name",
						valueMember:"value",
						width:"12%"}
					);	//购电方下拉框
		me.ywcxContainer2.addControl(me.gdfSearch); //添加文本框
		
		setComponent7Items();	//给购电方下拉框赋值
		
		me.sdfLabel = new mx.controls.Label(
					{	text:"售电方：",
						width:"80px",
						textAlign:"right",
						verticalAlign: "middle"}
					);	//售电方标签
		me.ywcxContainer2.addControl(me.sdfLabel);	//添加标签
		
		me.sdfSearch = new mx.editors.DropDownEditor(
					{	displayMember:"name",
						valueMember:"value",
						width:"12%"}
					); //售电方下拉框
		me.ywcxContainer2.addControl(me.sdfSearch);	//添加下拉框
		setComponent8Items();
		
		
		me.queryHtBotton = new mx.controls.Button({ text: "查询合同",width:80,height:30,left:40,bottom:-5});//查询合同
		me.queryHtBotton.on("click", search);
		me.ywczContainer.addControl(me.queryHtBotton);
		
		me.copyHtBotton = new mx.controls.Button({ text: "复制合同",width:80,height:30,left:60,bottom:-5 });//复制合同
		me.copyHtBotton.on("click", _copyContract);
		me.ywczContainer.addControl(me.copyHtBotton);
		
    }
    
    /**
     * 给购电方下拉框赋值
     */
    function setComponent7Items(){
    	var type =  me.htlx;	//获取合同类型值
    	var items = restClient.getSync(htadd.
    			mappath("~/rest/coContractbaseinfoV2Act/getComponentItems"),
    			{type:type,marketId:marketId,value:1}).resultValue;	//获取购电方数据
    	me.gdfSearch.setItems(items);
    }	
    /**
     * 给售电方下拉框赋值
     */
    function setComponent8Items(){
    	var type =  me.htlx;	//获取合同类型值
    	var items = restClient.getSync(htadd.
    			mappath("~/rest/coContractbaseinfoV2Act/getComponentItems"),
    			{type:type,marketId:marketId,value:2}).resultValue;	//获取售电方数据
    	me.sdfSearch.setItems(items);
    }	
    /**
     * 查询合同方法
     */
    function search(){
    	var contracttype = me.htTypeSearch.$e.attr("htlx") == null ? "" : me.htTypeSearch.$e.attr("htlx");	//合同类型
    	var contractcyc = me.htzqSearch.getValue() == null ? "" : me.htzqSearch.getValue();		//合同周期
    	var searchDateType = me.sjlxSearch.getValue() == null ? "" : me.sjlxSearch.getValue();	//时间类型
    	var startDate = me.kssjSearch.getValue() == null ? "" : me.kssjSearch.getValue();	//开始时间
    	var endDate = me.jssjSearch.getValue() == null ? "" : me.jssjSearch.getValue();	//结束时间
    	var prepcontractflag = me.htztSearch.getValue() == null ? "" : me.htztSearch.getValue();	//合同状态
    	var marketid1 = me.ywcjSearch.getValue() == null ? "" : me.ywcjSearch.getValue();	//业务场景
    	var sequenceid ="";	//合同序列
//    	if(me.htxl=="" || me.htxl== null ){
    	sequenceid=me.htSquSearch.getValue() == null ? "" : me.htSquSearch.getValue();
//    	}else{
//    		sequenceid=me.htxl;
//    	}
       	var purchaser = me.gdfSearch.getValue() == null ? "" : me.gdfSearch.getValue();	//购电方
    	var seller = me.sdfSearch.getValue() == null ? "" : me.sdfSearch.getValue();		//售电方
    	
    	
//    	alert(contracttype+"||"+contractcyc+"||"+searchDateType+"||"+startDate+"||"+endDate+"||"+prepcontractflag+"||"+marketid+"||"+sequenceid+"||"+purchaser+"||"+seller);
    	//给dataGrid添加过滤条件
    	_dataGrid.setFilter({contracttype:contracttype,contractcyc:contractcyc,searchDateType:searchDateType,startDate:startDate,
    		endDate:endDate,prepcontractflag:prepcontractflag,purchaser:purchaser,seller:seller,
    		marketid1:marketid1,sequenceid:sequenceid,marketid:marketId});
    	//加载表格数据
    	_dataGrid.load();
    }
	    
    function _initDataGrid()
    {
        var restUrl = "~/rest/coContractCopy/";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : htadd.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "col0"
        });
        /* 初始化 DataGrid */
        _dataGrid = new mx.datacontrols.ComplexGrid({   
			// 构造查询属性。
			alias: "coContractbaseinfoV2ActMainViewDataGrid",
			columns:[
			{	name: "col0", caption: "主键" , editorType: "TextEditor",align:"center",dataAlign:"center"	},         
			{	name: "col1", caption: "主合同编号" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
			{	name: "col2", caption: "合同名称" , editorType: "TextEditor",align:"center",dataAlign:"center"	},  
			{	name: "col3", caption: "合同类型" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
			{	name: "col4", caption: "购电方" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
			{	name: "col5", caption: "售电方" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
			{	name: "col6", caption: "开始时间" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
			{	name: "col7", caption: "截止时间" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
			{	name: "col8", caption: "总电量" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
			{	name: "col9", caption: "电价" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
			{	name: "col10", caption: "备案状态" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
			{	name: "col11", caption: "签订状态" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
//			{	name: "col12", caption: "合同流转状态" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
			{	name: "col13", caption: "合同状态" , editorType: "TextEditor",align:"center",dataAlign:"center"	}
//			{	name: "col14", caption: "维护人" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
//			{	name: "col15", caption: "维护时间" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
//			{	name: "col16", caption: "维护单位" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
//			{	name: "col17", caption: "附件下载" , editorType: "TextEditor",align:"center",dataAlign:"center"	}
            ],
            // 构造列排序条件，如果有多列，则以逗号分隔。例sorter: "school ASC, class DESC"
            displayCheckBox: true,
	        displayPrimaryKey:false,//列表是否显示主键
            allowEditing: false, //列表默认不可编辑
	        pageSize : 10,
            entityContainer: gridEntityContainer,
            displayRowNumber : true,
            displayToolBar : false,
            rowNumberColWidth : 40
        });
        me.mainSplit.addControl(_dataGrid,1);//添加表格
    }
    
	    function _copyContract(){
			if(_dataGrid!=null){
				 if(_dataGrid.getCheckedIDs().length==0){
//		    		 alert("请选择一条数据");
		    		 mx.indicate("info", "请选择一条数据！");
		    		 return false;
		    	 }else if(_dataGrid.getCheckedIDs().length>1){
//		    		 alert("只能选择一条数据");
		    		 mx.indicate("info", "只能选择一条数据！");
		    			 return false;
		    	 }
				 var newContractId = mx.utils.GUIDUtil.newGUID(true);
				 var str=newContractId.split("-");
				 newContractId="";
				 for(var i=0;i<str.length;i++){
					 newContractId+=str[i];
				 }
		    	 var items = restClient.getSync(htadd.mappath("~/rest/coContractCopy/copyContract"),{contractId:_dataGrid.getCheckedIDs()[0],newContractId:newContractId,marketId:marketId}).resultValue.items;
		    	 var str = items[0];
		    	 if(str=='1'){
		    	 	 mx.indicate("info", "复制成功！");
//		    		 alert("复制成功");
		    		 _dataGrid.load();
		    	 }else{
		    	 	 mx.indicate("info", "复制失败！");
//		    		 alert("复制失败");
		    	 }
			}
		}
    
	    me.endOfClass(arguments)
	    return me;
}