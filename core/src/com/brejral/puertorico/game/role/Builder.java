package com.brejral.puertorico.game.role;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.brejral.puertorico.game.GameHelper;
import com.brejral.puertorico.game.building.Building;
import com.brejral.puertorico.game.player.Player;

public class Builder extends Role {
	public static final String NAME = "Builder";
	
	public Builder() {
		super(NAME);
	}
	
	public void onRoleStart() {
		super.onRoleStart();
	}
	
	public void onAction(Building building) {
		super.onAction();
	}
	
	public void onRoleEnd() {
		super.onRoleEnd();
	}
	
	public List<String> buildingsPlayerCanBuild() {
		Player player = GameHelper.getCurrentPlayerForAction();
		List<String> buildingNames = new ArrayList<String>();
		for (Entry<String, Integer> entry : GameHelper.getBuildingSupply().entrySet()) {
			if (entry.getValue() > 0 && player.canAffordBuilding(entry.getKey())) {
				buildingNames.add(entry.getKey());
			}
		}
		return buildingNames;
	}
}
