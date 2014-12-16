package com.brejral.puertorico.game.building;

public class University extends Building {
	public static final String NAME = "University";
	public static final String TOOLTIP = "<html><b>" + NAME + "</b><br>"
				+ "<b>Cost:</b> 8 <b>Points:</b> 3<br>"
				+ "+ 1 colonist for building<br>"
				+ "(builder phase)</html>";
	
	public University() {
		super(NAME, TOOLTIP);
		setPrice(8);
		setPoints(3);
	}
}
