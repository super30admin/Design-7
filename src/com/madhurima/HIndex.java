//Time complexity: O(nlogn) with sorting, O(n) without sorting
//Space Complexity: O(1) with sorting, O(n) without sorting
//Did the code run successfully in LeetCode = yes

package com.madhurima;

import java.util.Arrays;

public class HIndex {
}


class HIndexBySorting {

    //by sorting citations array
    public int hIndex(int[] citations) {
        if(citations == null || citations.length == 0){
            return 0;
        }

        int n = citations.length;
        Arrays.sort(citations);

        for(int i = 0;  i < n; i++){
            int diff = n - i; //number of papers having citations[i] or more citations
            if(citations[i] >= diff){
                return diff;
            }
        }

        return 0;

    }
}

class HIndexWithoutSorting {

    //without sorting
    public int hIndex(int[] citations) {
        if(citations == null || citations.length == 0){
            return 0;
        }

        int n = citations.length;
        int[] bucket = new int[n+1];

        for(int i = 0; i < n; i++){
            if(citations[i] > n){
                bucket[n]++;
            }else{
                bucket[citations[i]]++;
            }
        }

        int sum = 0;

        for(int i = n; i >= 0; i--){
            sum = sum + bucket[i];
            if(sum >= i){ //total papers
                return i; //h-index
            }
        }

        return 0;

    }
}