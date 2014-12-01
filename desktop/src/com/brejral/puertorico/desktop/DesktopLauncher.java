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
import com.brejral.puertorico.game.crop.Corn;
import com.brejral.puertorico.game.player.Player;
// Tim import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
// Tim import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	
	private static JLabel createLabelPairGUI(JPanel pane, GridBagConstraints c, int gridx, int gridy, String label) {
		JPanel panel = new JPanel(new FlowLayout());
		JLabel jlabel = new JLabel(label);
		JLabel field = new JLabel("0");
		panel.add(jlabel);
		panel.add(field);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = gridx;
		c.gridy = gridy;
		pane.add(panel, c);
		return field;
	}

	private static void createSummaryGUI(JFrame frame) {
		JButton button;
		
		JPanel pane = new JPanel(new GridBagLayout());
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		// Tim if (shouldFill) {
		                //natural height, maximum width
		                // Tim c.fill = GridBagConstraints.HORIZONTAL;
		// Tim }

        JLabel supplyCoinsLabel = createLabelPairGUI(pane, c, 0, 0, "Coins");
        JLabel supplyPointsLabel = createLabelPairGUI(pane, c, 1, 0, "Points");
        JLabel supplySettlersLabel = createLabelPairGUI(pane, c, 2, 0, "Settlers");

        // int supplyCoins = game.getBank().getCoinSupply();
        // supplyCoinsLabel.setText(Integer.toString(supplyCoins));

        int supplyCoins = GameHelper.getBank().getCoinSupply();
        supplyCoinsLabel.setText(Integer.toString(supplyCoins));

        int supplyPoints = GameHelper.getBank().getPointSupply();
        supplyPointsLabel.setText(Integer.toString(supplyPoints));

        int supplySettlers = GameHelper.getBank().getSettlerSupply();
        supplySettlersLabel.setText(Integer.toString(supplySettlers));

		int supplyCorn = GameHelper.getBank().getGoodSupply().get(Corn.NAME);

		// Tim button = new JButton("Button 1");
		// Tim if (shouldWeightX) {
		                   // Tim c.weightx = 0.5;
		// Tim }
		// c.fill = GridBagConstraints.HORIZONTAL;
		// Tim c.gridx = 0;
		// Tim c.gridy = 0;
		// Tim pane.add(button, c);

		// Tim button = new JButton("Button 2");
		// Tim c.fill = GridBagConstraints.HORIZONTAL;
		// Tim c.weightx = 0.5;
		// Tim c.gridx = 1;
		// Tim c.gridy = 0;
		// Tim pane.add(button, c);

		// Tim button = new JButton("Button 3");
		// Tim c.fill = GridBagConstraints.HORIZONTAL;
		// Tim c.weightx = 0.5;
		// Tim c.gridx = 2;
		// Tim c.gridy = 0;
		// Tim pane.add(button, c);

		button = new JButton("Long-Named Button 4");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 40;      //make this component tall
		c.weightx = 0.0;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 1;
		pane.add(button, c);

		button = new JButton("5");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 0;       //reset to default
		c.weighty = 1.0;   //request any extra vertical space
		c.anchor = GridBagConstraints.PAGE_END; //bottom of space
		c.insets = new Insets(10,0,0,0);  //top padding
		c.gridx = 1;       //aligned with button 2
		c.gridwidth = 2;   //2 columns wide
		c.gridy = 2;       //third row
		pane.add(button, c);

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
 
        //Add the ubiquitous "Hello World" label.
        // Tim JLabel label = new JLabel("Hello World");
        // Tim frame.getContentPane().add(label);
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
    	// game = new Game(players);
    	new Game(players);
    	
        createAndShowGUI();
	}
}
