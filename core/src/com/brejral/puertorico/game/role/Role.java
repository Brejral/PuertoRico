package com.brejral.puertorico.game.role;

import com.brejral.puertorico.game.player.Player;

public class Role {
	protected int bonusCoins;
	
	protected void addBonusCoin() {
		bonusCoins++;
	}
	
	protected void onRoleStart(Player player) {
		player.addCoins(bonusCoins);
		bonusCoins = 0;
	}
	
	protected void onAction(Player player) {
		
	}
	
	protected void onRoleEnd() {
		
	}

}
