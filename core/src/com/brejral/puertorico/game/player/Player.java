package com.brejral.puertorico.game.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.brejral.puertorico.game.GameHelper;
import com.brejral.puertorico.game.building.Building;
import com.brejral.puertorico.game.building.CoffeeRoaster;
import com.brejral.puertorico.game.building.IndigoPlant;
import com.brejral.puertorico.game.building.SmallIndigoPlant;
import com.brejral.puertorico.game.building.SmallSugarMill;
import com.brejral.puertorico.game.building.SugarMill;
import com.brejral.puertorico.game.building.TobaccoStorage;
import com.brejral.puertorico.game.crop.Coffee;
import com.brejral.puertorico.game.crop.Corn;
import com.brejral.puertorico.game.crop.Crop;
import com.brejral.puertorico.game.crop.Indigo;
import com.brejral.puertorico.game.crop.Quarry;
import com.brejral.puertorico.game.crop.Sugar;
import com.brejral.puertorico.game.crop.Tobacco;
import com.brejral.puertorico.game.role.Role;
import com.brejral.puertorico.user.User;

public class Player implements Comparable<Player> {
	private User user;
	private String name;
	private Role role;
	private int points = 0, coins = 0, settlers = 0;
	private boolean isGovernor = false, isTurn = false, isAction = false;
	private List<Crop> crops = new ArrayList<Crop>(12);
	private List<Building> buildings = new ArrayList<Building>(12); // top to bottom, left to right
	private HashMap<String, Integer> goods = new HashMap<String, Integer>();

	public Player() {
		this("");
	}

	public Player(String name) {
		this.name = name;
		initialize();
	}

	public Player(User usr) {
		user = usr;
	}

	public User getUser() {
		return user;
	}

	public String getName() {
		return name;
	}

	public void resetPlayer() {
		points = 0;
		coins = 0;
	}

	public void initialize() {
		for (int i = 0; i < 12; i++) {
			buildings.add(null);
		}
		for (String cropName : Crop.CROP_LIST) {
			goods.put(cropName, 0);
		}
	}

	public int getCoins() {
		return coins;
	}

	public void addCoins(int value) {
		System.out.println(name + " coins increased from " + coins + " to " + (coins + value));
		coins += value;
	}

	public void subtractCoins(int value) {
		System.out.println(name + " coins decreased from " + coins + " to " + (coins - value));
		coins -= value;
	}

	public int getPoints() {
		return points;
	}

	public void addPoints(int value) {
		System.out.println(name + " points increased from " + points + " to " + (points + value));
		points += value;
	}

	public boolean isGovernor() {
		return isGovernor;
	}

	public void setGovernor(boolean isGovernor) {
		this.isGovernor = isGovernor;
	}

	public boolean isTurn() {
		return isTurn;
	}

	public void setTurn(boolean isTurn) {
		this.isTurn = isTurn;
	}

	public boolean isAction() {
		return isAction;
	}

	public void setAction(boolean isAction) {
		this.isAction = isAction;
	}

	public List<Crop> getCrops() {
		return crops;
	}

	public List<Crop> getCrops(String cropName) {
		List<Crop> foundCrops = new ArrayList<Crop>();
		for (Crop crop : crops) {
			if (crop.getName().equals(cropName)) {
				foundCrops.add(crop);
			}
		}
		return foundCrops;
	}

	public int getNumberOfSettledCrops(String cropName) {
		int settledCrops = 0;
		for (Crop crop : getCrops(cropName)) {
			if (crop.isSettled()) {
				settledCrops++;
			}
		}
		return settledCrops;
	}

	public void addCrop(String cropName) {
		System.out.println(name + " adds " + cropName);
		Crop crop = GameHelper.getCropFromBank(cropName);
		crops.add(crop);
	}

	public void addCrop(Crop crop) {
		System.out.println(name + " adds " + crop.getName());
		crops.add(crop);
	}

