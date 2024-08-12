//TC - O(1) - addToFirst and Remove Last are O(1) operations
//SC - O(height * width)
class SnakeGame {

    LinkedList<int[]> snakeBody;
    boolean[][] visited;
    int[][] food;
    int idx;
    int[] snakeHead;
    int width;
    int height;
    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        this.snakeBody = new LinkedList<>();
        this.visited = new boolean[height][width];
        this.snakeBody.addFirst(new int[]{0,0});
        this.idx = 0;
        this.snakeHead = new int[]{0,0};
        this.food = food;

    }
    
    public int move(String direction) {
        if(direction.equals("U")){
            snakeHead[0]--;
        }
        else if(direction.equals("D")){
            snakeHead[0]++;
        }
        else if(direction.equals("L")){
            snakeHead[1]--;
        }
        else if(direction.equals("R")){
            snakeHead[1]++;
        }

        //check if snake went out of bounds
        if(snakeHead[0] < 0 || snakeHead[0] == height || snakeHead[1] == width || snakeHead[1] < 0){
            return -1;
        }
        //touches body
        if(visited[snakeHead[0]][snakeHead[1]] == true){
            return -1;
        }
        //on eating food
        if(idx < food.length){
            if(food[idx][0] == snakeHead[0] && food[idx][1] == snakeHead[1]){
                int[] head = new int[]{snakeHead[0],snakeHead[1]};
                snakeBody.addFirst(head);
                visited[snakeHead[0]][snakeHead[1]] = true;
                idx++;
                return snakeBody.size() - 1;

            }
        }
        //not eat food
        int[] head = new int[] {snakeHead[0], snakeHead[1]};
        snakeBody.addFirst(head);
        visited[snakeHead[0]][snakeHead[1]] = true;
        snakeBody.removeLast();
        int[] tail = snakeBody.getLast();
        visited[tail[0]][tail[1]] = false;
        return snakeBody.size() - 1;

    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */