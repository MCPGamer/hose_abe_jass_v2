package ch.zli.hose_abe_jass_backend.model;

public enum CardValue {
	Ass(11), Koenig(10), Ober(10), Under(10), Banner(10), Neun(9), Acht(8), Sieben(7), Sechs(6);
	
	private int value;
	private CardValue(int val) {
		this.value = val;
	}
	
	public int getValue() {
		return value;
	}
}
