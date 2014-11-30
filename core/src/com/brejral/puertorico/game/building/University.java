package com.brejral.puertorico.game.building;

public class University extends Building {
	public static final String NAME = "University";
	
	public University() {
		super(NAME);
		setCost(8);
		setPoints(3);
	}
}
