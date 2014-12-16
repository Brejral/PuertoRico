package com.brejral.puertorico.game.ship;

import com.brejral.puertorico.game.GameHelper;

public class Ship {
	private int goodCapacity, settlers, goods;
	private String goodName = null;

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

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public boolean isGood(String goodName) {
		return this.goodName != null ? this.goodName.equals(goodName) : false;
	}

	public void clearGoods() {
		if (goodName != null) {
			GameHelper.addGoodsToSupply(goodName, goods);
			goods = 0;
			goodName = null;
		}
	}

	public boolean isFull() {
		return goods == goodCapacity;
	}

	public boolean isEmpty() {
		return goods == 0;
	}

	public boolean hasOpenSlots() {
		return goods < goodCapacity;
	}

	public boolean hasGoods() {
		return goods > 0;
	}
}
