//Time complexity: O(m*n)
//Space Complexity: O(m*n)
//Did the code run successfully in LeetCode = yes

package com.madhurima;

import java.util.Arrays;
import java.util.LinkedList;


public class SnakeGame {
    int width;
    int height;
    LinkedList<int[]> snakeBody;
    LinkedList<int[]> foodItems;
    int[] snakeHead;

    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        snakeBody = new LinkedList<>();
        foodItems = new LinkedList<>(Arrays.asList(food));
        snakeHead = new int[]{0, 0};
        snakeBody.addLast(snakeHead);
    }

    public int move(String direction) {
        if(direction.equals("U")){
            snakeHead[0]--;
        }else if(direction.equals("D")){
            snakeHead[0]++;
        }else if(direction.equals("L")){
            snakeHead[1]--;
        }else if(direction.equals("R")){
            snakeHead[1]++;
        }

        //check if game is over if snake has gone out of bounds
        if(snakeHead[0] < 0 || snakeHead[0] == height || snakeHead[1] < 0 || snakeHead[1] == width){
            return -1;
        }

        //check if game is over if the head of snake is touching its snakeBody
        for(int i = 1; i < snakeBody.size(); i++){
            int[] curr = snakeBody.get(i);
            if(snakeHead[0] == curr[0] && snakeHead[1] == curr[1]){
                return -1;
            }
        }

        //check if head of snake lies on a food item
        if(foodItems.size() > 0){
            int[] foodItem = foodItems.get(0);
            if(snakeHead[0] == foodItem[0] && snakeHead[1] == foodItem[1]){
                snakeBody.addLast(new int[]{snakeHead[0], snakeHead[1]});
                foodItems.removeFirst();
                return snakeBody.size() - 1;
            }
        }

        snakeBody.addLast(new int[]{snakeHead[0], snakeHead[1]});
        snakeBody.removeFirst();
        return snakeBody.size() - 1;
    }
}