package com.brejral.puertorico.game.building;

public class Wharf extends Building {
	public static final String NAME = "Wharf";

	public Wharf() {
		super(NAME);
		setCost(9);
		setPoints(3);
	}
}
