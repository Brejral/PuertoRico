package com.brejral.puertorico.game.building;

public class Hospice extends Building {
	public static final String NAME = "Hospice";
	public static final String TOOLTIP = "<html><b>" + NAME + "</b><br>"
				+ "<b>Cost:</b> 4 <b>Points:</b> 2<br>"
				+ "+ 1 colonist for settling<br>"
				+ "(settler phase)</html>";

	public Hospice() {
		super(NAME, TOOLTIP);
		setPrice(4);
		setPoints(2);
	}
}
