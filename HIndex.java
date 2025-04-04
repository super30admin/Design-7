/*
 * Given an array of citations (each citation is a non-negative integer) of a researcher,
 * write a function to compute the researcher's h-index.
 * According to the definition of h-index on Wikipedia: "A scientist has index h if h of his/her N papers have at least h citations each,
 * and the other N − h papers have no more than h citations each."
 *
 * TC: O(n)
 * SC: O(n)
 */
public class HIndex {
    public int hIndex(int[] citations) {
        int n = citations.length;
        // use counting sort
        int[] count = new int[n + 1];

        for(int c: citations) {
            if(c >= n) {
                // put all citations >= n in the last bucket
                // since max hIndex can be n
                count[n]++;
            } else {
                count[c]++;
            }
        }

        int total = 0;
        for(int i = n; i >= 0; i--) {
            // gather the count of papers
            // with i or more citations
            total += count[i];
            // the first time this total >= i
            // is our hIndex
            if(total >= i) {
                return i;
            }
        }
        return 0;
    }
}
