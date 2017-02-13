/**
 * Matthew Allen Phillips
 * 10 Febraury 2017
 * Displays a cell list of change combinations.
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

import model.Purse;

/**
 * A display pane for use by the GUI. Shows a
 * list of cells, each giving a valid combination
 * of currency to create the listed total.
 * 
 * @author Matt Phillips
 * @version 10 February 2017
 */
public class CellDisplay extends JPanel implements PropertyChangeListener {

   /**
    * Font used in the input buttons.
    */
   private static final Font FONT = new Font("Font L", Font.PLAIN, 20);
    
    /**
     * A constant for limiting display calculations in width.
     */
    private static final int WI_RES = 1200;
    
    /**
     * A constant for limiting display calculations in width.
     */
    private static final int HI_RES = 800;
    
    /**
     * Auto-generated (Eclipse IDE).
     */
    private static final long serialVersionUID = -2570358058879503043L;
    
    /**
     * Holds the minimum coin count to get to any total.
     */
    private int[] myCounts;
    
    /**
     * Stores a cursor (pseudo) for the purse display.
     */
    private int myCursor;
    
    /**
     * Stores a cursor (actual) for the purse display.
     */
    private int myTarget;

    /**
     * Stores a Purse of needed coins and best coins at each step.
     */
    private Purse[] myPurses;
    
    /**
     * Creates a new CellDisplay panel.
     */
    public CellDisplay(final int[] theCounts, final Purse[] thePurses) {
        super();
        
        myCounts = theCounts;
        myPurses = thePurses;
        
        myCursor = (theCounts.length - 1);
        myTarget = (theCounts.length - 1);

        if (myCounts.length >= 49) {
            myCursor = 48;
        }
        
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(1300, 170));
        
        repaint();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g = (Graphics2D) theGraphics;
        
        int cellWidth  = WI_RES / Math.min(50, myCounts.length + 1);
        int cellHeight = HI_RES / 50; 
        int offsetX    = cellWidth / 12;
        int offsetY    = (3 * cellHeight / 4);
        int x1 = 20;
        int x2 = 80;
        int y1 = 50;
        int y2 = y1 + offsetY;
        int y3 = y1 + cellHeight + 10;
        int y4 = y3 + offsetY;
        
        g.drawString("Cents Made:", x1, y2);
        g.drawString("Coin Count:", x1, y4);
        
        g.setColor(Color.MAGENTA);
        g.fillRect(x2 + (cellWidth * (myCursor + 1)), y2, cellWidth, cellHeight);
        g.fillRect(x2 + (cellWidth * (myCursor + 1)), y2 + 32, cellWidth, cellHeight / 2);
        g.fillRect(x2 + (cellWidth * (myCursor + 1)), y2 - 21, cellWidth, cellHeight / 2);
                
        if (myTarget != 0 && myCursor != 0) {
            g.setColor(Color.BLUE);
            g.fillRect(x2 + (cellWidth * (myCursor + 1)) - (cellWidth  * myPurses[myTarget].getBestCoin()), y2, cellWidth, cellHeight);
            g.setColor(Color.RED);
            final int left = x2 + (cellWidth * (myCursor + 2)) - (cellWidth  * myPurses[myTarget].getBestCoin());
            final int right = x2 + (cellWidth * (myCursor + 1));
            g.fillRect(left, y2, right - left, cellHeight);
        }
        
        int j = 0;
        if (myCounts.length >= 49) { j = myCounts.length - 49; }
        
        for (int i = j; i < myCounts.length; i++) {
            x2 += cellWidth;
            
            g.setColor(Color.BLACK);
            g.fillRect(x2, y1, cellWidth, cellHeight);
            g.fillRect(x2, y3, cellWidth, cellHeight);
            g.setColor(Color.WHITE);
            g.drawRect(x2, y1, cellWidth, cellHeight);
            g.drawRect(x2, y3, cellWidth, cellHeight);
            g.setColor(Color.WHITE);
            g.drawString("" + i, x2 + offsetX, y2);
            g.drawString("" + myCounts[i], x2 + offsetX, y4);
        }
        
        g.setFont(FONT);
        g.setColor(Color.MAGENTA);
        g.drawString("Cursor: we can make " + myTarget + " cents with max efficiency using " + myCounts[myTarget] + " coins.", x1, 120);
        if (myTarget != 0) {
            g.setColor(Color.BLUE);
            g.drawString("Previous subproblem: our max efficiency up until now, " + myCounts[myTarget - myPurses[myTarget].getBestCoin()] 
                            + " coins, is the minimum of (" + myTarget + " - X) for each X in your coin values { " 
                            + myPurses[0].getValues() + "}.", x1, 145);
            g.setColor(Color.RED);
            g.drawString("Difference: we add one-" + myPurses[myTarget].getBestCoin() 
                         + " cent coin to our previous maximum efficiency to get the "
                         + "maximum efficiency for " + myTarget + " cents: " 
                         + myCounts[myTarget] + " coins.", x1, 170);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void propertyChange(final PropertyChangeEvent e) {
        if (e.getPropertyName() == "CURSOR") {
            myCursor += (Integer) e.getNewValue();
            myTarget += (Integer) e.getNewValue();
            if (myTarget >= myCounts.length) {
                myTarget -= myCursor;
            }
            if (myTarget < 0) {
                myTarget += myCounts.length; 
            }
            if (myCounts.length <= 48) {
                myCursor %= myCounts.length;
                if (myCursor < 0) {
                    myCursor += myCounts.length;
                }
            }
            if (myCounts.length > 48) {
                if (myTarget < (myCounts.length - 49)) {
                    myTarget += 49;
                }
                if (myCursor > 48) {
                    myCursor -= 49;
                }
                if (myCursor < 0) {
                    myCursor += 49;
                }
            }
            repaint();
        }
    }
    
    protected void setAnswers(int[] theCounts) {
        myCounts = theCounts;
        myCursor = (theCounts.length - 1);
        myTarget = (theCounts.length - 1);

        if (myCounts.length >= 49) {
            myCursor = 48;
        }
        repaint();
    }
    
    /**
     * @param thePurses the new Purses collection for this grid to iterate.
     */
    protected void setCollections(final Purse[] thePurses) {
        myPurses = thePurses;
        repaint();
    }
}
