package com.brejral.puertorico.game.building;

import com.brejral.puertorico.game.player.Player;

public class GuildHall extends Building {
	public static final String NAME = "Guild Hall";

	public GuildHall() {
		super(NAME);
		setPrice(10);
		setPoints(4);
		setSize(2);
	}
	
	public int getGameEndPoints(Player player) {
		int points = 0;
		for (Building building : player.getBuildings()) {
			if (building.isProduction()) {
				if (building.isSmallProduction()) {
					points++;
				} else {
					points += 2;
				}
			}
		}
		return points;
	}
}
