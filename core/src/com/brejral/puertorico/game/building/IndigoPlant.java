package com.brejral.puertorico.game.building;

import com.brejral.puertorico.game.crop.Indigo;

public class IndigoPlant extends Building {
	public static final String NAME = "Indigo Plant";

	public IndigoPlant() {
		super(NAME);
		setIsProduction(true);
		setPrice(3);
		setSettlerSlots(3);
		setCrop(Indigo.NAME);
		setPoints(2);
	}
}
