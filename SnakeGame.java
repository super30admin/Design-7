package week10.day4;

import java.util.LinkedList;

class SnakeGame1 {
	LinkedList<int[]> snakelist;
	int[] snakeHead;
	boolean[][] matrix;
	int width;
	int height;
	int[][] foodList;
	int idx;

	public SnakeGame1(int width, int height, int[][] food) {
		this.width = width;
		this.height = height;
		this.foodList = food;
		snakelist = new LinkedList<>();
		snakeHead = new int[] { 0, 0 };
		snakelist.addFirst(snakeHead);
		matrix = new boolean[height][width];
	}

	public int move(String direction) {
		if (direction.equals("R")) {
			snakeHead[1]++;
		} else if (direction.equals("L")) {
			snakeHead[1]--;
		} else if (direction.equals("U")) {
			snakeHead[0]--;
		} else if (direction.equals("D")) {
			snakeHead[0]++;
		}
		// check new snakeHead is valid
		if (snakeHead[0] < 0 || snakeHead[0] == height || snakeHead[1] < 0 || snakeHead[1] == width) {
			return -1;
		}
		// check new snakeHead hits its body
		if (matrix[snakeHead[0]][snakeHead[1]]) {
			return -1;
		}
		// check food is available at snakeHead
		if (idx < foodList.length) {
			if (snakeHead[0] == foodList[idx][0] && snakeHead[1] == foodList[idx][1]) {
				idx++;
				matrix[snakeHead[0]][snakeHead[1]] = true;
				snakelist.addFirst(new int[] { snakeHead[0], snakeHead[1] });
				return snakelist.size() - 1;
			}
		}
		matrix[snakeHead[0]][snakeHead[1]] = true;
		snakelist.addFirst(new int[] { snakeHead[0], snakeHead[1] });
		
		snakelist.removeLast();
		int[] tail = snakelist.peekLast();
		matrix[tail[0]][tail[1]] = false;
		return snakelist.size() - 1;
	}
}

public class SnakeGame {

	public static void main(String[] args) {
		 SnakeGame1 obj = new SnakeGame1(3, 3, new int[][]{{2, 0}, {0, 0}, {0, 2}, {0, 1}, {2, 2}, {0, 1}});
	        System.out.println(obj.move("D"));
	        System.out.println(obj.move("D"));
	        System.out.println(obj.move("R"));
	        System.out.println(obj.move("U"));
	        System.out.println(obj.move("U"));
	        System.out.println(obj.move("L"));
	        System.out.println(obj.move("D"));
	        System.out.println(obj.move("R"));
	        System.out.println(obj.move("R"));
	        System.out.println(obj.move("U"));
	        System.out.println(obj.move("L"));
	        System.out.println(obj.move("L"));
	        System.out.println(obj.move("D"));
	        System.out.println(obj.move("R"));
	        System.out.println(obj.move("U"));

	}

}
