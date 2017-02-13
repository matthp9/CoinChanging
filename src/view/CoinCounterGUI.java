/*
 * Matthew Allen Phillips
 * 10 February 2017
 * Primary GUI for Coin Counter.
 */

package view;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.CoinCounter;
import support.ControlListener;

/**
 * Defines the primary GUI system for Coin Counter.
 * Utilizes help from the JFrame swing component.
 * @author Matt Phillips
 * @version 10 February 2016
 */
public class CoinCounterGUI extends JFrame {

    /**
     * Auto-generated (Eclipse IDE).
     */
    private static final long serialVersionUID = 3579710878316152026L;
    
    private CoinCounter myCounter;
    
    /**
     * Updatable display for cursor-adaptable cells.
     */
    private CellDisplay myCellDisplay;
    
    /**
     * Updatable display for coin grid.
     */
    private GridDisplay myGridDisplay;
    
    /**
     * Creates a new GUI.
     */
    public CoinCounterGUI() {
        super("The Change-Making Problem");
    }
    
    /**
     * Invokes the GUI startup.
     */
    public void start() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("images/admin/header.png").getImage());
           
        int[] coins = {1, 5, 10, 25};
        int target  = 29;
        myCounter = new CoinCounter(coins, target);
        
        final HeaderDisplay hd = new HeaderDisplay();
        myCellDisplay          = new CellDisplay(myCounter.getAllAnswers(), 
                                                 myCounter.getPurses());
        myGridDisplay          = new GridDisplay(myCounter.getPurses());
        final InputDisplay  id = new InputDisplay(this);
        
        addPropertyChangeListener(myCellDisplay);
        addPropertyChangeListener(myGridDisplay);
        
        add(hd, BorderLayout.NORTH);
        add(myCellDisplay, BorderLayout.CENTER);
        
        JPanel sp = new JPanel();
        sp.setLayout(new BorderLayout());
        sp.add(myGridDisplay, BorderLayout.NORTH);
        sp.add(id, BorderLayout.SOUTH);
        add(sp, BorderLayout.SOUTH);
        
        addKeyListener(new ControlListener(this));
        
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
    
    /**
     * Shifts cursors in visual panels.
     */
    public void shiftCursor(final int theShift) {
        this.firePropertyChange("CURSOR", 0, theShift);
    }
    
    /**
     * Updates after numerical user input.
     * @param thePanel panel to reinstantiate.
     */
    protected void calculate(final int[] theCoins, 
                              final int theTarget) {
        removePropertyChangeListener(myCellDisplay);
        removePropertyChangeListener(myGridDisplay);
        
        myCounter = new CoinCounter(theCoins, theTarget);
        
        myCellDisplay.setAnswers(myCounter.getAllAnswers());
        myCellDisplay.setCollections(myCounter.getPurses());
        myGridDisplay.setCollections(myCounter.getPurses());
        
        addPropertyChangeListener(myCellDisplay);
        addPropertyChangeListener(myGridDisplay);
    }
}
