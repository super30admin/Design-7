// Time Complexity (TC): O(1) per move operation.
// Space Complexity (SC): O(width * height + food.length).

import java.util.LinkedList;

class SnakeGame {
    LinkedList<int[]> snake;
    int[] snakeHead;
    boolean[][] visited;
    int w;
    int h;
    int[][] foodList;
    int idx; // idx is on food array;

    public SnakeGame(int width, int height, int[][] food) {
        this.w = width;
        this.h = height;
        this.snake = new LinkedList<>();
        this.visited = new boolean[height][width];
        this.foodList = food;
        this.snakeHead = new int[]{0, 0};
        this.snake.addFirst(this.snakeHead);
    }
    
    public int move(String direction) {
        if(direction.equals("U"))
        {
            snakeHead[0]--;
        }
        else if(direction.equals("D"))
        {
            snakeHead[0]++;
        }
        else if(direction.equals("R"))
        {
            snakeHead[1]++;
        }
        else 
        {
            snakeHead[1]--;
        }

        //bounds check
        if(snakeHead[0] < 0 || snakeHead[0] == h || snakeHead[1] < 0 || snakeHead[1] == w)
        {
            return -1;
        }
        //hits itself
        if(visited[snakeHead[0]][snakeHead[1]])
        {
            return -1;
        }
        //eats food
        if(idx < foodList.length)
        {
            if(snakeHead[0] == foodList[idx][0] && snakeHead[1] == foodList[idx][1])
            {
                //eat it
                idx++;
                visited[snakeHead[0]][snakeHead[1]] = true;
                int[] newHead = new int[]{snakeHead[0], snakeHead[1]};
                
                this.snake.addFirst(newHead);
                return snake.size()-1;
            }
        }
        //normal move
            visited[snakeHead[0]][snakeHead[1]] = true;
            int[] newHead = new int[]{snakeHead[0], snakeHead[1]};
            this.snake.addFirst(newHead);

            //remove tail
            this.snake.removeLast();

            //curr tail which will be there make it as false
            int[] currTail = this.snake.peekLast();
            visited[currTail[0]][currTail[1]] = false;

            return snake.size()-1;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */
