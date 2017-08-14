$ns("COContractEnergyInfo.views");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGridSearchBox");
$import("mx.datacontrols.ComplexGrid");
$import("mx.datacontrols.DataTree");
$import("mx.containers.Panel");
$import("mx.rpc.RESTClient");
COContractEnergyInfo.views.MainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
//    me.param="CQ20071107114";
    me.mLabel1 = null;
    me.mLabel2 = null;
    var Label1 = null;
    var Label2 = null;
//    me.timeno = null;
    me.clickstime = null;
    me.sdataTimeEditor = null;
    me.edataTimeEditor = null;
    var restClient = new mx.rpc.RESTClient();
    var dataTree=null;
    //网格列表
    var _dataGrid = null;
    //表单窗口对象
    var _detailWin = null;

    base.init = me.init;
    me.init = function()
    {
        base.init();
        _initControls();
    };
    
    function _initControls()
    {
    	initLayout();//初始化布局
	    _initDataGrid();
    	_initDetailWindow();
        me.on("activate", me.controller._onactivate);
    }
    /**
     * 页面初始化布局(主要控制页面控件的布局)
     */
    function initLayout(){
    	me.hSplit = new mx.containers.HSplit({
    	    rows : "50px,auto",
    	    borderThick:1
    	});
		me.addControl(me.hSplit);
		var result = restClient.getSync(COContractEnergyInfo.mappath("~/rest/cocontractenergyinfo/init?contractid="+me.contractid));
		//result.resultValue.items[0]
		var sstr = result.resultValue.items[0][0];
		var estr = result.resultValue.items[0][1];
		
		
		me.mLabel1 = new mx.controls.Label({
			text : "合同开始日期:"+sstr,
			textAlign:"right",
			width : "160px"
		});
		me.mLabel2 = new mx.controls.Label({
			text : "合同结束日期:"+estr,
			textAlign:"right",
			width : "160px"
		});
		Label1 = new mx.controls.Label({
			text : "开始日期:",
			textAlign:"right",
			width : "90px"
		});
		Label2 = new mx.controls.Label({
			text : "结束日期:",
			textAlign:"right",
			width : "90px"
		});
		me.sdataTimeEditor = new mx.editors.DateTimeEditor({formatString: "yyyy-MM-dd" });
		me.edataTimeEditor = new mx.editors.DateTimeEditor({formatString: "yyyy-MM-dd" });
		me.hSplit.addControl(me.mLabel1);
		me.hSplit.addControl(me.mLabel2);
		me.hSplit.addControl(Label1);
		me.hSplit.addControl(me.sdataTimeEditor);
		me.hSplit.addControl(Label2);
		me.hSplit.addControl(me.edataTimeEditor);
		me.vSplit = new mx.containers.VSplit({
			cols:"35%, 65%" 
		});  
		me.hSplit.addControl(me.vSplit,1);
		
		me.panel = new mx.containers.Panel({ name:"panel", title:"时间段信息", width:"100%"}); 
		me.panel1 = new mx.containers.Panel({ name:"panel", title:"电量信息", width:"100%" }); 
		me.vSplit.addControl(me.panel,0);
		me.vSplit.addControl(me.panel1,1);
		
    }
    function _initDataGrid()
    {	
    	var type=me.contractid;
    	//type = "CQ2007110638";
	    var treeEntityContainer = new mx.datacontainers.TreeEntityContainer({
	    	baseUrl : "~/contract/rest/cocontractenergyinfo/getCOContractEnergyTree?type="+type // rest 服务地址规约详见接口设计文档
		});
		dataTree = new mx.datacontrols.DataTree({
			entityContainer : treeEntityContainer,
			displayCheckBox: false, // 是否需要在树中显示选中框 
			onnodeclick: onselectionchanged 
		});
		dataTree.load();
		
		me.panel.addControl(dataTree,0);
    	 var restUrl = "~/rest/cocontractenergyinfo/";
         /* 初始化 EntityContainer */        
         var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
             baseUrl : COContractEnergyInfo.mappath(restUrl),
             iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
             primaryKey: "guid"
         });
		if (me.params.readType != '1') {// 1的时候只读

			var button1 = new mx.controls.Button({
				text : "新增",
				left : 50,
				onclick : function() {
					me.controller._btnNew_onclick();
				}
			});
			me.panel1.addControl(button1, 0);

			var button2 = new mx.controls.Button({
				text : "修改",
				left : 50,
				onclick : function() {
					me.controller._btnEdit_onclick();
				}
			});
			me.panel1.addControl(button2, 0);
			var button3 = new mx.controls.Button({
				text : "删除",
				left : 50,
				onclick : function() {
					me.controller._btnDelete_onclick();
				}
			});
			me.panel1.addControl(button3, 0);
		}
         /* 初始化 DataGrid */
         _dataGrid = new mx.datacontrols.DataGrid({   
 			// 构造查询属性。
 			alias: "cococoMainViewDataGrid",
 			
 			columns:[
 	        {	name: "period", caption: "电量类型" , editorType: "DropDownEditor"	, width:"20%"},
 	        {	name: "energy", caption: "电量" , editorType: "TextEditor", width:"20%"	},
 	        {	name: "purchaserenergy", caption: "购电方电价" , editorType: "TextEditor", width:"20%"	},
 	        {	name: "sellerprice", caption: "售电方电价" , editorType: "TextEditor", width:"20%"	},
 	        {	name: "price", caption: "成交电价" , editorType: "TextEditor", width:"20%"	},
 	        {	name: "guid", caption: "GUID" , editorType: "TextEditor"	}
             ],
             // 构造列排序条件，如果有多列，则以逗号分隔。例sorter: "school ASC, class DESC"
            displayCheckBox: true,
 	        displayPrimaryKey:false,//列表是否显示主键
            allowEditing: false, //列表默认不可编辑
 	        pageSize : 20,
            entityContainer: gridEntityContainer,
 	        create: me.controller._btnNew_onclick,
            remove: me.controller._btnDelete_onclick
         });
         _dataGrid.setFilter({contractid:type});
 	    //重置toolBar按钮项
        // _resetToolBarItems();
         me.panel1.addControl(_dataGrid,0);
	}
    function onselectionchanged(e) {
	    if(e.node.id!=null) {
	    	var contract = e.node.id.split('&');
	    	var dates = e.node.text.split('段')[1].split('至');
//	    	me.clickstime = e.node.text.split('段')[1].split('至')[0];
//	    	me.timeno = e.node.text.split('段')[0].split("第")[1];
	    	me.sdataTimeEditor.setValue(dates[0]);
	    	me.edataTimeEditor.setValue(dates[1]);
	    	_dataGrid.setFilter({contractid:contract[0],startdate:dates[0],enddate:dates[1]});
	    	_dataGrid.load();
	    }
	    else
	    {
//	    	me.timeno = 1;
	    }
	}
	/**
	 * 重置按钮项
	 */
    function _resetToolBarItems(){
    	// 去除保存按钮
		_dataGrid.toolBar.removeByIndex(1);
		_dataGrid.toolBar.removeByName("save");
		_dataGrid.toolBar.removeByName("moveup");
		_dataGrid.toolBar.removeByName("movedown");
		//插入编辑按钮
		_dataGrid.toolBar.insertItem(2,"-",true);
		_dataGrid.toolBar.insertItem(3,{ name: "edit", text: mx.msg("EDIT"), toolTip: mx.msg("EDIT"), imageKey : "edit", onclick: me.controller._btnEdit_onclick},true);
    }

    /**
     * 初始化表单视图窗口对象
     */
    function _initDetailWindow(){
    	_detailWin = utils.commonFun.WinMananger.create({
			reusable: true,//是否复用
			width:640,
			height:480,
			title:"表单维护"
		});
    }

    /**
     * 获取表单视图窗口对象
     */
    me.getDetailWindow = function(){
    	return _detailWin;
    }
    
    /**
     * 获取DataGrid网格列表对象
     */
    me.getDataGrid = function(){
    	return _dataGrid;
    }
    /**
     * 获取dataTree
     */
    me.getDataTree = function(){
    	return dataTree;
    }
    me.getmLabel1 = function(){
    	return me.mLabel1;
    }
    
	me.endOfClass(arguments)
    return me;
};