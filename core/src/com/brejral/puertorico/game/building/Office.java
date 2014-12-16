package com.brejral.puertorico.game.building;

public class Office extends Building {
	public static final String NAME = "Office";
	public static final String TOOLTIP = "<html><b>" + NAME + "</b><br>"
				+ "<b>Cost:</b> 5 <b>Points:</b> 2<br>"
				+ "sell same kind of goods<br>"
				+ "(trader phase)</html>";

	public Office() {
		super(NAME, TOOLTIP);
		setPrice(5);
		setPoints(2);
	}
}
