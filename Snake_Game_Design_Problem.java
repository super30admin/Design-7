class SnakeGame {
    boolean visited[][];
    int []snakehead;
    LinkedList<int []> snake;
    int width;
    int height;
    int [][]food;
    int size;
    int index;
    SnakeGame(int width, int height, int [][]food) {
        this.width = width;
        this.height = height;
        this.visited = new boolean[width][height];
        this.food = food;
        this.snakehead = new int[]{0,0};
        this.snake.addFirst(this.snakehead);
    }

    public int move(String direction) {
        if(direction == "U") {
           snakehead[0]--;

        } else if(direction == "D") {
           snakehead[0]++;
        } else if(direction == "L") {
            snakehead[1]--;
        } else {
            snakehead[1]++;
        }


        //Boundary checks
        if(snakehead[0] < 0 || snakehead[0] == visited.length || snakehead[1] < 0 || snakehead[1] == visited[0].length) {
            return -1;
        }

        if(visited[snakehead[0]][snakehead[1]]) {
            return -1;
        }

        if(index < food.length) {
            if(snakehead[0] == food[index][0] && snakehead[1] == food[index][1]) {
                index++;
                visited[snakehead[0]][snakehead[1]] = true;
                int []newhead = new int[] {snakehead[0],snakehead[1]};
                this.snake.addFirst(newhead);
                return this.snake.size();
            }
        }


        visited[snakehead[0]][snakehead[1]] = true;
        int []newhead = new int[] {snakehead[0],snakehead[1]};
        this.snake.addFirst(newhead);

        this.snake.removeLast();

        int []currentTail = this.snake.peekLast();
        visited[currentTail[0]][currentTail[1]] = false;
        return this.snake.size()-1;





    }
}