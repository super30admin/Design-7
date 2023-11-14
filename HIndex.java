//TC will be O(n log n)
//SC will be O(1)

import java.util.Arrays;

class HIndex {
    public int hIndex(int[] citations) {
        if(citations == null || citations.length == 0){
            return 0;
        }

        int n = citations.length;

        Arrays.sort(citations);
        for(int i = 0; i < citations.length; i++){
            if(citations[i] >= n - i){
                return n - i;
            }
        }
        return 0;
    }


    public static void main(String[] args){
        int[] citations = {3,0,6,1,5};
        HIndex obj = new HIndex();
        System.out.println(obj.hIndex(citations));

    }

}