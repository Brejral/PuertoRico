package com.brejral.puertorico.game.ship;

public class Ship {
	private int goodCapacity, settlers;
	

	public Ship() {
		
	}
	
	public Ship(int capacity) {
		goodCapacity = capacity;
	}
	
	public void setSettlers(int value) {
		settlers = value;
	}
	
	public int getSettlers() {
		return settlers;
	}
	
	public int getGoodCapacity() {
		return goodCapacity;
	}
}
