package com.brejral.puertorico.desktop;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.brejral.puertorico.game.Game;
import com.brejral.puertorico.game.GameHelper;
import com.brejral.puertorico.game.crop.Coffee;
import com.brejral.puertorico.game.crop.Corn;
import com.brejral.puertorico.game.crop.Indigo;
import com.brejral.puertorico.game.crop.Quarry;
import com.brejral.puertorico.game.crop.Sugar;
import com.brejral.puertorico.game.crop.Tobacco;
import com.brejral.puertorico.game.player.Player;

// Tim import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
// Tim import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	
    static JLabel supplyCoinsLabel;
    static JLabel supplyPointsLabel;
    static JLabel supplySettlersLabel;

    static JLabel supplyGoodsCoffeeLabel ;
    static JLabel supplyGoodsCornLabel;
    static JLabel supplyGoodsIndigoLabel;
    static JLabel supplyGoodsSugarLabel;
    static JLabel supplyGoodsTobaccoLabel;
    static JLabel supplyGoodsQuarryLabel;

	private static JLabel createLabelPairGUI(JPanel pane, GridBagConstraints c, int gridx, int gridy, int gridw, String label) {
		JPanel panel = new JPanel(new FlowLayout());
		JLabel jlabel = new JLabel(label);
		JLabel field = new JLabel("0");
		panel.add(jlabel);
		panel.add(field);
		c.fill = GridBagConstraints.HORIZONTAL;
		// c.weightx = 1.0;   //request any extra vertical space
		// c.gridx = (gridx*2);
		c.gridx = gridx;
		c.gridy = gridy;
		c.gridwidth = gridw;
		// pane.add(jlabel, c);
		// c.fill = GridBagConstraints.HORIZONTAL;
		// c.weightx = 1.0;   //request any extra vertical space
		// c.gridx = (gridx*2)+1;
		// c.gridy = gridy;
		// pane.add(field, c);
		pane.add(panel, c);
		return field;
	}

    private static void updateAll()
    {
        updateSupplies();
        updateGoods();
	}

    private static void updateSupplies()
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

    private static void updateGoods()
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

	private static void createSummaryGUI(JFrame frame) {
		JButton button;
		int col = 0;
		int row = 0;
		
		JPanel pane = new JPanel(new GridBagLayout());
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JLabel jlabel = new JLabel("Supplies:");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = col++;
		c.gridy = row;
		c.gridwidth = 1;
		pane.add(jlabel, c);

        supplyCoinsLabel = createLabelPairGUI(pane, c, col++, row, 1, "Coins");
        supplyPointsLabel = createLabelPairGUI(pane, c, col++, row, 1, "Points");
        supplySettlersLabel = createLabelPairGUI(pane, c, col++, row, 1, "Settlers");
        supplyGoodsQuarryLabel  = createLabelPairGUI(pane, c, col++, row, 1, Quarry.NAME);
        row++;

		col = 0;
		jlabel = new JLabel("Goods:");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = col++;
		c.gridy = row;
		c.gridwidth = 1;
		pane.add(jlabel, c);

        supplyGoodsCoffeeLabel  = createLabelPairGUI(pane, c, col++, row, 1, Coffee.NAME);
        supplyGoodsCornLabel    = createLabelPairGUI(pane, c, col++, row, 1, Corn.NAME);
        supplyGoodsIndigoLabel  = createLabelPairGUI(pane, c, col++, row, 1, Indigo.NAME);
        supplyGoodsSugarLabel   = createLabelPairGUI(pane, c, col++, row, 1, Sugar.NAME);
        supplyGoodsTobaccoLabel = createLabelPairGUI(pane, c, col++, row, 1, Tobacco.NAME);
        row++;

        updateAll();

        /*
		button = new JButton("Long-Named Button 4");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 40;      //make this component tall
		c.weightx = 0.0;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = row;
		pane.add(button, c);
        row++;

		button = new JButton("5");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 0;       //reset to default
		c.weighty = 1.0;   //request any extra vertical space
		c.anchor = GridBagConstraints.PAGE_END; //bottom of space
		c.insets = new Insets(10,0,0,0);  //top padding
		c.gridx = 1;       //aligned with button 2
		c.gridwidth = 2;   //2 columns wide
		c.gridy = row;       //third row
		pane.add(button, c);
        row++;
        */

        frame.getContentPane().add(pane);
	}
	
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("HelloWorldSwing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        createSummaryGUI(frame);
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
    public static void main (String[] arg) {
		// Tim LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		// Tim new LwjglApplication(new PuertoRico(), config);
    	List<Player> players = new ArrayList<Player>();
    	for (int i=0; i<4; i++)
    	{
    		players.add(new Player());
    	}
    	new Game(players);
    	
        createAndShowGUI();
	}
}
