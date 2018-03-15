


$(function () {
    var datagrid; //定义全局变量datagrid
    var editRow = undefined; //定义全局变量：当前编辑的行
    datagrid = $("#homepagesharetable").datagrid({
        url: '/Share_system/share/queryOthersShareByPage.do', //请求的数据源
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
	                url: "/Share_system/shareGrade/addOrUpdateShareGrade.do",  
	                data: 
	  			    {
	  			    	"s_id":rowData.shareId,
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