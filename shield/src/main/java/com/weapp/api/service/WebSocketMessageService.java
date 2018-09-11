package com.weapp.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WebSocketMessageService
{


    @Autowired
    private StringRedisTemplate redis;


    public void pushMessage(String customerId, String id)
    {
        try
        {
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
