package ch.zli.hose_abe_jass_backend.model;

public class Room {
	private String roomCode;
	private Player host;
	private Player[] players = new Player[11];
	private Player[] originPlayerOrder = new Player[11];
	private Card[] table = new Card[3];
	private boolean finalRound = false;
	private boolean gameOver = false;
	private boolean roundOver = false;
	private int playerTurn = 0;
	private boolean bonusLifeUsed = false;
	private int roundsPlayed = 0;
	
	public Room(String roomCode, Player host) {
		this.roomCode = roomCode;
		this.host = host;
		this.players[0] = host;
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

	public Player[] getOriginPlayerOrder() {
		return originPlayerOrder;
	}

	public void setOriginPlayerOrder(Player[] originPlayerOrder) {
		this.originPlayerOrder = originPlayerOrder;
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

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	
	public boolean isRoundOver() {
		return roundOver;
	}

	public void setRoundOver(boolean roundOver) {
		this.roundOver = roundOver;
	}

	public int getPlayerTurn() {
		return playerTurn;
	}

	public void setPlayerTurn(int playerturn) {
		this.playerTurn = playerturn;
	}

	public boolean isBonusLifeUsed() {
		return bonusLifeUsed;
	}

	public void setBonusLifeUsed(boolean bonusLifeUsed) {
		this.bonusLifeUsed = bonusLifeUsed;
	}

	public int getRoundsPlayed() {
		return roundsPlayed;
	}

	public void setRoundsPlayed(int roundsPlayed) {
		this.roundsPlayed = roundsPlayed;
	}
}
