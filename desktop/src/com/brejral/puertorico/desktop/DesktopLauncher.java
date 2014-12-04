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
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import com.brejral.puertorico.game.Game;
import com.brejral.puertorico.game.GameHelper;
import com.brejral.puertorico.game.crop.Coffee;
import com.brejral.puertorico.game.crop.Corn;
import com.brejral.puertorico.game.crop.Crop;
import com.brejral.puertorico.game.crop.Indigo;
import com.brejral.puertorico.game.crop.Quarry;
import com.brejral.puertorico.game.crop.Sugar;
import com.brejral.puertorico.game.crop.Tobacco;
import com.brejral.puertorico.game.player.Player;
import com.brejral.puertorico.game.role.Role;

// Tim import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
// Tim import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher implements ActionListener {
	
    /* ********************************************************************************************** */
	// Global GUI items
    static JLabel supplyCoinsLabel;
    static JLabel supplyPointsLabel;
    static JLabel supplySettlersLabel;

    static JLabel supplyGoodsCoffeeLabel ;
    static JLabel supplyGoodsCornLabel;
    static JLabel supplyGoodsIndigoLabel;
    static JLabel supplyGoodsSugarLabel;
    static JLabel supplyGoodsTobaccoLabel;
    static JLabel supplyGoodsQuarryLabel;
    
    static JButton[] supplyCrops = new JButton[6];
    static JButton[] roles = new JButton[8];
    
    static JLabel[] playerCoinsLabel = new JLabel[5];
    static JLabel[] playerPointsLabel = new JLabel[5];
    static JLabel[] playerSettlersLabel = new JLabel[5];

    static JLabel[] playerGoodsCoffeeLabel  = new JLabel[5];
    static JLabel[] playerGoodsCornLabel = new JLabel[5];
    static JLabel[] playerGoodsIndigoLabel = new JLabel[5];
    static JLabel[] playerGoodsSugarLabel = new JLabel[5];
    static JLabel[] playerGoodsTobaccoLabel = new JLabel[5];
    static JLabel[] playerGoodsQuarryLabel = new JLabel[5];
    
    static JLabel messageLine;
    
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
    		displayMessage("Crop " + cmd.substring(11) + " was choosen");
    	} else if (cmd.startsWith("chooseRole")) {
    		displayMessage("Role " + cmd.substring(11) + " was choosen");
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
    private void updateAll()
    {
        updateSupplies();
        updateGoods();
        updatePlayers();
	}

    /* ********************************************************************************************** */
    private void updateSupplies()
    {
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
    private void updateGoods()
    {
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
    private void updatePlayers()
    {
		List<Player> players = GameHelper.getPlayers();
		int numPlayers = players.size();
		for (int i=0; i<numPlayers; i++)
		{
            // Display the Number of Main Supplies Remaining
            int supplyCoins = players.get(i).getCoins();
            playerCoinsLabel[i].setText(Integer.toString(supplyCoins));

            int supplyPoints = players.get(i).getPoints();
            playerPointsLabel[i].setText(Integer.toString(supplyPoints));

            int supplySettlers = players.get(i).getSettlers();
            playerSettlersLabel[i].setText(Integer.toString(supplySettlers));

            // List<Quarry> supplyGoodsQuarryList = GameHelper.getBank().getQuarrySupply();
            // playerGoodsQuarryLabel[i].setText(Integer.toString(supplyGoodsQuarryList.size()));
        }
	}

    /* ********************************************************************************************** */
	private void createPlayerPanel(JPanel panel, int idx) {
        Color[] playerColors = {Color.RED, new Color(0, 160, 0), Color.BLUE, Color.MAGENTA, new Color(0, 160, 160) };

		Border playerLineBorder = BorderFactory.createLineBorder(playerColors[idx]);
		TitledBorder playerBorder = BorderFactory.createTitledBorder(playerLineBorder, "Player " + (idx+1) + ":");
        playerBorder.setTitleJustification(TitledBorder.LEFT);
        playerBorder.setTitlePosition(TitledBorder.DEFAULT_POSITION);
        playerBorder.setTitleColor(playerColors[idx]);

		JPanel playerPanel = new JPanel();
        playerPanel.setBorder(playerBorder);
		playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));

		TitledBorder supplyBorder = BorderFactory.createTitledBorder("Supplies:");
        supplyBorder.setTitleJustification(TitledBorder.LEFT);
        supplyBorder.setTitlePosition(TitledBorder.DEFAULT_POSITION);

        JPanel supplyPanel = new JPanel(new GridLayout(1, 0), false);
        supplyPanel.setBorder(supplyBorder);

		                              createLabel(supplyPanel, "Coins");
        playerCoinsLabel[idx]       = createLabel(supplyPanel, "0");
		                              createLabel(supplyPanel, "Points");
        playerPointsLabel[idx]      = createLabel(supplyPanel, "0");
		                              createLabel(supplyPanel, "Settlers");
        playerSettlersLabel[idx]    = createLabel(supplyPanel, "0");
		                              createLabel(supplyPanel, Quarry.NAME);
        playerGoodsQuarryLabel[idx] = createLabel(supplyPanel, "0");
        playerPanel.add(supplyPanel);
        
		TitledBorder goodsBorder = BorderFactory.createTitledBorder("Goods:");
        goodsBorder.setTitleJustification(TitledBorder.LEFT);
        goodsBorder.setTitlePosition(TitledBorder.DEFAULT_POSITION);

        JPanel goodsPanel = new JPanel(new GridLayout(1, 0), false);
        goodsPanel.setBorder(goodsBorder);
        
		                               createLabel(goodsPanel, Coffee.NAME);
        playerGoodsCoffeeLabel[idx]  = createLabel(goodsPanel, "0");
		                               createLabel(goodsPanel, Corn.NAME);
        playerGoodsCornLabel[idx]    = createLabel(goodsPanel, "0");
		                               createLabel(goodsPanel, Indigo.NAME);
        playerGoodsIndigoLabel[idx]  = createLabel(goodsPanel, "0");
		                               createLabel(goodsPanel, Sugar.NAME);
        playerGoodsSugarLabel[idx]   = createLabel(goodsPanel, "0");
		                               createLabel(goodsPanel, Tobacco.NAME);
        playerGoodsTobaccoLabel[idx] = createLabel(goodsPanel, "0");
        playerPanel.add(goodsPanel);

		panel.add(playerPanel);
    }

    /* ********************************************************************************************** */
	private void createMainPanel(JFrame frame) {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		TitledBorder supplyBorder = BorderFactory.createTitledBorder("Supplies:");
        supplyBorder.setTitleJustification(TitledBorder.LEFT);
        supplyBorder.setTitlePosition(TitledBorder.DEFAULT_POSITION);

        JPanel supplyPanel = new JPanel(new GridLayout(1, 0), false);
        supplyPanel.setBorder(supplyBorder);

		                          createLabel(supplyPanel, "Coins");
        supplyCoinsLabel        = createLabel(supplyPanel, "0");
		                          createLabel(supplyPanel, "Points");
        supplyPointsLabel       = createLabel(supplyPanel, "0");
		                          createLabel(supplyPanel, "Settlers");
        supplySettlersLabel     = createLabel(supplyPanel, "0");
		                          createLabel(supplyPanel, Quarry.NAME);
        supplyGoodsQuarryLabel  = createLabel(supplyPanel, "0");
        mainPanel.add(supplyPanel);
        // Does nothing - mainPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        
		TitledBorder goodsBorder = BorderFactory.createTitledBorder("Goods:");
        goodsBorder.setTitleJustification(TitledBorder.LEFT);
        goodsBorder.setTitlePosition(TitledBorder.DEFAULT_POSITION);

        JPanel goodsPanel = new JPanel(new GridLayout(1, 0), false);
        goodsPanel.setBorder(goodsBorder);
        
		                          createLabel(goodsPanel, Coffee.NAME);
        supplyGoodsCoffeeLabel  = createLabel(goodsPanel, "0");
		                          createLabel(goodsPanel, Corn.NAME);
        supplyGoodsCornLabel    = createLabel(goodsPanel, "0");
		                          createLabel(goodsPanel, Indigo.NAME);
        supplyGoodsIndigoLabel  = createLabel(goodsPanel, "0");
		                          createLabel(goodsPanel, Sugar.NAME);
        supplyGoodsSugarLabel   = createLabel(goodsPanel, "0");
		                          createLabel(goodsPanel, Tobacco.NAME);
        supplyGoodsTobaccoLabel = createLabel(goodsPanel, "0");
        mainPanel.add(goodsPanel);

		TitledBorder cropsBorder = BorderFactory.createTitledBorder("Crops:");
        cropsBorder.setTitleJustification(TitledBorder.LEFT);
        cropsBorder.setTitlePosition(TitledBorder.DEFAULT_POSITION);

        JPanel cropsPanel = new JPanel(new GridLayout(1, 0), false);
        cropsPanel.setBorder(cropsBorder);
        
        // String[] tempCrops = {"Indigo", "Corn", "Coffee", "Sugar", "Tobacco", "Corn", "Coffee"};
		// int numPlayers = GameHelper.getNumberOfPlayers();
		// for (int i=0; i<numPlayers+1; i++)
		// {
			// supplyCrops[i] = createButton(cropsPanel, tempCrops[i], "chooseCrop " + tempCrops[i]);
		// }
        List<Crop> settlerCrops = GameHelper.getSettlerCropSupply();
		for (int i=0; i<settlerCrops.size(); i++)
		{
			String name = settlerCrops.get(i).getName();
			supplyCrops[i] = createButton(cropsPanel, name, "chooseCrop " + name);
		}
        mainPanel.add(cropsPanel);

		TitledBorder rolesBorder = BorderFactory.createTitledBorder("Roles:");
        rolesBorder.setTitleJustification(TitledBorder.LEFT);
        rolesBorder.setTitlePosition(TitledBorder.DEFAULT_POSITION);

        JPanel rolesPanel = new JPanel(new GridLayout(1, 0), false);
        rolesPanel.setBorder(rolesBorder);
        
		List<Role> rolesList = GameHelper.getBank().getRoles();
		int numRoles = rolesList.size();
		for (int i=0; i<numRoles; i++)
		{
			String name = rolesList.get(i).getName();
			roles[i] = createButton(rolesPanel, name, "chooseRole " + name);
		}
        mainPanel.add(rolesPanel);

		Border messageBorder = BorderFactory.createLineBorder(Color.GRAY);

        JPanel messagePanel = new JPanel(new GridLayout(1, 0), false);
        messagePanel.setBorder(messageBorder);
        
		int numPlayers = GameHelper.getNumberOfPlayers();
		for (int i=0; i<numPlayers; i++)
		{
            createPlayerPanel(mainPanel, i);
        }

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
    		players.add(new Player());
    	}
    	new Game(players);
    	
    	new DesktopLauncher();
	}
}
