package com.brejral.puertorico.game;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.brejral.puertorico.game.bank.Bank;
import com.brejral.puertorico.game.crop.Corn;
import com.brejral.puertorico.game.crop.Indigo;
import com.brejral.puertorico.game.player.Player;
import com.brejral.puertorico.game.role.Role;

public class Game {
	private int numberOfPlayers;
	private Bank bank;
	private List<Player> players;
	private boolean isLastRound;

	public Game(List<Player> players) {
		numberOfPlayers = players.size();
		GameHelper.setGame(this);
		this.players = players;
		bank = new Bank();
		StartGame();
	}

	private void StartGame() {
		Collections.shuffle(players, new Random(GameHelper.RAND_SEED));

		// Decide how many players get Corn
		int numGetCorn = 2;
		if (numberOfPlayers == 3) {
			numGetCorn = 1;
		}

		// Setup the Players
		for (int i = 0; i < numberOfPlayers; i++) {
			Player player = players.get(i);
			if (i == 0) {
				player.setGovenor(true);
				player.setTurn(true);
				player.setAction(true);
			}
			if (i < numberOfPlayers - numGetCorn) {
				player.addCrop(Indigo.NAME);
			} else {
				player.addCrop(Corn.NAME);
			}
			player.addCoins(numberOfPlayers - 1);
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

	public void setLastRound() {
		isLastRound = true;
	}

	public boolean isLastRound() {
		return isLastRound;
	}

	public void endGame() {

	}

	public void nextRound() {
		Player govenor = getCurrentPlayerForRound();
		Player nextPlayer = GameHelper.getNextPlayer(govenor);
		govenor.setAction(false);
		if (isLastRound()) {
			endGame();
		} else {
			getBank().addBonusCoinsToRoles();
			for (Player player : players) {
				getBank().addRole(player.getRole());
				player.setRole(null);
			}
			nextPlayer.setGovenor(true);
			nextPlayer.setTurn(true);
			nextPlayer.setAction(true);
		}
	}

	public void nextTurn() {
		Player turnPlayer = getCurrentPlayerForTurn();
		Player nextPlayer = GameHelper.getNextPlayer(turnPlayer);
		if (isEndOfRound()) {
			nextRound();
		} else {
			GameHelper.getCurrentPlayerForAction().setAction(false);
			nextPlayer.setTurn(true);
			nextPlayer.setAction(true);
		}
		turnPlayer.setTurn(false);
	}

	public void nextAction() {
		Player actionPlayer = getCurrentPlayerForAction();
		Player nextPlayer = GameHelper.getNextPlayer(actionPlayer);
		actionPlayer.setAction(false);
		nextPlayer.setAction(true);
	}

	public Player getCurrentPlayerForRound() {
		for (Player player : players) {
			if (player.isGovernor()) {
				return player;
			}
		}
		return null;
	}

	public boolean isEndOfRound() {
		Player turnPlayer = getCurrentPlayerForTurn();
		return GameHelper.getNextPlayer(turnPlayer).equals(getCurrentPlayerForRound());
	}

	public boolean isEndOfTurn() {
		Player actionPlayer = getCurrentPlayerForAction();
		return GameHelper.getNextPlayer(actionPlayer).equals(getCurrentPlayerForTurn());
	}

	public Player getCurrentPlayerForTurn() {
		for (Player player : players) {
			if (player.isTurn()) {
				return player;
			}
		}
		return null;
	}

	public Player getCurrentPlayerForAction() {
		for (Player player : players) {
			if (player.isAction()) {
				return player;
			}
		}
		return null;
	}

	public Role getCurrentRole() {
		return getCurrentPlayerForTurn().getRole();
	}
}
