package com.brejral.puertorico.game.building;

import com.brejral.puertorico.game.player.Player;

public class CustomsHouse extends Building {
	public static final String NAME = "Customs House";
	public static final String TOOLTIP = "<html><b>" + NAME + "</b><br>"
				+ "<b>Cost:</b> 10 <b>Points:</b> 4<br>"
				+ "1 victory point for every 4 victory point chips<br>"
				+ "(game end)</html>";

	public CustomsHouse() {
		super(NAME, TOOLTIP);
		setPrice(10);
		setPoints(4);
		setSize(2);
	}
	
	public int getGameEndPoints(Player player) {
		return player.getPoints()/4;
	}
}
