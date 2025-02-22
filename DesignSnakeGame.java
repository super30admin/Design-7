// tc: move - O(1)
// sc: O(W x H + N)

class SnakeGame {

    LinkedList<int[]> snake;

    int[] head;

    int i; // moving on the food arr;

    int w;
    int h;

    int[][] foodList;

    boolean[][] visited;

    public SnakeGame(int width, int height, int[][] food) {
        this.visited = new boolean[height][width];

        this.w = width;
        this.h = height;

        this.head = new int[] { 0, 0 };

        this.foodList = food;

        this.snake = new LinkedList<>();

        this.snake.addLast(new int[] { head[0], head[1] });
    }

    public int move(String direction) {
        if (direction.equals("U")) {
            head[0]--;
        } else if (direction.equals("D")) {
            head[0]++;
        } else if (direction.equals("R")) {
            head[1]++;
        } else if (direction.equals("L")) {
            head[1]--;
        }

        // hits borders

        if (head[0] < 0 || head[0] == h || head[1] < 0 || head[1] == w)
            return -1;

        // hits itself

        // for(int j = 1; j < snake.size(); j++){

        // int [] curr = snake.get(j);

        // if(curr[0] == head[0] && curr[1] == head[1]){

        // return -1;

        // }

        // }

        if (visited[head[0]][head[1]])
            return -1;

        // eats food

        if (i < foodList.length) {
            int[] currFood = foodList[i];

            if (currFood[0] == head[0] && currFood[1] == head[1]) {
                visited[head[0]][head[1]] = true;

                snake.addLast(new int[] { head[0], head[1] });

                i++;

                return snake.size() - 1;
            }
        }

        // normal move

        // remove the head of linkedList || tail of snake

        if (snake.size() > 1) {
            int[] toRemove = snake.get(1);

            visited[toRemove[0]][toRemove[1]] = false;
        }

        snake.removeFirst(); // removing tail of snake

        snake.addLast(new int[] { head[0], head[1] });

        if (snake.size() > 1) {
            visited[head[0]][head[1]] = true;
        }

        return snake.size() - 1;
    }
}
