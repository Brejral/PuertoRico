package com.brejral.puertorico.game.building;

import com.brejral.puertorico.game.player.Player;

public class Building {
	public static final String[] BUILDING_LIST = { "Small Indigo Plant", "Small Sugar Mill", "Small Market", "Hacienda", "Construction Hut", "Small Warehouse", "Indigo Plant", "Sugar Mill", "Hospice", "Office", "Large Market", "Large Warehouse",
				"Tobacco Storage", "Coffee Roaster", "Factory", "University", "Harbor", "Wharf", "Guild Hall", "Residence", "Fortress", "City Hall", "Customs House" };
	private String name, tooltip;
	private int size = 1, settlerSlots = 1, settlers, price, points;
	private boolean isProduction = false, isRemovingSettler = false;
	private String crop;

	public Building(String nm, String tooltip) {
		this.name = nm;
		this.tooltip = tooltip;
	}

	public String getName() {
		return name;
	}
	
	public String getTooltip() {
		return tooltip;
	}

	protected void setIsProduction(boolean value) {
		this.isProduction = value;
	}

	public boolean isProduction() {
		return isProduction;
	}

	public boolean isUtility() {
		return !isProduction;
	}
	
	public boolean isRemovingSettler() {
		return isRemovingSettler;
	}
	
	public void invertIsRemovingSettler() {
		isRemovingSettler = !isRemovingSettler;
	}
	
	public void setIsRemovingSettler(boolean value) {
		isRemovingSettler = value;
	}

	public int getSettlerSlots() {
		return settlerSlots;
	}

	protected void setSettlerSlots(int settlerSlots) {
		this.settlerSlots = settlerSlots;
	}

	public int getSettlers() {
		return settlers;
	}

	public void setSettlers(int settlers) {
		this.settlers = settlers;
	}
	
	public void addSettlers(int value) {
		this.settlers += value;
	}
	
	public void subtractSettlers(int value) {
		this.settlers -= value;
	}

	public int getPrice() {
		return price;
	}

	protected void setPrice(int value) {
		this.price = value;
	}

	public int getPoints() {
		return points;
	}

	protected void setPoints(int value) {
		this.points = value;
	}

	public int getSize() {
		return size;
	}

	protected void setSize(int size) {
		this.size = size;
	}

	public String getCrop() {
		return crop;
	}

	protected void setCrop(String cropName) {
		this.crop = cropName;
	}

	public boolean isActive() {
		if (isUtility()) {
			return getSettlers() == 1;
		}
		return false;
	}

	public int getOpenSlots() {
		return settlerSlots - settlers;
	}

	public boolean isGameEndUtility() {
		return size == 2;
	}

	public int getGameEndPoints(Player player) {
		return 0;
	}

	public boolean isSmallProduction() {
		return isProduction && settlerSlots == 1;
	}
}
