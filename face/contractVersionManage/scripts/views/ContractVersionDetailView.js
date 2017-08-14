$ns("contractVersionManage.views");

$import("mx.controls.ToolBar");
$import("mx.datacontainers.FormEntityContainer");
$import("mx.datacontrols.DataForm");


contractVersionManage.views.ContractVersionDetailView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    /**
     * 表单对象id
     */
    me.objID = null;
    /**
      * 表单对象
     */
    var _dataGrid = null;

    /* 初始化单表单控件 */
    base.init = me.init;
    me.init = function()
    {
        base.init();
        _initControls();
    };

    me.load = function()
    {
    	_dataGrid.setFilter(JSON.stringify({"contractid":me.objID}));
    	_dataGrid.load();
    }

    function _initControls()
    {	
    	_initHsplit();
    	_initOperatePanel();
    	_initBtn();
	    _initDataGrid();
        me.on("activate", me.controller._onactivate);
    }
    
    function _initHsplit(){
    	me.hSplit = new mx.containers.HSplit({
    		rows:"60, auto"
		});
		me.addControl(me.hSplit);
    }
    
    function _initOperatePanel(){
    	me.operatePanel = mx.containers.Panel({ name: "panel", title: "业务操作", width: "100%", height: 50 });
    	
    	me.hSplit.addControl(me.operatePanel,0);
    }
    
    function _initBtn(){
    	var button = new mx.controls.Button({
	    	text: "回滚"
		});
		button.on("click", contractRollBack);
		me.operatePanel.addControl(button);
    }
    
    function contractRollBack(){
    	if(_dataGrid.selection == null){
    		mx.indicate("info","请选择一条记录");
    		return;
    	}
    	
    	if(confirm("确定要回滚当前选择的合同信息吗？")){
	    	var versionId = _dataGrid.selection.values["versionid"]; 
	    	var data = {"versionId" : versionId,"contractid": me.objID};//recordId历史版本,contractid现有版本
	    	
			var startUrl = contractVersionManage.mappath("~/rest/contractVerion/rollBack");
			me.client = new mx.rpc.RESTClient(); 
			var result = me.client.getSync(startUrl, { "params": JSON.stringify(data)});
			if(result.resultValue == "success"){
				mx.indicate("info","版本更新成功");
				// TODO 最初表格书刷新，重新load 带入页号参数
				me.parent.hide();
			} else {
				mx.indicate("info","版本更新失败");
			}
		}
    }
	
    function _initDataGrid()
    {
        var restUrl = "~/rest/contractVerion/hisVersionCompared";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : contractVersionManage.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "contractid",
            loadMeta:false
        });
        
        _dataGrid = new mx.datacontrols.ComplexGrid({   
			// 构造查询属性。
			alias: "contractVersionDetailViewDataGrid",
			
			columns:[
			{   id:"radio",name:"detailradio",renderCell: me._radioRender,width:"30"},
	        {	name: "contractid", caption: "contractid" , editorType: "TextEditor",align:"center",dataAlign:"center"},
	        {	name: "description", caption: "说明" , editorType: "TextEditor",align:"center",dataAlign:"center"},
	        {	name: "updatetime", caption: "信息更新时间" , editorType: "DateTimeEditor",align:"center",dataAlign:"center", formatString: "yyyy-MM-dd HH:mm:ss",width:"150"},
	        {	name: "version", caption: "版本号" , editorType: "TextEditor",align:"center",dataAlign:"center"},
	        {	name: "contractname", caption: "合同名称" , editorType: "TextEditor",align:"center",dataAlign:"center"},
	        {	name: "papercontractcode", caption: "纸质合同编号" , editorType: "TextEditor", width:"150",align:"center",dataAlign:"center"	},
	        {	name: "purchaseparticipantname", caption: "购方市场成员" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
	        {	name: "sellparticipantname", caption: "售方市场成员" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
	        {	name: "contractprice", caption: "合同电价" , editorType: "TextEditor", width:"80",align:"center",dataAlign:"center"	},
	        {	name: "energy", caption: "合同电量" , editorType: "TextEditor",width:"100",align:"center",dataAlign:"center"	},
	        {	name: "contractcycle", caption: "合同周期" , editorType: "TextEditor",width:"100",align:"center",dataAlign:"center"	},
	        {	name: "contractprepare", caption: "预合同标志" , editorType: "TextEditor",width:"100",align:"center",dataAlign:"center"	},
			{	name: "contractstartdate", caption: "合同开始时间" , editorType: "DateTimeEditor",align:"center",dataAlign:"center", formatString: "yyyy-MM-dd", width:"90"},
	        {	name: "contractenddate", caption: "合同结束时间" , editorType: "DateTimeEditor",align:"center",dataAlign:"center", formatString: "yyyy-MM-dd", width:"90"},
	        {	name: "signstate", caption: "签订状态" , editorType: "TextEditor", width:"60",align:"center",dataAlign:"center"	},
	        {	name: "backupstate", caption: "备案状态" , editorType: "TextEditor", width:"60",align:"center",dataAlign:"center"	},
	        {	name: "isrelation", caption: "关联权限" , editorType: "DropDownEditor", width:"60",align:"center",dataAlign:"center"	},
	        {	name: "exectype", caption: "合同执行类型" , editorType: "TextEditor", width:"60",align:"center",dataAlign:"center"	},
	        {	name: "maintainperson", caption: "合同更新人", editorType: "TextEditor",align:"center",dataAlign:"center"},
//	        {	name: "signeddate", caption: "合同签订时间" , editorType: "DateTimeEditor", width:"150",align:"center",dataAlign:"center",formatString: "yyyy-MM-dd"	},
//	        {	name: "signedperson", caption: "合同签订人" , editorType: "TextEditor",align:"center",dataAlign:"center", width:"200"},
//	        {	name: "signedsite", caption: "合同签订地点" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
//	        {	name: "contractsettle", caption: "合同结算方" , editorType: "TextEditor", width:"60",align:"center",dataAlign:"center"	},
//	        {	name: "sequenceid", caption: "合同序列" , editorType: "TextEditor", width:"60",align:"center",dataAlign:"center"	},
//	        {	name: "flowstate", caption: "合同流程状态" , editorType: "TextEditor", width:"60",align:"center",dataAlign:"center"	},
//	        {	name: "purchaserperson", caption: "购电方联系人" , editorType: "TextEditor", width:"80",align:"center",dataAlign:"center"	},
//	        {	name: "purchaserphone", caption: "购电方联系电话" , editorType: "TextEditor", width:"80",align:"center",dataAlign:"center"	},
//	        {	name: "purchaserconftime", caption: "购电方确认时间" , editorType: "DateTimeEditor", width:"80",align:"center",dataAlign:"center",formatString: "yyyy-MM-dd"	},
//	        {	name: "sellerperson", caption: "售电方联系人" , editorType: "TextEditor", width:"80",align:"center",dataAlign:"center"	},
//	        {	name: "sellerphone", caption: "售电方联系电话" , editorType: "TextEditor", width:"80",align:"center",dataAlign:"center"	},
//	        {	name: "sellerconftime", caption: "售电方确认时间" , editorType: "DateTimeEditor", width:"80",align:"center",dataAlign:"center",formatString: "yyyy-MM-dd"	},
		    {	name: "versionid", caption: "版本id", editorType: "TextEditor",align:"center",dataAlign:"center"}
            ],
            displayCheckBox: false,
	        displayPrimaryKey:false,//列表是否显示主键
            allowEditing: false, //列表默认不可编辑
	        pageSize : 20,
            entityContainer: gridEntityContainer,
            displayToolBar:false,
            onselectionchanged:me._radioSelected,
            displayColSplitLine:true,//表格分割线
            onload:setVisible,
            autoWrap:true
        });
        
        me.hSplit.addControl(_dataGrid,1);
    }
    
    /**
     * 创建radio表单
     */
	me._radioRender = function(p_item, $p_cell)
	{	
		var versionid = p_item.values.versionid;
		$("<input type='radio' id='detailradio"+versionid+"' name='radio' value='1'>").appendTo($p_cell).on("click",
    			function(){   					
    					//me._radio_Check(p_item);
				}).css("cursor","hand"); 
	}
    
	/**
	 * 点击每行数据的时候，radio设置为选中效果
	 */
    me._radioSelected = function(e)
	{	
		if(typeof(e.target) =="undefined" || e.target.selection == null){
			return;
		} else {
			var versionid = e.target.selection.values.versionid;
			$("input[type=radio ][id='detailradio"+versionid+"']").attr("checked","checked");
		}
	};
    
    function setVisible(){
    	_dataGrid.columns["versionid"].setVisible(false);
      	
    	for(var i=1;i<_dataGrid.items.length;i++){
    		for(var j = 0; j< _dataGrid.columns.length; j++){
    			var columnName = _dataGrid.columns[j].name;
    			if(_dataGrid.items[i].values[columnName]!=_dataGrid.items[i-1].values[columnName]){
    				_dataGrid.items[i-1].$e.find("'#"+columnName+"'").css("color","red");
            	}
    		}
    		
    		
    	}  	
    	
    }
	
	me.endOfClass(arguments)
    return me;
};