
// var contextPa = $contextPath;
function getContextPath() {

	var pathName = document.location.pathname;
	var index = pathName.substr(1).indexOf("/");
	var result = pathName.substr(0, index + 1);
	return result;
}


//$(function(){
//			var pager = $('#mytable').datagrid().datagrid('getPager');//
//			pager.pagination({
//				buttons:[{
//					iconCls:'icon-search',
//					handler:function(){
//						alert('search');
//					}
//				},{
//					iconCls:'icon-add',
//					handler:function(){
//						addShare();
//					}
//				},
//				{
//					iconCls:'icon-remove',
//					handler:function(){
//						deleteShare();
//					}
//					
//				}
//				]
//			});	
//			})

		
		
	
			
function addShare(){
	$("#sharedialog").dialog({
        autoOpen: false,
        bgiframe: true,
        height:680,
        width:1080,
        title:"分享信息",
        modal:true,
        resizable:true
        });
        
   }
   
function closeDialog(){
	$("#sharedialog").dialog('close');
}
//增加分享方法，表单提交
function saveShare() {
	//var button = $('#submitBtn');//防止表单 
	$('#addShareForm').form('submit', {
				url : getContextPath() + "/share/addshare.do",
				onSubmit : function() {
					var isValid =  $(this).form('validate');
					if(!isValid)
					{
						return isValid;
					}
					else
					{//防止表单重复提交
						$('#submitBtn').linkbutton('disable');
					}
				},
				success : function(result) {
					$('#submitBtn').linkbutton('enable');
					//解析后台返回的JSON数据
					var result = eval('(' + result + ')');
//					var _result = result;
					if (!result.success) {
						$.messager.show({
							title : 'Error',
							msg : '添加失败！',
							timeout: 1000,
							style:{
                            left:800, // 与左边界的距离
                            top:450 // 与顶部的距离
                            }
					    });
					} else {
						$.messager.show({
							title : 'Success!',
							msg : '添加成功！',
							timeout: 1000,
							style:{
                            left:800, // 与左边界的距离
                            top:350 // 与顶部的距离
                            }
					});
					//关闭注册框
					$('#sharedialog').dialog('close'); // close the dialog
					}
					//新增和删除完成后都要重新加载datagrid中的数据
					$('#mysharetable').datagrid('reload');
				}
			});
} 

function deleteShare(){
//	var datagrid = $('#mytable').datagrid();
	var row = $('#mysharetable').datagrid('getSelected');
        	    if (row){
        	    	$.messager.confirm('确认', '是否确认删除该分享?', function (r) {  
        	            $.ajax({  
        	                type: "post",  
        	                url: "/Share_system/share/deleteShareById.do",  
        	                data: 
        	  			    {
        	                  	"share_id":row.shareId
        	                  	
        	                 },  
        	                success: function (result) {  
        	                    if (result.success) {  
        	                        $.messager.alert("提示", "删除成功！", "info");  
        	                        $('#mysharetable').datagrid('reload');
//        	                        getAllData();  
        	                    }  
        	                    else {  
        	                        $.messager.alert("错误", result.Msg, "info");  
        	                    }  
        	                },  
        	                dataType: "json"  
        	            });  
//        	        	alert('User ID:'+row.user_id);
        	    });  
        	   }
        	   else{
        	   	$.messager.confirm('提示', '请先选择要删除的行')
        	   }
        	   
}

$(function () {
	

    var datagrid; //定义全局变量datagrid
    var editRow = undefined; //定义全局变量：当前编辑的行
    datagrid = $("#mysharetable").datagrid({
        url: '/Share_system/share/queryMyShareByPage.do', //请求的数据源
        method: 'get',
        pagination: true, //显示分页
        pageSize: 10, //页大小
        pageList: [10, 20, 30, 40],
        rownumbers:true,
        fit: false, //datagrid自适应宽度
        fitColumn: true, //列自适应宽度
        striped: true, //行背景交换
        nowap: true, //列内容多时自动折至第二行
        border: false,
        height: 500,
        title:'我的分享',
        idField: 'shareId', //主键
        columns: [[//显示的列
        {field: 'shareId', title: ' ', width: 120, sortable: true,checkbox:true
//           editor: { type: 'validatebox', options: { required: true} }
        },
        {field: 'subject', title: '主题', width: 190, sortable: true,
           editor: { type: 'validatebox', options: { required: true} }
        },
         { field: 'content', title: '内容', width: 480, sortable: true,
             editor: { type: 'validatebox', options: { required: true} }
         },
         { field: 'attachements', title: '附件', width: 360, sortable: true//附件不允许编辑
//             editor: { type: 'validatebox', options: { required: true} }
         },
           { field: 'studentNum', title: '听课人数', width: 100,
               editor: { type: 'validatebox', options: { required: true} }
           },
           { field: 'grade_num', title: '评分人数', width: 100//评分人数不允许编辑
//               editor: { type: 'validatebox', options: { required: true} }
           }
           ]],
           
        queryParams: {}, //查询参数
        toolbar: [
        {
        	 text: '添加分享', iconCls: 'icon-add', handler: function () {//添加列表的操作按钮添加，修改，删除等
             addShare();
        }
        }, '-',
        {
          text: '删除', iconCls: 'icon-remove', handler: function () {
             
        	  deleteShare();
         }
         }, '-',
         { text: '保存', iconCls: 'icon-save', handler: function () {
             //保存时结束当前编辑的行，自动触发onAfterEdit事件如果要与后台交互可将数据通过Ajax提交后台
             
             datagrid.datagrid("endEdit", editRow);
         }
         }, '-',
         { text: '取消编辑', iconCls: 'icon-redo', handler: function () {
             //取消当前编辑行把当前编辑行把undefined回滚改变的数据,取消选择的行
             editRow = undefined;
             datagrid.datagrid("rejectChanges");
             datagrid.datagrid("unselectAll");
         }
         }, '-'],
        onAfterEdit: function (rowIndex, rowData, changes) {
            //endEdit该方法触发此事件
            //console.info(rowData);
            $.ajax({  
	               type: "post",  
	                url: "/Share_system/share/updateshare.do",  
	                data: 
	  			    {//只允许更新以下字段：主题，内容，听课人数，其他字段不允许更新
	  			    	"s_id":rowData.shareId,
//	  			    	"type":rowData.type,
	  			    	"subject":rowData.subject,
	  			    	"content":rowData.content,
	  			    	"stu_num":rowData.studentNum
	  			    	
	                 },  
	                success: function (result) {  
	                    if (result.success) {  
	                        $.messager.alert("提示", "更新成功！", "info");  
	                        $('#mysharetable').datagrid('reload');
//	                        getAllData();  
	                    }  
	                    else {  
	                        $.messager.alert("错误", '更新失败，请联系管理员', "info");  
	                    }  
	                },  
	                dataType: "json"  
	            });  
            editRow = undefined;
        },
        onDblClickRow: function (rowIndex, rowData) {
        //双击开启编辑行
            if (editRow != undefined) {
                datagrid.datagrid("endEdit", editRow);
            }
            if (editRow == undefined) {
                datagrid.datagrid("beginEdit", rowIndex);
                editRow = rowIndex;
            }
        }
    });
});