	public void addQuarry() {
		System.out.println(name + " adds " + Quarry.NAME);
		Quarry quarry = GameHelper.getQuarryFromBank();
		crops.add(quarry);
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Role getRole() {
		return role;
	}

	public void clearRole() {
		setRole(null);
	}

	public boolean isRole(String roleName) {
		return role != null ? role.getName().equals(roleName) : false;
	}

	public int getNumberProductionSettlersForCrop(String cropName) {
		List<String> buildingNames = new ArrayList<String>();
		switch (cropName) {
		case Indigo.NAME:
			buildingNames.add(SmallIndigoPlant.NAME);
			buildingNames.add(IndigoPlant.NAME);
			break;
		case Sugar.NAME:
			buildingNames.add(SmallSugarMill.NAME);
			buildingNames.add(SugarMill.NAME);
			break;
		case Tobacco.NAME:
			buildingNames.add(TobaccoStorage.NAME);
			break;
		case Coffee.NAME:
			buildingNames.add(CoffeeRoaster.NAME);
			break;
		default:
			return 0;
		}
		return getSettlersForBuildings(buildingNames);
	}

	public boolean hasActiveBuilding(String buildingName) {
		for (Building building : getBuildings(buildingName)) {
			if (building.isActive()) {
				return true;
			}
		}
		return false;
	}

	public int getSettlersForBuildings(List<String> buildingNames) {
		int settlers = 0;
		for (Building building : getBuildings(buildingNames)) {
			settlers += building.getSettlers();
		}
		return settlers;
	}

	public int getNumberActiveBuildings(String buildingName) {
		List<String> buildingNames = new ArrayList<String>();
		buildingNames.add(buildingName);
		return getNumberActiveBuildings(buildingNames);
	}

	public int getNumberActiveBuildings(List<String> buildingNames) {
		int activeBuildings = 0;
		for (Building building : getBuildings(buildingNames)) {
			if (building.isActive()) {
				activeBuildings++;
			}
		}
		return activeBuildings;
	}

	public List<Building> getBuildings(List<String> buildingNames) {
		List<Building> foundBuildings = new ArrayList<Building>();
		for (Building building : buildings) {
			if (building != null && buildingNames.contains(building.getName())) {
				foundBuildings.add(building);
			}
		}
		return foundBuildings;
	}

	public List<Building> getBuildings(String buildingName) {
		List<String> buildingNames = new ArrayList<String>();
		buildingNames.add(buildingName);
		return getBuildings(buildingNames);
	}

	public List<Building> getBuildings() {
		return buildings;
	}

	public void addBuilding(int index, Building building) {
		System.out.println(name + " adds " + building.getName() + " to " + index);
		buildings.set(index, building);
		if (isBuildingSpaceFull()) {
			GameHelper.setLastRound();
		}
	}

	public int getOpenBuildingSlots() {
		int openSlots = 0;
		for (Building building : getBuildings()) {
			if (building != null) {
				openSlots += building.getOpenSlots();
			}
		}
		return openSlots;
	}

	public HashMap<String, Integer> getGoodsPlayerCanProduce() {
		HashMap<String, Integer> goods = new HashMap<String, Integer>();
		for (String cropName : Crop.CROP_LIST) {
			int settledCrops = getNumberOfSettledCrops(cropName);
			int productionSettlers = getNumberProductionSettlersForCrop(cropName);
			if (cropName.equals(Corn.NAME)) {
				goods.put(cropName, settledCrops);
			} else {
				goods.put(cropName, Math.min(settledCrops, productionSettlers));
			}
		}
		return goods;
	}

	public HashMap<String, Integer> getCropSupply() {
		return goods;
	}

	public void setGood(String cropName, int value) {
		goods.put(cropName, value);
	}

	public void addGood(String goodName, int value) {
		System.out.println(name + "'s " + goodName + " increased from " + goods.get(goodName) + " to " + (goods.get(goodName) + value));
		setGood(goodName, goods.get(goodName) + value);
	}

	public void subtractGood(String goodName, int value) {
		System.out.println(name + "'s " + goodName + " decreased from " + goods.get(goodName) + " to " + (goods.get(goodName) - value));
		setGood(goodName, goods.get(goodName) - value);
	}

	public int getNumberOfGoods(String goodName) {
		return goods.get(goodName);
	}

	public int getTotalPoints() {
		int totalPoints = points;
		for (Building building : buildings) {
			if (building != null) {
				totalPoints += building.getPoints();
				if (building.isGameEndUtility() && building.isActive()) {
					totalPoints += building.getGameEndPoints(this);
				}
			}
		}
		return totalPoints;
	}

	public int getSettlers() {
		return settlers;
	}

	public void addSettlers(int value) {
		this.settlers += value;
	}

	public void subtractSettlers(int value) {
		this.settlers -= value;
	}

	public boolean didSelectRole() {
		return isAction && isTurn;
	}

	public int getPriceOfBuildingForPlayer(String buildingName) {
		return getPriceOfBuildingForPlayer(GameHelper.getBuildingFromSupply(buildingName));
	}

	public int getPriceOfBuildingForPlayer(Building building) {
		int price = building.getPrice();
		int discount = Math.min(getNumberOfSettledCrops(Quarry.NAME), building.getPoints()) + (isTurn ? 1 : 0);
		return price - discount;
	}

	public boolean canAffordBuilding(String buildingName) {
		return getPriceOfBuildingForPlayer(buildingName) <= coins;
	}

	public boolean isBuildingSlotEmpty(int index) {
		return buildings.get(index) == null;
	}

	public boolean canBuildLargeBuilding() {
		return (isBuildingSlotEmpty(1) && (isBuildingSlotEmpty(0) || isBuildingSlotEmpty(2))) || (isBuildingSlotEmpty(4) && (isBuildingSlotEmpty(3) || isBuildingSlotEmpty(5)))
					|| (isBuildingSlotEmpty(7) && (isBuildingSlotEmpty(6) || isBuildingSlotEmpty(8))) || (isBuildingSlotEmpty(10) && (isBuildingSlotEmpty(9) || isBuildingSlotEmpty(11)));
	}

	public boolean hasGood(String goodName) {
		return goodName != null ? goods.get(goodName) > 0 : false;
	}

	@Override
	public int compareTo(Player player) {
		return player.getTotalPoints() - this.getTotalPoints();
	}
	
	private boolean isBuildingSpaceFull() {
		for (int i = 0; i < getBuildings().size(); i++) {
			Building building = getBuildings().get(i);
			if (building == null) {
				return false;
			}
			if (building.getSize() == 2) {
				i++;
			}
		}
		return true;
	}
}
