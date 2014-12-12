package com.brejral.puertorico.game.role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.brejral.puertorico.game.GameHelper;
import com.brejral.puertorico.game.building.Factory;
import com.brejral.puertorico.game.crop.Crop;
import com.brejral.puertorico.game.player.Player;

public class Craftsman extends Role {
	public static final String NAME = "Craftsman";

	public Craftsman() {
		super(NAME);
	}

	public void onRoleStart() {
		super.onRoleStart();
		produceCropsForPlayers();
		List<String> goodChoices = getGoodsPlayerCanProduceForBonus();
		if (goodChoices.size() < 2) {
			onAction(goodChoices.size() == 0 ? null : goodChoices.get(0));
		}
	}

	/**
	 * This only action taken for the craftsman is to select the extra good to produce
	 * @param crop
	 */
	public void onAction(String cropName) {
		if (cropName != null) {
			Player player = GameHelper.getCurrentPlayerForTurn();
			GameHelper.addGoodsToPlayerFromSupply(player, cropName, 1);
		}
		super.onRoleEnd();
	}
	
	public List<String> getGoodsPlayerCanProduceForBonus() {
		Player player = GameHelper.getCurrentPlayerForTurn();
		HashMap<String, Integer> goodsToProduce = player.getGoodsPlayerCanProduce();
		List<String> cropList = new ArrayList<String>();
		for (String cropName : Crop.CROP_LIST) {
			if (goodsToProduce.get(cropName) > 0 && GameHelper.getGoodSupply(cropName) > 0) {
				cropList.add(cropName);
			}
		}
		return cropList;
	}

	public void produceCropsForPlayers() {
		for (Player player : GameHelper.getPlayerListForTurn()) {
			HashMap<String, Integer> goodsToProduce = player.getGoodsPlayerCanProduce();
			int numberOfCropsProduced = 0;
			for (String cropName : Crop.CROP_LIST) {
				int goodsProduced = GameHelper.addGoodsToPlayerFromSupply(player, cropName, goodsToProduce.get(cropName));
				if (goodsProduced > 0) {
					numberOfCropsProduced++;
				}
			}
			if (player.hasActiveBuilding(Factory.NAME)) {
				int coins = 0;
				switch (numberOfCropsProduced) {
				case 2:
					coins = 1;
					break;
				case 3:
					coins = 2;
					break;
				case 4:
					coins = 3;
					break;
				case 5:
					coins = 5;
					break;
				}
				GameHelper.addCoinsToPlayerFromSupply(player, coins);
			}
			player = GameHelper.getNextPlayer(player);
		}
	}

}
