using System;
namespace Algorithms
{
    public class BestTimeToBuyAndSellStocks
    {
        /// Time Complexity : O(N) 
        // Space Complexity :O(1) 
        // Did this code successfully run on Leetcode :Yes
        // Any problem you faced while coding this :  No 
        public int MaxProfit(int[] prices)
        {
            if (prices.Length == 0) return 0;
            int maxProfit = 0;
            int peek = prices[0];
            int valley = prices[0];
            int i = 0;
            while (i < prices.Length - 1)
            {
                while (i < prices.Length - 1 && prices[i] >= prices[i + 1])
                {
                    i++;
                }
                valley = prices[i];
                while (i < prices.Length - 1 && prices[i] <= prices[i + 1])
                {
                    i++;
                }
                peek = prices[i];
                maxProfit += peek - valley;
            }
            return maxProfit;
        }
    }
}
