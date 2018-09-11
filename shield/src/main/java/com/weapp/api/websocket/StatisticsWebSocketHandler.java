package com.weapp.api.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;


public class StatisticsWebSocketHandler extends TextWebSocketHandler {
	private static final Logger logger =  LoggerFactory.getLogger(StatisticsWebSocketHandler.class);

	private final static List<WebSocketSession> sessions = Collections.synchronizedList(new ArrayList<WebSocketSession>());





	/**
	 * 处理接收文本
	 */
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String cont = message.getPayload();
		System.out.println("System.out...... -> " +cont);
		if(StringUtils.isEmpty(cont)){
			return;
		}
		//校验JSON格式
		if(!cont.startsWith("{") || !cont.endsWith("}")){
			return;
		}
		try {
			//发送消息
			sendAllMessage("a","aaa");
			super.handleTextMessage(session, message);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.info("connect to the websocket chat success......");
		sessions.add(session);
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		if(session.isOpen()){
			session.close();
		}
		logger.info("websocket chat connection closed......");
		sessions.remove(session);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		logger.info("websocket chat connection closed......");
		sessions.remove(session);
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

	/**
	 * 判断用户是否在线
	 * @param userName	登录用户名
	 * @return
     */
	public boolean isUserConnected(String userName){
		if(StringUtils.isEmpty(userName)){
			return false;
		}
		for (WebSocketSession user : sessions) {
			if (user.getAttributes().get("user").equals(userName)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 发送消息
	 * @param userName 	昵称
	 * @param content	发送内容
	 */
	public static void sendAllMessage(String userName, String content){
		for (WebSocketSession session : sessions) {
				if(session.isOpen()){
					try{
						Map<String,Object> retMap = new HashMap<String,Object>();
						retMap.put("currentUserNum",sessions.size());
						retMap.put("successNum",content);
						session.sendMessage(new TextMessage(JSON.toJSONString(retMap)));
					}catch (IOException ioe){
						ioe.printStackTrace();
					}
				}
				break;
		}
	}

}
