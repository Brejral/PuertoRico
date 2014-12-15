package com.brejral.puertorico.game.building;

public class Wharf extends Building {
	public static final String NAME = "Wharf";
	private boolean isUsed = false;

	public Wharf() {
		super(NAME);
		setPrice(9);
		setPoints(3);
	}
	
	public boolean isUsed() {
		return isUsed;
	}
	
	public void invertIsUsed() {
		isUsed = !isUsed;
	}
}
