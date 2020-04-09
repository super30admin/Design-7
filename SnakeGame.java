class SnakeGame {

    /** Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
    private int width, height;
int[][] food;
private int score, idx;
private Deque<Integer> snake;
private Set<Integer> body;
public SnakeGame(int width, int height, int[][] food) {
    this.width = width;
    this.height = height;
    this.food = food;
    score = 0;
    idx = 0;
    snake = new LinkedList<>();
    snake.addLast(0);
    body = new HashSet<>();
    body.add(0);
}

public int move(String direction) {
    // first of all, get head
    int h = snake.getFirst() / width, w = snake.getFirst() % width;
    if (direction.equals("U")) h--;
    else if (direction.equals("D")) h++;
    else if (direction.equals("L")) w--;
    else w++;
    if (w < 0 || w == width || h < 0 || h == height) return -1;
    int head = h*width + w;
    snake.addFirst(head); // move head
    if (idx < food.length && h == food[idx][0] && w == food[idx][1]) {
        score++; // and still keep the tail
        idx++;
    } else
        body.remove(snake.removeLast()); // move tail
    if (!body.add(head)) return -1;
    return score;
}
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */


