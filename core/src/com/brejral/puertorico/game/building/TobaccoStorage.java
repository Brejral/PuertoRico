package com.brejral.puertorico.game.building;

import com.brejral.puertorico.game.crop.Tobacco;

public class TobaccoStorage extends Building {
	public static final String NAME = "Tobacco Storage";
	public static final String TOOLTIP = "<html><b>" + NAME + "</b><br>"
				+ "<b>Cost:</b> 5 <b>Points:</b> 3<br>"
				+ "tobacco production building</html>";

	public TobaccoStorage() {
		super(NAME, TOOLTIP);
		setIsProduction(true);
		setPrice(5);
		setPoints(3);
		setSettlerSlots(3);
		setCrop(Tobacco.NAME);
	}
}
