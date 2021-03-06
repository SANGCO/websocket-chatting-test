package dev.sangco.websocket.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sangco.websocket.model.ChatMessage;
import dev.sangco.websocket.model.ChatRoom;
import dev.sangco.websocket.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketChatHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final ChatService chatService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payLoad = message.getPayload();
        log.info("payLoad {}", payLoad);
//        TextMessage textMessage = new TextMessage("Welcome chatting server~^^");
//        session.sendMessage(textMessage);
        ChatMessage chatMessage = objectMapper.readValue(payLoad, ChatMessage.class);
        ChatRoom room = chatService.findRoomById(chatMessage.getRoomId());
        room.handleActions(session, chatMessage, chatService);
    }
}
