$ns("contractaffixinfo.views");

$import("mx.datacontainers.GridEntityContainer");
$import("mx.datacontrols.DataGridSearchBox");
$import("mx.datacontrols.DataGrid");
$import("mx.containers.Panel");


contractaffixinfo.views.ContractAffixInfoMainView = function()
{
    var me = $extend(mx.views.View);
    var base = {};
    
    //网格列表
    var _dataGrid = null;
    //表单窗口对象
    var _detailWin = null;
    
//    me.contractid = "8a8192163ed6c198013ed97f0885000e";

    base.init = me.init;
    me.init = function()
    {
        base.init();
        _initControls();
    };
    
    function _initControls()
    {
    	_initHSplit();
    	_initPanel();
    	_initContainer();
    	_initButton();
//    	_initUpload();
//    	_initDownload();
//    	_initDelete();
	    _initDataGrid();
    	_initDetailWindow();
        me.on("activate", me.controller._onactivate);
    }
    
    function _initHSplit(){
    
    	me.hSplit = new mx.containers.HSplit({
    		rows:"60,auto"
    	});
    	me.addControl(me.hSplit);
    }
    
    function _initContainer(){
    	me.container = new mx.containers.Container({
    		id:"container",
    		padding: "2px"
    	});
    	me.panel.addControl(me.container);
    }
    
    function _initPanel(){
    	me.panel = new mx.containers.Panel({
    		id:"Panel",
			border:"1",
			title:"业务操作"
    	});
    	me.hSplit.addControl(me.panel);
    }
    
    function _initButton(){
    	if(me.params.readType != '1'){//1的时候只读
    		_initUpload();//文本上传
    		_initDownload();//文本下载
    		_initDelete();//删除
		}
    }
    
    function _initUpload(){
    	me.upload = new mx.controls.Button({
    		text: "文本上传",
			left: 20,
			onclick:me.controller._btnUpload_onclick
    	});
    	me.container.addControl(me.upload);
    }
    
    function _initDownload(){
    	me.download = new mx.controls.Button({
    		text: "文本下载",
			left: 40,
			onclick:me.controller._btnDownload_onclick
    	});
    	me.container.addControl(me.download);
    }
    
    function _initDelete(){
    	me.deleteButton = new mx.controls.Button({
    		text: "删除",
			left: 60,
			onclick:me.controller._btnDelete_onclick
    	});
    	me.container.addControl(me.deleteButton);
    }
    
    function _initDataGrid()
    {
        var restUrl = "~/rest/cocontractaffixinfo_mk/";
        /* 初始化 EntityContainer */        
        var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
            baseUrl : contractaffixinfo.mappath(restUrl),
            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
            primaryKey: "guid"
        });
        
        /* 初始化 DataGrid */
        _dataGrid = new mx.datacontrols.DataGrid({   
			// 构造查询属性。
			alias: "contractaffixinfoContractAffixInfoMainViewDataGrid",
			
			columns:[
	        {	name: "marketid", caption: "场景id" , editorType: "TextEditor",visible:false	},
	        {	name: "contractid", caption: "合同id" , editorType: "TextEditor",visible:false	},
//	        {	name: "affixno", caption: "附件编号" , editorType: "TextEditor"	},
	        {	
	        	name: "affixname", caption: "附件名" , editorType: "TextEditor",width:"20%",
	        	renderCell:function(p_item, $p_cell){
		        	var linkEditor = new mx.editors.LinkEditor({"type" : "text", "width" : "90px","text" : p_item.getValue("affixname"),"onLink":null}); 
	                linkEditor.on("click",function(){
	                    var guid = p_item.getValue("guid");
	    				window.open(contractaffixinfo.mappath("~/rest/cocontractaffixinfo_mk/down?guid="+guid));
	                });
	                linkEditor.$e.css({"cursor":"hand"});
	                $p_cell.append(linkEditor.$e);
					$p_cell.click( function () {});
	        	}
	        },
//	        {	name: "papercontractfile", caption: "合同文本附件" , editorType: "TextEditor"	},
	        {	name: "uploadperson", caption: "维护人" , editorType: "TextEditor",width:"20%"	},
	        {	name: "uploadtime", caption: "维护时间" , editorType: "DateTimeEditor"	, formatString: "yyyy-MM-dd",width:"20%"},
	        {	name: "description", caption: "说明" , editorType: "TextEditor",width:"20%"	},
	        {	name: "guid", caption: "GUID" , editorType: "TextEditor"	},
	        {	name: "affixtype", caption: "附件类型" , editorType: "TextEditor",width:"20%"	}
            ],
            // 构造列排序条件，如果有多列，则以逗号分隔。例sorter: "school ASC, class DESC"
            displayRowNumber:true,
            displayCheckBox: true,
	        displayPrimaryKey:false,//列表是否显示主键
            allowEditing: false, //列表默认不可编辑
	        pageSize : 20,
            entityContainer: gridEntityContainer,
            onload:_initValue
        });
        _dataGrid.setFilter({contractid:me.contractid});
        me.hSplit.addControl(_dataGrid,1);
    }
    
    function _initValue(){
    	_dataGrid.columns["marketid"].setVisible(false);
    	_dataGrid.columns["contractid"].setVisible(false);
    }
    
    /**
     * 初始化表单视图窗口对象
     */
    function _initDetailWindow(){
    	_detailWin = utils.commonFun.WinMananger.create({
			reusable: true,//是否复用
			width:500,
			height:400,
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