package com.brejral.puertorico.game.role;

import com.brejral.puertorico.game.GameHelper;
import com.brejral.puertorico.game.player.Player;

public class Trader extends Role {
	public static final String NAME = "Trader";

	public Trader() {
		super(NAME);
	}
	
	public void onRoleAction(Player player) {
		GameHelper.getCurrentPlayerForTurn();
	}
}
