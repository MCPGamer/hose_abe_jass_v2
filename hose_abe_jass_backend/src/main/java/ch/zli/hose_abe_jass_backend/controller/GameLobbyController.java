package ch.zli.hose_abe_jass_backend.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins="*")
@RestController
public class GameLobbyController {
	@GetMapping("/")
	private String ping(Model model) {
		return "{\"ping\":true}";
	}
}
