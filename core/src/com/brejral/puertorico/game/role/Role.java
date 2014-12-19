package com.brejral.puertorico.game.role;

import com.brejral.puertorico.game.GameHelper;

public class Role {
	protected int bonusCoins;
	private String name, tooltip;

	public Role(String name, String tooltip) {
		this.name = name;
		this.tooltip = tooltip;
	}

	public String getName() {
		return name;
	}
	
	public String getTooltip() {
		return tooltip;
	}

	public void addBonusCoin() {
		bonusCoins++;
	}

	public int getBonusCoins() {
		return bonusCoins;
	}

	public void onRoleStart() {
		if (bonusCoins > 0) {
			GameHelper.getCurrentPlayerForTurn().addCoins(bonusCoins);
			bonusCoins = 0;
		}
	}

	public void onAction() {
		GameHelper.nextAction();
	}

	public void onRoleEnd() {
		GameHelper.nextTurn();
	}

}
