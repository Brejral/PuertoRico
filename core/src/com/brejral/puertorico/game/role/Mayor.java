package com.brejral.puertorico.game.role;

import java.util.ArrayList;
import java.util.List;

import com.brejral.puertorico.game.GameHelper;
import com.brejral.puertorico.game.building.Building;
import com.brejral.puertorico.game.crop.Crop;
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
	
	public void addRemoveSettler(boolean isBuilding, int index) {
		Player player = GameHelper.getCurrentPlayerForAction();
		if (isBuilding) {
			Building building = player.getBuildings().get(index);
			if (!building.isRemovingSettler() && player.getSettlers() != 0) {
				building.addSettlers(1);
				player.subtractSettlers(1);
				if (building.getOpenSlots() == 0 || player.getSettlers() == 0) {
					building.setIsRemovingSettler(true);
				}
			} else {
				building.subtractSettlers(1);
				player.addSettlers(1);
				if (building.getSettlers() == 0) {
					building.setIsRemovingSettler(false);
				}
			}
		} else {
			Crop crop = player.getCrops().get(index);
			if (!crop.isSettled() && player.getSettlers() > 0) {
				crop.setIsSettled(true);
				player.subtractSettlers(1);
			} else {
				crop.setIsSettled(false);
				player.addSettlers(1);
			}
		}
	}
	
	public void onAction() {
		if (GameHelper.getNextPlayer(GameHelper.getCurrentPlayerForAction()).equals(GameHelper.getCurrentPlayerForTurn())) {
			onRoleEnd();
		} else {
			super.onAction();
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
