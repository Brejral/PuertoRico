package com.brejral.puertorico.game.role;

import com.brejral.puertorico.game.player.Player;

public class Role {
	protected int bonusCoins;
	
	protected void addBonusCoin() {
		bonusCoins++;
	}
	
	protected void onRoleSelected(Player player) {
		player.addCoins(bonusCoins);
		bonusCoins = 0;
	}
	
	protected void onRoleTurnEnd() {
		
	}

}
