package com.brejral.puertorico.game.building;

import com.brejral.puertorico.game.player.Player;

public class GuildHall extends Building {
	public static final String NAME = "Guild Hall";
	public static final String TOOLTIP = "<html><b>" + NAME + "</b><br>"
				+ "<b>Cost:</b> 10 <b>Points:</b> 4<br>"
				+ "2 victory points for each large production building<br>"
				+ "1 victory point for each small production building<br>"
				+ "(game end)</html>";

	public GuildHall() {
		super(NAME, TOOLTIP);
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
