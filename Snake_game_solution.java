package design;

import java.util.Arrays;
import java.util.LinkedList;

public class Snake_game_solution {
	 LinkedList<int []> snake;
	    LinkedList<int []> food;
	    int width;int height;
	    int [] snakehead;
	    /** Initialize your data structure here.
	        @param width - screen width
	        @param height - screen height 
	        @param food - A list of food positions
	        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
	    public Snake_game_solution(int width, int height, int[][] food) {
	        snake=new LinkedList<>();
	        this.food= new LinkedList<>(Arrays.asList(food));
	        
	        this.width=width;
	        this.height= height;
	        snakehead= new int []{0,0};
	        snake.add(snakehead);
	    }
	    
	    /** Moves the snake.
	        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
	        @return The game's score after the move. Return -1 if game over. 
	        Game over when snake crosses the screen boundary or bites its body. */
	    public int move(String direction) {
	        if(direction.equals("U")) snakehead[0]--;
	        if(direction.equals("D")) snakehead[0]++;
	        if(direction.equals("L")) snakehead[1]--;
	        if(direction.equals("R")) snakehead[1]++;
	        
	        
	        // snake hits boundaries
	        
	        if(snakehead[0]<0|| snakehead[0]>=height||
	          snakehead[1]<0|| snakehead[1]>=width){
	            return -1;
	        }
	        
	        
	        
	        //snake hit 
	        
	        for(int i=1;i<snake.size();i++){
	            int [] curr= snake.get(i);
	            if(curr[0]==snakehead[0]&& curr[1]==snakehead[1]){
	                return -1;
	            }
	        }
	        
	        
	        
	        //food
	        
	        
	        
	        if(!food.isEmpty()){
	            int [] appeared = food.get(0);
	            
	            if(appeared[0]==snakehead[0]&&
	              appeared[1]==snakehead[1]){
	                snake.add(food.remove());
	                
	                return snake.size()-1;
	            }
	        }
	        
	        
	        
	        snake.remove();
	        
	        snake.add(new int [] {snakehead[0],snakehead[1]});
	        
	        
	        
	        return snake.size()-1;
	        
	    }

}
