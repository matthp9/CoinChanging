/**
 * Matthew Allen Phillips
 * 10 February 2017
 * Provides a grid display for a coin Purse.
 */

package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import coins.Coin;
import model.Purse;

/**
 * Provides a display panel to organize a
 * coin Purse for viewing in grid format.
 * 
 * @author Matt Phillips
 * @version 10 February 2017
 */
public class GridDisplay extends JPanel implements PropertyChangeListener {

    /**
     * Font used in all text.
     */
    private static final Font FONT = new Font("Font L", Font.PLAIN, 20);
    
    /**
     * A constant for limiting display calculations in width.
     */
    private static final int WI_RES = 1200;
    
    /**
     * Auto-generated (Eclipse IDE).
     */
    private static final long serialVersionUID = -2570358045879503043L;
    
    /**
     * USD Set of Coin Image Representations.
     */
    private static final Image[] COIN_IMGS = {new ImageIcon("images/USD2/coin1.png").getImage(),
                                              new ImageIcon("images/USD2/coin2.png").getImage(),
                                              new ImageIcon("images/USD2/coin3.png").getImage(),
                                              new ImageIcon("images/USD2/coin4.png").getImage()};
    
    /**
     * Holds the coin combinations to reach any total.
     */
    private Purse[] myPurses;
    
    /**
     * Holds image representations of the coins being used.
     */
    private Image[] myImages;
    
    /**
     * Stores a cursor for the purse display. (Linear = array-based).
     */
    private int myCursor;
    
    /**
     * Creates a new GridDisplay.
     * @param thePurses list of Purse containers of coin combinations.
     */
    public GridDisplay(final Purse[] thePurses) {
        super();
        
        myPurses = thePurses;
        myCursor = (thePurses.length - 1);
        
        final String[] purseVals = myPurses[myCursor].getValues().split(" ");
        
        myImages = new Image[purseVals.length];
        
        String[] usd = {"1", "5", "10", "25"};
        
        if (Arrays.toString(purseVals).equals(Arrays.toString(usd))) {
            myImages = COIN_IMGS;
        } else {
            myImages = new Image[purseVals.length];
            for (int i = 0; i < purseVals.length; i++) {
                myImages[i] = new ImageIcon("images/coins/coin" + i + ".png").getImage();
            }
        }
        
        setPreferredSize(new Dimension(1300, 400));
        setBackground(Color.GREEN.darker().darker().darker());
        
        repaint();
    }
    
    /**
     * {@inheritDoc}
     */
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g = (Graphics2D) theGraphics;
        
        g.setColor(Color.WHITE);
        g.setFont(FONT);
        
        g.drawString("How to Make: ", 10, 30);
        
        final String[] purseInts = myPurses[myCursor].toString().split(" ");
        final String[] purseVals = myPurses[myCursor].getValues().split(" ");
        
        int cellWidth  = WI_RES / purseInts.length;
        int cellHeight = 300;
        
        int offsetX = cellWidth / 32;
        int offsetY = cellHeight / 8;
        
        int x  = 50;
        int y  = 50;
        int d  = 10;
        
        /* Output "rolls" of coins, homogeneously. */
        for (int roll = (purseInts.length - 1); roll >= 0; roll--) {
            int valueOfCoin = Integer.parseInt(purseVals[roll]);
            int countInRoll = Integer.parseInt(purseInts[roll]);
            
            g.drawRect(x, y, cellWidth, cellHeight);
            g.drawString(valueOfCoin + " Cent Pieces: " + countInRoll, 
                                                            x + d, y + (2 *d));
            
            for (int ctr = 0; ctr < countInRoll; ctr++) {
                g.drawImage(myImages[roll], x + offsetX + (d * ctr),
                                             y + offsetY, null);
            }
            x += cellWidth;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void propertyChange(final PropertyChangeEvent e) {
        if (e.getPropertyName() == "CURSOR") {
            myCursor += (Integer) e.getNewValue();
            if (myPurses.length > 48) {
                if (myCursor >= myPurses.length) {
                    myCursor -= 49;
                }
                if (myCursor < (myPurses.length - 49)) {
                    myCursor += 49;
                }
            } else {
                myCursor %= myPurses.length;
            }
            if (myCursor < 0) {
                myCursor += myPurses.length;
            }
            repaint();
        }
    }
    
    /**
     * @param thePurses the new Purses collection for this grid to iterate.
     */
    protected void setCollections(final Purse[] thePurses) {
        myPurses = thePurses;
        myCursor = (thePurses.length - 1);

        final String[] purseVals = myPurses[myCursor].getValues().split(" ");
        
        String[] usd = {"1", "5", "10", "25"};
        
        if (Arrays.toString(purseVals).equals(Arrays.toString(usd))) {
            myImages = COIN_IMGS;
        } else {
            myImages = new Image[purseVals.length];
            for (int i = 0; i < purseVals.length; i++) {
                myImages[i] = new ImageIcon("images/coins/coin" + i + ".png").getImage();
            }
        }
        
        repaint();
    }
}
