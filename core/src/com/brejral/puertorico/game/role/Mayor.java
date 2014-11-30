package com.brejral.puertorico.game.role;

import java.util.List;

import com.brejral.puertorico.game.GameHelper;
import com.brejral.puertorico.game.player.Player;

public class Mayor extends Role {

	public void onRoleTurnEnd() {
		super.onRoleTurnEnd();
		List<Player> players = GameHelper.getPlayers();
		int openSlotCount = 0;
		for (Player player : players) {
			openSlotCount += player.getOpenBuildingSlots();
		}
		GameHelper.resupplySettlerShip(openSlotCount);
	}
}
