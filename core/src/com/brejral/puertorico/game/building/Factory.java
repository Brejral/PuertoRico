package com.brejral.puertorico.game.building;

public class Factory extends Building {
	public static final String NAME = "Factory";

	public Factory() {
		super(NAME);
		setPrice(7);
		setPoints(3);
	}
}
