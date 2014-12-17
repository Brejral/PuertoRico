package com.brejral.puertorico.game.building;

import java.util.ArrayList;
import java.util.List;

public class SmallWarehouse extends Building {
	public static final String NAME = "Small Warehouse";
	public static final String TOOLTIP = "<html><b>" + NAME + "</b><br>"
				+ "<b>Cost:</b> 3 <b>Points:</b> 1<br>"
				+ "store 1 kind of goods<br>"
				+ "(captain phase)</html>";
	private List<String> goodsStored = new ArrayList<>();

	public SmallWarehouse() {
		super(NAME, TOOLTIP);
		setPrice(3);
		setPoints(1);
	}
	
	public List<String> getGoodsStored() {
		return goodsStored;
	}
	
	public int getNumberOfGoodsStored() {
		return goodsStored.size();
	}
	
	public void storeGood(String goodName) {
		goodsStored.add(goodName);
	}
	
	public void clearGoodsStored() {
		goodsStored = new ArrayList<>();
	}
	
	public boolean isOpen() {
		return isActive() && goodsStored.size() < 1;
	}
	
	public boolean hasGoodsStored() {
		return isActive() && goodsStored.size() > 0;
	}
}
