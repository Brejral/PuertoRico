package com.brejral.puertorico.game.building;

public class Building {
	private String name;
	protected int size;
	
	public Building(String nm) {
		this.name = nm;
	}
	
	public String getName() {
		return name;
	}
}
