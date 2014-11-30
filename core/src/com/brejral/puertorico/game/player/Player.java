package com.brejral.puertorico.game.player;

import java.util.ArrayList;
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
	private int points = 0, coins = 0;
	private boolean isGovenor = false, isTurn = false;
	private List<Crop> cropList = new ArrayList<Crop>(12);
	private List<Building> buildings = new ArrayList<Building>(); //ArrayList of buildings counted top to bottom then left to right
	
	public Player() {
		
	}
	
	public Player(User usr) {
		user = usr;
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
	
	public void addCrop(String className) {
		Crop crop = GameHelper.getCropFromBank(className);
		cropList.add(crop);
	}
	
	public void addQuarry() {
		Quarry quarry = GameHelper.getQuarryFromBank();
		cropList.add(quarry);
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
	
	public void clearRole() {
		setRole(null);
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

	public boolean isTurn() {
		return isTurn;
	}

	public void setTurn(boolean isTurn) {
		this.isTurn = isTurn;
	}
}
