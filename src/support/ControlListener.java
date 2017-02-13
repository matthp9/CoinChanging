/**
 * Matthew Allen Phillips
 * 10 February 2017
 * Key Listener for Cursor Control.
 */

package support;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import view.CoinCounterGUI;

/**
 * Listens for change in the user cursor.
 * 
 * @author Matt Phillips
 * @version 10 February 2017
 */
public class ControlListener extends KeyAdapter {
    
    /**
     * A GUI frame tethered to this listener.
     */
    private final CoinCounterGUI myGUI;
    
    /**
     * Constructs a new ControlListener.
     * @param theGUI a GUI to tether focus to for this Listener.
     */
    public ControlListener(final CoinCounterGUI theGUI) {
        super();
        
        myGUI = theGUI;
    }

    @Override
    public void keyPressed(final KeyEvent theEvent) {
        int keyCode = theEvent.getKeyCode();
        switch (keyCode) {
            case (KeyEvent.VK_LEFT):
                myGUI.shiftCursor(-1);
                break;
            case (KeyEvent.VK_RIGHT):
                myGUI.shiftCursor(1);
                break;
        }
    }
}
