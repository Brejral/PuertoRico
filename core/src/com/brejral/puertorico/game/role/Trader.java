package com.brejral.puertorico.game.role;

import java.util.ArrayList;
import java.util.List;

import com.brejral.puertorico.game.GameHelper;
import com.brejral.puertorico.game.building.LargeMarket;
import com.brejral.puertorico.game.building.Office;
import com.brejral.puertorico.game.building.SmallMarket;
import com.brejral.puertorico.game.crop.Crop;
import com.brejral.puertorico.game.player.Player;

public class Trader extends Role {
	public static final String NAME = "Trader";
	private List<String> tradedCrops = new ArrayList<String>(4);

	public Trader() {
		super(NAME);
	}
	
	public void onRoleStart() {
		super.onRoleStart();
	}
	
	public void onAction(String cropName) {
		Player player = GameHelper.getCurrentPlayerForAction();
		tradedCrops.add(cropName);
		player.subtractGood(cropName, 1);
		GameHelper.addGoodSupply(cropName, 1);
		int price = GameHelper.getCropPrice(cropName);
		price += player.getNumberActiveBuildings(SmallMarket.NAME);
		price += 2 * player.getNumberActiveBuildings(LargeMarket.NAME);
		price += player.didSelectRole() ? 1 : 0;
		player.addCoins(price);
		GameHelper.addCoinsToPlayerFromSupply(player, price);
		super.onAction();
		if (tradedCrops.size() == 4) {
			onRoleEnd();
		}
	}
	
	public void onRoleEnd() {
		if (tradedCrops.size() == 4) {
			for (String cropName : tradedCrops) {
				GameHelper.addGoodSupply(cropName, 1);
			}
			tradedCrops.clear();
		}
		super.onRoleEnd();
	}
	
	public List<String> getTradableCropsForCurrentPlayer() {
		List<String> cropsToTrade = new ArrayList<String>();
		Player player = GameHelper.getCurrentPlayerForAction();
		for (String cropName : Crop.CROP_LIST) {
			if (player.getNumberOfGoods(cropName) > 0 && (!hasCropBeenTraded(cropName)) || player.hasActiveBuilding(Office.NAME)) {
				cropsToTrade.add(cropName);
			}
		}
		return cropsToTrade;
	}
	
	private boolean hasCropBeenTraded(String cropName) {
		return tradedCrops.contains(cropName);
	}
}
