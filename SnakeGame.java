/**
 * Simulates Snake Game using a LinkedList to represent the snake body and track head/tail movement.
 * Key idea is to move the head based on direction, conditionally grow on food, and avoid collisions with boundaries/body.
 * Uses careful ordering: allow moving into tail position by removing tail before collision check when not growing.
 *
 * Time Complexity: O(n) per move (due to body collision check)
 * Space Complexity: O(n) for snake body
 */
class SnakeGame {
    LinkedList<int[]> snakeBody;
    int w, h;
    int[][] food;
    int idx;

    public SnakeGame(int width, int height, int[][] food) {
        this.h = height;
        this.w = width;
        this.food = food;
        this.idx = 0;
        this.snakeBody = new LinkedList<>();
        snakeBody.addFirst(new int[]{0, 0});
    }

    public int move(String direction) {
        int[] head = snakeBody.getFirst();
        int r = head[0], c = head[1];

        // Compute new head position
        if (direction.equals("L")) c--;
        else if (direction.equals("R")) c++;
        else if (direction.equals("D")) r++;
        else if (direction.equals("U")) r--;

        // Boundary check
        if (r < 0 || c < 0 || r == h || c == w) return -1;

        // Check if we are eating food
        boolean isFood = (idx < food.length && food[idx][0] == r && food[idx][1] == c);

        // Key insight: if NOT eating, remove tail first so moving into tail cell is allowed
        if (!isFood) {
            snakeBody.removeLast();
        }

        // Now check self-collision (safe because tail already removed if needed)
        for (int[] part : snakeBody) {
            if (part[0] == r && part[1] == c) {
                return -1;
            }
        }

        // Add new head
        snakeBody.addFirst(new int[]{r, c});

        // If food eaten, increment index (snake grows automatically)
        if (isFood) {
            idx++;
        }

        return snakeBody.size() - 1;
    }
}