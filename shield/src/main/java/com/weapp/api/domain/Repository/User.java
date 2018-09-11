package com.weapp.api.domain.Repository;

public class User {
    /**
     * 用户信息: set json格式  user:uid
     * 昵称
     * 头像
     * 建立的房间id
     * 加入的房间id
     * c
     */
    public String uid;
    public String name;
    public String avatar;
    public String joinRoomId;
    public String createRoomId;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getJoinRoomId() {
        return joinRoomId;
    }

    public void setJoinRoomId(String joinRoomId) {
        this.joinRoomId = joinRoomId;
    }

    public String getCreateRoomId() {
        return createRoomId;
    }

    public void setCreateRoomId(String createRoomId) {
        this.createRoomId = createRoomId;
    }
}
