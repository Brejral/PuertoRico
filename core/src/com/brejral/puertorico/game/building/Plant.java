package com.brejral.puertorico.game.building;


public class Plant extends Building {
	private int settlerSlots, settlers;
	private String crop;
	

	public Plant(String nm, String cropType, int maxSettlers) {
		super(nm);
		this.size = 1;
		this.crop = cropType;
		this.setSettlerSlots(maxSettlers);
	}


	public int getSettlerSlots() {
		return settlerSlots;
	}


	public void setSettlerSlots(int settlerSlots) {
		this.settlerSlots = settlerSlots;
	}


	public int getSettlers() {
		return settlers;
	}


	public void setSettlers(int settlers) {
		this.settlers = settlers;
	}
	
}
