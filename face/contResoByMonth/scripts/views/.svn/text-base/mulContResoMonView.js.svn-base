$ns("contResoByMonth.views");

$import("mx.containers.Panel");
$import("mx.controls.Label");
$import("mx.editors.DropDownEditor");
$import("mx.rpc.RESTClient");
$import("mx.datacontrols.DataGridSearchBox");
//$import("mx.datacontrols.ComplexGrid");
$import("mx.datacontrols.DataGrid");
$import("mx.datacontainers.GridEntityContainer");
contResoByMonth.views.mulContResoMonView=function(){
	var me = $extend(mx.views.View);
	
	var base = {};
	base.init = me.init;
	var restClient = new mx.rpc.RESTClient();
	var _dataGrid = null;
	var tenCoType="1";
	var contractid;
	me.init = function () {
		base.init();
		me.permissionID = "-1";
		initViewController();
		
		if(me.params.readType == '1'){//1的时候只读
			me.queryBotton.hide();
			me.fjBotton.hide();
			me.bcBotton.hide();
			me.fjfsSelect.setEnabled(false);
			me.zswSelect.setEnabled(false);
			_dataGrid.allowEditing=false;
		}
		
		
	};
	var syear = null;//@获取syear
	var smonth = null;//@获取smonth
	var sday = null;//@获取sday
	var eyear = null;//@获取eyear
	var emonth = null;//@获取emonth
	var eday = null;//@获取eday
	var tenItems=null;
	var oldval=null;
	var sdate=null;
	var edate=null;
	function initViewController(){
		me.mainSplit = new mx.containers.HSplit({
			rows : "52%,auto",
			width:"100%",
			height:"100%",
			borderThick : 1
		});
		me.ywcxPanel = new mx.containers.Panel({ name:"panel", title:"业务查询", width:"100%", height:"120px",padding:"2px",border:0}); 
    	me.ywczPanel = new mx.containers.Panel({ name:"panel", title:"业务操作", width:"100%", height:"70px",padding:"2px",border:0}); 
    	me.mainSplit.addControl(me.ywcxPanel,0);
		me.mainSplit.addControl(me.ywczPanel,0);
		me.addControl(me.mainSplit);
		me.ywcxContainer = new mx.containers.HSplit({
			rows : "40%,auto",
			width:"100%",
			height:"100%",
			borderThick : 0
		});
		me.ywczContainer = new mx.containers.Container({
			width:"100%",
			height:"100%",
			borderThick : 0
		});
		me.ywcxPanel.addControl(me.ywcxContainer);
		me.ywczPanel.addControl(me.ywczContainer);
		var fjfsLabel = new mx.controls.Label({
			text : "分解方式：",
			width : "80px",
			textAlign: "right", 
			verticalAlign: "middle",
			bottom:"-5px"
		}); 
		
		//业务查询  分解方式
		me.ywcxContainer.addControl(fjfsLabel,0);
		me.fjfsSelect= new mx.editors.DropDownEditor(
    			{
    				displayMember: "name",
    			    valueMember: "value",
    				width : "170px",
    				bottom :"-5px",
    				allowDropDown : false,
    				onchanged: _fjfsChange
    			});
		me.ywcxContainer.addControl(me.fjfsSelect,0);
		var fjfsitems = restClient.getSync(contResoByMonth.mappath("~/rest/contResoByMonth/queryFjfsSelect")).resultValue;	//获取分解方式
		me.fjfsSelect.setItems(fjfsitems);
		me.fjfsSelect.setValue(fjfsitems[0].value);
		//业务查询  取整位数
		var zwsLabel = new mx.controls.Label({
			text : "取整位数：",
			width : "80px",
			textAlign: "right", 
			verticalAlign: "middle",
			bottom:"-5px"
		}); 
		
		me.ywcxContainer.addControl(zwsLabel,1);
		me.zswSelect = new mx.editors.DropDownEditor(
				{
					displayMember: "name",
    			    valueMember: "value",
    				width : "170px",
    				bottom :"-5px",
    				allowDropDown : false,
    				nullable: false,
				    items: [
				        { name: "-10", value: "-10" },
				        { name: "-9", value: "-9" },
				        { name: "-8", value: "-8" },
				        { name: "-7", value: "-7" },
				        { name: "-6", value: "-6" },
				        { name: "-5", value: "-5" },
				        { name: "-4", value: "-4" },
				        { name: "-3", value: "-3" },
				        { name: "-2", value: "-2" },
				        { name: "-1", value: "-1" },
				        { name: "0", value: "0" },
				        { name: "1", value: "1" },
				        { name: "2", value: "2" }, 
				        { name: "3", value: "3" },
				        { name: "4", value: "4" },
				        { name: "5", value: "5" },
				        { name: "6", value: "6" },
				        { name: "7", value: "7" },
				        { name: "8", value: "8" },
				        { name: "9", value: "9" },
				        { name: "10", value: "10" }
				    ],
				    onchanged: _qzswSelectChanged
				});
		me.zswSelect.setValue("0",false);   
		me.ywcxContainer.addControl(me.zswSelect,1);
		
		me.queryBotton = new mx.controls.Button({ text: "查询",width:80,height:30,left:30,top:15});//查询合同
		me.queryBotton.on("click", search);
		me.ywczContainer.addControl(me.queryBotton);
		
		me.fjBotton = new mx.controls.Button({ text: "分解",width:80,height:30,left:40,top:15 });//复制合同
		me.fjBotton.on("click",energyFJ );
		me.ywczContainer.addControl(me.fjBotton);
		
		me.bcBotton = new mx.controls.Button({ text: "保存",width:80,height:30,left:50,top:15 });//复制合同
		me.bcBotton.on("click", save);
		me.ywczContainer.addControl(me.bcBotton);
		_initDataGrid();
	}
	
	//表格是否可编辑
	function _fjfsChange(){
//		var tenArr=["sellergen","sellerenergy","purchasergen","purchaserenergy","energy"];
		if(me.fjfsSelect.getValue()=='1'){
			_dataGrid.allowEditing=true;
		}else{
			_dataGrid.allowEditing=false;
			for(var i=0;i<_dataGrid.entityContainer.data.length;i++){
				var tenObj=_dataGrid.entityContainer.getEntityByKey(_dataGrid.entityContainer.data[i].col0);
				var nowContractId;
		    	if(_dataGrid.entityContainer.data[i].col0.length>32){
		    		nowContractId = _dataGrid.entityContainer.data[i].col0.substring(0,_dataGrid.entityContainer.data[i].col0.length-1);
		    	}else{
		    		nowContractId = _dataGrid.entityContainer.data[i].col0;
		    	}
		    	var items = restClient.getSync(contResoByMonth.mappath("~/rest/contResoByMonth/initHtdl?contractid="+nowContractId)).resultValue;	//获取分解方式
		    	smonth = items[5].smonth;//@获取smonth
		    	emonth = items[5].emonth;//@获取emonth
				for ( var n = Number(smonth); n <= Number(emonth); n++) {
					_dataGrid.entityContainer.setValue(tenObj,"col"+n,"");
				}
			}
		}
		_dataGrid.load();
//		changeDisplDllx();
	}
	function _initDataGrid(){
		 var gridEntityContainer = new mx.datacontainers.GridEntityContainer({
			 	type : "local", //声明容器类型为本地数据。
	            iscID : "-1", // iscID 是数据元素的统一权限功能编码。默认值为  "-1" ，表示不应用权限设置。
	            primaryKey: "col0",
	            data :
	                [
	                ] //本地数据信息。
	        });
		 _dataGrid = new mx.datacontrols.DataGrid({   
				// 构造查询属性。
				alias: "mulContResoMonViewDataGrid",
				allowInterlacedRow:true,
				allowAdjusting:false,
				allowDraggingColumn:false,
				columns:[
				{	name: "col0", caption: "prikey" , width:110, editorType: "TextEditor",align:"center",dataAlign:"center",readOnly:true}, 
				{	name: "papercontractcode", caption: "纸质合同编号" , width:110, editorType: "TextEditor",align:"center",dataAlign:"center",readOnly:true}, 
				{	name: "contractname", caption: "合同名称" , width:110, editorType: "TextEditor",align:"center",dataAlign:"center",readOnly:true}, 
				{	name: "colDllx", caption: "电量类型" , width:110, editorType: "TextEditor",align:"center",dataAlign:"center",readOnly:true},         
				{	name: "colZdl", caption: "总电量" ,width:90,editorType: "TextEditor",align:"center",dataAlign:"center",readOnly:true},
				{	name: "col1", caption: "1月" ,width:90,editorType: "TextEditor",align:"center",dataAlign:"center",validType:"DIGIT", validOptions:{minValue: 0, validateMessage:"请输入正数"}},  
				{	name: "col2", caption: "2月" ,width:90,editorType: "TextEditor",align:"center",dataAlign:"center",validType:"DIGIT", validOptions:{minValue: 0, validateMessage:"请输入正数"}},
				{	name: "col3", caption: "3月" ,width:90,editorType: "TextEditor",align:"center",dataAlign:"center",validType:"DIGIT", validOptions:{minValue: 0, validateMessage:"请输入正数"}},
				{	name: "col4", caption: "4月" ,width:90,editorType: "TextEditor",align:"center",dataAlign:"center",validType:"DIGIT", validOptions:{minValue: 0, validateMessage:"请输入正数"}},
				{	name: "col5", caption: "5月" ,width:90,editorType: "TextEditor",align:"center",dataAlign:"center",validType:"DIGIT", validOptions:{minValue: 0, validateMessage:"请输入正数"}},
				{	name: "col6", caption: "6月" ,width:90,editorType: "TextEditor",align:"center",dataAlign:"center",validType:"DIGIT", validOptions:{minValue: 0, validateMessage:"请输入正数"}},
				{	name: "col7", caption: "7月" ,width:90, editorType: "TextEditor",align:"center",dataAlign:"center",validType:"DIGIT", validOptions:{minValue: 0, validateMessage:"请输入正数"}},
				{	name: "col8", caption: "8月" ,width:90,editorType: "TextEditor",align:"center",dataAlign:"center",validType:"DIGIT", validOptions:{minValue: 0, validateMessage:"请输入正数"}},
				{	name: "col9", caption: "9月" ,width:90,editorType: "TextEditor",align:"center",dataAlign:"center",validType:"DIGIT", validOptions:{minValue: 0, validateMessage:"请输入正数"}},
				{	name: "col10", caption: "10月" ,width:90,editorType: "TextEditor",align:"center",dataAlign:"center",validType:"DIGIT", validOptions:{minValue: 0, validateMessage:"请输入正数"}},
				{	name: "col11", caption: "11月" ,width:90,editorType: "TextEditor",align:"center",dataAlign:"center",validType:"DIGIT", validOptions:{minValue: 0, validateMessage:"请输入正数"}},
				{	name: "col12", caption: "12月" ,width:90,editorType: "TextEditor",align:"center",dataAlign:"center",validType:"DIGIT", validOptions:{minValue: 0, validateMessage:"请输入正数"}},
				{	name: "col13", caption: "lasttxt" ,width:90,editorType: "TextEditor",align:"center",dataAlign:"center",visible:false}
	            ],
	            // 构造列排序条件，如果有多列，则以逗号分隔。例sorter: "school ASC, class DESC"
	            displayCheckBox: false,
		        displayPrimaryKey:false,//列表是否显示主键
	            allowEditing: true, //列表默认不可编辑
	            entityContainer: gridEntityContainer,
	            allowPaging:false,
	            oncelledited:function(e){
	            	editCell(e);
	            },
	            oncellediting:function(e){
	            	initoldVal(e);
	            },
	            onload:dataOnload,
	            rowNumberColWidth : 40
	        });
	        me.mainSplit.addControl(_dataGrid,1);//添加表格
	        initHtdl();
//	        _dataGrid.load();
	}
	
	function changeDisplDllx(){
		//验证合同类型   tenStr为1  表示 合同类型 是 发电权 或者 发电权 子集   为"0"  表示其他合同类型  
		var tenArr=["sellergen","sellerenergy","purchasergen","purchaserenergy","energy"];
		if(tenCoType=="0"){
			for(var i=0;i<4;i++){
				var tenArr=["sellergen","sellerenergy","purchasergen","purchaserenergy","energy"];
				$("#"+tenArr[i],_dataGrid.$e).css("display","none");
			}
		}
	}
	//根据 合同分解方式 和  总电量 是否有值 来判断 表格是否可编辑
	function dataOnload(){
		var tenArr=["sellergen","sellerenergy","purchasergen","purchaserenergy","energy"];
		var contractid = _dataGrid.entityContainer.data;
		for(var m=0;m<_dataGrid.entityContainer.data.length;m++){
			contractid = _dataGrid.entityContainer.data[m].col0;
			if(_dataGrid.entityContainer.data[m].col0.length>32){
				contractid = _dataGrid.entityContainer.data[m].col0.substring(0,_dataGrid.entityContainer.data[m].col0.length-1);
	    	}else{
	    		contractid = _dataGrid.entityContainer.data[m].col0;
	    	}
			tenCoType=restClient.getSync(contResoByMonth.mappath("~/rest/contResoByMonth/checkCoTypeTwo?contractid="+contractid)).resultValue;
			var items = restClient.getSync(contResoByMonth.mappath("~/rest/contResoByMonth/initHtdl?contractid="+contractid)).resultValue;	//获取分解方式
			tenItems=items;
			if(tenItems.length!=6){
				_dataGrid.allowEditing=false;
				return;
			}else{
				if(me.fjfsSelect.getValue()=='1'){
					_dataGrid.allowEditing=true;
				}else{
					_dataGrid.allowEditing=false;
//					return ;
				}
			}
			//第一个 json  是sellergen 第二个 json  是sellerenergy 三个 json  是purchasergen 第四个json 是purchaserenergy  第五个json 是energy
			syear = items[5].syear;//@获取syear
			smonth = items[5].smonth;//@获取smonth
			sday = items[5].sday;//@获取sday
			eyear = items[5].eyear;//@获取eyear
			emonth = items[5].emonth;//@获取emonth
			eday = items[5].eday;//@获取eday
			sdate = items[5].sdate;//@获取sdate
			edate = items[5].edate;//@获取edate
			if(tenCoType=="0"){
			
				var tenObj=_dataGrid.entityContainer.getEntityByKey(contractid);
				if(eval("tenItems[5]."+tenArr[4])!=null && eval("tenItems[5]."+tenArr[4])!=""){
					$("#"+contractid,_dataGrid.$e).find("td:eq(4)").css("background-color","")
					for(var n=1;n<13;n++){
						if(n>=Number(smonth) && n<=Number(emonth)){
							$("#"+contractid,_dataGrid.$e).find("td:eq("+(n+4)+")").prop("disabled",false);
							$("#"+contractid,_dataGrid.$e).find("td:eq("+(n+4)+")").css("background-color","");
						}else{
							$("#"+contractid,_dataGrid.$e).find("td:eq("+(n+4)+")").prop("disabled",true);
							$("#"+contractid,_dataGrid.$e).find("td:eq("+(n+4)+")").css("background-color","#BEBEBE");
						}
					}
				}else{
					$("#"+contractid,_dataGrid.$e).find("td:eq(4)").css("background-color","#BEBEBE");
					for(var n=1;n<13;n++){
							$("#"+contractid,_dataGrid.$e).find("td:eq("+(n+4)+")").css("background-color","#BEBEBE");
							$("#"+contractid,_dataGrid.$e).find("td:eq("+(n+4)+")").prop("disabled",true);
					}
				}
			
			}else{
				for(var i=0;i<tenArr.length;i++){
					var tenObj=_dataGrid.entityContainer.getEntityByKey(contractid+i);
					if(eval("tenItems[5]."+tenArr[i])!=null && eval("tenItems[5]."+tenArr[i])!=""){
						$("#"+contractid+i,_dataGrid.$e).find("td:eq(4)").css("background-color","")
						for(var n=1;n<13;n++){
							if(n>=Number(smonth) && n<=Number(emonth)){
								$("#"+contractid+i,_dataGrid.$e).find("td:eq("+(n+4)+")").prop("disabled",false);
								$("#"+contractid+i,_dataGrid.$e).find("td:eq("+(n+4)+")").css("background-color","");
							}else{
								$("#"+contractid+i,_dataGrid.$e).find("td:eq("+(n+4)+")").prop("disabled",true);
								$("#"+contractid+i,_dataGrid.$e).find("td:eq("+(n+4)+")").css("background-color","#BEBEBE");
							}
						}
					}else{
						$("#"+contractid+i,_dataGrid.$e).find("td:eq(4)").css("background-color","#BEBEBE");
						for(var n=1;n<13;n++){
								$("#"+contractid+i,_dataGrid.$e).find("td:eq("+(n+4)+")").css("background-color","#BEBEBE");
								$("#"+contractid+i,_dataGrid.$e).find("td:eq("+(n+4)+")").prop("disabled",true);
						}
					}
				}
			}
		}
		
		
	}
	function initoldVal(obj){
//		var tenArr=["energy","sellergen","sellerenergy","purchasergen","purchaserenergy"];
//		var tenNum=$.inArray(obj.item.id,tenArr);
//		//记录点击表格 修改之前的值 
//		//_dataGrid.entityContainer.data[tenNum].col13   下段代码  为了 拼接成这样的格式
//		oldval=eval("_dataGrid.entityContainer.data["+tenNum+"]."+obj.column.name);
		var tenObj=_dataGrid.entityContainer.getEntityByKey(obj.item.id);
		oldval = eval("tenObj."+obj.column.name);
	}
	function editCell(obj){
		//此数组 顺序不能变动
//		var tenArr=["energy","sellergen","sellerenergy","purchasergen","purchaserenergy"];
		// 当前点击格子 所在行
//		var tenNum=$.inArray(obj.item.id,tenArr);
		//根据行id  找出当前行对象
		var tenObj=_dataGrid.entityContainer.getEntityByKey(obj.item.id);
		//获取最后一行隐藏列的值
    	var value = tenObj.col13;//@获取当前value值
    	//获取点击表格 的月份
    	var num1=obj.column.name.substring(3,obj.column.name.length);
    	var nowContractId;
    	if(obj.item.id.length>32){
    		nowContractId = obj.item.id.substring(0,obj.item.id.length-1);
    	}else{
    		nowContractId = obj.item.id;
    	}
    	var items = restClient.getSync(contResoByMonth.mappath("~/rest/contResoByMonth/initHtdl?contractid="+nowContractId)).resultValue;	//获取分解方式
		emonth = items[5].emonth;//@获取emonth
    	emonth=Number(emonth);
    	
    	//如果点击格 是 合同最后的月份    就降隐藏列  的值 赋给 这个月份
    	if (Number(num1)==emonth) {
    		_dataGrid.entityContainer.setValue(tenObj, "col"+emonth,value);
    		obj.item.setValue(emonth+4,value);
    	}else{	
    		//如果 点击的不是最后一个月份 
    		var newvalue =obj.cell.html();//@获取newvalue值
    		var oldvalue = (oldval==null?"":oldval);//@获取oldvalue值
    		var str=accSub(accAdd(Number(value),Number(oldvalue)),Number(newvalue));
    		_dataGrid.entityContainer.setValue(tenObj, "col"+emonth,str);
    		obj.item.setValue(emonth+4,str);
    	}
    	_dataGrid.entityContainer.setValue(tenObj, "col13",eval("obj.item.values.col"+emonth));
    	obj.item.setValue(16,eval("obj.item.values.col"+emonth));
//    	_dataGrid.load();
//    	ischanged=true;
	}
	//初始化电量表格
	function initHtdl(){
//		var tenArr=["sellergen","sellerenergy","purchasergen","purchaserenergy","energy"];
		alert("下列几种情况的合同不能进行分解，合同数据不带入合同电量按月分解页面：1、合同电量、替代方发电量、替代方上网电量、被替代方发电量、被替代方上网电量全部为空 。2、合同开始时间、结束时间为空。3、开始时间必须早于结束时间。");
		var contractid = me.contractids;
		for(var m=0;m<me.contractids.length;m++){
			contractid = me.contractids[m];
			tenCoType=restClient.getSync(contResoByMonth.mappath("~/rest/contResoByMonth/checkCoTypeTwo?contractid="+contractid)).resultValue;
			var items = restClient.getSync(contResoByMonth.mappath("~/rest/contResoByMonth/initHtdl?contractid="+contractid)).resultValue;	//获取分解方式
			tenItems=items;
			me.fjBotton.setEnabled(true);
			me.bcBotton.setEnabled(true);
			if(items[0]=='error1'||items[0]=='error2'||items[0]=='error3'||items[0]=='error4'){
//				alert(items[1]);
//				me.queryBotton.setEnabled(false);
//				me.fjBotton.setEnabled(false);
//				me.bcBotton.setEnabled(false);
//				me.fjfsSelect.setEnabled(false);
//				me.zswSelect.setEnabled(false);
//				return ;
			}else if(items[0]=='canOper'){
					if(confirm('电量已经分解，可以到电量电力界面进行微调。是否删除分解内容，重新分解')){
						var tenitems = restClient.getSync(contResoByMonth.mappath("~/rest/contResoByMonth/delHtdl?contractid="+contractid)).resultValue;	//获取分解方式
						alert(tenitems[0]);
						initHtdl();
						return ; //return不能删除     如果 运行 这里边 就不该在运行 _dataGrid.load();
					}else{
						me.fjBotton.setEnabled(false);
						me.bcBotton.setEnabled(false);
					}
			}else{
				if(items.length==6){
					
					
					//第一个 json  是sellergen 第二个 json  是sellerenergy 三个 json  是purchasergen 第四个json 是purchaserenergy  第五个json 是energy
					 syear = items[5].syear;//@获取syear
					 smonth = items[5].smonth;//@获取smonth
					 sday = items[5].sday;//@获取sday
					 eyear = items[5].eyear;//@获取eyear
					 emonth = items[5].emonth;//@获取emonth
					 eday = items[5].eday;//@获取eday
					 sdate = items[5].sdate;//@获取sdate
					 edate = items[5].edate;//@获取edate
					 
					if(tenCoType=="0"){
						 var flag=_dataGrid.entityContainer.getEntityByKey(contractid);
						 if(flag){
							 var tenObj=_dataGrid.entityContainer.getEntityByKey(contractid);
							 for(var n=1;n<13;n++){
									if(n>=Number(smonth) && n<=Number(emonth)){
										_dataGrid.entityContainer.setValue(tenObj, "col"+n,eval("items["+4+"].month"+n));
									}
								}
						 }else{
							 _dataGrid.entityContainer.create({col0:contractid,papercontractcode:items[5].papercontractcode,contractname:items[5].contractname,colDllx:"合同电量",colZdl:items[5].energy});
							 var tenObj=_dataGrid.entityContainer.getEntityByKey(contractid);
							 for(var n=1;n<13;n++){
								if(n>=Number(smonth) && n<=Number(emonth)){
									_dataGrid.entityContainer.setValue(tenObj, "col"+n,eval("items["+4+"].month"+n));
								}
							}
						 }
						 
					}else{
						 var flag=_dataGrid.entityContainer.getEntityByKey(contractid+"0");
						 if(flag){
							 for(var i=0;i<=4;i++){ 
								 var tenObj=_dataGrid.entityContainer.getEntityByKey(contractid+""+i);
								 for(var n=1;n<13;n++){
										if(n>=Number(smonth) && n<=Number(emonth)){
											_dataGrid.entityContainer.setValue(tenObj, "col"+n,eval("items["+i+"].month"+n));
										}
									}
							}
						 }else{
							 _dataGrid.entityContainer.create({col0:contractid+"0",papercontractcode:items[5].papercontractcode,contractname:items[5].contractname,colDllx:"替代方电量",colZdl:items[5].sellergen});
							 _dataGrid.entityContainer.create({col0:contractid+"1",papercontractcode:items[5].papercontractcode,contractname:items[5].contractname,colDllx:"替代方上网电量",colZdl:items[5].sellerenergy});
							 _dataGrid.entityContainer.create({col0:contractid+"2",papercontractcode:items[5].papercontractcode,contractname:items[5].contractname,colDllx:"被替代方电量",colZdl:items[5].purchasergen});
							 _dataGrid.entityContainer.create({col0:contractid+"3",papercontractcode:items[5].papercontractcode,contractname:items[5].contractname,colDllx:"被替代方上网电量",colZdl:items[5].purchaserenergy});
							 _dataGrid.entityContainer.create({col0:contractid+"4",papercontractcode:items[5].papercontractcode,contractname:items[5].contractname,colDllx:"合同电量",colZdl:items[5].energy});
							 for(var i=0;i<=4;i++){ 
								 var tenObj=_dataGrid.entityContainer.getEntityByKey(contractid+""+i);
								 for(var n=1;n<13;n++){
										if(n>=Number(smonth) && n<=Number(emonth)){
											_dataGrid.entityContainer.setValue(tenObj, "col"+n,eval("items["+i+"].month"+n));
										}
									}
							 }
						 }
						
					}
						
					 
				}
				var sellergen=items[5].sellergen;
				var sellerenergy=items[5].sellerenergy;
				var purchasergen=items[5].purchasergen;
				var purchaserenergy=items[5].purchaserenergy;
				var energy=items[5].energy;
				var obj1 = [sellergen,sellerenergy,purchasergen,purchaserenergy,energy];
				if(tenCoType=="0"){
					var tenObj=_dataGrid.entityContainer.getEntityByKey(contractid);
					var sum = 0;
					for ( var j = Number(smonth); j < Number(emonth); j++) {
						if(eval("items["+4+"].month"+j)!=null && eval("items["+4+"].month"+j)!='' ){
							sum +=Number(eval("items["+4+"].month"+j));
						}
					}
					var tenStr=(obj1[4]-sum+"");
					var tenNum=tenStr.indexOf(".");
					if(tenNum!=-1&&tenStr.substring(tenNum+1, tenStr.length).length>4){
						tenStr=tenStr.substring(0,tenNum)+"."+tenStr.substring(tenNum+1, tenStr.length).substring(0, 4);
						_dataGrid.entityContainer.setValue(tenObj, "col13",Number(tenStr));
					}else{
						_dataGrid.entityContainer.setValue(tenObj, "col13",obj1[4]-sum);
					}
				}else{
					//循环五次 因为有五行数据
					for(var i =0;i<=4;i++ ){
						var sum = 0;
						var tenObj=_dataGrid.entityContainer.getEntityByKey(contractid+i);
						for ( var j = Number(smonth); j < Number(emonth); j++) {
							if(eval("items["+i+"].month"+j)!=null && eval("items["+i+"].month"+j)!='' ){
								sum +=Number(eval("items["+i+"].month"+j));
							}
						}
						var tenStr=(obj1[i]-sum+"");
						var tenNum=tenStr.indexOf(".");
						if(tenNum!=-1&&tenStr.substring(tenNum+1, tenStr.length).length>4){
							tenStr=tenStr.substring(0,tenNum)+"."+tenStr.substring(tenNum+1, tenStr.length).substring(0, 4);
							_dataGrid.entityContainer.setValue(tenObj, "col13",Number(tenStr));
						}else{
							_dataGrid.entityContainer.setValue(tenObj, "col13",obj1[i]-sum);
						}
					}
				}
				
			}
		}
		if(_dataGrid.entityContainer.data.length==0){
			alert("选择的合同无能分解数据，请重新选择！");
			me.queryBotton.setEnabled(false);
			me.fjBotton.setEnabled(false);
			me.bcBotton.setEnabled(false);
			me.fjfsSelect.setEnabled(false);
			me.zswSelect.setEnabled(false);
			return;
		}
		_dataGrid.load();
	}
	//拖动标尺
	function _qzswSelectChanged()
	{
		var contractid = _dataGrid.entityContainer.data;
		for(var q=0;q<_dataGrid.entityContainer.data.length;q++){
			if(_dataGrid.entityContainer.data[q].col0.length>32){
				contractid = _dataGrid.entityContainer.data[q].col0.substring(0,_dataGrid.entityContainer.data[q].col0.length-1);
	    	}else{
	    		contractid = _dataGrid.entityContainer.data[q].col0;
	    	}
			tenCoType=restClient.getSync(contResoByMonth.mappath("~/rest/contResoByMonth/checkCoTypeTwo?contractid="+contractid)).resultValue;
			var items = restClient.getSync(contResoByMonth.mappath("~/rest/contResoByMonth/initHtdl?contractid="+contractid)).resultValue;	//获取分解方式
			tenItems=items;
			//第一个 json  是sellergen 第二个 json  是sellerenergy 三个 json  是purchasergen 第四个json 是purchaserenergy  第五个json 是energy
			syear = items[5].syear;//@获取syear
			smonth = items[5].smonth;//@获取smonth
			sday = items[5].sday;//@获取sday
			eyear = items[5].eyear;//@获取eyear
			emonth = items[5].emonth;//@获取emonth
			eday = items[5].eday;//@获取eday
			sdate = items[5].sdate;//@获取sdate
			edate = items[5].edate;//@获取edate
			if(tenCoType=="0"){
				var tenZsw=me.zswSelect.getValue();//@获取整数位的值
				var energyFS=me.fjfsSelect.getValue();//@获取energyFS
				if(tenItems.length!=6){
					return;
				}
				var sellergen=tenItems[5].sellergen;
				var sellerenergy=tenItems[5].sellerenergy;
				var purchasergen=tenItems[5].purchasergen;
				var purchaserenergy=tenItems[5].purchaserenergy;
				var energy=tenItems[5].energy;
//				var tenArr=["energy","sellergen","sellerenergy","purchasergen","purchaserenergy"];
				if(energyFS==1){
					var obj1 = [energy,sellergen,sellerenergy,purchasergen,purchaserenergy];
					var sum = 0;
					var tenObj=_dataGrid.entityContainer.getEntityByKey(contractid);
					for (var j = Number(smonth); j < Number(emonth); j++) {
						if(eval("tenObj.col"+j)!=null && eval("tenObj.col"+j)!=""){
							var m = parseFloat(eval("tenObj.col"+j));//@获取值并转换为float类型
							var n="";
							if(tenZsw > 0){
								n = accMul(Math.round(accMul(m,-1*(tenZsw-1))),tenZsw-1);
							}else {
								n = accMul(Math.round(accMul(m,-1*tenZsw)),tenZsw);
							}
							_dataGrid.entityContainer.setValue(tenObj, "col"+j,n);
							sum = accAdd(sum,n);
						}
					}
					if(obj1[4]==null||obj1[4]==""){
						if(sum == 0){
							_dataGrid.entityContainer.setValue(tenObj, "col"+emonth,"");
							_dataGrid.entityContainer.setValue(tenObj, "col13","");
						}else{
							alert("数据出错");
							_dataGrid.entityContainer.setValue(tenObj, "col"+emonth,obj1[4]-sum);//@设置值
							_dataGrid.entityContainer.setValue(tenObj, "col13",obj1[4]-sum);//@设置值
							return;
						}
					}else{
							_dataGrid.entityContainer.setValue(tenObj, "col"+emonth,accSub(obj1[4],sum));//@设置值
							_dataGrid.entityContainer.setValue(tenObj, "col13",accSub(obj1[4],sum));//@设置值
					}
					
				}else if(energyFS==7){
					var energyFjitems = restClient.getSync(contResoByMonth.mappath("~/rest/contResoByMonth/energyMonthFJ?contractid="+contractid+'&energyFS='+energyFS+'&accuracyJs='+tenZsw+'&flag=1')).resultValue;	//获取分解方式
					energyFjitems=eval(energyFjitems);
							for ( var i = Number(smonth); i <= Number(emonth); i++) {
								var tenObj5=_dataGrid.entityContainer.getEntityByKey(contractid);
								_dataGrid.entityContainer.setValue(tenObj5, "col"+i,energyFjitems[i-Number(smonth)].data_5);//@设置值
								if(i==Number(emonth)){
									_dataGrid.entityContainer.setValue(tenObj5, "col13",energyFjitems[i-Number(smonth)].data_5);//@设置值
								}
							}
				}else{
					var energyFjitems = restClient.getSync(contResoByMonth.mappath("~/rest/contResoByMonth/energyFJ?contractid="+contractid+'&energyFS='+energyFS+'&accuracyJs='+tenZsw+'&flag=1')).resultValue;	//获取分解方式
					energyFjitems=eval(energyFjitems);
							for ( var i = Number(smonth); i <= Number(emonth); i++) {
								var tenObj5=_dataGrid.entityContainer.getEntityByKey(contractid);
								_dataGrid.entityContainer.setValue(tenObj5, "col"+i,energyFjitems[i-Number(smonth)].data_5);//@设置值
								if(i==Number(emonth)){
									_dataGrid.entityContainer.setValue(tenObj5, "col13",energyFjitems[i-Number(smonth)].data_5);//@设置值
								}
							}
				}
			}else{
				var tenZsw=me.zswSelect.getValue();//@获取整数位的值
				var energyFS=me.fjfsSelect.getValue();//@获取energyFS
				if(tenItems.length!=6){
					return;
				}
				var sellergen=tenItems[5].sellergen;
				var sellerenergy=tenItems[5].sellerenergy;
				var purchasergen=tenItems[5].purchasergen;
				var purchaserenergy=tenItems[5].purchaserenergy;
				var energy=tenItems[5].energy;
//					var tenArr=["energy","sellergen","sellerenergy","purchasergen","purchaserenergy"];
				if(energyFS==1){
					var obj1 = [energy,sellergen,sellerenergy,purchasergen,purchaserenergy];
					for(var i = 0;i<= 4; i++){  
						var sum = 0;
						var tenObj=_dataGrid.entityContainer.getEntityByKey(contractid+i);
						for (var j = Number(smonth); j < Number(emonth); j++) {
							if(eval("tenObj.col"+j)!=null && eval("tenObj.col"+j)!=""){
								var m = parseFloat(eval("tenObj.col"+j));//@获取值并转换为float类型
								var n="";
								if(tenZsw > 0){
									n = accMul(Math.round(accMul(m,-1*(tenZsw-1))),tenZsw-1);
								}else {
									n = accMul(Math.round(accMul(m,-1*tenZsw)),tenZsw);
								}
								_dataGrid.entityContainer.setValue(tenObj, "col"+j,n);
								sum = accAdd(sum,n);
							}
						}
						if(obj1[i]==null||obj1[i]==""){
							if(sum == 0){
								_dataGrid.entityContainer.setValue(tenObj, "col"+emonth,"");
								_dataGrid.entityContainer.setValue(tenObj, "col13","");
							}else{
								alert("数据出错");
								_dataGrid.entityContainer.setValue(tenObj, "col"+emonth,obj1[i]-sum);//@设置值
								_dataGrid.entityContainer.setValue(tenObj, "col13",obj1[i]-sum);//@设置值
								return;
							}
						}else{
								_dataGrid.entityContainer.setValue(tenObj, "col"+emonth,accSub(obj1[i],sum));//@设置值
								_dataGrid.entityContainer.setValue(tenObj, "col13",accSub(obj1[i],sum));//@设置值
						}
						
					}
				}else if(energyFS==7){
					var energyFjitems = restClient.getSync(contResoByMonth.mappath("~/rest/contResoByMonth/energyMonthFJ?contractid="+contractid+'&energyFS='+energyFS+'&accuracyJs='+tenZsw+'&flag=1')).resultValue;	//获取分解方式
					energyFjitems=eval(energyFjitems);
					for ( var i = Number(smonth); i <= Number(emonth); i++) {
						
						var tenObj1=_dataGrid.entityContainer.getEntityByKey(contractid+"0");
						_dataGrid.entityContainer.setValue(tenObj1, "col"+i,energyFjitems[i-Number(smonth)].data_1);//@设置值
						var tenObj2=_dataGrid.entityContainer.getEntityByKey(contractid+"1");
						_dataGrid.entityContainer.setValue(tenObj2, "col"+i,energyFjitems[i-Number(smonth)].data_2);//@设置值
						var tenObj3=_dataGrid.entityContainer.getEntityByKey(contractid+"2");
						_dataGrid.entityContainer.setValue(tenObj3, "col"+i,energyFjitems[i-Number(smonth)].data_3);//@设置值
						var tenObj4=_dataGrid.entityContainer.getEntityByKey(contractid+"3");
						_dataGrid.entityContainer.setValue(tenObj4, "col"+i,energyFjitems[i-Number(smonth)].data_4);//@设置值
						var tenObj5=_dataGrid.entityContainer.getEntityByKey(contractid+"4");
						_dataGrid.entityContainer.setValue(tenObj5, "col"+i,energyFjitems[i-Number(smonth)].data_5);//@设置值
//						var tenArr=["sellergen","sellerenergy","purchasergen","purchaserenergy","energy"];
//						var tenObj1=_dataGrid.entityContainer.getEntityByKey("sellergen");
//						_dataGrid.entityContainer.setValue(tenObj1, "col"+i,energyFjitems[i-Number(smonth)].data_1);//@设置值
//						var tenObj2=_dataGrid.entityContainer.getEntityByKey("sellerenergy");
//						_dataGrid.entityContainer.setValue(tenObj2, "col"+i,energyFjitems[i-Number(smonth)].data_2);//@设置值
//						var tenObj3=_dataGrid.entityContainer.getEntityByKey("purchasergen");
//						_dataGrid.entityContainer.setValue(tenObj3, "col"+i,energyFjitems[i-Number(smonth)].data_3);//@设置值
//						var tenObj4=_dataGrid.entityContainer.getEntityByKey("purchaserenergy");
//						_dataGrid.entityContainer.setValue(tenObj4, "col"+i,energyFjitems[i-Number(smonth)].data_4);//@设置值
//						var tenObj5=_dataGrid.entityContainer.getEntityByKey("energy");
//						_dataGrid.entityContainer.setValue(tenObj5, "col"+i,energyFjitems[i-Number(smonth)].data_5);//@设置值
						if(i==Number(emonth)){
							_dataGrid.entityContainer.setValue(tenObj1, "col13",energyFjitems[i-Number(smonth)].data_1);//@设置值
							_dataGrid.entityContainer.setValue(tenObj2, "col13",energyFjitems[i-Number(smonth)].data_2);//@设置值
							_dataGrid.entityContainer.setValue(tenObj3, "col13",energyFjitems[i-Number(smonth)].data_3);//@设置值
							_dataGrid.entityContainer.setValue(tenObj4, "col13",energyFjitems[i-Number(smonth)].data_4);//@设置值
							_dataGrid.entityContainer.setValue(tenObj5, "col13",energyFjitems[i-Number(smonth)].data_5);//@设置值
						}
					}
				}else{
					var energyFjitems = restClient.getSync(contResoByMonth.mappath("~/rest/contResoByMonth/energyFJ?contractid="+contractid+'&energyFS='+energyFS+'&accuracyJs='+tenZsw+'&flag=1')).resultValue;	//获取分解方式
					energyFjitems=eval(energyFjitems);
							for ( var i = Number(smonth); i <= Number(emonth); i++) {
									
								var tenObj1=_dataGrid.entityContainer.getEntityByKey(contractid+"0");
								_dataGrid.entityContainer.setValue(tenObj1, "col"+i,energyFjitems[i-Number(smonth)].data_1);//@设置值
								var tenObj2=_dataGrid.entityContainer.getEntityByKey(contractid+"1");
								_dataGrid.entityContainer.setValue(tenObj2, "col"+i,energyFjitems[i-Number(smonth)].data_2);//@设置值
								var tenObj3=_dataGrid.entityContainer.getEntityByKey(contractid+"2");
								_dataGrid.entityContainer.setValue(tenObj3, "col"+i,energyFjitems[i-Number(smonth)].data_3);//@设置值
								var tenObj4=_dataGrid.entityContainer.getEntityByKey(contractid+"3");
								_dataGrid.entityContainer.setValue(tenObj4, "col"+i,energyFjitems[i-Number(smonth)].data_4);//@设置值
								var tenObj5=_dataGrid.entityContainer.getEntityByKey(contractid+"4");
								_dataGrid.entityContainer.setValue(tenObj5, "col"+i,energyFjitems[i-Number(smonth)].data_5);//@设置值
//								var tenArr=["sellergen","sellerenergy","purchasergen","purchaserenergy","energy"];
//								var tenObj1=_dataGrid.entityContainer.getEntityByKey("sellergen");
//								_dataGrid.entityContainer.setValue(tenObj1, "col"+i,energyFjitems[i-Number(smonth)].data_1);//@设置值
//								var tenObj2=_dataGrid.entityContainer.getEntityByKey("sellerenergy");
//								_dataGrid.entityContainer.setValue(tenObj2, "col"+i,energyFjitems[i-Number(smonth)].data_2);//@设置值
//								var tenObj3=_dataGrid.entityContainer.getEntityByKey("purchasergen");
//								_dataGrid.entityContainer.setValue(tenObj3, "col"+i,energyFjitems[i-Number(smonth)].data_3);//@设置值
//								var tenObj4=_dataGrid.entityContainer.getEntityByKey("purchaserenergy");
//								_dataGrid.entityContainer.setValue(tenObj4, "col"+i,energyFjitems[i-Number(smonth)].data_4);//@设置值
//								var tenObj5=_dataGrid.entityContainer.getEntityByKey("energy");
//								_dataGrid.entityContainer.setValue(tenObj5, "col"+i,energyFjitems[i-Number(smonth)].data_5);//@设置值
								if(i==Number(emonth)){
									_dataGrid.entityContainer.setValue(tenObj1, "col13",energyFjitems[i-Number(smonth)].data_1);//@设置值
									_dataGrid.entityContainer.setValue(tenObj2, "col13",energyFjitems[i-Number(smonth)].data_2);//@设置值
									_dataGrid.entityContainer.setValue(tenObj3, "col13",energyFjitems[i-Number(smonth)].data_3);//@设置值
									_dataGrid.entityContainer.setValue(tenObj4, "col13",energyFjitems[i-Number(smonth)].data_4);//@设置值
									_dataGrid.entityContainer.setValue(tenObj5, "col13",energyFjitems[i-Number(smonth)].data_5);//@设置值
								}
							}
				}
			}
		}
		
		_dataGrid.load();
//		changeDisplDllx();
	}
	//处理过的乘法运算arg1*10^n
	function accMul(arg1,n){
		var arg1_str = arg1.toString();//@转换为字符串
		var index = arg1_str.indexOf(".");
		try{
			var arr1 = arg1_str.split(".");
			var r1 = arr1[1].length;
			var r2 = arr1[0].length;
		} catch(e){
			r1 = 0;
		}
		arg1_str = arg1_str.replace(".","");
		
		if(-n>=r2){
			for(var i = 0;i < -n-r2+1;i++){
				arg1_str = "0"+arg1_str;
			}
		}
		var sub = n - r1;
		if(n>r1){
			for(var i = 0;i < sub;i++){
				arg1_str += "0";
				r1++;
			}
		}
		var len = arg1_str.length;
		var mul_str = arg1_str.substring(0,len-r1+n)+"."+arg1_str.substring(len-r1+n,len);
		
		var mul = Number(mul_str);
		return mul;
	}
	//处理过的加法运算
	function accAdd(arg1,arg2){
		var arg1_str = arg1.toString();//@转换为字符串
		var arg2_str = arg2.toString();//@转换为字符串
		try{
			var arr1 = arg1_str.split(".");
			var r1 = arr1[1].length;
		} catch(e){
			r1 = 0
		}
		try{
			var arr2 = arg2_str.split(".");
			var r2 = arr2[1].length;
		} catch(e){
			r2 = 0
		}
		var c = Math.abs(r1 - r2);
		var m = Math.max(r1,r2);
		arg1_str = arg1_str.replace(".","");
		arg2_str = arg2_str.replace(".","");
		if(r1<r2){
			for ( var i = 0; i < c; i++) {
				arg1_str += "0";
			}
		}else{
			for ( var i = 0; i < c; i++) {
				arg2_str += "0";
			}
		}
		var sum = Number(arg1_str)+Number(arg2_str);
		var len = sum.toString().length;
		var sum_str = accMul(sum,-m);
		//var sum_str = sum.toString().substring(0,len-m)+"."+sum.toString().substring(len-m,len);
		return Number(sum_str);
	}
	function accSub(arg1,arg2){
		return accAdd(arg1,-arg2);
	}
	//查询
	function search(){
		//刷新当前页面
		if(_dataGrid.$e.find("input[type='text']").size()!=0 && _dataGrid.$e.find("input[type='text']").parent("div:first").attr("class").indexOf("error")!=-1){
			return;
		}
		 initHtdl();
//	     _dataGrid.load();
	}
	function energyFJ(){
		if(_dataGrid.$e.find("input[type='text']").size()!=0 && _dataGrid.$e.find("input[type='text']").parent("div:first").attr("class").indexOf("error")!=-1){
			return;
		}
		var tenZsw=me.zswSelect.getValue();//@获取整数位的值
		var energyFS=me.fjfsSelect.getValue();//@获取energyFS
		var contractid = _dataGrid.entityContainer.data;//@获取合同id
		
		if(energyFS == '1'){
			alert('当前分解方式为手动分解，请手动填充');
			return;
		}else if(energyFS == '7'){
			for(var m=0;m<_dataGrid.entityContainer.data.length;m++){
				contractid = _dataGrid.entityContainer.data[m].col0;
				if(_dataGrid.entityContainer.data[m].col0.length>32){
					contractid = _dataGrid.entityContainer.data[m].col0.substring(0,_dataGrid.entityContainer.data[m].col0.length-1);
		    	}else{
		    		contractid = _dataGrid.entityContainer.data[m].col0;
		    	}
				tenCoType=restClient.getSync(contResoByMonth.mappath("~/rest/contResoByMonth/checkCoTypeTwo?contractid="+contractid)).resultValue;
				var items = restClient.getSync(contResoByMonth.mappath("~/rest/contResoByMonth/initHtdl?contractid="+contractid)).resultValue;	//获取分解方式
				tenItems=items;
				//第一个 json  是sellergen 第二个 json  是sellerenergy 三个 json  是purchasergen 第四个json 是purchaserenergy  第五个json 是energy
				syear = items[5].syear;//@获取syear
				smonth = items[5].smonth;//@获取smonth
				sday = items[5].sday;//@获取sday
				eyear = items[5].eyear;//@获取eyear
				emonth = items[5].emonth;//@获取emonth
				eday = items[5].eday;//@获取eday
				sdate = items[5].sdate;//@获取sdate
				edate = items[5].edate;//@获取edate
				if(tenCoType=="0"){
					var energyFjitems = restClient.getSync(contResoByMonth.mappath("~/rest/contResoByMonth/energyMonthFJ?contractid="+contractid+'&energyFS='+energyFS+'&accuracyJs='+tenZsw+'&flag=1')).resultValue;	//获取分解方式
					energyFjitems=eval(energyFjitems);
							for ( var i = Number(smonth); i <= Number(emonth); i++) {
								var tenObj5=_dataGrid.entityContainer.getEntityByKey(contractid);
								_dataGrid.entityContainer.setValue(tenObj5, "col"+i,energyFjitems[i-Number(smonth)].data_5);//@设置值
								if(i==Number(emonth)){
									_dataGrid.entityContainer.setValue(tenObj5, "col13",energyFjitems[i-Number(smonth)].data_5);//@设置值
								}
							}
				}else{
					var energyFjitems = restClient.getSync(contResoByMonth.mappath("~/rest/contResoByMonth/energyMonthFJ?contractid="+contractid+'&energyFS='+energyFS+'&accuracyJs='+tenZsw+'&flag=1')).resultValue;	//获取分解方式
					energyFjitems=eval(energyFjitems);
							for ( var i = Number(smonth); i <= Number(emonth); i++) {
								var tenArr=["sellergen","sellerenergy","purchasergen","purchaserenergy","energy"];
								var tenObj1=_dataGrid.entityContainer.getEntityByKey(contractid+"0");
								_dataGrid.entityContainer.setValue(tenObj1, "col"+i,energyFjitems[i-Number(smonth)].data_1);//@设置值
								var tenObj2=_dataGrid.entityContainer.getEntityByKey(contractid+"1");
								_dataGrid.entityContainer.setValue(tenObj2, "col"+i,energyFjitems[i-Number(smonth)].data_2);//@设置值
								var tenObj3=_dataGrid.entityContainer.getEntityByKey(contractid+"2");
								_dataGrid.entityContainer.setValue(tenObj3, "col"+i,energyFjitems[i-Number(smonth)].data_3);//@设置值
								var tenObj4=_dataGrid.entityContainer.getEntityByKey(contractid+"3");
								_dataGrid.entityContainer.setValue(tenObj4, "col"+i,energyFjitems[i-Number(smonth)].data_4);//@设置值
								var tenObj5=_dataGrid.entityContainer.getEntityByKey(contractid+"4");
								_dataGrid.entityContainer.setValue(tenObj5, "col"+i,energyFjitems[i-Number(smonth)].data_5);//@设置值
								if(i==Number(emonth)){
									_dataGrid.entityContainer.setValue(tenObj1, "col13",energyFjitems[i-Number(smonth)].data_1);//@设置值
									_dataGrid.entityContainer.setValue(tenObj2, "col13",energyFjitems[i-Number(smonth)].data_2);//@设置值
									_dataGrid.entityContainer.setValue(tenObj3, "col13",energyFjitems[i-Number(smonth)].data_3);//@设置值
									_dataGrid.entityContainer.setValue(tenObj4, "col13",energyFjitems[i-Number(smonth)].data_4);//@设置值
									_dataGrid.entityContainer.setValue(tenObj5, "col13",energyFjitems[i-Number(smonth)].data_5);//@设置值
								}
							}
				}
				
			}
			
			_dataGrid.load();
		}else {
			for(var m=0;m<_dataGrid.entityContainer.data.length;m++){
				contractid = _dataGrid.entityContainer.data[m].col0;
				if(_dataGrid.entityContainer.data[m].col0.length>32){
					contractid = _dataGrid.entityContainer.data[m].col0.substring(0,_dataGrid.entityContainer.data[m].col0.length-1);
		    	}else{
		    		contractid = _dataGrid.entityContainer.data[m].col0;
		    	}
				tenCoType=restClient.getSync(contResoByMonth.mappath("~/rest/contResoByMonth/checkCoTypeTwo?contractid="+contractid)).resultValue;
				var items = restClient.getSync(contResoByMonth.mappath("~/rest/contResoByMonth/initHtdl?contractid="+contractid)).resultValue;	//获取分解方式
				tenItems=items;
				//第一个 json  是sellergen 第二个 json  是sellerenergy 三个 json  是purchasergen 第四个json 是purchaserenergy  第五个json 是energy
				syear = items[5].syear;//@获取syear
				smonth = items[5].smonth;//@获取smonth
				sday = items[5].sday;//@获取sday
				eyear = items[5].eyear;//@获取eyear
				emonth = items[5].emonth;//@获取emonth
				eday = items[5].eday;//@获取eday
				sdate = items[5].sdate;//@获取sdate
				edate = items[5].edate;//@获取edate
				if(tenCoType=="0"){
					var energyFjitems = restClient.getSync(contResoByMonth.mappath("~/rest/contResoByMonth/energyFJ?contractid="+contractid+'&energyFS='+energyFS+'&accuracyJs='+tenZsw+'&flag=1')).resultValue;	//获取分解方式
					energyFjitems=eval(energyFjitems);
							for ( var i = Number(smonth); i <= Number(emonth); i++) {
								var tenObj5=_dataGrid.entityContainer.getEntityByKey(contractid);
								_dataGrid.entityContainer.setValue(tenObj5, "col"+i,energyFjitems[i-Number(smonth)].data_5);//@设置值
								if(i==Number(emonth)){
									_dataGrid.entityContainer.setValue(tenObj5, "col13",energyFjitems[i-Number(smonth)].data_5);//@设置值
								}
							}
				}else{
					var energyFjitems = restClient.getSync(contResoByMonth.mappath("~/rest/contResoByMonth/energyFJ?contractid="+contractid+'&energyFS='+energyFS+'&accuracyJs='+tenZsw+'&flag=1')).resultValue;	//获取分解方式
					energyFjitems=eval(energyFjitems);
							for ( var i = Number(smonth); i <= Number(emonth); i++) {
								var tenArr=["sellergen","sellerenergy","purchasergen","purchaserenergy","energy"];
								var tenObj1=_dataGrid.entityContainer.getEntityByKey(contractid+"0");
								_dataGrid.entityContainer.setValue(tenObj1, "col"+i,energyFjitems[i-Number(smonth)].data_1);//@设置值
								var tenObj2=_dataGrid.entityContainer.getEntityByKey(contractid+"1");
								_dataGrid.entityContainer.setValue(tenObj2, "col"+i,energyFjitems[i-Number(smonth)].data_2);//@设置值
								var tenObj3=_dataGrid.entityContainer.getEntityByKey(contractid+"2");
								_dataGrid.entityContainer.setValue(tenObj3, "col"+i,energyFjitems[i-Number(smonth)].data_3);//@设置值
								var tenObj4=_dataGrid.entityContainer.getEntityByKey(contractid+"3");
								_dataGrid.entityContainer.setValue(tenObj4, "col"+i,energyFjitems[i-Number(smonth)].data_4);//@设置值
								var tenObj5=_dataGrid.entityContainer.getEntityByKey(contractid+"4");
								_dataGrid.entityContainer.setValue(tenObj5, "col"+i,energyFjitems[i-Number(smonth)].data_5);//@设置值
								if(i==Number(emonth)){
									_dataGrid.entityContainer.setValue(tenObj1, "col13",energyFjitems[i-Number(smonth)].data_1);//@设置值
									_dataGrid.entityContainer.setValue(tenObj2, "col13",energyFjitems[i-Number(smonth)].data_2);//@设置值
									_dataGrid.entityContainer.setValue(tenObj3, "col13",energyFjitems[i-Number(smonth)].data_3);//@设置值
									_dataGrid.entityContainer.setValue(tenObj4, "col13",energyFjitems[i-Number(smonth)].data_4);//@设置值
									_dataGrid.entityContainer.setValue(tenObj5, "col13",energyFjitems[i-Number(smonth)].data_5);//@设置值
								}
							}
				}
				
			}
			
			_dataGrid.load();
//			changeDisplDllx();
		}	
	}
	
	function save(){
		if(_dataGrid.$e.find("input[type='text']").size()!=0 && _dataGrid.$e.find("input[type='text']").parent("div:first").attr("class").indexOf("error")!=-1){
			return;
		}
		var contractid = me.contractid;//@获取合同id
//		var sdate = $("#sdate").val();//@获取开始时间
//		var edate = $("#edate").val();//@获取结束时间
//		var syear = $("#syear").val();//@获取开始年份
//		var eyear = $("#eyear").val();//@获取结束年份
//		var smonth = $("#smonth").val();//@获取开始月份
//		var emonth = $("#emonth").val();//@获取结束月份
//		var b = $('#form1').form('validate');
		
		var message = "";
		if(confirm("如合同原已分解数据将会被替换，是否继续分解？")){
			for(var q=0;q<_dataGrid.entityContainer.data.length;q++){
				var tenObj=_dataGrid.entityContainer.getEntityByKey(_dataGrid.entityContainer.data[q].col0);
				var nowContractId;
		    	if(_dataGrid.entityContainer.data[q].col0.length>32){
		    		nowContractId = _dataGrid.entityContainer.data[q].col0.substring(0,_dataGrid.entityContainer.data[q].col0.length-1);
		    	}else{
		    		nowContractId = _dataGrid.entityContainer.data[q].col0;
		    	}
		    	tenCoType=restClient.getSync(contResoByMonth.mappath("~/rest/contResoByMonth/checkCoTypeTwo?contractid="+nowContractId)).resultValue;
		    	var items = restClient.getSync(contResoByMonth.mappath("~/rest/contResoByMonth/initHtdl?contractid="+nowContractId)).resultValue;	//获取分解方式
				tenItems=items;
				//第一个 json  是sellergen 第二个 json  是sellerenergy 三个 json  是purchasergen 第四个json 是purchaserenergy  第五个json 是energy
				syear = items[5].syear;//@获取syear
				smonth = items[5].smonth;//@获取smonth
				sday = items[5].sday;//@获取sday
				eyear = items[5].eyear;//@获取eyear
				emonth = items[5].emonth;//@获取emonth
				eday = items[5].eday;//@获取eday
				sdate = items[5].sdate;//@获取sdate
				edate = items[5].edate;//@获取edate
		    	smonth = items[5].smonth;//@获取smonth
		    	emonth = items[5].emonth;//@获取emonth
		    	if(tenCoType=="0"){
		    		//保存分解数据
					var tenArr=["sellergen","sellerenergy","purchasergen","purchaserenergy","energy"];
					var tenObj=_dataGrid.entityContainer.getEntityByKey(nowContractId);
					tenArr[4]="";
					for(var i=1;i<13;i++){
						if(i!=12){
							tenArr[4]+=(eval("tenObj.col"+i)==null?"":eval("tenObj.col"+i))+",";
						}else{
							tenArr[4]+=(eval("tenObj.col"+i)==null?"":eval("tenObj.col"+i));
						}
					}
					for(var n=0;n<4;n++){
						 var tenObj=_dataGrid.entityContainer.getEntityByKey(contractid+""+i);
						 tenArr[n]="";
						 for(var i=1;i<13;i++){
								if(i!=12){
									tenArr[n]+=(eval("items["+n+"].month"+i)==null?"":eval("items["+n+"].month"+i))+",";
								}else{
									tenArr[n]+=eval("items["+n+"].month"+i)==null?"":eval("items["+n+"].month"+i);
								}
							}
					 }
					var str=("contractid="+nowContractId+'&sdate='+sdate+'&edate='+edate);
					str+=("&energys="+tenArr[4]+"&sellergens="+tenArr[0]+"&sellerenergys="+tenArr[1]);
					str+="&purchasergens="+tenArr[2]+"&purchaserenergys="+tenArr[3];
					var tenData = restClient.getSync(contResoByMonth.mappath("~/rest/contResoByMonth/save?"+str)).resultValue;	//获取分解方式
					if(tenData=="保存失败"){
						message = message + nowContractId;
					}
		    	}else{
		    		//保存分解数据
					var tenArr=["sellergen","sellerenergy","purchasergen","purchaserenergy","energy"];
					for(var n=0;n<5;n++){
						var tenObj=_dataGrid.entityContainer.getEntityByKey(nowContractId+""+n);
						tenArr[n]="";
						for(var i=1;i<13;i++){
							if(i!=12){
								tenArr[n]+=(eval("tenObj.col"+i)==null?"":eval("tenObj.col"+i))+",";
							}else{
								tenArr[n]+=(eval("tenObj.col"+i)==null?"":eval("tenObj.col"+i));
							}
						}
					}
					var str=("contractid="+nowContractId+'&sdate='+sdate+'&edate='+edate);
					str+=("&energys="+tenArr[4]+"&sellergens="+tenArr[0]+"&sellerenergys="+tenArr[1]);
					str+="&purchasergens="+tenArr[2]+"&purchaserenergys="+tenArr[3];
					var tenData = restClient.getSync(contResoByMonth.mappath("~/rest/contResoByMonth/save?"+str)).resultValue;	//获取分解方式
					if(tenData=="保存失败"){
						message = message + nowContractId;
					}
		    	}
			}
			
			
			
		}else{
			return;
		}
		
		if(message==""){
			alert("保存成功！");
		}else{
			alert("有合同id为："+message+"的合同保存失败！");
		}
	}
	return me.endOfClass(arguments);
};