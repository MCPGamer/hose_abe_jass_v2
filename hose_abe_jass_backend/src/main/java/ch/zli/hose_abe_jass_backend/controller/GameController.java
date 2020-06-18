package ch.zli.hose_abe_jass_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ch.zli.hose_abe_jass_backend.exception.JoinRoomException;
import ch.zli.hose_abe_jass_backend.model.RoomService;

@CrossOrigin(origins="*")
@RestController
public class GameController {
	@Autowired
	private RoomService roomService;
	
	//TODO: rest functions for this
	
	@PostMapping("/joinRoom")
	public Object joinRoom(@RequestBody String name, @RequestBody String roomCode) {
		try {
			return roomService.joinRoom(name, roomCode);
		} catch (JoinRoomException ex) {
			return ex.getErrorMessage();
		}
	}
}
