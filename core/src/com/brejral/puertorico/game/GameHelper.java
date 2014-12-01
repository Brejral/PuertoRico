package com.brejral.puertorico.game;

import java.util.List;

import com.brejral.puertorico.game.bank.Bank;
import com.brejral.puertorico.game.crop.Crop;
import com.brejral.puertorico.game.crop.Quarry;
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

	public static Crop getCropFromBank(String className) {
		List<Crop> crops = GAME.getBank().getCropSupply();
		for (Crop crop : crops) {
			if (crop.getClass().getName().equals(className)) {
				return crops.remove(crops.indexOf(crop));
			}
		}
		return null;
	}

	public static Quarry getQuarryFromBank() {
		return GAME.getBank().getQuarrySupply().remove(0);
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
}
