package com.brejral.puertorico.game.building;

public class Fortress extends Building {
	public static final String NAME = "Fortress";

	public Fortress() {
		super(NAME);
		setCost(10);
		setPoints(4);
	}
}
