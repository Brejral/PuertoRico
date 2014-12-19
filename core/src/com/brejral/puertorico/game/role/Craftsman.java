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
	public static final String TOOLTIP = "<html><b>" + NAME + "</b><br><b>Action: </b>"
				+ "In clockwise order, players take their products from the supply.<br>"
				+ "<b>Privilege: </b>The craftsman takes one additional good.</html>";
	
	public Craftsman() {
		super(NAME, TOOLTIP);
	}

	public void onRoleStart() {
		super.onRoleStart();
		produceCropsForPlayers();
		List<String> goodChoices = getGoodsPlayerCanProduceForBonus();
		if (goodChoices.size() < 2) {
			onGoodSelect(goodChoices.size() == 0 ? null : goodChoices.get(0));
		}
	}

	/**
	 * This only action taken for the craftsman is to select the extra good to produce
	 * @param crop
	 */
	public void onGoodSelect(String cropName) {
		if (cropName != null) {
			Player player = GameHelper.getCurrentPlayerForTurn();
			GameHelper.addGoodsToPlayerFromSupply(player, cropName, 1);
		}
		super.onRoleEnd();
	}
	
	public List<String> getGoodsPlayerCanProduceForBonus() {
		Player player = GameHelper.getCurrentPlayerForTurn();
		HashMap<String, Integer> goodsToProduce = player.getGoodsPlayerCanProduce();
		List<String> goodList = new ArrayList<String>();
		for (String goodName : Crop.CROP_LIST) {
			if (goodsToProduce.get(goodName) > 0 && GameHelper.getGoodSupply(goodName) > 0) {
				goodList.add(goodName);
			}
		}
		return goodList;
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
