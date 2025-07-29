import java.util.LinkedList;

// Time Complexity: O(1) for move
// Space Complexity: O(n) where n is the length of the snake

// Intuition: We use linkedlist to represent the snake's body, where the head is at the front and the tail is at the back.
// We also keep track of the food in an array and an index to know which food we are currently targeting.
// We move the snake by updating the head position based on the direction and checking for collisions with walls or itself.
// If the snake eats food, we add a new segment to the front and do not remove the tail.
// If it does not eat food, we add a new segment to the front and remove the tail segment.
// We return the length of the snake after each move.

class SnakeGame {
    LinkedList<int[]> body;
    int w, h;
    int[][] food;
    int idx;

    public SnakeGame(int width, int height, int[][] food) {
        this.h = height;
        this.w = width;
        this.food = food;
        this.idx = 0;
        this.body = new LinkedList<>();
        body.addFirst(new int[] { 0, 0 });
    }

    public int move(String direction) {
        int[] head = body.getFirst();
        int r = head[0], c = head[1];
        if (direction.equals("L")) {
            c--;
        } else if (direction.equals("R")) {
            c++;
        } else if (direction.equals("D")) {
            r++;
        } else if (direction.equals("U")) {
            r--;
        }

        if (r < 0 || c < 0 || r == h || c == w)
            return -1;

        for (int i = 0; i < body.size() - 1; i++) {
            int[] temp = body.get(i);
            if (temp[0] == r && temp[1] == c) {
                return -1;
            }
        }

        if (idx < food.length) {
            if (food[idx][0] == r && food[idx][1] == c) {
                body.addFirst(new int[] { r, c });
                idx++;
                return body.size() - 1;
            }
        }

        body.addFirst(new int[] { r, c });
        body.removeLast();
        return body.size() - 1;
    }
}
