package com.lihui.share.web.vo.msg;

/**
 * 消息传递中的 用户信息
 */
public class MsgUserInfo {
    /** 用户id */
    private Long userId;
    /** 昵称*/
    private String nickname;
    /** 头像 */
    private String icons;
    private Integer userType;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIcons() {
        return icons;
    }

    public void setIcons(String icons) {
        this.icons = icons;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }
}
