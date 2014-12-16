package com.brejral.puertorico.game.role;

import java.util.ArrayList;
import java.util.List;

import com.brejral.puertorico.game.GameHelper;
import com.brejral.puertorico.game.building.Building;
import com.brejral.puertorico.game.building.Harbor;
import com.brejral.puertorico.game.building.Wharf;
import com.brejral.puertorico.game.crop.Crop;
import com.brejral.puertorico.game.player.Player;
import com.brejral.puertorico.game.ship.Ship;

public class Captain extends Role {
	public static final String NAME = "Captain";
	private boolean hasCaptainReceivedBonus = false;
	private Integer shipIndex = null;
	
	public Captain() {
		super(NAME);
	}
	
	public void onRoleStart() {
		super.onRoleStart();
		if (getShipsPlayerCanSelect().size() == 0 && !hasUnusedWharf()) {
			advanceToNextAction();
		}
	}
	
	public void selectShip(int shipIndex) {
		this.shipIndex = shipIndex;
		Ship selectedShip = shipIndex > -1 ? GameHelper.getCargoShips().get(shipIndex) : null;
		List<String> goodOptions = getGoodsPlayerCanSelect();
 		if ((selectedShip != null && selectedShip.hasGoods()) || goodOptions.size() == 1) {
			selectGood(selectedShip.hasGoods() ? selectedShip.getGoodName() : goodOptions.get(0));
		}
	}
	
	public void selectGood(String goodName) {
		Player player = GameHelper.getCurrentPlayerForAction();
		List<Ship> ships = GameHelper.getCargoShips();
		Ship ship = shipIndex != -1 ? ships.get(shipIndex) : null;
		int shippedGoods = 0;
		if (shipIndex == -1) {
			shippedGoods = player.getNumberOfGoods(goodName);
		} else if (ship.getGoods() == 0) {
			ship.setGoodName(goodName);
			shippedGoods = Math.min(ship.getGoodCapacity(), player.getNumberOfGoods(goodName));
		} else {
			shippedGoods = Math.min(ship.getOpenGoodSlots(), player.getNumberOfGoods(goodName));
		}
		int points = shippedGoods + (player.isTurn() && !hasCaptainReceivedBonus ? 1 : 0) + (player.hasActiveBuilding(Harbor.NAME) ? 1 : 0);
		GameHelper.addPointsToPlayerFromSupply(player, points);
		player.subtractGood(goodName, shippedGoods);
		if (ship != null) {
			ship.addGoods(shippedGoods);
		}
		shipIndex = null;
		advanceToNextAction();
	}
	
	private void advanceToNextAction() {
		for (int i = 0; i < GameHelper.getNumberOfPlayers(); i++) {
			super.onAction();
			List<Ship> shipOptions = getShipsPlayerCanSelect();
			if (shipOptions.size() > 1 || (hasUnusedWharf() && shipOptions.size() == 1)) {
				return;
			} else if (shouldAutoSelectShip()) {
				selectShip(getAutoSelectShipIndex());
				return;
			}
		}
		onRoleEnd();
	}
	
	public void onRoleEnd() {
		hasCaptainReceivedBonus = false;
		GameHelper.clearCargoShips();
		super.onRoleEnd();
	}
	
	public List<Ship> getShipsPlayerCanSelect() {
		Player player = GameHelper.getCurrentPlayerForAction();
		List<Ship> shipList = new ArrayList<>();
		for (Ship ship : GameHelper.getCargoShips()) {
			if ((ship.isEmpty() && getGoodsPlayerCanSelect().size() > 0) || (player.hasGood(ship.getGoodName()) && ship.hasOpenSlots())) {
				shipList.add(ship);
			}
		}
		return shipList;
	}
	
	public List<String> getGoodsPlayerCanSelect() {
		Player player = GameHelper.getCurrentPlayerForAction();
		List<String> goodList = new ArrayList<>();
		for (String goodName : Crop.CROP_LIST) {
			if ((!hasGoodBeenLoadedOnShip(goodName) || (shipIndex != null && shipIndex == -1)) && player.getNumberOfGoods(goodName) > 0) {
				goodList.add(goodName);
			}
		}
		return goodList;
	}
	
	private boolean hasGoodBeenLoadedOnShip(String goodName) {
		for (Ship ship : GameHelper.getCargoShips()) {
			if (ship.isGood(goodName)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean shouldAutoSelectShip() {
		List<Ship> shipOptions = getShipsPlayerCanSelect();
		return shipOptions.size() == 1 || (shipOptions.size() == 0 && hasUnusedWharf());
	}
	
	private Integer getAutoSelectShipIndex() {
		List<Ship> shipOptions = getShipsPlayerCanSelect();
		if (shipOptions.size() == 1) {
			return GameHelper.getCargoShips().indexOf(shipOptions.get(0));
		} else if (shipOptions.size() == 0 && hasUnusedWharf()) {
			setUsedWharf();
			return -1;
		}
		return null;
	}
	
	public boolean hasShipBeenSelected() {
		return shipIndex != null;
	}
	
	public int getSelectedShip() {
		return shipIndex;
	}
	
	private boolean hasUnusedWharf() {
		List<Building> wharfs = GameHelper.getCurrentPlayerForAction().getBuildings(Wharf.NAME);
		for (Building building : wharfs) {
			if (!((Wharf) building).isUsed()) {
				return true;
			}
		}
		return false;
	}
	
	private void setUsedWharf() {
		List<Building> wharfs = GameHelper.getCurrentPlayerForAction().getBuildings(Wharf.NAME);
		for (Building building : wharfs) {
			if (!((Wharf) building).isUsed()) {
				((Wharf)building).invertIsUsed();
			}
		}
	}
}
