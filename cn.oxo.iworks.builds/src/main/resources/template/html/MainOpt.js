function M${ModuleDifBean.moduleDif.id()}Opt(initParams) {

	var mainTable;

	var  parentTable;

	function setParentTable_(parentTable_) {
		parentTable = parentTable_;
	}
	
	this.setParentTable=function(table){
		setParentTable_(table);
	}
	
	function setMainTable(table) {
		mainTable = table;
	}

	function getMainTable() {
		return mainTable;
	}

	function mainTableEvent(data) {
		console.log(data);
	}
	
	this.remove = function(this_) {
		mainTable.deletes(this_);
	}

	this.deletes = function(this_) {
		mainTable.deletes(this_);
	}

	this.deletes = function(this_) {
		mainTable.deletes(this_);
	}

	this.search = function(this_) {
		mainTable.search(this_);
	}

	this.save = function(this_) {
		mainTable.save(this_);
	}

	this.update = function(this_) {
		mainTable.update(this_);
	}
	
	this.getTableSelectRows =function(){
		return mainTable.getTableSelectRows();
	}
	
	this.show = function(this_){
		if(parentTable ==null){
			alert("这个方法是用到父子表的");
		}else{
			
			var selectRows = parentTable.getTableSelectRows();
			if(selectRows.length!=1){
				MyappErrorMsg('错误', '只能选择一条记录，查看详情');
			}else{
				alert("这里要根据具体情况,带查询参数 : "+selectRows.length);
				jQuery('#${ModuleDifBean.moduleDif.id()}_show_windows').modal('show');
				mainTable.load({
					data:{
						'searchBean.sss':'www'
					}
				});	
			}
		}
		
	}
	

	this.sysmodule = function(this_) {
		var bt = jQuery(this_);
		var table = getMainTable();
		var selectRow = table.getTableSelectRows();
		if (selectRow.length == 0) {
			saiotErrorMsg('错误', '请选择要增加模块的系统');
		} else if (selectRow.length > 1) {
			saiotErrorMsg('错误', '请选择要增加模块的系统[你选择了' + selectRow.length + '系统]');
		} else {
			jQuery('#sys_module_windows').find('#sysname').html(
					selectRow[0].clientName);
			// alert(jQuery('#module_add_windows').find('#moduleAuth2ClientId').length+"
			// "+selectRow[0].id);
			jQuery('#module_add_windows').find('#moduleAuth2ClientId').val(
					selectRow[0].id);
			moduleOpt.show(bt, {
				data : {
					'searchBean.auth2ClientId' : selectRow[0].id
				}
			});
		}

	}

	this.intWork = function(params) {
		initTable(params.tableParams);
	}

	function initTable(params) {

		var mainTable = new MyAppTable({
			tableId : '${ModuleDifBean.moduleDif.id()}_table',
			powerUrl : "./system/auth2/client/",
			buttonIds : [  #foreach($opt  in   ${ModuleDifBean.moduleDif.opt()}) '${ModuleDifBean.moduleDif.id()}_${opt}'  #if($velocityCount < ${ModuleDifBean.moduleDif.opt().size()}) , #end #end  ],
			baseUrl : "${ModuleDifBean.moduleDif.action()}",
			tableEvent : mainTableEvent,
			tableParams : {
				toolbar : '#${ModuleDifBean.moduleDif.id()}_toolbar_btn',
				height : params.height,
				columns : TableColumns,
				authload : params.authload
			}
		});
		mainTable.initTable();
		setMainTable(mainTable);
	}


	var TableColumns = [ {
		checkbox : true,
		title : '全选',
		valign : 'middle'
	}

	#foreach($htmlGridDefBean  in   ${ModuleDifBean.htmlGridDefBeans})

	,{
		#if(${htmlGridDefBean.isGrid()}==true)
		title : '${htmlGridDefBean.htmlGridDef.name()}',
		field : '${htmlGridDefBean.name}',
		align : 'left',
		width : '${htmlGridDefBean.htmlGridDef.width()}px',
		valign : 'middle',
		formatter : function(value, row, index) {
			#if(${htmlGridDefBean.htmlGridDef.fieldType()}=='localCombo')
				 switch(value){ #foreach($detail_  in   ${htmlGridDefBean.detail})   case '${detail_.key}' :  return '${detail_.value}'   ;  #end  default : return '数据错误'}
				
			#else
				return   #if(${htmlGridDefBean.htmlGridDef.mapping()}=='')  value #else  value==null ? null : value.${htmlGridDefBean.htmlGridDef.mapping()}   #end  ;	
			#end
			
		}
		#end
	}
	#end
	];

}


