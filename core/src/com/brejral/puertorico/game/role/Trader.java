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
	public static final String TOOLTIP = "<html><b>" + NAME + "</b><br><b>Action: </b>"
				+ "In clockwise order, players can sell a good at the trading house.<br>"
				+ "<b>Privilege: </b>With a sale, the trader takes one more doubloon than allowed.</html>";
		private List<String> tradedGoods = new ArrayList<String>(4);

	public Trader() {
		super(NAME, TOOLTIP);
	}

	public void onRoleStart() {
		super.onRoleStart();
		if (getTradableGoodsForCurrentPlayer().size() == 0) {
			tradeGood("None");
		}
	}

	public void tradeGood(String cropName) {
		Player player = GameHelper.getCurrentPlayerForAction();
		if (!cropName.equals("None")) {
			tradedGoods.add(cropName);
			GameHelper.addGoodsToSupplyFromPlayer(player, cropName, 1);
			int price = GameHelper.getCropPrice(cropName);
			price += player.getNumberActiveBuildings(SmallMarket.NAME);
			price += 2 * player.getNumberActiveBuildings(LargeMarket.NAME);
			price += player.didSelectRole() ? 1 : 0;
			GameHelper.addCoinsToPlayerFromSupply(player, price);
		}
		if (tradedGoods.size() == 4 || isEndOfRound()) {
			onRoleEnd();
		} else {
			super.onAction();
			while (getTradableGoodsForCurrentPlayer().size() == 0) {
				if (isEndOfRound()) {
					onRoleEnd();
					break;
				} else {
					super.onAction();
				}
			}
		}
	}

	public void onRoleEnd() {
		if (tradedGoods.size() == 4) {
			for (String cropName : tradedGoods) {
				GameHelper.addGoodsToSupply(cropName, 1);
			}
			tradedGoods.clear();
		}
		super.onRoleEnd();
	}

	public List<String> getTradableGoodsForCurrentPlayer() {
		List<String> cropsToTrade = new ArrayList<String>();
		Player player = GameHelper.getCurrentPlayerForAction();
		for (String cropName : Crop.CROP_LIST) {
			if (player.hasGood(cropName) && (!hasCropBeenTraded(cropName) || player.hasActiveBuilding(Office.NAME))) {
				cropsToTrade.add(cropName);
			}
		}
		return cropsToTrade;
	}

	private boolean hasCropBeenTraded(String cropName) {
		return tradedGoods.contains(cropName);
	}

	public List<String> getTradedGoods() {
		return tradedGoods;
	}

	public boolean isEndOfRound() {
		return GameHelper.getNextPlayer(GameHelper.getCurrentPlayerForAction()).equals(GameHelper.getCurrentPlayerForTurn());
	}
}
