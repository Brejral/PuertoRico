package com.brejral.puertorico.game.role;

import com.brejral.puertorico.game.player.Player;

public class Role {
	protected int bonusCoins;
	private String name;
	
	public Role(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void addBonusCoin() {
		bonusCoins++;
	}
	
	public void onRoleStart(Player player) {
		player.addCoins(bonusCoins);
		bonusCoins = 0;
	}
	
	public void onAction(Player player) {
		
	}
	
	public void onRoleEnd() {
		
	}

}
