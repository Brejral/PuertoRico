package com.brejral.puertorico.game.building;

public class Harbor extends Building {
	public static final String NAME = "Harbor";
	public static final String TOOLTIP = "<html><b>" + NAME + "</b><br>"
				+ "<b>Cost:</b> 8 <b>Points:</b> 3<br>"
				+ "+ 1 victory point per delivery<br>"
				+ "(captain phase)</html>";

	public Harbor() {
		super(NAME, TOOLTIP);
		setPrice(8);
		setPoints(3);
	}
}
