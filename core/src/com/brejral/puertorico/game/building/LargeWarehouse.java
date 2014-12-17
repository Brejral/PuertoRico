package com.brejral.puertorico.game.building;

import java.util.ArrayList;
import java.util.List;

public class LargeWarehouse extends Building {
	public static final String NAME = "Large Warehouse";
	public static final String TOOLTIP = "<html><b>" + NAME + "</b><br>"
				+ "<b>Cost:</b> 6 <b>Points:</b> 2<br>"
				+ "store 2 kinds of goods<br>"
				+ "(captain phase)</html>";
	private List<String> goodsStored = new ArrayList<>();


	public LargeWarehouse() {
		super(NAME, TOOLTIP);
		setPrice(6);
		setPoints(2);
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
		return isActive() && goodsStored.size() < 2;
	}
	
	public boolean hasGoodsStored() {
		return isActive() && goodsStored.size() > 0;
	}
}
