// Time Complexity : O(n)
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No
package problem2;

import java.util.Arrays;
import java.util.LinkedList;

public class SnakeGame {
	LinkedList<int[]> snake;
	LinkedList<int[]> foodList;
	int width;
	int height;
	int[] snakeHead;

	/**
	 * Initialize your data structure here.
	 * 
	 * @param width  - screen width
	 * @param height - screen height
	 * @param food   - A list of food positions E.g food = [[1,1], [1,0]] means the
	 *               first food is positioned at [1,1], the second is at [1,0].
	 */
	public SnakeGame(int width, int height, int[][] food) {
		this.width = width;
		this.height = height;

		this.snake = new LinkedList<>();
		foodList = new LinkedList<>(Arrays.asList(food));

		snakeHead = new int[] { 0, 0 };
		snake.add(snakeHead);
	}

	/**
	 * Moves the snake.
	 * 
	 * @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down
	 * @return The game's score after the move. Return -1 if game over. Game over
	 *         when snake crosses the screen boundary or bites its body.
	 */
	public int move(String direction) {
		switch (direction) {
		case "U":
			snakeHead[0]--;
			break;

		case "D":
			snakeHead[0]++;
			break;

		case "L":
			snakeHead[1]--;
			break;

		case "R":
			snakeHead[1]++;
			break;
		}

		// Snake Hits Walls
		if (snakeHead[0] == height || snakeHead[0] < 0 || snakeHead[1] == width || snakeHead[1] < 0) {
			return -1;
		}
		// Snake Hits Itself
		for (int i = 1; i < snake.size(); i++) {
			int[] currCell = snake.get(i);
			if (currCell[0] == snakeHead[0] && currCell[1] == snakeHead[1]) {
				return -1;
			}
		}
		// Snake eats Food
		if (!foodList.isEmpty()) {
			int[] currFoodCell = foodList.getFirst();
			if (snakeHead[0] == currFoodCell[0] && snakeHead[1] == currFoodCell[1]) {
				snake.add(foodList.remove());
				return snake.size() - 1;
			}
		}
		// Normal Move in the grid
		snake.remove();
		snake.add(new int[] { snakeHead[0], snakeHead[1] });

		return snake.size() - 1;
	}

	public static void main(String[] args) {
		SnakeGame obj = new SnakeGame(3, 2, new int[][] { { 1, 2 }, { 0, 1 } });
		System.out.println("Move: R, Score: " + obj.move("R"));
		System.out.println("Move: D, Score: " + obj.move("D"));
		System.out.println("Move: R, Score: " + obj.move("R"));
		System.out.println("Move: U, Score: " + obj.move("U"));
		System.out.println("Move: L, Score: " + obj.move("L"));
		System.out.println("Move: U, Score: " + obj.move("U"));
	}
}
