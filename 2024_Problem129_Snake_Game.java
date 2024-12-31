//353. Design Snake Game - https://leetcode.com/problems/design-snake-game/

class SnakeGame {
    int width, height;
    LinkedList<int[]> snakeBody; //we need order, thus a list
    boolean[][] visited;
    int[][] food;
    int index; //pointer for food array in input

    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        this.snakeBody = new LinkedList<>();
        this.visited = new boolean[height][width];
        this.food = food;
        this.index = 0;
        snakeBody.addFirst(new int[]{0,0}); //initial position of the snake
    }

    public int move(String direction) {
        //get head of the snake
        int[] snakeHead = snakeBody.getFirst();
        int r = snakeHead[0], c = snakeHead[1]; //row and col of head

        if(direction.equals("D")) {
            r++;
        }else if(direction.equals("U")) {
            r--;
        } else if(direction.equals("L")) {
            c--;
        } else if(direction.equals("R")) {
            c++;
        }
        //snake hits the borders or itself[visited]
        if(r < 0 || r >= height || c < 0 || c >= width || visited[r][c]) {
            return -1;
        }

        if(index < food.length){ //food is available
            //check if food is available at the current r & c position
            if(food[index][0] == r && food[index][1] == c){
                index++; //snake ate food at this index, move 1 step ahead
                snakeBody.addFirst(new int[]{r,c});
                visited[r][c] = true;
                return snakeBody.size()-1;
            }
        }
        //snake is moving
        snakeBody.addFirst(new int[]{r,c});
        visited[r][c] = true;
        snakeBody.removeLast();
        //we are not maintainig tail; so get last and mark it as False
        int[] tail = snakeBody.getLast();
        //first remove the last, then mark it as false
        visited[tail[0]][tail[1]] = false;

        return snakeBody.size() - 1;
    }
}
