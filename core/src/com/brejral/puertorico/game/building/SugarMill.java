package com.brejral.puertorico.game.building;

import com.brejral.puertorico.game.crop.Sugar;

public class SugarMill extends Building {
	public static final String NAME = "Sugar Mill";

	public SugarMill() {
		super(NAME);
		setIsProduction(true);
		setSettlerSlots(3);
		setPrice(4);
		setCrop(Sugar.NAME);
		setPoints(2);
	}

}
