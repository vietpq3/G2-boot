package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import entity.ActionType;
import entity.PlayMessage;

@Controller
public class WebSocketController {

	@Autowired
	@Qualifier("playerList")
	private List<String> playerList;

	@Autowired
	private String previousPlayerId;

	@MessageMapping("/play")
	@SendTo("/topic/gameRoom")
	public PlayMessage sendMessage(@Payload PlayMessage playMessage, SimpMessageHeaderAccessor headerAccessor) {

		if (playMessage.getActionType() == ActionType.END) {
			// previousPlayerId = playerList.get(1);
			previousPlayerId = playMessage.getPlayerId();
			playMessage.setPreviousPlayer(previousPlayerId);
		} else if (playMessage.getPlayerId().equals(previousPlayerId)) {
			playMessage.setActionType(null);
		} else if (!playerList.contains(playMessage.getPlayerId())) {
			playMessage.setActionType(ActionType.WATCH);
		} else if (playerList.size() < 2) {
			playMessage.setActionType(ActionType.WAIT);
		} else {
			playMessage.setSymbol(playerList.indexOf(playMessage.getPlayerId()) == 0 ? "X" : "O");
			previousPlayerId = playMessage.getPlayerId();
			playMessage.setPreviousPlayer(previousPlayerId);
		}

		return playMessage;
	}

	@MessageMapping("/addPlayer")
	@SendTo("/topic/gameRoom")
	public PlayMessage addPlayer(@Payload PlayMessage playMessage, SimpMessageHeaderAccessor headerAccessor) {

		headerAccessor.getSessionAttributes().put("playerId", playMessage.getPlayerId());

		if (playerList.contains(playMessage.getPlayerId())) {
			previousPlayerId = "";
		} else {
			switch (playerList.size()) {
			case 0:
				playMessage.setActionType(ActionType.WAIT);
				playerList.add(playMessage.getPlayerId());
				break;
			case 1:
				playMessage.setActionType(ActionType.START);
				playerList.add(playMessage.getPlayerId());
				previousPlayerId = playMessage.getPlayerId();
				playMessage.setPreviousPlayer(previousPlayerId);
				break;
			case 2:
				playMessage.setActionType(ActionType.WATCH);
				break;
			default:
				break;
			}
		}

		return playMessage;
	}

}