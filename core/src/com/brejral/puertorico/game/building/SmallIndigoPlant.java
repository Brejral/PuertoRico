package com.brejral.puertorico.game.building;

import com.brejral.puertorico.game.crop.Indigo;

public class SmallIndigoPlant extends Building {
	public static final String NAME = "Small Indigo Plant";
	public static final String TOOLTIP = "<html><b>" + NAME + "</b><br>"
				+ "<b>Cost:</b> 1 <b>Points:</b> 1<br>"
				+ "small indigo production building</html>";

	public SmallIndigoPlant() {
		super(NAME, TOOLTIP);
		setIsProduction(true);
		setPrice(1);
		setPoints(1);
		setCrop(Indigo.NAME);
	}
}
