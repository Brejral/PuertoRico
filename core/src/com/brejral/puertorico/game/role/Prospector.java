package com.brejral.puertorico.game.role;

import com.brejral.puertorico.game.GameHelper;
import com.brejral.puertorico.game.player.Player;

public class Prospector extends Role {
	public static final String NAME = "Prospector";
	
	public Prospector() {
		super(NAME);
	}
	
	public void onRoleStart() {
		super.onRoleStart();
		Player player = GameHelper.getCurrentPlayerForTurn();
		GameHelper.addCoinsToPlayerFromSupply(player, 1);
		super.onRoleEnd();
	}
}
