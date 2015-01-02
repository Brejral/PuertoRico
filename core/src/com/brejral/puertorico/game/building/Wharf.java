package com.brejral.puertorico.game.building;

public class Wharf extends Building {
	public static final String NAME = "Wharf";
	public static final String TOOLTIP = "<html><b>" + NAME + "</b><br>"
				+ "<b>Cost:</b> 9 <b>Points:</b> 3<br>"
				+ "your own ship<br>"
				+ "(captain phase)</html>";
	private boolean isUsed = false;

	public Wharf() {
		super(NAME, TOOLTIP);
		setPrice(9);
		setPoints(3);
	}
	
	public boolean isUsed() {
		return isUsed;
	}
	
	public void invertIsUsed() {
		isUsed = !isUsed;
	}
}
