package com.brejral.puertorico.game.role;

import com.brejral.puertorico.game.GameHelper;
import com.brejral.puertorico.game.building.ConstructionHut;
import com.brejral.puertorico.game.building.Hacienda;
import com.brejral.puertorico.game.building.Hospice;
import com.brejral.puertorico.game.crop.Crop;
import com.brejral.puertorico.game.player.Player;

public class Settler extends Role {
	public static final String NAME = "Settler";
	
	public Settler() {
		super(NAME);
	}
	
	public void onRoleStart() {
		super.onRoleStart();
	}
	
	public void onCropSelect(Crop crop) {
		Player player = GameHelper.getCurrentPlayerForAction();
		GameHelper.addCropToPlayerFromSettlerCropSupply(player, crop);
		if (player.hasActiveBuilding(Hacienda.NAME)) {
			Crop bonusCrop = GameHelper.getSettlerCropSupply().remove(0);
			bonusCrop.setIsSettled(player.hasActiveBuilding(Hospice.NAME));
			player.addCrop(bonusCrop);
		}
		super.onAction();
	}
	
	public void onRoleEnd() {
		GameHelper.setSettlerCropSupply();
		super.onRoleEnd();
	}
	
	public boolean canChooseQuarry(Player player) {
		return player.isTurn() || player.hasActiveBuilding(ConstructionHut.NAME);
	}
}
