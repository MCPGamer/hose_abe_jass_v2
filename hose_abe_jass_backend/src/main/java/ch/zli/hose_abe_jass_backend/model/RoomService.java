package ch.zli.hose_abe_jass_backend.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import ch.zli.hose_abe_jass_backend.exception.JoinRoomException;

@Service
@ApplicationScope
public class RoomService {

  private ArrayList<GameHandler> gameHandlers = new ArrayList<>();

  //TODO: All Methods for Creating / Joining rooms go here
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
        .filter(gameHandler -> roomCode.toUpperCase().equals(gameHandler.getRoom().getRoomCode()))
        .findFirst()
        .orElseThrow(() -> new JoinRoomException("No Room exists with that Code"));
    Room room = game.getRoom();
    Player[] players = room.getPlayers();
    for (int i = 0; i < 11; i++) {
      if (players[i] == null) {
        players[i] = new Player(name);
        playerAdded = true;
        break;
      } else {
        boolean hasDuplicatedName = Arrays.stream(players).anyMatch(player -> player.getName().equals(name));
        if (hasDuplicatedName) {
          throw new JoinRoomException("A Player in that Room already has that Name");
        }
      }
    }
    if (!playerAdded) {
      throw new JoinRoomException("Room is already full");
    }

    return room;
  }

  public Room startGame(String roomCode) {
    Room room = gameHandlers.stream()
        .filter(gameHandler -> gameHandler.getRoom().getRoomCode().equals(roomCode))
        .findFirst()
        .get()
        .getRoom();
    room.setTable(generateCards(room.getTable()));
    for (Player player : room.getPlayers()) {
      if (player != null) {
        player.setCards(generateCards(player.getCards()));
      }
    }
    return room;
  }

  private Card[] generateCards(Card[] emptyCards) {
    Random random = new Random(System.nanoTime());
    CardValue[] cardValues = CardValue.values();
    CardColor[] cardColors = CardColor.values();
    for (int i = 0; i < emptyCards.length; i++) {
      CardValue cardValue = cardValues[random.nextInt(cardValues.length)];
      CardColor cardColor = cardColors[random.nextInt(cardColors.length)];
      emptyCards[i] = new Card(cardValue, cardColor);
    }
    return emptyCards;
  }
}
