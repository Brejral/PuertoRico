package com.brejral.puertorico.game.building;

import com.brejral.puertorico.game.crop.Coffee;

public class CoffeeRoaster extends Building {
	public static final String NAME = "Coffee Roaster";
	public static final String TOOLTIP = "<html><b>" + NAME + "</b><br>"
				+ "<b>Cost:</b> 6 <b>Points:</b> 3<br>"
				+ "coffee production building</html>";

	public CoffeeRoaster() {
		super(NAME, TOOLTIP);
		setIsProduction(true);
		setPrice(6);
		setPoints(3);
		setSettlerSlots(2);
		setCrop(Coffee.NAME);
	}
}
