package com.lihui.share.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lihui.share.entity.User;
import com.lihui.share.web.vo.Code;
import com.lihui.share.web.vo.SessionUser;
import com.lihui.share.web.vo.msg.MsgDeliverClient;
import com.lihui.share.web.vo.msg.MsgDeliverClientPToP;
import com.lihui.share.web.vo.msg.MsgDeliverClientRoom;
import com.lihui.share.web.vo.msg.MsgDeliverService;
import com.lihui.share.web.vo.msg.MsgDeliverServicePToP;
import com.lihui.share.web.vo.msg.MsgDeliverServiceRoom;
import com.lihui.share.web.vo.msg.MsgUserInfo;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

public class SessionManager {
    /**
     * session中的用户信息，包括游客的都是该属性
     */
    public static final String USER_SESSION = "user_session";

    private SessionManager() {
    }

    ;

    public static SessionManager instance() {
        return SingletonHolder.sessionManager;
    }

    private static class SingletonHolder {
        private static SessionManager sessionManager = new SessionManager();
    }

    //有id 的用户 k = 用户id
    private HashMap<Long, WebSocketSession> userMap = new HashMap<>();
    //游客 ke = 游客id
    private HashMap<Long, WebSocketSession> touristMap = new HashMap<>();
    // 聊天室 k= 聊天室id，v = <用户id，对应的session>
    private HashMap<Long,HashMap<Long,WebSocketSession>> chatroomMap =  new HashMap<>();

    /**
     * 增加一个用户session
     *
     * @param userId 用户id
     * @param webSocketSession
     * @param userType 用户类型
     */
    public void put(Long userId,int userType, WebSocketSession webSocketSession) {
        switch (userType) {
            case Code.USER_TYPE_1:
                userMap.put(userId, webSocketSession);
                break;
            case Code.USER_TYPE_2:
                touristMap.put(userId, webSocketSession);
                break;
            default:
                touristMap.put(userId, webSocketSession);
                break;
        }
    }

    /**
     * sss
     * @param webSocketSession
     */
    public void remove(WebSocketSession webSocketSession){
        Map<String, Object> attributes = webSocketSession.getAttributes();
        SessionUser user = (SessionUser) attributes.get(SessionManager.USER_SESSION);
        Integer userType = user.getMsgUserInfo().getUserType();
        Long userId = user.getMsgUserInfo().getUserId();
        switch (userType) {
            case Code.USER_TYPE_1:
                userMap.remove(userId);
                break;
            case Code.USER_TYPE_2:
                touristMap.remove(userId);
                break;
            default:
                touristMap.remove(userId);
                break;
        }
        //判断是否加入了对应的聊天室
        ArrayList<Long> chatroomIds = user.getChatroomIds();
        Iterator<Long> iterator = chatroomIds.iterator();
        while (iterator.hasNext()){
            Long id = iterator.next();
            if(chatroomMap.containsKey(id)){
                chatroomMap.get(id).remove(userId);
                iterator.remove();
            }
        }
    }

    /**
     * 往对应的聊天室增加一个人
     * @param chatroomId 聊天室id
     * @param userId 用户id
     * @param webSocketSession
     */
    public  void chatroomMapPut(Long chatroomId,Long userId,WebSocketSession webSocketSession){
        HashMap<Long, WebSocketSession> longWebSocketSessionHashMap = chatroomMap.get(chatroomId);
        if(longWebSocketSessionHashMap == null){
            longWebSocketSessionHashMap = new HashMap<>();
        }
        longWebSocketSessionHashMap.put(userId,webSocketSession);
        chatroomMap.put(chatroomId,longWebSocketSessionHashMap);
    }

