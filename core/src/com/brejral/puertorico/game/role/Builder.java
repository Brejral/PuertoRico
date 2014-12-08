package com.brejral.puertorico.game.role;

import java.util.ArrayList;
import java.util.List;

import com.brejral.puertorico.game.GameHelper;
import com.brejral.puertorico.game.building.Building;
import com.brejral.puertorico.game.building.University;
import com.brejral.puertorico.game.player.Player;

public class Builder extends Role {
	public static final String NAME = "Builder";
	
	public Builder() {
		super(NAME);
	}
	
	public void onRoleStart() {
		super.onRoleStart();
	}
	
	public void onAction(String buildingName, int index) {
		Player player = GameHelper.getCurrentPlayerForAction();
		Building building = GameHelper.removeBuildingFromSupply(buildingName);
		if (player.hasActiveBuilding(University.NAME)) {
			building.setSettlers(1);
		}
		player.addBuilding(index, building);
		super.onAction();
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
}
