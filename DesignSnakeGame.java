
// Time Complexity : O(n)
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this :

// Your code here along with comments explaining your approach
class SnakeGame {
    
    private int width;
    private int height;
    private int score;

    private List<int[]> foodPos;
    private Map<String, int[]> dirMap;
    private Queue<int[]> queue = new LinkedList<>();
    private Deque<String> deque;


    /** Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        this.score = 0;
        this.dirMap = createDirMap();
        this.foodPos = new ArrayList<>(Arrays.asList(food));
        this.deque = new LinkedList<>();
    
        queue.add(new int[]{0,0});
    }
    
    /** Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body. */
    public int move(String direction) {
        int[] currentPos = queue.remove();
        int[] currentFood = foodPos.size() > 0 ? foodPos.get(0) : new int[]{-1,-1};

        int[] next = dirMap.get(direction);

        int x = currentPos[0] + next[0];
        int y = currentPos[1] + next[1];
        queue.add(new int[]{x,y});

        if(x == height || y == width || x < 0 || y < 0 || deque.contains((x + "" + y)))
            return -1;

        deque.add(x + "" + y);

        if(currentFood[0] == x && currentFood[1] == y){
            foodPos.remove(0);
            score++;
        } else if(deque.size() > score)
                deque.removeFirst();

        return score;
    }
    
    private Map<String, int[]> createDirMap(){
        Map<String, int[]> map = new HashMap<>();
        map.put("U", new int[]{-1,0});
        map.put("L", new int[]{0,-1});
        map.put("R", new int[]{0,1});
        map.put("D", new int[]{1,0});
        return map;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */