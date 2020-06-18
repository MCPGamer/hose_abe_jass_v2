package ch.zli.hose_abe_jass_backend.model;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

@Service
@ApplicationScope
public class RoomService {
	@Autowired
	private ArrayList<GameService> gameServices = new ArrayList<>();
	
	//TODO: All Methods for Creating / Joining rooms go here
}
