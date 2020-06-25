package ch.zli.hose_abe_jass_backend.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
		this.simpMessagingTemplate.convertAndSend("/roomUpdate", getRoomByCode(roomcode));
	}

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
		broadcastNews(roomCode);
		return room;
	}

	private Card[] generateCards() {
		Card[] cards = new Card[36];
		int currentCard = 0;
		for (CardColor color : CardColor.values()) {
			for (CardValue value : CardValue.values()) {
				cards[currentCard] = new Card(value, color);
				currentCard++;
			}
		}
		return cards;
	}

	private void shuffle(Card[] cards) {
		Random rand = new Random();

		for (int i = 0; i < cards.length; i++) {
			int randomIndexToSwap = rand.nextInt(cards.length);
			Card temp = cards[randomIndexToSwap];
			cards[randomIndexToSwap] = cards[i];
			cards[i] = temp;
		}
	}

	private Card[] getFirst3Cards(Card[] cardSet) {
		// Find first Card in array that is not null
		int topCard = 0;
		for (int i = 0; i < cardSet.length; i++) {
			if (cardSet[i] != null) {
				topCard = i;
				break;
			}
		}

		// Give player the first 3 Cards
		Card[] handoutCards = new Card[] { cardSet[topCard], cardSet[topCard + 1], cardSet[topCard + 2] };

		// Remove them from the Deck
		cardSet[topCard] = null;
		cardSet[topCard + 1] = null;
		cardSet[topCard + 2] = null;

		return handoutCards;
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

	public Room swapSingle(String roomCode, String username, int playerCard, int tableCard) {
		Room room = getRoomByCode(roomCode);
		Player player = null;

		for (Player p : room.getPlayers()) {
			if (p != null && p.getName().equals(username)) {
				player = p;
			}
		}

		Card temp = player.getCards()[playerCard];
		player.getCards()[playerCard] = room.getTable()[tableCard];
		room.getTable()[tableCard] = temp;

		if (room.isFinalRound()) {
			player.setFinalTurnPlayed(true);
		}

		setNextPersonsTurn(room);

		broadcastNews(roomCode);
		return room;
	}

	public Room swapAll(String roomCode, String username) {
		Room room = getRoomByCode(roomCode);
		Player player = null;

		for (Player p : room.getPlayers()) {
			if (p != null && p.getName().equals(username)) {
				player = p;
			}
		}

		Card[] temp = player.getCards();
		player.setCards(room.getTable());
		room.setTable(temp);
		;

		if (room.isFinalRound()) {
			player.setFinalTurnPlayed(true);
		}

		setNextPersonsTurn(room);

		broadcastNews(roomCode);
		return room;
	}

	public Room swapNone(String roomCode, String username) {
		Room room = getRoomByCode(roomCode);
		Player player = null;

		for (Player p : room.getPlayers()) {
			if (p != null && p.getName().equals(username)) {
				player = p;
			}
		}

		player.setFinalTurnPlayed(true);
		room.setFinalRound(true);

		setNextPersonsTurn(room);
		broadcastNews(roomCode);
		return room;
	}

	private void setNextPersonsTurn(Room room) {
		int countPlayers = 0;
		for (Player p : room.getPlayers()) {
			if (p != null) {
				countPlayers++;
			}
		}

		room.setPlayerTurn(room.getPlayerTurn() + 1);
		if (room.getPlayerTurn() == countPlayers) {
			room.setPlayerTurn(0);
		}

		if (room.getPlayers()[room.getPlayerTurn()].isFinalTurnPlayed()) {
			room.setGameOver(true);
			finishGame(room.getRoomCode());
		}
	}

	public Room joinRoom(String name, String roomCode) throws JoinRoomException {
		Room room = null;
		boolean playerAdded = false;

		room = getRoomByCode(roomCode);
		if (room == null) {
			throw new JoinRoomException("Mit diesem Code existiert kein Raum.");
		}

		if (room.getTable()[0] != null) {
			throw new JoinRoomException("Dieses Spiel wurde bereits gestartet.");
		}

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
					throw new JoinRoomException("Dieser Benutzername ist bereits vergeben.");
				}
			}
		}

		if (!playerAdded) {
			throw new JoinRoomException("Raum ist voll.");
		}

		broadcastNews(roomCode);
		return room;
	}

	public void finishGame(String roomCode) {
		Room room = getRoomByCode(roomCode);
		Map<Player, Double> playerScores = new HashMap<>();

		for (Player player : room.getPlayers()) {
			if (player != null) {
				Card[] playerCards = player.getCards();
				if (playerCards[0].getCardValue() == playerCards[1].getCardValue()
						&& playerCards[1] == playerCards[2]) {
					if (playerCards[0].getCardValue().getValue() == 11) {
						playerScores.put(player, 33.00);
					} else {
						playerScores.put(player, 30.5);
					}
				} else {
					CardColor cardColor = null;
					int max = 0;
					for (CardColor value : CardColor.values()) {
						int count = 0;
						for (Card playerCard : playerCards) {
							if (playerCard.getCardColor().name().equals(value.name())) {
								count++;
							}
						}
						if (count > max) {
							max = count;
							cardColor = value;
						}
					}
					if (max == 1) {
						int maxValue = 0;
						for (Card playerCard : playerCards) {
							int cardNumber = playerCard.getCardValue().getValue();
							if (cardNumber > maxValue) {
								maxValue = cardNumber;
							}
						}
						playerScores.put(player, (double) maxValue);
					} else {
						int sum = 0;
						for (Card playerCard : playerCards) {
							if (playerCard.getCardColor().name().equals(cardColor.name())) {
								sum += playerCard.getCardValue().getValue();
							}
						}
						playerScores.put(player, (double) sum);
					}
				}
			}
		}
		List<Entry<Player, Double>> entryList = new ArrayList(playerScores.entrySet());
		Collections.sort(entryList, new Comparator<Entry<Player, Double>>() {
			@Override
			public int compare(Entry<Player, Double> o1, Entry<Player, Double> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});

		LinkedHashMap<Player, Double> sortedPlayers = new LinkedHashMap<>();
		for (Entry<Player, Double> entry : entryList) {
			sortedPlayers.put(entry.getKey(), entry.getValue());
		}

		int index = 0;
		for (Player p : sortedPlayers.keySet()) {
			room.getPlayers()[index] = p;
			index++;
		}

		broadcastNews(room.getRoomCode());
	}

	public Room getRoomByCode(String roomcode) {
		GameHandler gh = gameHandlers.stream()
				.filter(gameHandler -> roomcode.toUpperCase().equals(gameHandler.getRoom().getRoomCode())).findFirst()
				.orElse(null);
		return gh != null ? gh.getRoom() : null;
	}

	public void deleteRoom(String roomCode) {
		Room room = getRoomByCode(roomCode);
		if(room != null) {
			GameHandler gh = gameHandlers.stream().filter(handler -> handler.getRoom().equals(room))
					.findFirst().get();
			gameHandlers.remove(gh);
		}
	}
}
