package ch.zli.hose_abe_jass_backend.model;


public class GameHandler {
	private Room room;
	
	public GameHandler(Room room){
		this.room = room;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}
}
