/**
 * Matthew Allen Phillips
 * 11 February 2017
 * Provides currency input.
 */

package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

import javax.swing.JButton;
import javax.swing.JPanel;

import coins.Coin;

//import tools.Palette;

/**
 * Allows the user to manually enter
 * currency values, clear the currency
 * list, or select predetermined coins.
 * 
 * @author Matt Phillips
 * @version 11 February 2017
 */
public class InputDisplay extends JPanel {

    /**
     * Font used in the input buttons.
     */
    private static final Font FONT = new Font("Font L", Font.PLAIN, 20);
    
    /**
     * Font used in the input buttons.
     */
    private static final Font INSTRUCTIONS = new Font("Font L", Font.PLAIN, 16);
    
    /**
     * Auto-generated (Eclipse IDE).
     */
    private static final long serialVersionUID = 1444827836719787490L;
    
    /**
     * Tracks the user's next value to add.
     */
    private StringBuilder myCurrentValueToAdd;
    
    /**
     * Target value wished to be built with various coins.
     */
    private int myTarget;
    
    /**
     * Holds all coins added by the user.
     */
    private List<Integer> myCoinCollection;
    
    /**
     * Tethers this input display to a GUI.
     */
    private final CoinCounterGUI myGUI;
    
    /**
     * Internal display panel.
     */
    private JPanel myPanel;

    public InputDisplay(final CoinCounterGUI theGUI) {
        myCurrentValueToAdd = new StringBuilder(4);
        myTarget            = 100;
        myCoinCollection    = new ArrayList<Integer>();
        myCoinCollection.add(1);
        
        myPanel             = new JPanel();
        myGUI               = theGUI;
        
        setPreferredSize(new Dimension(1300, 300));
        setBackground(Color.GREEN.darker().darker().darker());
        
        init();
        
        repaint();
    }
    
    /**
     * {@inheritDoc}
     */
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g = (Graphics2D) theGraphics;
        
        int x1 = 50;
        int y1 = 10;
        int y2 = 110;
        int y3 = 160;
        
        int offsetX  = 10;
        int offsetY  = 30;
                        
        int w  = 400;
        int h = 40;
        
        g.setColor(Color.WHITE);
        g.fillRect(x1, y1, w, h);
        g.fillRect(x1, y2, w, h);
        g.fillRect(x1, y3, w, h);
        
        g.setColor(Color.BLACK);
        g.drawRect(x1, y1, w, h);
        g.drawRect(x1, y2, w, h);
        g.drawRect(x1, y3, w, h);
        
        g.setFont(FONT);
        g.drawString("Input: " + myCurrentValueToAdd.toString(), 
                     x1 + offsetX, y1 + offsetY);
        g.drawString("I want to make change for: " + myTarget + " cents.", 
                     x1 + offsetX, y2 + offsetY);
        g.drawString("My coin values: " + myCoinCollection.toString(),
                     x1 + offsetX, y3 + offsetY);
        
