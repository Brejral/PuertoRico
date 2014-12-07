package com.brejral.puertorico.game.building;

import com.brejral.puertorico.game.crop.Sugar;

public class SmallSugarMill extends Building {
	public static final String NAME = "Small Sugar Plant";

	public SmallSugarMill() {
		super(NAME);
		setIsProduction(true);
		setCrop(Sugar.NAME);
		setPrice(2);
		setPoints(1);
	}

}
