package ch.zli.hose_abe_jass_backend.model;

public class Player {
	private String name;
	private Card[] cards = new Card[3];
	private boolean finalTurnPlayed = false;
	private int life = 3;
	private boolean hasBonusLife = false;
	
	public Player(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Card[] getCards() {
		return cards;
	}

	public void setCards(Card[] cards) {
		this.cards = cards;
	}

	public boolean isFinalTurnPlayed() {
		return finalTurnPlayed;
	}

	public void setFinalTurnPlayed(boolean finalTurnPlayed) {
		this.finalTurnPlayed = finalTurnPlayed;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public boolean isHasBonusLife() {
		return hasBonusLife;
	}

	public void setHasBonusLife(boolean hasBonusLife) {
		this.hasBonusLife = hasBonusLife;
	}
}
