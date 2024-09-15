package org.jane.monopoly.websocket.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.jane.monopoly.log.LogService;
import org.jane.monopoly.user.dto.UserLoginInfo;
import org.jane.monopoly.user.service.UserManageService;
import org.jane.monopoly.websocket.dto.Message;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

    private final UserManageService userManageService;
    private final LogService logService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        userManageService.userLogout(session.getId());
    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String payload = message.getPayload();

            Message messageDto = objectMapper.readValue(payload, Message.class);

            switch (messageDto.getType()) {
                case "userLogin":
                    UserLoginInfo userLoginInfo = objectMapper.readValue(messageDto.getData(), UserLoginInfo.class);
                    userManageService.userLogin(userLoginInfo, session.getId());
                    break;
                case "content":
                    logService.Log(messageDto.getData());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
