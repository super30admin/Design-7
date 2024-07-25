class Solution {
    public int hIndex(int[] citations) {
        // Tc: O(n) Sc: O(n)
        int n = citations.length;

        int freq[] = new int[n + 1];

        for (int i : citations) {
            if (i > n) {
                freq[n]++;
            } else {
                freq[i]++;
            }
        }

        int cnt = 0;
        for (int i = n; i >= 0; i--) {
            cnt += freq[i];

            if (cnt >= i)
                return i;
        }
        return 0;
    }
}

/*
 * //Tc: O(nlogn)
 * int i = 0;
 * Arrays.sort(citations);
 * int n = citations.length;
 * while(i < n && citations[n-i-1] > i)
 * {
 * i++;
 * }
 * 
 * return i;
 * 
 */