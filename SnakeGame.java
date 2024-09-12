// Time Complexity - O(move() calls) * O(snake length) + O(food arr length)
// Space Complexity - O(n) n = snakes size of n nodes in linkedlist
// Approach
// Maintain a LL to store the length of snake and it increases once it eats food. If food is found,
// add it to linkedlist and according to the direction move the snake. Head of snake is updated once
// it either gets food or moves to next position by updating row and col. If snake head moves out of boundary
// or hits itself, return -1. Otherwise, update the snake head at each move() call. Once food is eaten, food linkedlist
// size decreases as food starts disappearing

import java.util.Arrays;
import java.util.LinkedList;

class SnakeGame {

    /** Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
    int h, w;
    LinkedList<int[]> snake;
    LinkedList<int[]> foodList;
    int[] snakeHead;
    public SnakeGame(int width, int height, int[][] food) {
        snake = new LinkedList<>();
        this.h = height;
        this.w = width;
        foodList = new LinkedList<>(Arrays.asList(food));
        snakeHead = new int[] {0,0};
        snake.add(snakeHead);
    }
    
    /** Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body. */
    public int move(String direction) {
        if(direction.equals("U")) snakeHead[0]--;
        if(direction.equals("D")) snakeHead[0]++;
        if(direction.equals("R")) snakeHead[1]++;
        if(direction.equals("L")) snakeHead[1]--;
        if(snakeHead[0] == h || snakeHead[1] == w || snakeHead[0] < 0 || snakeHead[1] < 0) {
            return -1;
        }
        for(int i=1;i<snake.size();i++) {
            int[] node = snake.get(i);
            if(snakeHead[0] == node[0] && snakeHead[1] == node[1]) return -1;
        }
        if(foodList.size() > 0) {
            int[] food = foodList.peek();
            if(snakeHead[0] == food[0] && snakeHead[1] == food[1]) {
                snake.add(foodList.remove());
                return snake.size()-1;
            }
        }
        snake.remove();
        snake.add(new int[]{snakeHead[0],snakeHead[1]});
        return snake.size()-1;
    }
}