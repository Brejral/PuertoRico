package com.brejral.puertorico.game.crop;


public class Crop {
	public static final String[] CROP_LIST = {"Corn", "Indigo", "Sugar", "Tobacco", "Coffee" };
	private int sellingPrice;
	private boolean isSettled;
	private String name;
	
	public Crop(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	protected void setSellingPrice(int price) {
		this.sellingPrice = price;
	}
	
	public int getSellingPrice() {
		return sellingPrice;
	}
	
	public boolean isSettled() {
		return this.isSettled;
	}
	
	public void setIsSettled(boolean value) {
		this.isSettled = value;
	}
}
