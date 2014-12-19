package com.brejral.puertorico.game.role;

import com.brejral.puertorico.game.GameHelper;
import com.brejral.puertorico.game.building.ConstructionHut;
import com.brejral.puertorico.game.building.Hacienda;
import com.brejral.puertorico.game.building.Hospice;
import com.brejral.puertorico.game.crop.Crop;
import com.brejral.puertorico.game.player.Player;

public class Settler extends Role {
	public static final String NAME = "Settler";
	public static final String TOOLTIP = "<html><b>" + NAME + "</b><br><b>Action: </b>"
				+ "In clockwise order, players take a plantation from the supply.<br>"
				+ "<b>Privilege: </b>The settler can take one quarry instead.</html>";
		
	public Settler() {
		super(NAME, TOOLTIP);
	}
	
	public void onRoleStart() {
		super.onRoleStart();
	}
	
	public void onCropSelect(Crop crop) {
		Player player = GameHelper.getCurrentPlayerForAction();
		GameHelper.addCropToPlayerFromSettlerCropSupply(player, crop);
		if (player.hasActiveBuilding(Hacienda.NAME)) {
			Crop bonusCrop = GameHelper.getCropSupply().remove(0);
			bonusCrop.setIsSettled(player.hasActiveBuilding(Hospice.NAME));
			player.addCrop(bonusCrop);
		}
		if (GameHelper.getNextPlayer(player).equals(GameHelper.getCurrentPlayerForTurn())) {
			onRoleEnd();
		} else {
			super.onAction();
		}
	}
	
	public void onRoleEnd() {
		GameHelper.setSettlerCropSupply();
		super.onRoleEnd();
	}
	
	public boolean canChooseQuarry(Player player) {
		return player.isTurn() || player.hasActiveBuilding(ConstructionHut.NAME);
	}
}
