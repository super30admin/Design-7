
// In this problem, taking few data structures, we will store the snakehead in int array, store the cordinates where the snakebody
// is currently in a linkedlist so that we can do O(1) insertions and deletions from head and tail, also, keeping a visited matrix
// indicating where the snakebody is currently, so that when snake touches itself we can know from visited matrix. 

// Time Complexity : O(1)
// Space Complexity : O(n) overall and O(1) for move method
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no
import java.util.*;

class SnakeGame {
    // Declare required data structures
    int width;
    int height;
    int[][] food;
    // snakehead array for storing the cordinates of snake's head
    int[] snakeHead;
    // snakebody linkedlist for storing all cordinates of where the snake body is
    // currently
    java.util.LinkedList<int[]> snakeBody;
    // visited boolean matrix for knowing where snake body is currently on board
    boolean[][] visited;
    // idx to iterate over food array
    int idx;

    public SnakeGame(int width, int height, int[][] food) {
        // Initialize them
        this.width = width;
        this.height = height;
        this.food = food;
        // snakehead will be at 0,0 for start and snake will be of length 1
        snakeHead = new int[] { 0, 0 };
        // snakebody is of length 1 only with 0,0 as the position
        snakeBody = new java.util.LinkedList<>();
        // So add 0,0 to the linkedlist at head
        snakeBody.addFirst(new int[] { 0, 0 });
        // visited will be of size same as grid
        this.visited = new boolean[height][width];
        // Since snake is at 0,0, make it as true
        visited[0][0] = true;
        // Food's first location is given at index 0
        idx = 0;
    }

    public int move(String direction) {
        // According to the direction that snake moved, update it's head location
        // So for up, row will decrement by 1 and column which is given by snakehead[1]
        // will remain same
        if (direction == "U") {
            snakeHead[0]--;
        }
        // So for down, row will increment by 1 and column which is given by
        // snakehead[1] will remain same
        else if (direction == "D") {
            snakeHead[0]++;
        }
        // So for left, column will decrement by 1 and row which is given by
        // snakehead[0] will remain same
        else if (direction == "L") {
            snakeHead[1]--;
        }
        // So for right, column will increment by 1 and row which is given by
        // snakehead[0] will remain same
        else if (direction == "R") {
            snakeHead[1]++;
        }
        // Check if the new head is touching the wall?
        if (snakeHead[0] < 0 || snakeHead[0] == height || snakeHead[1] < 0 || snakeHead[1] == width) {
            // In that case game over and return -1
            return -1;
        }
        // Check if the new head is going to a place where snakebody is, that means it
        // is touching itself
        if (visited[snakeHead[0]][snakeHead[1]] == true) {
            // In that case, game over so return -1
            return -1;
        }
        // Else check if the new head is going to a cell where there is a food
        if (idx < food.length) {
            if (snakeHead[0] == food[idx][0] && snakeHead[1] == food[idx][1]) {
                // Move the index for next food location
                idx++;
                // In that case, snakebody length will increase
                // Save it's head location in one variable and add this cordinates to the
                // linkedlist
                int[] head = new int[] { snakeHead[0], snakeHead[1] };
                snakeBody.addFirst(head);
                // In visited mark it as true
                visited[snakeHead[0]][snakeHead[1]] = true;
                // For score, return size - 1, doing -1 because when we started game snakebody
                // was already of length 1 and score was 0, and body only increased when snake
                // ate some food, so does score
                return snakeBody.size() - 1;
            }
        }
        // If food is not present at that location, it is a normal move than, we need to
        // add it in linkedlist and also we need to remove the last cordinates in
        // linkedlist because snakebody is not growing here, it is moving from one
        // location to other
        int[] head = new int[] { snakeHead[0], snakeHead[1] };
        // So add to first
        snakeBody.addFirst(head);
        // Then get the cordinates of last in ll
        int[] tail = snakeBody.getLast();
        // In visited mark this is false, since bodypart or tail of snake is no longer
        // here
        visited[tail[0]][tail[1]] = false;
        // Also, remove it from LL
        snakeBody.removeLast();
        // Mark the new head position as true in visited
        visited[snakeHead[0]][snakeHead[1]] = true;
        // For score, return size - 1
        return snakeBody.size() - 1;
    }

    public static void main(String[] args) {
        SnakeGame s = new SnakeGame(3, 2, new int[][] { { 1, 2 }, { 0, 1 } });
        System.out.println(s.move("R"));
        System.out.println(s.move("D"));
        System.out.println(s.move("R"));
        System.out.println(s.move("U"));
        System.out.println(s.move("L"));
        System.out.println(s.move("U"));

    }
}