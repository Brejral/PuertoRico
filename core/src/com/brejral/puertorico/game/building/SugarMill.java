package com.brejral.puertorico.game.building;

import com.brejral.puertorico.game.crop.Sugar;

public class SugarMill extends Building {
	public static final String NAME = "Sugar Mill";
	public static final String TOOLTIP = "<html><b>" + NAME + "</b><br>"
				+ "<b>Cost:</b> 4 <b>Points:</b> 2<br>"
				+ "sugar production building</html>";

	public SugarMill() {
		super(NAME, TOOLTIP);
		setIsProduction(true);
		setSettlerSlots(3);
		setPrice(4);
		setCrop(Sugar.NAME);
		setPoints(2);
	}

}
