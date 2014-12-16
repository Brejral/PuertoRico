package com.brejral.puertorico.game.building;

public class SmallMarket extends Building {
	public static final String NAME = "Small Market";
	public static final String TOOLTIP = "<html><b>" + NAME + "</b><br>"
				+ "<b>Cost:</b> 1 <b>Points:</b> 1<br>"
				+ "+ 1 doubloon with sale<br>"
				+ "(trader phase)</html>";


	public SmallMarket() {
		super(NAME, TOOLTIP);
		setPrice(1);
		setPoints(1);
	}
}
