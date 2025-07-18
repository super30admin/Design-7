// TC: O(1)
// SC: O(m*n)
public class SnakeGame {
    int[][] foodList;
    int i;
    LinkedList<int[]> snake;
    int[] snakeHead;
    boolean[][] visited;
    int w;
    int h;
    public SnakeGame(int width, int height, int[][] food) {
        this.foodList = food;
        this.w = width;
        this.h = height;
        this.visited = new boolean[height][width];
        this.snake = new LinkedList<>();
        this.snakeHead = new int [] {0,0};
        this.snake.addFirst(snakeHead);
    }

    /**
     * @param direction: the direction of the move
     * @return: the score after the move
     */
    public int move(String direction) {
        // write your code here

        if(direction.equals("R")){
            snakeHead[1] = snakeHead[1] +1;
        }else if(direction.equals("D")){
            snakeHead[0] = snakeHead[0] +1;
        }else if(direction.equals("U")){
            snakeHead[0] = snakeHead[0] -1;
        }else{
            snakeHead[1] = snakeHead[1] -1;
        }

        if(snakeHead[1] < 0 || snakeHead[1] == w || snakeHead[0] < 0 || snakeHead[0] == h){
            return -1;
        }

        if(visited[snakeHead[0]][snakeHead[1]] == true){
            return -1;
        }

        // eat food;
        if(i < foodList.length){
            i++;
            int[] currFood = foodList[i];
            if(snakeHead[0] == currFood[0] && snakeHead[1] == currFood[1] ){
                int[] head = new int[]{snakeHead[0], snakeHead[1]};
                snake.addFirst(head);
                visited[snakeHead[0]][snakeHead[1]] = true;
                return snake.size() -1;
            }
        }



        int[] head = new int[]{snakeHead[0], snakeHead[1]};
        snake.addFirst(head);
        visited[snakeHead[0]][snakeHead[1]] = true;

        snake.removeLast();
        int[] newTail = snake.getLast();
        visited[newTail[0]][newTail[1]] = false;

        return snake.size() -1;

    }
}