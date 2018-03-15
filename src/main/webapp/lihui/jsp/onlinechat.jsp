<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>  
<%  
    String path = request.getContextPath();  
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()  
            + path + "/";  
%>  
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>webSocket在线聊天室</title>
    <script src="<%=basePath%>/assets/common/app.js"></script>
    <script src="<%=basePath%>/assets/common/SocketClient.js"></script>
    <script src="<%=basePath%>/assets/common/specialEffects.js"></script>
    <link rel="stylesheet" href="<%=basePath%>/assets/common/specialEffects.css">
    <link rel="stylesheet" href="<%=basePath%>/assets/common/chatTM.css">
    <link rel="stylesheet" href="<%=basePath%>/assets/common/Msg.css">

    <script src="<%=basePath%>/assets/common/texiao/qqDoudong/js/sucai.js"></script>
    <link rel="stylesheet" href="<%=basePath%>/assets/common/texiao/qqDoudong/css/style.css">


    <script src="<%=basePath%>/assets/lib/sockjs/sockjs.js"></script>
    <script src="<%=basePath%>/assets/lib/stomp/lib/stomp.js"></script>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="<%=basePath%>/assets/lib/bootstrap/css/bootstrap.min.css">
    <!-- 可选的Bootstrap主题文件（一般不用引入） -->
    <!--<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">-->
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="<%=basePath%>/assets/lib/jquery/jquery.min.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="<%=basePath%>/assets/lib/bootstrap/js/bootstrap.min.js"></script>

    <script src="<%=basePath%>/assets/common/grayscale.js"></script>
    <link rel="stylesheet" href="<%=basePath%>/assets/lib/jquery-ui-bootstrap/assets/css/font-awesome.min.css">
    <link rel="stylesheet" href="<%=basePath%>/assets/lib/jquery-ui-bootstrap/css/custom-theme/jquery-ui-1.10.0.custom.css">
    <script src="<%=basePath%>/assets/lib/jquery-ui-bootstrap/assets/js/jquery-ui-1.10.0.custom.min.js"></script>


    <script type="text/javascript">
        $(function () {
            $('[data-toggle="popover"]').popover();  //初始化所有弹窗
            //个人圆圈头像移动
            $("#cir").draggable();
            //好友列表 移动
            $("#right").draggable({
                handle: '#right-top-top'
            });
            // 聊天界面 移动
            $('#left').draggable({
                handle: '#tou'
            });
            // 聊天界面 限制大小
            $('#left').resizable({
                minWidth: "480",
                minHeight: "400",
                maxWidth: "720",
                maxHeight: "600"
            });
            $('#right').resizable({
                minWidth: "216",
                minHeight: "400",
                maxWidth: "324",
                maxHeight: "600"
            });

            $("#douDong").click(function () {
                send("x", 5);
            });


            grayscale($("#photo"));  //头像变灰色
//           connection("zq1989.oicp.net/echo", "zq1989.oicp.net/sockjs/echo");
//            connection("zq1989.oicp.net/echo", "zq1989.oicp.net/sockjs/echo")
//            connection("localhost:8080/echo", "localhost:8080/sockjs/echo");

            connection(window.location.host + "/Share_system/echo.do", window.location.host + "/Share_system/sockjs/echo.do")
            $('#send').bind('click', function () {
                var message = $("#message");
                send(message.val(), 2);
                message.val("");
            });
            $("#message").keydown(function (event) {
                if (event.keyCode == 13) {
                    $("#send").click();
                }
            });

            $('#left-right-top-close').bind('click', function () {
                $("#left").hide("slow");
            });
            $('#left-right-top-hide').bind('click', function () {
                $("#left").hide("slow");
            });

            $('#right-top-top-right-close').bind('click', function () {
                $("#right").hide("slow");
            });
            $('#photo').bind('click', function () {
                $("#right").show("slow");
            });

            scrollQQ($("#cir"));
            scrollTT($("#left"));
            $("#roomListRefresh").click(function () {
                $.get("/room/getRoomList", function (data, status) {
                    console.log(data);
                    if (status == "success") {
                        $("#onLRoom").empty();
                        $.each(data, function (i, o) {
                            var objLits = ' <li class="list-group-item" id="roomLit' + o.chatroomId + '">' +
                                ' [' + o.chatroomId + ']' + o.name
                            '</li>';
                            $("#onLRoom").append(objLits);
                        });
                    }
                });
            });

            $("#roomListRefresh").click();
            $("#roomLit1").click(function () {
                $("#left").show("slow");  //待完善。该点击事件无效
            });


            $('<audio id="chatAudio"><source src="/assets/common/audio/ios7.mp3" type="audio/mpeg"></audio>').appendTo('body');//载入声音文件
        });

    </script>
    <!--百度分享-->
    <script>window._bd_share_config = {
        "common": {
            "bdSnsKey": {},
            "bdText": "",
            "bdMini": "2",
            "bdMiniList": false,
            "bdPic": "",
            "bdStyle": "0",
            "bdSize": "16"
        },
        "slide": {"type": "slide", "bdImg": "2", "bdPos": "left", "bdTop": "42"},
        "image": {"viewList": ["qzone", "tsina", "tqq", "renren", "weixin"], "viewText": "分享到：", "viewSize": "16"},
        "selectShare": {"bdContainerClass": null, "bdSelectMiniList": ["qzone", "tsina", "tqq", "renren", "weixin"]}
    };
    with (document)0[(getElementsByTagName('head')[0] || body).appendChild(createElement('script')).src = 'http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion=' + ~(-new Date() / 36e5)];</script>


