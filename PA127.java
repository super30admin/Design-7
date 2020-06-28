//Leetcode : 353. Design Snake Game
//Time Complexity: O(n), where n=width*height.
//Space Complexity: O(n), size of the List having nodes of snake, n is width*height.
class SnakeGame {

    /** Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
    LinkedList<int[]> snake;
    int fp;
    int[][] food;
    int w;
    int h;
    public SnakeGame(int width, int height, int[][] food) {
        snake= new LinkedList<>();
        fp=0;
        this.food=food;
        w= width;
        h= height;
        snake.add(new int[]{0,0});
    }
    
    /** Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body. */
    public int move(String direction) {
        int[] h2 = snake.get(snake.size()-1);
        int[] head= new int[]{h2[0],h2[1]}; //new is required otherwise it will update the all the elements in list 
        if(direction.equals("U")) head[0]-=1;
        else if(direction.equals("D")) head[0]+=1;
        else if(direction.equals("L")) head[1]-=1;
        else if(direction.equals("R")) head[1]+=1;      
        if(head[0]<0 || head[0]>=h || head[1]<0 || head[1]>=w) return -1;
        if(fp<food.length){         
            int[] f= food[fp];         
            if(f[0]==head[0] && f[1]==head[1]){
                snake.add(head);
                fp++;
                return snake.size()-1;
            }
        }
        snake.remove(0);    
        for(int i=0;i<snake.size();i++){
            int[] n= snake.get(i);
            if(head[0]==n[0] && head[1]==n[1]) return -1;
        }   
        snake.add(head);
        return snake.size()-1;
    }
    
}
