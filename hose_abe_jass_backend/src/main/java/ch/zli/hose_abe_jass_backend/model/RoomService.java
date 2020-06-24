package ch.zli.hose_abe_jass_backend.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

  public Room finishGame(String roomCode) {
    Room room = getRoomByCode(roomCode);
    Map<Double, Player> playerScores = new HashMap<>();

    for (Player player : room.getPlayers()) {
      Card[] playerCards = player.getCards();
      if (playerCards[0].getCardValue() == playerCards[1].getCardValue() && playerCards[1] == playerCards[2]) {
        if (playerCards[0].getCardValue().getValue() == 11) {
          playerScores.put(33.00, player);
        } else {
          playerScores.put(30.5, player);
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
          playerScores.put((double) maxValue, player);
        } else {
          int sum = 0;
          for (Card playerCard : playerCards) {
            if (playerCard.getCardColor().name().equals(cardColor.name())) {
              sum += playerCard.getCardValue().getValue();
            }
          }
          playerScores.put((double) sum, player);
        }
      }
    }
    List<Double> sortedKeys = new ArrayList<>(playerScores.keySet());
    Collections.sort(sortedKeys);
    int index = 0;
    for (int i = sortedKeys.size() - 1; i >= 0; i--) {
      room.getPlayers()[index] = playerScores.get(sortedKeys.get(i));
      index++;
    }
    // TODO: add broadcastNews
    return room;
  }

  public Room getRoomByCode(String roomcode) {
    GameHandler gh = gameHandlers.stream()
        .filter(gameHandler -> roomcode.toUpperCase().equals(gameHandler.getRoom().getRoomCode()))
        .findFirst()
        .orElse(null);
    return gh != null ? gh.getRoom() : null;
  }
}
