package com.brejral.puertorico.game.building;

import com.brejral.puertorico.game.player.Player;

public class CustomsHouse extends Building {
	public static final String NAME = "Customs House";

	public CustomsHouse() {
		super(NAME);
		setPrice(10);
		setPoints(4);
		setSize(2);
	}
	
	public int getGameEndPoints(Player player) {
		return player.getPoints()/4;
	}
}
