package com.brejral.puertorico.game;

import java.util.List;

import com.brejral.puertorico.game.bank.Bank;
import com.brejral.puertorico.game.crop.Crop;
import com.brejral.puertorico.game.crop.Quarry;
import com.brejral.puertorico.game.player.Player;

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
	
	public static void resupplySettlerShip(int openSlots) {
		getBank().resupplySettlerShip(openSlots);
	}
	
	public static void setLastRound() {
		getGame().setLastRound();
	}
}
