package com.brejral.puertorico.game.role;

import java.util.ArrayList;
import java.util.List;

import com.brejral.puertorico.game.GameHelper;
import com.brejral.puertorico.game.player.Player;

public class Mayor extends Role {
	public static final String NAME = "Mayor";

	public void onRoleStart(Player player) {
		super.onRoleStart(player);
		List<Integer> newSettlersList = new ArrayList<Integer>(GameHelper.getNumberOfPlayers());
		int modSettlers = GameHelper.getSettlerShip().getSettlers() % GameHelper.getNumberOfPlayers();
		int settlersPerPlayer = GameHelper.getSettlerShip().getSettlers()/GameHelper.getNumberOfPlayers();
		for (int i = 0; i < newSettlersList.size(); i++) {
			int value = settlersPerPlayer;
			if (i < modSettlers) {
				value++;
			}
			if (i == 0) {
				value++;
			}
			newSettlersList.set(i, value);
		}
		
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
