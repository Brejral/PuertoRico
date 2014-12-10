package com.brejral.puertorico.game.role;

import java.util.ArrayList;
import java.util.List;

import com.brejral.puertorico.game.GameHelper;
import com.brejral.puertorico.game.player.Player;
import com.brejral.puertorico.game.ship.Ship;

public class Mayor extends Role {
	public static final String NAME = "Mayor";
	
	public Mayor() {
		super(NAME);
	}

	public void onRoleStart() {
		super.onRoleStart();
		List<Integer> newSettlersList = new ArrayList<Integer>(GameHelper.getNumberOfPlayers());
		Ship settlerShip = GameHelper.getSettlerShip();
		int modSettlers = settlerShip.getSettlers() % GameHelper.getNumberOfPlayers();
		int settlersPerPlayer = settlerShip.getSettlers()/GameHelper.getNumberOfPlayers();
		for (int i = 0; i < GameHelper.getNumberOfPlayers(); i++) {
			int value = settlersPerPlayer;
			if (i < modSettlers) {
				value++;
			}
			if (i == 0 && GameHelper.getSettlerSupply() > 0) {
				value++;
			}
			newSettlersList.add(value);
		}
		List<Player> players = GameHelper.getPlayerListForTurn();
		for (int i = 0; i < players.size(); i++) {
			players.get(i).addSettlers(newSettlersList.get(i));
		}
		GameHelper.subtractSettlerFromSupply();
		settlerShip.clearSettlers();
	}
	
	public void onRoleEnd() {
		List<Player> players = GameHelper.getPlayers();
		int openSlotCount = 0;
		for (Player player : players) {
			openSlotCount += player.getOpenBuildingSlots();
		}
		GameHelper.resupplySettlerShip(openSlotCount);
		super.onRoleEnd();
	}
}
