package com.brejral.puertorico.game.ship;

import com.brejral.puertorico.game.GameHelper;

public class Ship {
	private int goodCapacity, settlers, goods;
	private String cropName = null;
	
	

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
	
	public void clearSettlers() {
		settlers = 0;
	}
	
	public int getGoodCapacity() {
		return goodCapacity;
	}

	public int getGoods() {
		return goods;
	}

	public void addGoods(int goods) {
		this.goods += goods;
	}
	
	public int getOpenGoodSlots() {
		return goodCapacity - goods;
	}

	public String getCropName() {
		return cropName;
	}

	public void setCropName(String cropName) {
		this.cropName = cropName;
	}
	
	public void clearGoods() {
		this.cropName = null;
		GameHelper.addGoodsToSupply(cropName, goods);
		this.goods = 0;
	}
}
