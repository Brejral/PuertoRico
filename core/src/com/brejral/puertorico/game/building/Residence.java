package com.brejral.puertorico.game.building;

import com.brejral.puertorico.game.player.Player;

public class Residence extends Building {
	public static final String NAME = "Residence";

	public Residence() {
		super(NAME);
		setCost(10);
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
