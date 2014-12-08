package com.brejral.puertorico.desktop;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import com.brejral.puertorico.game.Game;
import com.brejral.puertorico.game.GameHelper;
import com.brejral.puertorico.game.building.Building;
import com.brejral.puertorico.game.crop.Coffee;
import com.brejral.puertorico.game.crop.Corn;
import com.brejral.puertorico.game.crop.Crop;
import com.brejral.puertorico.game.crop.Indigo;
import com.brejral.puertorico.game.crop.Quarry;
import com.brejral.puertorico.game.crop.Sugar;
import com.brejral.puertorico.game.crop.Tobacco;
import com.brejral.puertorico.game.player.Player;
import com.brejral.puertorico.game.role.Builder;
import com.brejral.puertorico.game.role.Captain;
import com.brejral.puertorico.game.role.Mayor;
import com.brejral.puertorico.game.role.Role;
import com.brejral.puertorico.game.role.Settler;
import com.brejral.puertorico.game.role.Trader;

// Tim import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
// Tim import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher implements ActionListener {

	/* ********************************************************************************************** */
	// Global GUI items
	JLabel supplyCoinsLabel;
	JLabel supplyPointsLabel;
	JLabel supplySettlersLabel;

	JLabel supplyGoodsCoffeeLabel;
	JLabel supplyGoodsCornLabel;
	JLabel supplyGoodsIndigoLabel;
	JLabel supplyGoodsSugarLabel;
	JLabel supplyGoodsTobaccoLabel;
	JLabel supplyGoodsQuarryLabel;

	JButton[] roles = new JButton[8];

	JPanel settlerCropsPanel = null;
	JButton[] settlerCrops = new JButton[6];

	JPanel buildingsPanel = null;
	JButton[] buildings = new JButton[50];

	JPanel colonistShipPanel;
	JLabel colonistShip;

	JPanel cargoShipsPanel;
	JButton[] cargoShips = new JButton[3];
	
	JPanel tradingHousePanel;
	JLabel tradingHouse;

	JLabel[] playerCoinsLabel = new JLabel[5];
	JLabel[] playerPointsLabel = new JLabel[5];
	JLabel[] playerSettlersLabel = new JLabel[5];

	JLabel[] playerGoodsCoffeeLabel = new JLabel[5];
	JLabel[] playerGoodsCornLabel = new JLabel[5];
	JLabel[] playerGoodsIndigoLabel = new JLabel[5];
	JLabel[] playerGoodsSugarLabel = new JLabel[5];
	JLabel[] playerGoodsTobaccoLabel = new JLabel[5];
	JLabel[] playerGoodsQuarryLabel = new JLabel[5];

	JTabbedPane tabbedPlayerPane = null;
	int playerBuildingsMax = 12;
	JButton[][] playerBuildings = new JButton[5][playerBuildingsMax];

	int playerCropsMax = 12;
	JButton[][] playerCrops = new JButton[5][playerCropsMax];

	JLabel messageLine;

	Color[] playerColors = { Color.RED, new Color(0, 160, 0), Color.BLUE, Color.MAGENTA, new Color(0, 160, 160) };

	/* ********************************************************************************************** */
	/**
	 * Main Constructor
	 * 
	 * Create the GUI and show it.
	 */
	public DesktopLauncher() {
		// Create and set up the window.
		JFrame frame = new JFrame("Puerto Rico");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		createMainPanel(frame);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	/* ********************************************************************************************** */
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.startsWith("chooseCrop")) {
			int idx = Integer.parseInt(cmd.substring(11));
			List<Crop> settlerCrops = GameHelper.getSettlerCropSupply();
			String name = settlerCrops.get(idx).getName();
			displayMessage("Crop " + idx + "(" + name + ") was choosen");
		} else if (cmd.startsWith("chooseRole")) {
			displayMessage("Role " + cmd.substring(11) + " was choosen");
			GameHelper.selectRoleForPlayer(cmd.substring(11));
			updateAll();
		} else if (cmd.startsWith("chooseBuilding")) {
			displayMessage("Building " + cmd.substring(15) + " was choosen");
		} else if (cmd.equals("disable")) {
			// b2.setEnabled(false);
		} else {
			// b2.setEnabled(true);
		}
	}

	/* ********************************************************************************************** */
	private JLabel createLabel(JPanel panel, String label) {
		JLabel jlabel = new JLabel(label);
		panel.add(jlabel);
		return jlabel;
	}

	/* ********************************************************************************************** */
	private JLabel createLabelPair(JPanel panel, String label) {
		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.X_AXIS));
		labelPanel.add(new JLabel(label));
		labelPanel.add(new JLabel(" "));
		JLabel jlabel = new JLabel("0");
		labelPanel.add(jlabel);
		panel.add(labelPanel);
		return jlabel;
	}

	/* ********************************************************************************************** */
	private JButton createButton(JPanel panel, String label, String action) {
		JButton button = new JButton(label);
		button.setVerticalTextPosition(AbstractButton.CENTER);
		button.setHorizontalTextPosition(AbstractButton.LEADING); // aka LEFT, for left-to-right locales
		button.setActionCommand(action);
		button.addActionListener(this);
		panel.add(button);
		return button;
	}

	/* ********************************************************************************************** */
	private JButton createButton(JPanel panel, String label) {
		return createButton(panel, label, label);
	}

	/* ********************************************************************************************** */
	private void displayMessage(String message) {
		messageLine.setText(message);
	}

	/* ********************************************************************************************** */
	private void updateAll() {
		updateRoles();
		updateSupplies();
		updateGoods();
		updateSettlerCrops();
		updateBuildings();
		updateColonistShip();
		updateCargoShips();
		updateTradingHouse();
		updatePlayers();

	}

	/* ********************************************************************************************** */
	private void updateRoles() {
		boolean setEnabled = !GameHelper.hasRoleBeenSelected();
		List<Role> roleList = GameHelper.getRoles();
		for (int i = 0; i < GameHelper.getNumberOfRoles(); i++) {
			Role role = i < roleList.size() ? roleList.get(i) : null;
			roles[i].setVisible(i < roleList.size());
			roles[i].setEnabled(setEnabled);
			if (role != null) {
				roles[i].setText(role.getName() + (role.getBonusCoins() > 0 ? "(" + role.getBonusCoins() + ")" : ""));
			}
		}
	}

	/* ********************************************************************************************** */
	private void updateSupplies() {

		// Supply Fields
		// Display the Number of Main Supplies Remaining
		int supplyCoins = GameHelper.getCoinSupply();
		supplyCoinsLabel.setText(Integer.toString(supplyCoins));

		int supplyPoints = GameHelper.getPointSupply();
		supplyPointsLabel.setText(Integer.toString(supplyPoints));

		int supplySettlers = GameHelper.getSettlerSupply();
		supplySettlersLabel.setText(Integer.toString(supplySettlers));

		List<Quarry> supplyGoodsQuarryList = GameHelper.getQuarrySupply();
		supplyGoodsQuarryLabel.setText(Integer.toString(supplyGoodsQuarryList.size()));
	}

	/* ********************************************************************************************** */
	private void updateGoods() {

		// Display the number Goods Remaining
		int supplyGoodsCoffee = GameHelper.getGoodSupply(Coffee.NAME);
		supplyGoodsCoffeeLabel.setText(Integer.toString(supplyGoodsCoffee));

		int supplyGoodsCorn = GameHelper.getGoodSupply(Corn.NAME);
		supplyGoodsCornLabel.setText(Integer.toString(supplyGoodsCorn));

		int supplyGoodsIndigo = GameHelper.getGoodSupply(Indigo.NAME);
		supplyGoodsIndigoLabel.setText(Integer.toString(supplyGoodsIndigo));

		int supplyGoodsSugar = GameHelper.getGoodSupply(Sugar.NAME);
		supplyGoodsSugarLabel.setText(Integer.toString(supplyGoodsSugar));

		int supplyGoodsTobacco = GameHelper.getGoodSupply(Tobacco.NAME);
		supplyGoodsTobaccoLabel.setText(Integer.toString(supplyGoodsTobacco));
	}

	/* ********************************************************************************************** */
	private void updateBuildings() {

		Role role = GameHelper.getCurrentRole();
		boolean enabled = false;
		List<String> affordableBuildings = null;
		if (role != null && role.getName().equals(Builder.NAME)) {
			enabled = true;
			affordableBuildings = ((Builder)role).buildingsPlayerCanBuild();
		}

		// Get a set of the Building entries
		int idx = 0;
		// Display Building elements
		for (String buildingName : Building.BUILDING_LIST) {
			int cnt = GameHelper.getBuildingSupplyCount(buildingName);
			String label = buildingName + " (" + cnt + ")";
			buildings[idx].setText(label);
			buildings[idx].setEnabled(enabled && affordableBuildings.contains(buildingName));
			idx++;
		}
		buildingsPanel.setEnabled(enabled);
	}

	/* ********************************************************************************************** */
	private void updateSettlerCrops() {

		Role role = GameHelper.getCurrentRole();
		boolean enabled = false;
		if (role != null && role.getName().equals(Settler.NAME)) {
			enabled = true;
		}

		List<Crop> settlerCropSupply = GameHelper.getSettlerCropSupply();
		for (int i = 0; i < settlerCropSupply.size(); i++) {
			String name = settlerCropSupply.get(i).getName();
			settlerCrops[i].setText(name);
			settlerCrops[i].setEnabled(enabled);
		}
		settlerCropsPanel.setEnabled(enabled);
	}

	/* ********************************************************************************************** */
	private void updateColonistShip() {
		Role role = GameHelper.getCurrentRole();
		boolean enabled = false;
		if (role != null && role.getName().equals(Mayor.NAME)) {
			enabled = true;
		}
		// :TODO: Need to add method to get number of colonist on the colonist ship
		colonistShip.setText("Coloinist " + GameHelper.getNumberOfPlayers());
		colonistShip.setEnabled(enabled);
		colonistShipPanel.setEnabled(enabled);
	}

	/* ********************************************************************************************** */
	private void updateCargoShips() {
		Role role = GameHelper.getCurrentRole();
		boolean enabled = false;
		if (role != null && role.getName().equals(Captain.NAME)) {
			enabled = true;
		}
		// :TODO: Need to add method to get cargo for each cargo ship
		String cargoShipsLabel = "\u25A1 \u25A1 \u25A1 \u25A1 \u25A1";
		for (int i=0; i < 3; i++) {
			cargoShipsLabel += " \u25A1";
			cargoShips[i].setText(cargoShipsLabel);
			cargoShips[i].setEnabled(enabled);
		}
		cargoShipsPanel.setEnabled(enabled);
	}

	/* ********************************************************************************************** */
	private void updateTradingHouse() {
		Role role = GameHelper.getCurrentRole();
		boolean enabled = false;
		if (role != null && role.getName().equals(Trader.NAME)) {
			enabled = true;
		}
		// :TODO: Need to add method to get list of goods in the trader house
		// String tradingHouseLabel = "\u25A1 \u25A1 \u25A1 \u25A1       ";
		// String text = "<font color=#FFCC00>Corn=0</font> " + 
		//               "<font color='blue'>Indigo=1 </font>" +
		//               "<font color=#B2B28F>Sugar=2 </font>" +
		//               "<font color=#5C1F00>Tobacco=3 </font>" +
		//               "<font color='black'>Indigo=4 </font>";
		// tradingHouse.setText("<html>" + tradingHouseLabel + text + "</html>");
		String tradingHouseLabel = "\u25A1 \u25A1 \u25A1 \u25A1       Corn=0 Indigo=1 Sugar=2 Tobacco=3 Coffee=4";
		tradingHouse.setText(tradingHouseLabel);
		tradingHouse.setEnabled(enabled);
		tradingHousePanel.setEnabled(enabled);
	}

	/* ********************************************************************************************** */
	private void updatePlayers() {

		List<Player> players = GameHelper.getPlayers();
		int numPlayers = players.size();
		for (int i = 0; i < numPlayers; i++) {

			Player player = players.get(i);
			String name = player.getName();

			if (name == null) {
				name = "Player " + (i + 1);
			}

			// White King \u2654 or Black King \u265A or Army Star \u272F or Outline Star \u2729
			tabbedPlayerPane.setTitleAt(i, name + (player.isGovernor() ? " \u2736" : "") + (player.isTurn() ? " \u21D0" : ""));

			// Display the Number of Main Supplies Remaining
			int supplyCoins = player.getCoins();
			playerCoinsLabel[i].setText(Integer.toString(supplyCoins));

			int supplyPoints = player.getPoints();
			playerPointsLabel[i].setText(Integer.toString(supplyPoints));

			int supplySettlers = player.getSettlers();
			playerSettlersLabel[i].setText(Integer.toString(supplySettlers));

			// List<Quarry> supplyGoodsQuarryList = GameHelper.getBank().getQuarrySupply();
			// playerGoodsQuarryLabel[i].setText(Integer.toString(supplyGoodsQuarryList.size()));

			// Display the Buildings owned by this Player
			List<Building> ownedBuildings = player.getBuildings();
			for (int j = 0; j < playerBuildingsMax; j++) {
				if (j < ownedBuildings.size() && ownedBuildings.get(j) != null) {
					Building building = ownedBuildings.get(j);
					name = building.getName();
					for (int k = 0; i < building.getSettlerSlots(); i++) {
						name += building.getSettlers() >= k ? "\u25A0" : "\u25A1";
					}
					playerBuildings[i][j].setText(name);
				} else {
					playerBuildings[i][j].setText("\u25Ac");
				}
			}

			// Display the Crops owned by this Player
			List<Crop> ownedCrops = player.getCrops();
			for (int j = 0; j < playerCropsMax; j++) {
				if (j < ownedCrops.size() && ownedCrops.get(j) != null) {
					Crop crop = ownedCrops.get(j);
					name = crop.getName() + " " + (crop.isSettled() ? "\u25A0" : "\u25A1");
					playerCrops[i][j].setText(name);
				} else {
					playerCrops[i][j].setText("\u25Ac");
				}
			}
		}
	}

	/* ********************************************************************************************** */
	// private void createPlayerPanel(JPanel panel, int idx) {
	private void createPlayerPanel(JTabbedPane pane, int idx) {

		String name = "Player " + (idx + 1);

		JPanel playerPanel = new JPanel();
		// playerPanel.setBorder(playerBorder);
		playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));

		JPanel subPanel = new JPanel();
		subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.X_AXIS));

		// Player Supplies
		TitledBorder supplyBorder = BorderFactory.createTitledBorder("Supplies:");
		supplyBorder.setTitleJustification(TitledBorder.LEFT);
		supplyBorder.setTitlePosition(TitledBorder.DEFAULT_POSITION);
		supplyBorder.setTitleColor(playerColors[idx]);

		JPanel playerSupplyPanel = new JPanel(new GridLayout(1, 0), false);
		playerSupplyPanel.setBorder(supplyBorder);

		playerCoinsLabel[idx] = createLabelPair(playerSupplyPanel, "Coins");
		playerPointsLabel[idx] = createLabelPair(playerSupplyPanel, "Points");
		playerSettlersLabel[idx] = createLabelPair(playerSupplyPanel, "Settlers");
		playerGoodsQuarryLabel[idx] = createLabelPair(playerSupplyPanel, Quarry.NAME);
		subPanel.add(playerSupplyPanel);

		// Player Goods
		TitledBorder goodsBorder = BorderFactory.createTitledBorder("Goods:");
		goodsBorder.setTitleJustification(TitledBorder.LEFT);
		goodsBorder.setTitlePosition(TitledBorder.DEFAULT_POSITION);
		goodsBorder.setTitleColor(playerColors[idx]);

		JPanel playerGoodsPanel = new JPanel(new GridLayout(1, 0), false);
		playerGoodsPanel.setBorder(goodsBorder);

		playerGoodsCoffeeLabel[idx] = createLabelPair(playerGoodsPanel, Coffee.NAME);
		playerGoodsCornLabel[idx] = createLabelPair(playerGoodsPanel, Corn.NAME);
		playerGoodsIndigoLabel[idx] = createLabelPair(playerGoodsPanel, Indigo.NAME);
		playerGoodsSugarLabel[idx] = createLabelPair(playerGoodsPanel, Sugar.NAME);
		playerGoodsTobaccoLabel[idx] = createLabelPair(playerGoodsPanel, Tobacco.NAME);
		subPanel.add(playerGoodsPanel);

		playerPanel.add(subPanel);

		// Player Buildings
		TitledBorder playerBuildingsBorder = BorderFactory.createTitledBorder("Buildings:");
		playerBuildingsBorder.setTitleJustification(TitledBorder.LEFT);
		playerBuildingsBorder.setTitlePosition(TitledBorder.DEFAULT_POSITION);
		playerBuildingsBorder.setTitleColor(playerColors[idx]);

		// Buildings grid is 4 columns
		JPanel playerBuildingsPanel = new JPanel(new GridLayout(0, 4), false);
		playerBuildingsPanel.setBorder(playerBuildingsBorder);

		// List<Building> playerBuildingsList = GameHelper.getBank().getBuildings();
		for (int i = 0; i < playerBuildingsMax; i++) {
			playerBuildings[idx][i] = createButton(playerBuildingsPanel, "", "choosePlayerBuilding " + i);
		}
		playerPanel.add(playerBuildingsPanel);

		// Player Crops
		TitledBorder playerCropsBorder = BorderFactory.createTitledBorder("Crops:");
		playerCropsBorder.setTitleJustification(TitledBorder.LEFT);
		playerCropsBorder.setTitlePosition(TitledBorder.DEFAULT_POSITION);
		playerCropsBorder.setTitleColor(playerColors[idx]);

		// Crops grid is 4 columns
		JPanel playerCropsPanel = new JPanel(new GridLayout(0, 4), false);
		playerCropsPanel.setBorder(playerCropsBorder);

		for (int i = 0; i < playerCropsMax; i++) {
			playerCrops[idx][i] = createButton(playerCropsPanel, "", "choosePlayerCrop " + i);
		}
		playerPanel.add(playerCropsPanel);

		pane.addTab(name, null, playerPanel, null);
		pane.setForegroundAt(idx, playerColors[idx]);
		// panel.add(playerPanel);
	}

	/* ********************************************************************************************** */
	private void createMainPanel(JFrame frame) {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		// Put Supplies and Goods in a Subpanel
		JPanel subPanel = new JPanel();
		subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.X_AXIS));

		// Supplies
		TitledBorder supplyBorder = BorderFactory.createTitledBorder("Supplies:");
		supplyBorder.setTitleJustification(TitledBorder.LEFT);
		supplyBorder.setTitlePosition(TitledBorder.DEFAULT_POSITION);

		// Supplies grid is 1 row
		JPanel supplyPanel = new JPanel(new GridLayout(1, 0), false);
		supplyPanel.setBorder(supplyBorder);

		supplyCoinsLabel = createLabelPair(supplyPanel, "Coins");
		supplyPointsLabel = createLabelPair(supplyPanel, "Points");
		supplySettlersLabel = createLabelPair(supplyPanel, "Settlers");
		supplyGoodsQuarryLabel = createLabelPair(supplyPanel, Quarry.NAME);
		subPanel.add(supplyPanel);

		// Goods
		TitledBorder goodsBorder = BorderFactory.createTitledBorder("Goods:");
		goodsBorder.setTitleJustification(TitledBorder.LEFT);
		goodsBorder.setTitlePosition(TitledBorder.DEFAULT_POSITION);

		// Goods grid is 1 row
		JPanel goodsPanel = new JPanel(new GridLayout(1, 0), false);
		goodsPanel.setBorder(goodsBorder);

		supplyGoodsCoffeeLabel = createLabelPair(goodsPanel, Coffee.NAME);
		supplyGoodsCornLabel = createLabelPair(goodsPanel, Corn.NAME);
		supplyGoodsIndigoLabel = createLabelPair(goodsPanel, Indigo.NAME);
		supplyGoodsSugarLabel = createLabelPair(goodsPanel, Sugar.NAME);
		supplyGoodsTobaccoLabel = createLabelPair(goodsPanel, Tobacco.NAME);
		subPanel.add(goodsPanel);

		mainPanel.add(subPanel);

		// Roles
		TitledBorder rolesBorder = BorderFactory.createTitledBorder("Roles:");
		rolesBorder.setTitleJustification(TitledBorder.LEFT);
		rolesBorder.setTitlePosition(TitledBorder.DEFAULT_POSITION);

		// Roles grid is 1 row
		JPanel rolesPanel = new JPanel(new GridLayout(1, 0), false);
		rolesPanel.setBorder(rolesBorder);

		List<Role> rolesList = GameHelper.getBank().getRoles();
		int numRoles = GameHelper.getNumberOfRoles();
		for (int i = 0; i < numRoles; i++) {
			String name = rolesList.get(i).getName();
			roles[i] = createButton(rolesPanel, name, "chooseRole " + name);
		}
		mainPanel.add(rolesPanel);

		// Crops for players to pick from
		TitledBorder cropsBorder = BorderFactory.createTitledBorder("Crops:");
		cropsBorder.setTitleJustification(TitledBorder.LEFT);
		cropsBorder.setTitlePosition(TitledBorder.DEFAULT_POSITION);

		// Crops grid is 1 row
		settlerCropsPanel = new JPanel(new GridLayout(1, 0), false);
		settlerCropsPanel.setBorder(cropsBorder);

		// Crops to pick for during the Settler role
		List<Crop> settlerCropSupply = GameHelper.getSettlerCropSupply();
		for (int i = 0; i < settlerCropSupply.size(); i++) {
			String name = settlerCropSupply.get(i).getName();
			settlerCrops[i] = createButton(settlerCropsPanel, name, "chooseCrop " + i);
		}
		mainPanel.add(settlerCropsPanel);

		// Buildings
		TitledBorder buildingsBorder = BorderFactory.createTitledBorder("Buildings:");
		buildingsBorder.setTitleJustification(TitledBorder.LEFT);
		buildingsBorder.setTitlePosition(TitledBorder.DEFAULT_POSITION);

		// Buildings grid is 8 columns
		buildingsPanel = new JPanel(new GridLayout(0, 8), false);
		buildingsPanel.setBorder(buildingsBorder);

		// Get a set of the Building entries
		int idx = 0;
		// Display Building elements
		for (String buildingName : Building.BUILDING_LIST) {
			int cnt = GameHelper.getBuildingSupplyCount(buildingName);
			String label = buildingName + " (" + cnt + ")";
			buildings[idx] = createButton(buildingsPanel, label, "chooseBuilding " + buildingName);
			idx++;
		}
		mainPanel.add(buildingsPanel);

		// Put Cargo Ships and the Trading House in a Subpanel
		subPanel = new JPanel();
		subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.X_AXIS));

		// Colonist Ship
		TitledBorder colonistShipBorder = BorderFactory.createTitledBorder("Colonist Ship:");
		colonistShipBorder.setTitleJustification(TitledBorder.LEFT);
		colonistShipBorder.setTitlePosition(TitledBorder.DEFAULT_POSITION);

		// Colonist Ship grid is 1 row
		colonistShipPanel = new JPanel(new GridLayout(1, 0), false);
		colonistShipPanel.setBorder(colonistShipBorder);
		// Display Colonist Ship elements
		colonistShip = createLabel(colonistShipPanel, "Coloinist ");
		subPanel.add(colonistShipPanel);

		// Cargo Ships
		TitledBorder cargoShipsBorder = BorderFactory.createTitledBorder("Cargo Ships:");
		cargoShipsBorder.setTitleJustification(TitledBorder.LEFT);
		cargoShipsBorder.setTitlePosition(TitledBorder.DEFAULT_POSITION);

		// Cargo Ships grid is 1 row
		cargoShipsPanel = new JPanel(new GridLayout(1, 0), false);
		cargoShipsPanel.setBorder(cargoShipsBorder);
		// Display Cargo Ship elements
		String cargoShipsLabel = "\u25A1 \u25A1 \u25A1 \u25A1 \u25A1";
		for (int i=0; i < 3; i++) {
			cargoShipsLabel += " \u25A1";
			cargoShips[i] = createButton(cargoShipsPanel, cargoShipsLabel, "chooseCargoShip " + i);
		}
		subPanel.add(cargoShipsPanel);

		// Trading House
		TitledBorder tradingHouseBorder = BorderFactory.createTitledBorder("Trading House:");
		tradingHouseBorder.setTitleJustification(TitledBorder.LEFT);
		tradingHouseBorder.setTitlePosition(TitledBorder.DEFAULT_POSITION);

		// Trading House grid is 1 row
		tradingHousePanel = new JPanel(new GridLayout(1, 0), false);
		tradingHousePanel.setBorder(tradingHouseBorder);
		// Display Trading House elements
		String tradingHouseLabel = "\u25A1 \u25A1 \u25A1 \u25A1       Corn=0 Indigo=1 Sugar=2 Tobacco=3 Coffee=4";
		tradingHouse = createLabel(tradingHousePanel, tradingHouseLabel);
		subPanel.add(tradingHousePanel);

		mainPanel.add(subPanel);

		// Players
		tabbedPlayerPane = new JTabbedPane();
		int numPlayers = GameHelper.getNumberOfPlayers();
		for (int i = 0; i < numPlayers; i++) {
			createPlayerPanel(tabbedPlayerPane, i);
		}

		tabbedPlayerPane.setSelectedIndex(0);
		// String toolTip = new
		// String("<html>Blue Wavy Line border art crew:<br>&nbsp;&nbsp;&nbsp;Bill Pauley<br>&nbsp;&nbsp;&nbsp;Cris St. Aubyn<br>&nbsp;&nbsp;&nbsp;Ben Wronsky<br>&nbsp;&nbsp;&nbsp;Nathan Walrath<br>&nbsp;&nbsp;&nbsp;Tommy Adams, special consultant</html>");
		// tabbedPlayerPane.setToolTipTextAt(1, toolTip);
		mainPanel.add(tabbedPlayerPane);

		// Message Area
		Border messageBorder = BorderFactory.createLineBorder(Color.GRAY);

		JPanel messagePanel = new JPanel(new GridLayout(1, 0), false);
		messagePanel.setBorder(messageBorder);

		messageLine = createLabel(messagePanel, "...");
		mainPanel.add(messagePanel);

		updateAll();

		frame.getContentPane().add(mainPanel);
	}

	/* ********************************************************************************************** */
	public static void main(String[] arg) {
		// Tim LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		// Tim new LwjglApplication(new PuertoRico(), config);
		List<Player> players = new ArrayList<Player>();
		for (int i = 0; i < 4; i++) {
			players.add(new Player("Player " + (i + 1)));
		}
		new Game(players);

		new DesktopLauncher();
	}
}
