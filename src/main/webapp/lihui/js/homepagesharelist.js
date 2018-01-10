


$(function () {
    var datagrid; //定义全局变量datagrid
    var editRow = undefined; //定义全局变量：当前编辑的行
    datagrid = $("#homepagesharetable").datagrid({
        url: '/share_system/share/queryOthersShareByPage.do', //请求的数据源
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
        idField: 'shareId', //主键
        columns: [[//显示的列
        {field: 'type', title: '类型', width: 100, sortable: true //checkbox:true
//           editor: { type: 'validatebox', options: { required: true} }
        },
        {field: 'subject', title: '主题', width: 190, sortable: true
//           editor: { type: 'validatebox', options: { required: true} }
        },
         { field: 'content', title: '内容', width: 190, sortable: true
//             editor: { type: 'validatebox', options: { required: true} }
         },
         { field: 'attachements', title: '附件', width: 220, sortable: true
//             editor: { type: 'validatebox', options: { required: true} }
         },
          { field: 'auther', title: '讲师', width: 100,
        	 formatter:function(value,row,rowIndex) {
				    if(row.auther!=undefined){
				        return row.auther.emp_id;
				    }
             }
//              editor: { type: 'validatebox', options: { required: true} }
          },
           { field: 'studentNum', title: '听课人数', width: 180
//               editor: { type: 'validatebox', options: { required: true} }
           },
           { field: 'grade_num', title: '评分人数', width: 180
//               editor: { type: 'validatebox', options: { required: true} }
           },
           { field: 'myScore', title: '我的评分', width: 150,
           formatter:function(value,row,rowIndex) {
				    if(row.userGrades!=undefined){
				    	var userGrade = row.userGrades[0];
				    	if(userGrade != undefined){
				        return userGrade.grade;
				    	}
				    	else return '';
				    }
				    else return '';
           		},
               editor: { type: 'validatebox', options: { required: true}}
           }
          
           ]],
        queryParams: {}, //查询参数
        toolbar: [
//        {
//        	 text: '添加', iconCls: 'icon-add', handler: function () {//添加列表的操作按钮添加，修改，删除等
//            //添加时先判断是否有开启编辑的行，如果有则把开户编辑的那行结束编辑
//            if (editRow != undefined) {
//                datagrid.datagrid("endEdit", editRow);
//            }
//            //添加时如果没有正在编辑的行，则在datagrid的第一行插入一行
//            if (editRow == undefined) {
//                datagrid.datagrid("insertRow", {
//                    index: 0, // index start with 0
//                    row: {
//
//                    }
//                });
//                //将新插入的那一行开户编辑状态
//                datagrid.datagrid("beginEdit", 0);
//                //给当前编辑的行赋值
//                editRow = 0;
//            }
//
//        }
//        }, '-',
//         { text: '删除', iconCls: 'icon-remove', handler: function () {
//             //删除时先获取选择行
//        		var row = $('#usertable').datagrid('getSelected');
//        	    if (row){
//        	    	$.messager.confirm('确认', '是否确认删除该用户?', function (r) {  
//        	            $.ajax({  
//        	                type: "post",  
//        	                url: "/share_system/user/deleteUserById.do",  
//        	                data: 
//        	  			    {
//        	                  	"user_id":row.user_id
//        	                  	
//        	                 },  
//        	                success: function (result) {  
//        	                    if (result.success) {  
//        	                        $.messager.alert("提示", "删除成功！", "info");  
//        	                        $('#usertable').datagrid('reload');
////        	                        getAllData();  
//        	                    }  
//        	                    else {  
//        	                        $.messager.alert("错误", result.Msg, "info");  
//        	                    }  
//        	                },  
//        	                dataType: "json"  
//        	            });  
////        	        	alert('User ID:'+row.user_id);
//        	    });  
//        	   }
//        	   else{
//        	   	$.messager.confirm('提示', '请先选择要删除的行')
//        	   }
//        	   
//                     
//         }
//         }, '-',
//         { text: '修改', iconCls: 'icon-edit', handler: function () {
//             //修改时要获取选择到的行，多行getSelections
//             var row = datagrid.datagrid("getSelected");
//             //如果只选择了一行则可以进行修改，否则不操作
//             if (row.length == 1) {
//                 //修改之前先关闭已经开启的编辑行，当调用endEdit该方法时会触发onAfterEdit事件
//                 if (editRow != undefined) {
//                     datagrid.datagrid("endEdit", editRow);
//                 }
//                 //当无编辑行时
//                 if (editRow == undefined) {
//                     //获取到当前选择行的下标
//                     var index = datagrid.datagrid("getRowIndex", rows[0]);
//                     //开启编辑
//                     datagrid.datagrid("beginEdit", index);
//                     //把当前开启编辑的行赋值给全局变量editRow
//                     editRow = index;
//                     //当开启了当前选择行的编辑状态之后，
//                     //应该取消当前列表的所有选择行，要不然双击之后无法再选择其他行进行编辑
//                     datagrid.datagrid("unselectAll");
//                 }
//             }
//         }
//         }, '-',
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
	                url: "/share_system/shareGrade/addOrUpdateShareGrade.do",  
	                data: 
	  			    {
	  			    	"shareId":rowData.shareId,
	                  	"user_id":rowData.user_id,
	                  	"myScore":rowData.myScore
	                 },  
	                success: function (result) {  
	                    if (result.success) {  
	                        $.messager.alert("提示", "保存成功！", "info");  
	                        $('#homepagesharetable').datagrid('reload');
//	                        getAllData();  
	                    }  
	                    else {  
	                        $.messager.alert("错误", result.Msg, "info");  
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