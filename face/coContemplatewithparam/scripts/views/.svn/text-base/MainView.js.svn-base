$ns("coContemplatewithparam.views");
$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGridSearchBox");
$import("mx.datacontrols.ComplexGrid");
$import("mx.containers.Panel");
coContemplatewithparam.views.MainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    var dropDownEditor=null;
    //网格列表
    var _dataGrid = null;
    //表单窗口对象
    var _detailWin = null;
    me.downEditor=null;
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
    	    rows : "65px,auto",
    	    borderThick:1
    	});
		me.addControl(me.hSplit);
		me.panel = new mx.containers.Panel({ name:"panel", title:"业务查询", width:"100%", height: 150}); 
		me.panel1 = new mx.containers.Panel({ name:"panel", title:"业务操作", width:"100%",height: 50 }); 
    	
		me.hSplit.addControl(me.panel,0);
		me.hSplit.addControl(me.panel1,1);
		
		me.containers = new mx.containers.Container({
			heigth:"100%",
			padding:"2px"
		});
		me.panel.addControl(me.containers);
		var Label1 = new mx.controls.Label({
			text : "合同文本范围:",
			textAlign:"right",
			width : "90px"
		});
		me.containers.addControl(Label1);
		me.downEditor = utils.dropDownEditor.MarketTree.MarketDropDownEditor(true,"",false); 
//				dropDownEditor = new mx.editors.DropDownTreeEditor( {     displayCheckBox: false, // 设置是否多选
//				 nodes:							 [ { id: "1", text: "北京", itemType: "item", hasChildren: false }, { id: "2",
//				 text: "江苏", itemType: "folder", hasChildren: true, childNodes:[ { id: "2-1", text: "南京", itemType: "item", hasChildren: false	} ] } ],
//				 onchanged: _dropDownEditor_changed
//				 });
//		me.downEditor.onchanged = function(e) {
//			alert(e);
//		}
		me.containers.addControl(me.downEditor);
		var Label2 = new mx.controls.Label({
			text : "合同类型选择:",
			textAlign:"right",
			width : "90px"
		});
		me.containers.addControl(Label2);
		me.downEditor1 = utils.dropDownEditor.ContractTypeTree.ContractTypeDropDownEditor(true,false);
		me.containers.addControl(me.downEditor1);
		var checkListEditor = new mx.editors.CheckListEditor( {type:"checkbox", items:[{text: "显示已失效参数", value: "1"}], onitemchanged:item_changed }); 
		function item_changed() {
			alert(checkListEditor.value);
		}
		me.containers.addControl(checkListEditor);
//		var Label3 = new mx.controls.Label({
//			text : "显示已失效参数",
//			textAlign:"right",
//			width : "90px"
//		});
//		me.containers.addControl(Label3);
		me.containers1 = new mx.containers.Container({
			heigth:"100%",
			border:"1px",
			padding:"2px"
		});
		me.panel1.addControl(me.containers1);
		var button0 = new mx.controls.Button({ 
			text: "查询",left:50,
			onclick:function(){
				_dataGrid.setFilter({marketid:me.downEditor.value});
				if(me.downEditor1.value!=null) {
					_dataGrid.setFilter({contracttype:me.downEditor1.value});
				}
				alert(checkListEditor.value);
				me.controller._loadDataGrid();
			}}); 
		me.containers1.addControl(button0);
		var button1 = new mx.controls.Button({ 
			text: "增加范本",left:50,
			onclick:function(){
			me.controller._btnNew_onclick();
    	}}); 
		me.containers1.addControl(button1);
		var button2 = new mx.controls.Button({ 
			text: "修改范本",left:50,
			onclick:function(){
				me.controller._btnEdit_onclick();
			}}); 
		me.containers1.addControl(button2);
		var button3 = new mx.controls.Button({ 
			text: "删除范本",left:50,
			onclick:function(){
			me.controller._btnDelete_onclick();
    	}}); 
		me.containers1.addControl(button3);		
		var button3 = new mx.controls.Button({ 
			text: "下载范本",left:50,
			onclick:function(){
			me.controller._DownButton_onclick();
    	}}); 
		me.containers1.addControl(button3);		
    }
    function _changed(e) {     alert(e.item.value); }
    function _dropDownEditor_changed()	 {
    	alert("value值发生改变，目前为" +dropDownEditor.value);
	}
    function _initDataGrid()
    {
        var restUrl = "~/rest/cocontemplatewithparam/";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : coContemplatewithparam.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "contracttemplatecode"
        });
        
        /* 初始化 DataGrid */
        _dataGrid = new mx.datacontrols.DataGrid({   
			// 构造查询属性。
			alias: "coContemplatewithparamMainViewDataGrid",
			height:"90%",
			columns:[
	        {	name: "marketid", caption: "市场ID" , editorType: "TextEditor"	},
	        {	name: "contracttype", caption: "合同类型" , editorType: "TextEditor"	},
	        {	name: "contracttemplatecode", caption: "合同范本文号" , editorType: "TextEditor"	},
	        {	name: "contracttemplatename", caption: "合同范本名称" , editorType: "TextEditor"	},
	        {	name: "contracttemplatefile", caption: "合同范本文件" , editorType: "TextEditor"	},
	        {	name: "version", caption: "版本号" , editorType: "TextEditor"	},
	        {	name: "issueddate", caption: "下发时间" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "starteffectivedate", caption: "生效日期" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "endeffectivedate", caption: "失效日期" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd"},
	        {	name: "effectiveflag", caption: "失效标志" , editorType: "TextEditor"	},
	        {	name: "isshare", caption: "是否共享" , editorType: "TextEditor"	},
	        {	name: "descreption", caption: "描述" , editorType: "TextEditor"	}
            ],
            // 构造列排序条件，如果有多列，则以逗号分隔。例sorter: "school ASC, class DESC"
            displayCheckBox: true,
	        displayPrimaryKey:false,//列表是否显示主键
            allowEditing: false, //列表默认不可编辑
	        pageSize : 20,
            entityContainer: gridEntityContainer
        });
        me.hSplit.addControl(_dataGrid,1);
	    //重置toolBar按钮项
        //_resetToolBarItems();
        //me.addControl(_dataGrid);
      //加载表格数据
    	_dataGrid.load();
    }
    
    /**
     * 重置按钮项
     */
    function _resetToolBarItems(){
    	//去除保存按钮
		_dataGrid.toolBar.removeByIndex(1);
		_dataGrid.toolBar.removeByName("save");
		_dataGrid.toolBar.removeByName("moveup");
		_dataGrid.toolBar.removeByName("movedown");
		//插入编辑按钮
		//_dataGrid.toolBar.insertItem(2,"-",true);
		//_dataGrid.toolBar.insertItem(3,{ name: "edit", text: mx.msg("EDIT"), toolTip: mx.msg("EDIT"), imageKey : "edit", onclick: me.controller._btnEdit_onclick},true);
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
    
	me.endOfClass(arguments)
    return me;
};