package com.brejral.puertorico.game.building;

import com.brejral.puertorico.game.crop.Indigo;

public class SmallIndigoPlant extends Building {
	public static final String NAME = "Small Indigo Plant";

	public SmallIndigoPlant() {
		super(NAME);
		setIsProduction(true);
		setPrice(1);
		setPoints(1);
		setCrop(Indigo.NAME);
	}
}
