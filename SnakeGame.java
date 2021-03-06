//Time Complexity: 0(n)
//Space Complexity: 0(n)
class SnakeGame {
    int width; int height;
    int score = 0;
    LinkedList<int []> snake;
    LinkedList<int []> food1;
    int[] head;
    
    /** Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        food1 = new LinkedList<>(Arrays.asList(food));
        snake = new LinkedList<>();
        head = new int[]{0,0};
        snake.add(head);
    }
    
    /** Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body. */
    public int move(String direction) {
        if(direction.equals("U"))
        {
            head[0]--;
        }
        if(direction.equals("D"))
        {
            head[0]++;
        }
        if(direction.equals("L"))
        {
            head[1]--;
        }
        if(direction.equals("R"))
        {
            head[1]++;
        }
        //Check for bounds
        if(head[0] < 0 || head[0] >= height || head[1] < 0 || head[1] >= width) return -1;
        
        //check for collision 0 is the tail index in the linkedlist as we add to end of the list when we do add
        //so ignoring the 0 index for edge case
        for(int i =1; i < snake.size() ; i++)
        {
            int[] mat = snake.get(i);
            if(head[0] == mat[0] && head[1] == mat[1]) return -1;
        }
        //traverse food one by one
        if(!food1.isEmpty())
        {
            int[] val = food1.peek();
            if(head[0] == val[0] && head[1] == val[1])
            {
                snake.add(food1.remove());
                return snake.size() -1;
            }
        }
        //just move the snake
        snake.remove();
        snake.add(new int[]{head[0], head[1]});
        return snake.size()-1;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */