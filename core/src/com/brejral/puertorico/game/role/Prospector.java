package com.brejral.puertorico.game.role;

import com.brejral.puertorico.game.GameHelper;
import com.brejral.puertorico.game.player.Player;

public class Prospector extends Role {

	public void onRoleStart(Player player) {
		super.onRoleStart(player);
		player.addCoins(1);
		GameHelper.getBank().removeCoinSupply(1);
		super.onRoleEnd();
	}
}