</head>
<body>
这是在线聊天页面
<!--<div class="msg_bianKuangWw">
    <img class="msgIcon_left" src="/assets/common/image/1.jpg">
    <div class="msgTitle_left">xxx</div>
    <div class="msgPanel_left">
        <span class="triangle_left"></span>
        <div class="article_left">游客79602 欢迎您进入测试聊天室-\\(^o^)/</div>
    </div>
</div>
<div class="msg_bianKuangWw">
    <img class="msgIcon_right" src="/assets/common/image/2.jpg">
    <div class="msgTitle_right" style="text-align:right; padding-right:50px; ">xxx</div>
    <div class="msgPanel_right">
        <span class="triangle_right"></span>
        <div class="article_right">游客79602 欢迎您进入测试聊天室-\\(^o^)/</div>
    </div>
</div>-->


<!-- 圆圈头像 -->
<div class="cir" id="cir" style="z-index:999">
    <img id="photo" src="/assets/common/image/defeatPhoto.jpg"/>
    <div id="userInfo"></div>
</div>
<div id="topBiankuang">
    <div id="right" class="panel panel-default">
        <div id="right-top">
            <div class="panel-body" id="right-top-top">
                <div id="right-top-top-left">
                    chatTM
                </div>
                <div class="pull-right" id="right-top-top-right" style="margin-top:  -30px;margin-right: -10px">
                    <button id="right-top-top-right-close" class="close">&times;</button>
                </div>
            </div>
        </div>
        <div id="right-down">
            <!-- Nav tabs -->
            <ul class="nav nav-tabs" role="tablist">
                <li role="presentation" class="active"><a href="#onLRoom" aria-controls="onLRoom" role="tab" data-toggle="tab">聊天室<span
                        id="roomListRefresh" class="glyphicon glyphicon-refresh cc"/></a></li>
                <li role="presentation"><a href="#onLFriendList" aria-controls="onLFriendList" role="tab" data-toggle="tab">好友列表</a></li>
            </ul>
            <!-- Tab panes -->
            <div class="tab-content">
                <div role="tabpanel" class="tab-pane active" id="onLRoom">xxxxxx</div>
                <div role="tabpanel" class="tab-pane" id="onLFriendList">5645656.</div>
            </div>
        </div>

    </div>

    <!-- 聊天界面整个边框-->
    <div id="left" class="panel panel-default">
        <!-- 正在对话列表-->
        <!-- <div id="left-left" class="panel panel-default">正在对话列表</div>-->
        <!-- 聊天界面-->
        <div id="left-right" class="panel panel-default">
            <div id="left-right-top">
                <div class="panel-body" id="tou">
                    <div id="tou-msg">
                        Basic panel example
                    </div>
                    <div class="pull-right" style="margin-top:  -30px;margin-right: -10px">
                        <button id="left-right-top-close" class="close">&times;</button>
                        <button id="left-right-top-hide" class="close" style="margin-right: 8px"><span
                                class="glyphicon glyphicon-minus"></span></button>
                    </div>

                </div>
            </div>
            <div id="left-right-down" class="panel panel-default">
                <div id="left-right-down-left">
                    <div id="left-right-down-left-message">
                        <div class="well" id="msg"></div>
                    </div>
                    <div id="left-right-down-left-inpit">
                        <textarea style="height: 100%" class="form-control" placeholder="发送信息..." id="message"></textarea>
                    </div>
                    <div id="left-right-down-left-btn">
                        <div class="input-group">
                              <span class="input-group-btn">
                                  <button class="btn btn-primary" type="button" id="send" style="float: right;">发送</button>
                                  <button class="btn btn-inverse" type="button" id="close" style="float: right;">关闭</button>
                                  <button class="btn btn-inverse" type="button" id="douDong" style="float: right;">抖一下</button>
                              </span>
                        </div>
                    </div>
                </div>
                <div id="left-right-down-right" class="panel panel-default">
                    <div id="left-right-down-right_noticeGJ">
                        <div class="well" id="left-right-down-right_notice">聊天室</div>
                    </div>
                    <div id="left-right-down-right-onlineListGJ">在线人数:0</div>
                    <div id="left-right-down-right_listGJ" class="panel panel-default">
                        <ul class="list-group" id="onlineList">
                        </ul>
                    </div>
                </div>
            </div>

        </div>
    </div>

</div>
</body>
</html>