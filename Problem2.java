// Time Complexity : O(1)
// Space Complexity : O(m+n) where m and n are height and width for storing the food and current snake body
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no


// Your code here along with comments explaining your approach

/**
 * Using a linked list to maintain the position of snake's body within the matrix. When snake moves, head goes ahead by one pointer and tail gets removed if there is no food.
 * When there is food, we are going to increase the head while keeping the tail same since the length needs to increase.
 * Using a hash set to check the if current position is snake's body since the game needs to end when snake eats itself.
 */

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */
public class SnakeGame {
    int m;
    int n;
    int[][] food;
    LinkedList<Node> snake;
    Set<Node> snakeSet;
    int foodIdx = 0;
    int foodSize;

    record Node(int x, int y) {
    }

    public SnakeGame(int width, int height, int[][] food) {
        this.m = height;
        this.n = width;
        this.food = food;
        this.foodSize = food.length;
        this.snake = new LinkedList<>();
        this.snakeSet = new HashSet<>();
        // adding the entry node at last
        Node node = new Node(0, 0);
        snake.addLast(node);
        snakeSet.add(node);
    }

    public int move(String direction) {
        Node head = snake.getLast();
        int x = head.x;
        int y = head.y;
        if (direction.equals("D")) {
            x++;
            // reached lower bound
            if (x == m) return -1;
        } else if (direction.equals("U")) {
            x--;
            if (x == -1) return -1;
        } else if (direction.equals("R")) {
            y++;
            if (y == n) return -1;
        } else if (direction.equals("L")) {
            y--;
            if (y == -1) return -1;
        }

        // getting the tail of snake from first
        Node tail = snake.getFirst();
        // if it is food, then move the foodIdx to unlock the next food location without moving the tail
        if (foodIdx < foodSize && food[foodIdx][0] == x && food[foodIdx][1] == y) {
            foodIdx++;
        } else {
            // if it is not food, then we have to remove the tail since snake will be moving one position ahead
            snake.removeFirst();
            snakeSet.remove(tail);
        }

        Node newHead = new Node(x, y);

        // snake eats itself
        if (snakeSet.contains(newHead)) return -1;

        // add the new head for moving to new position
        snake.addLast(newHead);
        snakeSet.add(newHead);

        return snake.size() - 1;
    }
}

