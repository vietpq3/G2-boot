package entity;

public class PlayMessage {

	private String playerId;
	private int size;
	private int x;
	private int y;
	private String symbol;
	private String previousPlayer;
	private ActionType actionType;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public ActionType getActionType() {
		return actionType;
	}

	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getPreviousPlayer() {
		return previousPlayer;
	}

	public void setPreviousPlayer(String previousPlayer) {
		this.previousPlayer = previousPlayer;
	}
}
