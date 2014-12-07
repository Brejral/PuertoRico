package com.brejral.puertorico.game.building;

import com.brejral.puertorico.game.player.Player;

public class CityHall extends Building {
	public static final String NAME = "City Hall";

	public CityHall() {
		super(NAME);
		setPrice(10);
		setPoints(4);
		setSize(2);
	}
	
	public int getGameEndPoints(Player player) {
		int points = 0;
		for (Building building : player.getBuildings()) {
			if (building.isUtility()) {
				points++;
			}
		}
		return points;
	}
}
