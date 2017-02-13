/**
 * Matthew Allen Phillips
 * 10 February 2016
 * Holds coin counters for smooth
 * connection to the program GUI.
 */

package model;

import java.util.TreeMap;

/**
 * Defines storage to encapsulate a count of multiple cointypes.
 * @author Matt Phillips
 * @version 10 February 2017
 */
public class Purse implements Cloneable {
    
    /**
     * Maps each coin type to a count of how many
     * of that coin exist in this specific purse.
     */
    private TreeMap<Integer, Integer> myCCounts;
    
    /**
     * Stores the best coin to reach current Purse summation.
     */
    private int myBestCoin;
    
    /**
     * Initializes the purse with keys. Gives each coin type a
     * place in the map, and starts each with a value of zero.
     * @param theCoinValues used to map to tallies.
     */
    public Purse(final int[] theCoinValues) {
        /* Holds coin values -> tallies. */
        myCCounts = new TreeMap<Integer, Integer>();
        
        /* Holds best coin for this step. */
        myBestCoin = -1;
        
        /* Initialize the purse map. */
        for (int cv : theCoinValues) {
            myCCounts.put(cv, 0);
        }
    }
    
    /**
     * @return a String representation of the Purse's coin value-types.
     */
    public String getValues() {
        final StringBuilder sb = new StringBuilder(64);
        for (Integer i : myCCounts.keySet()) {
            sb.append(i);
            sb.append(" ");
        }
        return sb.toString();
    }
    
    /**
     * {@inheritDoc}
    * @return a String representation of the Purse's coin-counts.
    */
   @Override
   public String toString() {
       final StringBuilder sb = new StringBuilder(64);
       for (Integer i : myCCounts.keySet()) {
           sb.append(myCCounts.get(i));
           sb.append(" ");
       }
       return sb.toString();
   }
   
   /**
    * @return the proper and best coin to reach this Purse's value.
    */
   public int getBestCoin() {
       return myBestCoin;
   }
   
   /**
    * @param theVal value to set this Purse's new value to.
    */
   public void setBestCoin(final int theVal) {
       myBestCoin = theVal;
   }
   
   /**
    * {@inheritDoc}
    */
   @SuppressWarnings("unchecked")
   public Purse clone() throws CloneNotSupportedException {
       final Purse result = (Purse) super.clone();
       
       result.myCCounts   = (TreeMap<Integer, Integer>) myCCounts.clone();
       
       return result;
   }
    
    /**
     * Adds another coin of client-supplied typevalue. 
     * @param theCoinType integer representing coin type.
     * @return 1 if unsuccessful; 0 if successful.
     */
    protected int addCoin(int theCoinType) {
        if (!myCCounts.containsKey(theCoinType)) {
            /*  Coin type doesn't exist in this purse. */
            return 1;
        } else {
            /* Coin type exists; increment it. */
            int tally = myCCounts.get(theCoinType);
            myCCounts.put(theCoinType, ++tally);
            return 0;
        }
    }
}
