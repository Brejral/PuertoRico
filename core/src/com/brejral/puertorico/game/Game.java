package com.brejral.puertorico.game;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.brejral.puertorico.game.bank.Bank;
import com.brejral.puertorico.game.crop.Corn;
import com.brejral.puertorico.game.crop.Indigo;
import com.brejral.puertorico.game.player.Player;
import com.brejral.puertorico.game.ship.Ship;

public class Game {
	private int numberOfPlayers;
	private Bank bank;
	private List<Player> players;
	private List<Ship> cargoShips;
	
	public Game(List<Player> players) {
		numberOfPlayers = players.size();
		GameHelper.setGame(this);
		this.players = players;
		bank = new Bank();
		StartGame();
	}
	
	private void StartGame() {
		Collections.shuffle(players, new Random(GameHelper.RAND_SEED));
		Collections.shuffle(bank.getCropSupply(), new Random(GameHelper.RAND_SEED));
		bank.setSettlerCrops();
		switch(numberOfPlayers) {
		case 3:
			for (int i = 0; i < 3; i++) {
				Player player = players.get(i);
				if (i == 0) {
					player.setGovenor(true);
				}
				if (i < 2) {
					player.addCrop(Indigo.class.getName());
				} else {
					player.addCrop(Corn.class.getName());
				}
				player.addCoins(2);
			}
			break;
		case 4:
			for (int i = 0; i < 3; i++) {
				Player player = players.get(i);
				if (i == 0) {
					player.setGovenor(true);
				}
				if (i < 2) {
					player.addCrop(Indigo.class.getName());
				} else {
					player.addCrop(Corn.class.getName());
				}
				player.addCoins(3);
			}
			break;
		case 5:
			for (int i = 0; i < 3; i++) {
				Player player = players.get(i);
				if (i == 0) {
					player.setGovenor(true);
				}
				if (i < 3) {
					player.addCrop(Indigo.class.getName());
				} else {
					player.addCrop(Corn.class.getName());
				}
				player.addCoins(4);
			}
			break;
		}
	}
	
	public Bank getBank() {
		return bank;
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}
}
