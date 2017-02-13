/*
 * Matthew Allen Phillips
 * 10 February 2017
 * Driver class.
 */

package view;

import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Driver/main program for Coin Counter.
 * @author Matt Phillips
 * @version 10 February 2017
 */
public class CoinCounterMain {

    /**
     * Private constructor to prevent instantiating CoinCounter.
     */
    private CoinCounterMain() {
        // Do nothing.
    }
    
    /**
     * Sets the preferred Metal look of the CointCounterGUI.
     */
    private static void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (final UnsupportedLookAndFeelException e) {
            System.out.println("UnsupportedLookAndFeelException");
        } catch (final ClassNotFoundException e) {
            System.out.println("ClassNotFoundException");
        } catch (final InstantiationException e) {
            System.out.println("InstantiationException");
        } catch (final IllegalAccessException e) {
            System.out.println("IllegalAccessException");
        }
    }
    
    /**
     * Invokes CoinCounterGUI without command line arguments (default).
     * 
     * @param theArgs Command line arguments.
     */
    public static void main(final String... theArgs) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                setLookAndFeel();
                new CoinCounterGUI().start();
            }
        });
    }
}
