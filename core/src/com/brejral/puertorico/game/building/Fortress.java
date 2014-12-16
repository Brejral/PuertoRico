package com.brejral.puertorico.game.building;

import com.brejral.puertorico.game.player.Player;

public class Fortress extends Building {
	public static final String NAME = "Fortress";
	public static final String TOOLTIP = "<html><b>" + NAME + "</b><br>"
				+ "<b>Cost:</b> 10 <b>Points:</b> 4<br>"
				+ "1 victory point for every 3 colonists<br>"
				+ "(game end)</html>";
	
	public Fortress() {
		super(NAME, TOOLTIP);
		setPrice(10);
		setPoints(4);
		setSize(2);
	}
	
	public int getEndGamePoints(Player player) {
		return player.getSettlers()/3;
	}
}
