package com.brejral.puertorico.game.crop;

public class Crop {
	protected int sellingPrice;
	protected boolean isSettled;
	
	public Crop() {
		
	}
	
	public boolean isSettled() {
		return this.isSettled;
	}
	
	public void setIsSettled(boolean value) {
		this.isSettled = value;
	}
}
