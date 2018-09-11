//package com.weapp.api.config;
//
//import com.weapp.common.constant.ApiConstant;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//import org.springframework.web.socket.server.HandshakeFailureException;
//import org.springframework.web.socket.server.HandshakeHandler;
//
//import java.util.Map;
//
//@Configuration
//@EnableWebSocketMessageBroker
//public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer
//{
//
//
//
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/socket.io","/ws").setAllowedOrigins("*").withSockJS();
//    }
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        registry.enableSimpleBroker("/p"); //个人订阅,组队订阅
//        //registry.enableSimpleBroker(ApiConstant.PERSON_CHANNEL,ApiConstant.TEAM_CHANNEL); //个人订阅,组队订阅
//
////        registry.setApplicationDestinationPrefixes("/app");
//    }
//
//
//
//
//
//
//
//
////
////
////    @Bean
////    public FilterRegistrationBean corsFilter() {
////        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////        CorsConfiguration config = new CorsConfiguration();
////        config.setAllowCredentials(true);
////        config.addAllowedOrigin("*");
////        config.addAllowedHeader("*");
////        config.addAllowedMethod("OPTIONS");
////        config.addAllowedMethod("HEAD");
////        config.addAllowedMethod("GET");
////        config.addAllowedMethod("PUT");
////        config.addAllowedMethod("POST");
////        config.addAllowedMethod("DELETE");
////        config.addAllowedMethod("PATCH");
////        source.registerCorsConfiguration("/**", config);
////        final FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
////        bean.setOrder(0);
////        return bean;
////    }
//
//}