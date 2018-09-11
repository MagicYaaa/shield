package com.weapp.api.controller;

import com.weapp.api.service.WebSocketMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ws/manager")
public class WSController
{
    @Autowired
    WebSocketMessageService webSocketMessageService;


    @RequestMapping("pushMessage")
    @ResponseBody
    public String pushMessage(@RequestParam String customerId, @RequestParam String taskId)
    {
        try {

            taskId = taskId.split("\\?")[0];
            webSocketMessageService.pushMessage(customerId, taskId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "{\"code\":0,\"data\":\"push success\"}";
    }
}
