package com.weapp.api.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.weapp.api.domain.Repository.Room;
import com.weapp.api.domain.Repository.User;
import com.weapp.api.domain.request.CreateJson;
import com.weapp.api.domain.request.QureryJson;
import com.weapp.api.domain.response.BaseResp;
import com.weapp.common.util.JsonMapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;


@Service
public class DataService {
    private static final Logger logger = LogManager.getLogger(DataService.class);
    @Autowired
    WebSocketMessageService webSocketMessageService;

    public static final String todayList = "roomlist:today";
    public static final String tomorrowList = "roomlist:tomorrow";


    @Autowired
    StringRedisTemplate redis;

    /**
     * 用户登录
     *
     * @param user
     */
    public User setUser(User user) {

        User redisUser = getUser(user.uid);
        if (redisUser == null) {
            String userJson = JsonMapper.nonEmptyMapper().toJson(user);
            redis.opsForValue().set(getUserId(user.uid), userJson);
        } else {
            user.createRoomId = redisUser.createRoomId;
            user.joinRoomId = redisUser.joinRoomId;

            String userJson = JsonMapper.nonEmptyMapper().toJson(user);
            redis.opsForValue().set(getUserId(user.uid), userJson);
        }
        return user;

    }

    /**
     * 更新用户
     *
     * @param user
     * @return
     */
    private User updateUser(User user) {

        String userJson = JsonMapper.nonEmptyMapper().toJson(user);
        redis.opsForValue().set(getUserId(user.uid), userJson);
        return user;

    }


    /**
     * 创建房间
     *
     * @param data
     * @return
     */
    public BaseResp createRoom(CreateJson data) {
        int hour  = Integer.valueOf(data.time.split(":")[0]);
        Room room = new Room();
        room.setNotice(data.notice);
        room.setRoute(data.route);
        room.setTime(data.time);
        room.setToday(data.today);
        room.setTimestamp(data.timestamp);
        Map<String, String> user = Maps.newHashMap();
        User u = getUser(data.uid);
        if (u == null) {
            return new BaseResp(-100, "用户不存在");
        }
        user.put(data.uid, u.avatar);
        room.setUserInfoList(user);
        String roomId = getRoomId(UUID.randomUUID().toString());
        redis.opsForValue().set(roomId, JsonMapper.nonEmptyMapper().toJson(room));
        redis.opsForZSet().add(data.today == 0 ? todayList : tomorrowList, roomId, (double) hour);

        //update user
        u.createRoomId = roomId;
        updateUser(u);
        return new BaseResp(0, "创建成功");

    }


    public static final int ROOM_SIZE = 4;

    /**
     * 加入房间
     *
     * @param roomId
     * @param uid
     * @return
     */
    public BaseResp joinRoom(String roomId, String uid) {
        String roomStr = redis.opsForValue().get(getRoomId(roomId));
        if (roomStr == null || roomStr.equals("")) {
            return new BaseResp(-1, "房间不存在");
        }
        Room room = JsonMapper.nonEmptyMapper().fromJson(roomStr, Room.class);
        if (room == null) {
            return new BaseResp(-1, "房间不存在");
        }
        if (room.getUserInfoList().size() >= ROOM_SIZE) {
            return new BaseResp(-1, "房间已满");
        }
        User u = getUser(uid);
        if (u == null) {
            return new BaseResp(-100, "用户不存在");
        }
        room.getUserInfoList().put(uid, u.avatar);
        redis.opsForValue().set(roomId, JSON.toJSONString(room));

        //update user
        u.joinRoomId = roomId;
        updateUser(u);
        return new BaseResp(0, "加入成功");
    }

    private User getUser(String uid) {
        String userStr = redis.opsForValue().get(getUserId(uid));
        return JsonMapper.nonEmptyMapper().fromJson(userStr, User.class);
    }

    private Room getRoom(String roomId) {
        String str = redis.opsForValue().get(getRoomId(roomId));

        return JsonMapper.nonEmptyMapper().fromJson(str, Room.class);

    }

    /**
     * 退出房间
     *
     * @param roomId
     * @param uid
     * @return
     */
    public BaseResp quitRoom(String roomId, String uid) {
        String roomStr = redis.opsForValue().get(getRoomId(roomId));
        if (roomStr == null || roomStr.equals("")) {
            return new BaseResp(-1, "房间不存在");
        }
        Room room = JsonMapper.nonEmptyMapper().fromJson(roomStr, Room.class);
        if (room == null) {
            return new BaseResp(-1, "房间不存在");
        }
        if (room.getUserInfoList().size() == 0) {
            return new BaseResp(-1, "房间不存在");
        }
        room.getUserInfoList().remove(uid);
        User u = getUser(uid);
        if (u.joinRoomId.equals(roomId)) {
            u.joinRoomId = "";
        }
        if (u.createRoomId.equals(roomId)) {
            u.createRoomId = "";
            if (room.getUserInfoList().size() > 0) {

                String leaderid = room.getUserInfoList().keySet().iterator().next();
                User leader = getUser(leaderid);
                room.setLeaderName(leader.name);

            } else {
                room.setLeaderName("暂无");
            }
        }

        redis.opsForValue().set(roomId, JSON.toJSONString(room));
        return new BaseResp(0, "退出成功");

    }


    public BaseResp getRoomList(QureryJson data) {
        Set<String> set = redis.opsForZSet().rangeByScore(data.today == 0 ? todayList : tomorrowList, data.fromTime, data.toTime);
        List<Room> rtnList = Lists.newArrayList();
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String roomId = it.next();
            String roomStr = redis.opsForValue().get(roomId);
            if (!StringUtils.isEmpty(roomStr)) {
                Room room = JsonMapper.nonEmptyMapper().fromJson(roomStr, Room.class);
                if (room.getTimestamp() > System.currentTimeMillis()) {
                    for (String key : room.getUserInfoList().keySet()) {
                        room.getAvatarList().add(room.getUserInfoList().get(key));
                    }
                    rtnList.add(room);
                }


            }
        }
        BaseResp<List<Room>> resp = new BaseResp(0, "查询成功", rtnList);
        return resp;


    }


    private static String getRoomId(String id) {
        return "room:" + id;
    }

    private static String getUserId(String id) {
        return "user:" + id;
    }
    /**
     *
     * 存储内容:
     *
     * 用户信息: set json格式  user:uid
     *       昵称
     *       头像
     *       建立的房间id
     *       加入的房间id
     *
     * 房间信息:set  json格式 room:roomid
     *      房间介绍
     *      今天明天
     *      时间
     *      路线
     *      成员列表list
     *
     * 房间列表 zset   roomlist:today
     *       roomid
     * 房间列表 zset   roomlist:tomorrow
     *      roomid
     *
     *
     */
}
