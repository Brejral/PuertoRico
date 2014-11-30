package com.brejral.puertorico.game.building;

public class Harbor extends Building {
	public static final String NAME = "Harbor";

	public Harbor() {
		super(NAME);
		setCost(8);
		setPoints(3);
	}
}
