package com.brejral.puertorico.game.building;

import com.brejral.puertorico.game.crop.Indigo;

public class IndigoPlant extends Building {
	public static final String NAME = "Indigo Plant";
	public static final String TOOLTIP = "<html><b>" + NAME + "</b><br>"
				+ "<b>Cost:</b> 3 <b>Points:</b> 2<br>"
				+ "indigo production plant</html>";

	public IndigoPlant() {
		super(NAME, TOOLTIP);
		setIsProduction(true);
		setPrice(3);
		setSettlerSlots(3);
		setCrop(Indigo.NAME);
		setPoints(2);
	}
}
