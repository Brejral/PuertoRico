package com.brejral.puertorico.desktop;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.brejral.puertorico.game.role.Role;
import com.brejral.puertorico.game.role.Settler;

// Tim import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
// Tim import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher implements ActionListener {
	
    /* ********************************************************************************************** */
	// Global GUI items
	// :TODO: Change static variables to private or protected I think
    JLabel supplyCoinsLabel;
    JLabel supplyPointsLabel;
    JLabel supplySettlersLabel;

    JLabel supplyGoodsCoffeeLabel ;
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
    
    JLabel[] playerCoinsLabel = new JLabel[5];
    JLabel[] playerPointsLabel = new JLabel[5];
    JLabel[] playerSettlersLabel = new JLabel[5];

    JLabel[] playerGoodsCoffeeLabel  = new JLabel[5];
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
    
    Color[] playerColors = {Color.RED, new Color(0, 160, 0), Color.BLUE, Color.MAGENTA, new Color(0, 160, 160) };

    /* ********************************************************************************************** */
    /**
     * Main Constructor
     * 
     * Create the GUI and show it.  
     */
    public DesktopLauncher() {
        //Create and set up the window.
        JFrame frame = new JFrame("Puerto Rico");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        createMainPanel(frame);
 
        //Display the window.
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
	    button.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
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
        updateSupplies();
        updateGoods();
        updateSettlerCrops();
        updateBuildings();
        updatePlayers();
        
        String governorName = GameHelper.getCurrentPlayerForRound().getName();
        displayMessage(governorName + " choose a Role");
	}

    /* ********************************************************************************************** */
    private void updateSupplies() {

    	// Supply Fields
    	// Display the Number of Main Supplies Remaining
        int supplyCoins = GameHelper.getBank().getCoinSupply();
        supplyCoinsLabel.setText(Integer.toString(supplyCoins));

        int supplyPoints = GameHelper.getBank().getPointSupply();
        supplyPointsLabel.setText(Integer.toString(supplyPoints));

        int supplySettlers = GameHelper.getBank().getSettlerSupply();
        supplySettlersLabel.setText(Integer.toString(supplySettlers));

		List<Quarry> supplyGoodsQuarryList = GameHelper.getBank().getQuarrySupply();
        supplyGoodsQuarryLabel.setText(Integer.toString(supplyGoodsQuarryList.size()));
	}

    /* ********************************************************************************************** */
    private void updateGoods() {

        // Display the number Goods Remaining
        int supplyGoodsCoffee = GameHelper.getBank().getGoodSupply().get(Coffee.NAME);
        supplyGoodsCoffeeLabel.setText(Integer.toString(supplyGoodsCoffee));

        int supplyGoodsCorn = GameHelper.getBank().getGoodSupply().get(Corn.NAME);
        supplyGoodsCornLabel.setText(Integer.toString(supplyGoodsCorn));

        int supplyGoodsIndigo = GameHelper.getBank().getGoodSupply().get(Indigo.NAME);
        supplyGoodsIndigoLabel.setText(Integer.toString(supplyGoodsIndigo));

        int supplyGoodsSugar = GameHelper.getBank().getGoodSupply().get(Sugar.NAME);
        supplyGoodsSugarLabel.setText(Integer.toString(supplyGoodsSugar));

        int supplyGoodsTobacco = GameHelper.getBank().getGoodSupply().get(Tobacco.NAME);
        supplyGoodsTobaccoLabel.setText(Integer.toString(supplyGoodsTobacco));
	}

    /* ********************************************************************************************** */
    private void updateBuildings() {
        
		Role role = GameHelper.getCurrentRole();
		boolean enabled = false;
        if (role != null && role.getName().equals(Builder.NAME)) {
        	enabled = true;
        }

        // Get a set of the Building entries
        Set set = GameHelper.getBank().getBuildingSupply().entrySet();
        Iterator itr = set.iterator();
        int idx=0;
        // Display Building elements
        while (itr.hasNext()) {
           Map.Entry me = (Map.Entry)itr.next();
           String name = me.getKey().toString();
           int cnt = (Integer)me.getValue();
           String label = name + " (" + cnt + ")";
           buildings[idx].setText(label);
           buildings[idx].setEnabled(enabled && cnt > 0);
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
		for (int i=0; i<settlerCropSupply.size(); i++) {
			String name = settlerCropSupply.get(i).getName();
			settlerCrops[i].setText(name);
            settlerCrops[i].setEnabled(enabled);
		}
        settlerCropsPanel.setEnabled(enabled);
    }

    /* ********************************************************************************************** */
    private void updatePlayers() {

		List<Player> players = GameHelper.getPlayers();
		int numPlayers = players.size();
		for (int i=0; i<numPlayers; i++) {

			Player player = players.get(i);
			String name = player.getName();
			
			if (name == null) {
				name = "Player " + (i+1);
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
            for (int j=0; j<playerBuildingsMax; j++) {
            	if (j < ownedBuildings.size()) {
            		Building building = ownedBuildings.get(j);
            		name = building.getName() + (building.isActive() ? "\u25A0" : "\u25A1");
            		playerBuildings[i][j].setText(name);
            	} else {
            		playerBuildings[i][j].setText("\u25Ac");
            	}
            }
            
            // Display the Crops owned by this Player
            List<Crop> ownedCrops = player.getCrops();
            for (int j=0; j<playerCropsMax; j++) {
            	if (j < ownedCrops.size()) {
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

        String name = "Player " + (idx+1);

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

        playerCoinsLabel[idx]       = createLabelPair(playerSupplyPanel, "Coins");
        playerPointsLabel[idx]      = createLabelPair(playerSupplyPanel, "Points");
        playerSettlersLabel[idx]    = createLabelPair(playerSupplyPanel, "Settlers");
        playerGoodsQuarryLabel[idx] = createLabelPair(playerSupplyPanel, Quarry.NAME);
        subPanel.add(playerSupplyPanel);
        
        // Player Goods
		TitledBorder goodsBorder = BorderFactory.createTitledBorder("Goods:");
        goodsBorder.setTitleJustification(TitledBorder.LEFT);
        goodsBorder.setTitlePosition(TitledBorder.DEFAULT_POSITION);
        goodsBorder.setTitleColor(playerColors[idx]);

        JPanel playerGoodsPanel = new JPanel(new GridLayout(1, 0), false);
        playerGoodsPanel.setBorder(goodsBorder);
        
        playerGoodsCoffeeLabel[idx]  = createLabelPair(playerGoodsPanel, Coffee.NAME);
        playerGoodsCornLabel[idx]    = createLabelPair(playerGoodsPanel, Corn.NAME);
        playerGoodsIndigoLabel[idx]  = createLabelPair(playerGoodsPanel, Indigo.NAME);
        playerGoodsSugarLabel[idx]   = createLabelPair(playerGoodsPanel, Sugar.NAME);
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
		for (int i=0; i<playerBuildingsMax; i++) {
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
        
		for (int i=0; i<playerCropsMax; i++) {
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

		JPanel subPanel = new JPanel();
		subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.X_AXIS));

		// Supplies
		TitledBorder supplyBorder = BorderFactory.createTitledBorder("Supplies:");
        supplyBorder.setTitleJustification(TitledBorder.LEFT);
        supplyBorder.setTitlePosition(TitledBorder.DEFAULT_POSITION);

        // Supplies grid is 1 row
        JPanel supplyPanel = new JPanel(new GridLayout(1, 0), false);
        supplyPanel.setBorder(supplyBorder);

        supplyCoinsLabel        = createLabelPair(supplyPanel, "Coins");
        supplyPointsLabel       = createLabelPair(supplyPanel, "Points");
        supplySettlersLabel     = createLabelPair(supplyPanel, "Settlers");
        supplyGoodsQuarryLabel  = createLabelPair(supplyPanel, Quarry.NAME);
        subPanel.add(supplyPanel);
        
        // Goods
		TitledBorder goodsBorder = BorderFactory.createTitledBorder("Goods:");
        goodsBorder.setTitleJustification(TitledBorder.LEFT);
        goodsBorder.setTitlePosition(TitledBorder.DEFAULT_POSITION);

        // Goods grid is 1 row
        JPanel goodsPanel = new JPanel(new GridLayout(1, 0), false);
        goodsPanel.setBorder(goodsBorder);
        
        supplyGoodsCoffeeLabel  = createLabelPair(goodsPanel, Coffee.NAME);
        supplyGoodsCornLabel    = createLabelPair(goodsPanel, Corn.NAME);
        supplyGoodsIndigoLabel  = createLabelPair(goodsPanel, Indigo.NAME);
        supplyGoodsSugarLabel   = createLabelPair(goodsPanel, Sugar.NAME);
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
		int numRoles = rolesList.size();
		for (int i=0; i<numRoles; i++) {
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
		for (int i=0; i<settlerCropSupply.size(); i++) {
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
        Set set = GameHelper.getBank().getBuildingSupply().entrySet();
        Iterator itr = set.iterator();
        int idx=0;
        // Display Building elements
        while (itr.hasNext()) {
           Map.Entry me = (Map.Entry)itr.next();
           String name = me.getKey().toString();
           int cnt = (Integer)me.getValue();
           String label = name + " (" + cnt + ")";
           buildings[idx] = createButton(buildingsPanel, label, "chooseBuilding " + name);
           idx++;
        }
        mainPanel.add(buildingsPanel);

        // Players
        tabbedPlayerPane = new JTabbedPane();
		int numPlayers = GameHelper.getNumberOfPlayers();
		for (int i=0; i<numPlayers; i++) {
            createPlayerPanel(tabbedPlayerPane, i);
        }
		 
        tabbedPlayerPane.setSelectedIndex(0);
        // String toolTip = new String("<html>Blue Wavy Line border art crew:<br>&nbsp;&nbsp;&nbsp;Bill Pauley<br>&nbsp;&nbsp;&nbsp;Cris St. Aubyn<br>&nbsp;&nbsp;&nbsp;Ben Wronsky<br>&nbsp;&nbsp;&nbsp;Nathan Walrath<br>&nbsp;&nbsp;&nbsp;Tommy Adams, special consultant</html>");
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
    public static void main (String[] arg) {
		// Tim LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		// Tim new LwjglApplication(new PuertoRico(), config);
    	List<Player> players = new ArrayList<Player>();
    	for (int i=0; i<4; i++)
    	{
    		players.add(new Player("Player " + (i+1)));
    	}
    	new Game(players);
    	
    	new DesktopLauncher();
	}
}
