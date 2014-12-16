package com.brejral.puertorico.game.building;

import com.brejral.puertorico.game.player.Player;

public class CityHall extends Building {
	public static final String NAME = "City Hall";
	public static final String TOOLTIP = "<html><b>" + NAME + "</b><br>"
				+ "<b>Cost:</b> 10 <b>Points:</b> 4<br>"
				+ "1 victory point for each violet building<br>"
				+ "(game end)</html>";

	public CityHall() {
		super(NAME, TOOLTIP);
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
