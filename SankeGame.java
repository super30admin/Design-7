//Time Complexity :O(N) for move
//Space Complexity :O(N)
//Did this code successfully run on Leetcode :yes
//Any problem you faced while coding this : Got issues when food size became zero. Later refered back to the class.


//Your code here along with comments explaining your approach
class SnakeGame {

    /** Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
    LinkedList<int[]> snake;
    int width, height;
    LinkedList<int[]> f;
    int[] head;
    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        f = new LinkedList<>();
        f.addAll(Arrays.asList(food));
        snake = new LinkedList<>();
        head = new int[2];
        head[0] = 0;head[1] = 0;
        snake.add(head);
    }
    
    /** Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body. */
    public int move(String direction) {
        if(direction.equals("U")){
            head[0] -= 1;
        }else if(direction.equals("L")){
            head[1] -= 1;
        }else if(direction.equals("D")){
            head[0] += 1;
        }else if(direction.equals("R")){
            head[1] += 1;
        }
        //System.out.print(" Head "+head[0]+" , "+head[1]);
        if(head[0] < 0 || head[1] == width || head[1] < 0 || head[0] == height){return -1;}
        for(int x = 1; x < snake.size(); x++){
            int[] node = snake.get(x);
            if(node[0] == head[0] && node[1] == head[1]){
                return -1;
            }
        }
        if(f.size() > 0){
            int food[] = f.getFirst();
            if(food[0] == head[0] && food[1] == head[1]){
                //System.out.println("s "+head[0]+", "+head[1]);
                snake.add(f.remove());
                return snake.size()-1;
            }
        }
        snake.remove();
        //System.out.println("s "+head[0]+", "+head[1]);
        snake.add(new int[]{head[0],head[1]});
        return snake.size()-1;
    }
}