        g.setFont(INSTRUCTIONS);
        g.setColor(Color.WHITE);
        g.drawString("Instructions: " , 850, y1 + offsetY);
        g.drawString("1. Select coin values", 850, y1 + offsetY + 20);
        g.drawString("   * ADD adds input to coin values", 850, y1 + offsetY + 40);
        g.drawString("   * DEL removes a coin value", 850, y1 + offsetY + 60);
        g.drawString("2. Select a target value", 850, y1 + offsetY + 80);
        g.drawString("   * TGT saves input as target value", 850, y1 + offsetY + 100);
        g.drawString("3. Select GO! to create change", 850, y1 + offsetY + 120);
        g.drawString("   * CLR clears your input", 850, y1 + offsetY + 140);
        g.drawString("   * USD adds std. U.S. currency coins", 850, y1 + offsetY + 160);
        g.drawString("4. Scroll the cursor using Left/Right", 850, y1 + offsetY + 180);
        g.drawString("   keys to watch the algorithm work", 850, y1 + offsetY + 200);
        g.drawRect(825, y1, 300, 265);
    }
    
    /**
     * Initializes the display.
     */
    private void init() {
        final ActionListener insert = new InsertAction();
        final ActionListener add    = new AddAction();
        final ClearAction clear     = new ClearAction();
        final DeleteAction del      = new DeleteAction();
        final USDAction usd         = new USDAction();
        final TargetAction tgt      = new TargetAction();
        final GoAction go           = new GoAction();
        
        myPanel.setLayout(new GridLayout(0, 3));
        
        addButton("7", insert, Color.WHITE);
        addButton("8", insert, Color.WHITE);
        addButton("9", insert, Color.WHITE);
        addButton("4", insert, Color.WHITE);
        addButton("5", insert, Color.WHITE);
        addButton("6", insert, Color.WHITE);
        addButton("1", insert, Color.WHITE);
        addButton("2", insert, Color.WHITE);
        addButton("3", insert, Color.WHITE);
        
        addButton("CLR", clear, new Color(230, 242, 255));
        addButton("0", insert, Color.WHITE);
        addButton("DEL", del, new Color(255, 230, 230));
        
        addButton("ADD", add, new Color(249, 230, 255));
        addButton("TGT", tgt, new Color(255, 255, 230));
        addButton("USD", usd, new Color(230, 255, 230));
        
        addButton("", null, Color.LIGHT_GRAY);
        addButton("GO!", go, new Color(245, 245, 240));
        addButton("", null, Color.LIGHT_GRAY);
        
        
        add(myPanel, BorderLayout.WEST);
    }
    
    /**
     * Adds a button to the center panel.
     * @param theLabel label to stick on the button
     * @param theListener action taken by the button
     */
    private final void addButton(final String theLabel, 
                                 final ActionListener theListener,
                                 final Color theColor) {
        final JButton button = new JButton(theLabel);
        if (theListener == null) {
            button.setEnabled(false);
        }
        button.addActionListener(theListener);
        button.setFocusable(false);
        button.setFont(FONT);
        button.setPreferredSize(new Dimension(80, 45));
        button.setBackground(theColor);
        myPanel.add(button);
    }
    
    private final class InsertAction implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            final String input = theEvent.getActionCommand();
            if (myCurrentValueToAdd.length() < 3) {
                myCurrentValueToAdd.append(input);
            }
            repaint();
        }
    }
    
    private final class AddAction implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            int toAdd = -1;
            try {
                toAdd = Integer.parseInt(myCurrentValueToAdd.toString());
            } catch (final Exception e) {
                // Failed
            }
            if (toAdd > 0 && toAdd < 1000 && !myCoinCollection.contains(toAdd)
                            && (myCoinCollection.size() < 6)) {
                myCoinCollection.add(toAdd);
                myCurrentValueToAdd = new StringBuilder(4);
            }
            repaint();
        }
    }
    
    private final class ClearAction implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myCurrentValueToAdd = new StringBuilder(4);
            repaint();
        }
    }
    
    private final class DeleteAction implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            if (myCoinCollection.size() > 1) {
                myCoinCollection.remove(myCoinCollection.size() - 1);
            }
            repaint();
        }
    }
    
    private final class USDAction implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myCoinCollection.clear();
            for (final Coin c : Coin.values()) {
                myCoinCollection.add(c.myValue);
            }
            repaint();
        }
    }
    
    private final class TargetAction implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            int toAdd = -1;
            try {
                toAdd = Integer.parseInt(myCurrentValueToAdd.toString());
            } catch (final Exception e) {
                // Failed
            }
            if ((toAdd >= 0) && (toAdd < 1000)) {
                myTarget = toAdd;
                myCurrentValueToAdd = new StringBuilder(4);
            }
            repaint();
        }
    }
    
    private final class GoAction implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myCurrentValueToAdd = new StringBuilder(4);
            int[] allCoins = new int[myCoinCollection.size()];
            for (int i = 0; i < myCoinCollection.size(); i++) {
                allCoins[i] = myCoinCollection.get(i);
            }
            Arrays.sort(allCoins);
            myGUI.calculate(allCoins, myTarget);
            repaint();
        }
    }
}
