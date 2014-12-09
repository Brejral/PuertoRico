package com.brejral.puertorico.game.role;

import java.util.ArrayList;
import java.util.List;

import com.brejral.puertorico.game.GameHelper;
import com.brejral.puertorico.game.building.Building;
import com.brejral.puertorico.game.building.University;
import com.brejral.puertorico.game.player.Player;

public class Builder extends Role {
	public static final String NAME = "Builder";
	private String selectedBuilding;
	
	public Builder() {
		super(NAME);
	}
	
	public void onRoleStart() {
		super.onRoleStart();
	}
	
	public boolean hasSelectedBuilding() {
		return selectedBuilding != null;
	}
	
	public void onBuildingSelect(String buildingName) {
		selectedBuilding = buildingName;
	}
	
	public void onLocationSelect(int index) {
		Player player = GameHelper.getCurrentPlayerForAction();
		Building building = GameHelper.removeBuildingFromSupply(selectedBuilding);
		if (player.hasActiveBuilding(University.NAME)) {
			building.setSettlers(1);
		}
		player.addBuilding(index, building);
		GameHelper.addCoinsToSupplyFromPlayer(player, player.getPriceOfBuildingForPlayer(building));
		selectedBuilding = null;
		if (GameHelper.getNextPlayer(player).equals(GameHelper.getCurrentPlayerForTurn())) {
			onRoleEnd();
		} else {
			super.onAction();
		}
	}
	
	public void onRoleEnd() {
		super.onRoleEnd();
	}
	
	public List<String> buildingsPlayerCanBuild() {
		Player player = GameHelper.getCurrentPlayerForAction();
		List<String> buildingNames = new ArrayList<String>();
		for (String buildingName : Building.BUILDING_LIST) {
			if (GameHelper.getBuildingSupplyCount(buildingName) > 0 && player.canAffordBuilding(buildingName) && (GameHelper.getBuildingFromSupply(buildingName).getSize() != 2 || player.canBuildLargeBuilding())) {
				buildingNames.add(buildingName);
			}
		}
		return buildingNames;
	}
	
	public boolean canPlayerBuildInLocation(int index) {
		if (!hasSelectedBuilding()) {
			return false;
		}
		Player player = GameHelper.getCurrentPlayerForAction();
		if (GameHelper.getBuildingFromSupply(selectedBuilding).getSize() == 1) {
			return player.getBuildings().get(index) == null; 
		}
		if (index == 2 || index == 5 || index == 8 || index == 11) {
			return false; // Cannot build size 2 building on bottom row
		}
		return player.getBuildings().get(index) == null && player.getBuildings().get(index + 1) == null;
	}
}
