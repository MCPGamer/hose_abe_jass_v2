package ch.zli.hose_abe_jass_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.zli.hose_abe_jass_backend.exception.JoinRoomException;
import ch.zli.hose_abe_jass_backend.model.Room;
import ch.zli.hose_abe_jass_backend.model.RoomService;

@CrossOrigin(origins = "*")
@RestController
public class GameController {
	@Autowired
	private RoomService roomService;
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	// TODO: rest functions for this

	@PostMapping("/room/{name}/{roomCode}")
	public Object joinRoom(@PathVariable String name, @PathVariable String roomCode) {
		try {
			Room room = roomService.joinRoom(name, roomCode);
			broadcastNews(roomCode);
			return room;
		} catch (JoinRoomException ex) {
			return "{ \"error\": \"" + ex.getErrorMessage() + "\"}";
		}
	}

	@PostMapping("/room/{username}")
	public Room createRoom(@PathVariable String username) {
		return roomService.createRoom(username);
	}

	@MessageMapping("/roomUpdate")
	public void broadcastNews(@Payload String roomcode) {
		System.out.println("Sending:" + roomcode);
		this.simpMessagingTemplate.convertAndSend("/roomUpdate", roomService.getRoomByCode(roomcode));
	}
}
