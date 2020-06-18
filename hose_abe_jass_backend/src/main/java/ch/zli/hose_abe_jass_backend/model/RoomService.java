package ch.zli.hose_abe_jass_backend.model;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import ch.zli.hose_abe_jass_backend.exception.JoinRoomException;

@Service
@ApplicationScope
public class RoomService {
	@Autowired
	private ArrayList<GameHandler> gameHandlers = new ArrayList<>();

	//TODO: All Methods for Creating / Joining rooms go here

	public Room joinRoom(String name, String roomCode) throws JoinRoomException{
		Room room= null;
		boolean playerAdded = false;
		boolean nameDuplicate = false;
		
		for(GameHandler handler : gameHandlers) {
			if(roomCode.toUpperCase().equals(handler.getRoom().getRoomCode())) {
				room = handler.getRoom();
			}
		}
		
		if(room == null) {
			throw new JoinRoomException("No Room exists with that Code");
		}
		
		Player[] players = room.getPlayers();
		for(int i = 0; i < 12; i++) {
			if(players[i] == null) {
				players[i] = new Player(name);
				playerAdded = true;
				break;
			} else {
				if(players[i].getName().equals(name)) {
					nameDuplicate = true;
					break;
				}
			}
		}
		
		if(!playerAdded) {
			throw new JoinRoomException("Room is already full");
		}
		
		if(nameDuplicate) {
			throw new JoinRoomException("A Player in that Room already has that Name");
		}
		
		return room;
	}
}
