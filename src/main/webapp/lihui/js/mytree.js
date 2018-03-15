function getContextPath() {

    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var result = pathName.substr(0,index+1);
    return result;
}


function clickTreeNode()
{//管理员页面，点击左侧树，在中部添加tab，并显示相应的管理表格
	$('#mytree').tree({
	onClick: function(node){
//		alert(node.text);  // 调试代码
//	     $('#mytab1').tabs('add',{title:node.text,closable:true,padding:10});
		
                 var title = node.text;  
                    if(title=="用户管理"){  
//                         var userurl = "usermanagement.jsp" ;                  
                         addTab(title, "usermanagement.jsp") ;
                        
                    }else 
                    if(title=="分享管理"){  
//                         var shareurl = "sharemanagement.jsp" ;      
                         addTab(title, "sharemanagement.jsp") ;
                         
                    }     
                    else if(title=="公告管理"){  
//                         var announceurl = "announcemanagement.jsp" ;     
                         addTab(title, "announcemanagement.jsp") ; 
                         
                    }
//                    else if(title=="论坛管理"){  
////                         var forumurl = "forummanagement.jsp" ;     
//                         addTab(title, "forummanagement.jsp" ) ; 
//                         
//                    }  
                    else{  
                        $.messager.alert("提示", "功能模块未开发！", "info"); 
                    }                           
                } 
                
      });
      
}
            //添加选项卡 ，使用class选择器.
            function addTab(title, url){
            	var mytab1 = $('.easyui-tabs');
                    var content = '<iframe scrolling="auto" frameborder="0"  src="' + url+ '" style="width:100%;height:100%;"></iframe>';  
                    	if(mytab1.tabs('exists', title)){  
                            mytab1.tabs('select', title);            
		                }else{  
		                	mytab1.tabs('add', {  
		                     title : title,  
		                     closable :  true,  
		                     content : content,
		                     width: $('#mainPanle').width() ,  
		                     height: $('#mainPanle').height()  
		                    } );   
		                       
		                }            
            }  
            //使用id选择器#
//            function addTab(title, url){                  
//                    content = '<iframe scrolling="auto" frameborder="0"  src="' + url+ '" style="width:100%;height:100%;"></iframe>';  
//                    if(!$("#mytab1").tabs('exists', title)){  
//                        $("#mytab1").tabs("add", {  
//                     title : title,  
//                     closable :  true,  
//                     content : content,  
//                     width: $('#mainPanle').width() ,  
//                     height: $('#mainPanle').height()  
//                    } );                   
//                }else{  
//                     $('#mytab1').tabs('select', title);  
//                }            
//            }  
              
             function tabClose() {  
               /*双击关闭TAB选项卡*/  
               $(".tabs-inner").dblclick(function () {  
                   var subtitle = $(this).children("span").text();  
                   $('#mytab1').tabs('close', subtitle);
                   
               })  
//   
//               $(".tabs-inner").bind('contextmenu', function (e) {  
//                   $('#mm').menu('show', {  
//                       left: e.pageX,  
//                       top: e.pageY 
//                   });  
//                   var subtitle = $(this).children("span").text();  
//                   $('#mm').data("currtab", subtitle);  
//                   return false;  
//               });  
           }  
   
          
      
