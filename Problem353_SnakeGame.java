/**
 * Time Complexity - O(1)
 * Space Complexity - O(n)
 */

class SnakeGame {

    Set<Integer> set; // copy of snake for faster self body eating case
    Deque <Integer> body;
    int score, foodIndex;
    int[][] food;
    int width;
    int height;
    /** Initialize your data structure here.
     @param width - screen width
     @param height - screen height
     @param food - A list of food positions
     E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
    public SnakeGame(int width, int height, int[][] food) {
        body = new LinkedList<>();
        set = new HashSet<>();
        body.add(0);
        set.add(0);
        score = 0;
        foodIndex = 0;
        this.food = food;
        this.width = width;
        this.height = height;
    }

    /** Moves the snake.
     @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down
     @return The game's score after the move. Return -1 if game over.
     Game over when snake crosses the screen boundary or bites its body. */
    public int move(String direction) {

        int rowHead = body.peekFirst() / width;
        int colHead = body.peekFirst() % width;

        switch (direction) {
            case "U" : rowHead--;
                break;
            case "D" : rowHead++;
                break;
            case "L" : colHead--;
                break;
            default :  colHead++;
        }

        int head = rowHead * width + colHead;
        //case 1: out of boundary or eating body
        set.remove(body.peekLast());    // increment from front and decrement from last (remove tail)

        if(rowHead < 0 || rowHead == height || colHead < 0 || colHead == width || set.contains(head)){
            return -1;
        }

        // case 2 : 3 Add head
        body.offerFirst(head);
        set.add(head);

        //case2: eating food, keep tail
        if(foodIndex < food.length && food[foodIndex][0] == rowHead && food[foodIndex][1] == colHead){
            set.add(body.peekLast());        // Add tail
            foodIndex++;
            return ++score;
        }

        //case3: normal move, remove tail
        body.pollLast();
        return score;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */