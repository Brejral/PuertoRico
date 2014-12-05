package com.brejral.puertorico.game;

import java.util.List;

import com.brejral.puertorico.game.bank.Bank;
import com.brejral.puertorico.game.crop.Coffee;
import com.brejral.puertorico.game.crop.Corn;
import com.brejral.puertorico.game.crop.Crop;
import com.brejral.puertorico.game.crop.Indigo;
import com.brejral.puertorico.game.crop.Quarry;
import com.brejral.puertorico.game.crop.Sugar;
import com.brejral.puertorico.game.crop.Tobacco;
import com.brejral.puertorico.game.player.Player;
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
	
	public static List<Crop> getSettlerCropSupply() {
		return getBank().getSettlerCropSupply();
	}

	public static List<Player> getPlayers() {
		return getGame().getPlayers();
	}
	
	public static Bank getBank() {
		return getGame().getBank();
	}
	
	public static Ship getSettlerShip() {
		return getBank().getSettlerShip();
	}
	
	public static void resupplySettlerShip(int openSlots) {
		getBank().resupplySettlerShip(openSlots);
	}
	
	public static void addGoodSupply(String cropName, int value) {
		getBank().addGoodSupply(cropName, value);
	}
	
	public static void subtractGoodSupply(String cropName, int value) {
		getBank().subtractGoodSupply(cropName, value);
	}
	
	public static int getCropPrice(String cropName) {
		switch(cropName) {
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
	
	// Moves the govenor position
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
	
	public static Player getCurrentPlayerForAction() {
		return getGame().getCurrentPlayerForAction();
	}

	public static Player getCurrentPlayerForTurn() {
		return getGame().getCurrentPlayerForTurn();
	}
	
	public static Player getCurrentPlayerForRound() {
		return getGame().getCurrentPlayerForRound();
	}
}
