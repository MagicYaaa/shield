package com.weapp.api.domain.Repository;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

public class Room {
     /** 房间信息:set  json格式 room:roomid
     *      房间介绍
     *      今天明天
     *      时间
     *      路线
     *      成员列表list

     */

    public int route;
    public int today;
    public String time;
    public long timestamp;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String,String> userInfoList;
    public String notice;
    public String leaderName;
    public List<String> avatarList = Lists.newArrayList();


    public List<String> getAvatarList() {
        return avatarList;
    }

    public void setAvatarList(List<String> avatarList) {
        this.avatarList = avatarList;
    }

    public int getRoute() {
        return route;
    }

    public void setRoute(int route) {
        this.route = route;
    }

    public int getToday() {
        return today;
    }

    public void setToday(int today) {
        this.today = today;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Map<String, String> getUserInfoList() {
        return userInfoList;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public void setUserInfoList(Map<String, String> userInfoList) {
        this.userInfoList = userInfoList;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }
}
