package com.example.mitakehw.controllers.conversation.websocket;

import com.sun.security.auth.UserPrincipal;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig  implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        //沒有在這邊註冊的話 Client 會收不到訊息
        config.enableSimpleBroker("/topic", "/user", "/subscribe");
        //要傳送特定對象時，我們使用的前綴路徑，client需要在監聽的路徑前綴加上才可以順利收到
        config.setUserDestinationPrefix("/user");
        //client 在發送請求時，server會自動過濾掉的前綴字
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //websocket 請求建立連線的路徑
        registry.addEndpoint("/chatroom");
        registry.addEndpoint("/chatroom").setAllowedOriginPatterns("*").withSockJS();
    }
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        // configureClientInboundChannel會在一個新的 websocket 連線時觸發
        //會收到 StompCommand.CONNECT 的指令
        //將 server 端自己產生的 sessionId 填入這個 session 的 User 之中
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
//                    String user = accessor.getNativeHeader("user").get(0);
                    accessor.setUser(new UserPrincipal(accessor.getSessionId()));
                }

                return message;
            }
        });
    }
}
