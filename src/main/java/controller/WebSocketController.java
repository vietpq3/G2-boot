package controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import entity.ActionType;
import entity.PlayMessage;
import utils.XOUtils;

@Controller
public class WebSocketController {

	@Autowired
	@Qualifier("playerList")
	private List<String> playerList;

	@Autowired
	private String previousPlayerId;

	@Autowired
	@Qualifier("board")
	public String[][] board;

	@MessageMapping("/play")
	@SendTo("/topic/gameRoom")
	public PlayMessage sendMessage(@Payload PlayMessage playMessage, SimpMessageHeaderAccessor headerAccessor) {

		String playerId = headerAccessor.getSessionId();
		playMessage.setPlayerId(playerId);
		int x = playMessage.getX();
		int y = playMessage.getY();

		if (playerId.equals(previousPlayerId) || StringUtils.isNotEmpty(board[x][y])
				|| playMessage.getActionType() == ActionType.END) {
			playMessage.setActionType(null);
		} else if (!playerList.contains(playerId)) {
			playMessage.setActionType(ActionType.WATCH);
		} else if (playerList.size() < 2) {
			playMessage.setActionType(ActionType.WAIT);
		} else {
			String ownerSymbol = playerList.indexOf(playerId) == 0 ? "X" : "O";
			playMessage.setSymbol(ownerSymbol);
			previousPlayerId = playerId;
			board[x][y] = ownerSymbol;

			if (this.checkWinner(x, y) != null) {
				playMessage.setActionType(ActionType.END);
				XOUtils.resetBoard(board);
			}
		}

		return playMessage;
	}

	private String checkWinner(int x, int y) {
		String temp = "";
		for (int i = -5; i < 6; i++) {
			try {
				temp += board[x + i][y + i];
			} catch (IndexOutOfBoundsException e) {
				continue;
			}
		}
		String result = check(temp);
		if (result != null)
			return result;

		temp = "";
		for (int i = -5; i < 6; i++) {
			try {
				temp += board[x - i][y + i];
			} catch (IndexOutOfBoundsException e) {
				continue;
			}
		}
		result = check(temp);
		if (result != null)
			return result;

		temp = "";
		for (int i = -5; i < 6; i++) {
			try {
				temp += board[x + i][y];
			} catch (IndexOutOfBoundsException e) {
				continue;
			}
		}
		result = check(temp);
		if (result != null)
			return result;

		temp = "";
		for (int i = -5; i < 6; i++) {
			try {
				temp += board[x][y + i];
			} catch (IndexOutOfBoundsException e) {
				continue;
			}
		}
		result = check(temp);
		if (result != null)
			return result;

		return null;
	}

	private String check(String temp) {
		if (temp.indexOf("XXXXX") > -1 && temp.indexOf("OXXXXXO") < 0) {
			return "X";
		}
		if (temp.indexOf("OOOOO") > -1 && temp.indexOf("XOOOOOX") < 0) {
			return "O";
		}
		return null;
	}

	@MessageMapping("/addPlayer")
	@SendTo("/topic/gameRoom")
	public PlayMessage addPlayer(@Payload PlayMessage playMessage, SimpMessageHeaderAccessor headerAccessor) {

		String playerId = headerAccessor.getSessionId();
		playMessage.setPlayerId(playerId);

		if (playerList.contains(playerId)) {
			previousPlayerId = "";
		} else {
			switch (playerList.size()) {
			case 0:
				playMessage.setActionType(ActionType.WAIT);
				playerList.add(playerId);
				break;
			case 1:
				playMessage.setActionType(ActionType.START);
				playerList.add(playerId);
				previousPlayerId = playerId;
				break;
			case 2:
				playMessage.setActionType(ActionType.WATCH);
				break;
			default:
				playMessage.setActionType(null);
				break;
			}
		}

		return playMessage;
	}

}
