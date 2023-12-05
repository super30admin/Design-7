// Time Complexity : O(1)
// Space Complexity : O(1)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : None

import java.util.LinkedList;

public class SnakeGame {
    boolean[][] visited;
    int m;
    int n;
    int[][] food;
    LinkedList<int[]> snake;
    int[] head;
    int idx;
    public SnakeGame(int width, int height, int[][] food){
        this.visited = new boolean[width][height];
        this.m = height;
        this.n = width;
        this.food = food;
        this.snake = new LinkedList<>();
        this.head = new int[]{0, 0};
        snake.addFirst(head);
    }

    public int move(String direction){
        if(direction.equals("R")){
            head[1]++;
        }
        else if(direction.equals("L")){
            head[1]--;
        }
        else if(direction.equals("U")){
            head[0]--;
        }
        else{
            head[0]++;
        }
        //boundary hit check
        if(head[0] < 0 || head[0] == m || head[1] < 0 || head[1] == n)
            return -1;
        //hits itself
        if(visited[head[0]][head[1]])
            return -1;

        //eats food
        if(idx < food.length){
            int[] currfood = food[idx];
            if(currfood[0] == head[0] && currfood[1] == head[1]){
                //eat
                snake.addFirst(new int[]{head[0], head[1]});
                visited[head[0]][head[1]] = true;
                idx++;
                return snake.size() - 1;
            }
        }
        //normal move RULD dirs
        snake.addFirst(new int[]{head[0], head[1]});
        visited[head[0]][head[1]] = true;
        //remove tail mark unvisited
        snake.pollLast();
        int[] tail = snake.peekLast();
        visited[tail[0]][tail[1]] = false;
        return snake.size() - 1;
    }
}
