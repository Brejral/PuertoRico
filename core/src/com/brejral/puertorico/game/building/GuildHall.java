package com.brejral.puertorico.game.building;

public class GuildHall extends Building {
	public static final String NAME = "Guild Hall";

	public GuildHall() {
		super(NAME);
		setCost(10);
		setPoints(4);
	}
}
