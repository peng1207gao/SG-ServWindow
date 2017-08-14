$ns("IdeaConManage.views");

$import("mx.controls.ToolBar");
$import("mx.datacontainers.FormEntityContainer");
$import("mx.datacontrols.DataForm");
$import("mx.containers.Panel");


IdeaConManage.views.IdeaConDetailView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    /**
     * 表单对象id
     */
    me.objID = null;
    
    me.sceneType = null;//场景类型，确认是合同意向管理功能(ideaContract)，还是多级合同管理功能（multiLevel）
    
    /**
      * 表单对象
     */
    var _form = null;
    me._contractTree = null;
    /**
     * 工具条
     */
    var _toolBar = null;

    /* 初始化单表单控件 */
    base.init = me.init;
    me.init = function()
    {
        base.init();
        _initControls();
    };

    me.load = function()
    {
    	//加载表单信息
    	_form.load(me.objID);
    }

    function _initControls()
    {	
    	_initHSplit();
    	_initBtnPanel();
    	_initVSplit();
    	_initContractTree();
	    _initDataForm();
	    _initDetailWindow();
        me.on("activate", me.controller._onactivate);
    }
    
    function _initHSplit(){
    	me.Hsplit = new mx.containers.HSplit({
    		rows : "55,auto",width:"100%",
			borderThick : 1
    	});
    	me.addControl(me.Hsplit);
    }
    
    function _initBtnPanel(){
    	me.btnPanel = new mx.containers.Panel({ name:"panel3", title:"操作", width:"100%", height: "100%" });
    	me.cancelRelationBtn = new mx.controls.Button({text : "取消合同关联",left:30 });
    	me.cancelRelationBtn.on("click", me.cancleRelation);
    	
    	me.btnPanel.addControl(me.cancelRelationBtn);
    	me.Hsplit.addControl(me.btnPanel, 0);
    }
	
	function _initVSplit(){
		me.Vsplit = new mx.containers.VSplit({
			rows : "50%,50%",width:"100%",
			borderThick : 1
		});
		me.Hsplit.addControl(me.Vsplit, 1);
	}
	
	/**
	 * 打开详细界面时候，初始化树结构
	 */
	function _initContractTree(){
		me._contractTree = new mx.datacontrols.DataTree({
			baseUrl: IdeaConManage.mappath("~/rest/cocontractbaseinfo/getContractTree"),
			autoSelect:true,
			onselectionchanged: _tree_selectionchanged
		});
		
		me.Vsplit.addControl(me._contractTree,0);
	} 
	
	function _tree_selectionchanged(){
		var contractid = me._contractTree.selection.id;
		if(contractid != null && contractid != ""){
			_form.load(contractid);
		}
	}
	
    function _initDataForm()
    {
        var restUrl = "~/rest/cocontractbaseinfo/";
        /* 初始化 EntityContainer */        
        var formEntityContainer = new mx.datacontainers.FormEntityContainer({
            baseUrl : IdeaConManage.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "contractid"
        });
        
        _form = new mx.datacontrols.DataForm({
			
			alias:"IdeaConManageIdeaConDetailViewDataForm",
			displayPrimaryKey: false,
			fields: [
	        {	name: "contractid", caption: "contractid", editorType: "TextEditor", visible:false},
	        {	name: "contractname", caption: "合同名称", editorType: "TextEditor"},
	        {	name: "papercontractcode", caption: "主合同编号", editorType: "TextEditor"},
	        {	name: "contracttype", caption: "合同类型", editorType: "TextEditor"},
	        {	name: "contractstate", caption: "合同状态", editorType: "TextEditor"},
	        {	name: "purchaser", caption: "购电方", editorType: "TextEditor"},
	        {	name: "seller", caption: "售电方", editorType: "TextEditor"},
	        {	name: "contractstartdate", caption: "开始日期", editorType: "DateTimeEditor", formatString: "yyyy-MM-dd"},
	        {	name: "contractenddate", caption: "截止日期", editorType: "DateTimeEditor", formatString: "yyyy-MM-dd"},
		    {	name: "energy", caption: "总电量", editorType: "TextEditor"},
		    {	name: "maintainperson", caption: "维护人员", editorType: "TextEditor"},
		    {	name: "maintaintime", caption: "维护时间", editorType: "DateTimeEditor", formatString: "yyyy-MM-dd"},
		    {	name: "maintainunit", caption: "维护单位", editorType: "TextEditor"},
		    {	name: "contractprice", caption: "电价", editorType: "TextEditor"}
		    ],
		    onload:setReadOnly,
            entityContainer: formEntityContainer
        });
        
        me.Vsplit.addControl(_form,1);
    }
    /**
     * 设置表单内容只读
     */
    function setReadOnly(){
   		me.getForm().editors.contractname.setEnabled(false);
    	me.getForm().editors.contracttype.setEnabled(false);
    	me.getForm().editors.papercontractcode.setEnabled(false);
    	me.getForm().editors.contractstate.setEnabled(false);
    	me.getForm().editors.purchaser.setEnabled(false);
    	me.getForm().editors.seller.setEnabled(false);
    	me.getForm().editors.contractstartdate.setEnabled(false);
    	me.getForm().editors.contractenddate.setEnabled(false);
    	me.getForm().editors.energy.setEnabled(false);
    	me.getForm().editors.maintainperson.setEnabled(false);
    	me.getForm().editors.maintaintime.setEnabled(false);
    	me.getForm().editors.maintainunit.setEnabled(false);
    	me.getForm().editors.contractprice.setEnabled(false);
    }

    me.cancleRelation = function(){
    	
    	var selectedNode = me._contractTree.selection;
    	if(selectedNode.parent == null && selectedNode.hasChildren == false){
    		mx.indicate("info","没有与该合同关联的合同");
    		return;
    	}
    	
    	me.restClient = new mx.rpc.RESTClient();
    	if(me.sceneType == "ideaContract"){
    		if(selectedNode != null){
	    		if(selectedNode.hasChildren == true){//根节点合同，取消关联时同时取消其与所有子节点合同的关联
	    			var isCancleAll = confirm("确定取消父合同与其所有子合同的关联关系？");
	    			if(isCancleAll){
	    				var data = {"contractid" : selectedNode.id, "isRootNode" : true};//父节点取消关联
				     	var s = me.restClient.getSync(IdeaConManage.mappath("~/rest/cocontractbaseinfo/cancleIdeaRelation"), { "params": JSON.stringify(data)});
				     	if(typeof(s)!='undefined'&&s!=null){
				 			if(s.type!='error'){
				 				if(s.resultValue!=null){
				 					mx.indicate("info","取消关联成功");
				 					me.parent.hide();
				 				}
				 			} else {
				 				alert("取消关联失败");
				 				mx.indicate("info", "取消关联失败！");
				 			}
				 		}   			
					}
	    		} else {
	    			var isCancleAll = confirm("确定该合同与其父合同的关联关系？");
	    			if(isCancleAll){
	    				var data = {"contractid" : selectedNode.id, "isRootNode" : false};//子节点取消关联
				     	var s = me.restClient.getSync(IdeaConManage.mappath("~/rest/cocontractbaseinfo/cancleIdeaRelation"), { "params": JSON.stringify(data)});
				     	if(typeof(s)!='undefined'&&s!=null){
				 			if(s.type!='error'){
				 				if(s.resultValue!=null){
				 					me._contractTree.load({"contractid":selectedNode.parent.id, "sceneType":me.sceneType});//刷新意向性协议树形列表
				 				}
				 			} else {
//				 				alert("取消关联失败");
				 				mx.indicate("info", "取消关联失败！");
				 			}
				 		}   			
	    			}
	    		}
	    	}
    	} else if(me.sceneType == "multiLevel"){
    		if(selectedNode != null){
	    		if(selectedNode.hasChildren == true){//根节点合同，取消关联时同时取消其与所有子节点合同的关联
	    			var isCancleAll = confirm("确定取消父合同与其所有子合同的关联关系？");
	    			if(isCancleAll){
	    				var data = {"contractid" : selectedNode.id, "isRootNode" : true};//父节点取消关联
				     	var s = me.restClient.getSync(IdeaConManage.mappath("~/rest/cocontractbaseinfo/cancleMultiLevelRelation"), { "params": JSON.stringify(data)});
				     	if(typeof(s)!='undefined'&&s!=null){
				 			if(s.type!='error'){
				 				if(s.resultValue!=null){
				 					mx.indicate("info","取消关联成功");
				 					me.parent.hide();
				 				}
				 			} else {
//				 				alert("取消关联失败");
				 				mx.indicate("info", "取消关联失败！");
				 			}
				 		}   			
					}
	    		} else {
	    			var isCancleAll = confirm("确定该合同与其父合同的关联关系？");
	    			if(isCancleAll){
	    				var data = {"contractid" : selectedNode.id, "isRootNode" : false};//子节点取消关联
				     	var s = me.restClient.getSync(IdeaConManage.mappath("~/rest/cocontractbaseinfo/cancleMultiLevelRelation"), { "params": JSON.stringify(data)});
				     	if(typeof(s)!='undefined'&&s!=null){
				 			if(s.type!='error'){
				 				if(s.resultValue!=null){
				 					me._contractTree.load({"contractid":selectedNode.parent.id, "sceneType":me.sceneType});//刷新多级合同树形列表
				 				}
				 			} else {
//				 				alert("取消关联失败");
								mx.indicate("info", "取消关联失败！");
				 			}
				 		}   			
	    			}
	    		}
	    	}
    	}
    	
    	
    	
    }
    
    /**
     * 获取表单对象
     */
    me.getForm = function(){
		return _form;
    }
    
    me.getTree = function(){
    	return me._contractTree;
    }
	
    var _detailWin = null;
 	/**
     * 初始化表单视图窗口对象
     */
    function _initDetailWindow(){
    	_detailWin = utils.commonFun.WinMananger.create({
			reusable: true,//是否复用
			width:840,
			height:480
		});
    }
    
    me.getDetailWindow = function(){
    	return _detailWin;
    }
    
	me.endOfClass(arguments)
    return me;
};