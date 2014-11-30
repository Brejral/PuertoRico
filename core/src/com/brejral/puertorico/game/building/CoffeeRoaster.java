package com.brejral.puertorico.game.building;

import com.brejral.puertorico.game.crop.Coffee;

public class CoffeeRoaster extends Building {
	public static final String NAME = "Coffee Roaster";

	public CoffeeRoaster() {
		super(NAME);
		setIsProduction(true);
		setCost(6);
		setPoints(3);
		setSettlerSlots(2);
		setCrop(Coffee.NAME);
	}
}
