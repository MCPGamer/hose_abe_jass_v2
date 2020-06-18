package ch.zli.hose_abe_jass_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import ch.zli.hose_abe_jass_backend.model.GameService;

@CrossOrigin(origins="*")
@RestController
public class GameController {
	@Autowired
	private GameService gameService;
	
	//TODO: rest functions for this
}
