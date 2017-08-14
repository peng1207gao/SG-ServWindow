$ns("coContractbaseinfoV2Act.views");
$import("mx.rpc.RESTClient");
$import("mx.controls.ToolBar");
$import("mx.datacontrols.DataGrid");

coContractbaseinfoV2Act.views.copybullMainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    var restClient = new mx.rpc.RESTClient();
    me.dataGrid = null;

    base.init = me.init;
    me.init = function()
    {
        base.init();

        _initControls();
    };
    
    function _initControls()
    {
    	_initLayout();
    	_initDataGrid();
    	_initData();
    	
        me.on("activate", me.controller._onactivate);
    }
    function _initLayout(){
    	//页面上面分成上下两部分
    	me.mSplit = new mx.containers.HSplit({rows:"60px,auto",borderThick:1});
    	me.addControl(me.mSplit);
    	
    	me.panel1 = new mx.containers.Panel({title:"业务操作", width:"100%"});
    	me.mSplit.addControl(me.panel1,0);//添加panel1
    	me.container1 = new mx.containers.Container({padding:"2px",width:"100%"});
    	me.panel1.addControl(me.container1);//添加container1
    	var saveBtn = new mx.controls.Button({text : "保存",left : 20});
		me.container1.addControl(saveBtn);
		saveBtn.on("click",me.controller._btnSave_onclick);
		
		var closeBtn = new mx.controls.Button({text : "关闭",left : 40});
		me.container1.addControl(closeBtn);
		closeBtn.on("click",function(){
			me.controller.win.hide();
		});
    }
    
    function _initDataGrid(){
   	 	var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
   	 		type:"local",
   	 		loadMeta: false,
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "col0"
        });
	
	 /* 初始化 DataGrid */
       me.dataGrid = new mx.datacontrols.DataGrid({      
           
           // TODO: 设置显示列。
           columns:[
				{	name: "col0", caption: "主键" , editorType: "TextEditor",align:"center",dataAlign:"center"	},         
				{	name: "col1", caption: "纸质合同编号" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
				{	name: "col16", caption: "售电方别名" , editorType: "TextEditor",align:"center",dataAlign:"center",readOnly:true	},
				{	name: "col2", caption: "合同名称" , editorType: "TextEditor",align:"center",dataAlign:"center"},  
				{	name: "col3", caption: "合同类型" , editorType: "TextEditor",align:"center",dataAlign:"center",readOnly:true	},
				{	name: "col4", caption: "购电方" , editorType: "TextEditor",align:"center",dataAlign:"center",readOnly:true},
				{	name: "col5", caption: "售电方" , editorType: "TextEditor",align:"center",dataAlign:"center",readOnly:true	},
				{	name: "energy", caption: "总电量" , editorType: "TextEditor",align:"center",dataAlign:"center",valueType:"number",validType: "REGEXP",validOptions:{expression: /^(-)?(0|[1-9]\d{0,7})(\.\d{1,4})?$/},validateMessage:"请输入最多8位整数,最多4位小数的数字."	},
				{	name: "contractprice", caption: "电价" , editorType: "TextEditor",align:"center",dataAlign:"center",valueType:"number",validType: "REGEXP",validOptions:{expression: /^(-)?(0|[1-9]\d{0,7})(\.\d{1,4})?$/},validateMessage:"请输入最多8位整数,最多4位小数的数字."	},
				{	name: "col8", caption: "开始时间" , editorType: "DateTimeEditor",align:"center",dataAlign:"center"	},
				{	name: "col9", caption: "截止时间" , editorType: "DateTimeEditor",align:"center",dataAlign:"center"	},
				{	name: "col10", caption: "合同状态" , editorType: "TextEditor",align:"center",dataAlign:"center",readOnly:true	},
				//{	name: "col11", caption: "合同状态" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
				//{	name: "col12", caption: "维护人" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
				//{	name: "col13", caption: "维护时间" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
				//{	name: "col14", caption: "维护单位" , editorType: "TextEditor",align:"center",dataAlign:"center"	},
				{	name: "contracttype", caption: "contracttype" , editorType: "TextEditor",visible:false},
//				{	name: "col15", caption: "附件下载" , editorType: "TextEditor",align:"center",dataAlign:"center",visible:false,readOnly:true	},
				{	name: "col22", caption: "合同信息" , editorType: "TextEditor",align:"center",dataAlign:"center",renderCell:detailContact,readOnly:true}  

               	                    ],
           entityContainer: gridEntityContainer,
           displayRowNumber : true,
           displayCheckBox: false,
           allowEditing: true,
           allowPaging:false,
           displayPrimaryKey:false,
           allowInterlacedRow: true,
           displayRowNumber: true,
	        allowPaging: false,
           onload:_init
          
       });
       me.mSplit.addControl(me.dataGrid,1);//添加panel1
    }
    
    function _init(){
    	 me.dataGrid.columns["contracttype"].setVisible(false);
//    	 me.dataGrid.columns["col15"].setVisible(false);
    	 me.dataGrid.columns.col16.readOnly=true;
    	 me.dataGrid.columns.col3.readOnly=true;
    	 me.dataGrid.columns.col4.readOnly=true;
    	 me.dataGrid.columns.col5.readOnly=true;
    	 me.dataGrid.columns.col10.readOnly=true;
//    	 me.dataGrid.setItems(me.controller.v_items);
    }
    /**
     * 合同名称加超链接查看详细信息
     */
    function detailContact(p_item,$p_cell){
    	
    	var objId = p_item.getValue("col0"); 
    	var contracttype = p_item.getValue("contracttype"); 
    	var coTName = p_item.getValue("col3"); 
    	var htxlList = restClient.getSync(coContractbaseinfoV2Act.mappath("~/rest/coContractbaseinfoV2Act/getHtxl"),{contractid:objId});
    	var htxlName = null;
    	if(htxlList!=null && htxlList.length>0){
    		for(var i=0;i<htxlList.length;i++){
    			htxlName = htxlList[i][1];
    		}
    	}
    	$p_cell[0].onclick = function(){detail(objId,contracttype,coTName,htxlName);}	//添加查询事件
    	$p_cell.html("<a href=# >"+p_item.getValue("col2")+"</a>");	//添加超链接
    	
    }
    function _initData(){
    	var items = me.controller.v_items;
    	for(var i=0;i<items.length;i++){
    		me.dataGrid.appendItem(items[i].values);
//    		var p_items = jQuery.parseJSON('{"month":"2013-02","zhigou":"456","feizhigou":"564","kuaqu":"5565","kuasheng":"6784"}');
//    		me.dataGrid.appendItem(jQuery.parseJSON('{"month":"2013-02","zhigou":"456","feizhigou":"564","kuaqu":"5565","kuasheng":"6784"}'));
    	}
    }
    
    function detail(objId,contracttype,coTName,htxlName){
      	var _detailView = new htadd.views.zjxzViewController().getView({objID:objId,readType:"1",coType:contracttype,htxlName:htxlName,coTName:coTName});
    	if (!_detailView.initialized)
		{
			_detailView.init();
		}
        
        var _detailWin = utils.commonFun.WinMananger.create({
			reusable: true,//是否复用
			width: "90%",
			height: "90%",
			title:"合同详情页面"
		});
        _detailWin.setView(_detailView);
        _detailWin.showDialog();
    }
    return me.endOfClass(arguments);
};