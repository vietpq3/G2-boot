package config;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import entity.ActionType;
import entity.PlayMessage;

@Component
public class WebSocketEventListener {

	private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

	@Autowired
	private SimpMessageSendingOperations messagingTemplate;

	@Autowired
	@Qualifier(value = "playerList")
	private List<String> playerList;

	@SuppressWarnings("unused")
	@Autowired
	private String[][] board;

	@EventListener
	public void handleWebSocketConnectListener(SessionConnectedEvent event) {
		logger.info("Received a new web socket connection");
	}

	@EventListener
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
		String playerId = headerAccessor.getSessionId();

		PlayMessage playMessage = new PlayMessage();
		playMessage.setPlayerId(playerId);

		if (playerId != null) {
			logger.info("User Disconnected : " + playerId);
		}

		if (playerList.remove(playerId)) {
			playMessage.setActionType(ActionType.LEAVE);
			board = new String[25][25];
		}

		messagingTemplate.convertAndSend("/topic/gameRoom", playMessage);
	}
}
