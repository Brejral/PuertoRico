package com.brejral.puertorico.game.building;

public class SmallWarehouse extends Building {
	public static final String NAME = "Small Warehouse";

	public SmallWarehouse() {
		super(NAME);
		setCost(3);
		setPoints(1);
	}
}
