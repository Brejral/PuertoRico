package com.brejral.puertorico.game.bank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.brejral.puertorico.game.GameHelper;
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

public class Bank {
	private int settlerSupply, coinSupply, pointSupply;
	private List<Crop> cropSupply, settlerCropSupply;
	private List<Quarry> quarrySupply;
	private Ship settlerShip;
	private List<Ship> cargoShips;
	private List<Role> roles;
	private HashMap<String, Integer> goodSupply;
	private HashMap<String, Integer> buildingSupply;
	
	public Bank() {
		initializeCropSupply();
		initializeGoodSupply();
		initializeQuarrySupply();
		initializeRoles();
		initializeCargoShips();
		initializeBuildingSupply();
		settlerShip = new Ship();
		settlerCropSupply = new ArrayList<Crop>(GameHelper.getNumberOfPlayers() + 1);
		switch(GameHelper.getNumberOfPlayers()) {
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
			roles.add(new Prospector());
			roles.add(new Prospector());
		} else if (GameHelper.getNumberOfPlayers() == 4) {
			roles.add(new Prospector());
		}
	}
	
	private void initializeCargoShips() {
		cargoShips = new ArrayList<Ship>();
		cargoShips.add(new Ship(GameHelper.getNumberOfPlayers() + 1));
		cargoShips.add(new Ship(GameHelper.getNumberOfPlayers() + 2));
		cargoShips.add(new Ship(GameHelper.getNumberOfPlayers() + 3));
	}
	
	private void initializeBuildingSupply() {
		buildingSupply = new HashMap<String, Integer>();
		buildingSupply.put(SmallIndigoPlant.NAME, 4);
		buildingSupply.put(SmallSugarMill.NAME, 4);
		buildingSupply.put(IndigoPlant.NAME, 3);
		buildingSupply.put(SugarMill.NAME, 3);
		buildingSupply.put(TobaccoStorage.NAME, 3);
		buildingSupply.put(CoffeeRoaster.NAME, 3);
		buildingSupply.put(SmallMarket.NAME, 2);
		buildingSupply.put(Hacienda.NAME, 2);
		buildingSupply.put(ConstructionHut.NAME, 2);
		buildingSupply.put(SmallWarehouse.NAME, 2);
		buildingSupply.put(Hospice.NAME, 2);
		buildingSupply.put(Office.NAME, 2);
		buildingSupply.put(LargeMarket.NAME, 2);
		buildingSupply.put(LargeWarehouse.NAME, 2);
		buildingSupply.put(Factory.NAME, 2);
		buildingSupply.put(University.NAME, 2);
		buildingSupply.put(Harbor.NAME, 2);
		buildingSupply.put(Wharf.NAME, 2);
		buildingSupply.put(GuildHall.NAME, 1);
		buildingSupply.put(Residence.NAME, 1);
		buildingSupply.put(CustomsHouse.NAME, 1);
		buildingSupply.put(CityHall.NAME, 1);
		buildingSupply.put(Fortress.NAME, 1);
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
	
	public void addCoinSupply(int value) {
		this.coinSupply += value;
	}

	public int getPointSupply() {
		return pointSupply;
	}

	public void setPointSupply(int pointSupply) {
		this.pointSupply = pointSupply;
	}

	public HashMap<String, Integer> getGoodSupply() {
		return goodSupply;
	}
	
	public List<Crop> getCropSupply() {
		return cropSupply;
	}
	
	public List<Quarry> getQuarrySupply() {
		return quarrySupply;
	}

	public void setSettlerCropsSupply() {
		for (int i = 0; i < settlerCropSupply.size(); i++) {
			if (settlerCropSupply.get(i) == null) {
				settlerCropSupply.set(i, cropSupply.get(0));
				cropSupply.remove(0);
			}
		}
	}
	
	public List<Crop> getSettlerCropsSupply() {
		return settlerCropSupply;
	}

	public void resupplySettlerShip(int openSlots) {
		int resupply = 0;
		if (openSlots > settlerSupply) {
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

	public Ship getSettlerShip() {
		return settlerShip;
	}
	
	public List<Role> getRoles() {
		return roles;
	}

	public void addRole(Role role) {
		this.roles.add(role);
	}

	public void addBonusCoinsToRoles() {
		for (Role role : roles) {
			role.addBonusCoin();
		}
	}
}
