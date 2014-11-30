package com.brejral.puertorico.game.building;

public class Residence extends Building {
	public static final String NAME = "Residence";

	public Residence() {
		super(NAME);
		setCost(10);
		setPoints(4);
	}
}
