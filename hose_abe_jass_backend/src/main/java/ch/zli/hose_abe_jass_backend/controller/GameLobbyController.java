package ch.zli.hose_abe_jass_backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.zli.hose_abe_jass_backend.model.Room;
import ch.zli.hose_abe_jass_backend.model.RoomService;

@CrossOrigin(origins="*")
@RestController
public class GameLobbyController {
	private final RoomService roomService;

	public GameLobbyController(RoomService roomService) {
		this.roomService = roomService;
	}


	@PostMapping("/room/{username}")
	public Room createRoom(@PathVariable String username) {
		return roomService.createRoom(username);
	}
}
