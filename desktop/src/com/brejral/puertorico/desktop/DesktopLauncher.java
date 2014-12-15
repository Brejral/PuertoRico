package com.brejral.puertorico.desktop;

import java.awt.Color;
import java.awt.GridLayout;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import com.brejral.puertorico.game.Game;
import com.brejral.puertorico.game.GameHelper;
import com.brejral.puertorico.game.building.Building;
import com.brejral.puertorico.game.building.Wharf;
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
import com.brejral.puertorico.game.role.Craftsman;
import com.brejral.puertorico.game.role.Mayor;
import com.brejral.puertorico.game.role.Role;
import com.brejral.puertorico.game.role.Settler;
import com.brejral.puertorico.game.role.Trader;
import com.brejral.puertorico.game.ship.Ship;

// Tim import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
// Tim import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher implements ActionListener {

	/* ********************************************************************************************** */
	// Global GUI items
	JFrame mainFrame;
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
	JButton[] settlerCrops = new JButton[7];

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

	JButton[] playerGoodsCoffeeButton = new JButton[5];
	JButton[] playerGoodsCornButton = new JButton[5];
	JButton[] playerGoodsIndigoButton = new JButton[5];
	JButton[] playerGoodsSugarButton = new JButton[5];
	JButton[] playerGoodsTobaccoButton = new JButton[5];
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
		mainFrame = new JFrame("Puerto Rico");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		createMainPanel(mainFrame);

		// Display the window.
		mainFrame.pack();
		mainFrame.setVisible(true);
	}

	/* ********************************************************************************************** */
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.startsWith("chooseCrop")) {
			int idx = Integer.parseInt(cmd.substring(11));
			List<Crop> settlerCrops = GameHelper.getSettlerCropSupply();
			Crop crop = settlerCrops.get(idx);
			displayMessage("Crop " + idx + "(" + crop.getName() + ") was chosen");
			((Settler) GameHelper.getCurrentRole()).onCropSelect(crop);
		} else if (cmd.startsWith("chooseRole")) {
			displayMessage("Role " + cmd.substring(11) + " was chosen");
			if (cmd.substring(11).equals(Mayor.NAME) && GameHelper.hasRoleBeenSelected()) {
				((Mayor) GameHelper.getCurrentRole()).onAction();
			} else if (cmd.substring(11).equals(Trader.NAME) && GameHelper.hasRoleBeenSelected()) {
				((Trader) GameHelper.getCurrentRole()).tradeGood("None");
			} else if (cmd.substring(11).equals(Builder.NAME) && GameHelper.hasRoleBeenSelected()) {
				((Builder) GameHelper.getCurrentRole()).advanceAction();
			} else {
				GameHelper.selectRoleForPlayer(cmd.substring(11));
			}
		} else if (cmd.startsWith("chooseBuilding")) {
			displayMessage("Building " + cmd.substring(15) + " was chosen");
			if (GameHelper.isRole(Captain.NAME)) {
				((Captain) GameHelper.getCurrentRole()).selectShip(-1);
			} else {
				((Builder) GameHelper.getCurrentRole()).onBuildingSelect(cmd.substring(15));
			}
		} else if (cmd.startsWith("choosePlayerBuilding")) {
			displayMessage("Player Building " + cmd.substring(21) + " was chosen");
			if (GameHelper.isRole(Builder.NAME)) {
				((Builder) GameHelper.getCurrentRole()).onLocationSelect(Integer.parseInt(cmd.substring(21)));
			} else if (GameHelper.isRole(Mayor.NAME)) {
				((Mayor) GameHelper.getCurrentRole()).addRemoveSettler(true, Integer.parseInt(cmd.substring(21)));
			}
		} else if (cmd.startsWith("chooseCargoShip")) {
			displayMessage("Cargo Ship " + cmd.substring(16) + " was chosen");
			((Captain) GameHelper.getCurrentRole()).selectShip(Integer.parseInt(cmd.substring(16)));
		} else if (cmd.startsWith("choosePlayerCrop")) {
			displayMessage("Player Crop " + cmd.substring(17) + " was chosen");
			if (GameHelper.isRole(Mayor.NAME)) {
				((Mayor) GameHelper.getCurrentRole()).addRemoveSettler(false, Integer.parseInt(cmd.substring(17)));
			}
		} else if (cmd.startsWith("choosePlayerGood")) {
			displayMessage("Player Good " + cmd.substring(17) + " was chosen");
			if (GameHelper.isRole(Trader.NAME)) {
				((Trader) GameHelper.getCurrentRole()).tradeGood(cmd.substring(17));
			} else if (GameHelper.isRole(Captain.NAME)) {
				((Captain) GameHelper.getCurrentRole()).selectGood(cmd.substring(17));
			} else if (GameHelper.isRole(Craftsman.NAME)) {
				((Craftsman) GameHelper.getCurrentRole()).onAction(cmd.substring(17));
			}
		} else if (cmd.startsWith("chooseQuarry")) {
			displayMessage("Quarry was chosen");
			((Settler) GameHelper.getCurrentRole()).onCropSelect(new Quarry());
		} else if (cmd.equals("disable")) {
			// b2.setEnabled(false);
		} else {
			// b2.setEnabled(true);
		}
		updateAll();
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
	private int showGoodSelectionWindow(List<String> cropOptions, String message) {
		return JOptionPane.showOptionDialog(mainFrame, message, "Select Good", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, cropOptions.toArray(), null);
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
		// checkForGoodSelection();
	}

	/* ********************************************************************************************** */
	private void updateRoles() {
		boolean setEnabled = !GameHelper.hasRoleBeenSelected();
		List<String> roleList = GameHelper.getRoleNames();
		for (int i = 0; i < roleList.size(); i++) {
			String roleName = roleList.get(i);
			Role role = GameHelper.getRole(roleName);
			roles[i].setEnabled(setEnabled && GameHelper.isRoleAvailable(roleName));
			roles[i].setText(roleName + (role != null && role.getBonusCoins() > 0 ? " (" + role.getBonusCoins() + ")" : ""));
			if (GameHelper.isRole(roleName)) {
				roles[i].setBackground(Color.GREEN);
				if (roleName.equals(Mayor.NAME) || roleName.equals(Trader.NAME) || (roleName.equals(Builder.NAME) && !((Builder)GameHelper.getCurrentRole()).hasSelectedBuilding())) {
					roles[i].setEnabled(true);
				}
			} else {
				roles[i].setBackground(null);
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
			enabled = !((Builder) role).hasSelectedBuilding();
			affordableBuildings = ((Builder) role).buildingsPlayerCanBuild();
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
		for (int i = 0; i < GameHelper.getNumberOfSettlerCropSupply(); i++) {
			String name = i < settlerCropSupply.size() ? settlerCropSupply.get(i).getName() : "\u25Ac";
			settlerCrops[i].setText(name);
			settlerCrops[i].setEnabled(enabled && !name.equals("\u25Ac"));
		}
		settlerCrops[settlerCropSupply.size()].setEnabled(enabled && ((Settler) role).canChooseQuarry(GameHelper.getCurrentPlayerForAction()));
		settlerCropsPanel.setEnabled(enabled);
	}

	/* ********************************************************************************************** */
	private void updateColonistShip() {
		colonistShip.setText("Coloinist " + GameHelper.getSettlerShip().getSettlers());
	}

	/* ********************************************************************************************** */
	private void updateCargoShips() {
		Role role = GameHelper.getCurrentRole();
		boolean enabled = false;
		List<Ship> enabledShips = null;
		if (role != null && role.getName().equals(Captain.NAME) && !((Captain) role).hasShipBeenSelected()) {
			enabled = true;
			enabledShips = ((Captain) role).getShipsPlayerCanSelect();
		}
		List<Ship> cargoShipList = GameHelper.getCargoShips();
		for (int i = 0; i < cargoShipList.size(); i++) {
			Ship ship = cargoShipList.get(i);
			String cargoShipsLabel = "";
			for (int j = 0; j < ship.getGoodCapacity(); j++) {
				if (j < ship.getGoods()) {
					cargoShipsLabel +=  "<font color=" + getColorForCrop(ship.getGoodName()) + "> \u25a0 </font>";
				} else {
					cargoShipsLabel += " \u25A1";
				}
			}
			cargoShips[i].setText("<html>" + cargoShipsLabel + "</html>");
			cargoShips[i].setEnabled(enabled && enabledShips.contains(ship));
		}
	}

	/* ********************************************************************************************** */
	private void updateTradingHouse() {
		String tradingHouseLabel = "";
		List<String> tradedGoods = GameHelper.getTradedGoods();
		for (int i = 0; i < 4; i++) {
			String goodName = i < tradedGoods.size() ? tradedGoods.get(i) : null;
			if (goodName != null) {
				tradingHouseLabel += "<font color=" + getColorForCrop(goodName) + "> \u25a0 </font>";
			} else {
				tradingHouseLabel += " \u25a1 ";
			}
		}
		tradingHouseLabel += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "<font color=yellow>Corn=0 </font>" + "<font color=blue>Indigo=1 </font>" + "<font color=#D2D2A0>Sugar=2 </font>" + "<font color=#C8AA82>Tobacco=3 </font>"
					+ "<font color=black>Coffee=4 </font>";
		tradingHouse.setText("<html>" + tradingHouseLabel + "</html>");

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

			if (player.isAction()) {
				tabbedPlayerPane.setSelectedIndex(i);
			}

			// White King \u2654 or Black King \u265A or Army Star \u272F or Outline Star \u2729
			tabbedPlayerPane.setTitleAt(i, name + (player.isGovernor() ? " \u2654" : "") + (player.isTurn() ? " \u2736" : "") + (player.isAction() ? " \u21D0" : ""));

			// Display the Number of Main Supplies Remaining
			int supplyCoins = player.getCoins();
			playerCoinsLabel[i].setText(Integer.toString(supplyCoins));

			int supplyPoints = player.getPoints();
			playerPointsLabel[i].setText(Integer.toString(supplyPoints));

			int supplySettlers = player.getSettlers();
			playerSettlersLabel[i].setText(Integer.toString(supplySettlers));

			boolean goodsEnabled = player.isAction() && GameHelper.isRole(Trader.NAME);
			List<String> goodsEnabledList = null;
			if (goodsEnabled) {
				if (GameHelper.isRole(Trader.NAME)) {
					goodsEnabledList = ((Trader) GameHelper.getCurrentRole()).getTradableGoodsForCurrentPlayer();
				} else if (GameHelper.isRole(Captain.NAME)) {
					goodsEnabledList = ((Captain) GameHelper.getCurrentRole()).getGoodsPlayerCanSelect();
				} else if (GameHelper.isRole(Craftsman.NAME)) {
					goodsEnabledList = ((Craftsman) GameHelper.getCurrentRole()).getGoodsPlayerCanProduceForBonus();
				}
			}
			playerGoodsCornButton[i].setText(Corn.NAME + " " + player.getNumberOfGoods(Corn.NAME));
			playerGoodsCornButton[i].setEnabled(goodsEnabled && goodsEnabledList.contains(Corn.NAME));
			playerGoodsIndigoButton[i].setText(Indigo.NAME + " " + player.getNumberOfGoods(Indigo.NAME));
			playerGoodsIndigoButton[i].setEnabled(goodsEnabled && goodsEnabledList.contains(Indigo.NAME));
			playerGoodsSugarButton[i].setText(Sugar.NAME + " " + player.getNumberOfGoods(Sugar.NAME));
			playerGoodsSugarButton[i].setEnabled(goodsEnabled && goodsEnabledList.contains(Sugar.NAME));
			playerGoodsTobaccoButton[i].setText(Tobacco.NAME + " " + player.getNumberOfGoods(Tobacco.NAME));
			playerGoodsTobaccoButton[i].setEnabled(goodsEnabled && goodsEnabledList.contains(Tobacco.NAME));
			playerGoodsCoffeeButton[i].setText(Coffee.NAME + " " + player.getNumberOfGoods(Coffee.NAME));
			playerGoodsCoffeeButton[i].setEnabled(goodsEnabled && goodsEnabledList.contains(Coffee.NAME));

			// List<Quarry> supplyGoodsQuarryList = GameHelper.getBank().getQuarrySupply();
			// playerGoodsQuarryLabel[i].setText(Integer.toString(supplyGoodsQuarryList.size()));

			// Display the Buildings owned by this Player
			List<Building> ownedBuildings = player.getBuildings();
			for (int j = 0; j < playerBuildingsMax; j++) {
				Building building = ownedBuildings.get(j);
				if (building != null) {
					name = building.getName();
					for (int k = 0; k < building.getSettlerSlots(); k++) {
						name += building.getSettlers() >= k + 1 ? " \u25A0" : " \u25A1";
					}
					playerBuildings[i][j].setText(name);
					playerBuildings[i][j].setEnabled(player.isAction() && (GameHelper.isRole(Mayor.NAME) && (building.getSettlers() > 0 || player.getSettlers() > 0))
								|| (GameHelper.isRole(Captain.NAME) && building.getName().equals(Wharf.NAME)));
				} else {
					playerBuildings[i][j].setText("\u25Ac");
					playerBuildings[i][j].setEnabled(player.isAction() && GameHelper.isRole(Builder.NAME) && ((Builder) GameHelper.getCurrentRole()).canPlayerBuildInLocation(j));
				}
			}

			// Display the Crops owned by this Player
			List<Crop> ownedCrops = player.getCrops();
			for (int j = 0; j < playerCropsMax; j++) {
				Crop crop = j < ownedCrops.size() && ownedCrops.get(j) != null ? ownedCrops.get(j) : null;
				boolean isEnabled = player.isAction() && GameHelper.isRole(Mayor.NAME) && crop != null && (crop.isSettled() || player.getSettlers() != 0);
				if (crop != null) {
					name = crop.getName() + " " + (crop.isSettled() ? "\u25A0" : "\u25A1");
					playerCrops[i][j].setText(name);
				} else {
					playerCrops[i][j].setText("\u25Ac");
				}
				playerCrops[i][j].setEnabled(isEnabled);
			}
		}
	}

	/* ********************************************************************************************** */
	// private void checkForGoodSelection() {
	// if (GameHelper.isRole(Trader.NAME)) {
	// Trader trader = ((Trader) GameHelper.getCurrentRole());
	// List<String> options = trader.getTradableGoodsForCurrentPlayer();
	// String selectedCrop = "";
	// if (options.size() > 0) {
	// options.add("None");
	// int selection = showGoodSelectionWindow(options, "Select a good to trade.");
	// selectedCrop = options.get(selection);
	// } else {
	// selectedCrop = "None";
	// }
	// trader.tradeGood(selectedCrop);
	// updateAll();
	// } else if (GameHelper.isRole(Captain.NAME)) {
	//
	// }
	// }

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

		playerGoodsCornButton[idx] = createButton(playerGoodsPanel, Corn.NAME + " 0", "choosePlayerGood " + Corn.NAME);
		playerGoodsIndigoButton[idx] = createButton(playerGoodsPanel, Indigo.NAME + " 0", "choosePlayerGood " + Indigo.NAME);
		playerGoodsSugarButton[idx] = createButton(playerGoodsPanel, Sugar.NAME + " 0", "choosePlayerGood " + Sugar.NAME);
		playerGoodsTobaccoButton[idx] = createButton(playerGoodsPanel, Tobacco.NAME + " 0", "choosePlayerGood " + Tobacco.NAME);
		playerGoodsCoffeeButton[idx] = createButton(playerGoodsPanel, Coffee.NAME + " 0", "choosePlayerGood " + Coffee.NAME);
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

		List<String> rolesList = GameHelper.getRoleNames();
		for (int i = 0; i < rolesList.size(); i++) {
			String name = rolesList.get(i);
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
		settlerCrops[settlerCropSupply.size()] = createButton(settlerCropsPanel, Quarry.NAME, "chooseQuarry ");
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
		List<Ship> cargoShipList = GameHelper.getCargoShips();
		for (int i = 0; i < cargoShipList.size(); i++) {
			String cargoShipsLabel = "\u25A1";
			for (int j = 1; j < cargoShipList.get(i).getGoodCapacity(); j++) {
				cargoShipsLabel += " \u25A1";
			}
			cargoShips[i] = createButton(cargoShipsPanel, cargoShipsLabel, "chooseCargoShip " + i);
		}
		subPanel.add(cargoShipsPanel);

		// Trading House
		TitledBorder tradingHouseBorder = BorderFactory.createTitledBorder("Trading House:");
		tradingHouseBorder.setTitleJustification(TitledBorder.LEFT);
		tradingHouseBorder.setTitlePosition(TitledBorder.DEFAULT_POSITION);

		// Trading House grid is 1 row
		tradingHousePanel = new JPanel(new GridLayout(1, 5), false);
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
	private String getColorForCrop(String cropName) {
		switch (cropName) {
		case Corn.NAME:
			return "yellow";
		case Indigo.NAME:
			return "blue";
		case Sugar.NAME:
			return "#D2D2A0";
		case Tobacco.NAME:
			return "#C8AA82";
		case Coffee.NAME:
			return "black";
		default:
			return null;
		}
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
