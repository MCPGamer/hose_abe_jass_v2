package ch.zli.hose_abe_jass_backend.model;

public class Card {
	private CardValue cardValue;
	private CardColor cardColor;
	
	public Card(CardValue cardValue, CardColor cardColor) {
		this.cardValue = cardValue;
		this.cardColor = cardColor;
	}

	public CardValue getCardValue() {
		return cardValue;
	}

	public void setCardValue(CardValue cardValue) {
		this.cardValue = cardValue;
	}

	public CardColor getCardColor() {
		return cardColor;
	}

	public void setCardColor(CardColor cardColor) {
		this.cardColor = cardColor;
	}
}
