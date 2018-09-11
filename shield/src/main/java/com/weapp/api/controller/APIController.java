package com.weapp.api.controller;

import com.weapp.api.domain.Repository.User;
import com.weapp.api.domain.request.CreateJson;
import com.weapp.api.domain.request.QureryJson;
import com.weapp.api.domain.response.BaseResp;
import com.weapp.api.service.DataService;
import com.weapp.common.util.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/",produces = "application/json;charset=utf-8")
public class APIController {
    Logger logger = LoggerFactory.getLogger(APIController.class);

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestBody(required = false) User user) {
        User u = dataService.setUser(user);
        BaseResp<String> resp = new BaseResp<String>();
        if (!StringUtils.isEmpty(u.createRoomId)) {
            resp.data = u.createRoomId;
            resp.code = 1;
        }
        if (!StringUtils.isEmpty(u.joinRoomId)) {
            resp.data = u.joinRoomId;
            resp.code = 2;
        }
        resp.code = 0;
        logger.info(JsonMapper.nonEmptyMapper().toJson(resp));
        return JsonMapper.nonEmptyMapper().toJson(resp);

    }

    @RequestMapping(value="/getList")
    @ResponseBody
    public String getList(@RequestBody QureryJson data
    ) {
        logger.info(JsonMapper.nonEmptyMapper().toJson(dataService.getRoomList(data)));
        return JsonMapper.nonEmptyMapper().toJson(dataService.getRoomList(data));
    }


    @Autowired

    DataService dataService;


    @PostMapping("/create")
    @ResponseBody
    public String create(@RequestBody CreateJson data) {

        logger.info(JsonMapper.nonEmptyMapper().toJson(data));

        return JsonMapper.nonEmptyMapper().toJson(dataService.createRoom(data));

    }

    @GetMapping("/join")
    @ResponseBody
    public String join(String uid, String roomId) {

        return JsonMapper.nonEmptyMapper().toJson(dataService.joinRoom(roomId, uid));

    }

    @GetMapping("/quit")
    @ResponseBody
    public String quit(String uid, String roomId) {
        return JsonMapper.nonEmptyMapper().toJson(dataService.quitRoom(roomId, uid));
    }
}
