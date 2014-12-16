package com.brejral.puertorico.game.building;

public class LargeMarket extends Building {
	public static final String NAME = "Large Market";
	public static final String TOOLTIP = "<html><b>" + NAME + "</b><br>"
				+ "<b>Cost:</b> 5 <b>Points:</b> 2<br>"
				+ "+ 2 doubloons with sale<br>"
				+ "(trader phase)</html>";

	public LargeMarket() {
		super(NAME, TOOLTIP);
		setPrice(5);
		setPoints(2);
	}
}
