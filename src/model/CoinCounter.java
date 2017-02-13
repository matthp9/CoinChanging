/**
 * Matthew Allen Phillips
 * 10 February 2016
 * Core Coin Counter algorithm.
 */

package model;

/**
 * Computation object for counting coins.
 * 
 * @author Matt Phillips
 * @version 10 February 2017
 */
public class CoinCounter {
    
    
    /*
     * Supplied by client.
     */
    /**
     * A supplied list of coins we give to the counter.
     */
    private final int[] myCoins;
    
    /**
     * A target value supplied to the counter.
     */
    private int myTarget;
    
    
    
    /*
     * Will be available to client after computation.
     */
    /**
     * Final encapsulation for desired input value.
     */
    private int myMinCoins;
    
    /**
     * Stores the coins needed at each increasing value.
     */
    private int[] myIndices;
    
    /**
     * Stores coins in a Purse at each subproblem to value.
     */
    private final Purse[] myPurses;
    
    
    
    /**
     * Constructs a worker to ues on the client's input.
     * 
     * @param theCoins a list of coin denominations to work with.
     * @param theTarget a wanted value to find its minimum coins.
     */
    public CoinCounter(final int[] theCoins, final int theTarget) {
        /* We begin with these values. */
        myCoins  = theCoins;
        myTarget = theTarget;
        
        /* We will fill and end with these values. */
        myMinCoins = Integer.MAX_VALUE;
        myIndices  = new int[myTarget + 1]; // Dynamic Programming component.
    
        myPurses = new Purse[myTarget + 1];
        for (int ctr = 0; ctr < myPurses.length; ctr++) {
            myPurses[ctr] = new Purse(myCoins);
        }
        
        countChange();
    }
    
    /**
     * Getter...
     * ...returns the minimum coins needed for the user's target value.
     * @return minimum denominational coins to reach your target.
     */
    public int getAnswer() {
        return myMinCoins;
    }
    
    /**
     * Getter...
     * ...returns the storage of all value combinations up to target.
     * @return the storage of all coin minimums up the value.
     */
    public int[] getAllAnswers() {
        return myIndices;
    }
    
    /**
     * Getter...
     * ...returns the collection of Purses
     *    representing coin combinations.
     * @return the storage of all coin Purse collections.
     */ 
    public Purse[] getPurses() {
        return myPurses;
    }
    
    /**
     * Setter...
     * @param theNewTarget a new index to observe min coins at.
     * @return 1 if unsuccessful; 0 if unsuccessful.
     */
    public int setAnswer(final int theNewTarget) {
        if ((theNewTarget > myIndices.length - 1) || (theNewTarget < 0)) {
            return 1; // Failed, prepare error return.
        } else {
            myTarget   = theNewTarget;
            myMinCoins = myIndices[theNewTarget];
            return 0; // Succeeded, in array bounds.
        }
    }
    
    /**
     * @return a String representation of the counter data.
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(64);
        sb.append("Minimum Coins Needed For ");
        sb.append(myTarget);
        sb.append(" Cents: ");
        sb.append(myMinCoins);
        sb.append(" Coins.\n");
        sb.append("Most Efficient Formation Of ");
        sb.append(myTarget);
        sb.append(" Cents: ");
        sb.append(myPurses[myTarget].toString());
        return sb.toString();
    }
    
    /**
     * Core algorithmic sequence. Primary goal of countChange is to
     * calculate the minimum number of denominational coins to create
     * the user's desired target value. Intermediately, we create a 
     * storage unit to hold subproblems up to that value. We store a 
     * parallel unit to hold the coins used for each subproblem.
     */
    private void countChange() {
        /* To make 0.00, we use 0 coins. */
        myIndices[0] = 0;
        
        /* 
         * Work from subproblem $0.01 to target. 
         * Because subproblems overlap, we will
         * store them and use them dynamically.
         */
        for (int curr = 1; curr <= myTarget; curr++) {
            /* 
             * Adds one coin (current-next used) to
             * the preemptive minimum coins needed.
             */
            myIndices[curr] = getMinPrior(curr) + 1;
        }
        
        myMinCoins = myIndices[myTarget];
    }
    
    /**
     * Helper method...
     * ...returns the up-to-current-value minimum coins needed.
     * ...updates the coin Purses of each subproblem value.
     * @return the minimum coins needed up to current value.
     */
    private int getMinPrior(int theIndex) {
        
        /* 
         * We're looking for the minimum coins
         * needed to create the value up to now.
         */
        int minimumThroughout = Integer.MAX_VALUE;
       
        /* Store the current coin we are looking at. */
        int coinIndex = 0;
        int bestCoin  = -1;
        
        /* 
         * Because of the dynamically updated minimums,
         * we simply check any coins which leave us with
         * a positive value remaining to verify.
         */
        while (coinIndex < myCoins.length 
                        && myCoins[coinIndex] <= theIndex) {
            
            /* We want to see minimum up to this point. */
            int minimumNow = myIndices[theIndex - myCoins[coinIndex]];
            
            /* Update minimum to this point. */
            if (minimumNow < minimumThroughout) {
                minimumThroughout  = minimumNow;
                bestCoin = myCoins[coinIndex];
            }
            ++coinIndex;
        }
        
        /* Update Purses by previous optimal solution and adding newest coin. */
        try {
            myPurses[theIndex] = myPurses[theIndex - bestCoin].clone();
        }
        catch (final CloneNotSupportedException e) {
            System.out.println("Purse in CoinCounter algorithm could\n"
                             + "not be cloned please try again!");
        }
        myPurses[theIndex].addCoin(bestCoin);
        
        myPurses[theIndex].setBestCoin(bestCoin);
        
        return minimumThroughout;
    }
}
