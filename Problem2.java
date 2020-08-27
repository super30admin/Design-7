//Time Complexity : O(len(snake)) 
//Space Complexity : O(n) + O(m) , m = number of food items

class SnakeGame {
    LinkedList<int[]> snake;
    LinkedList<int[]> food;
    HashSet<int[]> set;
    int[] head;
    int height, width;

    SnakeGame(int height, int width, int[][] food) {
        snake = new LinkedList<>();
        set = new HashSet<>();
        this.food = (LinkedList<int[]>) Arrays.asList(food);
        head = new int[] { 0, 0 };
        this.height = 0;
        this.width = 0;
        snake.add(head);
        set.add(head);
    }
    
    // O(n) , n = snake length
    int move(String dir) {
        if (dir.equals("U"))
            head[0] -= 1;
        else if (dir.equals("D"))
            head[0] += 1;
        else if (dir.equals("L"))
            head[1] -= 1;
        else if (dir.equals("R"))
            head[1] += 1;
        else
            return Integer.MIN_VALUE; // whwn given string is incorrect.

        // hit boundary
        if (!isValid(head)) {
            return -1;
        }

        // hits itself
        for (int i = 1; i < snake.size(); i++) {
            int[] node = snake.get(i);
            if (head[0] == node[0] && head[1] == node[1])
                return -1;
        }

        // eats food
        if (!food.isEmpty()) {
            int[] node = food.getFirst();
            if (head[0] == node[0] && head[1] == node[1]) {
                snake.add(food.removeFirst());
                return snake.size() - 1;
            }
        }

        // not eat food
        snake.removeFirst();
        snake.add(head);
        return snake.size() - 1;
    }

    boolean isValid(int[] node) {
        return node[0] >= 0 && node[0] < height && node[1] >= 0 && node[1] < width;
    }
}
