// Time Complexity : O(1) for each move
// Space Complexity : O(w*h) +O(l)-> for creating boolean visited array and l is the length of the snake body
// Did this code successfully run on Leetcode : Yes

import java.util.LinkedList;

public class SnakeGame {
    LinkedList<int[]> snakeBody;
    boolean[][] visited;
    int h; int w;
    int[][] foodList; int foodIndex;
    int[] dir;

    public SnakeGame(int width, int height, int[][] food) {
        this.h = height;
        this.w = width;
        this.snakeBody = new LinkedList<>();
        this.visited  = new boolean[h][w];
        this.foodList = food;
        this.foodIndex = 0;
        this.dir = new int[]{0,0};
        this.snakeBody.add(dir);
    }

    public int move(String direction) {
        if(direction.equals("U")){
            dir[0]--;
        }else if(direction.equals("D")){
            dir[0]++;
        }else if(direction.equals("R")){
            dir[1]++;
        }else if(direction.equals("L")){
            dir[1]--;
        }


        // check for boundary
        if(dir[0]<0 || dir[0] == h || dir[1]<0 || dir[1] == w) return -1;
        // check for hits
        if(visited[dir[0]][dir[1]]) return -1;

        //check for food
        if(foodIndex<foodList.length && (dir[0]==foodList[foodIndex][0] && dir[1]==foodList[foodIndex][1]))
        {
            snakeBody.addFirst(new int[]{dir[0],dir[1]});
            visited[dir[0]][dir[1]] = true;
            foodIndex++;
        }else{
            snakeBody.addFirst(new int[]{dir[0],dir[1]});
            visited[dir[0]][dir[1]] = true;
            snakeBody.removeLast();
            int[] temp = snakeBody.getLast();
            visited[temp[0]][temp[1]] = false;
        }
        return snakeBody.size()-1;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */