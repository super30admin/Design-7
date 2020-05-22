// Time Complexity :  O(n) 
// Space Complexity : O(n) 
// Did this code successfully run on Leetcode : Yes Its working
// Any problem you faced while coding this : No
class SnakeGame {

    /** Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
    int width;
    int height;
    Iterator<int[]> food;
    int[] current_food;
    LinkedList<int[]> body;
    int[] head_position = {0,0};
    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        List<int[]> f = new ArrayList<>(food.length); 
        for(int[] x:food)
        {
            f.add(x);
        }
        this.food = f.iterator();
        this.current_food = this.food.hasNext() ?this.food.next():new int[]{-1,-1};
 
        this.body = new LinkedList<>();
        this.body.add(head_position);
    }
    
    /** Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body. */
    public int move(String direction) {
        int r = -1;
        int c = -1;
        if(direction.equals("U")){
            r = head_position[0]-1;
            c = head_position[1];
          
        }
        else if(direction.equals("L")){
            r = head_position[0];
            c = head_position[1]-1;
        }
        else if(direction.equals("R")) {
            r = head_position[0];
            c = head_position[1]+1;
        }
        else {
            r = head_position[0]+1;
            c = head_position[1];
        }
    
        
        if(r >=0 && r <height && c >=0 && c<width)
        {
            head_position = new int[]{r,c};
            //checking for body collide
            for (int i = 1; i < body.size(); i++) { // skipping head
                int[] tail = body.get(i);
                if (r == tail[0] && c == tail[1]){
                    System.out.println(r+ " "+c+ " "+tail[0]+" "+tail[1]);
                    return -1;
                }
            }
            
            this.body.add(head_position);
            if(r==current_food[0] && c== current_food[1])
            {
                current_food = this.food.hasNext() ?this.food.next():new int[]{-1,-1};
                return body.size()-1;
            }
            else
            {
                this.body.remove(); // shift snake
                return body.size()-1;
            }
        }
        else
        {
            return -1;
        }
        
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */