package com.brejral.puertorico.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.brejral.puertorico.game.bank.Bank;
import com.brejral.puertorico.game.building.Building;
import com.brejral.puertorico.game.building.Hospice;
import com.brejral.puertorico.game.crop.Coffee;
import com.brejral.puertorico.game.crop.Corn;
import com.brejral.puertorico.game.crop.Crop;
import com.brejral.puertorico.game.crop.Indigo;
import com.brejral.puertorico.game.crop.Quarry;
import com.brejral.puertorico.game.crop.Sugar;
import com.brejral.puertorico.game.crop.Tobacco;
import com.brejral.puertorico.game.player.Player;
import com.brejral.puertorico.game.role.Builder;
import com.brejral.puertorico.game.role.Captain;
import com.brejral.puertorico.game.role.Craftsman;
import com.brejral.puertorico.game.role.Mayor;
import com.brejral.puertorico.game.role.Prospector;
import com.brejral.puertorico.game.role.Role;
import com.brejral.puertorico.game.role.Settler;
import com.brejral.puertorico.game.role.Trader;
import com.brejral.puertorico.game.ship.Ship;

public class GameHelper {
	private static Game GAME;
	public static long RAND_SEED = System.nanoTime();

	public static void setGame(Game game) {
		GAME = game;
	}

	public static Game getGame() {
		return GAME;
	}

	public static int getNumberOfPlayers() {
		return getGame().getNumberOfPlayers();
	}

	public static int getGoodSupply(String cropName) {
		return getBank().getGoodSupply().get(cropName);
	}

	public static Crop getCropFromBank(String cropName) {
		List<Crop> crops = getBank().getCropSupply();
		for (Crop crop : crops) {
			if (crop.getName().equals(cropName)) {
				return crops.remove(crops.indexOf(crop));
			}
		}
		return null;
	}

	public static Quarry getQuarryFromBank() {
		return getBank().getQuarrySupply().remove(0);
	}

	public static List<Crop> getCropSupply() {
		return getBank().getCropSupply();
	}

	public static List<Crop> getSettlerCropSupply() {
		return getBank().getSettlerCropSupply();
	}

	public static int getNumberOfSettlerCropSupply() {
		return getNumberOfPlayers() + 1;
	}

	public static void addCoinsToPlayerFromSupply(Player player, int value) {
		getBank().subtractCoinFromSupply(value);
		player.addCoins(value);
	}

	public static void addCoinsToSupplyFromPlayer(Player player, int value) {
		getBank().addCoinToSupply(value);
		player.subtractCoins(value);
	}

	public static int addGoodsToPlayerFromSupply(Player player, String cropName, int value) {
		int supplyGoods = getBank().getGoodSupply().get(cropName);
		boolean useSupplyGoods = supplyGoods < value;
		player.addGood(cropName, useSupplyGoods ? supplyGoods : value);
		getBank().subtractGoodFromSupply(cropName, useSupplyGoods ? supplyGoods : value);
		return useSupplyGoods ? supplyGoods : value;
	}

	public static List<Player> getPlayers() {
		return getGame().getPlayers();
	}

	public static Bank getBank() {
		return getGame().getBank();
	}

	public static int getCoinSupply() {
		return getBank().getCoinSupply();
	}

	public static int getPointSupply() {
		return getBank().getPointSupply();
	}

	public static int getSettlerSupply() {
		return getBank().getSettlerSupply();
	}

	public static void subtractSettlerFromSupply() {
		if (getSettlerSupply() > 0) {
			getBank().setSettlerSupply(getSettlerSupply() - 1);
		}
	}

	public static List<Quarry> getQuarrySupply() {
		return getBank().getQuarrySupply();
	}

	public static List<String> getRoleNames() {
		return getBank().getRoleNames();
	}

	public static List<Role> getRoles() {
		return getBank().getRoles();
	}

	public static Role getRole(String roleName) {
		return getBank().getRole(roleName);
	}

	public static boolean isRoleAvailable(String roleName) {
		return getBank().isRoleAvailable(roleName);
	}

	public static int getNumberOfRoles() {
		return getBank().getNumberOfRoles();
	}

	public static Ship getSettlerShip() {
		return getBank().getSettlerShip();
	}

	public static List<Ship> getCargoShips() {
		return getBank().getCargoShips();
	}

	public static List<Building> getBuildingSupply() {
		return getBank().getBuildingSupply();
	}

	public static Building getBuildingFromSupply(String buildingName) {
		for (Building building : getBuildingSupply()) {
			if (building.getName().equals(buildingName)) {
				return building;
			}
		}
		return null;
	}

	public static int getBuildingSupplyCount(String buildingName) {
		return getBank().getBuildingSupplyCount(buildingName);
	}

	public static Building removeBuildingFromSupply(String buildingName) {
		return getBank().removeBuildingFromSupply(buildingName);
	}

	public static void setSettlerCropSupply() {
		getBank().setSettlerCropSupply();
	}

	public static void selectRoleForPlayer(String roleName) {
		Role role = getBank().getRole(roleName);
		getBank().removeRole(role);
		getCurrentPlayerForTurn().setRole(role);
		if (role.getName().equals(Prospector.NAME)) {
			((Prospector) role).onRoleStart();
		} else if (role.getName().equals(Builder.NAME)) {
			((Builder) role).onRoleStart();
		} else if (role.getName().equals(Captain.NAME)) {
			((Captain) role).onRoleStart();
		} else if (role.getName().equals(Craftsman.NAME)) {
			((Craftsman) role).onRoleStart();
		} else if (role.getName().equals(Mayor.NAME)) {
			((Mayor) role).onRoleStart();
		} else if (role.getName().equals(Settler.NAME)) {
			((Settler) role).onRoleStart();
		} else if (role.getName().equals(Trader.NAME)) {
			((Trader) role).onRoleStart();
		}
	}

	public static void resupplySettlerShip(int openSlots) {
		getBank().resupplySettlerShip(openSlots);
	}

	public static void addGoodsToSupply(String cropName, int value) {
		getBank().addGoodToSupply(cropName, value);
	}

	public static void subtractGoodSupply(String cropName, int value) {
		getBank().subtractGoodFromSupply(cropName, value);
	}

	public static int getCropPrice(String cropName) {
		switch (cropName) {
		case Corn.NAME:
			return Corn.PRICE;
		case Indigo.NAME:
			return Indigo.PRICE;
		case Sugar.NAME:
			return Sugar.PRICE;
		case Tobacco.NAME:
			return Tobacco.PRICE;
		case Coffee.NAME:
			return Coffee.PRICE;
		default:
			return 0;
		}
	}

	public static void setLastRound() {
		getGame().setLastRound();
	}

	// Moves the governor position
	public static void nextRound() {
		getGame().nextRound();
	}

	// The next player selects their role
	public static void nextTurn() {
		getGame().nextTurn();
	}

	// The next player takes their action for the current role
	public static void nextAction() {
		getGame().nextAction();
	}

	public static Player getNextPlayer(Player player) {
		List<Player> players = getPlayers();
		int index = players.indexOf(player);
		if (index + 1 == players.size()) {
			index = 0;
		} else {
			index++;
		}
		return players.get(index);
	}

	public static List<Player> getPlayerListForTurn() {
		List<Player> playerList = new ArrayList<Player>();
		Player player = getCurrentPlayerForTurn();
		for (int i = 0; i < getNumberOfPlayers(); i++) {
			playerList.add(player);
			player = getNextPlayer(player);
		}
		return playerList;
	}

	public static Player getCurrentPlayerForAction() {
		return getGame().getCurrentPlayerForAction();
	}

	public static Player getCurrentPlayerForTurn() {
		return getGame().getCurrentPlayerForTurn();
	}

	public static Player getCurrentPlayerForRound() {
		return getGame().getCurrentPlayerForRound();
	}

	public static Role getCurrentRole() {
		return getCurrentPlayerForTurn().getRole();
	}

	public static boolean isRole(String roleName) {
		Role role = getCurrentRole();
		return role != null ? role.getName().equals(roleName) : false;
	}

	public static List<String> getGoodsPlayerCanSelectForCraftsmanBonus() {
		List<String> goodNames = new ArrayList<String>();
		Player player = getCurrentPlayerForTurn();
		for (Entry<String, Integer> goodEntry : player.getGoodsPlayerCanProduce().entrySet()) {
			if (goodEntry.getValue() > 0 && getBank().getGoodSupply(goodEntry.getKey()) > 0) {
				goodNames.add(goodEntry.getKey());
			}
		}
		return goodNames;
	}

	public static void addCropToPlayerFromSettlerCropSupply(Player player, Crop crop) {
		if (crop.getName().equals(Quarry.NAME)) {
			getQuarrySupply().remove(0);
		} else {
			getSettlerCropSupply().remove(crop);
		}
		if (player.hasActiveBuilding(Hospice.NAME)) {
			crop.setIsSettled(true);
		}
		player.addCrop(crop);
	}

	public static boolean hasRoleBeenSelected() {
		return getCurrentPlayerForTurn().getRole() != null;
	}

	public static List<String> getTradedGoods() {
		for (Player player : getPlayers()) {
			if (player.isRole(Trader.NAME)) {
				return ((Trader) player.getRole()).getTradedGoods();
			}
		}
		return ((Trader) getRole(Trader.NAME)).getTradedGoods();
	}
}
