/**
LeetCode Submitted : YES
Space Complexity : O(F) => Number of food slots in the Grid
Time Complexity : O(N*M) Size of the Grid

The idea is to create Linked list of Snake such that when it reaches a food slot then its size increase by swapping its head pointer.
Also, for each position we are ignoring tail of the list as we will not take that into account. Also, need to handle boundary case and case when snake bites itself.
**/


class SnakeGame {
        
        int height;
        int width;
        LinkedList<int[]> snake = new LinkedList<>();
        LinkedList<int[]> foodList;
        int[] head;
    
    /** Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
    public SnakeGame(int width, int height, int[][] food) {
        this.height = height;
        this.width  = width;
        this.foodList = new LinkedList<>(Arrays.asList(food));
        this.head = new int[]{0,0};
        snake.add(head);
    }
    
    /** Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body. */
    public int move(String direction) {
        if(direction.equals("U")){
            head[0] = head[0]-1;
        }else if(direction.equals("D")){
            head[0] = head[0]+1;
        }else if(direction.equals("L")){
            head[1] = head[1]-1;
        }else if(direction.equals("R")){
            head[1] = head[1]+1;
        }
        
        if(head[0]<0 || head[0] >= height || head[1]<0 || head[0] >=width)
            return -1;
        
        
        //System.out.println(head[0]);
        //System.out.println(head[1]);
        //System.out.println("------");
        for(int i = 1; i<snake.size(); i++){
            int[] curr = snake.get(i);
            if(curr[0] == head[0] && curr[1] == head[1])
                return -1;
        }
        
        if(foodList.size() >= 1){
            int[] f = foodList.get(0);
            if(f[0] == head[0] && f[1] == head[1]){
                foodList.remove();
                snake.add(new int[]{f[0],f[1]});
                return snake.size() -1;    
            }
        }
        
        //snake.add(new int[]{head[0],head[1]});
        return snake.size()-1;
        
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */
