package com.weapp.api.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		//注册通道
		registry.addHandler(chatWebSocketHandler(),"/chat").setAllowedOrigins("*").addInterceptors(myInterceptor());
		registry.addHandler(statisticsWebSocketHandler(),"/statistics").setAllowedOrigins("*").addInterceptors(myInterceptor());
	}
	//消息处理Handler
	@Bean
	public ChatWebSocketHandler chatWebSocketHandler() {
		return new ChatWebSocketHandler();
	}
	@Bean
	public StatisticsWebSocketHandler statisticsWebSocketHandler() {
		return new StatisticsWebSocketHandler();
	}

	//websocket拦截器
	@Bean
	public WebSocketHandshakeInterceptor myInterceptor(){
		return new WebSocketHandshakeInterceptor();
	}
}