    /**
     * 发送消息
     * @param user  消息发送者
     * @param msgJson 消息json
     * @param msgOrigin 消息源
     */
    public void sendMessage(SessionUser user, String msgJson,int msgOrigin) {
        MsgDeliverClient mdc = null;
        MsgDeliverService mds = null;
        JSONObject jsonObject = JSON.parseObject(msgJson);
        Integer msgType = jsonObject.getInteger("msgType");
        if(Code.MSG_ORIGIN_CLIENT == msgOrigin){ //来自客户端
            if(Code.MSG_TYPE_1 == msgType.intValue()){
                mdc = JSON.parseObject(msgJson, MsgDeliverClientPToP.class);
            }else{
                mdc = JSON.parseObject(msgJson, MsgDeliverClientRoom.class);
            }
        }else if(Code.MSG_ORIGIN_SERVICE == msgOrigin){
            if(Code.MSG_TYPE_1 == msgType.intValue()){
                mds = JSON.parseObject(msgJson, MsgDeliverServicePToP.class);
            }else{
                mds = JSON.parseObject(msgJson, MsgDeliverServiceRoom.class);
            }
        }


        switch (msgType) {
            case Code.MSG_TYPE_1:
                break;
            case Code.MSG_TYPE_2:  //聊天室消息
                if(Code.MSG_ORIGIN_CLIENT == msgOrigin){ //来自客户端
                    MsgDeliverClientRoom mdcr = (MsgDeliverClientRoom)mdc;
                    if(chatroomMap.containsKey(mdcr.getTargetId())){
                        HashMap<Long, WebSocketSession> room = chatroomMap.get(mdcr.getTargetId());
                        MsgDeliverServiceRoom mdsr = new MsgDeliverServiceRoom();
                        mdsr.setMsgType(Code.MSG_TYPE_2);
                        mdsr.setMsgBody(mdcr.getMsgBody());
                        mdsr.setTargetId(mdcr.getTargetId());
                        mdsr.setMsgUserInfo(user.getMsgUserInfo());
                        mdsr.setTimeOfArrive(new Date());
                        for (Map.Entry<Long,WebSocketSession>  entry : room.entrySet()){
                            WebSocketSession session = entry.getValue();
                            try {
                                session.sendMessage(new TextMessage(JSONObject.toJSONString(mdsr)));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }else if(Code.MSG_ORIGIN_SERVICE == msgOrigin){
                    MsgDeliverServiceRoom mdsr = (MsgDeliverServiceRoom)mds;
                    if(chatroomMap.containsKey(mdsr.getTargetId())){
                        HashMap<Long, WebSocketSession> room = chatroomMap.get(mdsr.getTargetId());
                        for (Map.Entry<Long,WebSocketSession>  entry : room.entrySet()){
                            WebSocketSession session = entry.getValue();
                            try {
                                session.sendMessage(new TextMessage(JSONObject.toJSONString(mdsr)));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                break;
            case Code.MSG_TYPE_4:  //聊天室消息
                MsgDeliverServiceRoom mdsr = (MsgDeliverServiceRoom)mds;
                HashMap<Long, WebSocketSession> map = chatroomMap.get(mdsr.getTargetId());
                if(map == null){
                    return;
                }
                for (Map.Entry<Long,WebSocketSession>  entry : map.entrySet()){
                    WebSocketSession session = entry.getValue();
                    try {
                        session.sendMessage(new TextMessage(JSONObject.toJSONString(mdsr)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case Code.MSG_TYPE_5:  //聊天室消息
                MsgDeliverClientRoom mdcr = (MsgDeliverClientRoom)mdc;
                HashMap<Long, WebSocketSession> map5 = chatroomMap.get(mdcr.getTargetId());
                if(map5 == null){
                    return;
                }
                for (Map.Entry<Long,WebSocketSession>  entry : map5.entrySet()){
                    WebSocketSession session = entry.getValue();
                    try {
                        session.sendMessage(new TextMessage(JSONObject.toJSONString(mdcr)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }


    }
    /**
     * 给所有在线用户发送消息,但是不包括自己
     * @param user 是谁发的？
     * @param message
     */
    public void sendMessageToUsers(SessionUser user,String message) {
        for (Map.Entry<Long, WebSocketSession> entry : this.userMap.entrySet()) {
            WebSocketSession session = entry.getValue();
            if(entry.getKey().longValue() != user.getUser_id()) {
                    if (session.isOpen()) {
                        try {
                            session.sendMessage(this.getMsg(user, message));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            }
        }
        for (Map.Entry<Long, WebSocketSession> entry : this.touristMap.entrySet()) {
            WebSocketSession session = entry.getValue();
            if(entry.getKey().longValue() != user.getUser_id()) {
                if (session.isOpen()) {
                    try {
                        session.sendMessage(this.getMsg(user, message));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    /**
     * 获取消息 模版。
     * @param temp
     * @param message
     * @return
     */
    private  TextMessage getMsg(User user,String message) {
//        JSONObject result = new JSONObject();
//        result.put("")
//                return new TextMessage(user.getNickname() + "  :  " + DateUtil.dateToString(new Date(), DateUtil.PATTERN_WHOLE) + "<br/>&nbsp;&nbsp;" + message);
//            } else {
//                return new TextMessage(temp + "  :  " + DateUtil.dateToString(new Date(), DateUtil.PATTERN_WHOLE) + "<br/>&nbsp;&nbsp;" + message);
//            }
//        }
        return  null;
    }

    /**
     * 给某个用户发送消息
     *
     * @param temp
     * @param message
     */
    public void sendMessageToUser(Object temp, String message) {
//        if (temp instanceof User) {
//            User user = (User) temp;
//            this.sendMessageToUser(user.getUserId(), this.getMsg(temp,message));
//        } else {
//            this.sendMessageToUser(temp.toString(), this.getMsg(temp,message));
//        }
    }

    /**
     * 给某个游客发送消息
     *
     * @param touristName
     * @param message
     */
    private void sendMessageToUser(String touristName, TextMessage message) {
        WebSocketSession session = this.touristMap.get(touristName);
        if (session != null) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 给某个用户发送消息
     *
     * @param userId
     * @param message
     */
    private void sendMessageToUser(Integer userId, TextMessage message) {
        WebSocketSession session = this.userMap.get(userId);
        if (session != null) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取指定聊天室 人数
     * @param roomId
     * @return
     */
    public int getRoomOnlieNum(long roomId) {
        HashMap<Long, WebSocketSession> map = chatroomMap.get(roomId);
        if(map == null){
            return  0;
        }
        return map.size();
    }

    /**
     * 获取指定聊天室在线列表用户信息
     * @param roomId
     * @return
     */
    public List<MsgUserInfo> getRoomOnlieList(long roomId) {
        List<MsgUserInfo> result = new ArrayList<>();
        HashMap<Long, WebSocketSession> map = chatroomMap.get(roomId);
        if(map == null){
            return  null;
        }

        for (Map.Entry<Long,WebSocketSession>  entry : map.entrySet()){
            WebSocketSession session = entry.getValue();
            Map<String, Object> attributes = session.getAttributes();
            SessionUser user = (SessionUser) attributes.get(SessionManager.USER_SESSION);
            result.add(user.getMsgUserInfo());
        }
        return result;
    }

}
