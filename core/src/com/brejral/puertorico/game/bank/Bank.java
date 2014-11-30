package com.brejral.puertorico.game.bank;

import java.util.ArrayList;
import java.util.List;

import com.brejral.puertorico.game.GameHelper;
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
	private int settlerSupply, coinSupply, pointSupply, cornSupply = 10, coffeeSupply = 9, indigoSupply = 11, sugarSupply = 11, tabaccoSupply = 9;
	private List<Crop> cropSupply, settlerCropSupply;
	private List<Quarry> quarrySupply;
	private Ship settlerShip;
	private List<Ship> cargoShips;
	private List<Role> roles;
	
	public Bank() {
		initializeCropSupply();
		initializeQuarrySupply();
		initializeRoles();
		initializeCargoShips();
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

	public int getSettlerSupply() {
		return settlerSupply;
	}

	public void setSettlerSupply(int settlerSupply) {
		this.settlerSupply = settlerSupply;
	}

	public int getCoinSupply() {
		return coinSupply;
	}

	public void setCoinSupply(int coinSupply) {
		this.coinSupply = coinSupply;
	}

	public int getPointSupply() {
		return pointSupply;
	}

	public void setPointSupply(int pointSupply) {
		this.pointSupply = pointSupply;
	}

	public int getCornSupply() {
		return cornSupply;
	}

	public void setCornSupply(int cornSupply) {
		this.cornSupply = cornSupply;
	}

	public int getCoffeeSupply() {
		return coffeeSupply;
	}

	public void setCoffeeSupply(int coffeeSupply) {
		this.coffeeSupply = coffeeSupply;
	}

	public int getIndigoSupply() {
		return indigoSupply;
	}

	public void setIndigoSupply(int indigoSupply) {
		this.indigoSupply = indigoSupply;
	}

	public int getSugarSupply() {
		return sugarSupply;
	}

	public void setSugarSupply(int sugarSupply) {
		this.sugarSupply = sugarSupply;
	}

	public int getTabaccoSupply() {
		return tabaccoSupply;
	}

	public void setTabaccoSupply(int tabaccoSupply) {
		this.tabaccoSupply = tabaccoSupply;
	}
	
	public List<Crop> getCropSupply() {
		return cropSupply;
	}
	
	public List<Quarry> getQuarrySupply() {
		return quarrySupply;
	}

	public void setSettlerCrops() {
		for (int i = 0; i < settlerCropSupply.size(); i++) {
			if (settlerCropSupply.get(i) == null) {
				settlerCropSupply.set(i, cropSupply.get(0));
				cropSupply.remove(0);
			}
		}
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

	public void setCargoShips(List<Ship> cargoShips) {
		this.cargoShips = cargoShips;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
}
