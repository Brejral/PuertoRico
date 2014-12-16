package com.brejral.puertorico.game.building;

import com.brejral.puertorico.game.crop.Sugar;

public class SmallSugarMill extends Building {
	public static final String NAME = "Small Sugar Mill";
	public static final String TOOLTIP = "<html><b>" + NAME + "</b><br>"
				+ "<b>Cost:</b> 2 <b>Points:</b> 1<br>"
				+ "small sugar production building</html>";

	public SmallSugarMill() {
		super(NAME, TOOLTIP);
		setIsProduction(true);
		setCrop(Sugar.NAME);
		setPrice(2);
		setPoints(1);
	}

}
