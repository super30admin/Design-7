//TC: O(1) for move.

class SnakeGame {
    LinkedList<int[]> snake;
    int[] snakeHead;
    int idx;
    boolean[][] visited;
    int h; int w;
    int[][] food;

    public SnakeGame(int width, int height, int[][] food) {
        this.snake = new LinkedList<>();
        this.snakeHead = new int[] {0,0};
        this.snake.addFirst(this.snakeHead);
        this.visited = new boolean[height][width];
        this.h = height;
        this.w = width;
        this.food = food;
    }
    
    public int move(String direction) {
        if(direction.equals("U")){
            snakeHead[0]--;
        }else if(direction.equals("D")){
            snakeHead[0]++;
        }else if(direction.equals("L")){
            snakeHead[1]--;           
        }else{
            snakeHead[1]++;
        }

        //bound check
        if(snakeHead[0] < 0 || snakeHead[0] >= h || snakeHead[1] < 0 || snakeHead[1] >= w){
            return -1;
        }
        //snake touch its body
        if(visited[snakeHead[0]][snakeHead[1]]){
            return -1;
        }

        //food
        if(idx < food.length){
            //eat food
            if(snakeHead[0] == food[idx][0] && snakeHead[1] == food[idx][1]){
                snake.addFirst(new int[]{snakeHead[0],snakeHead[1]});
                visited[snakeHead[0]][snakeHead[1]] = true;
                idx++;

                return snake.size()-1;
            }
        }

        snake.addFirst(new int[]{snakeHead[0],snakeHead[1]});
        visited[snakeHead[0]][snakeHead[1]] = true;
        //move forward - remove tail
        snake.removeLast();
        int[] currTail = snake.peekLast();
        visited[currTail[0]][currTail[1]] = false; // mark new tail unvisited

        return snake.size()-1;
        
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */