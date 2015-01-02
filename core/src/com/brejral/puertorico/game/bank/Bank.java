package com.brejral.puertorico.game.bank;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import com.brejral.puertorico.game.GameHelper;
import com.brejral.puertorico.game.building.Building;
import com.brejral.puertorico.game.building.CityHall;
import com.brejral.puertorico.game.building.CoffeeRoaster;
import com.brejral.puertorico.game.building.ConstructionHut;
import com.brejral.puertorico.game.building.CustomsHouse;
import com.brejral.puertorico.game.building.Factory;
import com.brejral.puertorico.game.building.Fortress;
import com.brejral.puertorico.game.building.GuildHall;
import com.brejral.puertorico.game.building.Hacienda;
import com.brejral.puertorico.game.building.Harbor;
import com.brejral.puertorico.game.building.Hospice;
import com.brejral.puertorico.game.building.IndigoPlant;
import com.brejral.puertorico.game.building.LargeMarket;
import com.brejral.puertorico.game.building.LargeWarehouse;
import com.brejral.puertorico.game.building.Office;
import com.brejral.puertorico.game.building.Residence;
import com.brejral.puertorico.game.building.SmallIndigoPlant;
import com.brejral.puertorico.game.building.SmallMarket;
import com.brejral.puertorico.game.building.SmallSugarMill;
import com.brejral.puertorico.game.building.SmallWarehouse;
import com.brejral.puertorico.game.building.SugarMill;
import com.brejral.puertorico.game.building.TobaccoStorage;
import com.brejral.puertorico.game.building.University;
import com.brejral.puertorico.game.building.Wharf;
import com.brejral.puertorico.game.crop.Coffee;
import com.brejral.puertorico.game.crop.Corn;
import com.brejral.puertorico.game.crop.Crop;
import com.brejral.puertorico.game.crop.Indigo;
import com.brejral.puertorico.game.crop.Quarry;
import com.brejral.puertorico.game.crop.Sugar;
import com.brejral.puertorico.game.crop.Tobacco;
import com.brejral.puertorico.game.role.Builder;
import com.brejral.puertorico.game.role.Captain;
import com.brejral.puertorico.game.role.Craftsman;
import com.brejral.puertorico.game.role.Mayor;
import com.brejral.puertorico.game.role.Prospector;
import com.brejral.puertorico.game.role.Role;
import com.brejral.puertorico.game.role.Settler;
import com.brejral.puertorico.game.role.Trader;
import com.brejral.puertorico.game.ship.Ship;

public class Bank implements Serializable {
	private int settlerSupply, coinSupply, pointSupply;
	private List<Crop> cropSupply, settlerCropSupply;
	private List<Quarry> quarrySupply;
	private Ship settlerShip;
	private List<Ship> cargoShips;
	private List<Role> roles;
	private List<String> roleNames;
	private HashMap<String, Integer> goodSupply;
	private List<Building> buildingSupply;
	private HashMap<String, Integer> buildingSupplyCount;

	public Bank() {
		initializeCropSupply();
		initializeSettlerCropSupply();
		initializeGoodSupply();
		initializeQuarrySupply();
		initializeRoles();
		initializeCargoShips();
		initializeBuildingSupply();
		settlerShip = new Ship();
		switch (GameHelper.getNumberOfPlayers()) {
		case 3:
			settlerShip.setSettlers(3);
			setCoinSupply(48);
			setSettlerSupply(52);
			setPointSupply(75);
			break;
		case 4:
			settlerShip.setSettlers(4);
			setCoinSupply(42);
			setSettlerSupply(71);
			setPointSupply(100);
			break;
		case 5:
			settlerShip.setSettlers(5);
			setCoinSupply(34);
			setSettlerSupply(90);
			setPointSupply(122);
			break;
		}
	}
	
	private void initializeSettlerCropSupply() {
		settlerCropSupply = new ArrayList<Crop>(GameHelper.getNumberOfPlayers() + 1);
		setSettlerCropSupply();
	}

	private void initializeQuarrySupply() {
		quarrySupply = new ArrayList<Quarry>();
		for (int i = 0; i < 8; i++) {
			quarrySupply.add(new Quarry());
		}
	}

	private void initializeCropSupply() {
		cropSupply = new ArrayList<Crop>();
		for (int i = 0; i < 8; i++) {
			cropSupply.add(new Coffee());
		}
		for (int i = 0; i < 9; i++) {
			cropSupply.add(new Tobacco());
		}
		for (int i = 0; i < 10; i++) {
			cropSupply.add(new Corn());
		}
		for (int i = 0; i < 11; i++) {
			cropSupply.add(new Sugar());
		}
		for (int i = 0; i < 12; i++) {
			cropSupply.add(new Indigo());
		}
		Collections.shuffle(cropSupply, new Random(GameHelper.RAND_SEED));
	}

	private void initializeGoodSupply() {
		goodSupply = new HashMap<String, Integer>();
		goodSupply.put("Corn", 10);
		goodSupply.put("Coffee", 9);
		goodSupply.put("Indigo", 11);
		goodSupply.put("Sugar", 11);
		goodSupply.put("Tobacco", 9);
	}

	private void initializeRoles() {
		roles = new ArrayList<Role>();
		roles.add(new Builder());
		roles.add(new Settler());
		roles.add(new Craftsman());
		roles.add(new Mayor());
		roles.add(new Captain());
		roles.add(new Trader());
		if (GameHelper.getNumberOfPlayers() == 5) {
			roles.add(new Prospector(1));
			roles.add(new Prospector(2));
		} else if (GameHelper.getNumberOfPlayers() == 4) {
			roles.add(new Prospector(1));
		}
		
		roleNames = new ArrayList<String>();
		for (Role role : roles) {
			roleNames.add(role.getName());
		}
	}

	private void initializeCargoShips() {
		cargoShips = new ArrayList<Ship>();
		cargoShips.add(new Ship(GameHelper.getNumberOfPlayers() + 1));
		cargoShips.add(new Ship(GameHelper.getNumberOfPlayers() + 2));
		cargoShips.add(new Ship(GameHelper.getNumberOfPlayers() + 3));
	}

	private void initializeBuildingSupply() {
		buildingSupply = new ArrayList<Building>();
		for (int i = 0; i < 4; i++) {
			buildingSupply.add(new SmallIndigoPlant());
			buildingSupply.add(new SmallSugarMill());
		}
		for (int i = 0; i < 3; i++) {
			buildingSupply.add(new IndigoPlant());
			buildingSupply.add(new SugarMill());
			buildingSupply.add(new TobaccoStorage());
			buildingSupply.add(new CoffeeRoaster());
		}
		for (int i = 0; i < 2; i++) {
			buildingSupply.add(new SmallMarket());
			buildingSupply.add(new Hacienda());
			buildingSupply.add(new ConstructionHut());
			buildingSupply.add(new SmallWarehouse());
			buildingSupply.add(new Hospice());
			buildingSupply.add(new Office());
			buildingSupply.add(new LargeMarket());
			buildingSupply.add(new LargeWarehouse());
			buildingSupply.add(new Factory());
			buildingSupply.add(new University());
			buildingSupply.add(new Harbor());
			buildingSupply.add(new Wharf());
		}
		buildingSupply.add(new GuildHall());
		buildingSupply.add(new Residence());
		buildingSupply.add(new CustomsHouse());
		buildingSupply.add(new CityHall());
		buildingSupply.add(new Fortress());

		buildingSupplyCount = new HashMap<String, Integer>();
		for (Building building : buildingSupply) {
			if (buildingSupplyCount.containsKey(building.getName())) {
				buildingSupplyCount.put(building.getName(), buildingSupplyCount.get(building.getName()) + 1);
			} else {
				buildingSupplyCount.put(building.getName(), 1);
			}
		}
	}

	public int getSettlerSupply() {
		return settlerSupply;
	}

	public void setSettlerSupply(int settlerSupply) {
		this.settlerSupply = settlerSupply;
	}

	public int getCoinSupply() {
		return coinSupply;
	}

	private void setCoinSupply(int coinSupply) {
		this.coinSupply = coinSupply;
	}

	public void removeCoinSupply(int value) {
		this.coinSupply -= value;
	}

	public void addCoinToSupply(int value) {
		this.coinSupply += value;
	}

	public void subtractCoinFromSupply(int value) {
		this.coinSupply -= value;
	}

	public int getPointSupply() {
		return pointSupply;
	}

	public void setPointSupply(int pointSupply) {
		this.pointSupply = pointSupply;
	}
	
	public void subtractPointsFromSupply(int value) {
		pointSupply -= value;
		if (pointSupply < 0) {
			pointSupply = 0;
			GameHelper.setLastRound();
		}
	}

	public HashMap<String, Integer> getGoodSupply() {
		return goodSupply;
	}

	public int getGoodSupply(String cropName) {
		return goodSupply.get(cropName);
	}

	private void setGoodSupply(String cropName, int value) {
		System.out.println(cropName + " supply changed from " + goodSupply.get(cropName) + " to " + value);
		goodSupply.put(cropName, value);
	}

	public void addGoodToSupply(String cropName, int value) {
		setGoodSupply(cropName, getGoodSupply(cropName) + value);
	}

	public void subtractGoodFromSupply(String cropName, int value) {
		setGoodSupply(cropName, getGoodSupply(cropName) - value);
	}

	public List<Crop> getCropSupply() {
		return cropSupply;
	}

	public List<Quarry> getQuarrySupply() {
		return quarrySupply;
	}

	public void setSettlerCropSupply() {
		for (int i = 0; i < GameHelper.getNumberOfPlayers() + 1; i++) {
			if (settlerCropSupply.size() < i + 1) {
				settlerCropSupply.add(cropSupply.remove(0));
			}
		}
	}

	public List<Crop> getSettlerCropSupply() {
		return settlerCropSupply;
	}

	public void resupplySettlerShip(int openSlots) {
		int resupply = 0;
		if ((openSlots < GameHelper.getNumberOfPlayers() && GameHelper.getNumberOfPlayers() > settlerSupply) || openSlots > settlerSupply) {
			GameHelper.setLastRound();
			resupply = settlerSupply;
		} else if (openSlots < GameHelper.getNumberOfPlayers()) {
			resupply = GameHelper.getNumberOfPlayers();
		} else {
			resupply = openSlots;
		}
		settlerShip.setSettlers(resupply);
		settlerSupply -= resupply;
	}
	
	public List<Ship> getCargoShips() {
		return cargoShips;
	}
	
	public void clearCargoShips() {
		for (Ship ship : cargoShips) {
			ship.clearGoods();
		}
	}

	public Ship getSettlerShip() {
		return settlerShip;
	}
	
	public List<String> getRoleNames() {
		return roleNames;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public Role getRole(String roleName) {
		for (Role role : roles) {
			if (role.getName().equals(roleName)) {
				return role;
			}
		}
		return null;
	}
	
	public Role getRole(int index) {
		for (Role role : roles) {
			if (role.getName().equals(Prospector.NAME) && ((Prospector)role).getIndex() == index) {
				return role;
			}
		}
		return null;
	}

	public void addRole(Role role) {
		this.roles.add(role);
	}

	public void removeRole(Role role) {
		roles.remove(role);
	}

	public void addBonusCoinsToRoles() {
		for (Role role : roles) {
			role.addBonusCoin();
		}
	}

	public List<Building> getBuildingSupply() {
		return buildingSupply;
	}

	public Building removeBuildingFromSupply(String buildingName) {
		for (int i = 0; i < buildingSupply.size(); i++) {
			Building building = buildingSupply.get(i);
			if (building.getName().equals(buildingName)) {
				subtractBuildingSupplyCount(buildingName);
				return buildingSupply.remove(i);
			}
		}
		return null;
	}

	public void subtractBuildingSupplyCount(String buildingName) {
		buildingSupplyCount.put(buildingName, buildingSupplyCount.get(buildingName) - 1);
	}

	public int getBuildingSupplyCount(String buildingName) {
		return buildingSupplyCount.get(buildingName);
	}

	public int getNumberOfRoles() {
		return roleNames.size();
	}
	
	public boolean isRoleAvailable(String roleName) {
		return getRole(roleName) != null;
	}
	
	public boolean isRoleAvailable(int index) {
		return getRole(index) != null;
	}
}
