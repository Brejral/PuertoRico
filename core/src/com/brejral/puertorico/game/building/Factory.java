package com.brejral.puertorico.game.building;

public class Factory extends Building {
	public static final String NAME = "Factory";
	public static final String TOOLTIP = "<html><b>" + NAME + "</b><br>"
				+ "<b>Cost:</b> 7 <b>Points:</b> 3<br>"
				+ "+ 0/1/2/3/5 doubloons with production<br>"
				+ "(craftsman phase)</html>";
	
	public Factory() {
		super(NAME, TOOLTIP);
		setPrice(7);
		setPoints(3);
	}
}
