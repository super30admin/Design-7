using System;
using System.Collections.Generic;
using System.Text;

namespace Design
{
    internal class SnakeGameLC
    {
        //TC: O(n)
        //SC:O(n)
        class SnakeGame
        {
            LinkedList<int[]> snakeBody;
            LinkedList<int[]> foodItems;
            int[] snakeHead;
            int width, height;

            public SnakeGame(int width, int height, int[][] food)
            {
                this.width = width;
                this.height = height;
                snakeHead = new int[] { 0, 0 };
                snakeBody = new LinkedList<int[]>();
                snakeBody.AddFirst(snakeHead);
                foodItems = new LinkedList<int[]>((food));
            }
            public int move(string direction)
            {
                if (direction.Equals("U")) snakeHead[0]--;
                else if (direction.Equals("D")) snakeHead[0]++;
                else if (direction.Equals("L")) snakeHead[1]--;
                else if (direction.Equals("R")) snakeHead[1]++;
                //Is it touching the boundary
                if (snakeHead[0] == height || snakeHead[0] < 0 || snakeHead[1] == width || snakeHead[1] < 0)
                {
                    return -1;
                }
                //Is the snake touching itself
                foreach (int[] obj in snakeBody)
                {
                    int[] curr = obj;
                    if (curr[0] == snakeHead[0] && curr[1] == snakeHead[1])
                    {
                        return -1;
                    }
                }
               
                //Snake Eats food
                if (foodItems.Count !=0)
                {
                    int[] foodItem = foodItems.First.Value;
                    if (snakeHead[0] == foodItem[0] && snakeHead[1] == foodItem[1])
                    {
                        foodItems.RemoveFirst();
                        snakeBody.AddFirst(foodItem);
                        return snakeBody.Count - 1;
                    }
                }
                snakeBody.RemoveLast();
                snakeBody.AddFirst(new int[] { snakeHead[0], snakeHead[1] });
                return snakeBody.Count - 1;
            }
        }
    }
}
