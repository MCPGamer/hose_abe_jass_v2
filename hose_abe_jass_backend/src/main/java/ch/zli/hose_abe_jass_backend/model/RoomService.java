package ch.zli.hose_abe_jass_backend.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import ch.zli.hose_abe_jass_backend.exception.JoinRoomException;

@Service
@ApplicationScope
public class RoomService {
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	private ArrayList<GameHandler> gameHandlers = new ArrayList<>();

	@MessageMapping("/roomUpdate")
	public void broadcastNews(@Payload String roomcode) {
		System.out.println("Sending:" + roomcode);
		this.simpMessagingTemplate.convertAndSend("/roomUpdate", getRoomByCode(roomcode));
	}
	
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
		boolean playerAdded = false;
		GameHandler game = gameHandlers.stream()
				.filter(gameHandler -> roomCode.toUpperCase().equals(gameHandler.getRoom().getRoomCode())).findFirst()
				.orElseThrow(() -> new JoinRoomException("No Room exists with that Code"));
		Room room = game.getRoom();
		Player[] players = room.getPlayers();
		for (int i = 0; i < 11; i++) {
			if (players[i] == null) {
				players[i] = new Player(name);
				playerAdded = true;
				break;
			} else {
				boolean hasDuplicatedName = Arrays.stream(players)
						.anyMatch(player -> player != null && player.getName().equals(name));
				if (hasDuplicatedName) {
					throw new JoinRoomException("A Player in that Room already has that Name");
				}
			}
		}
		if (!playerAdded) {
			throw new JoinRoomException("Room is already full");
		}

		broadcastNews(roomCode);
		return room;
	}

	public Room startGame(String roomCode) {
		Room room = getRoomByCode(roomCode);
		Card[] cards = generateCards();
		shuffle(cards);
		room.setTable(getFirst3Cards(cards));
		for (Player player : room.getPlayers()) {
			if (player != null) {
				player.setCards(getFirst3Cards(cards));
			}
		}
		return room;
	}

	private Card[] generateCards() {
		Card[] cards = new Card[36];
		int currentCard = 0;
		for(CardColor color : CardColor.values()) {
			for(CardValue value : CardValue.values()) {
				cards[currentCard] = new Card(value, color);
				currentCard++;
			}
		}
		return cards;
	}
	
	private void shuffle(Card[] cards) {
		// TODO: Shuffel cards
	}
	
	private Card[] getFirst3Cards(Card[] cardSet) {
		return new Card[3];
	}

	public Room getRoomByCode(String roomcode) {
		GameHandler gh = gameHandlers.stream()
				.filter(gameHandler -> roomcode.toUpperCase().equals(gameHandler.getRoom().getRoomCode())).findFirst()
				.orElse(null);
		return gh != null ? gh.getRoom() : null;
	}
}
