package com.brejral.puertorico.game.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.brejral.puertorico.game.GameHelper;
import com.brejral.puertorico.game.building.Building;
import com.brejral.puertorico.game.crop.Crop;
import com.brejral.puertorico.game.crop.Quarry;
import com.brejral.puertorico.game.role.Role;
import com.brejral.puertorico.user.User;

public class Player {
	private User user;
	private Role role;
	private int points = 0, coins = 0, settlers = 0;
	private boolean isGovenor = false, isTurn = false, isAction = false;
	private List<Crop> crops = new ArrayList<Crop>(12);
	private List<Building> buildings = new ArrayList<Building>(); //ArrayList of buildings counted top to bottom then left to right
	private HashMap<String, Integer> cropSupply = new HashMap<String, Integer>();
	
	public Player() {
		
	}
	
	public Player(User usr) {
		user = usr;
	}
	
	public User getUser() {
		return user;
	}
	
	public void resetPlayer() {
		points = 0;
		coins = 0;
	}
	
	public int getCoins() {
		return coins;
	}

	public void addCoins(int value) {
		coins += value;
	}
	
	public void subtractCoins(int value) {
		coins -= value;
	}

	public int getPoints() {
		return points;
	}

	public void addPoints(int value) {
		points += value;
	}

	public boolean isGovenor() {
		return isGovenor;
	}

	public void setGovenor(boolean isGovenor) {
		this.isGovenor = isGovenor;
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
	
	public void addCrop(String className) {
		Crop crop = GameHelper.getCropFromBank(className);
		crops.add(crop);
	}
	
	public void addQuarry() {
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
	
	public boolean hasActiveBuilding(String buildingName) {
		for (Building building : getBuildings(buildingName)) {
			if (building.isActive()) {
				return true;
			}
		}
		return false;
	}
	
	public int getNumberActiveBuildings(String buildingName) {
		int activeBuildings = 0;
		for (Building building : getBuildings(buildingName)) {
			if (building.isActive()) {
				activeBuildings++;
			}
		}
		return activeBuildings;
	}
	
	public List<Building> getBuildings(String buildingName) {
		List<Building> foundBuildings = new ArrayList<Building>();
		for (Building building : buildings) {
			if (building.getName().equals(buildingName)) {
				foundBuildings.add(building);
			}
		}
		return foundBuildings;
	}

	public List<Building> getBuildings() {
		return buildings;
	}
	
	public int getOpenBuildingSlots() {
		int openSlots = 0;
		for (Building building : getBuildings()) {
			openSlots += building.getOpenSlots();
		}
		return openSlots;
	}

	public void produceCrops() {
		
	}

	public HashMap<String, Integer> getCropSupply() {
		return cropSupply;
	}
	
	public void setCropSupply(String cropName, int value) {
		cropSupply.put(cropName, value);
	}
	
	public void addCropSupply(String cropName, int value) {
		setCropSupply(cropName, cropSupply.get(cropName) + value);
	}
	
	public int getTotalPoints() {
		int totalPoints = points;
		for (Building building : buildings) {
			totalPoints += building.getPoints();
			if (building.isGameEndUtility() && building.isActive()) {
				totalPoints += building.getGameEndPoints(this);
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
}
