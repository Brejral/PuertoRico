package com.brejral.puertorico.game.building;

public class Office extends Building {
	public static final String NAME = "Office";

	public Office() {
		super(NAME);
		setPrice(5);
		setPoints(2);
	}
}
