package com.brejral.puertorico.game.building;

public class ConstructionHut extends Building {
	public static final String NAME = "Construction Hut";

	public ConstructionHut() {
		super(NAME);
		setPrice(2);
		setPoints(1);
	}
}
