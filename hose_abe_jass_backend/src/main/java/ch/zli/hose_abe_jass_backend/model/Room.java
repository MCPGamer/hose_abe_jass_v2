package ch.zli.hose_abe_jass_backend.model;

public class Room {
	private String roomCode;
	private Player host;
	private Player[] players = new Player[11];
	private Card[] table = new Card[3];
	private boolean finalRound = false;
	private int playerturn;
	
	public Room(String roomCode, Player host) {
		this.roomCode = roomCode;
		this.host = host;
	}

	public String getRoomCode() {
		return roomCode;
	}

	public void setRoomCode(String roomCode) {
		this.roomCode = roomCode;
	}

	public Player getHost() {
		return host;
	}

	public void setHost(Player host) {
		this.host = host;
	}

	public Player[] getPlayers() {
		return players;
	}

	public void setPlayers(Player[] players) {
		this.players = players;
	}

	public Card[] getTable() {
		return table;
	}

	public void setTable(Card[] table) {
		this.table = table;
	}

	public boolean isFinalRound() {
		return finalRound;
	}

	public void setFinalRound(boolean finalRound) {
		this.finalRound = finalRound;
	}

	public int getPlayerturn() {
		return playerturn;
	}

	public void setPlayerturn(int playerturn) {
		this.playerturn = playerturn;
	}
}
