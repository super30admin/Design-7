//TC - O(N)
//SC - O(N)

import java.util.*;

class SnakeGame {

	/**
	 * Initialize your data structure here.
	 * 
	 * @param width  - screen width
	 * @param height - screen height
	 * @param food   - A list of food positions E.g food = [[1,1], [1,0]] means the
	 *               first food is positioned at [1,1], the second is at [1,0].
	 */
	int width;
	int height;
	int[] snakehead;
	LinkedList<int[]> snake = new LinkedList<>();
	LinkedList<int[]> foodList;

	public SnakeGame(int width, int height, int[][] food) {
		this.height = height;
		this.width = width;
		snakehead = new int[] { 0, 0 };
		snake.add(snakehead);
		foodList = new LinkedList<>(Arrays.asList(food));
	}

	/**
	 * Moves the snake.
	 * 
	 * @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down
	 * @return The game's score after the move. Return -1 if game over. Game over
	 *         when snake crosses the screen boundary or bites its body.
	 */
	public int move(String direction) {
		if (direction.equals("U"))
			snakehead[0] -= 1;
		if (direction.equals("D"))
			snakehead[0] += 1;
		if (direction.equals("L"))
			snakehead[1] -= 1;
		if (direction.equals("R"))
			snakehead[1] += 1;
		// Hit Borders
		if (snakehead[0] < 0 || snakehead[0] > height - 1 || snakehead[1] < 0 || snakehead[1] > width - 1) {
			return -1;
		}
		// Snake hits itself
		for (int i = 1; i < snake.size(); i++) {
			int[] curr = snake.get(i);
			if (curr[0] == snakehead[0] && curr[1] == snakehead[1])
				return -1;
		}
		// Eat food
		if (foodList.size() != 0) {
			int[] appearedfood = foodList.get(0);
			if (appearedfood[0] == snakehead[0] && appearedfood[1] == snakehead[1]) {
				snake.add(foodList.remove());
				return snake.size() - 1;
			}
		}
		// Move without eating
		snake.remove();
		snake.add(new int[] { snakehead[0], snakehead[1] });
		return snake.size() - 1;
	}
}

/**
 * Your SnakeGame object will be instantiated and called as such: SnakeGame obj
 * = new SnakeGame(width, height, food); int param_1 = obj.move(direction);
 */
