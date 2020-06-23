package ch.zli.hose_abe_jass_backend.model;

import java.util.ArrayList;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import ch.zli.hose_abe_jass_backend.exception.JoinRoomException;

@Service
@ApplicationScope
public class RoomService {
	private ArrayList<GameHandler> gameHandlers = new ArrayList<>();

	// TODO: All Methods for Creating / Joining rooms go here
	public Room createRoom(String username) {
		boolean roomAlreadyExists;
		String generatedString;
		do {
			generatedString = generateCode();
			roomAlreadyExists = false;
			for (GameHandler gameHandler : gameHandlers) {
				if (gameHandler.getRoom().getRoomCode().equals(generatedString)) {
					roomAlreadyExists = true;
					break;
				}
			}
		} while (roomAlreadyExists);
		Room gameRoom = new Room(generatedString, new Player(username));
		GameHandler gameHandler = new GameHandler(gameRoom);
		gameHandlers.add(gameHandler);
		return gameRoom;
	}

	private String generateCode() {
		int leftLimit = 97;
		int rightLimit = 122;
		int targetStringLength = 4;
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(targetStringLength);
		for (int i = 0; i < targetStringLength; i++) {
			int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
			buffer.append((char) randomLimitedInt);
		}
		return buffer.toString().toUpperCase();
	}

	public Room joinRoom(String name, String roomCode) throws JoinRoomException {
		Room room = null;
		boolean playerAdded = false;
		boolean nameDuplicate = false;

		for (GameHandler handler : gameHandlers) {
			if (roomCode.toUpperCase().equals(handler.getRoom().getRoomCode())) {
				room = handler.getRoom();
			}
		}

		if (room == null) {
			throw new JoinRoomException("No Room exists with that Code");
		}

		Player[] players = room.getPlayers();
		for (int i = 0; i < 11; i++) {
			if (players[i] == null) {
				players[i] = new Player(name);
				playerAdded = true;
				break;
			} else {
				if (players[i].getName().equals(name)) {
					nameDuplicate = true;
					break;
				}
			}
		}

		if (nameDuplicate) {
			throw new JoinRoomException("A Player in that Room already has that Name");
		}

		if (!playerAdded) {
			throw new JoinRoomException("Room is already full");
		}

		return room;
	}

	public Room getRoomByCode(String roomcode) {
		GameHandler gh = gameHandlers.stream()
				.filter(gameHandler -> roomcode.toUpperCase().equals(gameHandler.getRoom().getRoomCode())).findFirst()
				.orElse(null);
		return gh != null ? gh.getRoom() : null;
	}
}
