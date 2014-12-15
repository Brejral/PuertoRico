package com.brejral.puertorico.game.role;

import com.brejral.puertorico.game.GameHelper;

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
