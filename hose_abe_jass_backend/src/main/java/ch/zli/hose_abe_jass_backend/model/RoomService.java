package ch.zli.hose_abe_jass_backend.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

@Service
@ApplicationScope
public class RoomService {
	private ArrayList<GameHandler> gameHandlers = new ArrayList<>();
	private List<Room> rooms = new ArrayList<>();
	
	//TODO: All Methods for Creating / Joining rooms go here


	public Room createRoom(String username) {
		boolean roomAlreadyExists;
		String generatedString;
		do {
			generatedString = generateCode();
			roomAlreadyExists= false;
			for (Room room : rooms) {
				if (room.getRoomCode().equals(generatedString)) {
					roomAlreadyExists = true;
					break;
				}
			}
		} while (roomAlreadyExists);
		Room gameRoom = new Room(generatedString, new Player(username));
		rooms.add(gameRoom);
		return gameRoom;
	}

	private String generateCode() {
		int leftLimit = 97;
		int rightLimit = 122;
		int targetStringLength = 4;
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(targetStringLength);
		for (int i = 0; i < targetStringLength; i++) {
			int randomLimitedInt = leftLimit + (int)
					(random.nextFloat() * (rightLimit - leftLimit + 1));
			buffer.append((char) randomLimitedInt);
		}
		return buffer.toString().toUpperCase();
	}
}
