package cas2xb3_finalprototype;

public class Quick {
	
	private Quick() { }

    /**
     * Rearranges the array in ascending order, using the natural order.
     * @param a the array to be sorted
     */
    public static void sort(LuggageT[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
        assert isSorted(a);
    }

    // quicksort the subarray from a[lo] to a[hi]
    private static void sort(LuggageT[] a, int lo, int hi) { 
        if (hi <= lo) return;
        int j = partition(a, lo, hi);
        sort(a, lo, j-1);
        sort(a, j+1, hi);
        assert isSorted(a, lo, hi);
    }

    // partition the subarray a[lo..hi] so that a[lo..j-1] <= a[j] <= a[j+1..hi]
    // and return the index j.
    private static int partition(LuggageT[] a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        LuggageT v = new LuggageT();;
        v = a[lo];
        String claim1 = v.getClaimNumber();
        while (true) { 

            // find item on lo to swap
        	String claim2 = a[++i].getClaimNumber();
            while (less(claim2, claim1))
                if (i == hi) break;

            // find item on hi to swap
            String claim2_1 = a[--j].getClaimNumber();
            while (less(claim1, claim2_1))
                if (j == lo) break;      // redundant since a[lo] acts as sentinel

            // check if pointers cross
            if (i >= j) break;

            exch(a, i, j);
        }

        // put partitioning item v at a[j]
        exch(a, lo, j);

        // now, a[lo .. j-1] <= a[j] <= a[j+1 .. hi]
        return j;
    }

    /**
     * Rearranges the array so that {@code a[k]} contains the kth smallest key;
     * {@code a[0]} through {@code a[k-1]} are less than (or equal to) {@code a[k]}; and
     * {@code a[k+1]} through {@code a[n-1]} are greater than (or equal to) {@code a[k]}.
     *
     * @param  a the array
     * @param  k the rank of the key
     * @return the key of rank {@code k}
     */
    public static LuggageT select(LuggageT[] a, int k) {
        if (k < 0 || k >= a.length) {
            throw new IndexOutOfBoundsException("Selected element out of bounds");
        }
        StdRandom.shuffle(a);
        int lo = 0, hi = a.length - 1;
        while (hi > lo) {
            int i = partition(a, lo, hi);
            if      (i > k) hi = i - 1;
            else if (i < k) lo = i + 1;
            else return a[i];
        }
        return a[lo];
    }



   /***************************************************************************
    *  Helper sorting functions.
    ***************************************************************************/
    
    // is v < w ?
    private static boolean less(String v, String w) {
        return v.compareTo(w) < 0;
    }
        
    // exchange a[i] and a[j]
    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }


   /***************************************************************************
    *  Check if array is sorted - useful for debugging.
    ***************************************************************************/
    private static boolean isSorted(LuggageT[] a) {
        return isSorted(a, 0, a.length - 1);
    }

    private static boolean isSorted(LuggageT[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i].getClaimNumber(), a[i-1].getClaimNumber())) return false;
        return true;
    }


    // print array to standard output
    private static void show(LuggageT[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }

    /**
     * Reads in a sequence of strings from standard input; quicksorts them; 
     * and prints them to standard output in ascending order. 
     * Shuffles the array and then prints the strings again to
     * standard output, but this time, using the select method.
     *
     * @param args the command-line arguments
     */
    
    public static void main(String[] args) throws Exception {
    	LuggageT[] b = new LuggageT[1000000];
    	FileReading a = new FileReading();
    	b = a.luggage();
    	Quick.sort(b);
		System.out.println(b[0]);
	}
}


