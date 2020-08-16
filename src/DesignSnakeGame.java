import java.util.Arrays;
import java.util.LinkedList;

/**
 * https://leetcode.com/problems/design-snake-game/
 * 
 */
public class DesignSnakeGame {
	int height;
	int width;
	int[] snakeHead;
	LinkedList<int[]> snake;
	LinkedList<int[]> foodList;
	
	public DesignSnakeGame(int width, int height, int[][] food) {
		this.width = width;
		this.height = height;
		snake = new LinkedList<>();
		foodList = new LinkedList<>(Arrays.asList(food));
		snakeHead = new int[] {0,0};
		snake.add(snakeHead);
	}
	
	public int move(String direction) {
		if(direction.equals("U")) snakeHead[0]-=1;
		if(direction.equals("D")) snakeHead[0]+=1;
		if(direction.equals("L")) snakeHead[1]-=1;
		if(direction.equals("R")) snakeHead[1]+=1;
		
		//if the head hits the border
		if(snakeHead[0] <0 || snakeHead[0] >= height || snakeHead[1] < 0 || snakeHead[1] >= width) {
			return -1;
		}
		
		//what if snake hits itself
		//skip the first index, its head of Linkedlist and tail of the snake, old tail will never collide with snake since it will move ahead
		for(int i = 1; i<snake.size(); i++) {
			int[] temp = snake.get(i);
			if(temp[0] == snakeHead[0] && temp[1] == snakeHead[1]) {
				return -1;
			}
		}
		
		//snake eats food
		if(!foodList.isEmpty()) {
			int[] appearedFood = foodList.get(0);
			if(appearedFood[0] == snakeHead[0] && appearedFood[1] == snakeHead[1]) {
				snake.add(appearedFood);
				return snake.size()-1;
			}
		}
		
		//normal move
		snake.remove(); //removes tail of snake and head of linkedlist
		snake.add(new int[] {snakeHead[0], snakeHead[1]}); //add snake head
		return snake.size()-1;
	}
}
