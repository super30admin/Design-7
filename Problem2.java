/*
274. H-Index
Ran on leetcode: Yes
TC: O(n)
SC: O(n)
Sort based on citations, each value represents the number of papers with exactly that many citations. From the last bucket count the number of papers with atleast i citations. if the number of papers is >= i retun the h index.
*/
class Solution {
    public int hIndex(int[] citations) {

        // sort based on citations, each index represent how many papers are present with that index citations
        int[] buckets = new int[citations.length + 1];

        for(int cit: citations){ // count papers with exactly i citation
            if(cit >= citations.length){ // if more than citations.length citation add to last bucket since h index cant be bigger
                buckets[citations.length]++;
                continue;
            }
            buckets[cit]++;
        }

        int papers = 0;

        // Count the number of papers with atleast i number of citations, if number of papers is >= citations return the h index 
        for(int i = citations.length; i >= 0; i--){
            papers += buckets[i];
            if(papers >= i){
                return i;
            }
        }

        return -1;
    }
}