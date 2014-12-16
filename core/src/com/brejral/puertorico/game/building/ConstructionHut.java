package com.brejral.puertorico.game.building;

public class ConstructionHut extends Building {
	public static final String NAME = "Construction Hut";
	public static final String TOOLTIP = "<html><b>" + NAME + "</b><br>"
				+ "<b>Cost:</b> 2 <b>Points:</b> 1<br>"
				+ "quarry instead of plantation<br>"
				+ "(settler phase)</html>";
	
	public ConstructionHut() {
		super(NAME, TOOLTIP);
		setPrice(2);
		setPoints(1);
	}
}
