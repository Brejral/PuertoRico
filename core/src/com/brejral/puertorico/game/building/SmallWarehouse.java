package com.brejral.puertorico.game.building;

public class SmallWarehouse extends Building {
	public static final String NAME = "Small Warehouse";
	public static final String TOOLTIP = "<html><b>" + NAME + "</b><br>"
				+ "<b>Cost:</b> 3 <b>Points:</b> 1<br>"
				+ "store 1 kind of goods<br>"
				+ "(captain phase)</html>";

	public SmallWarehouse() {
		super(NAME, TOOLTIP);
		setPrice(3);
		setPoints(1);
	}
}
