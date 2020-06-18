package ch.zli.hose_abe_jass_backend.model;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

@Service
@ApplicationScope
public class GameService {
	private Room room;

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}
	
	//TODO: All Methods for Playing the Game go here
}
