package com.brejral.puertorico.game.building;

public class Hacienda extends Building {
	public static final String NAME = "Hacienda";
	public static final String TOOLTIP = "<html><b>" + NAME + "</b><br>"
				+ "<b>Cost:</b> 2 <b>Points:</b> 1<br>"
				+ "+1 plantation from supply<br>"
				+ "(settler supply)</html>";

	public Hacienda(){
		super(NAME, TOOLTIP);
		setPrice(2);
		setPoints(1);
	}
}
