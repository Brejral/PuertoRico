package com.brejral.puertorico.game.role;

import java.util.ArrayList;
import java.util.List;

import com.brejral.puertorico.game.GameHelper;
import com.brejral.puertorico.game.crop.Crop;
import com.brejral.puertorico.game.player.Player;

public class Trader extends Role {
	public static final String NAME = "Trader";
	private List<Crop> tradedCrops = new ArrayList<Crop>(4);

	public Trader() {
		super(NAME);
	}
	
	public void onRoleStart() {
		Player player = GameHelper.getCurrentPlayerForTurn();
		super.onRoleStart(player);
	}
	
	public void onRoleAction(Crop crop) {
		Player player = GameHelper.getCurrentPlayerForAction();
	}
	
	public List<Crop> getTradableCropsForCurrentPlayer() {
		List<Crop> cropsToTrade = new ArrayList<Crop>();
		Player player = GameHelper.getCurrentPlayerForAction();
		
		return cropsToTrade;
	}
}
