package com.brejral.puertorico.game.role;

import java.util.ArrayList;
import java.util.List;

import com.brejral.puertorico.game.GameHelper;
import com.brejral.puertorico.game.building.Building;
import com.brejral.puertorico.game.building.Harbor;
import com.brejral.puertorico.game.building.LargeWarehouse;
import com.brejral.puertorico.game.building.SmallWarehouse;
import com.brejral.puertorico.game.building.Wharf;
import com.brejral.puertorico.game.crop.Crop;
import com.brejral.puertorico.game.player.Player;
import com.brejral.puertorico.game.ship.Ship;

public class Captain extends Role {
	public static final String NAME = "Captain";
	private boolean hasCaptainReceivedBonus = false;
	private boolean isDoneShipping = false;
	private Integer shipIndex = null;

	public Captain() {
		super(NAME);
	}

	public void onRoleStart() {
		super.onRoleStart();
		if (getShipsPlayerCanSelect().size() == 0 && !hasUnusedWharf()) {
			advanceToNextShippingAction();
		} else if (shouldAutoSelectShip()) {
			selectShip(getAutoSelectShipIndex());
		}
	}

	public void selectShip(int shipIndex) {
		this.shipIndex = shipIndex;
		Ship selectedShip = shipIndex > -1 ? GameHelper.getCargoShips().get(shipIndex) : null;
		List<String> goodOptions = getGoodsPlayerCanSelect();
		if ((selectedShip != null && selectedShip.hasGoods()) || goodOptions.size() == 1) {
			selectGoodToShip(selectedShip.hasGoods() ? selectedShip.getGoodName() : goodOptions.get(0));
		}
	}

	public void selectGoodToShip(String goodName) {
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
		int points = shippedGoods + (shouldReceiveCaptainBonus() ? 1 : 0) + (player.hasActiveBuilding(Harbor.NAME) ? 1 : 0);
		GameHelper.addPointsToPlayerFromSupply(player, points);
		player.subtractGood(goodName, shippedGoods);
		if (ship != null) {
			ship.addGoods(shippedGoods);
		}
		shipIndex = null;
		advanceToNextShippingAction();
	}

	public void selectGoodToStore(String goodName) {
		Building building = getOpenWarehouse();
		if (building == null) {
			Player player = GameHelper.getCurrentPlayerForAction();
			if (player.getNumberOfGoods(goodName) > 1) {
				GameHelper.addGoodsToSupplyFromPlayer(player, goodName, player.getNumberOfGoods(goodName) - 1);
			}
			advanceToNextStoringAction();
		} else if (building.getName().equals(SmallWarehouse.NAME)) {
			((SmallWarehouse) building).storeGood(goodName);
		} else if (building.getName().equals(LargeWarehouse.NAME)) {
			((LargeWarehouse) building).storeGood(goodName);
		}
	}

	private void advanceToNextShippingAction() {
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
		onShippingEnd();
	}

	private void advanceToNextStoringAction() {
		for (Building building : getWarehousesForPlayer()) {
			if (building.getName().equals(SmallWarehouse.NAME)) {
				((SmallWarehouse)building).clearGoodsStored();
			} else if (building.getName().equals(LargeWarehouse.NAME)) {
				((LargeWarehouse)building).clearGoodsStored();
			}
		}
		if (GameHelper.getNextPlayer(GameHelper.getCurrentPlayerForAction()).isTurn()) {
			onRoleEnd();
		} else {
			super.onAction();
			checkForAutoGoodStoring();
		}
	}

	public void onShippingEnd() {
		isDoneShipping = true;
		GameHelper.getCurrentPlayerForAction().setAction(false);
		GameHelper.getCurrentPlayerForTurn().setAction(true);
		checkForAutoGoodStoring();
	}

	public void onRoleEnd() {
		hasCaptainReceivedBonus = false;
		isDoneShipping = false;
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

	public boolean isDoneShipping() {
		return isDoneShipping;
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
				((Wharf) building).invertIsUsed();
			}
		}
	}

	public Building getOpenWarehouse() {
		for (Building building : getWarehousesForPlayer()) {
			if (building.getName().equals(SmallWarehouse.NAME)) {
				if (((SmallWarehouse) building).isOpen()) {
					return building;
				}
			} else if (building.getName().equals(LargeWarehouse.NAME)) {
				if (((LargeWarehouse) building).isOpen()) {
					return building;
				}
			}
		}
		return null;
	}
	
	public List<Building> getWarehousesForPlayer() {
		Player player = GameHelper.getCurrentPlayerForAction();
		List<String> warehouseBuildings = new ArrayList<>();
		warehouseBuildings.add(SmallWarehouse.NAME);
		warehouseBuildings.add(LargeWarehouse.NAME);
		return player.getBuildings(warehouseBuildings);
	}
	
	public List<String> getGoodsToStore() {
		Player player = GameHelper.getCurrentPlayerForAction();
		List<String> goodsToStore = new ArrayList<>();
		for (String goodName : Crop.CROP_LIST) {
			if (player.getNumberOfGoods(goodName) > 0) {
				goodsToStore.add(goodName);
			}
		}
		List<Building> warehouses = getWarehousesForPlayer();
		for (Building warehouse : warehouses) {
			if (warehouse.getName().equals(SmallWarehouse.NAME)) {
				goodsToStore.removeAll(((SmallWarehouse)warehouse).getGoodsStored());
			} else if (warehouse.getName().equals(LargeWarehouse.NAME)) {
				goodsToStore.removeAll(((LargeWarehouse)warehouse).getGoodsStored());
			}
		}
		return goodsToStore;
	}
	
	private void checkForAutoGoodStoring() {
		Player player = GameHelper.getCurrentPlayerForAction();
		int numSmallWarehouses = player.getNumberActiveBuildings(SmallWarehouse.NAME);
		int numLargeWarehouses = player.getNumberActiveBuildings(LargeWarehouse.NAME);
		List<String> goodsToStore = getGoodsToStore();
		boolean playerHas1CountGood = false;
		for (String goodName : goodsToStore) {
			if (player.getNumberOfGoods(goodName) == 1) {
				playerHas1CountGood = true;
				break;
			}
		}
		if (goodsToStore.size() <= numSmallWarehouses + 2 * numLargeWarehouses) {
			advanceToNextStoringAction();
		} else if (goodsToStore.size() == 1 + numSmallWarehouses + 2 * numLargeWarehouses && playerHas1CountGood) {
			advanceToNextStoringAction();
		} else if (goodsToStore.size() == 1) {
			selectGoodToStore(goodsToStore.get(0));
		}
	}
	
	private boolean shouldReceiveCaptainBonus() {
		if (GameHelper.getCurrentPlayerForAction().isTurn() && !hasCaptainReceivedBonus) {
			hasCaptainReceivedBonus = true;
			return true;
		}
		return false;
	}
}
