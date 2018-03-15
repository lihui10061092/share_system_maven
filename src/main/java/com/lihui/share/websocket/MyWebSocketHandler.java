package com.lihui.share.websocket;

import com.alibaba.fastjson.JSONObject;
import com.lihui.share.base.Base;
import com.lihui.share.web.vo.Code;
import com.lihui.share.web.vo.SessionUser;
import com.lihui.share.web.vo.msg.MsgDeliverServiceRoom;

import org.apache.log4j.Logger;
import org.springframework.web.socket.*;

import java.util.Date;
import java.util.Map;

public class MyWebSocketHandler extends Base implements WebSocketHandler {
//    private static final Logger logger = Logger.getLogger(MyWebSocketHandler.class);
    private SessionManager sessionManager = SessionManager.instance();
    //初次链接成功执行
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.debug("链接成功......");
        Object temp = session.getAttributes().get(SessionManager.USER_SESSION);

        if(temp!= null){
            SessionUser user = (SessionUser)temp; //用户信息
            Integer userType = user.getMsgUserInfo().getUserType();
            switch (userType) {
                case Code.USER_TYPE_1: //是用户
//                    userMap.put(userId, webSocketSession);
                    break;
                case Code.USER_TYPE_2: //是游客，自动加入系统聊天室,并且发送通知信息
                    MsgDeliverServiceRoom mdsr_userInfo = new MsgDeliverServiceRoom();
                    mdsr_userInfo.setMsgType(Code.MSG_TYPE_3);
                    mdsr_userInfo.setMsgUserInfo(user.getMsgUserInfo());
                    session.sendMessage(new TextMessage(JSONObject.toJSONString(mdsr_userInfo)));

                    sessionManager.chatroomMapPut(0l,user.getMsgUserInfo().getUserId(),session);
                    sessionManager.put(user.getMsgUserInfo().getUserId(),user.getMsgUserInfo().getUserType(),session);
                    user.getChatroomIds().add(0l);
                    MsgDeliverServiceRoom mdsr = new MsgDeliverServiceRoom();
                    mdsr.setMsgUserInfo(Code.sysUser);
                    mdsr.setTimeOfArrive(new Date());
                    mdsr.setTargetId(0l);
                    mdsr.setMsgBody(user.getMsgUserInfo().getNickname()  + "骑着皮皮虾进入了本聊天室.....");
                    mdsr.setMsgType(Code.MSG_TYPE_2);
                    SessionUser sys = new SessionUser();
                    sys.setMsgUserInfo(Code.sysUser);
                    sessionManager.sendMessage(sys,JSONObject.toJSONString(mdsr),Code.MSG_ORIGIN_SERVICE);

                    mdsr.setMsgType(Code.MSG_TYPE_4);
                    mdsr.setOnlieNum(sessionManager.getRoomOnlieNum(0l));
                    mdsr.setOnlieList(sessionManager.getRoomOnlieList(0l));
                    //推送在线人数和在线列表
                    sessionManager.sendMessage(sys,JSONObject.toJSONString(mdsr),Code.MSG_ORIGIN_SERVICE);
                    break;
                default:
                    break;
            }
        }
    }

    //接受消息处理消息
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        Map<String, Object> attributes = webSocketSession.getAttributes();
        SessionUser user = (SessionUser) attributes.get(SessionManager.USER_SESSION);
        sessionManager.sendMessage(user, webSocketMessage.getPayload() + "",Code.MSG_ORIGIN_CLIENT);
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        if(webSocketSession.isOpen()){
            webSocketSession.close();
        }
        logger.debug("链接出错，关闭链接......");
        sessionManager.remove(webSocketSession);
        MsgDeliverServiceRoom mdsr = new MsgDeliverServiceRoom();
        mdsr.setMsgUserInfo(Code.sysUser);
        mdsr.setTimeOfArrive(new Date());
        mdsr.setTargetId(0l);
        SessionUser sys = new SessionUser();
        sys.setMsgUserInfo(Code.sysUser);
        mdsr.setMsgType(Code.MSG_TYPE_4);
        mdsr.setOnlieNum(sessionManager.getRoomOnlieNum(0l));
        mdsr.setOnlieList(sessionManager.getRoomOnlieList(0l));
        //推送在线人数和在线列表
        sessionManager.sendMessage(sys,JSONObject.toJSONString(mdsr),Code.MSG_ORIGIN_SERVICE);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        logger.debug("链接关闭......" + closeStatus.toString());
        sessionManager.remove(webSocketSession);
        MsgDeliverServiceRoom mdsr = new MsgDeliverServiceRoom();
        mdsr.setMsgUserInfo(Code.sysUser);
        mdsr.setTimeOfArrive(new Date());
        mdsr.setTargetId(0l);
        SessionUser sys = new SessionUser();
        sys.setMsgUserInfo(Code.sysUser);
        mdsr.setMsgType(Code.MSG_TYPE_4);
        mdsr.setOnlieNum(sessionManager.getRoomOnlieNum(0l));
        mdsr.setOnlieList(sessionManager.getRoomOnlieList(0l));
        //推送在线人数和在线列表
        sessionManager.sendMessage(sys,JSONObject.toJSONString(mdsr),Code.MSG_ORIGIN_SERVICE);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
