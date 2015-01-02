package com.brejral.puertorico.game.role;

import com.brejral.puertorico.game.GameHelper;
import com.brejral.puertorico.game.player.Player;

public class Prospector extends Role {
	public static final String NAME = "Prospector";
	public static final String TOOLTIP = "<html><b>" + NAME + "</b><br><b>Action: </b>" + "No action!<br>" + "<b>Privilege: </b>The prospector takes one doubloon from the bank.</html>";
	private int index;

	public Prospector(int index) {
		super(NAME, TOOLTIP);
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public void onRoleStart() {
		super.onRoleStart();
		Player player = GameHelper.getCurrentPlayerForTurn();
		GameHelper.addCoinsToPlayerFromSupply(player, 1);
		super.onRoleEnd();
	}
}
