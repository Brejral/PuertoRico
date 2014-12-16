package com.brejral.puertorico.game.building;

public class LargeWarehouse extends Building {
	public static final String NAME = "Large Warehouse";
	public static final String TOOLTIP = "<html><b>" + NAME + "</b><br>"
				+ "<b>Cost:</b> 6 <b>Points:</b> 2<br>"
				+ "store 2 kinds of goods<br>"
				+ "(captain phase)</html>";

	public LargeWarehouse() {
		super(NAME, TOOLTIP);
		setPrice(6);
		setPoints(2);
	}
}
