package com.brejral.puertorico.game.crop;


public class Crop {
	public static final String[] CROP_LIST = {"Corn", "Indigo", "Sugar", "Tobacco", "Coffee" };
	private boolean isSettled;
	private String name;
	
	public Crop(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isSettled() {
		return isSettled;
	}
	
	public void setIsSettled(boolean value) {
		this.isSettled = value;
	}
}
