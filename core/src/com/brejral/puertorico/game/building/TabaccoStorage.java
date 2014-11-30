package com.brejral.puertorico.game.building;

import com.brejral.puertorico.game.crop.Tobacco;

public class TabaccoStorage extends Building {
	public static final String NAME = "Tobacco Storage";

	public TabaccoStorage() {
		super(NAME);
		setIsProduction(true);
		setCost(5);
		setPoints(3);
		setSettlerSlots(3);
		setCrop(Tobacco.NAME);
	}
}
