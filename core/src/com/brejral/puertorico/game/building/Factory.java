package com.brejral.puertorico.game.building;

public class Factory extends Building {
	public static final String NAME = "Factory";

	public Factory() {
		super(NAME);
		setCost(7);
		setPoints(3);
	}
}
