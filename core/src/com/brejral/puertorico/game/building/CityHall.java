package com.brejral.puertorico.game.building;

public class CityHall extends Building {
	public static final String NAME = "City Hall";

	public CityHall() {
		super(NAME);
		setCost(10);
		setPoints(4);
	}
}
