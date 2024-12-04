//Leetcode Problem 353: Design Snake Game
//T.C: O(mxn) :: S.C: O(mxn)

import java.util.Arrays;
import java.util.LinkedList;

public class DesignSnakeGame {
    
    int width, height;
    int[] snakeHead;
    LinkedList<int[]> snakeBody;
    LinkedList<int[]> foodItems;
    
    public DesignSnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        snakeHead = new int[] {0, 0};
        snakeBody = new LinkedList<>();
        foodItems = new LinkedList<>(Arrays.asList(food));
        snakeBody.addLast(snakeHead);
    }
    
    public int move(String direction) {
        if (direction.equals("U")) {
            snakeHead[0]--;
        } else if (direction.equals("D")) {
            snakeHead[0]++;
        } else if (direction.equals("L")) {
            snakeHead[1]--;
        } else if (direction.equals("R")) {
            snakeHead[1]++;
        }
        //check if the snake has gone out of bounds
        if (snakeHead[0] == height || snakeHead[0] < 0 || snakeHead[1] == width || snakeHead[1] < 0) {
            return -1;
        }

        //check if the snake is touching itself
        for (int i = 1; i < snakeBody.size(); i++) {
            int[] curr = snakeBody.get(i);
            if (curr[0] == snakeHead[0] && curr[1] == snakeHead[1]) {
                return -1;
            }
        }
        //check if food appears in front of snake
        if (!foodItems.isEmpty()) {
            int[] foodLocation = foodItems.get(0);
            if (snakeHead[0] == foodLocation[0] && snakeHead[1] == foodLocation[1]) {
                snakeBody.addLast(new int[] {snakeHead[0], snakeHead[1]});
                foodItems.removeFirst();
                
                return snakeBody.size() - 1;
            }
        }
        //If the snake is moving to an empty space
        snakeBody.addLast(new int[] {snakeHead[0], snakeHead[1]});
        snakeBody.removeFirst();
        
        return snakeBody.size() - 1;
    }
    
    public static void main(String[] args) {
        
        int[][] food = new int[][] {{2, 2}, {1, 0}, {0, 0}};
        
        DesignSnakeGame snakeGame = new DesignSnakeGame(4, 4, food);
        
        System.out.println(snakeGame.move("D"));
        System.out.println(snakeGame.move("D"));
        System.out.println(snakeGame.move("R"));
        System.out.println(snakeGame.move("R"));
        System.out.println(snakeGame.move("U"));
        System.out.println(snakeGame.move("L"));
        System.out.println(snakeGame.move("L"));
        System.out.println(snakeGame.move("U"));
        System.out.println(snakeGame.move("U"));
        
    }
}

/*
 * Output:
harsh@harsh:~/Documents/GitHub_Local/Masters_Courses/S30_Homework/Design-7$ javac DesignSnakeGame.java
harsh@harsh:~/Documents/GitHub_Local/Masters_Courses/S30_Homework/Design-7$ java DesignSnakeGame
0
0
0
1
1
1
2
3
-1
 * 
 */