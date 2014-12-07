package com.brejral.puertorico.game.building;

import com.brejral.puertorico.game.player.Player;

public class Fortress extends Building {
	public static final String NAME = "Fortress";

	public Fortress() {
		super(NAME);
		setPrice(10);
		setPoints(4);
		setSize(2);
	}
	
	public int getEndGamePoints(Player player) {
		return player.getSettlers()/3;
	}
}
