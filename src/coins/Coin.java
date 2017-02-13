/**
 * Matthew Allen Phillips
 * 10 February 2016
 * Coin enumeration; used in Coin Count algorithm.
 */

package coins;

/**
 * Declares constants for denomination Coin.
 * 
 * @author Matt Phillips
 * @version 10 February 2017
 */
public enum Coin {
    
    /**
     * 1 cent coin.
     */
    PENNY(1),
    
    /**
     * 5 cent coin.
     */
    NICKEL(5),
    
    /**
     * 10 cent coin.
     */
    DIME(10),
    
    /**
     * 25 cent coin.
     */
    QUARTER(25);
    
    /**
     * USD Value of the Coin.
     */
    public int myValue;
    
    private Coin(int theValue) {
        myValue = theValue;
    }
    
    
}
