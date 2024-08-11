/***
 TC - O(n)
 SC - O(n)
 1. h-index lies between 0 and n
 2. Keep a result array to store the count of citations for each index, if citations crosses n, then increment count at index n
 3. Iterate over the result array from end and sum all the elements anc check if the sum >= index, then return the index.
 */
class HIndex {
    public int hIndex(int[] citations) {
        if(citations == null || citations.length == 0)
            return 0;

        int n = citations.length;
        int[] result = new int[n+1];

        for(int i =0; i < n; i++) {

            if(citations[i] >= n)
                result[n]++;

            else
                result[citations[i]]++;
        }

        int sum=0;

        for(int i=n; i >=0; i--) {
            sum = sum + result[i];
            if(sum >= i)
                return i;
        }

        return 0;

    }
}