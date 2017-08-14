$ns("cocontractparameter.views");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGridSearchBox");
$import("mx.datacontrols.DataGrid");
$import("mx.rpc.RESTClient");
$import("mx.containers.HSplit");
$import("mx.containers.Panel");
$import("mx.containers.Container");

////////////////////////////////////////////////////////
//合同管理-----》文本管理------》合同参数配置--范本标签配置
cocontractparameter.views.MainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    //网格列表
    var _dataGrid = null;
    //表单窗口对象
    var _detailWin = null;

    var restClient = new mx.rpc.RESTClient();

    base.init = me.init;
    me.init = function()
    {
        base.init();
        _initControls();
    };
    
    function _initControls()
    {
    	//页面上面分成上下两部分
    	me.mSplit = new mx.containers.HSplit({rows:"120px,auto",borderThick:1});
    	me.addControl(me.mSplit);
    	
    	me.panel1 = new mx.containers.Panel({title:"业务查询", width:"100%", height:55});
    	me.mSplit.addControl(me.panel1,0);//添加panel1
    	me.container1 = new mx.containers.Container({padding:"2px",width:"100%"});
    	me.panel1.addControl(me.container1);//添加container1
    	var contractTempLabel = new mx.controls.Label({
    	    text: "合同范本：",
    	    textAlign: "right",
    	    width:"8%",
    	    right:4,
    	    verticalAlign: "middle"
    	});
    	//合同类型下拉框
    	me.contractTempDownEditor = utils.dropDownEditor.ContractTemplate.ContractTemplateDropDownEditor(false,null); 
    	me.contractTempDownEditor.setWidth("300px");
    	me.container1.addControl(contractTempLabel);
    	me.container1.addControl(me.contractTempDownEditor);
    	
    	me.panel2 = new mx.containers.Panel({title:"业务操作", width:"100%", height:55});
    	me.mSplit.addControl(me.panel2,0);//添加panel2
    	me.container2 = new mx.containers.Container({padding:"2px",width:"100%"});
    	me.panel2.addControl(me.container2);//添加container2
    	
    	//查询按钮
		var btn_search = new mx.controls.Button({text : "查询",left : 20});
		me.addControl(btn_search);
		var contracttemplateId = null;//合同范本ID
		//查询按钮点击事件
		btn_search.on("click", function() {
			contracttemplateId = me.contractTempDownEditor.getValue();
			var filter = {};
			filter.contracttemplateId = contracttemplateId;
			me.getDataGrid().setFilter(filter);
			me.getDataGrid().load();
		});
		me.container2.addControl(btn_search);
		
		//批量维护按钮
		var btn_plwh = new mx.controls.Button({text : "批量维护",left : 40});
		me.addControl(btn_plwh);
		//批量维护按钮点击事件
		btn_plwh.on("click", function() {
			if(me.contractTempDownEditor.getValue() != "" && me.contractTempDownEditor.getValue() != null){
				me._openView = utils.commonFun.WinMananger.create({
					reusable : true,
					width : "95%",
					height : "95%"
				});
				var _detailView = new cocontractparameter.views.ConfigModuleEditerViewController().getView({winView : me._openView,main:me.controller.getView()});
				me._openView.setView(_detailView);
				var p_title = "模板配置详细";
				me._openView.setTitle(p_title ? p_title : _detailView.title);
				me._openView.showDialog(function(){
					_detailView.get_btn_save().on("click", function()
						{
							me.getDataGrid().setFilter({contractTemp:me.contractTempDownEditor.getValue()});
							me.getDataGrid().load();
						});
				});
			}else{
				mx.indicate("info", "请选择合同范本");
			}
		});
		me.container2.addControl(btn_plwh);
		
		//修改标签按钮
//		var btn_updparam = new mx.controls.Button({text : "修改标签",left : 60});
//		me.addControl(btn_updparam);
//		//修改标签按钮点击事件
//		btn_updparam.on("click", function() {
//			var v_dataGrid = me.getDataGrid();
//    		
//    		if (v_dataGrid.getCheckedIDs().length == 0) {
//    			mx.indicate("info", "请勾选一条待修改记录。");
//                return;
//            }else if(v_dataGrid.getCheckedIDs().length > 1) {
//            	mx.indicate("info", "只能勾选一条待修改记录。");
//                return;
//            }else {
//            	me.controller._btnEdit_onclick();
//    		}
//		});
//		me.container2.addControl(btn_updparam);
		
		//删除标签按钮
		var btn_delparam = new mx.controls.Button({text : "删除标签",left : 60});
		me.addControl(btn_delparam);
		//删除标签按钮点击事件
		btn_delparam.on("click", function() {
			var v_dataGrid = me.getDataGrid();
    		
    		if (v_dataGrid.getCheckedIDs().length == 0) {
    			mx.indicate("info", "请选择要删除的记录");
                return;
            }else {
            	me.controller._btnDelete_onclick();
            }
		});
		me.container2.addControl(btn_delparam);
		
		//复制标签按钮
		var btn_copyparam = new mx.controls.Button({text : "复制标签",left : 80});
		me.addControl(btn_copyparam);
		//复制标签按钮点击事件
		btn_copyparam.on("click", function() {
			var v_dataGrid = me.getDataGrid();
    		
    		if (v_dataGrid.getCheckedIDs().length == 0) {
    			mx.indicate("info", "请选择要复制的记录");
                return;
            }else if (v_dataGrid.getCheckedIDs().length > 1) {
            	mx.indicate("info", "只能选择一条要复制的记录");
                return;
            }else{
    			var params = {"tempparamid":me.getDataGrid().getCheckedItems()[0].values.tempparamid, "bookmark":me.getDataGrid().getCheckedItems()[0].values.bookmark};
	    		var responseContent = restClient.getSync(cocontractparameter.mappath("~/rest/cocontractparameter/copyParam"),{ "params": JSON.stringify(params)});
	    		if(responseContent){
					mx.indicate("info", "复制保存成功！");
					me.getDataGrid().load();
				}else{
					mx.indicate("info", "复制保存失败！");
				}
            }
		});
		me.container2.addControl(btn_copyparam);
		
		var btn_save = new mx.controls.Button({ text: "保存" ,left:100});
		me.container2.addControl(btn_save);
		btn_save.on("click",me.controller.Save );
    	
	    _initDataGrid();
    	_initDetailWindow();
        me.on("activate", me.controller._onactivate);
    }
    
    function _initDataGrid()
    {
        var restUrl = "~/rest/cocontractparameter/";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : cocontractparameter.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "tempparamid"
        });
        
        /* 初始化 DataGrid */
        _dataGrid = new mx.datacontrols.DataGrid({   
			// 构造查询属性。
			alias: "cocontractparameterMainViewDataGrid",
//			columns:[
//			         {name:"month",caption:"当月",
			columns:[
			{	name: "tempparamid", caption: "合同范本名称ID" , editorType: "TextEditor",display:"none"	},
	        {	name: "contracttemplatename", caption: "合同范本名称" , editorType: "TextEditor", align:"center", width:"30%", dataAlign:"center",readOnly:true},
	        {	name: "tempparamname", caption: "书签名称" , editorType: "TextEditor", align:"center", width:"25%", dataAlign:"center",readOnly:true},
	        {	name: "dicname", caption: "标签名称" , editorType: "TextEditor", align:"center", width:"25%", dataAlign:"center", readOnly:true},
//	        {	name: "bookmark", caption: "书签名称" , editorType: "TextEditor", align:"center", width:"20%", dataAlign:"center"},
	        {	name: "tempparamdesc", caption: "备注" , editorType: "TextEditor", align:"center", width:"20%", dataAlign:"center", readOnly:true}
            ],
            // 构造列排序条件，如果有多列，则以逗号分隔。例sorter: "school ASC, class DESC"
            displayCheckBox: true,
            displayRowNumber: true, //显示序号
	        displayPrimaryKey:false,//列表是否显示主键
	        validateOnSaving: true, //保存前判断空值
	        displayToolBar:false,
	        allowAdjusting : true,
	        allowEditing:true,
	        pageSize : 20,

	        oncellediting:me.controller.getEditingItem,
	        oncelledited:me.controller.getEditItem,
	        onload: me._datagridOnload,
            entityContainer: gridEntityContainer
        });
        
       // _dataGrid.setFilter({contractId:me.contractid, contractName:"天津"});//me.contractId});
        _dataGrid.load();
        me.addControl(_dataGrid);
        me.mSplit.addControl(_dataGrid,1);//添加panel2
    }

    me._datagridOnload = function()
    {    	
    	me.getDataGrid().columns.tempparamid.setVisible(false);
    	
    	me.getDataGrid().columns.contracttemplatename.readOnly = true;
//    	me.getDataGrid().columns.tempparamname.readOnly = true;
    	me.getDataGrid().columns.dicname.readOnly = true;
    	//me.getDataGrid().columns.bookmark.readOnly = true;
    	me.getDataGrid().columns.tempparamdesc.readOnly = true;
        //me._checkDeclageFlag();//写入申报状态
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