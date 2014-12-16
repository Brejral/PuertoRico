package com.brejral.puertorico.game.building;

import com.brejral.puertorico.game.player.Player;

public class Residence extends Building {
	public static final String NAME = "Residence";
	public static final String TOOLTIP = "<html><b>" + NAME + "</b><br>"
				+ "<b>Cost:</b> 10 <b>Points:</b> 4<br>"
				+ "4 VP for &le; 9<br>"
				+ "5 VP for 10<br>"
				+ "6 VP for 11<br>"
				+ "7 VP for 12<br>"
				+ "occupied island spaces<br>"
				+ "(game end)</html>";

	public Residence() {
		super(NAME, TOOLTIP);
		setPrice(10);
		setPoints(4);
		setSize(2);
	}
	
	public int getGameEndPoints(Player player) {
		int cropCount = player.getCrops().size();
		if (cropCount == 12) {
			return 7;
		} else if (cropCount == 11) {
			return 6;
		} else if (cropCount == 10) {
			return 5;
		}
		return 4;
	}
}
